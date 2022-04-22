package io.netty.handler.codec.http.websocketx;

import java.net.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;
import io.netty.handler.codec.http.*;

public abstract class WebSocketClientHandshaker
{
    private final URI uri;
    private final WebSocketVersion version;
    private boolean handshakeComplete;
    private final String expectedSubprotocol;
    private String actualSubprotocol;
    protected final HttpHeaders customHeaders;
    private final int maxFramePayloadLength;
    
    protected WebSocketClientHandshaker(final URI uri, final WebSocketVersion version, final String expectedSubprotocol, final HttpHeaders customHeaders, final int maxFramePayloadLength) {
        this.uri = uri;
        this.version = version;
        this.expectedSubprotocol = expectedSubprotocol;
        this.customHeaders = customHeaders;
        this.maxFramePayloadLength = maxFramePayloadLength;
    }
    
    public URI uri() {
        return this.uri;
    }
    
    public WebSocketVersion version() {
        return this.version;
    }
    
    public int maxFramePayloadLength() {
        return this.maxFramePayloadLength;
    }
    
    public boolean isHandshakeComplete() {
        return this.handshakeComplete;
    }
    
    private void setHandshakeComplete() {
        this.handshakeComplete = true;
    }
    
    public String expectedSubprotocol() {
        return this.expectedSubprotocol;
    }
    
    public String actualSubprotocol() {
        return this.actualSubprotocol;
    }
    
    private void setActualSubprotocol(final String actualSubprotocol) {
        this.actualSubprotocol = actualSubprotocol;
    }
    
    public ChannelFuture handshake(final Channel channel) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        return this.handshake(channel, channel.newPromise());
    }
    
    public final ChannelFuture handshake(final Channel channel, final ChannelPromise channelPromise) {
        final FullHttpRequest handshakeRequest = this.newHandshakeRequest();
        if (channel.pipeline().get(HttpResponseDecoder.class) == null && channel.pipeline().get(HttpClientCodec.class) == null) {
            channelPromise.setFailure((Throwable)new IllegalStateException("ChannelPipeline does not contain a HttpResponseDecoder or HttpClientCodec"));
            return channelPromise;
        }
        channel.writeAndFlush(handshakeRequest).addListener((GenericFutureListener)new ChannelFutureListener(channelPromise) {
            final ChannelPromise val$promise;
            final WebSocketClientHandshaker this$0;
            
            public void operationComplete(final ChannelFuture channelFuture) {
                if (channelFuture.isSuccess()) {
                    final ChannelPipeline pipeline = channelFuture.channel().pipeline();
                    ChannelHandlerContext channelHandlerContext = pipeline.context(HttpRequestEncoder.class);
                    if (channelHandlerContext == null) {
                        channelHandlerContext = pipeline.context(HttpClientCodec.class);
                    }
                    if (channelHandlerContext == null) {
                        this.val$promise.setFailure((Throwable)new IllegalStateException("ChannelPipeline does not contain a HttpRequestEncoder or HttpClientCodec"));
                        return;
                    }
                    pipeline.addAfter(channelHandlerContext.name(), "ws-encoder", this.this$0.newWebSocketEncoder());
                    this.val$promise.setSuccess();
                }
                else {
                    this.val$promise.setFailure(channelFuture.cause());
                }
            }
            
            @Override
            public void operationComplete(final Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        });
        return channelPromise;
    }
    
    protected abstract FullHttpRequest newHandshakeRequest();
    
    public final void finishHandshake(final Channel channel, final FullHttpResponse fullHttpResponse) {
        this.verify(fullHttpResponse);
        this.setActualSubprotocol(fullHttpResponse.headers().get("Sec-WebSocket-Protocol"));
        this.setHandshakeComplete();
        final ChannelPipeline pipeline = channel.pipeline();
        final HttpContentDecompressor httpContentDecompressor = (HttpContentDecompressor)pipeline.get(HttpContentDecompressor.class);
        if (httpContentDecompressor != null) {
            pipeline.remove(httpContentDecompressor);
        }
        final ChannelHandlerContext context = pipeline.context(HttpResponseDecoder.class);
        if (context == null) {
            final ChannelHandlerContext context2 = pipeline.context(HttpClientCodec.class);
            if (context2 == null) {
                throw new IllegalStateException("ChannelPipeline does not contain a HttpRequestEncoder or HttpClientCodec");
            }
            pipeline.replace(context2.name(), "ws-decoder", this.newWebsocketDecoder());
        }
        else {
            if (pipeline.get(HttpRequestEncoder.class) != null) {
                pipeline.remove(HttpRequestEncoder.class);
            }
            pipeline.replace(context.name(), "ws-decoder", this.newWebsocketDecoder());
        }
    }
    
    protected abstract void verify(final FullHttpResponse p0);
    
    protected abstract WebSocketFrameDecoder newWebsocketDecoder();
    
    protected abstract WebSocketFrameEncoder newWebSocketEncoder();
    
    public ChannelFuture close(final Channel channel, final CloseWebSocketFrame closeWebSocketFrame) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        return this.close(channel, closeWebSocketFrame, channel.newPromise());
    }
    
    public ChannelFuture close(final Channel channel, final CloseWebSocketFrame closeWebSocketFrame, final ChannelPromise channelPromise) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        return channel.writeAndFlush(closeWebSocketFrame, channelPromise);
    }
}
