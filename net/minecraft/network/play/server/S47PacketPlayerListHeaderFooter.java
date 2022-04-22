package net.minecraft.network.play.server;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S47PacketPlayerListHeaderFooter implements Packet
{
    private IChatComponent field_179703_a;
    private IChatComponent field_179702_b;
    private static final String __OBFID;
    
    public S47PacketPlayerListHeaderFooter() {
    }
    
    public S47PacketPlayerListHeaderFooter(final IChatComponent field_179703_a) {
        this.field_179703_a = field_179703_a;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_179703_a = packetBuffer.readChatComponent();
        this.field_179702_b = packetBuffer.readChatComponent();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeChatComponent(this.field_179703_a);
        packetBuffer.writeChatComponent(this.field_179702_b);
    }
    
    public void func_179699_a(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.func_175096_a(this);
    }
    
    public IChatComponent func_179700_a() {
        return this.field_179703_a;
    }
    
    public IChatComponent func_179701_b() {
        return this.field_179702_b;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_179699_a((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00002285";
    }
}
