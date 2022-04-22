package com.ibm.icu.impl;

import java.util.*;

public class MultiComparator implements Comparator
{
    private Comparator[] comparators;
    
    public MultiComparator(final Comparator... comparators) {
        this.comparators = comparators;
    }
    
    public int compare(final Object o, final Object o2) {
        while (0 < this.comparators.length) {
            final int compare = this.comparators[0].compare(o, o2);
            if (compare == 0) {
                int n = 0;
                ++n;
            }
            else {
                if (compare > 0) {
                    return 1;
                }
                return -1;
            }
        }
        return 0;
    }
}
