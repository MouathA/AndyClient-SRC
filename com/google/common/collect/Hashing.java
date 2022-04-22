package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;

@GwtCompatible
final class Hashing
{
    private static final int C1 = -862048943;
    private static final int C2 = 461845907;
    
    private Hashing() {
    }
    
    static int smear(final int n) {
        return 461845907 * Integer.rotateLeft(n * -862048943, 15);
    }
    
    static int smearedHash(@Nullable final Object o) {
        return smear((o == null) ? 0 : o.hashCode());
    }
    
    static int closedTableSize(int max, final double n) {
        max = Math.max(max, 2);
        final int highestOneBit = Integer.highestOneBit(max);
        if (max > (int)(n * highestOneBit)) {
            final int n2 = highestOneBit << 1;
            return (n2 > 0) ? n2 : 1073741824;
        }
        return highestOneBit;
    }
    
    static boolean needsResizing(final int n, final int n2, final double n3) {
        return n > n3 * n2 && n2 < 1073741824;
    }
}
