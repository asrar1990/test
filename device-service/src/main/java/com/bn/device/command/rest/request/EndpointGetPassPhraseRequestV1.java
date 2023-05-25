package com.bn.device.command.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Created by sbose on 8/5/23.
 */
@Data
@Builder(toBuilder = true)
public class EndpointGetPassPhraseRequestV1 {
    @Schema(description = "Device Serial number")
    private String uniqueId;
    @Schema(description = "Device Model Id")
    private String modelId;
}
