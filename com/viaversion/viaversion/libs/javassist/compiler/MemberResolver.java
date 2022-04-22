package com.viaversion.viaversion.libs.javassist.compiler;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import java.lang.ref.*;
import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.compiler.ast.*;
import java.util.*;

public class MemberResolver implements TokenId
{
    private ClassPool classPool;
    private static final int YES = 0;
    private static final int NO = -1;
    private static final String INVALID = "<invalid>";
    private static Map invalidNamesMap;
    private Map invalidNames;
    
    public MemberResolver(final ClassPool classPool) {
        this.invalidNames = null;
        this.classPool = classPool;
    }
    
    public ClassPool getClassPool() {
        return this.classPool;
    }
    
    private static void fatal() throws CompileError {
        throw new CompileError("fatal");
    }
    
    public Method lookupMethod(final CtClass ctClass, final CtClass ctClass2, final MethodInfo methodInfo, final String s, final int[] array, final int[] array2, final String[] array3) throws CompileError {
        Method method = null;
        if (methodInfo != null && ctClass == ctClass2 && methodInfo.getName().equals(s)) {
            final int compareSignature = this.compareSignature(methodInfo.getDescriptor(), array, array2, array3);
            if (compareSignature != -1) {
                final Method method2 = new Method(ctClass, methodInfo, compareSignature);
                if (compareSignature == 0) {
                    return method2;
                }
                method = method2;
            }
        }
        final Method lookupMethod = this.lookupMethod(ctClass, s, array, array2, array3, method != null);
        if (lookupMethod != null) {
            return lookupMethod;
        }
        return method;
    }
    
    private Method lookupMethod(final CtClass ctClass, final String s, final int[] array, final int[] array2, final String[] array3, final boolean b) throws CompileError {
        Method method = null;
        final ClassFile classFile2 = ctClass.getClassFile2();
        if (classFile2 != null) {
            for (final MethodInfo methodInfo : classFile2.getMethods()) {
                if (methodInfo.getName().equals(s) && (methodInfo.getAccessFlags() & 0x40) == 0x0) {
                    final int compareSignature = this.compareSignature(methodInfo.getDescriptor(), array, array2, array3);
                    if (compareSignature == -1) {
                        continue;
                    }
                    final Method method2 = new Method(ctClass, methodInfo, compareSignature);
                    if (compareSignature == 0) {
                        return method2;
                    }
                    if (method != null && method.notmatch <= compareSignature) {
                        continue;
                    }
                    method = method2;
                }
            }
        }
        if (b) {
            method = null;
        }
        else if (method != null) {
            return method;
        }
        final boolean interface1 = Modifier.isInterface(ctClass.getModifiers());
        if (!interface1) {
            final CtClass superclass = ctClass.getSuperclass();
            if (superclass != null) {
                final Method lookupMethod = this.lookupMethod(superclass, s, array, array2, array3, b);
                if (lookupMethod != null) {
                    return lookupMethod;
                }
            }
        }
        final CtClass[] interfaces = ctClass.getInterfaces();
        while (0 < interfaces.length) {
            final Method lookupMethod2 = this.lookupMethod(interfaces[0], s, array, array2, array3, b);
            if (lookupMethod2 != null) {
                return lookupMethod2;
            }
            int n = 0;
            ++n;
        }
        if (interface1) {
            final CtClass superclass2 = ctClass.getSuperclass();
            if (superclass2 != null) {
                final Method lookupMethod3 = this.lookupMethod(superclass2, s, array, array2, array3, b);
                if (lookupMethod3 != null) {
                    return lookupMethod3;
                }
            }
        }
        return method;
    }
    
