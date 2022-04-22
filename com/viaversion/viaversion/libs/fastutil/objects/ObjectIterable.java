package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.*;

public interface ObjectIterable extends Iterable
{
    ObjectIterator iterator();
    
    default ObjectSpliterator spliterator() {
        return ObjectSpliterators.asSpliteratorUnknownSize(this.iterator(), 0);
    }
    
    default Spliterator spliterator() {
        return this.spliterator();
    }
    
    default Iterator iterator() {
        return this.iterator();
    }
}
