package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.*;
import java.util.*;

public class WrappedBlockData
{
    private final String minecraftKey;
    private final int savedBlockStateId;
    private final LinkedHashMap blockData;
    
    public static WrappedBlockData fromString(final String s) {
        final String[] split = s.split("\\[");
        final WrappedBlockData wrappedBlockData = new WrappedBlockData(split[0], ConnectionData.getId(s));
        if (split.length > 1) {
            final String[] split2 = split[1].replace("]", "").split(",");
            while (0 < split2.length) {
                final String[] split3 = split2[0].split("=");
                wrappedBlockData.blockData.put(split3[0], split3[1]);
                int n = 0;
                ++n;
            }
        }
        return wrappedBlockData;
    }
    
    public static WrappedBlockData fromStateId(final int n) {
        final String key = ConnectionData.getKey(n);
        if (key != null) {
            return fromString(key);
        }
        Via.getPlatform().getLogger().info("Unable to get blockdata from " + n);
        return fromString("minecraft:air");
    }
    
    private WrappedBlockData(final String minecraftKey, final int savedBlockStateId) {
        this.blockData = new LinkedHashMap();
        this.minecraftKey = minecraftKey;
        this.savedBlockStateId = savedBlockStateId;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.minecraftKey + "[");
        for (final Map.Entry<String, V> entry : this.blockData.entrySet()) {
            sb.append(entry.getKey()).append('=').append((String)entry.getValue()).append(',');
        }
        return sb.substring(0, sb.length() - 1) + "]";
    }
    
    public String getMinecraftKey() {
        return this.minecraftKey;
    }
    
    public int getSavedBlockStateId() {
        return this.savedBlockStateId;
    }
    
    public int getBlockStateId() {
        return ConnectionData.getId(this.toString());
    }
    
    public WrappedBlockData set(final String s, final Object o) {
        if (!this.hasData(s)) {
            throw new UnsupportedOperationException("No blockdata found for " + s + " at " + this.minecraftKey);
        }
        this.blockData.put(s, o.toString());
        return this;
    }
    
    public String getValue(final String s) {
        return this.blockData.get(s);
    }
    
    public boolean hasData(final String s) {
        return this.blockData.containsKey(s);
    }
}
