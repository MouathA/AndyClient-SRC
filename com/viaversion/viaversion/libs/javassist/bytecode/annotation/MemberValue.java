package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;
import java.io.*;

public abstract class MemberValue
{
    ConstPool cp;
    char tag;
    
    MemberValue(final char tag, final ConstPool cp) {
        this.cp = cp;
        this.tag = tag;
    }
    
    abstract Object getValue(final ClassLoader p0, final ClassPool p1, final Method p2) throws ClassNotFoundException;
    
    abstract Class getType(final ClassLoader p0) throws ClassNotFoundException;
    
    static Class loadClass(final ClassLoader classLoader, final String s) throws ClassNotFoundException, NoSuchClassError {
        return Class.forName(convertFromArray(s), true, classLoader);
    }
    
    private static String convertFromArray(final String s) {
        int i = s.indexOf("[]");
        if (i != -1) {
            final StringBuffer sb = new StringBuffer(Descriptor.of(s.substring(0, i)));
            while (i != -1) {
                sb.insert(0, "[");
                i = s.indexOf("[]", i + 1);
            }
            return sb.toString().replace('/', '.');
        }
        return s;
    }
    
    public abstract void accept(final MemberValueVisitor p0);
    
    public abstract void write(final AnnotationsWriter p0) throws IOException;
}
