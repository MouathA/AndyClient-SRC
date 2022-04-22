package net.minecraft.network.play.server;

import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S10PacketSpawnPainting implements Packet
{
    private int field_148973_a;
    private BlockPos field_179838_b;
    private EnumFacing field_179839_c;
    private String field_148968_f;
    private static final String __OBFID;
    
    public S10PacketSpawnPainting() {
    }
    
    public S10PacketSpawnPainting(final EntityPainting entityPainting) {
        this.field_148973_a = entityPainting.getEntityId();
        this.field_179838_b = entityPainting.func_174857_n();
        this.field_179839_c = entityPainting.field_174860_b;
        this.field_148968_f = entityPainting.art.title;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_148973_a = packetBuffer.readVarIntFromBuffer();
        this.field_148968_f = packetBuffer.readStringFromBuffer(EntityPainting.EnumArt.field_180001_A);
        this.field_179838_b = packetBuffer.readBlockPos();
        this.field_179839_c = EnumFacing.getHorizontal(packetBuffer.readUnsignedByte());
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_148973_a);
        packetBuffer.writeString(this.field_148968_f);
        packetBuffer.writeBlockPos(this.field_179838_b);
        packetBuffer.writeByte(this.field_179839_c.getHorizontalIndex());
    }
    
    public void func_180722_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnPainting(this);
    }
    
    public int func_148965_c() {
        return this.field_148973_a;
    }
    
    public BlockPos func_179837_b() {
        return this.field_179838_b;
    }
    
    public EnumFacing func_179836_c() {
        return this.field_179839_c;
    }
    
    public String func_148961_h() {
        return this.field_148968_f;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180722_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001280";
    }
}
