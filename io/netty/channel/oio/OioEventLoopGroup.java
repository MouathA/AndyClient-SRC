package io.netty.channel.oio;

import io.netty.channel.*;
import java.util.concurrent.*;

public class OioEventLoopGroup extends ThreadPerChannelEventLoopGroup
{
    public OioEventLoopGroup() {
        this(0);
    }
    
    public OioEventLoopGroup(final int n) {
        this(n, Executors.defaultThreadFactory());
    }
    
    public OioEventLoopGroup(final int n, final ThreadFactory threadFactory) {
        super(n, threadFactory, new Object[0]);
    }
}
