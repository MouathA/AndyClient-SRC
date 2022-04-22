package io.netty.handler.codec.http.websocketx;

import io.netty.channel.*;
import java.util.*;
import io.netty.handler.codec.*;
import java.nio.*;
import io.netty.buffer.*;
import io.netty.util.internal.logging.*;

public class WebSocket08FrameEncoder extends MessageToMessageEncoder implements WebSocketFrameEncoder
{
    private static final InternalLogger logger;
    private static final byte OPCODE_CONT = 0;
    private static final byte OPCODE_TEXT = 1;
    private static final byte OPCODE_BINARY = 2;
    private static final byte OPCODE_CLOSE = 8;
    private static final byte OPCODE_PING = 9;
    private static final byte OPCODE_PONG = 10;
    private final boolean maskPayload;
    
    public WebSocket08FrameEncoder(final boolean maskPayload) {
        this.maskPayload = maskPayload;
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final WebSocketFrame webSocketFrame, final List list) throws Exception {
        final ByteBuf content = webSocketFrame.content();
        if (!(webSocketFrame instanceof TextWebSocketFrame)) {
            if (!(webSocketFrame instanceof PingWebSocketFrame)) {
                if (!(webSocketFrame instanceof PongWebSocketFrame)) {
                    if (!(webSocketFrame instanceof CloseWebSocketFrame)) {
                        if (!(webSocketFrame instanceof BinaryWebSocketFrame)) {
                            if (!(webSocketFrame instanceof ContinuationWebSocketFrame)) {
                                throw new UnsupportedOperationException("Cannot encode frame of type: " + webSocketFrame.getClass().getName());
                            }
                        }
                    }
                }
            }
        }
        final int readableBytes = content.readableBytes();
        if (WebSocket08FrameEncoder.logger.isDebugEnabled()) {
            WebSocket08FrameEncoder.logger.debug("Encoding WebSocket Frame opCode=" + 0 + " length=" + readableBytes);
        }
        if (webSocketFrame.isFinalFragment()) {}
        final int n = 0x0 | webSocketFrame.rsv() % 8 << 4;
        if (0 == 9 && readableBytes > 125) {
            throw new TooLongFrameException("invalid payload for PING (payload length must be <= 125, was " + readableBytes);
        }
        final int n2 = this.maskPayload ? 4 : 0;
        ByteBuf byteBuf;
        int n4;
        if (readableBytes <= 125) {
            int n3 = 2 + n2;
            if (this.maskPayload) {
                n3 += readableBytes;
            }
            byteBuf = channelHandlerContext.alloc().buffer(n3);
            byteBuf.writeByte(0);
            n4 = (byte)(this.maskPayload ? (0x80 | (byte)readableBytes) : ((byte)readableBytes));
            byteBuf.writeByte(0);
        }
        else if (readableBytes <= 65535) {
            int n5 = 4 + n2;
            if (this.maskPayload) {
                n5 += readableBytes;
            }
            byteBuf = channelHandlerContext.alloc().buffer(n5);
            byteBuf.writeByte(0);
            byteBuf.writeByte(this.maskPayload ? 254 : 126);
            byteBuf.writeByte(readableBytes >>> 8 & 0xFF);
            byteBuf.writeByte(readableBytes & 0xFF);
        }
        else {
            int n6 = 10 + n2;
            if (this.maskPayload) {
                n6 += readableBytes;
            }
            byteBuf = channelHandlerContext.alloc().buffer(n6);
            byteBuf.writeByte(0);
            byteBuf.writeByte(this.maskPayload ? 255 : 127);
            byteBuf.writeLong(readableBytes);
        }
        if (this.maskPayload) {
            final byte[] array = ByteBuffer.allocate(4).putInt((int)(Math.random() * 2.147483647E9)).array();
            byteBuf.writeBytes(array);
            for (int i = content.readerIndex(); i < content.writerIndex(); ++i) {
                final byte byte1 = content.getByte(i);
                final ByteBuf byteBuf2 = byteBuf;
                final byte b = byte1;
                final byte[] array2 = array;
                final int n7 = 0;
                ++n4;
                byteBuf2.writeByte(b ^ array2[n7 % 4]);
            }
            list.add(byteBuf);
        }
        else if (byteBuf.writableBytes() >= content.readableBytes()) {
            byteBuf.writeBytes(content);
            list.add(byteBuf);
        }
        else {
            list.add(byteBuf);
            list.add(content.retain());
        }
        if (false && byteBuf != null) {
            byteBuf.release();
        }
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.encode(channelHandlerContext, (WebSocketFrame)o, list);
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(WebSocket08FrameEncoder.class);
    }
}
