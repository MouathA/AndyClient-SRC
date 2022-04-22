package org.apache.commons.lang3.concurrent;

import java.util.concurrent.atomic.*;

public abstract class AtomicSafeInitializer implements ConcurrentInitializer
{
    private final AtomicReference factory;
    private final AtomicReference reference;
    
    public AtomicSafeInitializer() {
        this.factory = new AtomicReference();
        this.reference = new AtomicReference();
    }
    
    @Override
    public final Object get() throws ConcurrentException {
        Object value;
        while ((value = this.reference.get()) == null) {
            if (this.factory.compareAndSet(null, this)) {
                this.reference.set(this.initialize());
            }
        }
        return value;
    }
    
    protected abstract Object initialize() throws ConcurrentException;
}
