package oshi.software.os.windows.nt;

import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.*;

public class OSNativeSystemInfo
{
    private WinBase.SYSTEM_INFO _si;
    
    public OSNativeSystemInfo() {
        this._si = null;
        final WinBase.SYSTEM_INFO si = new WinBase.SYSTEM_INFO();
        Kernel32.INSTANCE.GetSystemInfo(si);
        final IntByReference intByReference = new IntByReference();
        if (Kernel32.INSTANCE.IsWow64Process(Kernel32.INSTANCE.GetCurrentProcess(), intByReference) && intByReference.getValue() > 0) {
            Kernel32.INSTANCE.GetNativeSystemInfo(si);
        }
        this._si = si;
    }
    
    public OSNativeSystemInfo(final WinBase.SYSTEM_INFO si) {
        this._si = null;
        this._si = si;
    }
    
    public int getNumberOfProcessors() {
        return this._si.dwNumberOfProcessors.intValue();
    }
}
