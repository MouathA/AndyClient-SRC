package viamcp.handler;

import io.netty.handler.codec.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import java.util.function.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.exception.*;
import com.viaversion.viaversion.util.*;

@ChannelHandler.Sharable
public class MCPEncodeHandler extends MessageToMessageEncoder
{
    private final UserConnection info;
    private boolean handledCompression;
    
    public MCPEncodeHandler(final UserConnection info) {
        this.info = info;
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        if (!this.info.checkOutgoingPacket()) {
            throw CancelEncoderException.generate(null);
        }
        if (!this.info.shouldTransformPacket()) {
            list.add(byteBuf.retain());
            return;
        }
        final ByteBuf writeBytes = channelHandlerContext.alloc().buffer().writeBytes(byteBuf);
        final boolean handleCompressionOrder = this.handleCompressionOrder(channelHandlerContext, writeBytes);
        this.info.transformOutgoing(writeBytes, CancelEncoderException::generate);
        if (handleCompressionOrder) {
            CommonTransformer.compress(channelHandlerContext, writeBytes);
        }
        list.add(writeBytes.retain());
        writeBytes.release();
    }
    
    private boolean handleCompressionOrder(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws InvocationTargetException {
        if (this.handledCompression) {
            return false;
        }
        final int index = channelHandlerContext.pipeline().names().indexOf("compress");
        if (index == -1) {
            return false;
        }
        this.handledCompression = true;
        if (index > channelHandlerContext.pipeline().names().indexOf("via-encoder")) {
            CommonTransformer.decompress(channelHandlerContext, byteBuf);
            final ChannelHandler value = channelHandlerContext.pipeline().get("via-encoder");
            final ChannelHandler value2 = channelHandlerContext.pipeline().get("via-decoder");
            channelHandlerContext.pipeline().remove(value);
            channelHandlerContext.pipeline().remove(value2);
            channelHandlerContext.pipeline().addAfter("compress", "via-encoder", value);
            channelHandlerContext.pipeline().addAfter("decompress", "via-decoder", value2);
            return true;
        }
        return false;
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
        if (PipelineUtil.containsCause(t, CancelCodecException.class)) {
            return;
        }
        super.exceptionCaught(channelHandlerContext, t);
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)o, list);
    }
}