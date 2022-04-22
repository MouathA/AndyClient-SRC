package com.viaversion.viaversion.libs.javassist.util.proxy;

import java.lang.invoke.*;
import java.security.*;
import com.viaversion.viaversion.libs.javassist.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;
import java.util.*;
import java.lang.ref.*;

public class ProxyFactory
{
    private Class superClass;
    private Class[] interfaces;
    private MethodFilter methodFilter;
    private MethodHandler handler;
    private List signatureMethods;
    private boolean hasGetHandler;
    private byte[] signature;
    private String classname;
    private String basename;
    private String superName;
    private Class thisClass;
    private String genericSignature;
    private boolean factoryUseCache;
    private boolean factoryWriteReplace;
    public static boolean onlyPublicMethods;
    public String writeDirectory;
    private static final Class OBJECT_TYPE;
    private static final String HOLDER = "_methods_";
    private static final String HOLDER_TYPE = "[Ljava/lang/reflect/Method;";
    private static final String FILTER_SIGNATURE_FIELD = "_filter_signature";
    private static final String FILTER_SIGNATURE_TYPE = "[B";
    private static final String HANDLER = "handler";
    private static final String NULL_INTERCEPTOR_HOLDER = "com.viaversion.viaversion.libs.javassist.util.proxy.RuntimeSupport";
    private static final String DEFAULT_INTERCEPTOR = "default_interceptor";
    private static final String HANDLER_TYPE;
    private static final String HANDLER_SETTER = "setHandler";
    private static final String HANDLER_SETTER_TYPE;
    private static final String HANDLER_GETTER = "getHandler";
    private static final String HANDLER_GETTER_TYPE;
    private static final String SERIAL_VERSION_UID_FIELD = "serialVersionUID";
    private static final String SERIAL_VERSION_UID_TYPE = "J";
    private static final long SERIAL_VERSION_UID_VALUE = -1L;
    public static boolean useCache;
    public static boolean useWriteReplace;
    private static Map proxyCache;
    private static char[] hexDigits;
    public static ClassLoaderProvider classLoaderProvider;
    public static UniqueName nameGenerator;
    private static final String packageForJavaBase = "com.viaversion.viaversion.libs.javassist.util.proxy.";
    private static Comparator sorter;
    private static final String HANDLER_GETTER_KEY = "getHandler:()";
    
    public boolean isUseCache() {
        return this.factoryUseCache;
    }
    
    public void setUseCache(final boolean factoryUseCache) {
        if (this.handler != null && factoryUseCache) {
            throw new RuntimeException("caching cannot be enabled if the factory default interceptor has been set");
        }
        this.factoryUseCache = factoryUseCache;
    }
    
    public boolean isUseWriteReplace() {
        return this.factoryWriteReplace;
    }
    
    public void setUseWriteReplace(final boolean factoryWriteReplace) {
        this.factoryWriteReplace = factoryWriteReplace;
    }
    
    public static boolean isProxyClass(final Class clazz) {
        return Proxy.class.isAssignableFrom(clazz);
    }
    
    public ProxyFactory() {
        this.superClass = null;
        this.interfaces = null;
        this.methodFilter = null;
        this.handler = null;
        this.signature = null;
        this.signatureMethods = null;
        this.hasGetHandler = false;
        this.thisClass = null;
        this.genericSignature = null;
        this.writeDirectory = null;
        this.factoryUseCache = ProxyFactory.useCache;
        this.factoryWriteReplace = ProxyFactory.useWriteReplace;
    }
    
    public void setSuperclass(final Class superClass) {
        this.superClass = superClass;
        this.signature = null;
    }
    
    public Class getSuperclass() {
        return this.superClass;
    }
    
    public void setInterfaces(final Class[] interfaces) {
        this.interfaces = interfaces;
        this.signature = null;
    }
    
    public Class[] getInterfaces() {
        return this.interfaces;
    }
    
    public void setFilter(final MethodFilter methodFilter) {
        this.methodFilter = methodFilter;
        this.signature = null;
    }
    
    public void setGenericSignature(final String genericSignature) {
        this.genericSignature = genericSignature;
    }
    
    public Class createClass() {
        if (this.signature == null) {
            this.computeSignature(this.methodFilter);
        }
        return this.createClass1(null);
    }
    
    public Class createClass(final MethodFilter methodFilter) {
        this.computeSignature(methodFilter);
        return this.createClass1(null);
    }
    
    Class createClass(final byte[] array) {
        this.installSignature(array);
        return this.createClass1(null);
    }
    
    public Class createClass(final MethodHandles.Lookup lookup) {
        if (this.signature == null) {
            this.computeSignature(this.methodFilter);
        }
        return this.createClass1(lookup);
    }
    
