package com.sun.jna.win32;

import com.sun.jna.*;
import java.lang.reflect.*;

public class StdCallFunctionMapper implements FunctionMapper
{
    static Class class$com$sun$jna$NativeMapped;
    
    protected int getArgumentNativeStackSize(Class nativeType) {
        if (((StdCallFunctionMapper.class$com$sun$jna$NativeMapped == null) ? (StdCallFunctionMapper.class$com$sun$jna$NativeMapped = class$("com.sun.jna.NativeMapped")) : StdCallFunctionMapper.class$com$sun$jna$NativeMapped).isAssignableFrom(nativeType)) {
            nativeType = NativeMappedConverter.getInstance(nativeType).nativeType();
        }
        if (nativeType.isArray()) {
            return Pointer.SIZE;
        }
        return Native.getNativeSize(nativeType);
    }
    
    public String getFunctionName(final NativeLibrary nativeLibrary, final Method method) {
        final String name = method.getName();
        final Class<?>[] parameterTypes = method.getParameterTypes();
        while (0 < parameterTypes.length) {
            final int n = 0 + this.getArgumentNativeStackSize(parameterTypes[0]);
            int n2 = 0;
            ++n2;
        }
        return nativeLibrary.getFunction(name + "@" + 0, 1).getName();
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
}
