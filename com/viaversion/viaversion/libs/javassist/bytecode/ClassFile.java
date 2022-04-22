package com.viaversion.viaversion.libs.javassist.bytecode;

import com.viaversion.viaversion.libs.javassist.*;
import java.io.*;
import java.util.*;

public final class ClassFile
{
    int major;
    int minor;
    ConstPool constPool;
    int thisClass;
    int accessFlags;
    int superClass;
    int[] interfaces;
    List fields;
    List methods;
    List attributes;
    String thisclassname;
    String[] cachedInterfaces;
    String cachedSuperclass;
    public static final int JAVA_1 = 45;
    public static final int JAVA_2 = 46;
    public static final int JAVA_3 = 47;
    public static final int JAVA_4 = 48;
    public static final int JAVA_5 = 49;
    public static final int JAVA_6 = 50;
    public static final int JAVA_7 = 51;
    public static final int JAVA_8 = 52;
    public static final int JAVA_9 = 53;
    public static final int JAVA_10 = 54;
    public static final int JAVA_11 = 55;
    public static final int MAJOR_VERSION;
    
    public ClassFile(final DataInputStream dataInputStream) throws IOException {
        this.read(dataInputStream);
    }
    
    public ClassFile(final boolean b, final String thisclassname, final String s) {
        this.major = ClassFile.MAJOR_VERSION;
        this.minor = 0;
        this.constPool = new ConstPool(thisclassname);
        this.thisClass = this.constPool.getThisClassInfo();
        if (b) {
            this.accessFlags = 1536;
        }
        else {
            this.accessFlags = 32;
        }
        this.initSuperclass(s);
        this.interfaces = null;
        this.fields = new ArrayList();
        this.methods = new ArrayList();
        this.thisclassname = thisclassname;
        (this.attributes = new ArrayList()).add(new SourceFileAttribute(this.constPool, getSourcefileName(this.thisclassname)));
    }
    
    private void initSuperclass(final String cachedSuperclass) {
        if (cachedSuperclass != null) {
            this.superClass = this.constPool.addClassInfo(cachedSuperclass);
            this.cachedSuperclass = cachedSuperclass;
        }
        else {
            this.superClass = this.constPool.addClassInfo("java.lang.Object");
            this.cachedSuperclass = "java.lang.Object";
        }
    }
    
    private static String getSourcefileName(final String s) {
        return s.replaceAll("^.*\\.", "") + ".java";
    }
    
