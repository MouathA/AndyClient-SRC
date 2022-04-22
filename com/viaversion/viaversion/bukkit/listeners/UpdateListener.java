package com.viaversion.viaversion.bukkit.listeners;

import org.bukkit.event.player.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.update.*;
import org.bukkit.event.*;

public class UpdateListener implements Listener
{
    @EventHandler
    public void onJoin(final PlayerJoinEvent playerJoinEvent) {
        if (playerJoinEvent.getPlayer().hasPermission("viaversion.update") && Via.getConfig().isCheckForUpdates()) {
            UpdateUtil.sendUpdateMessage(playerJoinEvent.getPlayer().getUniqueId());
        }
    }
}
