package com.viaversion.viaversion.libs.fastutil.ints;

public final class IntIterables
{
    private IntIterables() {
    }
    
    public static long size(final IntIterable intIterable) {
        long n = 0L;
        final IntIterator iterator = intIterable.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            ++n;
        }
        return n;
    }
}
