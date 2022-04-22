package io.netty.handler.traffic;

import java.util.concurrent.*;
import io.netty.util.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import io.netty.util.internal.logging.*;

public abstract class AbstractTrafficShapingHandler extends ChannelDuplexHandler
{
    private static final InternalLogger logger;
    public static final long DEFAULT_CHECK_INTERVAL = 1000L;
    public static final long DEFAULT_MAX_TIME = 15000L;
    static final long MINIMAL_WAIT = 10L;
    protected TrafficCounter trafficCounter;
    private long writeLimit;
    private long readLimit;
    protected long maxTime;
    protected long checkInterval;
    private static final AttributeKey READ_SUSPENDED;
    private static final AttributeKey REOPEN_TASK;
    
    void setTrafficCounter(final TrafficCounter trafficCounter) {
        this.trafficCounter = trafficCounter;
    }
    
    protected AbstractTrafficShapingHandler(final long writeLimit, final long readLimit, final long checkInterval, final long maxTime) {
        this.maxTime = 15000L;
        this.checkInterval = 1000L;
        this.writeLimit = writeLimit;
        this.readLimit = readLimit;
        this.checkInterval = checkInterval;
        this.maxTime = maxTime;
    }
    
    protected AbstractTrafficShapingHandler(final long n, final long n2, final long n3) {
        this(n, n2, n3, 15000L);
    }
    
    protected AbstractTrafficShapingHandler(final long n, final long n2) {
        this(n, n2, 1000L, 15000L);
    }
    
    protected AbstractTrafficShapingHandler() {
        this(0L, 0L, 1000L, 15000L);
    }
    
    protected AbstractTrafficShapingHandler(final long n) {
        this(0L, 0L, n, 15000L);
    }
    
    public void configure(final long n, final long n2, final long n3) {
        this.configure(n, n2);
        this.configure(n3);
    }
    
    public void configure(final long writeLimit, final long readLimit) {
        this.writeLimit = writeLimit;
        this.readLimit = readLimit;
        if (this.trafficCounter != null) {
            this.trafficCounter.resetAccounting(System.currentTimeMillis() + 1L);
        }
    }
    
    public void configure(final long checkInterval) {
        this.checkInterval = checkInterval;
        if (this.trafficCounter != null) {
            this.trafficCounter.configure(this.checkInterval);
        }
    }
    
    public long getWriteLimit() {
        return this.writeLimit;
    }
    
    public void setWriteLimit(final long writeLimit) {
        this.writeLimit = writeLimit;
        if (this.trafficCounter != null) {
            this.trafficCounter.resetAccounting(System.currentTimeMillis() + 1L);
        }
    }
    
    public long getReadLimit() {
        return this.readLimit;
    }
    
    public void setReadLimit(final long readLimit) {
        this.readLimit = readLimit;
        if (this.trafficCounter != null) {
            this.trafficCounter.resetAccounting(System.currentTimeMillis() + 1L);
        }
    }
    
    public long getCheckInterval() {
        return this.checkInterval;
    }
    
    public void setCheckInterval(final long checkInterval) {
        this.checkInterval = checkInterval;
        if (this.trafficCounter != null) {
            this.trafficCounter.configure(checkInterval);
        }
    }
    
    public void setMaxTimeWait(final long maxTime) {
        this.maxTime = maxTime;
    }
    
    public long getMaxTimeWait() {
        return this.maxTime;
    }
    
    protected void doAccounting(final TrafficCounter trafficCounter) {
    }
    
