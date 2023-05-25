package com.bn.common.dto.util;

import org.apache.commons.lang3.StringUtils;

public class ToStringUtil {
    /**
     * Wraps any string (possibly containing spaces) with double quotes around it.
     * If your values contain spaces, wrap them in quotes (for example, username="bob smith").
     * {@linktourl http://dev.splunk.com/view/logging-best-practices/SP-CAAADP6}
     * @param in
     * @return
     */
    public static String wrapDoubleQuotes(String in) {
        if(StringUtils.isBlank(in)){
            return in;
        }
        if(in.indexOf(' ') == -1) {
            return in;
        }
        return "\""+in+"\"";
    }
}
