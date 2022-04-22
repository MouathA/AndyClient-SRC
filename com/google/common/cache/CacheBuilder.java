package com.google.common.cache;

import com.google.common.annotations.*;
import java.util.concurrent.*;
import javax.annotation.*;
import java.util.logging.*;
import com.google.common.base.*;

@GwtCompatible(emulated = true)
public final class CacheBuilder
{
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
    private static final int DEFAULT_EXPIRATION_NANOS = 0;
    private static final int DEFAULT_REFRESH_NANOS = 0;
    static final Supplier NULL_STATS_COUNTER;
    static final CacheStats EMPTY_STATS;
    static final Supplier CACHE_STATS_COUNTER;
    static final Ticker NULL_TICKER;
    private static final Logger logger;
    static final int UNSET_INT = -1;
    boolean strictParsing;
    int initialCapacity;
    int concurrencyLevel;
    long maximumSize;
    long maximumWeight;
    Weigher weigher;
    LocalCache.Strength keyStrength;
    LocalCache.Strength valueStrength;
    long expireAfterWriteNanos;
    long expireAfterAccessNanos;
    long refreshNanos;
    Equivalence keyEquivalence;
    Equivalence valueEquivalence;
    RemovalListener removalListener;
    Ticker ticker;
    Supplier statsCounterSupplier;
    
    CacheBuilder() {
        this.strictParsing = true;
        this.initialCapacity = -1;
        this.concurrencyLevel = -1;
        this.maximumSize = -1L;
        this.maximumWeight = -1L;
        this.expireAfterWriteNanos = -1L;
        this.expireAfterAccessNanos = -1L;
        this.refreshNanos = -1L;
        this.statsCounterSupplier = CacheBuilder.NULL_STATS_COUNTER;
    }
    
    public static CacheBuilder newBuilder() {
        return new CacheBuilder();
    }
    
    @Beta
    @GwtIncompatible("To be supported")
    public static CacheBuilder from(final CacheBuilderSpec cacheBuilderSpec) {
        return cacheBuilderSpec.toCacheBuilder().lenientParsing();
    }
    
    @Beta
    @GwtIncompatible("To be supported")
    public static CacheBuilder from(final String s) {
        return from(CacheBuilderSpec.parse(s));
    }
    
    @GwtIncompatible("To be supported")
    CacheBuilder lenientParsing() {
        this.strictParsing = false;
        return this;
    }
    
    @GwtIncompatible("To be supported")
    CacheBuilder keyEquivalence(final Equivalence equivalence) {
        Preconditions.checkState(this.keyEquivalence == null, "key equivalence was already set to %s", this.keyEquivalence);
        this.keyEquivalence = (Equivalence)Preconditions.checkNotNull(equivalence);
        return this;
    }
    
    Equivalence getKeyEquivalence() {
        return (Equivalence)Objects.firstNonNull(this.keyEquivalence, this.getKeyStrength().defaultEquivalence());
    }
    
    @GwtIncompatible("To be supported")
    CacheBuilder valueEquivalence(final Equivalence equivalence) {
        Preconditions.checkState(this.valueEquivalence == null, "value equivalence was already set to %s", this.valueEquivalence);
        this.valueEquivalence = (Equivalence)Preconditions.checkNotNull(equivalence);
        return this;
    }
    
    Equivalence getValueEquivalence() {
        return (Equivalence)Objects.firstNonNull(this.valueEquivalence, this.getValueStrength().defaultEquivalence());
    }
    
    public CacheBuilder initialCapacity(final int initialCapacity) {
        Preconditions.checkState(this.initialCapacity == -1, "initial capacity was already set to %s", this.initialCapacity);
        Preconditions.checkArgument(initialCapacity >= 0);
        this.initialCapacity = initialCapacity;
        return this;
    }
    
    int getInitialCapacity() {
        return (this.initialCapacity == -1) ? 16 : this.initialCapacity;
    }
    
    public CacheBuilder concurrencyLevel(final int concurrencyLevel) {
        Preconditions.checkState(this.concurrencyLevel == -1, "concurrency level was already set to %s", this.concurrencyLevel);
        Preconditions.checkArgument(concurrencyLevel > 0);
        this.concurrencyLevel = concurrencyLevel;
        return this;
    }
    
    int getConcurrencyLevel() {
        return (this.concurrencyLevel == -1) ? 4 : this.concurrencyLevel;
    }
    
    public CacheBuilder maximumSize(final long maximumSize) {
        Preconditions.checkState(this.maximumSize == -1L, "maximum size was already set to %s", this.maximumSize);
        Preconditions.checkState(this.maximumWeight == -1L, "maximum weight was already set to %s", this.maximumWeight);
        Preconditions.checkState(this.weigher == null, (Object)"maximum size can not be combined with weigher");
        Preconditions.checkArgument(maximumSize >= 0L, (Object)"maximum size must not be negative");
        this.maximumSize = maximumSize;
        return this;
    }
    
