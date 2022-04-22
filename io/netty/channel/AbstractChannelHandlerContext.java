package io.netty.channel;

import io.netty.util.concurrent.*;
import io.netty.buffer.*;
import java.net.*;
import io.netty.util.*;
import io.netty.util.internal.*;

abstract class AbstractChannelHandlerContext extends DefaultAttributeMap implements ChannelHandlerContext
{
    AbstractChannelHandlerContext next;
    AbstractChannelHandlerContext prev;
    private final boolean inbound;
    private final boolean outbound;
    private final AbstractChannel channel;
    private final DefaultChannelPipeline pipeline;
    private final String name;
    private boolean removed;
    final EventExecutor executor;
    private ChannelFuture succeededFuture;
    private Runnable invokeChannelReadCompleteTask;
    private Runnable invokeReadTask;
    private Runnable invokeChannelWritableStateChangedTask;
    private Runnable invokeFlushTask;
    
    AbstractChannelHandlerContext(final DefaultChannelPipeline pipeline, final EventExecutorGroup eventExecutorGroup, final String name, final boolean inbound, final boolean outbound) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        this.channel = pipeline.channel;
        this.pipeline = pipeline;
        this.name = name;
        if (eventExecutorGroup != null) {
            EventExecutor next = pipeline.childExecutors.get(eventExecutorGroup);
            if (next == null) {
                next = eventExecutorGroup.next();
                pipeline.childExecutors.put(eventExecutorGroup, next);
            }
            this.executor = next;
        }
        else {
            this.executor = null;
        }
        this.inbound = inbound;
        this.outbound = outbound;
    }
    
    void teardown() {
        final EventExecutor executor = this.executor();
        if (executor.inEventLoop()) {
            this.teardown0();
        }
        else {
            executor.execute(new Runnable() {
                final AbstractChannelHandlerContext this$0;
                
                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$000(this.this$0);
                }
            });
        }
    }
    
    private void teardown0() {
        final AbstractChannelHandlerContext prev = this.prev;
        if (prev != null) {
            // monitorenter(pipeline = this.pipeline)
            this.pipeline.remove0(this);
            // monitorexit(pipeline)
            prev.teardown();
        }
    }
    
    @Override
    public Channel channel() {
        return this.channel;
    }
    
    @Override
    public ChannelPipeline pipeline() {
        return this.pipeline;
    }
    
    @Override
    public ByteBufAllocator alloc() {
        return this.channel().config().getAllocator();
    }
    
    @Override
    public EventExecutor executor() {
        if (this.executor == null) {
            return this.channel().eventLoop();
        }
        return this.executor;
    }
    
    @Override
    public String name() {
        return this.name;
    }
    
    @Override
    public ChannelHandlerContext fireChannelRegistered() {
        final AbstractChannelHandlerContext contextInbound = this.findContextInbound();
        final EventExecutor executor = contextInbound.executor();
        if (executor.inEventLoop()) {
            contextInbound.invokeChannelRegistered();
        }
        else {
            executor.execute(new OneTimeTask(contextInbound) {
                final AbstractChannelHandlerContext val$next;
                final AbstractChannelHandlerContext this$0;
                
                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$100(this.val$next);
                }
            });
        }
        return this;
    }
    
    private void invokeChannelRegistered() {
        ((ChannelInboundHandler)this.handler()).channelRegistered(this);
    }
    
    @Override
    public ChannelHandlerContext fireChannelUnregistered() {
        final AbstractChannelHandlerContext contextInbound = this.findContextInbound();
        final EventExecutor executor = contextInbound.executor();
        if (executor.inEventLoop()) {
            contextInbound.invokeChannelUnregistered();
        }
        else {
            executor.execute(new OneTimeTask(contextInbound) {
                final AbstractChannelHandlerContext val$next;
                final AbstractChannelHandlerContext this$0;
                
                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$200(this.val$next);
                }
            });
        }
        return this;
    }
    
    private void invokeChannelUnregistered() {
        ((ChannelInboundHandler)this.handler()).channelUnregistered(this);
    }
    
    @Override
    public ChannelHandlerContext fireChannelActive() {
        final AbstractChannelHandlerContext contextInbound = this.findContextInbound();
        final EventExecutor executor = contextInbound.executor();
        if (executor.inEventLoop()) {
            contextInbound.invokeChannelActive();
        }
        else {
            executor.execute(new OneTimeTask(contextInbound) {
                final AbstractChannelHandlerContext val$next;
                final AbstractChannelHandlerContext this$0;
                
                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$300(this.val$next);
                }
            });
        }
        return this;
    }
    
    private void invokeChannelActive() {
        ((ChannelInboundHandler)this.handler()).channelActive(this);
    }
    
    @Override
    public ChannelHandlerContext fireChannelInactive() {
        final AbstractChannelHandlerContext contextInbound = this.findContextInbound();
        final EventExecutor executor = contextInbound.executor();
        if (executor.inEventLoop()) {
            contextInbound.invokeChannelInactive();
        }
        else {
            executor.execute(new OneTimeTask(contextInbound) {
                final AbstractChannelHandlerContext val$next;
                final AbstractChannelHandlerContext this$0;
                
                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$400(this.val$next);
                }
            });
        }
        return this;
    }
    
    private void invokeChannelInactive() {
        ((ChannelInboundHandler)this.handler()).channelInactive(this);
    }
    
    @Override
    public ChannelHandlerContext fireExceptionCaught(final Throwable t) {
        if (t == null) {
            throw new NullPointerException("cause");
        }
        final AbstractChannelHandlerContext next = this.next;
        final EventExecutor executor = next.executor();
        if (executor.inEventLoop()) {
            next.invokeExceptionCaught(t);
        }
        else {
            executor.execute(new OneTimeTask(next, t) {
                final AbstractChannelHandlerContext val$next;
                final Throwable val$cause;
                final AbstractChannelHandlerContext this$0;
                
                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$500(this.val$next, this.val$cause);
                }
            });
        }
        return this;
    }
    
    private void invokeExceptionCaught(final Throwable t) {
        this.handler().exceptionCaught(this, t);
    }
    
    @Override
    public ChannelHandlerContext fireUserEventTriggered(final Object o) {
        if (o == null) {
            throw new NullPointerException("event");
        }
        final AbstractChannelHandlerContext contextInbound = this.findContextInbound();
        final EventExecutor executor = contextInbound.executor();
        if (executor.inEventLoop()) {
            contextInbound.invokeUserEventTriggered(o);
        }
        else {
            executor.execute(new OneTimeTask(contextInbound, o) {
                final AbstractChannelHandlerContext val$next;
                final Object val$event;
                final AbstractChannelHandlerContext this$0;
                
                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$600(this.val$next, this.val$event);
                }
            });
        }
        return this;
    }
    
    private void invokeUserEventTriggered(final Object o) {
        ((ChannelInboundHandler)this.handler()).userEventTriggered(this, o);
    }
    
    @Override
    public ChannelHandlerContext fireChannelRead(final Object o) {
        if (o == null) {
            throw new NullPointerException("msg");
        }
        final AbstractChannelHandlerContext contextInbound = this.findContextInbound();
        final EventExecutor executor = contextInbound.executor();
        if (executor.inEventLoop()) {
            contextInbound.invokeChannelRead(o);
        }
        else {
            executor.execute(new OneTimeTask(contextInbound, o) {
                final AbstractChannelHandlerContext val$next;
                final Object val$msg;
                final AbstractChannelHandlerContext this$0;
                
                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$700(this.val$next, this.val$msg);
                }
            });
        }
        return this;
    }
    
    private void invokeChannelRead(final Object o) {
        ((ChannelInboundHandler)this.handler()).channelRead(this, o);
    }
    
    @Override
    public ChannelHandlerContext fireChannelReadComplete() {
        final AbstractChannelHandlerContext contextInbound = this.findContextInbound();
        final EventExecutor executor = contextInbound.executor();
        if (executor.inEventLoop()) {
            contextInbound.invokeChannelReadComplete();
        }
        else {
            Runnable invokeChannelReadCompleteTask = contextInbound.invokeChannelReadCompleteTask;
            if (invokeChannelReadCompleteTask == null) {
                invokeChannelReadCompleteTask = (contextInbound.invokeChannelReadCompleteTask = new Runnable(contextInbound) {
                    final AbstractChannelHandlerContext val$next;
                    final AbstractChannelHandlerContext this$0;
                    
                    @Override
                    public void run() {
                        AbstractChannelHandlerContext.access$800(this.val$next);
                    }
                });
            }
            executor.execute(invokeChannelReadCompleteTask);
        }
        return this;
    }
    
    private void invokeChannelReadComplete() {
        ((ChannelInboundHandler)this.handler()).channelReadComplete(this);
    }
    
    @Override
    public ChannelHandlerContext fireChannelWritabilityChanged() {
        final AbstractChannelHandlerContext contextInbound = this.findContextInbound();
        final EventExecutor executor = contextInbound.executor();
        if (executor.inEventLoop()) {
            contextInbound.invokeChannelWritabilityChanged();
        }
        else {
            Runnable invokeChannelWritableStateChangedTask = contextInbound.invokeChannelWritableStateChangedTask;
            if (invokeChannelWritableStateChangedTask == null) {
                invokeChannelWritableStateChangedTask = (contextInbound.invokeChannelWritableStateChangedTask = new Runnable(contextInbound) {
                    final AbstractChannelHandlerContext val$next;
                    final AbstractChannelHandlerContext this$0;
                    
                    @Override
                    public void run() {
                        AbstractChannelHandlerContext.access$900(this.val$next);
                    }
                });
            }
            executor.execute(invokeChannelWritableStateChangedTask);
        }
        return this;
    }
    
    private void invokeChannelWritabilityChanged() {
        ((ChannelInboundHandler)this.handler()).channelWritabilityChanged(this);
    }
    
    @Override
    public ChannelFuture bind(final SocketAddress socketAddress) {
        return this.bind(socketAddress, this.newPromise());
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress socketAddress) {
        return this.connect(socketAddress, this.newPromise());
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress socketAddress, final SocketAddress socketAddress2) {
        return this.connect(socketAddress, socketAddress2, this.newPromise());
    }
    
    @Override
    public ChannelFuture disconnect() {
        return this.disconnect(this.newPromise());
    }
    
    @Override
    public ChannelFuture close() {
        return this.close(this.newPromise());
    }
    
    @Override
    public ChannelFuture deregister() {
        return this.deregister(this.newPromise());
    }
    
    @Override
    public ChannelFuture bind(final SocketAddress socketAddress, final ChannelPromise channelPromise) {
        if (socketAddress == null) {
            throw new NullPointerException("localAddress");
        }
        if (!null) {
            return channelPromise;
        }
        final AbstractChannelHandlerContext contextOutbound = this.findContextOutbound();
        final EventExecutor executor = contextOutbound.executor();
        if (executor.inEventLoop()) {
            contextOutbound.invokeBind(socketAddress, channelPromise);
        }
        else {
            safeExecute(executor, new OneTimeTask(contextOutbound, socketAddress, channelPromise) {
                final AbstractChannelHandlerContext val$next;
                final SocketAddress val$localAddress;
                final ChannelPromise val$promise;
                final AbstractChannelHandlerContext this$0;
                
                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$1000(this.val$next, this.val$localAddress, this.val$promise);
                }
            }, channelPromise, null);
        }
        return channelPromise;
    }
    
    private void invokeBind(final SocketAddress socketAddress, final ChannelPromise channelPromise) {
        ((ChannelOutboundHandler)this.handler()).bind(this, socketAddress, channelPromise);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress socketAddress, final ChannelPromise channelPromise) {
        return this.connect(socketAddress, null, channelPromise);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
        if (socketAddress == null) {
            throw new NullPointerException("remoteAddress");
        }
        if (!null) {
            return channelPromise;
        }
        final AbstractChannelHandlerContext contextOutbound = this.findContextOutbound();
        final EventExecutor executor = contextOutbound.executor();
        if (executor.inEventLoop()) {
            contextOutbound.invokeConnect(socketAddress, socketAddress2, channelPromise);
        }
        else {
            safeExecute(executor, new OneTimeTask(contextOutbound, socketAddress, socketAddress2, channelPromise) {
                final AbstractChannelHandlerContext val$next;
                final SocketAddress val$remoteAddress;
                final SocketAddress val$localAddress;
                final ChannelPromise val$promise;
                final AbstractChannelHandlerContext this$0;
                
                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$1100(this.val$next, this.val$remoteAddress, this.val$localAddress, this.val$promise);
                }
            }, channelPromise, null);
        }
        return channelPromise;
    }
    
    private void invokeConnect(final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
        ((ChannelOutboundHandler)this.handler()).connect(this, socketAddress, socketAddress2, channelPromise);
    }
    
    @Override
    public ChannelFuture disconnect(final ChannelPromise channelPromise) {
        if (!null) {
            return channelPromise;
        }
        final AbstractChannelHandlerContext contextOutbound = this.findContextOutbound();
        final EventExecutor executor = contextOutbound.executor();
        if (executor.inEventLoop()) {
            if (!this.channel().metadata().hasDisconnect()) {
                contextOutbound.invokeClose(channelPromise);
            }
            else {
                contextOutbound.invokeDisconnect(channelPromise);
            }
        }
        else {
            safeExecute(executor, new OneTimeTask(contextOutbound, channelPromise) {
                final AbstractChannelHandlerContext val$next;
                final ChannelPromise val$promise;
                final AbstractChannelHandlerContext this$0;
                
                @Override
                public void run() {
                    if (!this.this$0.channel().metadata().hasDisconnect()) {
                        AbstractChannelHandlerContext.access$1200(this.val$next, this.val$promise);
                    }
                    else {
                        AbstractChannelHandlerContext.access$1300(this.val$next, this.val$promise);
                    }
                }
            }, channelPromise, null);
        }
        return channelPromise;
    }
    
    private void invokeDisconnect(final ChannelPromise channelPromise) {
        ((ChannelOutboundHandler)this.handler()).disconnect(this, channelPromise);
    }
    
    @Override
    public ChannelFuture close(final ChannelPromise channelPromise) {
        if (!null) {
            return channelPromise;
        }
        final AbstractChannelHandlerContext contextOutbound = this.findContextOutbound();
        final EventExecutor executor = contextOutbound.executor();
        if (executor.inEventLoop()) {
            contextOutbound.invokeClose(channelPromise);
        }
        else {
            safeExecute(executor, new OneTimeTask(contextOutbound, channelPromise) {
                final AbstractChannelHandlerContext val$next;
                final ChannelPromise val$promise;
                final AbstractChannelHandlerContext this$0;
                
                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$1200(this.val$next, this.val$promise);
                }
            }, channelPromise, null);
        }
        return channelPromise;
    }
    
    private void invokeClose(final ChannelPromise channelPromise) {
        ((ChannelOutboundHandler)this.handler()).close(this, channelPromise);
    }
    
    @Override
    public ChannelFuture deregister(final ChannelPromise channelPromise) {
        if (!null) {
            return channelPromise;
        }
        final AbstractChannelHandlerContext contextOutbound = this.findContextOutbound();
        final EventExecutor executor = contextOutbound.executor();
        if (executor.inEventLoop()) {
            contextOutbound.invokeDeregister(channelPromise);
        }
        else {
            safeExecute(executor, new OneTimeTask(contextOutbound, channelPromise) {
                final AbstractChannelHandlerContext val$next;
                final ChannelPromise val$promise;
                final AbstractChannelHandlerContext this$0;
                
                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$1400(this.val$next, this.val$promise);
                }
            }, channelPromise, null);
        }
        return channelPromise;
    }
    
    private void invokeDeregister(final ChannelPromise channelPromise) {
        ((ChannelOutboundHandler)this.handler()).deregister(this, channelPromise);
    }
    
    @Override
    public ChannelHandlerContext read() {
        final AbstractChannelHandlerContext contextOutbound = this.findContextOutbound();
        final EventExecutor executor = contextOutbound.executor();
        if (executor.inEventLoop()) {
            contextOutbound.invokeRead();
        }
        else {
            Runnable invokeReadTask = contextOutbound.invokeReadTask;
            if (invokeReadTask == null) {
                invokeReadTask = (contextOutbound.invokeReadTask = new Runnable(contextOutbound) {
                    final AbstractChannelHandlerContext val$next;
                    final AbstractChannelHandlerContext this$0;
                    
                    @Override
                    public void run() {
                        AbstractChannelHandlerContext.access$1500(this.val$next);
                    }
                });
            }
            executor.execute(invokeReadTask);
        }
        return this;
    }
    
    private void invokeRead() {
        ((ChannelOutboundHandler)this.handler()).read(this);
    }
    
    @Override
    public ChannelFuture write(final Object o) {
        return this.write(o, this.newPromise());
    }
    
    @Override
    public ChannelFuture write(final Object o, final ChannelPromise channelPromise) {
        if (o == null) {
            throw new NullPointerException("msg");
        }
        if (null) {
            ReferenceCountUtil.release(o);
            return channelPromise;
        }
        this.write(o, false, channelPromise);
        return channelPromise;
    }
    
    private void invokeWrite(final Object o, final ChannelPromise channelPromise) {
        ((ChannelOutboundHandler)this.handler()).write(this, o, channelPromise);
    }
    
    @Override
    public ChannelHandlerContext flush() {
        final AbstractChannelHandlerContext contextOutbound = this.findContextOutbound();
        final EventExecutor executor = contextOutbound.executor();
        if (executor.inEventLoop()) {
            contextOutbound.invokeFlush();
        }
        else {
            Runnable invokeFlushTask = contextOutbound.invokeFlushTask;
            if (invokeFlushTask == null) {
                invokeFlushTask = (contextOutbound.invokeFlushTask = new Runnable(contextOutbound) {
                    final AbstractChannelHandlerContext val$next;
                    final AbstractChannelHandlerContext this$0;
                    
                    @Override
                    public void run() {
                        AbstractChannelHandlerContext.access$1600(this.val$next);
                    }
                });
            }
            safeExecute(executor, invokeFlushTask, this.channel.voidPromise(), null);
        }
        return this;
    }
    
    private void invokeFlush() {
        ((ChannelOutboundHandler)this.handler()).flush(this);
    }
    
    @Override
    public ChannelFuture writeAndFlush(final Object o, final ChannelPromise channelPromise) {
        if (o == null) {
            throw new NullPointerException("msg");
        }
        if (null) {
            ReferenceCountUtil.release(o);
            return channelPromise;
        }
        this.write(o, true, channelPromise);
        return channelPromise;
    }
    
    private void write(final Object o, final boolean b, final ChannelPromise channelPromise) {
        final AbstractChannelHandlerContext contextOutbound = this.findContextOutbound();
        final EventExecutor executor = contextOutbound.executor();
        if (executor.inEventLoop()) {
            contextOutbound.invokeWrite(o, channelPromise);
            if (b) {
                contextOutbound.invokeFlush();
            }
        }
        else {
            final int size = this.channel.estimatorHandle().size(o);
            if (size > 0) {
                final ChannelOutboundBuffer outboundBuffer = this.channel.unsafe().outboundBuffer();
                if (outboundBuffer != null) {
                    outboundBuffer.incrementPendingOutboundBytes(size);
                }
            }
            AbstractWriteTask abstractWriteTask;
            if (b) {
                abstractWriteTask = WriteAndFlushTask.access$1700(contextOutbound, o, size, channelPromise);
            }
            else {
                abstractWriteTask = WriteTask.access$1800(contextOutbound, o, size, channelPromise);
            }
            safeExecute(executor, abstractWriteTask, channelPromise, o);
        }
    }
    
    @Override
    public ChannelFuture writeAndFlush(final Object o) {
        return this.writeAndFlush(o, this.newPromise());
    }
    
    private static void notifyOutboundHandlerException(final Throwable t, final ChannelPromise channelPromise) {
        if (channelPromise instanceof VoidChannelPromise) {
            return;
        }
        if (!channelPromise.tryFailure(t) && DefaultChannelPipeline.logger.isWarnEnabled()) {
            DefaultChannelPipeline.logger.warn("Failed to fail the promise because it's done already: {}", channelPromise, t);
        }
    }
    
    private void notifyHandlerException(final Throwable t) {
        if (t != null) {
            if (DefaultChannelPipeline.logger.isWarnEnabled()) {
                DefaultChannelPipeline.logger.warn("An exception was thrown by a user handler while handling an exceptionCaught event", t);
            }
            return;
        }
        this.invokeExceptionCaught(t);
    }
    
    @Override
    public ChannelPromise newPromise() {
        return new DefaultChannelPromise(this.channel(), this.executor());
    }
    
    @Override
    public ChannelProgressivePromise newProgressivePromise() {
        return new DefaultChannelProgressivePromise(this.channel(), this.executor());
    }
    
    @Override
    public ChannelFuture newSucceededFuture() {
        ChannelFuture succeededFuture = this.succeededFuture;
        if (succeededFuture == null) {
            succeededFuture = (this.succeededFuture = new SucceededChannelFuture(this.channel(), this.executor()));
        }
        return succeededFuture;
    }
    
    @Override
    public ChannelFuture newFailedFuture(final Throwable t) {
        return new FailedChannelFuture(this.channel(), this.executor(), t);
    }
    
    private AbstractChannelHandlerContext findContextInbound() {
        AbstractChannelHandlerContext next = this;
        do {
            next = next.next;
        } while (!next.inbound);
        return next;
    }
    
    private AbstractChannelHandlerContext findContextOutbound() {
        AbstractChannelHandlerContext prev = this;
        do {
            prev = prev.prev;
        } while (!prev.outbound);
        return prev;
    }
    
    @Override
    public ChannelPromise voidPromise() {
        return this.channel.voidPromise();
    }
    
    void setRemoved() {
        this.removed = true;
    }
    
    @Override
    public boolean isRemoved() {
        return this.removed;
    }
    
    private static void safeExecute(final EventExecutor eventExecutor, final Runnable runnable, final ChannelPromise channelPromise, final Object o) {
        eventExecutor.execute(runnable);
    }
    
    static void access$000(final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        abstractChannelHandlerContext.teardown0();
    }
    
    static void access$100(final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        abstractChannelHandlerContext.invokeChannelRegistered();
    }
    
    static void access$200(final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        abstractChannelHandlerContext.invokeChannelUnregistered();
    }
    
    static void access$300(final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        abstractChannelHandlerContext.invokeChannelActive();
    }
    
    static void access$400(final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        abstractChannelHandlerContext.invokeChannelInactive();
    }
    
    static void access$500(final AbstractChannelHandlerContext abstractChannelHandlerContext, final Throwable t) {
        abstractChannelHandlerContext.invokeExceptionCaught(t);
    }
    
    static void access$600(final AbstractChannelHandlerContext abstractChannelHandlerContext, final Object o) {
        abstractChannelHandlerContext.invokeUserEventTriggered(o);
    }
    
    static void access$700(final AbstractChannelHandlerContext abstractChannelHandlerContext, final Object o) {
        abstractChannelHandlerContext.invokeChannelRead(o);
    }
    
    static void access$800(final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        abstractChannelHandlerContext.invokeChannelReadComplete();
    }
    
    static void access$900(final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        abstractChannelHandlerContext.invokeChannelWritabilityChanged();
    }
    
    static void access$1000(final AbstractChannelHandlerContext abstractChannelHandlerContext, final SocketAddress socketAddress, final ChannelPromise channelPromise) {
        abstractChannelHandlerContext.invokeBind(socketAddress, channelPromise);
    }
    
    static void access$1100(final AbstractChannelHandlerContext abstractChannelHandlerContext, final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
        abstractChannelHandlerContext.invokeConnect(socketAddress, socketAddress2, channelPromise);
    }
    
    static void access$1200(final AbstractChannelHandlerContext abstractChannelHandlerContext, final ChannelPromise channelPromise) {
        abstractChannelHandlerContext.invokeClose(channelPromise);
    }
    
    static void access$1300(final AbstractChannelHandlerContext abstractChannelHandlerContext, final ChannelPromise channelPromise) {
        abstractChannelHandlerContext.invokeDisconnect(channelPromise);
    }
    
    static void access$1400(final AbstractChannelHandlerContext abstractChannelHandlerContext, final ChannelPromise channelPromise) {
        abstractChannelHandlerContext.invokeDeregister(channelPromise);
    }
    
    static void access$1500(final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        abstractChannelHandlerContext.invokeRead();
    }
    
    static void access$1600(final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        abstractChannelHandlerContext.invokeFlush();
    }
    
    static AbstractChannel access$1900(final AbstractChannelHandlerContext abstractChannelHandlerContext) {
        return abstractChannelHandlerContext.channel;
    }
    
    static void access$2000(final AbstractChannelHandlerContext abstractChannelHandlerContext, final Object o, final ChannelPromise channelPromise) {
        abstractChannelHandlerContext.invokeWrite(o, channelPromise);
    }
    
    static final class WriteAndFlushTask extends AbstractWriteTask
    {
        private static final Recycler RECYCLER;
        
        private static WriteAndFlushTask newInstance(final AbstractChannelHandlerContext abstractChannelHandlerContext, final Object o, final int n, final ChannelPromise channelPromise) {
            final WriteAndFlushTask writeAndFlushTask = (WriteAndFlushTask)WriteAndFlushTask.RECYCLER.get();
            AbstractWriteTask.init(writeAndFlushTask, abstractChannelHandlerContext, o, n, channelPromise);
            return writeAndFlushTask;
        }
        
        private WriteAndFlushTask(final Recycler.Handle handle) {
            super(handle, null);
        }
        
        public void write(final AbstractChannelHandlerContext abstractChannelHandlerContext, final Object o, final ChannelPromise channelPromise) {
            super.write(abstractChannelHandlerContext, o, channelPromise);
            AbstractChannelHandlerContext.access$1600(abstractChannelHandlerContext);
        }
        
        @Override
        protected void recycle(final Recycler.Handle handle) {
            WriteAndFlushTask.RECYCLER.recycle(this, handle);
        }
        
        static WriteAndFlushTask access$1700(final AbstractChannelHandlerContext abstractChannelHandlerContext, final Object o, final int n, final ChannelPromise channelPromise) {
            return newInstance(abstractChannelHandlerContext, o, n, channelPromise);
        }
        
        WriteAndFlushTask(final Recycler.Handle handle, final AbstractChannelHandlerContext$1 runnable) {
            this(handle);
        }
        
        static {
            RECYCLER = new Recycler() {
                @Override
                protected WriteAndFlushTask newObject(final Handle handle) {
                    return new WriteAndFlushTask(handle, null);
                }
                
                @Override
                protected Object newObject(final Handle handle) {
                    return this.newObject(handle);
                }
            };
        }
    }
    
    abstract static class AbstractWriteTask extends RecyclableMpscLinkedQueueNode implements Runnable
    {
        private AbstractChannelHandlerContext ctx;
        private Object msg;
        private ChannelPromise promise;
        private int size;
        
        private AbstractWriteTask(final Recycler.Handle handle) {
            super(handle);
        }
        
        protected static void init(final AbstractWriteTask abstractWriteTask, final AbstractChannelHandlerContext ctx, final Object msg, final int size, final ChannelPromise promise) {
            abstractWriteTask.ctx = ctx;
            abstractWriteTask.msg = msg;
            abstractWriteTask.promise = promise;
            abstractWriteTask.size = size;
        }
        
        @Override
        public final void run() {
            if (this.size > 0) {
                final ChannelOutboundBuffer outboundBuffer = AbstractChannelHandlerContext.access$1900(this.ctx).unsafe().outboundBuffer();
                if (outboundBuffer != null) {
                    outboundBuffer.decrementPendingOutboundBytes(this.size);
                }
            }
            this.write(this.ctx, this.msg, this.promise);
            this.ctx = null;
            this.msg = null;
            this.promise = null;
        }
        
        @Override
        public Runnable value() {
            return this;
        }
        
        protected void write(final AbstractChannelHandlerContext abstractChannelHandlerContext, final Object o, final ChannelPromise channelPromise) {
            AbstractChannelHandlerContext.access$2000(abstractChannelHandlerContext, o, channelPromise);
        }
        
        @Override
        public Object value() {
            return this.value();
        }
        
        AbstractWriteTask(final Recycler.Handle handle, final AbstractChannelHandlerContext$1 runnable) {
            this(handle);
        }
    }
    
    static final class WriteTask extends AbstractWriteTask implements SingleThreadEventLoop.NonWakeupRunnable
    {
        private static final Recycler RECYCLER;
        
        private static WriteTask newInstance(final AbstractChannelHandlerContext abstractChannelHandlerContext, final Object o, final int n, final ChannelPromise channelPromise) {
            final WriteTask writeTask = (WriteTask)WriteTask.RECYCLER.get();
            AbstractWriteTask.init(writeTask, abstractChannelHandlerContext, o, n, channelPromise);
            return writeTask;
        }
        
        private WriteTask(final Recycler.Handle handle) {
            super(handle, null);
        }
        
        @Override
        protected void recycle(final Recycler.Handle handle) {
            WriteTask.RECYCLER.recycle(this, handle);
        }
        
        static WriteTask access$1800(final AbstractChannelHandlerContext abstractChannelHandlerContext, final Object o, final int n, final ChannelPromise channelPromise) {
            return newInstance(abstractChannelHandlerContext, o, n, channelPromise);
        }
        
        WriteTask(final Recycler.Handle handle, final AbstractChannelHandlerContext$1 runnable) {
            this(handle);
        }
        
        static {
            RECYCLER = new Recycler() {
                @Override
                protected WriteTask newObject(final Handle handle) {
                    return new WriteTask(handle, null);
                }
                
                @Override
                protected Object newObject(final Handle handle) {
                    return this.newObject(handle);
                }
            };
        }
    }
}
