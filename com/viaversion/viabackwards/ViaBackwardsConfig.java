package com.viaversion.viabackwards;

import com.viaversion.viaversion.util.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ViaBackwardsConfig extends Config implements com.viaversion.viabackwards.api.ViaBackwardsConfig
{
    private boolean addCustomEnchantsToLore;
    private boolean addTeamColorToPrefix;
    private boolean fix1_13FacePlayer;
    private boolean alwaysShowOriginalMobName;
    private boolean fix1_13FormattedInventoryTitles;
    private boolean handlePingsAsInvAcknowledgements;
    
    public ViaBackwardsConfig(final File file) {
        super(file);
    }
    
    @Override
    public void reloadConfig() {
        super.reloadConfig();
        this.loadFields();
    }
    
    private void loadFields() {
        this.addCustomEnchantsToLore = this.getBoolean("add-custom-enchants-into-lore", true);
        this.addTeamColorToPrefix = this.getBoolean("add-teamcolor-to-prefix", true);
        this.fix1_13FacePlayer = this.getBoolean("fix-1_13-face-player", false);
        this.fix1_13FormattedInventoryTitles = this.getBoolean("fix-formatted-inventory-titles", true);
        this.alwaysShowOriginalMobName = this.getBoolean("always-show-original-mob-name", true);
        this.handlePingsAsInvAcknowledgements = this.getBoolean("handle-pings-as-inv-acknowledgements", false);
    }
    
    @Override
    public boolean addCustomEnchantsToLore() {
        return this.addCustomEnchantsToLore;
    }
    
    @Override
    public boolean addTeamColorTo1_13Prefix() {
        return this.addTeamColorToPrefix;
    }
    
    @Override
    public boolean isFix1_13FacePlayer() {
        return this.fix1_13FacePlayer;
    }
    
    @Override
    public boolean fix1_13FormattedInventoryTitle() {
        return this.fix1_13FormattedInventoryTitles;
    }
    
    @Override
    public boolean alwaysShowOriginalMobName() {
        return this.alwaysShowOriginalMobName;
    }
    
    @Override
    public boolean handlePingsAsInvAcknowledgements() {
        return this.handlePingsAsInvAcknowledgements || Boolean.getBoolean("com.viaversion.handlePingsAsInvAcknowledgements");
    }
    
    @Override
    public URL getDefaultConfigURL() {
        return this.getClass().getClassLoader().getResource("assets/viabackwards/config.yml");
    }
    
    @Override
    protected void handleConfig(final Map map) {
    }
    
    @Override
    public List getUnsupportedOptions() {
        return Collections.emptyList();
    }
}
