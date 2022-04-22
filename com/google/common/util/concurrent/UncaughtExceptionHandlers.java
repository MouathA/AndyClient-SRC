package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import java.util.logging.*;

public final class UncaughtExceptionHandlers
{
    private UncaughtExceptionHandlers() {
    }
    
    public static Thread.UncaughtExceptionHandler systemExit() {
        return new Exiter(Runtime.getRuntime());
    }
    
    @VisibleForTesting
    static final class Exiter implements Thread.UncaughtExceptionHandler
    {
        private static final Logger logger;
        private final Runtime runtime;
        
        Exiter(final Runtime runtime) {
            this.runtime = runtime;
        }
        
        @Override
        public void uncaughtException(final Thread thread, final Throwable t) {
            Exiter.logger.log(Level.SEVERE, String.format("Caught an exception in %s.  Shutting down.", thread), t);
            this.runtime.exit(1);
        }
        
        static {
            logger = Logger.getLogger(Exiter.class.getName());
        }
    }
}
