package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.function.*;
import org.jetbrains.annotations.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import java.util.*;

@Debug.Renderer(text = "\"long[\" + this.value.length + \"]\"", childrenArray = "this.value", hasChildren = "this.value.length > 0")
final class LongArrayBinaryTagImpl extends ArrayBinaryTagImpl implements LongArrayBinaryTag
{
    final long[] value;
    
    LongArrayBinaryTagImpl(final long[] value) {
        this.value = Arrays.copyOf(value, value.length);
    }
    
    @Override
    public long[] value() {
        return Arrays.copyOf(this.value, this.value.length);
    }
    
    @Override
    public int size() {
        return this.value.length;
    }
    
    @Override
    public long get(final int index) {
        ArrayBinaryTagImpl.checkIndex(index, this.value.length);
        return this.value[index];
    }
    
    @Override
    public PrimitiveIterator.OfLong iterator() {
        return new PrimitiveIterator.OfLong() {
            private int index;
            final LongArrayBinaryTagImpl this$0;
            
            @Override
            public boolean hasNext() {
                return this.index < this.this$0.value.length - 1;
            }
            
            @Override
            public long nextLong() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return this.this$0.value[this.index++];
            }
        };
    }
    
    @Override
    public Spliterator.OfLong spliterator() {
        return Arrays.spliterator(this.value);
    }
    
    @NotNull
    @Override
    public LongStream stream() {
        return Arrays.stream(this.value);
    }
    
    @Override
    public void forEachLong(@NotNull final LongConsumer action) {
        while (0 < this.value.length) {
            action.accept(this.value[0]);
            int n = 0;
            ++n;
        }
    }
    
    static long[] value(final LongArrayBinaryTag tag) {
        return (tag instanceof LongArrayBinaryTagImpl) ? ((LongArrayBinaryTagImpl)tag).value : tag.value();
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        return this == other || (other != null && this.getClass() == other.getClass() && Arrays.equals(this.value, ((LongArrayBinaryTagImpl)other).value));
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.value);
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.value));
    }
    
    @Override
    public Spliterator spliterator() {
        return this.spliterator();
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}
