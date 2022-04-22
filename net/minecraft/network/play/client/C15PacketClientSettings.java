package net.minecraft.network.play.client;

import net.minecraft.entity.player.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C15PacketClientSettings implements Packet
{
    private String lang;
    private int view;
    private EntityPlayer.EnumChatVisibility chatVisibility;
    private boolean enableColors;
    private int field_179711_e;
    private static final String __OBFID;
    
    public C15PacketClientSettings() {
    }
    
    public C15PacketClientSettings(final String lang, final int view, final EntityPlayer.EnumChatVisibility chatVisibility, final boolean enableColors, final int field_179711_e) {
        this.lang = lang;
        this.view = view;
        this.chatVisibility = chatVisibility;
        this.enableColors = enableColors;
        this.field_179711_e = field_179711_e;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.lang = packetBuffer.readStringFromBuffer(7);
        this.view = packetBuffer.readByte();
        this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(packetBuffer.readByte());
        this.enableColors = packetBuffer.readBoolean();
        this.field_179711_e = packetBuffer.readUnsignedByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.lang);
        packetBuffer.writeByte(this.view);
        packetBuffer.writeByte(this.chatVisibility.getChatVisibility());
        packetBuffer.writeBoolean(this.enableColors);
        packetBuffer.writeByte(this.field_179711_e);
    }
    
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processClientSettings(this);
    }
    
    public String getLang() {
        return this.lang;
    }
    
    public EntityPlayer.EnumChatVisibility getChatVisibility() {
        return this.chatVisibility;
    }
    
    public boolean isColorsEnabled() {
        return this.enableColors;
    }
    
    public int getView() {
        return this.field_179711_e;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001350";
    }
}
