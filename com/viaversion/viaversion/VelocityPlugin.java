package com.viaversion.viaversion;

import net.kyori.adventure.text.serializer.legacy.*;
import com.google.inject.*;
import org.slf4j.*;
import java.nio.file.*;
import com.velocitypowered.api.plugin.annotation.*;
import com.velocitypowered.api.event.proxy.*;
import com.velocitypowered.api.command.*;
import com.viaversion.viaversion.velocity.util.*;
import com.viaversion.viaversion.commands.*;
import com.velocitypowered.api.event.*;
import com.viaversion.viaversion.api.platform.*;
import java.util.concurrent.*;
import com.viaversion.viaversion.velocity.platform.*;
import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.velocity.command.*;
import java.util.function.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.dump.*;
import com.velocitypowered.api.plugin.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.velocity.service.*;
import java.util.*;
import com.viaversion.viaversion.api.configuration.*;
import com.viaversion.viaversion.api.*;
import com.velocitypowered.api.proxy.*;
import net.kyori.adventure.text.*;

@Plugin(id = "viaversion", name = "ViaVersion", version = "4.2.0-22w06a-SNAPSHOT", authors = { "_MylesC", "creeper123123321", "Gerrygames", "kennytv", "Matsv" }, description = "Allow newer Minecraft versions to connect to an older server version.", url = "https://viaversion.com")
public class VelocityPlugin implements ViaPlatform
{
    public static final LegacyComponentSerializer COMPONENT_SERIALIZER;
    public static ProxyServer PROXY;
    @Inject
    private ProxyServer proxy;
    @Inject
    private Logger loggerslf4j;
    @Inject
    @DataDirectory
    private Path configDir;
    private VelocityViaAPI api;
    private java.util.logging.Logger logger;
    private VelocityViaConfig conf;
    
    @Subscribe
    public void onProxyInit(final ProxyInitializeEvent proxyInitializeEvent) {
        if (!this.hasConnectionEvent()) {
            final Logger loggerslf4j = this.loggerslf4j;
            loggerslf4j.error("      / \\");
            loggerslf4j.error("     /   \\");
            loggerslf4j.error("    /  |  \\");
            loggerslf4j.error("   /   |   \\        VELOCITY 3.0.0 IS REQUIRED");
            loggerslf4j.error("  /         \\   VIAVERSION WILL NOT WORK AS INTENDED");
            loggerslf4j.error(" /     o     \\");
            loggerslf4j.error("/_____________\\");
        }
        VelocityPlugin.PROXY = this.proxy;
        final VelocityCommandHandler velocityCommandHandler = new VelocityCommandHandler();
        VelocityPlugin.PROXY.getCommandManager().register("viaver", (Command)velocityCommandHandler, new String[] { "vvvelocity", "viaversion" });
        this.api = new VelocityViaAPI();
        this.conf = new VelocityViaConfig(this.configDir.toFile());
        this.logger = new LoggerWrapper(this.loggerslf4j);
        Via.init(ViaManagerImpl.builder().platform(this).commandHandler(velocityCommandHandler).loader(new VelocityViaLoader()).injector(new VelocityViaInjector()).build());
        this.proxy.getPluginManager().getPlugin("viabackwards").isPresent();
    }
    
    @Subscribe(order = PostOrder.LAST)
    public void onProxyLateInit(final ProxyInitializeEvent proxyInitializeEvent) {
        ((ViaManagerImpl)Via.getManager()).init();
    }
    
    @Override
    public String getPlatformName() {
        final String implementationTitle = ProxyServer.class.getPackage().getImplementationTitle();
        return (implementationTitle != null) ? implementationTitle : "Velocity";
    }
    
    @Override
    public String getPlatformVersion() {
        final String implementationVersion = ProxyServer.class.getPackage().getImplementationVersion();
        return (implementationVersion != null) ? implementationVersion : "Unknown";
    }
    
    @Override
    public boolean isProxy() {
        return true;
    }
    
    @Override
    public String getPluginVersion() {
        return "4.2.0-22w06a-SNAPSHOT";
    }
    
