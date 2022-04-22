package com.google.common.io;

import com.google.common.annotations.*;
import java.util.logging.*;
import java.io.*;

@Beta
public final class Flushables
{
    private static final Logger logger;
    
    private Flushables() {
    }
    
    public static void flush(final Flushable flushable, final boolean b) throws IOException {
        flushable.flush();
    }
    
    public static void flushQuietly(final Flushable flushable) {
        flush(flushable, true);
    }
    
    static {
        logger = Logger.getLogger(Flushables.class.getName());
    }
}
