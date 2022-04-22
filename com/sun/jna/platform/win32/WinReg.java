package com.sun.jna.platform.win32;

import com.sun.jna.win32.*;
import com.sun.jna.ptr.*;
import com.sun.jna.*;

public interface WinReg extends StdCallLibrary
{
    public static final HKEY HKEY_CLASSES_ROOT = new HKEY(Integer.MIN_VALUE);
    public static final HKEY HKEY_CURRENT_USER = new HKEY(-2147483647);
    public static final HKEY HKEY_LOCAL_MACHINE = new HKEY(-2147483646);
    public static final HKEY HKEY_USERS = new HKEY(-2147483645);
    public static final HKEY HKEY_PERFORMANCE_DATA = new HKEY(-2147483644);
    public static final HKEY HKEY_PERFORMANCE_TEXT = new HKEY(-2147483568);
    public static final HKEY HKEY_PERFORMANCE_NLSTEXT = new HKEY(-2147483552);
    public static final HKEY HKEY_CURRENT_CONFIG = new HKEY(-2147483643);
    public static final HKEY HKEY_DYN_DATA = new HKEY(-2147483642);
    
    public static class HKEYByReference extends ByReference
    {
        public HKEYByReference() {
            this((HKEY)null);
        }
        
        public HKEYByReference(final HKEY value) {
            super(Pointer.SIZE);
            this.setValue(value);
        }
        
        public void setValue(final HKEY hkey) {
            this.getPointer().setPointer(0L, (hkey != null) ? hkey.getPointer() : null);
        }
        
        public HKEY getValue() {
            final Pointer pointer = this.getPointer().getPointer(0L);
            if (pointer == null) {
                return null;
            }
            if (WinBase.INVALID_HANDLE_VALUE.getPointer().equals(pointer)) {
                return (HKEY)WinBase.INVALID_HANDLE_VALUE;
            }
            final HKEY hkey = new HKEY();
            hkey.setPointer(pointer);
            return hkey;
        }
    }
    
    public static class HKEY extends WinNT.HANDLE
    {
        public HKEY() {
        }
        
        public HKEY(final Pointer pointer) {
            super(pointer);
        }
        
        public HKEY(final int n) {
            super(new Pointer(n));
        }
    }
}
