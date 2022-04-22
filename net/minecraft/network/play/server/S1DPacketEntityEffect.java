package net.minecraft.network.play.server;

import net.minecraft.potion.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S1DPacketEntityEffect implements Packet
{
    private int field_149434_a;
    private byte field_149432_b;
    private byte field_149433_c;
    private int field_149431_d;
    private byte field_179708_e;
    private static final String __OBFID;
    
    public S1DPacketEntityEffect() {
    }
    
    public S1DPacketEntityEffect(final int field_149434_a, final PotionEffect potionEffect) {
        this.field_149434_a = field_149434_a;
        this.field_149432_b = (byte)(potionEffect.getPotionID() & 0xFF);
        this.field_149433_c = (byte)(potionEffect.getAmplifier() & 0xFF);
        if (potionEffect.getDuration() > 32767) {
            this.field_149431_d = 32767;
        }
        else {
            this.field_149431_d = potionEffect.getDuration();
        }
        this.field_179708_e = (byte)(potionEffect.func_180154_f() ? 1 : 0);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149434_a = packetBuffer.readVarIntFromBuffer();
        this.field_149432_b = packetBuffer.readByte();
        this.field_149433_c = packetBuffer.readByte();
        this.field_149431_d = packetBuffer.readVarIntFromBuffer();
        this.field_179708_e = packetBuffer.readByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_149434_a);
        packetBuffer.writeByte(this.field_149432_b);
        packetBuffer.writeByte(this.field_149433_c);
        packetBuffer.writeVarIntToBuffer(this.field_149431_d);
        packetBuffer.writeByte(this.field_179708_e);
    }
    
    public boolean func_149429_c() {
        return this.field_149431_d == 32767;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityEffect(this);
    }
    
    public int func_149426_d() {
        return this.field_149434_a;
    }
    
    public byte func_149427_e() {
        return this.field_149432_b;
    }
    
    public byte func_149428_f() {
        return this.field_149433_c;
    }
    
    public int func_180755_e() {
        return this.field_149431_d;
    }
    
    public boolean func_179707_f() {
        return this.field_179708_e != 0;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001343";
    }
}
