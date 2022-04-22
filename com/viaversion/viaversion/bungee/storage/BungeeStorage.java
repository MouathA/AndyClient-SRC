package com.viaversion.viaversion.bungee.storage;

import com.viaversion.viaversion.api.connection.*;
import java.lang.reflect.*;
import net.md_5.bungee.api.connection.*;
import java.util.*;

public class BungeeStorage implements StorableObject
{
    private static Field bossField;
    private final ProxiedPlayer player;
    private String currentServer;
    private Set bossbar;
    
    public BungeeStorage(final ProxiedPlayer player) {
        this.player = player;
        this.currentServer = "";
        if (BungeeStorage.bossField != null) {
            this.bossbar = (Set)BungeeStorage.bossField.get(player);
        }
    }
    
    public ProxiedPlayer getPlayer() {
        return this.player;
    }
    
    public String getCurrentServer() {
        return this.currentServer;
    }
    
    public void setCurrentServer(final String currentServer) {
        this.currentServer = currentServer;
    }
    
    public Set getBossbar() {
        return this.bossbar;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final BungeeStorage bungeeStorage = (BungeeStorage)o;
        return Objects.equals(this.player, bungeeStorage.player) && Objects.equals(this.currentServer, bungeeStorage.currentServer) && Objects.equals(this.bossbar, bungeeStorage.bossbar);
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * ((this.player != null) ? this.player.hashCode() : 0) + ((this.currentServer != null) ? this.currentServer.hashCode() : 0)) + ((this.bossbar != null) ? this.bossbar.hashCode() : 0);
    }
    
    static {
        (BungeeStorage.bossField = Class.forName("net.md_5.bungee.UserConnection").getDeclaredField("sentBossBars")).setAccessible(true);
    }
}
