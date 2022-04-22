package io.netty.handler.codec.serialization;

class ClassLoaderClassResolver implements ClassResolver
{
    private final ClassLoader classLoader;
    
    ClassLoaderClassResolver(final ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
    
    @Override
    public Class resolve(final String s) throws ClassNotFoundException {
        return this.classLoader.loadClass(s);
    }
}
