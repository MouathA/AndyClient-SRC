package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage;

import com.viaversion.viaversion.api.connection.*;
import java.util.*;
import java.util.concurrent.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

public class BackwardsBlockStorage implements StorableObject
{
    private static final IntSet WHITELIST;
    private final Map blocks;
    
    public BackwardsBlockStorage() {
        this.blocks = new ConcurrentHashMap();
    }
    
    public void checkAndStore(final Position position, final int n) {
        if (!BackwardsBlockStorage.WHITELIST.contains(n)) {
            this.blocks.remove(position);
            return;
        }
        this.blocks.put(position, n);
    }
    
    public boolean isWelcome(final int n) {
        return BackwardsBlockStorage.WHITELIST.contains(n);
    }
    
    public Integer get(final Position position) {
        return this.blocks.get(position);
    }
    
    public int remove(final Position position) {
        return this.blocks.remove(position);
    }
    
    public void clear() {
        this.blocks.clear();
    }
    
    public Map getBlocks() {
        return this.blocks;
    }
    
    static {
        WHITELIST = new IntOpenHashSet(779);
        int n = 0;
        while (1099 <= 5286) {
            BackwardsBlockStorage.WHITELIST.add(1099);
            ++n;
        }
        while (1099 < 256) {
            BackwardsBlockStorage.WHITELIST.add(1847);
            ++n;
        }
        while (1099 <= 7173) {
            BackwardsBlockStorage.WHITELIST.add(1099);
            ++n;
        }
        BackwardsBlockStorage.WHITELIST.add(1647);
        while (1099 <= 5566) {
            BackwardsBlockStorage.WHITELIST.add(1099);
            ++n;
        }
        while (1099 <= 1039) {
            BackwardsBlockStorage.WHITELIST.add(1099);
            ++n;
        }
        while (1099 <= 1082) {
            BackwardsBlockStorage.WHITELIST.add(1099);
            ++n;
        }
        while (1099 <= 1110) {
            BackwardsBlockStorage.WHITELIST.add(1099);
            ++n;
        }
    }
}
