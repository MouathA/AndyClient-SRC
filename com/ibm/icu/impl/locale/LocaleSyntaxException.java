package com.ibm.icu.impl.locale;

public class LocaleSyntaxException extends Exception
{
    private static final long serialVersionUID = 1L;
    private int _index;
    
    public LocaleSyntaxException(final String s) {
        this(s, 0);
    }
    
    public LocaleSyntaxException(final String s, final int index) {
        super(s);
        this._index = -1;
        this._index = index;
    }
    
    public int getErrorIndex() {
        return this._index;
    }
}
