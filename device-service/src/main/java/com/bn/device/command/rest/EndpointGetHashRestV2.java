package com.bn.device.command.rest;

import brave.Tracer;
import com.bn.common.exception.BNException;
import com.bn.common.validator.AccountValidator;
import com.bn.device.command.rest.request.EndpointGetPassPhraseRequestV1;
import com.bn.device.command.rest.response.EndpointGetPassPhraseResponseV1;
import com.bn.device.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sbose on 5/5/23.
 */
@Service
@Slf4j
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class EndpointGetHashRestV2 {

    @Autowired
    private Tracer tracer;

    @Autowired
    @Qualifier("deviceServiceImpl")
    DeviceService deviceService;
    
    public EndpointGetPassPhraseResponseV1 execute(EndpointGetPassPhraseRequestV1 request) throws BNException {
        //Validate required data
        AccountValidator.validateEndpointGetHash(request.getUniqueId(), request.getModelId());
        EndpointGetPassPhraseResponseV1.EndpointGetPassPhraseResponseV1Builder response =
                EndpointGetPassPhraseResponseV1.builder();
        try {
            String passphrase = deviceService.endpointGetHash(request.getUniqueId(), request.getModelId());
            response.passphrase(passphrase);
            response.messageId(tracer.currentSpan().context().traceIdString());

            List<EndpointGetPassPhraseResponseV1.NotificationV1> notificationV1List = new ArrayList<>();
            EndpointGetPassPhraseResponseV1.NotificationV1.NotificationV1Builder notificationV1 =
                    EndpointGetPassPhraseResponseV1.NotificationV1.builder();
            notificationV1.category(EndpointGetPassPhraseResponseV1.NotificationCategory.LIST)
                    .time(System.currentTimeMillis())
                    .desc("Passphrase notification");
            notificationV1List.add(notificationV1.build());
            response.notifications(notificationV1List);
        } catch (BNException be) {
            log.info("execute:" + be.getErrorDesc());
            throw be;

        } catch (Exception e) {
            StringWriter desc = new StringWriter();
            desc.append("execute:uniqueid:").append(request.getUniqueId())
                    .append(":model:").append(request.getModelId());
            throw BNException.getInstance("AD9999", desc.toString());
        }
        return response.build();
    }
    
}
