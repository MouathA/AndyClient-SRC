package com.google.common.base;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
final class Absent extends Optional
{
    static final Absent INSTANCE;
    private static final long serialVersionUID = 0L;
    
    static Optional withType() {
        return Absent.INSTANCE;
    }
    
    private Absent() {
    }
    
    @Override
    public boolean isPresent() {
        return false;
    }
    
    @Override
    public Object get() {
        throw new IllegalStateException("Optional.get() cannot be called on an absent value");
    }
    
    @Override
    public Object or(final Object o) {
        return Preconditions.checkNotNull(o, (Object)"use Optional.orNull() instead of Optional.or(null)");
    }
    
    @Override
    public Optional or(final Optional optional) {
        return (Optional)Preconditions.checkNotNull(optional);
    }
    
    @Override
    public Object or(final Supplier supplier) {
        return Preconditions.checkNotNull(supplier.get(), (Object)"use Optional.orNull() instead of a Supplier that returns null");
    }
    
    @Nullable
    @Override
    public Object orNull() {
        return null;
    }
    
    @Override
    public Set asSet() {
        return Collections.emptySet();
    }
    
    @Override
    public Optional transform(final Function function) {
        Preconditions.checkNotNull(function);
        return Optional.absent();
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o == this;
    }
    
    @Override
    public int hashCode() {
        return 1502476572;
    }
    
    @Override
    public String toString() {
        return "Optional.absent()";
    }
    
    private Object readResolve() {
        return Absent.INSTANCE;
    }
    
    static {
        INSTANCE = new Absent();
    }
}
