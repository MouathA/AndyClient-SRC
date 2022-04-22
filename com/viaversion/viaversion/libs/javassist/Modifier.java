package com.viaversion.viaversion.libs.javassist;

public class Modifier
{
    public static final int PUBLIC = 1;
    public static final int PRIVATE = 2;
    public static final int PROTECTED = 4;
    public static final int STATIC = 8;
    public static final int FINAL = 16;
    public static final int SYNCHRONIZED = 32;
    public static final int VOLATILE = 64;
    public static final int VARARGS = 128;
    public static final int TRANSIENT = 128;
    public static final int NATIVE = 256;
    public static final int INTERFACE = 512;
    public static final int ABSTRACT = 1024;
    public static final int STRICT = 2048;
    public static final int ANNOTATION = 8192;
    public static final int ENUM = 16384;
    
    public static boolean isPublic(final int n) {
        return (n & 0x1) != 0x0;
    }
    
    public static boolean isPrivate(final int n) {
        return (n & 0x2) != 0x0;
    }
    
    public static boolean isProtected(final int n) {
        return (n & 0x4) != 0x0;
    }
    
    public static boolean isPackage(final int n) {
        return (n & 0x7) == 0x0;
    }
    
    public static boolean isStatic(final int n) {
        return (n & 0x8) != 0x0;
    }
    
    public static boolean isFinal(final int n) {
        return (n & 0x10) != 0x0;
    }
    
    public static boolean isSynchronized(final int n) {
        return (n & 0x20) != 0x0;
    }
    
    public static boolean isVolatile(final int n) {
        return (n & 0x40) != 0x0;
    }
    
    public static boolean isTransient(final int n) {
        return (n & 0x80) != 0x0;
    }
    
    public static boolean isNative(final int n) {
        return (n & 0x100) != 0x0;
    }
    
    public static boolean isInterface(final int n) {
        return (n & 0x200) != 0x0;
    }
    
    public static boolean isAnnotation(final int n) {
        return (n & 0x2000) != 0x0;
    }
    
    public static boolean isEnum(final int n) {
        return (n & 0x4000) != 0x0;
    }
    
    public static boolean isAbstract(final int n) {
        return (n & 0x400) != 0x0;
    }
    
    public static boolean isStrict(final int n) {
        return (n & 0x800) != 0x0;
    }
    
    public static boolean isVarArgs(final int n) {
        return (n & 0x80) != 0x0;
    }
    
    public static int setPublic(final int n) {
        return (n & 0xFFFFFFF9) | 0x1;
    }
    
    public static int setProtected(final int n) {
        return (n & 0xFFFFFFFC) | 0x4;
    }
    
    public static int setPrivate(final int n) {
        return (n & 0xFFFFFFFA) | 0x2;
    }
    
    public static int setPackage(final int n) {
        return n & 0xFFFFFFF8;
    }
    
    public static int clear(final int n, final int n2) {
        return n & ~n2;
    }
    
    public static String toString(final int n) {
        return java.lang.reflect.Modifier.toString(n);
    }
}
