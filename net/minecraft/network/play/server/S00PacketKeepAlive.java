package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S00PacketKeepAlive implements Packet
{
    private int field_149136_a;
    private static final String __OBFID;
    
    public S00PacketKeepAlive() {
    }
    
    public S00PacketKeepAlive(final int field_149136_a) {
        this.field_149136_a = field_149136_a;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleKeepAlive(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149136_a = packetBuffer.readVarIntFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_149136_a);
    }
    
    public int func_149134_c() {
        return this.field_149136_a;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001303";
    }
}
