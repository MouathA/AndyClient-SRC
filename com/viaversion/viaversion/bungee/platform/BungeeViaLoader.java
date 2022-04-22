package com.viaversion.viaversion.bungee.platform;

import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.plugin.*;
import com.viaversion.viaversion.bungee.handlers.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.bungee.listeners.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import com.viaversion.viaversion.bungee.providers.*;
import com.viaversion.viaversion.bungee.service.*;
import java.util.concurrent.*;
import net.md_5.bungee.api.scheduler.*;
import java.util.*;

public class BungeeViaLoader implements ViaPlatformLoader
{
    private final BungeePlugin plugin;
    private final Set listeners;
    private final Set tasks;
    
    public BungeeViaLoader(final BungeePlugin plugin) {
        this.listeners = new HashSet();
        this.tasks = new HashSet();
        this.plugin = plugin;
    }
    
    private void registerListener(final Listener listener) {
        this.listeners.add(listener);
        ProxyServer.getInstance().getPluginManager().registerListener((Plugin)this.plugin, listener);
    }
    
    @Override
    public void load() {
        this.registerListener((Listener)this.plugin);
        this.registerListener((Listener)new UpdateListener());
        this.registerListener((Listener)new BungeeServerHandler());
        if (Via.getAPI().getServerVersion().lowestSupportedVersion() < ProtocolVersion.v1_9.getVersion()) {
            this.registerListener((Listener)new ElytraPatch());
        }
        Via.getManager().getProviders().use(VersionProvider.class, new BungeeVersionProvider());
        Via.getManager().getProviders().use(EntityIdProvider.class, new BungeeEntityIdProvider());
        if (Via.getAPI().getServerVersion().lowestSupportedVersion() < ProtocolVersion.v1_9.getVersion()) {
            Via.getManager().getProviders().use(MovementTransmitterProvider.class, new BungeeMovementTransmitter());
            Via.getManager().getProviders().use(BossBarProvider.class, new BungeeBossBarProvider());
            Via.getManager().getProviders().use(MainHandProvider.class, new BungeeMainHandProvider());
        }
        if (this.plugin.getConf().getBungeePingInterval() > 0) {
            this.tasks.add(this.plugin.getProxy().getScheduler().schedule((Plugin)this.plugin, (Runnable)new ProtocolDetectorService(this.plugin), 0L, (long)this.plugin.getConf().getBungeePingInterval(), TimeUnit.SECONDS));
        }
    }
    
    @Override
    public void unload() {
        final Iterator<Listener> iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            ProxyServer.getInstance().getPluginManager().unregisterListener((Listener)iterator.next());
        }
        this.listeners.clear();
        final Iterator<ScheduledTask> iterator2 = this.tasks.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().cancel();
        }
        this.tasks.clear();
    }
}
