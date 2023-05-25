package com.bn.device.command.gpb;

import com.bn.common.command.gpb.AbstractCommand;
import com.bn.common.command.gpb.AbstractCommandResponse;
import com.bn.common.exception.BNException;
import com.bn.common.validator.AccountValidator;
import com.bn.device.command.gpb.response.EndpointGetPassPhraseCommandResponse;
import com.bn.gpb.device.GpbDevice;
import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageLite;
import com.bn.device.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.StringWriter;

/**
 * Created by sbose on 23/4/23.
 */
@Service
@Slf4j
public class EndpointGetHashCommand extends AbstractCommand {

    @Autowired
    @Qualifier("deviceServiceImpl")
    DeviceService deviceService;

    private int version;
    private String uniqueId;
    private String modelId;

    public void deSerialize(int version, ByteString message) throws Exception {
        GpbDevice.EndpointGetPassPhraseRequestV1 endpointGetHashReq =
                GpbDevice.EndpointGetPassPhraseRequestV1.parseFrom(message.toByteArray());

        this.version = version;
        this.uniqueId = endpointGetHashReq.getUniqueid();
        this.modelId = endpointGetHashReq.getModelid();
    }

    public GeneratedMessageLite serialize() throws BNException {
        GpbDevice.EndpointGetPassPhraseResponseV1.Builder result = GpbDevice.EndpointGetPassPhraseResponseV1.newBuilder();
        try {

            EndpointGetPassPhraseCommandResponse response = (EndpointGetPassPhraseCommandResponse) this.getResponse();
            result.setPassphrase(response.getPassphrase());
        } catch (BNException be) {
            throw be;
        } catch (Exception e) {
            throw EndpointGetHashCommand.newSerializeBnException("AD8099", this, e);
        }
        return result.build();
    }

    @Override
    protected AbstractCommandResponse execute() throws Exception {
        //Validate required data
        AccountValidator.validateEndpointGetHash(uniqueId, modelId);

        EndpointGetPassPhraseCommandResponse response = new EndpointGetPassPhraseCommandResponse(this.getCommand());
        try {
            String passphrase = deviceService.endpointGetHash(uniqueId, modelId);
            log.info("passphrase:" + passphrase);
            response.setPassphrase(passphrase);

        } catch (BNException be) {
            log.info("execute:" + be.getErrorDesc());
            throw be;

        } catch (Exception e) {
            StringWriter desc = new StringWriter();
            desc.append("execute:uniqueid:").append(uniqueId)
                    .append(":model:").append(modelId);
            throw BNException.getInstance("AD9999", desc.toString());
        }
        return response;
    }
}