package io.netty.util.concurrent;

import java.util.concurrent.*;

final class DefaultEventExecutor extends SingleThreadEventExecutor
{
    DefaultEventExecutor(final DefaultEventExecutorGroup defaultEventExecutorGroup, final ThreadFactory threadFactory) {
        super(defaultEventExecutorGroup, threadFactory, true);
    }
    
    @Override
    protected void run() {
        do {
            final Runnable takeTask = this.takeTask();
            if (takeTask != null) {
                takeTask.run();
                this.updateLastExecutionTime();
            }
        } while (!this.confirmShutdown());
    }
}
