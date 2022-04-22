package com.sun.jna.platform.win32;

import com.sun.jna.*;

public abstract class Shell32Util
{
    public static String getFolderPath(final WinDef.HWND hwnd, final int n, final WinDef.DWORD dword) {
        final char[] array = new char[260];
        final WinNT.HRESULT shGetFolderPath = Shell32.INSTANCE.SHGetFolderPath(hwnd, n, null, dword, array);
        if (!shGetFolderPath.equals(W32Errors.S_OK)) {
            throw new Win32Exception(shGetFolderPath);
        }
        return Native.toString(array);
    }
    
    public static String getFolderPath(final int n) {
        return getFolderPath(null, n, ShlObj.SHGFP_TYPE_CURRENT);
    }
}
