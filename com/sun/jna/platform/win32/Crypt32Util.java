package com.sun.jna.platform.win32;

import com.sun.jna.*;
import com.sun.jna.ptr.*;

public abstract class Crypt32Util
{
    public static byte[] cryptProtectData(final byte[] array) {
        return cryptProtectData(array, 0);
    }
    
    public static byte[] cryptProtectData(final byte[] array, final int n) {
        return cryptProtectData(array, null, n, "", null);
    }
    
    public static byte[] cryptProtectData(final byte[] array, final byte[] array2, final int n, final String s, final WinCrypt.CRYPTPROTECT_PROMPTSTRUCT cryptprotect_PROMPTSTRUCT) {
        final WinCrypt.DATA_BLOB data_BLOB = new WinCrypt.DATA_BLOB(array);
        final WinCrypt.DATA_BLOB data_BLOB2 = new WinCrypt.DATA_BLOB();
        if (!Crypt32.INSTANCE.CryptProtectData(data_BLOB, s, (array2 == null) ? null : new WinCrypt.DATA_BLOB(array2), null, cryptprotect_PROMPTSTRUCT, n, data_BLOB2)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        final byte[] data = data_BLOB2.getData();
        if (data_BLOB2.pbData != null) {
            Kernel32.INSTANCE.LocalFree(data_BLOB2.pbData);
        }
        return data;
    }
    
    public static byte[] cryptUnprotectData(final byte[] array) {
        return cryptUnprotectData(array, 0);
    }
    
    public static byte[] cryptUnprotectData(final byte[] array, final int n) {
        return cryptUnprotectData(array, null, n, null);
    }
    
    public static byte[] cryptUnprotectData(final byte[] array, final byte[] array2, final int n, final WinCrypt.CRYPTPROTECT_PROMPTSTRUCT cryptprotect_PROMPTSTRUCT) {
        final WinCrypt.DATA_BLOB data_BLOB = new WinCrypt.DATA_BLOB(array);
        final WinCrypt.DATA_BLOB data_BLOB2 = new WinCrypt.DATA_BLOB();
        final WinCrypt.DATA_BLOB data_BLOB3 = (array2 == null) ? null : new WinCrypt.DATA_BLOB(array2);
        final PointerByReference pointerByReference = new PointerByReference();
        if (!Crypt32.INSTANCE.CryptUnprotectData(data_BLOB, pointerByReference, data_BLOB3, null, cryptprotect_PROMPTSTRUCT, n, data_BLOB2)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        final byte[] data = data_BLOB2.getData();
        if (data_BLOB2.pbData != null) {
            Kernel32.INSTANCE.LocalFree(data_BLOB2.pbData);
        }
        if (pointerByReference.getValue() != null) {
            Kernel32.INSTANCE.LocalFree(pointerByReference.getValue());
        }
        return data;
    }
}
