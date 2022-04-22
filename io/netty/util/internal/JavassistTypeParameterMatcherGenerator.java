package io.netty.util.internal;

import io.netty.util.internal.logging.*;
import javassist.*;

public final class JavassistTypeParameterMatcherGenerator
{
    private static final InternalLogger logger;
    private static final ClassPool classPool;
    
    public static void appendClassPath(final ClassPath classPath) {
        JavassistTypeParameterMatcherGenerator.classPool.appendClassPath(classPath);
    }
    
    public static void appendClassPath(final String s) throws NotFoundException {
        JavassistTypeParameterMatcherGenerator.classPool.appendClassPath(s);
    }
    
    public static TypeParameterMatcher generate(final Class clazz) {
        ClassLoader classLoader = PlatformDependent.getContextClassLoader();
        if (classLoader == null) {
            classLoader = PlatformDependent.getSystemClassLoader();
        }
        return generate(clazz, classLoader);
    }
    
    public static TypeParameterMatcher generate(final Class clazz, final ClassLoader classLoader) {
        return (TypeParameterMatcher)Class.forName("io.netty.util.internal.__matchers__." + typeName(clazz) + "Matcher", true, classLoader).newInstance();
    }
    
    private static String typeName(final Class clazz) {
        if (clazz.isArray()) {
            return typeName(clazz.getComponentType()) + "[]";
        }
        return clazz.getName();
    }
    
    private JavassistTypeParameterMatcherGenerator() {
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(JavassistTypeParameterMatcherGenerator.class);
        (classPool = new ClassPool(true)).appendClassPath((ClassPath)new ClassClassPath((Class)NoOpTypeParameterMatcher.class));
    }
}
