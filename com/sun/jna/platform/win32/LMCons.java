package com.sun.jna.platform.win32;

import com.sun.jna.win32.*;

public interface LMCons extends StdCallLibrary
{
    public static final int NETBIOS_NAME_LEN = 16;
    public static final int MAX_PREFERRED_LENGTH = -1;
}
