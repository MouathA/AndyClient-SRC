package io.netty.handler.codec.spdy;

import io.netty.channel.*;
import io.netty.handler.codec.*;
import java.util.*;
import io.netty.buffer.*;
import io.netty.handler.codec.http.*;

public class SpdyHttpDecoder extends MessageToMessageDecoder
{
    private final boolean validateHeaders;
    private final int spdyVersion;
    private final int maxContentLength;
    private final Map messageMap;
    
    public SpdyHttpDecoder(final SpdyVersion spdyVersion, final int n) {
        this(spdyVersion, n, new HashMap(), true);
    }
    
    public SpdyHttpDecoder(final SpdyVersion spdyVersion, final int n, final boolean b) {
        this(spdyVersion, n, new HashMap(), b);
    }
    
    protected SpdyHttpDecoder(final SpdyVersion spdyVersion, final int n, final Map map) {
        this(spdyVersion, n, map, true);
    }
    
    protected SpdyHttpDecoder(final SpdyVersion spdyVersion, final int maxContentLength, final Map messageMap, final boolean validateHeaders) {
        if (spdyVersion == null) {
            throw new NullPointerException("version");
        }
        if (maxContentLength <= 0) {
            throw new IllegalArgumentException("maxContentLength must be a positive integer: " + maxContentLength);
        }
        this.spdyVersion = spdyVersion.getVersion();
        this.maxContentLength = maxContentLength;
        this.messageMap = messageMap;
        this.validateHeaders = validateHeaders;
    }
    
    protected FullHttpMessage putMessage(final int n, final FullHttpMessage fullHttpMessage) {
        return this.messageMap.put(n, fullHttpMessage);
    }
    
    protected FullHttpMessage getMessage(final int n) {
        return this.messageMap.get(n);
    }
    
