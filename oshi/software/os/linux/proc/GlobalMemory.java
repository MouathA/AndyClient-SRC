package oshi.software.os.linux.proc;

import oshi.hardware.*;
import java.io.*;
import java.util.*;

public class GlobalMemory implements Memory
{
    private long totalMemory;
    
    public GlobalMemory() {
        this.totalMemory = 0L;
    }
    
    public long getAvailable() {
        long longValue = 0L;
        final Scanner scanner = new Scanner(new FileReader("/proc/meminfo"));
        scanner.useDelimiter("\n");
        while (scanner.hasNext()) {
            final String next = scanner.next();
            if (next.startsWith("MemFree:") || next.startsWith("MemAvailable:")) {
                final String[] split = next.split("\\s+");
                longValue = new Long(split[1]);
                if (split[2].equals("kB")) {
                    longValue *= 1024L;
                }
                if (split[0].equals("MemAvailable:")) {
                    break;
                }
                continue;
            }
        }
        scanner.close();
        return longValue;
    }
    
    public long getTotal() {
        if (this.totalMemory == 0L) {
            final Scanner scanner = new Scanner(new FileReader("/proc/meminfo"));
            scanner.useDelimiter("\n");
            while (scanner.hasNext()) {
                final String next = scanner.next();
                if (next.startsWith("MemTotal:")) {
                    final String[] split = next.split("\\s+");
                    this.totalMemory = new Long(split[1]);
                    if (split[2].equals("kB")) {
                        this.totalMemory *= 1024L;
                        break;
                    }
                    break;
                }
            }
            scanner.close();
        }
        return this.totalMemory;
    }
}
