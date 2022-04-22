package com.ibm.icu.text;

public class BidiRun
{
    int start;
    int limit;
    int insertRemove;
    byte level;
    
    BidiRun() {
        this(0, 0, (byte)0);
    }
    
    BidiRun(final int start, final int limit, final byte level) {
        this.start = start;
        this.limit = limit;
        this.level = level;
    }
    
    void copyFrom(final BidiRun bidiRun) {
        this.start = bidiRun.start;
        this.limit = bidiRun.limit;
        this.level = bidiRun.level;
        this.insertRemove = bidiRun.insertRemove;
    }
    
    public int getStart() {
        return this.start;
    }
    
    public int getLimit() {
        return this.limit;
    }
    
    public int getLength() {
        return this.limit - this.start;
    }
    
    public byte getEmbeddingLevel() {
        return this.level;
    }
    
    public boolean isOddRun() {
        return (this.level & 0x1) == 0x1;
    }
    
    public boolean isEvenRun() {
        return (this.level & 0x1) == 0x0;
    }
    
    public byte getDirection() {
        return (byte)(this.level & 0x1);
    }
    
    @Override
    public String toString() {
        return "BidiRun " + this.start + " - " + this.limit + " @ " + this.level;
    }
}
