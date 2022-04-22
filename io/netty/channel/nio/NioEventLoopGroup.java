package io.netty.channel.nio;

import io.netty.channel.*;
import java.util.concurrent.*;
import java.nio.channels.spi.*;
import io.netty.util.concurrent.*;
import java.util.*;

public class NioEventLoopGroup extends MultithreadEventLoopGroup
{
    public NioEventLoopGroup() {
        this(0);
    }
    
    public NioEventLoopGroup(final int n) {
        this(n, null);
    }
    
    public NioEventLoopGroup(final int n, final ThreadFactory threadFactory) {
        this(n, threadFactory, SelectorProvider.provider());
    }
    
    public NioEventLoopGroup(final int n, final ThreadFactory threadFactory, final SelectorProvider selectorProvider) {
        super(n, threadFactory, new Object[] { selectorProvider });
    }
    
    public void setIoRatio(final int ioRatio) {
        final Iterator<EventExecutor> iterator = this.children().iterator();
        while (iterator.hasNext()) {
            ((NioEventLoop)iterator.next()).setIoRatio(ioRatio);
        }
    }
    
    public void rebuildSelectors() {
        final Iterator<EventExecutor> iterator = this.children().iterator();
        while (iterator.hasNext()) {
            ((NioEventLoop)iterator.next()).rebuildSelector();
        }
    }
    
    @Override
    protected EventExecutor newChild(final ThreadFactory threadFactory, final Object... array) throws Exception {
        return new NioEventLoop(this, threadFactory, (SelectorProvider)array[0]);
    }
}
