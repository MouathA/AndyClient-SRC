package io.netty.channel.local;

import java.util.concurrent.*;
import java.net.*;
import io.netty.channel.*;
import io.netty.util.internal.*;

final class LocalChannelRegistry
{
    private static final ConcurrentMap boundChannels;
    
    static LocalAddress register(final Channel channel, final LocalAddress localAddress, final SocketAddress socketAddress) {
        if (localAddress != null) {
            throw new ChannelException("already bound");
        }
        if (!(socketAddress instanceof LocalAddress)) {
            throw new ChannelException("unsupported address type: " + StringUtil.simpleClassName(socketAddress));
        }
        LocalAddress localAddress2 = (LocalAddress)socketAddress;
        if (LocalAddress.ANY.equals(localAddress2)) {
            localAddress2 = new LocalAddress(channel);
        }
        final Channel channel2 = LocalChannelRegistry.boundChannels.putIfAbsent(localAddress2, channel);
        if (channel2 != null) {
            throw new ChannelException("address already in use by: " + channel2);
        }
        return localAddress2;
    }
    
    static Channel get(final SocketAddress socketAddress) {
        return (Channel)LocalChannelRegistry.boundChannels.get(socketAddress);
    }
    
    static void unregister(final LocalAddress localAddress) {
        LocalChannelRegistry.boundChannels.remove(localAddress);
    }
    
    private LocalChannelRegistry() {
    }
    
    static {
        boundChannels = PlatformDependent.newConcurrentHashMap();
    }
}
