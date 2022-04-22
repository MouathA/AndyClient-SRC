package net.minecraft.network.play.server;

import net.minecraft.item.*;
import java.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S30PacketWindowItems implements Packet
{
    private int field_148914_a;
    private ItemStack[] field_148913_b;
    private static final String __OBFID;
    
    public S30PacketWindowItems() {
    }
    
    public S30PacketWindowItems(final int field_148914_a, final List list) {
        this.field_148914_a = field_148914_a;
        this.field_148913_b = new ItemStack[list.size()];
        while (0 < this.field_148913_b.length) {
            final ItemStack itemStack = list.get(0);
            this.field_148913_b[0] = ((itemStack == null) ? null : itemStack.copy());
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_148914_a = packetBuffer.readUnsignedByte();
        final short short1 = packetBuffer.readShort();
        this.field_148913_b = new ItemStack[short1];
        while (0 < short1) {
            this.field_148913_b[0] = packetBuffer.readItemStackFromBuffer();
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.field_148914_a);
        packetBuffer.writeShort(this.field_148913_b.length);
        final ItemStack[] field_148913_b = this.field_148913_b;
        while (0 < field_148913_b.length) {
            packetBuffer.writeItemStackToBuffer(field_148913_b[0]);
            int n = 0;
            ++n;
        }
    }
    
    public void func_180732_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleWindowItems(this);
    }
    
    public int func_148911_c() {
        return this.field_148914_a;
    }
    
    public ItemStack[] func_148910_d() {
        return this.field_148913_b;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180732_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001294";
    }
}
