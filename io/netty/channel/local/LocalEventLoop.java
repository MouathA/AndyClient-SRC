package io.netty.channel.local;

import java.util.concurrent.*;
import io.netty.channel.*;

final class LocalEventLoop extends SingleThreadEventLoop
{
    LocalEventLoop(final LocalEventLoopGroup localEventLoopGroup, final ThreadFactory threadFactory) {
        super(localEventLoopGroup, threadFactory, true);
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
