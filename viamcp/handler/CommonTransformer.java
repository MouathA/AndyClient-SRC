package viamcp.handler;

import io.netty.buffer.*;
import com.viaversion.viaversion.util.*;
import io.netty.channel.*;
import java.lang.reflect.*;
import io.netty.handler.codec.*;

public class CommonTransformer
{
    public static final String HANDLER_DECODER_NAME;
    public static final String HANDLER_ENCODER_NAME;
    
    public static void decompress(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws InvocationTargetException {
        final ChannelHandler value = channelHandlerContext.pipeline().get("decompress");
        final ByteBuf byteBuf2 = (value instanceof MessageToMessageDecoder) ? PipelineUtil.callDecode((MessageToMessageDecoder)value, channelHandlerContext, byteBuf).get(0) : PipelineUtil.callDecode((ByteToMessageDecoder)value, channelHandlerContext, byteBuf).get(0);
        byteBuf.clear().writeBytes(byteBuf2);
        byteBuf2.release();
    }
    
    public static void compress(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws Exception {
        final ByteBuf buffer = channelHandlerContext.alloc().buffer();
        PipelineUtil.callEncode((MessageToByteEncoder)channelHandlerContext.pipeline().get("compress"), channelHandlerContext, byteBuf, buffer);
        byteBuf.clear().writeBytes(buffer);
        buffer.release();
    }
    
    static {
        HANDLER_ENCODER_NAME = "via-encoder";
        HANDLER_DECODER_NAME = "via-decoder";
    }
}
