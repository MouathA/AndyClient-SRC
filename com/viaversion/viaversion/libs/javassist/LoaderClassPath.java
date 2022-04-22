package com.viaversion.viaversion.libs.javassist;

import java.lang.ref.*;
import java.io.*;
import java.net.*;

public class LoaderClassPath implements ClassPath
{
    private Reference clref;
    
    public LoaderClassPath(final ClassLoader classLoader) {
        this.clref = new WeakReference(classLoader);
    }
    
    @Override
    public String toString() {
        return (this.clref.get() == null) ? "<null>" : this.clref.get().toString();
    }
    
    @Override
    public InputStream openClassfile(final String s) throws NotFoundException {
        final String string = s.replace('.', '/') + ".class";
        final ClassLoader classLoader = this.clref.get();
        if (classLoader == null) {
            return null;
        }
        return classLoader.getResourceAsStream(string);
    }
    
    @Override
    public URL find(final String s) {
        final String string = s.replace('.', '/') + ".class";
        final ClassLoader classLoader = this.clref.get();
        if (classLoader == null) {
            return null;
        }
        return classLoader.getResource(string);
    }
}
