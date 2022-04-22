package io.netty.util.concurrent;

import java.util.concurrent.*;

public class DefaultEventExecutorGroup extends MultithreadEventExecutorGroup
{
    public DefaultEventExecutorGroup(final int n) {
        this(n, null);
    }
    
    public DefaultEventExecutorGroup(final int n, final ThreadFactory threadFactory) {
        super(n, threadFactory, new Object[0]);
    }
    
    @Override
    protected EventExecutor newChild(final ThreadFactory threadFactory, final Object... array) throws Exception {
        return new DefaultEventExecutor(this, threadFactory);
    }
}
