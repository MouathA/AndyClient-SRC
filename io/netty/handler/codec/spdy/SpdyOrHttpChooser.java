package io.netty.handler.codec.spdy;

import io.netty.handler.codec.*;
import javax.net.ssl.*;
import io.netty.util.internal.*;
import io.netty.buffer.*;
import java.util.*;
import io.netty.handler.codec.http.*;
import io.netty.channel.*;

public abstract class SpdyOrHttpChooser extends ByteToMessageDecoder
{
    private final int maxSpdyContentLength;
    private final int maxHttpContentLength;
    
    protected SpdyOrHttpChooser(final int maxSpdyContentLength, final int maxHttpContentLength) {
        this.maxSpdyContentLength = maxSpdyContentLength;
        this.maxHttpContentLength = maxHttpContentLength;
    }
    
    protected SelectedProtocol getProtocol(final SSLEngine sslEngine) {
        final String[] split = StringUtil.split(sslEngine.getSession().getProtocol(), ':');
        if (split.length < 2) {
            return SelectedProtocol.HTTP_1_1;
        }
        return SelectedProtocol.protocol(split[1]);
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        if (channelHandlerContext == null) {
            channelHandlerContext.pipeline().remove(this);
        }
    }
    
    protected void addSpdyHandlers(final ChannelHandlerContext channelHandlerContext, final SpdyVersion spdyVersion) {
        final ChannelPipeline pipeline = channelHandlerContext.pipeline();
        pipeline.addLast("spdyFrameCodec", new SpdyFrameCodec(spdyVersion));
        pipeline.addLast("spdySessionHandler", new SpdySessionHandler(spdyVersion, true));
        pipeline.addLast("spdyHttpEncoder", new SpdyHttpEncoder(spdyVersion));
        pipeline.addLast("spdyHttpDecoder", new SpdyHttpDecoder(spdyVersion, this.maxSpdyContentLength));
        pipeline.addLast("spdyStreamIdHandler", new SpdyHttpResponseStreamIdHandler());
        pipeline.addLast("httpRequestHandler", this.createHttpRequestHandlerForSpdy());
    }
    
    protected void addHttpHandlers(final ChannelHandlerContext channelHandlerContext) {
        final ChannelPipeline pipeline = channelHandlerContext.pipeline();
        pipeline.addLast("httpRequestDecoder", new HttpRequestDecoder());
        pipeline.addLast("httpResponseEncoder", new HttpResponseEncoder());
        pipeline.addLast("httpChunkAggregator", new HttpObjectAggregator(this.maxHttpContentLength));
        pipeline.addLast("httpRequestHandler", this.createHttpRequestHandlerForHttp());
    }
    
    protected abstract ChannelInboundHandler createHttpRequestHandlerForHttp();
    
    protected ChannelInboundHandler createHttpRequestHandlerForSpdy() {
        return this.createHttpRequestHandlerForHttp();
    }
    
    public enum SelectedProtocol
    {
        SPDY_3_1("SPDY_3_1", 0, "spdy/3.1"), 
        HTTP_1_1("HTTP_1_1", 1, "http/1.1"), 
        HTTP_1_0("HTTP_1_0", 2, "http/1.0"), 
        UNKNOWN("UNKNOWN", 3, "Unknown");
        
        private final String name;
        private static final SelectedProtocol[] $VALUES;
        
        private SelectedProtocol(final String s, final int n, final String name) {
            this.name = name;
        }
        
        public String protocolName() {
            return this.name;
        }
        
        public static SelectedProtocol protocol(final String s) {
            final SelectedProtocol[] values = values();
            while (0 < values.length) {
                final SelectedProtocol selectedProtocol = values[0];
                if (selectedProtocol.protocolName().equals(s)) {
                    return selectedProtocol;
                }
                int n = 0;
                ++n;
            }
            return SelectedProtocol.UNKNOWN;
        }
        
        static {
            $VALUES = new SelectedProtocol[] { SelectedProtocol.SPDY_3_1, SelectedProtocol.HTTP_1_1, SelectedProtocol.HTTP_1_0, SelectedProtocol.UNKNOWN };
        }
    }
}
