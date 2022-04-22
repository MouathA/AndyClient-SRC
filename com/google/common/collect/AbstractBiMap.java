package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.base.*;
import java.util.*;
import java.io.*;

@GwtCompatible(emulated = true)
abstract class AbstractBiMap extends ForwardingMap implements BiMap, Serializable
{
    private transient Map delegate;
    transient AbstractBiMap inverse;
    private transient Set keySet;
    private transient Set valueSet;
    private transient Set entrySet;
    @GwtIncompatible("Not needed in emulated source.")
    private static final long serialVersionUID = 0L;
    
    AbstractBiMap(final Map map, final Map map2) {
        this.setDelegates(map, map2);
    }
    
    private AbstractBiMap(final Map delegate, final AbstractBiMap inverse) {
        this.delegate = delegate;
        this.inverse = inverse;
    }
    
    @Override
    protected Map delegate() {
        return this.delegate;
    }
    
    Object checkKey(@Nullable final Object o) {
        return o;
    }
    
    Object checkValue(@Nullable final Object o) {
        return o;
    }
    
    void setDelegates(final Map delegate, final Map map) {
        Preconditions.checkState(this.delegate == null);
        Preconditions.checkState(this.inverse == null);
        Preconditions.checkArgument(delegate.isEmpty());
        Preconditions.checkArgument(map.isEmpty());
        Preconditions.checkArgument(delegate != map);
        this.delegate = delegate;
        this.inverse = new Inverse(map, this, null);
    }
    
    void setInverse(final AbstractBiMap inverse) {
        this.inverse = inverse;
    }
    
    @Override
    public boolean containsValue(@Nullable final Object o) {
        return this.inverse.containsKey(o);
    }
    
    @Override
    public Object put(@Nullable final Object o, @Nullable final Object o2) {
        return this.putInBothMaps(o, o2, false);
    }
    
    @Override
    public Object forcePut(@Nullable final Object o, @Nullable final Object o2) {
        return this.putInBothMaps(o, o2, true);
    }
    
    private Object putInBothMaps(@Nullable final Object o, @Nullable final Object o2, final boolean b) {
        this.checkKey(o);
        this.checkValue(o2);
        final boolean containsKey = this.containsKey(o);
        if (containsKey && Objects.equal(o2, this.get(o))) {
            return o2;
        }
        if (b) {
            this.inverse().remove(o2);
        }
        else {
            Preconditions.checkArgument(!this.containsValue(o2), "value already present: %s", o2);
        }
        final Object put = this.delegate.put(o, o2);
        this.updateInverseMap(o, containsKey, put, o2);
        return put;
    }
    
    private void updateInverseMap(final Object o, final boolean b, final Object o2, final Object o3) {
        if (b) {
            this.removeFromInverseMap(o2);
        }
        this.inverse.delegate.put(o3, o);
    }
    
    @Override
    public Object remove(@Nullable final Object o) {
        return this.containsKey(o) ? this.removeFromBothMaps(o) : null;
    }
    
    private Object removeFromBothMaps(final Object o) {
        final Object remove = this.delegate.remove(o);
        this.removeFromInverseMap(remove);
        return remove;
    }
    
    private void removeFromInverseMap(final Object o) {
        this.inverse.delegate.remove(o);
    }
    
