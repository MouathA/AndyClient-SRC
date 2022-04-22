package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S3APacketTabComplete implements Packet
{
    private String[] field_149632_a;
    private static final String __OBFID;
    
    public S3APacketTabComplete() {
    }
    
    public S3APacketTabComplete(final String[] field_149632_a) {
        this.field_149632_a = field_149632_a;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149632_a = new String[packetBuffer.readVarIntFromBuffer()];
        while (0 < this.field_149632_a.length) {
            this.field_149632_a[0] = packetBuffer.readStringFromBuffer(32767);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_149632_a.length);
        final String[] field_149632_a = this.field_149632_a;
        while (0 < field_149632_a.length) {
            packetBuffer.writeString(field_149632_a[0]);
            int n = 0;
            ++n;
        }
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleTabComplete(this);
    }
    
    public String[] func_149630_c() {
        return this.field_149632_a;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001288";
    }
}
