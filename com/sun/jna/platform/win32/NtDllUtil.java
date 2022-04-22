package com.sun.jna.platform.win32;

import com.sun.jna.ptr.*;
import com.sun.jna.*;

public abstract class NtDllUtil
{
    public static String getKeyName(final WinReg.HKEY hkey) {
        final IntByReference intByReference = new IntByReference();
        final int zwQueryKey = NtDll.INSTANCE.ZwQueryKey(hkey, 0, null, 0, intByReference);
        if (zwQueryKey != -1073741789 || intByReference.getValue() <= 0) {
            throw new Win32Exception(zwQueryKey);
        }
        final Wdm.KEY_BASIC_INFORMATION key_BASIC_INFORMATION = new Wdm.KEY_BASIC_INFORMATION(intByReference.getValue());
        final int zwQueryKey2 = NtDll.INSTANCE.ZwQueryKey(hkey, 0, key_BASIC_INFORMATION, intByReference.getValue(), intByReference);
        if (zwQueryKey2 != 0) {
            throw new Win32Exception(zwQueryKey2);
        }
        return key_BASIC_INFORMATION.getName();
    }
}