    @Override
    public void putAll(final Map map) {
        for (final Map.Entry<Object, V> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }
    
    @Override
    public void clear() {
        this.delegate.clear();
        this.inverse.delegate.clear();
    }
    
    @Override
    public BiMap inverse() {
        return this.inverse;
    }
    
    @Override
    public Set keySet() {
        final Set keySet = this.keySet;
        return (keySet == null) ? (this.keySet = new KeySet(null)) : keySet;
    }
    
    @Override
    public Set values() {
        final Set valueSet = this.valueSet;
        return (valueSet == null) ? (this.valueSet = new ValueSet(null)) : valueSet;
    }
    
    @Override
    public Set entrySet() {
        final Set entrySet = this.entrySet;
        return (entrySet == null) ? (this.entrySet = new EntrySet(null)) : entrySet;
    }
    
    @Override
    public Collection values() {
        return this.values();
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
    
    static Map access$200(final AbstractBiMap abstractBiMap) {
        return abstractBiMap.delegate;
    }
    
    static Object access$300(final AbstractBiMap abstractBiMap, final Object o) {
        return abstractBiMap.removeFromBothMaps(o);
    }
    
    static void access$600(final AbstractBiMap abstractBiMap, final Object o, final boolean b, final Object o2, final Object o3) {
        abstractBiMap.updateInverseMap(o, b, o2, o3);
    }
    
    static void access$700(final AbstractBiMap abstractBiMap, final Object o) {
        abstractBiMap.removeFromInverseMap(o);
    }
    
    AbstractBiMap(final Map map, final AbstractBiMap abstractBiMap, final AbstractBiMap$1 object) {
        this(map, abstractBiMap);
    }
    
    private static class Inverse extends AbstractBiMap
    {
        @GwtIncompatible("Not needed in emulated source.")
        private static final long serialVersionUID = 0L;
        
        private Inverse(final Map map, final AbstractBiMap abstractBiMap) {
            super(map, abstractBiMap, null);
        }
        
        @Override
        Object checkKey(final Object o) {
            return this.inverse.checkValue(o);
        }
        
        @Override
        Object checkValue(final Object o) {
            return this.inverse.checkKey(o);
        }
        
        @GwtIncompatible("java.io.ObjectOuputStream")
        private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.inverse());
        }
        
        @GwtIncompatible("java.io.ObjectInputStream")
        private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.setInverse((AbstractBiMap)objectInputStream.readObject());
        }
        
        @GwtIncompatible("Not needed in the emulated source.")
        Object readResolve() {
            return this.inverse().inverse();
        }
        
        @Override
        public Collection values() {
            return super.values();
        }
        
        @Override
        protected Object delegate() {
            return super.delegate();
        }
        
