package com.viaversion.viaversion.bungee.platform;

import com.viaversion.viaversion.configuration.*;
import java.io.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.bungee.providers.*;
import java.util.*;

public class BungeeViaConfig extends AbstractViaConfig
{
    private static final List UNSUPPORTED;
    private int bungeePingInterval;
    private boolean bungeePingSave;
    private Map bungeeServerProtocols;
    
    public BungeeViaConfig(final File file) {
        super(new File(file, "config.yml"));
        this.reloadConfig();
    }
    
    @Override
    protected void loadFields() {
        super.loadFields();
        this.bungeePingInterval = this.getInt("bungee-ping-interval", 60);
        this.bungeePingSave = this.getBoolean("bungee-ping-save", true);
        this.bungeeServerProtocols = (Map)this.get("bungee-servers", Map.class, new HashMap());
    }
    
    @Override
    protected void handleConfig(final Map map) {
        HashMap<String, Integer> hashMap;
        if (!(map.get("bungee-servers") instanceof Map)) {
            hashMap = new HashMap<String, Integer>();
        }
        else {
            hashMap = (HashMap<String, Integer>)map.get("bungee-servers");
        }
        for (final Map.Entry<K, String> entry : new HashSet<Map.Entry<K, String>>((Collection<? extends Map.Entry<K, String>>)hashMap.entrySet())) {
            if (!(entry.getValue() instanceof Integer)) {
                if (entry.getValue() instanceof String) {
                    final ProtocolVersion closest = ProtocolVersion.getClosest(entry.getValue());
                    if (closest != null) {
                        hashMap.put((K)entry.getKey(), Integer.valueOf(closest.getVersion()));
                    }
                    else {
                        hashMap.remove(entry.getKey());
                    }
                }
                else {
                    hashMap.remove(entry.getKey());
                }
            }
        }
        if (!hashMap.containsKey("default")) {
            hashMap.put("default", BungeeVersionProvider.getLowestSupportedVersion());
        }
        map.put("bungee-servers", hashMap);
    }
    
    @Override
    public List getUnsupportedOptions() {
        return BungeeViaConfig.UNSUPPORTED;
    }
    
    @Override
    public boolean isItemCache() {
        return false;
    }
    
    @Override
    public boolean isNMSPlayerTicking() {
        return false;
    }
    
    public int getBungeePingInterval() {
        return this.bungeePingInterval;
    }
    
    public boolean isBungeePingSave() {
        return this.bungeePingSave;
    }
    
    public Map getBungeeServerProtocols() {
        return this.bungeeServerProtocols;
    }
    
    static {
        UNSUPPORTED = Arrays.asList("nms-player-ticking", "item-cache", "anti-xray-patch", "quick-move-action-fix", "velocity-ping-interval", "velocity-ping-save", "velocity-servers", "blockconnection-method", "change-1_9-hitbox", "change-1_14-hitbox");
    }
}