    private int compareSignature(final String s, final int[] array, final int[] array2, final String[] array3) throws CompileError {
        final int length = array.length;
        if (length != Descriptor.numOfParameters(s)) {
            return -1;
        }
        while (1 < s.length()) {
            final int n = 1;
            int n2 = 0;
            ++n2;
            char c = s.charAt(n);
            if (c == ')') {
                return (length == 0) ? 0 : -1;
            }
            if (0 >= length) {
                return -1;
            }
            while (c == '[') {
                int n3 = 0;
                ++n3;
                final int n4 = 1;
                ++n2;
                c = s.charAt(n4);
            }
            if (array[0] == 412) {
                if (!false && c != 'L') {
                    return -1;
                }
                if (c == 'L') {
                    n2 = s.indexOf(59, 1) + 1;
                }
            }
            else if (array2[0] != 0) {
                if (false || c != 'L' || !s.startsWith("java/lang/Object;", 1)) {
                    return -1;
                }
                n2 = s.indexOf(59, 1) + 1;
                int n5 = 0;
                ++n5;
                if (1 <= 0) {
                    return -1;
                }
            }
            else if (c == 'L') {
                final int index = s.indexOf(59, 1);
                if (index < 0 || array[0] != 307) {
                    return -1;
                }
                final String substring = s.substring(1, index);
                if (!substring.equals(array3[0])) {
                    if (!this.lookupClassByJvmName(array3[0]).subtypeOf(this.lookupClassByJvmName(substring))) {
                        return -1;
                    }
                    int n5 = 0;
                    ++n5;
                }
                n2 = index + 1;
            }
            else {
                final int descToType = descToType(c);
                final int n6 = array[0];
                if (descToType != n6) {
                    if (descToType != 324 || (n6 != 334 && n6 != 303 && n6 != 306)) {
                        return -1;
                    }
                    int n5 = 0;
                    ++n5;
                }
            }
            int n7 = 0;
            ++n7;
        }
        return -1;
    }
    
    public CtField lookupFieldByJvmName2(final String s, final Symbol symbol, final ASTree asTree) throws NoFieldException {
        return this.lookupClass(jvmToJavaName(s), true).getField(symbol.get());
    }
    
    public CtField lookupFieldByJvmName(final String s, final Symbol symbol) throws CompileError {
        return this.lookupField(jvmToJavaName(s), symbol);
    }
    
    public CtField lookupField(final String s, final Symbol symbol) throws CompileError {
        return this.lookupClass(s, false).getField(symbol.get());
    }
    
    public CtClass lookupClassByName(final ASTList list) throws CompileError {
        return this.lookupClass(Declarator.astToClassName(list, '.'), false);
    }
    
    public CtClass lookupClassByJvmName(final String s) throws CompileError {
        return this.lookupClass(jvmToJavaName(s), false);
    }
    
    public CtClass lookupClass(final Declarator declarator) throws CompileError {
        return this.lookupClass(declarator.getType(), declarator.getArrayDim(), declarator.getClassName());
    }
    
    public CtClass lookupClass(final int n, int n2, final String s) throws CompileError {
        String s2;
        if (n == 307) {
            final CtClass lookupClassByJvmName = this.lookupClassByJvmName(s);
            if (n2 <= 0) {
                return lookupClassByJvmName;
            }
            s2 = lookupClassByJvmName.getName();
        }
        else {
            s2 = getTypeName(n);
        }
        while (n2-- > 0) {
            s2 += "[]";
        }
        return this.lookupClass(s2, false);
    }
    
    static String getTypeName(final int n) throws CompileError {
        String s = "";
        switch (n) {
            case 301: {
                s = "boolean";
                break;
            }
            case 306: {
                s = "char";
                break;
            }
            case 303: {
                s = "byte";
                break;
            }
            case 334: {
                s = "short";
                break;
            }
            case 324: {
                s = "int";
                break;
            }
            case 326: {
                s = "long";
                break;
            }
            case 317: {
                s = "float";
                break;
            }
            case 312: {
                s = "double";
                break;
            }
            case 344: {
                s = "void";
                break;
            }
        }
        return s;
    }
    
    public CtClass lookupClass(final String s, final boolean b) throws CompileError {
        final Map invalidNames = this.getInvalidNames();
        final String s2 = invalidNames.get(s);
        if (s2 == "<invalid>") {
            throw new CompileError("no such class: " + s);
        }
        if (s2 != null) {
            return this.classPool.get(s2);
        }
        final CtClass lookupClass0 = this.lookupClass0(s, b);
        invalidNames.put(s, lookupClass0.getName());
        return lookupClass0;
    }
    
    public static int getInvalidMapSize() {
        return MemberResolver.invalidNamesMap.size();
    }
    
