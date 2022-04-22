package net.minecraft.network.play.server;

import net.minecraft.util.*;
import net.minecraft.nbt.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S35PacketUpdateTileEntity implements Packet
{
    private BlockPos field_179824_a;
    private int metadata;
    private NBTTagCompound nbt;
    private static final String __OBFID;
    
    public S35PacketUpdateTileEntity() {
    }
    
    public S35PacketUpdateTileEntity(final BlockPos field_179824_a, final int metadata, final NBTTagCompound nbt) {
        this.field_179824_a = field_179824_a;
        this.metadata = metadata;
        this.nbt = nbt;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179824_a = packetBuffer.readBlockPos();
        this.metadata = packetBuffer.readUnsignedByte();
        this.nbt = packetBuffer.readNBTTagCompoundFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.field_179824_a);
        packetBuffer.writeByte((byte)this.metadata);
        packetBuffer.writeNBTTagCompoundToBuffer(this.nbt);
    }
    
    public void func_180725_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleUpdateTileEntity(this);
    }
    
    public BlockPos func_179823_a() {
        return this.field_179824_a;
    }
    
    public int getTileEntityType() {
        return this.metadata;
    }
    
    public NBTTagCompound getNbtCompound() {
        return this.nbt;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180725_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001285";
    }
}
