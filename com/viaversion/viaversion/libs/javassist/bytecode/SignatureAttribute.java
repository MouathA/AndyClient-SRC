package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.*;
import java.util.*;
import com.viaversion.viaversion.libs.javassist.*;

public class SignatureAttribute extends AttributeInfo
{
    public static final String tag = "Signature";
    
    SignatureAttribute(final ConstPool constPool, final int n, final DataInputStream dataInputStream) throws IOException {
        super(constPool, n, dataInputStream);
    }
    
    public SignatureAttribute(final ConstPool constPool, final String s) {
        super(constPool, "Signature");
        final int addUtf8Info = constPool.addUtf8Info(s);
        this.set(new byte[] { (byte)(addUtf8Info >>> 8), (byte)addUtf8Info });
    }
    
    public String getSignature() {
        return this.getConstPool().getUtf8Info(ByteArray.readU16bit(this.get(), 0));
    }
    
    public void setSignature(final String s) {
        ByteArray.write16bit(this.getConstPool().addUtf8Info(s), this.info, 0);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool constPool, final Map map) {
        return new SignatureAttribute(constPool, this.getSignature());
    }
    
    @Override
    void renameClass(final String s, final String s2) {
        this.setSignature(renameClass(this.getSignature(), s, s2));
    }
    
    @Override
    void renameClass(final Map map) {
        this.setSignature(renameClass(this.getSignature(), map));
    }
    
    static String renameClass(final String s, final String s2, final String s3) {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(s2, s3);
        return renameClass(s, hashMap);
    }
    
    static String renameClass(final String s, final Map map) {
        if (map == null) {
            return s;
        }
        final StringBuilder sb = new StringBuilder();
        while (true) {
            final int index = s.indexOf(76, 0);
            if (index < 0) {
                break;
            }
            final StringBuilder sb2 = new StringBuilder();
            int n = index;
            char char1;
            while ((char1 = s.charAt(++n)) != ';') {
                sb2.append(char1);
                if (char1 == '<') {
                    char char2;
                    while ((char2 = s.charAt(++n)) != '>') {
                        sb2.append(char2);
                    }
                    sb2.append(char2);
                }
            }
            final String s2 = map.get(sb2.toString());
            if (s2 == null) {
                continue;
            }
            sb.append(s.substring(0, index));
            sb.append('L');
            sb.append(s2);
            sb.append(char1);
        }
        return s;
    }
    
    private static boolean isNamePart(final int n) {
        return n != 59 && n != 60;
    }
    
    public static ClassSignature toClassSignature(final String s) throws BadBytecode {
        return parseSig(s);
    }
    
    public static MethodSignature toMethodSignature(final String s) throws BadBytecode {
        return parseMethodSig(s);
    }
    
    public static ObjectType toFieldSignature(final String s) throws BadBytecode {
        return parseObjectType(s, new Cursor(null), false);
    }
    
    public static Type toTypeSignature(final String s) throws BadBytecode {
        return parseType(s, new Cursor(null));
    }
    
    private static ClassSignature parseSig(final String s) throws BadBytecode, IndexOutOfBoundsException {
        final Cursor cursor = new Cursor(null);
        final TypeParameter[] typeParams = parseTypeParams(s, cursor);
        final ClassType classType = parseClassType(s, cursor);
        final int length = s.length();
        final ArrayList<ClassType> list = new ArrayList<ClassType>();
        while (cursor.position < length && s.charAt(cursor.position) == 'L') {
            list.add(parseClassType(s, cursor));
        }
        return new ClassSignature(typeParams, classType, list.toArray(new ClassType[list.size()]));
    }
    
    private static MethodSignature parseMethodSig(final String s) throws BadBytecode {
        final Cursor cursor = new Cursor(null);
        final TypeParameter[] typeParams = parseTypeParams(s, cursor);
        if (s.charAt(cursor.position++) != '(') {
            throw error(s);
        }
        final ArrayList<Type> list = new ArrayList<Type>();
        while (s.charAt(cursor.position) != ')') {
            list.add(parseType(s, cursor));
        }
        final Cursor cursor2 = cursor;
        ++cursor2.position;
        final Type type = parseType(s, cursor);
        final int length = s.length();
        final ArrayList<ObjectType> list2 = new ArrayList<ObjectType>();
        while (cursor.position < length && s.charAt(cursor.position) == '^') {
            final Cursor cursor3 = cursor;
            ++cursor3.position;
            final ObjectType objectType = parseObjectType(s, cursor, false);
            if (objectType instanceof ArrayType) {
                throw error(s);
            }
            list2.add(objectType);
        }
        return new MethodSignature(typeParams, list.toArray(new Type[list.size()]), type, list2.toArray(new ObjectType[list2.size()]));
    }
    
