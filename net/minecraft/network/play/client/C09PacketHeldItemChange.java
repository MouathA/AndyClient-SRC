package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C09PacketHeldItemChange implements Packet
{
    private int slotId;
    private static final String __OBFID;
    
    public C09PacketHeldItemChange() {
    }
    
    public C09PacketHeldItemChange(final int slotId) {
        this.slotId = slotId;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.slotId = packetBuffer.readShort();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeShort(this.slotId);
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processHeldItemChange(this);
    }
    
    public int getSlotId() {
        return this.slotId;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001368";
    }
}
