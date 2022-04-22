package com.viaversion.viaversion.libs.javassist.bytecode;

import com.viaversion.viaversion.libs.javassist.*;
import java.util.*;
import java.io.*;

public final class ConstPool
{
    LongVector items;
    int numOfItems;
    int thisClassInfo;
    Map itemsCache;
    public static final int CONST_Class = 7;
    public static final int CONST_Fieldref = 9;
    public static final int CONST_Methodref = 10;
    public static final int CONST_InterfaceMethodref = 11;
    public static final int CONST_String = 8;
    public static final int CONST_Integer = 3;
    public static final int CONST_Float = 4;
    public static final int CONST_Long = 5;
    public static final int CONST_Double = 6;
    public static final int CONST_NameAndType = 12;
    public static final int CONST_Utf8 = 1;
    public static final int CONST_MethodHandle = 15;
    public static final int CONST_MethodType = 16;
    public static final int CONST_Dynamic = 17;
    public static final int CONST_DynamicCallSite = 18;
    public static final int CONST_InvokeDynamic = 18;
    public static final int CONST_Module = 19;
    public static final int CONST_Package = 20;
    public static final CtClass THIS;
    public static final int REF_getField = 1;
    public static final int REF_getStatic = 2;
    public static final int REF_putField = 3;
    public static final int REF_putStatic = 4;
    public static final int REF_invokeVirtual = 5;
    public static final int REF_invokeStatic = 6;
    public static final int REF_invokeSpecial = 7;
    public static final int REF_newInvokeSpecial = 8;
    public static final int REF_invokeInterface = 9;
    
    public ConstPool(final String s) {
        this.items = new LongVector();
        this.itemsCache = null;
        this.numOfItems = 0;
        this.addItem0(null);
        this.thisClassInfo = this.addClassInfo(s);
    }
    
    public ConstPool(final DataInputStream dataInputStream) throws IOException {
        this.itemsCache = null;
        this.thisClassInfo = 0;
        this.read(dataInputStream);
    }
    
    void prune() {
        this.itemsCache = null;
    }
    
    public int getSize() {
        return this.numOfItems;
    }
    
    public String getClassName() {
        return this.getClassInfo(this.thisClassInfo);
    }
    
    public int getThisClassInfo() {
        return this.thisClassInfo;
    }
    
    void setThisClassInfo(final int thisClassInfo) {
        this.thisClassInfo = thisClassInfo;
    }
    
    ConstInfo getItem(final int n) {
        return this.items.elementAt(n);
    }
    
    public int getTag(final int n) {
        return this.getItem(n).getTag();
    }
    
    public String getClassInfo(final int n) {
        final ClassInfo classInfo = (ClassInfo)this.getItem(n);
        if (classInfo == null) {
            return null;
        }
        return Descriptor.toJavaName(this.getUtf8Info(classInfo.name));
    }
    
    public String getClassInfoByDescriptor(final int n) {
        final ClassInfo classInfo = (ClassInfo)this.getItem(n);
        if (classInfo == null) {
            return null;
        }
        final String utf8Info = this.getUtf8Info(classInfo.name);
        if (utf8Info.charAt(0) == '[') {
            return utf8Info;
        }
        return Descriptor.of(utf8Info);
    }
    
    public int getNameAndTypeName(final int n) {
        return ((NameAndTypeInfo)this.getItem(n)).memberName;
    }
    
    public int getNameAndTypeDescriptor(final int n) {
        return ((NameAndTypeInfo)this.getItem(n)).typeDescriptor;
    }
    
    public int getMemberClass(final int n) {
        return ((MemberrefInfo)this.getItem(n)).classIndex;
    }
    
    public int getMemberNameAndType(final int n) {
        return ((MemberrefInfo)this.getItem(n)).nameAndTypeIndex;
    }
    
    public int getFieldrefClass(final int n) {
        return ((FieldrefInfo)this.getItem(n)).classIndex;
    }
    
    public String getFieldrefClassName(final int n) {
        final FieldrefInfo fieldrefInfo = (FieldrefInfo)this.getItem(n);
        if (fieldrefInfo == null) {
            return null;
        }
        return this.getClassInfo(fieldrefInfo.classIndex);
    }
    
    public int getFieldrefNameAndType(final int n) {
        return ((FieldrefInfo)this.getItem(n)).nameAndTypeIndex;
    }
    