    @GwtIncompatible("To be supported")
    public CacheBuilder maximumWeight(final long maximumWeight) {
        Preconditions.checkState(this.maximumWeight == -1L, "maximum weight was already set to %s", this.maximumWeight);
        Preconditions.checkState(this.maximumSize == -1L, "maximum size was already set to %s", this.maximumSize);
        this.maximumWeight = maximumWeight;
        Preconditions.checkArgument(maximumWeight >= 0L, (Object)"maximum weight must not be negative");
        return this;
    }
    
    @GwtIncompatible("To be supported")
    public CacheBuilder weigher(final Weigher weigher) {
        Preconditions.checkState(this.weigher == null);
        if (this.strictParsing) {
            Preconditions.checkState(this.maximumSize == -1L, "weigher can not be combined with maximum size", this.maximumSize);
        }
        this.weigher = (Weigher)Preconditions.checkNotNull(weigher);
        return this;
    }
    
    long getMaximumWeight() {
        if (this.expireAfterWriteNanos == 0L || this.expireAfterAccessNanos == 0L) {
            return 0L;
        }
        return (this.weigher == null) ? this.maximumSize : this.maximumWeight;
    }
    
    Weigher getWeigher() {
        return (Weigher)Objects.firstNonNull(this.weigher, OneWeigher.INSTANCE);
    }
    
    @GwtIncompatible("java.lang.ref.WeakReference")
    public CacheBuilder weakKeys() {
        return this.setKeyStrength(LocalCache.Strength.WEAK);
    }
    
    CacheBuilder setKeyStrength(final LocalCache.Strength strength) {
        Preconditions.checkState(this.keyStrength == null, "Key strength was already set to %s", this.keyStrength);
        this.keyStrength = (LocalCache.Strength)Preconditions.checkNotNull(strength);
        return this;
    }
    
    LocalCache.Strength getKeyStrength() {
        return (LocalCache.Strength)Objects.firstNonNull(this.keyStrength, LocalCache.Strength.STRONG);
    }
    
    @GwtIncompatible("java.lang.ref.WeakReference")
    public CacheBuilder weakValues() {
        return this.setValueStrength(LocalCache.Strength.WEAK);
    }
    
    @GwtIncompatible("java.lang.ref.SoftReference")
    public CacheBuilder softValues() {
        return this.setValueStrength(LocalCache.Strength.SOFT);
    }
    
    CacheBuilder setValueStrength(final LocalCache.Strength strength) {
        Preconditions.checkState(this.valueStrength == null, "Value strength was already set to %s", this.valueStrength);
        this.valueStrength = (LocalCache.Strength)Preconditions.checkNotNull(strength);
        return this;
    }
    
    LocalCache.Strength getValueStrength() {
        return (LocalCache.Strength)Objects.firstNonNull(this.valueStrength, LocalCache.Strength.STRONG);
    }
    
    public CacheBuilder expireAfterWrite(final long n, final TimeUnit timeUnit) {
        Preconditions.checkState(this.expireAfterWriteNanos == -1L, "expireAfterWrite was already set to %s ns", this.expireAfterWriteNanos);
        Preconditions.checkArgument(n >= 0L, "duration cannot be negative: %s %s", n, timeUnit);
        this.expireAfterWriteNanos = timeUnit.toNanos(n);
        return this;
    }
    
    long getExpireAfterWriteNanos() {
        return (this.expireAfterWriteNanos == -1L) ? 0L : this.expireAfterWriteNanos;
    }
    
    public CacheBuilder expireAfterAccess(final long n, final TimeUnit timeUnit) {
        Preconditions.checkState(this.expireAfterAccessNanos == -1L, "expireAfterAccess was already set to %s ns", this.expireAfterAccessNanos);
        Preconditions.checkArgument(n >= 0L, "duration cannot be negative: %s %s", n, timeUnit);
        this.expireAfterAccessNanos = timeUnit.toNanos(n);
        return this;
    }
    
    long getExpireAfterAccessNanos() {
        return (this.expireAfterAccessNanos == -1L) ? 0L : this.expireAfterAccessNanos;
    }
    
    @Beta
    @GwtIncompatible("To be supported (synchronously).")
    public CacheBuilder refreshAfterWrite(final long n, final TimeUnit timeUnit) {
        Preconditions.checkNotNull(timeUnit);
        Preconditions.checkState(this.refreshNanos == -1L, "refresh was already set to %s ns", this.refreshNanos);
        Preconditions.checkArgument(n > 0L, "duration must be positive: %s %s", n, timeUnit);
        this.refreshNanos = timeUnit.toNanos(n);
        return this;
    }
    
