package net.minecraft.network.play.server;

import net.minecraft.util.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S2DPacketOpenWindow implements Packet
{
    private int windowId;
    private String inventoryType;
    private IChatComponent windowTitle;
    private int slotCount;
    private int entityId;
    private static final String __OBFID;
    
    public S2DPacketOpenWindow() {
    }
    
    public S2DPacketOpenWindow(final int n, final String s, final IChatComponent chatComponent) {
        this(n, s, chatComponent, 0);
    }
    
    public S2DPacketOpenWindow(final int windowId, final String inventoryType, final IChatComponent windowTitle, final int slotCount) {
        this.windowId = windowId;
        this.inventoryType = inventoryType;
        this.windowTitle = windowTitle;
        this.slotCount = slotCount;
    }
    
    public S2DPacketOpenWindow(final int n, final String s, final IChatComponent chatComponent, final int n2, final int entityId) {
        this(n, s, chatComponent, n2);
        this.entityId = entityId;
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleOpenWindow(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readUnsignedByte();
        this.inventoryType = packetBuffer.readStringFromBuffer(32);
        this.windowTitle = packetBuffer.readChatComponent();
        this.slotCount = packetBuffer.readUnsignedByte();
        if (this.inventoryType.equals("EntityHorse")) {
            this.entityId = packetBuffer.readInt();
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeString(this.inventoryType);
        packetBuffer.writeChatComponent(this.windowTitle);
        packetBuffer.writeByte(this.slotCount);
        if (this.inventoryType.equals("EntityHorse")) {
            packetBuffer.writeInt(this.entityId);
        }
    }
    
    public int func_148901_c() {
        return this.windowId;
    }
    
    public String func_148902_e() {
        return this.inventoryType;
    }
    
    public IChatComponent func_179840_c() {
        return this.windowTitle;
    }
    
    public int func_148898_f() {
        return this.slotCount;
    }
    
    public int func_148897_h() {
        return this.entityId;
    }
    
    public boolean func_148900_g() {
        return this.slotCount > 0;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001293";
    }
}