    private static TypeParameter[] parseTypeParams(final String s, final Cursor cursor) throws BadBytecode {
        final ArrayList<TypeParameter> list = new ArrayList<TypeParameter>();
        if (s.charAt(cursor.position) == '<') {
            ++cursor.position;
            while (s.charAt(cursor.position) != '>') {
                final int position = cursor.position;
                final int index = cursor.indexOf(s, 58);
                final ObjectType objectType = parseObjectType(s, cursor, true);
                final ArrayList<ObjectType> list2 = new ArrayList<ObjectType>();
                while (s.charAt(cursor.position) == ':') {
                    ++cursor.position;
                    list2.add(parseObjectType(s, cursor, false));
                }
                list.add(new TypeParameter(s, position, index, objectType, list2.toArray(new ObjectType[list2.size()])));
            }
            ++cursor.position;
        }
        return list.toArray(new TypeParameter[list.size()]);
    }
    
    private static ObjectType parseObjectType(final String s, final Cursor cursor, final boolean b) throws BadBytecode {
        final int position = cursor.position;
        switch (s.charAt(position)) {
            case 'L': {
                return parseClassType2(s, cursor, null);
            }
            case 'T': {
                return new TypeVariable(s, position + 1, cursor.indexOf(s, 59));
            }
            case '[': {
                return parseArray(s, cursor);
            }
            default: {
                if (b) {
                    return null;
                }
                throw error(s);
            }
        }
    }
    
    private static ClassType parseClassType(final String s, final Cursor cursor) throws BadBytecode {
        if (s.charAt(cursor.position) == 'L') {
            return parseClassType2(s, cursor, null);
        }
        throw error(s);
    }
    
    private static ClassType parseClassType2(final String s, final Cursor cursor, final ClassType classType) throws BadBytecode {
        final int n = ++cursor.position;
        char c;
        do {
            c = s.charAt(cursor.position++);
        } while (c != '$' && c != '<' && c != ';');
        final int n2 = cursor.position - 1;
        TypeArgument[] typeArgs;
        if (c == '<') {
            typeArgs = parseTypeArgs(s, cursor);
            c = s.charAt(cursor.position++);
        }
        else {
            typeArgs = null;
        }
        final ClassType make = ClassType.make(s, n, n2, typeArgs, classType);
        if (c == '$' || c == '.') {
            --cursor.position;
            return parseClassType2(s, cursor, make);
        }
        return make;
    }
    
    private static TypeArgument[] parseTypeArgs(final String s, final Cursor cursor) throws BadBytecode {
        final ArrayList<TypeArgument> list = new ArrayList<TypeArgument>();
        while (s.charAt(cursor.position++) != '>') {
            --cursor.position;
            list.add(new TypeArgument(parseObjectType(s, cursor, false), ' '));
        }
        return list.toArray(new TypeArgument[list.size()]);
    }
    
    private static ObjectType parseArray(final String s, final Cursor cursor) throws BadBytecode {
        while (s.charAt(++cursor.position) == '[') {
            int n = 0;
            ++n;
        }
        return new ArrayType(1, parseType(s, cursor));
    }
    
    private static Type parseType(final String s, final Cursor cursor) throws BadBytecode {
        Type objectType = parseObjectType(s, cursor, true);
        if (objectType == null) {
            objectType = new BaseType(s.charAt(cursor.position++));
        }
        return objectType;
    }
    
    private static BadBytecode error(final String s) {
        return new BadBytecode("bad signature: " + s);
    }
    
    static BadBytecode access$000(final String s) {
        return error(s);
    }
    
    public static class TypeVariable extends ObjectType
    {
        String name;
        
        TypeVariable(final String s, final int n, final int n2) {
            this.name = s.substring(n, n2);
        }
        
        public TypeVariable(final String name) {
            this.name = name;
        }
        
