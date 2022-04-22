package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import java.io.*;

abstract class ConstInfo
{
    int index;
    
    public ConstInfo(final int index) {
        this.index = index;
    }
    
    public abstract int getTag();
    
    public String getClassName(final ConstPool constPool) {
        return null;
    }
    
    public void renameClass(final ConstPool constPool, final String s, final String s2, final Map map) {
    }
    
    public void renameClass(final ConstPool constPool, final Map map, final Map map2) {
    }
    
    public abstract int copy(final ConstPool p0, final ConstPool p1, final Map p2);
    
    public abstract void write(final DataOutputStream p0) throws IOException;
    
    public abstract void print(final PrintWriter p0);
    
    @Override
    public String toString() {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.print(new PrintWriter(byteArrayOutputStream));
        return byteArrayOutputStream.toString();
    }
}
