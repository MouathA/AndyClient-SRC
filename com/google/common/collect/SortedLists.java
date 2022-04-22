package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
@Beta
final class SortedLists
{
    private SortedLists() {
    }
    
    public static int binarySearch(final List list, final Comparable comparable, final KeyPresentBehavior keyPresentBehavior, final KeyAbsentBehavior keyAbsentBehavior) {
        Preconditions.checkNotNull(comparable);
        return binarySearch(list, Preconditions.checkNotNull(comparable), Ordering.natural(), keyPresentBehavior, keyAbsentBehavior);
    }
    
    public static int binarySearch(final List list, final Function function, @Nullable final Comparable comparable, final KeyPresentBehavior keyPresentBehavior, final KeyAbsentBehavior keyAbsentBehavior) {
        return binarySearch(list, function, comparable, Ordering.natural(), keyPresentBehavior, keyAbsentBehavior);
    }
    
    public static int binarySearch(final List list, final Function function, @Nullable final Object o, final Comparator comparator, final KeyPresentBehavior keyPresentBehavior, final KeyAbsentBehavior keyAbsentBehavior) {
        return binarySearch(Lists.transform(list, function), o, comparator, keyPresentBehavior, keyAbsentBehavior);
    }
    
    public static int binarySearch(List arrayList, @Nullable final Object o, final Comparator comparator, final KeyPresentBehavior keyPresentBehavior, final KeyAbsentBehavior keyAbsentBehavior) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(arrayList);
        Preconditions.checkNotNull(keyPresentBehavior);
        Preconditions.checkNotNull(keyAbsentBehavior);
        if (!(arrayList instanceof RandomAccess)) {
            arrayList = Lists.newArrayList(arrayList);
        }
        int n = arrayList.size() - 1;
        while (0 <= n) {
            final int n2 = 0 + n >>> 1;
            final int compare = comparator.compare(o, arrayList.get(n2));
            if (compare < 0) {
                n = n2 - 1;
            }
            else {
                if (compare > 0) {
                    continue;
                }
                return 0 + keyPresentBehavior.resultIndex(comparator, o, arrayList.subList(0, n + 1), n2 - 0);
            }
        }
        return keyAbsentBehavior.resultIndex(0);
    }
    
    public enum KeyAbsentBehavior
    {
        NEXT_LOWER {
            @Override
            int resultIndex(final int n) {
                return n - 1;
            }
        }, 
        NEXT_HIGHER {
            public int resultIndex(final int n) {
                return n;
            }
        }, 
        INVERTED_INSERTION_INDEX {
            public int resultIndex(final int n) {
                return ~n;
            }
        };
        
        private static final KeyAbsentBehavior[] $VALUES;
        
        private KeyAbsentBehavior(final String s, final int n) {
        }
        
        abstract int resultIndex(final int p0);
        
        KeyAbsentBehavior(final String s, final int n, final SortedLists$1 object) {
            this(s, n);
        }
        
        static {
            $VALUES = new KeyAbsentBehavior[] { KeyAbsentBehavior.NEXT_LOWER, KeyAbsentBehavior.NEXT_HIGHER, KeyAbsentBehavior.INVERTED_INSERTION_INDEX };
        }
    }
    
    public enum KeyPresentBehavior
    {
        ANY_PRESENT {
            @Override
            int resultIndex(final Comparator comparator, final Object o, final List list, final int n) {
                return n;
            }
        }, 
        LAST_PRESENT {
            @Override
            int resultIndex(final Comparator comparator, final Object o, final List list, final int n) {
                int i = n;
                int n2 = list.size() - 1;
                while (i < n2) {
                    final int n3 = i + n2 + 1 >>> 1;
                    if (comparator.compare(list.get(n3), o) > 0) {
                        n2 = n3 - 1;
                    }
                    else {
                        i = n3;
                    }
                }
                return i;
            }
        }, 
        FIRST_PRESENT {
            @Override
            int resultIndex(final Comparator comparator, final Object o, final List list, final int n) {
                int n2 = n;
                while (0 < n2) {
                    final int n3 = 0 + n2 >>> 1;
                    if (comparator.compare(list.get(n3), o) < 0) {
                        continue;
                    }
                    n2 = n3;
                }
                return 0;
            }
        }, 
        FIRST_AFTER {
            public int resultIndex(final Comparator comparator, final Object o, final List list, final int n) {
                return SortedLists$KeyPresentBehavior$4.LAST_PRESENT.resultIndex(comparator, o, list, n) + 1;
            }
        }, 
        LAST_BEFORE {
            public int resultIndex(final Comparator comparator, final Object o, final List list, final int n) {
                return SortedLists$KeyPresentBehavior$5.FIRST_PRESENT.resultIndex(comparator, o, list, n) - 1;
            }
        };
        
        private static final KeyPresentBehavior[] $VALUES;
        
        private KeyPresentBehavior(final String s, final int n) {
        }
        
        abstract int resultIndex(final Comparator p0, final Object p1, final List p2, final int p3);
        
        KeyPresentBehavior(final String s, final int n, final SortedLists$1 object) {
            this(s, n);
        }
        
        static {
            $VALUES = new KeyPresentBehavior[] { KeyPresentBehavior.ANY_PRESENT, KeyPresentBehavior.LAST_PRESENT, KeyPresentBehavior.FIRST_PRESENT, KeyPresentBehavior.FIRST_AFTER, KeyPresentBehavior.LAST_BEFORE };
        }
    }
}
