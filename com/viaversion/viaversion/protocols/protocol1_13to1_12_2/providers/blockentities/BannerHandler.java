package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import java.util.*;

public class BannerHandler implements BlockEntityProvider.BlockEntityHandler
{
    private static final int WALL_BANNER_START = 7110;
    private static final int WALL_BANNER_STOP = 7173;
    private static final int BANNER_START = 6854;
    private static final int BANNER_STOP = 7109;
    
    @Override
    public int transform(final UserConnection userConnection, final CompoundTag compoundTag) {
        final BlockStorage blockStorage = (BlockStorage)userConnection.get(BlockStorage.class);
        final Position position = new Position((int)this.getLong((NumberTag)compoundTag.get("x")), (short)this.getLong((NumberTag)compoundTag.get("y")), (int)this.getLong((NumberTag)compoundTag.get("z")));
        if (!blockStorage.contains(position)) {
            Via.getPlatform().getLogger().warning("Received an banner color update packet, but there is no banner! O_o " + compoundTag);
            return -1;
        }
        int original = blockStorage.get(position).getOriginal();
        if (compoundTag.get("Base") != null) {
            ((NumberTag)compoundTag.get("Base")).asInt();
        }
        if (original >= 6854 && original <= 7109) {
            original += 240;
        }
        else if (original >= 7110 && original <= 7173) {
            original += 60;
        }
        else {
            Via.getPlatform().getLogger().warning("Why does this block have the banner block entity? :(" + compoundTag);
        }
        if (compoundTag.get("Patterns") instanceof ListTag) {
            for (final Tag tag : (ListTag)compoundTag.get("Patterns")) {
                if (tag instanceof CompoundTag) {
                    final Tag value = ((CompoundTag)tag).get("Color");
                    if (!(value instanceof IntTag)) {
                        continue;
                    }
                    ((IntTag)value).setValue(15 - (int)value.getValue());
                }
            }
        }
        final Tag value2 = compoundTag.get("CustomName");
        if (value2 instanceof StringTag) {
            ((StringTag)value2).setValue(ChatRewriter.legacyTextToJsonString(((StringTag)value2).getValue()));
        }
        return original;
    }
    
    private long getLong(final NumberTag numberTag) {
        return numberTag.asLong();
    }
}
