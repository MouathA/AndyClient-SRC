package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.*;

public abstract class AbstractIntCollection extends AbstractCollection implements IntCollection
{
    protected AbstractIntCollection() {
    }
    
    @Override
    public abstract IntIterator iterator();
    
    @Override
    public boolean add(final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean contains(final int n) {
        final IntIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (n == iterator.nextInt()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean rem(final int n) {
        final IntIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (n == iterator.nextInt()) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    
    @Deprecated
    @Override
    public boolean add(final Integer n) {
        return super.add(n);
    }
    
    @Deprecated
    @Override
    public boolean contains(final Object o) {
        return super.contains(o);
    }
    
    @Deprecated
    @Override
    public boolean remove(final Object o) {
        return super.remove(o);
    }
    
    @Override
    public int[] toArray(int[] copy) {
        final int size = this.size();
        if (copy == null) {
            copy = new int[size];
        }
        else if (copy.length < size) {
            copy = Arrays.copyOf(copy, size);
        }
        IntIterators.unwrap(this.iterator(), copy);
        return copy;
    }
    
    @Override
    public int[] toIntArray() {
        return this.toArray((int[])null);
    }
    
    @Deprecated
    @Override
    public int[] toIntArray(final int[] array) {
        return this.toArray(array);
    }
    
    @Override
    public final void forEach(final IntConsumer intConsumer) {
        super.forEach(intConsumer);
    }
    
    @Override
    public final boolean removeIf(final IntPredicate intPredicate) {
        return super.removeIf(intPredicate);
    }
    
    @Override
    public boolean addAll(final IntCollection collection) {
        final IntIterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (this.add(iterator.nextInt())) {
                continue;
            }
        }
        return true;
    }
    
    @Override
    public boolean addAll(final Collection collection) {
        if (collection instanceof IntCollection) {
            return this.addAll((IntCollection)collection);
        }
        return super.addAll(collection);
    }
    
    @Override
    public boolean containsAll(final IntCollection collection) {
        final IntIterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (!this.contains(iterator.nextInt())) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean containsAll(final Collection collection) {
        if (collection instanceof IntCollection) {
            return this.containsAll((IntCollection)collection);
        }
        return super.containsAll(collection);
    }
    
    @Override
    public boolean removeAll(final IntCollection collection) {
        final IntIterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (this.rem(iterator.nextInt())) {
                continue;
            }
        }
        return true;
    }
    
    @Override
    public boolean removeAll(final Collection collection) {
        if (collection instanceof IntCollection) {
            return this.removeAll((IntCollection)collection);
        }
        return super.removeAll(collection);
    }
    
    @Override
    public boolean retainAll(final IntCollection collection) {
        final IntIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (!collection.contains(iterator.nextInt())) {
                iterator.remove();
            }
        }
        return true;
    }
    
    @Override
    public boolean retainAll(final Collection collection) {
        if (collection instanceof IntCollection) {
            return this.retainAll((IntCollection)collection);
        }
        return super.retainAll(collection);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final IntIterator iterator = this.iterator();
        int size = this.size();
        sb.append("{");
        while (size-- != 0) {
            if (!false) {
                sb.append(", ");
            }
            sb.append(String.valueOf(iterator.nextInt()));
        }
        sb.append("}");
        return sb.toString();
    }
    
    @Deprecated
    @Override
    public boolean add(final Object o) {
        return this.add((Integer)o);
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}
