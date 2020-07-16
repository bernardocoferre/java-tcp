package com.bernardocoferre.tcp.support;

import org.apache.mina.core.session.IoSession;

import java.net.InetSocketAddress;

public class Addr {

    /**
     * Returns the client IP Address from a given {@link IoSession} instance.
     *
     * @param session the current session
     * @return the client IP Address
     */
    public static String getIpAddress(IoSession session) {
        try {
            return ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
        } catch (NullPointerException e) {
            return null;
        }
    }

}
