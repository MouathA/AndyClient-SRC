package net.minecraft.network.play.server;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S40PacketDisconnect implements Packet
{
    private IChatComponent reason;
    private static final String __OBFID;
    
    public S40PacketDisconnect() {
    }
    
    public S40PacketDisconnect(final IChatComponent reason) {
        this.reason = reason;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.reason = packetBuffer.readChatComponent();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeChatComponent(this.reason);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleDisconnect(this);
    }
    
    public IChatComponent func_149165_c() {
        return this.reason;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001298";
    }
}
