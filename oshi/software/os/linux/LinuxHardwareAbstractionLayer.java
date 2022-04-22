package oshi.software.os.linux;

import oshi.hardware.*;
import oshi.software.os.linux.proc.*;
import java.io.*;
import java.util.*;

public class LinuxHardwareAbstractionLayer implements HardwareAbstractionLayer
{
    private static final String SEPARATOR = "\\s+:\\s";
    private Processor[] _processors;
    private Memory _memory;
    
    public LinuxHardwareAbstractionLayer() {
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
            final Scanner scanner = new Scanner(new FileReader("/proc/cpuinfo"));
            scanner.useDelimiter("\n");
            CentralProcessor centralProcessor = null;
            while (scanner.hasNext()) {
                final String next = scanner.next();
                if (next.equals("")) {
                    if (centralProcessor != null) {
                        list.add(centralProcessor);
                    }
                    centralProcessor = null;
                }
                else {
                    if (centralProcessor == null) {
                        centralProcessor = new CentralProcessor();
                    }
                    if (next.startsWith("model name\t")) {
                        centralProcessor.setName(next.split("\\s+:\\s")[1]);
                    }
                    else if (next.startsWith("flags\t")) {
                        final String[] split = next.split("\\s+:\\s")[1].split(" ");
                        while (0 < split.length && !split[0].equalsIgnoreCase("LM")) {
                            int n = 0;
                            ++n;
                        }
                        centralProcessor.setCpu64(true);
                    }
                    else if (next.startsWith("cpu family\t")) {
                        centralProcessor.setFamily(next.split("\\s+:\\s")[1]);
                    }
                    else if (next.startsWith("model\t")) {
                        centralProcessor.setModel(next.split("\\s+:\\s")[1]);
                    }
                    else if (next.startsWith("stepping\t")) {
                        centralProcessor.setStepping(next.split("\\s+:\\s")[1]);
                    }
                    else {
                        if (!next.startsWith("vendor_id")) {
                            continue;
                        }
                        centralProcessor.setVendor(next.split("\\s+:\\s")[1]);
                    }
                }
            }
            scanner.close();
            if (centralProcessor != null) {
                list.add(centralProcessor);
            }
            this._processors = list.toArray(new Processor[0]);
        }
        return this._processors;
    }
}
