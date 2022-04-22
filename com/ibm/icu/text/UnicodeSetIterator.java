package com.ibm.icu.text;

import java.util.*;

public class UnicodeSetIterator
{
    public int codepoint;
    public int codepointEnd;
    public String string;
    private UnicodeSet set;
    private int endRange;
    private int range;
    @Deprecated
    protected int endElement;
    @Deprecated
    protected int nextElement;
    private Iterator stringIterator;
    
    public UnicodeSetIterator(final UnicodeSet set) {
        this.endRange = 0;
        this.range = 0;
        this.stringIterator = null;
        this.reset(set);
    }
    
    public UnicodeSetIterator() {
        this.endRange = 0;
        this.range = 0;
        this.stringIterator = null;
        this.reset(new UnicodeSet());
    }
    
    public boolean next() {
        if (this.nextElement <= this.endElement) {
            final int n = this.nextElement++;
            this.codepointEnd = n;
            this.codepoint = n;
            return true;
        }
        if (this.range < this.endRange) {
            this.loadRange(++this.range);
            final int n2 = this.nextElement++;
            this.codepointEnd = n2;
            this.codepoint = n2;
            return true;
        }
        if (this.stringIterator == null) {
            return false;
        }
        this.codepoint = -1;
        this.string = this.stringIterator.next();
        if (!this.stringIterator.hasNext()) {
            this.stringIterator = null;
        }
        return true;
    }
    
    public boolean nextRange() {
        if (this.nextElement <= this.endElement) {
            this.codepointEnd = this.endElement;
            this.codepoint = this.nextElement;
            this.nextElement = this.endElement + 1;
            return true;
        }
        if (this.range < this.endRange) {
            this.loadRange(++this.range);
            this.codepointEnd = this.endElement;
            this.codepoint = this.nextElement;
            this.nextElement = this.endElement + 1;
            return true;
        }
        if (this.stringIterator == null) {
            return false;
        }
        this.codepoint = -1;
        this.string = this.stringIterator.next();
        if (!this.stringIterator.hasNext()) {
            this.stringIterator = null;
        }
        return true;
    }
    
    public void reset(final UnicodeSet set) {
        this.set = set;
        this.reset();
    }
    
    public void reset() {
        this.endRange = this.set.getRangeCount() - 1;
        this.range = 0;
        this.endElement = -1;
        this.nextElement = 0;
        if (this.endRange >= 0) {
            this.loadRange(this.range);
        }
        this.stringIterator = null;
        if (this.set.strings != null) {
            this.stringIterator = this.set.strings.iterator();
            if (!this.stringIterator.hasNext()) {
                this.stringIterator = null;
            }
        }
    }
    
    public String getString() {
        if (this.codepoint != -1) {
            return UTF16.valueOf(this.codepoint);
        }
        return this.string;
    }
    
    @Deprecated
    public UnicodeSet getSet() {
        return this.set;
    }
    
    @Deprecated
    protected void loadRange(final int n) {
        this.nextElement = this.set.getRangeStart(n);
        this.endElement = this.set.getRangeEnd(n);
    }
}
