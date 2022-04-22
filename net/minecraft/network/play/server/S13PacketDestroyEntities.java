package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S13PacketDestroyEntities implements Packet
{
    private int[] field_149100_a;
    private static final String __OBFID;
    
    public S13PacketDestroyEntities() {
    }
    
    public S13PacketDestroyEntities(final int... field_149100_a) {
        this.field_149100_a = field_149100_a;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149100_a = new int[packetBuffer.readVarIntFromBuffer()];
        while (0 < this.field_149100_a.length) {
            this.field_149100_a[0] = packetBuffer.readVarIntFromBuffer();
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_149100_a.length);
        while (0 < this.field_149100_a.length) {
            packetBuffer.writeVarIntToBuffer(this.field_149100_a[0]);
            int n = 0;
            ++n;
        }
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleDestroyEntities(this);
    }
    
    public int[] func_149098_c() {
        return this.field_149100_a;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001320";
    }
}
