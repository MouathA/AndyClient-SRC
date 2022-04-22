package com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8;

import com.viaversion.viaversion.bukkit.listeners.*;
import org.bukkit.plugin.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import org.bukkit.event.block.*;
import org.bukkit.block.*;
import org.bukkit.event.*;
import org.bukkit.*;

public class PaperPatch extends ViaBukkitListener
{
    public PaperPatch(final Plugin plugin) {
        super(plugin, Protocol1_9To1_8.class);
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlace(final BlockPlaceEvent blockPlaceEvent) {
        if (this.isOnPipe(blockPlaceEvent.getPlayer())) {
            if (this.isPlacable(blockPlaceEvent.getBlockPlaced().getType())) {
                return;
            }
            final Location location = blockPlaceEvent.getPlayer().getLocation();
            final Block block = location.getBlock();
            if (block.equals(blockPlaceEvent.getBlock())) {
                blockPlaceEvent.setCancelled(true);
            }
            else if (block.getRelative(BlockFace.UP).equals(blockPlaceEvent.getBlock())) {
                blockPlaceEvent.setCancelled(true);
            }
            else {
                final Location subtract = location.clone().subtract(blockPlaceEvent.getBlock().getLocation().add(0.5, 0.0, 0.5));
                if (Math.abs(subtract.getX()) <= 0.8 && Math.abs(subtract.getZ()) <= 0.8) {
                    if (subtract.getY() <= 0.1 && subtract.getY() >= -0.1) {
                        blockPlaceEvent.setCancelled(true);
                        return;
                    }
                    if (blockPlaceEvent.getBlockAgainst().getFace(blockPlaceEvent.getBlock()) == BlockFace.UP && subtract.getY() < 1.0 && subtract.getY() >= 0.0) {
                        blockPlaceEvent.setCancelled(true);
                    }
                }
            }
        }
    }
    
    private boolean isPlacable(final Material material) {
        if (!material.isSolid()) {
            return true;
        }
        switch (material.getId()) {
            case 63:
            case 68:
            case 176:
            case 177: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
}
