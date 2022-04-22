package com.google.common.cache;

import com.google.common.annotations.*;
import java.util.concurrent.*;
import com.google.common.collect.*;
import java.util.*;
import com.google.common.base.*;
import javax.annotation.*;

@Beta
public final class CacheBuilderSpec
{
    private static final Splitter KEYS_SPLITTER;
    private static final Splitter KEY_VALUE_SPLITTER;
    private static final ImmutableMap VALUE_PARSERS;
    @VisibleForTesting
    Integer initialCapacity;
    @VisibleForTesting
    Long maximumSize;
    @VisibleForTesting
    Long maximumWeight;
    @VisibleForTesting
    Integer concurrencyLevel;
    @VisibleForTesting
    LocalCache.Strength keyStrength;
    @VisibleForTesting
    LocalCache.Strength valueStrength;
    @VisibleForTesting
    Boolean recordStats;
    @VisibleForTesting
    long writeExpirationDuration;
    @VisibleForTesting
    TimeUnit writeExpirationTimeUnit;
    @VisibleForTesting
    long accessExpirationDuration;
    @VisibleForTesting
    TimeUnit accessExpirationTimeUnit;
    @VisibleForTesting
    long refreshDuration;
    @VisibleForTesting
    TimeUnit refreshTimeUnit;
    private final String specification;
    
    private CacheBuilderSpec(final String specification) {
        this.specification = specification;
    }
    
    public static CacheBuilderSpec parse(final String s) {
        final CacheBuilderSpec cacheBuilderSpec = new CacheBuilderSpec(s);
        if (!s.isEmpty()) {
            for (final String s2 : CacheBuilderSpec.KEYS_SPLITTER.split(s)) {
                final ImmutableList copy = ImmutableList.copyOf(CacheBuilderSpec.KEY_VALUE_SPLITTER.split(s2));
                Preconditions.checkArgument(!copy.isEmpty(), (Object)"blank key-value pair");
                Preconditions.checkArgument(copy.size() <= 2, "key-value pair %s with more than one equals sign", s2);
                final String s3 = copy.get(0);
                final ValueParser valueParser = (ValueParser)CacheBuilderSpec.VALUE_PARSERS.get(s3);
                Preconditions.checkArgument(valueParser != null, "unknown key %s", s3);
                valueParser.parse(cacheBuilderSpec, s3, (copy.size() == 1) ? null : copy.get(1));
            }
        }
        return cacheBuilderSpec;
    }
    
    public static CacheBuilderSpec disableCaching() {
        return parse("maximumSize=0");
    }
    
