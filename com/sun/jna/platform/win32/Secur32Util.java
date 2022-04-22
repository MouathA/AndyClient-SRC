package com.sun.jna.platform.win32;

import com.sun.jna.ptr.*;
import com.sun.jna.*;
import java.util.*;

public abstract class Secur32Util
{
    public static String getUserNameEx(final int n) {
        char[] array = new char[128];
        final IntByReference intByReference = new IntByReference(array.length);
        boolean b = Secur32.INSTANCE.GetUserNameEx(n, array, intByReference);
        if (!b) {
            switch (Kernel32.INSTANCE.GetLastError()) {
                case 234: {
                    array = new char[intByReference.getValue() + 1];
                    b = Secur32.INSTANCE.GetUserNameEx(n, array, intByReference);
                    break;
                }
                default: {
                    throw new Win32Exception(Native.getLastError());
                }
            }
        }
        if (!b) {
            throw new Win32Exception(Native.getLastError());
        }
        return Native.toString(array);
    }
    
    public static SecurityPackage[] getSecurityPackages() {
        final IntByReference intByReference = new IntByReference();
        final Sspi.PSecPkgInfo.ByReference byReference = new Sspi.PSecPkgInfo.ByReference();
        final int enumerateSecurityPackages = Secur32.INSTANCE.EnumerateSecurityPackages(intByReference, byReference);
        if (enumerateSecurityPackages != 0) {
            throw new Win32Exception(enumerateSecurityPackages);
        }
        final Sspi.SecPkgInfo.ByReference[] array = byReference.toArray(intByReference.getValue());
        final ArrayList list = new ArrayList<SecurityPackage>(intByReference.getValue());
        final Sspi.SecPkgInfo.ByReference[] array2 = array;
        while (0 < array2.length) {
            final Sspi.SecPkgInfo.ByReference byReference2 = array2[0];
            final SecurityPackage securityPackage = new SecurityPackage();
            securityPackage.name = byReference2.Name.toString();
            securityPackage.comment = byReference2.Comment.toString();
            list.add(securityPackage);
            int n = 0;
            ++n;
        }
        final int freeContextBuffer = Secur32.INSTANCE.FreeContextBuffer(byReference.pPkgInfo.getPointer());
        if (freeContextBuffer != 0) {
            throw new Win32Exception(freeContextBuffer);
        }
        return list.toArray(new SecurityPackage[0]);
    }
    
    public static class SecurityPackage
    {
        public String name;
        public String comment;
    }
}
