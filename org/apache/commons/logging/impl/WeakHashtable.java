package org.apache.commons.logging.impl;

import java.util.*;
import java.lang.ref.*;

public final class WeakHashtable extends Hashtable
{
    private static final long serialVersionUID = -1546036869799732453L;
    private static final int MAX_CHANGES_BEFORE_PURGE = 100;
    private static final int PARTIAL_PURGE_COUNT = 10;
    private final ReferenceQueue queue;
    private int changeCount;
    
    public WeakHashtable() {
        this.queue = new ReferenceQueue();
        this.changeCount = 0;
    }
    
    public boolean containsKey(final Object o) {
        return super.containsKey(new Referenced(o, (WeakHashtable$1)null));
    }
    
    public Enumeration elements() {
        this.purge();
        return super.elements();
    }
    
    public Set entrySet() {
        this.purge();
        final Set<Map.Entry<Referenced, V>> entrySet = super.entrySet();
        final HashSet<Entry> set = new HashSet<Entry>();
        for (final Map.Entry<Referenced, V> entry : entrySet) {
            final Object access$100 = Referenced.access$100(entry.getKey());
            final Object value = entry.getValue();
            if (access$100 != null) {
                set.add(new Entry(access$100, value, null));
            }
        }
        return set;
    }
    
    public Object get(final Object o) {
        return super.get(new Referenced(o, (WeakHashtable$1)null));
    }
    
    public Enumeration keys() {
        this.purge();
        return new Enumeration((Enumeration)super.keys()) {
            private final Enumeration val$enumer = val$enumer;
            private final WeakHashtable this$0 = this$0;
            
            public boolean hasMoreElements() {
                return this.val$enumer.hasMoreElements();
            }
            
            public Object nextElement() {
                return Referenced.access$100(this.val$enumer.nextElement());
            }
        };
    }
    
