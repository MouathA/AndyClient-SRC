package com.ibm.icu.impl;

import java.util.concurrent.*;
import java.lang.ref.*;

public abstract class SoftCache extends CacheBase
{
    private ConcurrentHashMap map;
    
    public SoftCache() {
        this.map = new ConcurrentHashMap();
    }
    
    @Override
    public final Object getInstance(final Object o, final Object o2) {
        final SettableSoftReference settableSoftReference = this.map.get(o);
        if (settableSoftReference != null) {
            // monitorenter(settableSoftReference2 = settableSoftReference)
            final Object value = SettableSoftReference.access$000(settableSoftReference).get();
            if (value != null) {
                // monitorexit(settableSoftReference2)
                return value;
            }
            final Object instance = this.createInstance(o, o2);
            if (instance != null) {
                SettableSoftReference.access$002(settableSoftReference, new SoftReference(instance));
            }
            // monitorexit(settableSoftReference2)
            return instance;
        }
        else {
            final Object instance2 = this.createInstance(o, o2);
            if (instance2 == null) {
                return null;
            }
            final SettableSoftReference settableSoftReference3 = this.map.putIfAbsent(o, new SettableSoftReference(instance2, null));
            if (settableSoftReference3 == null) {
                return instance2;
            }
            return SettableSoftReference.access$200(settableSoftReference3, instance2);
        }
    }
    
    private static final class SettableSoftReference
    {
        private SoftReference ref;
        
        private SettableSoftReference(final Object o) {
            this.ref = new SoftReference((T)o);
        }
        
        private synchronized Object setIfAbsent(final Object o) {
            final Object value = this.ref.get();
            if (value == null) {
                this.ref = new SoftReference((T)o);
                return o;
            }
            return value;
        }
        
        static SoftReference access$000(final SettableSoftReference settableSoftReference) {
            return settableSoftReference.ref;
        }
        
        static SoftReference access$002(final SettableSoftReference settableSoftReference, final SoftReference ref) {
            return settableSoftReference.ref = ref;
        }
        
        SettableSoftReference(final Object o, final SoftCache$1 object) {
            this(o);
        }
        
        static Object access$200(final SettableSoftReference settableSoftReference, final Object ifAbsent) {
            return settableSoftReference.setIfAbsent(ifAbsent);
        }
    }
}
