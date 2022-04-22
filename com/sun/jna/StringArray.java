package com.sun.jna;

import java.util.*;

public class StringArray extends Memory implements Function.PostCallRead
{
    private boolean wide;
    private List natives;
    private Object[] original;
    
    public StringArray(final String[] array) {
        this(array, false);
    }
    
    public StringArray(final String[] array, final boolean b) {
        this((Object[])array, b);
    }
    
    public StringArray(final WString[] array) {
        this(array, true);
    }
    
    private StringArray(final Object[] original, final boolean wide) {
        super((original.length + 1) * Pointer.SIZE);
        this.natives = new ArrayList();
        this.original = original;
        this.wide = wide;
        while (0 < original.length) {
            Pointer pointer = null;
            if (original[0] != null) {
                final NativeString nativeString = new NativeString(original[0].toString(), wide);
                this.natives.add(nativeString);
                pointer = nativeString.getPointer();
            }
            this.setPointer(Pointer.SIZE * 0, pointer);
            int n = 0;
            ++n;
        }
        this.setPointer(Pointer.SIZE * original.length, null);
    }
    
    public void read() {
        final boolean b = this.original instanceof WString[];
        while (0 < this.original.length) {
            final Pointer pointer = this.getPointer(0 * Pointer.SIZE);
            String string = null;
            if (pointer != null) {
                string = pointer.getString(0L, this.wide);
                if (b) {
                    string = (String)new WString(string);
                }
            }
            this.original[0] = string;
            int n = 0;
            ++n;
        }
    }
    
    public String toString() {
        return (this.wide ? "const wchar_t*[]" : "const char*[]") + Arrays.asList(this.original);
    }
}
