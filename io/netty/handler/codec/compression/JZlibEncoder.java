package io.netty.handler.codec.compression;

import com.jcraft.jzlib.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;
import java.util.concurrent.*;
import io.netty.util.internal.*;
import io.netty.buffer.*;

public class JZlibEncoder extends ZlibEncoder
{
    private final int wrapperOverhead;
    private final Deflater z;
    private boolean finished;
    private ChannelHandlerContext ctx;
    
    public JZlibEncoder() {
        this(6);
    }
    
    public JZlibEncoder(final int n) {
        this(ZlibWrapper.ZLIB, n);
    }
    
    public JZlibEncoder(final ZlibWrapper zlibWrapper) {
        this(zlibWrapper, 6);
    }
    
    public JZlibEncoder(final ZlibWrapper zlibWrapper, final int n) {
        this(zlibWrapper, n, 15, 8);
    }
    
    public JZlibEncoder(final ZlibWrapper zlibWrapper, final int n, final int n2, final int n3) {
        this.z = new Deflater();
        if (n < 0 || n > 9) {
            throw new IllegalArgumentException("compressionLevel: " + n + " (expected: 0-9)");
        }
        if (n2 < 9 || n2 > 15) {
            throw new IllegalArgumentException("windowBits: " + n2 + " (expected: 9-15)");
        }
        if (n3 < 1 || n3 > 9) {
            throw new IllegalArgumentException("memLevel: " + n3 + " (expected: 1-9)");
        }
        if (zlibWrapper == null) {
            throw new NullPointerException("wrapper");
        }
        if (zlibWrapper == ZlibWrapper.ZLIB_OR_NONE) {
            throw new IllegalArgumentException("wrapper '" + ZlibWrapper.ZLIB_OR_NONE + "' is not " + "allowed for compression.");
        }
        final int init = this.z.init(n, n2, n3, ZlibUtil.convertWrapperType(zlibWrapper));
        if (init != 0) {
            ZlibUtil.fail(this.z, "initialization failure", init);
        }
        this.wrapperOverhead = ZlibUtil.wrapperOverhead(zlibWrapper);
    }
    
    public JZlibEncoder(final byte[] array) {
        this(6, array);
    }
    
    public JZlibEncoder(final int n, final byte[] array) {
        this(n, 15, 8, array);
    }
    
    public JZlibEncoder(final int n, final int n2, final int n3, final byte[] array) {
        this.z = new Deflater();
        if (n < 0 || n > 9) {
            throw new IllegalArgumentException("compressionLevel: " + n + " (expected: 0-9)");
        }
        if (n2 < 9 || n2 > 15) {
            throw new IllegalArgumentException("windowBits: " + n2 + " (expected: 9-15)");
        }
        if (n3 < 1 || n3 > 9) {
            throw new IllegalArgumentException("memLevel: " + n3 + " (expected: 1-9)");
        }
        if (array == null) {
            throw new NullPointerException("dictionary");
        }
        final int deflateInit = this.z.deflateInit(n, n2, n3, JZlib.W_ZLIB);
        if (deflateInit != 0) {
            ZlibUtil.fail(this.z, "initialization failure", deflateInit);
        }
        else {
            final int deflateSetDictionary = this.z.deflateSetDictionary(array, array.length);
            if (deflateSetDictionary != 0) {
                ZlibUtil.fail(this.z, "failed to set the dictionary", deflateSetDictionary);
            }
        }
        this.wrapperOverhead = ZlibUtil.wrapperOverhead(ZlibWrapper.ZLIB);
    }
    
    @Override
    public ChannelFuture close() {
        return this.close(this.ctx().channel().newPromise());
    }
    
    @Override
    public ChannelFuture close(final ChannelPromise channelPromise) {
        final ChannelHandlerContext ctx = this.ctx();
        final EventExecutor executor = ctx.executor();
        if (executor.inEventLoop()) {
            return this.finishEncode(ctx, channelPromise);
        }
        final ChannelPromise promise = ctx.newPromise();
        executor.execute(new Runnable(promise, channelPromise) {
            final ChannelPromise val$p;
            final ChannelPromise val$promise;
            final JZlibEncoder this$0;
            
            @Override
            public void run() {
                JZlibEncoder.access$100(this.this$0, JZlibEncoder.access$000(this.this$0), this.val$p).addListener((GenericFutureListener)new ChannelPromiseNotifier(new ChannelPromise[] { this.val$promise }));
            }
        });
        return promise;
    }
    