    public Class createClass(final MethodHandles.Lookup lookup, final MethodFilter methodFilter) {
        this.computeSignature(methodFilter);
        return this.createClass1(lookup);
    }
    
    Class createClass(final MethodHandles.Lookup lookup, final byte[] array) {
        this.installSignature(array);
        return this.createClass1(lookup);
    }
    
    private Class createClass1(final MethodHandles.Lookup lookup) {
        Class clazz = this.thisClass;
        if (clazz == null) {
            final ClassLoader classLoader = this.getClassLoader();
            // monitorenter(proxyCache = ProxyFactory.proxyCache)
            if (this.factoryUseCache) {
                this.createClass2(classLoader, lookup);
            }
            else {
                this.createClass3(classLoader, lookup);
            }
            clazz = this.thisClass;
            this.thisClass = null;
        }
        // monitorexit(proxyCache)
        return clazz;
    }
    
    public String getKey(final Class clazz, final Class[] array, final byte[] array2, final boolean b) {
        final StringBuffer sb = new StringBuffer();
        if (clazz != null) {
            sb.append(clazz.getName());
        }
        sb.append(":");
        int n = 0;
        while (0 < array.length) {
            sb.append(array[0].getName());
            sb.append(":");
            ++n;
        }
        while (0 < array2.length) {
            final byte b2 = array2[0];
            final int n2 = b2 & 0xF;
            final int n3 = b2 >> 4 & 0xF;
            sb.append(ProxyFactory.hexDigits[n2]);
            sb.append(ProxyFactory.hexDigits[n3]);
            ++n;
        }
        if (b) {
            sb.append(":w");
        }
        return sb.toString();
    }
    
    private void createClass2(final ClassLoader classLoader, final MethodHandles.Lookup lookup) {
        final String key = this.getKey(this.superClass, this.interfaces, this.signature, this.factoryWriteReplace);
        Object o = ProxyFactory.proxyCache.get(classLoader);
        if (o == null) {
            o = new HashMap<ClassLoader, ProxyDetails>();
            ProxyFactory.proxyCache.put(classLoader, o);
        }
        final ProxyDetails proxyDetails = ((Map<ClassLoader, ProxyDetails>)o).get(key);
        if (proxyDetails != null) {
            this.thisClass = (Class)proxyDetails.proxyClass.get();
            if (this.thisClass != null) {
                return;
            }
        }
        this.createClass3(classLoader, lookup);
        ((Map<ClassLoader, ProxyDetails>)o).put((ClassLoader)key, new ProxyDetails(this.signature, this.thisClass, this.factoryWriteReplace));
    }
    
    private void createClass3(final ClassLoader classLoader, final MethodHandles.Lookup lookup) {
        this.allocateClassName();
        final ClassFile make = this.make();
        if (this.writeDirectory != null) {
            FactoryHelper.writeFile(make, this.writeDirectory);
        }
        if (lookup == null) {
            this.thisClass = FactoryHelper.toClass(make, this.getClassInTheSamePackage(), classLoader, this.getDomain());
        }
        else {
            this.thisClass = FactoryHelper.toClass(make, lookup);
        }
        this.setField("_filter_signature", this.signature);
        if (!this.factoryUseCache) {
            this.setField("default_interceptor", this.handler);
        }
    }
    
    private Class getClassInTheSamePackage() {
        if (this.basename.startsWith("com.viaversion.viaversion.libs.javassist.util.proxy.")) {
            return this.getClass();
        }
        if (this.superClass != null && this.superClass != ProxyFactory.OBJECT_TYPE) {
            return this.superClass;
        }
        if (this.interfaces != null && this.interfaces.length > 0) {
            return this.interfaces[0];
        }
        return this.getClass();
    }
    
    private void setField(final String s, final Object o) {
        if (this.thisClass != null && o != null) {
            final Field field = this.thisClass.getField(s);
            SecurityActions.setAccessible(field, true);
            field.set(null, o);
            SecurityActions.setAccessible(field, false);
        }
    }
    
    static byte[] getFilterSignature(final Class clazz) {
        return (byte[])getField(clazz, "_filter_signature");
    }
    
    private static Object getField(final Class clazz, final String s) {
        final Field field = clazz.getField(s);
        field.setAccessible(true);
        final Object value = field.get(null);
        field.setAccessible(false);
        return value;
    }
    
    public static MethodHandler getHandler(final Proxy proxy) {
        final Field declaredField = proxy.getClass().getDeclaredField("handler");
        declaredField.setAccessible(true);
        final Object value = declaredField.get(proxy);
        declaredField.setAccessible(false);
        return (MethodHandler)value;
    }
    
    protected ClassLoader getClassLoader() {
        return ProxyFactory.classLoaderProvider.get(this);
    }
    
