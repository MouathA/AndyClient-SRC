package net.minecraft.network.play.server;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S2APacketParticles implements Packet
{
    private EnumParticleTypes field_179751_a;
    private float field_149234_b;
    private float field_149235_c;
    private float field_149232_d;
    private float field_149233_e;
    private float field_149230_f;
    private float field_149231_g;
    private float field_149237_h;
    private int field_149238_i;
    private boolean field_179752_j;
    private int[] field_179753_k;
    private static final String __OBFID;
    
    public S2APacketParticles() {
    }
    
    public S2APacketParticles(final EnumParticleTypes field_179751_a, final boolean field_179752_j, final float field_149234_b, final float field_149235_c, final float field_149232_d, final float field_149233_e, final float field_149230_f, final float field_149231_g, final float field_149237_h, final int field_149238_i, final int... field_179753_k) {
        this.field_179751_a = field_179751_a;
        this.field_179752_j = field_179752_j;
        this.field_149234_b = field_149234_b;
        this.field_149235_c = field_149235_c;
        this.field_149232_d = field_149232_d;
        this.field_149233_e = field_149233_e;
        this.field_149230_f = field_149230_f;
        this.field_149231_g = field_149231_g;
        this.field_149237_h = field_149237_h;
        this.field_149238_i = field_149238_i;
        this.field_179753_k = field_179753_k;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179751_a = EnumParticleTypes.func_179342_a(packetBuffer.readInt());
        if (this.field_179751_a == null) {
            this.field_179751_a = EnumParticleTypes.BARRIER;
        }
        this.field_179752_j = packetBuffer.readBoolean();
        this.field_149234_b = packetBuffer.readFloat();
        this.field_149235_c = packetBuffer.readFloat();
        this.field_149232_d = packetBuffer.readFloat();
        this.field_149233_e = packetBuffer.readFloat();
        this.field_149230_f = packetBuffer.readFloat();
        this.field_149231_g = packetBuffer.readFloat();
        this.field_149237_h = packetBuffer.readFloat();
        this.field_149238_i = packetBuffer.readInt();
        final int func_179345_d = this.field_179751_a.func_179345_d();
        this.field_179753_k = new int[func_179345_d];
        while (0 < func_179345_d) {
            this.field_179753_k[0] = packetBuffer.readVarIntFromBuffer();
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.field_179751_a.func_179348_c());
        packetBuffer.writeBoolean(this.field_179752_j);
        packetBuffer.writeFloat(this.field_149234_b);
        packetBuffer.writeFloat(this.field_149235_c);
        packetBuffer.writeFloat(this.field_149232_d);
        packetBuffer.writeFloat(this.field_149233_e);
        packetBuffer.writeFloat(this.field_149230_f);
        packetBuffer.writeFloat(this.field_149231_g);
        packetBuffer.writeFloat(this.field_149237_h);
        packetBuffer.writeInt(this.field_149238_i);
        while (0 < this.field_179751_a.func_179345_d()) {
            packetBuffer.writeVarIntToBuffer(this.field_179753_k[0]);
            int n = 0;
            ++n;
        }
    }
    
    public EnumParticleTypes func_179749_a() {
        return this.field_179751_a;
    }
    
    public boolean func_179750_b() {
        return this.field_179752_j;
    }
    
    public double func_149220_d() {
        return this.field_149234_b;
    }
    
    public double func_149226_e() {
        return this.field_149235_c;
    }
    
    public double func_149225_f() {
        return this.field_149232_d;
    }
    
    public float func_149221_g() {
        return this.field_149233_e;
    }
    
    public float func_149224_h() {
        return this.field_149230_f;
    }
    
    public float func_149223_i() {
        return this.field_149231_g;
    }
    
    public float func_149227_j() {
        return this.field_149237_h;
    }
    
    public int func_149222_k() {
        return this.field_149238_i;
    }
    
    public int[] func_179748_k() {
        return this.field_179753_k;
    }
    
    public void func_180740_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleParticles(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180740_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001308";
    }
}
