package io.netty.handler.timeout;

import java.util.concurrent.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;

public class WriteTimeoutHandler extends ChannelOutboundHandlerAdapter
{
    private static final long MIN_TIMEOUT_NANOS;
    private final long timeoutNanos;
    private boolean closed;
    
    public WriteTimeoutHandler(final int n) {
        this(n, TimeUnit.SECONDS);
    }
    
    public WriteTimeoutHandler(final long n, final TimeUnit timeUnit) {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        if (n <= 0L) {
            this.timeoutNanos = 0L;
        }
        else {
            this.timeoutNanos = Math.max(timeUnit.toNanos(n), WriteTimeoutHandler.MIN_TIMEOUT_NANOS);
        }
    }
    
    @Override
    public void write(final ChannelHandlerContext channelHandlerContext, final Object o, final ChannelPromise channelPromise) throws Exception {
        this.scheduleTimeout(channelHandlerContext, channelPromise);
        channelHandlerContext.write(o, channelPromise);
    }
    
    private void scheduleTimeout(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) {
        if (this.timeoutNanos > 0L) {
            channelPromise.addListener((GenericFutureListener)new ChannelFutureListener((ScheduledFuture)channelHandlerContext.executor().schedule((Runnable)new Runnable(channelPromise, channelHandlerContext) {
                final ChannelPromise val$future;
                final ChannelHandlerContext val$ctx;
                final WriteTimeoutHandler this$0;
                
                @Override
                public void run() {
                    if (!this.val$future.isDone()) {
                        this.this$0.writeTimedOut(this.val$ctx);
                    }
                }
            }, this.timeoutNanos, TimeUnit.NANOSECONDS)) {
                final ScheduledFuture val$sf;
                final WriteTimeoutHandler this$0;
                
                public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                    this.val$sf.cancel(false);
                }
                
                @Override
                public void operationComplete(final Future future) throws Exception {
                    this.operationComplete((ChannelFuture)future);
                }
            });
        }
    }
    
    protected void writeTimedOut(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.closed) {
            channelHandlerContext.fireExceptionCaught(WriteTimeoutException.INSTANCE);
            channelHandlerContext.close();
            this.closed = true;
        }
    }
    
    static {
        MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
    }
}
