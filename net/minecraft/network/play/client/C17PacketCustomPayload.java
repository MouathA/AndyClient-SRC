package net.minecraft.network.play.client;

import java.io.*;
import io.netty.buffer.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C17PacketCustomPayload implements Packet
{
    private String channel;
    private PacketBuffer data;
    private static final String __OBFID;
    
    public C17PacketCustomPayload() {
    }
    
    public C17PacketCustomPayload(final String channel, final PacketBuffer data) {
        this.channel = channel;
        this.data = data;
        if (data.writerIndex() > 32767) {
            throw new IllegalArgumentException("Payload may not be larger than 32767 bytes");
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.channel = packetBuffer.readStringFromBuffer(20);
        final int readableBytes = packetBuffer.readableBytes();
        if (readableBytes >= 0 && readableBytes <= 32767) {
            this.data = new PacketBuffer(packetBuffer.readBytes(readableBytes));
            return;
        }
        throw new IOException("Payload may not be larger than 32767 bytes");
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.channel);
        packetBuffer.writeBytes(this.data);
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processVanilla250Packet(this);
    }
    
    public String getChannelName() {
        return this.channel;
    }
    
    public PacketBuffer getBufferData() {
        return this.data;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001356";
    }
}
