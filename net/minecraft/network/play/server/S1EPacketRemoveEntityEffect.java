package net.minecraft.network.play.server;

import net.minecraft.potion.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S1EPacketRemoveEntityEffect implements Packet
{
    private int field_149079_a;
    private int field_149078_b;
    private static final String __OBFID;
    
    public S1EPacketRemoveEntityEffect() {
    }
    
    public S1EPacketRemoveEntityEffect(final int field_149079_a, final PotionEffect potionEffect) {
        this.field_149079_a = field_149079_a;
        this.field_149078_b = potionEffect.getPotionID();
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149079_a = packetBuffer.readVarIntFromBuffer();
        this.field_149078_b = packetBuffer.readUnsignedByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_149079_a);
        packetBuffer.writeByte(this.field_149078_b);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleRemoveEntityEffect(this);
    }
    
    public int func_149076_c() {
        return this.field_149079_a;
    }
    
    public int func_149075_d() {
        return this.field_149078_b;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001321";
    }
}
