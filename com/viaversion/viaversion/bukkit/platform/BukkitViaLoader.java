package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import com.viaversion.viaversion.bukkit.listeners.*;
import com.viaversion.viaversion.bukkit.classgenerator.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.bukkit.listeners.multiversion.*;
import com.viaversion.viaversion.bukkit.listeners.protocol1_15to1_14_4.*;
import com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import java.util.concurrent.*;
import org.bukkit.entity.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.providers.*;
import com.viaversion.viaversion.bukkit.providers.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.*;
import org.bukkit.event.*;
import org.bukkit.scheduler.*;
import java.util.*;

public class BukkitViaLoader implements ViaPlatformLoader
{
    private final ViaVersionPlugin plugin;
    private final Set listeners;
    private final Set tasks;
    private HandItemCache handItemCache;
    
    public BukkitViaLoader(final ViaVersionPlugin plugin) {
        this.listeners = new HashSet();
        this.tasks = new HashSet();
        this.plugin = plugin;
    }
    
    public void registerListener(final Listener listener) {
        Bukkit.getPluginManager().registerEvents(this.storeListener(listener), (Plugin)this.plugin);
    }
    
    public Listener storeListener(final Listener listener) {
        this.listeners.add(listener);
        return listener;
    }
    
    @Override
    public void load() {
        this.registerListener((Listener)new UpdateListener());
        final ViaVersionPlugin viaVersionPlugin = (ViaVersionPlugin)Bukkit.getPluginManager().getPlugin("ViaVersion");
        ClassGenerator.registerPSConnectListener(viaVersionPlugin);
        final int lowestSupportedVersion = Via.getAPI().getServerVersion().lowestSupportedVersion();
        if (lowestSupportedVersion < ProtocolVersion.v1_9.getVersion()) {
            ((ArmorListener)this.storeListener((Listener)new ArmorListener((Plugin)viaVersionPlugin))).register();
            ((DeathListener)this.storeListener((Listener)new DeathListener((Plugin)viaVersionPlugin))).register();
            ((BlockListener)this.storeListener((Listener)new BlockListener((Plugin)viaVersionPlugin))).register();
            if (viaVersionPlugin.getConf().isItemCache()) {
                this.handItemCache = new HandItemCache();
                this.tasks.add(this.handItemCache.runTaskTimerAsynchronously((Plugin)viaVersionPlugin, 1L, 1L));
            }
        }
        if (lowestSupportedVersion < ProtocolVersion.v1_14.getVersion()) {
            final boolean b = viaVersionPlugin.getConf().is1_9HitboxFix() && lowestSupportedVersion < ProtocolVersion.v1_9.getVersion();
            if (false || viaVersionPlugin.getConf().is1_14HitboxFix()) {
                ((PlayerSneakListener)this.storeListener((Listener)new PlayerSneakListener(viaVersionPlugin, false, viaVersionPlugin.getConf().is1_14HitboxFix()))).register();
            }
        }
        if (lowestSupportedVersion < ProtocolVersion.v1_15.getVersion()) {
            Class.forName("org.bukkit.event.entity.EntityToggleGlideEvent");
            ((EntityToggleGlideListener)this.storeListener((Listener)new EntityToggleGlideListener(viaVersionPlugin))).register();
        }
        if (lowestSupportedVersion < ProtocolVersion.v1_12.getVersion() && !Boolean.getBoolean("com.viaversion.ignorePaperBlockPlacePatch")) {
            Class.forName("org.github.paperspigot.PaperSpigotConfig");
            if (false) {
                ((PaperPatch)this.storeListener((Listener)new PaperPatch((Plugin)viaVersionPlugin))).register();
            }
        }
        if (lowestSupportedVersion < ProtocolVersion.v1_9.getVersion()) {
            Via.getManager().getProviders().use(MovementTransmitterProvider.class, new BukkitViaMovementTransmitter());
            Via.getManager().getProviders().use(HandItemProvider.class, new HandItemProvider() {
                final BukkitViaLoader this$0;
                
                @Override
                public Item getHandItem(final UserConnection userConnection) {
                    if (BukkitViaLoader.access$000(this.this$0) != null) {
                        return BukkitViaLoader.access$000(this.this$0).getHandItem(userConnection.getProtocolInfo().getUuid());
                    }
                    return Bukkit.getScheduler().callSyncMethod(Bukkit.getPluginManager().getPlugin("ViaVersion"), (Callable)BukkitViaLoader$1::lambda$getHandItem$0).get(10L, TimeUnit.SECONDS);
                }
                
                private static Item lambda$getHandItem$0(final UserConnection userConnection) throws Exception {
                    final Player player = Bukkit.getPlayer(userConnection.getProtocolInfo().getUuid());
                    if (player != null) {
                        return HandItemCache.convert(player.getItemInHand());
                    }
                    return null;
                }
            });
        }
        if (lowestSupportedVersion < ProtocolVersion.v1_12.getVersion() && viaVersionPlugin.getConf().is1_12QuickMoveActionFix()) {
            Via.getManager().getProviders().use(InventoryQuickMoveProvider.class, new BukkitInventoryQuickMoveProvider());
        }
        if (lowestSupportedVersion < ProtocolVersion.v1_13.getVersion() && Via.getConfig().getBlockConnectionMethod().equalsIgnoreCase("world")) {
            final BukkitBlockConnectionProvider blockConnectionProvider = new BukkitBlockConnectionProvider();
            Via.getManager().getProviders().use(BlockConnectionProvider.class, blockConnectionProvider);
            ConnectionData.blockConnectionProvider = blockConnectionProvider;
        }
    }
    
    @Override
    public void unload() {
        final Iterator<Listener> iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            HandlerList.unregisterAll((Listener)iterator.next());
        }
        this.listeners.clear();
        final Iterator<BukkitTask> iterator2 = this.tasks.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().cancel();
        }
        this.tasks.clear();
    }
    
    static HandItemCache access$000(final BukkitViaLoader bukkitViaLoader) {
        return bukkitViaLoader.handItemCache;
    }
}
