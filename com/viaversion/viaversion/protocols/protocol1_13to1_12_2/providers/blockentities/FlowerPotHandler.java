package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.*;
import java.util.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.io.*;
import java.util.concurrent.*;

public class FlowerPotHandler implements BlockEntityProvider.BlockEntityHandler
{
    private static final Map flowers;
    
    public static void register(final String s, final byte b, final byte b2, final int n) {
        FlowerPotHandler.flowers.put(new Pair(s, b2), n);
        FlowerPotHandler.flowers.put(new Pair(b, b2), n);
    }
    
    @Override
    public int transform(final UserConnection userConnection, final CompoundTag compoundTag) {
        final Object o = compoundTag.contains("Item") ? compoundTag.get("Item").getValue() : null;
        final Object o2 = compoundTag.contains("Data") ? compoundTag.get("Data").getValue() : null;
        Serializable s;
        if (o instanceof String) {
            s = ((String)o).replace("minecraft:", "");
        }
        else if (o instanceof Number) {
            s = ((Number)o).byteValue();
        }
        else {
            s = 0;
        }
        Byte b;
        if (o2 instanceof Number) {
            b = ((Number)o2).byteValue();
        }
        else {
            b = 0;
        }
        final Integer n = FlowerPotHandler.flowers.get(new Pair(s, b));
        if (n != null) {
            return n;
        }
        final Integer n2 = FlowerPotHandler.flowers.get(new Pair(s, 0));
        if (n2 != null) {
            return n2;
        }
        return 5265;
    }
    
    static {
        flowers = new ConcurrentHashMap();
        register("air", (byte)0, (byte)0, 5265);
        register("sapling", (byte)6, (byte)0, 5266);
        register("sapling", (byte)6, (byte)1, 5267);
        register("sapling", (byte)6, (byte)2, 5268);
        register("sapling", (byte)6, (byte)3, 5269);
        register("sapling", (byte)6, (byte)4, 5270);
        register("sapling", (byte)6, (byte)5, 5271);
        register("tallgrass", (byte)31, (byte)2, 5272);
        register("yellow_flower", (byte)37, (byte)0, 5273);
        register("red_flower", (byte)38, (byte)0, 5274);
        register("red_flower", (byte)38, (byte)1, 5275);
        register("red_flower", (byte)38, (byte)2, 5276);
        register("red_flower", (byte)38, (byte)3, 5277);
        register("red_flower", (byte)38, (byte)4, 5278);
        register("red_flower", (byte)38, (byte)5, 5279);
        register("red_flower", (byte)38, (byte)6, 5280);
        register("red_flower", (byte)38, (byte)7, 5281);
        register("red_flower", (byte)38, (byte)8, 5282);
        register("red_mushroom", (byte)40, (byte)0, 5283);
        register("brown_mushroom", (byte)39, (byte)0, 5284);
        register("deadbush", (byte)32, (byte)0, 5285);
        register("cactus", (byte)81, (byte)0, 5286);
    }
}
