package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.concurrent.*;
import com.google.common.collect.*;
import java.util.*;
import java.lang.reflect.*;

@Beta
public final class SimpleTimeLimiter implements TimeLimiter
{
    private final ExecutorService executor;
    
    public SimpleTimeLimiter(final ExecutorService executorService) {
        this.executor = (ExecutorService)Preconditions.checkNotNull(executorService);
    }
    
    public SimpleTimeLimiter() {
        this(Executors.newCachedThreadPool());
    }
    
    @Override
    public Object newProxy(final Object o, final Class clazz, final long n, final TimeUnit timeUnit) {
        Preconditions.checkNotNull(o);
        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(timeUnit);
        Preconditions.checkArgument(n > 0L, "bad timeout: %s", n);
        Preconditions.checkArgument(clazz.isInterface(), (Object)"interfaceType must be an interface type");
        return newProxy(clazz, new InvocationHandler(o, n, timeUnit, findInterruptibleMethods(clazz)) {
            final Object val$target;
            final long val$timeoutDuration;
            final TimeUnit val$timeoutUnit;
            final Set val$interruptibleMethods;
            final SimpleTimeLimiter this$0;
            
            @Override
            public Object invoke(final Object o, final Method method, final Object[] array) throws Throwable {
                return this.this$0.callWithTimeout(new Callable(method, array) {
                    final Method val$method;
                    final Object[] val$args;
                    final SimpleTimeLimiter$1 this$1;
                    
                    @Override
                    public Object call() throws Exception {
                        return this.val$method.invoke(this.this$1.val$target, this.val$args);
                    }
                }, this.val$timeoutDuration, this.val$timeoutUnit, this.val$interruptibleMethods.contains(method));
            }
        });
    }
    
    @Override
    public Object callWithTimeout(final Callable callable, final long n, final TimeUnit timeUnit, final boolean b) throws Exception {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(timeUnit);
        Preconditions.checkArgument(n > 0L, "timeout must be positive: %s", n);
        final Future<Object> submit = this.executor.submit((Callable<Object>)callable);
        if (b) {
            return submit.get(n, timeUnit);
        }
        return Uninterruptibles.getUninterruptibly(submit, n, timeUnit);
    }
    
    private static Exception throwCause(final Exception ex, final boolean b) throws Exception {
        final Throwable cause = ex.getCause();
        if (cause == null) {
            throw ex;
        }
        if (b) {
            cause.setStackTrace((StackTraceElement[])ObjectArrays.concat(cause.getStackTrace(), ex.getStackTrace(), StackTraceElement.class));
        }
        if (cause instanceof Exception) {
            throw (Exception)cause;
        }
        if (cause instanceof Error) {
            throw (Error)cause;
        }
        throw ex;
    }
    
    private static Set findInterruptibleMethods(final Class clazz) {
        final HashSet hashSet = Sets.newHashSet();
        final Method[] methods = clazz.getMethods();
        while (0 < methods.length) {
            final Method method = methods[0];
            if (declaresInterruptedEx(method)) {
                hashSet.add(method);
            }
            int n = 0;
            ++n;
        }
        return hashSet;
    }
    
    private static boolean declaresInterruptedEx(final Method method) {
        final Class<?>[] exceptionTypes = method.getExceptionTypes();
        while (0 < exceptionTypes.length) {
            if (exceptionTypes[0] == InterruptedException.class) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    private static Object newProxy(final Class clazz, final InvocationHandler invocationHandler) {
        return clazz.cast(Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, invocationHandler));
    }
    
    static Exception access$000(final Exception ex, final boolean b) throws Exception {
        return throwCause(ex, b);
    }
}
