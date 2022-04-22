package io.netty.handler.codec.spdy;

import io.netty.buffer.*;
import java.nio.*;
import java.util.*;

public class SpdyFrameEncoder
{
    private final int version;
    
    public SpdyFrameEncoder(final SpdyVersion spdyVersion) {
        if (spdyVersion == null) {
            throw new NullPointerException("spdyVersion");
        }
        this.version = spdyVersion.getVersion();
    }
    
    private void writeControlFrameHeader(final ByteBuf byteBuf, final int n, final byte b, final int n2) {
        byteBuf.writeShort(this.version | 0x8000);
        byteBuf.writeShort(n);
        byteBuf.writeByte(b);
        byteBuf.writeMedium(n2);
    }
    
    public ByteBuf encodeDataFrame(final ByteBufAllocator byteBufAllocator, final int n, final boolean b, final ByteBuf byteBuf) {
        final int n2 = b ? 1 : 0;
        final int readableBytes = byteBuf.readableBytes();
        final ByteBuf order = byteBufAllocator.ioBuffer(8 + readableBytes).order(ByteOrder.BIG_ENDIAN);
        order.writeInt(n & Integer.MAX_VALUE);
        order.writeByte(n2);
        order.writeMedium(readableBytes);
        order.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
        return order;
    }
    
    public ByteBuf encodeSynStreamFrame(final ByteBufAllocator byteBufAllocator, final int n, final int n2, final byte b, final boolean b2, final boolean b3, final ByteBuf byteBuf) {
        final int readableBytes = byteBuf.readableBytes();
        int n3 = b2 ? 1 : 0;
        if (b3) {
            n3 = (byte)(n3 | 0x2);
        }
        final int n4 = 10 + readableBytes;
        final ByteBuf order = byteBufAllocator.ioBuffer(8 + n4).order(ByteOrder.BIG_ENDIAN);
        this.writeControlFrameHeader(order, 1, (byte)n3, n4);
        order.writeInt(n);
        order.writeInt(n2);
        order.writeShort((b & 0xFF) << 13);
        order.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
        return order;
    }
    
    public ByteBuf encodeSynReplyFrame(final ByteBufAllocator byteBufAllocator, final int n, final boolean b, final ByteBuf byteBuf) {
        final int readableBytes = byteBuf.readableBytes();
        final int n2 = b ? 1 : 0;
        final int n3 = 4 + readableBytes;
        final ByteBuf order = byteBufAllocator.ioBuffer(8 + n3).order(ByteOrder.BIG_ENDIAN);
        this.writeControlFrameHeader(order, 2, (byte)n2, n3);
        order.writeInt(n);
        order.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
        return order;
    }
    
    public ByteBuf encodeRstStreamFrame(final ByteBufAllocator byteBufAllocator, final int n, final int n2) {
        final ByteBuf order = byteBufAllocator.ioBuffer(16).order(ByteOrder.BIG_ENDIAN);
        this.writeControlFrameHeader(order, 3, (byte)0, 8);
        order.writeInt(n);
        order.writeInt(n2);
        return order;
    }
    
    public ByteBuf encodeSettingsFrame(final ByteBufAllocator byteBufAllocator, final SpdySettingsFrame spdySettingsFrame) {
        final Set ids = spdySettingsFrame.ids();
        final int size = ids.size();
        spdySettingsFrame.clearPreviouslyPersistedSettings();
        final int n = 4 + 8 * size;
        final ByteBuf order = byteBufAllocator.ioBuffer(8 + n).order(ByteOrder.BIG_ENDIAN);
        this.writeControlFrameHeader(order, 4, (byte)0, n);
        order.writeInt(size);
        for (final Integer n2 : ids) {
            if (spdySettingsFrame.isPersistValue(n2)) {
                final byte b = 1;
            }
            if (spdySettingsFrame.isPersisted(n2)) {
                final byte b2 = 2;
            }
            order.writeByte(0);
            order.writeMedium(n2);
            order.writeInt(spdySettingsFrame.getValue(n2));
        }
        return order;
    }
    
    public ByteBuf encodePingFrame(final ByteBufAllocator byteBufAllocator, final int n) {
        final ByteBuf order = byteBufAllocator.ioBuffer(12).order(ByteOrder.BIG_ENDIAN);
        this.writeControlFrameHeader(order, 6, (byte)0, 4);
        order.writeInt(n);
        return order;
    }
    
    public ByteBuf encodeGoAwayFrame(final ByteBufAllocator byteBufAllocator, final int n, final int n2) {
        final ByteBuf order = byteBufAllocator.ioBuffer(16).order(ByteOrder.BIG_ENDIAN);
        this.writeControlFrameHeader(order, 7, (byte)0, 8);
        order.writeInt(n);
        order.writeInt(n2);
        return order;
    }
    
    public ByteBuf encodeHeadersFrame(final ByteBufAllocator byteBufAllocator, final int n, final boolean b, final ByteBuf byteBuf) {
        final int readableBytes = byteBuf.readableBytes();
        final int n2 = b ? 1 : 0;
        final int n3 = 4 + readableBytes;
        final ByteBuf order = byteBufAllocator.ioBuffer(8 + n3).order(ByteOrder.BIG_ENDIAN);
        this.writeControlFrameHeader(order, 8, (byte)n2, n3);
        order.writeInt(n);
        order.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
        return order;
    }
    
    public ByteBuf encodeWindowUpdateFrame(final ByteBufAllocator byteBufAllocator, final int n, final int n2) {
        final ByteBuf order = byteBufAllocator.ioBuffer(16).order(ByteOrder.BIG_ENDIAN);
        this.writeControlFrameHeader(order, 9, (byte)0, 8);
        order.writeInt(n);
        order.writeInt(n2);
        return order;
    }
}
