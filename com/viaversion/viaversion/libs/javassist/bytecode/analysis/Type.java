package com.viaversion.viaversion.libs.javassist.bytecode.analysis;

import com.viaversion.viaversion.libs.javassist.*;
import java.util.*;

public class Type
{
    private final CtClass clazz;
    private final boolean special;
    private static final Map prims;
    public static final Type DOUBLE;
    public static final Type BOOLEAN;
    public static final Type LONG;
    public static final Type CHAR;
    public static final Type BYTE;
    public static final Type SHORT;
    public static final Type INTEGER;
    public static final Type FLOAT;
    public static final Type VOID;
    public static final Type UNINIT;
    public static final Type RETURN_ADDRESS;
    public static final Type TOP;
    public static final Type BOGUS;
    public static final Type OBJECT;
    public static final Type SERIALIZABLE;
    public static final Type CLONEABLE;
    public static final Type THROWABLE;
    
    public static Type get(final CtClass ctClass) {
        final Type type = Type.prims.get(ctClass);
        return (type != null) ? type : new Type(ctClass);
    }
    
    private static Type lookupType(final String s) {
        return new Type(ClassPool.getDefault().get(s));
    }
    
    Type(final CtClass ctClass) {
        this(ctClass, false);
    }
    
    private Type(final CtClass clazz, final boolean special) {
        this.clazz = clazz;
        this.special = special;
    }
    
    boolean popChanged() {
        return false;
    }
    
    public int getSize() {
        return (this.clazz == CtClass.doubleType || this.clazz == CtClass.longType || this == Type.TOP) ? 2 : 1;
    }
    
    public CtClass getCtClass() {
        return this.clazz;
    }
    
    public boolean isReference() {
        return !this.special && (this.clazz == null || !this.clazz.isPrimitive());
    }
    
    public boolean isSpecial() {
        return this.special;
    }
    
    public boolean isArray() {
        return this.clazz != null && this.clazz.isArray();
    }
    
    public int getDimensions() {
        if (!this.isArray()) {
            return 0;
        }
        final String name = this.clazz.getName();
        int n2 = 0;
        for (int n = name.length() - 1; name.charAt(n) == ']'; n -= 2, ++n2) {}
        return 0;
    }
    
    public Type getComponent() {
        if (this.clazz == null || !this.clazz.isArray()) {
            return null;
        }
        final CtClass componentType = this.clazz.getComponentType();
        final Type type = Type.prims.get(componentType);
        return (type != null) ? type : new Type(componentType);
    }
    
    public boolean isAssignableFrom(final Type type) {
        if (this == type) {
            return true;
        }
        if ((type == Type.UNINIT && this.isReference()) || (this == Type.UNINIT && type.isReference())) {
            return true;
        }
        if (type instanceof MultiType) {
            return ((MultiType)type).isAssignableTo(this);
        }
        if (type instanceof MultiArrayType) {
            return ((MultiArrayType)type).isAssignableTo(this);
        }
        return this.clazz != null && !this.clazz.isPrimitive() && type.clazz.subtypeOf(this.clazz);
    }
    
    public Type merge(final Type type) {
        if (type == this) {
            return this;
        }
        if (type == null) {
            return this;
        }
        if (type == Type.UNINIT) {
            return this;
        }
        if (this == Type.UNINIT) {
            return type;
        }
        if (!type.isReference() || !this.isReference()) {
            return Type.BOGUS;
        }
        if (type instanceof MultiType) {
            return type.merge(this);
        }
        if (type.isArray() && this.isArray()) {
            return this.mergeArray(type);
        }
        return this.mergeClasses(type);
    }
    
    Type getRootComponent(Type component) {
        while (component.isArray()) {
            component = component.getComponent();
        }
        return component;
    }
    
    private Type createArray(final Type type, final int n) {
        if (type instanceof MultiType) {
            return new MultiArrayType((MultiType)type, n);
        }
        return get(this.getClassPool(type).get(this.arrayName(type.clazz.getName(), n)));
    }
    
    String arrayName(String s, final int n) {
        int i = s.length();
        final int n2 = i + n * 2;
        final char[] array = new char[n2];
        s.getChars(0, i, array, 0);
        while (i < n2) {
            array[i++] = '[';
            array[i++] = ']';
        }
        s = new String(array);
        return s;
    }
    
