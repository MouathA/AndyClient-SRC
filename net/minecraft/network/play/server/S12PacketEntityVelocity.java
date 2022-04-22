package net.minecraft.network.play.server;

import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S12PacketEntityVelocity implements Packet
{
    private int field_149417_a;
    private int field_149415_b;
    private int field_149416_c;
    private int field_149414_d;
    private static final String __OBFID;
    
    public S12PacketEntityVelocity() {
    }
    
    public S12PacketEntityVelocity(final Entity entity) {
        this(entity.getEntityId(), entity.motionX, entity.motionY, entity.motionZ);
    }
    
    public S12PacketEntityVelocity(final int field_149417_a, double n, double n2, double n3) {
        this.field_149417_a = field_149417_a;
        final double n4 = 3.9;
        if (n < -n4) {
            n = -n4;
        }
        if (n2 < -n4) {
            n2 = -n4;
        }
        if (n3 < -n4) {
            n3 = -n4;
        }
        if (n > n4) {
            n = n4;
        }
        if (n2 > n4) {
            n2 = n4;
        }
        if (n3 > n4) {
            n3 = n4;
        }
        this.field_149415_b = (int)(n * 8000.0);
        this.field_149416_c = (int)(n2 * 8000.0);
        this.field_149414_d = (int)(n3 * 8000.0);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149417_a = packetBuffer.readVarIntFromBuffer();
        this.field_149415_b = packetBuffer.readShort();
        this.field_149416_c = packetBuffer.readShort();
        this.field_149414_d = packetBuffer.readShort();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_149417_a);
        packetBuffer.writeShort(this.field_149415_b);
        packetBuffer.writeShort(this.field_149416_c);
        packetBuffer.writeShort(this.field_149414_d);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityVelocity(this);
    }
    
    public int func_149412_c() {
        return this.field_149417_a;
    }
    
    public int func_149411_d() {
        return this.field_149415_b;
    }
    
    public int func_149410_e() {
        return this.field_149416_c;
    }
    
    public int func_149409_f() {
        return this.field_149414_d;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001328";
    }
}
