package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import java.util.*;
import com.google.common.collect.*;

public class SpawnEggRewriter
{
    private static final BiMap spawnEggs;
    
    private static void registerSpawnEgg(final String s) {
        SpawnEggRewriter.spawnEggs.put(s, SpawnEggRewriter.spawnEggs.size());
    }
    
    public static int getSpawnEggId(final String s) {
        if (!SpawnEggRewriter.spawnEggs.containsKey(s)) {
            return -1;
        }
        return 0x17F0000 | (SpawnEggRewriter.spawnEggs.get(s) & 0xFFFF);
    }
    
    public static Optional getEntityId(final int n) {
        if (n >> 16 != 383) {
            return Optional.empty();
        }
        return Optional.ofNullable(SpawnEggRewriter.spawnEggs.inverse().get(n & 0xFFFF));
    }
    
    static {
        spawnEggs = HashBiMap.create();
        registerSpawnEgg("minecraft:bat");
        registerSpawnEgg("minecraft:blaze");
        registerSpawnEgg("minecraft:cave_spider");
        registerSpawnEgg("minecraft:chicken");
        registerSpawnEgg("minecraft:cow");
        registerSpawnEgg("minecraft:creeper");
        registerSpawnEgg("minecraft:donkey");
        registerSpawnEgg("minecraft:elder_guardian");
        registerSpawnEgg("minecraft:enderman");
        registerSpawnEgg("minecraft:endermite");
        registerSpawnEgg("minecraft:evocation_illager");
        registerSpawnEgg("minecraft:ghast");
        registerSpawnEgg("minecraft:guardian");
        registerSpawnEgg("minecraft:horse");
        registerSpawnEgg("minecraft:husk");
        registerSpawnEgg("minecraft:llama");
        registerSpawnEgg("minecraft:magma_cube");
        registerSpawnEgg("minecraft:mooshroom");
        registerSpawnEgg("minecraft:mule");
        registerSpawnEgg("minecraft:ocelot");
        registerSpawnEgg("minecraft:parrot");
        registerSpawnEgg("minecraft:pig");
        registerSpawnEgg("minecraft:polar_bear");
        registerSpawnEgg("minecraft:rabbit");
        registerSpawnEgg("minecraft:sheep");
        registerSpawnEgg("minecraft:shulker");
        registerSpawnEgg("minecraft:silverfish");
        registerSpawnEgg("minecraft:skeleton");
        registerSpawnEgg("minecraft:skeleton_horse");
        registerSpawnEgg("minecraft:slime");
        registerSpawnEgg("minecraft:spider");
        registerSpawnEgg("minecraft:squid");
        registerSpawnEgg("minecraft:stray");
        registerSpawnEgg("minecraft:vex");
        registerSpawnEgg("minecraft:villager");
        registerSpawnEgg("minecraft:vindication_illager");
        registerSpawnEgg("minecraft:witch");
        registerSpawnEgg("minecraft:wither_skeleton");
        registerSpawnEgg("minecraft:wolf");
        registerSpawnEgg("minecraft:zombie");
        registerSpawnEgg("minecraft:zombie_horse");
        registerSpawnEgg("minecraft:zombie_pigman");
        registerSpawnEgg("minecraft:zombie_villager");
    }
}
