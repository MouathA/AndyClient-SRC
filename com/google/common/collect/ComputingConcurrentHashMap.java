package com.google.common.collect;

import java.util.concurrent.*;
import com.google.common.base.*;
import java.io.*;
import javax.annotation.concurrent.*;
import java.lang.ref.*;
import javax.annotation.*;
import java.util.concurrent.atomic.*;

class ComputingConcurrentHashMap extends MapMakerInternalMap
{
    final Function computingFunction;
    private static final long serialVersionUID = 4L;
    
    ComputingConcurrentHashMap(final MapMaker mapMaker, final Function function) {
        super(mapMaker);
        this.computingFunction = (Function)Preconditions.checkNotNull(function);
    }
    
    @Override
    Segment createSegment(final int n, final int n2) {
        return new ComputingSegment(this, n, n2);
    }
    
    @Override
    ComputingSegment segmentFor(final int n) {
        return (ComputingSegment)super.segmentFor(n);
    }
    
    Object getOrCompute(final Object o) throws ExecutionException {
        final int hash = this.hash(Preconditions.checkNotNull(o));
        return this.segmentFor(hash).getOrCompute(o, hash, this.computingFunction);
    }
    
    @Override
    Object writeReplace() {
        return new ComputingSerializationProxy(this.keyStrength, this.valueStrength, this.keyEquivalence, this.valueEquivalence, this.expireAfterWriteNanos, this.expireAfterAccessNanos, this.maximumSize, this.concurrencyLevel, this.removalListener, this, this.computingFunction);
    }
    
    @Override
    Segment segmentFor(final int n) {
        return this.segmentFor(n);
    }
    
    static final class ComputingSerializationProxy extends AbstractSerializationProxy
    {
        final Function computingFunction;
        private static final long serialVersionUID = 4L;
        
        ComputingSerializationProxy(final Strength strength, final Strength strength2, final Equivalence equivalence, final Equivalence equivalence2, final long n, final long n2, final int n3, final int n4, final MapMaker.RemovalListener removalListener, final ConcurrentMap concurrentMap, final Function computingFunction) {
            super(strength, strength2, equivalence, equivalence2, n, n2, n3, n4, removalListener, concurrentMap);
            this.computingFunction = computingFunction;
        }
        
        private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            this.writeMapTo(objectOutputStream);
        }
        
