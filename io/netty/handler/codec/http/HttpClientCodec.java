package io.netty.handler.codec.http;

import java.util.concurrent.atomic.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import io.netty.handler.codec.*;

public final class HttpClientCodec extends CombinedChannelDuplexHandler
{
    private final Queue queue;
    private boolean done;
    private final AtomicLong requestResponseCounter;
    private final boolean failOnMissingResponse;
    
    public HttpClientCodec() {
        this(4096, 8192, 8192, false);
    }
    
    public void setSingleDecode(final boolean singleDecode) {
        ((HttpResponseDecoder)this.inboundHandler()).setSingleDecode(singleDecode);
    }
    
    public boolean isSingleDecode() {
        return ((HttpResponseDecoder)this.inboundHandler()).isSingleDecode();
    }
    
    public HttpClientCodec(final int n, final int n2, final int n3) {
        this(n, n2, n3, false);
    }
    
    public HttpClientCodec(final int n, final int n2, final int n3, final boolean b) {
        this(n, n2, n3, b, true);
    }
    
    public HttpClientCodec(final int n, final int n2, final int n3, final boolean failOnMissingResponse, final boolean b) {
        this.queue = new ArrayDeque();
        this.requestResponseCounter = new AtomicLong();
        this.init(new Decoder(n, n2, n3, b), new Encoder(null));
        this.failOnMissingResponse = failOnMissingResponse;
    }
    
    static boolean access$100(final HttpClientCodec httpClientCodec) {
        return httpClientCodec.done;
    }
    
    static Queue access$200(final HttpClientCodec httpClientCodec) {
        return httpClientCodec.queue;
    }
    
    static boolean access$300(final HttpClientCodec httpClientCodec) {
        return httpClientCodec.failOnMissingResponse;
    }
    
    static AtomicLong access$400(final HttpClientCodec httpClientCodec) {
        return httpClientCodec.requestResponseCounter;
    }
    
    static boolean access$102(final HttpClientCodec httpClientCodec, final boolean done) {
        return httpClientCodec.done = done;
    }
    
    private final class Decoder extends HttpResponseDecoder
    {
        final HttpClientCodec this$0;
        
        Decoder(final HttpClientCodec this$0, final int n, final int n2, final int n3, final boolean b) {
            this.this$0 = this$0;
            super(n, n2, n3, b);
        }
        
        @Override
        protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
            if (HttpClientCodec.access$100(this.this$0)) {
                final int actualReadableBytes = this.actualReadableBytes();
                if (actualReadableBytes == 0) {
                    return;
                }
                list.add(byteBuf.readBytes(actualReadableBytes));
            }
            else {
                final int size = list.size();
                super.decode(channelHandlerContext, byteBuf, list);
                if (HttpClientCodec.access$300(this.this$0)) {
                    for (int size2 = list.size(), i = size; i < size2; ++i) {
                        this.decrement(list.get(i));
                    }
                }
            }
        }
        
        private void decrement(final Object o) {
            if (o == null) {
                return;
            }
            if (o instanceof LastHttpContent) {
                HttpClientCodec.access$400(this.this$0).decrementAndGet();
            }
        }
        
        @Override
        protected boolean isContentAlwaysEmpty(final HttpMessage httpMessage) {
            final int code = ((HttpResponse)httpMessage).getStatus().code();
            if (code == 100) {
                return true;
            }
            final HttpMethod httpMethod = HttpClientCodec.access$200(this.this$0).poll();
            switch (httpMethod.name().charAt(0)) {
                case 'H': {
                    if (HttpMethod.HEAD.equals(httpMethod)) {
                        return true;
                    }
                    break;
                }
                case 'C': {
                    if (code == 200 && HttpMethod.CONNECT.equals(httpMethod)) {
                        HttpClientCodec.access$102(this.this$0, true);
                        HttpClientCodec.access$200(this.this$0).clear();
                        return true;
                    }
                    break;
                }
            }
            return super.isContentAlwaysEmpty(httpMessage);
        }
        
        @Override
        public void channelInactive(final ChannelHandlerContext channelHandlerContext) throws Exception {
            super.channelInactive(channelHandlerContext);
            if (HttpClientCodec.access$300(this.this$0)) {
                final long value = HttpClientCodec.access$400(this.this$0).get();
                if (value > 0L) {
                    channelHandlerContext.fireExceptionCaught(new PrematureChannelClosureException("channel gone inactive with " + value + " missing response(s)"));
                }
            }
        }
    }
    
    private final class Encoder extends HttpRequestEncoder
    {
        final HttpClientCodec this$0;
        
        private Encoder(final HttpClientCodec this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
            if (o instanceof HttpRequest && !HttpClientCodec.access$100(this.this$0)) {
                HttpClientCodec.access$200(this.this$0).offer(((HttpRequest)o).getMethod());
            }
            super.encode(channelHandlerContext, o, list);
            if (HttpClientCodec.access$300(this.this$0) && o instanceof LastHttpContent) {
                HttpClientCodec.access$400(this.this$0).incrementAndGet();
            }
        }
        
        Encoder(final HttpClientCodec httpClientCodec, final HttpClientCodec$1 object) {
            this(httpClientCodec);
        }
    }
}
