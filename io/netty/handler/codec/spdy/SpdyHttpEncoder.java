package io.netty.handler.codec.spdy;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import java.util.*;
import io.netty.handler.codec.http.*;

public class SpdyHttpEncoder extends MessageToMessageEncoder
{
    private final int spdyVersion;
    private int currentStreamId;
    
    public SpdyHttpEncoder(final SpdyVersion spdyVersion) {
        if (spdyVersion == null) {
            throw new NullPointerException("version");
        }
        this.spdyVersion = spdyVersion.getVersion();
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final HttpObject httpObject, final List list) throws Exception {
        if (httpObject instanceof HttpRequest) {
            final SpdySynStreamFrame synStreamFrame = this.createSynStreamFrame((HttpMessage)httpObject);
            list.add(synStreamFrame);
            synStreamFrame.isLast();
        }
        if (httpObject instanceof HttpResponse) {
            final HttpResponse httpResponse = (HttpResponse)httpObject;
            if (httpResponse.headers().contains("X-SPDY-Associated-To-Stream-ID")) {
                final SpdySynStreamFrame synStreamFrame2 = this.createSynStreamFrame(httpResponse);
                synStreamFrame2.isLast();
                list.add(synStreamFrame2);
            }
            else {
                final SpdySynReplyFrame synReplyFrame = this.createSynReplyFrame(httpResponse);
                synReplyFrame.isLast();
                list.add(synReplyFrame);
            }
        }
        if (httpObject instanceof HttpContent) {
            final HttpContent httpContent = (HttpContent)httpObject;
            httpContent.content().retain();
            final DefaultSpdyDataFrame defaultSpdyDataFrame = new DefaultSpdyDataFrame(this.currentStreamId, httpContent.content());
            defaultSpdyDataFrame.setLast(httpContent instanceof LastHttpContent);
            if (httpContent instanceof LastHttpContent) {
                final HttpHeaders trailingHeaders = ((LastHttpContent)httpContent).trailingHeaders();
                if (trailingHeaders.isEmpty()) {
                    list.add(defaultSpdyDataFrame);
                }
                else {
                    final DefaultSpdyHeadersFrame defaultSpdyHeadersFrame = new DefaultSpdyHeadersFrame(this.currentStreamId);
                    for (final Map.Entry<String, V> entry : trailingHeaders) {
                        defaultSpdyHeadersFrame.headers().add(entry.getKey(), entry.getValue());
                    }
                    list.add(defaultSpdyHeadersFrame);
                    list.add(defaultSpdyDataFrame);
                }
            }
            else {
                list.add(defaultSpdyDataFrame);
            }
        }
    }
    
    private SpdySynStreamFrame createSynStreamFrame(final HttpMessage httpMessage) throws Exception {
        final int streamId = SpdyHttpHeaders.getStreamId(httpMessage);
        final int associatedToStreamId = SpdyHttpHeaders.getAssociatedToStreamId(httpMessage);
        final byte priority = SpdyHttpHeaders.getPriority(httpMessage);
        final String url = SpdyHttpHeaders.getUrl(httpMessage);
        String scheme = SpdyHttpHeaders.getScheme(httpMessage);
        SpdyHttpHeaders.removeStreamId(httpMessage);
        SpdyHttpHeaders.removeAssociatedToStreamId(httpMessage);
        SpdyHttpHeaders.removePriority(httpMessage);
        SpdyHttpHeaders.removeUrl(httpMessage);
        SpdyHttpHeaders.removeScheme(httpMessage);
        httpMessage.headers().remove("Connection");
        httpMessage.headers().remove("Keep-Alive");
        httpMessage.headers().remove("Proxy-Connection");
        httpMessage.headers().remove("Transfer-Encoding");
        final DefaultSpdySynStreamFrame defaultSpdySynStreamFrame = new DefaultSpdySynStreamFrame(streamId, associatedToStreamId, priority);
        if (httpMessage instanceof FullHttpRequest) {
            final HttpRequest httpRequest = (HttpRequest)httpMessage;
            SpdyHeaders.setMethod(this.spdyVersion, defaultSpdySynStreamFrame, httpRequest.getMethod());
            SpdyHeaders.setUrl(this.spdyVersion, defaultSpdySynStreamFrame, httpRequest.getUri());
            SpdyHeaders.setVersion(this.spdyVersion, defaultSpdySynStreamFrame, httpMessage.getProtocolVersion());
        }
        if (httpMessage instanceof HttpResponse) {
            SpdyHeaders.setStatus(this.spdyVersion, defaultSpdySynStreamFrame, ((HttpResponse)httpMessage).getStatus());
            SpdyHeaders.setUrl(this.spdyVersion, defaultSpdySynStreamFrame, url);
            SpdyHeaders.setVersion(this.spdyVersion, defaultSpdySynStreamFrame, httpMessage.getProtocolVersion());
            defaultSpdySynStreamFrame.setUnidirectional(true);
        }
        if (this.spdyVersion >= 3) {
            final String host = HttpHeaders.getHost(httpMessage);
            httpMessage.headers().remove("Host");
            SpdyHeaders.setHost(defaultSpdySynStreamFrame, host);
        }
        if (scheme == null) {
            scheme = "https";
        }
        SpdyHeaders.setScheme(this.spdyVersion, defaultSpdySynStreamFrame, scheme);
        for (final Map.Entry<String, V> entry : httpMessage.headers()) {
            defaultSpdySynStreamFrame.headers().add(entry.getKey(), entry.getValue());
        }
        this.currentStreamId = defaultSpdySynStreamFrame.streamId();
        defaultSpdySynStreamFrame.setLast(isLast(httpMessage));
        return defaultSpdySynStreamFrame;
    }
    
    private SpdySynReplyFrame createSynReplyFrame(final HttpResponse httpResponse) throws Exception {
        final int streamId = SpdyHttpHeaders.getStreamId(httpResponse);
        SpdyHttpHeaders.removeStreamId(httpResponse);
        httpResponse.headers().remove("Connection");
        httpResponse.headers().remove("Keep-Alive");
        httpResponse.headers().remove("Proxy-Connection");
        httpResponse.headers().remove("Transfer-Encoding");
        final DefaultSpdySynReplyFrame defaultSpdySynReplyFrame = new DefaultSpdySynReplyFrame(streamId);
        SpdyHeaders.setStatus(this.spdyVersion, defaultSpdySynReplyFrame, httpResponse.getStatus());
        SpdyHeaders.setVersion(this.spdyVersion, defaultSpdySynReplyFrame, httpResponse.getProtocolVersion());
        for (final Map.Entry<String, V> entry : httpResponse.headers()) {
            defaultSpdySynReplyFrame.headers().add(entry.getKey(), entry.getValue());
        }
        this.currentStreamId = streamId;
        defaultSpdySynReplyFrame.setLast(isLast(httpResponse));
        return defaultSpdySynReplyFrame;
    }
    
    private static boolean isLast(final HttpMessage httpMessage) {
        if (httpMessage instanceof FullHttpMessage) {
            final FullHttpMessage fullHttpMessage = (FullHttpMessage)httpMessage;
            if (fullHttpMessage.trailingHeaders().isEmpty() && !fullHttpMessage.content().isReadable()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.encode(channelHandlerContext, (HttpObject)o, list);
    }
}
