package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.concurrent.*;
import com.google.common.base.*;

@Deprecated
@Beta
@GwtCompatible(emulated = true)
abstract class GenericMapMaker
{
    @GwtIncompatible("To be supported")
    MapMaker.RemovalListener removalListener;
    
    @GwtIncompatible("To be supported")
    abstract GenericMapMaker keyEquivalence(final Equivalence p0);
    
    public abstract GenericMapMaker initialCapacity(final int p0);
    
    abstract GenericMapMaker maximumSize(final int p0);
    
    public abstract GenericMapMaker concurrencyLevel(final int p0);
    
    @GwtIncompatible("java.lang.ref.WeakReference")
    public abstract GenericMapMaker weakKeys();
    
    @GwtIncompatible("java.lang.ref.WeakReference")
    public abstract GenericMapMaker weakValues();
    
    @Deprecated
    @GwtIncompatible("java.lang.ref.SoftReference")
    public abstract GenericMapMaker softValues();
    
    abstract GenericMapMaker expireAfterWrite(final long p0, final TimeUnit p1);
    
    @GwtIncompatible("To be supported")
    abstract GenericMapMaker expireAfterAccess(final long p0, final TimeUnit p1);
    
    @GwtIncompatible("To be supported")
    MapMaker.RemovalListener getRemovalListener() {
        return (MapMaker.RemovalListener)Objects.firstNonNull(this.removalListener, NullListener.INSTANCE);
    }
    
    public abstract ConcurrentMap makeMap();
    
    @GwtIncompatible("MapMakerInternalMap")
    abstract MapMakerInternalMap makeCustomMap();
    
    @Deprecated
    abstract ConcurrentMap makeComputingMap(final Function p0);
    
    @GwtIncompatible("To be supported")
    enum NullListener implements MapMaker.RemovalListener
    {
        INSTANCE("INSTANCE", 0);
        
        private static final NullListener[] $VALUES;
        
        private NullListener(final String s, final int n) {
        }
        
        @Override
        public void onRemoval(final MapMaker.RemovalNotification removalNotification) {
        }
        
        static {
            $VALUES = new NullListener[] { NullListener.INSTANCE };
        }
    }
}