    protected FullHttpMessage removeMessage(final int n) {
        return this.messageMap.remove(n);
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final SpdyFrame spdyFrame, final List list) throws Exception {
        if (spdyFrame instanceof SpdySynStreamFrame) {
            final SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)spdyFrame;
            final int streamId = spdySynStreamFrame.streamId();
            if (SpdyCodecUtil.isServerId(streamId)) {
                final int associatedStreamId = spdySynStreamFrame.associatedStreamId();
                if (associatedStreamId == 0) {
                    channelHandlerContext.writeAndFlush(new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.INVALID_STREAM));
                    return;
                }
                final String url = SpdyHeaders.getUrl(this.spdyVersion, spdySynStreamFrame);
                if (url == null) {
                    channelHandlerContext.writeAndFlush(new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.PROTOCOL_ERROR));
                    return;
                }
                if (spdySynStreamFrame.isTruncated()) {
                    channelHandlerContext.writeAndFlush(new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.INTERNAL_ERROR));
                    return;
                }
                final FullHttpResponse httpResponse = createHttpResponse(channelHandlerContext, this.spdyVersion, spdySynStreamFrame, this.validateHeaders);
                SpdyHttpHeaders.setStreamId(httpResponse, streamId);
                SpdyHttpHeaders.setAssociatedToStreamId(httpResponse, associatedStreamId);
                SpdyHttpHeaders.setPriority(httpResponse, spdySynStreamFrame.priority());
                SpdyHttpHeaders.setUrl(httpResponse, url);
                if (spdySynStreamFrame.isLast()) {
                    HttpHeaders.setContentLength(httpResponse, 0L);
                    list.add(httpResponse);
                }
                else {
                    this.putMessage(streamId, httpResponse);
                }
            }
            else {
                if (spdySynStreamFrame.isTruncated()) {
                    final DefaultSpdySynReplyFrame defaultSpdySynReplyFrame = new DefaultSpdySynReplyFrame(streamId);
                    defaultSpdySynReplyFrame.setLast(true);
                    SpdyHeaders.setStatus(this.spdyVersion, defaultSpdySynReplyFrame, HttpResponseStatus.REQUEST_HEADER_FIELDS_TOO_LARGE);
                    SpdyHeaders.setVersion(this.spdyVersion, defaultSpdySynReplyFrame, HttpVersion.HTTP_1_0);
                    channelHandlerContext.writeAndFlush(defaultSpdySynReplyFrame);
                    return;
                }
                final FullHttpRequest httpRequest = createHttpRequest(this.spdyVersion, spdySynStreamFrame);
                SpdyHttpHeaders.setStreamId(httpRequest, streamId);
                if (spdySynStreamFrame.isLast()) {
                    list.add(httpRequest);
                }
                else {
                    this.putMessage(streamId, httpRequest);
                }
            }
        }
        else if (spdyFrame instanceof SpdySynReplyFrame) {
            final SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)spdyFrame;
            final int streamId2 = spdySynReplyFrame.streamId();
            if (spdySynReplyFrame.isTruncated()) {
                channelHandlerContext.writeAndFlush(new DefaultSpdyRstStreamFrame(streamId2, SpdyStreamStatus.INTERNAL_ERROR));
                return;
            }
            final FullHttpResponse httpResponse2 = createHttpResponse(channelHandlerContext, this.spdyVersion, spdySynReplyFrame, this.validateHeaders);
            SpdyHttpHeaders.setStreamId(httpResponse2, streamId2);
            if (spdySynReplyFrame.isLast()) {
                HttpHeaders.setContentLength(httpResponse2, 0L);
                list.add(httpResponse2);
            }
            else {
                this.putMessage(streamId2, httpResponse2);
            }
        }
        else if (spdyFrame instanceof SpdyHeadersFrame) {
            final SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)spdyFrame;
            final int streamId3 = spdyHeadersFrame.streamId();
            final FullHttpMessage message = this.getMessage(streamId3);
            if (message == null) {
                return;
            }
            if (!spdyHeadersFrame.isTruncated()) {
                for (final Map.Entry<String, V> entry : spdyHeadersFrame.headers()) {
                    message.headers().add(entry.getKey(), entry.getValue());
                }
            }
            if (spdyHeadersFrame.isLast()) {
                HttpHeaders.setContentLength(message, message.content().readableBytes());
                this.removeMessage(streamId3);
                list.add(message);
            }
        }
        else if (spdyFrame instanceof SpdyDataFrame) {
            final SpdyDataFrame spdyDataFrame = (SpdyDataFrame)spdyFrame;
            final int streamId4 = spdyDataFrame.streamId();
            final FullHttpMessage message2 = this.getMessage(streamId4);
            if (message2 == null) {
                return;
            }
            final ByteBuf content = message2.content();
            if (content.readableBytes() > this.maxContentLength - spdyDataFrame.content().readableBytes()) {
                this.removeMessage(streamId4);
                throw new TooLongFrameException("HTTP content length exceeded " + this.maxContentLength + " bytes.");
            }
            final ByteBuf content2 = spdyDataFrame.content();
            content.writeBytes(content2, content2.readerIndex(), content2.readableBytes());
            if (spdyDataFrame.isLast()) {
                HttpHeaders.setContentLength(message2, content.readableBytes());
                this.removeMessage(streamId4);
                list.add(message2);
            }
        }
        else if (spdyFrame instanceof SpdyRstStreamFrame) {
            this.removeMessage(((SpdyRstStreamFrame)spdyFrame).streamId());
        }
    }
    
    private static FullHttpRequest createHttpRequest(final int n, final SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        final SpdyHeaders headers = spdyHeadersFrame.headers();
        final HttpMethod method = SpdyHeaders.getMethod(n, spdyHeadersFrame);
        final String url = SpdyHeaders.getUrl(n, spdyHeadersFrame);
        final HttpVersion version = SpdyHeaders.getVersion(n, spdyHeadersFrame);
        SpdyHeaders.removeMethod(n, spdyHeadersFrame);
        SpdyHeaders.removeUrl(n, spdyHeadersFrame);
        SpdyHeaders.removeVersion(n, spdyHeadersFrame);
        final DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(version, method, url);
        SpdyHeaders.removeScheme(n, spdyHeadersFrame);
        final String value = headers.get(":host");
        headers.remove(":host");
        defaultFullHttpRequest.headers().set("Host", value);
        for (final Map.Entry<String, V> entry : spdyHeadersFrame.headers()) {
            defaultFullHttpRequest.headers().add(entry.getKey(), entry.getValue());
        }
        HttpHeaders.setKeepAlive(defaultFullHttpRequest, true);
        defaultFullHttpRequest.headers().remove("Transfer-Encoding");
        return defaultFullHttpRequest;
    }
    
    private static FullHttpResponse createHttpResponse(final ChannelHandlerContext channelHandlerContext, final int n, final SpdyHeadersFrame spdyHeadersFrame, final boolean b) throws Exception {
        final HttpResponseStatus status = SpdyHeaders.getStatus(n, spdyHeadersFrame);
        final HttpVersion version = SpdyHeaders.getVersion(n, spdyHeadersFrame);
        SpdyHeaders.removeStatus(n, spdyHeadersFrame);
        SpdyHeaders.removeVersion(n, spdyHeadersFrame);
        final DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(version, status, channelHandlerContext.alloc().buffer(), b);
        for (final Map.Entry<String, V> entry : spdyHeadersFrame.headers()) {
            defaultFullHttpResponse.headers().add(entry.getKey(), entry.getValue());
        }
        HttpHeaders.setKeepAlive(defaultFullHttpResponse, true);
        defaultFullHttpResponse.headers().remove("Transfer-Encoding");
        defaultFullHttpResponse.headers().remove("Trailer");
        return defaultFullHttpResponse;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.decode(channelHandlerContext, (SpdyFrame)o, list);
    }
}
