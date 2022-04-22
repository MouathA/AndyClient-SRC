package com.ibm.icu.text;

public class ReplaceableString implements Replaceable
{
    private StringBuffer buf;
    
    public ReplaceableString(final String s) {
        this.buf = new StringBuffer(s);
    }
    
    public ReplaceableString(final StringBuffer buf) {
        this.buf = buf;
    }
    
    public ReplaceableString() {
        this.buf = new StringBuffer();
    }
    
    @Override
    public String toString() {
        return this.buf.toString();
    }
    
    public String substring(final int n, final int n2) {
        return this.buf.substring(n, n2);
    }
    
    public int length() {
        return this.buf.length();
    }
    
    public char charAt(final int n) {
        return this.buf.charAt(n);
    }
    
    public int char32At(final int n) {
        return UTF16.charAt(this.buf, n);
    }
    
    public void getChars(final int n, final int n2, final char[] array, final int n3) {
        if (n != n2) {
            this.buf.getChars(n, n2, array, n3);
        }
    }
    
    public void replace(final int n, final int n2, final String s) {
        this.buf.replace(n, n2, s);
    }
    
    public void replace(final int n, final int n2, final char[] array, final int n3, final int n4) {
        this.buf.delete(n, n2);
        this.buf.insert(n, array, n3, n4);
    }
    
    public void copy(final int n, final int n2, final int n3) {
        if (n == n2 && n >= 0 && n <= this.buf.length()) {
            return;
        }
        final char[] array = new char[n2 - n];
        this.getChars(n, n2, array, 0);
        this.replace(n3, n3, array, 0, n2 - n);
    }
    
    public boolean hasMetaData() {
        return false;
    }
}
