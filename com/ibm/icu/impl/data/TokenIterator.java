package com.ibm.icu.impl.data;

import java.io.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.text.*;

public class TokenIterator
{
    private ResourceReader reader;
    private String line;
    private StringBuffer buf;
    private boolean done;
    private int pos;
    private int lastpos;
    
    public TokenIterator(final ResourceReader reader) {
        this.reader = reader;
        this.line = null;
        this.done = false;
        this.buf = new StringBuffer();
        final int n = -1;
        this.lastpos = n;
        this.pos = n;
    }
    
    public String next() throws IOException {
        if (this.done) {
            return null;
        }
        while (true) {
            if (this.line == null) {
                this.line = this.reader.readLineSkippingComments();
                if (this.line == null) {
                    this.done = true;
                    return null;
                }
                this.pos = 0;
            }
            this.buf.setLength(0);
            this.lastpos = this.pos;
            this.pos = this.nextToken(this.pos);
            if (this.pos >= 0) {
                return this.buf.toString();
            }
            this.line = null;
        }
    }
    
    public int getLineNumber() {
        return this.reader.getLineNumber();
    }
    
    public String describePosition() {
        return this.reader.describePosition() + ':' + (this.lastpos + 1);
    }
    
    private int nextToken(int i) {
        i = PatternProps.skipWhiteSpace(this.line, i);
        if (i == this.line.length()) {
            return -1;
        }
        final int n = i;
        final char char1 = this.line.charAt(i++);
        switch (char1) {
            case 34:
            case 39: {
                break;
            }
            case 35: {
                return -1;
            }
            default: {
                this.buf.append(char1);
                break;
            }
        }
        int[] array = null;
        while (i < this.line.length()) {
            final char char2 = this.line.charAt(i);
            if (char2 == '\\') {
                if (array == null) {
                    array = new int[] { 0 };
                }
                array[0] = i + 1;
                final int unescape = Utility.unescapeAt(this.line, array);
                if (unescape < 0) {
                    throw new RuntimeException("Invalid escape at " + this.reader.describePosition() + ':' + i);
                }
                UTF16.append(this.buf, unescape);
                i = array[0];
            }
            else {
                if ((false && char2 == '\0') || (!false && PatternProps.isWhiteSpace(char2))) {
                    return ++i;
                }
                if (!false && char2 == '#') {
                    return i;
                }
                this.buf.append(char2);
                ++i;
            }
        }
        if (false) {
            throw new RuntimeException("Unterminated quote at " + this.reader.describePosition() + ':' + n);
        }
        return i;
    }
}
