package org.apache.http.util;

import java.net.*;

public final class NetUtils
{
    public static void formatAddress(final StringBuilder sb, final SocketAddress socketAddress) {
        Args.notNull(sb, "Buffer");
        Args.notNull(socketAddress, "Socket address");
        if (socketAddress instanceof InetSocketAddress) {
            final InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
            final InetAddress address = inetSocketAddress.getAddress();
            sb.append((address != null) ? address.getHostAddress() : address).append(':').append(inetSocketAddress.getPort());
        }
        else {
            sb.append(socketAddress);
        }
    }
}