    public void compact() {
        final ConstPool compact0 = this.compact0();
        final Iterator<MethodInfo> iterator = this.methods.iterator();
        while (iterator.hasNext()) {
            iterator.next().compact(compact0);
        }
        final Iterator<FieldInfo> iterator2 = this.fields.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().compact(compact0);
        }
        this.attributes = AttributeInfo.copyAll(this.attributes, compact0);
        this.constPool = compact0;
    }
    
    private ConstPool compact0() {
        final ConstPool constPool = new ConstPool(this.thisclassname);
        this.thisClass = constPool.getThisClassInfo();
        if (this.getSuperclass() != null) {
            this.superClass = constPool.addClassInfo(this.getSuperclass());
        }
        if (this.interfaces != null) {
            while (0 < this.interfaces.length) {
                this.interfaces[0] = constPool.addClassInfo(this.constPool.getClassInfo(this.interfaces[0]));
                int n = 0;
                ++n;
            }
        }
        return constPool;
    }
    
    public void prune() {
        final ConstPool compact0 = this.compact0();
        final ArrayList<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        final AttributeInfo attribute = this.getAttribute("RuntimeInvisibleAnnotations");
        if (attribute != null) {
            attributes.add(attribute.copy(compact0, null));
        }
        final AttributeInfo attribute2 = this.getAttribute("RuntimeVisibleAnnotations");
        if (attribute2 != null) {
            attributes.add(attribute2.copy(compact0, null));
        }
        final AttributeInfo attribute3 = this.getAttribute("Signature");
        if (attribute3 != null) {
            attributes.add(attribute3.copy(compact0, null));
        }
        final Iterator<MethodInfo> iterator = this.methods.iterator();
        while (iterator.hasNext()) {
            iterator.next().prune(compact0);
        }
        final Iterator<FieldInfo> iterator2 = this.fields.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().prune(compact0);
        }
        this.attributes = attributes;
        this.constPool = compact0;
    }
    
    public ConstPool getConstPool() {
        return this.constPool;
    }
    
    public boolean isInterface() {
        return (this.accessFlags & 0x200) != 0x0;
    }
    
    public boolean isFinal() {
        return (this.accessFlags & 0x10) != 0x0;
    }
    
    public boolean isAbstract() {
        return (this.accessFlags & 0x400) != 0x0;
    }
    
    public int getAccessFlags() {
        return this.accessFlags;
    }
    
    public void setAccessFlags(int accessFlags) {
        if ((accessFlags & 0x200) == 0x0) {
            accessFlags |= 0x20;
        }
        this.accessFlags = accessFlags;
    }
    
    public int getInnerAccessFlags() {
        final InnerClassesAttribute innerClassesAttribute = (InnerClassesAttribute)this.getAttribute("InnerClasses");
        if (innerClassesAttribute == null) {
            return -1;
        }
        final String name = this.getName();
        while (0 < innerClassesAttribute.tableLength()) {
            if (name.equals(innerClassesAttribute.innerClass(0))) {
                return innerClassesAttribute.accessFlags(0);
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    public String getName() {
        return this.thisclassname;
    }
    
    public void setName(final String s) {
        this.renameClass(this.thisclassname, s);
    }
    
    public String getSuperclass() {
        if (this.cachedSuperclass == null) {
            this.cachedSuperclass = this.constPool.getClassInfo(this.superClass);
        }
        return this.cachedSuperclass;
    }
    
    public int getSuperclassId() {
        return this.superClass;
    }
    
    public void setSuperclass(String s) throws CannotCompileException {
        if (s == null) {
            s = "java.lang.Object";
        }
        this.superClass = this.constPool.addClassInfo(s);
        final Iterator<MethodInfo> iterator = this.methods.iterator();
        while (iterator.hasNext()) {
            iterator.next().setSuperclass(s);
        }
        this.cachedSuperclass = s;
    }
    
    public final void renameClass(String jvmName, String jvmName2) {
        if (jvmName.equals(jvmName2)) {
            return;
        }
        if (jvmName.equals(this.thisclassname)) {
            this.thisclassname = jvmName2;
        }
        jvmName = Descriptor.toJvmName(jvmName);
        jvmName2 = Descriptor.toJvmName(jvmName2);
        this.constPool.renameClass(jvmName, jvmName2);
        AttributeInfo.renameClass(this.attributes, jvmName, jvmName2);
        for (final MethodInfo methodInfo : this.methods) {
            methodInfo.setDescriptor(Descriptor.rename(methodInfo.getDescriptor(), jvmName, jvmName2));
            AttributeInfo.renameClass(methodInfo.getAttributes(), jvmName, jvmName2);
        }
        for (final FieldInfo fieldInfo : this.fields) {
            fieldInfo.setDescriptor(Descriptor.rename(fieldInfo.getDescriptor(), jvmName, jvmName2));
            AttributeInfo.renameClass(fieldInfo.getAttributes(), jvmName, jvmName2);
        }
    }
    
    public final void renameClass(final Map map) {
        final String s = map.get(Descriptor.toJvmName(this.thisclassname));
        if (s != null) {
            this.thisclassname = Descriptor.toJavaName(s);
        }
        this.constPool.renameClass(map);
        AttributeInfo.renameClass(this.attributes, map);
        for (final MethodInfo methodInfo : this.methods) {
            methodInfo.setDescriptor(Descriptor.rename(methodInfo.getDescriptor(), map));
            AttributeInfo.renameClass(methodInfo.getAttributes(), map);
        }
        for (final FieldInfo fieldInfo : this.fields) {
            fieldInfo.setDescriptor(Descriptor.rename(fieldInfo.getDescriptor(), map));
            AttributeInfo.renameClass(fieldInfo.getAttributes(), map);
        }
    }
    
    public final void getRefClasses(final Map map) {
        this.constPool.renameClass(map);
        AttributeInfo.getRefClasses(this.attributes, map);
        for (final MethodInfo methodInfo : this.methods) {
            Descriptor.rename(methodInfo.getDescriptor(), map);
            AttributeInfo.getRefClasses(methodInfo.getAttributes(), map);
        }
        for (final FieldInfo fieldInfo : this.fields) {
            Descriptor.rename(fieldInfo.getDescriptor(), map);
            AttributeInfo.getRefClasses(fieldInfo.getAttributes(), map);
        }
    }
    
    public String[] getInterfaces() {
        if (this.cachedInterfaces != null) {
            return this.cachedInterfaces;
        }
        String[] cachedInterfaces;
        if (this.interfaces == null) {
            cachedInterfaces = new String[0];
        }
        else {
            final String[] array = new String[this.interfaces.length];
            while (0 < this.interfaces.length) {
                array[0] = this.constPool.getClassInfo(this.interfaces[0]);
                int n = 0;
                ++n;
            }
            cachedInterfaces = array;
        }
        return this.cachedInterfaces = cachedInterfaces;
    }
    
    public void setInterfaces(final String[] array) {
        this.cachedInterfaces = null;
        if (array != null) {
            this.interfaces = new int[array.length];
            while (0 < array.length) {
                this.interfaces[0] = this.constPool.addClassInfo(array[0]);
                int n = 0;
                ++n;
            }
        }
    }
    
    public void addInterface(final String s) {
        this.cachedInterfaces = null;
        final int addClassInfo = this.constPool.addClassInfo(s);
        if (this.interfaces == null) {
            (this.interfaces = new int[1])[0] = addClassInfo;
        }
        else {
            final int length = this.interfaces.length;
            final int[] interfaces = new int[length + 1];
            System.arraycopy(this.interfaces, 0, interfaces, 0, length);
            interfaces[length] = addClassInfo;
            this.interfaces = interfaces;
        }
    }
    
    public List getFields() {
        return this.fields;
    }
    
    public void addField(final FieldInfo fieldInfo) throws DuplicateMemberException {
        this.testExistingField(fieldInfo.getName(), fieldInfo.getDescriptor());
        this.fields.add(fieldInfo);
    }
    
    public final void addField2(final FieldInfo fieldInfo) {
        this.fields.add(fieldInfo);
    }
    
    private void testExistingField(final String s, final String s2) throws DuplicateMemberException {
        final Iterator<FieldInfo> iterator = this.fields.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equals(s)) {
                throw new DuplicateMemberException("duplicate field: " + s);
            }
        }
    }
    
    public List getMethods() {
        return this.methods;
    }
    
    public MethodInfo getMethod(final String s) {
        for (final MethodInfo methodInfo : this.methods) {
            if (methodInfo.getName().equals(s)) {
                return methodInfo;
            }
        }
        return null;
    }
    
    public MethodInfo getStaticInitializer() {
        return this.getMethod("<clinit>");
    }
    
    public void addMethod(final MethodInfo methodInfo) throws DuplicateMemberException {
        this.testExistingMethod(methodInfo);
        this.methods.add(methodInfo);
    }
    
    public final void addMethod2(final MethodInfo methodInfo) {
        this.methods.add(methodInfo);
    }
    
    private void testExistingMethod(final MethodInfo methodInfo) throws DuplicateMemberException {
        final String name = methodInfo.getName();
        final String descriptor = methodInfo.getDescriptor();
        final ListIterator<MethodInfo> listIterator = (ListIterator<MethodInfo>)this.methods.listIterator(0);
        while (listIterator.hasNext()) {
            if (isDuplicated(methodInfo, name, descriptor, listIterator.next(), listIterator)) {
                throw new DuplicateMemberException("duplicate method: " + name + " in " + this.getName());
            }
        }
    }
    
    private static boolean isDuplicated(final MethodInfo methodInfo, final String s, final String s2, final MethodInfo methodInfo2, final ListIterator listIterator) {
        if (!methodInfo2.getName().equals(s)) {
            return false;
        }
        final String descriptor = methodInfo2.getDescriptor();
        if (!Descriptor.eqParamTypes(descriptor, s2)) {
            return false;
        }
        if (!descriptor.equals(s2)) {
            return false;
        }
        if (notBridgeMethod(methodInfo2)) {
            return true;
        }
        listIterator.remove();
        return false;
    }
    
    private static boolean notBridgeMethod(final MethodInfo methodInfo) {
        return (methodInfo.getAccessFlags() & 0x40) == 0x0;
    }
    
    public List getAttributes() {
        return this.attributes;
    }
    
    public AttributeInfo getAttribute(final String s) {
        for (final AttributeInfo attributeInfo : this.attributes) {
            if (attributeInfo.getName().equals(s)) {
                return attributeInfo;
            }
        }
        return null;
    }
    
    public AttributeInfo removeAttribute(final String s) {
        return AttributeInfo.remove(this.attributes, s);
    }
    
    public void addAttribute(final AttributeInfo attributeInfo) {
        AttributeInfo.remove(this.attributes, attributeInfo.getName());
        this.attributes.add(attributeInfo);
    }
    
    public String getSourceFile() {
        final SourceFileAttribute sourceFileAttribute = (SourceFileAttribute)this.getAttribute("SourceFile");
        if (sourceFileAttribute == null) {
            return null;
        }
        return sourceFileAttribute.getFileName();
    }
    
    private void read(final DataInputStream dataInputStream) throws IOException {
        final int int1 = dataInputStream.readInt();
        if (int1 != -889275714) {
            throw new IOException("bad magic number: " + Integer.toHexString(int1));
        }
        this.minor = dataInputStream.readUnsignedShort();
        this.major = dataInputStream.readUnsignedShort();
        this.constPool = new ConstPool(dataInputStream);
        this.accessFlags = dataInputStream.readUnsignedShort();
        this.thisClass = dataInputStream.readUnsignedShort();
        this.constPool.setThisClassInfo(this.thisClass);
        this.superClass = dataInputStream.readUnsignedShort();
        final int unsignedShort = dataInputStream.readUnsignedShort();
        int n = 0;
        if (unsignedShort == 0) {
            this.interfaces = null;
        }
        else {
            this.interfaces = new int[unsignedShort];
            while (0 < unsignedShort) {
                this.interfaces[0] = dataInputStream.readUnsignedShort();
                ++n;
            }
        }
        final ConstPool constPool = this.constPool;
        final int unsignedShort2 = dataInputStream.readUnsignedShort();
        this.fields = new ArrayList();
        while (0 < unsignedShort2) {
            this.addField2(new FieldInfo(constPool, dataInputStream));
            ++n;
        }
        final int unsignedShort3 = dataInputStream.readUnsignedShort();
        this.methods = new ArrayList();
        while (0 < unsignedShort3) {
            this.addMethod2(new MethodInfo(constPool, dataInputStream));
            ++n;
        }
        this.attributes = new ArrayList();
        while (0 < dataInputStream.readUnsignedShort()) {
            this.addAttribute(AttributeInfo.read(constPool, dataInputStream));
            ++n;
        }
        this.thisclassname = this.constPool.getClassInfo(this.thisClass);
    }
    
    public void write(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(-889275714);
        dataOutputStream.writeShort(this.minor);
        dataOutputStream.writeShort(this.major);
        this.constPool.write(dataOutputStream);
        dataOutputStream.writeShort(this.accessFlags);
        dataOutputStream.writeShort(this.thisClass);
        dataOutputStream.writeShort(this.superClass);
        if (this.interfaces != null) {
            final int length = this.interfaces.length;
        }
        dataOutputStream.writeShort(0);
        int n = 0;
        while (0 < 0) {
            dataOutputStream.writeShort(this.interfaces[0]);
            ++n;
        }
        this.fields.size();
        dataOutputStream.writeShort(0);
        while (0 < 0) {
            this.fields.get(0).write(dataOutputStream);
            ++n;
        }
        dataOutputStream.writeShort(this.methods.size());
        final Iterator<MethodInfo> iterator = this.methods.iterator();
        while (iterator.hasNext()) {
            iterator.next().write(dataOutputStream);
        }
        dataOutputStream.writeShort(this.attributes.size());
        AttributeInfo.writeAll(this.attributes, dataOutputStream);
    }
    
    public int getMajorVersion() {
        return this.major;
    }
    
    public void setMajorVersion(final int major) {
        this.major = major;
    }
    
    public int getMinorVersion() {
        return this.minor;
    }
    
    public void setMinorVersion(final int minor) {
        this.minor = minor;
    }
    
    public void setVersionToJava5() {
        this.major = 49;
        this.minor = 0;
    }
    
    static {
        Class.forName("java.lang.StringBuilder");
        Class.forName("java.util.zip.DeflaterInputStream");
        Class.forName("java.lang.invoke.CallSite", false, ClassLoader.getSystemClassLoader());
        Class.forName("java.util.function.Function");
        Class.forName("java.lang.Module");
        List.class.getMethod("copyOf", Collection.class);
        Class.forName("java.util.Optional").getMethod("isEmpty", (Class<?>[])new Class[0]);
        MAJOR_VERSION = 55;
    }
}
