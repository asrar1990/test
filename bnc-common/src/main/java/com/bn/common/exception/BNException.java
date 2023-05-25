package com.bn.common.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by sbose on 23/3/23.
 */
public class BNException extends Exception {
    public static final String UNKNOWN_DESCRIPTION = "Unknown description";
    protected String errorCode;
    protected String description;
    protected String lineAtEx;
    private String errorDesc;

    public BNException(String errorCode) {
        super(errorCode);
        this.description = UNKNOWN_DESCRIPTION;
        this.errorDesc = UNKNOWN_DESCRIPTION;
    }

    public static BNException getInstance(String errorCode) {
        BNException exception = new BNException(errorCode);
        exception.description = UNKNOWN_DESCRIPTION;
        exception.errorDesc = UNKNOWN_DESCRIPTION;
        return exception;
    }

    public static BNException getInstance(String errorCode, String description) {
        BNException exception = new BNException(errorCode);
        exception.description = (description == null) ? UNKNOWN_DESCRIPTION : description;
        exception.errorDesc = description;
        return exception;
    }

    public static BNException getInstance(String errorCode, Throwable ex) {
        return new BNException(errorCode, ex);
    }

    public static BNException getInstance(String errorCode, String description, Throwable ex) {
        final BNException exception = new BNException(errorCode, ex);
        exception.description = StringUtils.defaultString(description, UNKNOWN_DESCRIPTION);
        exception.errorDesc = StringUtils.defaultString(description, UNKNOWN_DESCRIPTION);
        return exception;
    }

    protected BNException(String errorCode, Throwable ex) {
        super(errorCode, ex);
        this.errorCode = errorCode;
        this.description = ":";
        if (ex != null) {
            this.lineAtEx = BNExceptionUtil.getBncErrorAt(ex);
        }
        this.errorDesc = description;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDesc() {
        return this.errorDesc;
    }
}