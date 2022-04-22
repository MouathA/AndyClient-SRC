package org.apache.commons.lang3.concurrent;

public abstract class LazyInitializer implements ConcurrentInitializer
{
    private Object object;
    
    @Override
    public Object get() throws ConcurrentException {
        Object o = this.object;
        if (o == null) {
            // monitorenter(this)
            o = this.object;
            if (o == null) {
                o = (this.object = this.initialize());
            }
        }
        // monitorexit(this)
        return o;
    }
    
    protected abstract Object initialize() throws ConcurrentException;
}
