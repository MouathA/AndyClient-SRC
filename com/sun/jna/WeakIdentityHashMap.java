package com.sun.jna;

import java.util.*;
import java.lang.ref.*;

public class WeakIdentityHashMap implements Map
{
    private final ReferenceQueue queue;
    private Map backingStore;
    
    public WeakIdentityHashMap() {
        this.queue = new ReferenceQueue();
        this.backingStore = new HashMap();
    }
    
    public void clear() {
        this.backingStore.clear();
        this.reap();
    }
    
    public boolean containsKey(final Object o) {
        this.reap();
        return this.backingStore.containsKey(new IdentityWeakReference(o));
    }
    
    public boolean containsValue(final Object o) {
        this.reap();
        return this.backingStore.containsValue(o);
    }
    
    public Set entrySet() {
        this.reap();
        final HashSet<WeakIdentityHashMap$1> set = new HashSet<WeakIdentityHashMap$1>();
        for (final Entry<IdentityWeakReference, V> entry : this.backingStore.entrySet()) {
            set.add(new Entry(entry.getKey().get(), entry.getValue()) {
                private final Object val$key;
                private final Object val$value;
                private final WeakIdentityHashMap this$0;
                
                public Object getKey() {
                    return this.val$key;
                }
                
                public Object getValue() {
                    return this.val$value;
                }
                
                public Object setValue(final Object o) {
                    throw new UnsupportedOperationException();
                }
            });
        }
        return Collections.unmodifiableSet((Set<?>)set);
    }
    
    public Set keySet() {
        this.reap();
        final HashSet<Object> set = new HashSet<Object>();
        final Iterator<IdentityWeakReference> iterator = this.backingStore.keySet().iterator();
        while (iterator.hasNext()) {
            set.add(iterator.next().get());
        }
        return Collections.unmodifiableSet((Set<?>)set);
    }
    
    public boolean equals(final Object o) {
        return this.backingStore.equals(((WeakIdentityHashMap)o).backingStore);
    }
    
    public Object get(final Object o) {
        this.reap();
        return this.backingStore.get(new IdentityWeakReference(o));
    }
    
    public Object put(final Object o, final Object o2) {
        this.reap();
        return this.backingStore.put(new IdentityWeakReference(o), o2);
    }
    
    public int hashCode() {
        this.reap();
        return this.backingStore.hashCode();
    }
    
    public boolean isEmpty() {
        this.reap();
        return this.backingStore.isEmpty();
    }
    
    public void putAll(final Map map) {
        throw new UnsupportedOperationException();
    }
    
    public Object remove(final Object o) {
        this.reap();
        return this.backingStore.remove(new IdentityWeakReference(o));
    }
    
    public int size() {
        this.reap();
        return this.backingStore.size();
    }
    
    public Collection values() {
        this.reap();
        return this.backingStore.values();
    }
    
    private synchronized void reap() {
        for (Reference reference = this.queue.poll(); reference != null; reference = this.queue.poll()) {
            this.backingStore.remove(reference);
        }
    }
    
    static ReferenceQueue access$000(final WeakIdentityHashMap weakIdentityHashMap) {
        return weakIdentityHashMap.queue;
    }
    
    class IdentityWeakReference extends WeakReference
    {
        int hash;
        private final WeakIdentityHashMap this$0;
        
        IdentityWeakReference(final WeakIdentityHashMap this$0, final Object o) {
            this.this$0 = this$0;
            super(o, WeakIdentityHashMap.access$000(this$0));
            this.hash = System.identityHashCode(o);
        }
        
        public int hashCode() {
            return this.hash;
        }
        
        public boolean equals(final Object o) {
            return this == o || this.get() == ((IdentityWeakReference)o).get();
        }
    }
}
