package com.google.common.collect;

import com.google.common.annotations.*;

@GwtCompatible
public enum BoundType
{
    OPEN {
        @Override
        BoundType flip() {
            return BoundType$1.CLOSED;
        }
    }, 
    CLOSED {
        @Override
        BoundType flip() {
            return BoundType$2.OPEN;
        }
    };
    
    private static final BoundType[] $VALUES;
    
    private BoundType(final String s, final int n) {
    }
    
    static BoundType forBoolean(final boolean b) {
        return b ? BoundType.CLOSED : BoundType.OPEN;
    }
    
    abstract BoundType flip();
    
    BoundType(final String s, final int n, final BoundType$1 boundType) {
        this(s, n);
    }
    
    static {
        $VALUES = new BoundType[] { BoundType.OPEN, BoundType.CLOSED };
    }
}
