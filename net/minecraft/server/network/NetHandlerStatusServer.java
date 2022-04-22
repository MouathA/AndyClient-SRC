package net.minecraft.server.network;

import net.minecraft.network.status.*;
import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.status.client.*;
import net.minecraft.network.status.server.*;

public class NetHandlerStatusServer implements INetHandlerStatusServer
{
    private final MinecraftServer server;
    private final NetworkManager networkManager;
    private static final String __OBFID;
    
    public NetHandlerStatusServer(final MinecraftServer server, final NetworkManager networkManager) {
        this.server = server;
        this.networkManager = networkManager;
    }
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
    }
    
    @Override
    public void processServerQuery(final C00PacketServerQuery c00PacketServerQuery) {
        this.networkManager.sendPacket(new S00PacketServerInfo(this.server.getServerStatusResponse()));
    }
    
    @Override
    public void processPing(final C01PacketPing c01PacketPing) {
        this.networkManager.sendPacket(new S01PacketPong(c01PacketPing.getClientTime()));
    }
    
    static {
        __OBFID = "CL_00001464";
    }
}