        public String getName() {
            return this.name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        @Override
        void encode(final StringBuffer sb) {
            sb.append('T').append(this.name).append(';');
        }
    }
    
    public abstract static class ObjectType extends Type
    {
        public String encode() {
            final StringBuffer sb = new StringBuffer();
            this.encode(sb);
            return sb.toString();
        }
    }
    
    public abstract static class Type
    {
        abstract void encode(final StringBuffer p0);
        
        static void toString(final StringBuffer sb, final Type[] array) {
            while (0 < array.length) {
                if (0 > 0) {
                    sb.append(", ");
                }
                sb.append(array[0]);
                int n = 0;
                ++n;
            }
        }
        
        public String jvmTypeName() {
            return this.toString();
        }
    }
    
    public static class ArrayType extends ObjectType
    {
        int dim;
        Type componentType;
        
        public ArrayType(final int dim, final Type componentType) {
            this.dim = dim;
            this.componentType = componentType;
        }
        
        public int getDimension() {
            return this.dim;
        }
        
        public Type getComponentType() {
            return this.componentType;
        }
        
        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer(this.componentType.toString());
            while (0 < this.dim) {
                sb.append("[]");
                int n = 0;
                ++n;
            }
            return sb.toString();
        }
        
        @Override
        void encode(final StringBuffer sb) {
            while (0 < this.dim) {
                sb.append('[');
                int n = 0;
                ++n;
            }
            this.componentType.encode(sb);
        }
    }
    
    public static class NestedClassType extends ClassType
    {
        ClassType parent;
        
        NestedClassType(final String s, final int n, final int n2, final TypeArgument[] array, final ClassType parent) {
            super(s, n, n2, array);
            this.parent = parent;
        }
        
        public NestedClassType(final ClassType parent, final String s, final TypeArgument[] array) {
            super(s, array);
            this.parent = parent;
        }
        
        @Override
        public ClassType getDeclaringClass() {
            return this.parent;
        }
    }
    
    public static class ClassType extends ObjectType
    {
        String name;
        TypeArgument[] arguments;
        public static ClassType OBJECT;
        
        static ClassType make(final String s, final int n, final int n2, final TypeArgument[] array, final ClassType classType) {
            if (classType == null) {
                return new ClassType(s, n, n2, array);
            }
            return new NestedClassType(s, n, n2, array, classType);
        }
        
        ClassType(final String s, final int n, final int n2, final TypeArgument[] arguments) {
            this.name = s.substring(n, n2).replace('/', '.');
            this.arguments = arguments;
        }
        
        public ClassType(final String name, final TypeArgument[] arguments) {
            this.name = name;
            this.arguments = arguments;
        }
        
        public ClassType(final String s) {
            this(s, null);
        }
        
        public String getName() {
            return this.name;
        }
        
        public TypeArgument[] getTypeArguments() {
            return this.arguments;
        }
        
        public ClassType getDeclaringClass() {
            return null;
        }
        
        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer();
            final ClassType declaringClass = this.getDeclaringClass();
            if (declaringClass != null) {
                sb.append(declaringClass.toString()).append('.');
            }
            return this.toString2(sb);
        }
        
        private String toString2(final StringBuffer sb) {
            sb.append(this.name);
            if (this.arguments != null) {
                sb.append('<');
                while (0 < this.arguments.length) {
                    if (0 > 0) {
                        sb.append(", ");
                    }
                    sb.append(this.arguments[0].toString());
                    int n = 0;
                    ++n;
                }
                sb.append('>');
            }
            return sb.toString();
        }
        
        @Override
        public String jvmTypeName() {
            final StringBuffer sb = new StringBuffer();
            final ClassType declaringClass = this.getDeclaringClass();
            if (declaringClass != null) {
                sb.append(declaringClass.jvmTypeName()).append('$');
            }
            return this.toString2(sb);
        }
        
        @Override
        void encode(final StringBuffer sb) {
            sb.append('L');
            this.encode2(sb);
            sb.append(';');
        }
        
        void encode2(final StringBuffer sb) {
            final ClassType declaringClass = this.getDeclaringClass();
            if (declaringClass != null) {
                declaringClass.encode2(sb);
                sb.append('$');
            }
            sb.append(this.name.replace('.', '/'));
            if (this.arguments != null) {
                TypeArgument.encode(sb, this.arguments);
            }
        }
        
