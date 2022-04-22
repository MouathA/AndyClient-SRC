package com.viaversion.viaversion.bungee.util;

import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import java.lang.reflect.*;
import io.netty.handler.codec.*;

public class BungeePipelineUtil
{
    private static Method DECODE_METHOD;
    private static Method ENCODE_METHOD;
    
    public static List callDecode(final MessageToMessageDecoder messageToMessageDecoder, final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws InvocationTargetException {
        final ArrayList list = new ArrayList();
        BungeePipelineUtil.DECODE_METHOD.invoke(messageToMessageDecoder, channelHandlerContext, byteBuf, list);
        return list;
    }
    
    public static ByteBuf callEncode(final MessageToByteEncoder messageToByteEncoder, final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws InvocationTargetException {
        final ByteBuf buffer = channelHandlerContext.alloc().buffer();
        BungeePipelineUtil.ENCODE_METHOD.invoke(messageToByteEncoder, channelHandlerContext, byteBuf, buffer);
        return buffer;
    }
    
    public static ByteBuf decompress(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) {
        return callDecode((MessageToMessageDecoder)channelHandlerContext.pipeline().get("decompress"), channelHandlerContext.pipeline().context("decompress"), byteBuf).get(0);
    }
    
    public static ByteBuf compress(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) {
        return callEncode((MessageToByteEncoder)channelHandlerContext.pipeline().get("compress"), channelHandlerContext.pipeline().context("compress"), byteBuf);
    }
    
    static {
        (BungeePipelineUtil.DECODE_METHOD = MessageToMessageDecoder.class.getDeclaredMethod("decode", ChannelHandlerContext.class, Object.class, List.class)).setAccessible(true);
        (BungeePipelineUtil.ENCODE_METHOD = MessageToByteEncoder.class.getDeclaredMethod("encode", ChannelHandlerContext.class, Object.class, ByteBuf.class)).setAccessible(true);
    }
}
