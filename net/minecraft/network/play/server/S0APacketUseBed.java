package net.minecraft.network.play.server;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.world.*;
import net.minecraft.network.*;

public class S0APacketUseBed implements Packet
{
    private int playerID;
    private BlockPos field_179799_b;
    private static final String __OBFID;
    
    public S0APacketUseBed() {
    }
    
    public S0APacketUseBed(final EntityPlayer entityPlayer, final BlockPos field_179799_b) {
        this.playerID = entityPlayer.getEntityId();
        this.field_179799_b = field_179799_b;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.playerID = packetBuffer.readVarIntFromBuffer();
        this.field_179799_b = packetBuffer.readBlockPos();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.playerID);
        packetBuffer.writeBlockPos(this.field_179799_b);
    }
    
    public void func_180744_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleUseBed(this);
    }
    
    public EntityPlayer getPlayer(final World world) {
        return (EntityPlayer)world.getEntityByID(this.playerID);
    }
    
    public BlockPos func_179798_a() {
        return this.field_179799_b;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180744_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001319";
    }
}
