package com.google.common.base;

import javax.annotation.*;
import com.google.common.annotations.*;
import java.io.*;

@GwtCompatible
public abstract class Equivalence
{
    protected Equivalence() {
    }
    
    public final boolean equivalent(@Nullable final Object o, @Nullable final Object o2) {
        return o == o2 || (o != null && o2 != null && this.doEquivalent(o, o2));
    }
    
    protected abstract boolean doEquivalent(final Object p0, final Object p1);
    
    public final int hash(@Nullable final Object o) {
        if (o == null) {
            return 0;
        }
        return this.doHash(o);
    }
    
    protected abstract int doHash(final Object p0);
    
    public final Equivalence onResultOf(final Function function) {
        return new FunctionalEquivalence(function, this);
    }
    
    public final Wrapper wrap(@Nullable final Object o) {
        return new Wrapper(this, o, null);
    }
    
    @GwtCompatible(serializable = true)
    public final Equivalence pairwise() {
        return new PairwiseEquivalence(this);
    }
    
    @Beta
    public final Predicate equivalentTo(@Nullable final Object o) {
        return new EquivalentToPredicate(this, o);
    }
    
    public static Equivalence equals() {
        return Equals.INSTANCE;
    }
    
    public static Equivalence identity() {
        return Identity.INSTANCE;
    }
    
    static final class Identity extends Equivalence implements Serializable
    {
        static final Identity INSTANCE;
        private static final long serialVersionUID = 1L;
        
        @Override
        protected boolean doEquivalent(final Object o, final Object o2) {
            return false;
        }
        
        @Override
        protected int doHash(final Object o) {
            return System.identityHashCode(o);
        }
        
        private Object readResolve() {
            return Identity.INSTANCE;
        }
        
        static {
            INSTANCE = new Identity();
        }
    }
    
    static final class Equals extends Equivalence implements Serializable
    {
        static final Equals INSTANCE;
        private static final long serialVersionUID = 1L;
        
        @Override
        protected boolean doEquivalent(final Object o, final Object o2) {
            return o.equals(o2);
        }
        
        public int doHash(final Object o) {
            return o.hashCode();
        }
        
        private Object readResolve() {
            return Equals.INSTANCE;
        }
        
        static {
            INSTANCE = new Equals();
        }
    }
    
    private static final class EquivalentToPredicate implements Predicate, Serializable
    {
        private final Equivalence equivalence;
        @Nullable
        private final Object target;
        private static final long serialVersionUID = 0L;
        
        EquivalentToPredicate(final Equivalence equivalence, @Nullable final Object target) {
            this.equivalence = (Equivalence)Preconditions.checkNotNull(equivalence);
            this.target = target;
        }
        
        @Override
        public boolean apply(@Nullable final Object o) {
            return this.equivalence.equivalent(o, this.target);
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof EquivalentToPredicate) {
                final EquivalentToPredicate equivalentToPredicate = (EquivalentToPredicate)o;
                return this.equivalence.equals(equivalentToPredicate.equivalence) && Objects.equal(this.target, equivalentToPredicate.target);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return Objects.hashCode(this.equivalence, this.target);
        }
        
        @Override
        public String toString() {
            return this.equivalence + ".equivalentTo(" + this.target + ")";
        }
    }
    
    public static final class Wrapper implements Serializable
    {
        private final Equivalence equivalence;
        @Nullable
        private final Object reference;
        private static final long serialVersionUID = 0L;
        
        private Wrapper(final Equivalence equivalence, @Nullable final Object reference) {
            this.equivalence = (Equivalence)Preconditions.checkNotNull(equivalence);
            this.reference = reference;
        }
        
        @Nullable
        public Object get() {
            return this.reference;
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof Wrapper) {
                final Wrapper wrapper = (Wrapper)o;
                if (this.equivalence.equals(wrapper.equivalence)) {
                    return this.equivalence.equivalent(this.reference, wrapper.reference);
                }
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return this.equivalence.hash(this.reference);
        }
        
        @Override
        public String toString() {
            return this.equivalence + ".wrap(" + this.reference + ")";
        }
        
        Wrapper(final Equivalence equivalence, final Object o, final Equivalence$1 object) {
            this(equivalence, o);
        }
    }
}
