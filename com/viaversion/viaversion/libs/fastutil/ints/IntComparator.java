package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.*;
import java.io.*;
import java.lang.invoke.*;

@FunctionalInterface
public interface IntComparator extends Comparator
{
    int compare(final int p0, final int p1);
    
    default IntComparator reversed() {
        return IntComparators.oppositeComparator(this);
    }
    
    @Deprecated
    default int compare(final Integer n, final Integer n2) {
        return this.compare((int)n, (int)n2);
    }
    
    default IntComparator thenComparing(final IntComparator intComparator) {
        return this::lambda$thenComparing$931d6fed$1;
    }
    
    default Comparator thenComparing(final Comparator comparator) {
        if (comparator instanceof IntComparator) {
            return this.thenComparing((IntComparator)comparator);
        }
        return super.thenComparing(comparator);
    }
    
    default Comparator reversed() {
        return this.reversed();
    }
    
    @Deprecated
    default int compare(final Object o, final Object o2) {
        return this.compare((Integer)o, (Integer)o2);
    }
    
    default Object $deserializeLambda$(final SerializedLambda serializedLambda) {
        final String implMethodName = serializedLambda.getImplMethodName();
        switch (implMethodName.hashCode()) {
            case -1554871547: {
                if (implMethodName.equals("lambda$thenComparing$931d6fed$1")) {}
                break;
            }
        }
        switch (false) {
            case 0: {
                if (serializedLambda.getImplMethodKind() == 7 && serializedLambda.getFunctionalInterfaceClass().equals("com/viaversion/viaversion/libs/fastutil/ints/IntComparator") && serializedLambda.getFunctionalInterfaceMethodName().equals("compare") && serializedLambda.getFunctionalInterfaceMethodSignature().equals("(II)I") && serializedLambda.getImplClass().equals("com/viaversion/viaversion/libs/fastutil/ints/IntComparator") && serializedLambda.getImplMethodSignature().equals("(Lit/unimi/dsi/fastutil/ints/IntComparator;II)I")) {
                    return (IntComparator)serializedLambda.getCapturedArg(0)::lambda$thenComparing$931d6fed$1;
                }
                break;
            }
        }
        throw new IllegalArgumentException("Invalid lambda deserialization");
    }
    
    default int lambda$thenComparing$931d6fed$1(final IntComparator intComparator, final int n, final int n2) {
        final int compare = this.compare(n, n2);
        return (compare == 0) ? intComparator.compare(n, n2) : compare;
    }
}
