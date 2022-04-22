package org.apache.http.message;

import org.apache.http.annotation.*;

@NotThreadSafe
public class ParserCursor
{
    private final int lowerBound;
    private final int upperBound;
    private int pos;
    
    public ParserCursor(final int n, final int upperBound) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Lower bound cannot be negative");
        }
        if (n > upperBound) {
            throw new IndexOutOfBoundsException("Lower bound cannot be greater then upper bound");
        }
        this.lowerBound = n;
        this.upperBound = upperBound;
        this.pos = n;
    }
    
    public int getLowerBound() {
        return this.lowerBound;
    }
    
    public int getUpperBound() {
        return this.upperBound;
    }
    
    public int getPos() {
        return this.pos;
    }
    
    public void updatePos(final int pos) {
        if (pos < this.lowerBound) {
            throw new IndexOutOfBoundsException("pos: " + pos + " < lowerBound: " + this.lowerBound);
        }
        if (pos > this.upperBound) {
            throw new IndexOutOfBoundsException("pos: " + pos + " > upperBound: " + this.upperBound);
        }
        this.pos = pos;
    }
    
    public boolean atEnd() {
        return this.pos >= this.upperBound;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(Integer.toString(this.lowerBound));
        sb.append('>');
        sb.append(Integer.toString(this.pos));
        sb.append('>');
        sb.append(Integer.toString(this.upperBound));
        sb.append(']');
        return sb.toString();
    }
}
