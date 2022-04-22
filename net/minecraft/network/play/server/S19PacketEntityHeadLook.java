package net.minecraft.network.play.server;

import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.world.*;
import net.minecraft.network.*;

public class S19PacketEntityHeadLook implements Packet
{
    private int field_149384_a;
    private byte field_149383_b;
    private static final String __OBFID;
    
    public S19PacketEntityHeadLook() {
    }
    
    public S19PacketEntityHeadLook(final Entity entity, final byte field_149383_b) {
        this.field_149384_a = entity.getEntityId();
        this.field_149383_b = field_149383_b;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149384_a = packetBuffer.readVarIntFromBuffer();
        this.field_149383_b = packetBuffer.readByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_149384_a);
        packetBuffer.writeByte(this.field_149383_b);
    }
    
    public void func_180745_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityHeadLook(this);
    }
    
    public Entity func_149381_a(final World world) {
        return world.getEntityByID(this.field_149384_a);
    }
    
    public byte func_149380_c() {
        return this.field_149383_b;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180745_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001323";
    }
}
