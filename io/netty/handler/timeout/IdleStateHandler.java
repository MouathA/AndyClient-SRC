package io.netty.handler.timeout;

import java.util.concurrent.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;

public class IdleStateHandler extends ChannelDuplexHandler
{
    private static final long MIN_TIMEOUT_NANOS;
    private final long readerIdleTimeNanos;
    private final long writerIdleTimeNanos;
    private final long allIdleTimeNanos;
    ScheduledFuture readerIdleTimeout;
    long lastReadTime;
    private boolean firstReaderIdleEvent;
    ScheduledFuture writerIdleTimeout;
    long lastWriteTime;
    private boolean firstWriterIdleEvent;
    ScheduledFuture allIdleTimeout;
    private boolean firstAllIdleEvent;
    private int state;
    
    public IdleStateHandler(final int n, final int n2, final int n3) {
        this(n, n2, n3, TimeUnit.SECONDS);
    }
    
    public IdleStateHandler(final long n, final long n2, final long n3, final TimeUnit timeUnit) {
        this.firstReaderIdleEvent = true;
        this.firstWriterIdleEvent = true;
        this.firstAllIdleEvent = true;
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        if (n <= 0L) {
            this.readerIdleTimeNanos = 0L;
        }
        else {
            this.readerIdleTimeNanos = Math.max(timeUnit.toNanos(n), IdleStateHandler.MIN_TIMEOUT_NANOS);
        }
        if (n2 <= 0L) {
            this.writerIdleTimeNanos = 0L;
        }
        else {
            this.writerIdleTimeNanos = Math.max(timeUnit.toNanos(n2), IdleStateHandler.MIN_TIMEOUT_NANOS);
        }
        if (n3 <= 0L) {
            this.allIdleTimeNanos = 0L;
        }
        else {
            this.allIdleTimeNanos = Math.max(timeUnit.toNanos(n3), IdleStateHandler.MIN_TIMEOUT_NANOS);
        }
    }
    
    public long getReaderIdleTimeInMillis() {
        return TimeUnit.NANOSECONDS.toMillis(this.readerIdleTimeNanos);
    }
    
    public long getWriterIdleTimeInMillis() {
        return TimeUnit.NANOSECONDS.toMillis(this.writerIdleTimeNanos);
    }
    
    public long getAllIdleTimeInMillis() {
        return TimeUnit.NANOSECONDS.toMillis(this.allIdleTimeNanos);
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
        final boolean b = true;
        this.firstAllIdleEvent = b;
        this.firstReaderIdleEvent = b;
        channelHandlerContext.fireChannelRead(o);
    }
    
    @Override
    public void write(final ChannelHandlerContext channelHandlerContext, final Object o, final ChannelPromise channelPromise) throws Exception {
        channelPromise.addListener((GenericFutureListener)new ChannelFutureListener() {
            final IdleStateHandler this$0;
            
            public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                this.this$0.lastWriteTime = System.nanoTime();
                IdleStateHandler.access$002(this.this$0, IdleStateHandler.access$102(this.this$0, true));
            }
            
            @Override
            public void operationComplete(final Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        });
        channelHandlerContext.write(o, channelPromise);
    }
    
    private void initialize(final ChannelHandlerContext channelHandlerContext) {
        switch (this.state) {
            case 1:
            case 2: {}
            default: {
                this.state = 1;
                final EventExecutor executor = channelHandlerContext.executor();
                final long nanoTime = System.nanoTime();
                this.lastWriteTime = nanoTime;
                this.lastReadTime = nanoTime;
                if (this.readerIdleTimeNanos > 0L) {
                    this.readerIdleTimeout = executor.schedule((Runnable)new ReaderIdleTimeoutTask(channelHandlerContext), this.readerIdleTimeNanos, TimeUnit.NANOSECONDS);
                }
                if (this.writerIdleTimeNanos > 0L) {
                    this.writerIdleTimeout = executor.schedule((Runnable)new WriterIdleTimeoutTask(channelHandlerContext), this.writerIdleTimeNanos, TimeUnit.NANOSECONDS);
                }
                if (this.allIdleTimeNanos > 0L) {
                    this.allIdleTimeout = executor.schedule((Runnable)new AllIdleTimeoutTask(channelHandlerContext), this.allIdleTimeNanos, TimeUnit.NANOSECONDS);
                }
            }
        }
    }
    
    private void destroy() {
        this.state = 2;
        if (this.readerIdleTimeout != null) {
            this.readerIdleTimeout.cancel(false);
            this.readerIdleTimeout = null;
        }
        if (this.writerIdleTimeout != null) {
            this.writerIdleTimeout.cancel(false);
            this.writerIdleTimeout = null;
        }
        if (this.allIdleTimeout != null) {
            this.allIdleTimeout.cancel(false);
            this.allIdleTimeout = null;
        }
    }
    
    protected void channelIdle(final ChannelHandlerContext channelHandlerContext, final IdleStateEvent idleStateEvent) throws Exception {
        channelHandlerContext.fireUserEventTriggered(idleStateEvent);
    }
    
    static boolean access$002(final IdleStateHandler idleStateHandler, final boolean firstWriterIdleEvent) {
        return idleStateHandler.firstWriterIdleEvent = firstWriterIdleEvent;
    }
    
    static boolean access$102(final IdleStateHandler idleStateHandler, final boolean firstAllIdleEvent) {
        return idleStateHandler.firstAllIdleEvent = firstAllIdleEvent;
    }
    
