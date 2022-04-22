package io.netty.handler.codec.compression;

import java.util.zip.*;
import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;
import java.util.concurrent.*;

public class JdkZlibEncoder extends ZlibEncoder
{
    private final ZlibWrapper wrapper;
    private final Deflater deflater;
    private boolean finished;
    private ChannelHandlerContext ctx;
    private final CRC32 crc;
    private static final byte[] gzipHeader;
    private boolean writeHeader;
    
    public JdkZlibEncoder() {
        this(6);
    }
    
    public JdkZlibEncoder(final int n) {
        this(ZlibWrapper.ZLIB, n);
    }
    
    public JdkZlibEncoder(final ZlibWrapper zlibWrapper) {
        this(zlibWrapper, 6);
    }
    
    public JdkZlibEncoder(final ZlibWrapper wrapper, final int n) {
        this.crc = new CRC32();
        this.writeHeader = true;
        if (n < 0 || n > 9) {
            throw new IllegalArgumentException("compressionLevel: " + n + " (expected: 0-9)");
        }
        if (wrapper == null) {
            throw new NullPointerException("wrapper");
        }
        if (wrapper == ZlibWrapper.ZLIB_OR_NONE) {
            throw new IllegalArgumentException("wrapper '" + ZlibWrapper.ZLIB_OR_NONE + "' is not " + "allowed for compression.");
        }
        this.wrapper = wrapper;
        this.deflater = new Deflater(n, wrapper != ZlibWrapper.ZLIB);
    }
    
    public JdkZlibEncoder(final byte[] array) {
        this(6, array);
    }
    
    public JdkZlibEncoder(final int n, final byte[] dictionary) {
        this.crc = new CRC32();
        this.writeHeader = true;
        if (n < 0 || n > 9) {
            throw new IllegalArgumentException("compressionLevel: " + n + " (expected: 0-9)");
        }
        if (dictionary == null) {
            throw new NullPointerException("dictionary");
        }
        this.wrapper = ZlibWrapper.ZLIB;
        (this.deflater = new Deflater(n)).setDictionary(dictionary);
    }
    
    @Override
    public ChannelFuture close() {
        return this.close(this.ctx().newPromise());
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
            final JdkZlibEncoder this$0;
            
            @Override
            public void run() {
                JdkZlibEncoder.access$100(this.this$0, JdkZlibEncoder.access$000(this.this$0), this.val$p).addListener((GenericFutureListener)new ChannelPromiseNotifier(new ChannelPromise[] { this.val$promise }));
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
            byteBuf2.writeBytes(byteBuf);
            return;
        }
        final int readableBytes = byteBuf.readableBytes();
        byte[] array;
        if (byteBuf.hasArray()) {
            array = byteBuf.array();
            final int n = byteBuf.arrayOffset() + byteBuf.readerIndex();
            byteBuf.skipBytes(readableBytes);
        }
        else {
            array = new byte[readableBytes];
            byteBuf.readBytes(array);
        }
        if (this.writeHeader) {
            this.writeHeader = false;
            if (this.wrapper == ZlibWrapper.GZIP) {
                byteBuf2.writeBytes(JdkZlibEncoder.gzipHeader);
            }
        }
        if (this.wrapper == ZlibWrapper.GZIP) {
            this.crc.update(array, 0, readableBytes);
        }
        this.deflater.setInput(array, 0, readableBytes);
        while (!this.deflater.needsInput()) {
            this.deflate(byteBuf2);
        }
    }
    
    protected final ByteBuf allocateBuffer(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final boolean b) throws Exception {
        int n = (int)Math.ceil(byteBuf.readableBytes() * 1.001) + 12;
        if (this.writeHeader) {
            switch (this.wrapper) {
                case GZIP: {
                    n += JdkZlibEncoder.gzipHeader.length;
                    break;
                }
                case ZLIB: {
                    n += 2;
                    break;
                }
            }
        }
        return channelHandlerContext.alloc().heapBuffer(n);
    }
    
    @Override
    public void close(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        final ChannelFuture finishEncode = this.finishEncode(channelHandlerContext, channelHandlerContext.newPromise());
        finishEncode.addListener((GenericFutureListener)new ChannelFutureListener(channelHandlerContext, channelPromise) {
            final ChannelHandlerContext val$ctx;
            final ChannelPromise val$promise;
            final JdkZlibEncoder this$0;
            
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
                final JdkZlibEncoder this$0;
                
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
        ByteBuf byteBuf = channelHandlerContext.alloc().heapBuffer();
        if (this.writeHeader && this.wrapper == ZlibWrapper.GZIP) {
            this.writeHeader = false;
            byteBuf.writeBytes(JdkZlibEncoder.gzipHeader);
        }
        this.deflater.finish();
        while (!this.deflater.finished()) {
            this.deflate(byteBuf);
            if (!byteBuf.isWritable()) {
                channelHandlerContext.write(byteBuf);
                byteBuf = channelHandlerContext.alloc().heapBuffer();
            }
        }
        if (this.wrapper == ZlibWrapper.GZIP) {
            final int n = (int)this.crc.getValue();
            final int totalIn = this.deflater.getTotalIn();
            byteBuf.writeByte(n);
            byteBuf.writeByte(n >>> 8);
            byteBuf.writeByte(n >>> 16);
            byteBuf.writeByte(n >>> 24);
            byteBuf.writeByte(totalIn);
            byteBuf.writeByte(totalIn >>> 8);
            byteBuf.writeByte(totalIn >>> 16);
            byteBuf.writeByte(totalIn >>> 24);
        }
        this.deflater.end();
        return channelHandlerContext.writeAndFlush(byteBuf, channelPromise);
    }
    
    private void deflate(final ByteBuf byteBuf) {
        int i;
        do {
            final int writerIndex = byteBuf.writerIndex();
            i = this.deflater.deflate(byteBuf.array(), byteBuf.arrayOffset() + writerIndex, byteBuf.writableBytes(), 2);
            byteBuf.writerIndex(writerIndex + i);
        } while (i > 0);
    }
    
    @Override
    public void handlerAdded(final ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)o, byteBuf);
    }
    
    @Override
    protected ByteBuf allocateBuffer(final ChannelHandlerContext channelHandlerContext, final Object o, final boolean b) throws Exception {
        return this.allocateBuffer(channelHandlerContext, (ByteBuf)o, b);
    }
    
    static ChannelHandlerContext access$000(final JdkZlibEncoder jdkZlibEncoder) {
        return jdkZlibEncoder.ctx();
    }
    
    static ChannelFuture access$100(final JdkZlibEncoder jdkZlibEncoder, final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) {
        return jdkZlibEncoder.finishEncode(channelHandlerContext, channelPromise);
    }
    
    static {
        gzipHeader = new byte[] { 31, -117, 8, 0, 0, 0, 0, 0, 0, 0 };
    }
}
