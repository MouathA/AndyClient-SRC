package io.netty.channel.epoll;

import java.util.concurrent.*;
import io.netty.util.concurrent.*;
import java.util.*;
import io.netty.channel.*;

public final class EpollEventLoopGroup extends MultithreadEventLoopGroup
{
    public EpollEventLoopGroup() {
        this(0);
    }
    
    public EpollEventLoopGroup(final int n) {
        this(n, null);
    }
    
    public EpollEventLoopGroup(final int n, final ThreadFactory threadFactory) {
        this(n, threadFactory, 128);
    }
    
    public EpollEventLoopGroup(final int n, final ThreadFactory threadFactory, final int n2) {
        super(n, threadFactory, new Object[] { n2 });
    }
    
    public void setIoRatio(final int ioRatio) {
        final Iterator<EventExecutor> iterator = this.children().iterator();
        while (iterator.hasNext()) {
            ((EpollEventLoop)iterator.next()).setIoRatio(ioRatio);
        }
    }
    
    @Override
    protected EventExecutor newChild(final ThreadFactory threadFactory, final Object... array) throws Exception {
        return new EpollEventLoop(this, threadFactory, (int)array[0]);
    }
}
