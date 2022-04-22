package com.viaversion.viaversion.velocity.service;

import com.viaversion.viaversion.velocity.platform.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.*;
import java.util.function.*;
import java.util.*;
import com.velocitypowered.api.proxy.server.*;
import com.viaversion.viaversion.api.configuration.*;
import java.util.concurrent.*;

public class ProtocolDetectorService implements Runnable
{
    private static final Map detectedProtocolIds;
    private static ProtocolDetectorService instance;
    
    public ProtocolDetectorService() {
        ProtocolDetectorService.instance = this;
    }
    
    public static Integer getProtocolId(final String s) {
        final Map velocityServerProtocols = ((VelocityViaConfig)Via.getConfig()).getVelocityServerProtocols();
        final Integer n = velocityServerProtocols.get(s);
        if (n != null) {
            return n;
        }
        final Integer n2 = ProtocolDetectorService.detectedProtocolIds.get(s);
        if (n2 != null) {
            return n2;
        }
        final Integer n3 = velocityServerProtocols.get("default");
        if (n3 != null) {
            return n3;
        }
        return ProtocolVersion.getProtocol(Via.getManager().getInjector().getServerProtocolVersion()).getVersion();
    }
    
    @Override
    public void run() {
        final Iterator<RegisteredServer> iterator = VelocityPlugin.PROXY.getAllServers().iterator();
        while (iterator.hasNext()) {
            probeServer(iterator.next());
        }
    }
    
    public static void probeServer(final RegisteredServer registeredServer) {
        registeredServer.ping().thenAccept((Consumer)ProtocolDetectorService::lambda$probeServer$0);
    }
    
    public static Map getDetectedIds() {
        return new HashMap(ProtocolDetectorService.detectedProtocolIds);
    }
    
    public static ProtocolDetectorService getInstance() {
        return ProtocolDetectorService.instance;
    }
    
    private static void lambda$probeServer$0(final String s, final ServerPing serverPing) {
        if (serverPing != null && serverPing.getVersion() != null) {
            ProtocolDetectorService.detectedProtocolIds.put(s, serverPing.getVersion().getProtocol());
            if (((VelocityViaConfig)Via.getConfig()).isVelocityPingSave()) {
                final Map velocityServerProtocols = ((VelocityViaConfig)Via.getConfig()).getVelocityServerProtocols();
                final Integer n = velocityServerProtocols.get(s);
                if (n != null && n == serverPing.getVersion().getProtocol()) {
                    return;
                }
                // monitorenter(configurationProvider = Via.getPlatform().getConfigurationProvider())
                velocityServerProtocols.put(s, serverPing.getVersion().getProtocol());
                // monitorexit(configurationProvider)
                Via.getPlatform().getConfigurationProvider().saveConfig();
            }
        }
    }
    
    static {
        detectedProtocolIds = new ConcurrentHashMap();
    }
}
