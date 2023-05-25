package com.bn.common.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * Mechanism to differentiate Framework exception from regular exception
 */
public class BNFrameWorkException extends BNException {

    private static final long serialVersionUID = 1L;

    private BNFrameWorkException(String errorCode, Throwable ex) {
        super(errorCode, ex);
    }

    private BNFrameWorkException(String errorCode) {
        super(errorCode);
    }

    public static BNFrameWorkException newException(String errorCode, String description, Throwable e) {
        final BNFrameWorkException exception = new BNFrameWorkException(errorCode, e);
        return setDescription(errorCode, description, exception);
    }

    public static BNFrameWorkException newException(String errorCode, String description) {
        final BNFrameWorkException exception = new BNFrameWorkException(errorCode);
        return setDescription(errorCode, description, exception);
    }

    private static BNFrameWorkException setDescription(String errorCode, String description, BNFrameWorkException exception) {
        exception.description = StringUtils.defaultIfEmpty(description, "Unknown description");
        return exception;
    }
}
