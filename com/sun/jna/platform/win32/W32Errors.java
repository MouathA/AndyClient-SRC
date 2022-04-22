package com.sun.jna.platform.win32;

public abstract class W32Errors implements WinError
{
    public static final boolean SUCCEEDED(final int n) {
        return n >= 0;
    }
    
    public static final boolean FAILED(final int n) {
        return n < 0;
    }
    
    public static final int HRESULT_CODE(final int n) {
        return n & 0xFFFF;
    }
    
    public static final int SCODE_CODE(final int n) {
        return n & 0xFFFF;
    }
    
    public static final int HRESULT_FACILITY(int n) {
        return (n >>= 16) & 0x1FFF;
    }
    
    public static final int SCODE_FACILITY(final short n) {
        return (short)(n >> 16) & 0x1FFF;
    }
    
    public static short HRESULT_SEVERITY(int n) {
        return (short)((n >>= 31) & 0x1);
    }
    
    public static short SCODE_SEVERITY(final short n) {
        return (short)((short)(n >> 31) & 0x1);
    }
    
    public static int MAKE_HRESULT(final short n, final short n2, final short n3) {
        return n << 31 | n2 << 16 | n3;
    }
    
    public static final int MAKE_SCODE(final short n, final short n2, final short n3) {
        return n << 31 | n2 << 16 | n3;
    }
    
    public static final WinNT.HRESULT HRESULT_FROM_WIN32(final int n) {
        return new WinNT.HRESULT((n <= 0) ? n : ((n & 0xFFFF) | 0x70000 | Integer.MIN_VALUE));
    }
    
    public static final int FILTER_HRESULT_FROM_FLT_NTSTATUS(final int n) {
        return (n & 0x8000FFFF) | 0x1F0000;
    }
}