    public Set keySet() {
        this.purge();
        final Set<Referenced> keySet = super.keySet();
        final HashSet<Object> set = new HashSet<Object>();
        final Iterator<Referenced> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            final Object access$100 = Referenced.access$100(iterator.next());
            if (access$100 != null) {
                set.add(access$100);
            }
        }
        return set;
    }
    
    public synchronized Object put(final Object o, final Object o2) {
        if (o == null) {
            throw new NullPointerException("Null keys are not allowed");
        }
        if (o2 == null) {
            throw new NullPointerException("Null values are not allowed");
        }
        if (this.changeCount++ > 100) {
            this.purge();
            this.changeCount = 0;
        }
        else if (this.changeCount % 10 == 0) {
            this.purgeOne();
        }
        return super.put(new Referenced(o, this.queue, null), o2);
    }
    
    public void putAll(final Map map) {
        if (map != null) {
            for (final Map.Entry<Object, V> entry : map.entrySet()) {
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }
    
    public Collection values() {
        this.purge();
        return super.values();
    }
    
    public synchronized Object remove(final Object o) {
        if (this.changeCount++ > 100) {
            this.purge();
            this.changeCount = 0;
        }
        else if (this.changeCount % 10 == 0) {
            this.purgeOne();
        }
        return super.remove(new Referenced(o, (WeakHashtable$1)null));
    }
    
    public boolean isEmpty() {
        this.purge();
        return super.isEmpty();
    }
    
    public int size() {
        this.purge();
        return super.size();
    }
    
    public String toString() {
        this.purge();
        return super.toString();
    }
    
    protected void rehash() {
        this.purge();
        super.rehash();
    }
    
    private void purge() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/util/ArrayList.<init>:()V
        //     7: astore_1       
        //     8: aload_0        
        //     9: getfield        org/apache/commons/logging/impl/WeakHashtable.queue:Ljava/lang/ref/ReferenceQueue;
        //    12: dup            
        //    13: astore_2       
        //    14: monitorenter   
        //    15: aload_0        
        //    16: getfield        org/apache/commons/logging/impl/WeakHashtable.queue:Ljava/lang/ref/ReferenceQueue;
        //    19: invokevirtual   java/lang/ref/ReferenceQueue.poll:()Ljava/lang/ref/Reference;
        //    22: checkcast       Lorg/apache/commons/logging/impl/WeakHashtable$WeakKey;
        //    25: dup            
        //    26: astore_3       
        //    27: ifnull          44
        //    30: aload_1        
        //    31: aload_3        
        //    32: invokestatic    org/apache/commons/logging/impl/WeakHashtable$WeakKey.access$400:(Lorg/apache/commons/logging/impl/WeakHashtable$WeakKey;)Lorg/apache/commons/logging/impl/WeakHashtable$Referenced;
        //    35: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    40: pop            
        //    41: goto            15
        //    44: aload_2        
        //    45: monitorexit    
        //    46: goto            56
        //    49: astore          4
        //    51: aload_2        
        //    52: monitorexit    
        //    53: aload           4
        //    55: athrow         
        //    56: aload_1        
        //    57: invokeinterface java/util/List.size:()I
        //    62: istore_2       
        //    63: iconst_0       
        //    64: iload_2        
        //    65: if_icmpge       86
        //    68: aload_0        
        //    69: aload_1        
        //    70: iconst_0       
        //    71: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //    76: invokespecial   java/util/Hashtable.remove:(Ljava/lang/Object;)Ljava/lang/Object;
        //    79: pop            
        //    80: iinc            3, 1
        //    83: goto            63
        //    86: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void purgeOne() {
        // monitorenter(queue = this.queue)
        final WeakKey weakKey = (WeakKey)this.queue.poll();
        if (weakKey != null) {
            super.remove(WeakKey.access$400(weakKey));
        }
    }
    // monitorexit(queue)
    
    private static final class WeakKey extends WeakReference
    {
        private final Referenced referenced;
        
        private WeakKey(final Object o, final ReferenceQueue referenceQueue, final Referenced referenced) {
            super(o, referenceQueue);
            this.referenced = referenced;
        }
        
        private Referenced getReferenced() {
            return this.referenced;
        }
        
        static Referenced access$400(final WeakKey weakKey) {
            return weakKey.getReferenced();
        }
        
        WeakKey(final Object o, final ReferenceQueue referenceQueue, final Referenced referenced, final WeakHashtable$1 enumeration) {
            this(o, referenceQueue, referenced);
        }
    }
    
    private static final class Referenced
    {
        private final WeakReference reference;
        private final int hashCode;
        
        private Referenced(final Object o) {
            this.reference = new WeakReference((T)o);
            this.hashCode = o.hashCode();
        }
        
        private Referenced(final Object o, final ReferenceQueue referenceQueue) {
            this.reference = new WeakKey(o, referenceQueue, this, null);
            this.hashCode = o.hashCode();
        }
        
        public int hashCode() {
            return this.hashCode;
        }
        
        private Object getValue() {
            return this.reference.get();
        }
        
        public boolean equals(final Object o) {
            if (o instanceof Referenced) {
                final Referenced referenced = (Referenced)o;
                final Object value = this.getValue();
                final Object value2 = referenced.getValue();
                if (value == null) {
                    final boolean b = value2 == null;
                    final boolean b2 = false && this.hashCode() == referenced.hashCode();
                }
                else {
                    value.equals(value2);
                }
            }
            return false;
        }
        
        Referenced(final Object o, final WeakHashtable$1 enumeration) {
            this(o);
        }
        
        static Object access$100(final Referenced referenced) {
            return referenced.getValue();
        }
        
        Referenced(final Object o, final ReferenceQueue referenceQueue, final WeakHashtable$1 enumeration) {
            this(o, referenceQueue);
        }
    }
    
    private static final class Entry implements Map.Entry
    {
        private final Object key;
        private final Object value;
        
        private Entry(final Object key, final Object value) {
            this.key = key;
            this.value = value;
        }
        
        public boolean equals(final Object o) {
            if (o != null && o instanceof Map.Entry) {
                final Map.Entry entry = (Map.Entry)o;
                if (this.getKey() == null) {
                    if (entry.getKey() != null) {
                        return false;
                    }
                }
                else if (!this.getKey().equals(entry.getKey())) {
                    return false;
                }
                if ((this.getValue() != null) ? this.getValue().equals(entry.getValue()) : (entry.getValue() == null)) {}
            }
            return false;
        }
        
        public int hashCode() {
            return ((this.getKey() == null) ? 0 : this.getKey().hashCode()) ^ ((this.getValue() == null) ? 0 : this.getValue().hashCode());
        }
        
        public Object setValue(final Object o) {
            throw new UnsupportedOperationException("Entry.setValue is not supported.");
        }
        
        public Object getValue() {
            return this.value;
        }
        
        public Object getKey() {
            return this.key;
        }
        
        Entry(final Object o, final Object o2, final WeakHashtable$1 enumeration) {
            this(o, o2);
        }
    }
}
