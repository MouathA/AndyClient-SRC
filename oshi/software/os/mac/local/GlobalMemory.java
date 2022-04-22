package oshi.software.os.mac.local;

import oshi.hardware.*;
import oshi.util.*;
import java.util.*;

public class GlobalMemory implements Memory
{
    private long totalMemory;
    
    public GlobalMemory() {
        this.totalMemory = 0L;
    }
    
    public long getAvailable() {
        long n = 0L;
        for (final String s : ExecutingCommand.runNative("vm_stat")) {
            if (s.startsWith("Pages free:")) {
                n += new Long(s.split(":\\s+")[1].replace(".", ""));
            }
            else {
                if (!s.startsWith("Pages speculative:")) {
                    continue;
                }
                n += new Long(s.split(":\\s+")[1].replace(".", ""));
            }
        }
        return n * 4096L;
    }
    
    public long getTotal() {
        if (this.totalMemory == 0L) {
            this.totalMemory = new Long(ExecutingCommand.getFirstAnswer("sysctl -n hw.memsize"));
        }
        return this.totalMemory;
    }
}
