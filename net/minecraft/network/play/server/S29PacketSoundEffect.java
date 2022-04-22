package net.minecraft.network.play.server;

import org.apache.commons.lang3.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S29PacketSoundEffect implements Packet
{
    private String field_149219_a;
    private int field_149217_b;
    private int field_149218_c;
    private int field_149215_d;
    private float field_149216_e;
    private int field_149214_f;
    private static final String __OBFID;
    
    public S29PacketSoundEffect() {
        this.field_149218_c = Integer.MAX_VALUE;
    }
    
    public S29PacketSoundEffect(final String field_149219_a, final double n, final double n2, final double n3, final float field_149216_e, float clamp_float) {
        this.field_149218_c = Integer.MAX_VALUE;
        Validate.notNull(field_149219_a, "name", new Object[0]);
        this.field_149219_a = field_149219_a;
        this.field_149217_b = (int)(n * 8.0);
        this.field_149218_c = (int)(n2 * 8.0);
        this.field_149215_d = (int)(n3 * 8.0);
        this.field_149216_e = field_149216_e;
        this.field_149214_f = (int)(clamp_float * 63.0f);
        clamp_float = MathHelper.clamp_float(clamp_float, 0.0f, 255.0f);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149219_a = packetBuffer.readStringFromBuffer(256);
        this.field_149217_b = packetBuffer.readInt();
        this.field_149218_c = packetBuffer.readInt();
        this.field_149215_d = packetBuffer.readInt();
        this.field_149216_e = packetBuffer.readFloat();
        this.field_149214_f = packetBuffer.readUnsignedByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.field_149219_a);
        packetBuffer.writeInt(this.field_149217_b);
        packetBuffer.writeInt(this.field_149218_c);
        packetBuffer.writeInt(this.field_149215_d);
        packetBuffer.writeFloat(this.field_149216_e);
        packetBuffer.writeByte(this.field_149214_f);
    }
    
    public String func_149212_c() {
        return this.field_149219_a;
    }
    
    public double func_149207_d() {
        return this.field_149217_b / 8.0f;
    }
    
    public double func_149211_e() {
        return this.field_149218_c / 8.0f;
    }
    
    public double func_149210_f() {
        return this.field_149215_d / 8.0f;
    }
    
    public float func_149208_g() {
        return this.field_149216_e;
    }
    
    public float func_149209_h() {
        return this.field_149214_f / 63.0f;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSoundEffect(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001309";
    }
}
