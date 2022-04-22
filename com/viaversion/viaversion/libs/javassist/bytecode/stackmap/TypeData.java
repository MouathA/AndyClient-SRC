package com.viaversion.viaversion.libs.javassist.bytecode.stackmap;

import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;
import java.util.*;

public abstract class TypeData
{
    public static TypeData[] make(final int n) {
        final TypeData[] array = new TypeData[n];
        while (0 < n) {
            array[0] = TypeTag.TOP;
            int n2 = 0;
            ++n2;
        }
        return array;
    }
    
    protected TypeData() {
    }
    
    private static void setType(final TypeData typeData, final String s, final ClassPool classPool) throws BadBytecode {
        typeData.setType(s, classPool);
    }
    
    public abstract int getTypeTag();
    
    public abstract int getTypeData(final ConstPool p0);
    
    public TypeData join() {
        return new TypeVar(this);
    }
    
    public abstract BasicType isBasicType();
    
    public abstract boolean is2WordType();
    
    public boolean isNullType() {
        return false;
    }
    
    public boolean isUninit() {
        return false;
    }
    
    public abstract boolean eq(final TypeData p0);
    
    public abstract String getName();
    
    public abstract void setType(final String p0, final ClassPool p1) throws BadBytecode;
    
    public abstract TypeData getArrayType(final int p0) throws NotFoundException;
    
    public int dfs(final List list, final int n, final ClassPool classPool) throws NotFoundException {
        return n;
    }
    
    protected TypeVar toTypeVar(final int n) {
        return null;
    }
    
    public void constructorCalled(final int n) {
    }
    
    @Override
    public String toString() {
        return super.toString() + "(" + this.toString2(new HashSet()) + ")";
    }
    
    abstract String toString2(final Set p0);
    
    public static CtClass commonSuperClassEx(final CtClass ctClass, final CtClass ctClass2) throws NotFoundException {
        if (ctClass == ctClass2) {
            return ctClass;
        }
        if (ctClass.isArray() && ctClass2.isArray()) {
            final CtClass componentType = ctClass.getComponentType();
            final CtClass componentType2 = ctClass2.getComponentType();
            final CtClass commonSuperClassEx = commonSuperClassEx(componentType, componentType2);
            if (commonSuperClassEx == componentType) {
                return ctClass;
            }
            if (commonSuperClassEx == componentType2) {
                return ctClass2;
            }
            return ctClass.getClassPool().get((commonSuperClassEx == null) ? "java.lang.Object" : (commonSuperClassEx.getName() + "[]"));
        }
        else {
            if (ctClass.isPrimitive() || ctClass2.isPrimitive()) {
                return null;
            }
            if (ctClass.isArray() || ctClass2.isArray()) {
                return ctClass.getClassPool().get("java.lang.Object");
            }
            return commonSuperClass(ctClass, ctClass2);
        }
    }
    
    public static CtClass commonSuperClass(final CtClass ctClass, final CtClass ctClass2) throws NotFoundException {
        CtClass superclass = ctClass;
        final CtClass ctClass4;
        CtClass ctClass3 = ctClass4 = ctClass2;
        CtClass superclass2 = superclass;
        while (superclass == ctClass3 || superclass.getSuperclass() == null) {
            final CtClass superclass3 = superclass.getSuperclass();
            final CtClass superclass4 = ctClass3.getSuperclass();
            CtClass superclass5;
            if (superclass4 == null) {
                superclass5 = ctClass4;
            }
            else {
                if (superclass3 != null) {
                    superclass = superclass3;
                    ctClass3 = superclass4;
                    continue;
                }
                final CtClass ctClass5 = superclass2;
                superclass2 = ctClass4;
                final CtClass ctClass6 = ctClass5;
                superclass = ctClass3;
                superclass5 = ctClass6;
            }
            while (true) {
                superclass = superclass.getSuperclass();
                if (superclass == null) {
                    break;
                }
                superclass2 = superclass2.getSuperclass();
            }
            CtClass superclass6;
            for (superclass6 = superclass2; superclass6 != superclass5; superclass6 = superclass6.getSuperclass(), superclass5 = superclass5.getSuperclass()) {}
            return superclass6;
        }
        return superclass;
    }
    
