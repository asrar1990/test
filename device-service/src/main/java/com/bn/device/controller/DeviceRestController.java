package com.bn.device.controller;

import com.bn.common.exception.BNException;
import com.bn.device.command.rest.EndpointGetHashRestV3;
import com.bn.rest.model.passphrase.GetPassPhraseRequest;
import com.bn.rest.model.passphrase.GetPassPhraseResponse;
import com.bn.rest.model.passphrase.InlineResponseDefault;
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

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
@Slf4j
public class DeviceRestController {
    
    @Autowired
    @Qualifier("endpointGetHashRestV3")
    EndpointGetHashRestV3 endpointGetHashRest;

    @PostMapping(value = "/getPassPhrase")
    public ResponseEntity<Object> endpointGetPassPhrasePost(@RequestBody final GetPassPhraseRequest jsonRequest
            , final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        try {
            log.info("Request : " + jsonRequest.toString());
            GetPassPhraseResponse gpbResponse = endpointGetHashRest.execute(jsonRequest, httpServletRequest);
            log.info("Response : " + gpbResponse.toString());
            return ResponseEntity.ok().body(gpbResponse);
        } catch (BNException e) {
            InlineResponseDefault errorResponse = new InlineResponseDefault();
            errorResponse.message("The request is invalid. Please check the request body and parameters.")
                    .code("INVALID_REQUEST")
                    .status("400");
            log.error("Unexpected exception while executing the EndpointGetPassPhrase command", e);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        }
    }
}

