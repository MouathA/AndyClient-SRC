package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S03PacketTimeUpdate implements Packet
{
    private long field_149369_a;
    private long field_149368_b;
    private static final String __OBFID;
    
    public S03PacketTimeUpdate() {
    }
    
    public S03PacketTimeUpdate(final long field_149369_a, final long field_149368_b, final boolean b) {
        this.field_149369_a = field_149369_a;
        this.field_149368_b = field_149368_b;
        if (!b) {
            this.field_149368_b = -this.field_149368_b;
            if (this.field_149368_b == 0L) {
                this.field_149368_b = -1L;
            }
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149369_a = packetBuffer.readLong();
        this.field_149368_b = packetBuffer.readLong();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeLong(this.field_149369_a);
        packetBuffer.writeLong(this.field_149368_b);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleTimeUpdate(this);
    }
    
    public long func_149366_c() {
        return this.field_149369_a;
    }
    
    public long func_149365_d() {
        return this.field_149368_b;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001337";
    }
}
