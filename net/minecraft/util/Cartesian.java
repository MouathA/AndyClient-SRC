package net.minecraft.util;

import com.google.common.base.*;
import java.lang.reflect.*;
import com.google.common.collect.*;
import java.util.*;

public class Cartesian
{
    private static final String __OBFID;
    
    public static Iterable cartesianProduct(final Class clazz, final Iterable iterable) {
        return new Product(clazz, (Iterable[])toArray(Iterable.class, iterable), null);
    }
    
    public static Iterable cartesianProduct(final Iterable iterable) {
        return arraysAsLists(cartesianProduct(Object.class, iterable));
    }
    
    private static Iterable arraysAsLists(final Iterable iterable) {
        return Iterables.transform(iterable, new GetList(null));
    }
    
    private static Object[] toArray(final Class clazz, final Iterable iterable) {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<Object> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next());
        }
        return arrayList.toArray(createArray(clazz, arrayList.size()));
    }
    
    private static Object[] createArray(final Class clazz, final int n) {
        return (Object[])Array.newInstance(clazz, n);
    }
    
    static Object[] access$0(final Class clazz, final int n) {
        return createArray(clazz, n);
    }
    
    static {
        __OBFID = "CL_00002327";
    }
    
    static class GetList implements Function
    {
        private static final String __OBFID;
        
        private GetList() {
        }
        
        public List apply(final Object[] array) {
            return Arrays.asList(array);
        }
        
        @Override
        public Object apply(final Object o) {
            return this.apply((Object[])o);
        }
        
        GetList(final Object o) {
            this();
        }
        
        static {
            __OBFID = "CL_00002325";
        }
    }
    
    static class Product implements Iterable
    {
        private final Class clazz;
        private final Iterable[] iterables;
        private static final String __OBFID;
        
        private Product(final Class clazz, final Iterable[] iterables) {
            this.clazz = clazz;
            this.iterables = iterables;
        }
        
        @Override
        public Iterator iterator() {
            return (this.iterables.length <= 0) ? Collections.singletonList(Cartesian.access$0(this.clazz, 0)).iterator() : new ProductIterator(this.clazz, this.iterables, null);
        }
        
        Product(final Class clazz, final Iterable[] array, final Object o) {
            this(clazz, array);
        }
        
        static {
            __OBFID = "CL_00002324";
        }
        
        static class ProductIterator extends UnmodifiableIterator
        {
            private int index;
            private final Iterable[] iterables;
            private final Iterator[] iterators;
            private final Object[] results;
            private static final String __OBFID;
            
            private ProductIterator(final Class clazz, final Iterable[] iterables) {
                this.index = -2;
                this.iterables = iterables;
                this.iterators = (Iterator[])Cartesian.access$0(Iterator.class, this.iterables.length);
                while (0 < this.iterables.length) {
                    this.iterators[0] = iterables[0].iterator();
                    int n = 0;
                    ++n;
                }
                this.results = Cartesian.access$0(clazz, this.iterators.length);
            }
            
            private void endOfData() {
                this.index = -1;
                Arrays.fill(this.iterators, null);
                Arrays.fill(this.results, null);
            }
            
            @Override
            public boolean hasNext() {
                if (this.index == -2) {
                    this.index = 0;
                    final Iterator[] iterators = this.iterators;
                    while (0 < iterators.length) {
                        if (!iterators[0].hasNext()) {
                            this.endOfData();
                            break;
                        }
                        int n = 0;
                        ++n;
                    }
                    return true;
                }
                if (this.index >= this.iterators.length) {
                    this.index = this.iterators.length - 1;
                    while (this.index >= 0) {
                        if (this.iterators[this.index].hasNext()) {
                            break;
                        }
                        if (this.index == 0) {
                            this.endOfData();
                            break;
                        }
                        final Iterator iterator = this.iterables[this.index].iterator();
                        this.iterators[this.index] = iterator;
                        if (!iterator.hasNext()) {
                            this.endOfData();
                            break;
                        }
                        --this.index;
                    }
                }
                return this.index >= 0;
            }
            
            public Object[] next0() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                while (this.index < this.iterators.length) {
                    this.results[this.index] = this.iterators[this.index].next();
                    ++this.index;
                }
                return this.results.clone();
            }
            
            @Override
            public Object next() {
                return this.next0();
            }
            
            ProductIterator(final Class clazz, final Iterable[] array, final Object o) {
                this(clazz, array);
            }
            
            static {
                __OBFID = "CL_00002323";
            }
        }
    }
}
