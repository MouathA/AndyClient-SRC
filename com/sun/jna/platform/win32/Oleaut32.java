package com.sun.jna.platform.win32;

import com.sun.jna.win32.*;
import com.sun.jna.*;

public interface Oleaut32 extends StdCallLibrary
{
    public static final Oleaut32 INSTANCE = (Oleaut32)Native.loadLibrary("Oleaut32", Oleaut32.class, W32APIOptions.UNICODE_OPTIONS);
    
    Pointer SysAllocString(final String p0);
    
    void SysFreeString(final Pointer p0);
}