    public String getFieldrefName(final int n) {
        final FieldrefInfo fieldrefInfo = (FieldrefInfo)this.getItem(n);
        if (fieldrefInfo == null) {
            return null;
        }
        final NameAndTypeInfo nameAndTypeInfo = (NameAndTypeInfo)this.getItem(fieldrefInfo.nameAndTypeIndex);
        if (nameAndTypeInfo == null) {
            return null;
        }
        return this.getUtf8Info(nameAndTypeInfo.memberName);
    }
    
    public String getFieldrefType(final int n) {
        final FieldrefInfo fieldrefInfo = (FieldrefInfo)this.getItem(n);
        if (fieldrefInfo == null) {
            return null;
        }
        final NameAndTypeInfo nameAndTypeInfo = (NameAndTypeInfo)this.getItem(fieldrefInfo.nameAndTypeIndex);
        if (nameAndTypeInfo == null) {
            return null;
        }
        return this.getUtf8Info(nameAndTypeInfo.typeDescriptor);
    }
    
    public int getMethodrefClass(final int n) {
        return ((MemberrefInfo)this.getItem(n)).classIndex;
    }
    
    public String getMethodrefClassName(final int n) {
        final MemberrefInfo memberrefInfo = (MemberrefInfo)this.getItem(n);
        if (memberrefInfo == null) {
            return null;
        }
        return this.getClassInfo(memberrefInfo.classIndex);
    }
    
    public int getMethodrefNameAndType(final int n) {
        return ((MemberrefInfo)this.getItem(n)).nameAndTypeIndex;
    }
    
    public String getMethodrefName(final int n) {
        final MemberrefInfo memberrefInfo = (MemberrefInfo)this.getItem(n);
        if (memberrefInfo == null) {
            return null;
        }
        final NameAndTypeInfo nameAndTypeInfo = (NameAndTypeInfo)this.getItem(memberrefInfo.nameAndTypeIndex);
        if (nameAndTypeInfo == null) {
            return null;
        }
        return this.getUtf8Info(nameAndTypeInfo.memberName);
    }
    
    public String getMethodrefType(final int n) {
        final MemberrefInfo memberrefInfo = (MemberrefInfo)this.getItem(n);
        if (memberrefInfo == null) {
            return null;
        }
        final NameAndTypeInfo nameAndTypeInfo = (NameAndTypeInfo)this.getItem(memberrefInfo.nameAndTypeIndex);
        if (nameAndTypeInfo == null) {
            return null;
        }
        return this.getUtf8Info(nameAndTypeInfo.typeDescriptor);
    }
    
    public int getInterfaceMethodrefClass(final int n) {
        return ((MemberrefInfo)this.getItem(n)).classIndex;
    }
    
    public String getInterfaceMethodrefClassName(final int n) {
        return this.getClassInfo(((MemberrefInfo)this.getItem(n)).classIndex);
    }
    
    public int getInterfaceMethodrefNameAndType(final int n) {
        return ((MemberrefInfo)this.getItem(n)).nameAndTypeIndex;
    }
    
    public String getInterfaceMethodrefName(final int n) {
        final MemberrefInfo memberrefInfo = (MemberrefInfo)this.getItem(n);
        if (memberrefInfo == null) {
            return null;
        }
        final NameAndTypeInfo nameAndTypeInfo = (NameAndTypeInfo)this.getItem(memberrefInfo.nameAndTypeIndex);
        if (nameAndTypeInfo == null) {
            return null;
        }
        return this.getUtf8Info(nameAndTypeInfo.memberName);
    }
    
    public String getInterfaceMethodrefType(final int n) {
        final MemberrefInfo memberrefInfo = (MemberrefInfo)this.getItem(n);
        if (memberrefInfo == null) {
            return null;
        }
        final NameAndTypeInfo nameAndTypeInfo = (NameAndTypeInfo)this.getItem(memberrefInfo.nameAndTypeIndex);
        if (nameAndTypeInfo == null) {
            return null;
        }
        return this.getUtf8Info(nameAndTypeInfo.typeDescriptor);
    }
    
    public Object getLdcValue(final int n) {
        final ConstInfo item = this.getItem(n);
        Object o = null;
        if (item instanceof StringInfo) {
            o = this.getStringInfo(n);
        }
        else if (item instanceof FloatInfo) {
            o = this.getFloatInfo(n);
        }
        else if (item instanceof IntegerInfo) {
            o = this.getIntegerInfo(n);
        }
        else if (item instanceof LongInfo) {
            o = this.getLongInfo(n);
        }
        else if (item instanceof DoubleInfo) {
            o = this.getDoubleInfo(n);
        }
        return o;
    }
    
