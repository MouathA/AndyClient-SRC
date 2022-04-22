package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S32PacketConfirmTransaction implements Packet
{
    private int field_148894_a;
    private short field_148892_b;
    private boolean field_148893_c;
    private static final String __OBFID;
    
    public S32PacketConfirmTransaction() {
    }
    
    public S32PacketConfirmTransaction(final int field_148894_a, final short field_148892_b, final boolean field_148893_c) {
        this.field_148894_a = field_148894_a;
        this.field_148892_b = field_148892_b;
        this.field_148893_c = field_148893_c;
    }
    
    public void func_180730_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleConfirmTransaction(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_148894_a = packetBuffer.readUnsignedByte();
        this.field_148892_b = packetBuffer.readShort();
        this.field_148893_c = packetBuffer.readBoolean();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.field_148894_a);
        packetBuffer.writeShort(this.field_148892_b);
        packetBuffer.writeBoolean(this.field_148893_c);
    }
    
    public int func_148889_c() {
        return this.field_148894_a;
    }
    
    public short func_148890_d() {
        return this.field_148892_b;
    }
    
    public boolean func_148888_e() {
        return this.field_148893_c;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180730_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001291";
    }
}
