package com.sun.jna.platform.win32;

import com.sun.jna.win32.*;
import com.sun.jna.*;
import com.sun.jna.ptr.*;

public interface BaseTSD extends StdCallLibrary
{
    public static class SIZE_T extends ULONG_PTR
    {
        public SIZE_T() {
            this(0L);
        }
        
        public SIZE_T(final long n) {
            super(n);
        }
    }
    
    public static class ULONG_PTR extends IntegerType
    {
        public ULONG_PTR() {
            this(0L);
        }
        
        public ULONG_PTR(final long n) {
            super(Pointer.SIZE, n);
        }
    }
    
    public static class DWORD_PTR extends IntegerType
    {
        public DWORD_PTR() {
            this(0L);
        }
        
        public DWORD_PTR(final long n) {
            super(Pointer.SIZE, n);
        }
    }
    
    public static class ULONG_PTRByReference extends ByReference
    {
        public ULONG_PTRByReference() {
            this(new ULONG_PTR(0L));
        }
        
        public ULONG_PTRByReference(final ULONG_PTR value) {
            super(Pointer.SIZE);
            this.setValue(value);
        }
        
        public void setValue(final ULONG_PTR ulong_PTR) {
            if (Pointer.SIZE == 4) {
                this.getPointer().setInt(0L, ulong_PTR.intValue());
            }
            else {
                this.getPointer().setLong(0L, ulong_PTR.longValue());
            }
        }
        
        public ULONG_PTR getValue() {
            return new ULONG_PTR((Pointer.SIZE == 4) ? this.getPointer().getInt(0L) : this.getPointer().getLong(0L));
        }
    }
    
    public static class SSIZE_T extends LONG_PTR
    {
        public SSIZE_T() {
            this(0L);
        }
        
        public SSIZE_T(final long n) {
            super(n);
        }
    }
    
    public static class LONG_PTR extends IntegerType
    {
        public LONG_PTR() {
            this(0L);
        }
        
        public LONG_PTR(final long n) {
            super(Pointer.SIZE, n);
        }
    }
}
