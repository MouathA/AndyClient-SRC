package com.ibm.icu.impl;

import java.text.*;
import com.ibm.icu.text.*;

public class RuleCharacterIterator
{
    private String text;
    private ParsePosition pos;
    private SymbolTable sym;
    private char[] buf;
    private int bufPos;
    private boolean isEscaped;
    public static final int DONE = -1;
    public static final int PARSE_VARIABLES = 1;
    public static final int PARSE_ESCAPES = 2;
    public static final int SKIP_WHITESPACE = 4;
    
    public RuleCharacterIterator(final String text, final SymbolTable sym, final ParsePosition pos) {
        if (text == null || pos.getIndex() > text.length()) {
            throw new IllegalArgumentException();
        }
        this.text = text;
        this.sym = sym;
        this.pos = pos;
        this.buf = null;
    }
    
    public boolean atEnd() {
        return this.buf == null && this.pos.getIndex() == this.text.length();
    }
    
    public int next(final int n) {
        this.isEscaped = false;
        while (true) {
            this._current();
            this._advance(UTF16.getCharCount(-1));
            if (-1 == 36 && this.buf == null && (n & 0x1) != 0x0 && this.sym != null) {
                final String reference = this.sym.parseReference(this.text, this.pos, this.text.length());
                if (reference == null) {
                    break;
                }
                this.bufPos = 0;
                this.buf = this.sym.lookup(reference);
                if (this.buf == null) {
                    throw new IllegalArgumentException("Undefined variable: " + reference);
                }
                if (this.buf.length != 0) {
                    continue;
                }
                this.buf = null;
            }
            else {
                if ((n & 0x4) != 0x0 && PatternProps.isWhiteSpace(-1)) {
                    continue;
                }
                if (-1 != 92 || (n & 0x2) == 0x0) {
                    break;
                }
                final int[] array = { 0 };
                Utility.unescapeAt(this.lookahead(), array);
                this.jumpahead(array[0]);
                this.isEscaped = true;
                if (-1 < 0) {
                    throw new IllegalArgumentException("Invalid escape");
                }
                break;
            }
        }
        return -1;
    }
    
    public boolean isEscaped() {
        return this.isEscaped;
    }
    
    public boolean inVariable() {
        return this.buf != null;
    }
    
    public Object getPos(final Object o) {
        if (o == null) {
            return new Object[] { this.buf, { this.pos.getIndex(), this.bufPos } };
        }
        final Object[] array = (Object[])o;
        array[0] = this.buf;
        final int[] array2 = (int[])array[1];
        array2[0] = this.pos.getIndex();
        array2[1] = this.bufPos;
        return o;
    }
    
    public void setPos(final Object o) {
        final Object[] array = (Object[])o;
        this.buf = (char[])array[0];
        final int[] array2 = (int[])array[1];
        this.pos.setIndex(array2[0]);
        this.bufPos = array2[1];
    }
    
    public void skipIgnored(final int n) {
        if ((n & 0x4) != 0x0) {
            while (true) {
                final int current = this._current();
                if (!PatternProps.isWhiteSpace(current)) {
                    break;
                }
                this._advance(UTF16.getCharCount(current));
            }
        }
    }
    
    public String lookahead() {
        if (this.buf != null) {
            return new String(this.buf, this.bufPos, this.buf.length - this.bufPos);
        }
        return this.text.substring(this.pos.getIndex());
    }
    
    public void jumpahead(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        if (this.buf != null) {
            this.bufPos += n;
            if (this.bufPos > this.buf.length) {
                throw new IllegalArgumentException();
            }
            if (this.bufPos == this.buf.length) {
                this.buf = null;
            }
        }
        else {
            final int index = this.pos.getIndex() + n;
            this.pos.setIndex(index);
            if (index > this.text.length()) {
                throw new IllegalArgumentException();
            }
        }
    }
    
    @Override
    public String toString() {
        final int index = this.pos.getIndex();
        return this.text.substring(0, index) + '|' + this.text.substring(index);
    }
    
    private int _current() {
        if (this.buf != null) {
            return UTF16.charAt(this.buf, 0, this.buf.length, this.bufPos);
        }
        final int index = this.pos.getIndex();
        return (index < this.text.length()) ? UTF16.charAt(this.text, index) : -1;
    }
    
    private void _advance(final int n) {
        if (this.buf != null) {
            this.bufPos += n;
            if (this.bufPos == this.buf.length) {
                this.buf = null;
            }
        }
        else {
            this.pos.setIndex(this.pos.getIndex() + n);
            if (this.pos.getIndex() > this.text.length()) {
                this.pos.setIndex(this.text.length());
            }
        }
    }
}
