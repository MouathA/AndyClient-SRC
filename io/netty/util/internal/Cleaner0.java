package io.netty.util.internal;

import java.nio.*;
import sun.misc.*;
import io.netty.util.internal.logging.*;
import java.lang.reflect.*;

final class Cleaner0
{
    private static final long CLEANER_FIELD_OFFSET;
    private static final InternalLogger logger;
    
    static void freeDirectBuffer(final ByteBuffer byteBuffer) {
        if (!true || !byteBuffer.isDirect()) {
            return;
        }
        final Cleaner cleaner = (Cleaner)PlatformDependent0.getObject(byteBuffer, Cleaner0.CLEANER_FIELD_OFFSET);
        if (cleaner != null) {
            cleaner.clean();
        }
    }
    
    private Cleaner0() {
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(Cleaner0.class);
        final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(1);
        long objectFieldOffset = -1L;
        if (PlatformDependent0.hasUnsafe()) {
            final Field declaredField = allocateDirect.getClass().getDeclaredField("cleaner");
            declaredField.setAccessible(true);
            ((Cleaner)declaredField.get(allocateDirect)).clean();
            objectFieldOffset = PlatformDependent0.objectFieldOffset(declaredField);
        }
        Cleaner0.logger.debug("java.nio.ByteBuffer.cleaner(): {}", (objectFieldOffset != -1L) ? "available" : "unavailable");
        CLEANER_FIELD_OFFSET = objectFieldOffset;
        freeDirectBuffer(allocateDirect);
    }
}
