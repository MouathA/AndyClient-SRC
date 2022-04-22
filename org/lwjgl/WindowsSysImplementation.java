package org.lwjgl;

import org.lwjgl.opengl.*;
import java.lang.reflect.*;
import java.security.*;

final class WindowsSysImplementation extends DefaultSysImplementation
{
    private static final int JNI_VERSION = 24;
    
    public int getRequiredJNIVersion() {
        return 24;
    }
    
    @Override
    public long getTimerResolution() {
        return 1000L;
    }
    
    @Override
    public long getTime() {
        return nGetTime();
    }
    
    private static native long nGetTime();
    
    @Override
    public boolean has64Bit() {
        return true;
    }
    
    private static long getHwnd() {
        if (!Display.isCreated()) {
            return 0L;
        }
        return AccessController.doPrivileged((PrivilegedExceptionAction<Long>)new PrivilegedExceptionAction() {
            public Long run() throws Exception {
                final Method declaredMethod = Display.class.getDeclaredMethod("getImplementation", (Class<?>[])new Class[0]);
                declaredMethod.setAccessible(true);
                final Object invoke = declaredMethod.invoke(null, new Object[0]);
                final Method declaredMethod2 = Class.forName("org.lwjgl.opengl.WindowsDisplay").getDeclaredMethod("getHwnd", (Class<?>[])new Class[0]);
                declaredMethod2.setAccessible(true);
                return (Long)declaredMethod2.invoke(invoke, new Object[0]);
            }
            
            public Object run() throws Exception {
                return this.run();
            }
        });
    }
    
    @Override
    public void alert(final String s, final String s2) {
        Display.isCreated();
        LWJGLUtil.log(String.format("*** Alert *** %s\n%s\n", s, s2));
        nAlert(getHwnd(), MemoryUtil.getAddress(MemoryUtil.encodeUTF16(s)), MemoryUtil.getAddress(MemoryUtil.encodeUTF16(s2)));
    }
    
    private static native void nAlert(final long p0, final long p1, final long p2);
    
    private static native void initCommonControls();
    
    public boolean openURL(final String s) {
        LWJGLUtil.execPrivileged(new String[] { "rundll32", "url.dll,FileProtocolHandler", s });
        return true;
    }
    
    @Override
    public String getClipboard() {
        return nGetClipboard();
    }
    
    private static native String nGetClipboard();
}
