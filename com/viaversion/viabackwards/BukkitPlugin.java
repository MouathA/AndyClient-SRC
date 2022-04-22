package com.viaversion.viabackwards;

import org.bukkit.plugin.java.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.bukkit.platform.*;
import com.viaversion.viaversion.api.protocol.version.*;
import org.bukkit.event.*;
import com.viaversion.viabackwards.listener.*;
import org.bukkit.plugin.*;

public class BukkitPlugin extends JavaPlugin implements ViaBackwardsPlatform
{
    public void onLoad() {
        if (!ViaVersionPlugin.getInstance().isLateBind()) {
            this.init();
        }
    }
    
    public void onEnable() {
        if (ViaVersionPlugin.getInstance().isLateBind()) {
            this.init();
        }
    }
    
    private void init() {
        this.init(this.getDataFolder());
        Via.getPlatform().runSync(this::onServerLoaded);
    }
    
    private void onServerLoaded() {
        final BukkitViaLoader bukkitViaLoader = (BukkitViaLoader)Via.getManager().getLoader();
        final int highestSupportedVersion = Via.getAPI().getServerVersion().highestSupportedVersion();
        if (highestSupportedVersion >= ProtocolVersion.v1_16.getVersion()) {
            ((FireExtinguishListener)bukkitViaLoader.storeListener((Listener)new FireExtinguishListener(this))).register();
        }
        if (highestSupportedVersion >= ProtocolVersion.v1_14.getVersion()) {
            ((LecternInteractListener)bukkitViaLoader.storeListener((Listener)new LecternInteractListener(this))).register();
        }
        if (highestSupportedVersion >= ProtocolVersion.v1_12.getVersion()) {
            ((FireDamageListener)bukkitViaLoader.storeListener((Listener)new FireDamageListener(this))).register();
        }
    }
    
    public void disable() {
        this.getPluginLoader().disablePlugin((Plugin)this);
    }
}
