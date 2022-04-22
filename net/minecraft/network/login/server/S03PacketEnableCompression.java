package net.minecraft.network.login.server;

import java.io.*;
import net.minecraft.network.login.*;
import net.minecraft.network.*;

public class S03PacketEnableCompression implements Packet
{
    private int field_179733_a;
    private static final String __OBFID;
    
    public S03PacketEnableCompression() {
    }
    
    public S03PacketEnableCompression(final int field_179733_a) {
        this.field_179733_a = field_179733_a;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179733_a = packetBuffer.readVarIntFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_179733_a);
    }
    
    public void func_179732_a(final INetHandlerLoginClient netHandlerLoginClient) {
        netHandlerLoginClient.func_180464_a(this);
    }
    
    public int func_179731_a() {
        return this.field_179733_a;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_179732_a((INetHandlerLoginClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00002279";
    }
}
