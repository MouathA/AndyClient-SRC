package com.viaversion.viaversion.bukkit.listeners;

import com.viaversion.viaversion.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import com.viaversion.viaversion.api.connection.*;

public class ViaBukkitListener extends ViaListener implements Listener
{
    private final Plugin plugin;
    
    public ViaBukkitListener(final Plugin plugin, final Class clazz) {
        super(clazz);
        this.plugin = plugin;
    }
    
    protected UserConnection getUserConnection(final Player player) {
        return this.getUserConnection(player.getUniqueId());
    }
    
    protected boolean isOnPipe(final Player player) {
        return this.isOnPipe(player.getUniqueId());
    }
    
    @Override
    public void register() {
        if (this.isRegistered()) {
            return;
        }
        this.plugin.getServer().getPluginManager().registerEvents((Listener)this, this.plugin);
        this.setRegistered(true);
    }
    
    public Plugin getPlugin() {
        return this.plugin;
    }
}
