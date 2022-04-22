package com.viaversion.viaversion.libs.javassist;

import java.util.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;

public class ClassMap extends HashMap
{
    private static final long serialVersionUID = 1L;
    private ClassMap parent;
    
    public ClassMap() {
        this.parent = null;
    }
    
    ClassMap(final ClassMap parent) {
        this.parent = parent;
    }
    
    public void put(final CtClass ctClass, final CtClass ctClass2) {
        this.put(ctClass.getName(), ctClass2.getName());
    }
    
    public String put(final String s, final String s2) {
        if (s == s2) {
            return s;
        }
        final String jvmName = toJvmName(s);
        final String value = this.get((Object)jvmName);
        if (value == null || !value.equals(jvmName)) {
            return super.put(jvmName, toJvmName(s2));
        }
        return value;
    }
    
    public void putIfNone(final String s, final String s2) {
        if (s == s2) {
            return;
        }
        final String jvmName = toJvmName(s);
        if (this.get((Object)jvmName) == null) {
            super.put(jvmName, toJvmName(s2));
        }
    }
    
    protected final String put0(final String s, final String s2) {
        return super.put(s, s2);
    }
    
    @Override
    public String get(final Object o) {
        final String s = super.get(o);
        if (s == null && this.parent != null) {
            return this.parent.get(o);
        }
        return s;
    }
    
    public void fix(final CtClass ctClass) {
        this.fix(ctClass.getName());
    }
    
    public void fix(final String s) {
        final String jvmName = toJvmName(s);
        super.put(jvmName, jvmName);
    }
    
    public static String toJvmName(final String s) {
        return Descriptor.toJvmName(s);
    }
    
    public static String toJavaName(final String s) {
        return Descriptor.toJavaName(s);
    }
    
    @Override
    public Object put(final Object o, final Object o2) {
        return this.put((String)o, (String)o2);
    }
    
    @Override
    public Object get(final Object o) {
        return this.get(o);
    }
}
