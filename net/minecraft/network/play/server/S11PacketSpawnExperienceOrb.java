package net.minecraft.network.play.server;

import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S11PacketSpawnExperienceOrb implements Packet
{
    private int field_148992_a;
    private int field_148990_b;
    private int field_148991_c;
    private int field_148988_d;
    private int field_148989_e;
    private static final String __OBFID;
    
    public S11PacketSpawnExperienceOrb() {
    }
    
    public S11PacketSpawnExperienceOrb(final EntityXPOrb entityXPOrb) {
        this.field_148992_a = entityXPOrb.getEntityId();
        this.field_148990_b = MathHelper.floor_double(entityXPOrb.posX * 32.0);
        this.field_148991_c = MathHelper.floor_double(entityXPOrb.posY * 32.0);
        this.field_148988_d = MathHelper.floor_double(entityXPOrb.posZ * 32.0);
        this.field_148989_e = entityXPOrb.getXpValue();
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_148992_a = packetBuffer.readVarIntFromBuffer();
        this.field_148990_b = packetBuffer.readInt();
        this.field_148991_c = packetBuffer.readInt();
        this.field_148988_d = packetBuffer.readInt();
        this.field_148989_e = packetBuffer.readShort();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_148992_a);
        packetBuffer.writeInt(this.field_148990_b);
        packetBuffer.writeInt(this.field_148991_c);
        packetBuffer.writeInt(this.field_148988_d);
        packetBuffer.writeShort(this.field_148989_e);
    }
    
    public void func_180719_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnExperienceOrb(this);
    }
    
    public int func_148985_c() {
        return this.field_148992_a;
    }
    
    public int func_148984_d() {
        return this.field_148990_b;
    }
    
    public int func_148983_e() {
        return this.field_148991_c;
    }
    
    public int func_148982_f() {
        return this.field_148988_d;
    }
    
    public int func_148986_g() {
        return this.field_148989_e;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180719_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001277";
    }
}
