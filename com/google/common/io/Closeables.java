package com.google.common.io;

import java.util.logging.*;
import com.google.common.annotations.*;
import javax.annotation.*;
import java.io.*;

@Beta
public final class Closeables
{
    @VisibleForTesting
    static final Logger logger;
    
    private Closeables() {
    }
    
    public static void close(@Nullable final Closeable closeable, final boolean b) throws IOException {
        if (closeable == null) {
            return;
        }
        closeable.close();
    }
    
    public static void closeQuietly(@Nullable final InputStream inputStream) {
        close(inputStream, true);
    }
    
    public static void closeQuietly(@Nullable final Reader reader) {
        close(reader, true);
    }
    
    static {
        logger = Logger.getLogger(Closeables.class.getName());
    }
}
