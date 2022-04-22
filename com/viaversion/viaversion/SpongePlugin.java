package com.viaversion.viaversion;

import org.spongepowered.plugin.builtin.jvm.*;
import net.kyori.adventure.text.serializer.legacy.*;
import org.spongepowered.plugin.*;
import java.util.logging.*;
import java.nio.file.*;
import com.google.inject.*;
import org.spongepowered.api.config.*;
import com.viaversion.viaversion.sponge.util.*;
import com.viaversion.viaversion.commands.*;
import org.spongepowered.api.event.*;
import org.spongepowered.api.command.registrar.*;
import org.spongepowered.api.command.*;
import org.spongepowered.api.event.lifecycle.*;
import org.spongepowered.api.*;
import com.viaversion.viaversion.api.platform.*;
import org.spongepowered.api.scheduler.*;
import com.viaversion.viaversion.sponge.platform.*;
import org.spongepowered.api.util.*;
import com.viaversion.viaversion.api.command.*;
import org.spongepowered.api.entity.living.player.server.*;
import com.viaversion.viaversion.sponge.commands.*;
import java.util.function.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.dump.*;
import java.util.*;
import org.spongepowered.plugin.metadata.model.*;
import java.util.stream.*;
import com.viaversion.viaversion.util.*;
import org.spongepowered.plugin.metadata.*;
import com.viaversion.viaversion.api.configuration.*;
import com.viaversion.viaversion.api.*;
import net.kyori.adventure.text.*;

@Plugin("viaversion")
public class SpongePlugin implements ViaPlatform
{
    public static final LegacyComponentSerializer LEGACY_SERIALIZER;
    private final SpongeViaAPI api;
    private final PluginContainer container;
    private final Game game;
    private final Logger logger;
    private SpongeViaConfig conf;
    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;
    
    @Inject
    SpongePlugin(final PluginContainer container, final Game game, final org.apache.logging.log4j.Logger logger) {
        this.api = new SpongeViaAPI();
        this.container = container;
        this.game = game;
        this.logger = new LoggerWrapper(logger);
    }
    
    @Listener
    public void constructPlugin(final ConstructPluginEvent constructPluginEvent) {
        this.conf = new SpongeViaConfig(this.configDir.toFile());
        this.logger.info("ViaVersion " + this.getPluginVersion() + " is now loaded!");
        Via.init(ViaManagerImpl.builder().platform(this).commandHandler(new SpongeCommandHandler()).injector(new SpongeViaInjector()).loader(new SpongeViaLoader(this)).build());
    }
    
    @Listener
    public void onServerStart(final StartingEngineEvent startingEngineEvent) {
        Sponge.server().commandManager().registrar((Class)Command.Raw.class).get().register(this.container, (Object)Via.getManager().getCommandHandler(), "viaversion", new String[] { "viaver", "vvsponge" });
        this.game.pluginManager().plugin("viabackwards").isPresent();
        this.logger.info("ViaVersion is injecting!");
        ((ViaManagerImpl)Via.getManager()).init();
    }
    
    @Listener
    public void onServerStop(final StoppingEngineEvent stoppingEngineEvent) {
        ((ViaManagerImpl)Via.getManager()).destroy();
    }
    
    @Override
    public String getPlatformName() {
        return this.game.platform().container(Platform.Component.IMPLEMENTATION).metadata().name().orElse("unknown");
    }
    
    @Override
    public String getPlatformVersion() {
        return this.game.platform().container(Platform.Component.IMPLEMENTATION).metadata().version().toString();
    }
    
    @Override
    public String getPluginVersion() {
        return this.container.metadata().version().toString();
    }
    
    @Override
    public PlatformTask runAsync(final Runnable runnable) {
        return new SpongeViaTask(this.game.asyncScheduler().submit(Task.builder().plugin(this.container).execute(runnable).build()));
    }
    
    @Override
    public PlatformTask runSync(final Runnable runnable) {
        return new SpongeViaTask(this.game.server().scheduler().submit(Task.builder().plugin(this.container).execute(runnable).build()));
    }
    
    @Override
    public PlatformTask runSync(final Runnable runnable, final long n) {
        return new SpongeViaTask(this.game.server().scheduler().submit(Task.builder().plugin(this.container).execute(runnable).delay(Ticks.of(n)).build()));
    }
    
    @Override
    public PlatformTask runRepeatingSync(final Runnable runnable, final long n) {
        return new SpongeViaTask(this.game.server().scheduler().submit(Task.builder().plugin(this.container).execute(runnable).interval(Ticks.of(n)).build()));
    }
    
    @Override
    public ViaCommandSender[] getOnlinePlayers() {
        final Collection onlinePlayers = this.game.server().onlinePlayers();
        final ViaCommandSender[] array = new ViaCommandSender[onlinePlayers.size()];
        for (final ServerPlayer serverPlayer : onlinePlayers) {
            final ViaCommandSender[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = new SpongePlayer(serverPlayer);
        }
        return array;
    }
    
    @Override
    public void sendMessage(final UUID uuid, final String s) {
        this.game.server().player(uuid).ifPresent(SpongePlugin::lambda$sendMessage$0);
    }
    
    @Override
    public boolean kickPlayer(final UUID uuid, final String s) {
        return this.game.server().player(uuid).map(SpongePlugin::lambda$kickPlayer$1).orElse(false);
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
    public void onReload() {
        this.logger.severe("ViaVersion is already loaded, this should work fine. If you get any console errors, try rebooting.");
    }
    
    @Override
    public JsonObject getDump() {
        final JsonObject jsonObject = new JsonObject();
        final ArrayList<PluginInfo> list = new ArrayList<PluginInfo>();
        for (final PluginContainer pluginContainer : this.game.pluginManager().plugins()) {
            final PluginMetadata metadata = pluginContainer.metadata();
            list.add(new PluginInfo(true, metadata.name().orElse("Unknown"), metadata.version().toString(), pluginContainer.instance().getClass().getCanonicalName(), (List)metadata.contributors().stream().map(PluginContributor::name).collect(Collectors.toList())));
        }
        jsonObject.add("plugins", GsonUtil.getGson().toJsonTree(list));
        return jsonObject;
    }
    
    @Override
    public boolean isOldClientsAllowed() {
        return true;
    }
    
    @Override
    public SpongeViaAPI getApi() {
        return this.api;
    }
    
    @Override
    public SpongeViaConfig getConf() {
        return this.conf;
    }
    
    @Override
    public Logger getLogger() {
        return this.logger;
    }
    
    public PluginContainer container() {
        return this.container;
    }
    
    @Override
    public ViaVersionConfig getConf() {
        return this.getConf();
    }
    
    @Override
    public ViaAPI getApi() {
        return this.getApi();
    }
    
    private static Boolean lambda$kickPlayer$1(final String s, final ServerPlayer serverPlayer) {
        serverPlayer.kick((Component)LegacyComponentSerializer.legacySection().deserialize(s));
        return true;
    }
    
    private static void lambda$sendMessage$0(final String s, final ServerPlayer serverPlayer) {
        serverPlayer.sendMessage((Component)SpongePlugin.LEGACY_SERIALIZER.deserialize(s));
    }
    
    static {
        LEGACY_SERIALIZER = LegacyComponentSerializer.builder().extractUrls().build();
    }
}