    private ChannelHandlerContext ctx() {
        final ChannelHandlerContext ctx = this.ctx;
        if (ctx == null) {
            throw new IllegalStateException("not added to a pipeline");
        }
        return ctx;
    }
    
    @Override
    public boolean isClosed() {
        return this.finished;
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final ByteBuf byteBuf2) throws Exception {
        if (this.finished) {
            return;
        }
        final int readableBytes = byteBuf.readableBytes();
        final boolean hasArray = byteBuf.hasArray();
        this.z.avail_in = readableBytes;
        if (hasArray) {
            this.z.next_in = byteBuf.array();
            this.z.next_in_index = byteBuf.arrayOffset() + byteBuf.readerIndex();
        }
        else {
            final byte[] next_in = new byte[readableBytes];
            byteBuf.getBytes(byteBuf.readerIndex(), next_in);
            this.z.next_in = next_in;
            this.z.next_in_index = 0;
        }
        final int next_in_index = this.z.next_in_index;
        final int avail_out = (int)Math.ceil(readableBytes * 1.001) + 12 + this.wrapperOverhead;
        byteBuf2.ensureWritable(avail_out);
        this.z.avail_out = avail_out;
        this.z.next_out = byteBuf2.array();
        this.z.next_out_index = byteBuf2.arrayOffset() + byteBuf2.writerIndex();
        final int next_out_index = this.z.next_out_index;
        final int deflate = this.z.deflate(2);
        byteBuf.skipBytes(this.z.next_in_index - next_in_index);
        if (deflate != 0) {
            ZlibUtil.fail(this.z, "compression failure", deflate);
        }
        final int n = this.z.next_out_index - next_out_index;
        if (n > 0) {
            byteBuf2.writerIndex(byteBuf2.writerIndex() + n);
        }
        this.z.next_in = null;
        this.z.next_out = null;
    }
    
    @Override
    public void close(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) {
        final ChannelFuture finishEncode = this.finishEncode(channelHandlerContext, channelHandlerContext.newPromise());
        finishEncode.addListener((GenericFutureListener)new ChannelFutureListener(channelHandlerContext, channelPromise) {
            final ChannelHandlerContext val$ctx;
            final ChannelPromise val$promise;
            final JZlibEncoder this$0;
            
            public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                this.val$ctx.close(this.val$promise);
            }
            
            @Override
            public void operationComplete(final Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        });
        if (!finishEncode.isDone()) {
            channelHandlerContext.executor().schedule((Runnable)new Runnable(channelHandlerContext, channelPromise) {
                final ChannelHandlerContext val$ctx;
                final ChannelPromise val$promise;
                final JZlibEncoder this$0;
                
                @Override
                public void run() {
                    this.val$ctx.close(this.val$promise);
                }
            }, 10L, TimeUnit.SECONDS);
        }
    }
    
    private ChannelFuture finishEncode(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) {
        if (this.finished) {
            channelPromise.setSuccess();
            return channelPromise;
        }
        this.finished = true;
        this.z.next_in = EmptyArrays.EMPTY_BYTES;
        this.z.next_in_index = 0;
        this.z.avail_in = 0;
        final byte[] next_out = new byte[32];
        this.z.next_out = next_out;
        this.z.next_out_index = 0;
        this.z.avail_out = next_out.length;
        final int deflate = this.z.deflate(4);
        if (deflate != 0 && deflate != 1) {
            channelPromise.setFailure((Throwable)ZlibUtil.deflaterException(this.z, "compression failure", deflate));
            this.z.deflateEnd();
            this.z.next_in = null;
            this.z.next_out = null;
            return channelPromise;
        }
        ByteBuf byteBuf;
        if (this.z.next_out_index != 0) {
            byteBuf = Unpooled.wrappedBuffer(next_out, 0, this.z.next_out_index);
        }
        else {
            byteBuf = Unpooled.EMPTY_BUFFER;
        }
        this.z.deflateEnd();
        this.z.next_in = null;
        this.z.next_out = null;
        return channelHandlerContext.writeAndFlush(byteBuf, channelPromise);
    }
    
    @Override
    public void handlerAdded(final ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)o, byteBuf);
    }
    
    static ChannelHandlerContext access$000(final JZlibEncoder zlibEncoder) {
        return zlibEncoder.ctx();
    }
    
    static ChannelFuture access$100(final JZlibEncoder zlibEncoder, final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) {
        return zlibEncoder.finishEncode(channelHandlerContext, channelPromise);
    }
}
