package org.lwjgl.util.mapped;

import java.util.*;

final class MappedForeach implements Iterable
{
    final MappedObject mapped;
    final int elementCount;
    
    MappedForeach(final MappedObject mapped, final int elementCount) {
        this.mapped = mapped;
        this.elementCount = elementCount;
    }
    
    public Iterator iterator() {
        return new Iterator() {
            private int index;
            final MappedForeach this$0;
            
            public boolean hasNext() {
                return this.index < this.this$0.elementCount;
            }
            
            public MappedObject next() {
                this.this$0.mapped.setViewAddress(this.this$0.mapped.getViewAddress(this.index++));
                return this.this$0.mapped;
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
            
            public Object next() {
                return this.next();
            }
        };
    }
}
