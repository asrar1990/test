package com.bn.common.command.rest;

import com.bn.common.command.gpb.Command;
import com.bn.common.dto.common.RetailerInfo;
import com.bn.common.exception.BNException;
import com.bn.common.exception.BNFrameWorkException;
import com.bn.common.util.HttpHeaderUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by sbose on 5/5/23.
 */
@Slf4j
@Getter
public abstract class AbstractRestController<R> implements Rest<R> {
    private boolean hasExecuted;
    private BNException exception;
    private String command;
    private R response;

    private RetailerInfo retailer = new RetailerInfo("BN2", "US");
    private Locale locale = new Locale("en", "US");

    protected abstract R execute() throws Exception;
    protected abstract void deSerialize(int version, Object message) throws Exception;
    protected abstract Object serialize() throws BNException;


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

    public EnvelopeResponseV1 executeCommand(EnvelopeRequestV1 envelope, HttpServletRequest request) throws BNException {
        EnvelopeResponseV1.EnvelopeResponseV1Builder eEnvelope = EnvelopeResponseV1.builder();

        int version = 1;
        String endpoint = null;
        try {
            String clientIp = HttpHeaderUtil.getClientIP(request);
            version = getVersionFromRequest(request);

            endpoint = envelope.getCommand();
            int apiVersion = envelope.getVersion();
            String threadName = getThreadName(Thread.currentThread().getName());
            this.command = endpoint;

            log.info("Rest : {}, Version : {}, ApiVersion : {}, ClientIP : {}, Thread : {}", endpoint, version,
                    apiVersion, clientIp, threadName);

            deSerialize(version, envelope.getPayload());

            executeCommand();

            Object message = serialize();
            if (message != null) {
                eEnvelope.payload(message);
            }
        } catch(BNFrameWorkException bn) {
            eEnvelope.frameworkError(getError(bn));
        } catch(BNException bn) {
            eEnvelope.error(this.getError(bn));
        } catch(Exception ex) {
            eEnvelope.error(getError(BNException.getInstance("BS0000", "Error executing the command", ex)));
        }

        return eEnvelope.build();
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

    protected EnvelopeResponseV1.Error getError(Exception bn) {
        final EnvelopeResponseV1.Error.ErrorBuilder error = EnvelopeResponseV1.Error.builder();
        error.errorCode("ER0001");
        error.errorDesc("Error executing the command");
        error.errorText("Error executing the command");
        return error.build();
    }

    private final String STATUS_HEADER="X-BNCloud-Status";

    private void setStatusHeader(final HttpServletResponse response, final EnvelopeResponseV1 envelope) {
        if( envelope == null || response == null ){
            return;
        }
        try {
            if( envelope.getError() != null){
                response.setHeader(STATUS_HEADER, envelope.getError().getErrorCode());
            }else if( envelope.getFrameworkError() != null ){
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
