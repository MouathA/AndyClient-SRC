package com.viaversion.viaversion.util;

import java.lang.reflect.*;
import io.netty.buffer.*;
import io.netty.handler.codec.*;
import io.netty.channel.*;
import java.util.*;

public class PipelineUtil
{
    private static Method DECODE_METHOD;
    private static Method ENCODE_METHOD;
    private static Method MTM_DECODE;
    
    public static List callDecode(final ByteToMessageDecoder byteToMessageDecoder, final ChannelHandlerContext channelHandlerContext, final Object o) throws InvocationTargetException {
        final ArrayList list = new ArrayList();
        PipelineUtil.DECODE_METHOD.invoke(byteToMessageDecoder, channelHandlerContext, o, list);
        return list;
    }
    
    public static void callEncode(final MessageToByteEncoder messageToByteEncoder, final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws InvocationTargetException {
        PipelineUtil.ENCODE_METHOD.invoke(messageToByteEncoder, channelHandlerContext, o, byteBuf);
    }
    
    public static List callDecode(final MessageToMessageDecoder messageToMessageDecoder, final ChannelHandlerContext channelHandlerContext, final Object o) throws InvocationTargetException {
        final ArrayList list = new ArrayList();
        PipelineUtil.MTM_DECODE.invoke(messageToMessageDecoder, channelHandlerContext, o, list);
        return list;
    }
    
    public static boolean containsCause(Throwable cause, final Class clazz) {
        while (cause != null) {
            if (clazz.isAssignableFrom(cause.getClass())) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }
    
    public static ChannelHandlerContext getContextBefore(final String s, final ChannelPipeline channelPipeline) {
        for (final String s2 : channelPipeline.names()) {
            if (true) {
                return channelPipeline.context(channelPipeline.get(s2));
            }
            if (s2.equalsIgnoreCase(s)) {}
        }
        return null;
    }
    
    public static ChannelHandlerContext getPreviousContext(final String s, final ChannelPipeline channelPipeline) {
        String s2 = null;
        for (final String s3 : channelPipeline.toMap().keySet()) {
            if (s3.equals(s)) {
                return channelPipeline.context(s2);
            }
            s2 = s3;
        }
        return null;
    }
    
    static {
        (PipelineUtil.DECODE_METHOD = ByteToMessageDecoder.class.getDeclaredMethod("decode", ChannelHandlerContext.class, ByteBuf.class, List.class)).setAccessible(true);
        (PipelineUtil.ENCODE_METHOD = MessageToByteEncoder.class.getDeclaredMethod("encode", ChannelHandlerContext.class, Object.class, ByteBuf.class)).setAccessible(true);
        (PipelineUtil.MTM_DECODE = MessageToMessageDecoder.class.getDeclaredMethod("decode", ChannelHandlerContext.class, Object.class, List.class)).setAccessible(true);
    }
}
