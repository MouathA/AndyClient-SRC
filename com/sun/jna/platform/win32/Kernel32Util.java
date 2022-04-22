package com.sun.jna.platform.win32;

import com.sun.jna.ptr.*;
import com.sun.jna.*;
import java.util.*;

public abstract class Kernel32Util implements WinDef
{
    public static String getComputerName() {
        final char[] array = new char[WinBase.MAX_COMPUTERNAME_LENGTH + 1];
        if (!Kernel32.INSTANCE.GetComputerName(array, new IntByReference(array.length))) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return Native.toString(array);
    }
    
    public static String formatMessageFromHR(final WinNT.HRESULT hresult) {
        final PointerByReference pointerByReference = new PointerByReference();
        if (0 == Kernel32.INSTANCE.FormatMessage(4864, null, hresult.intValue(), 0, pointerByReference, 0, null)) {
            throw new LastErrorException(Kernel32.INSTANCE.GetLastError());
        }
        final String string = pointerByReference.getValue().getString(0L, !Boolean.getBoolean("w32.ascii"));
        Kernel32.INSTANCE.LocalFree(pointerByReference.getValue());
        return string.trim();
    }
    
    public static String formatMessageFromLastErrorCode(final int n) {
        return formatMessageFromHR(W32Errors.HRESULT_FROM_WIN32(n));
    }
    
    public static String getTempPath() {
        final DWORD dword = new DWORD(260L);
        final char[] array = new char[dword.intValue()];
        if (Kernel32.INSTANCE.GetTempPath(dword, array).intValue() == 0) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return Native.toString(array);
    }
    
    public static void deleteFile(final String s) {
        if (!Kernel32.INSTANCE.DeleteFile(s)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
    }
    
    public static String[] getLogicalDriveStrings() {
        final DWORD getLogicalDriveStrings = Kernel32.INSTANCE.GetLogicalDriveStrings(new DWORD(0L), null);
        if (getLogicalDriveStrings.intValue() <= 0) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        final char[] array = new char[getLogicalDriveStrings.intValue()];
        if (Kernel32.INSTANCE.GetLogicalDriveStrings(getLogicalDriveStrings, array).intValue() <= 0) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        final ArrayList<String> list = new ArrayList<String>();
        String string = "";
        while (0 < array.length - 1) {
            if (array[0] == '\0') {
                list.add(string);
                string = "";
            }
            else {
                string += array[0];
            }
            int n = 0;
            ++n;
        }
        return list.toArray(new String[0]);
    }
    
    public static int getFileAttributes(final String s) {
        final int getFileAttributes = Kernel32.INSTANCE.GetFileAttributes(s);
        if (getFileAttributes == -1) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return getFileAttributes;
    }
}
