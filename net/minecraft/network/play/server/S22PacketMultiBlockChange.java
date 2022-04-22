package net.minecraft.network.play.server;

import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;
import net.minecraft.util.*;

public class S22PacketMultiBlockChange implements Packet
{
    private ChunkCoordIntPair field_148925_b;
    private BlockUpdateData[] field_179845_b;
    private static final String __OBFID;
    
    public S22PacketMultiBlockChange() {
    }
    
    public S22PacketMultiBlockChange(final int n, final short[] array, final Chunk chunk) {
        this.field_148925_b = new ChunkCoordIntPair(chunk.xPosition, chunk.zPosition);
        this.field_179845_b = new BlockUpdateData[n];
        while (0 < this.field_179845_b.length) {
            this.field_179845_b[0] = new BlockUpdateData(array[0], chunk);
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_148925_b = new ChunkCoordIntPair(packetBuffer.readInt(), packetBuffer.readInt());
        this.field_179845_b = new BlockUpdateData[packetBuffer.readVarIntFromBuffer()];
        while (0 < this.field_179845_b.length) {
            this.field_179845_b[0] = new BlockUpdateData(packetBuffer.readShort(), (IBlockState)Block.BLOCK_STATE_IDS.getByValue(packetBuffer.readVarIntFromBuffer()));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.field_148925_b.chunkXPos);
        packetBuffer.writeInt(this.field_148925_b.chunkZPos);
        packetBuffer.writeVarIntToBuffer(this.field_179845_b.length);
        final BlockUpdateData[] field_179845_b = this.field_179845_b;
        while (0 < field_179845_b.length) {
            final BlockUpdateData blockUpdateData = field_179845_b[0];
            packetBuffer.writeShort(blockUpdateData.func_180089_b());
            packetBuffer.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(blockUpdateData.func_180088_c()));
            int n = 0;
            ++n;
        }
    }
    
    public void func_180729_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleMultiBlockChange(this);
    }
    
    public BlockUpdateData[] func_179844_a() {
        return this.field_179845_b;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180729_a((INetHandlerPlayClient)netHandler);
    }
    
    static ChunkCoordIntPair access$0(final S22PacketMultiBlockChange s22PacketMultiBlockChange) {
        return s22PacketMultiBlockChange.field_148925_b;
    }
    
    static {
        __OBFID = "CL_00001290";
    }
    
    public class BlockUpdateData
    {
        private final short field_180091_b;
        private final IBlockState field_180092_c;
        private static final String __OBFID;
        final S22PacketMultiBlockChange this$0;
        
        public BlockUpdateData(final S22PacketMultiBlockChange this$0, final short field_180091_b, final IBlockState field_180092_c) {
            this.this$0 = this$0;
            this.field_180091_b = field_180091_b;
            this.field_180092_c = field_180092_c;
        }
        
        public BlockUpdateData(final S22PacketMultiBlockChange this$0, final short field_180091_b, final Chunk chunk) {
            this.this$0 = this$0;
            this.field_180091_b = field_180091_b;
            this.field_180092_c = chunk.getBlockState(this.func_180090_a());
        }
        
        public BlockPos func_180090_a() {
            return new BlockPos(S22PacketMultiBlockChange.access$0(this.this$0).getBlock(this.field_180091_b >> 12 & 0xF, this.field_180091_b & 0xFF, this.field_180091_b >> 8 & 0xF));
        }
        
        public short func_180089_b() {
            return this.field_180091_b;
        }
        
        public IBlockState func_180088_c() {
            return this.field_180092_c;
        }
        
        static {
            __OBFID = "CL_00002302";
        }
    }
}
