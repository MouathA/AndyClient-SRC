package com.viaversion.viaversion.util;

import com.viaversion.viaversion.api.minecraft.entities.*;
import java.util.*;
import com.viaversion.viaversion.api.*;

public class EntityTypeUtil
{
    public static EntityType[] toOrderedArray(final EntityType[] array) {
        final ArrayList<Object> list = new ArrayList<Object>();
        while (0 < array.length) {
            final EntityType entityType = array[0];
            if (entityType.getId() != -1) {
                list.add(entityType);
            }
            int n = 0;
            ++n;
        }
        list.sort(Comparator.comparingInt(EntityType::getId));
        return list.toArray(new EntityType[0]);
    }
    
    public static EntityType getTypeFromId(final EntityType[] array, final int n, final EntityType entityType) {
        final EntityType entityType2;
        if (n < 0 || n >= array.length || (entityType2 = array[n]) == null) {
            Via.getPlatform().getLogger().severe("Could not find " + entityType.getClass().getSimpleName() + " type id " + n);
            return entityType;
        }
        return entityType2;
    }
}
