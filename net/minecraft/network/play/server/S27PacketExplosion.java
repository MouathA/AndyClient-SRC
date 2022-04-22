package net.minecraft.network.play.server;

import com.google.common.collect.*;
import net.minecraft.util.*;
import java.io.*;
import java.util.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S27PacketExplosion implements Packet
{
    private double field_149158_a;
    private double field_149156_b;
    private double field_149157_c;
    private float field_149154_d;
    private List field_149155_e;
    private float field_149152_f;
    private float field_149153_g;
    private float field_149159_h;
    private static final String __OBFID;
    
    public S27PacketExplosion() {
    }
    
    public S27PacketExplosion(final double field_149158_a, final double field_149156_b, final double field_149157_c, final float field_149154_d, final List list, final Vec3 vec3) {
        this.field_149158_a = field_149158_a;
        this.field_149156_b = field_149156_b;
        this.field_149157_c = field_149157_c;
        this.field_149154_d = field_149154_d;
        this.field_149155_e = Lists.newArrayList(list);
        if (vec3 != null) {
            this.field_149152_f = (float)vec3.xCoord;
            this.field_149153_g = (float)vec3.yCoord;
            this.field_149159_h = (float)vec3.zCoord;
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149158_a = packetBuffer.readFloat();
        this.field_149156_b = packetBuffer.readFloat();
        this.field_149157_c = packetBuffer.readFloat();
        this.field_149154_d = packetBuffer.readFloat();
        final int int1 = packetBuffer.readInt();
        this.field_149155_e = Lists.newArrayListWithCapacity(int1);
        final int n = (int)this.field_149158_a;
        final int n2 = (int)this.field_149156_b;
        final int n3 = (int)this.field_149157_c;
        while (0 < int1) {
            this.field_149155_e.add(new BlockPos(packetBuffer.readByte() + n, packetBuffer.readByte() + n2, packetBuffer.readByte() + n3));
            int n4 = 0;
            ++n4;
        }
        this.field_149152_f = packetBuffer.readFloat();
        this.field_149153_g = packetBuffer.readFloat();
        this.field_149159_h = packetBuffer.readFloat();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat((float)this.field_149158_a);
        packetBuffer.writeFloat((float)this.field_149156_b);
        packetBuffer.writeFloat((float)this.field_149157_c);
        packetBuffer.writeFloat(this.field_149154_d);
        packetBuffer.writeInt(this.field_149155_e.size());
        final int n = (int)this.field_149158_a;
        final int n2 = (int)this.field_149156_b;
        final int n3 = (int)this.field_149157_c;
        for (final BlockPos blockPos : this.field_149155_e) {
            final int n4 = blockPos.getX() - n;
            final int n5 = blockPos.getY() - n2;
            final int n6 = blockPos.getZ() - n3;
            packetBuffer.writeByte(n4);
            packetBuffer.writeByte(n5);
            packetBuffer.writeByte(n6);
        }
        packetBuffer.writeFloat(this.field_149152_f);
        packetBuffer.writeFloat(this.field_149153_g);
        packetBuffer.writeFloat(this.field_149159_h);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleExplosion(this);
    }
    
    public float func_149149_c() {
        return this.field_149152_f;
    }
    
    public float func_149144_d() {
        return this.field_149153_g;
    }
    
    public float func_149147_e() {
        return this.field_149159_h;
    }
    
    public double func_149148_f() {
        return this.field_149158_a;
    }
    
    public double func_149143_g() {
        return this.field_149156_b;
    }
    
    public double func_149145_h() {
        return this.field_149157_c;
    }
    
    public float func_149146_i() {
        return this.field_149154_d;
    }
    
    public List func_149150_j() {
        return this.field_149155_e;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001300";
    }
}
