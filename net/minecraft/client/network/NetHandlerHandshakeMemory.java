package net.minecraft.client.network;

import net.minecraft.network.handshake.*;
import net.minecraft.server.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.server.network.*;
import net.minecraft.network.*;
import net.minecraft.util.*;

public class NetHandlerHandshakeMemory implements INetHandlerHandshakeServer
{
    private final MinecraftServer field_147385_a;
    private final NetworkManager field_147384_b;
    private static final String __OBFID;
    
    public NetHandlerHandshakeMemory(final MinecraftServer field_147385_a, final NetworkManager field_147384_b) {
        this.field_147385_a = field_147385_a;
        this.field_147384_b = field_147384_b;
    }
    
    @Override
    public void processHandshake(final C00Handshake c00Handshake) {
        this.field_147384_b.setConnectionState(c00Handshake.getRequestedState());
        this.field_147384_b.setNetHandler(new NetHandlerLoginServer(this.field_147385_a, this.field_147384_b));
    }
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
    }
    
    static {
        __OBFID = "CL_00001445";
    }
}
