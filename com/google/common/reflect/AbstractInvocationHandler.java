package com.google.common.reflect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.lang.reflect.*;

@Beta
public abstract class AbstractInvocationHandler implements InvocationHandler
{
    private static final Object[] NO_ARGS;
    
    @Override
    public final Object invoke(final Object o, final Method method, @Nullable Object[] no_ARGS) throws Throwable {
        if (no_ARGS == null) {
            no_ARGS = AbstractInvocationHandler.NO_ARGS;
        }
        if (no_ARGS.length == 0 && method.getName().equals("hashCode")) {
            return this.hashCode();
        }
        if (no_ARGS.length == 1 && method.getName().equals("equals") && method.getParameterTypes()[0] == Object.class) {
            final Object o2 = no_ARGS[0];
            if (o2 == null) {
                return false;
            }
            if (o == o2) {
                return true;
            }
            return o.getClass() == 0 && this.equals(Proxy.getInvocationHandler(o2));
        }
        else {
            if (no_ARGS.length == 0 && method.getName().equals("toString")) {
                return this.toString();
            }
            return this.handleInvocation(o, method, no_ARGS);
        }
    }
    
    protected abstract Object handleInvocation(final Object p0, final Method p1, final Object[] p2) throws Throwable;
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    static {
        NO_ARGS = new Object[0];
    }
}
