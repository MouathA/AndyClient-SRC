package com.viaversion.viaversion.protocols.protocol1_9to1_8.providers;

import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import java.util.zip.*;
import java.util.*;
import io.netty.handler.codec.*;

public class CompressionProvider implements Provider
{
    public void handlePlayCompression(final UserConnection userConnection, final int n) {
        if (!userConnection.isClientSide()) {
            throw new IllegalStateException("PLAY state Compression packet is unsupported");
        }
        final ChannelPipeline pipeline = userConnection.getChannel().pipeline();
        if (n < 0) {
            if (pipeline.get("compress") != null) {
                pipeline.remove("compress");
                pipeline.remove("decompress");
            }
        }
        else if (pipeline.get("compress") == null) {
            pipeline.addBefore(Via.getManager().getInjector().getEncoderName(), "compress", this.getEncoder(n));
            pipeline.addBefore(Via.getManager().getInjector().getDecoderName(), "decompress", this.getDecoder(n));
        }
        else {
            ((CompressionHandler)pipeline.get("compress")).setCompressionThreshold(n);
            ((CompressionHandler)pipeline.get("decompress")).setCompressionThreshold(n);
        }
    }
    
    protected CompressionHandler getEncoder(final int n) {
        return new Compressor(n);
    }
    
    protected CompressionHandler getDecoder(final int n) {
        return new Decompressor(n);
    }
    
    private static class Compressor extends MessageToByteEncoder implements CompressionHandler
    {
        private final Deflater deflater;
        private int threshold;
        
        public Compressor(final int threshold) {
            this.threshold = threshold;
            this.deflater = new Deflater();
        }
        
        protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final ByteBuf byteBuf2) throws Exception {
            final int readableBytes = byteBuf.readableBytes();
            if (readableBytes < this.threshold) {
                byteBuf2.writeByte(0);
                byteBuf2.writeBytes(byteBuf);
                return;
            }
            Type.VAR_INT.writePrimitive(byteBuf2, readableBytes);
            ByteBuf writeBytes = byteBuf;
            if (!byteBuf.hasArray()) {
                writeBytes = channelHandlerContext.alloc().heapBuffer().writeBytes(byteBuf);
            }
            else {
                byteBuf.retain();
            }
            final ByteBuf heapBuffer = channelHandlerContext.alloc().heapBuffer();
            this.deflater.setInput(writeBytes.array(), writeBytes.arrayOffset() + writeBytes.readerIndex(), writeBytes.readableBytes());
            this.deflater.finish();
            while (!this.deflater.finished()) {
                heapBuffer.ensureWritable(4096);
                heapBuffer.writerIndex(heapBuffer.writerIndex() + this.deflater.deflate(heapBuffer.array(), heapBuffer.arrayOffset() + heapBuffer.writerIndex(), heapBuffer.writableBytes()));
            }
            byteBuf2.writeBytes(heapBuffer);
            heapBuffer.release();
            writeBytes.release();
            this.deflater.reset();
        }
        
        @Override
        public void setCompressionThreshold(final int threshold) {
            this.threshold = threshold;
        }
        
        @Override
        protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
            this.encode(channelHandlerContext, (ByteBuf)o, byteBuf);
        }
    }
    
    public interface CompressionHandler extends ChannelHandler
    {
        void setCompressionThreshold(final int p0);
    }
    
    private static class Decompressor extends MessageToMessageDecoder implements CompressionHandler
    {
        private final Inflater inflater;
        private int threshold;
        
        public Decompressor(final int threshold) {
            this.threshold = threshold;
            this.inflater = new Inflater();
        }
        
        protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
            if (!byteBuf.isReadable()) {
                return;
            }
            final int primitive = Type.VAR_INT.readPrimitive(byteBuf);
            if (primitive == 0) {
                list.add(byteBuf.readBytes(byteBuf.readableBytes()));
                return;
            }
            if (primitive < this.threshold) {
                throw new DecoderException("Badly compressed packet - size of " + primitive + " is below server threshold of " + this.threshold);
            }
            if (primitive > 2097152) {
                throw new DecoderException("Badly compressed packet - size of " + primitive + " is larger than protocol maximum of " + 2097152);
            }
            ByteBuf writeBytes = byteBuf;
            if (!byteBuf.hasArray()) {
                writeBytes = channelHandlerContext.alloc().heapBuffer().writeBytes(byteBuf);
            }
            else {
                byteBuf.retain();
            }
            final ByteBuf heapBuffer = channelHandlerContext.alloc().heapBuffer(primitive, primitive);
            this.inflater.setInput(writeBytes.array(), writeBytes.arrayOffset() + writeBytes.readerIndex(), writeBytes.readableBytes());
            heapBuffer.writerIndex(heapBuffer.writerIndex() + this.inflater.inflate(heapBuffer.array(), heapBuffer.arrayOffset(), primitive));
            list.add(heapBuffer.retain());
            heapBuffer.release();
            writeBytes.release();
            this.inflater.reset();
        }
        
        @Override
        public void setCompressionThreshold(final int threshold) {
            this.threshold = threshold;
        }
        
        @Override
        protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
            this.decode(channelHandlerContext, (ByteBuf)o, list);
        }
    }
}
