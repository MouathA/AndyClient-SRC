package com.viaversion.viaversion.api.minecraft;

import java.util.*;

public enum RegistryType
{
    BLOCK("BLOCK", 0, "block"), 
    ITEM("ITEM", 1, "item"), 
    FLUID("FLUID", 2, "fluid"), 
    ENTITY("ENTITY", 3, "entity_type"), 
    GAME_EVENT("GAME_EVENT", 4, "game_event");
    
    private static final Map MAP;
    private static final RegistryType[] VALUES;
    private final String resourceLocation;
    private static final RegistryType[] $VALUES;
    
    public static RegistryType[] getValues() {
        return RegistryType.VALUES;
    }
    
    public static RegistryType getByKey(final String s) {
        return RegistryType.MAP.get(s);
    }
    
    private RegistryType(final String s, final int n, final String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }
    
    @Deprecated
    public String getResourceLocation() {
        return this.resourceLocation;
    }
    
    public String resourceLocation() {
        return this.resourceLocation;
    }
    
    static {
        $VALUES = new RegistryType[] { RegistryType.BLOCK, RegistryType.ITEM, RegistryType.FLUID, RegistryType.ENTITY, RegistryType.GAME_EVENT };
        MAP = new HashMap();
        VALUES = values();
        final RegistryType[] values = getValues();
        while (0 < values.length) {
            final RegistryType registryType = values[0];
            RegistryType.MAP.put(registryType.resourceLocation, registryType);
            int n = 0;
            ++n;
        }
    }
}
