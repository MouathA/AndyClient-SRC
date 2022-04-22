package org.lwjgl.util.mapped;

import java.nio.*;
import org.lwjgl.*;

public final class CacheUtil
{
    private CacheUtil() {
    }
    
    public static int getCacheLineSize() {
        return 64;
    }
    
    public static ByteBuffer createByteBuffer(final int n) {
        final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(n + 64);
        if (MemoryUtil.getAddress(allocateDirect) % 64 != 0L) {
            allocateDirect.position(64 - (int)(MemoryUtil.getAddress(allocateDirect) & (long)63));
        }
        allocateDirect.limit(allocateDirect.position() + n);
        return allocateDirect.slice().order(ByteOrder.nativeOrder());
    }
    
    public static ShortBuffer createShortBuffer(final int n) {
        return createByteBuffer(n << 1).asShortBuffer();
    }
    
    public static CharBuffer createCharBuffer(final int n) {
        return createByteBuffer(n << 1).asCharBuffer();
    }
    
    public static IntBuffer createIntBuffer(final int n) {
        return createByteBuffer(n << 2).asIntBuffer();
    }
    
    public static LongBuffer createLongBuffer(final int n) {
        return createByteBuffer(n << 3).asLongBuffer();
    }
    
    public static FloatBuffer createFloatBuffer(final int n) {
        return createByteBuffer(n << 2).asFloatBuffer();
    }
    
    public static DoubleBuffer createDoubleBuffer(final int n) {
        return createByteBuffer(n << 3).asDoubleBuffer();
    }
    
    public static PointerBuffer createPointerBuffer(final int n) {
        return new PointerBuffer(createByteBuffer(n * PointerBuffer.getPointerSize()));
    }
    
    static {
        final Integer privilegedInteger = LWJGLUtil.getPrivilegedInteger("org.lwjgl.util.mapped.CacheLineSize");
        if (privilegedInteger != null) {
            if (privilegedInteger < 1) {
                throw new IllegalStateException("Invalid CacheLineSize specified: " + privilegedInteger);
            }
            CacheUtil.CACHE_LINE_SIZE = privilegedInteger;
        }
        else if (Runtime.getRuntime().availableProcessors() == 1) {
            if (LWJGLUtil.DEBUG) {
                LWJGLUtil.log("Cannot detect cache line size on single-core CPUs, assuming 64 bytes.");
            }
        }
        else {
            CacheUtil.CACHE_LINE_SIZE = CacheLineSize.getCacheLineSize();
        }
    }
}
