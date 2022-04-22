package com.viaversion.viaversion.bungee.service;

import com.viaversion.viaversion.*;
import com.viaversion.viaversion.bungee.platform.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.bungee.providers.*;
import net.md_5.bungee.api.config.*;
import net.md_5.bungee.api.*;
import com.viaversion.viaversion.api.configuration.*;
import java.util.*;
import java.util.concurrent.*;

public class ProtocolDetectorService implements Runnable
{
    private static final Map detectedProtocolIds;
    private static ProtocolDetectorService instance;
    private final BungeePlugin plugin;
    
    public ProtocolDetectorService(final BungeePlugin plugin) {
        this.plugin = plugin;
        ProtocolDetectorService.instance = this;
    }
    
    public static Integer getProtocolId(final String s) {
        final Map bungeeServerProtocols = ((BungeeViaConfig)Via.getConfig()).getBungeeServerProtocols();
        final Integer n = bungeeServerProtocols.get(s);
        if (n != null) {
            return n;
        }
        final Integer n2 = ProtocolDetectorService.detectedProtocolIds.get(s);
        if (n2 != null) {
            return n2;
        }
        final Integer n3 = bungeeServerProtocols.get("default");
        if (n3 != null) {
            return n3;
        }
        return BungeeVersionProvider.getLowestSupportedVersion();
    }
    
    @Override
    public void run() {
        final Iterator<Map.Entry<K, ServerInfo>> iterator = this.plugin.getProxy().getServers().entrySet().iterator();
        while (iterator.hasNext()) {
            probeServer(iterator.next().getValue());
        }
    }
    
    public static void probeServer(final ServerInfo serverInfo) {
        serverInfo.ping((Callback)new Callback(serverInfo.getName()) {
            final String val$key;
            
            public void done(final ServerPing serverPing, final Throwable t) {
                if (t == null && serverPing != null && serverPing.getVersion() != null && serverPing.getVersion().getProtocol() > 0) {
                    ProtocolDetectorService.access$000().put(this.val$key, serverPing.getVersion().getProtocol());
                    if (((BungeeViaConfig)Via.getConfig()).isBungeePingSave()) {
                        final Map bungeeServerProtocols = ((BungeeViaConfig)Via.getConfig()).getBungeeServerProtocols();
                        final Integer n = bungeeServerProtocols.get(this.val$key);
                        if (n != null && n == serverPing.getVersion().getProtocol()) {
                            return;
                        }
                        // monitorenter(configurationProvider = Via.getPlatform().getConfigurationProvider())
                        bungeeServerProtocols.put(this.val$key, serverPing.getVersion().getProtocol());
                        // monitorexit(configurationProvider)
                        Via.getPlatform().getConfigurationProvider().saveConfig();
                    }
                }
            }
            
            public void done(final Object o, final Throwable t) {
                this.done((ServerPing)o, t);
            }
        });
    }
    
    public static Map getDetectedIds() {
        return new HashMap(ProtocolDetectorService.detectedProtocolIds);
    }
    
    public static ProtocolDetectorService getInstance() {
        return ProtocolDetectorService.instance;
    }
    
    public BungeePlugin getPlugin() {
        return this.plugin;
    }
    
    static Map access$000() {
        return ProtocolDetectorService.detectedProtocolIds;
    }
    
    static {
        detectedProtocolIds = new ConcurrentHashMap();
    }
}
