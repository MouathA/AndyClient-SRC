package io.netty.handler.codec.http;

import io.netty.handler.codec.*;
import java.util.*;
import io.netty.util.internal.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import io.netty.util.*;

public abstract class HttpObjectEncoder extends MessageToMessageEncoder
{
    private static final byte[] CRLF;
    private static final byte[] ZERO_CRLF;
    private static final byte[] ZERO_CRLF_CRLF;
    private static final ByteBuf CRLF_BUF;
    private static final ByteBuf ZERO_CRLF_CRLF_BUF;
    private static final int ST_INIT = 0;
    private static final int ST_CONTENT_NON_CHUNK = 1;
    private static final int ST_CONTENT_CHUNK = 2;
    private int state;
    
    public HttpObjectEncoder() {
        this.state = 0;
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        ByteBuf buffer = null;
        if (o instanceof HttpMessage) {
            if (this.state != 0) {
                throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(o));
            }
            final HttpMessage httpMessage = (HttpMessage)o;
            buffer = channelHandlerContext.alloc().buffer();
            this.encodeInitialLine(buffer, httpMessage);
            HttpHeaders.encode(httpMessage.headers(), buffer);
            buffer.writeBytes(HttpObjectEncoder.CRLF);
            this.state = (HttpHeaders.isTransferEncodingChunked(httpMessage) ? 2 : 1);
        }
        if (o instanceof HttpContent || o instanceof ByteBuf || o instanceof FileRegion) {
            if (this.state == 0) {
                throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(o));
            }
            final long contentLength = contentLength(o);
            if (this.state == 1) {
                if (contentLength > 0L) {
                    if (buffer != null && buffer.writableBytes() >= contentLength && o instanceof HttpContent) {
                        buffer.writeBytes(((HttpContent)o).content());
                        list.add(buffer);
                    }
                    else {
                        if (buffer != null) {
                            list.add(buffer);
                        }
                        list.add(encodeAndRetain(o));
                    }
                }
                else if (buffer != null) {
                    list.add(buffer);
                }
                else {
                    list.add(Unpooled.EMPTY_BUFFER);
                }
                if (o instanceof LastHttpContent) {
                    this.state = 0;
                }
            }
            else {
                if (this.state != 2) {
                    throw new Error();
                }
                if (buffer != null) {
                    list.add(buffer);
                }
                this.encodeChunkedContent(channelHandlerContext, o, contentLength, list);
            }
        }
        else if (buffer != null) {
            list.add(buffer);
        }
    }
    
    private void encodeChunkedContent(final ChannelHandlerContext channelHandlerContext, final Object o, final long n, final List list) {
        if (n > 0L) {
            final byte[] bytes = Long.toHexString(n).getBytes(CharsetUtil.US_ASCII);
            final ByteBuf buffer = channelHandlerContext.alloc().buffer(bytes.length + 2);
            buffer.writeBytes(bytes);
            buffer.writeBytes(HttpObjectEncoder.CRLF);
            list.add(buffer);
            list.add(encodeAndRetain(o));
            list.add(HttpObjectEncoder.CRLF_BUF.duplicate());
        }
        if (o instanceof LastHttpContent) {
            final HttpHeaders trailingHeaders = ((LastHttpContent)o).trailingHeaders();
            if (trailingHeaders.isEmpty()) {
                list.add(HttpObjectEncoder.ZERO_CRLF_CRLF_BUF.duplicate());
            }
            else {
                final ByteBuf buffer2 = channelHandlerContext.alloc().buffer();
                buffer2.writeBytes(HttpObjectEncoder.ZERO_CRLF);
                HttpHeaders.encode(trailingHeaders, buffer2);
                buffer2.writeBytes(HttpObjectEncoder.CRLF);
                list.add(buffer2);
            }
            this.state = 0;
        }
        else if (n == 0L) {
            list.add(Unpooled.EMPTY_BUFFER);
        }
    }
    
    @Override
    public boolean acceptOutboundMessage(final Object o) throws Exception {
        return o instanceof HttpObject || o instanceof ByteBuf || o instanceof FileRegion;
    }
    
    private static Object encodeAndRetain(final Object o) {
        if (o instanceof ByteBuf) {
            return ((ByteBuf)o).retain();
        }
        if (o instanceof HttpContent) {
            return ((HttpContent)o).content().retain();
        }
        if (o instanceof FileRegion) {
            return ((FileRegion)o).retain();
        }
        throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(o));
    }
    
    private static long contentLength(final Object o) {
        if (o instanceof HttpContent) {
            return ((HttpContent)o).content().readableBytes();
        }
        if (o instanceof ByteBuf) {
            return ((ByteBuf)o).readableBytes();
        }
        if (o instanceof FileRegion) {
            return ((FileRegion)o).count();
        }
        throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(o));
    }
    
    @Deprecated
    protected static void encodeAscii(final String s, final ByteBuf byteBuf) {
        HttpHeaders.encodeAscii0(s, byteBuf);
    }
    
    protected abstract void encodeInitialLine(final ByteBuf p0, final HttpMessage p1) throws Exception;
    
    static {
        CRLF = new byte[] { 13, 10 };
        ZERO_CRLF = new byte[] { 48, 13, 10 };
        ZERO_CRLF_CRLF = new byte[] { 48, 13, 10, 13, 10 };
        CRLF_BUF = Unpooled.unreleasableBuffer(Unpooled.directBuffer(HttpObjectEncoder.CRLF.length).writeBytes(HttpObjectEncoder.CRLF));
        ZERO_CRLF_CRLF_BUF = Unpooled.unreleasableBuffer(Unpooled.directBuffer(HttpObjectEncoder.ZERO_CRLF_CRLF.length).writeBytes(HttpObjectEncoder.ZERO_CRLF_CRLF));
    }
}
