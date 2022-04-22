package net.minecraft.network.login.client;

import com.mojang.authlib.*;
import java.util.*;
import java.io.*;
import net.minecraft.network.login.*;
import net.minecraft.network.*;

public class C00PacketLoginStart implements Packet
{
    private GameProfile profile;
    private static final String __OBFID;
    
    public C00PacketLoginStart() {
    }
    
    public C00PacketLoginStart(final GameProfile profile) {
        this.profile = profile;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.profile = new GameProfile(null, packetBuffer.readStringFromBuffer(16));
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.profile.getName());
    }
    
    public void func_180773_a(final INetHandlerLoginServer netHandlerLoginServer) {
        netHandlerLoginServer.processLoginStart(this);
    }
    
    public GameProfile getProfile() {
        return this.profile;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180773_a((INetHandlerLoginServer)netHandler);
    }
    
    static {
        __OBFID = "CL_00001379";
    }
}
