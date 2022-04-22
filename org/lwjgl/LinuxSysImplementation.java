package org.lwjgl;

import java.awt.*;
import java.security.*;

final class LinuxSysImplementation extends J2SESysImplementation
{
    private static final int JNI_VERSION = 19;
    
    public int getRequiredJNIVersion() {
        return 19;
    }
    
    public boolean openURL(final String s) {
        final String[] array = { "sensible-browser", "xdg-open", "google-chrome", "chromium", "firefox", "iceweasel", "mozilla", "opera", "konqueror", "nautilus", "galeon", "netscape" };
        if (0 < array.length) {
            LWJGLUtil.execPrivileged(new String[] { array[0], s });
            return true;
        }
        return false;
    }
    
    @Override
    public boolean has64Bit() {
        return true;
    }
    
    static {
        Toolkit.getDefaultToolkit();
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
            public Object run() {
                System.loadLibrary("jawt");
                return null;
            }
        });
    }
}
