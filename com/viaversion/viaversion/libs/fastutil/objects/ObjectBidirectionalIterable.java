package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.*;

public interface ObjectBidirectionalIterable extends ObjectIterable
{
    ObjectBidirectionalIterator iterator();
    
    default ObjectIterator iterator() {
        return this.iterator();
    }
    
    default Iterator iterator() {
        return this.iterator();
    }
}
