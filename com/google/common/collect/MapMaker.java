package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.concurrent.*;
import com.google.common.base.*;
import java.io.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible(emulated = true)
public final class MapMaker extends GenericMapMaker
{
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
    private static final int DEFAULT_EXPIRATION_NANOS = 0;
    static final int UNSET_INT = -1;
    boolean useCustomMap;
    int initialCapacity;
    int concurrencyLevel;
    int maximumSize;
    MapMakerInternalMap.Strength keyStrength;
    MapMakerInternalMap.Strength valueStrength;
    long expireAfterWriteNanos;
    long expireAfterAccessNanos;
    RemovalCause nullRemovalCause;
    Equivalence keyEquivalence;
    Ticker ticker;
    
    public MapMaker() {
        this.initialCapacity = -1;
        this.concurrencyLevel = -1;
        this.maximumSize = -1;
        this.expireAfterWriteNanos = -1L;
        this.expireAfterAccessNanos = -1L;
    }
    
    @GwtIncompatible("To be supported")
    @Override
    MapMaker keyEquivalence(final Equivalence equivalence) {
        Preconditions.checkState(this.keyEquivalence == null, "key equivalence was already set to %s", this.keyEquivalence);
        this.keyEquivalence = (Equivalence)Preconditions.checkNotNull(equivalence);
        this.useCustomMap = true;
        return this;
    }
    
    Equivalence getKeyEquivalence() {
        return (Equivalence)Objects.firstNonNull(this.keyEquivalence, this.getKeyStrength().defaultEquivalence());
    }
    
    @Override
    public MapMaker initialCapacity(final int initialCapacity) {
        Preconditions.checkState(this.initialCapacity == -1, "initial capacity was already set to %s", this.initialCapacity);
        Preconditions.checkArgument(initialCapacity >= 0);
        this.initialCapacity = initialCapacity;
        return this;
    }
    
    int getInitialCapacity() {
        return (this.initialCapacity == -1) ? 16 : this.initialCapacity;
    }
    
    @Deprecated
    @Override
    MapMaker maximumSize(final int maximumSize) {
        Preconditions.checkState(this.maximumSize == -1, "maximum size was already set to %s", this.maximumSize);
        Preconditions.checkArgument(maximumSize >= 0, (Object)"maximum size must not be negative");
        this.maximumSize = maximumSize;
        this.useCustomMap = true;
        if (this.maximumSize == 0) {
            this.nullRemovalCause = RemovalCause.SIZE;
        }
        return this;
    }
    
    @Override
    public MapMaker concurrencyLevel(final int concurrencyLevel) {
        Preconditions.checkState(this.concurrencyLevel == -1, "concurrency level was already set to %s", this.concurrencyLevel);
        Preconditions.checkArgument(concurrencyLevel > 0);
        this.concurrencyLevel = concurrencyLevel;
        return this;
    }
    
    int getConcurrencyLevel() {
        return (this.concurrencyLevel == -1) ? 4 : this.concurrencyLevel;
    }
    
    @GwtIncompatible("java.lang.ref.WeakReference")
    @Override
    public MapMaker weakKeys() {
        return this.setKeyStrength(MapMakerInternalMap.Strength.WEAK);
    }
    
    MapMaker setKeyStrength(final MapMakerInternalMap.Strength strength) {
        Preconditions.checkState(this.keyStrength == null, "Key strength was already set to %s", this.keyStrength);
        this.keyStrength = (MapMakerInternalMap.Strength)Preconditions.checkNotNull(strength);
        Preconditions.checkArgument(this.keyStrength != MapMakerInternalMap.Strength.SOFT, (Object)"Soft keys are not supported");
        if (strength != MapMakerInternalMap.Strength.STRONG) {
            this.useCustomMap = true;
        }
        return this;
    }
    
    MapMakerInternalMap.Strength getKeyStrength() {
        return (MapMakerInternalMap.Strength)Objects.firstNonNull(this.keyStrength, MapMakerInternalMap.Strength.STRONG);
    }
    
    @GwtIncompatible("java.lang.ref.WeakReference")
    @Override
    public MapMaker weakValues() {
        return this.setValueStrength(MapMakerInternalMap.Strength.WEAK);
    }
    
    @Deprecated
    @GwtIncompatible("java.lang.ref.SoftReference")
    @Override
    public MapMaker softValues() {
        return this.setValueStrength(MapMakerInternalMap.Strength.SOFT);
    }
    
    MapMaker setValueStrength(final MapMakerInternalMap.Strength strength) {
        Preconditions.checkState(this.valueStrength == null, "Value strength was already set to %s", this.valueStrength);
        this.valueStrength = (MapMakerInternalMap.Strength)Preconditions.checkNotNull(strength);
        if (strength != MapMakerInternalMap.Strength.STRONG) {
            this.useCustomMap = true;
        }
        return this;
    }
    
