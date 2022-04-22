package net.minecraft.network.status.client;

import java.io.*;
import net.minecraft.network.status.*;
import net.minecraft.network.*;

public class C01PacketPing implements Packet
{
    private long clientTime;
    private static final String __OBFID;
    
    public C01PacketPing() {
    }
    
    public C01PacketPing(final long clientTime) {
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
    
    public void func_180774_a(final INetHandlerStatusServer netHandlerStatusServer) {
        netHandlerStatusServer.processPing(this);
    }
    
    public long getClientTime() {
        return this.clientTime;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180774_a((INetHandlerStatusServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001392";
    }
}
