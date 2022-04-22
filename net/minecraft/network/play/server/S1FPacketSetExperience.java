package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S1FPacketSetExperience implements Packet
{
    private float field_149401_a;
    private int field_149399_b;
    private int field_149400_c;
    private static final String __OBFID;
    
    public S1FPacketSetExperience() {
    }
    
    public S1FPacketSetExperience(final float field_149401_a, final int field_149399_b, final int field_149400_c) {
        this.field_149401_a = field_149401_a;
        this.field_149399_b = field_149399_b;
        this.field_149400_c = field_149400_c;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149401_a = packetBuffer.readFloat();
        this.field_149400_c = packetBuffer.readVarIntFromBuffer();
        this.field_149399_b = packetBuffer.readVarIntFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat(this.field_149401_a);
        packetBuffer.writeVarIntToBuffer(this.field_149400_c);
        packetBuffer.writeVarIntToBuffer(this.field_149399_b);
    }
    
    public void func_180749_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSetExperience(this);
    }
    
    public float func_149397_c() {
        return this.field_149401_a;
    }
    
    public int func_149396_d() {
        return this.field_149399_b;
    }
    
    public int func_149395_e() {
        return this.field_149400_c;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180749_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001331";
    }
}
