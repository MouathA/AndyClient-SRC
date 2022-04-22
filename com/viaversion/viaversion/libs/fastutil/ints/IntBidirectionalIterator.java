package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.*;

public interface IntBidirectionalIterator extends IntIterator, ObjectBidirectionalIterator
{
    int previousInt();
    
    @Deprecated
    default Integer previous() {
        return this.previousInt();
    }
    
    default int back(final int n) {
        int n2 = n;
        while (n2-- != 0 && this.hasPrevious()) {
            this.previousInt();
        }
        return n - n2 - 1;
    }
    
    default int skip(final int n) {
        return super.skip(n);
    }
    
    @Deprecated
    default Object previous() {
        return this.previous();
    }
}
