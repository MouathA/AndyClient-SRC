package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.*;
import java.util.*;

public interface ObjectCollection extends Collection, ObjectIterable
{
    ObjectIterator iterator();
    
    default ObjectSpliterator spliterator() {
        return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this), 64);
    }
    
    default Spliterator spliterator() {
        return this.spliterator();
    }
    
    default Iterator iterator() {
        return this.iterator();
    }
}
