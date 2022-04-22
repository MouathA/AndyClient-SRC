package net.minecraft.network.status.server;

import java.io.*;
import net.minecraft.network.status.*;
import net.minecraft.network.*;

public class S01PacketPong implements Packet
{
    private long clientTime;
    private static final String __OBFID;
    
    public S01PacketPong() {
    }
    
    public S01PacketPong(final long clientTime) {
        this.clientTime = clientTime;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.clientTime = packetBuffer.readLong();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeLong(this.clientTime);
    }
    
    public void processPacket(final INetHandlerStatusClient netHandlerStatusClient) {
        netHandlerStatusClient.handlePong(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerStatusClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001383";
    }
}
