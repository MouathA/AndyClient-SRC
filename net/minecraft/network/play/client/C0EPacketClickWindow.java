package net.minecraft.network.play.client;

import net.minecraft.item.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C0EPacketClickWindow implements Packet
{
    private int windowId;
    private int slotId;
    private int usedButton;
    private short actionNumber;
    private ItemStack clickedItem;
    private int mode;
    private static final String __OBFID;
    
    public C0EPacketClickWindow() {
    }
    
    public C0EPacketClickWindow(final int windowId, final int slotId, final int usedButton, final int mode, final ItemStack itemStack, final short actionNumber) {
        this.windowId = windowId;
        this.slotId = slotId;
        this.usedButton = usedButton;
        this.clickedItem = ((itemStack != null) ? itemStack.copy() : null);
        this.actionNumber = actionNumber;
        this.mode = mode;
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processClickWindow(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readByte();
        this.slotId = packetBuffer.readShort();
        this.usedButton = packetBuffer.readByte();
        this.actionNumber = packetBuffer.readShort();
        this.mode = packetBuffer.readByte();
        this.clickedItem = packetBuffer.readItemStackFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.slotId);
        packetBuffer.writeByte(this.usedButton);
        packetBuffer.writeShort(this.actionNumber);
        packetBuffer.writeByte(this.mode);
        packetBuffer.writeItemStackToBuffer(this.clickedItem);
    }
    
    public int getWindowId() {
        return this.windowId;
    }
    
    public int getSlotId() {
        return this.slotId;
    }
    
    public int getUsedButton() {
        return this.usedButton;
    }
    
    public short getActionNumber() {
        return this.actionNumber;
    }
    
    public ItemStack getClickedItem() {
        return this.clickedItem;
    }
    
    public int getMode() {
        return this.mode;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001353";
    }
}
