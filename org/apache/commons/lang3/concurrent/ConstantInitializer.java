package org.apache.commons.lang3.concurrent;

import org.apache.commons.lang3.*;

public class ConstantInitializer implements ConcurrentInitializer
{
    private static final String FMT_TO_STRING = "ConstantInitializer@%d [ object = %s ]";
    private final Object object;
    
    public ConstantInitializer(final Object object) {
        this.object = object;
    }
    
    public final Object getObject() {
        return this.object;
    }
    
    @Override
    public Object get() throws ConcurrentException {
        return this.getObject();
    }
    
    @Override
    public int hashCode() {
        return (this.getObject() != null) ? this.getObject().hashCode() : 0;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof ConstantInitializer && ObjectUtils.equals(this.getObject(), ((ConstantInitializer)o).getObject()));
    }
    
    @Override
    public String toString() {
        return String.format("ConstantInitializer@%d [ object = %s ]", System.identityHashCode(this), String.valueOf(this.getObject()));
    }
}
