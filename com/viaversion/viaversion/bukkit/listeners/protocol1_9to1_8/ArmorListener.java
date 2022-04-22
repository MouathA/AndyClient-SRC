package com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8;

import com.viaversion.viaversion.bukkit.listeners.*;
import java.util.*;
import org.bukkit.plugin.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.event.player.*;
import com.viaversion.viaversion.api.*;

public class ArmorListener extends ViaBukkitListener
{
    private static final UUID ARMOR_ATTRIBUTE;
    
    public ArmorListener(final Plugin plugin) {
        super(plugin, Protocol1_9To1_8.class);
    }
    
    public void sendArmorUpdate(final Player player) {
        if (!this.isOnPipe(player)) {
            return;
        }
        final ItemStack[] armorContents = player.getInventory().getArmorContents();
        while (0 < armorContents.length) {
            final int n = 0 + ArmorType.findById(armorContents[0].getTypeId()).getArmorPoints();
            int n2 = 0;
            ++n2;
        }
        final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_9.ENTITY_PROPERTIES, null, this.getUserConnection(player));
        create.write(Type.VAR_INT, player.getEntityId());
        create.write(Type.INT, 1);
        create.write(Type.STRING, "generic.armor");
        create.write(Type.DOUBLE, 0.0);
        create.write(Type.VAR_INT, 1);
        create.write(Type.UUID, ArmorListener.ARMOR_ATTRIBUTE);
        create.write(Type.DOUBLE, 0);
        create.write(Type.BYTE, 0);
        create.scheduleSend(Protocol1_9To1_8.class);
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInventoryClick(final InventoryClickEvent inventoryClickEvent) {
        final HumanEntity whoClicked = inventoryClickEvent.getWhoClicked();
        if (whoClicked instanceof Player && inventoryClickEvent.getInventory() instanceof CraftingInventory) {
            final Player player = (Player)whoClicked;
            if (inventoryClickEvent.getCurrentItem() != null && ArmorType.isArmor(inventoryClickEvent.getCurrentItem().getTypeId())) {
                this.sendDelayedArmorUpdate(player);
                return;
            }
            if (inventoryClickEvent.getRawSlot() >= 5 && inventoryClickEvent.getRawSlot() <= 8) {
                this.sendDelayedArmorUpdate(player);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInteract(final PlayerInteractEvent playerInteractEvent) {
        if (playerInteractEvent.getItem() != null && (playerInteractEvent.getAction() == Action.RIGHT_CLICK_AIR || playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.getPlugin(), this::lambda$onInteract$0, 3L);
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onItemBreak(final PlayerItemBreakEvent playerItemBreakEvent) {
        this.sendDelayedArmorUpdate(playerItemBreakEvent.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onJoin(final PlayerJoinEvent playerJoinEvent) {
        this.sendDelayedArmorUpdate(playerJoinEvent.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onRespawn(final PlayerRespawnEvent playerRespawnEvent) {
        this.sendDelayedArmorUpdate(playerRespawnEvent.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onWorldChange(final PlayerChangedWorldEvent playerChangedWorldEvent) {
        this.sendArmorUpdate(playerChangedWorldEvent.getPlayer());
    }
    
    public void sendDelayedArmorUpdate(final Player player) {
        if (!this.isOnPipe(player)) {
            return;
        }
        Via.getPlatform().runSync(this::lambda$sendDelayedArmorUpdate$1);
    }
    
    private void lambda$sendDelayedArmorUpdate$1(final Player player) {
        this.sendArmorUpdate(player);
    }
    
    private void lambda$onInteract$0(final Player player) {
        this.sendArmorUpdate(player);
    }
    
    static {
        ARMOR_ATTRIBUTE = UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150");
    }
}
