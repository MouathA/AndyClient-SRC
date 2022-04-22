package net.minecraft.network.play.client;

import net.minecraft.item.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C10PacketCreativeInventoryAction implements Packet
{
    private int slotId;
    private ItemStack stack;
    private static final String __OBFID;
    
    public C10PacketCreativeInventoryAction() {
    }
    
    public C10PacketCreativeInventoryAction(final int slotId, final ItemStack itemStack) {
        this.slotId = slotId;
        this.stack = ((itemStack != null) ? itemStack.copy() : null);
    }
    
    public void func_180767_a(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processCreativeInventoryAction(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.slotId = packetBuffer.readShort();
        this.stack = packetBuffer.readItemStackFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeShort(this.slotId);
        packetBuffer.writeItemStackToBuffer(this.stack);
    }
    
    public int getSlotId() {
        return this.slotId;
    }
    
    public ItemStack getStack() {
        return this.stack;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180767_a((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001369";
    }
}
