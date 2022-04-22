package com.google.common.io;

import com.google.common.annotations.*;
import java.util.*;
import javax.annotation.*;
import java.io.*;
import com.google.common.base.*;
import java.lang.reflect.*;
import java.util.logging.*;

@Beta
public final class Closer implements Closeable
{
    private static final Suppressor SUPPRESSOR;
    @VisibleForTesting
    final Suppressor suppressor;
    private final Deque stack;
    private Throwable thrown;
    
    public static Closer create() {
        return new Closer(Closer.SUPPRESSOR);
    }
    
    @VisibleForTesting
    Closer(final Suppressor suppressor) {
        this.stack = new ArrayDeque(4);
        this.suppressor = (Suppressor)Preconditions.checkNotNull(suppressor);
    }
    
    public Closeable register(@Nullable final Closeable closeable) {
        if (closeable != null) {
            this.stack.addFirst(closeable);
        }
        return closeable;
    }
    
    public RuntimeException rethrow(final Throwable thrown) throws IOException {
        Preconditions.checkNotNull(thrown);
        Throwables.propagateIfPossible(this.thrown = thrown, IOException.class);
        throw new RuntimeException(thrown);
    }
    
    public RuntimeException rethrow(final Throwable thrown, final Class clazz) throws IOException, Exception {
        Preconditions.checkNotNull(thrown);
        Throwables.propagateIfPossible(this.thrown = thrown, IOException.class);
        Throwables.propagateIfPossible(thrown, clazz);
        throw new RuntimeException(thrown);
    }
    
    public RuntimeException rethrow(final Throwable thrown, final Class clazz, final Class clazz2) throws IOException, Exception {
        Preconditions.checkNotNull(thrown);
        Throwables.propagateIfPossible(this.thrown = thrown, IOException.class);
        Throwables.propagateIfPossible(thrown, clazz, clazz2);
        throw new RuntimeException(thrown);
    }
    
    @Override
    public void close() throws IOException {
        final Throwable thrown = this.thrown;
        while (!this.stack.isEmpty()) {
            this.stack.removeFirst().close();
        }
        if (this.thrown == null && thrown != null) {
            Throwables.propagateIfPossible(thrown, IOException.class);
            throw new AssertionError((Object)thrown);
        }
    }
    
    static {
        SUPPRESSOR = (SuppressingSuppressor.isAvailable() ? SuppressingSuppressor.INSTANCE : LoggingSuppressor.INSTANCE);
    }
    
    @VisibleForTesting
    static final class SuppressingSuppressor implements Suppressor
    {
        static final SuppressingSuppressor INSTANCE;
        static final Method addSuppressed;
        
        static boolean isAvailable() {
            return SuppressingSuppressor.addSuppressed != null;
        }
        
        private static Method getAddSuppressed() {
            return Throwable.class.getMethod("addSuppressed", Throwable.class);
        }
        
        @Override
        public void suppress(final Closeable closeable, final Throwable t, final Throwable t2) {
            if (t == t2) {
                return;
            }
            SuppressingSuppressor.addSuppressed.invoke(t, t2);
        }
        
        static {
            INSTANCE = new SuppressingSuppressor();
            addSuppressed = getAddSuppressed();
        }
    }
    
    @VisibleForTesting
    interface Suppressor
    {
        void suppress(final Closeable p0, final Throwable p1, final Throwable p2);
    }
    
    @VisibleForTesting
    static final class LoggingSuppressor implements Suppressor
    {
        static final LoggingSuppressor INSTANCE;
        
        @Override
        public void suppress(final Closeable closeable, final Throwable t, final Throwable t2) {
            Closeables.logger.log(Level.WARNING, "Suppressing exception thrown when closing " + closeable, t2);
        }
        
        static {
            INSTANCE = new LoggingSuppressor();
        }
    }
}
