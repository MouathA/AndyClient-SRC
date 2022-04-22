package com.viaversion.viabackwards.listener;

import com.viaversion.viaversion.bukkit.listeners.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.*;
import org.bukkit.plugin.*;
import org.bukkit.event.player.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class LecternInteractListener extends ViaBukkitListener
{
    public LecternInteractListener(final BukkitPlugin bukkitPlugin) {
        super((Plugin)bukkitPlugin, Protocol1_13_2To1_14.class);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onLecternInteract(final PlayerInteractEvent playerInteractEvent) {
        final Block clickedBlock = playerInteractEvent.getClickedBlock();
        if (clickedBlock == null || clickedBlock.getType() != Material.LECTERN) {
            return;
        }
        final Player player = playerInteractEvent.getPlayer();
        if (!this.isOnPipe(player)) {
            return;
        }
        final ItemStack item = ((Lectern)clickedBlock.getState()).getInventory().getItem(0);
        if (item == null) {
            return;
        }
        final BookMeta bookMeta = (BookMeta)item.getItemMeta();
        final ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
        final BookMeta itemMeta = (BookMeta)itemStack.getItemMeta();
        itemMeta.setPages(bookMeta.getPages());
        itemMeta.setAuthor("an upsidedown person");
        itemMeta.setTitle("buk");
        itemStack.setItemMeta((ItemMeta)itemMeta);
        player.openBook(itemStack);
        playerInteractEvent.setCancelled(true);
    }
}
