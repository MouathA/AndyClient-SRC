package com.sun.jna.platform.wince;

import com.sun.jna.platform.win32.*;
import com.sun.jna.win32.*;
import com.sun.jna.*;

public interface CoreDLL extends WinNT
{
    public static final CoreDLL INSTANCE = (CoreDLL)Native.loadLibrary("coredll", CoreDLL.class, W32APIOptions.UNICODE_OPTIONS);
}
