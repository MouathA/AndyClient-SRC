package oshi.software.os.linux;

import oshi.software.os.*;
import java.io.*;
import java.util.*;
import oshi.software.os.linux.proc.*;

public class LinuxOperatingSystem implements OperatingSystem
{
    private OperatingSystemVersion _version;
    private String _family;
    
    public LinuxOperatingSystem() {
        this._version = null;
        this._family = null;
    }
    
    public String getFamily() {
        if (this._family == null) {
            final Scanner scanner = new Scanner(new FileReader("/etc/os-release"));
            scanner.useDelimiter("\n");
            while (scanner.hasNext()) {
                final String[] split = scanner.next().split("=");
                if (split[0].equals("NAME")) {
                    this._family = split[1].replaceAll("^\"|\"$", "");
                    break;
                }
            }
            scanner.close();
        }
        return this._family;
    }
    
    public String getManufacturer() {
        return "GNU/Linux";
    }
    
    public OperatingSystemVersion getVersion() {
        if (this._version == null) {
            this._version = new OSVersionInfoEx();
        }
        return this._version;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getManufacturer());
        sb.append(" ");
        sb.append(this.getFamily());
        sb.append(" ");
        sb.append(this.getVersion().toString());
        return sb.toString();
    }
}
