package com.viaversion.viaversion.libs.javassist.tools.reflect;

import java.lang.reflect.*;
import java.io.*;

public class Metaobject implements Serializable
{
    private static final long serialVersionUID = 1L;
    protected ClassMetaobject classmetaobject;
    protected Metalevel baseobject;
    protected Method[] methods;
    
    public Metaobject(final Object o, final Object[] array) {
        this.baseobject = (Metalevel)o;
        this.classmetaobject = this.baseobject._getClass();
        this.methods = this.classmetaobject.getReflectiveMethods();
    }
    
    protected Metaobject() {
        this.baseobject = null;
        this.classmetaobject = null;
        this.methods = null;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this.baseobject);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.baseobject = (Metalevel)objectInputStream.readObject();
        this.classmetaobject = this.baseobject._getClass();
        this.methods = this.classmetaobject.getReflectiveMethods();
    }
    
    public final ClassMetaobject getClassMetaobject() {
        return this.classmetaobject;
    }
    
    public final Object getObject() {
        return this.baseobject;
    }
    
    public final void setObject(final Object o) {
        this.baseobject = (Metalevel)o;
        this.classmetaobject = this.baseobject._getClass();
        this.methods = this.classmetaobject.getReflectiveMethods();
        this.baseobject._setMetaobject(this);
    }
    
    public final String getMethodName(final int n) {
        final String name = this.methods[n].getName();
        char char1;
        do {
            final String s = name;
            final int n2 = 3;
            int n3 = 0;
            ++n3;
            char1 = s.charAt(n2);
        } while (char1 >= '0' && '9' >= char1);
        return name.substring(3);
    }
    
    public final Class[] getParameterTypes(final int n) {
        return this.methods[n].getParameterTypes();
    }
    
    public final Class getReturnType(final int n) {
        return this.methods[n].getReturnType();
    }
    
    public Object trapFieldRead(final String s) {
        return this.getClassMetaobject().getJavaClass().getField(s).get(this.getObject());
    }
    
    public void trapFieldWrite(final String s, final Object o) {
        this.getClassMetaobject().getJavaClass().getField(s).set(this.getObject(), o);
    }
    
    public Object trapMethodcall(final int n, final Object[] array) throws Throwable {
        return this.methods[n].invoke(this.getObject(), array);
    }
}
