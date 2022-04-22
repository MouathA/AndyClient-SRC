package org.lwjgl.opencl;

import java.util.*;
import org.lwjgl.*;
import java.lang.reflect.*;

public final class Util
{
    private static final Map CL_ERROR_TOKENS;
    
    private Util() {
    }
    
    public static void checkCLError(final int n) {
        if (n != 0) {
            throwCLError(n);
        }
    }
    
    private static void throwCLError(final int n) {
        String s = Util.CL_ERROR_TOKENS.get(n);
        if (s == null) {
            s = "UNKNOWN";
        }
        throw new OpenCLException("Error Code: " + s + " (" + LWJGLUtil.toHexString(n) + ")");
    }
    
    static {
        CL_ERROR_TOKENS = LWJGLUtil.getClassTokens(new LWJGLUtil.TokenFilter() {
            public boolean accept(final Field field, final int n) {
                return n < 0;
            }
        }, null, CL10.class, CL11.class, KHRGLSharing.class, KHRICD.class, APPLEGLSharing.class, EXTDeviceFission.class);
    }
}
