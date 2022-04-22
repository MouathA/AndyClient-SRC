package com.google.common.util.concurrent;

import javax.annotation.*;
import java.util.concurrent.*;
import com.google.common.base.*;

public final class Callables
{
    private Callables() {
    }
    
    public static Callable returning(@Nullable final Object o) {
        return new Callable(o) {
            final Object val$value;
            
            @Override
            public Object call() {
                return this.val$value;
            }
        };
    }
    
    static Callable threadRenaming(final Callable callable, final Supplier supplier) {
        Preconditions.checkNotNull(supplier);
        Preconditions.checkNotNull(callable);
        return new Callable(supplier, callable) {
            final Supplier val$nameSupplier;
            final Callable val$callable;
            
            @Override
            public Object call() throws Exception {
                final Thread currentThread = Thread.currentThread();
                final String name = currentThread.getName();
                final boolean access$000 = Callables.access$000((String)this.val$nameSupplier.get(), currentThread);
                final Object call = this.val$callable.call();
                if (access$000) {
                    Callables.access$000(name, currentThread);
                }
                return call;
            }
        };
    }
    
    static Runnable threadRenaming(final Runnable runnable, final Supplier supplier) {
        Preconditions.checkNotNull(supplier);
        Preconditions.checkNotNull(runnable);
        return new Runnable(supplier, runnable) {
            final Supplier val$nameSupplier;
            final Runnable val$task;
            
            @Override
            public void run() {
                final Thread currentThread = Thread.currentThread();
                final String name = currentThread.getName();
                final boolean access$000 = Callables.access$000((String)this.val$nameSupplier.get(), currentThread);
                this.val$task.run();
                if (access$000) {
                    Callables.access$000(name, currentThread);
                }
            }
        };
    }
    
    private static boolean trySetName(final String name, final Thread thread) {
        thread.setName(name);
        return true;
    }
    
    static boolean access$000(final String s, final Thread thread) {
        return trySetName(s, thread);
    }
}
