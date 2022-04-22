package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data;

import com.viaversion.viaversion.api.minecraft.entities.*;

public class EntityTypeMapping
{
    public static int getOldEntityId(final int n) {
        if (n == 4) {
            return Entity1_14Types.PUFFERFISH.getId();
        }
        return (n >= 5) ? (n - 1) : n;
    }
}
