package com.viaversion.viaversion.util;

import java.util.*;

public class Triple
{
    private final Object first;
    private final Object second;
    private final Object third;
    
    public Triple(final Object first, final Object second, final Object third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }
    
    public Object first() {
        return this.first;
    }
    
    public Object second() {
        return this.second;
    }
    
    public Object third() {
        return this.third;
    }
    
    @Deprecated
    public Object getFirst() {
        return this.first;
    }
    
    @Deprecated
    public Object getSecond() {
        return this.second;
    }
    
    @Deprecated
    public Object getThird() {
        return this.third;
    }
    
    @Override
    public String toString() {
        return "Triple{" + this.first + ", " + this.second + ", " + this.third + '}';
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Triple triple = (Triple)o;
        return Objects.equals(this.first, triple.first) && Objects.equals(this.second, triple.second) && Objects.equals(this.third, triple.third);
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * ((this.first != null) ? this.first.hashCode() : 0) + ((this.second != null) ? this.second.hashCode() : 0)) + ((this.third != null) ? this.third.hashCode() : 0);
    }
}
