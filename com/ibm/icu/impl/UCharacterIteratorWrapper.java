package com.ibm.icu.impl;

import java.text.*;
import com.ibm.icu.text.*;

public class UCharacterIteratorWrapper implements CharacterIterator
{
    private UCharacterIterator iterator;
    
    public UCharacterIteratorWrapper(final UCharacterIterator iterator) {
        this.iterator = iterator;
    }
    
    public char first() {
        this.iterator.setToStart();
        return (char)this.iterator.current();
    }
    
    public char last() {
        this.iterator.setToLimit();
        return (char)this.iterator.previous();
    }
    
    public char current() {
        return (char)this.iterator.current();
    }
    
    public char next() {
        this.iterator.next();
        return (char)this.iterator.current();
    }
    
    public char previous() {
        return (char)this.iterator.previous();
    }
    
    public char setIndex(final int index) {
        this.iterator.setIndex(index);
        return (char)this.iterator.current();
    }
    
    public int getBeginIndex() {
        return 0;
    }
    
    public int getEndIndex() {
        return this.iterator.getLength();
    }
    
    public int getIndex() {
        return this.iterator.getIndex();
    }
    
    public Object clone() {
        final UCharacterIteratorWrapper uCharacterIteratorWrapper = (UCharacterIteratorWrapper)super.clone();
        uCharacterIteratorWrapper.iterator = (UCharacterIterator)this.iterator.clone();
        return uCharacterIteratorWrapper;
    }
}