        private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.delegate = this.readMapMaker(objectInputStream).makeComputingMap(this.computingFunction);
            this.readEntries(objectInputStream);
        }
        
        Object readResolve() {
            return this.delegate;
        }
    }
    
    private static final class ComputingValueReference implements ValueReference
    {
        final Function computingFunction;
        @GuardedBy("ComputingValueReference.this")
        ValueReference computedReference;
        
        public ComputingValueReference(final Function computingFunction) {
            this.computedReference = MapMakerInternalMap.unset();
            this.computingFunction = computingFunction;
        }
        
        @Override
        public Object get() {
            return null;
        }
        
        @Override
        public ReferenceEntry getEntry() {
            return null;
        }
        
        @Override
        public ValueReference copyFor(final ReferenceQueue referenceQueue, @Nullable final Object o, final ReferenceEntry referenceEntry) {
            return this;
        }
        
        @Override
        public boolean isComputingReference() {
            return true;
        }
        
        @Override
        public Object waitForValue() throws ExecutionException {
            if (this.computedReference == MapMakerInternalMap.UNSET) {
                // monitorenter(this)
                while (this.computedReference == MapMakerInternalMap.UNSET) {
                    this.wait();
                }
                // monitorexit(this)
                Thread.currentThread().interrupt();
            }
            return this.computedReference.waitForValue();
        }
        
        @Override
        public void clear(final ValueReference valueReference) {
            this.setValueReference(valueReference);
        }
        
        Object compute(final Object o, final int n) throws ExecutionException {
            final Object apply = this.computingFunction.apply(o);
            this.setValueReference(new ComputedReference(apply));
            return apply;
        }
        
        void setValueReference(final ValueReference computedReference) {
            // monitorenter(this)
            if (this.computedReference == MapMakerInternalMap.UNSET) {
                this.computedReference = computedReference;
                this.notifyAll();
            }
        }
        // monitorexit(this)
    }
    
    private static final class ComputationExceptionReference implements ValueReference
    {
        final Throwable t;
        
        ComputationExceptionReference(final Throwable t) {
            this.t = t;
        }
        
        @Override
        public Object get() {
            return null;
        }
        
        @Override
        public ReferenceEntry getEntry() {
            return null;
        }
        
        @Override
        public ValueReference copyFor(final ReferenceQueue referenceQueue, final Object o, final ReferenceEntry referenceEntry) {
            return this;
        }
        
        @Override
        public boolean isComputingReference() {
            return false;
        }
        
        @Override
        public Object waitForValue() throws ExecutionException {
            throw new ExecutionException(this.t);
        }
        
        @Override
        public void clear(final ValueReference valueReference) {
        }
    }
    
    private static final class ComputedReference implements ValueReference
    {
        final Object value;
        
        ComputedReference(@Nullable final Object value) {
            this.value = value;
        }
        
        @Override
        public Object get() {
            return this.value;
        }
        
        @Override
        public ReferenceEntry getEntry() {
            return null;
        }
        
        @Override
        public ValueReference copyFor(final ReferenceQueue referenceQueue, final Object o, final ReferenceEntry referenceEntry) {
            return this;
        }
        
        @Override
        public boolean isComputingReference() {
            return false;
        }
        
        @Override
        public Object waitForValue() {
            return this.get();
        }
        
        @Override
        public void clear(final ValueReference valueReference) {
        }
    }
    
    static final class ComputingSegment extends Segment
    {
        ComputingSegment(final MapMakerInternalMap mapMakerInternalMap, final int n, final int n2) {
            super(mapMakerInternalMap, n, n2);
        }
        
        Object getOrCompute(final Object o, final int n, final Function function) throws ExecutionException {
            Object waitForValue;
            ReferenceEntry referenceEntry;
            do {
                referenceEntry = this.getEntry(o, n);
                if (referenceEntry != null) {
                    final Object liveValue = this.getLiveValue(referenceEntry);
                    if (liveValue != null) {
                        this.recordRead(referenceEntry);
                        final Object o2 = liveValue;
                        this.postReadCleanup();
                        return o2;
                    }
                }
                if (referenceEntry == null || !referenceEntry.getValueReference().isComputingReference()) {
                    this.lock();
                    this.preWriteCleanup();
                    final int count = this.count - 1;
                    final AtomicReferenceArray table = this.table;
                    referenceEntry = table.get(n & table.length() - 1);
                    while (referenceEntry != null) {
                        final Object key = referenceEntry.getKey();
                        if (referenceEntry.getHash() == n && key != null && this.map.keyEquivalence.equivalent(o, key)) {
                            if (referenceEntry.getValueReference().isComputingReference()) {
                                break;
                            }
                            final Object value = referenceEntry.getValueReference().get();
                            if (value == null) {
                                this.enqueueNotification(key, n, value, MapMaker.RemovalCause.COLLECTED);
                            }
                            else {
                                if (!this.map.expires() || !this.map.isExpired(referenceEntry)) {
                                    this.recordLockedRead(referenceEntry);
                                    final Object o3 = value;
                                    this.unlock();
                                    this.postWriteCleanup();
                                    this.postReadCleanup();
                                    return o3;
                                }
                                this.enqueueNotification(key, n, value, MapMaker.RemovalCause.EXPIRED);
                            }
                            this.evictionQueue.remove(referenceEntry);
                            this.expirationQueue.remove(referenceEntry);
                            this.count = count;
                            break;
                        }
                        else {
                            referenceEntry = referenceEntry.getNext();
                        }
                    }
                    this.unlock();
                    this.postWriteCleanup();
                }
                Preconditions.checkState(!Thread.holdsLock(referenceEntry), (Object)"Recursive computation");
                waitForValue = referenceEntry.getValueReference().waitForValue();
            } while (waitForValue == null);
            this.recordRead(referenceEntry);
            final Object o4 = waitForValue;
            this.postReadCleanup();
            return o4;
        }
        
        Object compute(final Object o, final int n, final ReferenceEntry referenceEntry, final ComputingValueReference computingValueReference) throws ExecutionException {
            System.nanoTime();
            // monitorenter(referenceEntry)
            final Object compute = computingValueReference.compute(o, n);
            final long nanoTime = System.nanoTime();
            // monitorexit(referenceEntry)
            if (compute != null && this.put(o, n, compute, true) != null) {
                this.enqueueNotification(o, n, compute, MapMaker.RemovalCause.REPLACED);
            }
            final Object o2 = compute;
            if (nanoTime == 0L) {
                System.nanoTime();
            }
            if (compute == null) {
                this.clearValue(o, n, computingValueReference);
            }
            return o2;
        }
    }
}
