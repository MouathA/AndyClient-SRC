package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S09PacketHeldItemChange implements Packet
{
    private int field_149387_a;
    private static final String __OBFID;
    
    public S09PacketHeldItemChange() {
    }
    
    public S09PacketHeldItemChange(final int field_149387_a) {
        this.field_149387_a = field_149387_a;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149387_a = packetBuffer.readByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.field_149387_a);
    }
    
    public void func_180746_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleHeldItemChange(this);
    }
    
    public int func_149385_c() {
        return this.field_149387_a;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180746_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001324";
    }
}
