package com.google.common.base;

import java.util.*;
import javax.annotation.*;
import com.google.common.annotations.*;
import java.io.*;

@GwtCompatible
public final class Functions
{
    private Functions() {
    }
    
    public static Function toStringFunction() {
        return ToStringFunction.INSTANCE;
    }
    
    public static Function identity() {
        return IdentityFunction.INSTANCE;
    }
    
    public static Function forMap(final Map map) {
        return new FunctionForMapNoDefault(map);
    }
    
    public static Function forMap(final Map map, @Nullable final Object o) {
        return new ForMapWithDefault(map, o);
    }
    
    public static Function compose(final Function function, final Function function2) {
        return new FunctionComposition(function, function2);
    }
    
    public static Function forPredicate(final Predicate predicate) {
        return new PredicateFunction(predicate, null);
    }
    
    public static Function constant(@Nullable final Object o) {
        return new ConstantFunction(o);
    }
    
    @Beta
    public static Function forSupplier(final Supplier supplier) {
        return new SupplierFunction(supplier, null);
    }
    
    private static class SupplierFunction implements Function, Serializable
    {
        private final Supplier supplier;
        private static final long serialVersionUID = 0L;
        
        private SupplierFunction(final Supplier supplier) {
            this.supplier = (Supplier)Preconditions.checkNotNull(supplier);
        }
        
        @Override
        public Object apply(@Nullable final Object o) {
            return this.supplier.get();
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof SupplierFunction && this.supplier.equals(((SupplierFunction)o).supplier);
        }
        
        @Override
        public int hashCode() {
            return this.supplier.hashCode();
        }
        
        @Override
        public String toString() {
            return "forSupplier(" + this.supplier + ")";
        }
        
        SupplierFunction(final Supplier supplier, final Functions$1 object) {
            this(supplier);
        }
    }
    
    private static class ConstantFunction implements Function, Serializable
    {
        private final Object value;
        private static final long serialVersionUID = 0L;
        
        public ConstantFunction(@Nullable final Object value) {
            this.value = value;
        }
        
        @Override
        public Object apply(@Nullable final Object o) {
            return this.value;
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof ConstantFunction && Objects.equal(this.value, ((ConstantFunction)o).value);
        }
        
        @Override
        public int hashCode() {
            return (this.value == null) ? 0 : this.value.hashCode();
        }
        
        @Override
        public String toString() {
            return "constant(" + this.value + ")";
        }
    }
    
    private static class PredicateFunction implements Function, Serializable
    {
        private final Predicate predicate;
        private static final long serialVersionUID = 0L;
        
        private PredicateFunction(final Predicate predicate) {
            this.predicate = (Predicate)Preconditions.checkNotNull(predicate);
        }
        
        @Override
        public Boolean apply(@Nullable final Object o) {
            return this.predicate.apply(o);
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof PredicateFunction && this.predicate.equals(((PredicateFunction)o).predicate);
        }
        
        @Override
        public int hashCode() {
            return this.predicate.hashCode();
        }
        
        @Override
        public String toString() {
            return "forPredicate(" + this.predicate + ")";
        }
        
        @Override
        public Object apply(final Object o) {
            return this.apply(o);
        }
        
        PredicateFunction(final Predicate predicate, final Functions$1 object) {
            this(predicate);
        }
    }
    
    private static class FunctionComposition implements Function, Serializable
    {
        private final Function g;
        private final Function f;
        private static final long serialVersionUID = 0L;
        
        public FunctionComposition(final Function function, final Function function2) {
            this.g = (Function)Preconditions.checkNotNull(function);
            this.f = (Function)Preconditions.checkNotNull(function2);
        }
        
        @Override
        public Object apply(@Nullable final Object o) {
            return this.g.apply(this.f.apply(o));
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            if (o instanceof FunctionComposition) {
                final FunctionComposition functionComposition = (FunctionComposition)o;
                return this.f.equals(functionComposition.f) && this.g.equals(functionComposition.g);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return this.f.hashCode() ^ this.g.hashCode();
        }
        
        @Override
        public String toString() {
            return this.g + "(" + this.f + ")";
        }
    }
    
    private static class ForMapWithDefault implements Function, Serializable
    {
        final Map map;
        final Object defaultValue;
        private static final long serialVersionUID = 0L;
        
        ForMapWithDefault(final Map map, @Nullable final Object defaultValue) {
            this.map = (Map)Preconditions.checkNotNull(map);
            this.defaultValue = defaultValue;
        }
        
        @Override
        public Object apply(@Nullable final Object o) {
            final Object value = this.map.get(o);
            return (value != null || this.map.containsKey(o)) ? value : this.defaultValue;
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            if (o instanceof ForMapWithDefault) {
                final ForMapWithDefault forMapWithDefault = (ForMapWithDefault)o;
                return this.map.equals(forMapWithDefault.map) && Objects.equal(this.defaultValue, forMapWithDefault.defaultValue);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return Objects.hashCode(this.map, this.defaultValue);
        }
        
        @Override
        public String toString() {
            return "forMap(" + this.map + ", defaultValue=" + this.defaultValue + ")";
        }
    }
    
    private static class FunctionForMapNoDefault implements Function, Serializable
    {
        final Map map;
        private static final long serialVersionUID = 0L;
        
        FunctionForMapNoDefault(final Map map) {
            this.map = (Map)Preconditions.checkNotNull(map);
        }
        
        @Override
        public Object apply(@Nullable final Object o) {
            final Object value = this.map.get(o);
            Preconditions.checkArgument(value != null || this.map.containsKey(o), "Key '%s' not present in map", o);
            return value;
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof FunctionForMapNoDefault && this.map.equals(((FunctionForMapNoDefault)o).map);
        }
        
        @Override
        public int hashCode() {
            return this.map.hashCode();
        }
        
        @Override
        public String toString() {
            return "forMap(" + this.map + ")";
        }
    }
    
    private enum IdentityFunction implements Function
    {
        INSTANCE("INSTANCE", 0);
        
        private static final IdentityFunction[] $VALUES;
        
        private IdentityFunction(final String s, final int n) {
        }
        
        @Nullable
        @Override
        public Object apply(@Nullable final Object o) {
            return o;
        }
        
        @Override
        public String toString() {
            return "identity";
        }
        
        static {
            $VALUES = new IdentityFunction[] { IdentityFunction.INSTANCE };
        }
    }
    
    private enum ToStringFunction implements Function
    {
        INSTANCE("INSTANCE", 0);
        
        private static final ToStringFunction[] $VALUES;
        
        private ToStringFunction(final String s, final int n) {
        }
        
        @Override
        public String apply(final Object o) {
            Preconditions.checkNotNull(o);
            return o.toString();
        }
        
        @Override
        public String toString() {
            return "toString";
        }
        
        @Override
        public Object apply(final Object o) {
            return this.apply(o);
        }
        
        static {
            $VALUES = new ToStringFunction[] { ToStringFunction.INSTANCE };
        }
    }
}
