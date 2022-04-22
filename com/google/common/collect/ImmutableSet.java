package com.google.common.collect;

import com.google.common.base.*;
import com.google.common.annotations.*;
import java.util.*;
import javax.annotation.*;
import java.io.*;

@GwtCompatible(serializable = true, emulated = true)
public abstract class ImmutableSet extends ImmutableCollection implements Set
{
    static final int MAX_TABLE_SIZE = 1073741824;
    private static final double DESIRED_LOAD_FACTOR = 0.7;
    private static final int CUTOFF = 751619276;
    
    public static ImmutableSet of() {
        return EmptyImmutableSet.INSTANCE;
    }
    
    public static ImmutableSet of(final Object o) {
        return new SingletonImmutableSet(o);
    }
    
    public static ImmutableSet of(final Object o, final Object o2) {
        return construct(2, o, o2);
    }
    
    public static ImmutableSet of(final Object o, final Object o2, final Object o3) {
        return construct(3, o, o2, o3);
    }
    
    public static ImmutableSet of(final Object o, final Object o2, final Object o3, final Object o4) {
        return construct(4, o, o2, o3, o4);
    }
    
    public static ImmutableSet of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5) {
        return construct(5, o, o2, o3, o4, o5);
    }
    
    public static ImmutableSet of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object... array) {
        final Object[] array2 = new Object[6 + array.length];
        array2[0] = o;
        array2[1] = o2;
        array2[2] = o3;
        array2[3] = o4;
        array2[4] = o5;
        array2[5] = o6;
        System.arraycopy(array, 0, array2, 6, array.length);
        return construct(array2.length, array2);
    }
    
    private static ImmutableSet construct(final int n, final Object... array) {
        switch (n) {
            case 0: {
                return of();
            }
            case 1: {
                return of(array[0]);
            }
            default: {
                final int chooseTableSize = chooseTableSize(n);
                final Object[] array2 = new Object[chooseTableSize];
                final int n2 = chooseTableSize - 1;
                while (0 < n) {
                    final Object checkElementNotNull = ObjectArrays.checkElementNotNull(array[0], 0);
                    int smear = Hashing.smear(checkElementNotNull.hashCode());
                    while (true) {
                        final int n3 = smear & n2;
                        final Object o = array2[n3];
                        if (o == null) {
                            final int n4 = 0;
                            int n5 = 0;
                            ++n5;
                            array2[n3] = (array[n4] = checkElementNotNull);
                            break;
                        }
                        if (o.equals(checkElementNotNull)) {
                            break;
                        }
                        ++smear;
                    }
                    int n6 = 0;
                    ++n6;
                }
                Arrays.fill(array, 0, n, null);
                if (false == true) {
                    return new SingletonImmutableSet(array[0], 0);
                }
                if (chooseTableSize != chooseTableSize(0)) {
                    return construct(0, array);
                }
                return new RegularImmutableSet((0 < array.length) ? ObjectArrays.arraysCopyOf(array, 0) : array, 0, array2, n2);
            }
        }
    }
    
    @VisibleForTesting
    static int chooseTableSize(final int n) {
        if (n < 751619276) {
            int n2;
            for (n2 = Integer.highestOneBit(n - 1) << 1; n2 * 0.7 < n; n2 <<= 1) {}
            return n2;
        }
        Preconditions.checkArgument(n < 1073741824, (Object)"collection too large");
        return 1073741824;
    }
    
    public static ImmutableSet copyOf(final Object[] array) {
        switch (array.length) {
            case 0: {
                return of();
            }
            case 1: {
                return of(array[0]);
            }
            default: {
                return construct(array.length, (Object[])array.clone());
            }
        }
    }
    
    public static ImmutableSet copyOf(final Iterable iterable) {
        return (iterable instanceof Collection) ? copyOf(Collections2.cast(iterable)) : copyOf(iterable.iterator());
    }
    
    public static ImmutableSet copyOf(final Iterator iterator) {
        if (!iterator.hasNext()) {
            return of();
        }
        final Object next = iterator.next();
        if (!iterator.hasNext()) {
            return of(next);
        }
        return new Builder().add(next).addAll(iterator).build();
    }
    
    public static ImmutableSet copyOf(final Collection collection) {
        if (collection instanceof ImmutableSet && !(collection instanceof ImmutableSortedSet)) {
            final ImmutableSet set = (ImmutableSet)collection;
            if (!set.isPartialView()) {
                return set;
            }
        }
        else if (collection instanceof EnumSet) {
            return copyOfEnumSet((EnumSet)collection);
        }
        final Object[] array = collection.toArray();
        return construct(array.length, array);
    }
    
    private static ImmutableSet copyOfEnumSet(final EnumSet set) {
        return ImmutableEnumSet.asImmutable(EnumSet.copyOf((EnumSet<Enum>)set));
    }
    
    ImmutableSet() {
    }
    
    boolean isHashCodeFast() {
        return false;
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o == this || ((!(o instanceof ImmutableSet) || !this.isHashCodeFast() || !((ImmutableSet)o).isHashCodeFast() || this.hashCode() == o.hashCode()) && Sets.equalsImpl(this, o));
    }
    
    @Override
    public int hashCode() {
        return Sets.hashCodeImpl(this);
    }
    
    @Override
    public abstract UnmodifiableIterator iterator();
    
    @Override
    Object writeReplace() {
        return new SerializedForm(this.toArray());
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
    
    static ImmutableSet access$000(final int n, final Object[] array) {
        return construct(n, array);
    }
    
    public static class Builder extends ArrayBasedBuilder
    {
        public Builder() {
            this(4);
        }
        
        Builder(final int n) {
            super(n);
        }
        
        @Override
        public Builder add(final Object o) {
            super.add(o);
            return this;
        }
        
        @Override
        public Builder add(final Object... array) {
            super.add(array);
            return this;
        }
        
        @Override
        public Builder addAll(final Iterable iterable) {
            super.addAll(iterable);
            return this;
        }
        
        @Override
        public Builder addAll(final Iterator iterator) {
            super.addAll(iterator);
            return this;
        }
        
        @Override
        public ImmutableSet build() {
            final ImmutableSet access$000 = ImmutableSet.access$000(this.size, this.contents);
            this.size = access$000.size();
            return access$000;
        }
        
        @Override
        public ImmutableCollection.Builder addAll(final Iterable iterable) {
            return this.addAll(iterable);
        }
        
        @Override
        public ImmutableCollection.Builder add(final Object[] array) {
            return this.add(array);
        }
        
        @Override
        public ArrayBasedBuilder add(final Object o) {
            return this.add(o);
        }
        
        @Override
        public ImmutableCollection build() {
            return this.build();
        }
        
        @Override
        public ImmutableCollection.Builder addAll(final Iterator iterator) {
            return this.addAll(iterator);
        }
        
        @Override
        public ImmutableCollection.Builder add(final Object o) {
            return this.add(o);
        }
    }
    
    private static class SerializedForm implements Serializable
    {
        final Object[] elements;
        private static final long serialVersionUID = 0L;
        
        SerializedForm(final Object[] elements) {
            this.elements = elements;
        }
        
        Object readResolve() {
            return ImmutableSet.copyOf(this.elements);
        }
    }
}
