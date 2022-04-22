package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.provider;

import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.*;
import de.gerrygames.viarewind.netty.*;
import io.netty.channel.*;
import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import java.util.zip.*;
import java.util.*;
import io.netty.handler.codec.*;

public class CompressionHandlerProvider implements Provider
{
    public void handleSetCompression(final UserConnection userConnection, final int n) {
        final ChannelPipeline pipeline = userConnection.getChannel().pipeline();
        if (userConnection.isClientSide()) {
            pipeline.addBefore(Via.getManager().getInjector().getEncoderName(), "compress", this.getEncoder(n));
            pipeline.addBefore(Via.getManager().getInjector().getDecoderName(), "decompress", this.getDecoder(n));
        }
        else {
            ((CompressionSendStorage)userConnection.get(CompressionSendStorage.class)).setRemoveCompression(true);
        }
    }
    
    public void handleTransform(final UserConnection userConnection) {
        final CompressionSendStorage compressionSendStorage = (CompressionSendStorage)userConnection.get(CompressionSendStorage.class);
        if (compressionSendStorage.isRemoveCompression()) {
            final ChannelPipeline pipeline = userConnection.getChannel().pipeline();
            String s = null;
            String s2 = null;
            if (pipeline.get("compress") != null) {
                s = "compress";
                s2 = "decompress";
            }
            else if (pipeline.get("compression-encoder") != null) {
                s = "compression-encoder";
                s2 = "compression-decoder";
            }
            if (s == null) {
                throw new IllegalStateException("Couldn't remove compression for 1.7!");
            }
            pipeline.replace(s2, s2, new EmptyChannelHandler());
            pipeline.replace(s, s, new ForwardMessageToByteEncoder());
            compressionSendStorage.setRemoveCompression(false);
        }
    }
    
    protected ChannelHandler getEncoder(final int n) {
        return new Compressor(n);
    }
    
    protected ChannelHandler getDecoder(final int n) {
        return new Decompressor(n);
    }
    
    private static class Compressor extends MessageToByteEncoder
    {
        private final Deflater deflater;
        private final int threshold;
        
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
                writeBytes = ByteBufAllocator.DEFAULT.heapBuffer().writeBytes(byteBuf);
            }
            else {
                byteBuf.retain();
            }
            final ByteBuf heapBuffer = ByteBufAllocator.DEFAULT.heapBuffer();
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
        protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
            this.encode(channelHandlerContext, (ByteBuf)o, byteBuf);
        }
    }
    
    private static class Decompressor extends MessageToMessageDecoder
    {
        private final Inflater inflater;
        private final int threshold;
        
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
                writeBytes = ByteBufAllocator.DEFAULT.heapBuffer().writeBytes(byteBuf);
            }
            else {
                byteBuf.retain();
            }
            final ByteBuf heapBuffer = ByteBufAllocator.DEFAULT.heapBuffer(primitive, primitive);
            this.inflater.setInput(writeBytes.array(), writeBytes.arrayOffset() + writeBytes.readerIndex(), writeBytes.readableBytes());
            heapBuffer.writerIndex(heapBuffer.writerIndex() + this.inflater.inflate(heapBuffer.array(), heapBuffer.arrayOffset(), primitive));
            list.add(heapBuffer.retain());
            heapBuffer.release();
            writeBytes.release();
            this.inflater.reset();
        }
        
        @Override
        protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
            this.decode(channelHandlerContext, (ByteBuf)o, list);
        }
    }
}
