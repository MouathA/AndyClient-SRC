package com.google.common.collect;

import java.util.concurrent.*;
import com.google.common.annotations.*;
import com.google.common.base.*;

@Beta
public final class Interners
{
    private Interners() {
    }
    
    public static Interner newStrongInterner() {
        return new Interner(new MapMaker().makeMap()) {
            final ConcurrentMap val$map;
            
            @Override
            public Object intern(final Object o) {
                final Object putIfAbsent = this.val$map.putIfAbsent(Preconditions.checkNotNull(o), o);
                return (putIfAbsent == null) ? o : putIfAbsent;
            }
        };
    }
    
    @GwtIncompatible("java.lang.ref.WeakReference")
    public static Interner newWeakInterner() {
        return new WeakInterner(null);
    }
    
    public static Function asFunction(final Interner interner) {
        return new InternerFunction((Interner)Preconditions.checkNotNull(interner));
    }
    
    private static class InternerFunction implements Function
    {
        private final Interner interner;
        
        public InternerFunction(final Interner interner) {
            this.interner = interner;
        }
        
        @Override
        public Object apply(final Object o) {
            return this.interner.intern(o);
        }
        
        @Override
        public int hashCode() {
            return this.interner.hashCode();
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof InternerFunction && this.interner.equals(((InternerFunction)o).interner);
        }
    }
    
    private static class WeakInterner implements Interner
    {
        private final MapMakerInternalMap map;
        
        private WeakInterner() {
            this.map = new MapMaker().weakKeys().keyEquivalence(Equivalence.equals()).makeCustomMap();
        }
        
        @Override
        public Object intern(final Object o) {
            while (true) {
                final MapMakerInternalMap.ReferenceEntry entry = this.map.getEntry(o);
                if (entry != null) {
                    final Object key = entry.getKey();
                    if (key != null) {
                        return key;
                    }
                }
                if (this.map.putIfAbsent(o, Dummy.VALUE) == null) {
                    return o;
                }
            }
        }
        
        WeakInterner(final Interners$1 interner) {
            this();
        }
        
        private enum Dummy
        {
            VALUE("VALUE", 0);
            
            private static final Dummy[] $VALUES;
            
            private Dummy(final String s, final int n) {
            }
            
            static {
                $VALUES = new Dummy[] { Dummy.VALUE };
            }
        }
    }
}
