package com.viaversion.viaversion.bungee.listeners;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.event.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.update.*;
import net.md_5.bungee.event.*;

public class UpdateListener implements Listener
{
    @EventHandler
    public void onJoin(final PostLoginEvent postLoginEvent) {
        if (postLoginEvent.getPlayer().hasPermission("viaversion.update") && Via.getConfig().isCheckForUpdates()) {
            UpdateUtil.sendUpdateMessage(postLoginEvent.getPlayer().getUniqueId());
        }
    }
}
