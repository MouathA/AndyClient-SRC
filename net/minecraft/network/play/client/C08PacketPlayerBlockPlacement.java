package net.minecraft.network.play.client;

import net.minecraft.util.*;
import net.minecraft.item.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C08PacketPlayerBlockPlacement implements Packet
{
    private static final BlockPos field_179726_a;
    private BlockPos field_179725_b;
    private int placedBlockDirection;
    private ItemStack stack;
    private float facingX;
    private float facingY;
    private float facingZ;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001371";
        field_179726_a = new BlockPos(-1, -1, -1);
    }
    
    public C08PacketPlayerBlockPlacement() {
    }
    
    public C08PacketPlayerBlockPlacement(final ItemStack itemStack) {
        this(C08PacketPlayerBlockPlacement.field_179726_a, 255, itemStack, 0.0f, 0.0f, 0.0f);
    }
    
    public C08PacketPlayerBlockPlacement(final BlockPos field_179725_b, final int placedBlockDirection, final ItemStack itemStack, final float facingX, final float facingY, final float facingZ) {
        this.field_179725_b = field_179725_b;
        this.placedBlockDirection = placedBlockDirection;
        this.stack = ((itemStack != null) ? itemStack.copy() : null);
        this.facingX = facingX;
        this.facingY = facingY;
        this.facingZ = facingZ;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179725_b = packetBuffer.readBlockPos();
        this.placedBlockDirection = packetBuffer.readUnsignedByte();
        this.stack = packetBuffer.readItemStackFromBuffer();
        this.facingX = packetBuffer.readUnsignedByte() / 16.0f;
        this.facingY = packetBuffer.readUnsignedByte() / 16.0f;
        this.facingZ = packetBuffer.readUnsignedByte() / 16.0f;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.field_179725_b);
        packetBuffer.writeByte(this.placedBlockDirection);
        packetBuffer.writeItemStackToBuffer(this.stack);
        packetBuffer.writeByte((int)(this.facingX * 16.0f));
        packetBuffer.writeByte((int)(this.facingY * 16.0f));
        packetBuffer.writeByte((int)(this.facingZ * 16.0f));
    }
    
    public void func_180769_a(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processPlayerBlockPlacement(this);
    }
    
    public BlockPos func_179724_a() {
        return this.field_179725_b;
    }
    
    public int getPlacedBlockDirection() {
        return this.placedBlockDirection;
    }
    
    public ItemStack getStack() {
        return this.stack;
    }
    
    public float getPlacedBlockOffsetX() {
        return this.facingX;
    }
    
    public float getPlacedBlockOffsetY() {
        return this.facingY;
    }
    
    public float getPlacedBlockOffsetZ() {
        return this.facingZ;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180769_a((INetHandlerPlayServer)netHandler);
    }
}
