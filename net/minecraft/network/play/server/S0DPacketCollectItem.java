package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S0DPacketCollectItem implements Packet
{
    private int field_149357_a;
    private int field_149356_b;
    private static final String __OBFID;
    
    public S0DPacketCollectItem() {
    }
    
    public S0DPacketCollectItem(final int field_149357_a, final int field_149356_b) {
        this.field_149357_a = field_149357_a;
        this.field_149356_b = field_149356_b;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149357_a = packetBuffer.readVarIntFromBuffer();
        this.field_149356_b = packetBuffer.readVarIntFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_149357_a);
        packetBuffer.writeVarIntToBuffer(this.field_149356_b);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleCollectItem(this);
    }
    
    public int func_149354_c() {
        return this.field_149357_a;
    }
    
    public int func_149353_d() {
        return this.field_149356_b;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001339";
    }
}
