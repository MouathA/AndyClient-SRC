package net.minecraft.util;

import io.netty.handler.codec.*;
import org.apache.logging.log4j.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;

public class MessageSerializer extends MessageToByteEncoder
{
    private static final Logger logger;
    private static final Marker RECEIVED_PACKET_MARKER;
    private final EnumPacketDirection direction;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001253";
        logger = LogManager.getLogger();
        RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_SENT", NetworkManager.logMarkerPackets);
    }
    
    public MessageSerializer(final EnumPacketDirection direction) {
        this.direction = direction;
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, Packet packet, final ByteBuf byteBuf) throws IOException {
        final Integer packetId = ((EnumConnectionState)channelHandlerContext.channel().attr(NetworkManager.attrKeyConnectionState).get()).getPacketId(this.direction, packet);
        if (MessageSerializer.logger.isDebugEnabled()) {
            MessageSerializer.logger.debug(MessageSerializer.RECEIVED_PACKET_MARKER, "OUT: [{}:{}] {}", channelHandlerContext.channel().attr(NetworkManager.attrKeyConnectionState).get(), packetId, packet.getClass().getName());
        }
        if (packetId == null) {
            throw new IOException("Can't serialize unregistered packet");
        }
        final PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
        packetBuffer.writeVarIntToBuffer(packetId);
        if (packet instanceof S0CPacketSpawnPlayer) {
            packet = packet;
        }
        packet.writePacketData(packetBuffer);
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws IOException {
        this.encode(channelHandlerContext, (Packet)o, byteBuf);
    }
}
