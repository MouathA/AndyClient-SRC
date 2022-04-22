package com.viaversion.viaversion.libs.javassist;

import java.io.*;
import java.net.*;

public class ClassClassPath implements ClassPath
{
    private Class thisClass;
    
    public ClassClassPath(final Class thisClass) {
        this.thisClass = thisClass;
    }
    
    ClassClassPath() {
        this(Object.class);
    }
    
    @Override
    public InputStream openClassfile(final String s) throws NotFoundException {
        return this.thisClass.getResourceAsStream('/' + s.replace('.', '/') + ".class");
    }
    
    @Override
    public URL find(final String s) {
        return this.thisClass.getResource('/' + s.replace('.', '/') + ".class");
    }
    
    @Override
    public String toString() {
        return this.thisClass.getName() + ".class";
    }
}
