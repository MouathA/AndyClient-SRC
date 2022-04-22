package net.minecraft.network.play.server;

import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.world.*;
import net.minecraft.network.*;

public class S19PacketEntityStatus implements Packet
{
    private int field_149164_a;
    private byte field_149163_b;
    private static final String __OBFID;
    
    public S19PacketEntityStatus() {
    }
    
    public S19PacketEntityStatus(final Entity entity, final byte field_149163_b) {
        this.field_149164_a = entity.getEntityId();
        this.field_149163_b = field_149163_b;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149164_a = packetBuffer.readInt();
        this.field_149163_b = packetBuffer.readByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.field_149164_a);
        packetBuffer.writeByte(this.field_149163_b);
    }
    
    public void func_180736_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityStatus(this);
    }
    
    public Entity func_149161_a(final World world) {
        return world.getEntityByID(this.field_149164_a);
    }
    
    public byte func_149160_c() {
        return this.field_149163_b;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180736_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001299";
    }
}
