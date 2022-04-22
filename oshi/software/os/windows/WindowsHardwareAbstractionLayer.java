package oshi.software.os.windows;

import oshi.hardware.*;
import java.util.*;
import oshi.software.os.windows.nt.*;
import com.sun.jna.platform.win32.*;

public class WindowsHardwareAbstractionLayer implements HardwareAbstractionLayer
{
    private Processor[] _processors;
    private Memory _memory;
    
    public WindowsHardwareAbstractionLayer() {
        this._processors = null;
        this._memory = null;
    }
    
    public Memory getMemory() {
        if (this._memory == null) {
            this._memory = new GlobalMemory();
        }
        return this._memory;
    }
    
    public Processor[] getProcessors() {
        if (this._processors == null) {
            final ArrayList<CentralProcessor> list = new ArrayList<CentralProcessor>();
            final String[] registryGetKeys = Advapi32Util.registryGetKeys(WinReg.HKEY_LOCAL_MACHINE, "HARDWARE\\DESCRIPTION\\System\\CentralProcessor");
            while (0 < registryGetKeys.length) {
                final String string = "HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\" + registryGetKeys[0];
                final CentralProcessor centralProcessor = new CentralProcessor();
                centralProcessor.setIdentifier(Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, string, "Identifier"));
                centralProcessor.setName(Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, string, "ProcessorNameString"));
                centralProcessor.setVendor(Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, string, "VendorIdentifier"));
                list.add(centralProcessor);
                int n = 0;
                ++n;
            }
            this._processors = list.toArray(new Processor[0]);
        }
        return this._processors;
    }
}
