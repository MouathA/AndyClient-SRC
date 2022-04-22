package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.*;

public abstract class AbstractObjectCollection extends AbstractCollection implements ObjectCollection
{
    protected AbstractObjectCollection() {
    }
    
    @Override
    public abstract ObjectIterator iterator();
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final ObjectIterator iterator = this.iterator();
        int size = this.size();
        sb.append("{");
        while (size-- != 0) {
            sb.append(", ");
            final AbstractObjectCollection next = iterator.next();
            if (this == next) {
                sb.append("(this collection)");
            }
            else {
                sb.append(String.valueOf(next));
            }
        }
        sb.append("}");
        return sb.toString();
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}
