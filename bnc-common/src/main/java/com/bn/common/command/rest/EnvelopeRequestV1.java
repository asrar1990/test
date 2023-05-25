package com.bn.common.command.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by kumaresan on 5/5/23.
 */
@Data
@Builder(toBuilder = true)
public class EnvelopeRequestV1 {
    private String command;
    private int version;
    private Object payload;
    private AuthRequestV1 auth;
    private String deviceVersion;
    private Long messageId;
    private String provider;
    private LocaleV1 userLocale;
    private List<AdditionalInfoV1> additionalInfos;
    private List<RequestNotificationV1> requestNotifications;
    private String providerCountry;


    @Data
    class AuthRequestV1 {
        private DeviceCheckV1 deviceCheck;
        private AccountCheckV1 accountCheck;
        private List<AppTokenV1> appToken;
    }

    @Data
    class AccountCheckV1 {
        private Long accountId;
        private String token;
        private Long profileId;
    }

    @Data
    class DeviceCheckV1 {
        private String deviceId;
        private String token;
    }

    @Data
    class AppTokenV1 {
        private String appName;
        private String token;
        private String tokenExpireTime;
    }

    @Data
    class AdditionalInfoV1 {
        private String key;
        private String value;
        private String type;
    }

    @Data
    class RequestNotificationV1 {
        private Long time;
        private int category; //values from GPBAppConstants.RequestNotificationCategory enum
        private byte[] data; //potentially json-formatted such as: {"action":"getProductDetails","ean":"xxxxxxxxxxxxxxx","listIdHash":"xxxxx-xxxxx-xxxxx--xxxxxx","position":25,"offset":"3", "page":"4","context":"SHOP","feedback":"like it"}
    }

    @Data
    class LocaleV1 {
        private String language;
        private String country;
    }
}
