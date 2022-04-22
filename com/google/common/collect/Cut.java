package com.google.common.collect;

import java.io.*;
import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.primitives.*;
import com.google.common.base.*;

@GwtCompatible
abstract class Cut implements Comparable, Serializable
{
    final Comparable endpoint;
    private static final long serialVersionUID = 0L;
    
    Cut(@Nullable final Comparable endpoint) {
        this.endpoint = endpoint;
    }
    
    abstract boolean isLessThan(final Comparable p0);
    
    abstract BoundType typeAsLowerBound();
    
    abstract BoundType typeAsUpperBound();
    
    abstract Cut withLowerBoundType(final BoundType p0, final DiscreteDomain p1);
    
    abstract Cut withUpperBoundType(final BoundType p0, final DiscreteDomain p1);
    
    abstract void describeAsLowerBound(final StringBuilder p0);
    
    abstract void describeAsUpperBound(final StringBuilder p0);
    
    abstract Comparable leastValueAbove(final DiscreteDomain p0);
    
    abstract Comparable greatestValueBelow(final DiscreteDomain p0);
    
    Cut canonical(final DiscreteDomain discreteDomain) {
        return this;
    }
    
    public int compareTo(final Cut cut) {
        if (cut == belowAll()) {
            return 1;
        }
        if (cut == aboveAll()) {
            return -1;
        }
        final int compareOrThrow = Range.compareOrThrow(this.endpoint, cut.endpoint);
        if (compareOrThrow != 0) {
            return compareOrThrow;
        }
        return Booleans.compare(this instanceof AboveValue, cut instanceof AboveValue);
    }
    
    Comparable endpoint() {
        return this.endpoint;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Cut && this.compareTo((Cut)o) == 0;
    }
    
    static Cut belowAll() {
        return BelowAll.access$000();
    }
    
    static Cut aboveAll() {
        return AboveAll.access$100();
    }
    
    static Cut belowValue(final Comparable comparable) {
        return new BelowValue(comparable);
    }
    
    static Cut aboveValue(final Comparable comparable) {
        return new AboveValue(comparable);
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((Cut)o);
    }
    
    private static final class AboveValue extends Cut
    {
        private static final long serialVersionUID = 0L;
        
        AboveValue(final Comparable comparable) {
            super((Comparable)Preconditions.checkNotNull(comparable));
        }
        
        @Override
        boolean isLessThan(final Comparable comparable) {
            return Range.compareOrThrow(this.endpoint, comparable) < 0;
        }
        
        @Override
        BoundType typeAsLowerBound() {
            return BoundType.OPEN;
        }
        
        @Override
        BoundType typeAsUpperBound() {
            return BoundType.CLOSED;
        }
        
