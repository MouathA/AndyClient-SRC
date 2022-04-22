package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.*;

public interface IntBidirectionalIterable extends IntIterable
{
    IntBidirectionalIterator iterator();
    
    default IntIterator iterator() {
        return this.iterator();
    }
    
    default Iterator iterator() {
        return this.iterator();
    }
}
