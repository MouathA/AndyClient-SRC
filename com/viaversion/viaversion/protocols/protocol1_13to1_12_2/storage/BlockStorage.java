package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage;

import com.viaversion.viaversion.api.connection.*;
import java.util.*;
import com.viaversion.viaversion.libs.flare.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

public class BlockStorage implements StorableObject
{
    private static final IntSet WHITELIST;
    private final Map blocks;
    
    public BlockStorage() {
        this.blocks = SyncMap.hashmap();
    }
    
    public void store(final Position position, final int n) {
        this.store(position, n, -1);
    }
    
    public void store(final Position position, final int n, final int n2) {
        if (!BlockStorage.WHITELIST.contains(n)) {
            return;
        }
        this.blocks.put(position, new ReplacementData(n, n2));
    }
    
    public boolean isWelcome(final int n) {
        return BlockStorage.WHITELIST.contains(n);
    }
    
    public boolean contains(final Position position) {
        return this.blocks.containsKey(position);
    }
    
    public ReplacementData get(final Position position) {
        return this.blocks.get(position);
    }
    
    public ReplacementData remove(final Position position) {
        return this.blocks.remove(position);
    }
    
    static {
        (WHITELIST = new IntOpenHashSet(46, 0.99f)).add(5266);
        while (true) {
            BlockStorage.WHITELIST.add(972);
            int n = 0;
            ++n;
        }
    }
    
    public static final class ReplacementData
    {
        private final int original;
        private int replacement;
        
        public ReplacementData(final int original, final int replacement) {
            this.original = original;
            this.replacement = replacement;
        }
        
        public int getOriginal() {
            return this.original;
        }
        
        public int getReplacement() {
            return this.replacement;
        }
        
        public void setReplacement(final int replacement) {
            this.replacement = replacement;
        }
    }
}
