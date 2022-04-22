package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.*;

public interface ObjectIterator extends Iterator
{
    default int skip(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
        }
        int n2 = n;
        while (n2-- != 0 && this.hasNext()) {
            this.next();
        }
        return n - n2 - 1;
    }
}
