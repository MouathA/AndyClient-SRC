package io.netty.channel;

import io.netty.util.concurrent.*;

public class ThreadPerChannelEventLoop extends SingleThreadEventLoop
{
    private final ThreadPerChannelEventLoopGroup parent;
    private Channel ch;
    
    public ThreadPerChannelEventLoop(final ThreadPerChannelEventLoopGroup parent) {
        super(parent, parent.threadFactory, true);
        this.parent = parent;
    }
    
    @Override
    public ChannelFuture register(final Channel channel, final ChannelPromise channelPromise) {
        return super.register(channel, channelPromise).addListener((GenericFutureListener)new ChannelFutureListener() {
            final ThreadPerChannelEventLoop this$0;
            
            public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    ThreadPerChannelEventLoop.access$002(this.this$0, channelFuture.channel());
                }
                else {
                    this.this$0.deregister();
                }
            }
            
            @Override
            public void operationComplete(final Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        });
    }
    
    @Override
    protected void run() {
        while (true) {
            final Runnable takeTask = this.takeTask();
            if (takeTask != null) {
                takeTask.run();
                this.updateLastExecutionTime();
            }
            final Channel ch = this.ch;
            if (this.isShuttingDown()) {
                if (ch != null) {
                    ch.unsafe().close(ch.unsafe().voidPromise());
                }
                if (this.confirmShutdown()) {
                    break;
                }
                continue;
            }
            else {
                if (ch == null || ch.isRegistered()) {
                    continue;
                }
                this.runAllTasks();
                this.deregister();
            }
        }
    }
    
    protected void deregister() {
        this.ch = null;
        this.parent.activeChildren.remove(this);
        this.parent.idleChildren.add(this);
    }
    
    static Channel access$002(final ThreadPerChannelEventLoop threadPerChannelEventLoop, final Channel ch) {
        return threadPerChannelEventLoop.ch = ch;
    }
}
