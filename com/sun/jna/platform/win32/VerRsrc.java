package com.sun.jna.platform.win32;

import com.sun.jna.win32.*;
import com.sun.jna.*;

public interface VerRsrc extends StdCallLibrary
{
    public static class VS_FIXEDFILEINFO extends Structure
    {
        public WinDef.DWORD dwSignature;
        public WinDef.DWORD dwStrucVersion;
        public WinDef.DWORD dwFileVersionMS;
        public WinDef.DWORD dwFileVersionLS;
        public WinDef.DWORD dwProductVersionMS;
        public WinDef.DWORD dwProductVersionLS;
        public WinDef.DWORD dwFileFlagsMask;
        public WinDef.DWORD dwFileFlags;
        public WinDef.DWORD dwFileOS;
        public WinDef.DWORD dwFileType;
        public WinDef.DWORD dwFileSubtype;
        public WinDef.DWORD dwFileDateMS;
        public WinDef.DWORD dwFileDateLS;
        
        public VS_FIXEDFILEINFO() {
        }
        
        public VS_FIXEDFILEINFO(final Pointer pointer) {
            super(pointer);
            this.read();
        }
        
        public static class ByReference extends VS_FIXEDFILEINFO implements Structure.ByReference
        {
            public ByReference() {
            }
            
            public ByReference(final Pointer pointer) {
                super(pointer);
            }
        }
    }
}
