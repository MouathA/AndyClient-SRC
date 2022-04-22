package io.netty.handler.codec.http.websocketx;

import io.netty.util.internal.*;
import java.util.*;
import io.netty.util.concurrent.*;
import io.netty.handler.codec.http.*;
import io.netty.channel.*;
import io.netty.util.internal.logging.*;

public abstract class WebSocketServerHandshaker
{
    protected static final InternalLogger logger;
    private final String uri;
    private final String[] subprotocols;
    private final WebSocketVersion version;
    private final int maxFramePayloadLength;
    private String selectedSubprotocol;
    public static final String SUB_PROTOCOL_WILDCARD = "*";
    
    protected WebSocketServerHandshaker(final WebSocketVersion version, final String uri, final String s, final int maxFramePayloadLength) {
        this.version = version;
        this.uri = uri;
        if (s != null) {
            final String[] split = StringUtil.split(s, ',');
            while (0 < split.length) {
                split[0] = split[0].trim();
                int n = 0;
                ++n;
            }
            this.subprotocols = split;
        }
        else {
            this.subprotocols = EmptyArrays.EMPTY_STRINGS;
        }
        this.maxFramePayloadLength = maxFramePayloadLength;
    }
    
    public String uri() {
        return this.uri;
    }
    
    public Set subprotocols() {
        final LinkedHashSet<Object> set = new LinkedHashSet<Object>();
        Collections.addAll(set, this.subprotocols);
        return set;
    }
    
    public WebSocketVersion version() {
        return this.version;
    }
    
    public int maxFramePayloadLength() {
        return this.maxFramePayloadLength;
    }
    
    public ChannelFuture handshake(final Channel channel, final FullHttpRequest fullHttpRequest) {
        return this.handshake(channel, fullHttpRequest, null, channel.newPromise());
    }
    
    public final ChannelFuture handshake(final Channel channel, final FullHttpRequest fullHttpRequest, final HttpHeaders httpHeaders, final ChannelPromise channelPromise) {
        if (WebSocketServerHandshaker.logger.isDebugEnabled()) {
            WebSocketServerHandshaker.logger.debug("{} WebSocket version {} server handshake", channel, this.version());
        }
        final FullHttpResponse handshakeResponse = this.newHandshakeResponse(fullHttpRequest, httpHeaders);
        final ChannelPipeline pipeline = channel.pipeline();
        if (pipeline.get(HttpObjectAggregator.class) != null) {
            pipeline.remove(HttpObjectAggregator.class);
        }
        if (pipeline.get(HttpContentCompressor.class) != null) {
            pipeline.remove(HttpContentCompressor.class);
        }
        final ChannelHandlerContext context = pipeline.context(HttpRequestDecoder.class);
        String s;
        if (context == null) {
            final ChannelHandlerContext context2 = pipeline.context(HttpServerCodec.class);
            if (context2 == null) {
                channelPromise.setFailure((Throwable)new IllegalStateException("No HttpDecoder and no HttpServerCodec in the pipeline"));
                return channelPromise;
            }
            pipeline.addBefore(context2.name(), "wsdecoder", this.newWebsocketDecoder());
            pipeline.addBefore(context2.name(), "wsencoder", this.newWebSocketEncoder());
            s = context2.name();
        }
        else {
            pipeline.replace(context.name(), "wsdecoder", this.newWebsocketDecoder());
            s = pipeline.context(HttpResponseEncoder.class).name();
            pipeline.addBefore(s, "wsencoder", this.newWebSocketEncoder());
        }
        channel.writeAndFlush(handshakeResponse).addListener((GenericFutureListener)new ChannelFutureListener(s, channelPromise) {
            final String val$encoderName;
            final ChannelPromise val$promise;
            final WebSocketServerHandshaker this$0;
            
            public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    channelFuture.channel().pipeline().remove(this.val$encoderName);
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
    
    protected abstract FullHttpResponse newHandshakeResponse(final FullHttpRequest p0, final HttpHeaders p1);
    
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
        return channel.writeAndFlush(closeWebSocketFrame, channelPromise).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
    }
    
    protected String selectSubprotocol(final String s) {
        if (s == null || this.subprotocols.length == 0) {
            return null;
        }
        final String[] split = StringUtil.split(s, ',');
        while (0 < split.length) {
            final String trim = split[0].trim();
            final String[] subprotocols = this.subprotocols;
            while (0 < subprotocols.length) {
                final String s2 = subprotocols[0];
                if ("*".equals(s2) || trim.equals(s2)) {
                    return this.selectedSubprotocol = trim;
                }
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return null;
    }
    
    public String selectedSubprotocol() {
        return this.selectedSubprotocol;
    }
    
    protected abstract WebSocketFrameDecoder newWebsocketDecoder();
    
    protected abstract WebSocketFrameEncoder newWebSocketEncoder();
    
    static {
        logger = InternalLoggerFactory.getInstance(WebSocketServerHandshaker.class);
    }
}
