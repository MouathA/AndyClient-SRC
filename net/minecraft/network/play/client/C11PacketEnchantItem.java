package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C11PacketEnchantItem implements Packet
{
    private int id;
    private int button;
    private static final String __OBFID;
    
    public C11PacketEnchantItem() {
    }
    
    public C11PacketEnchantItem(final int id, final int button) {
        this.id = id;
        this.button = button;
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processEnchantItem(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.id = packetBuffer.readByte();
        this.button = packetBuffer.readByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.id);
        packetBuffer.writeByte(this.button);
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getButton() {
        return this.button;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001352";
    }
}
