package com.ibm.icu.text;

import com.ibm.icu.impl.*;

class ReplaceableContextIterator implements UCaseProps.ContextIterator
{
    protected Replaceable rep;
    protected int index;
    protected int limit;
    protected int cpStart;
    protected int cpLimit;
    protected int contextStart;
    protected int contextLimit;
    protected int dir;
    protected boolean reachedLimit;
    
    ReplaceableContextIterator() {
        this.rep = null;
        final int n = 0;
        this.contextLimit = n;
        this.contextStart = n;
        this.index = n;
        this.cpLimit = n;
        this.cpStart = n;
        this.limit = n;
        this.dir = 0;
        this.reachedLimit = false;
    }
    
    public void setText(final Replaceable rep) {
        this.rep = rep;
        final int length = rep.length();
        this.contextLimit = length;
        this.limit = length;
        final int n = 0;
        this.contextStart = n;
        this.index = n;
        this.cpLimit = n;
        this.cpStart = n;
        this.dir = 0;
        this.reachedLimit = false;
    }
    
    public void setIndex(final int n) {
        this.cpLimit = n;
        this.cpStart = n;
        this.index = 0;
        this.dir = 0;
        this.reachedLimit = false;
    }
    
    public int getCaseMapCPStart() {
        return this.cpStart;
    }
    
    public void setLimit(final int limit) {
        if (0 <= limit && limit <= this.rep.length()) {
            this.limit = limit;
        }
        else {
            this.limit = this.rep.length();
        }
        this.reachedLimit = false;
    }
    
    public void setContextLimits(final int contextStart, final int contextLimit) {
        if (contextStart < 0) {
            this.contextStart = 0;
        }
        else if (contextStart <= this.rep.length()) {
            this.contextStart = contextStart;
        }
        else {
            this.contextStart = this.rep.length();
        }
        if (contextLimit < this.contextStart) {
            this.contextLimit = this.contextStart;
        }
        else if (contextLimit <= this.rep.length()) {
            this.contextLimit = contextLimit;
        }
        else {
            this.contextLimit = this.rep.length();
        }
        this.reachedLimit = false;
    }
    
    public int nextCaseMapCP() {
        if (this.cpLimit < this.limit) {
            this.cpStart = this.cpLimit;
            final int char32At = this.rep.char32At(this.cpLimit);
            this.cpLimit += UTF16.getCharCount(char32At);
            return char32At;
        }
        return -1;
    }
    
    public int replace(final String s) {
        final int n = s.length() - (this.cpLimit - this.cpStart);
        this.rep.replace(this.cpStart, this.cpLimit, s);
        this.cpLimit += n;
        this.limit += n;
        this.contextLimit += n;
        return n;
    }
    
    public boolean didReachLimit() {
        return this.reachedLimit;
    }
    
    public void reset(final int n) {
        if (n > 0) {
            this.dir = 1;
            this.index = this.cpLimit;
        }
        else if (n < 0) {
            this.dir = -1;
            this.index = this.cpStart;
        }
        else {
            this.dir = 0;
            this.index = 0;
        }
        this.reachedLimit = false;
    }
    
    public int next() {
        if (this.dir > 0) {
            if (this.index < this.contextLimit) {
                final int char32At = this.rep.char32At(this.index);
                this.index += UTF16.getCharCount(char32At);
                return char32At;
            }
            this.reachedLimit = true;
        }
        else if (this.dir < 0 && this.index > this.contextStart) {
            final int char32At2 = this.rep.char32At(this.index - 1);
            this.index -= UTF16.getCharCount(char32At2);
            return char32At2;
        }
        return -1;
    }
}
