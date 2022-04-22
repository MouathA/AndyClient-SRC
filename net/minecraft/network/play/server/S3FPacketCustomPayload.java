package net.minecraft.network.play.server;

import java.io.*;
import io.netty.buffer.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S3FPacketCustomPayload implements Packet
{
    private String channel;
    private PacketBuffer data;
    private static final String __OBFID;
    
    public S3FPacketCustomPayload() {
    }
    
    public S3FPacketCustomPayload(final String channel, final PacketBuffer data) {
        this.channel = channel;
        this.data = data;
        if (data.writerIndex() > 1048576) {
            throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.channel = packetBuffer.readStringFromBuffer(20);
        final int readableBytes = packetBuffer.readableBytes();
        if (readableBytes >= 0 && readableBytes <= 1048576) {
            this.data = new PacketBuffer(packetBuffer.readBytes(readableBytes));
            return;
        }
        throw new IOException("Payload may not be larger than 1048576 bytes");
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.channel);
        packetBuffer.writeBytes(this.data);
    }
    
    public void process(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleCustomPayload(this);
    }
    
    public String getChannelName() {
        return this.channel;
    }
    
    public PacketBuffer getBufferData() {
        return this.data;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.process((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001297";
    }
}
