package io.netty.handler.stream;

import java.util.*;
import java.nio.channels.*;
import io.netty.util.concurrent.*;
import io.netty.buffer.*;
import io.netty.util.internal.logging.*;
import io.netty.util.*;
import io.netty.channel.*;

public class ChunkedWriteHandler extends ChannelDuplexHandler
{
    private static final InternalLogger logger;
    private final Queue queue;
    private ChannelHandlerContext ctx;
    private PendingWrite currentWrite;
    
    public ChunkedWriteHandler() {
        this.queue = new ArrayDeque();
    }
    
    @Deprecated
    public ChunkedWriteHandler(final int n) {
        this.queue = new ArrayDeque();
        if (n <= 0) {
            throw new IllegalArgumentException("maxPendingWrites: " + n + " (expected: > 0)");
        }
    }
    
    @Override
    public void handlerAdded(final ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }
    
    public void resumeTransfer() {
        final ChannelHandlerContext ctx = this.ctx;
        if (ctx == null) {
            return;
        }
        if (ctx.executor().inEventLoop()) {
            this.doFlush(ctx);
        }
        else {
            ctx.executor().execute(new Runnable(ctx) {
                final ChannelHandlerContext val$ctx;
                final ChunkedWriteHandler this$0;
                
                @Override
                public void run() {
                    ChunkedWriteHandler.access$000(this.this$0, this.val$ctx);
                }
            });
        }
    }
    
    @Override
    public void write(final ChannelHandlerContext channelHandlerContext, final Object o, final ChannelPromise channelPromise) throws Exception {
        this.queue.add(new PendingWrite(o, channelPromise));
    }
    
    @Override
    public void flush(final ChannelHandlerContext channelHandlerContext) throws Exception {
        final Channel channel = channelHandlerContext.channel();
        if (channel.isWritable() || !channel.isActive()) {
            this.doFlush(channelHandlerContext);
        }
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.doFlush(channelHandlerContext);
        super.channelInactive(channelHandlerContext);
    }
    
    @Override
    public void channelWritabilityChanged(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (channelHandlerContext.channel().isWritable()) {
            this.doFlush(channelHandlerContext);
        }
        channelHandlerContext.fireChannelWritabilityChanged();
    }
    
    private void discard(Throwable t) {
        while (true) {
            PendingWrite currentWrite = this.currentWrite;
            if (this.currentWrite == null) {
                currentWrite = this.queue.poll();
            }
            else {
                this.currentWrite = null;
            }
            if (currentWrite == null) {
                break;
            }
            final Object msg = currentWrite.msg;
            if (msg instanceof ChunkedInput) {
                final ChunkedInput chunkedInput = (ChunkedInput)msg;
                if (!chunkedInput.isEndOfInput()) {
                    if (t == null) {
                        t = new ClosedChannelException();
                    }
                    currentWrite.fail(t);
                }
                else {
                    currentWrite.success();
                }
                closeInput(chunkedInput);
            }
            else {
                if (t == null) {
                    t = new ClosedChannelException();
                }
                currentWrite.fail(t);
            }
        }
    }
    
