package com.viaversion.viaversion.velocity.storage;

import com.viaversion.viaversion.api.connection.*;
import com.velocitypowered.api.proxy.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.util.*;
import java.util.*;

public class VelocityStorage implements StorableObject
{
    private final Player player;
    private String currentServer;
    private List cachedBossbar;
    private static Method getServerBossBars;
    private static Class clientPlaySessionHandler;
    private static Method getMinecraftConnection;
    
    public VelocityStorage(final Player player) {
        this.player = player;
        this.currentServer = "";
    }
    
    public List getBossbar() {
        if (this.cachedBossbar == null) {
            if (VelocityStorage.clientPlaySessionHandler == null) {
                return null;
            }
            if (VelocityStorage.getServerBossBars == null) {
                return null;
            }
            if (VelocityStorage.getMinecraftConnection == null) {
                return null;
            }
            final Object invoke = ReflectionUtil.invoke(VelocityStorage.getMinecraftConnection.invoke(this.player, new Object[0]), "getSessionHandler");
            if (VelocityStorage.clientPlaySessionHandler.isInstance(invoke)) {
                this.cachedBossbar = (List)VelocityStorage.getServerBossBars.invoke(invoke, new Object[0]);
            }
        }
        return this.cachedBossbar;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public String getCurrentServer() {
        return this.currentServer;
    }
    
    public void setCurrentServer(final String currentServer) {
        this.currentServer = currentServer;
    }
    
    public List getCachedBossbar() {
        return this.cachedBossbar;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final VelocityStorage velocityStorage = (VelocityStorage)o;
        return Objects.equals(this.player, velocityStorage.player) && Objects.equals(this.currentServer, velocityStorage.currentServer) && Objects.equals(this.cachedBossbar, velocityStorage.cachedBossbar);
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * ((this.player != null) ? this.player.hashCode() : 0) + ((this.currentServer != null) ? this.currentServer.hashCode() : 0)) + ((this.cachedBossbar != null) ? this.cachedBossbar.hashCode() : 0);
    }
    
    static {
        VelocityStorage.clientPlaySessionHandler = Class.forName("com.velocitypowered.proxy.connection.client.ClientPlaySessionHandler");
        VelocityStorage.getServerBossBars = VelocityStorage.clientPlaySessionHandler.getDeclaredMethod("getServerBossBars", (Class[])new Class[0]);
        VelocityStorage.getMinecraftConnection = Class.forName("com.velocitypowered.proxy.connection.client.ConnectedPlayer").getDeclaredMethod("getMinecraftConnection", (Class<?>[])new Class[0]);
    }
}
