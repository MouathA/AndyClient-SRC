package net.minecraft.network.play.server;

import net.minecraft.util.*;
import net.minecraft.block.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S24PacketBlockAction implements Packet
{
    private BlockPos field_179826_a;
    private int field_148872_d;
    private int field_148873_e;
    private Block field_148871_f;
    private static final String __OBFID;
    
    public S24PacketBlockAction() {
    }
    
    public S24PacketBlockAction(final BlockPos field_179826_a, final Block field_148871_f, final int field_148872_d, final int field_148873_e) {
        this.field_179826_a = field_179826_a;
        this.field_148872_d = field_148872_d;
        this.field_148873_e = field_148873_e;
        this.field_148871_f = field_148871_f;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179826_a = packetBuffer.readBlockPos();
        this.field_148872_d = packetBuffer.readUnsignedByte();
        this.field_148873_e = packetBuffer.readUnsignedByte();
        this.field_148871_f = Block.getBlockById(packetBuffer.readVarIntFromBuffer() & 0xFFF);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.field_179826_a);
        packetBuffer.writeByte(this.field_148872_d);
        packetBuffer.writeByte(this.field_148873_e);
        packetBuffer.writeVarIntToBuffer(Block.getIdFromBlock(this.field_148871_f) & 0xFFF);
    }
    
    public void func_180726_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleBlockAction(this);
    }
    
    public BlockPos func_179825_a() {
        return this.field_179826_a;
    }
    
    public int getData1() {
        return this.field_148872_d;
    }
    
    public int getData2() {
        return this.field_148873_e;
    }
    
    public Block getBlockType() {
        return this.field_148871_f;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180726_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001286";
    }
}