        static {
            ClassType.OBJECT = new ClassType("java.lang.Object", null);
        }
    }
    
    public static class TypeArgument
    {
        ObjectType arg;
        char wildcard;
        
        TypeArgument(final ObjectType arg, final char wildcard) {
            this.arg = arg;
            this.wildcard = wildcard;
        }
        
        public TypeArgument(final ObjectType objectType) {
            this(objectType, ' ');
        }
        
        public TypeArgument() {
            this(null, '*');
        }
        
        public static TypeArgument subclassOf(final ObjectType objectType) {
            return new TypeArgument(objectType, '+');
        }
        
        public static TypeArgument superOf(final ObjectType objectType) {
            return new TypeArgument(objectType, '-');
        }
        
        public char getKind() {
            return this.wildcard;
        }
        
        public boolean isWildcard() {
            return this.wildcard != ' ';
        }
        
        public ObjectType getType() {
            return this.arg;
        }
        
        @Override
        public String toString() {
            if (this.wildcard == '*') {
                return "?";
            }
            final String string = this.arg.toString();
            if (this.wildcard == ' ') {
                return string;
            }
            if (this.wildcard == '+') {
                return "? extends " + string;
            }
            return "? super " + string;
        }
        
        static void encode(final StringBuffer sb, final TypeArgument[] array) {
            sb.append('<');
            while (0 < array.length) {
                final TypeArgument typeArgument = array[0];
                if (typeArgument.isWildcard()) {
                    sb.append(typeArgument.wildcard);
                }
                if (typeArgument.getType() != null) {
                    typeArgument.getType().encode(sb);
                }
                int n = 0;
                ++n;
            }
            sb.append('>');
        }
    }
    
    public static class BaseType extends Type
    {
        char descriptor;
        
        BaseType(final char descriptor) {
            this.descriptor = descriptor;
        }
        
        public BaseType(final String s) {
            this(Descriptor.of(s).charAt(0));
        }
        
        public char getDescriptor() {
            return this.descriptor;
        }
        
        public CtClass getCtlass() {
            return Descriptor.toPrimitiveClass(this.descriptor);
        }
        
        @Override
        public String toString() {
            return Descriptor.toClassName(Character.toString(this.descriptor));
        }
        
        @Override
        void encode(final StringBuffer sb) {
            sb.append(this.descriptor);
        }
    }
    
    public static class TypeParameter
    {
        String name;
        ObjectType superClass;
        ObjectType[] superInterfaces;
        
        TypeParameter(final String s, final int n, final int n2, final ObjectType superClass, final ObjectType[] superInterfaces) {
            this.name = s.substring(n, n2);
            this.superClass = superClass;
            this.superInterfaces = superInterfaces;
        }
        
        public TypeParameter(final String name, final ObjectType superClass, final ObjectType[] superInterfaces) {
            this.name = name;
            this.superClass = superClass;
            if (superInterfaces == null) {
                this.superInterfaces = new ObjectType[0];
            }
            else {
                this.superInterfaces = superInterfaces;
            }
        }
        
        public TypeParameter(final String s) {
            this(s, null, null);
        }
        
        public String getName() {
            return this.name;
        }
        
        public ObjectType getClassBound() {
            return this.superClass;
        }
        
        public ObjectType[] getInterfaceBound() {
            return this.superInterfaces;
        }
        
        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer(this.getName());
            if (this.superClass != null) {
                sb.append(" extends ").append(this.superClass.toString());
            }
            final int length = this.superInterfaces.length;
            if (length > 0) {
                while (0 < length) {
                    if (0 > 0 || this.superClass != null) {
                        sb.append(" & ");
                    }
                    else {
                        sb.append(" extends ");
                    }
                    sb.append(this.superInterfaces[0].toString());
                    int n = 0;
                    ++n;
                }
            }
            return sb.toString();
        }
        
        static void toString(final StringBuffer sb, final TypeParameter[] array) {
            sb.append('<');
            while (0 < array.length) {
                if (0 > 0) {
                    sb.append(", ");
                }
                sb.append(array[0]);
                int n = 0;
                ++n;
            }
            sb.append('>');
        }
        
        void encode(final StringBuffer sb) {
            sb.append(this.name);
            if (this.superClass == null) {
                sb.append(":Ljava/lang/Object;");
            }
            else {
                sb.append(':');
                this.superClass.encode(sb);
            }
            while (0 < this.superInterfaces.length) {
                sb.append(':');
                this.superInterfaces[0].encode(sb);
                int n = 0;
                ++n;
            }
        }
    }
    
    public static class MethodSignature
    {
        TypeParameter[] typeParams;
        Type[] params;
        Type retType;
        ObjectType[] exceptions;
        
        public MethodSignature(final TypeParameter[] array, final Type[] array2, final Type type, final ObjectType[] array3) {
            this.typeParams = ((array == null) ? new TypeParameter[0] : array);
            this.params = ((array2 == null) ? new Type[0] : array2);
            this.retType = ((type == null) ? new BaseType("void") : type);
            this.exceptions = ((array3 == null) ? new ObjectType[0] : array3);
        }
        
        public TypeParameter[] getTypeParameters() {
            return this.typeParams;
        }
        
        public Type[] getParameterTypes() {
            return this.params;
        }
        
        public Type getReturnType() {
            return this.retType;
        }
        
        public ObjectType[] getExceptionTypes() {
            return this.exceptions;
        }
        
        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer();
            TypeParameter.toString(sb, this.typeParams);
            sb.append(" (");
            Type.toString(sb, this.params);
            sb.append(") ");
            sb.append(this.retType);
            if (this.exceptions.length > 0) {
                sb.append(" throws ");
                Type.toString(sb, this.exceptions);
            }
            return sb.toString();
        }
        
        public String encode() {
            final StringBuffer sb = new StringBuffer();
            int n = 0;
            if (this.typeParams.length > 0) {
                sb.append('<');
                while (0 < this.typeParams.length) {
                    this.typeParams[0].encode(sb);
                    ++n;
                }
                sb.append('>');
            }
            sb.append('(');
            while (0 < this.params.length) {
                this.params[0].encode(sb);
                ++n;
            }
            sb.append(')');
            this.retType.encode(sb);
            if (this.exceptions.length > 0) {
                while (0 < this.exceptions.length) {
                    sb.append('^');
                    this.exceptions[0].encode(sb);
                    ++n;
                }
            }
            return sb.toString();
        }
    }
    
    public static class ClassSignature
    {
        TypeParameter[] params;
        ClassType superClass;
        ClassType[] interfaces;
        
        public ClassSignature(final TypeParameter[] array, final ClassType classType, final ClassType[] array2) {
            this.params = ((array == null) ? new TypeParameter[0] : array);
            this.superClass = ((classType == null) ? ClassType.OBJECT : classType);
            this.interfaces = ((array2 == null) ? new ClassType[0] : array2);
        }
        
        public ClassSignature(final TypeParameter[] array) {
            this(array, null, null);
        }
        
        public TypeParameter[] getParameters() {
            return this.params;
        }
        
        public ClassType getSuperClass() {
            return this.superClass;
        }
        
        public ClassType[] getInterfaces() {
            return this.interfaces;
        }
        
        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer();
            TypeParameter.toString(sb, this.params);
            sb.append(" extends ").append(this.superClass);
            if (this.interfaces.length > 0) {
                sb.append(" implements ");
                Type.toString(sb, this.interfaces);
            }
            return sb.toString();
        }
        
        public String encode() {
            final StringBuffer sb = new StringBuffer();
            int n = 0;
            if (this.params.length > 0) {
                sb.append('<');
                while (0 < this.params.length) {
                    this.params[0].encode(sb);
                    ++n;
                }
                sb.append('>');
            }
            this.superClass.encode(sb);
            while (0 < this.interfaces.length) {
                this.interfaces[0].encode(sb);
                ++n;
            }
            return sb.toString();
        }
    }
    
    private static class Cursor
    {
        int position;
        
        private Cursor() {
            this.position = 0;
        }
        
        int indexOf(final String s, final int n) throws BadBytecode {
            final int index = s.indexOf(n, this.position);
            if (index < 0) {
                throw SignatureAttribute.access$000(s);
            }
            this.position = index + 1;
            return index;
        }
        
        Cursor(final SignatureAttribute$1 object) {
            this();
        }
    }
}
