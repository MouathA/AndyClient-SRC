package com.google.common.base;

import javax.annotation.*;
import com.google.common.annotations.*;
import java.util.regex.*;
import java.util.*;
import java.io.*;

@GwtCompatible(emulated = true)
public final class Predicates
{
    private static final Joiner COMMA_JOINER;
    
    private Predicates() {
    }
    
    @GwtCompatible(serializable = true)
    public static Predicate alwaysTrue() {
        return ObjectPredicate.ALWAYS_TRUE.withNarrowedType();
    }
    
    @GwtCompatible(serializable = true)
    public static Predicate alwaysFalse() {
        return ObjectPredicate.ALWAYS_FALSE.withNarrowedType();
    }
    
    @GwtCompatible(serializable = true)
    public static Predicate isNull() {
        return ObjectPredicate.IS_NULL.withNarrowedType();
    }
    
    @GwtCompatible(serializable = true)
    public static Predicate notNull() {
        return ObjectPredicate.NOT_NULL.withNarrowedType();
    }
    
    public static Predicate not(final Predicate predicate) {
        return new NotPredicate(predicate);
    }
    
    public static Predicate and(final Iterable iterable) {
        return new AndPredicate(defensiveCopy(iterable), null);
    }
    
    public static Predicate and(final Predicate... array) {
        return new AndPredicate(defensiveCopy((Object[])array), null);
    }
    
    public static Predicate and(final Predicate predicate, final Predicate predicate2) {
        return new AndPredicate(asList((Predicate)Preconditions.checkNotNull(predicate), (Predicate)Preconditions.checkNotNull(predicate2)), null);
    }
    
    public static Predicate or(final Iterable iterable) {
        return new OrPredicate(defensiveCopy(iterable), null);
    }
    
    public static Predicate or(final Predicate... array) {
        return new OrPredicate(defensiveCopy((Object[])array), null);
    }
    
    public static Predicate or(final Predicate predicate, final Predicate predicate2) {
        return new OrPredicate(asList((Predicate)Preconditions.checkNotNull(predicate), (Predicate)Preconditions.checkNotNull(predicate2)), null);
    }
    
    public static Predicate equalTo(@Nullable final Object o) {
        return (o == null) ? isNull() : new IsEqualToPredicate(o, null);
    }
    
    @GwtIncompatible("Class.isInstance")
    public static Predicate instanceOf(final Class clazz) {
        return new InstanceOfPredicate(clazz, null);
    }
    
    @GwtIncompatible("Class.isAssignableFrom")
    @Beta
    public static Predicate assignableFrom(final Class clazz) {
        return new AssignableFromPredicate(clazz, null);
    }
    
    public static Predicate in(final Collection collection) {
        return new InPredicate(collection, null);
    }
    
    public static Predicate compose(final Predicate predicate, final Function function) {
        return new CompositionPredicate(predicate, function, null);
    }
    
    @GwtIncompatible("java.util.regex.Pattern")
    public static Predicate containsPattern(final String s) {
        return new ContainsPatternFromStringPredicate(s);
    }
    
    @GwtIncompatible("java.util.regex.Pattern")
    public static Predicate contains(final Pattern pattern) {
        return new ContainsPatternPredicate(pattern);
    }
    
    private static List asList(final Predicate predicate, final Predicate predicate2) {
        return Arrays.asList(predicate, predicate2);
    }
    
    private static List defensiveCopy(final Object... array) {
        return defensiveCopy(Arrays.asList(array));
    }
    