    private void doFlush(final ChannelHandlerContext channelHandlerContext) throws Exception {
        final Channel channel = channelHandlerContext.channel();
        if (!channel.isActive()) {
            this.discard(null);
            return;
        }
        while (channel.isWritable()) {
            if (this.currentWrite == null) {
                this.currentWrite = this.queue.poll();
            }
            if (this.currentWrite == null) {
                break;
            }
            final PendingWrite currentWrite = this.currentWrite;
            final Object msg = currentWrite.msg;
            if (msg instanceof ChunkedInput) {
                final ChunkedInput chunkedInput = (ChunkedInput)msg;
                Object o = chunkedInput.readChunk(channelHandlerContext);
                final boolean endOfInput = chunkedInput.isEndOfInput();
                if (o == null) {
                    final boolean b = !endOfInput;
                }
                if (false) {
                    break;
                }
                if (o == null) {
                    o = Unpooled.EMPTY_BUFFER;
                }
                final int amount = amount(o);
                final ChannelFuture write = channelHandlerContext.write(o);
                if (endOfInput) {
                    this.currentWrite = null;
                    write.addListener((GenericFutureListener)new ChannelFutureListener(currentWrite, amount, chunkedInput) {
                        final PendingWrite val$currentWrite;
                        final int val$amount;
                        final ChunkedInput val$chunks;
                        final ChunkedWriteHandler this$0;
                        
                        public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                            this.val$currentWrite.progress(this.val$amount);
                            this.val$currentWrite.success();
                            ChunkedWriteHandler.closeInput(this.val$chunks);
                        }
                        
                        @Override
                        public void operationComplete(final Future future) throws Exception {
                            this.operationComplete((ChannelFuture)future);
                        }
                    });
                }
                else if (channel.isWritable()) {
                    write.addListener((GenericFutureListener)new ChannelFutureListener(msg, currentWrite, amount) {
                        final Object val$pendingMessage;
                        final PendingWrite val$currentWrite;
                        final int val$amount;
                        final ChunkedWriteHandler this$0;
                        
                        public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                            if (!channelFuture.isSuccess()) {
                                ChunkedWriteHandler.closeInput((ChunkedInput)this.val$pendingMessage);
                                this.val$currentWrite.fail(channelFuture.cause());
                            }
                            else {
                                this.val$currentWrite.progress(this.val$amount);
                            }
                        }
                        
                        @Override
                        public void operationComplete(final Future future) throws Exception {
                            this.operationComplete((ChannelFuture)future);
                        }
                    });
                }
                else {
                    write.addListener((GenericFutureListener)new ChannelFutureListener(msg, currentWrite, amount, channel) {
                        final Object val$pendingMessage;
                        final PendingWrite val$currentWrite;
                        final int val$amount;
                        final Channel val$channel;
                        final ChunkedWriteHandler this$0;
                        
                        public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                            if (!channelFuture.isSuccess()) {
                                ChunkedWriteHandler.closeInput((ChunkedInput)this.val$pendingMessage);
                                this.val$currentWrite.fail(channelFuture.cause());
                            }
                            else {
                                this.val$currentWrite.progress(this.val$amount);
                                if (this.val$channel.isWritable()) {
                                    this.this$0.resumeTransfer();
                                }
                            }
                        }
                        
                        @Override
                        public void operationComplete(final Future future) throws Exception {
                            this.operationComplete((ChannelFuture)future);
                        }
                    });
                }
            }
            else {
                channelHandlerContext.write(msg, currentWrite.promise);
                this.currentWrite = null;
            }
            channelHandlerContext.flush();
            if (!channel.isActive()) {
                this.discard(new ClosedChannelException());
            }
        }
    }
    
    static void closeInput(final ChunkedInput chunkedInput) {
        chunkedInput.close();
    }
    
    private static int amount(final Object o) {
        if (o instanceof ByteBuf) {
            return ((ByteBuf)o).readableBytes();
        }
        if (o instanceof ByteBufHolder) {
            return ((ByteBufHolder)o).content().readableBytes();
        }
        return 1;
    }
    
    static void access$000(final ChunkedWriteHandler chunkedWriteHandler, final ChannelHandlerContext channelHandlerContext) throws Exception {
        chunkedWriteHandler.doFlush(channelHandlerContext);
    }
    
    static InternalLogger access$100() {
        return ChunkedWriteHandler.logger;
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(ChunkedWriteHandler.class);
    }
    
    private static final class PendingWrite
    {
        final Object msg;
        final ChannelPromise promise;
        private long progress;
        
        PendingWrite(final Object msg, final ChannelPromise promise) {
            this.msg = msg;
            this.promise = promise;
        }
        
        void fail(final Throwable t) {
            ReferenceCountUtil.release(this.msg);
            this.promise.tryFailure(t);
        }
        
        void success() {
            if (this.promise.isDone()) {
                return;
            }
            if (this.promise instanceof ChannelProgressivePromise) {
                ((ChannelProgressivePromise)this.promise).tryProgress(this.progress, this.progress);
            }
            this.promise.trySuccess();
        }
        
        void progress(final int n) {
            this.progress += n;
            if (this.promise instanceof ChannelProgressivePromise) {
                ((ChannelProgressivePromise)this.promise).tryProgress(this.progress, -1L);
            }
        }
    }
}
