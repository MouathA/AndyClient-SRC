package net.minecraft.network.play.server;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.effect.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S2CPacketSpawnGlobalEntity implements Packet
{
    private int field_149059_a;
    private int field_149057_b;
    private int field_149058_c;
    private int field_149055_d;
    private int field_149056_e;
    private static final String __OBFID;
    
    public S2CPacketSpawnGlobalEntity() {
    }
    
    public S2CPacketSpawnGlobalEntity(final Entity entity) {
        this.field_149059_a = entity.getEntityId();
        this.field_149057_b = MathHelper.floor_double(entity.posX * 32.0);
        this.field_149058_c = MathHelper.floor_double(entity.posY * 32.0);
        this.field_149055_d = MathHelper.floor_double(entity.posZ * 32.0);
        if (entity instanceof EntityLightningBolt) {
            this.field_149056_e = 1;
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149059_a = packetBuffer.readVarIntFromBuffer();
        this.field_149056_e = packetBuffer.readByte();
        this.field_149057_b = packetBuffer.readInt();
        this.field_149058_c = packetBuffer.readInt();
        this.field_149055_d = packetBuffer.readInt();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_149059_a);
        packetBuffer.writeByte(this.field_149056_e);
        packetBuffer.writeInt(this.field_149057_b);
        packetBuffer.writeInt(this.field_149058_c);
        packetBuffer.writeInt(this.field_149055_d);
    }
    
    public void func_180720_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnGlobalEntity(this);
    }
    
    public int func_149052_c() {
        return this.field_149059_a;
    }
    
    public int func_149051_d() {
        return this.field_149057_b;
    }
    
    public int func_149050_e() {
        return this.field_149058_c;
    }
    
    public int func_149049_f() {
        return this.field_149055_d;
    }
    
    public int func_149053_g() {
        return this.field_149056_e;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180720_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001278";
    }
}
