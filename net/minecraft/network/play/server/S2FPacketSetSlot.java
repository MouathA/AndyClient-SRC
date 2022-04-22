package net.minecraft.network.play.server;

import net.minecraft.item.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S2FPacketSetSlot implements Packet
{
    private int field_149179_a;
    private int field_149177_b;
    private ItemStack field_149178_c;
    private static final String __OBFID;
    
    public S2FPacketSetSlot() {
    }
    
    public S2FPacketSetSlot(final int field_149179_a, final int field_149177_b, final ItemStack itemStack) {
        this.field_149179_a = field_149179_a;
        this.field_149177_b = field_149177_b;
        this.field_149178_c = ((itemStack == null) ? null : itemStack.copy());
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSetSlot(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149179_a = packetBuffer.readByte();
        this.field_149177_b = packetBuffer.readShort();
        this.field_149178_c = packetBuffer.readItemStackFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.field_149179_a);
        packetBuffer.writeShort(this.field_149177_b);
        packetBuffer.writeItemStackToBuffer(this.field_149178_c);
    }
    
    public int func_149175_c() {
        return this.field_149179_a;
    }
    
    public int func_149173_d() {
        return this.field_149177_b;
    }
    
    public ItemStack func_149174_e() {
        return this.field_149178_c;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001296";
    }
}
