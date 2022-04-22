package net.minecraft.network.play.server;

import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S1BPacketEntityAttach implements Packet
{
    private int field_149408_a;
    private int field_149406_b;
    private int field_149407_c;
    private static final String __OBFID;
    
    public S1BPacketEntityAttach() {
    }
    
    public S1BPacketEntityAttach(final int field_149408_a, final Entity entity, final Entity entity2) {
        this.field_149408_a = field_149408_a;
        this.field_149406_b = entity.getEntityId();
        this.field_149407_c = ((entity2 != null) ? entity2.getEntityId() : -1);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149406_b = packetBuffer.readInt();
        this.field_149407_c = packetBuffer.readInt();
        this.field_149408_a = packetBuffer.readUnsignedByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.field_149406_b);
        packetBuffer.writeInt(this.field_149407_c);
        packetBuffer.writeByte(this.field_149408_a);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityAttach(this);
    }
    
    public int func_149404_c() {
        return this.field_149408_a;
    }
    
    public int func_149403_d() {
        return this.field_149406_b;
    }
    
    public int func_149402_e() {
        return this.field_149407_c;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001327";
    }
}
