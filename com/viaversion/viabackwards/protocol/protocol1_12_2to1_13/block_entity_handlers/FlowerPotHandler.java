package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

public class FlowerPotHandler implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler
{
    private static final Int2ObjectMap FLOWERS;
    private static final Pair AIR;
    
    private static void register(final int n, final String s, final byte b) {
        FlowerPotHandler.FLOWERS.put(n, new Pair(s, b));
    }
    
    public static boolean isFlowah(final int n) {
        return n >= 5265 && n <= 5286;
    }
    
    public Pair getOrDefault(final int n) {
        final Pair pair = (Pair)FlowerPotHandler.FLOWERS.get(n);
        return (pair != null) ? pair : FlowerPotHandler.AIR;
    }
    
    @Override
    public CompoundTag transform(final UserConnection userConnection, final int n, final CompoundTag compoundTag) {
        final Pair orDefault = this.getOrDefault(n);
        compoundTag.put("Item", new StringTag((String)orDefault.getKey()));
        compoundTag.put("Data", new IntTag((byte)orDefault.getValue()));
        return compoundTag;
    }
    
    static {
        FLOWERS = new Int2ObjectOpenHashMap(22, 0.99f);
        AIR = new Pair("minecraft:air", 0);
        FlowerPotHandler.FLOWERS.put(5265, FlowerPotHandler.AIR);
        register(5266, "minecraft:sapling", (byte)0);
        register(5267, "minecraft:sapling", (byte)1);
        register(5268, "minecraft:sapling", (byte)2);
        register(5269, "minecraft:sapling", (byte)3);
        register(5270, "minecraft:sapling", (byte)4);
        register(5271, "minecraft:sapling", (byte)5);
        register(5272, "minecraft:tallgrass", (byte)2);
        register(5273, "minecraft:yellow_flower", (byte)0);
        register(5274, "minecraft:red_flower", (byte)0);
        register(5275, "minecraft:red_flower", (byte)1);
        register(5276, "minecraft:red_flower", (byte)2);
        register(5277, "minecraft:red_flower", (byte)3);
        register(5278, "minecraft:red_flower", (byte)4);
        register(5279, "minecraft:red_flower", (byte)5);
        register(5280, "minecraft:red_flower", (byte)6);
        register(5281, "minecraft:red_flower", (byte)7);
        register(5282, "minecraft:red_flower", (byte)8);
        register(5283, "minecraft:red_mushroom", (byte)0);
        register(5284, "minecraft:brown_mushroom", (byte)0);
        register(5285, "minecraft:deadbush", (byte)0);
        register(5286, "minecraft:cactus", (byte)0);
    }
}
