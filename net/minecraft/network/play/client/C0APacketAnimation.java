package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C0APacketAnimation implements Packet
{
    private static final String __OBFID;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
    }
    
    public void func_179721_a(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.func_175087_a(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_179721_a((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001345";
    }
}
