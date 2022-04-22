package org.lwjgl.opengl;

import java.util.*;

final class CallbackUtil
{
    private static final Map contextUserParamsARB;
    private static final Map contextUserParamsAMD;
    private static final Map contextUserParamsKHR;
    
    private CallbackUtil() {
    }
    
    static long createGlobalRef(final Object o) {
        return (o == null) ? 0L : ncreateGlobalRef(o);
    }
    
    private static native long ncreateGlobalRef(final Object p0);
    
    private static native void deleteGlobalRef(final long p0);
    
    private static void registerContextCallback(final long n, final Map map) {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        if (capabilities == null) {
            deleteGlobalRef(n);
            throw new IllegalStateException("No context is current.");
        }
        final Long n2 = map.remove(capabilities);
        if (n2 != null) {
            deleteGlobalRef(n2);
        }
        if (n != 0L) {
            map.put(capabilities, n);
        }
    }
    
    static void unregisterCallbacks(final Object o) {
        final ContextCapabilities capabilities = GLContext.getCapabilities(o);
        final Long n = CallbackUtil.contextUserParamsARB.remove(capabilities);
        if (n != null) {
            deleteGlobalRef(n);
        }
        final Long n2 = CallbackUtil.contextUserParamsAMD.remove(capabilities);
        if (n2 != null) {
            deleteGlobalRef(n2);
        }
        final Long n3 = CallbackUtil.contextUserParamsKHR.remove(capabilities);
        if (n3 != null) {
            deleteGlobalRef(n3);
        }
    }
    
    static native long getDebugOutputCallbackARB();
    
    static void registerContextCallbackARB(final long n) {
        registerContextCallback(n, CallbackUtil.contextUserParamsARB);
    }
    
    static native long getDebugOutputCallbackAMD();
    
    static void registerContextCallbackAMD(final long n) {
        registerContextCallback(n, CallbackUtil.contextUserParamsAMD);
    }
    
    static native long getDebugCallbackKHR();
    
    static void registerContextCallbackKHR(final long n) {
        registerContextCallback(n, CallbackUtil.contextUserParamsKHR);
    }
    
    static {
        contextUserParamsARB = new HashMap();
        contextUserParamsAMD = new HashMap();
        contextUserParamsKHR = new HashMap();
    }
}
