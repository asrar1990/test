package com.bn.common.command.rest;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by sbose on 8/5/23.
 */
@Data
@Builder(toBuilder = true)
public class EndpointPassphraseRequestV1 {
    private String uniqueId;
    private String modelId;
}
