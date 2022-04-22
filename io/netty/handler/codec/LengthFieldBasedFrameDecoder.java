package io.netty.handler.codec;

import java.nio.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;

public class LengthFieldBasedFrameDecoder extends ByteToMessageDecoder
{
    private final ByteOrder byteOrder;
    private final int maxFrameLength;
    private final int lengthFieldOffset;
    private final int lengthFieldLength;
    private final int lengthFieldEndOffset;
    private final int lengthAdjustment;
    private final int initialBytesToStrip;
    private final boolean failFast;
    private boolean discardingTooLongFrame;
    private long tooLongFrameLength;
    private long bytesToDiscard;
    
    public LengthFieldBasedFrameDecoder(final int n, final int n2, final int n3) {
        this(n, n2, n3, 0, 0);
    }
    
    public LengthFieldBasedFrameDecoder(final int n, final int n2, final int n3, final int n4, final int n5) {
        this(n, n2, n3, n4, n5, true);
    }
    
    public LengthFieldBasedFrameDecoder(final int n, final int n2, final int n3, final int n4, final int n5, final boolean b) {
        this(ByteOrder.BIG_ENDIAN, n, n2, n3, n4, n5, b);
    }
    
    public LengthFieldBasedFrameDecoder(final ByteOrder byteOrder, final int maxFrameLength, final int lengthFieldOffset, final int lengthFieldLength, final int lengthAdjustment, final int initialBytesToStrip, final boolean failFast) {
        if (byteOrder == null) {
            throw new NullPointerException("byteOrder");
        }
        if (maxFrameLength <= 0) {
            throw new IllegalArgumentException("maxFrameLength must be a positive integer: " + maxFrameLength);
        }
        if (lengthFieldOffset < 0) {
            throw new IllegalArgumentException("lengthFieldOffset must be a non-negative integer: " + lengthFieldOffset);
        }
        if (initialBytesToStrip < 0) {
            throw new IllegalArgumentException("initialBytesToStrip must be a non-negative integer: " + initialBytesToStrip);
        }
        if (lengthFieldOffset > maxFrameLength - lengthFieldLength) {
            throw new IllegalArgumentException("maxFrameLength (" + maxFrameLength + ") " + "must be equal to or greater than " + "lengthFieldOffset (" + lengthFieldOffset + ") + " + "lengthFieldLength (" + lengthFieldLength + ").");
        }
        this.byteOrder = byteOrder;
        this.maxFrameLength = maxFrameLength;
        this.lengthFieldOffset = lengthFieldOffset;
        this.lengthFieldLength = lengthFieldLength;
        this.lengthAdjustment = lengthAdjustment;
        this.lengthFieldEndOffset = lengthFieldOffset + lengthFieldLength;
        this.initialBytesToStrip = initialBytesToStrip;
        this.failFast = failFast;
    }
    
    @Override
    protected final void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        final Object decode = this.decode(channelHandlerContext, byteBuf);
        if (decode != null) {
            list.add(decode);
        }
    }
    
    protected Object decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws Exception {
        if (this.discardingTooLongFrame) {
            final long bytesToDiscard = this.bytesToDiscard;
            final int n = (int)Math.min(bytesToDiscard, byteBuf.readableBytes());
            byteBuf.skipBytes(n);
            this.bytesToDiscard = bytesToDiscard - n;
            this.failIfNecessary(false);
        }
        if (byteBuf.readableBytes() < this.lengthFieldEndOffset) {
            return null;
        }
        final long unadjustedFrameLength = this.getUnadjustedFrameLength(byteBuf, byteBuf.readerIndex() + this.lengthFieldOffset, this.lengthFieldLength, this.byteOrder);
        if (unadjustedFrameLength < 0L) {
            byteBuf.skipBytes(this.lengthFieldEndOffset);
            throw new CorruptedFrameException("negative pre-adjustment length field: " + unadjustedFrameLength);
        }
        final long tooLongFrameLength = unadjustedFrameLength + (this.lengthAdjustment + this.lengthFieldEndOffset);
        if (tooLongFrameLength < this.lengthFieldEndOffset) {
            byteBuf.skipBytes(this.lengthFieldEndOffset);
            throw new CorruptedFrameException("Adjusted frame length (" + tooLongFrameLength + ") is less " + "than lengthFieldEndOffset: " + this.lengthFieldEndOffset);
        }
        if (tooLongFrameLength > this.maxFrameLength) {
            final long bytesToDiscard2 = tooLongFrameLength - byteBuf.readableBytes();
            this.tooLongFrameLength = tooLongFrameLength;
            if (bytesToDiscard2 < 0L) {
                byteBuf.skipBytes((int)tooLongFrameLength);
            }
            else {
                this.discardingTooLongFrame = true;
                this.bytesToDiscard = bytesToDiscard2;
                byteBuf.skipBytes(byteBuf.readableBytes());
            }
            this.failIfNecessary(true);
            return null;
        }
        final int n2 = (int)tooLongFrameLength;
        if (byteBuf.readableBytes() < n2) {
            return null;
        }
        if (this.initialBytesToStrip > n2) {
            byteBuf.skipBytes(n2);
            throw new CorruptedFrameException("Adjusted frame length (" + tooLongFrameLength + ") is less " + "than initialBytesToStrip: " + this.initialBytesToStrip);
        }
        byteBuf.skipBytes(this.initialBytesToStrip);
        final int readerIndex = byteBuf.readerIndex();
        final int n3 = n2 - this.initialBytesToStrip;
        final ByteBuf frame = this.extractFrame(channelHandlerContext, byteBuf, readerIndex, n3);
        byteBuf.readerIndex(readerIndex + n3);
        return frame;
    }
    
    protected long getUnadjustedFrameLength(ByteBuf order, final int n, final int n2, final ByteOrder byteOrder) {
        order = order.order(byteOrder);
        long n3 = 0L;
        switch (n2) {
            case 1: {
                n3 = order.getUnsignedByte(n);
                break;
            }
            case 2: {
                n3 = order.getUnsignedShort(n);
                break;
            }
            case 3: {
                n3 = order.getUnsignedMedium(n);
                break;
            }
            case 4: {
                n3 = order.getUnsignedInt(n);
                break;
            }
            case 8: {
                n3 = order.getLong(n);
                break;
            }
            default: {
                throw new DecoderException("unsupported lengthFieldLength: " + this.lengthFieldLength + " (expected: 1, 2, 3, 4, or 8)");
            }
        }
        return n3;
    }
    
    private void failIfNecessary(final boolean b) {
        if (this.bytesToDiscard == 0L) {
            final long tooLongFrameLength = this.tooLongFrameLength;
            this.tooLongFrameLength = 0L;
            this.discardingTooLongFrame = false;
            if (!this.failFast || (this.failFast && b)) {
                this.fail(tooLongFrameLength);
            }
        }
        else if (this.failFast && b) {
            this.fail(this.tooLongFrameLength);
        }
    }
    
    protected ByteBuf extractFrame(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final int n, final int n2) {
        final ByteBuf buffer = channelHandlerContext.alloc().buffer(n2);
        buffer.writeBytes(byteBuf, n, n2);
        return buffer;
    }
    
    private void fail(final long n) {
        if (n > 0L) {
            throw new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + ": " + n + " - discarded");
        }
        throw new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + " - discarding");
    }
}
