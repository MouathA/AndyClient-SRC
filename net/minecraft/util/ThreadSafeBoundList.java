package net.minecraft.util;

import java.util.concurrent.locks.*;
import java.lang.reflect.*;

public class ThreadSafeBoundList
{
    private final Object[] field_152759_a;
    private final Class field_152760_b;
    private final ReadWriteLock field_152761_c;
    private int field_152762_d;
    private int field_152763_e;
    private static final String __OBFID;
    
    public ThreadSafeBoundList(final Class field_152760_b, final int n) {
        this.field_152761_c = new ReentrantReadWriteLock();
        this.field_152760_b = field_152760_b;
        this.field_152759_a = (Object[])Array.newInstance(field_152760_b, n);
    }
    
    public Object func_152757_a(final Object o) {
        this.field_152761_c.writeLock().lock();
        this.field_152759_a[this.field_152763_e] = o;
        this.field_152763_e = (this.field_152763_e + 1) % this.func_152758_b();
        if (this.field_152762_d < this.func_152758_b()) {
            ++this.field_152762_d;
        }
        this.field_152761_c.writeLock().unlock();
        return o;
    }
    
    public int func_152758_b() {
        this.field_152761_c.readLock().lock();
        final int length = this.field_152759_a.length;
        this.field_152761_c.readLock().unlock();
        return length;
    }
    
    public Object[] func_152756_c() {
        final Object[] array = (Object[])Array.newInstance(this.field_152760_b, this.field_152762_d);
        this.field_152761_c.readLock().lock();
        while (0 < this.field_152762_d) {
            int n = (this.field_152763_e - this.field_152762_d + 0) % this.func_152758_b();
            if (n < 0) {
                n += this.func_152758_b();
            }
            array[0] = this.field_152759_a[n];
            int n2 = 0;
            ++n2;
        }
        this.field_152761_c.readLock().unlock();
        return array;
    }
    
    static {
        __OBFID = "CL_00001868";
    }
}
