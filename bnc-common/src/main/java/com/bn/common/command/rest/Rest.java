package com.bn.common.command.rest;

import com.bn.common.exception.BNException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by sbose on 6/4/23.
 */
public interface Rest<R> {
    R getResponse() throws BNException;

    EnvelopeResponseV1 executeCommand(final EnvelopeRequestV1 content, HttpServletRequest request) throws BNException;

    String getCommand();
}
