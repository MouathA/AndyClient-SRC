package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.*;

public class SpawnerHandler implements BlockEntityProvider.BlockEntityHandler
{
    @Override
    public int transform(final UserConnection userConnection, final CompoundTag compoundTag) {
        if (compoundTag.contains("SpawnData") && compoundTag.get("SpawnData") instanceof CompoundTag) {
            final CompoundTag compoundTag2 = (CompoundTag)compoundTag.get("SpawnData");
            if (compoundTag2.contains("id") && compoundTag2.get("id") instanceof StringTag) {
                final StringTag stringTag = (StringTag)compoundTag2.get("id");
                stringTag.setValue(EntityNameRewriter.rewrite(stringTag.getValue()));
            }
        }
        return -1;
    }
}
