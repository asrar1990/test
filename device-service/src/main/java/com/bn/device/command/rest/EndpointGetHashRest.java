package com.bn.device.command.rest;

import com.bn.common.command.gpb.AbstractCommandResponse;
import com.bn.common.exception.BNException;
import com.bn.common.command.rest.AbstractRestController;
import com.bn.common.validator.AccountValidator;
import com.bn.device.command.gpb.response.EndpointGetPassPhraseCommandResponse;
import com.bn.device.command.rest.request.EndpointGetPassPhraseRequestV1;
import com.bn.device.command.rest.response.EndpointGetPassPhraseResponseV1;
import com.bn.device.service.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.GeneratedMessageLite;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.StringWriter;

/**
 * Created by sbose on 5/5/23.
 */
@Service
@Slf4j
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class EndpointGetHashRest extends AbstractRestController {

    @Autowired
    @Qualifier("deviceServiceImpl")
    DeviceService deviceService;

    @Autowired
    ObjectMapper mapper;
    
    private int version;
    private String uniqueId;
    private String modelId;
    
    @Override
    protected void deSerialize(int version, Object message) throws Exception {
        String jsonStr = mapper.writeValueAsString(message);
        EndpointGetPassPhraseRequestV1 requestV1 = mapper.readValue(jsonStr, EndpointGetPassPhraseRequestV1.class);
        this.version = version;
        this.uniqueId = requestV1.getUniqueId();
        this.modelId = requestV1.getModelId();
    }

    @Override
    protected Object serialize() throws BNException {
        EndpointGetPassPhraseCommandResponse commandResponse = (EndpointGetPassPhraseCommandResponse) this.getResponse();
        EndpointGetPassPhraseResponseV1.EndpointGetPassPhraseResponseV1Builder response =
                EndpointGetPassPhraseResponseV1.builder();
        response.passphrase(commandResponse.getPassphrase());
        return response.build();
    }
    
    @Override
    protected AbstractCommandResponse execute() throws Exception {
        //Validate required data
        AccountValidator.validateEndpointGetHash(uniqueId, modelId);

        EndpointGetPassPhraseCommandResponse response = new EndpointGetPassPhraseCommandResponse(this.getCommand());
        try {
            String passphrase = deviceService.endpointGetHash(uniqueId, modelId);
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