    MapMakerInternalMap.Strength getValueStrength() {
        return (MapMakerInternalMap.Strength)Objects.firstNonNull(this.valueStrength, MapMakerInternalMap.Strength.STRONG);
    }
    
    @Deprecated
    @Override
    MapMaker expireAfterWrite(final long n, final TimeUnit timeUnit) {
        this.checkExpiration(n, timeUnit);
        this.expireAfterWriteNanos = timeUnit.toNanos(n);
        if (n == 0L && this.nullRemovalCause == null) {
            this.nullRemovalCause = RemovalCause.EXPIRED;
        }
        this.useCustomMap = true;
        return this;
    }
    
    private void checkExpiration(final long n, final TimeUnit timeUnit) {
        Preconditions.checkState(this.expireAfterWriteNanos == -1L, "expireAfterWrite was already set to %s ns", this.expireAfterWriteNanos);
        Preconditions.checkState(this.expireAfterAccessNanos == -1L, "expireAfterAccess was already set to %s ns", this.expireAfterAccessNanos);
        Preconditions.checkArgument(n >= 0L, "duration cannot be negative: %s %s", n, timeUnit);
    }
    
    long getExpireAfterWriteNanos() {
        return (this.expireAfterWriteNanos == -1L) ? 0L : this.expireAfterWriteNanos;
    }
    
    @Deprecated
    @GwtIncompatible("To be supported")
    @Override
    MapMaker expireAfterAccess(final long n, final TimeUnit timeUnit) {
        this.checkExpiration(n, timeUnit);
        this.expireAfterAccessNanos = timeUnit.toNanos(n);
        if (n == 0L && this.nullRemovalCause == null) {
            this.nullRemovalCause = RemovalCause.EXPIRED;
        }
        this.useCustomMap = true;
        return this;
    }
    
    long getExpireAfterAccessNanos() {
        return (this.expireAfterAccessNanos == -1L) ? 0L : this.expireAfterAccessNanos;
    }
    
    Ticker getTicker() {
        return (Ticker)Objects.firstNonNull(this.ticker, Ticker.systemTicker());
    }
    
    @Deprecated
    @GwtIncompatible("To be supported")
    GenericMapMaker removalListener(final RemovalListener removalListener) {
        Preconditions.checkState(this.removalListener == null);
        super.removalListener = (RemovalListener)Preconditions.checkNotNull(removalListener);
        this.useCustomMap = true;
        return this;
    }
    
    @Override
    public ConcurrentMap makeMap() {
        if (!this.useCustomMap) {
            return new ConcurrentHashMap(this.getInitialCapacity(), 0.75f, this.getConcurrencyLevel());
        }
        return (this.nullRemovalCause == null) ? new MapMakerInternalMap(this) : new NullConcurrentMap(this);
    }
    
    @GwtIncompatible("MapMakerInternalMap")
    @Override
    MapMakerInternalMap makeCustomMap() {
        return new MapMakerInternalMap(this);
    }
    
    @Deprecated
    @Override
    ConcurrentMap makeComputingMap(final Function function) {
        return (ConcurrentMap)((this.nullRemovalCause == null) ? new ComputingMapAdapter(this, function) : new NullComputingConcurrentMap(this, function));
    }
    
    @Override
    public String toString() {
        final Objects.ToStringHelper stringHelper = Objects.toStringHelper(this);
        if (this.initialCapacity != -1) {
            stringHelper.add("initialCapacity", this.initialCapacity);
        }
        if (this.concurrencyLevel != -1) {
            stringHelper.add("concurrencyLevel", this.concurrencyLevel);
        }
        if (this.maximumSize != -1) {
            stringHelper.add("maximumSize", this.maximumSize);
        }
        if (this.expireAfterWriteNanos != -1L) {
            stringHelper.add("expireAfterWrite", this.expireAfterWriteNanos + "ns");
        }
        if (this.expireAfterAccessNanos != -1L) {
            stringHelper.add("expireAfterAccess", this.expireAfterAccessNanos + "ns");
        }
        if (this.keyStrength != null) {
            stringHelper.add("keyStrength", Ascii.toLowerCase(this.keyStrength.toString()));
        }
        if (this.valueStrength != null) {
            stringHelper.add("valueStrength", Ascii.toLowerCase(this.valueStrength.toString()));
        }
        if (this.keyEquivalence != null) {
            stringHelper.addValue("keyEquivalence");
        }
        if (this.removalListener != null) {
            stringHelper.addValue("removalListener");
        }
        return stringHelper.toString();
    }
    
    @Override
    GenericMapMaker expireAfterAccess(final long n, final TimeUnit timeUnit) {
        return this.expireAfterAccess(n, timeUnit);
    }
    
    @Override
    GenericMapMaker expireAfterWrite(final long n, final TimeUnit timeUnit) {
        return this.expireAfterWrite(n, timeUnit);
    }
    
