package io.netty.handler.codec;

import io.netty.channel.*;
import io.netty.buffer.*;

@ChannelHandler.Sharable
public class LengthFieldPrepender extends MessageToByteEncoder
{
    private final int lengthFieldLength;
    private final boolean lengthIncludesLengthFieldLength;
    private final int lengthAdjustment;
    
    public LengthFieldPrepender(final int n) {
        this(n, false);
    }
    
    public LengthFieldPrepender(final int n, final boolean b) {
        this(n, 0, b);
    }
    
    public LengthFieldPrepender(final int n, final int n2) {
        this(n, n2, false);
    }
    
    public LengthFieldPrepender(final int lengthFieldLength, final int lengthAdjustment, final boolean lengthIncludesLengthFieldLength) {
        if (lengthFieldLength != 1 && lengthFieldLength != 2 && lengthFieldLength != 3 && lengthFieldLength != 4 && lengthFieldLength != 8) {
            throw new IllegalArgumentException("lengthFieldLength must be either 1, 2, 3, 4, or 8: " + lengthFieldLength);
        }
        this.lengthFieldLength = lengthFieldLength;
        this.lengthIncludesLengthFieldLength = lengthIncludesLengthFieldLength;
        this.lengthAdjustment = lengthAdjustment;
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final ByteBuf byteBuf2) throws Exception {
        int n = byteBuf.readableBytes() + this.lengthAdjustment;
        if (this.lengthIncludesLengthFieldLength) {
            n += this.lengthFieldLength;
        }
        if (n < 0) {
            throw new IllegalArgumentException("Adjusted frame length (" + n + ") is less than zero");
        }
        switch (this.lengthFieldLength) {
            case 1: {
                if (n >= 256) {
                    throw new IllegalArgumentException("length does not fit into a byte: " + n);
                }
                byteBuf2.writeByte((byte)n);
                break;
            }
            case 2: {
                if (n >= 65536) {
                    throw new IllegalArgumentException("length does not fit into a short integer: " + n);
                }
                byteBuf2.writeShort((short)n);
                break;
            }
            case 3: {
                if (n >= 16777216) {
                    throw new IllegalArgumentException("length does not fit into a medium integer: " + n);
                }
                byteBuf2.writeMedium(n);
                break;
            }
            case 4: {
                byteBuf2.writeInt(n);
                break;
            }
            case 8: {
                byteBuf2.writeLong(n);
                break;
            }
            default: {
                throw new Error("should not reach here");
            }
        }
        byteBuf2.writeBytes(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)o, byteBuf);
    }
}
