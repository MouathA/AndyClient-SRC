package net.minecraft.network.status.client;

import java.io.*;
import net.minecraft.network.status.*;
import net.minecraft.network.*;

public class C00PacketServerQuery implements Packet
{
    private static final String __OBFID;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
    }
    
    public void func_180775_a(final INetHandlerStatusServer netHandlerStatusServer) {
        netHandlerStatusServer.processServerQuery(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180775_a((INetHandlerStatusServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001393";
    }
}
