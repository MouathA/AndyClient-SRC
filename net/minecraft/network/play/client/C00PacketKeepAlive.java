package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C00PacketKeepAlive implements Packet
{
    private int key;
    private static final String __OBFID;
    
    public C00PacketKeepAlive() {
    }
    
    public C00PacketKeepAlive(final int key) {
        this.key = key;
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processKeepAlive(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.key = packetBuffer.readVarIntFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.key);
    }
    
    public int getKey() {
        return this.key;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001359";
    }
}
