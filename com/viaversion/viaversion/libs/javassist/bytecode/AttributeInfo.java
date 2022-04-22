package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.*;
import java.util.*;

public class AttributeInfo
{
    protected ConstPool constPool;
    int name;
    byte[] info;
    
    protected AttributeInfo(final ConstPool constPool, final int name, final byte[] info) {
        this.constPool = constPool;
        this.name = name;
        this.info = info;
    }
    
    protected AttributeInfo(final ConstPool constPool, final String s) {
        this(constPool, s, null);
    }
    
    public AttributeInfo(final ConstPool constPool, final String s, final byte[] array) {
        this(constPool, constPool.addUtf8Info(s), array);
    }
    
    protected AttributeInfo(final ConstPool constPool, final int name, final DataInputStream dataInputStream) throws IOException {
        this.constPool = constPool;
        this.name = name;
        final int int1 = dataInputStream.readInt();
        this.info = new byte[int1];
        if (int1 > 0) {
            dataInputStream.readFully(this.info);
        }
    }
    
    static AttributeInfo read(final ConstPool constPool, final DataInputStream dataInputStream) throws IOException {
        final int unsignedShort = dataInputStream.readUnsignedShort();
        final String utf8Info = constPool.getUtf8Info(unsignedShort);
        final char char1 = utf8Info.charAt(0);
        if (char1 < 'E') {
            if (utf8Info.equals("AnnotationDefault")) {
                return new AnnotationDefaultAttribute(constPool, unsignedShort, dataInputStream);
            }
            if (utf8Info.equals("BootstrapMethods")) {
                return new BootstrapMethodsAttribute(constPool, unsignedShort, dataInputStream);
            }
            if (utf8Info.equals("Code")) {
                return new CodeAttribute(constPool, unsignedShort, dataInputStream);
            }
            if (utf8Info.equals("ConstantValue")) {
                return new ConstantAttribute(constPool, unsignedShort, dataInputStream);
            }
            if (utf8Info.equals("Deprecated")) {
                return new DeprecatedAttribute(constPool, unsignedShort, dataInputStream);
            }
        }
        if (char1 < 'M') {
            if (utf8Info.equals("EnclosingMethod")) {
                return new EnclosingMethodAttribute(constPool, unsignedShort, dataInputStream);
            }
            if (utf8Info.equals("Exceptions")) {
                return new ExceptionsAttribute(constPool, unsignedShort, dataInputStream);
            }
            if (utf8Info.equals("InnerClasses")) {
                return new InnerClassesAttribute(constPool, unsignedShort, dataInputStream);
            }
            if (utf8Info.equals("LineNumberTable")) {
                return new LineNumberAttribute(constPool, unsignedShort, dataInputStream);
            }
            if (utf8Info.equals("LocalVariableTable")) {
                return new LocalVariableAttribute(constPool, unsignedShort, dataInputStream);
            }
            if (utf8Info.equals("LocalVariableTypeTable")) {
                return new LocalVariableTypeAttribute(constPool, unsignedShort, dataInputStream);
            }
        }
        if (char1 < 'S') {
            if (utf8Info.equals("MethodParameters")) {
                return new MethodParametersAttribute(constPool, unsignedShort, dataInputStream);
            }
            if (utf8Info.equals("NestHost")) {
                return new NestHostAttribute(constPool, unsignedShort, dataInputStream);
            }
            if (utf8Info.equals("NestMembers")) {
                return new NestMembersAttribute(constPool, unsignedShort, dataInputStream);
            }
            if (utf8Info.equals("RuntimeVisibleAnnotations") || utf8Info.equals("RuntimeInvisibleAnnotations")) {
                return new AnnotationsAttribute(constPool, unsignedShort, dataInputStream);
            }
            if (utf8Info.equals("RuntimeVisibleParameterAnnotations") || utf8Info.equals("RuntimeInvisibleParameterAnnotations")) {
                return new ParameterAnnotationsAttribute(constPool, unsignedShort, dataInputStream);
            }
            if (utf8Info.equals("RuntimeVisibleTypeAnnotations") || utf8Info.equals("RuntimeInvisibleTypeAnnotations")) {
                return new TypeAnnotationsAttribute(constPool, unsignedShort, dataInputStream);
            }
        }
        if (char1 >= 'S') {
            if (utf8Info.equals("Signature")) {
                return new SignatureAttribute(constPool, unsignedShort, dataInputStream);
            }
            if (utf8Info.equals("SourceFile")) {
                return new SourceFileAttribute(constPool, unsignedShort, dataInputStream);
            }
            if (utf8Info.equals("Synthetic")) {
                return new SyntheticAttribute(constPool, unsignedShort, dataInputStream);
            }
            if (utf8Info.equals("StackMap")) {
                return new StackMap(constPool, unsignedShort, dataInputStream);
            }
            if (utf8Info.equals("StackMapTable")) {
                return new StackMapTable(constPool, unsignedShort, dataInputStream);
            }
        }
        return new AttributeInfo(constPool, unsignedShort, dataInputStream);
    }
    
