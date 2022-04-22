package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.function.*;
import org.jetbrains.annotations.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import java.util.*;

@Debug.Renderer(text = "\"int[\" + this.value.length + \"]\"", childrenArray = "this.value", hasChildren = "this.value.length > 0")
final class IntArrayBinaryTagImpl extends ArrayBinaryTagImpl implements IntArrayBinaryTag
{
    final int[] value;
    
    IntArrayBinaryTagImpl(final int... value) {
        this.value = Arrays.copyOf(value, value.length);
    }
    
    @Override
    public int[] value() {
        return Arrays.copyOf(this.value, this.value.length);
    }
    
    @Override
    public int size() {
        return this.value.length;
    }
    
    @Override
    public int get(final int index) {
        ArrayBinaryTagImpl.checkIndex(index, this.value.length);
        return this.value[index];
    }
    
    @Override
    public PrimitiveIterator.OfInt iterator() {
        return new PrimitiveIterator.OfInt() {
            private int index;
            final IntArrayBinaryTagImpl this$0;
            
            @Override
            public int nextInt() {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: if_icmpge       12
                //     4: new             Ljava/util/NoSuchElementException;
                //     7: dup            
                //     8: invokespecial   java/util/NoSuchElementException.<init>:()V
                //    11: athrow         
                //    12: aload_0        
                //    13: getfield        com/viaversion/viaversion/libs/kyori/adventure/nbt/IntArrayBinaryTagImpl$1.this$0:Lcom/viaversion/viaversion/libs/kyori/adventure/nbt/IntArrayBinaryTagImpl;
                //    16: getfield        com/viaversion/viaversion/libs/kyori/adventure/nbt/IntArrayBinaryTagImpl.value:[I
                //    19: aload_0        
                //    20: dup            
                //    21: getfield        com/viaversion/viaversion/libs/kyori/adventure/nbt/IntArrayBinaryTagImpl$1.index:I
                //    24: dup_x1         
                //    25: iconst_1       
                //    26: iadd           
                //    27: putfield        com/viaversion/viaversion/libs/kyori/adventure/nbt/IntArrayBinaryTagImpl$1.index:I
                //    30: iaload         
                //    31: ireturn        
                // 
                // The error that occurred was:
                // 
                // java.lang.ArrayIndexOutOfBoundsException
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
        };
    }
    
    @Override
    public Spliterator.OfInt spliterator() {
        return Arrays.spliterator(this.value);
    }
    
    @NotNull
    @Override
    public IntStream stream() {
        return Arrays.stream(this.value);
    }
    
    @Override
    public void forEachInt(@NotNull final IntConsumer action) {
        while (0 < this.value.length) {
            action.accept(this.value[0]);
            int n = 0;
            ++n;
        }
    }
    
    static int[] value(final IntArrayBinaryTag tag) {
        return (tag instanceof IntArrayBinaryTagImpl) ? ((IntArrayBinaryTagImpl)tag).value : tag.value();
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        return this == other || (other != null && this.getClass() == other.getClass() && Arrays.equals(this.value, ((IntArrayBinaryTagImpl)other).value));
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
