package com.google.common.escape;

import com.google.common.base.*;
import java.util.*;
import com.google.common.annotations.*;

@Beta
@GwtCompatible
public final class ArrayBasedEscaperMap
{
    private final char[][] replacementArray;
    private static final char[][] EMPTY_REPLACEMENT_ARRAY;
    
    public static ArrayBasedEscaperMap create(final Map map) {
        return new ArrayBasedEscaperMap(createReplacementArray(map));
    }
    
    private ArrayBasedEscaperMap(final char[][] replacementArray) {
        this.replacementArray = replacementArray;
    }
    
    char[][] getReplacementArray() {
        return this.replacementArray;
    }
    
    @VisibleForTesting
    static char[][] createReplacementArray(final Map map) {
        Preconditions.checkNotNull(map);
        if (map.isEmpty()) {
            return ArrayBasedEscaperMap.EMPTY_REPLACEMENT_ARRAY;
        }
        final char[][] array = new char[(char)Collections.max((Collection<? extends Character>)map.keySet()) + '\u0001'][];
        for (final char charValue : map.keySet()) {
            array[charValue] = ((String)map.get(charValue)).toCharArray();
        }
        return array;
    }
    
    static {
        EMPTY_REPLACEMENT_ARRAY = new char[0][0];
    }
}
