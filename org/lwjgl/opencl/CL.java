package org.lwjgl.opencl;

import org.lwjgl.*;
import java.nio.*;

public final class CL
{
    private static boolean created;
    
    private CL() {
    }
    
    private static native void nCreate(final String p0) throws LWJGLException;
    
    private static native void nCreateDefault() throws LWJGLException;
    
    private static native void nDestroy();
    
    public static boolean isCreated() {
        return CL.created;
    }
    
    public static void create() throws LWJGLException {
        if (CL.created) {
            return;
        }
        String s = null;
        String[] array = null;
        switch (LWJGLUtil.getPlatform()) {
            case 3: {
                s = "OpenCL";
                array = new String[] { "OpenCL.dll" };
                break;
            }
            case 1: {
                s = "OpenCL";
                array = new String[] { "libOpenCL64.so", "libOpenCL.so" };
                break;
            }
            case 2: {
                s = "OpenCL";
                array = new String[] { "OpenCL.dylib" };
                break;
            }
            default: {
                throw new LWJGLException("Unknown platform: " + LWJGLUtil.getPlatform());
            }
        }
        final String[] libraryPaths = LWJGLUtil.getLibraryPaths(s, array, CL.class.getClassLoader());
        LWJGLUtil.log("Found " + libraryPaths.length + " OpenCL paths");
        final String[] array2 = libraryPaths;
        if (0 < array2.length) {
            nCreate(array2[0]);
            CL.created = true;
        }
        if (!CL.created && LWJGLUtil.getPlatform() == 2) {
            CL.created = true;
        }
        if (!CL.created) {
            throw new LWJGLException("Could not locate OpenCL library.");
        }
        if (!CLCapabilities.OpenCL10) {
            throw new RuntimeException("OpenCL 1.0 not supported.");
        }
    }
    
    public static void destroy() {
    }
    
    static long getFunctionAddress(final String[] array) {
        while (0 < array.length) {
            final long functionAddress = getFunctionAddress(array[0]);
            if (functionAddress != 0L) {
                return functionAddress;
            }
            int n = 0;
            ++n;
        }
        return 0L;
    }
    
    static long getFunctionAddress(final String s) {
        return ngetFunctionAddress(MemoryUtil.getAddress(MemoryUtil.encodeASCII(s)));
    }
    
    private static native long ngetFunctionAddress(final long p0);
    
    static native ByteBuffer getHostBuffer(final long p0, final int p1);
    
    private static native void resetNativeStubs(final Class p0);
}
