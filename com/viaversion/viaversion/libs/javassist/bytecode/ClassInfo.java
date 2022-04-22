package com.viaversion.viaversion.libs.javassist.bytecode;

import java.util.*;
import java.io.*;

class ClassInfo extends ConstInfo
{
    static final int tag = 7;
    int name;
    
    public ClassInfo(final int name, final int n) {
        super(n);
        this.name = name;
    }
    
    public ClassInfo(final DataInputStream dataInputStream, final int n) throws IOException {
        super(n);
        this.name = dataInputStream.readUnsignedShort();
    }
    
    @Override
    public int hashCode() {
        return this.name;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof ClassInfo && ((ClassInfo)o).name == this.name;
    }
    
    @Override
    public int getTag() {
        return 7;
    }
    
    @Override
    public String getClassName(final ConstPool constPool) {
        return constPool.getUtf8Info(this.name);
    }
    
    @Override
    public void renameClass(final ConstPool constPool, final String s, final String s2, final Map map) {
        final String utf8Info = constPool.getUtf8Info(this.name);
        String s3 = null;
        if (utf8Info.equals(s)) {
            s3 = s2;
        }
        else if (utf8Info.charAt(0) == '[') {
            final String rename = Descriptor.rename(utf8Info, s, s2);
            if (utf8Info != rename) {
                s3 = rename;
            }
        }
        if (s3 != null) {
            if (map == null) {
                this.name = constPool.addUtf8Info(s3);
            }
            else {
                map.remove(this);
                this.name = constPool.addUtf8Info(s3);
                map.put(this, this);
            }
        }
    }
    
    @Override
    public void renameClass(final ConstPool constPool, final Map map, final Map map2) {
        final String utf8Info = constPool.getUtf8Info(this.name);
        String s = null;
        if (utf8Info.charAt(0) == '[') {
            final String rename = Descriptor.rename(utf8Info, map);
            if (utf8Info != rename) {
                s = rename;
            }
        }
        else {
            final String s2 = map.get(utf8Info);
            if (s2 != null && !s2.equals(utf8Info)) {
                s = s2;
            }
        }
        if (s != null) {
            if (map2 == null) {
                this.name = constPool.addUtf8Info(s);
            }
            else {
                map2.remove(this);
                this.name = constPool.addUtf8Info(s);
                map2.put(this, this);
            }
        }
    }
    
    @Override
    public int copy(final ConstPool constPool, final ConstPool constPool2, final Map map) {
        String utf8Info = constPool.getUtf8Info(this.name);
        if (map != null) {
            final String s = map.get(utf8Info);
            if (s != null) {
                utf8Info = s;
            }
        }
        return constPool2.addClassInfo(utf8Info);
    }
    
    @Override
    public void write(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(7);
        dataOutputStream.writeShort(this.name);
    }
    
    @Override
    public void print(final PrintWriter printWriter) {
        printWriter.print("Class #");
        printWriter.println(this.name);
    }
}
