package org.apache.logging.log4j.core.impl;

public final class ContextAnchor
{
    public static final ThreadLocal THREAD_CONTEXT;
    
    private ContextAnchor() {
    }
    
    static {
        THREAD_CONTEXT = new ThreadLocal();
    }
}
