package io.netty.handler.codec;

import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;

public class LineBasedFrameDecoder extends ByteToMessageDecoder
{
    private final int maxLength;
    private final boolean failFast;
    private final boolean stripDelimiter;
    private boolean discarding;
    private int discardedBytes;
    
    public LineBasedFrameDecoder(final int n) {
        this(n, true, false);
    }
    
    public LineBasedFrameDecoder(final int maxLength, final boolean stripDelimiter, final boolean failFast) {
        this.maxLength = maxLength;
        this.failFast = failFast;
        this.stripDelimiter = stripDelimiter;
    }
    
    @Override
    protected final void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        final Object decode = this.decode(channelHandlerContext, byteBuf);
        if (decode != null) {
            list.add(decode);
        }
    }
    
    protected Object decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws Exception {
        final int endOfLine = findEndOfLine(byteBuf);
        if (this.discarding) {
            if (endOfLine >= 0) {
                final int n = this.discardedBytes + endOfLine - byteBuf.readerIndex();
                byteBuf.readerIndex(endOfLine + ((byteBuf.getByte(endOfLine) == 13) ? 2 : 1));
                this.discardedBytes = 0;
                this.discarding = false;
                if (!this.failFast) {
                    this.fail(channelHandlerContext, n);
                }
            }
            else {
                this.discardedBytes = byteBuf.readableBytes();
                byteBuf.readerIndex(byteBuf.writerIndex());
            }
            return null;
        }
        if (endOfLine < 0) {
            final int readableBytes = byteBuf.readableBytes();
            if (readableBytes > this.maxLength) {
                this.discardedBytes = readableBytes;
                byteBuf.readerIndex(byteBuf.writerIndex());
                this.discarding = true;
                if (this.failFast) {
                    this.fail(channelHandlerContext, "over " + this.discardedBytes);
                }
            }
            return null;
        }
        final int n2 = endOfLine - byteBuf.readerIndex();
        final int n3 = (byteBuf.getByte(endOfLine) == 13) ? 2 : 1;
        if (n2 > this.maxLength) {
            byteBuf.readerIndex(endOfLine + n3);
            this.fail(channelHandlerContext, n2);
            return null;
        }
        ByteBuf byteBuf2;
        if (this.stripDelimiter) {
            byteBuf2 = byteBuf.readSlice(n2);
            byteBuf.skipBytes(n3);
        }
        else {
            byteBuf2 = byteBuf.readSlice(n2 + n3);
        }
        return byteBuf2.retain();
    }
    
    private void fail(final ChannelHandlerContext channelHandlerContext, final int n) {
        this.fail(channelHandlerContext, String.valueOf(n));
    }
    
    private void fail(final ChannelHandlerContext channelHandlerContext, final String s) {
        channelHandlerContext.fireExceptionCaught(new TooLongFrameException("frame length (" + s + ") exceeds the allowed maximum (" + this.maxLength + ')'));
    }
    
    private static int findEndOfLine(final ByteBuf byteBuf) {
        for (int writerIndex = byteBuf.writerIndex(), i = byteBuf.readerIndex(); i < writerIndex; ++i) {
            final byte byte1 = byteBuf.getByte(i);
            if (byte1 == 10) {
                return i;
            }
            if (byte1 == 13 && i < writerIndex - 1 && byteBuf.getByte(i + 1) == 10) {
                return i;
            }
        }
        return -1;
    }
}
