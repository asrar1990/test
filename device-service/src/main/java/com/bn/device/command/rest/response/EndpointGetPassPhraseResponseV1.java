package com.bn.device.command.rest.response;

import com.bn.common.command.rest.EnvelopeResponseV1;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by sbose on 8/5/23.
 */
@Data
@Builder(toBuilder = true)
public class EndpointGetPassPhraseResponseV1 {
    @Schema(description = "Secret passphrase")
    private String passphrase;
    @Schema(description = "List of notifications (if any)")
    private List<EndpointGetPassPhraseResponseV1.NotificationV1> notifications;
    @Schema(description = "Unique transaction ID")
    private String messageId;

    @Data
    @Builder(toBuilder = true)
    public static class NotificationV1 {
        private EndpointGetPassPhraseResponseV1.NotificationCategory category;
        private Long time;
        @Schema(description = "The description about the notification.")
        private String desc;
        private boolean transieent;

    }

    public enum NotificationCategory {
        SW,
        SYNC,
        CCH,
        ABC, REFCR,
        SQFR,
        SYNC_TIME,
        SYNC_RP,
        SYNC_BO,
        SYNC_AN, SYNC_LO,
        LRFR,
        LIST,
        SYSTEM_SOFTWARE,
        SYNC_DC,
        SYNC_DC_TIME,
        PROVISION,
        ENTITLEMENT,
        USERPROFILES,
        PARTNER_AUTH,
        PARTNER_DEAUTH,
        SYNC_VL,
        SYNC_VE,
        ACCOUNT_LINK,
        PURCHASE_BLOCK,
        PURCHASE_ALLOW;
    }
}