    protected ClassLoader getClassLoader0() {
        ClassLoader classLoader = null;
        if (this.superClass != null && !this.superClass.getName().equals("java.lang.Object")) {
            classLoader = this.superClass.getClassLoader();
        }
        else if (this.interfaces != null && this.interfaces.length > 0) {
            classLoader = this.interfaces[0].getClassLoader();
        }
        if (classLoader == null) {
            classLoader = this.getClass().getClassLoader();
            if (classLoader == null) {
                classLoader = Thread.currentThread().getContextClassLoader();
                if (classLoader == null) {
                    classLoader = ClassLoader.getSystemClassLoader();
                }
            }
        }
        return classLoader;
    }
    
    protected ProtectionDomain getDomain() {
        Class<? extends ProxyFactory> clazz;
        if (this.superClass != null && !this.superClass.getName().equals("java.lang.Object")) {
            clazz = (Class<? extends ProxyFactory>)this.superClass;
        }
        else if (this.interfaces != null && this.interfaces.length > 0) {
            clazz = (Class<? extends ProxyFactory>)this.interfaces[0];
        }
        else {
            clazz = this.getClass();
        }
        return clazz.getProtectionDomain();
    }
    
    public Object create(final Class[] array, final Object[] array2, final MethodHandler handler) throws NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        final Object create = this.create(array, array2);
        ((Proxy)create).setHandler(handler);
        return create;
    }
    
    public Object create(final Class[] array, final Object[] array2) throws NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return this.createClass().getConstructor((Class<?>[])array).newInstance(array2);
    }
    
    @Deprecated
    public void setHandler(final MethodHandler handler) {
        if (this.factoryUseCache && handler != null) {
            this.factoryUseCache = false;
            this.thisClass = null;
        }
        this.setField("default_interceptor", this.handler = handler);
    }
    
    private static String makeProxyName(final String s) {
        // monitorenter(nameGenerator = ProxyFactory.nameGenerator)
        // monitorexit(nameGenerator)
        return ProxyFactory.nameGenerator.get(s);
    }
    
    private ClassFile make() throws CannotCompileException {
        final ClassFile classFile = new ClassFile(false, this.classname, this.superName);
        classFile.setAccessFlags(1);
        setInterfaces(classFile, this.interfaces, (Class)(this.hasGetHandler ? Proxy.class : ProxyObject.class));
        final ConstPool constPool = classFile.getConstPool();
        if (!this.factoryUseCache) {
            final FieldInfo fieldInfo = new FieldInfo(constPool, "default_interceptor", ProxyFactory.HANDLER_TYPE);
            fieldInfo.setAccessFlags(9);
            classFile.addField(fieldInfo);
        }
        final FieldInfo fieldInfo2 = new FieldInfo(constPool, "handler", ProxyFactory.HANDLER_TYPE);
        fieldInfo2.setAccessFlags(2);
        classFile.addField(fieldInfo2);
        final FieldInfo fieldInfo3 = new FieldInfo(constPool, "_filter_signature", "[B");
        fieldInfo3.setAccessFlags(9);
        classFile.addField(fieldInfo3);
        final FieldInfo fieldInfo4 = new FieldInfo(constPool, "serialVersionUID", "J");
        fieldInfo4.setAccessFlags(25);
        classFile.addField(fieldInfo4);
        if (this.genericSignature != null) {
            classFile.addAttribute(new SignatureAttribute(constPool, this.genericSignature));
        }
        this.makeConstructors(this.classname, classFile, constPool, this.classname);
        final ArrayList list = new ArrayList();
        addClassInitializer(classFile, constPool, this.classname, this.overrideMethods(classFile, constPool, this.classname, list), list);
        addSetter(this.classname, classFile, constPool);
        if (!this.hasGetHandler) {
            addGetter(this.classname, classFile, constPool);
        }
        if (this.factoryWriteReplace) {
            classFile.addMethod(makeWriteReplace(constPool));
        }
        this.thisClass = null;
        return classFile;
    }
    
    private void checkClassAndSuperName() {
        if (this.interfaces == null) {
            this.interfaces = new Class[0];
        }
        if (this.superClass == null) {
            this.superClass = ProxyFactory.OBJECT_TYPE;
            this.superName = this.superClass.getName();
            this.basename = ((this.interfaces.length == 0) ? this.superName : this.interfaces[0].getName());
        }
        else {
            this.superName = this.superClass.getName();
            this.basename = this.superName;
        }
        if (Modifier.isFinal(this.superClass.getModifiers())) {
            throw new RuntimeException(this.superName + " is final");
        }
        if (this.basename.startsWith("java.") || this.basename.startsWith("jdk.") || ProxyFactory.onlyPublicMethods) {
            this.basename = "com.viaversion.viaversion.libs.javassist.util.proxy." + this.basename.replace('.', '_');
        }
    }
    
    private void allocateClassName() {
        this.classname = makeProxyName(this.basename);
    }
    
    private void makeSortedMethodList() {
        this.checkClassAndSuperName();
        this.hasGetHandler = false;
        Collections.sort((List<Object>)(this.signatureMethods = new ArrayList(this.getMethods(this.superClass, this.interfaces).entrySet())), ProxyFactory.sorter);
    }
    
    private void computeSignature(final MethodFilter methodFilter) {
        this.makeSortedMethodList();
        final int size = this.signatureMethods.size();
        this.signature = new byte[size + 7 >> 3];
        while (0 < size) {
            final Method method = this.signatureMethods.get(0).getValue();
            final int modifiers = method.getModifiers();
            if (!Modifier.isFinal(modifiers) && !Modifier.isStatic(modifiers) && isVisible(modifiers, this.basename, method) && (methodFilter == null || methodFilter.isHandled(method))) {
                this.setBit(this.signature, 0);
            }
            int n = 0;
            ++n;
        }
    }
    
    private void installSignature(final byte[] signature) {
        this.makeSortedMethodList();
        if (signature.length != this.signatureMethods.size() + 7 >> 3) {
            throw new RuntimeException("invalid filter signature length for deserialized proxy class");
        }
        this.signature = signature;
    }
    
    private boolean testBit(final byte[] array, final int n) {
        final int n2 = n >> 3;
        return n2 <= array.length && (array[n2] & 1 << (n & 0x7)) != 0x0;
    }
    
    private void setBit(final byte[] array, final int n) {
        final int n2 = n >> 3;
        if (n2 < array.length) {
            array[n2] |= (byte)(1 << (n & 0x7));
        }
    }
    
    private static void setInterfaces(final ClassFile classFile, final Class[] array, final Class clazz) {
        final String name = clazz.getName();
        String[] interfaces;
        if (array == null || array.length == 0) {
            interfaces = new String[] { name };
        }
        else {
            interfaces = new String[array.length + 1];
            while (0 < array.length) {
                interfaces[0] = array[0].getName();
                int n = 0;
                ++n;
            }
            interfaces[array.length] = name;
        }
        classFile.setInterfaces(interfaces);
    }
    
    private static void addClassInitializer(final ClassFile classFile, final ConstPool constPool, final String s, final int n, final List list) throws CannotCompileException {
        final FieldInfo fieldInfo = new FieldInfo(constPool, "_methods_", "[Ljava/lang/reflect/Method;");
        fieldInfo.setAccessFlags(10);
        classFile.addField(fieldInfo);
        final MethodInfo methodInfo = new MethodInfo(constPool, "<clinit>", "()V");
        methodInfo.setAccessFlags(8);
        setThrows(methodInfo, constPool, new Class[] { ClassNotFoundException.class });
        final Bytecode bytecode = new Bytecode(constPool, 0, 2);
        bytecode.addIconst(n * 2);
        bytecode.addAnewarray("java.lang.reflect.Method");
        bytecode.addAstore(0);
        bytecode.addLdc(s);
        bytecode.addInvokestatic("java.lang.Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;");
        bytecode.addAstore(1);
        for (final Find2MethodsArgs find2MethodsArgs : list) {
            callFind2Methods(bytecode, find2MethodsArgs.methodName, find2MethodsArgs.delegatorName, find2MethodsArgs.origIndex, find2MethodsArgs.descriptor, 1, 0);
        }
        bytecode.addAload(0);
        bytecode.addPutstatic(s, "_methods_", "[Ljava/lang/reflect/Method;");
        bytecode.addLconst(-1L);
        bytecode.addPutstatic(s, "serialVersionUID", "J");
        bytecode.addOpcode(177);
        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
        classFile.addMethod(methodInfo);
    }
    
    private static void callFind2Methods(final Bytecode bytecode, final String s, final String s2, final int n, final String s3, final int n2, final int n3) {
        final String name = RuntimeSupport.class.getName();
        final String s4 = "(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;[Ljava/lang/reflect/Method;)V";
        bytecode.addAload(n2);
        bytecode.addLdc(s);
        if (s2 == null) {
            bytecode.addOpcode(1);
        }
        else {
            bytecode.addLdc(s2);
        }
        bytecode.addIconst(n);
        bytecode.addLdc(s3);
        bytecode.addAload(n3);
        bytecode.addInvokestatic(name, "find2Methods", s4);
    }
    
    private static void addSetter(final String s, final ClassFile classFile, final ConstPool constPool) throws CannotCompileException {
        final MethodInfo methodInfo = new MethodInfo(constPool, "setHandler", ProxyFactory.HANDLER_SETTER_TYPE);
        methodInfo.setAccessFlags(1);
        final Bytecode bytecode = new Bytecode(constPool, 2, 2);
        bytecode.addAload(0);
        bytecode.addAload(1);
        bytecode.addPutfield(s, "handler", ProxyFactory.HANDLER_TYPE);
        bytecode.addOpcode(177);
        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
        classFile.addMethod(methodInfo);
    }
    
    private static void addGetter(final String s, final ClassFile classFile, final ConstPool constPool) throws CannotCompileException {
        final MethodInfo methodInfo = new MethodInfo(constPool, "getHandler", ProxyFactory.HANDLER_GETTER_TYPE);
        methodInfo.setAccessFlags(1);
        final Bytecode bytecode = new Bytecode(constPool, 1, 1);
        bytecode.addAload(0);
        bytecode.addGetfield(s, "handler", ProxyFactory.HANDLER_TYPE);
        bytecode.addOpcode(176);
        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
        classFile.addMethod(methodInfo);
    }
    
    private int overrideMethods(final ClassFile classFile, final ConstPool constPool, final String s, final List list) throws CannotCompileException {
        final String uniqueName = makeUniqueName("_d", this.signatureMethods);
        for (final Map.Entry<K, Method> entry : this.signatureMethods) {
            if ((ClassFile.MAJOR_VERSION < 49 || !isBridge(entry.getValue())) && this.testBit(this.signature, 0)) {
                this.override(s, entry.getValue(), uniqueName, 0, keyToDesc((String)entry.getKey(), entry.getValue()), classFile, constPool, list);
            }
            int n = 0;
            ++n;
        }
        return 0;
    }
    
    private static boolean isBridge(final Method method) {
        return method.isBridge();
    }
    
    private void override(final String s, final Method method, final String s2, final int n, final String s3, final ClassFile classFile, final ConstPool constPool, final List list) throws CannotCompileException {
        final Class<?> declaringClass = method.getDeclaringClass();
        String string = s2 + n + method.getName();
        if (Modifier.isAbstract(method.getModifiers())) {
            string = null;
        }
        else {
            final MethodInfo delegator = this.makeDelegator(method, s3, constPool, declaringClass, string);
            delegator.setAccessFlags(delegator.getAccessFlags() & 0xFFFFFFBF);
            classFile.addMethod(delegator);
        }
        classFile.addMethod(makeForwarder(s, method, s3, constPool, declaringClass, string, n, list));
    }
    
    private void makeConstructors(final String s, final ClassFile classFile, final ConstPool constPool, final String s2) throws CannotCompileException {
        final Constructor[] declaredConstructors = SecurityActions.getDeclaredConstructors(this.superClass);
        final boolean b = !this.factoryUseCache;
        while (0 < declaredConstructors.length) {
            final Constructor constructor = declaredConstructors[0];
            final int modifiers = constructor.getModifiers();
            if (!Modifier.isFinal(modifiers) && !Modifier.isPrivate(modifiers) && isVisible(modifiers, this.basename, constructor)) {
                classFile.addMethod(makeConstructor(s, constructor, constPool, this.superClass, b));
            }
            int n = 0;
            ++n;
        }
    }
    
    private static String makeUniqueName(final String s, final List list) {
        if (makeUniqueName0(s, list.iterator())) {
            return s;
        }
        while (100 < 999) {
            final String string = s + 100;
            if (makeUniqueName0(string, list.iterator())) {
                return string;
            }
            int n = 0;
            ++n;
        }
        throw new RuntimeException("cannot make a unique method name");
    }
    
    private static boolean makeUniqueName0(final String s, final Iterator iterator) {
        while (iterator.hasNext()) {
            if (iterator.next().getKey().startsWith(s)) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean isVisible(final int n, final String s, final Member member) {
        if ((n & 0x2) != 0x0) {
            return false;
        }
        if ((n & 0x5) != 0x0) {
            return true;
        }
        final String packageName = getPackageName(s);
        final String packageName2 = getPackageName(member.getDeclaringClass().getName());
        if (packageName == null) {
            return packageName2 == null;
        }
        return packageName.equals(packageName2);
    }
    
    private static String getPackageName(final String s) {
        final int lastIndex = s.lastIndexOf(46);
        if (lastIndex < 0) {
            return null;
        }
        return s.substring(0, lastIndex);
    }
    
    private Map getMethods(final Class clazz, final Class[] array) {
        final HashMap hashMap = new HashMap();
        final HashSet set = new HashSet();
        while (0 < array.length) {
            this.getMethods(hashMap, array[0], set);
            int n = 0;
            ++n;
        }
        this.getMethods(hashMap, clazz, set);
        return hashMap;
    }
    
    private void getMethods(final Map map, final Class clazz, final Set set) {
        if (!set.add(clazz)) {
            return;
        }
        final Class[] interfaces = clazz.getInterfaces();
        while (0 < interfaces.length) {
            this.getMethods(map, interfaces[0], set);
            int n = 0;
            ++n;
        }
        final Class superclass = clazz.getSuperclass();
        if (superclass != null) {
            this.getMethods(map, superclass, set);
        }
        final Method[] declaredMethods = SecurityActions.getDeclaredMethods(clazz);
        while (0 < declaredMethods.length) {
            if (!Modifier.isPrivate(declaredMethods[0].getModifiers())) {
                final Method method = declaredMethods[0];
                final String string = method.getName() + ':' + RuntimeSupport.makeDescriptor(method);
                if (string.startsWith("getHandler:()")) {
                    this.hasGetHandler = true;
                }
                final Method method2 = map.put(string, method);
                if (null != method2 && isBridge(method) && !Modifier.isPublic(method2.getDeclaringClass().getModifiers()) && !Modifier.isAbstract(method2.getModifiers()) && !isDuplicated(0, declaredMethods)) {
                    map.put(string, method2);
                }
                if (null != method2 && Modifier.isPublic(method2.getModifiers()) && !Modifier.isPublic(method.getModifiers())) {
                    map.put(string, method2);
                }
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    private static boolean isDuplicated(final int n, final Method[] array) {
        final String name = array[n].getName();
        while (0 < array.length) {
            if (0 != n && name.equals(array[0].getName()) && areParametersSame(array[n], array[0])) {
                return true;
            }
            int n2 = 0;
            ++n2;
        }
        return false;
    }
    
    private static boolean areParametersSame(final Method method, final Method method2) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Class<?>[] parameterTypes2 = method2.getParameterTypes();
        if (parameterTypes.length == parameterTypes2.length) {
            while (0 < parameterTypes.length) {
                if (!parameterTypes[0].getName().equals(parameterTypes2[0].getName())) {
                    return false;
                }
                int n = 0;
                ++n;
            }
            return true;
        }
        return false;
    }
    
    private static String keyToDesc(final String s, final Method method) {
        return s.substring(s.indexOf(58) + 1);
    }
    
    private static MethodInfo makeConstructor(final String s, final Constructor constructor, final ConstPool constPool, final Class clazz, final boolean b) {
        final String descriptor = RuntimeSupport.makeDescriptor(constructor.getParameterTypes(), Void.TYPE);
        final MethodInfo methodInfo = new MethodInfo(constPool, "<init>", descriptor);
        methodInfo.setAccessFlags(1);
        setThrows(methodInfo, constPool, constructor.getExceptionTypes());
        final Bytecode bytecode = new Bytecode(constPool, 0, 0);
        if (b) {
            bytecode.addAload(0);
            bytecode.addGetstatic(s, "default_interceptor", ProxyFactory.HANDLER_TYPE);
            bytecode.addPutfield(s, "handler", ProxyFactory.HANDLER_TYPE);
            bytecode.addGetstatic(s, "default_interceptor", ProxyFactory.HANDLER_TYPE);
            bytecode.addOpcode(199);
            bytecode.addIndex(10);
        }
        bytecode.addAload(0);
        bytecode.addGetstatic("com.viaversion.viaversion.libs.javassist.util.proxy.RuntimeSupport", "default_interceptor", ProxyFactory.HANDLER_TYPE);
        bytecode.addPutfield(s, "handler", ProxyFactory.HANDLER_TYPE);
        final int currentPc = bytecode.currentPc();
        bytecode.addAload(0);
        final int addLoadParameters = addLoadParameters(bytecode, constructor.getParameterTypes(), 1);
        bytecode.addInvokespecial(clazz.getName(), "<init>", descriptor);
        bytecode.addOpcode(177);
        bytecode.setMaxLocals(addLoadParameters + 1);
        final CodeAttribute codeAttribute = bytecode.toCodeAttribute();
        methodInfo.setCodeAttribute(codeAttribute);
        final StackMapTable.Writer writer = new StackMapTable.Writer(32);
        writer.sameFrame(currentPc);
        codeAttribute.setAttribute(writer.toStackMapTable(constPool));
        return methodInfo;
    }
    
    private MethodInfo makeDelegator(final Method method, final String s, final ConstPool constPool, final Class clazz, final String s2) {
        final MethodInfo methodInfo = new MethodInfo(constPool, s2, s);
        methodInfo.setAccessFlags(0x11 | (method.getModifiers() & 0xFFFFFAD9));
        setThrows(methodInfo, constPool, method);
        final Bytecode bytecode = new Bytecode(constPool, 0, 0);
        bytecode.addAload(0);
        int addLoadParameters = addLoadParameters(bytecode, method.getParameterTypes(), 1);
        final Class invokespecialTarget = this.invokespecialTarget(clazz);
        bytecode.addInvokespecial(invokespecialTarget.isInterface(), constPool.addClassInfo(invokespecialTarget.getName()), method.getName(), s);
        addReturn(bytecode, method.getReturnType());
        bytecode.setMaxLocals(++addLoadParameters);
        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
        return methodInfo;
    }
    
    private Class invokespecialTarget(final Class clazz) {
        if (clazz.isInterface()) {
            final Class[] interfaces = this.interfaces;
            while (0 < interfaces.length) {
                final Class clazz2 = interfaces[0];
                if (clazz.isAssignableFrom(clazz2)) {
                    return clazz2;
                }
                int n = 0;
                ++n;
            }
        }
        return this.superClass;
    }
    
    private static MethodInfo makeForwarder(final String s, final Method method, final String s2, final ConstPool constPool, final Class clazz, final String s3, final int n, final List list) {
        final MethodInfo methodInfo = new MethodInfo(constPool, method.getName(), s2);
        methodInfo.setAccessFlags(0x10 | (method.getModifiers() & 0xFFFFFADF));
        setThrows(methodInfo, constPool, method);
        final int paramSize = Descriptor.paramSize(s2);
        final Bytecode bytecode = new Bytecode(constPool, 0, paramSize + 2);
        final int n2 = n * 2;
        final int n3 = n * 2 + 1;
        final int n4 = paramSize + 1;
        bytecode.addGetstatic(s, "_methods_", "[Ljava/lang/reflect/Method;");
        bytecode.addAstore(n4);
        list.add(new Find2MethodsArgs(method.getName(), s3, s2, n2));
        bytecode.addAload(0);
        bytecode.addGetfield(s, "handler", ProxyFactory.HANDLER_TYPE);
        bytecode.addAload(0);
        bytecode.addAload(n4);
        bytecode.addIconst(n2);
        bytecode.addOpcode(50);
        bytecode.addAload(n4);
        bytecode.addIconst(n3);
        bytecode.addOpcode(50);
        makeParameterList(bytecode, method.getParameterTypes());
        bytecode.addInvokeinterface(MethodHandler.class.getName(), "invoke", "(Ljava/lang/Object;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;", 5);
        final Class<?> returnType = method.getReturnType();
        addUnwrapper(bytecode, returnType);
        addReturn(bytecode, returnType);
        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
        return methodInfo;
    }
    
    private static void setThrows(final MethodInfo methodInfo, final ConstPool constPool, final Method method) {
        setThrows(methodInfo, constPool, method.getExceptionTypes());
    }
    
    private static void setThrows(final MethodInfo methodInfo, final ConstPool constPool, final Class[] array) {
        if (array.length == 0) {
            return;
        }
        final String[] exceptions = new String[array.length];
        while (0 < array.length) {
            exceptions[0] = array[0].getName();
            int n = 0;
            ++n;
        }
        final ExceptionsAttribute exceptionsAttribute = new ExceptionsAttribute(constPool);
        exceptionsAttribute.setExceptions(exceptions);
        methodInfo.setExceptionsAttribute(exceptionsAttribute);
    }
    
    private static int addLoadParameters(final Bytecode bytecode, final Class[] array, final int n) {
        while (0 < array.length) {
            final int n2 = 0 + addLoad(bytecode, 0 + n, array[0]);
            int n3 = 0;
            ++n3;
        }
        return 0;
    }
    
    private static int addLoad(final Bytecode bytecode, final int n, final Class clazz) {
        if (clazz.isPrimitive()) {
            if (clazz == Long.TYPE) {
                bytecode.addLload(n);
                return 2;
            }
            if (clazz == Float.TYPE) {
                bytecode.addFload(n);
            }
            else {
                if (clazz == Double.TYPE) {
                    bytecode.addDload(n);
                    return 2;
                }
                bytecode.addIload(n);
            }
        }
        else {
            bytecode.addAload(n);
        }
        return 1;
    }
    
    private static int addReturn(final Bytecode bytecode, final Class clazz) {
        if (clazz.isPrimitive()) {
            if (clazz == Long.TYPE) {
                bytecode.addOpcode(173);
                return 2;
            }
            if (clazz == Float.TYPE) {
                bytecode.addOpcode(174);
            }
            else {
                if (clazz == Double.TYPE) {
                    bytecode.addOpcode(175);
                    return 2;
                }
                if (clazz == Void.TYPE) {
                    bytecode.addOpcode(177);
                    return 0;
                }
                bytecode.addOpcode(172);
            }
        }
        else {
            bytecode.addOpcode(176);
        }
        return 1;
    }
    
    private static void makeParameterList(final Bytecode bytecode, final Class[] array) {
        final int length = array.length;
        bytecode.addIconst(length);
        bytecode.addAnewarray("java/lang/Object");
        while (0 < length) {
            bytecode.addOpcode(89);
            bytecode.addIconst(0);
            final Class clazz = array[0];
            if (clazz.isPrimitive()) {
                final int wrapper = makeWrapper(bytecode, clazz, 1);
            }
            else {
                bytecode.addAload(1);
                int wrapper = 0;
                ++wrapper;
            }
            bytecode.addOpcode(83);
            int n = 0;
            ++n;
        }
    }
    
    private static int makeWrapper(final Bytecode bytecode, final Class clazz, final int n) {
        final int typeIndex = FactoryHelper.typeIndex(clazz);
        final String s = FactoryHelper.wrapperTypes[typeIndex];
        bytecode.addNew(s);
        bytecode.addOpcode(89);
        addLoad(bytecode, n, clazz);
        bytecode.addInvokespecial(s, "<init>", FactoryHelper.wrapperDesc[typeIndex]);
        return n + FactoryHelper.dataSize[typeIndex];
    }
    
    private static void addUnwrapper(final Bytecode bytecode, final Class clazz) {
        if (clazz.isPrimitive()) {
            if (clazz == Void.TYPE) {
                bytecode.addOpcode(87);
            }
            else {
                final int typeIndex = FactoryHelper.typeIndex(clazz);
                final String s = FactoryHelper.wrapperTypes[typeIndex];
                bytecode.addCheckcast(s);
                bytecode.addInvokevirtual(s, FactoryHelper.unwarpMethods[typeIndex], FactoryHelper.unwrapDesc[typeIndex]);
            }
        }
        else {
            bytecode.addCheckcast(clazz.getName());
        }
    }
    
    private static MethodInfo makeWriteReplace(final ConstPool constPool) {
        final MethodInfo methodInfo = new MethodInfo(constPool, "writeReplace", "()Ljava/lang/Object;");
        final String[] exceptions = { "java.io.ObjectStreamException" };
        final ExceptionsAttribute exceptionsAttribute = new ExceptionsAttribute(constPool);
        exceptionsAttribute.setExceptions(exceptions);
        methodInfo.setExceptionsAttribute(exceptionsAttribute);
        final Bytecode bytecode = new Bytecode(constPool, 0, 1);
        bytecode.addAload(0);
        bytecode.addInvokestatic("com.viaversion.viaversion.libs.javassist.util.proxy.RuntimeSupport", "makeSerializedProxy", "(Ljava/lang/Object;)Ljavassist/util/proxy/SerializedProxy;");
        bytecode.addOpcode(176);
        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
        return methodInfo;
    }
    
    static {
        ProxyFactory.onlyPublicMethods = false;
        OBJECT_TYPE = Object.class;
        HANDLER_TYPE = 'L' + MethodHandler.class.getName().replace('.', '/') + ';';
        HANDLER_SETTER_TYPE = "(" + ProxyFactory.HANDLER_TYPE + ")V";
        HANDLER_GETTER_TYPE = "()" + ProxyFactory.HANDLER_TYPE;
        ProxyFactory.useCache = true;
        ProxyFactory.useWriteReplace = true;
        ProxyFactory.proxyCache = new WeakHashMap();
        ProxyFactory.hexDigits = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        ProxyFactory.classLoaderProvider = new ClassLoaderProvider() {
            @Override
            public ClassLoader get(final ProxyFactory proxyFactory) {
                return proxyFactory.getClassLoader0();
            }
        };
        ProxyFactory.nameGenerator = new UniqueName() {
            private final String sep = "_$$_jvst" + Integer.toHexString(this.hashCode() & 0xFFF) + "_";
            private int counter = 0;
            
            @Override
            public String get(final String s) {
                return s + this.sep + Integer.toHexString(this.counter++);
            }
        };
        ProxyFactory.sorter = new Comparator() {
            public int compare(final Map.Entry entry, final Map.Entry entry2) {
                return entry.getKey().compareTo((String)entry2.getKey());
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((Map.Entry)o, (Map.Entry)o2);
            }
        };
    }
    
    static class Find2MethodsArgs
    {
        String methodName;
        String delegatorName;
        String descriptor;
        int origIndex;
        
        Find2MethodsArgs(final String methodName, final String delegatorName, final String descriptor, final int origIndex) {
            this.methodName = methodName;
            this.delegatorName = delegatorName;
            this.descriptor = descriptor;
            this.origIndex = origIndex;
        }
    }
    
    public interface UniqueName
    {
        String get(final String p0);
    }
    
    public interface ClassLoaderProvider
    {
        ClassLoader get(final ProxyFactory p0);
    }
    
    static class ProxyDetails
    {
        byte[] signature;
        Reference proxyClass;
        boolean isUseWriteReplace;
        
        ProxyDetails(final byte[] signature, final Class clazz, final boolean isUseWriteReplace) {
            this.signature = signature;
            this.proxyClass = new WeakReference(clazz);
            this.isUseWriteReplace = isUseWriteReplace;
        }
    }
}
