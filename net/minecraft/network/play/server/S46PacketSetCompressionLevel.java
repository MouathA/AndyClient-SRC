package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S46PacketSetCompressionLevel implements Packet
{
    private int field_179761_a;
    private static final String __OBFID;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179761_a = packetBuffer.readVarIntFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_179761_a);
    }
    
    public void func_179759_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.func_175100_a(this);
    }
    
    public int func_179760_a() {
        return this.field_179761_a;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_179759_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00002300";
    }
}
