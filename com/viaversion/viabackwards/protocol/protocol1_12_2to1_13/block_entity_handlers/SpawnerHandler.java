package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class SpawnerHandler implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler
{
    @Override
    public CompoundTag transform(final UserConnection userConnection, final int n, final CompoundTag compoundTag) {
        final Tag value = compoundTag.get("SpawnData");
        if (value instanceof CompoundTag) {
            final Tag value2 = ((CompoundTag)value).get("id");
            if (value2 instanceof StringTag) {
                final StringTag stringTag = (StringTag)value2;
                stringTag.setValue(EntityNameRewrites.rewrite(stringTag.getValue()));
            }
        }
        return compoundTag;
    }
}
