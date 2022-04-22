package com.google.common.base;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;
import java.io.*;

@Beta
@GwtCompatible
public abstract class Converter implements Function
{
    private final boolean handleNullAutomatically;
    private transient Converter reverse;
    
    protected Converter() {
        this(true);
    }
    
    Converter(final boolean handleNullAutomatically) {
        this.handleNullAutomatically = handleNullAutomatically;
    }
    
    protected abstract Object doForward(final Object p0);
    
    protected abstract Object doBackward(final Object p0);
    
    @Nullable
    public final Object convert(@Nullable final Object o) {
        return this.correctedDoForward(o);
    }
    
    @Nullable
    Object correctedDoForward(@Nullable final Object o) {
        if (this.handleNullAutomatically) {
            return (o == null) ? null : Preconditions.checkNotNull(this.doForward(o));
        }
        return this.doForward(o);
    }
    
    @Nullable
    Object correctedDoBackward(@Nullable final Object o) {
        if (this.handleNullAutomatically) {
            return (o == null) ? null : Preconditions.checkNotNull(this.doBackward(o));
        }
        return this.doBackward(o);
    }
    
    public Iterable convertAll(final Iterable iterable) {
        Preconditions.checkNotNull(iterable, (Object)"fromIterable");
        return new Iterable(iterable) {
            final Iterable val$fromIterable;
            final Converter this$0;
            
            @Override
            public Iterator iterator() {
                return new Iterator() {
                    private final Iterator fromIterator = this.this$1.val$fromIterable.iterator();
                    final Converter$1 this$1;
                    
                    @Override
                    public boolean hasNext() {
                        return this.fromIterator.hasNext();
                    }
                    
                    @Override
                    public Object next() {
                        return this.this$1.this$0.convert(this.fromIterator.next());
                    }
                    
                    @Override
                    public void remove() {
                        this.fromIterator.remove();
                    }
                };
            }
        };
    }
    
    public Converter reverse() {
        final Converter reverse = this.reverse;
        return (reverse == null) ? (this.reverse = new ReverseConverter(this)) : reverse;
    }
    
    public Converter andThen(final Converter converter) {
        return new ConverterComposition(this, (Converter)Preconditions.checkNotNull(converter));
    }
    
    @Deprecated
    @Nullable
    @Override
    public final Object apply(@Nullable final Object o) {
        return this.convert(o);
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return super.equals(o);
    }
    
    public static Converter from(final Function function, final Function function2) {
        return new FunctionBasedConverter(function, function2, null);
    }
    
    public static Converter identity() {
        return IdentityConverter.INSTANCE;
    }
    
    private static final class IdentityConverter extends Converter implements Serializable
    {
        static final IdentityConverter INSTANCE;
        private static final long serialVersionUID = 0L;
        
        @Override
        protected Object doForward(final Object o) {
            return o;
        }
        
        @Override
        protected Object doBackward(final Object o) {
            return o;
        }
        
        @Override
        public IdentityConverter reverse() {
            return this;
        }
        
        @Override
        public Converter andThen(final Converter converter) {
            return (Converter)Preconditions.checkNotNull(converter, (Object)"otherConverter");
        }
        
        @Override
        public String toString() {
            return "Converter.identity()";
        }
        
        private Object readResolve() {
            return IdentityConverter.INSTANCE;
        }
        
        @Override
        public Converter reverse() {
            return this.reverse();
        }
        
        static {
            INSTANCE = new IdentityConverter();
        }
    }
    
    private static final class FunctionBasedConverter extends Converter implements Serializable
    {
        private final Function forwardFunction;
        private final Function backwardFunction;
        
        private FunctionBasedConverter(final Function function, final Function function2) {
            this.forwardFunction = (Function)Preconditions.checkNotNull(function);
            this.backwardFunction = (Function)Preconditions.checkNotNull(function2);
        }
        
        @Override
        protected Object doForward(final Object o) {
            return this.forwardFunction.apply(o);
        }
        
        @Override
        protected Object doBackward(final Object o) {
            return this.backwardFunction.apply(o);
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            if (o instanceof FunctionBasedConverter) {
                final FunctionBasedConverter functionBasedConverter = (FunctionBasedConverter)o;
                return this.forwardFunction.equals(functionBasedConverter.forwardFunction) && this.backwardFunction.equals(functionBasedConverter.backwardFunction);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return this.forwardFunction.hashCode() * 31 + this.backwardFunction.hashCode();
        }
        
        @Override
        public String toString() {
            return "Converter.from(" + this.forwardFunction + ", " + this.backwardFunction + ")";
        }
        
        FunctionBasedConverter(final Function function, final Function function2, final Converter$1 iterable) {
            this(function, function2);
        }
    }
    
    private static final class ConverterComposition extends Converter implements Serializable
    {
        final Converter first;
        final Converter second;
        private static final long serialVersionUID = 0L;
        
        ConverterComposition(final Converter first, final Converter second) {
            this.first = first;
            this.second = second;
        }
        
        @Override
        protected Object doForward(final Object o) {
            throw new AssertionError();
        }
        
        @Override
        protected Object doBackward(final Object o) {
            throw new AssertionError();
        }
        
        @Nullable
        @Override
        Object correctedDoForward(@Nullable final Object o) {
            return this.second.correctedDoForward(this.first.correctedDoForward(o));
        }
        
        @Nullable
        @Override
        Object correctedDoBackward(@Nullable final Object o) {
            return this.first.correctedDoBackward(this.second.correctedDoBackward(o));
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            if (o instanceof ConverterComposition) {
                final ConverterComposition converterComposition = (ConverterComposition)o;
                return this.first.equals(converterComposition.first) && this.second.equals(converterComposition.second);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return 31 * this.first.hashCode() + this.second.hashCode();
        }
        
        @Override
        public String toString() {
            return this.first + ".andThen(" + this.second + ")";
        }
    }
    
    private static final class ReverseConverter extends Converter implements Serializable
    {
        final Converter original;
        private static final long serialVersionUID = 0L;
        
        ReverseConverter(final Converter original) {
            this.original = original;
        }
        
        @Override
        protected Object doForward(final Object o) {
            throw new AssertionError();
        }
        
        @Override
        protected Object doBackward(final Object o) {
            throw new AssertionError();
        }
        
        @Nullable
        @Override
        Object correctedDoForward(@Nullable final Object o) {
            return this.original.correctedDoBackward(o);
        }
        
        @Nullable
        @Override
        Object correctedDoBackward(@Nullable final Object o) {
            return this.original.correctedDoForward(o);
        }
        
        @Override
        public Converter reverse() {
            return this.original;
        }
        
        @Override
        public boolean equals(@Nullable final Object o) {
            return o instanceof ReverseConverter && this.original.equals(((ReverseConverter)o).original);
        }
        
        @Override
        public int hashCode() {
            return ~this.original.hashCode();
        }
        
        @Override
        public String toString() {
            return this.original + ".reverse()";
        }
    }
}
