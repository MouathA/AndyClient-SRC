package com.ibm.icu.impl.duration;

public final class Period
{
    final byte timeLimit;
    final boolean inFuture;
    final int[] counts;
    
    public static Period at(final float n, final TimeUnit timeUnit) {
        checkCount(n);
        return new Period(0, false, n, timeUnit);
    }
    
    public static Period moreThan(final float n, final TimeUnit timeUnit) {
        checkCount(n);
        return new Period(2, false, n, timeUnit);
    }
    
    public static Period lessThan(final float n, final TimeUnit timeUnit) {
        checkCount(n);
        return new Period(1, false, n, timeUnit);
    }
    
    public Period and(final float n, final TimeUnit timeUnit) {
        checkCount(n);
        return this.setTimeUnitValue(timeUnit, n);
    }
    
    public Period omit(final TimeUnit timeUnit) {
        return this.setTimeUnitInternalValue(timeUnit, 0);
    }
    
    public Period at() {
        return this.setTimeLimit((byte)0);
    }
    
    public Period moreThan() {
        return this.setTimeLimit((byte)2);
    }
    
    public Period lessThan() {
        return this.setTimeLimit((byte)1);
    }
    
    public Period inFuture() {
        return this.setFuture(true);
    }
    
    public Period inPast() {
        return this.setFuture(false);
    }
    
    public Period inFuture(final boolean future) {
        return this.setFuture(future);
    }
    
    public Period inPast(final boolean b) {
        return this.setFuture(!b);
    }
    
    public boolean isSet() {
        while (0 < this.counts.length) {
            if (this.counts[0] != 0) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public boolean isSet(final TimeUnit timeUnit) {
        return this.counts[timeUnit.ordinal] > 0;
    }
    
    public float getCount(final TimeUnit timeUnit) {
        final byte ordinal = timeUnit.ordinal;
        if (this.counts[ordinal] == 0) {
            return 0.0f;
        }
        return (this.counts[ordinal] - 1) / 1000.0f;
    }
    
    public boolean isInFuture() {
        return this.inFuture;
    }
    
    public boolean isInPast() {
        return !this.inFuture;
    }
    
    public boolean isMoreThan() {
        return this.timeLimit == 2;
    }
    
    public boolean isLessThan() {
        return this.timeLimit == 1;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this.equals((Period)o);
    }
    
    public boolean equals(final Period period) {
        if (period != null && this.timeLimit == period.timeLimit && this.inFuture == period.inFuture) {
            while (0 < this.counts.length) {
                if (this.counts[0] != period.counts[0]) {
                    return false;
                }
                int n = 0;
                ++n;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int n = this.timeLimit << 1 | (this.inFuture ? 1 : 0);
        while (0 < this.counts.length) {
            n = (n << 2 ^ this.counts[0]);
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    private Period(final int n, final boolean inFuture, final float n2, final TimeUnit timeUnit) {
        this.timeLimit = (byte)n;
        this.inFuture = inFuture;
        (this.counts = new int[TimeUnit.units.length])[timeUnit.ordinal] = (int)(n2 * 1000.0f) + 1;
    }
    
    Period(final int n, final boolean inFuture, final int[] counts) {
        this.timeLimit = (byte)n;
        this.inFuture = inFuture;
        this.counts = counts;
    }
    
    private Period setTimeUnitValue(final TimeUnit timeUnit, final float n) {
        if (n < 0.0f) {
            throw new IllegalArgumentException("value: " + n);
        }
        return this.setTimeUnitInternalValue(timeUnit, (int)(n * 1000.0f) + 1);
    }
    
    private Period setTimeUnitInternalValue(final TimeUnit timeUnit, final int n) {
        final byte ordinal = timeUnit.ordinal;
        if (this.counts[ordinal] != n) {
            final int[] array = new int[this.counts.length];
            while (0 < this.counts.length) {
                array[0] = this.counts[0];
                int n2 = 0;
                ++n2;
            }
            array[ordinal] = n;
            return new Period(this.timeLimit, this.inFuture, array);
        }
        return this;
    }
    
    private Period setFuture(final boolean b) {
        if (this.inFuture != b) {
            return new Period(this.timeLimit, b, this.counts);
        }
        return this;
    }
    
    private Period setTimeLimit(final byte b) {
        if (this.timeLimit != b) {
            return new Period(b, this.inFuture, this.counts);
        }
        return this;
    }
    
    private static void checkCount(final float n) {
        if (n < 0.0f) {
            throw new IllegalArgumentException("count (" + n + ") cannot be negative");
        }
    }
}
