package com.bn.device.controller;

import com.bn.common.command.gpb.Command;
import com.bn.common.command.rest.*;
import com.bn.common.exception.BNException;
import com.bn.device.command.rest.EndpointGetHashRestV2;
import com.bn.device.command.rest.request.EndpointGetPassPhraseRequestV1;
import com.bn.device.command.rest.response.EndpointGetPassPhraseResponseV1;
import com.bn.device.command.rest.response.ErrorResponseV1;
import com.bn.device.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/device")
@RequiredArgsConstructor
@Slf4j
public class DeviceGPBController {

    @Autowired
    private Environment env;

    @Autowired
    @Qualifier("endpointGetHashCommand")
    Command endpointGetHashCommand;

    @Autowired
    @Qualifier("endpointRegisterCommand")
    Command endpointRegisterCommand;

    @Autowired
    @Qualifier("deviceServiceImpl")
    DeviceService deviceService;
    

    @PostMapping(value = "endpointGetPassPhrase")
    @Operation(
            tags = {"GPB APIs"},
            hidden = true
    )
    public ResponseEntity<byte[]> endpointGetPassPhrase(final @RequestBody byte[] gpbRequest, final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        try {
            byte[] gpbResponse = endpointGetHashCommand.executeCommand(gpbRequest, httpServletRequest);
            return ResponseEntity.ok().body(gpbResponse);
        } catch (BNException e) {
            log.error("Unexpected exception while executing the EndpointGetPassPhrase command", e);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "endpointRegister")
    @Operation(
            tags = {"GPB APIs"},
            hidden = true
    )
    public ResponseEntity<byte[]> endpointRegister(final @RequestBody byte[] gpbRequest, final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        try {
            byte[] gpbResponse = endpointRegisterCommand.executeCommand(gpbRequest, httpServletRequest);
            return ResponseEntity.ok().body(gpbResponse);
        } catch (BNException e) {
            log.error("Unexpected exception while executing the EndpointGetPassPhrase command", e);
        }
        return ResponseEntity.badRequest().build();
    }

   /* @GetMapping(value = "getPassPhrase")
    public ResponseEntity<EnvelopeResponseV1> endpointGetPassPhrase(@RequestBody final EnvelopeRequestV1 jsonRequest, final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        try {
            EnvelopeResponseV1 gpbResponse = endpointGetHashRest.executeCommand(jsonRequest, httpServletRequest);
            return ResponseEntity.ok().body(gpbResponse);
        } catch (BNException e) {
            log.error("Unexpected exception while executing the EndpointGetPassPhrase command", e);
        }
        return ResponseEntity.badRequest().build();
    }*/
    
    /*@GetMapping("requestor/{requestor}")
    @Operation(
            tags = {"Device"},
            operationId = "API for check open api",
            summary = "API for getting a secret passphrase",
            description = "This is description",
            parameters = {@Parameter(name = "requestor", description = "Requestor name", example = "saravana",
                    in= ParameterIn.PATH)}
    )
    public ResponseEntity<String> opeapicheck(@PathVariable(name = "requestor") String requestor) {
        return ResponseEntity.ok().body("Welcome to Device Service!" + requestor);
    }*/
}

