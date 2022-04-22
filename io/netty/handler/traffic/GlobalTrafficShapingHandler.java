package io.netty.handler.traffic;

import io.netty.util.concurrent.*;
import io.netty.buffer.*;
import java.util.*;
import io.netty.channel.*;
import java.util.concurrent.*;

@ChannelHandler.Sharable
public class GlobalTrafficShapingHandler extends AbstractTrafficShapingHandler
{
    private Map messagesQueues;
    
    void createGlobalTrafficCounter(final ScheduledExecutorService scheduledExecutorService) {
        if (scheduledExecutorService == null) {
            throw new NullPointerException("executor");
        }
        final TrafficCounter trafficCounter = new TrafficCounter(this, scheduledExecutorService, "GlobalTC", this.checkInterval);
        this.setTrafficCounter(trafficCounter);
        trafficCounter.start();
    }
    
    public GlobalTrafficShapingHandler(final ScheduledExecutorService scheduledExecutorService, final long n, final long n2, final long n3, final long n4) {
        super(n, n2, n3, n4);
        this.messagesQueues = new HashMap();
        this.createGlobalTrafficCounter(scheduledExecutorService);
    }
    
    public GlobalTrafficShapingHandler(final ScheduledExecutorService scheduledExecutorService, final long n, final long n2, final long n3) {
        super(n, n2, n3);
        this.messagesQueues = new HashMap();
        this.createGlobalTrafficCounter(scheduledExecutorService);
    }
    
    public GlobalTrafficShapingHandler(final ScheduledExecutorService scheduledExecutorService, final long n, final long n2) {
        super(n, n2);
        this.messagesQueues = new HashMap();
        this.createGlobalTrafficCounter(scheduledExecutorService);
    }
    
    public GlobalTrafficShapingHandler(final ScheduledExecutorService scheduledExecutorService, final long n) {
        super(n);
        this.messagesQueues = new HashMap();
        this.createGlobalTrafficCounter(scheduledExecutorService);
    }
    
    public GlobalTrafficShapingHandler(final EventExecutor eventExecutor) {
        this.messagesQueues = new HashMap();
        this.createGlobalTrafficCounter(eventExecutor);
    }
    
    public final void release() {
        if (this.trafficCounter != null) {
            this.trafficCounter.stop();
        }
    }
    
    @Override
    public void handlerAdded(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.messagesQueues.put(channelHandlerContext.channel().hashCode(), new LinkedList());
    }
    
    @Override
    public synchronized void handlerRemoved(final ChannelHandlerContext channelHandlerContext) throws Exception {
        final List<ToSend> list = this.messagesQueues.remove(channelHandlerContext.channel().hashCode());
        if (list != null) {
            for (final ToSend toSend : list) {
                if (toSend.toSend instanceof ByteBuf) {
                    ((ByteBuf)toSend.toSend).release();
                }
            }
            list.clear();
        }
    }
    
    @Override
    protected synchronized void submitWrite(final ChannelHandlerContext channelHandlerContext, final Object o, final long n, final ChannelPromise channelPromise) {
        final Integer value = channelHandlerContext.channel().hashCode();
        List<ToSend> list = this.messagesQueues.get(value);
        if (n == 0L && (list == null || list.isEmpty())) {
            channelHandlerContext.write(o, channelPromise);
            return;
        }
        final ToSend toSend = new ToSend(n, o, channelPromise, null);
        if (list == null) {
            list = new LinkedList<ToSend>();
            this.messagesQueues.put(value, list);
        }
        list.add(toSend);
        channelHandlerContext.executor().schedule((Runnable)new Runnable(channelHandlerContext, (List)list) {
            final ChannelHandlerContext val$ctx;
            final List val$mqfinal;
            final GlobalTrafficShapingHandler this$0;
            
            @Override
            public void run() {
                GlobalTrafficShapingHandler.access$100(this.this$0, this.val$ctx, this.val$mqfinal);
            }
        }, n, TimeUnit.MILLISECONDS);
    }
    
    private synchronized void sendAllValid(final ChannelHandlerContext channelHandlerContext, final List list) {
        while (!list.isEmpty()) {
            final ToSend toSend = list.remove(0);
            if (toSend.date > System.currentTimeMillis()) {
                list.add(0, toSend);
                break;
            }
            channelHandlerContext.write(toSend.toSend, toSend.promise);
        }
        channelHandlerContext.flush();
    }
    
    static void access$100(final GlobalTrafficShapingHandler globalTrafficShapingHandler, final ChannelHandlerContext channelHandlerContext, final List list) {
        globalTrafficShapingHandler.sendAllValid(channelHandlerContext, list);
    }
    
    private static final class ToSend
    {
        final long date;
        final Object toSend;
        final ChannelPromise promise;
        
        private ToSend(final long n, final Object toSend, final ChannelPromise promise) {
            this.date = System.currentTimeMillis() + n;
            this.toSend = toSend;
            this.promise = promise;
        }
        
        ToSend(final long n, final Object o, final ChannelPromise channelPromise, final GlobalTrafficShapingHandler$1 runnable) {
            this(n, o, channelPromise);
        }
    }
}