    public int getIntegerInfo(final int n) {
        return ((IntegerInfo)this.getItem(n)).value;
    }
    
    public float getFloatInfo(final int n) {
        return ((FloatInfo)this.getItem(n)).value;
    }
    
    public long getLongInfo(final int n) {
        return ((LongInfo)this.getItem(n)).value;
    }
    
    public double getDoubleInfo(final int n) {
        return ((DoubleInfo)this.getItem(n)).value;
    }
    
    public String getStringInfo(final int n) {
        return this.getUtf8Info(((StringInfo)this.getItem(n)).string);
    }
    
    public String getUtf8Info(final int n) {
        return ((Utf8Info)this.getItem(n)).string;
    }
    
    public int getMethodHandleKind(final int n) {
        return ((MethodHandleInfo)this.getItem(n)).refKind;
    }
    
    public int getMethodHandleIndex(final int n) {
        return ((MethodHandleInfo)this.getItem(n)).refIndex;
    }
    
    public int getMethodTypeInfo(final int n) {
        return ((MethodTypeInfo)this.getItem(n)).descriptor;
    }
    
    public int getInvokeDynamicBootstrap(final int n) {
        return ((InvokeDynamicInfo)this.getItem(n)).bootstrap;
    }
    
    public int getInvokeDynamicNameAndType(final int n) {
        return ((InvokeDynamicInfo)this.getItem(n)).nameAndType;
    }
    
    public String getInvokeDynamicType(final int n) {
        final InvokeDynamicInfo invokeDynamicInfo = (InvokeDynamicInfo)this.getItem(n);
        if (invokeDynamicInfo == null) {
            return null;
        }
        final NameAndTypeInfo nameAndTypeInfo = (NameAndTypeInfo)this.getItem(invokeDynamicInfo.nameAndType);
        if (nameAndTypeInfo == null) {
            return null;
        }
        return this.getUtf8Info(nameAndTypeInfo.typeDescriptor);
    }
    
    public int getDynamicBootstrap(final int n) {
        return ((DynamicInfo)this.getItem(n)).bootstrap;
    }
    
    public int getDynamicNameAndType(final int n) {
        return ((DynamicInfo)this.getItem(n)).nameAndType;
    }
    
    public String getDynamicType(final int n) {
        final DynamicInfo dynamicInfo = (DynamicInfo)this.getItem(n);
        if (dynamicInfo == null) {
            return null;
        }
        final NameAndTypeInfo nameAndTypeInfo = (NameAndTypeInfo)this.getItem(dynamicInfo.nameAndType);
        if (nameAndTypeInfo == null) {
            return null;
        }
        return this.getUtf8Info(nameAndTypeInfo.typeDescriptor);
    }
    
    public String getModuleInfo(final int n) {
        return this.getUtf8Info(((ModuleInfo)this.getItem(n)).name);
    }
    
    public String getPackageInfo(final int n) {
        return this.getUtf8Info(((PackageInfo)this.getItem(n)).name);
    }
    
    public int isConstructor(final String s, final int n) {
        return this.isMember(s, "<init>", n);
    }
    
    public int isMember(final String s, final String s2, final int n) {
        final MemberrefInfo memberrefInfo = (MemberrefInfo)this.getItem(n);
        if (this.getClassInfo(memberrefInfo.classIndex).equals(s)) {
            final NameAndTypeInfo nameAndTypeInfo = (NameAndTypeInfo)this.getItem(memberrefInfo.nameAndTypeIndex);
            if (this.getUtf8Info(nameAndTypeInfo.memberName).equals(s2)) {
                return nameAndTypeInfo.typeDescriptor;
            }
        }
        return 0;
    }
    
    public String eqMember(final String s, final String s2, final int n) {
        final MemberrefInfo memberrefInfo = (MemberrefInfo)this.getItem(n);
        final NameAndTypeInfo nameAndTypeInfo = (NameAndTypeInfo)this.getItem(memberrefInfo.nameAndTypeIndex);
        if (this.getUtf8Info(nameAndTypeInfo.memberName).equals(s) && this.getUtf8Info(nameAndTypeInfo.typeDescriptor).equals(s2)) {
            return this.getClassInfo(memberrefInfo.classIndex);
        }
        return null;
    }
    
    private int addItem0(final ConstInfo constInfo) {
        this.items.addElement(constInfo);
        return this.numOfItems++;
    }
    
