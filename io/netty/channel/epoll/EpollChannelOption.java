package io.netty.channel.epoll;

import io.netty.channel.*;

public final class EpollChannelOption extends ChannelOption
{
    public static final ChannelOption TCP_CORK;
    public static final ChannelOption TCP_KEEPIDLE;
    public static final ChannelOption TCP_KEEPINTVL;
    public static final ChannelOption TCP_KEEPCNT;
    public static final ChannelOption SO_REUSEPORT;
    
    private EpollChannelOption(final String s) {
        super(s);
    }
    
    static {
        TCP_CORK = ChannelOption.valueOf("TCP_CORK");
        TCP_KEEPIDLE = ChannelOption.valueOf("TCP_KEEPIDLE");
        TCP_KEEPINTVL = ChannelOption.valueOf("TCP_KEEPINTVL");
        TCP_KEEPCNT = ChannelOption.valueOf("TCP_KEEPCNT");
        SO_REUSEPORT = ChannelOption.valueOf("SO_REUSEPORT");
    }
}
