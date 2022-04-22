package com.google.common.base;

import com.google.common.annotations.*;
import java.util.*;
import javax.annotation.*;

@GwtCompatible
final class Present extends Optional
{
    private final Object reference;
    private static final long serialVersionUID = 0L;
    
    Present(final Object reference) {
        this.reference = reference;
    }
    
    @Override
    public boolean isPresent() {
        return true;
    }
    
    @Override
    public Object get() {
        return this.reference;
    }
    
    @Override
    public Object or(final Object o) {
        Preconditions.checkNotNull(o, (Object)"use Optional.orNull() instead of Optional.or(null)");
        return this.reference;
    }
    
    @Override
    public Optional or(final Optional optional) {
        Preconditions.checkNotNull(optional);
        return this;
    }
    
    @Override
    public Object or(final Supplier supplier) {
        Preconditions.checkNotNull(supplier);
        return this.reference;
    }
    
    @Override
    public Object orNull() {
        return this.reference;
    }
    
    @Override
    public Set asSet() {
        return Collections.singleton(this.reference);
    }
    
    @Override
    public Optional transform(final Function function) {
        return new Present(Preconditions.checkNotNull(function.apply(this.reference), (Object)"the Function passed to Optional.transform() must not return null."));
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o instanceof Present && this.reference.equals(((Present)o).reference);
    }
    
    @Override
    public int hashCode() {
        return 1502476572 + this.reference.hashCode();
    }
    
    @Override
    public String toString() {
        return "Optional.of(" + this.reference + ")";
    }
}
