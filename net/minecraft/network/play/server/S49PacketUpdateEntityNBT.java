package net.minecraft.network.play.server;

import net.minecraft.nbt.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;

public class S49PacketUpdateEntityNBT implements Packet
{
    private int field_179766_a;
    private NBTTagCompound field_179765_b;
    private static final String __OBFID;
    
    public S49PacketUpdateEntityNBT() {
    }
    
    public S49PacketUpdateEntityNBT(final int field_179766_a, final NBTTagCompound field_179765_b) {
        this.field_179766_a = field_179766_a;
        this.field_179765_b = field_179765_b;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179766_a = packetBuffer.readVarIntFromBuffer();
        this.field_179765_b = packetBuffer.readNBTTagCompoundFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_179766_a);
        packetBuffer.writeNBTTagCompoundToBuffer(this.field_179765_b);
    }
    
    public void func_179762_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.func_175097_a(this);
    }
    
    public NBTTagCompound func_179763_a() {
        return this.field_179765_b;
    }
    
    public Entity func_179764_a(final World world) {
        return world.getEntityByID(this.field_179766_a);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_179762_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00002301";
    }
}
