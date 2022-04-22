package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viaversion.libs.fastutil.ints.*;

public class PaintingMapping
{
    private static final Int2ObjectMap PAINTINGS;
    
    public static void init() {
        add("Kebab");
        add("Aztec");
        add("Alban");
        add("Aztec2");
        add("Bomb");
        add("Plant");
        add("Wasteland");
        add("Pool");
        add("Courbet");
        add("Sea");
        add("Sunset");
        add("Creebet");
        add("Wanderer");
        add("Graham");
        add("Match");
        add("Bust");
        add("Stage");
        add("Void");
        add("SkullAndRoses");
        add("Wither");
        add("Fighters");
        add("Pointer");
        add("Pigscene");
        add("BurningSkull");
        add("Skeleton");
        add("DonkeyKong");
    }
    
    private static void add(final String s) {
        PaintingMapping.PAINTINGS.put(PaintingMapping.PAINTINGS.size(), s);
    }
    
    public static String getStringId(final int n) {
        return (String)PaintingMapping.PAINTINGS.getOrDefault(n, "kebab");
    }
    
    static {
        PAINTINGS = new Int2ObjectOpenHashMap(26, 0.99f);
    }
}
