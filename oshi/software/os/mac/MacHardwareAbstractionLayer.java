package oshi.software.os.mac;

import oshi.hardware.*;
import java.util.*;
import oshi.util.*;
import oshi.software.os.mac.local.*;

public class MacHardwareAbstractionLayer implements HardwareAbstractionLayer
{
    private Processor[] _processors;
    private Memory _memory;
    
    public Processor[] getProcessors() {
        if (this._processors == null) {
            final ArrayList<CentralProcessor> list = new ArrayList<CentralProcessor>();
            while (0 < new Integer(ExecutingCommand.getFirstAnswer("sysctl -n hw.logicalcpu"))) {
                list.add(new CentralProcessor());
                int n = 0;
                ++n;
            }
            this._processors = list.toArray(new Processor[0]);
        }
        return this._processors;
    }
    
    public Memory getMemory() {
        if (this._memory == null) {
            this._memory = new GlobalMemory();
        }
        return this._memory;
    }
}