    @Override
    public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        final long calculateSize = this.calculateSize(o);
        if (calculateSize > 0L && this.trafficCounter != null) {
            final long timeToWait = this.trafficCounter.readTimeToWait(calculateSize, this.readLimit, this.maxTime);
            if (timeToWait >= 10L) {
                if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
                    AbstractTrafficShapingHandler.logger.debug("Channel:" + channelHandlerContext.channel().hashCode() + " Read Suspend: " + timeToWait + ":" + channelHandlerContext.channel().config().isAutoRead() + ":" + isHandlerActive(channelHandlerContext));
                }
                if (channelHandlerContext.channel().config().isAutoRead() && channelHandlerContext != null) {
                    channelHandlerContext.channel().config().setAutoRead(false);
                    channelHandlerContext.attr(AbstractTrafficShapingHandler.READ_SUSPENDED).set(true);
                    final Attribute attr = channelHandlerContext.attr(AbstractTrafficShapingHandler.REOPEN_TASK);
                    Runnable runnable = (Runnable)attr.get();
                    if (runnable == null) {
                        runnable = new ReopenReadTimerTask(channelHandlerContext);
                        attr.set(runnable);
                    }
                    channelHandlerContext.executor().schedule(runnable, timeToWait, TimeUnit.MILLISECONDS);
                    if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
                        AbstractTrafficShapingHandler.logger.debug("Channel:" + channelHandlerContext.channel().hashCode() + " Suspend final status => " + channelHandlerContext.channel().config().isAutoRead() + ":" + isHandlerActive(channelHandlerContext) + " will reopened at: " + timeToWait);
                    }
                }
            }
        }
        channelHandlerContext.fireChannelRead(o);
    }
    
    @Override
    public void read(final ChannelHandlerContext channelHandlerContext) {
        if (channelHandlerContext != null) {
            channelHandlerContext.read();
        }
    }
    
    @Override
    public void write(final ChannelHandlerContext channelHandlerContext, final Object o, final ChannelPromise channelPromise) throws Exception {
        final long calculateSize = this.calculateSize(o);
        if (calculateSize > 0L && this.trafficCounter != null) {
            final long writeTimeToWait = this.trafficCounter.writeTimeToWait(calculateSize, this.writeLimit, this.maxTime);
            if (writeTimeToWait >= 10L) {
                if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
                    AbstractTrafficShapingHandler.logger.debug("Channel:" + channelHandlerContext.channel().hashCode() + " Write suspend: " + writeTimeToWait + ":" + channelHandlerContext.channel().config().isAutoRead() + ":" + isHandlerActive(channelHandlerContext));
                }
                this.submitWrite(channelHandlerContext, o, writeTimeToWait, channelPromise);
                return;
            }
        }
        this.submitWrite(channelHandlerContext, o, 0L, channelPromise);
    }
    
    protected abstract void submitWrite(final ChannelHandlerContext p0, final Object p1, final long p2, final ChannelPromise p3);
    
    public TrafficCounter trafficCounter() {
        return this.trafficCounter;
    }
    
    @Override
    public String toString() {
        return "TrafficShaping with Write Limit: " + this.writeLimit + " Read Limit: " + this.readLimit + " and Counter: " + ((this.trafficCounter != null) ? this.trafficCounter.toString() : "none");
    }
    
    protected long calculateSize(final Object o) {
        if (o instanceof ByteBuf) {
            return ((ByteBuf)o).readableBytes();
        }
        if (o instanceof ByteBufHolder) {
            return ((ByteBufHolder)o).content().readableBytes();
        }
        return -1L;
    }
    
    static InternalLogger access$000() {
        return AbstractTrafficShapingHandler.logger;
    }
    
    static AttributeKey access$100() {
        return AbstractTrafficShapingHandler.READ_SUSPENDED;
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(AbstractTrafficShapingHandler.class);
        READ_SUSPENDED = AttributeKey.valueOf(AbstractTrafficShapingHandler.class.getName() + ".READ_SUSPENDED");
        REOPEN_TASK = AttributeKey.valueOf(AbstractTrafficShapingHandler.class.getName() + ".REOPEN_TASK");
    }
    
    private static final class ReopenReadTimerTask implements Runnable
    {
        final ChannelHandlerContext ctx;
        
        ReopenReadTimerTask(final ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }
        
        @Override
        public void run() {
            if (!this.ctx.channel().config().isAutoRead() && AbstractTrafficShapingHandler.isHandlerActive(this.ctx)) {
                if (AbstractTrafficShapingHandler.access$000().isDebugEnabled()) {
                    AbstractTrafficShapingHandler.access$000().debug("Channel:" + this.ctx.channel().hashCode() + " Not Unsuspend: " + this.ctx.channel().config().isAutoRead() + ":" + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
                }
                this.ctx.attr(AbstractTrafficShapingHandler.access$100()).set(false);
            }
            else {
                if (AbstractTrafficShapingHandler.access$000().isDebugEnabled()) {
                    if (this.ctx.channel().config().isAutoRead() && !AbstractTrafficShapingHandler.isHandlerActive(this.ctx)) {
                        AbstractTrafficShapingHandler.access$000().debug("Channel:" + this.ctx.channel().hashCode() + " Unsuspend: " + this.ctx.channel().config().isAutoRead() + ":" + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
                    }
                    else {
                        AbstractTrafficShapingHandler.access$000().debug("Channel:" + this.ctx.channel().hashCode() + " Normal Unsuspend: " + this.ctx.channel().config().isAutoRead() + ":" + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
                    }
                }
                this.ctx.attr(AbstractTrafficShapingHandler.access$100()).set(false);
                this.ctx.channel().config().setAutoRead(true);
                this.ctx.channel().read();
            }
            if (AbstractTrafficShapingHandler.access$000().isDebugEnabled()) {
                AbstractTrafficShapingHandler.access$000().debug("Channel:" + this.ctx.channel().hashCode() + " Unsupsend final status => " + this.ctx.channel().config().isAutoRead() + ":" + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
            }
        }
    }
}
