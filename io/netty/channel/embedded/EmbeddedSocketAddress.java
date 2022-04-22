package io.netty.channel.embedded;

import java.net.*;

final class EmbeddedSocketAddress extends SocketAddress
{
    private static final long serialVersionUID = 1400788804624980619L;
    
    @Override
    public String toString() {
        return "embedded";
    }
}
