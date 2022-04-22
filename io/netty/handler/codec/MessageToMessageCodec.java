package io.netty.handler.codec;

import io.netty.util.internal.*;
import java.util.*;
import io.netty.channel.*;

public abstract class MessageToMessageCodec extends ChannelDuplexHandler
{
    private final MessageToMessageEncoder encoder;
    private final MessageToMessageDecoder decoder;
    private final TypeParameterMatcher inboundMsgMatcher;
    private final TypeParameterMatcher outboundMsgMatcher;
    
    protected MessageToMessageCodec() {
        this.encoder = new MessageToMessageEncoder() {
            final MessageToMessageCodec this$0;
            
            @Override
            public boolean acceptOutboundMessage(final Object o) throws Exception {
                return this.this$0.acceptOutboundMessage(o);
            }
            
            @Override
            protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
                this.this$0.encode(channelHandlerContext, o, list);
            }
        };
        this.decoder = new MessageToMessageDecoder() {
            final MessageToMessageCodec this$0;
            
            @Override
            public boolean acceptInboundMessage(final Object o) throws Exception {
                return this.this$0.acceptInboundMessage(o);
            }
            
            @Override
            protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
                this.this$0.decode(channelHandlerContext, o, list);
            }
        };
        this.inboundMsgMatcher = TypeParameterMatcher.find(this, MessageToMessageCodec.class, "INBOUND_IN");
        this.outboundMsgMatcher = TypeParameterMatcher.find(this, MessageToMessageCodec.class, "OUTBOUND_IN");
    }
    
    protected MessageToMessageCodec(final Class clazz, final Class clazz2) {
        this.encoder = new MessageToMessageEncoder() {
            final MessageToMessageCodec this$0;
            
            @Override
            public boolean acceptOutboundMessage(final Object o) throws Exception {
                return this.this$0.acceptOutboundMessage(o);
            }
            
            @Override
            protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
                this.this$0.encode(channelHandlerContext, o, list);
            }
        };
        this.decoder = new MessageToMessageDecoder() {
            final MessageToMessageCodec this$0;
            
            @Override
            public boolean acceptInboundMessage(final Object o) throws Exception {
                return this.this$0.acceptInboundMessage(o);
            }
            
            @Override
            protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
                this.this$0.decode(channelHandlerContext, o, list);
            }
        };
        this.inboundMsgMatcher = TypeParameterMatcher.get(clazz);
        this.outboundMsgMatcher = TypeParameterMatcher.get(clazz2);
    }
    
    @Override
    public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        this.decoder.channelRead(channelHandlerContext, o);
    }
    
    @Override
    public void write(final ChannelHandlerContext channelHandlerContext, final Object o, final ChannelPromise channelPromise) throws Exception {
        this.encoder.write(channelHandlerContext, o, channelPromise);
    }
    
    public boolean acceptInboundMessage(final Object o) throws Exception {
        return this.inboundMsgMatcher.match(o);
    }
    
    public boolean acceptOutboundMessage(final Object o) throws Exception {
        return this.outboundMsgMatcher.match(o);
    }
    
    protected abstract void encode(final ChannelHandlerContext p0, final Object p1, final List p2) throws Exception;
    
    protected abstract void decode(final ChannelHandlerContext p0, final Object p1, final List p2) throws Exception;
}