    static List defensiveCopy(final Iterable iterable) {
        final ArrayList<Object> list = new ArrayList<Object>();
        final Iterator<Object> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            list.add(Preconditions.checkNotNull(iterator.next()));
        }
        return list;
    }
    
    static Joiner access$800() {
        return Predicates.COMMA_JOINER;
    }
    
    static {
        COMMA_JOINER = Joiner.on(',');
    }
    
    @GwtIncompatible("Only used by other GWT-incompatible code.")
    private static class ContainsPatternFromStringPredicate extends ContainsPatternPredicate
    {
        private static final long serialVersionUID = 0L;
        
        ContainsPatternFromStringPredicate(final String s) {
            super(Pattern.compile(s));
        }
        
        @Override
        public String toString() {
            return "Predicates.containsPattern(" + this.pattern.pattern() + ")";
        }
    }
    
    @GwtIncompatible("Only used by other GWT-incompatible code.")
    private static class ContainsPatternPredicate implements Predicate, Serializable
    {
        final Pattern pattern;
        private static final long serialVersionUID = 0L;
        
        ContainsPatternPredicate(final Pattern pattern) {
            this.pattern = (Pattern)Preconditions.checkNotNull(pattern);
        }
        
        public boolean apply(final CharSequence charSequence) {
            return this.pattern.matcher(charSequence).find();
        }
        
        @Override
        public int hashCode() {
            return Objects.hashCode(this.pattern.pattern(), this.pattern.flags());
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            if (o instanceof ContainsPatternPredicate) {
                final ContainsPatternPredicate containsPatternPredicate = (ContainsPatternPredicate)o;
                return Objects.equal(this.pattern.pattern(), containsPatternPredicate.pattern.pattern()) && Objects.equal(this.pattern.flags(), containsPatternPredicate.pattern.flags());
            }
            return false;
        }
        
        @Override
        public String toString() {
            return "Predicates.contains(" + Objects.toStringHelper(this.pattern).add("pattern", this.pattern.pattern()).add("pattern.flags", this.pattern.flags()).toString() + ")";
        }
        
        @Override
        public boolean apply(final Object o) {
            return this.apply((CharSequence)o);
        }
    }
    
    private static class CompositionPredicate implements Predicate, Serializable
    {
        final Predicate p;
        final Function f;
        private static final long serialVersionUID = 0L;
        
        private CompositionPredicate(final Predicate predicate, final Function function) {
            this.p = (Predicate)Preconditions.checkNotNull(predicate);
            this.f = (Function)Preconditions.checkNotNull(function);
        }
        
        @Override
        public boolean apply(@Nullable final Object o) {
            return this.p.apply(this.f.apply(o));
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            if (o instanceof CompositionPredicate) {
                final CompositionPredicate compositionPredicate = (CompositionPredicate)o;
                return this.f.equals(compositionPredicate.f) && this.p.equals(compositionPredicate.p);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return this.f.hashCode() ^ this.p.hashCode();
        }
        
        @Override
        public String toString() {
            return this.p.toString() + "(" + this.f.toString() + ")";
        }
        
        CompositionPredicate(final Predicate predicate, final Function function, final Predicates$1 object) {
            this(predicate, function);
        }
    }
    
    private static class InPredicate implements Predicate, Serializable
    {
        private final Collection target;
        private static final long serialVersionUID = 0L;
        
        private InPredicate(final Collection collection) {
            this.target = (Collection)Preconditions.checkNotNull(collection);
        }
        
        @Override
        public boolean apply(@Nullable final Object o) {
            return this.target.contains(o);
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof InPredicate && this.target.equals(((InPredicate)o).target);
        }
        
        @Override
        public int hashCode() {
            return this.target.hashCode();
        }
        
        @Override
        public String toString() {
            return "Predicates.in(" + this.target + ")";
        }
        
        InPredicate(final Collection collection, final Predicates$1 object) {
            this(collection);
        }
    }
    
    @GwtIncompatible("Class.isAssignableFrom")
    private static class AssignableFromPredicate implements Predicate, Serializable
    {
        private final Class clazz;
        private static final long serialVersionUID = 0L;
        
        private AssignableFromPredicate(final Class clazz) {
            this.clazz = (Class)Preconditions.checkNotNull(clazz);
        }
        
        public boolean apply(final Class clazz) {
            return this.clazz.isAssignableFrom(clazz);
        }
        
        @Override
        public int hashCode() {
            return this.clazz.hashCode();
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof AssignableFromPredicate && this.clazz == ((AssignableFromPredicate)o).clazz;
        }
        
        @Override
        public String toString() {
            return "Predicates.assignableFrom(" + this.clazz.getName() + ")";
        }
        
        @Override
        public boolean apply(final Object o) {
            return this.apply((Class)o);
        }
        
        AssignableFromPredicate(final Class clazz, final Predicates$1 object) {
            this(clazz);
        }
    }
    
    @GwtIncompatible("Class.isInstance")
    private static class InstanceOfPredicate implements Predicate, Serializable
    {
        private final Class clazz;
        private static final long serialVersionUID = 0L;
        
        private InstanceOfPredicate(final Class clazz) {
            this.clazz = (Class)Preconditions.checkNotNull(clazz);
        }
        
        @Override
        public boolean apply(@Nullable final Object o) {
            return this.clazz.isInstance(o);
        }
        
        @Override
        public int hashCode() {
            return this.clazz.hashCode();
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof InstanceOfPredicate && this.clazz == ((InstanceOfPredicate)o).clazz;
        }
        
        @Override
        public String toString() {
            return "Predicates.instanceOf(" + this.clazz.getName() + ")";
        }
        
        InstanceOfPredicate(final Class clazz, final Predicates$1 object) {
            this(clazz);
        }
    }
    
    private static class IsEqualToPredicate implements Predicate, Serializable
    {
        private final Object target;
        private static final long serialVersionUID = 0L;
        
        private IsEqualToPredicate(final Object target) {
            this.target = target;
        }
        
        @Override
        public boolean apply(final Object o) {
            return this.target.equals(o);
        }
        
        @Override
        public int hashCode() {
            return this.target.hashCode();
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof IsEqualToPredicate && this.target.equals(((IsEqualToPredicate)o).target);
        }
        
        @Override
        public String toString() {
            return "Predicates.equalTo(" + this.target + ")";
        }
        
        IsEqualToPredicate(final Object o, final Predicates$1 object) {
            this(o);
        }
    }
    
    private static class OrPredicate implements Predicate, Serializable
    {
        private final List components;
        private static final long serialVersionUID = 0L;
        
        private OrPredicate(final List components) {
            this.components = components;
        }
        
        @Override
        public boolean apply(@Nullable final Object o) {
            while (0 < this.components.size()) {
                if (this.components.get(0).apply(o)) {
                    return true;
                }
                int n = 0;
                ++n;
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return this.components.hashCode() + 87855567;
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof OrPredicate && this.components.equals(((OrPredicate)o).components);
        }
        
        @Override
        public String toString() {
            return "Predicates.or(" + Predicates.access$800().join(this.components) + ")";
        }
        
        OrPredicate(final List list, final Predicates$1 object) {
            this(list);
        }
    }
    
    private static class AndPredicate implements Predicate, Serializable
    {
        private final List components;
        private static final long serialVersionUID = 0L;
        
        private AndPredicate(final List components) {
            this.components = components;
        }
        
        @Override
        public boolean apply(@Nullable final Object o) {
            while (0 < this.components.size()) {
                if (!this.components.get(0).apply(o)) {
                    return false;
                }
                int n = 0;
                ++n;
            }
            return true;
        }
        
        @Override
        public int hashCode() {
            return this.components.hashCode() + 306654252;
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof AndPredicate && this.components.equals(((AndPredicate)o).components);
        }
        
        @Override
        public String toString() {
            return "Predicates.and(" + Predicates.access$800().join(this.components) + ")";
        }
        
        AndPredicate(final List list, final Predicates$1 object) {
            this(list);
        }
    }
    
    private static class NotPredicate implements Predicate, Serializable
    {
        final Predicate predicate;
        private static final long serialVersionUID = 0L;
        
        NotPredicate(final Predicate predicate) {
            this.predicate = (Predicate)Preconditions.checkNotNull(predicate);
        }
        
        @Override
        public boolean apply(@Nullable final Object o) {
            return !this.predicate.apply(o);
        }
        
        @Override
        public int hashCode() {
            return ~this.predicate.hashCode();
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof NotPredicate && this.predicate.equals(((NotPredicate)o).predicate);
        }
        
        @Override
        public String toString() {
            return "Predicates.not(" + this.predicate.toString() + ")";
        }
    }
    
    enum ObjectPredicate implements Predicate
    {
        ALWAYS_TRUE {
            @Override
            public boolean apply(@Nullable final Object o) {
                return true;
            }
            
            @Override
            public String toString() {
                return "Predicates.alwaysTrue()";
            }
        }, 
        ALWAYS_FALSE {
            @Override
            public boolean apply(@Nullable final Object o) {
                return false;
            }
            
            @Override
            public String toString() {
                return "Predicates.alwaysFalse()";
            }
        }, 
        IS_NULL {
            @Override
            public boolean apply(@Nullable final Object o) {
                return o == null;
            }
            
            @Override
            public String toString() {
                return "Predicates.isNull()";
            }
        }, 
        NOT_NULL {
            @Override
            public boolean apply(@Nullable final Object o) {
                return o != null;
            }
            
            @Override
            public String toString() {
                return "Predicates.notNull()";
            }
        };
        
        private static final ObjectPredicate[] $VALUES;
        
        private ObjectPredicate(final String s, final int n) {
        }
        
        Predicate withNarrowedType() {
            return this;
        }
        
        ObjectPredicate(final String s, final int n, final Predicates$1 object) {
            this(s, n);
        }
        
        static {
            $VALUES = new ObjectPredicate[] { ObjectPredicate.ALWAYS_TRUE, ObjectPredicate.ALWAYS_FALSE, ObjectPredicate.IS_NULL, ObjectPredicate.NOT_NULL };
        }
    }
}
