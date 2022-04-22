package net.minecraft.network.play.server;

import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S23PacketBlockChange implements Packet
{
    private BlockPos field_179828_a;
    private IBlockState field_148883_d;
    private static final String __OBFID;
    
    public S23PacketBlockChange() {
    }
    
    public S23PacketBlockChange(final World world, final BlockPos field_179828_a) {
        this.field_179828_a = field_179828_a;
        this.field_148883_d = world.getBlockState(field_179828_a);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179828_a = packetBuffer.readBlockPos();
        this.field_148883_d = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(packetBuffer.readVarIntFromBuffer());
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.field_179828_a);
        packetBuffer.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(this.field_148883_d));
    }
    
    public void func_180727_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleBlockChange(this);
    }
    
    public IBlockState func_180728_a() {
        return this.field_148883_d;
    }
    
    public BlockPos func_179827_b() {
        return this.field_179828_a;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180727_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001287";
    }
}
