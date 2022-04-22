package com.viaversion.viaversion.bukkit.listeners;

import com.viaversion.viaversion.bukkit.platform.*;
import com.viaversion.viaversion.api.*;
import org.bukkit.event.*;
import org.bukkit.event.server.*;

public class ProtocolLibEnableListener implements Listener
{
    @EventHandler
    public void onPluginEnable(final PluginEnableEvent pluginEnableEvent) {
        if (pluginEnableEvent.getPlugin().getName().equals("ProtocolLib")) {
            ((BukkitViaInjector)Via.getManager().getInjector()).setProtocolLib(true);
        }
    }
    
    @EventHandler
    public void onPluginDisable(final PluginDisableEvent pluginDisableEvent) {
        if (pluginDisableEvent.getPlugin().getName().equals("ProtocolLib")) {
            ((BukkitViaInjector)Via.getManager().getInjector()).setProtocolLib(false);
        }
    }
}
