package org.lwjgl.opengl;

import org.lwjgl.*;

final class WindowsRegistry
{
    static final int HKEY_CLASSES_ROOT = 1;
    static final int HKEY_CURRENT_USER = 2;
    static final int HKEY_LOCAL_MACHINE = 3;
    static final int HKEY_USERS = 4;
    
    static String queryRegistrationKey(final int n, final String s, final String s2) throws LWJGLException {
        switch (n) {
            case 1:
            case 2:
            case 3:
            case 4: {
                return nQueryRegistrationKey(n, s, s2);
            }
            default: {
                throw new IllegalArgumentException("Invalid enum: " + n);
            }
        }
    }
    
    private static native String nQueryRegistrationKey(final int p0, final String p1, final String p2) throws LWJGLException;
}
