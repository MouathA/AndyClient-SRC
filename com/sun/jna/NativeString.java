package com.sun.jna;

import java.nio.*;

class NativeString implements CharSequence, Comparable
{
    private Pointer pointer;
    private boolean wide;
    
    public NativeString(final String s) {
        this(s, false);
    }
    
    public NativeString(final String s, final boolean wide) {
        if (s == null) {
            throw new NullPointerException("String must not be null");
        }
        this.wide = wide;
        if (wide) {
            (this.pointer = new Memory((s.length() + 1) * Native.WCHAR_SIZE)).setString(0L, s, true);
        }
        else {
            final byte[] bytes = Native.getBytes(s);
            (this.pointer = new Memory(bytes.length + 1)).write(0L, bytes, 0, bytes.length);
            this.pointer.setByte(bytes.length, (byte)0);
        }
    }
    
    public int hashCode() {
        return this.toString().hashCode();
    }
    
    public boolean equals(final Object o) {
        return o instanceof CharSequence && this.compareTo(o) == 0;
    }
    
    public String toString() {
        return (this.wide ? "const wchar_t*" : "const char*") + "(" + this.pointer.getString(0L, this.wide) + ")";
    }
    
    public Pointer getPointer() {
        return this.pointer;
    }
    
    public char charAt(final int n) {
        return this.toString().charAt(n);
    }
    
    public int length() {
        return this.toString().length();
    }
    
    public CharSequence subSequence(final int n, final int n2) {
        return CharBuffer.wrap(this.toString()).subSequence(n, n2);
    }
    
    public int compareTo(final Object o) {
        if (o == null) {
            return 1;
        }
        return this.toString().compareTo(o.toString());
    }
}