        Inverse(final Map map, final AbstractBiMap abstractBiMap, final AbstractBiMap$1 object) {
            this(map, abstractBiMap);
        }
    }
    
    private class EntrySet extends ForwardingSet
    {
        final Set esDelegate;
        final AbstractBiMap this$0;
        
        private EntrySet(final AbstractBiMap this$0) {
            this.this$0 = this$0;
            this.esDelegate = AbstractBiMap.access$200(this.this$0).entrySet();
        }
        
        @Override
        protected Set delegate() {
            return this.esDelegate;
        }
        
        @Override
        public void clear() {
            this.this$0.clear();
        }
        
        @Override
        public boolean remove(final Object o) {
            if (!this.esDelegate.contains(o)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            AbstractBiMap.access$200(this.this$0.inverse).remove(entry.getValue());
            this.esDelegate.remove(entry);
            return true;
        }
        
        @Override
        public Iterator iterator() {
            return new Iterator((Iterator)this.esDelegate.iterator()) {
                Map.Entry entry;
                final Iterator val$iterator;
                final EntrySet this$1;
                
                @Override
                public boolean hasNext() {
                    return this.val$iterator.hasNext();
                }
                
                @Override
                public Map.Entry next() {
                    this.entry = this.val$iterator.next();
                    return new ForwardingMapEntry(this.entry) {
                        final Map.Entry val$finalEntry;
                        final AbstractBiMap$EntrySet$1 this$2;
                        
                        @Override
                        protected Map.Entry delegate() {
                            return this.val$finalEntry;
                        }
                        
                        @Override
                        public Object setValue(final Object value) {
                            Preconditions.checkState(this.this$2.this$1.contains(this), (Object)"entry no longer in map");
                            if (Objects.equal(value, this.getValue())) {
                                return value;
                            }
                            Preconditions.checkArgument(!this.this$2.this$1.this$0.containsValue(value), "value already present: %s", value);
                            final Object setValue = this.val$finalEntry.setValue(value);
                            Preconditions.checkState(Objects.equal(value, this.this$2.this$1.this$0.get(this.getKey())), (Object)"entry no longer in map");
                            AbstractBiMap.access$600(this.this$2.this$1.this$0, this.getKey(), true, setValue, value);
                            return setValue;
                        }
                        
                        @Override
                        protected Object delegate() {
                            return this.delegate();
                        }
                    };
                }
                
                @Override
                public void remove() {
                    CollectPreconditions.checkRemove(this.entry != null);
                    final Object value = this.entry.getValue();
                    this.val$iterator.remove();
                    AbstractBiMap.access$700(this.this$1.this$0, value);
                }
                
                @Override
                public Object next() {
                    return this.next();
                }
            };
        }
        
        @Override
        public Object[] toArray() {
            return this.standardToArray();
        }
        
        @Override
        public Object[] toArray(final Object[] array) {
            return this.standardToArray(array);
        }
        
        @Override
        public boolean contains(final Object o) {
            return Maps.containsEntryImpl(this.delegate(), o);
        }
        
        @Override
        public boolean containsAll(final Collection collection) {
            return this.standardContainsAll(collection);
        }
        
        @Override
        public boolean removeAll(final Collection collection) {
            return this.standardRemoveAll(collection);
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            return this.standardRetainAll(collection);
        }
        
        @Override
        protected Collection delegate() {
            return this.delegate();
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
        
        EntrySet(final AbstractBiMap abstractBiMap, final AbstractBiMap$1 object) {
            this(abstractBiMap);
        }
    }
    
    private class ValueSet extends ForwardingSet
    {
        final Set valuesDelegate;
        final AbstractBiMap this$0;
        
        private ValueSet(final AbstractBiMap this$0) {
            this.this$0 = this$0;
            this.valuesDelegate = this.this$0.inverse.keySet();
        }
        
        @Override
        protected Set delegate() {
            return this.valuesDelegate;
        }
        
        @Override
        public Iterator iterator() {
            return Maps.valueIterator(this.this$0.entrySet().iterator());
        }
        
        @Override
        public Object[] toArray() {
            return this.standardToArray();
        }
        
        @Override
        public Object[] toArray(final Object[] array) {
            return this.standardToArray(array);
        }
        
        @Override
        public String toString() {
            return this.standardToString();
        }
        
        @Override
        protected Collection delegate() {
            return this.delegate();
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
        
        ValueSet(final AbstractBiMap abstractBiMap, final AbstractBiMap$1 object) {
            this(abstractBiMap);
        }
    }
    
    private class KeySet extends ForwardingSet
    {
        final AbstractBiMap this$0;
        
        private KeySet(final AbstractBiMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        protected Set delegate() {
            return AbstractBiMap.access$200(this.this$0).keySet();
        }
        
        @Override
        public void clear() {
            this.this$0.clear();
        }
        
        @Override
        public boolean remove(final Object o) {
            if (!this.contains(o)) {
                return false;
            }
            AbstractBiMap.access$300(this.this$0, o);
            return true;
        }
        
        @Override
        public boolean removeAll(final Collection collection) {
            return this.standardRemoveAll(collection);
        }
        
        @Override
        public boolean retainAll(final Collection collection) {
            return this.standardRetainAll(collection);
        }
        
        @Override
        public Iterator iterator() {
            return Maps.keyIterator(this.this$0.entrySet().iterator());
        }
        
        @Override
        protected Collection delegate() {
            return this.delegate();
        }
        
        @Override
        protected Object delegate() {
            return this.delegate();
        }
        
        KeySet(final AbstractBiMap abstractBiMap, final AbstractBiMap$1 object) {
            this(abstractBiMap);
        }
    }
}
