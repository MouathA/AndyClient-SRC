package io.netty.channel.local;

import io.netty.channel.*;
import java.util.concurrent.*;
import io.netty.util.concurrent.*;

public class LocalEventLoopGroup extends MultithreadEventLoopGroup
{
    public LocalEventLoopGroup() {
        this(0);
    }
    
    public LocalEventLoopGroup(final int n) {
        this(n, null);
    }
    
    public LocalEventLoopGroup(final int n, final ThreadFactory threadFactory) {
        super(n, threadFactory, new Object[0]);
    }
    
    @Override
    protected EventExecutor newChild(final ThreadFactory threadFactory, final Object... array) throws Exception {
        return new LocalEventLoop(this, threadFactory);
    }
}
