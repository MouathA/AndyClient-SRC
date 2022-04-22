package com.ibm.icu.util;

public class IllformedLocaleException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    private int _errIdx;
    
    public IllformedLocaleException() {
        this._errIdx = -1;
    }
    
    public IllformedLocaleException(final String s) {
        super(s);
        this._errIdx = -1;
    }
    
    public IllformedLocaleException(final String s, final int errIdx) {
        super(s + ((errIdx < 0) ? "" : (" [at index " + errIdx + "]")));
        this._errIdx = -1;
        this._errIdx = errIdx;
    }
    
    public int getErrorIndex() {
        return this._errIdx;
    }
}
