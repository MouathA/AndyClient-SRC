package org.apache.commons.lang3.concurrent;

import java.util.concurrent.atomic.*;

public abstract class AtomicInitializer implements ConcurrentInitializer
{
    private final AtomicReference reference;
    
    public AtomicInitializer() {
        this.reference = new AtomicReference();
    }
    
    @Override
    public Object get() throws ConcurrentException {
        Object o = this.reference.get();
        if (o == null) {
            o = this.initialize();
            if (!this.reference.compareAndSet(null, o)) {
                o = this.reference.get();
            }
        }
        return o;
    }
    
    protected abstract Object initialize() throws ConcurrentException;
}
