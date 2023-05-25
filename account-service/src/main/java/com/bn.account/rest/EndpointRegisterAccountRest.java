package com.bn.account.rest;

import brave.Tracer;
import com.bn.account.rest.model.RegisterAccountRequest;
import com.bn.account.rest.model.RegisterAccountResponse;
import com.bn.account.service.AccountService;
import com.bn.account.validator.AccountValidator;
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
public class EndpointRegisterAccountRest {

    @Autowired
    private Tracer tracer;

    @Autowired
    @Qualifier("accountServiceImpl")
    AccountService accountService;


    public RegisterAccountResponse execute(final RegisterAccountRequest request,
            final HttpServletRequest httpServletRequest) throws BNException {
        RegisterAccountResponse response;
        try {
            String sleuthTrackingId = tracer.currentSpan().context().traceIdString();
            String bnTrackingId = httpServletRequest.getHeader("bn-transactionid");
            log.info("Trace ID : " + sleuthTrackingId + " <<>> BN Tracking ID : " + bnTrackingId);
            //Validate required data
            AccountValidator.validateRegisterUserDevice(request.getAccount(), request.getDevice(), request.getPassword()
                    , request.getUserHash(), request.getUserRand());

            response = accountService.endpointRegisterAccount(request.getAccount(), request.getDevice(),
                    request.getPassword(), request.getUserHash(), request.getUserRand(), true);


        } catch (BNException be) {
            log.info("unexpected error registering account: " + be.getErrorDesc());
            throw be;

        } catch (Exception e) {
            StringWriter desc = new StringWriter();
            desc.append("unexpected error registering account:userHash:").append(request.getUserHash())
                    .append(":rand:").append(request.getUserRand());
            throw BNException.getInstance("AD8221", desc.toString());
        }
        return response;
    }
    
}
