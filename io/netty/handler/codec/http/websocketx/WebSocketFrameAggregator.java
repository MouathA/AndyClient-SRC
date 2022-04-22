package io.netty.handler.codec.http.websocketx;

import io.netty.channel.*;
import java.util.*;
import io.netty.buffer.*;
import io.netty.handler.codec.*;

public class WebSocketFrameAggregator extends MessageToMessageDecoder
{
    private final int maxFrameSize;
    private WebSocketFrame currentFrame;
    private boolean tooLongFrameFound;
    
    public WebSocketFrameAggregator(final int maxFrameSize) {
        if (maxFrameSize < 1) {
            throw new IllegalArgumentException("maxFrameSize must be > 0");
        }
        this.maxFrameSize = maxFrameSize;
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final WebSocketFrame webSocketFrame, final List list) throws Exception {
        if (this.currentFrame == null) {
            this.tooLongFrameFound = false;
            if (webSocketFrame.isFinalFragment()) {
                list.add(webSocketFrame.retain());
                return;
            }
            final CompositeByteBuf addComponent = channelHandlerContext.alloc().compositeBuffer().addComponent(webSocketFrame.content().retain());
            addComponent.writerIndex(addComponent.writerIndex() + webSocketFrame.content().readableBytes());
            if (webSocketFrame instanceof TextWebSocketFrame) {
                this.currentFrame = new TextWebSocketFrame(true, webSocketFrame.rsv(), addComponent);
            }
            else {
                if (!(webSocketFrame instanceof BinaryWebSocketFrame)) {
                    addComponent.release();
                    throw new IllegalStateException("WebSocket frame was not of type TextWebSocketFrame or BinaryWebSocketFrame");
                }
                this.currentFrame = new BinaryWebSocketFrame(true, webSocketFrame.rsv(), addComponent);
            }
        }
        else {
            if (!(webSocketFrame instanceof ContinuationWebSocketFrame)) {
                list.add(webSocketFrame.retain());
                return;
            }
            if (this.tooLongFrameFound) {
                if (webSocketFrame.isFinalFragment()) {
                    this.currentFrame = null;
                }
                return;
            }
            final CompositeByteBuf compositeByteBuf = (CompositeByteBuf)this.currentFrame.content();
            if (compositeByteBuf.readableBytes() > this.maxFrameSize - webSocketFrame.content().readableBytes()) {
                this.currentFrame.release();
                this.tooLongFrameFound = true;
                throw new TooLongFrameException("WebSocketFrame length exceeded " + compositeByteBuf + " bytes.");
            }
            compositeByteBuf.addComponent(webSocketFrame.content().retain());
            compositeByteBuf.writerIndex(compositeByteBuf.writerIndex() + webSocketFrame.content().readableBytes());
            if (webSocketFrame.isFinalFragment()) {
                final WebSocketFrame currentFrame = this.currentFrame;
                this.currentFrame = null;
                list.add(currentFrame);
            }
        }
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelInactive(channelHandlerContext);
        if (this.currentFrame != null) {
            this.currentFrame.release();
            this.currentFrame = null;
        }
    }
    
    @Override
    public void handlerRemoved(final ChannelHandlerContext channelHandlerContext) throws Exception {
        super.handlerRemoved(channelHandlerContext);
        if (this.currentFrame != null) {
            this.currentFrame.release();
            this.currentFrame = null;
        }
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.decode(channelHandlerContext, (WebSocketFrame)o, list);
    }
}
