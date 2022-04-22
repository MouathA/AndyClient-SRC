package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.configuration.*;
import org.bukkit.plugin.*;
import com.viaversion.viaversion.api.*;
import java.io.*;
import java.util.*;

public class BukkitViaConfig extends AbstractViaConfig
{
    private static final List UNSUPPORTED;
    private boolean antiXRay;
    private boolean quickMoveActionFix;
    private boolean hitboxFix1_9;
    private boolean hitboxFix1_14;
    private String blockConnectionMethod;
    
    public BukkitViaConfig() {
        super(new File(((Plugin)Via.getPlatform()).getDataFolder(), "config.yml"));
        this.reloadConfig();
    }
    
    @Override
    protected void loadFields() {
        super.loadFields();
        this.antiXRay = this.getBoolean("anti-xray-patch", true);
        this.quickMoveActionFix = this.getBoolean("quick-move-action-fix", false);
        this.hitboxFix1_9 = this.getBoolean("change-1_9-hitbox", false);
        this.hitboxFix1_14 = this.getBoolean("change-1_14-hitbox", false);
        this.blockConnectionMethod = this.getString("blockconnection-method", "packet");
    }
    
    @Override
    protected void handleConfig(final Map map) {
    }
    
    @Override
    public boolean isAntiXRay() {
        return this.antiXRay;
    }
    
    @Override
    public boolean is1_12QuickMoveActionFix() {
        return this.quickMoveActionFix;
    }
    
    @Override
    public boolean is1_9HitboxFix() {
        return this.hitboxFix1_9;
    }
    
    @Override
    public boolean is1_14HitboxFix() {
        return this.hitboxFix1_14;
    }
    
    @Override
    public String getBlockConnectionMethod() {
        return this.blockConnectionMethod;
    }
    
    @Override
    public List getUnsupportedOptions() {
        return BukkitViaConfig.UNSUPPORTED;
    }
    
    static {
        UNSUPPORTED = Arrays.asList("bungee-ping-interval", "bungee-ping-save", "bungee-servers", "velocity-ping-interval", "velocity-ping-save", "velocity-servers");
    }
}
