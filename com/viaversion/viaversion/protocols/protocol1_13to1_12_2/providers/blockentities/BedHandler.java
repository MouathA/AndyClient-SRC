package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class BedHandler implements BlockEntityProvider.BlockEntityHandler
{
    @Override
    public int transform(final UserConnection userConnection, final CompoundTag compoundTag) {
        final BlockStorage blockStorage = (BlockStorage)userConnection.get(BlockStorage.class);
        final Position position = new Position((int)this.getLong((NumberTag)compoundTag.get("x")), (short)this.getLong((NumberTag)compoundTag.get("y")), (int)this.getLong((NumberTag)compoundTag.get("z")));
        if (!blockStorage.contains(position)) {
            Via.getPlatform().getLogger().warning("Received an bed color update packet, but there is no bed! O_o " + compoundTag);
            return -1;
        }
        int n = blockStorage.get(position).getOriginal() - 972 + 748;
        final Tag value = compoundTag.get("color");
        if (value != null) {
            n += ((NumberTag)value).asInt() * 16;
        }
        return n;
    }
    
    private long getLong(final NumberTag numberTag) {
        return numberTag.asLong();
    }
}
