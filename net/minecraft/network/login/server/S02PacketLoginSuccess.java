package net.minecraft.network.login.server;

import com.mojang.authlib.*;
import java.util.*;
import java.io.*;
import net.minecraft.network.login.*;
import net.minecraft.network.*;

public class S02PacketLoginSuccess implements Packet
{
    private GameProfile profile;
    private static final String __OBFID;
    
    public S02PacketLoginSuccess() {
    }
    
    public S02PacketLoginSuccess(final GameProfile profile) {
        this.profile = profile;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.profile = new GameProfile(UUID.fromString(packetBuffer.readStringFromBuffer(36)), packetBuffer.readStringFromBuffer(16));
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        final UUID id = this.profile.getId();
        packetBuffer.writeString((id == null) ? "" : id.toString());
        packetBuffer.writeString(this.profile.getName());
    }
    
    public void func_180771_a(final INetHandlerLoginClient netHandlerLoginClient) {
        netHandlerLoginClient.handleLoginSuccess(this);
    }
    
    public GameProfile func_179730_a() {
        return this.profile;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.func_180771_a((INetHandlerLoginClient)netHandler);
    }
    
    static {
        __OBFID = "CL_00001375";
    }
}
