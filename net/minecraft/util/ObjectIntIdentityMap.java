package net.minecraft.util;

import java.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;

public class ObjectIntIdentityMap implements IObjectIntIterable
{
    private final IdentityHashMap field_148749_a;
    private final List field_148748_b;
    private static final String __OBFID;
    
    public ObjectIntIdentityMap() {
        this.field_148749_a = new IdentityHashMap(512);
        this.field_148748_b = Lists.newArrayList();
    }
    
    public void put(final Object o, final int n) {
        this.field_148749_a.put(o, n);
        while (this.field_148748_b.size() <= n) {
            this.field_148748_b.add(null);
        }
        this.field_148748_b.set(n, o);
    }
    
    public int get(final Object o) {
        final Integer n = this.field_148749_a.get(o);
        return (n == null) ? -1 : n;
    }
    
    public final Object getByValue(final int n) {
        return (n >= 0 && n < this.field_148748_b.size()) ? this.field_148748_b.get(n) : null;
    }
    
    @Override
    public Iterator iterator() {
        return Iterators.filter(this.field_148748_b.iterator(), Predicates.notNull());
    }
    
    public List getObjectList() {
        return this.field_148748_b;
    }
    
    static {
        __OBFID = "CL_00001203";
    }
}
