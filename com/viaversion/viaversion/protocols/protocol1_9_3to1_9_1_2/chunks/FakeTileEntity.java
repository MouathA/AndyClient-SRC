package com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.chunks;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

public class FakeTileEntity
{
    private static final Int2ObjectMap tileEntities;
    
    private static void register(final String s, final int... array) {
        while (0 < array.length) {
            final int n = array[0];
            final CompoundTag compoundTag = new CompoundTag();
            compoundTag.put("id", new StringTag(s));
            FakeTileEntity.tileEntities.put(n, compoundTag);
            int n2 = 0;
            ++n2;
        }
    }
    
    public static boolean isTileEntity(final int n) {
        return FakeTileEntity.tileEntities.containsKey(n);
    }
    
    public static CompoundTag createTileEntity(final int n, final int n2, final int n3, final int n4) {
        final CompoundTag compoundTag = (CompoundTag)FakeTileEntity.tileEntities.get(n4);
        if (compoundTag != null) {
            final CompoundTag clone = compoundTag.clone();
            clone.put("x", new IntTag(n));
            clone.put("y", new IntTag(n2));
            clone.put("z", new IntTag(n3));
            return clone;
        }
        return null;
    }
    
    static {
        tileEntities = new Int2ObjectOpenHashMap();
        register("Furnace", 61, 62);
        register("Chest", 54, 146);
        register("EnderChest", 130);
        register("RecordPlayer", 84);
        register("Trap", 23);
        register("Dropper", 158);
        register("Sign", 63, 68);
        register("MobSpawner", 52);
        register("Music", 25);
        register("Piston", 33, 34, 29, 36);
        register("Cauldron", 117);
        register("EnchantTable", 116);
        register("Airportal", 119, 120);
        register("Beacon", 138);
        register("Skull", 144);
        register("DLDetector", 178, 151);
        register("Hopper", 154);
        register("Comparator", 149, 150);
        register("FlowerPot", 140);
        register("Banner", 176, 177);
        register("EndGateway", 209);
        register("Control", 137);
    }
}
