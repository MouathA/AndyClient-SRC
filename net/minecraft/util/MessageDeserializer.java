package net.minecraft.util;

import io.netty.handler.codec.*;
import org.apache.logging.log4j.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import java.io.*;
import net.minecraft.network.*;

public class MessageDeserializer extends ByteToMessageDecoder
{
    private static final Logger logger;
    private static final Marker RECEIVED_PACKET_MARKER;
    private final EnumPacketDirection direction;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001252";
        logger = LogManager.getLogger();
        RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.logMarkerPackets);
    }
    
    public MessageDeserializer(final EnumPacketDirection direction) {
        this.direction = direction;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws IOException, InstantiationException, IllegalAccessException {
        if (byteBuf.readableBytes() != 0) {
            final PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
            final int varIntFromBuffer = packetBuffer.readVarIntFromBuffer();
            final Packet packet = ((EnumConnectionState)channelHandlerContext.channel().attr(NetworkManager.attrKeyConnectionState).get()).getPacket(this.direction, varIntFromBuffer);
            if (packet == null) {
                throw new IOException("Bad packet id " + varIntFromBuffer);
            }
            packet.readPacketData(packetBuffer);
            if (packetBuffer.readableBytes() > 0) {
                throw new IOException("Packet " + ((EnumConnectionState)channelHandlerContext.channel().attr(NetworkManager.attrKeyConnectionState).get()).getId() + "/" + varIntFromBuffer + " (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + packetBuffer.readableBytes() + " bytes extra whilst reading packet " + varIntFromBuffer);
            }
            list.add(packet);
            if (MessageDeserializer.logger.isDebugEnabled()) {
                MessageDeserializer.logger.debug(MessageDeserializer.RECEIVED_PACKET_MARKER, " IN: [{}:{}] {}", channelHandlerContext.channel().attr(NetworkManager.attrKeyConnectionState).get(), varIntFromBuffer, packet.getClass().getName());
            }
        }
    }
}
