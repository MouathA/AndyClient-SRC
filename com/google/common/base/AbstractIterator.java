package com.google.common.base;

import java.util.*;
import com.google.common.annotations.*;

@GwtCompatible
abstract class AbstractIterator implements Iterator
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
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_acmpeq       12
        //     4: new             Ljava/util/NoSuchElementException;
        //     7: dup            
        //     8: invokespecial   java/util/NoSuchElementException.<init>:()V
        //    11: athrow         
        //    12: aload_0        
        //    13: getstatic       com/google/common/base/AbstractIterator$State.NOT_READY:Lcom/google/common/base/AbstractIterator$State;
        //    16: putfield        com/google/common/base/AbstractIterator.state:Lcom/google/common/base/AbstractIterator$State;
        //    19: aload_0        
        //    20: getfield        com/google/common/base/AbstractIterator.next:Ljava/lang/Object;
        //    23: astore_1       
        //    24: aload_0        
        //    25: aconst_null    
        //    26: putfield        com/google/common/base/AbstractIterator.next:Ljava/lang/Object;
        //    29: aload_1        
        //    30: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public final void remove() {
        throw new UnsupportedOperationException();
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