    private ClassPool getClassPool(final Type type) {
        final ClassPool classPool = type.clazz.getClassPool();
        return (classPool != null) ? classPool : ClassPool.getDefault();
    }
    
    private Type mergeArray(final Type type) {
        final Type rootComponent = this.getRootComponent(type);
        final Type rootComponent2 = this.getRootComponent(this);
        final int dimensions = type.getDimensions();
        final int dimensions2 = this.getDimensions();
        if (dimensions == dimensions2) {
            final Type merge = rootComponent2.merge(rootComponent);
            if (merge == Type.BOGUS) {
                return Type.OBJECT;
            }
            return this.createArray(merge, dimensions2);
        }
        else {
            Type type2;
            int n;
            if (dimensions < dimensions2) {
                type2 = rootComponent;
                n = dimensions;
            }
            else {
                type2 = rootComponent2;
                n = dimensions2;
            }
            if (eq(Type.CLONEABLE.clazz, type2.clazz) || eq(Type.SERIALIZABLE.clazz, type2.clazz)) {
                return this.createArray(type2, n);
            }
            return this.createArray(Type.OBJECT, n);
        }
    }
    
    private static CtClass findCommonSuperClass(final CtClass ctClass, final CtClass ctClass2) throws NotFoundException {
        CtClass superclass = ctClass;
        final CtClass ctClass4;
        CtClass ctClass3 = ctClass4 = ctClass2;
        CtClass superclass2 = superclass;
        while (!eq(superclass, ctClass3) || superclass.getSuperclass() == null) {
            final CtClass superclass3 = superclass.getSuperclass();
            final CtClass superclass4 = ctClass3.getSuperclass();
            CtClass superclass5;
            if (superclass4 == null) {
                superclass5 = ctClass4;
            }
            else {
                if (superclass3 != null) {
                    superclass = superclass3;
                    ctClass3 = superclass4;
                    continue;
                }
                final CtClass ctClass5 = superclass2;
                superclass2 = ctClass4;
                final CtClass ctClass6 = ctClass5;
                superclass = ctClass3;
                superclass5 = ctClass6;
            }
            while (true) {
                superclass = superclass.getSuperclass();
                if (superclass == null) {
                    break;
                }
                superclass2 = superclass2.getSuperclass();
            }
            CtClass superclass6;
            for (superclass6 = superclass2; !eq(superclass6, superclass5); superclass6 = superclass6.getSuperclass(), superclass5 = superclass5.getSuperclass()) {}
            return superclass6;
        }
        return superclass;
    }
    
    private Type mergeClasses(final Type type) throws NotFoundException {
        final CtClass commonSuperClass = findCommonSuperClass(this.clazz, type.clazz);
        if (commonSuperClass.getSuperclass() == null) {
            final Map commonInterfaces = this.findCommonInterfaces(type);
            if (commonInterfaces.size() == 1) {
                return new Type(commonInterfaces.values().iterator().next());
            }
            if (commonInterfaces.size() > 1) {
                return new MultiType(commonInterfaces);
            }
            return new Type(commonSuperClass);
        }
        else {
            final Map exclusiveDeclaredInterfaces = this.findExclusiveDeclaredInterfaces(type, commonSuperClass);
            if (exclusiveDeclaredInterfaces.size() > 0) {
                return new MultiType(exclusiveDeclaredInterfaces, new Type(commonSuperClass));
            }
            return new Type(commonSuperClass);
        }
    }
    
    private Map findCommonInterfaces(final Type type) {
        return this.findCommonInterfaces(this.getAllInterfaces(type.clazz, null), this.getAllInterfaces(this.clazz, null));
    }
    
    private Map findExclusiveDeclaredInterfaces(final Type type, final CtClass ctClass) {
        final Map declaredInterfaces = this.getDeclaredInterfaces(type.clazz, null);
        final Map declaredInterfaces2 = this.getDeclaredInterfaces(this.clazz, null);
        for (final String s : this.getAllInterfaces(ctClass, null).keySet()) {
            declaredInterfaces.remove(s);
            declaredInterfaces2.remove(s);
        }
        return this.findCommonInterfaces(declaredInterfaces, declaredInterfaces2);
    }
    