    private int addItem(final ConstInfo constInfo) {
        if (this.itemsCache == null) {
            this.itemsCache = makeItemsCache(this.items);
        }
        final ConstInfo constInfo2 = this.itemsCache.get(constInfo);
        if (constInfo2 != null) {
            return constInfo2.index;
        }
        this.items.addElement(constInfo);
        this.itemsCache.put(constInfo, constInfo);
        return this.numOfItems++;
    }
    
    public int copy(final int n, final ConstPool constPool, final Map map) {
        if (n == 0) {
            return 0;
        }
        return this.getItem(n).copy(this, constPool, map);
    }
    
    int addConstInfoPadding() {
        return this.addItem0(new ConstInfoPadding(this.numOfItems));
    }
    
    public int addClassInfo(final CtClass ctClass) {
        if (ctClass == ConstPool.THIS) {
            return this.thisClassInfo;
        }
        if (!ctClass.isArray()) {
            return this.addClassInfo(ctClass.getName());
        }
        return this.addClassInfo(Descriptor.toJvmName(ctClass));
    }
    
    public int addClassInfo(final String s) {
        return this.addItem(new ClassInfo(this.addUtf8Info(Descriptor.toJvmName(s)), this.numOfItems));
    }
    
    public int addNameAndTypeInfo(final String s, final String s2) {
        return this.addNameAndTypeInfo(this.addUtf8Info(s), this.addUtf8Info(s2));
    }
    
    public int addNameAndTypeInfo(final int n, final int n2) {
        return this.addItem(new NameAndTypeInfo(n, n2, this.numOfItems));
    }
    
    public int addFieldrefInfo(final int n, final String s, final String s2) {
        return this.addFieldrefInfo(n, this.addNameAndTypeInfo(s, s2));
    }
    
    public int addFieldrefInfo(final int n, final int n2) {
        return this.addItem(new FieldrefInfo(n, n2, this.numOfItems));
    }
    
    public int addMethodrefInfo(final int n, final String s, final String s2) {
        return this.addMethodrefInfo(n, this.addNameAndTypeInfo(s, s2));
    }
    
    public int addMethodrefInfo(final int n, final int n2) {
        return this.addItem(new MethodrefInfo(n, n2, this.numOfItems));
    }
    
    public int addInterfaceMethodrefInfo(final int n, final String s, final String s2) {
        return this.addInterfaceMethodrefInfo(n, this.addNameAndTypeInfo(s, s2));
    }
    
    public int addInterfaceMethodrefInfo(final int n, final int n2) {
        return this.addItem(new InterfaceMethodrefInfo(n, n2, this.numOfItems));
    }
    
    public int addStringInfo(final String s) {
        return this.addItem(new StringInfo(this.addUtf8Info(s), this.numOfItems));
    }
    
    public int addIntegerInfo(final int n) {
        return this.addItem(new IntegerInfo(n, this.numOfItems));
    }
    
    public int addFloatInfo(final float n) {
        return this.addItem(new FloatInfo(n, this.numOfItems));
    }
    
    public int addLongInfo(final long n) {
        final int addItem = this.addItem(new LongInfo(n, this.numOfItems));
        if (addItem == this.numOfItems - 1) {
            this.addConstInfoPadding();
        }
        return addItem;
    }
    
    public int addDoubleInfo(final double n) {
        final int addItem = this.addItem(new DoubleInfo(n, this.numOfItems));
        if (addItem == this.numOfItems - 1) {
            this.addConstInfoPadding();
        }
        return addItem;
    }
    
    public int addUtf8Info(final String s) {
        return this.addItem(new Utf8Info(s, this.numOfItems));
    }
    
    public int addMethodHandleInfo(final int n, final int n2) {
        return this.addItem(new MethodHandleInfo(n, n2, this.numOfItems));
    }
    
    public int addMethodTypeInfo(final int n) {
        return this.addItem(new MethodTypeInfo(n, this.numOfItems));
    }
    
    public int addInvokeDynamicInfo(final int n, final int n2) {
        return this.addItem(new InvokeDynamicInfo(n, n2, this.numOfItems));
    }
    
    public int addDynamicInfo(final int n, final int n2) {
        return this.addItem(new DynamicInfo(n, n2, this.numOfItems));
    }
    
    public int addModuleInfo(final int n) {
        return this.addItem(new ModuleInfo(n, this.numOfItems));
    }
    
    public int addPackageInfo(final int n) {
        return this.addItem(new PackageInfo(n, this.numOfItems));
    }
    
