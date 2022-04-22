package org.lwjgl.opencl;

import java.util.*;

final class CallbackUtil
{
    private static final Map contextUserData;
    
    private CallbackUtil() {
    }
    
    static long createGlobalRef(final Object o) {
        return (o == null) ? 0L : ncreateGlobalRef(o);
    }
    
    private static native long ncreateGlobalRef(final Object p0);
    
    static native void deleteGlobalRef(final long p0);
    
    static void checkCallback(final int n, final long n2) {
        if (n != 0 && n2 != 0L) {
            deleteGlobalRef(n2);
        }
    }
    
    static native long getContextCallback();
    
    static native long getMemObjectDestructorCallback();
    
    static native long getProgramCallback();
    
    static native long getNativeKernelCallback();
    
    static native long getEventCallback();
    
    static native long getPrintfCallback();
    
    static native long getLogMessageToSystemLogAPPLE();
    
    static native long getLogMessageToStdoutAPPLE();
    
    static native long getLogMessageToStderrAPPLE();
    
    static {
        contextUserData = new HashMap();
    }
}