    static long access$200(final IdleStateHandler idleStateHandler) {
        return idleStateHandler.readerIdleTimeNanos;
    }
    
    static boolean access$300(final IdleStateHandler idleStateHandler) {
        return idleStateHandler.firstReaderIdleEvent;
    }
    
    static boolean access$302(final IdleStateHandler idleStateHandler, final boolean firstReaderIdleEvent) {
        return idleStateHandler.firstReaderIdleEvent = firstReaderIdleEvent;
    }
    
    static long access$400(final IdleStateHandler idleStateHandler) {
        return idleStateHandler.writerIdleTimeNanos;
    }
    
    static boolean access$000(final IdleStateHandler idleStateHandler) {
        return idleStateHandler.firstWriterIdleEvent;
    }
    
    static long access$500(final IdleStateHandler idleStateHandler) {
        return idleStateHandler.allIdleTimeNanos;
    }
    
    static boolean access$100(final IdleStateHandler idleStateHandler) {
        return idleStateHandler.firstAllIdleEvent;
    }
    
    static {
        MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
    }
    
    private final class AllIdleTimeoutTask implements Runnable
    {
        private final ChannelHandlerContext ctx;
        final IdleStateHandler this$0;
        
        AllIdleTimeoutTask(final IdleStateHandler this$0, final ChannelHandlerContext ctx) {
            this.this$0 = this$0;
            this.ctx = ctx;
        }
        
        @Override
        public void run() {
            if (!this.ctx.channel().isOpen()) {
                return;
            }
            final long n = IdleStateHandler.access$500(this.this$0) - (System.nanoTime() - Math.max(this.this$0.lastReadTime, this.this$0.lastWriteTime));
            if (n <= 0L) {
                this.this$0.allIdleTimeout = this.ctx.executor().schedule((Runnable)this, IdleStateHandler.access$500(this.this$0), TimeUnit.NANOSECONDS);
                IdleStateEvent idleStateEvent;
                if (IdleStateHandler.access$100(this.this$0)) {
                    IdleStateHandler.access$102(this.this$0, false);
                    idleStateEvent = IdleStateEvent.FIRST_ALL_IDLE_STATE_EVENT;
                }
                else {
                    idleStateEvent = IdleStateEvent.ALL_IDLE_STATE_EVENT;
                }
                this.this$0.channelIdle(this.ctx, idleStateEvent);
            }
            else {
                this.this$0.allIdleTimeout = this.ctx.executor().schedule((Runnable)this, n, TimeUnit.NANOSECONDS);
            }
        }
    }
    
    private final class WriterIdleTimeoutTask implements Runnable
    {
        private final ChannelHandlerContext ctx;
        final IdleStateHandler this$0;
        
        WriterIdleTimeoutTask(final IdleStateHandler this$0, final ChannelHandlerContext ctx) {
            this.this$0 = this$0;
            this.ctx = ctx;
        }
        
        @Override
        public void run() {
            if (!this.ctx.channel().isOpen()) {
                return;
            }
            final long n = IdleStateHandler.access$400(this.this$0) - (System.nanoTime() - this.this$0.lastWriteTime);
            if (n <= 0L) {
                this.this$0.writerIdleTimeout = this.ctx.executor().schedule((Runnable)this, IdleStateHandler.access$400(this.this$0), TimeUnit.NANOSECONDS);
                IdleStateEvent idleStateEvent;
                if (IdleStateHandler.access$000(this.this$0)) {
                    IdleStateHandler.access$002(this.this$0, false);
                    idleStateEvent = IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT;
                }
                else {
                    idleStateEvent = IdleStateEvent.WRITER_IDLE_STATE_EVENT;
                }
                this.this$0.channelIdle(this.ctx, idleStateEvent);
            }
            else {
                this.this$0.writerIdleTimeout = this.ctx.executor().schedule((Runnable)this, n, TimeUnit.NANOSECONDS);
            }
        }
    }
    
    private final class ReaderIdleTimeoutTask implements Runnable
    {
        private final ChannelHandlerContext ctx;
        final IdleStateHandler this$0;
        
        ReaderIdleTimeoutTask(final IdleStateHandler this$0, final ChannelHandlerContext ctx) {
            this.this$0 = this$0;
            this.ctx = ctx;
        }
        
        @Override
        public void run() {
            if (!this.ctx.channel().isOpen()) {
                return;
            }
            final long n = IdleStateHandler.access$200(this.this$0) - (System.nanoTime() - this.this$0.lastReadTime);
            if (n <= 0L) {
                this.this$0.readerIdleTimeout = this.ctx.executor().schedule((Runnable)this, IdleStateHandler.access$200(this.this$0), TimeUnit.NANOSECONDS);
                IdleStateEvent idleStateEvent;
                if (IdleStateHandler.access$300(this.this$0)) {
                    IdleStateHandler.access$302(this.this$0, false);
                    idleStateEvent = IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT;
                }
                else {
                    idleStateEvent = IdleStateEvent.READER_IDLE_STATE_EVENT;
                }
                this.this$0.channelIdle(this.ctx, idleStateEvent);
            }
            else {
                this.this$0.readerIdleTimeout = this.ctx.executor().schedule((Runnable)this, n, TimeUnit.NANOSECONDS);
            }
        }
    }
}
