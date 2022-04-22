package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S31PacketWindowProperty implements Packet
{
    private int field_149186_a;
    private int field_149184_b;
    private int field_149185_c;
    private static final String __OBFID;
    
    public S31PacketWindowProperty() {
    }
    
    public S31PacketWindowProperty(final int field_149186_a, final int field_149184_b, final int field_149185_c) {
        this.field_149186_a = field_149186_a;
        this.field_149184_b = field_149184_b;
        this.field_149185_c = field_149185_c;
    }
    
    public void func_180733_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleWindowProperty(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149186_a = packetBuffer.readUnsignedByte();
        this.field_149184_b = packetBuffer.readShort();
        this.field_149185_c = packetBuffer.readShort();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.field_149186_a);
        packetBuffer.writeShort(this.field_149184_b);
        packetBuffer.writeShort(this.field_149185_c);
    }
    
    public int func_149182_c() {
        return this.field_149186_a;
    }
    
    public int func_149181_d() {
        return this.field_149184_b;
    }
    
    public int func_149180_e() {
        return this.field_149185_c;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180733_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001295";
    }
}
