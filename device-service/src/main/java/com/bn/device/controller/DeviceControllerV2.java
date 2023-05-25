package com.bn.device.controller;

/*import com.bn.common.command.gpb.Command;
import com.bn.common.exception.BNException;
import com.bn.device.command.rest.EndpointGetHashRestV2;
import com.bn.device.command.rest.request.EndpointGetPassPhraseRequestV1;
import com.bn.device.command.rest.response.EndpointGetPassPhraseResponseV1;
import com.bn.device.command.rest.response.ErrorResponseV1;
import com.bn.device.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;*/

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
@Slf4j
public class DeviceControllerV2 {


    /*@Autowired
    @Qualifier("deviceServiceImpl")
    DeviceService deviceService;
    
    @Autowired
    @Qualifier("endpointGetHashRestV2")
    EndpointGetHashRestV2 endpointGetHashRest;

    @PostMapping(value = "/getPassPhrase22")
    @Operation(
            tags = {"REST APIs"},
            summary = "API for getting a secret passphrase",
            description = "The GetPassPhrase will return a passphrase, and the client will have to take the passphrase and create hashOfPrivateKey from it.  Then use the hashOfPrivateKey to encrypt the data for registration.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema =  @Schema(implementation = EndpointGetPassPhraseRequestV1.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = EndpointGetPassPhraseResponseV1.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "Default", description = "Error", content = @Content(schema = @Schema(implementation = ErrorResponseV1.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            }
    )
    public ResponseEntity<Object> endpointGetPassPhrasePost(@RequestBody final EndpointGetPassPhraseRequestV1 jsonRequest
            , final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        try {
            EndpointGetPassPhraseResponseV1 gpbResponse = endpointGetHashRest.execute(jsonRequest);
            return ResponseEntity.ok().body(gpbResponse);
        } catch (BNException e) {
            ErrorResponseV1.ErrorResponseV1Builder errorResponse = ErrorResponseV1.builder();
            errorResponse.message("The request is invalid. Please check the request body and parameters.")
                    .code("INVALID_REQUEST")
                    .status("400");
            log.error("Unexpected exception while executing the EndpointGetPassPhrase command", e);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        }
    }*/
}

