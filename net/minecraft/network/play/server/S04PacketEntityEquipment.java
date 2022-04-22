package net.minecraft.network.play.server;

import net.minecraft.item.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S04PacketEntityEquipment implements Packet
{
    private int field_149394_a;
    private int field_149392_b;
    private ItemStack field_149393_c;
    private static final String __OBFID;
    
    public S04PacketEntityEquipment() {
    }
    
    public S04PacketEntityEquipment(final int field_149394_a, final int field_149392_b, final ItemStack itemStack) {
        this.field_149394_a = field_149394_a;
        this.field_149392_b = field_149392_b;
        this.field_149393_c = ((itemStack == null) ? null : itemStack.copy());
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149394_a = packetBuffer.readVarIntFromBuffer();
        this.field_149392_b = packetBuffer.readShort();
        this.field_149393_c = packetBuffer.readItemStackFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_149394_a);
        packetBuffer.writeShort(this.field_149392_b);
        packetBuffer.writeItemStackToBuffer(this.field_149393_c);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityEquipment(this);
    }
    
    public ItemStack func_149390_c() {
        return this.field_149393_c;
    }
    
    public int func_149389_d() {
        return this.field_149394_a;
    }
    
    public int func_149388_e() {
        return this.field_149392_b;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001330";
    }
}
