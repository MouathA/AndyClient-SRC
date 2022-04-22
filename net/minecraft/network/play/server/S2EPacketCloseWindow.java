package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S2EPacketCloseWindow implements Packet
{
    private int field_148896_a;
    private static final String __OBFID;
    
    public S2EPacketCloseWindow() {
    }
    
    public S2EPacketCloseWindow(final int field_148896_a) {
        this.field_148896_a = field_148896_a;
    }
    
    public void func_180731_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleCloseWindow(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_148896_a = packetBuffer.readUnsignedByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.field_148896_a);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180731_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001292";
    }
}