    public static void aastore(final TypeData typeData, final TypeData typeData2, final ClassPool classPool) throws BadBytecode {
        if (typeData instanceof AbsTypeVar && !typeData2.isNullType()) {
            ((AbsTypeVar)typeData).merge(ArrayType.make(typeData2));
        }
        if (typeData2 instanceof AbsTypeVar) {
            if (typeData instanceof AbsTypeVar) {
                ArrayElement.make(typeData);
            }
            else {
                if (!(typeData instanceof ClassName)) {
                    throw new BadBytecode("bad AASTORE: " + typeData);
                }
                if (!typeData.isNullType()) {
                    typeData2.setType(ArrayElement.access$000(typeData.getName()), classPool);
                }
            }
        }
    }
    
    public static class UninitThis extends UninitData
    {
        UninitThis(final String s) {
            super(-1, s);
        }
        
        @Override
        public UninitData copy() {
            return new UninitThis(this.getName());
        }
        
        @Override
        public int getTypeTag() {
            return 6;
        }
        
        @Override
        public int getTypeData(final ConstPool constPool) {
            return 0;
        }
        
        @Override
        String toString2(final Set set) {
            return "uninit:this";
        }
    }
    
    public static class UninitData extends ClassName
    {
        int offset;
        boolean initialized;
        
        UninitData(final int offset, final String s) {
            super(s);
            this.offset = offset;
            this.initialized = false;
        }
        
        public UninitData copy() {
            return new UninitData(this.offset, this.getName());
        }
        
        @Override
        public int getTypeTag() {
            return 8;
        }
        
        @Override
        public int getTypeData(final ConstPool constPool) {
            return this.offset;
        }
        
        @Override
        public TypeData join() {
            if (this.initialized) {
                return new TypeVar(new ClassName(this.getName()));
            }
            return new UninitTypeVar(this.copy());
        }
        
        @Override
        public boolean isUninit() {
            return true;
        }
        
        @Override
        public boolean eq(final TypeData typeData) {
            if (typeData instanceof UninitData) {
                final UninitData uninitData = (UninitData)typeData;
                return this.offset == uninitData.offset && this.getName().equals(uninitData.getName());
            }
            return false;
        }
        
        public int offset() {
            return this.offset;
        }
        
        @Override
        public void constructorCalled(final int n) {
            if (n == this.offset) {
                this.initialized = true;
            }
        }
        
        @Override
        String toString2(final Set set) {
            return this.getName() + "," + this.offset;
        }
    }
    
    public static class TypeVar extends AbsTypeVar
    {
        protected List lowers;
        protected List usedBy;
        protected List uppers;
        protected String fixedType;
        private boolean is2WordType;
        private int visited;
        private int smallest;
        private boolean inList;
        private int dimension;
        
        public TypeVar(final TypeData typeData) {
            this.visited = 0;
            this.smallest = 0;
            this.inList = false;
            this.dimension = 0;
            this.uppers = null;
            this.lowers = new ArrayList(2);
            this.usedBy = new ArrayList(2);
            this.merge(typeData);
            this.fixedType = null;
            this.is2WordType = typeData.is2WordType();
        }
        
        @Override
        public String getName() {
            if (this.fixedType == null) {
                return this.lowers.get(0).getName();
            }
            return this.fixedType;
        }
        
        @Override
        public BasicType isBasicType() {
            if (this.fixedType == null) {
                return this.lowers.get(0).isBasicType();
            }
            return null;
        }
        
        @Override
        public boolean is2WordType() {
            return this.fixedType == null && this.is2WordType;
        }
        
        @Override
        public boolean isUninit() {
            return this.fixedType == null && this.lowers.get(0).isUninit();
        }
        
        @Override
        public void merge(final TypeData typeData) {
            this.lowers.add(typeData);
            if (typeData instanceof TypeVar) {
                ((TypeVar)typeData).usedBy.add(this);
            }
        }
        
        @Override
        public int getTypeTag() {
            if (this.fixedType == null) {
                return this.lowers.get(0).getTypeTag();
            }
            return super.getTypeTag();
        }
        
