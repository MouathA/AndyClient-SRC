package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.util.*;
import java.io.*;

@GwtCompatible(serializable = true, emulated = true)
public abstract class ImmutableList extends ImmutableCollection implements List, RandomAccess
{
    private static final ImmutableList EMPTY;
    
    public static ImmutableList of() {
        return ImmutableList.EMPTY;
    }
    
    public static ImmutableList of(final Object o) {
        return new SingletonImmutableList(o);
    }
    
    public static ImmutableList of(final Object o, final Object o2) {
        return construct(o, o2);
    }
    
    public static ImmutableList of(final Object o, final Object o2, final Object o3) {
        return construct(o, o2, o3);
    }
    
    public static ImmutableList of(final Object o, final Object o2, final Object o3, final Object o4) {
        return construct(o, o2, o3, o4);
    }
    
    public static ImmutableList of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5) {
        return construct(o, o2, o3, o4, o5);
    }
    
    public static ImmutableList of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6) {
        return construct(o, o2, o3, o4, o5, o6);
    }
    
    public static ImmutableList of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7) {
        return construct(o, o2, o3, o4, o5, o6, o7);
    }
    
    public static ImmutableList of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8) {
        return construct(o, o2, o3, o4, o5, o6, o7, o8);
    }
    
    public static ImmutableList of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8, final Object o9) {
        return construct(o, o2, o3, o4, o5, o6, o7, o8, o9);
    }
    
    public static ImmutableList of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8, final Object o9, final Object o10) {
        return construct(o, o2, o3, o4, o5, o6, o7, o8, o9, o10);
    }
    
    public static ImmutableList of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8, final Object o9, final Object o10, final Object o11) {
        return construct(o, o2, o3, o4, o5, o6, o7, o8, o9, o10, o11);
    }
    
    public static ImmutableList of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8, final Object o9, final Object o10, final Object o11, final Object o12, final Object... array) {
        final Object[] array2 = new Object[12 + array.length];
        array2[0] = o;
        array2[1] = o2;
        array2[2] = o3;
        array2[3] = o4;
        array2[4] = o5;
        array2[5] = o6;
        array2[6] = o7;
        array2[7] = o8;
        array2[8] = o9;
        array2[9] = o10;
        array2[10] = o11;
        array2[11] = o12;
        System.arraycopy(array, 0, array2, 12, array.length);
        return construct(array2);
    }
    
    public static ImmutableList copyOf(final Iterable iterable) {
        Preconditions.checkNotNull(iterable);
        return (iterable instanceof Collection) ? copyOf(Collections2.cast(iterable)) : copyOf(iterable.iterator());
    }
    
    public static ImmutableList copyOf(final Collection collection) {
        if (collection instanceof ImmutableCollection) {
            final ImmutableList list = ((ImmutableCollection)collection).asList();
            return list.isPartialView() ? asImmutableList(list.toArray()) : list;
        }
        return construct(collection.toArray());
    }
    
    public static ImmutableList copyOf(final Iterator iterator) {
        if (!iterator.hasNext()) {
            return of();
        }
        final Object next = iterator.next();
        if (!iterator.hasNext()) {
            return of(next);
        }
        return new Builder().add(next).addAll(iterator).build();
    }
    
    public static ImmutableList copyOf(final Object[] array) {
        switch (array.length) {
            case 0: {
                return of();
            }
            case 1: {
                return new SingletonImmutableList(array[0]);
            }
            default: {
                return new RegularImmutableList(ObjectArrays.checkElementsNotNull((Object[])array.clone()));
            }
        }
    }
    
    private static ImmutableList construct(final Object... array) {
        return asImmutableList(ObjectArrays.checkElementsNotNull(array));
    }
    
    static ImmutableList asImmutableList(final Object[] array) {
        return asImmutableList(array, array.length);
    }
    
    static ImmutableList asImmutableList(Object[] arraysCopy, final int n) {
        switch (n) {
            case 0: {
                return of();
            }
            case 1: {
                return new SingletonImmutableList(arraysCopy[0]);
            }
            default: {
                if (n < arraysCopy.length) {
                    arraysCopy = ObjectArrays.arraysCopyOf(arraysCopy, n);
                }
                return new RegularImmutableList(arraysCopy);
            }
        }
    }
    
    ImmutableList() {
    }
    
    @Override
    public UnmodifiableIterator iterator() {
        return this.listIterator();
    }
    
    @Override
    public UnmodifiableListIterator listIterator() {
        return this.listIterator(0);
    }
    
    @Override
    public UnmodifiableListIterator listIterator(final int n) {
        return new AbstractIndexedListIterator(this.size(), n) {
            final ImmutableList this$0;
            
            @Override
            protected Object get(final int n) {
                return this.this$0.get(n);
            }
        };
    }
    
    @Override
    public int indexOf(@Nullable final Object o) {
        return (o == null) ? -1 : Lists.indexOfImpl(this, o);
    }
    
    @Override
    public int lastIndexOf(@Nullable final Object o) {
        return (o == null) ? -1 : Lists.lastIndexOfImpl(this, o);
    }
    
    @Override
    public boolean contains(@Nullable final Object o) {
        return this.indexOf(o) >= 0;
    }
    
    @Override
    public ImmutableList subList(final int n, final int n2) {
        Preconditions.checkPositionIndexes(n, n2, this.size());
        switch (n2 - n) {
            case 0: {
                return of();
            }
            case 1: {
                return of(this.get(n));
            }
            default: {
                return this.subListUnchecked(n, n2);
            }
        }
    }
    
    ImmutableList subListUnchecked(final int n, final int n2) {
        return new SubList(n, n2 - n);
    }
    
    @Deprecated
    @Override
    public final boolean addAll(final int n, final Collection collection) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final Object set(final int n, final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final void add(final int n, final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final Object remove(final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final ImmutableList asList() {
        return this;
    }
    
    @Override
    int copyIntoArray(final Object[] array, final int n) {
        final int size = this.size();
        while (0 < size) {
            array[n + 0] = this.get(0);
            int n2 = 0;
            ++n2;
        }
        return n + size;
    }
    
    public ImmutableList reverse() {
        return new ReverseImmutableList(this);
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return Lists.equalsImpl(this, o);
    }
    
    @Override
    public int hashCode() {
        while (0 < this.size()) {
            final int n = 31 + this.get(0).hashCode();
            int n2 = 0;
            ++n2;
        }
        return 1;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }
    
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
    
    @Override
    public List subList(final int n, final int n2) {
        return this.subList(n, n2);
    }
    
    @Override
    public ListIterator listIterator(final int n) {
        return this.listIterator(n);
    }
    
    @Override
    public ListIterator listIterator() {
        return this.listIterator();
    }
    
    static {
        EMPTY = new RegularImmutableList(ObjectArrays.EMPTY_ARRAY);
    }
    
    public static final class Builder extends ArrayBasedBuilder
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
        public Builder addAll(final Iterable iterable) {
            super.addAll(iterable);
            return this;
        }
        
        @Override
        public Builder add(final Object... array) {
            super.add(array);
            return this;
        }
        
        @Override
        public Builder addAll(final Iterator iterator) {
            super.addAll(iterator);
            return this;
        }
        
        @Override
        public ImmutableList build() {
            return ImmutableList.asImmutableList(this.contents, this.size);
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
    
    static class SerializedForm implements Serializable
    {
        final Object[] elements;
        private static final long serialVersionUID = 0L;
        
        SerializedForm(final Object[] elements) {
            this.elements = elements;
        }
        
        Object readResolve() {
            return ImmutableList.copyOf(this.elements);
        }
    }
    
    private static class ReverseImmutableList extends ImmutableList
    {
        private final transient ImmutableList forwardList;
        
        ReverseImmutableList(final ImmutableList forwardList) {
            this.forwardList = forwardList;
        }
        
        private int reverseIndex(final int n) {
            return this.size() - 1 - n;
        }
        
        private int reversePosition(final int n) {
            return this.size() - n;
        }
        
        @Override
        public ImmutableList reverse() {
            return this.forwardList;
        }
        
        @Override
        public boolean contains(@Nullable final Object o) {
            return this.forwardList.contains(o);
        }
        
        @Override
        public int indexOf(@Nullable final Object o) {
            final int lastIndex = this.forwardList.lastIndexOf(o);
            return (lastIndex >= 0) ? this.reverseIndex(lastIndex) : -1;
        }
        
        @Override
        public int lastIndexOf(@Nullable final Object o) {
            final int index = this.forwardList.indexOf(o);
            return (index >= 0) ? this.reverseIndex(index) : -1;
        }
        
        @Override
        public ImmutableList subList(final int n, final int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            return this.forwardList.subList(this.reversePosition(n2), this.reversePosition(n)).reverse();
        }
        
        @Override
        public Object get(final int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.forwardList.get(this.reverseIndex(n));
        }
        
        @Override
        public int size() {
            return this.forwardList.size();
        }
        
        @Override
        boolean isPartialView() {
            return this.forwardList.isPartialView();
        }
        
        @Override
        public List subList(final int n, final int n2) {
            return this.subList(n, n2);
        }
        
        @Override
        public ListIterator listIterator(final int n) {
            return super.listIterator(n);
        }
        
        @Override
        public ListIterator listIterator() {
            return super.listIterator();
        }
        
        @Override
        public Iterator iterator() {
            return super.iterator();
        }
    }
    
    class SubList extends ImmutableList
    {
        final transient int offset;
        final transient int length;
        final ImmutableList this$0;
        
        SubList(final ImmutableList this$0, final int offset, final int length) {
            this.this$0 = this$0;
            this.offset = offset;
            this.length = length;
        }
        
        @Override
        public int size() {
            return this.length;
        }
        
        @Override
        public Object get(final int n) {
            Preconditions.checkElementIndex(n, this.length);
            return this.this$0.get(n + this.offset);
        }
        
        @Override
        public ImmutableList subList(final int n, final int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.length);
            return this.this$0.subList(n + this.offset, n2 + this.offset);
        }
        
        @Override
        boolean isPartialView() {
            return true;
        }
        
        @Override
        public List subList(final int n, final int n2) {
            return this.subList(n, n2);
        }
        
        @Override
        public ListIterator listIterator(final int n) {
            return super.listIterator(n);
        }
        
        @Override
        public ListIterator listIterator() {
            return super.listIterator();
        }
        
        @Override
        public Iterator iterator() {
            return super.iterator();
        }
    }
}
