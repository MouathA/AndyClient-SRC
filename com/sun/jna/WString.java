package com.sun.jna;

import java.nio.*;

public final class WString implements CharSequence, Comparable
{
    private String string;
    
    public WString(final String string) {
        if (string == null) {
            throw new NullPointerException("String initializer must be non-null");
        }
        this.string = string;
    }
    
    public String toString() {
        return this.string;
    }
    
    public boolean equals(final Object o) {
        return o instanceof WString && this.toString().equals(o.toString());
    }
    
    public int hashCode() {
        return this.toString().hashCode();
    }
    
    public int compareTo(final Object o) {
        return this.toString().compareTo(o.toString());
    }
    
    public int length() {
        return this.toString().length();
    }
    
    public char charAt(final int n) {
        return this.toString().charAt(n);
    }
    
    public CharSequence subSequence(final int n, final int n2) {
        return CharBuffer.wrap(this.toString()).subSequence(n, n2);
    }
}
