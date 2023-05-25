package com.bn.common.command.gpb;

import com.bn.common.exception.BNException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by sbose on 6/4/23.
 */
public interface Command<R> {
    R getResponse() throws BNException;

    byte[] executeCommand(final byte[] content, HttpServletRequest request) throws BNException;

    String getCommand();
}
