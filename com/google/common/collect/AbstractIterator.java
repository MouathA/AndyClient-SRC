package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;

@GwtCompatible
public abstract class AbstractIterator extends UnmodifiableIterator
{
    private State state;
    private Object next;
    
    protected AbstractIterator() {
        this.state = State.NOT_READY;
    }
    
    protected abstract Object computeNext();
    
    protected final Object endOfData() {
        this.state = State.DONE;
        return null;
    }
    
    @Override
    public final boolean hasNext() {
        Preconditions.checkState(this.state != State.FAILED);
        switch (this.state) {
            case DONE: {
                return false;
            }
            case READY: {
                return true;
            }
            default: {
                return this.tryToComputeNext();
            }
        }
    }
    
    private boolean tryToComputeNext() {
        this.state = State.FAILED;
        this.next = this.computeNext();
        if (this.state != State.DONE) {
            this.state = State.READY;
            return true;
        }
        return false;
    }
    
    @Override
    public final Object next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        this.state = State.NOT_READY;
        final Object next = this.next;
        this.next = null;
        return next;
    }
    
    public final Object peek() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        return this.next;
    }
    
    private enum State
    {
        READY("READY", 0), 
        NOT_READY("NOT_READY", 1), 
        DONE("DONE", 2), 
        FAILED("FAILED", 3);
        
        private static final State[] $VALUES;
        
        private State(final String s, final int n) {
        }
        
        static {
            $VALUES = new State[] { State.READY, State.NOT_READY, State.DONE, State.FAILED };
        }
    }
}