    public String getName() {
        return this.constPool.getUtf8Info(this.name);
    }
    
    public ConstPool getConstPool() {
        return this.constPool;
    }
    
    public int length() {
        return this.info.length + 6;
    }
    
    public byte[] get() {
        return this.info;
    }
    
    public void set(final byte[] info) {
        this.info = info;
    }
    
    public AttributeInfo copy(final ConstPool constPool, final Map map) {
        return new AttributeInfo(constPool, this.getName(), Arrays.copyOf(this.info, this.info.length));
    }
    
    void write(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(this.name);
        dataOutputStream.writeInt(this.info.length);
        if (this.info.length > 0) {
            dataOutputStream.write(this.info);
        }
    }
    
    static int getLength(final List list) {
        final Iterator<AttributeInfo> iterator = list.iterator();
        while (iterator.hasNext()) {
            final int n = 0 + iterator.next().length();
        }
        return 0;
    }
    
    static AttributeInfo lookup(final List list, final String s) {
        if (list == null) {
            return null;
        }
        for (final AttributeInfo attributeInfo : list) {
            if (attributeInfo.getName().equals(s)) {
                return attributeInfo;
            }
        }
        return null;
    }
    
    static synchronized AttributeInfo remove(final List list, final String s) {
        if (list == null) {
            return null;
        }
        for (final AttributeInfo attributeInfo : list) {
            if (attributeInfo.getName().equals(s) && list.remove(attributeInfo)) {
                return attributeInfo;
            }
        }
        return null;
    }
    
    static void writeAll(final List list, final DataOutputStream dataOutputStream) throws IOException {
        if (list == null) {
            return;
        }
        final Iterator<AttributeInfo> iterator = list.iterator();
        while (iterator.hasNext()) {
            iterator.next().write(dataOutputStream);
        }
    }
    
    static List copyAll(final List list, final ConstPool constPool) {
        if (list == null) {
            return null;
        }
        final ArrayList<AttributeInfo> list2 = new ArrayList<AttributeInfo>();
        final Iterator<AttributeInfo> iterator = list.iterator();
        while (iterator.hasNext()) {
            list2.add(iterator.next().copy(constPool, null));
        }
        return list2;
    }
    
    void renameClass(final String s, final String s2) {
    }
    
    void renameClass(final Map map) {
    }
    
    static void renameClass(final List list, final String s, final String s2) {
        if (list == null) {
            return;
        }
        final Iterator<AttributeInfo> iterator = list.iterator();
        while (iterator.hasNext()) {
            iterator.next().renameClass(s, s2);
        }
    }
    
    static void renameClass(final List list, final Map map) {
        if (list == null) {
            return;
        }
        final Iterator<AttributeInfo> iterator = list.iterator();
        while (iterator.hasNext()) {
            iterator.next().renameClass(map);
        }
    }
    
    void getRefClasses(final Map map) {
    }
    
    static void getRefClasses(final List list, final Map map) {
        if (list == null) {
            return;
        }
        final Iterator<AttributeInfo> iterator = list.iterator();
        while (iterator.hasNext()) {
            iterator.next().getRefClasses(map);
        }
    }
}
