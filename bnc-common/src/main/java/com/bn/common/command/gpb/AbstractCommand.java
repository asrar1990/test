package com.bn.common.command.gpb;

import brave.Tracer;
import com.bn.common.util.HttpHeaderUtil;
import com.bn.common.dto.common.RetailerInfo;
import com.bn.common.exception.BNException;
import com.bn.common.exception.BNFrameWorkException;
import com.bn.gpb.GpbCommons;
import com.bn.gpb.envelope.Envelope;
import com.google.protobuf.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Locale;

/**
 * Created by sbose on 5/4/23.
 */
@Slf4j
@Getter
public abstract class AbstractCommand<R> implements Command<R> {

    @Autowired
    private Tracer tracer;

    private boolean hasExecuted;
    private BNException exception;
    private String command;
    private R response;

    private RetailerInfo retailer = new RetailerInfo("BN2", "US");
    private Locale locale = new Locale("en", "US");

    protected abstract R execute() throws Exception;
    protected abstract void deSerialize(int version, ByteString message) throws Exception;
    protected abstract GeneratedMessageLite serialize() throws BNException;


    @Override
    public final R getResponse() throws BNException {
        if(!hasExecuted) {
            // Error command not executed.
            throw BNException.getInstance("BS0000");
        }
        if(exception != null) {
            throw exception;
        }
        return response;
    }

    public byte[] executeCommand(final byte[] content, HttpServletRequest request) throws BNException {
        Envelope.EnvelopeResponseV1.Builder eEnvelope = Envelope.EnvelopeResponseV1.newBuilder();
        int version = 1;
        String endpoint = null;
        try {
            String bnTrackingId = request.getHeader("bn-transactionid");
            log.info("Trace ID : " + tracer.currentSpan().context().traceIdString()
                    + " <<>> BN Tracking ID : " + bnTrackingId);
            
            String clientIp = HttpHeaderUtil.getClientIP(request);
            version = getVersionFromRequest(request);
            Envelope.EnvelopeRequestV1 envelope = Envelope.EnvelopeRequestV1.parseFrom(content);

            endpoint = envelope.getCommand();
            int apiVersion = envelope.getVersion();
            String threadName = getThreadName(Thread.currentThread().getName());
            this.command = endpoint;

            log.info("Command : {}, Version : {}, ApiVersion : {}, ClientIP : {}, Thread : {}", endpoint, version,
                    apiVersion, clientIp, threadName);
            
            ByteString obj = envelope.getPayload();

            deSerialize(version, obj);

            executeCommand();

            GeneratedMessageLite message = serialize();
            ByteArrayOutputStream bos = null;
            if (message != null) {
                bos = new ByteArrayOutputStream();
                message.writeTo(bos);
                bos.close();
                ByteString b = ByteString.copyFrom(bos.toByteArray());
                eEnvelope.setPayload(b);
            }
        } catch(BNFrameWorkException bn) {
            log.error("Framework Error:", bn.getCause());
            eEnvelope.setFrameworkError(getError(bn));
        } catch(BNException bn) {
            log.error("BNException Error:", bn.getCause());
            eEnvelope.setError(this.getError(bn));
        } catch(Exception ex) {
            eEnvelope.setError(getError(BNException.getInstance("BS0000", "Error executing the command", ex)));
        }

        byte[] responseData = eEnvelope.build().toByteArray();
        return responseData;
    }

    private void executeCommand() {
        try {
            response = execute();
        } catch(BNException be) {
            //MG, updating 9/16 .. Should not be logging BNExceptions as errors!  This is at the discretion of developers of individual commands
            //this.getLogger().error("Error in executing the command " + be.getErrorCode() + "--" + be.getErrorDesc() + "--" + be.getDescription());
            exception = be;
        }
        catch(Exception ex) {
            exception = BNException.getInstance("BS0003");
            log.error("Exception in executing the command " + getCommand() + "- FIX THIS ASAP ", ex);
        }
        hasExecuted = true;
    }

    protected GpbCommons.Error getError(Exception bn) {
        final GpbCommons.Error.Builder error = GpbCommons.Error.newBuilder();
        error.setErrorCode("ER0001");
        error.setErrorDesc("Error executing the command");
        error.setErrorText("Error executing the command");
        return error.build();
    }

    private final String STATUS_HEADER="X-BNCloud-Status";

    private void setStatusHeader(final HttpServletResponse response,
            final Envelope.EnvelopeResponseV1.Builder envelope) {
        if( envelope == null || response == null ){
            return;
        }
        try {
            if( envelope.hasError()){
                response.setHeader(STATUS_HEADER, envelope.getError().getErrorCode());
            }else if( envelope.hasFrameworkError() ){
                response.setHeader(STATUS_HEADER,envelope.getFrameworkError().getErrorCode());
            }else{
                response.setHeader(STATUS_HEADER,"200");
            }
        }catch(NullPointerException exp){
            log.error("unable to set X-BNCloud-Status",exp);
        }
    }
    
    protected int getVersionFromRequest(HttpServletRequest request) {
        String versionStr = request.getParameter("v");
        if(versionStr == null) {
            versionStr = request.getParameter("version");
        }
        int version = 1;
        if(versionStr != null) {
            try {
                version = Integer.parseInt(versionStr);
            }
            catch(Exception ex) {
                log.info("Invalid version of the API version=" + version + ". Defaulting to 1", ex);
            }
        }
        else {
            log.info("Invalid version of the API version=" + version + ". Defaulting to 1");
        }
        return version;
    }

    protected String getThreadName(String name) {
        String threadName = name;
        if(!threadName.startsWith("Engine")) {
            threadName = "Engine-" + Thread.currentThread().getId();
            Thread.currentThread().setName(threadName);
        }
        return threadName;
    }

    public static BNException newSerializeBnException(String errorCode, Command command, Exception e) {
        final String desc = "serialize";
        log.error(desc + command, e);
        return BNException.getInstance(errorCode, desc + ", " + e.getMessage(), e);
    }

    public String getCommand() {
       return this.command;
    }
}
