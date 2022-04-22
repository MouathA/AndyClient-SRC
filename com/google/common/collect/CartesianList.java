package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.math.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
final class CartesianList extends AbstractList implements RandomAccess
{
    private final transient ImmutableList axes;
    private final transient int[] axesSizeProduct;
    
    static List create(final List list) {
        final ImmutableList.Builder builder = new ImmutableList.Builder(list.size());
        final Iterator<List> iterator = list.iterator();
        while (iterator.hasNext()) {
            final ImmutableList copy = ImmutableList.copyOf(iterator.next());
            if (copy.isEmpty()) {
                return ImmutableList.of();
            }
            builder.add((Object)copy);
        }
        return new CartesianList(builder.build());
    }
    
    CartesianList(final ImmutableList axes) {
        this.axes = axes;
        final int[] axesSizeProduct = new int[axes.size() + 1];
        axesSizeProduct[axes.size()] = 1;
        for (int i = axes.size() - 1; i >= 0; --i) {
            axesSizeProduct[i] = IntMath.checkedMultiply(axesSizeProduct[i + 1], ((List)axes.get(i)).size());
        }
        this.axesSizeProduct = axesSizeProduct;
    }
    
    private int getAxisIndexForProductIndex(final int n, final int n2) {
        return n / this.axesSizeProduct[n2 + 1] % this.axes.get(n2).size();
    }
    
    @Override
    public ImmutableList get(final int n) {
        Preconditions.checkElementIndex(n, this.size());
        return new ImmutableList(n) {
            final int val$index;
            final CartesianList this$0;
            
            @Override
            public int size() {
                return CartesianList.access$000(this.this$0).size();
            }
            
            @Override
            public Object get(final int n) {
                Preconditions.checkElementIndex(n, this.size());
                return CartesianList.access$000(this.this$0).get(n).get(CartesianList.access$100(this.this$0, this.val$index, n));
            }
            
            @Override
            boolean isPartialView() {
                return true;
            }
        };
    }
    
    @Override
    public int size() {
        return this.axesSizeProduct[0];
    }
    
    @Override
    public boolean contains(@Nullable final Object o) {
        if (!(o instanceof List)) {
            return false;
        }
        final List list = (List)o;
        if (list.size() != this.axes.size()) {
            return false;
        }
        final ListIterator<Object> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            if (!((List)this.axes.get(listIterator.nextIndex())).contains(listIterator.next())) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public Object get(final int n) {
        return this.get(n);
    }
    
    static ImmutableList access$000(final CartesianList list) {
        return list.axes;
    }
    
    static int access$100(final CartesianList list, final int n, final int n2) {
        return list.getAxisIndexForProductIndex(n, n2);
    }
}
