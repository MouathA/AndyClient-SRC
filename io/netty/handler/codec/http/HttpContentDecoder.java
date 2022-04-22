package io.netty.handler.codec.http;

import io.netty.handler.codec.*;
import io.netty.channel.embedded.*;
import io.netty.channel.*;
import java.util.*;
import io.netty.util.*;
import io.netty.buffer.*;

public abstract class HttpContentDecoder extends MessageToMessageDecoder
{
    private EmbeddedChannel decoder;
    private HttpMessage message;
    private boolean decodeStarted;
    private boolean continueResponse;
    static final boolean $assertionsDisabled;
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final HttpObject httpObject, final List list) throws Exception {
        if (httpObject instanceof HttpResponse && ((HttpResponse)httpObject).getStatus().code() == 100) {
            if (!(httpObject instanceof LastHttpContent)) {
                this.continueResponse = true;
            }
            list.add(ReferenceCountUtil.retain(httpObject));
            return;
        }
        if (this.continueResponse) {
            if (httpObject instanceof LastHttpContent) {
                this.continueResponse = false;
            }
            list.add(ReferenceCountUtil.retain(httpObject));
            return;
        }
        if (httpObject instanceof HttpMessage) {
            assert this.message == null;
            this.message = (HttpMessage)httpObject;
            this.decodeStarted = false;
            this.cleanup();
        }
        if (httpObject instanceof HttpContent) {
            final HttpContent httpContent = (HttpContent)httpObject;
            if (!this.decodeStarted) {
                this.decodeStarted = true;
                final HttpMessage message = this.message;
                final HttpHeaders headers = message.headers();
                this.message = null;
                final String value = headers.get("Content-Encoding");
                String trim;
                if (value != null) {
                    trim = value.trim();
                }
                else {
                    trim = "identity";
                }
                final EmbeddedChannel contentDecoder = this.newContentDecoder(trim);
                this.decoder = contentDecoder;
                if (contentDecoder != null) {
                    final String targetContentEncoding = this.getTargetContentEncoding(trim);
                    if ("identity".equals(targetContentEncoding)) {
                        headers.remove("Content-Encoding");
                    }
                    else {
                        headers.set("Content-Encoding", targetContentEncoding);
                    }
                    list.add(message);
                    this.decodeContent(httpContent, list);
                    if (headers.contains("Content-Length")) {
                        while (0 < list.size()) {
                            final HttpContent value2 = list.get(0);
                            if (value2 instanceof HttpContent) {
                                final int n = 0 + value2.content().readableBytes();
                            }
                            int n2 = 0;
                            ++n2;
                        }
                        headers.set("Content-Length", Integer.toString(0));
                    }
                    return;
                }
                if (httpContent instanceof LastHttpContent) {
                    this.decodeStarted = false;
                }
                list.add(message);
                list.add(httpContent.retain());
            }
            else if (this.decoder != null) {
                this.decodeContent(httpContent, list);
            }
            else {
                if (httpContent instanceof LastHttpContent) {
                    this.decodeStarted = false;
                }
                list.add(httpContent.retain());
            }
        }
    }
    
    private void decodeContent(final HttpContent httpContent, final List list) {
        this.decode(httpContent.content(), list);
        if (httpContent instanceof LastHttpContent) {
            this.finishDecode(list);
            final HttpHeaders trailingHeaders = ((LastHttpContent)httpContent).trailingHeaders();
            if (trailingHeaders.isEmpty()) {
                list.add(LastHttpContent.EMPTY_LAST_CONTENT);
            }
            else {
                list.add(new ComposedLastHttpContent(trailingHeaders));
            }
        }
    }
    
    protected abstract EmbeddedChannel newContentDecoder(final String p0) throws Exception;
    
    protected String getTargetContentEncoding(final String s) throws Exception {
        return "identity";
    }
    
    @Override
    public void handlerRemoved(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.cleanup();
        super.handlerRemoved(channelHandlerContext);
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.cleanup();
        super.channelInactive(channelHandlerContext);
    }
    
    private void cleanup() {
        if (this.decoder != null) {
            if (this.decoder.finish()) {
                while (true) {
                    final ByteBuf byteBuf = (ByteBuf)this.decoder.readOutbound();
                    if (byteBuf == null) {
                        break;
                    }
                    byteBuf.release();
                }
            }
            this.decoder = null;
        }
    }
    
    private void decode(final ByteBuf byteBuf, final List list) {
        this.decoder.writeInbound(byteBuf.retain());
        this.fetchDecoderOutput(list);
    }
    
    private void finishDecode(final List list) {
        if (this.decoder.finish()) {
            this.fetchDecoderOutput(list);
        }
        this.decodeStarted = false;
        this.decoder = null;
    }
    
    private void fetchDecoderOutput(final List list) {
        while (true) {
            final ByteBuf byteBuf = (ByteBuf)this.decoder.readInbound();
            if (byteBuf == null) {
                break;
            }
            if (!byteBuf.isReadable()) {
                byteBuf.release();
            }
            else {
                list.add(new DefaultHttpContent(byteBuf));
            }
        }
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.decode(channelHandlerContext, (HttpObject)o, list);
    }
    
    static {
        $assertionsDisabled = !HttpContentDecoder.class.desiredAssertionStatus();
    }
}