    CacheBuilder toCacheBuilder() {
        final CacheBuilder builder = CacheBuilder.newBuilder();
        if (this.initialCapacity != null) {
            builder.initialCapacity(this.initialCapacity);
        }
        if (this.maximumSize != null) {
            builder.maximumSize(this.maximumSize);
        }
        if (this.maximumWeight != null) {
            builder.maximumWeight(this.maximumWeight);
        }
        if (this.concurrencyLevel != null) {
            builder.concurrencyLevel(this.concurrencyLevel);
        }
        if (this.keyStrength != null) {
            switch (this.keyStrength) {
                case WEAK: {
                    builder.weakKeys();
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
        if (this.valueStrength != null) {
            switch (this.valueStrength) {
                case SOFT: {
                    builder.softValues();
                    break;
                }
                case WEAK: {
                    builder.weakValues();
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
        if (this.recordStats != null && this.recordStats) {
            builder.recordStats();
        }
        if (this.writeExpirationTimeUnit != null) {
            builder.expireAfterWrite(this.writeExpirationDuration, this.writeExpirationTimeUnit);
        }
        if (this.accessExpirationTimeUnit != null) {
            builder.expireAfterAccess(this.accessExpirationDuration, this.accessExpirationTimeUnit);
        }
        if (this.refreshTimeUnit != null) {
            builder.refreshAfterWrite(this.refreshDuration, this.refreshTimeUnit);
        }
        return builder;
    }
    
    public String toParsableString() {
        return this.specification;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this).addValue(this.toParsableString()).toString();
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.initialCapacity, this.maximumSize, this.maximumWeight, this.concurrencyLevel, this.keyStrength, this.valueStrength, this.recordStats, durationInNanos(this.writeExpirationDuration, this.writeExpirationTimeUnit), durationInNanos(this.accessExpirationDuration, this.accessExpirationTimeUnit), durationInNanos(this.refreshDuration, this.refreshTimeUnit));
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CacheBuilderSpec)) {
            return false;
        }
        final CacheBuilderSpec cacheBuilderSpec = (CacheBuilderSpec)o;
        return Objects.equal(this.initialCapacity, cacheBuilderSpec.initialCapacity) && Objects.equal(this.maximumSize, cacheBuilderSpec.maximumSize) && Objects.equal(this.maximumWeight, cacheBuilderSpec.maximumWeight) && Objects.equal(this.concurrencyLevel, cacheBuilderSpec.concurrencyLevel) && Objects.equal(this.keyStrength, cacheBuilderSpec.keyStrength) && Objects.equal(this.valueStrength, cacheBuilderSpec.valueStrength) && Objects.equal(this.recordStats, cacheBuilderSpec.recordStats) && Objects.equal(durationInNanos(this.writeExpirationDuration, this.writeExpirationTimeUnit), durationInNanos(cacheBuilderSpec.writeExpirationDuration, cacheBuilderSpec.writeExpirationTimeUnit)) && Objects.equal(durationInNanos(this.accessExpirationDuration, this.accessExpirationTimeUnit), durationInNanos(cacheBuilderSpec.accessExpirationDuration, cacheBuilderSpec.accessExpirationTimeUnit)) && Objects.equal(durationInNanos(this.refreshDuration, this.refreshTimeUnit), durationInNanos(cacheBuilderSpec.refreshDuration, cacheBuilderSpec.refreshTimeUnit));
    }
    
    @Nullable
    private static Long durationInNanos(final long n, @Nullable final TimeUnit timeUnit) {
        return (timeUnit == null) ? null : Long.valueOf(timeUnit.toNanos(n));
    }
    
    static {
        KEYS_SPLITTER = Splitter.on(',').trimResults();
        KEY_VALUE_SPLITTER = Splitter.on('=').trimResults();
        VALUE_PARSERS = ImmutableMap.builder().put("initialCapacity", new InitialCapacityParser()).put("maximumSize", new MaximumSizeParser()).put("maximumWeight", new MaximumWeightParser()).put("concurrencyLevel", new ConcurrencyLevelParser()).put("weakKeys", new KeyStrengthParser(LocalCache.Strength.WEAK)).put("softValues", new ValueStrengthParser(LocalCache.Strength.SOFT)).put("weakValues", new ValueStrengthParser(LocalCache.Strength.WEAK)).put("recordStats", new RecordStatsParser()).put("expireAfterAccess", new AccessDurationParser()).put("expireAfterWrite", new WriteDurationParser()).put("refreshAfterWrite", new RefreshDurationParser()).put("refreshInterval", new RefreshDurationParser()).build();
    }
    
    static class RefreshDurationParser extends DurationParser
    {
        @Override
        protected void parseDuration(final CacheBuilderSpec cacheBuilderSpec, final long refreshDuration, final TimeUnit refreshTimeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.refreshTimeUnit == null, (Object)"refreshAfterWrite already set");
            cacheBuilderSpec.refreshDuration = refreshDuration;
            cacheBuilderSpec.refreshTimeUnit = refreshTimeUnit;
        }
    }
    
    abstract static class DurationParser implements ValueParser
    {
        protected abstract void parseDuration(final CacheBuilderSpec p0, final long p1, final TimeUnit p2);
        
        @Override
        public void parse(final CacheBuilderSpec cacheBuilderSpec, final String s, final String s2) {
            Preconditions.checkArgument(s2 != null && !s2.isEmpty(), "value of key %s omitted", s);
            TimeUnit timeUnit = null;
            switch (s2.charAt(s2.length() - 1)) {
                case 'd': {
                    timeUnit = TimeUnit.DAYS;
                    break;
                }
                case 'h': {
                    timeUnit = TimeUnit.HOURS;
                    break;
                }
                case 'm': {
                    timeUnit = TimeUnit.MINUTES;
                    break;
                }
                case 's': {
                    timeUnit = TimeUnit.SECONDS;
                    break;
                }
                default: {
                    throw new IllegalArgumentException(String.format("key %s invalid format.  was %s, must end with one of [dDhHmMsS]", s, s2));
                }
            }
            this.parseDuration(cacheBuilderSpec, Long.parseLong(s2.substring(0, s2.length() - 1)), timeUnit);
        }
    }
    
    private interface ValueParser
    {
        void parse(final CacheBuilderSpec p0, final String p1, @Nullable final String p2);
    }
    
    static class WriteDurationParser extends DurationParser
    {
        @Override
        protected void parseDuration(final CacheBuilderSpec cacheBuilderSpec, final long writeExpirationDuration, final TimeUnit writeExpirationTimeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.writeExpirationTimeUnit == null, (Object)"expireAfterWrite already set");
            cacheBuilderSpec.writeExpirationDuration = writeExpirationDuration;
            cacheBuilderSpec.writeExpirationTimeUnit = writeExpirationTimeUnit;
        }
    }
    
