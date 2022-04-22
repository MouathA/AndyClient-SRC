package com.viaversion.viaversion.velocity.platform;

import com.viaversion.viaversion.configuration.*;
import java.io.*;
import com.viaversion.viaversion.api.protocol.version.*;
import java.util.*;

public class VelocityViaConfig extends AbstractViaConfig
{
    private static final List UNSUPPORTED;
    private int velocityPingInterval;
    private boolean velocityPingSave;
    private Map velocityServerProtocols;
    
    public VelocityViaConfig(final File file) {
        super(new File(file, "config.yml"));
        this.reloadConfig();
    }
    
    @Override
    protected void loadFields() {
        super.loadFields();
        this.velocityPingInterval = this.getInt("velocity-ping-interval", 60);
        this.velocityPingSave = this.getBoolean("velocity-ping-save", true);
        this.velocityServerProtocols = (Map)this.get("velocity-servers", Map.class, new HashMap());
    }
    
    @Override
    protected void handleConfig(final Map map) {
        HashMap<String, Integer> hashMap;
        if (!(map.get("velocity-servers") instanceof Map)) {
            hashMap = new HashMap<String, Integer>();
        }
        else {
            hashMap = (HashMap<String, Integer>)map.get("velocity-servers");
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
            hashMap.put("default", VelocityViaInjector.getLowestSupportedProtocolVersion());
        }
        map.put("velocity-servers", hashMap);
    }
    
    @Override
    public List getUnsupportedOptions() {
        return VelocityViaConfig.UNSUPPORTED;
    }
    
    @Override
    public boolean isItemCache() {
        return false;
    }
    
    @Override
    public boolean isNMSPlayerTicking() {
        return false;
    }
    
    public int getVelocityPingInterval() {
        return this.velocityPingInterval;
    }
    
    public boolean isVelocityPingSave() {
        return this.velocityPingSave;
    }
    
    public Map getVelocityServerProtocols() {
        return this.velocityServerProtocols;
    }
    
    static {
        UNSUPPORTED = Arrays.asList("nms-player-ticking", "item-cache", "anti-xray-patch", "quick-move-action-fix", "bungee-ping-interval", "bungee-ping-save", "bungee-servers", "blockconnection-method", "change-1_9-hitbox", "change-1_14-hitbox");
    }
}
