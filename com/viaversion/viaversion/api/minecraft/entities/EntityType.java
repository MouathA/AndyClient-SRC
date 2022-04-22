package com.viaversion.viaversion.api.minecraft.entities;

public interface EntityType
{
    int getId();
    
    EntityType getParent();
    
    String name();
    
    default boolean is(final EntityType... array) {
        while (0 < array.length) {
            if (this == array[0]) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    default boolean is(final EntityType entityType) {
        return this == entityType;
    }
    
    default boolean isOrHasParent(final EntityType entityType) {
        EntityType parent = this;
        while (parent != entityType) {
            parent = parent.getParent();
            if (parent == null) {
                return false;
            }
        }
        return true;
    }
}