    long getRefreshNanos() {
        return (this.refreshNanos == -1L) ? 0L : this.refreshNanos;
    }
    
    public CacheBuilder ticker(final Ticker ticker) {
        Preconditions.checkState(this.ticker == null);
        this.ticker = (Ticker)Preconditions.checkNotNull(ticker);
        return this;
    }
    
    Ticker getTicker(final boolean b) {
        if (this.ticker != null) {
            return this.ticker;
        }
        return b ? Ticker.systemTicker() : CacheBuilder.NULL_TICKER;
    }
    
    @CheckReturnValue
    public CacheBuilder removalListener(final RemovalListener removalListener) {
        Preconditions.checkState(this.removalListener == null);
        this.removalListener = (RemovalListener)Preconditions.checkNotNull(removalListener);
        return this;
    }
    
    RemovalListener getRemovalListener() {
        return (RemovalListener)Objects.firstNonNull(this.removalListener, NullListener.INSTANCE);
    }
    
    public CacheBuilder recordStats() {
        this.statsCounterSupplier = CacheBuilder.CACHE_STATS_COUNTER;
        return this;
    }
    
    boolean isRecordingStats() {
        return this.statsCounterSupplier == CacheBuilder.CACHE_STATS_COUNTER;
    }
    
    Supplier getStatsCounterSupplier() {
        return this.statsCounterSupplier;
    }
    
    public LoadingCache build(final CacheLoader cacheLoader) {
        this.checkWeightWithWeigher();
        return new LocalCache.LocalLoadingCache(this, cacheLoader);
    }
    
    public Cache build() {
        this.checkWeightWithWeigher();
        this.checkNonLoadingCache();
        return new LocalCache.LocalManualCache(this);
    }
    
    private void checkNonLoadingCache() {
        Preconditions.checkState(this.refreshNanos == -1L, (Object)"refreshAfterWrite requires a LoadingCache");
    }
    
    private void checkWeightWithWeigher() {
        if (this.weigher == null) {
            Preconditions.checkState(this.maximumWeight == -1L, (Object)"maximumWeight requires weigher");
        }
        else if (this.strictParsing) {
            Preconditions.checkState(this.maximumWeight != -1L, (Object)"weigher requires maximumWeight");
        }
        else if (this.maximumWeight == -1L) {
            CacheBuilder.logger.log(Level.WARNING, "ignoring weigher specified without maximumWeight");
        }
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
        if (this.maximumSize != -1L) {
            stringHelper.add("maximumSize", this.maximumSize);
        }
        if (this.maximumWeight != -1L) {
            stringHelper.add("maximumWeight", this.maximumWeight);
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
        if (this.valueEquivalence != null) {
            stringHelper.addValue("valueEquivalence");
        }
        if (this.removalListener != null) {
            stringHelper.addValue("removalListener");
        }
        return stringHelper.toString();
    }
    
    static {
        NULL_STATS_COUNTER = Suppliers.ofInstance(new AbstractCache.StatsCounter() {
            @Override
            public void recordHits(final int n) {
            }
            
            @Override
            public void recordMisses(final int n) {
            }
            
            @Override
            public void recordLoadSuccess(final long n) {
            }
            
            @Override
            public void recordLoadException(final long n) {
            }
            
            @Override
            public void recordEviction() {
            }
            
            @Override
            public CacheStats snapshot() {
                return CacheBuilder.EMPTY_STATS;
            }
        });
        EMPTY_STATS = new CacheStats(0L, 0L, 0L, 0L, 0L, 0L);
        CACHE_STATS_COUNTER = new Supplier() {
            @Override
            public AbstractCache.StatsCounter get() {
                return new AbstractCache.SimpleStatsCounter();
            }
            
            @Override
            public Object get() {
                return this.get();
            }
        };
        NULL_TICKER = new Ticker() {
            @Override
            public long read() {
                return 0L;
            }
        };
        logger = Logger.getLogger(CacheBuilder.class.getName());
    }
    
    enum OneWeigher implements Weigher
    {
        INSTANCE("INSTANCE", 0);
        
        private static final OneWeigher[] $VALUES;
        
        private OneWeigher(final String s, final int n) {
        }
        
        @Override
        public int weigh(final Object o, final Object o2) {
            return 1;
        }
        
        static {
            $VALUES = new OneWeigher[] { OneWeigher.INSTANCE };
        }
    }
    
    enum NullListener implements RemovalListener
    {
        INSTANCE("INSTANCE", 0);
        
        private static final NullListener[] $VALUES;
        
        private NullListener(final String s, final int n) {
        }
        
        @Override
        public void onRemoval(final RemovalNotification removalNotification) {
        }
        
        static {
            $VALUES = new NullListener[] { NullListener.INSTANCE };
        }
    }
}
