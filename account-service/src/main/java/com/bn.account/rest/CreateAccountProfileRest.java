package com.bn.account.rest;

import brave.Tracer;
import com.bn.account.rest.model.CreateAccountProfileRequest;
import com.bn.account.rest.model.CreateAccountProfileResponse;
import com.bn.account.service.AccountService;
import com.bn.common.exception.BNException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.StringWriter;

/**
 * Created by sbose on 16/5/23.
 */
@Service
@Slf4j
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class CreateAccountProfileRest {

    /*@Autowired
    private Tracer tracer;

    @Autowired
    @Qualifier("accountServiceImpl")
    AccountService accountService;
    
    public CreateAccountProfileResponse execute(final CreateAccountProfileRequest request,
            final HttpServletRequest httpServletRequest) throws BNException {
        //Validate required data
        CreateAccountProfileResponse response = new CreateAccountProfileResponse();
        try {
            String sleuthTrackingId = tracer.currentSpan().context().traceIdString();
            String bnTrackingId = httpServletRequest.getHeader("bn-transactionid");
            log.info("Trace ID : " + sleuthTrackingId + " <<>> BN Tracking ID : " + bnTrackingId);

        } catch (BNException be) {
            log.info("Exception during executing the service : " + be.getErrorDesc());
            throw be;

        } catch (Exception e) {
            StringWriter desc = new StringWriter();
            desc.append("Exception during executing the service:firstName:").append(request.getFirstName())
                    .append(":lastName:").append(request.getLastName())
                    .append(":profileType:").append(request.getProfileType());
            throw BNException.getInstance("AD9999", desc.toString());
        }
        return response;
    }*/
}
