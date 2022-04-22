package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.*;

public class BannerHandler implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler
{
    private static final int WALL_BANNER_START = 7110;
    private static final int WALL_BANNER_STOP = 7173;
    private static final int BANNER_START = 6854;
    private static final int BANNER_STOP = 7109;
    
    @Override
    public CompoundTag transform(final UserConnection userConnection, final int n, final CompoundTag compoundTag) {
        if (n >= 6854 && n <= 7109) {
            compoundTag.put("Base", new IntTag(15 - (n - 6854 >> 4)));
        }
        else if (n >= 7110 && n <= 7173) {
            compoundTag.put("Base", new IntTag(15 - (n - 7110 >> 2)));
        }
        else {
            ViaBackwards.getPlatform().getLogger().warning("Why does this block have the banner block entity? :(" + compoundTag);
        }
        final Tag value = compoundTag.get("Patterns");
        if (value instanceof ListTag) {
            for (final Tag tag : (ListTag)value) {
                if (!(tag instanceof CompoundTag)) {
                    continue;
                }
                final IntTag intTag = (IntTag)((CompoundTag)tag).get("Color");
                intTag.setValue(15 - intTag.asInt());
            }
        }
        return compoundTag;
    }
}
