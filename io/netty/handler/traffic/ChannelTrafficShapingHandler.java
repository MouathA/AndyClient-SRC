package io.netty.handler.traffic;

import io.netty.buffer.*;
import java.util.*;
import io.netty.channel.*;
import java.util.concurrent.*;

public class ChannelTrafficShapingHandler extends AbstractTrafficShapingHandler
{
    private List messagesQueue;
    
    public ChannelTrafficShapingHandler(final long n, final long n2, final long n3, final long n4) {
        super(n, n2, n3, n4);
        this.messagesQueue = new LinkedList();
    }
    
    public ChannelTrafficShapingHandler(final long n, final long n2, final long n3) {
        super(n, n2, n3);
        this.messagesQueue = new LinkedList();
    }
    
    public ChannelTrafficShapingHandler(final long n, final long n2) {
        super(n, n2);
        this.messagesQueue = new LinkedList();
    }
    
    public ChannelTrafficShapingHandler(final long n) {
        super(n);
        this.messagesQueue = new LinkedList();
    }
    
    @Override
    public void handlerAdded(final ChannelHandlerContext channelHandlerContext) throws Exception {
        final TrafficCounter trafficCounter = new TrafficCounter(this, channelHandlerContext.executor(), "ChannelTC" + channelHandlerContext.channel().hashCode(), this.checkInterval);
        this.setTrafficCounter(trafficCounter);
        trafficCounter.start();
    }
    
    @Override
    public synchronized void handlerRemoved(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.trafficCounter != null) {
            this.trafficCounter.stop();
        }
        for (final ToSend toSend : this.messagesQueue) {
            if (toSend.toSend instanceof ByteBuf) {
                ((ByteBuf)toSend.toSend).release();
            }
        }
        this.messagesQueue.clear();
    }
    
    @Override
    protected synchronized void submitWrite(final ChannelHandlerContext channelHandlerContext, final Object o, final long n, final ChannelPromise channelPromise) {
        if (n == 0L && this.messagesQueue.isEmpty()) {
            channelHandlerContext.write(o, channelPromise);
            return;
        }
        this.messagesQueue.add(new ToSend(n, o, channelPromise, null));
        channelHandlerContext.executor().schedule((Runnable)new Runnable(channelHandlerContext) {
            final ChannelHandlerContext val$ctx;
            final ChannelTrafficShapingHandler this$0;
            
            @Override
            public void run() {
                ChannelTrafficShapingHandler.access$100(this.this$0, this.val$ctx);
            }
        }, n, TimeUnit.MILLISECONDS);
    }
    
    private synchronized void sendAllValid(final ChannelHandlerContext channelHandlerContext) {
        while (!this.messagesQueue.isEmpty()) {
            final ToSend toSend = this.messagesQueue.remove(0);
            if (toSend.date > System.currentTimeMillis()) {
                this.messagesQueue.add(0, toSend);
                break;
            }
            channelHandlerContext.write(toSend.toSend, toSend.promise);
        }
        channelHandlerContext.flush();
    }
    
    static void access$100(final ChannelTrafficShapingHandler channelTrafficShapingHandler, final ChannelHandlerContext channelHandlerContext) {
        channelTrafficShapingHandler.sendAllValid(channelHandlerContext);
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
        
        ToSend(final long n, final Object o, final ChannelPromise channelPromise, final ChannelTrafficShapingHandler$1 runnable) {
            this(n, o, channelPromise);
        }
    }
}
