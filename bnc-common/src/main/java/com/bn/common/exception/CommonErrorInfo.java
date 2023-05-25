package com.bn.common.exception;

public class CommonErrorInfo {

    private String service;
    private String errorCode;
    private String description;
    private String httpStatus;

    public CommonErrorInfo() {
        init("", "", "", "200");
    }  // mock gunk only; sigh

    public CommonErrorInfo(String service, String errorCode, String description) {
        init(service, errorCode, description, "200");
    }

    public CommonErrorInfo(String service, String errorCode, String description, String httpStatus) {
        init(service, errorCode, description, httpStatus);
    }

    private void init(String service, String errorCode, String description, String httpStatus) {
        this.service = service;
        this.errorCode = errorCode;
        this.description = description;
        this.httpStatus = httpStatus;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

}
