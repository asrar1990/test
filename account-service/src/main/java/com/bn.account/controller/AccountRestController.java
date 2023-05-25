package com.bn.account.controller;

import com.bn.account.rest.CreateAccountProfileRest;
import com.bn.account.rest.EndpointRegisterAccountRest;
import com.bn.account.rest.model.Error;
import com.bn.account.rest.model.RegisterAccountRequest;
import com.bn.account.rest.model.RegisterAccountResponse;
import com.bn.common.exception.BNException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by sbose on 16/5/23.
 */
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Slf4j
public class AccountRestController {
    
    @Autowired
    @Qualifier("endpointRegisterAccountRest")
    EndpointRegisterAccountRest endpointRegisterAccount;

    @Autowired
    @Qualifier("createAccountProfileRest")
    CreateAccountProfileRest createAccountProfile;

    @PostMapping(value = "/endpointRegisterAccount")
    public ResponseEntity<Object> endpointGetPassPhrasePost(@RequestBody final RegisterAccountRequest jsonRequest
            , final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        try {
            log.info("Request : " + jsonRequest.toString());
            RegisterAccountResponse gpbResponse = endpointRegisterAccount.execute(jsonRequest, httpServletRequest);
            log.info("Response : " + gpbResponse.toString());
            return ResponseEntity.ok().body(gpbResponse);
        } catch (BNException e) {
            Error errorResponse = new Error();
            errorResponse.message("The request is invalid. Please check the request body and parameters.")
                    .code("INVALID_REQUEST")
                    .status("400");
            log.error("Unexpected exception while executing the EndpointGetPassPhrase command", e);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        }
    }
    
    /*@PostMapping(value = "/createAccountProfile")
    public ResponseEntity<Object> endpointGetPassPhrasePost(@RequestBody final CreateAccountProfileRequest jsonRequest
            , final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        try {
            log.info("Request : " + jsonRequest.toString());
            CreateAccountProfileResponse gpbResponse = createAccountProfile.execute(jsonRequest, httpServletRequest);
            log.info("Response : " + gpbResponse.toString());
            return ResponseEntity.ok().body(gpbResponse);
        } catch (BNException e) {
            Error errorResponse = new Error();
            errorResponse.message("The request is invalid. Please check the request body and parameters.")
                    .code("INVALID_REQUEST")
                    .status("400");
            log.error("Unexpected exception while executing the EndpointGetPassPhrase command", e);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        }
    }*/
}

