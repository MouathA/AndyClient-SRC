package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class SkullHandler implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler
{
    private static final int SKULL_START = 5447;
    
    @Override
    public CompoundTag transform(final UserConnection userConnection, final int n, final CompoundTag compoundTag) {
        final int n2 = n - 5447;
        final int n3 = n2 % 20;
        compoundTag.put("SkullType", new ByteTag((byte)Math.floor(n2 / 20.0f)));
        if (n3 < 4) {
            return compoundTag;
        }
        compoundTag.put("Rot", new ByteTag((byte)(n3 - 4 & 0xFF)));
        return compoundTag;
    }
}
