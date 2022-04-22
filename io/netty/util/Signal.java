package io.netty.util;

import java.util.concurrent.*;
import io.netty.util.internal.*;

public final class Signal extends Error
{
    private static final long serialVersionUID = -221145131122459977L;
    private static final ConcurrentMap map;
    private final UniqueName uname;
    
    public static Signal valueOf(final String s) {
        return new Signal(s);
    }
    
    @Deprecated
    public Signal(final String s) {
        super(s);
        this.uname = new UniqueName(Signal.map, s, new Object[0]);
    }
    
    public void expect(final Signal signal) {
        if (this != signal) {
            throw new IllegalStateException("unexpected signal: " + signal);
        }
    }
    
    @Override
    public Throwable initCause(final Throwable t) {
        return this;
    }
    
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
    
    @Override
    public String toString() {
        return this.uname.name();
    }
    
    static {
        map = PlatformDependent.newConcurrentHashMap();
    }
}
