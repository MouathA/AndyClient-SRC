package net.minecraft.network.play.server;

import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S0BPacketAnimation implements Packet
{
    private int entityId;
    private int type;
    private static final String __OBFID;
    
    public S0BPacketAnimation() {
    }
    
    public S0BPacketAnimation(final Entity entity, final int type) {
        this.entityId = entity.getEntityId();
        this.type = type;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.type = packetBuffer.readUnsignedByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeByte(this.type);
    }
    
    public void func_180723_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleAnimation(this);
    }
    
    public int func_148978_c() {
        return this.entityId;
    }
    
    public int func_148977_d() {
        return this.type;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180723_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001282";
    }
}