    static class AccessDurationParser extends DurationParser
    {
        @Override
        protected void parseDuration(final CacheBuilderSpec cacheBuilderSpec, final long accessExpirationDuration, final TimeUnit accessExpirationTimeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.accessExpirationTimeUnit == null, (Object)"expireAfterAccess already set");
            cacheBuilderSpec.accessExpirationDuration = accessExpirationDuration;
            cacheBuilderSpec.accessExpirationTimeUnit = accessExpirationTimeUnit;
        }
    }
    
    static class RecordStatsParser implements ValueParser
    {
        @Override
        public void parse(final CacheBuilderSpec cacheBuilderSpec, final String s, @Nullable final String s2) {
            Preconditions.checkArgument(s2 == null, (Object)"recordStats does not take values");
            Preconditions.checkArgument(cacheBuilderSpec.recordStats == null, (Object)"recordStats already set");
            cacheBuilderSpec.recordStats = true;
        }
    }
    
    static class ValueStrengthParser implements ValueParser
    {
        private final LocalCache.Strength strength;
        
        public ValueStrengthParser(final LocalCache.Strength strength) {
            this.strength = strength;
        }
        
        @Override
        public void parse(final CacheBuilderSpec cacheBuilderSpec, final String s, @Nullable final String s2) {
            Preconditions.checkArgument(s2 == null, "key %s does not take values", s);
            Preconditions.checkArgument(cacheBuilderSpec.valueStrength == null, "%s was already set to %s", s, cacheBuilderSpec.valueStrength);
            cacheBuilderSpec.valueStrength = this.strength;
        }
    }
    
    static class KeyStrengthParser implements ValueParser
    {
        private final LocalCache.Strength strength;
        
        public KeyStrengthParser(final LocalCache.Strength strength) {
            this.strength = strength;
        }
        
        @Override
        public void parse(final CacheBuilderSpec cacheBuilderSpec, final String s, @Nullable final String s2) {
            Preconditions.checkArgument(s2 == null, "key %s does not take values", s);
            Preconditions.checkArgument(cacheBuilderSpec.keyStrength == null, "%s was already set to %s", s, cacheBuilderSpec.keyStrength);
            cacheBuilderSpec.keyStrength = this.strength;
        }
    }
    
    static class ConcurrencyLevelParser extends IntegerParser
    {
        @Override
        protected void parseInteger(final CacheBuilderSpec cacheBuilderSpec, final int n) {
            Preconditions.checkArgument(cacheBuilderSpec.concurrencyLevel == null, "concurrency level was already set to ", cacheBuilderSpec.concurrencyLevel);
            cacheBuilderSpec.concurrencyLevel = n;
        }
    }
    
    abstract static class IntegerParser implements ValueParser
    {
        protected abstract void parseInteger(final CacheBuilderSpec p0, final int p1);
        
        @Override
        public void parse(final CacheBuilderSpec cacheBuilderSpec, final String s, final String s2) {
            Preconditions.checkArgument(s2 != null && !s2.isEmpty(), "value of key %s omitted", s);
            this.parseInteger(cacheBuilderSpec, Integer.parseInt(s2));
        }
    }
    
    static class MaximumWeightParser extends LongParser
    {
        @Override
        protected void parseLong(final CacheBuilderSpec cacheBuilderSpec, final long n) {
            Preconditions.checkArgument(cacheBuilderSpec.maximumWeight == null, "maximum weight was already set to ", cacheBuilderSpec.maximumWeight);
            Preconditions.checkArgument(cacheBuilderSpec.maximumSize == null, "maximum size was already set to ", cacheBuilderSpec.maximumSize);
            cacheBuilderSpec.maximumWeight = n;
        }
    }
    
    abstract static class LongParser implements ValueParser
    {
        protected abstract void parseLong(final CacheBuilderSpec p0, final long p1);
        
        @Override
        public void parse(final CacheBuilderSpec cacheBuilderSpec, final String s, final String s2) {
            Preconditions.checkArgument(s2 != null && !s2.isEmpty(), "value of key %s omitted", s);
            this.parseLong(cacheBuilderSpec, Long.parseLong(s2));
        }
    }
    
    static class MaximumSizeParser extends LongParser
    {
        @Override
        protected void parseLong(final CacheBuilderSpec cacheBuilderSpec, final long n) {
            Preconditions.checkArgument(cacheBuilderSpec.maximumSize == null, "maximum size was already set to ", cacheBuilderSpec.maximumSize);
            Preconditions.checkArgument(cacheBuilderSpec.maximumWeight == null, "maximum weight was already set to ", cacheBuilderSpec.maximumWeight);
            cacheBuilderSpec.maximumSize = n;
        }
    }
    
    static class InitialCapacityParser extends IntegerParser
    {
        @Override
        protected void parseInteger(final CacheBuilderSpec cacheBuilderSpec, final int n) {
            Preconditions.checkArgument(cacheBuilderSpec.initialCapacity == null, "initial capacity was already set to ", cacheBuilderSpec.initialCapacity);
            cacheBuilderSpec.initialCapacity = n;
        }
    }
}
