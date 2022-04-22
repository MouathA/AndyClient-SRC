package com.sun.jna.platform.win32;

import com.sun.jna.*;

public abstract class Ole32Util
{
    public static Guid.GUID getGUIDFromString(final String s) {
        final Guid.GUID.ByReference byReference = new Guid.GUID.ByReference();
        final WinNT.HRESULT iidFromString = Ole32.INSTANCE.IIDFromString(s, byReference);
        if (!iidFromString.equals(W32Errors.S_OK)) {
            throw new RuntimeException(iidFromString.toString());
        }
        return byReference;
    }
    
    public static String getStringFromGUID(final Guid.GUID guid) {
        final Guid.GUID.ByReference byReference = new Guid.GUID.ByReference(guid.getPointer());
        final char[] array = new char[39];
        final int stringFromGUID2 = Ole32.INSTANCE.StringFromGUID2(byReference, array, 39);
        if (stringFromGUID2 == 0) {
            throw new RuntimeException("StringFromGUID2");
        }
        array[stringFromGUID2 - 1] = '\0';
        return Native.toString(array);
    }
    
    public static Guid.GUID generateGUID() {
        final Guid.GUID.ByReference byReference = new Guid.GUID.ByReference();
        final WinNT.HRESULT coCreateGuid = Ole32.INSTANCE.CoCreateGuid(byReference);
        if (!coCreateGuid.equals(W32Errors.S_OK)) {
            throw new RuntimeException(coCreateGuid.toString());
        }
        return byReference;
    }
}
