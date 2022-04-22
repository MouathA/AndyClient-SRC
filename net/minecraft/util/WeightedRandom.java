package net.minecraft.util;

import java.util.*;

public class WeightedRandom
{
    private static final String __OBFID;
    
    public static int getTotalWeight(final Collection collection) {
        final Iterator<Item> iterator = collection.iterator();
        while (iterator.hasNext()) {
            final int n = 0 + iterator.next().itemWeight;
        }
        return 0;
    }
    
    public static Item getRandomItem(final Random random, final Collection collection, final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        return func_180166_a(collection, random.nextInt(n));
    }
    
    public static Item func_180166_a(final Collection collection, int n) {
        for (final Item item : collection) {
            n -= item.itemWeight;
            if (n < 0) {
                return item;
            }
        }
        return null;
    }
    
    public static Item getRandomItem(final Random random, final Collection collection) {
        return getRandomItem(random, collection, getTotalWeight(collection));
    }
    
    static {
        __OBFID = "CL_00001503";
    }
    
    public static class Item
    {
        protected int itemWeight;
        private static final String __OBFID;
        
        public Item(final int itemWeight) {
            this.itemWeight = itemWeight;
        }
        
        static {
            __OBFID = "CL_00001504";
        }
    }
}