    @Override
    public GenericMapMaker softValues() {
        return this.softValues();
    }
    
    @Override
    public GenericMapMaker weakValues() {
        return this.weakValues();
    }
    
    @Override
    public GenericMapMaker weakKeys() {
        return this.weakKeys();
    }
    
    @Override
    public GenericMapMaker concurrencyLevel(final int n) {
        return this.concurrencyLevel(n);
    }
    
    @Override
    GenericMapMaker maximumSize(final int n) {
        return this.maximumSize(n);
    }
    
    @Override
    public GenericMapMaker initialCapacity(final int n) {
        return this.initialCapacity(n);
    }
    
    @Override
    GenericMapMaker keyEquivalence(final Equivalence equivalence) {
        return this.keyEquivalence(equivalence);
    }
    
    static final class ComputingMapAdapter extends ComputingConcurrentHashMap implements Serializable
    {
        private static final long serialVersionUID = 0L;
        
        ComputingMapAdapter(final MapMaker mapMaker, final Function function) {
            super(mapMaker, function);
        }
        
        @Override
        public Object get(final Object o) {
            final Object orCompute = this.getOrCompute(o);
            if (orCompute == null) {
                throw new NullPointerException(this.computingFunction + " returned null for key " + o + ".");
            }
            return orCompute;
        }
    }
    
    static final class NullComputingConcurrentMap extends NullConcurrentMap
    {
        private static final long serialVersionUID = 0L;
        final Function computingFunction;
        
        NullComputingConcurrentMap(final MapMaker mapMaker, final Function function) {
            super(mapMaker);
            this.computingFunction = (Function)Preconditions.checkNotNull(function);
        }
        
        @Override
        public Object get(final Object o) {
            final Object compute = this.compute(o);
            Preconditions.checkNotNull(compute, "%s returned null for key %s.", this.computingFunction, o);
            this.notifyRemoval(o, compute);
            return compute;
        }
        
        private Object compute(final Object o) {
            Preconditions.checkNotNull(o);
            return this.computingFunction.apply(o);
        }
    }
    
    static class NullConcurrentMap extends AbstractMap implements ConcurrentMap, Serializable
    {
        private static final long serialVersionUID = 0L;
        private final RemovalListener removalListener;
        private final RemovalCause removalCause;
        
        NullConcurrentMap(final MapMaker mapMaker) {
            this.removalListener = mapMaker.getRemovalListener();
            this.removalCause = mapMaker.nullRemovalCause;
        }
        
        @Override
        public boolean containsKey(@Nullable final Object o) {
            return false;
        }
        
        @Override
        public boolean containsValue(@Nullable final Object o) {
            return false;
        }
        
        @Override
        public Object get(@Nullable final Object o) {
            return null;
        }
        
        void notifyRemoval(final Object o, final Object o2) {
            this.removalListener.onRemoval(new RemovalNotification(o, o2, this.removalCause));
        }
        
        @Override
        public Object put(final Object o, final Object o2) {
            Preconditions.checkNotNull(o);
            Preconditions.checkNotNull(o2);
            this.notifyRemoval(o, o2);
            return null;
        }
        
        @Override
        public Object putIfAbsent(final Object o, final Object o2) {
            return this.put(o, o2);
        }
        
        @Override
        public Object remove(@Nullable final Object o) {
            return null;
        }
        
        @Override
        public boolean remove(@Nullable final Object o, @Nullable final Object o2) {
            return false;
        }
        
        @Override
        public Object replace(final Object o, final Object o2) {
            Preconditions.checkNotNull(o);
            Preconditions.checkNotNull(o2);
            return null;
        }
        
        @Override
        public boolean replace(final Object o, @Nullable final Object o2, final Object o3) {
            Preconditions.checkNotNull(o);
            Preconditions.checkNotNull(o3);
            return false;
        }
        
        @Override
        public Set entrySet() {
            return Collections.emptySet();
        }
    }
    
    interface RemovalListener
    {
        void onRemoval(final RemovalNotification p0);
    }
    
    static final class RemovalNotification extends ImmutableEntry
    {
        private static final long serialVersionUID = 0L;
        private final RemovalCause cause;
        
        RemovalNotification(@Nullable final Object o, @Nullable final Object o2, final RemovalCause cause) {
            super(o, o2);
            this.cause = cause;
        }
        
        public RemovalCause getCause() {
            return this.cause;
        }
        
        public boolean wasEvicted() {
            return this.cause.wasEvicted();
        }
    }
    
    enum RemovalCause
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
        
        RemovalCause(final String s, final int n, final MapMaker$1 object) {
            this(s, n);
        }
        
        static {
            $VALUES = new RemovalCause[] { RemovalCause.EXPLICIT, RemovalCause.REPLACED, RemovalCause.COLLECTED, RemovalCause.EXPIRED, RemovalCause.SIZE };
        }
    }
}
