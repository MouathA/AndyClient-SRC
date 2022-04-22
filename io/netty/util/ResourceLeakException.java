package io.netty.util;

import java.util.*;

@Deprecated
public class ResourceLeakException extends RuntimeException
{
    private static final long serialVersionUID = 7186453858343358280L;
    private final StackTraceElement[] cachedStackTrace;
    
    public ResourceLeakException() {
        this.cachedStackTrace = this.getStackTrace();
    }
    
    public ResourceLeakException(final String s) {
        super(s);
        this.cachedStackTrace = this.getStackTrace();
    }
    
    public ResourceLeakException(final String s, final Throwable t) {
        super(s, t);
        this.cachedStackTrace = this.getStackTrace();
    }
    
    public ResourceLeakException(final Throwable t) {
        super(t);
        this.cachedStackTrace = this.getStackTrace();
    }
    
    @Override
    public int hashCode() {
        final StackTraceElement[] cachedStackTrace = this.cachedStackTrace;
        while (0 < cachedStackTrace.length) {
            final int n = 0 + cachedStackTrace[0].hashCode();
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof ResourceLeakException && (o == this || Arrays.equals(this.cachedStackTrace, ((ResourceLeakException)o).cachedStackTrace));
    }
}