    Map findCommonInterfaces(final Map map, Map hashMap) {
        if (hashMap == null) {
            hashMap = (HashMap<String, CtClass>)new HashMap<Object, CtClass>();
        }
        if (map == null || map.isEmpty()) {
            hashMap.clear();
        }
        final Iterator<String> iterator = hashMap.keySet().iterator();
        while (iterator.hasNext()) {
            if (!map.containsKey(iterator.next())) {
                iterator.remove();
            }
        }
        final ArrayList<CtClass> list = new ArrayList<CtClass>();
        final Iterator<CtClass> iterator2 = hashMap.values().iterator();
        while (iterator2.hasNext()) {
            list.addAll((Collection<?>)Arrays.asList(iterator2.next().getInterfaces()));
        }
        final Iterator<Object> iterator3 = list.iterator();
        while (iterator3.hasNext()) {
            hashMap.remove(iterator3.next().getName());
        }
        return hashMap;
    }
    
    Map getAllInterfaces(CtClass superclass, Map hashMap) {
        if (hashMap == null) {
            hashMap = new HashMap<String, CtClass>();
        }
        if (superclass.isInterface()) {
            hashMap.put(superclass.getName(), superclass);
        }
        do {
            final CtClass[] interfaces = superclass.getInterfaces();
            while (0 < interfaces.length) {
                final CtClass ctClass = interfaces[0];
                hashMap.put(ctClass.getName(), ctClass);
                this.getAllInterfaces(ctClass, hashMap);
                int n = 0;
                ++n;
            }
            superclass = superclass.getSuperclass();
        } while (superclass != null);
        return hashMap;
    }
    
    Map getDeclaredInterfaces(final CtClass ctClass, Map hashMap) {
        if (hashMap == null) {
            hashMap = new HashMap<String, CtClass>();
        }
        if (ctClass.isInterface()) {
            hashMap.put(ctClass.getName(), ctClass);
        }
        final CtClass[] interfaces = ctClass.getInterfaces();
        while (0 < interfaces.length) {
            final CtClass ctClass2 = interfaces[0];
            hashMap.put(ctClass2.getName(), ctClass2);
            this.getDeclaredInterfaces(ctClass2, hashMap);
            int n = 0;
            ++n;
        }
        return hashMap;
    }
    
    @Override
    public int hashCode() {
        return this.getClass().hashCode() + this.clazz.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Type && o.getClass() == this.getClass() && eq(this.clazz, ((Type)o).clazz);
    }
    
    static boolean eq(final CtClass ctClass, final CtClass ctClass2) {
        return ctClass == ctClass2 || (ctClass != null && ctClass2 != null && ctClass.getName().equals(ctClass2.getName()));
    }
    
    @Override
    public String toString() {
        if (this == Type.BOGUS) {
            return "BOGUS";
        }
        if (this == Type.UNINIT) {
            return "UNINIT";
        }
        if (this == Type.RETURN_ADDRESS) {
            return "RETURN ADDRESS";
        }
        if (this == Type.TOP) {
            return "TOP";
        }
        return (this.clazz == null) ? "null" : this.clazz.getName();
    }
    
    static {
        prims = new IdentityHashMap();
        DOUBLE = new Type(CtClass.doubleType);
        BOOLEAN = new Type(CtClass.booleanType);
        LONG = new Type(CtClass.longType);
        CHAR = new Type(CtClass.charType);
        BYTE = new Type(CtClass.byteType);
        SHORT = new Type(CtClass.shortType);
        INTEGER = new Type(CtClass.intType);
        FLOAT = new Type(CtClass.floatType);
        VOID = new Type(CtClass.voidType);
        UNINIT = new Type(null);
        RETURN_ADDRESS = new Type(null, true);
        TOP = new Type(null, true);
        BOGUS = new Type(null, true);
        OBJECT = lookupType("java.lang.Object");
        SERIALIZABLE = lookupType("java.io.Serializable");
        CLONEABLE = lookupType("java.lang.Cloneable");
        THROWABLE = lookupType("java.lang.Throwable");
        Type.prims.put(CtClass.doubleType, Type.DOUBLE);
        Type.prims.put(CtClass.longType, Type.LONG);
        Type.prims.put(CtClass.charType, Type.CHAR);
        Type.prims.put(CtClass.shortType, Type.SHORT);
        Type.prims.put(CtClass.intType, Type.INTEGER);
        Type.prims.put(CtClass.floatType, Type.FLOAT);
        Type.prims.put(CtClass.byteType, Type.BYTE);
        Type.prims.put(CtClass.booleanType, Type.BOOLEAN);
        Type.prims.put(CtClass.voidType, Type.VOID);
    }
}
