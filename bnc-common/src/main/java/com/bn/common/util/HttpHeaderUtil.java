package com.bn.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Slf4j
public class HttpHeaderUtil {
    /**
     * When we're running with a virtual IP (vip), our F5 are configured to pass the actual IP of the caller in one of
     * a pair of headers. This header is the first (authoritative) source of the actual caller IP. If this is not set
     * then we should check X-Forwarded-For next.<p/>
     * <p>
     * This particular header is set by Akamai.
     */
    private static final String HEADER_TRUE_CLIENT_IP = "X-True-Client-IP";

    /**
     * This is the secondary source for the caller IP: if X-True-Client-IP is null we should check this header to see
     * if the F5 has passed our calling IP thusly. If neither X-True-Client-IP, nor this header, are set then we can
     * assume that the actual IP on the request is the originating caller.
     */
    private static final String HEADER_X_FORWARDED_FOR = "X-Forwarded-For";

    public static String getDeviceIDCookie(final HttpServletRequest request) {
        return getCookieValue(request, "endpoint", -1, null);
    }

    public static String getCookieValue(final HttpServletRequest request, final String cookieName, final int version,
            final Boolean secure) {
        if (request.getCookies() != null) {
            for (final Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(cookieName) &&
                        ((version == -1 && secure == null) ||
                                (cookie.getVersion() == version && secure == null) ||
                                (version == -1 && secure == cookie.getSecure()) ||
                                (version == cookie.getVersion() && secure == cookie.getSecure())
                        ))
                    return cookie.getValue();
            }
        }

        return null;
    }

    public static String getPDSCookie(final HttpServletRequest request) {
        return request.getHeader("PDS");
    }

    /**
     * Attempts to find the IP of the caller. This is a little more complicated than it seems because usually we're
     * getting a call from an F5 (load balancer), which means that simply calling
     * {@link HttpServletRequest#getRemoteAddr()} is not sufficient.<p/>
     * <p>
     * The order of precedence is:<p/>
     *
     * <ol>
     * <li>Check for X-True-Client-IP being set by the F5.</li>
     * <li>Check for X-Forwarded-For being set b the F5 - this will be in the format of client IP, proxy 1, proxy 2..
     * .. proxy n.</li>
     * <li>Check for  {@link HttpServletRequest#getRemoteAddr()}, which is the IP of the caller
     * from the HTTPRequest.</li>
     * </ol>
     *
     * @param request A {@link HttpServletRequest} representing the HTTP call.
     * @return A string representation of an IP (IE: 127.0.0.1).
     */
    public static String getClientIP(final HttpServletRequest request) {
        String remoteAddress = request.getRemoteAddr();
        String addressSource = "request.getRemoteAddr()";

        // Try and get the X-True-Client-IP first.
        if (!StringUtils.isBlank(request.getHeader(HEADER_TRUE_CLIENT_IP))) {
            remoteAddress = request.getHeader(HEADER_TRUE_CLIENT_IP);
            addressSource = HEADER_TRUE_CLIENT_IP;
        } else if (!StringUtils.isBlank(request.getHeader(HEADER_X_FORWARDED_FOR))) {
            // If we're missing the X-True-Client-IP from Akamai, try and look for what the F5 sends us.
            remoteAddress = request.getHeader(HEADER_X_FORWARDED_FOR);
            final int commaIndex = remoteAddress.indexOf(',');

            // Chop off any proxies we might have found - we don't really care about those.
            if (commaIndex > -1) {
                remoteAddress = remoteAddress.substring(0, commaIndex);
            }

            addressSource = HEADER_X_FORWARDED_FOR;
        }

        if (log.isDebugEnabled()) {
            log.debug("getClientIP:pull from " + addressSource + ":" + remoteAddress);
        }

        return remoteAddress;
    }
}
