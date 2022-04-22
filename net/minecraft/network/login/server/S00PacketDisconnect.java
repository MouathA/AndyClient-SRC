package net.minecraft.network.login.server;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.login.*;
import net.minecraft.network.*;

public class S00PacketDisconnect implements Packet
{
    private IChatComponent reason;
    private static final String __OBFID;
    
    public S00PacketDisconnect() {
    }
    
    public S00PacketDisconnect(final IChatComponent reason) {
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
    
    public void func_180772_a(final INetHandlerLoginClient netHandlerLoginClient) {
        netHandlerLoginClient.handleDisconnect(this);
    }
    
    public IChatComponent func_149603_c() {
        return this.reason;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180772_a((INetHandlerLoginClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001377";
    }
}
