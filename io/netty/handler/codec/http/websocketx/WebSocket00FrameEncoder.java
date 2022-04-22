package io.netty.handler.codec.http.websocketx;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import java.util.*;
import io.netty.buffer.*;

@ChannelHandler.Sharable
public class WebSocket00FrameEncoder extends MessageToMessageEncoder implements WebSocketFrameEncoder
{
    private static final ByteBuf _0X00;
    private static final ByteBuf _0XFF;
    private static final ByteBuf _0XFF_0X00;
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final WebSocketFrame webSocketFrame, final List list) throws Exception {
        if (webSocketFrame instanceof TextWebSocketFrame) {
            final ByteBuf content = webSocketFrame.content();
            list.add(WebSocket00FrameEncoder._0X00.duplicate());
            list.add(content.retain());
            list.add(WebSocket00FrameEncoder._0XFF.duplicate());
        }
        else if (webSocketFrame instanceof CloseWebSocketFrame) {
            list.add(WebSocket00FrameEncoder._0XFF_0X00.duplicate());
        }
        else {
            final ByteBuf content2 = webSocketFrame.content();
            final int readableBytes = content2.readableBytes();
            final ByteBuf buffer = channelHandlerContext.alloc().buffer(5);
            buffer.writeByte(-128);
            final int n = readableBytes >>> 28 & 0x7F;
            final int n2 = readableBytes >>> 14 & 0x7F;
            final int n3 = readableBytes >>> 7 & 0x7F;
            final int n4 = readableBytes & 0x7F;
            if (n == 0) {
                if (n2 == 0) {
                    if (n3 == 0) {
                        buffer.writeByte(n4);
                    }
                    else {
                        buffer.writeByte(n3 | 0x80);
                        buffer.writeByte(n4);
                    }
                }
                else {
                    buffer.writeByte(n2 | 0x80);
                    buffer.writeByte(n3 | 0x80);
                    buffer.writeByte(n4);
                }
            }
            else {
                buffer.writeByte(n | 0x80);
                buffer.writeByte(n2 | 0x80);
                buffer.writeByte(n3 | 0x80);
                buffer.writeByte(n4);
            }
            list.add(buffer);
            list.add(content2.retain());
            if (false) {
                buffer.release();
            }
        }
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.encode(channelHandlerContext, (WebSocketFrame)o, list);
    }
    
    static {
        _0X00 = Unpooled.unreleasableBuffer(Unpooled.directBuffer(1, 1).writeByte(0));
        _0XFF = Unpooled.unreleasableBuffer(Unpooled.directBuffer(1, 1).writeByte(-1));
        _0XFF_0X00 = Unpooled.unreleasableBuffer(Unpooled.directBuffer(2, 2).writeByte(-1).writeByte(0));
    }
}
