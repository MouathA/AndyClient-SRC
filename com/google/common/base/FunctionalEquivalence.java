package com.google.common.base;

import java.io.*;
import com.google.common.annotations.*;
import javax.annotation.*;

@Beta
@GwtCompatible
final class FunctionalEquivalence extends Equivalence implements Serializable
{
    private static final long serialVersionUID = 0L;
    private final Function function;
    private final Equivalence resultEquivalence;
    
    FunctionalEquivalence(final Function function, final Equivalence equivalence) {
        this.function = (Function)Preconditions.checkNotNull(function);
        this.resultEquivalence = (Equivalence)Preconditions.checkNotNull(equivalence);
    }
    
    @Override
    protected boolean doEquivalent(final Object o, final Object o2) {
        return this.resultEquivalence.equivalent(this.function.apply(o), this.function.apply(o2));
    }
    
    @Override
    protected int doHash(final Object o) {
        return this.resultEquivalence.hash(this.function.apply(o));
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof FunctionalEquivalence) {
            final FunctionalEquivalence functionalEquivalence = (FunctionalEquivalence)o;
            return this.function.equals(functionalEquivalence.function) && this.resultEquivalence.equals(functionalEquivalence.resultEquivalence);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.function, this.resultEquivalence);
    }
    
    @Override
    public String toString() {
        return this.resultEquivalence + ".onResultOf(" + this.function + ")";
    }
}
