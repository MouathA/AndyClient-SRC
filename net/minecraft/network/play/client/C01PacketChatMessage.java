package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C01PacketChatMessage implements Packet
{
    private String message;
    private static final String __OBFID;
    
    public C01PacketChatMessage() {
    }
    
    public C01PacketChatMessage(String substring) {
        if (substring.length() > 100) {
            substring = substring.substring(0, 100);
        }
        this.message = substring;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.message = packetBuffer.readStringFromBuffer(100);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.message);
    }
    
    public void func_180757_a(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processChatMessage(this);
    }
    
    public String getMessage() {
        return this.message;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180757_a((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001347";
    }
}
