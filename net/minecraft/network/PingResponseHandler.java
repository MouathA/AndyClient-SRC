package net.minecraft.network;

import org.apache.logging.log4j.*;
import java.net.*;
import com.google.common.base.*;
import net.minecraft.server.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;
import io.netty.buffer.*;

public class PingResponseHandler extends ChannelInboundHandlerAdapter
{
    private static final Logger logger;
    private NetworkSystem networkSystem;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001444";
        logger = LogManager.getLogger();
    }
    
    public PingResponseHandler(final NetworkSystem networkSystem) {
        this.networkSystem = networkSystem;
    }
    
    @Override
    public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) {
        final ByteBuf byteBuf = (ByteBuf)o;
        byteBuf.markReaderIndex();
        if (byteBuf.readUnsignedByte() != 254) {
            if (false) {
                byteBuf.resetReaderIndex();
                channelHandlerContext.channel().pipeline().remove("legacy_query");
                channelHandlerContext.fireChannelRead(o);
            }
            return;
        }
        final InetSocketAddress inetSocketAddress = (InetSocketAddress)channelHandlerContext.channel().remoteAddress();
        final MinecraftServer server = this.networkSystem.getServer();
        switch (byteBuf.readableBytes()) {
            case 0: {
                PingResponseHandler.logger.debug("Ping: (<1.3.x) from {}:{}", inetSocketAddress.getAddress(), inetSocketAddress.getPort());
                this.writeAndFlush(channelHandlerContext, this.getStringBuffer(String.format("%s§%d§%d", server.getMOTD(), server.getCurrentPlayerCount(), server.getMaxPlayers())));
                break;
            }
            case 1: {
                if (byteBuf.readUnsignedByte() != 1) {
                    if (false) {
                        byteBuf.resetReaderIndex();
                        channelHandlerContext.channel().pipeline().remove("legacy_query");
                        channelHandlerContext.fireChannelRead(o);
                    }
                    return;
                }
                PingResponseHandler.logger.debug("Ping: (1.4-1.5.x) from {}:{}", inetSocketAddress.getAddress(), inetSocketAddress.getPort());
                this.writeAndFlush(channelHandlerContext, this.getStringBuffer(String.format("§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, server.getMinecraftVersion(), server.getMOTD(), server.getCurrentPlayerCount(), server.getMaxPlayers())));
                break;
            }
            default: {
                if (!(byteBuf.readUnsignedByte() == 1 & byteBuf.readUnsignedByte() == 250 & "MC|PingHost".equals(new String(byteBuf.readBytes(byteBuf.readShort() * 2).array(), Charsets.UTF_16BE)) & byteBuf.readUnsignedByte() >= 73 & 3 + byteBuf.readBytes(byteBuf.readShort() * 2).array().length + 4 == byteBuf.readUnsignedShort() & byteBuf.readInt() <= 65535 & byteBuf.readableBytes() == 0)) {
                    if (false) {
                        byteBuf.resetReaderIndex();
                        channelHandlerContext.channel().pipeline().remove("legacy_query");
                        channelHandlerContext.fireChannelRead(o);
                    }
                    return;
                }
                PingResponseHandler.logger.debug("Ping: (1.6) from {}:{}", inetSocketAddress.getAddress(), inetSocketAddress.getPort());
                final ByteBuf stringBuffer = this.getStringBuffer(String.format("§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, server.getMinecraftVersion(), server.getMOTD(), server.getCurrentPlayerCount(), server.getMaxPlayers()));
                this.writeAndFlush(channelHandlerContext, stringBuffer);
                stringBuffer.release();
                break;
            }
        }
        byteBuf.release();
        if (false) {
            byteBuf.resetReaderIndex();
            channelHandlerContext.channel().pipeline().remove("legacy_query");
            channelHandlerContext.fireChannelRead(o);
        }
    }
    
    private void writeAndFlush(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) {
        channelHandlerContext.pipeline().firstContext().writeAndFlush(byteBuf).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
    }
    
    private ByteBuf getStringBuffer(final String s) {
        final ByteBuf buffer = Unpooled.buffer();
        buffer.writeByte(255);
        final char[] charArray = s.toCharArray();
        buffer.writeShort(charArray.length);
        final char[] array = charArray;
        while (0 < charArray.length) {
            buffer.writeChar(array[0]);
            int n = 0;
            ++n;
        }
        return buffer;
    }
}
