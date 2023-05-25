package com.bn.device.command.rest;

import brave.Tracer;
import com.bn.common.dto.util.StringUtil;
import com.bn.common.exception.BNException;
import com.bn.common.validator.AccountValidator;
import com.bn.device.service.DeviceService;
import com.bn.rest.model.passphrase.GetPassPhraseRequest;
import com.bn.rest.model.passphrase.GetPassPhraseResponse;
import com.bn.rest.model.passphrase.Notifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sbose on 5/5/23.
 */
@Service
@Slf4j
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class EndpointGetHashRestV3 {

    @Autowired
    private Tracer tracer;

    @Autowired
    @Qualifier("deviceServiceImpl")
    DeviceService deviceService;
    
    public GetPassPhraseResponse execute(final GetPassPhraseRequest request,
            final HttpServletRequest httpServletRequest) throws BNException {
        GetPassPhraseResponse response = new GetPassPhraseResponse();
        try {
            String sleuthTrackingId = tracer.currentSpan().context().traceIdString();
            String bnTrackingId = httpServletRequest.getHeader("bn-transactionid");
            log.info("Trace ID : " + sleuthTrackingId + " <<>> BN Tracking ID : " + bnTrackingId);
            //Validate required data
            AccountValidator.validateEndpointGetHash(request.getUniqueId(), request.getModelId());
            String passphrase = deviceService.endpointGetHash(request.getUniqueId(), request.getModelId());
            response.setPassphrase(passphrase);
            response.setMessageid(StringUtil.isNotEmpty(bnTrackingId) ? bnTrackingId : sleuthTrackingId);
            List<Notifications> notificationV1List = new ArrayList<>();
            Notifications notificationV1 = new Notifications();
            notificationV1.category(Notifications.CategoryEnum.NUMBER_2);
            notificationV1.setTime(LocalDateTime.now());
            notificationV1.setDesc("Passphrase notification");
            notificationV1.setTransient(Boolean.TRUE);
            notificationV1List.add(notificationV1);
            response.notifications(notificationV1List);
        } catch (BNException be) {
            log.info("Exception during executing the service : " + be.getErrorDesc());
            throw be;

        } catch (Exception e) {
            StringWriter desc = new StringWriter();
            desc.append("Exception during executing the service:uniqueid:").append(request.getUniqueId())
                    .append(":model:").append(request.getModelId());
            throw BNException.getInstance("AD9999", desc.toString());
        }
        return response;
    }
    
}