        @Override
        public int getTypeData(final ConstPool constPool) {
            if (this.fixedType == null) {
                return this.lowers.get(0).getTypeData(constPool);
            }
            return super.getTypeData(constPool);
        }
        
        @Override
        public void setType(final String s, final ClassPool classPool) throws BadBytecode {
            if (this.uppers == null) {
                this.uppers = new ArrayList();
            }
            this.uppers.add(s);
        }
        
        @Override
        protected TypeVar toTypeVar(final int dimension) {
            this.dimension = dimension;
            return this;
        }
        
        @Override
        public TypeData getArrayType(final int n) throws NotFoundException {
            if (n == 0) {
                return this;
            }
            final BasicType basicType = this.isBasicType();
            if (basicType != null) {
                return basicType.getArrayType(n);
            }
            if (this == null) {
                return new NullType();
            }
            return new ClassName(this.getName()).getArrayType(n);
        }
        
        @Override
        public int dfs(final List list, int dfs, final ClassPool classPool) throws NotFoundException {
            if (this.visited > 0) {
                return dfs;
            }
            final int n = ++dfs;
            this.smallest = n;
            this.visited = n;
            list.add(this);
            this.inList = true;
            while (0 < this.lowers.size()) {
                final TypeVar typeVar = this.lowers.get(0).toTypeVar(this.dimension);
                if (typeVar != null) {
                    if (typeVar.visited == 0) {
                        dfs = typeVar.dfs(list, dfs, classPool);
                        if (typeVar.smallest < this.smallest) {
                            this.smallest = typeVar.smallest;
                        }
                    }
                    else if (typeVar.inList && typeVar.visited < this.smallest) {
                        this.smallest = typeVar.visited;
                    }
                }
                int n2 = 0;
                ++n2;
            }
            if (this.visited == this.smallest) {
                final ArrayList<TypeVar> list2 = new ArrayList<TypeVar>();
                TypeVar typeVar2;
                do {
                    typeVar2 = list.remove(list.size() - 1);
                    typeVar2.inList = false;
                    list2.add(typeVar2);
                } while (typeVar2 != this);
                this.fixTypes(list2, classPool);
            }
            return dfs;
        }
        
