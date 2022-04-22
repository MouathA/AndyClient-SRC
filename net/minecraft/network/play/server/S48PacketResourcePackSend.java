package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S48PacketResourcePackSend implements Packet
{
    private String url;
    private String hash;
    private static final String __OBFID;
    
    public S48PacketResourcePackSend() {
    }
    
    public S48PacketResourcePackSend(final String url, final String hash) {
        this.url = url;
        this.hash = hash;
        if (hash.length() > 40) {
            throw new IllegalArgumentException("Hash is too long (max 40, was " + hash.length() + ")");
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.url = packetBuffer.readStringFromBuffer(32767);
        this.hash = packetBuffer.readStringFromBuffer(40);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.url);
        packetBuffer.writeString(this.hash);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.func_175095_a(this);
    }
    
    public String func_179783_a() {
        return this.url;
    }
    
    public String func_179784_b() {
        return this.hash;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00002293";
    }
}
