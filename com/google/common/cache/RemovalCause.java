package com.google.common.cache;

import com.google.common.annotations.*;

@Beta
@GwtCompatible
public enum RemovalCause
{
    EXPLICIT {
        @Override
        boolean wasEvicted() {
            return false;
        }
    }, 
    REPLACED {
        @Override
        boolean wasEvicted() {
            return false;
        }
    }, 
    COLLECTED {
        @Override
        boolean wasEvicted() {
            return true;
        }
    }, 
    EXPIRED {
        @Override
        boolean wasEvicted() {
            return true;
        }
    }, 
    SIZE {
        @Override
        boolean wasEvicted() {
            return true;
        }
    };
    
    private static final RemovalCause[] $VALUES;
    
    private RemovalCause(final String s, final int n) {
    }
    
    abstract boolean wasEvicted();
    
    RemovalCause(final String s, final int n, final RemovalCause$1 removalCause) {
        this(s, n);
    }
    
    static {
        $VALUES = new RemovalCause[] { RemovalCause.EXPLICIT, RemovalCause.REPLACED, RemovalCause.COLLECTED, RemovalCause.EXPIRED, RemovalCause.SIZE };
    }
}
