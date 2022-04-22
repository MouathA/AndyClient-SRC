package io.netty.channel.socket.nio;

import io.netty.channel.socket.*;
import java.net.*;

final class ProtocolFamilyConverter
{
    private ProtocolFamilyConverter() {
    }
    
    public static ProtocolFamily convert(final InternetProtocolFamily internetProtocolFamily) {
        switch (internetProtocolFamily) {
            case IPv4: {
                return StandardProtocolFamily.INET;
            }
            case IPv6: {
                return StandardProtocolFamily.INET6;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
    }
}
