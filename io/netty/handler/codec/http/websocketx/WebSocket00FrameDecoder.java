package io.netty.handler.codec.http.websocketx;

import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import io.netty.handler.codec.*;

public class WebSocket00FrameDecoder extends ReplayingDecoder implements WebSocketFrameDecoder
{
    static final int DEFAULT_MAX_FRAME_SIZE = 16384;
    private final long maxFrameSize;
    private boolean receivedClosingHandshake;
    
    public WebSocket00FrameDecoder() {
        this(16384);
    }
    
    public WebSocket00FrameDecoder(final int n) {
        this.maxFrameSize = n;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        if (this.receivedClosingHandshake) {
            byteBuf.skipBytes(this.actualReadableBytes());
            return;
        }
        final byte byte1 = byteBuf.readByte();
        WebSocketFrame webSocketFrame;
        if ((byte1 & 0x80) == 0x80) {
            webSocketFrame = this.decodeBinaryFrame(channelHandlerContext, byte1, byteBuf);
        }
        else {
            webSocketFrame = this.decodeTextFrame(channelHandlerContext, byteBuf);
        }
        if (webSocketFrame != null) {
            list.add(webSocketFrame);
        }
    }
    
    private WebSocketFrame decodeBinaryFrame(final ChannelHandlerContext channelHandlerContext, final byte b, final ByteBuf byteBuf) {
        long n = 0L;
        byte byte1;
        do {
            byte1 = byteBuf.readByte();
            n = (n << 7 | (long)(byte1 & 0x7F));
            if (n > this.maxFrameSize) {
                throw new TooLongFrameException();
            }
            int n2 = 0;
            ++n2;
            if (0 > 8) {
                throw new TooLongFrameException();
            }
        } while ((byte1 & 0x80) == 0x80);
        if (b == -1 && n == 0L) {
            this.receivedClosingHandshake = true;
            return new CloseWebSocketFrame();
        }
        final ByteBuf buffer = channelHandlerContext.alloc().buffer((int)n);
        byteBuf.readBytes(buffer);
        return new BinaryWebSocketFrame(buffer);
    }
    
    private WebSocketFrame decodeTextFrame(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) {
        final int readerIndex = byteBuf.readerIndex();
        final int actualReadableBytes = this.actualReadableBytes();
        final int index = byteBuf.indexOf(readerIndex, readerIndex + actualReadableBytes, (byte)(-1));
        if (index == -1) {
            if (actualReadableBytes > this.maxFrameSize) {
                throw new TooLongFrameException();
            }
            return null;
        }
        else {
            final int n = index - readerIndex;
            if (n > this.maxFrameSize) {
                throw new TooLongFrameException();
            }
            final ByteBuf buffer = channelHandlerContext.alloc().buffer(n);
            byteBuf.readBytes(buffer);
            byteBuf.skipBytes(1);
            if (buffer.indexOf(buffer.readerIndex(), buffer.writerIndex(), (byte)(-1)) >= 0) {
                throw new IllegalArgumentException("a text frame should not contain 0xFF.");
            }
            return new TextWebSocketFrame(buffer);
        }
    }
}
