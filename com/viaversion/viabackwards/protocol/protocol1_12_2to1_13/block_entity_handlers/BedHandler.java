package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class BedHandler implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler
{
    @Override
    public CompoundTag transform(final UserConnection userConnection, final int n, final CompoundTag compoundTag) {
        compoundTag.put("color", new IntTag(n - 748 >> 4));
        return compoundTag;
    }
}
