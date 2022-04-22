package net.minecraft.network.play.server;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S18PacketEntityTeleport implements Packet
{
    private int field_149458_a;
    private int field_149456_b;
    private int field_149457_c;
    private int field_149454_d;
    private byte field_149455_e;
    private byte field_149453_f;
    private boolean field_179698_g;
    private static final String __OBFID;
    
    public S18PacketEntityTeleport() {
    }
    
    public S18PacketEntityTeleport(final Entity entity) {
        this.field_149458_a = entity.getEntityId();
        this.field_149456_b = MathHelper.floor_double(entity.posX * 32.0);
        this.field_149457_c = MathHelper.floor_double(entity.posY * 32.0);
        this.field_149454_d = MathHelper.floor_double(entity.posZ * 32.0);
        this.field_149455_e = (byte)(entity.rotationYaw * 256.0f / 360.0f);
        this.field_149453_f = (byte)(entity.rotationPitch * 256.0f / 360.0f);
        this.field_179698_g = entity.onGround;
    }
    
    public S18PacketEntityTeleport(final int field_149458_a, final int field_149456_b, final int field_149457_c, final int field_149454_d, final byte field_149455_e, final byte field_149453_f, final boolean field_179698_g) {
        this.field_149458_a = field_149458_a;
        this.field_149456_b = field_149456_b;
        this.field_149457_c = field_149457_c;
        this.field_149454_d = field_149454_d;
        this.field_149455_e = field_149455_e;
        this.field_149453_f = field_149453_f;
        this.field_179698_g = field_179698_g;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149458_a = packetBuffer.readVarIntFromBuffer();
        this.field_149456_b = packetBuffer.readInt();
        this.field_149457_c = packetBuffer.readInt();
        this.field_149454_d = packetBuffer.readInt();
        this.field_149455_e = packetBuffer.readByte();
        this.field_149453_f = packetBuffer.readByte();
        this.field_179698_g = packetBuffer.readBoolean();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_149458_a);
        packetBuffer.writeInt(this.field_149456_b);
        packetBuffer.writeInt(this.field_149457_c);
        packetBuffer.writeInt(this.field_149454_d);
        packetBuffer.writeByte(this.field_149455_e);
        packetBuffer.writeByte(this.field_149453_f);
        packetBuffer.writeBoolean(this.field_179698_g);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityTeleport(this);
    }
    
    public int func_149451_c() {
        return this.field_149458_a;
    }
    
    public int func_149449_d() {
        return this.field_149456_b;
    }
    
    public int func_149448_e() {
        return this.field_149457_c;
    }
    
    public int func_149446_f() {
        return this.field_149454_d;
    }
    
    public byte func_149450_g() {
        return this.field_149455_e;
    }
    
    public byte func_149447_h() {
        return this.field_149453_f;
    }
    
    public boolean func_179697_g() {
        return this.field_179698_g;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001340";
    }
}
