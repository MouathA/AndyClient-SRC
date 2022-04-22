package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.*;

public class SkullHandler implements BlockEntityProvider.BlockEntityHandler
{
    private static final int SKULL_WALL_START = 5447;
    private static final int SKULL_END = 5566;
    
    @Override
    public int transform(final UserConnection userConnection, final CompoundTag compoundTag) {
        final BlockStorage blockStorage = (BlockStorage)userConnection.get(BlockStorage.class);
        final Position position = new Position((int)this.getLong((NumberTag)compoundTag.get("x")), (short)this.getLong((NumberTag)compoundTag.get("y")), (int)this.getLong((NumberTag)compoundTag.get("z")));
        if (!blockStorage.contains(position)) {
            Via.getPlatform().getLogger().warning("Received an head update packet, but there is no head! O_o " + compoundTag);
            return -1;
        }
        int original = blockStorage.get(position).getOriginal();
        if (original >= 5447 && original <= 5566) {
            if (compoundTag.get("SkullType") != null) {
                original += ((NumberTag)compoundTag.get("SkullType")).asInt() * 20;
            }
            if (compoundTag.contains("Rot")) {
                original += ((NumberTag)compoundTag.get("Rot")).asInt();
            }
            return original;
        }
        Via.getPlatform().getLogger().warning("Why does this block have the skull block entity? " + compoundTag);
        return -1;
    }
    
    private long getLong(final NumberTag numberTag) {
        return numberTag.asLong();
    }
}
