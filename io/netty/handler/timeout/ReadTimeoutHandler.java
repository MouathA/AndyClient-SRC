package io.netty.handler.timeout;

import java.util.concurrent.*;
import io.netty.channel.*;

public class ReadTimeoutHandler extends ChannelInboundHandlerAdapter
{
    private static final long MIN_TIMEOUT_NANOS;
    private final long timeoutNanos;
    private ScheduledFuture timeout;
    private long lastReadTime;
    private int state;
    private boolean closed;
    
    public ReadTimeoutHandler(final int n) {
        this(n, TimeUnit.SECONDS);
    }
    
    public ReadTimeoutHandler(final long n, final TimeUnit timeUnit) {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        if (n <= 0L) {
            this.timeoutNanos = 0L;
        }
        else {
            this.timeoutNanos = Math.max(timeUnit.toNanos(n), ReadTimeoutHandler.MIN_TIMEOUT_NANOS);
        }
    }
    
    @Override
    public void handlerAdded(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (channelHandlerContext.channel().isActive() && channelHandlerContext.channel().isRegistered()) {
            this.initialize(channelHandlerContext);
        }
    }
    
    @Override
    public void handlerRemoved(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.destroy();
    }
    
    @Override
    public void channelRegistered(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (channelHandlerContext.channel().isActive()) {
            this.initialize(channelHandlerContext);
        }
        super.channelRegistered(channelHandlerContext);
    }
    
    @Override
    public void channelActive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.initialize(channelHandlerContext);
        super.channelActive(channelHandlerContext);
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.destroy();
        super.channelInactive(channelHandlerContext);
    }
    
    @Override
    public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        this.lastReadTime = System.nanoTime();
        channelHandlerContext.fireChannelRead(o);
    }
    
    private void initialize(final ChannelHandlerContext channelHandlerContext) {
        switch (this.state) {
            case 1:
            case 2: {}
            default: {
                this.state = 1;
                this.lastReadTime = System.nanoTime();
                if (this.timeoutNanos > 0L) {
                    this.timeout = channelHandlerContext.executor().schedule((Runnable)new ReadTimeoutTask(channelHandlerContext), this.timeoutNanos, TimeUnit.NANOSECONDS);
                }
            }
        }
    }
    
    private void destroy() {
        this.state = 2;
        if (this.timeout != null) {
            this.timeout.cancel(false);
            this.timeout = null;
        }
    }
    
    protected void readTimedOut(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.closed) {
            channelHandlerContext.fireExceptionCaught(ReadTimeoutException.INSTANCE);
            channelHandlerContext.close();
            this.closed = true;
        }
    }
    
    static long access$000(final ReadTimeoutHandler readTimeoutHandler) {
        return readTimeoutHandler.timeoutNanos;
    }
    
    static long access$100(final ReadTimeoutHandler readTimeoutHandler) {
        return readTimeoutHandler.lastReadTime;
    }
    
    static ScheduledFuture access$202(final ReadTimeoutHandler readTimeoutHandler, final ScheduledFuture timeout) {
        return readTimeoutHandler.timeout = timeout;
    }
    
    static {
        MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
    }
    
    private final class ReadTimeoutTask implements Runnable
    {
        private final ChannelHandlerContext ctx;
        final ReadTimeoutHandler this$0;
        
        ReadTimeoutTask(final ReadTimeoutHandler this$0, final ChannelHandlerContext ctx) {
            this.this$0 = this$0;
            this.ctx = ctx;
        }
        
        @Override
        public void run() {
            if (!this.ctx.channel().isOpen()) {
                return;
            }
            final long n = ReadTimeoutHandler.access$000(this.this$0) - (System.nanoTime() - ReadTimeoutHandler.access$100(this.this$0));
            if (n <= 0L) {
                ReadTimeoutHandler.access$202(this.this$0, this.ctx.executor().schedule((Runnable)this, ReadTimeoutHandler.access$000(this.this$0), TimeUnit.NANOSECONDS));
                this.this$0.readTimedOut(this.ctx);
            }
            else {
                ReadTimeoutHandler.access$202(this.this$0, this.ctx.executor().schedule((Runnable)this, n, TimeUnit.NANOSECONDS));
            }
        }
    }
}
