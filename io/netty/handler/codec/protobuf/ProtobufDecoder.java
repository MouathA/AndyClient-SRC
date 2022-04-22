package io.netty.handler.codec.protobuf;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import com.google.protobuf.*;

@ChannelHandler.Sharable
public class ProtobufDecoder extends MessageToMessageDecoder
{
    private static final boolean HAS_PARSER;
    private final MessageLite prototype;
    private final ExtensionRegistry extensionRegistry;
    
    public ProtobufDecoder(final MessageLite messageLite) {
        this(messageLite, null);
    }
    
    public ProtobufDecoder(final MessageLite messageLite, final ExtensionRegistry extensionRegistry) {
        if (messageLite == null) {
            throw new NullPointerException("prototype");
        }
        this.prototype = messageLite.getDefaultInstanceForType();
        this.extensionRegistry = extensionRegistry;
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        final int readableBytes = byteBuf.readableBytes();
        byte[] array;
        if (byteBuf.hasArray()) {
            array = byteBuf.array();
            final int n = byteBuf.arrayOffset() + byteBuf.readerIndex();
        }
        else {
            array = new byte[readableBytes];
            byteBuf.getBytes(byteBuf.readerIndex(), array, 0, readableBytes);
        }
        if (this.extensionRegistry == null) {
            if (ProtobufDecoder.HAS_PARSER) {
                list.add(this.prototype.getParserForType().parseFrom(array, 0, readableBytes));
            }
            else {
                list.add(this.prototype.newBuilderForType().mergeFrom(array, 0, readableBytes).build());
            }
        }
        else if (ProtobufDecoder.HAS_PARSER) {
            list.add(this.prototype.getParserForType().parseFrom(array, 0, readableBytes, (ExtensionRegistryLite)this.extensionRegistry));
        }
        else {
            list.add(this.prototype.newBuilderForType().mergeFrom(array, 0, readableBytes, (ExtensionRegistryLite)this.extensionRegistry).build());
        }
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.decode(channelHandlerContext, (ByteBuf)o, list);
    }
    
    static {
        MessageLite.class.getDeclaredMethod("getParserForType", (Class<?>[])new Class[0]);
        HAS_PARSER = true;
    }
}