        @Override
        Cut withLowerBoundType(final BoundType boundType, final DiscreteDomain discreteDomain) {
            switch (boundType) {
                case OPEN: {
                    return this;
                }
                case CLOSED: {
                    final Comparable next = discreteDomain.next(this.endpoint);
                    return (next == null) ? Cut.belowAll() : Cut.belowValue(next);
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
        
        @Override
        Cut withUpperBoundType(final BoundType boundType, final DiscreteDomain discreteDomain) {
            switch (boundType) {
                case OPEN: {
                    final Comparable next = discreteDomain.next(this.endpoint);
                    return (next == null) ? Cut.aboveAll() : Cut.belowValue(next);
                }
                case CLOSED: {
                    return this;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
        
        @Override
        void describeAsLowerBound(final StringBuilder sb) {
            sb.append('(').append(this.endpoint);
        }
        
        @Override
        void describeAsUpperBound(final StringBuilder sb) {
            sb.append(this.endpoint).append(']');
        }
        
        @Override
        Comparable leastValueAbove(final DiscreteDomain discreteDomain) {
            return discreteDomain.next(this.endpoint);
        }
        
        @Override
        Comparable greatestValueBelow(final DiscreteDomain discreteDomain) {
            return this.endpoint;
        }
        
        @Override
        Cut canonical(final DiscreteDomain discreteDomain) {
            final Comparable leastValueAbove = this.leastValueAbove(discreteDomain);
            return (leastValueAbove != null) ? Cut.belowValue(leastValueAbove) : Cut.aboveAll();
        }
        
        @Override
        public int hashCode() {
            return ~this.endpoint.hashCode();
        }
        
        @Override
        public String toString() {
            return "/" + this.endpoint + "\\";
        }
        
        @Override
        public int compareTo(final Object o) {
            return super.compareTo((Cut)o);
        }
    }
    
    private static final class BelowValue extends Cut
    {
        private static final long serialVersionUID = 0L;
        
        BelowValue(final Comparable comparable) {
            super((Comparable)Preconditions.checkNotNull(comparable));
        }
        
        @Override
        boolean isLessThan(final Comparable comparable) {
            return Range.compareOrThrow(this.endpoint, comparable) <= 0;
        }
        
        @Override
        BoundType typeAsLowerBound() {
            return BoundType.CLOSED;
        }
        
        @Override
        BoundType typeAsUpperBound() {
            return BoundType.OPEN;
        }
        
        @Override
        Cut withLowerBoundType(final BoundType boundType, final DiscreteDomain discreteDomain) {
            switch (boundType) {
                case CLOSED: {
                    return this;
                }
                case OPEN: {
                    final Comparable previous = discreteDomain.previous(this.endpoint);
                    return (previous == null) ? Cut.belowAll() : new AboveValue(previous);
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
        
        @Override
        Cut withUpperBoundType(final BoundType boundType, final DiscreteDomain discreteDomain) {
            switch (boundType) {
                case CLOSED: {
                    final Comparable previous = discreteDomain.previous(this.endpoint);
                    return (previous == null) ? Cut.aboveAll() : new AboveValue(previous);
                }
                case OPEN: {
                    return this;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
        
        @Override
        void describeAsLowerBound(final StringBuilder sb) {
            sb.append('[').append(this.endpoint);
        }
        
        @Override
        void describeAsUpperBound(final StringBuilder sb) {
            sb.append(this.endpoint).append(')');
        }
        
        @Override
        Comparable leastValueAbove(final DiscreteDomain discreteDomain) {
            return this.endpoint;
        }
        
        @Override
        Comparable greatestValueBelow(final DiscreteDomain discreteDomain) {
            return discreteDomain.previous(this.endpoint);
        }
        
        @Override
        public int hashCode() {
            return this.endpoint.hashCode();
        }
        
        @Override
        public String toString() {
            return "\\" + this.endpoint + "/";
        }
        
        @Override
        public int compareTo(final Object o) {
            return super.compareTo((Cut)o);
        }
    }
    
    private static final class AboveAll extends Cut
    {
        private static final AboveAll INSTANCE;
        private static final long serialVersionUID = 0L;
        
        private AboveAll() {
            super(null);
        }
        
        @Override
        Comparable endpoint() {
            throw new IllegalStateException("range unbounded on this side");
        }
        
        @Override
        boolean isLessThan(final Comparable comparable) {
            return false;
        }
        
        @Override
        BoundType typeAsLowerBound() {
            throw new AssertionError((Object)"this statement should be unreachable");
        }
        
        @Override
        BoundType typeAsUpperBound() {
            throw new IllegalStateException();
        }
        
        @Override
        Cut withLowerBoundType(final BoundType boundType, final DiscreteDomain discreteDomain) {
            throw new AssertionError((Object)"this statement should be unreachable");
        }
        
        @Override
        Cut withUpperBoundType(final BoundType boundType, final DiscreteDomain discreteDomain) {
            throw new IllegalStateException();
        }
        
        @Override
        void describeAsLowerBound(final StringBuilder sb) {
            throw new AssertionError();
        }
        
        @Override
        void describeAsUpperBound(final StringBuilder sb) {
            sb.append("+\u221e)");
        }
        
        @Override
        Comparable leastValueAbove(final DiscreteDomain discreteDomain) {
            throw new AssertionError();
        }
        
        @Override
        Comparable greatestValueBelow(final DiscreteDomain discreteDomain) {
            return discreteDomain.maxValue();
        }
        
        @Override
        public int compareTo(final Cut cut) {
            return (cut != this) ? 1 : 0;
        }
        
        @Override
        public String toString() {
            return "+\u221e";
        }
        
        private Object readResolve() {
            return AboveAll.INSTANCE;
        }
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((Cut)o);
        }
        
        static AboveAll access$100() {
            return AboveAll.INSTANCE;
        }
        
        static {
            INSTANCE = new AboveAll();
        }
    }
    
    private static final class BelowAll extends Cut
    {
        private static final BelowAll INSTANCE;
        private static final long serialVersionUID = 0L;
        
        private BelowAll() {
            super(null);
        }
        
        @Override
        Comparable endpoint() {
            throw new IllegalStateException("range unbounded on this side");
        }
        
        @Override
        boolean isLessThan(final Comparable comparable) {
            return true;
        }
        
        @Override
        BoundType typeAsLowerBound() {
            throw new IllegalStateException();
        }
        
        @Override
        BoundType typeAsUpperBound() {
            throw new AssertionError((Object)"this statement should be unreachable");
        }
        
        @Override
        Cut withLowerBoundType(final BoundType boundType, final DiscreteDomain discreteDomain) {
            throw new IllegalStateException();
        }
        
        @Override
        Cut withUpperBoundType(final BoundType boundType, final DiscreteDomain discreteDomain) {
            throw new AssertionError((Object)"this statement should be unreachable");
        }
        
        @Override
        void describeAsLowerBound(final StringBuilder sb) {
            sb.append("(-\u221e");
        }
        
        @Override
        void describeAsUpperBound(final StringBuilder sb) {
            throw new AssertionError();
        }
        
        @Override
        Comparable leastValueAbove(final DiscreteDomain discreteDomain) {
            return discreteDomain.minValue();
        }
        
        @Override
        Comparable greatestValueBelow(final DiscreteDomain discreteDomain) {
            throw new AssertionError();
        }
        
        @Override
        Cut canonical(final DiscreteDomain discreteDomain) {
            return Cut.belowValue(discreteDomain.minValue());
        }
        
        @Override
        public int compareTo(final Cut cut) {
            return (cut == this) ? 0 : -1;
        }
        
        @Override
        public String toString() {
            return "-\u221e";
        }
        
        private Object readResolve() {
            return BelowAll.INSTANCE;
        }
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((Cut)o);
        }
        
        static BelowAll access$000() {
            return BelowAll.INSTANCE;
        }
        
        static {
            INSTANCE = new BelowAll();
        }
    }
}
