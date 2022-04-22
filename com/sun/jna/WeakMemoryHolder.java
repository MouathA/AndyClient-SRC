package com.sun.jna;

import java.util.*;
import java.lang.ref.*;

public class WeakMemoryHolder
{
    ReferenceQueue referenceQueue;
    IdentityHashMap backingMap;
    
    public WeakMemoryHolder() {
        this.referenceQueue = new ReferenceQueue();
        this.backingMap = new IdentityHashMap();
    }
    
    public synchronized void put(final Object o, final Memory memory) {
        this.clean();
        this.backingMap.put(new WeakReference<Object>(o, this.referenceQueue), memory);
    }
    
    public synchronized void clean() {
        for (Reference reference = this.referenceQueue.poll(); reference != null; reference = this.referenceQueue.poll()) {
            this.backingMap.remove(reference);
        }
    }
}
