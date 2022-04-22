package com.viaversion.viabackwards.listener;

import com.viaversion.viaversion.bukkit.listeners.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.*;
import org.bukkit.plugin.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.event.*;

public class FireExtinguishListener extends ViaBukkitListener
{
    public FireExtinguishListener(final BukkitPlugin bukkitPlugin) {
        super((Plugin)bukkitPlugin, Protocol1_15_2To1_16.class);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onFireExtinguish(final PlayerInteractEvent playerInteractEvent) {
        if (playerInteractEvent.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }
        final Block clickedBlock = playerInteractEvent.getClickedBlock();
        if (clickedBlock == null) {
            return;
        }
        if (!this.isOnPipe(playerInteractEvent.getPlayer())) {
            return;
        }
        final Block relative = clickedBlock.getRelative(playerInteractEvent.getBlockFace());
        if (relative.getType() == Material.FIRE) {
            playerInteractEvent.setCancelled(true);
            relative.setType(Material.AIR);
        }
    }
}
