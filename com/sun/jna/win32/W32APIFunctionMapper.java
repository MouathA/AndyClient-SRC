package com.sun.jna.win32;

import com.sun.jna.*;
import java.lang.reflect.*;

public class W32APIFunctionMapper implements FunctionMapper
{
    public static final FunctionMapper UNICODE;
    public static final FunctionMapper ASCII;
    private final String suffix;
    
    protected W32APIFunctionMapper(final boolean b) {
        this.suffix = (b ? "W" : "A");
    }
    
    public String getFunctionName(final NativeLibrary nativeLibrary, final Method method) {
        String s = method.getName();
        if (!s.endsWith("W") && !s.endsWith("A")) {
            s = nativeLibrary.getFunction(s + this.suffix, 1).getName();
        }
        return s;
    }
    
    static {
        UNICODE = new W32APIFunctionMapper(true);
        ASCII = new W32APIFunctionMapper(false);
    }
}
