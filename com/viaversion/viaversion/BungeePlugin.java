package com.viaversion.viaversion;

import net.md_5.bungee.protocol.*;
import net.md_5.bungee.api.plugin.*;
import com.viaversion.viaversion.commands.*;
import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.bungee.platform.*;
import java.util.concurrent.*;
import com.viaversion.viaversion.api.command.*;
import net.md_5.bungee.api.connection.*;
import com.viaversion.viaversion.bungee.commands.*;
import net.md_5.bungee.api.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.dump.*;
import java.util.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.bungee.service.*;
import com.viaversion.viaversion.api.configuration.*;

public class BungeePlugin extends Plugin implements ViaPlatform, Listener
{
    private BungeeViaAPI api;
    private BungeeViaConfig config;
    
    public void onLoad() {
        ProtocolConstants.class.getField("MINECRAFT_1_18");
        this.api = new BungeeViaAPI();
        this.config = new BungeeViaConfig(this.getDataFolder());
        final BungeeCommandHandler bungeeCommandHandler = new BungeeCommandHandler();
        ProxyServer.getInstance().getPluginManager().registerCommand((Plugin)this, (Command)new BungeeCommand(bungeeCommandHandler));
        Via.init(ViaManagerImpl.builder().platform(this).injector(new BungeeViaInjector()).loader(new BungeeViaLoader(this)).commandHandler(bungeeCommandHandler).build());
    }
    
    public void onEnable() {
        ProxyServer.getInstance().getPluginManager().getPlugin("ViaBackwards");
        ((ViaManagerImpl)Via.getManager()).init();
    }
    
    public String getPlatformName() {
        return this.getProxy().getName();
    }
    
    public String getPlatformVersion() {
        return this.getProxy().getVersion();
    }
    
    public boolean isProxy() {
        return true;
    }
    
    public String getPluginVersion() {
        return this.getDescription().getVersion();
    }
    
    public PlatformTask runAsync(final Runnable runnable) {
        return new BungeeViaTask(this.getProxy().getScheduler().runAsync((Plugin)this, runnable));
    }
    
    public PlatformTask runSync(final Runnable runnable) {
        return this.runAsync(runnable);
    }
    
    public PlatformTask runSync(final Runnable runnable, final long n) {
        return new BungeeViaTask(this.getProxy().getScheduler().schedule((Plugin)this, runnable, n * 50L, TimeUnit.MILLISECONDS));
    }
    
    public PlatformTask runRepeatingSync(final Runnable runnable, final long n) {
        return new BungeeViaTask(this.getProxy().getScheduler().schedule((Plugin)this, runnable, 0L, n * 50L, TimeUnit.MILLISECONDS));
    }
    
    public ViaCommandSender[] getOnlinePlayers() {
        final Collection players = this.getProxy().getPlayers();
        final ViaCommandSender[] array = new ViaCommandSender[players.size()];
        for (final ProxiedPlayer proxiedPlayer : players) {
            final ViaCommandSender[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = new BungeeCommandSender((CommandSender)proxiedPlayer);
        }
        return array;
    }
    
    public void sendMessage(final UUID uuid, final String s) {
        this.getProxy().getPlayer(uuid).sendMessage(s);
    }
    
    public boolean kickPlayer(final UUID uuid, final String s) {
        final ProxiedPlayer player = this.getProxy().getPlayer(uuid);
        if (player != null) {
            player.disconnect(s);
            return true;
        }
        return false;
    }
    
    public boolean isPluginEnabled() {
        return true;
    }
    
    public ViaAPI getApi() {
        return this.api;
    }
    
    public BungeeViaConfig getConf() {
        return this.config;
    }
    
    public ConfigurationProvider getConfigurationProvider() {
        return this.config;
    }
    
    public void onReload() {
    }
    
    public JsonObject getDump() {
        final JsonObject jsonObject = new JsonObject();
        final ArrayList<PluginInfo> list = new ArrayList<PluginInfo>();
        for (final Plugin plugin : ProxyServer.getInstance().getPluginManager().getPlugins()) {
            list.add(new PluginInfo(true, plugin.getDescription().getName(), plugin.getDescription().getVersion(), plugin.getDescription().getMain(), Collections.singletonList(plugin.getDescription().getAuthor())));
        }
        jsonObject.add("plugins", GsonUtil.getGson().toJsonTree(list));
        jsonObject.add("servers", GsonUtil.getGson().toJsonTree(ProtocolDetectorService.getDetectedIds()));
        return jsonObject;
    }
    
    public boolean isOldClientsAllowed() {
        return true;
    }
    
    public ViaVersionConfig getConf() {
        return this.getConf();
    }
}
