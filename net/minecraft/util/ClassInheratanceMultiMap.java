package net.minecraft.util;

import java.util.*;
import org.apache.commons.lang3.*;
import com.google.common.collect.*;

public class ClassInheratanceMultiMap extends AbstractSet
{
    private final Multimap field_180218_a;
    private final Set field_180216_b;
    private final Class field_180217_c;
    private static final String __OBFID;
    
    public ClassInheratanceMultiMap(final Class field_180217_c) {
        this.field_180218_a = HashMultimap.create();
        this.field_180216_b = Sets.newIdentityHashSet();
        this.field_180217_c = field_180217_c;
        this.field_180216_b.add(field_180217_c);
    }
    
    public void func_180213_a(final Class clazz) {
        for (final Object next : this.field_180218_a.get(this.func_180212_a(clazz, false))) {
            if (clazz.isAssignableFrom(next.getClass())) {
                this.field_180218_a.put(clazz, next);
            }
        }
        this.field_180216_b.add(clazz);
    }
    
    protected Class func_180212_a(final Class clazz, final boolean b) {
        for (final Class clazz2 : ClassUtils.hierarchy(clazz, ClassUtils.Interfaces.INCLUDE)) {
            if (this.field_180216_b.contains(clazz2)) {
                if (clazz2 == this.field_180217_c && b) {
                    this.func_180213_a(clazz);
                }
                return clazz2;
            }
        }
        throw new IllegalArgumentException("Don't know how to search for " + clazz);
    }
    
    @Override
    public boolean add(final Object o) {
        for (final Class clazz : this.field_180216_b) {
            if (clazz.isAssignableFrom(o.getClass())) {
                this.field_180218_a.put(clazz, o);
            }
        }
        return true;
    }
    
    @Override
    public boolean remove(final Object o) {
        for (final Class clazz : this.field_180216_b) {
            if (clazz.isAssignableFrom(o.getClass())) {
                final boolean b = false | this.field_180218_a.remove(clazz, o);
            }
        }
        return false;
    }
    
    public Iterable func_180215_b(final Class clazz) {
        return new Iterable(clazz) {
            private static final String __OBFID;
            final ClassInheratanceMultiMap this$0;
            private final Class val$p_180215_1_;
            
            @Override
            public Iterator iterator() {
                return Iterators.filter(ClassInheratanceMultiMap.access$0(this.this$0).get(this.this$0.func_180212_a(this.val$p_180215_1_, true)).iterator(), this.val$p_180215_1_);
            }
            
            static {
                __OBFID = "CL_00002265";
            }
        };
    }
    
    @Override
    public Iterator iterator() {
        return new AbstractIterator((Iterator)this.field_180218_a.get(this.field_180217_c).iterator()) {
            private static final String __OBFID;
            final ClassInheratanceMultiMap this$0;
            private final Iterator val$var1;
            
            @Override
            protected Object computeNext() {
                return this.val$var1.hasNext() ? this.val$var1.next() : this.endOfData();
            }
            
            static {
                __OBFID = "CL_00002264";
            }
        };
    }
    
    @Override
    public int size() {
        return this.field_180218_a.get(this.field_180217_c).size();
    }
    
    static Multimap access$0(final ClassInheratanceMultiMap classInheratanceMultiMap) {
        return classInheratanceMultiMap.field_180218_a;
    }
    
    static {
        __OBFID = "CL_00002266";
    }
}