    private Map getInvalidNames() {
        Map<?, ?> invalidNames = (Map<?, ?>)this.invalidNames;
        if (invalidNames == null) {
            final Class<MemberResolver> clazz = MemberResolver.class;
            final Class<MemberResolver> clazz2 = MemberResolver.class;
            // monitorenter(clazz)
            final Reference<Map<?, ?>> reference = MemberResolver.invalidNamesMap.get(this.classPool);
            if (reference != null) {
                invalidNames = reference.get();
            }
            if (invalidNames == null) {
                invalidNames = new Hashtable<Object, Object>();
                MemberResolver.invalidNamesMap.put(this.classPool, new WeakReference<Map>(invalidNames));
            }
            // monitorexit(clazz2)
            this.invalidNames = invalidNames;
        }
        return invalidNames;
    }
    
    private CtClass searchImports(final String s) throws CompileError {
        if (s.indexOf(46) < 0) {
            final Iterator importedPackages = this.classPool.getImportedPackages();
            if (importedPackages.hasNext()) {
                return this.classPool.get(importedPackages.next().replaceAll("\\.$", "") + "." + s);
            }
        }
        this.getInvalidNames().put(s, "<invalid>");
        throw new CompileError("no such class: " + s);
    }
    
    private CtClass lookupClass0(final String s, final boolean b) throws NotFoundException {
        CtClass value;
        do {
            value = this.classPool.get(s);
        } while (value == null);
        return value;
    }
    
    public String resolveClassName(final ASTList list) throws CompileError {
        if (list == null) {
            return null;
        }
        return javaToJvmName(this.lookupClassByName(list).getName());
    }
    
    public String resolveJvmClassName(final String s) throws CompileError {
        if (s == null) {
            return null;
        }
        return javaToJvmName(this.lookupClassByJvmName(s).getName());
    }
    
    public static CtClass getSuperclass(final CtClass ctClass) throws CompileError {
        final CtClass superclass = ctClass.getSuperclass();
        if (superclass != null) {
            return superclass;
        }
        throw new CompileError("cannot find the super class of " + ctClass.getName());
    }
    
    public static CtClass getSuperInterface(final CtClass ctClass, final String s) throws CompileError {
        final CtClass[] interfaces = ctClass.getInterfaces();
        while (0 < interfaces.length) {
            if (interfaces[0].getName().equals(s)) {
                return interfaces[0];
            }
            int n = 0;
            ++n;
        }
        throw new CompileError("cannot find the super interface " + s + " of " + ctClass.getName());
    }
    
    public static String javaToJvmName(final String s) {
        return s.replace('.', '/');
    }
    
    public static String jvmToJavaName(final String s) {
        return s.replace('/', '.');
    }
    
    public static int descToType(final char c) throws CompileError {
        switch (c) {
            case 'Z': {
                return 301;
            }
            case 'C': {
                return 306;
            }
            case 'B': {
                return 303;
            }
            case 'S': {
                return 334;
            }
            case 'I': {
                return 324;
            }
            case 'J': {
                return 326;
            }
            case 'F': {
                return 317;
            }
            case 'D': {
                return 312;
            }
            case 'V': {
                return 344;
            }
            case 'L':
            case '[': {
                return 307;
            }
            default: {
                return 344;
            }
        }
    }
    
    public static int getModifiers(ASTList tail) {
        while (tail != null) {
            final Keyword keyword = (Keyword)tail.head();
            tail = tail.tail();
            switch (keyword.get()) {
                case 335: {}
                case 315: {}
                case 338: {}
                case 300: {}
                case 332: {}
                case 331: {}
                case 330: {}
                case 345: {}
                case 342: {
                    continue;
                }
            }
        }
        return 0;
    }
    
    static {
        MemberResolver.invalidNamesMap = new WeakHashMap();
    }
    
    public static class Method
    {
        public CtClass declaring;
        public MethodInfo info;
        public int notmatch;
        
        public Method(final CtClass declaring, final MethodInfo info, final int notmatch) {
            this.declaring = declaring;
            this.info = info;
            this.notmatch = notmatch;
        }
        
        public boolean isStatic() {
            return (this.info.getAccessFlags() & 0x8) != 0x0;
        }
    }
}
