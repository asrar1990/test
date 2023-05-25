package com.bn.device.command.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by sbose on 8/5/23.
 */
@Data
@Builder(toBuilder = true)
public class ErrorResponseV1 {
    @Schema(description = "Response status")
    private String status;
    @Schema(description = "Error code")
    private String code;
    @Schema(description = "Response message")
    private String message;
}
