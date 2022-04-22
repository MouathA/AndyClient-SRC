package net.minecraft.network.play.server;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S02PacketChat implements Packet
{
    private IChatComponent chatComponent;
    private byte field_179842_b;
    private static final String __OBFID;
    private byte type;
    
    public S02PacketChat() {
    }
    
    public S02PacketChat(final IChatComponent chatComponent) {
        this(chatComponent, (byte)1);
    }
    
    public S02PacketChat(final IChatComponent chatComponent, final byte field_179842_b) {
        this.chatComponent = chatComponent;
        this.field_179842_b = field_179842_b;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.chatComponent = packetBuffer.readChatComponent();
        this.field_179842_b = packetBuffer.readByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeChatComponent(this.chatComponent);
        packetBuffer.writeByte(this.field_179842_b);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleChat(this);
    }
    
    public IChatComponent func_148915_c() {
        return this.chatComponent;
    }
    
    public boolean isChat() {
        return this.field_179842_b == 1 || this.field_179842_b == 2;
    }
    
    public byte func_179841_c() {
        return this.field_179842_b;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public byte getType() {
        return this.type;
    }
    
    public IChatComponent getChatComponent() {
        return this.chatComponent;
    }
    
    static {
        __OBFID = "CL_00001289";
    }
}