    public Set getClassNames() {
        final HashSet<String> set = new HashSet<String>();
        final LongVector items = this.items;
        while (1 < this.numOfItems) {
            final String className = items.elementAt(1).getClassName(this);
            if (className != null) {
                set.add(className);
            }
            int n = 0;
            ++n;
        }
        return set;
    }
    
    public void renameClass(final String s, final String s2) {
        final LongVector items = this.items;
        while (1 < this.numOfItems) {
            items.elementAt(1).renameClass(this, s, s2, this.itemsCache);
            int n = 0;
            ++n;
        }
    }
    
    public void renameClass(final Map map) {
        final LongVector items = this.items;
        while (1 < this.numOfItems) {
            items.elementAt(1).renameClass(this, map, this.itemsCache);
            int n = 0;
            ++n;
        }
    }
    
    private void read(final DataInputStream dataInputStream) throws IOException {
        int unsignedShort = dataInputStream.readUnsignedShort();
        this.items = new LongVector(unsignedShort);
        this.numOfItems = 0;
        this.addItem0(null);
        while (--unsignedShort > 0) {
            final int one = this.readOne(dataInputStream);
            if (one == 5 || one == 6) {
                this.addConstInfoPadding();
                --unsignedShort;
            }
        }
    }
    
    private static Map makeItemsCache(final LongVector longVector) {
        final HashMap<ConstInfo, ConstInfo> hashMap = new HashMap<ConstInfo, ConstInfo>();
        while (true) {
            final int n = 1;
            int n2 = 0;
            ++n2;
            final ConstInfo element = longVector.elementAt(n);
            if (element == null) {
                break;
            }
            hashMap.put(element, element);
        }
        return hashMap;
    }
    
    private int readOne(final DataInputStream dataInputStream) throws IOException {
        final int unsignedByte = dataInputStream.readUnsignedByte();
        ConstInfo constInfo = null;
        switch (unsignedByte) {
            case 1: {
                constInfo = new Utf8Info(dataInputStream, this.numOfItems);
                break;
            }
            case 3: {
                constInfo = new IntegerInfo(dataInputStream, this.numOfItems);
                break;
            }
            case 4: {
                constInfo = new FloatInfo(dataInputStream, this.numOfItems);
                break;
            }
            case 5: {
                constInfo = new LongInfo(dataInputStream, this.numOfItems);
                break;
            }
            case 6: {
                constInfo = new DoubleInfo(dataInputStream, this.numOfItems);
                break;
            }
            case 7: {
                constInfo = new ClassInfo(dataInputStream, this.numOfItems);
                break;
            }
            case 8: {
                constInfo = new StringInfo(dataInputStream, this.numOfItems);
                break;
            }
            case 9: {
                constInfo = new FieldrefInfo(dataInputStream, this.numOfItems);
                break;
            }
            case 10: {
                constInfo = new MethodrefInfo(dataInputStream, this.numOfItems);
                break;
            }
            case 11: {
                constInfo = new InterfaceMethodrefInfo(dataInputStream, this.numOfItems);
                break;
            }
            case 12: {
                constInfo = new NameAndTypeInfo(dataInputStream, this.numOfItems);
                break;
            }
            case 15: {
                constInfo = new MethodHandleInfo(dataInputStream, this.numOfItems);
                break;
            }
            case 16: {
                constInfo = new MethodTypeInfo(dataInputStream, this.numOfItems);
                break;
            }
            case 17: {
                constInfo = new DynamicInfo(dataInputStream, this.numOfItems);
                break;
            }
            case 18: {
                constInfo = new InvokeDynamicInfo(dataInputStream, this.numOfItems);
                break;
            }
            case 19: {
                constInfo = new ModuleInfo(dataInputStream, this.numOfItems);
                break;
            }
            case 20: {
                constInfo = new PackageInfo(dataInputStream, this.numOfItems);
                break;
            }
            default: {
                throw new IOException("invalid constant type: " + unsignedByte + " at " + this.numOfItems);
            }
        }
        this.addItem0(constInfo);
        return unsignedByte;
    }
    
    public void write(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(this.numOfItems);
        final LongVector items = this.items;
        while (1 < this.numOfItems) {
            items.elementAt(1).write(dataOutputStream);
            int n = 0;
            ++n;
        }
    }
    
    public void print() {
        this.print(new PrintWriter(System.out, true));
    }
    
    public void print(final PrintWriter printWriter) {
        while (1 < this.numOfItems) {
            printWriter.print(1);
            printWriter.print(" ");
            this.items.elementAt(1).print(printWriter);
            int n = 0;
            ++n;
        }
    }
    
    static {
        THIS = null;
    }
}
