package io.netty.util.internal;

import io.netty.util.*;
import java.util.*;

public final class RecyclableArrayList extends ArrayList
{
    private static final long serialVersionUID = -8605125654176467947L;
    private static final int DEFAULT_INITIAL_CAPACITY = 8;
    private static final Recycler RECYCLER;
    private final Recycler.Handle handle;
    
    public static RecyclableArrayList newInstance() {
        return newInstance(8);
    }
    
    public static RecyclableArrayList newInstance(final int n) {
        final RecyclableArrayList list = (RecyclableArrayList)RecyclableArrayList.RECYCLER.get();
        list.ensureCapacity(n);
        return list;
    }
    
    private RecyclableArrayList(final Recycler.Handle handle) {
        this(handle, 8);
    }
    
    private RecyclableArrayList(final Recycler.Handle handle, final int n) {
        super(n);
        this.handle = handle;
    }
    
    @Override
    public boolean addAll(final Collection collection) {
        checkNullElements(collection);
        return super.addAll(collection);
    }
    
    @Override
    public boolean addAll(final int n, final Collection collection) {
        checkNullElements(collection);
        return super.addAll(n, collection);
    }
    
    private static void checkNullElements(final Collection collection) {
        if (collection instanceof RandomAccess && collection instanceof List) {
            final List<Object> list = (List<Object>)collection;
            while (0 < list.size()) {
                if (list.get(0) == null) {
                    throw new IllegalArgumentException("c contains null values");
                }
                int n = 0;
                ++n;
            }
        }
        else {
            final Iterator<Object> iterator = collection.iterator();
            while (iterator.hasNext()) {
                if (iterator.next() == null) {
                    throw new IllegalArgumentException("c contains null values");
                }
            }
        }
    }
    
    @Override
    public boolean add(final Object o) {
        if (o == null) {
            throw new NullPointerException("element");
        }
        return super.add(o);
    }
    
    @Override
    public void add(final int n, final Object o) {
        if (o == null) {
            throw new NullPointerException("element");
        }
        super.add(n, o);
    }
    
    @Override
    public Object set(final int n, final Object o) {
        if (o == null) {
            throw new NullPointerException("element");
        }
        return super.set(n, o);
    }
    
    public boolean recycle() {
        this.clear();
        return RecyclableArrayList.RECYCLER.recycle(this, this.handle);
    }
    
    RecyclableArrayList(final Recycler.Handle handle, final RecyclableArrayList$1 recycler) {
        this(handle);
    }
    
    static {
        RECYCLER = new Recycler() {
            @Override
            protected RecyclableArrayList newObject(final Handle handle) {
                return new RecyclableArrayList(handle, null);
            }
            
            @Override
            protected Object newObject(final Handle handle) {
                return this.newObject(handle);
            }
        };
    }
}
