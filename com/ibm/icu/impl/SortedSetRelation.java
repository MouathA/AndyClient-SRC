package com.ibm.icu.impl;

import java.util.*;

public class SortedSetRelation
{
    public static final int A_NOT_B = 4;
    public static final int A_AND_B = 2;
    public static final int B_NOT_A = 1;
    public static final int ANY = 7;
    public static final int CONTAINS = 6;
    public static final int DISJOINT = 5;
    public static final int ISCONTAINED = 3;
    public static final int NO_B = 4;
    public static final int EQUALS = 2;
    public static final int NO_A = 1;
    public static final int NONE = 0;
    public static final int ADDALL = 7;
    public static final int A = 6;
    public static final int COMPLEMENTALL = 5;
    public static final int B = 3;
    public static final int REMOVEALL = 4;
    public static final int RETAINALL = 2;
    public static final int B_REMOVEALL = 1;
    
    public static boolean hasRelation(final SortedSet set, final int n, final SortedSet set2) {
        if (n < 0 || n > 7) {
            throw new IllegalArgumentException("Relation " + n + " out of range");
        }
        final boolean b = (n & 0x4) != 0x0;
        final boolean b2 = (n & 0x2) != 0x0;
        final boolean b3 = (n & 0x1) != 0x0;
        switch (n) {
            case 6: {
                if (set.size() < set2.size()) {
                    return false;
                }
                break;
            }
            case 3: {
                if (set.size() > set2.size()) {
                    return false;
                }
                break;
            }
            case 2: {
                if (set.size() != set2.size()) {
                    return false;
                }
                break;
            }
        }
        if (set.size() == 0) {
            return set2.size() == 0 || b3;
        }
        if (set2.size() == 0) {
            return b;
        }
        final Iterator<Object> iterator = set.iterator();
        final Iterator<Object> iterator2 = set2.iterator();
        Comparable<Object> comparable = iterator.next();
        Object o = iterator2.next();
        while (true) {
            final int compareTo = comparable.compareTo(o);
            if (compareTo == 0) {
                if (!b2) {
                    return false;
                }
                if (!iterator.hasNext()) {
                    return !iterator2.hasNext() || b3;
                }
                if (!iterator2.hasNext()) {
                    return b;
                }
                comparable = iterator.next();
                o = iterator2.next();
            }
            else if (compareTo < 0) {
                if (!b) {
                    return false;
                }
                if (!iterator.hasNext()) {
                    return b3;
                }
                comparable = iterator.next();
            }
            else {
                if (!b3) {
                    return false;
                }
                if (!iterator2.hasNext()) {
                    return b;
                }
                o = iterator2.next();
            }
        }
    }
    
    public static SortedSet doOperation(final SortedSet set, final int n, final SortedSet set2) {
        switch (n) {
            case 7: {
                set.addAll(set2);
                return set;
            }
            case 6: {
                return set;
            }
            case 3: {
                set.clear();
                set.addAll(set2);
                return set;
            }
            case 4: {
                set.removeAll(set2);
                return set;
            }
            case 2: {
                set.retainAll(set2);
                return set;
            }
            case 5: {
                final TreeSet set3 = new TreeSet((SortedSet<E>)set2);
                set3.removeAll(set);
                set.removeAll(set2);
                set.addAll(set3);
                return set;
            }
            case 1: {
                final TreeSet set4 = new TreeSet(set2);
                set4.removeAll(set);
                set.clear();
                set.addAll(set4);
                return set;
            }
            case 0: {
                set.clear();
                return set;
            }
            default: {
                throw new IllegalArgumentException("Relation " + n + " out of range");
            }
        }
    }
}
