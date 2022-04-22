package io.netty.handler.codec;

import io.netty.util.internal.*;
import io.netty.buffer.*;
import java.util.*;
import io.netty.channel.*;

public abstract class ByteToMessageCodec extends ChannelDuplexHandler
{
    private final TypeParameterMatcher outboundMsgMatcher;
    private final MessageToByteEncoder encoder;
    private final ByteToMessageDecoder decoder;
    
    protected ByteToMessageCodec() {
        this(true);
    }
    
    protected ByteToMessageCodec(final Class clazz) {
        this(clazz, true);
    }
    
    protected ByteToMessageCodec(final boolean b) {
        this.decoder = new ByteToMessageDecoder() {
            final ByteToMessageCodec this$0;
            
            public void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
                this.this$0.decode(channelHandlerContext, byteBuf, list);
            }
            
            @Override
            protected void decodeLast(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
                this.this$0.decodeLast(channelHandlerContext, byteBuf, list);
            }
        };
        this.outboundMsgMatcher = TypeParameterMatcher.find(this, ByteToMessageCodec.class, "I");
        this.encoder = new Encoder(b);
    }
    
    protected ByteToMessageCodec(final Class clazz, final boolean b) {
        this.decoder = new ByteToMessageDecoder() {
            final ByteToMessageCodec this$0;
            
            public void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
                this.this$0.decode(channelHandlerContext, byteBuf, list);
            }
            
            @Override
            protected void decodeLast(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
                this.this$0.decodeLast(channelHandlerContext, byteBuf, list);
            }
        };
        this.checkForSharableAnnotation();
        this.outboundMsgMatcher = TypeParameterMatcher.get(clazz);
        this.encoder = new Encoder(b);
    }
    
    private void checkForSharableAnnotation() {
        if (this.isSharable()) {
            throw new IllegalStateException("@Sharable annotation is not allowed");
        }
    }
    
    public boolean acceptOutboundMessage(final Object o) throws Exception {
        return this.outboundMsgMatcher.match(o);
    }
    
    @Override
    public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        this.decoder.channelRead(channelHandlerContext, o);
    }
    
    @Override
    public void write(final ChannelHandlerContext channelHandlerContext, final Object o, final ChannelPromise channelPromise) throws Exception {
        this.encoder.write(channelHandlerContext, o, channelPromise);
    }
    
    protected abstract void encode(final ChannelHandlerContext p0, final Object p1, final ByteBuf p2) throws Exception;
    
    protected abstract void decode(final ChannelHandlerContext p0, final ByteBuf p1, final List p2) throws Exception;
    
    protected void decodeLast(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        this.decode(channelHandlerContext, byteBuf, list);
    }
    
    private final class Encoder extends MessageToByteEncoder
    {
        final ByteToMessageCodec this$0;
        
        Encoder(final ByteToMessageCodec this$0, final boolean b) {
            this.this$0 = this$0;
            super(b);
        }
        
        @Override
        public boolean acceptOutboundMessage(final Object o) throws Exception {
            return this.this$0.acceptOutboundMessage(o);
        }
        
        @Override
        protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
            this.this$0.encode(channelHandlerContext, o, byteBuf);
        }
    }
}
