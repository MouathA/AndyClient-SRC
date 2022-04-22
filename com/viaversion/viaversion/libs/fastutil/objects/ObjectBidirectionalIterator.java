package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.*;

public interface ObjectBidirectionalIterator extends ObjectIterator, BidirectionalIterator
{
    default int back(final int n) {
        int n2 = n;
        while (n2-- != 0 && this.hasPrevious()) {
            this.previous();
        }
        return n - n2 - 1;
    }
    
    default int skip(final int n) {
        return super.skip(n);
    }
}
