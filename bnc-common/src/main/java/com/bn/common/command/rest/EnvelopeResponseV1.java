package com.bn.common.command.rest;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class EnvelopeResponseV1 {
    private Object payload;
    private List<NotificationV1> notifications;
    private Error error;
    private Error frameworkError;
    private Long messageId;

    @Data
    @Builder(toBuilder = true)
    static class Error {
        private String errorCode;
        private String errorText;
        private String errorDesc;
    }

    @Data
    @Builder(toBuilder = true)
    static class NotificationV1 {
        private NotificationCategory category;
        private Long time;
        private String description;
        private boolean transieent;

    }

    enum NotificationCategory {
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
