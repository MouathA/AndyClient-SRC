package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C0DPacketCloseWindow implements Packet
{
    private int windowId;
    private static final String __OBFID;
    
    public C0DPacketCloseWindow() {
    }
    
    public C0DPacketCloseWindow(final int windowId) {
        this.windowId = windowId;
    }
    
    public void func_180759_a(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processCloseWindow(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180759_a((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001354";
    }
}
