package net.minecraft.network.play.server;

import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S0FPacketSpawnMob implements Packet
{
    private int field_149042_a;
    private int field_149040_b;
    private int field_149041_c;
    private int field_149038_d;
    private int field_149039_e;
    private int field_149036_f;
    private int field_149037_g;
    private int field_149047_h;
    private byte field_149048_i;
    private byte field_149045_j;
    private byte field_149046_k;
    private DataWatcher field_149043_l;
    private List field_149044_m;
    private static final String __OBFID;
    
    public S0FPacketSpawnMob() {
    }
    
    public S0FPacketSpawnMob(final EntityLivingBase entityLivingBase) {
        this.field_149042_a = entityLivingBase.getEntityId();
        this.field_149040_b = (byte)EntityList.getEntityID(entityLivingBase);
        this.field_149041_c = MathHelper.floor_double(entityLivingBase.posX * 32.0);
        this.field_149038_d = MathHelper.floor_double(entityLivingBase.posY * 32.0);
        this.field_149039_e = MathHelper.floor_double(entityLivingBase.posZ * 32.0);
        this.field_149048_i = (byte)(entityLivingBase.rotationYaw * 256.0f / 360.0f);
        this.field_149045_j = (byte)(entityLivingBase.rotationPitch * 256.0f / 360.0f);
        this.field_149046_k = (byte)(entityLivingBase.rotationYawHead * 256.0f / 360.0f);
        final double n = 3.9;
        double motionX = entityLivingBase.motionX;
        double motionY = entityLivingBase.motionY;
        double motionZ = entityLivingBase.motionZ;
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
        this.field_149036_f = (int)(motionX * 8000.0);
        this.field_149037_g = (int)(motionY * 8000.0);
        this.field_149047_h = (int)(motionZ * 8000.0);
        this.field_149043_l = entityLivingBase.getDataWatcher();
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149042_a = packetBuffer.readVarIntFromBuffer();
        this.field_149040_b = (packetBuffer.readByte() & 0xFF);
        this.field_149041_c = packetBuffer.readInt();
        this.field_149038_d = packetBuffer.readInt();
        this.field_149039_e = packetBuffer.readInt();
        this.field_149048_i = packetBuffer.readByte();
        this.field_149045_j = packetBuffer.readByte();
        this.field_149046_k = packetBuffer.readByte();
        this.field_149036_f = packetBuffer.readShort();
        this.field_149037_g = packetBuffer.readShort();
        this.field_149047_h = packetBuffer.readShort();
        this.field_149044_m = DataWatcher.readWatchedListFromPacketBuffer(packetBuffer);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_149042_a);
        packetBuffer.writeByte(this.field_149040_b & 0xFF);
        packetBuffer.writeInt(this.field_149041_c);
        packetBuffer.writeInt(this.field_149038_d);
        packetBuffer.writeInt(this.field_149039_e);
        packetBuffer.writeByte(this.field_149048_i);
        packetBuffer.writeByte(this.field_149045_j);
        packetBuffer.writeByte(this.field_149046_k);
        packetBuffer.writeShort(this.field_149036_f);
        packetBuffer.writeShort(this.field_149037_g);
        packetBuffer.writeShort(this.field_149047_h);
        this.field_149043_l.writeTo(packetBuffer);
    }
    
    public void func_180721_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnMob(this);
    }
    
    public List func_149027_c() {
        if (this.field_149044_m == null) {
            this.field_149044_m = this.field_149043_l.getAllWatched();
        }
        return this.field_149044_m;
    }
    
    public int func_149024_d() {
        return this.field_149042_a;
    }
    
    public int func_149025_e() {
        return this.field_149040_b;
    }
    
    public int func_149023_f() {
        return this.field_149041_c;
    }
    
    public int func_149034_g() {
        return this.field_149038_d;
    }
    
    public int func_149029_h() {
        return this.field_149039_e;
    }
    
    public int func_149026_i() {
        return this.field_149036_f;
    }
    
    public int func_149033_j() {
        return this.field_149037_g;
    }
    
    public int func_149031_k() {
        return this.field_149047_h;
    }
    
    public byte func_149028_l() {
        return this.field_149048_i;
    }
    
    public byte func_149030_m() {
        return this.field_149045_j;
    }
    
    public byte func_149032_n() {
        return this.field_149046_k;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180721_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001279";
    }
}
