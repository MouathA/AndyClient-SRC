package com.viaversion.viaversion.libs.javassist;

import java.security.*;
import java.net.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;
import java.util.*;
import java.io.*;

public class Loader extends ClassLoader
{
    private HashMap notDefinedHere;
    private Vector notDefinedPackages;
    private ClassPool source;
    private Translator translator;
    private ProtectionDomain domain;
    public boolean doDelegation;
    
    public Loader() {
        this((ClassPool)null);
    }
    
    public Loader(final ClassPool classPool) {
        this.doDelegation = true;
        this.init(classPool);
    }
    
    public Loader(final ClassLoader classLoader, final ClassPool classPool) {
        super(classLoader);
        this.doDelegation = true;
        this.init(classPool);
    }
    
    private void init(final ClassPool source) {
        this.notDefinedHere = new HashMap();
        this.notDefinedPackages = new Vector();
        this.source = source;
        this.translator = null;
        this.domain = null;
        this.delegateLoadingOf("com.viaversion.viaversion.libs.javassist.Loader");
    }
    
    public void delegateLoadingOf(final String s) {
        if (s.endsWith(".")) {
            this.notDefinedPackages.addElement(s);
        }
        else {
            this.notDefinedHere.put(s, this);
        }
    }
    
    public void setDomain(final ProtectionDomain domain) {
        this.domain = domain;
    }
    
    public void setClassPool(final ClassPool source) {
        this.source = source;
    }
    
    public void addTranslator(final ClassPool source, final Translator translator) throws NotFoundException, CannotCompileException {
        this.source = source;
        (this.translator = translator).start(source);
    }
    
    public static void main(final String[] array) throws Throwable {
        new Loader().run(array);
    }
    
    public void run(final String[] array) throws Throwable {
        if (array.length >= 1) {
            this.run(array[0], Arrays.copyOfRange(array, 1, array.length));
        }
    }
    
    public void run(final String s, final String[] array) throws Throwable {
        this.loadClass(s).getDeclaredMethod("main", String[].class).invoke(null, array);
    }
    
    @Override
    protected Class loadClass(String intern, final boolean b) throws ClassFormatError, ClassNotFoundException {
        intern = intern.intern();
        // monitorenter(s = intern)
        Class<?> clazz = this.findLoadedClass(intern);
        if (clazz == null) {
            clazz = (Class<?>)this.loadClassByDelegation(intern);
        }
        if (clazz == null) {
            clazz = (Class<?>)this.findClass(intern);
        }
        if (clazz == null) {
            clazz = (Class<?>)this.delegateToParent(intern);
        }
        if (b) {
            this.resolveClass(clazz);
        }
        // monitorexit(s)
        return clazz;
    }
    
    @Override
    protected Class findClass(final String s) throws ClassNotFoundException {
        byte[] array;
        if (this.source != null) {
            if (this.translator != null) {
                this.translator.onLoad(this.source, s);
            }
            array = this.source.get(s).toBytecode();
        }
        else {
            final InputStream resourceAsStream = this.getClass().getResourceAsStream("/" + s.replace('.', '/') + ".class");
            if (resourceAsStream == null) {
                return null;
            }
            array = ClassPoolTail.readStream(resourceAsStream);
        }
        final int lastIndex = s.lastIndexOf(46);
        if (lastIndex != -1) {
            final String substring = s.substring(0, lastIndex);
            if (this.isDefinedPackage(substring)) {
                this.definePackage(substring, null, null, null, null, null, null, null);
            }
        }
        if (this.domain == null) {
            return this.defineClass(s, array, 0, array.length);
        }
        return this.defineClass(s, array, 0, array.length, this.domain);
    }
    
    private boolean isDefinedPackage(final String s) {
        if (ClassFile.MAJOR_VERSION >= 53) {
            return this.getDefinedPackage(s) == null;
        }
        return this.getPackage(s) == null;
    }
    
    protected Class loadClassByDelegation(final String s) throws ClassNotFoundException {
        Class delegateToParent = null;
        if (this.doDelegation && (s.startsWith("java.") || s.startsWith("javax.") || s.startsWith("sun.") || s.startsWith("com.sun.") || s.startsWith("org.w3c.") || s.startsWith("org.xml.") || this.notDelegated(s))) {
            delegateToParent = this.delegateToParent(s);
        }
        return delegateToParent;
    }
    
    private boolean notDelegated(final String s) {
        if (this.notDefinedHere.containsKey(s)) {
            return true;
        }
        final Iterator<String> iterator = this.notDefinedPackages.iterator();
        while (iterator.hasNext()) {
            if (s.startsWith(iterator.next())) {
                return true;
            }
        }
        return false;
    }
    
    protected Class delegateToParent(final String s) throws ClassNotFoundException {
        final ClassLoader parent = this.getParent();
        if (parent != null) {
            return parent.loadClass(s);
        }
        return this.findSystemClass(s);
    }
    
    public static class Simple extends ClassLoader
    {
        public Simple() {
        }
        
        public Simple(final ClassLoader classLoader) {
            super(classLoader);
        }
        
        public Class invokeDefineClass(final CtClass ctClass) throws IOException, CannotCompileException {
            final byte[] bytecode = ctClass.toBytecode();
            return this.defineClass(ctClass.getName(), bytecode, 0, bytecode.length);
        }
    }
}
