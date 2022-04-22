package io.netty.handler.codec.http;

import java.util.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;
import io.netty.handler.codec.*;
import io.netty.util.*;
import io.netty.buffer.*;

public class HttpObjectAggregator extends MessageToMessageDecoder
{
    public static final int DEFAULT_MAX_COMPOSITEBUFFER_COMPONENTS = 1024;
    private static final FullHttpResponse CONTINUE;
    private final int maxContentLength;
    private AggregatedFullHttpMessage currentMessage;
    private boolean tooLongFrameFound;
    private int maxCumulationBufferComponents;
    private ChannelHandlerContext ctx;
    static final boolean $assertionsDisabled;
    
    public HttpObjectAggregator(final int maxContentLength) {
        this.maxCumulationBufferComponents = 1024;
        if (maxContentLength <= 0) {
            throw new IllegalArgumentException("maxContentLength must be a positive integer: " + maxContentLength);
        }
        this.maxContentLength = maxContentLength;
    }
    
    public final int getMaxCumulationBufferComponents() {
        return this.maxCumulationBufferComponents;
    }
    
    public final void setMaxCumulationBufferComponents(final int maxCumulationBufferComponents) {
        if (maxCumulationBufferComponents < 2) {
            throw new IllegalArgumentException("maxCumulationBufferComponents: " + maxCumulationBufferComponents + " (expected: >= 2)");
        }
        if (this.ctx == null) {
            this.maxCumulationBufferComponents = maxCumulationBufferComponents;
            return;
        }
        throw new IllegalStateException("decoder properties cannot be changed once the decoder is added to a pipeline.");
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final HttpObject httpObject, final List list) throws Exception {
        final AggregatedFullHttpMessage currentMessage = this.currentMessage;
        if (httpObject instanceof HttpMessage) {
            this.tooLongFrameFound = false;
            assert currentMessage == null;
            final HttpMessage httpMessage = (HttpMessage)httpObject;
            if (HttpHeaders.is100ContinueExpected(httpMessage)) {
                channelHandlerContext.writeAndFlush(HttpObjectAggregator.CONTINUE).addListener((GenericFutureListener)new ChannelFutureListener(channelHandlerContext) {
                    final ChannelHandlerContext val$ctx;
                    final HttpObjectAggregator this$0;
                    
                    public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                        if (!channelFuture.isSuccess()) {
                            this.val$ctx.fireExceptionCaught(channelFuture.cause());
                        }
                    }
                    
                    @Override
                    public void operationComplete(final Future future) throws Exception {
                        this.operationComplete((ChannelFuture)future);
                    }
                });
            }
            if (!httpMessage.getDecoderResult().isSuccess()) {
                HttpHeaders.removeTransferEncodingChunked(httpMessage);
                list.add(toFullMessage(httpMessage));
                this.currentMessage = null;
                return;
            }
            AggregatedFullHttpMessage aggregatedFullHttpMessage;
            if (httpObject instanceof HttpRequest) {
                aggregatedFullHttpMessage = (this.currentMessage = new AggregatedFullHttpRequest((HttpRequest)httpObject, channelHandlerContext.alloc().compositeBuffer(this.maxCumulationBufferComponents), null, null));
            }
            else {
                if (!(httpObject instanceof HttpResponse)) {
                    throw new Error();
                }
                aggregatedFullHttpMessage = (this.currentMessage = new AggregatedFullHttpResponse((HttpResponse)httpObject, Unpooled.compositeBuffer(this.maxCumulationBufferComponents), null, null));
            }
            HttpHeaders.removeTransferEncodingChunked(aggregatedFullHttpMessage);
        }
        else {
            if (!(httpObject instanceof HttpContent)) {
                throw new Error();
            }
            if (this.tooLongFrameFound) {
                if (httpObject instanceof LastHttpContent) {
                    this.currentMessage = null;
                }
                return;
            }
            assert currentMessage != null;
            final HttpContent httpContent = (HttpContent)httpObject;
            final CompositeByteBuf compositeByteBuf = (CompositeByteBuf)currentMessage.content();
            if (compositeByteBuf.readableBytes() > this.maxContentLength - httpContent.content().readableBytes()) {
                this.tooLongFrameFound = true;
                currentMessage.release();
                this.currentMessage = null;
                throw new TooLongFrameException("HTTP content length exceeded " + this.maxContentLength + " bytes.");
            }
            if (httpContent.content().isReadable()) {
                httpContent.retain();
                compositeByteBuf.addComponent(httpContent.content());
                compositeByteBuf.writerIndex(compositeByteBuf.writerIndex() + httpContent.content().readableBytes());
            }
            if (!httpContent.getDecoderResult().isSuccess()) {
                currentMessage.setDecoderResult(DecoderResult.failure(httpContent.getDecoderResult().cause()));
            }
            else {
                final boolean b = httpContent instanceof LastHttpContent;
            }
            if (true) {
                this.currentMessage = null;
                if (httpContent instanceof LastHttpContent) {
                    currentMessage.setTrailingHeaders(((LastHttpContent)httpContent).trailingHeaders());
                }
                else {
                    currentMessage.setTrailingHeaders(new DefaultHttpHeaders());
                }
                currentMessage.headers().set("Content-Length", String.valueOf(compositeByteBuf.readableBytes()));
                list.add(currentMessage);
            }
        }
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelInactive(channelHandlerContext);
        if (this.currentMessage != null) {
            this.currentMessage.release();
            this.currentMessage = null;
        }
    }
    
    @Override
    public void handlerAdded(final ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }
    
    @Override
    public void handlerRemoved(final ChannelHandlerContext channelHandlerContext) throws Exception {
        super.handlerRemoved(channelHandlerContext);
        if (this.currentMessage != null) {
            this.currentMessage.release();
            this.currentMessage = null;
        }
    }
    
    private static FullHttpMessage toFullMessage(final HttpMessage httpMessage) {
        if (httpMessage instanceof FullHttpMessage) {
            return ((FullHttpMessage)httpMessage).retain();
        }
        AggregatedFullHttpMessage aggregatedFullHttpMessage;
        if (httpMessage instanceof HttpRequest) {
            aggregatedFullHttpMessage = new AggregatedFullHttpRequest((HttpRequest)httpMessage, Unpooled.EMPTY_BUFFER, new DefaultHttpHeaders(), null);
        }
        else {
            if (!(httpMessage instanceof HttpResponse)) {
                throw new IllegalStateException();
            }
            aggregatedFullHttpMessage = new AggregatedFullHttpResponse((HttpResponse)httpMessage, Unpooled.EMPTY_BUFFER, new DefaultHttpHeaders(), null);
        }
        return aggregatedFullHttpMessage;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.decode(channelHandlerContext, (HttpObject)o, list);
    }
    
    static {
        $assertionsDisabled = !HttpObjectAggregator.class.desiredAssertionStatus();
        CONTINUE = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE, Unpooled.EMPTY_BUFFER);
    }
    
    private static final class AggregatedFullHttpResponse extends AggregatedFullHttpMessage implements FullHttpResponse
    {
        private AggregatedFullHttpResponse(final HttpResponse httpResponse, final ByteBuf byteBuf, final HttpHeaders httpHeaders) {
            super(httpResponse, byteBuf, httpHeaders, null);
        }
        
        @Override
        public FullHttpResponse copy() {
            final DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(this.getProtocolVersion(), this.getStatus(), this.content().copy());
            defaultFullHttpResponse.headers().set(this.headers());
            defaultFullHttpResponse.trailingHeaders().set(this.trailingHeaders());
            return defaultFullHttpResponse;
        }
        
        @Override
        public FullHttpResponse duplicate() {
            final DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(this.getProtocolVersion(), this.getStatus(), this.content().duplicate());
            defaultFullHttpResponse.headers().set(this.headers());
            defaultFullHttpResponse.trailingHeaders().set(this.trailingHeaders());
            return defaultFullHttpResponse;
        }
        
        @Override
        public FullHttpResponse setStatus(final HttpResponseStatus status) {
            ((HttpResponse)this.message).setStatus(status);
            return this;
        }
        
        @Override
        public HttpResponseStatus getStatus() {
            return ((HttpResponse)this.message).getStatus();
        }
        
        @Override
        public FullHttpResponse setProtocolVersion(final HttpVersion protocolVersion) {
            super.setProtocolVersion(protocolVersion);
            return this;
        }
        
        @Override
        public FullHttpResponse retain(final int n) {
            super.retain(n);
            return this;
        }
        
        @Override
        public FullHttpResponse retain() {
            super.retain();
            return this;
        }
        
        @Override
        public FullHttpMessage duplicate() {
            return this.duplicate();
        }
        
        @Override
        public FullHttpMessage copy() {
            return this.copy();
        }
        
        @Override
        public FullHttpMessage retain() {
            return this.retain();
        }
        
        @Override
        public FullHttpMessage retain(final int n) {
            return this.retain(n);
        }
        
        @Override
        public FullHttpMessage setProtocolVersion(final HttpVersion protocolVersion) {
            return this.setProtocolVersion(protocolVersion);
        }
        
        @Override
        public HttpMessage setProtocolVersion(final HttpVersion protocolVersion) {
            return this.setProtocolVersion(protocolVersion);
        }
        
        @Override
        public LastHttpContent retain() {
            return this.retain();
        }
        
        @Override
        public LastHttpContent retain(final int n) {
            return this.retain(n);
        }
        
        @Override
        public LastHttpContent copy() {
            return this.copy();
        }
        
        @Override
        public HttpContent retain(final int n) {
            return this.retain(n);
        }
        
        @Override
        public HttpContent retain() {
            return this.retain();
        }
        
        @Override
        public HttpContent duplicate() {
            return this.duplicate();
        }
        
        @Override
        public HttpContent copy() {
            return this.copy();
        }
        
        @Override
        public ByteBufHolder retain(final int n) {
            return this.retain(n);
        }
        
        @Override
        public ByteBufHolder retain() {
            return this.retain();
        }
        
        @Override
        public ByteBufHolder duplicate() {
            return this.duplicate();
        }
        
        @Override
        public ByteBufHolder copy() {
            return this.copy();
        }
        
        @Override
        public ReferenceCounted retain(final int n) {
            return this.retain(n);
        }
        
        @Override
        public ReferenceCounted retain() {
            return this.retain();
        }
        
        @Override
        public HttpResponse setProtocolVersion(final HttpVersion protocolVersion) {
            return this.setProtocolVersion(protocolVersion);
        }
        
        @Override
        public HttpResponse setStatus(final HttpResponseStatus status) {
            return this.setStatus(status);
        }
        
        AggregatedFullHttpResponse(final HttpResponse httpResponse, final ByteBuf byteBuf, final HttpHeaders httpHeaders, final HttpObjectAggregator$1 channelFutureListener) {
            this(httpResponse, byteBuf, httpHeaders);
        }
    }
    
    private abstract static class AggregatedFullHttpMessage extends DefaultByteBufHolder implements FullHttpMessage
    {
        protected final HttpMessage message;
        private HttpHeaders trailingHeaders;
        
        private AggregatedFullHttpMessage(final HttpMessage message, final ByteBuf byteBuf, final HttpHeaders trailingHeaders) {
            super(byteBuf);
            this.message = message;
            this.trailingHeaders = trailingHeaders;
        }
        
        @Override
        public HttpHeaders trailingHeaders() {
            return this.trailingHeaders;
        }
        
        public void setTrailingHeaders(final HttpHeaders trailingHeaders) {
            this.trailingHeaders = trailingHeaders;
        }
        
        @Override
        public HttpVersion getProtocolVersion() {
            return this.message.getProtocolVersion();
        }
        
        @Override
        public FullHttpMessage setProtocolVersion(final HttpVersion protocolVersion) {
            this.message.setProtocolVersion(protocolVersion);
            return this;
        }
        
        @Override
        public HttpHeaders headers() {
            return this.message.headers();
        }
        
        @Override
        public DecoderResult getDecoderResult() {
            return this.message.getDecoderResult();
        }
        
        @Override
        public void setDecoderResult(final DecoderResult decoderResult) {
            this.message.setDecoderResult(decoderResult);
        }
        
        @Override
        public FullHttpMessage retain(final int n) {
            super.retain(n);
            return this;
        }
        
        @Override
        public FullHttpMessage retain() {
            super.retain();
            return this;
        }
        
        @Override
        public abstract FullHttpMessage copy();
        
        @Override
        public abstract FullHttpMessage duplicate();
        
        @Override
        public ByteBufHolder retain(final int n) {
            return this.retain(n);
        }
        
        @Override
        public ByteBufHolder retain() {
            return this.retain();
        }
        
        @Override
        public ByteBufHolder duplicate() {
            return this.duplicate();
        }
        
        @Override
        public ByteBufHolder copy() {
            return this.copy();
        }
        
        @Override
        public ReferenceCounted retain(final int n) {
            return this.retain(n);
        }
        
        @Override
        public ReferenceCounted retain() {
            return this.retain();
        }
        
        @Override
        public HttpMessage setProtocolVersion(final HttpVersion protocolVersion) {
            return this.setProtocolVersion(protocolVersion);
        }
        
        @Override
        public LastHttpContent retain() {
            return this.retain();
        }
        
        @Override
        public LastHttpContent retain(final int n) {
            return this.retain(n);
        }
        
        @Override
        public LastHttpContent copy() {
            return this.copy();
        }
        
        @Override
        public HttpContent retain(final int n) {
            return this.retain(n);
        }
        
        @Override
        public HttpContent retain() {
            return this.retain();
        }
        
        @Override
        public HttpContent duplicate() {
            return this.duplicate();
        }
        
        @Override
        public HttpContent copy() {
            return this.copy();
        }
        
        AggregatedFullHttpMessage(final HttpMessage httpMessage, final ByteBuf byteBuf, final HttpHeaders httpHeaders, final HttpObjectAggregator$1 channelFutureListener) {
            this(httpMessage, byteBuf, httpHeaders);
        }
    }
    
    private static final class AggregatedFullHttpRequest extends AggregatedFullHttpMessage implements FullHttpRequest
    {
        private AggregatedFullHttpRequest(final HttpRequest httpRequest, final ByteBuf byteBuf, final HttpHeaders httpHeaders) {
            super(httpRequest, byteBuf, httpHeaders, null);
        }
        
        @Override
        public FullHttpRequest copy() {
            final DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(this.getProtocolVersion(), this.getMethod(), this.getUri(), this.content().copy());
            defaultFullHttpRequest.headers().set(this.headers());
            defaultFullHttpRequest.trailingHeaders().set(this.trailingHeaders());
            return defaultFullHttpRequest;
        }
        
        @Override
        public FullHttpRequest duplicate() {
            final DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(this.getProtocolVersion(), this.getMethod(), this.getUri(), this.content().duplicate());
            defaultFullHttpRequest.headers().set(this.headers());
            defaultFullHttpRequest.trailingHeaders().set(this.trailingHeaders());
            return defaultFullHttpRequest;
        }
        
        @Override
        public FullHttpRequest retain(final int n) {
            super.retain(n);
            return this;
        }
        
        @Override
        public FullHttpRequest retain() {
            super.retain();
            return this;
        }
        
        @Override
        public FullHttpRequest setMethod(final HttpMethod method) {
            ((HttpRequest)this.message).setMethod(method);
            return this;
        }
        
        @Override
        public FullHttpRequest setUri(final String uri) {
            ((HttpRequest)this.message).setUri(uri);
            return this;
        }
        
        @Override
        public HttpMethod getMethod() {
            return ((HttpRequest)this.message).getMethod();
        }
        
        @Override
        public String getUri() {
            return ((HttpRequest)this.message).getUri();
        }
        
        @Override
        public FullHttpRequest setProtocolVersion(final HttpVersion protocolVersion) {
            super.setProtocolVersion(protocolVersion);
            return this;
        }
        
        @Override
        public FullHttpMessage duplicate() {
            return this.duplicate();
        }
        
        @Override
        public FullHttpMessage copy() {
            return this.copy();
        }
        
        @Override
        public FullHttpMessage retain() {
            return this.retain();
        }
        
        @Override
        public FullHttpMessage retain(final int n) {
            return this.retain(n);
        }
        
        @Override
        public FullHttpMessage setProtocolVersion(final HttpVersion protocolVersion) {
            return this.setProtocolVersion(protocolVersion);
        }
        
        @Override
        public HttpMessage setProtocolVersion(final HttpVersion protocolVersion) {
            return this.setProtocolVersion(protocolVersion);
        }
        
        @Override
        public LastHttpContent retain() {
            return this.retain();
        }
        
        @Override
        public LastHttpContent retain(final int n) {
            return this.retain(n);
        }
        
        @Override
        public LastHttpContent copy() {
            return this.copy();
        }
        
        @Override
        public HttpContent retain(final int n) {
            return this.retain(n);
        }
        
        @Override
        public HttpContent retain() {
            return this.retain();
        }
        
        @Override
        public HttpContent duplicate() {
            return this.duplicate();
        }
        
        @Override
        public HttpContent copy() {
            return this.copy();
        }
        
        @Override
        public ByteBufHolder retain(final int n) {
            return this.retain(n);
        }
        
        @Override
        public ByteBufHolder retain() {
            return this.retain();
        }
        
        @Override
        public ByteBufHolder duplicate() {
            return this.duplicate();
        }
        
        @Override
        public ByteBufHolder copy() {
            return this.copy();
        }
        
        @Override
        public ReferenceCounted retain(final int n) {
            return this.retain(n);
        }
        
        @Override
        public ReferenceCounted retain() {
            return this.retain();
        }
        
        @Override
        public HttpRequest setProtocolVersion(final HttpVersion protocolVersion) {
            return this.setProtocolVersion(protocolVersion);
        }
        
        @Override
        public HttpRequest setUri(final String uri) {
            return this.setUri(uri);
        }
        
        @Override
        public HttpRequest setMethod(final HttpMethod method) {
            return this.setMethod(method);
        }
        
        AggregatedFullHttpRequest(final HttpRequest httpRequest, final ByteBuf byteBuf, final HttpHeaders httpHeaders, final HttpObjectAggregator$1 channelFutureListener) {
            this(httpRequest, byteBuf, httpHeaders);
        }
    }
}