        private void fixTypes(final List list, final ClassPool classPool) throws NotFoundException {
            final HashSet<String> set = new HashSet<String>();
            TypeData top = null;
            while (0 < list.size()) {
                final TypeVar typeVar = list.get(0);
                final List lowers = typeVar.lowers;
                while (0 < lowers.size()) {
                    final TypeData arrayType = lowers.get(0).getArrayType(typeVar.dimension);
                    final BasicType basicType = arrayType.isBasicType();
                    Label_0151: {
                        if (top != null) {
                            if (basicType != null) {
                                if (basicType == null || top == basicType) {
                                    break Label_0151;
                                }
                            }
                            top = TypeTag.TOP;
                            break;
                        }
                        if (basicType == null) {
                            top = arrayType;
                            if (arrayType.isUninit()) {
                                break;
                            }
                        }
                        else {
                            top = basicType;
                        }
                    }
                    if (basicType == null && !arrayType.isNullType()) {
                        set.add(arrayType.getName());
                    }
                    int n = 0;
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
            this.is2WordType = top.is2WordType();
            this.fixTypes1(list, top);
        }
        
        private void fixTypes1(final List list, final TypeData typeData) throws NotFoundException {
            while (0 < list.size()) {
                final TypeVar typeVar = list.get(0);
                final TypeData arrayType = typeData.getArrayType(-typeVar.dimension);
                if (arrayType.isBasicType() == null) {
                    typeVar.fixedType = arrayType.getName();
                }
                else {
                    typeVar.lowers.clear();
                    typeVar.lowers.add(arrayType);
                    typeVar.is2WordType = arrayType.is2WordType();
                }
                int n = 0;
                ++n;
            }
        }
        
        private String fixTypes2(final List list, final Set set, final ClassPool classPool) throws NotFoundException {
            final Iterator<String> iterator = set.iterator();
            if (set.size() == 0) {
                return null;
            }
            if (set.size() == 1) {
                return iterator.next();
            }
            CtClass ctClass = classPool.get(iterator.next());
            while (iterator.hasNext()) {
                ctClass = TypeData.commonSuperClassEx(ctClass, classPool.get(iterator.next()));
            }
            if (ctClass.getSuperclass() == null || ctClass != 0) {
                ctClass = this.fixByUppers(list, classPool, new HashSet(), ctClass);
            }
            if (ctClass.isArray()) {
                return Descriptor.toJvmName(ctClass);
            }
            return ctClass.getName();
        }
        
        private CtClass fixByUppers(final List list, final ClassPool classPool, final Set set, CtClass fixByUppers) throws NotFoundException {
            if (list == null) {
                return fixByUppers;
            }
            while (0 < list.size()) {
                final TypeVar typeVar = list.get(0);
                if (!set.add(typeVar)) {
                    return fixByUppers;
                }
                if (typeVar.uppers != null) {
                    while (0 < typeVar.uppers.size()) {
                        final CtClass value = classPool.get(typeVar.uppers.get(0));
                        if (value.subtypeOf(fixByUppers)) {
                            fixByUppers = value;
                        }
                        int n = 0;
                        ++n;
                    }
                }
                fixByUppers = this.fixByUppers(typeVar.usedBy, classPool, set, fixByUppers);
                int n2 = 0;
                ++n2;
            }
            return fixByUppers;
        }
        
        @Override
        String toString2(final Set set) {
            set.add(this);
            if (this.lowers.size() > 0) {
                final TypeData typeData = this.lowers.get(0);
                if (typeData != null && !set.contains(typeData)) {
                    return typeData.toString2(set);
                }
            }
            return "?";
        }
    }
    
    protected static class BasicType extends TypeData
    {
        private String name;
        private int typeTag;
        private char decodedName;
        
        public BasicType(final String name, final int typeTag, final char decodedName) {
            this.name = name;
            this.typeTag = typeTag;
            this.decodedName = decodedName;
        }
        
        @Override
        public int getTypeTag() {
            return this.typeTag;
        }
        
        @Override
        public int getTypeData(final ConstPool constPool) {
            return 0;
        }
        
        @Override
        public TypeData join() {
            if (this == TypeTag.TOP) {
                return this;
            }
            return super.join();
        }
        
        @Override
        public BasicType isBasicType() {
            return this;
        }
        
        @Override
        public boolean is2WordType() {
            return this.typeTag == 4 || this.typeTag == 3;
        }
        
        @Override
        public boolean eq(final TypeData typeData) {
            return this == typeData;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        public char getDecodedName() {
            return this.decodedName;
        }
        
        @Override
        public void setType(final String s, final ClassPool classPool) throws BadBytecode {
            throw new BadBytecode("conflict: " + this.name + " and " + s);
        }
        
        @Override
        public TypeData getArrayType(final int n) throws NotFoundException {
            if (this == TypeTag.TOP) {
                return this;
            }
            if (n < 0) {
                throw new NotFoundException("no element type: " + this.name);
            }
            if (n == 0) {
                return this;
            }
            final char[] array = new char[n + 1];
            while (0 < n) {
                array[0] = '[';
                int n2 = 0;
                ++n2;
            }
            array[n] = this.decodedName;
            return new ClassName(new String(array));
        }
        
        @Override
        String toString2(final Set set) {
            return this.name;
        }
        
        static char access$100(final BasicType basicType) {
            return basicType.decodedName;
        }
    }
    
    public static class ClassName extends TypeData
    {
        private String name;
        
        public ClassName(final String name) {
            this.name = name;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public BasicType isBasicType() {
            return null;
        }
        
        @Override
        public boolean is2WordType() {
            return false;
        }
        
        @Override
        public int getTypeTag() {
            return 7;
        }
        
        @Override
        public int getTypeData(final ConstPool constPool) {
            return constPool.addClassInfo(this.getName());
        }
        
        @Override
        public boolean eq(final TypeData typeData) {
            if (typeData.isUninit()) {
                return typeData.eq(this);
            }
            return this.name.equals(typeData.getName());
        }
        
        @Override
        public void setType(final String s, final ClassPool classPool) throws BadBytecode {
        }
        
        @Override
        public TypeData getArrayType(final int n) throws NotFoundException {
            if (n == 0) {
                return this;
            }
            if (n > 0) {
                final char[] array = new char[n];
                while (0 < n) {
                    array[0] = '[';
                    int n2 = 0;
                    ++n2;
                }
                String s = this.getName();
                if (s.charAt(0) != '[') {
                    s = "L" + s.replace('.', '/') + ";";
                }
                return new ClassName(new String(array) + s);
            }
            while (0 < -n) {
                if (this.name.charAt(0) != '[') {
                    throw new NotFoundException("no " + n + " dimensional array type: " + this.getName());
                }
                int char1 = 0;
                ++char1;
            }
            int char1 = this.name.charAt(-n);
            if (0 == 91) {
                return new ClassName(this.name.substring(-n));
            }
            if (0 == 76) {
                return new ClassName(this.name.substring(-n + 1, this.name.length() - 1).replace('/', '.'));
            }
            if ('\0' == BasicType.access$100(TypeTag.DOUBLE)) {
                return TypeTag.DOUBLE;
            }
            if ('\0' == BasicType.access$100(TypeTag.FLOAT)) {
                return TypeTag.FLOAT;
            }
            if ('\0' == BasicType.access$100(TypeTag.LONG)) {
                return TypeTag.LONG;
            }
            return TypeTag.INTEGER;
        }
        
        @Override
        String toString2(final Set set) {
            return this.name;
        }
    }
    
    public static class NullType extends ClassName
    {
        public NullType() {
            super("null-type");
        }
        
        @Override
        public int getTypeTag() {
            return 5;
        }
        
        @Override
        public boolean isNullType() {
            return true;
        }
        
        @Override
        public int getTypeData(final ConstPool constPool) {
            return 0;
        }
        
        @Override
        public TypeData getArrayType(final int n) {
            return this;
        }
    }
    
    public abstract static class AbsTypeVar extends TypeData
    {
        public abstract void merge(final TypeData p0);
        
        @Override
        public int getTypeTag() {
            return 7;
        }
        
        @Override
        public int getTypeData(final ConstPool constPool) {
            return constPool.addClassInfo(this.getName());
        }
        
        @Override
        public boolean eq(final TypeData typeData) {
            if (typeData.isUninit()) {
                return typeData.eq(this);
            }
            return this.getName().equals(typeData.getName());
        }
    }
    
    public static class UninitTypeVar extends AbsTypeVar
    {
        protected TypeData type;
        
        public UninitTypeVar(final UninitData type) {
            this.type = type;
        }
        
        @Override
        public int getTypeTag() {
            return this.type.getTypeTag();
        }
        
        @Override
        public int getTypeData(final ConstPool constPool) {
            return this.type.getTypeData(constPool);
        }
        
        @Override
        public BasicType isBasicType() {
            return this.type.isBasicType();
        }
        
        @Override
        public boolean is2WordType() {
            return this.type.is2WordType();
        }
        
        @Override
        public boolean isUninit() {
            return this.type.isUninit();
        }
        
        @Override
        public boolean eq(final TypeData typeData) {
            return this.type.eq(typeData);
        }
        
        @Override
        public String getName() {
            return this.type.getName();
        }
        
        @Override
        protected TypeVar toTypeVar(final int n) {
            return null;
        }
        
        @Override
        public TypeData join() {
            return this.type.join();
        }
        
        @Override
        public void setType(final String s, final ClassPool classPool) throws BadBytecode {
            this.type.setType(s, classPool);
        }
        
        @Override
        public void merge(final TypeData typeData) {
            if (!typeData.eq(this.type)) {
                this.type = TypeTag.TOP;
            }
        }
        
        @Override
        public void constructorCalled(final int n) {
            this.type.constructorCalled(n);
        }
        
        public int offset() {
            if (this.type instanceof UninitData) {
                return ((UninitData)this.type).offset;
            }
            throw new RuntimeException("not available");
        }
        
        @Override
        public TypeData getArrayType(final int n) throws NotFoundException {
            return this.type.getArrayType(n);
        }
        
        @Override
        String toString2(final Set set) {
            return "";
        }
    }
    
    public static class ArrayElement extends AbsTypeVar
    {
        private AbsTypeVar array;
        
        private ArrayElement(final AbsTypeVar array) {
            this.array = array;
        }
        
        public static TypeData make(final TypeData typeData) throws BadBytecode {
            if (typeData instanceof ArrayType) {
                return ((ArrayType)typeData).elementType();
            }
            if (typeData instanceof AbsTypeVar) {
                return new ArrayElement((AbsTypeVar)typeData);
            }
            if (typeData instanceof ClassName && !typeData.isNullType()) {
                return new ClassName(typeName(typeData.getName()));
            }
            throw new BadBytecode("bad AASTORE: " + typeData);
        }
        
        @Override
        public void merge(final TypeData typeData) {
            if (!typeData.isNullType()) {
                this.array.merge(ArrayType.make(typeData));
            }
        }
        
        @Override
        public String getName() {
            return typeName(this.array.getName());
        }
        
        public AbsTypeVar arrayType() {
            return this.array;
        }
        
        @Override
        public BasicType isBasicType() {
            return null;
        }
        
        @Override
        public boolean is2WordType() {
            return false;
        }
        
        private static String typeName(final String s) {
            if (s.length() > 1 && s.charAt(0) == '[') {
                final char char1 = s.charAt(1);
                if (char1 == 'L') {
                    return s.substring(2, s.length() - 1).replace('/', '.');
                }
                if (char1 == '[') {
                    return s.substring(1);
                }
            }
            return "java.lang.Object";
        }
        
        @Override
        public void setType(final String s, final ClassPool classPool) throws BadBytecode {
            this.array.setType(ArrayType.typeName(s), classPool);
        }
        
        @Override
        protected TypeVar toTypeVar(final int n) {
            return this.array.toTypeVar(n - 1);
        }
        
        @Override
        public TypeData getArrayType(final int n) throws NotFoundException {
            return this.array.getArrayType(n - 1);
        }
        
        @Override
        public int dfs(final List list, final int n, final ClassPool classPool) throws NotFoundException {
            return this.array.dfs(list, n, classPool);
        }
        
        @Override
        String toString2(final Set set) {
            return "*" + this.array.toString2(set);
        }
        
        static String access$000(final String s) {
            return typeName(s);
        }
    }
    
    public static class ArrayType extends AbsTypeVar
    {
        private AbsTypeVar element;
        
        private ArrayType(final AbsTypeVar element) {
            this.element = element;
        }
        
        static TypeData make(final TypeData typeData) throws BadBytecode {
            if (typeData instanceof ArrayElement) {
                return ((ArrayElement)typeData).arrayType();
            }
            if (typeData instanceof AbsTypeVar) {
                return new ArrayType((AbsTypeVar)typeData);
            }
            if (typeData instanceof ClassName && !typeData.isNullType()) {
                return new ClassName(typeName(typeData.getName()));
            }
            throw new BadBytecode("bad AASTORE: " + typeData);
        }
        
        @Override
        public void merge(final TypeData typeData) {
            if (!typeData.isNullType()) {
                this.element.merge(ArrayElement.make(typeData));
            }
        }
        
        @Override
        public String getName() {
            return typeName(this.element.getName());
        }
        
        public AbsTypeVar elementType() {
            return this.element;
        }
        
        @Override
        public BasicType isBasicType() {
            return null;
        }
        
        @Override
        public boolean is2WordType() {
            return false;
        }
        
        public static String typeName(final String s) {
            if (s.charAt(0) == '[') {
                return "[" + s;
            }
            return "[L" + s.replace('.', '/') + ";";
        }
        
        @Override
        public void setType(final String s, final ClassPool classPool) throws BadBytecode {
            this.element.setType(ArrayElement.access$000(s), classPool);
        }
        
        @Override
        protected TypeVar toTypeVar(final int n) {
            return this.element.toTypeVar(n + 1);
        }
        
        @Override
        public TypeData getArrayType(final int n) throws NotFoundException {
            return this.element.getArrayType(n + 1);
        }
        
        @Override
        public int dfs(final List list, final int n, final ClassPool classPool) throws NotFoundException {
            return this.element.dfs(list, n, classPool);
        }
        
        @Override
        String toString2(final Set set) {
            return "[" + this.element.toString2(set);
        }
    }
}
