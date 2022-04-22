package com.google.common.collect;

import java.io.*;
import com.google.common.annotations.*;
import javax.annotation.*;

@GwtCompatible
final class Count implements Serializable
{
    private int value;
    
    Count(final int value) {
        this.value = value;
    }
    
    public int get() {
        return this.value;
    }
    
    public int getAndAdd(final int n) {
        final int value = this.value;
        this.value = value + n;
        return value;
    }
    
    public int addAndGet(final int n) {
        return this.value += n;
    }
    
    public void set(final int value) {
        this.value = value;
    }
    
    public int getAndSet(final int value) {
        final int value2 = this.value;
        this.value = value;
        return value2;
    }
    
    @Override
    public int hashCode() {
        return this.value;
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o instanceof Count && ((Count)o).value == this.value;
    }
    
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
}
