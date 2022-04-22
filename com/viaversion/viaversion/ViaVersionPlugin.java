package com.viaversion.viaversion;

import org.bukkit.plugin.java.*;
import com.viaversion.viaversion.commands.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.bukkit.util.*;
import com.viaversion.viaversion.bukkit.listeners.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import com.viaversion.viaversion.bukkit.platform.*;
import org.bukkit.scheduler.*;
import com.viaversion.viaversion.api.command.*;
import org.bukkit.entity.*;
import com.viaversion.viaversion.bukkit.commands.*;
import org.bukkit.command.*;
import org.bukkit.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.dump.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.unsupported.*;
import java.util.*;
import com.viaversion.viaversion.api.configuration.*;

public class ViaVersionPlugin extends JavaPlugin implements ViaPlatform
{
    private static ViaVersionPlugin instance;
    private final BukkitCommandHandler commandHandler;
    private final BukkitViaConfig conf;
    private final ViaAPI api;
    private final List queuedTasks;
    private final List asyncQueuedTasks;
    private final boolean protocolSupport;
    private boolean compatSpigotBuild;
    private boolean spigot;
    private boolean lateBind;
    
    public ViaVersionPlugin() {
        this.api = new BukkitViaAPI(this);
        this.queuedTasks = new ArrayList();
        this.asyncQueuedTasks = new ArrayList();
        this.spigot = true;
        ViaVersionPlugin.instance = this;
        this.commandHandler = new BukkitCommandHandler();
        Via.init(ViaManagerImpl.builder().platform(this).commandHandler(this.commandHandler).injector(new BukkitViaInjector()).loader(new BukkitViaLoader(this)).build());
        this.conf = new BukkitViaConfig();
        this.protocolSupport = (Bukkit.getPluginManager().getPlugin("ProtocolSupport") != null);
    }
    
    public void onLoad() {
        ((BukkitViaInjector)Via.getManager().getInjector()).setProtocolLib(Bukkit.getPluginManager().getPlugin("ProtocolLib") != null);
        Class.forName("org.spigotmc.SpigotConfig");
        NMSUtil.nms("PacketEncoder", "net.minecraft.network.PacketEncoder").getDeclaredField("version");
        this.compatSpigotBuild = true;
        this.getServer().getPluginManager().getPlugin("ViaBackwards");
        this.lateBind = !((BukkitViaInjector)Via.getManager().getInjector()).isBinded();
        this.getLogger().info("ViaVersion " + this.getDescription().getVersion() + (this.compatSpigotBuild ? "compat" : "") + " is now loaded" + (this.lateBind ? ", waiting for boot. (late-bind)" : ", injecting!"));
        if (!this.lateBind) {
            ((ViaManagerImpl)Via.getManager()).init();
        }
    }
    
    public void onEnable() {
        if (this.lateBind) {
            ((ViaManagerImpl)Via.getManager()).init();
        }
        this.getCommand("viaversion").setExecutor((CommandExecutor)this.commandHandler);
        this.getCommand("viaversion").setTabCompleter((TabCompleter)this.commandHandler);
        this.getServer().getPluginManager().registerEvents((Listener)new ProtocolLibEnableListener(), (Plugin)this);
        if (this.conf.isAntiXRay() && !this.spigot) {
            this.getLogger().info("You have anti-xray on in your config, since you're not using spigot it won't fix xray!");
        }
        final Iterator<Runnable> iterator = this.queuedTasks.iterator();
        while (iterator.hasNext()) {
            Bukkit.getScheduler().runTask((Plugin)this, (Runnable)iterator.next());
        }
        this.queuedTasks.clear();
        final Iterator<Runnable> iterator2 = this.asyncQueuedTasks.iterator();
        while (iterator2.hasNext()) {
            Bukkit.getScheduler().runTaskAsynchronously((Plugin)this, (Runnable)iterator2.next());
        }
        this.asyncQueuedTasks.clear();
    }
    
    public void onDisable() {
        ((ViaManagerImpl)Via.getManager()).destroy();
    }
    
    public String getPlatformName() {
        return Bukkit.getServer().getName();
    }
    
    public String getPlatformVersion() {
        return Bukkit.getServer().getVersion();
    }
    
    public String getPluginVersion() {
        return this.getDescription().getVersion();
    }
    
    public PlatformTask runAsync(final Runnable runnable) {
        if (this.isPluginEnabled()) {
            return new BukkitViaTask(this.getServer().getScheduler().runTaskAsynchronously((Plugin)this, runnable));
        }
        this.asyncQueuedTasks.add(runnable);
        return new BukkitViaTask(null);
    }
    
