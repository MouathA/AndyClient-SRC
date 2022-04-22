package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;
import java.util.stream.*;
import java.util.function.*;
import java.util.*;

public interface LongArrayBinaryTag extends ArrayBinaryTag, Iterable
{
    @NotNull
    default LongArrayBinaryTag of(final long... value) {
        return new LongArrayBinaryTagImpl(value);
    }
    
    @NotNull
    default BinaryTagType type() {
        return BinaryTagTypes.LONG_ARRAY;
    }
    
    long[] value();
    
    int size();
    
    long get(final int index);
    
    PrimitiveIterator.OfLong iterator();
    
    Spliterator.OfLong spliterator();
    
    @NotNull
    LongStream stream();
    
    void forEachLong(@NotNull final LongConsumer action);
    
    default Spliterator spliterator() {
        return this.spliterator();
    }
    
    default Iterator iterator() {
        return this.iterator();
    }
}
