package io.netty.handler.codec;

import io.netty.buffer.*;
import io.netty.channel.*;
import java.util.*;

public class DelimiterBasedFrameDecoder extends ByteToMessageDecoder
{
    private final ByteBuf[] delimiters;
    private final int maxFrameLength;
    private final boolean stripDelimiter;
    private final boolean failFast;
    private boolean discardingTooLongFrame;
    private int tooLongFrameLength;
    private final LineBasedFrameDecoder lineBasedDecoder;
    
    public DelimiterBasedFrameDecoder(final int n, final ByteBuf byteBuf) {
        this(n, true, byteBuf);
    }
    
    public DelimiterBasedFrameDecoder(final int n, final boolean b, final ByteBuf byteBuf) {
        this(n, b, true, byteBuf);
    }
    
    public DelimiterBasedFrameDecoder(final int n, final boolean b, final boolean b2, final ByteBuf byteBuf) {
        this(n, b, b2, new ByteBuf[] { byteBuf.slice(byteBuf.readerIndex(), byteBuf.readableBytes()) });
    }
    
    public DelimiterBasedFrameDecoder(final int n, final ByteBuf... array) {
        this(n, true, array);
    }
    
    public DelimiterBasedFrameDecoder(final int n, final boolean b, final ByteBuf... array) {
        this(n, b, true, array);
    }
    
    public DelimiterBasedFrameDecoder(final int maxFrameLength, final boolean stripDelimiter, final boolean failFast, final ByteBuf... array) {
        validateMaxFrameLength(maxFrameLength);
        if (array == null) {
            throw new NullPointerException("delimiters");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("empty delimiters");
        }
        if (isLineBased(array) && !this.isSubclass()) {
            this.lineBasedDecoder = new LineBasedFrameDecoder(maxFrameLength, stripDelimiter, failFast);
            this.delimiters = null;
        }
        else {
            this.delimiters = new ByteBuf[array.length];
            while (0 < array.length) {
                final ByteBuf byteBuf = array[0];
                validateDelimiter(byteBuf);
                this.delimiters[0] = byteBuf.slice(byteBuf.readerIndex(), byteBuf.readableBytes());
                int n = 0;
                ++n;
            }
            this.lineBasedDecoder = null;
        }
        this.maxFrameLength = maxFrameLength;
        this.stripDelimiter = stripDelimiter;
        this.failFast = failFast;
    }
    
    private static boolean isLineBased(final ByteBuf[] array) {
        if (array.length != 2) {
            return false;
        }
        ByteBuf byteBuf = array[0];
        ByteBuf byteBuf2 = array[1];
        if (byteBuf.capacity() < byteBuf2.capacity()) {
            byteBuf = array[1];
            byteBuf2 = array[0];
        }
        return byteBuf.capacity() == 2 && byteBuf2.capacity() == 1 && byteBuf.getByte(0) == 13 && byteBuf.getByte(1) == 10 && byteBuf2.getByte(0) == 10;
    }
    
    private boolean isSubclass() {
        return this.getClass() != DelimiterBasedFrameDecoder.class;
    }
    
    @Override
    protected final void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        final Object decode = this.decode(channelHandlerContext, byteBuf);
        if (decode != null) {
            list.add(decode);
        }
    }
    
    protected Object decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws Exception {
        if (this.lineBasedDecoder != null) {
            return this.lineBasedDecoder.decode(channelHandlerContext, byteBuf);
        }
        ByteBuf byteBuf2 = null;
        final ByteBuf[] delimiters = this.delimiters;
        while (0 < delimiters.length) {
            final ByteBuf byteBuf3 = delimiters[0];
            final int index = indexOf(byteBuf, byteBuf3);
            if (index >= 0 && index < Integer.MAX_VALUE) {
                byteBuf2 = byteBuf3;
            }
            int tooLongFrameLength = 0;
            ++tooLongFrameLength;
        }
        if (byteBuf2 == null) {
            if (!this.discardingTooLongFrame) {
                if (byteBuf.readableBytes() > this.maxFrameLength) {
                    this.tooLongFrameLength = byteBuf.readableBytes();
                    byteBuf.skipBytes(byteBuf.readableBytes());
                    this.discardingTooLongFrame = true;
                    if (this.failFast) {
                        this.fail(this.tooLongFrameLength);
                    }
                }
            }
            else {
                this.tooLongFrameLength += byteBuf.readableBytes();
                byteBuf.skipBytes(byteBuf.readableBytes());
            }
            return null;
        }
        final int capacity = byteBuf2.capacity();
        if (this.discardingTooLongFrame) {
            this.discardingTooLongFrame = false;
            byteBuf.skipBytes(Integer.MAX_VALUE + capacity);
            final int tooLongFrameLength = this.tooLongFrameLength;
            this.tooLongFrameLength = 0;
            if (!this.failFast) {
                this.fail(0);
            }
            return null;
        }
        if (Integer.MAX_VALUE > this.maxFrameLength) {
            byteBuf.skipBytes(Integer.MAX_VALUE + capacity);
            this.fail(Integer.MAX_VALUE);
            return null;
        }
        ByteBuf byteBuf4;
        if (this.stripDelimiter) {
            byteBuf4 = byteBuf.readSlice(Integer.MAX_VALUE);
            byteBuf.skipBytes(capacity);
        }
        else {
            byteBuf4 = byteBuf.readSlice(Integer.MAX_VALUE + capacity);
        }
        return byteBuf4.retain();
    }
    
    private void fail(final long n) {
        if (n > 0L) {
            throw new TooLongFrameException("frame length exceeds " + this.maxFrameLength + ": " + n + " - discarded");
        }
        throw new TooLongFrameException("frame length exceeds " + this.maxFrameLength + " - discarding");
    }
    
    private static int indexOf(final ByteBuf byteBuf, final ByteBuf byteBuf2) {
        for (int i = byteBuf.readerIndex(); i < byteBuf.writerIndex(); ++i) {
            int n = i;
            while (0 < byteBuf2.capacity() && byteBuf.getByte(n) == byteBuf2.getByte(0)) {
                if (++n == byteBuf.writerIndex() && 0 != byteBuf2.capacity() - 1) {
                    return -1;
                }
                int n2 = 0;
                ++n2;
            }
            if (0 == byteBuf2.capacity()) {
                return i - byteBuf.readerIndex();
            }
        }
        return -1;
    }
    
    private static void validateDelimiter(final ByteBuf byteBuf) {
        if (byteBuf == null) {
            throw new NullPointerException("delimiter");
        }
        if (!byteBuf.isReadable()) {
            throw new IllegalArgumentException("empty delimiter");
        }
    }
    
    private static void validateMaxFrameLength(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("maxFrameLength must be a positive integer: " + n);
        }
    }
}
