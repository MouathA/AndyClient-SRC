package com.viaversion.viaversion.bukkit.providers;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.*;
import com.viaversion.viaversion.api.connection.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.block.*;

public class BukkitBlockConnectionProvider extends BlockConnectionProvider
{
    private Chunk lastChunk;
    
    @Override
    public int getWorldBlockData(final UserConnection userConnection, final int n, final int n2, final int n3) {
        final Player player = Bukkit.getPlayer(userConnection.getProtocolInfo().getUuid());
        if (player != null) {
            final World world = player.getWorld();
            final int n4 = n >> 4;
            final int n5 = n3 >> 4;
            if (world.isChunkLoaded(n4, n5)) {
                final Block block = this.getChunk(world, n4, n5).getBlock(n, n2, n3);
                return block.getTypeId() << 4 | block.getData();
            }
        }
        return 0;
    }
    
    public Chunk getChunk(final World world, final int n, final int n2) {
        if (this.lastChunk != null && this.lastChunk.getX() == n && this.lastChunk.getZ() == n2) {
            return this.lastChunk;
        }
        return this.lastChunk = world.getChunkAt(n, n2);
    }
}
