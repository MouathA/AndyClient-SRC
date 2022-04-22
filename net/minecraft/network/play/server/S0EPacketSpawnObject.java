package net.minecraft.network.play.server;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S0EPacketSpawnObject implements Packet
{
    private int field_149018_a;
    private int field_149016_b;
    private int field_149017_c;
    private int field_149014_d;
    private int field_149015_e;
    private int field_149012_f;
    private int field_149013_g;
    private int field_149021_h;
    private int field_149022_i;
    private int field_149019_j;
    private int field_149020_k;
    private static final String __OBFID;
    
    public S0EPacketSpawnObject() {
    }
    
    public S0EPacketSpawnObject(final Entity entity, final int n) {
        this(entity, n, 0);
    }
    
    public S0EPacketSpawnObject(final Entity entity, final int field_149019_j, final int field_149020_k) {
        this.field_149018_a = entity.getEntityId();
        this.field_149016_b = MathHelper.floor_double(entity.posX * 32.0);
        this.field_149017_c = MathHelper.floor_double(entity.posY * 32.0);
        this.field_149014_d = MathHelper.floor_double(entity.posZ * 32.0);
        this.field_149021_h = MathHelper.floor_float(entity.rotationPitch * 256.0f / 360.0f);
        this.field_149022_i = MathHelper.floor_float(entity.rotationYaw * 256.0f / 360.0f);
        this.field_149019_j = field_149019_j;
        this.field_149020_k = field_149020_k;
        if (field_149020_k > 0) {
            double motionX = entity.motionX;
            double motionY = entity.motionY;
            double motionZ = entity.motionZ;
            final double n = 3.9;
            if (motionX < -n) {
                motionX = -n;
            }
            if (motionY < -n) {
                motionY = -n;
            }
            if (motionZ < -n) {
                motionZ = -n;
            }
            if (motionX > n) {
                motionX = n;
            }
            if (motionY > n) {
                motionY = n;
            }
            if (motionZ > n) {
                motionZ = n;
            }
            this.field_149015_e = (int)(motionX * 8000.0);
            this.field_149012_f = (int)(motionY * 8000.0);
            this.field_149013_g = (int)(motionZ * 8000.0);
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149018_a = packetBuffer.readVarIntFromBuffer();
        this.field_149019_j = packetBuffer.readByte();
        this.field_149016_b = packetBuffer.readInt();
        this.field_149017_c = packetBuffer.readInt();
        this.field_149014_d = packetBuffer.readInt();
        this.field_149021_h = packetBuffer.readByte();
        this.field_149022_i = packetBuffer.readByte();
        this.field_149020_k = packetBuffer.readInt();
        if (this.field_149020_k > 0) {
            this.field_149015_e = packetBuffer.readShort();
            this.field_149012_f = packetBuffer.readShort();
            this.field_149013_g = packetBuffer.readShort();
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_149018_a);
        packetBuffer.writeByte(this.field_149019_j);
        packetBuffer.writeInt(this.field_149016_b);
        packetBuffer.writeInt(this.field_149017_c);
        packetBuffer.writeInt(this.field_149014_d);
        packetBuffer.writeByte(this.field_149021_h);
        packetBuffer.writeByte(this.field_149022_i);
        packetBuffer.writeInt(this.field_149020_k);
        if (this.field_149020_k > 0) {
            packetBuffer.writeShort(this.field_149015_e);
            packetBuffer.writeShort(this.field_149012_f);
            packetBuffer.writeShort(this.field_149013_g);
        }
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnObject(this);
    }
    
    public int func_149001_c() {
        return this.field_149018_a;
    }
    
    public int func_148997_d() {
        return this.field_149016_b;
    }
    
    public int func_148998_e() {
        return this.field_149017_c;
    }
    
    public int func_148994_f() {
        return this.field_149014_d;
    }
    
    public int func_149010_g() {
        return this.field_149015_e;
    }
    
    public int func_149004_h() {
        return this.field_149012_f;
    }
    
    public int func_148999_i() {
        return this.field_149013_g;
    }
    
    public int func_149008_j() {
        return this.field_149021_h;
    }
    
    public int func_149006_k() {
        return this.field_149022_i;
    }
    
    public int func_148993_l() {
        return this.field_149019_j;
    }
    
    public int func_149009_m() {
        return this.field_149020_k;
    }
    
    public void func_148996_a(final int field_149016_b) {
        this.field_149016_b = field_149016_b;
    }
    
    public void func_148995_b(final int field_149017_c) {
        this.field_149017_c = field_149017_c;
    }
    
    public void func_149005_c(final int field_149014_d) {
        this.field_149014_d = field_149014_d;
    }
    
    public void func_149003_d(final int field_149015_e) {
        this.field_149015_e = field_149015_e;
    }
    
    public void func_149000_e(final int field_149012_f) {
        this.field_149012_f = field_149012_f;
    }
    
    public void func_149007_f(final int field_149013_g) {
        this.field_149013_g = field_149013_g;
    }
    
    public void func_149002_g(final int field_149020_k) {
        this.field_149020_k = field_149020_k;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001276";
    }
}
