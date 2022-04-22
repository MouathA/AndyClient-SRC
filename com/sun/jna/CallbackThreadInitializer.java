package com.sun.jna;

public class CallbackThreadInitializer
{
    private boolean daemon;
    private boolean detach;
    private String name;
    private ThreadGroup group;
    
    public CallbackThreadInitializer() {
        this(true);
    }
    
    public CallbackThreadInitializer(final boolean b) {
        this(b, false);
    }
    
    public CallbackThreadInitializer(final boolean b, final boolean b2) {
        this(b, b2, null);
    }
    
    public CallbackThreadInitializer(final boolean b, final boolean b2, final String s) {
        this(b, b2, s, null);
    }
    
    public CallbackThreadInitializer(final boolean daemon, final boolean detach, final String name, final ThreadGroup group) {
        this.daemon = daemon;
        this.detach = detach;
        this.name = name;
        this.group = group;
    }
    
    public String getName(final Callback callback) {
        return this.name;
    }
    
    public ThreadGroup getThreadGroup(final Callback callback) {
        return this.group;
    }
    
    public boolean isDaemon(final Callback callback) {
        return this.daemon;
    }
    
    public boolean detach(final Callback callback) {
        return this.detach;
    }
}
