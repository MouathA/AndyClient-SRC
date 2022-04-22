package io.netty.util.internal.logging;

public abstract class InternalLoggerFactory
{
    private static InternalLoggerFactory defaultFactory;
    
    private static InternalLoggerFactory newDefaultFactory(final String s) {
        final Slf4JLoggerFactory slf4JLoggerFactory = new Slf4JLoggerFactory(true);
        slf4JLoggerFactory.newInstance(s).debug("Using SLF4J as the default logging framework");
        return slf4JLoggerFactory;
    }
    
    public static InternalLoggerFactory getDefaultFactory() {
        return InternalLoggerFactory.defaultFactory;
    }
    
    public static void setDefaultFactory(final InternalLoggerFactory defaultFactory) {
        if (defaultFactory == null) {
            throw new NullPointerException("defaultFactory");
        }
        InternalLoggerFactory.defaultFactory = defaultFactory;
    }
    
    public static InternalLogger getInstance(final Class clazz) {
        return getInstance(clazz.getName());
    }
    
    public static InternalLogger getInstance(final String s) {
        return getDefaultFactory().newInstance(s);
    }
    
    protected abstract InternalLogger newInstance(final String p0);
    
    static {
        InternalLoggerFactory.defaultFactory = newDefaultFactory(InternalLoggerFactory.class.getName());
    }
}