    @Override
    public PlatformTask runAsync(final Runnable runnable) {
        return this.runSync(runnable);
    }
    
    @Override
    public PlatformTask runSync(final Runnable runnable) {
        return this.runSync(runnable, 0L);
    }
    
    @Override
    public PlatformTask runSync(final Runnable runnable, final long n) {
        return new VelocityViaTask(VelocityPlugin.PROXY.getScheduler().buildTask((Object)this, runnable).delay(n * 50L, TimeUnit.MILLISECONDS).schedule());
    }
    
    @Override
    public PlatformTask runRepeatingSync(final Runnable runnable, final long n) {
        return new VelocityViaTask(VelocityPlugin.PROXY.getScheduler().buildTask((Object)this, runnable).repeat(n * 50L, TimeUnit.MILLISECONDS).schedule());
    }
    
    @Override
    public ViaCommandSender[] getOnlinePlayers() {
        return (ViaCommandSender[])VelocityPlugin.PROXY.getAllPlayers().stream().map(VelocityCommandSender::new).toArray(VelocityPlugin::lambda$getOnlinePlayers$0);
    }
    
    @Override
    public void sendMessage(final UUID uuid, final String s) {
        VelocityPlugin.PROXY.getPlayer(uuid).ifPresent(VelocityPlugin::lambda$sendMessage$1);
    }
    
    @Override
    public boolean kickPlayer(final UUID uuid, final String s) {
        return VelocityPlugin.PROXY.getPlayer(uuid).map(VelocityPlugin::lambda$kickPlayer$2).orElse(false);
    }
    
    @Override
    public boolean isPluginEnabled() {
        return true;
    }
    
    @Override
    public ConfigurationProvider getConfigurationProvider() {
        return this.conf;
    }
    
    @Override
    public File getDataFolder() {
        return this.configDir.toFile();
    }
    
    @Override
    public VelocityViaAPI getApi() {
        return this.api;
    }
    
    @Override
    public VelocityViaConfig getConf() {
        return this.conf;
    }
    
    @Override
    public void onReload() {
    }
    
    @Override
    public JsonObject getDump() {
        final JsonObject jsonObject = new JsonObject();
        final ArrayList<PluginInfo> list = new ArrayList<PluginInfo>();
        for (final PluginContainer pluginContainer : VelocityPlugin.PROXY.getPluginManager().getPlugins()) {
            list.add(new PluginInfo(true, pluginContainer.getDescription().getName().orElse(pluginContainer.getDescription().getId()), pluginContainer.getDescription().getVersion().orElse("Unknown Version"), pluginContainer.getInstance().isPresent() ? pluginContainer.getInstance().get().getClass().getCanonicalName() : "Unknown", pluginContainer.getDescription().getAuthors()));
        }
        jsonObject.add("plugins", GsonUtil.getGson().toJsonTree(list));
        jsonObject.add("servers", GsonUtil.getGson().toJsonTree(ProtocolDetectorService.getDetectedIds()));
        return jsonObject;
    }
    
    @Override
    public boolean isOldClientsAllowed() {
        return true;
    }
    
    @Override
    public java.util.logging.Logger getLogger() {
        return this.logger;
    }
    
    private boolean hasConnectionEvent() {
        Class.forName("com.velocitypowered.proxy.protocol.VelocityConnectionEvent");
        return true;
    }
    
    @Override
    public ViaVersionConfig getConf() {
        return this.getConf();
    }
    
    @Override
    public ViaAPI getApi() {
        return this.getApi();
    }
    
    private static Boolean lambda$kickPlayer$2(final String s, final Player player) {
        player.disconnect((Component)LegacyComponentSerializer.legacySection().deserialize(s));
        return true;
    }
    
    private static void lambda$sendMessage$1(final String s, final Player player) {
        player.sendMessage((Component)VelocityPlugin.COMPONENT_SERIALIZER.deserialize(s));
    }
    
    private static ViaCommandSender[] lambda$getOnlinePlayers$0(final int n) {
        return new ViaCommandSender[n];
    }
    
    static {
        COMPONENT_SERIALIZER = LegacyComponentSerializer.builder().character('§').extractUrls().build();
    }
}
