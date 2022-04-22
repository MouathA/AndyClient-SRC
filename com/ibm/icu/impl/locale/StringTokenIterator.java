package com.ibm.icu.impl.locale;

public class StringTokenIterator
{
    private String _text;
    private String _dlms;
    private String _token;
    private int _start;
    private int _end;
    private boolean _done;
    
    public StringTokenIterator(final String text, final String dlms) {
        this._text = text;
        this._dlms = dlms;
        this.setStart(0);
    }
    
    public String first() {
        this.setStart(0);
        return this._token;
    }
    
    public String current() {
        return this._token;
    }
    
    public int currentStart() {
        return this._start;
    }
    
    public int currentEnd() {
        return this._end;
    }
    
    public boolean isDone() {
        return this._done;
    }
    
    public String next() {
        if (this.hasNext()) {
            this._start = this._end + 1;
            this._end = this.nextDelimiter(this._start);
            this._token = this._text.substring(this._start, this._end);
        }
        else {
            this._start = this._end;
            this._token = null;
            this._done = true;
        }
        return this._token;
    }
    
    public boolean hasNext() {
        return this._end < this._text.length();
    }
    
    public StringTokenIterator setStart(final int start) {
        if (start > this._text.length()) {
            throw new IndexOutOfBoundsException();
        }
        this._start = start;
        this._end = this.nextDelimiter(this._start);
        this._token = this._text.substring(this._start, this._end);
        this._done = false;
        return this;
    }
    
    public StringTokenIterator setText(final String text) {
        this._text = text;
        this.setStart(0);
        return this;
    }
    
    private int nextDelimiter(final int n) {
        int i = 0;
    Label_0060:
        for (i = n; i < this._text.length(); ++i) {
            final char char1 = this._text.charAt(i);
            while (0 < this._dlms.length()) {
                if (char1 == this._dlms.charAt(0)) {
                    break Label_0060;
                }
                int n2 = 0;
                ++n2;
            }
        }
        return i;
    }
}
