package com.ibm.icu.impl;

import com.ibm.icu.util.*;

public class Row implements Comparable, Cloneable, Freezable
{
    protected Object[] items;
    protected boolean frozen;
    
    public static R2 of(final Object o, final Object o2) {
        return new R2(o, o2);
    }
    
    public static R3 of(final Object o, final Object o2, final Object o3) {
        return new R3(o, o2, o3);
    }
    
    public static R4 of(final Object o, final Object o2, final Object o3, final Object o4) {
        return new R4(o, o2, o3, o4);
    }
    
    public static R5 of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5) {
        return new R5(o, o2, o3, o4, o5);
    }
    
    public Row set0(final Object o) {
        return this.set(0, o);
    }
    
    public Object get0() {
        return this.items[0];
    }
    
    public Row set1(final Object o) {
        return this.set(1, o);
    }
    
    public Object get1() {
        return this.items[1];
    }
    
    public Row set2(final Object o) {
        return this.set(2, o);
    }
    
    public Object get2() {
        return this.items[2];
    }
    
    public Row set3(final Object o) {
        return this.set(3, o);
    }
    
    public Object get3() {
        return this.items[3];
    }
    
    public Row set4(final Object o) {
        return this.set(4, o);
    }
    
    public Object get4() {
        return this.items[4];
    }
    
    protected Row set(final int n, final Object o) {
        if (this.frozen) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        this.items[n] = o;
        return this;
    }
    
    @Override
    public int hashCode() {
        int length = this.items.length;
        final Object[] items = this.items;
        while (0 < items.length) {
            length = length * 37 + Utility.checkHash(items[0]);
            int n = 0;
            ++n;
        }
        return length;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        final Row row = (Row)o;
        if (this.items.length != row.items.length) {
            return false;
        }
        final Object[] items = this.items;
        while (0 < items.length) {
            final Object o2 = items[0];
            final Object[] items2 = row.items;
            final int n = 0;
            int n2 = 0;
            ++n2;
            if (!Utility.objectEquals(o2, items2[n])) {
                return false;
            }
            int n3 = 0;
            ++n3;
        }
        return true;
    }
    
    public int compareTo(final Object o) {
        final Row row = (Row)o;
        final int n = this.items.length - row.items.length;
        if (n != 0) {
            return n;
        }
        final Object[] items = this.items;
        while (0 < items.length) {
            final Comparable comparable = (Comparable)items[0];
            final Object[] items2 = row.items;
            final int n2 = 0;
            int n3 = 0;
            ++n3;
            final int checkCompare = Utility.checkCompare(comparable, (Comparable)items2[n2]);
            if (checkCompare != 0) {
                return checkCompare;
            }
            int n4 = 0;
            ++n4;
        }
        return 0;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("[");
        final Object[] items = this.items;
        while (0 < items.length) {
            final Object o = items[0];
            if (!false) {
                sb.append(", ");
            }
            sb.append(o);
            int n = 0;
            ++n;
        }
        return sb.append("]").toString();
    }
    
    public boolean isFrozen() {
        return this.frozen;
    }
    
    public Row freeze() {
        this.frozen = true;
        return this;
    }
    
    public Object clone() {
        if (this.frozen) {
            return this;
        }
        final Row row = (Row)super.clone();
        this.items = this.items.clone();
        return row;
    }
    
    public Row cloneAsThawed() {
        final Row row = (Row)super.clone();
        this.items = this.items.clone();
        row.frozen = false;
        return row;
    }
    
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }
    
    public Object freeze() {
        return this.freeze();
    }
    
    public static class R5 extends Row
    {
        public R5(final Object o, final Object o2, final Object o3, final Object o4, final Object o5) {
            this.items = new Object[] { o, o2, o3, o4, o5 };
        }
        
        @Override
        public Object cloneAsThawed() {
            return super.cloneAsThawed();
        }
        
        @Override
        public Object freeze() {
            return super.freeze();
        }
    }
    
    public static class R4 extends Row
    {
        public R4(final Object o, final Object o2, final Object o3, final Object o4) {
            this.items = new Object[] { o, o2, o3, o4 };
        }
        
        @Override
        public Object cloneAsThawed() {
            return super.cloneAsThawed();
        }
        
        @Override
        public Object freeze() {
            return super.freeze();
        }
    }
    
    public static class R3 extends Row
    {
        public R3(final Object o, final Object o2, final Object o3) {
            this.items = new Object[] { o, o2, o3 };
        }
        
        @Override
        public Object cloneAsThawed() {
            return super.cloneAsThawed();
        }
        
        @Override
        public Object freeze() {
            return super.freeze();
        }
    }
    
    public static class R2 extends Row
    {
        public R2(final Object o, final Object o2) {
            this.items = new Object[] { o, o2 };
        }
        
        @Override
        public Object cloneAsThawed() {
            return super.cloneAsThawed();
        }
        
        @Override
        public Object freeze() {
            return super.freeze();
        }
    }
}