    public PlatformTask runSync(final Runnable runnable) {
        if (this.isPluginEnabled()) {
            return new BukkitViaTask(this.getServer().getScheduler().runTask((Plugin)this, runnable));
        }
        this.queuedTasks.add(runnable);
        return new BukkitViaTask(null);
    }
    
    public PlatformTask runSync(final Runnable runnable, final long n) {
        return new BukkitViaTask(this.getServer().getScheduler().runTaskLater((Plugin)this, runnable, n));
    }
    
    public PlatformTask runRepeatingSync(final Runnable runnable, final long n) {
        return new BukkitViaTask(this.getServer().getScheduler().runTaskTimer((Plugin)this, runnable, 0L, n));
    }
    
    public ViaCommandSender[] getOnlinePlayers() {
        final ViaCommandSender[] array = new ViaCommandSender[Bukkit.getOnlinePlayers().size()];
        for (final Player player : Bukkit.getOnlinePlayers()) {
            final ViaCommandSender[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = new BukkitCommandSender((CommandSender)player);
        }
        return array;
    }
    
    public void sendMessage(final UUID uuid, final String s) {
        final Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            player.sendMessage(s);
        }
    }
    
    public boolean kickPlayer(final UUID uuid, final String s) {
        final Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            player.kickPlayer(s);
            return true;
        }
        return false;
    }
    
    public boolean isPluginEnabled() {
        return Bukkit.getPluginManager().getPlugin("ViaVersion").isEnabled();
    }
    
    public ConfigurationProvider getConfigurationProvider() {
        return this.conf;
    }
    
    public void onReload() {
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            this.getLogger().severe("ViaVersion is already loaded, we're going to kick all the players... because otherwise we'll crash because of ProtocolLib.");
            final Iterator<Player> iterator = Bukkit.getOnlinePlayers().iterator();
            while (iterator.hasNext()) {
                iterator.next().kickPlayer(ChatColor.translateAlternateColorCodes('&', this.conf.getReloadDisconnectMsg()));
            }
        }
        else {
            this.getLogger().severe("ViaVersion is already loaded, this should work fine. If you get any console errors, try rebooting.");
        }
    }
    
    public JsonObject getDump() {
        final JsonObject jsonObject = new JsonObject();
        final ArrayList<PluginInfo> list = new ArrayList<PluginInfo>();
        final Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
        while (0 < plugins.length) {
            final Plugin plugin = plugins[0];
            list.add(new PluginInfo(plugin.isEnabled(), plugin.getDescription().getName(), plugin.getDescription().getVersion(), plugin.getDescription().getMain(), plugin.getDescription().getAuthors()));
            int n = 0;
            ++n;
        }
        jsonObject.add("plugins", GsonUtil.getGson().toJsonTree(list));
        return jsonObject;
    }
    
    public boolean isOldClientsAllowed() {
        return !this.protocolSupport;
    }
    
    public BukkitViaConfig getConf() {
        return this.conf;
    }
    
    public ViaAPI getApi() {
        return this.api;
    }
    
    public final Collection getUnsupportedSoftwareClasses() {
        final ArrayList<UnsupportedSoftware> list = new ArrayList<UnsupportedSoftware>(super.getUnsupportedSoftwareClasses());
        list.add(new UnsupportedSoftwareImpl.Builder().name("Yatopia").reason("You are using server software that - outside of possibly breaking ViaVersion - can also cause severe damage to your server's integrity as a whole.").addClassName("org.yatopiamc.yatopia.server.YatopiaConfig").addClassName("net.yatopia.api.event.PlayerAttackEntityEvent").addClassName("yatopiamc.org.yatopia.server.YatopiaConfig").addMethod("org.bukkit.Server", "getLastTickTime").build());
        return Collections.unmodifiableList((List<?>)list);
    }
    
    public boolean isLateBind() {
        return this.lateBind;
    }
    
    public boolean isCompatSpigotBuild() {
        return this.compatSpigotBuild;
    }
    
    public boolean isSpigot() {
        return this.spigot;
    }
    
    public boolean isProtocolSupport() {
        return this.protocolSupport;
    }
    
    public static ViaVersionPlugin getInstance() {
        return ViaVersionPlugin.instance;
    }
    
    public ViaVersionConfig getConf() {
        return this.getConf();
    }
}
