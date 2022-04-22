package oshi.software.os.linux.proc;

import oshi.software.os.*;
import java.io.*;
import java.util.*;

public class OSVersionInfoEx implements OperatingSystemVersion
{
    private String _version;
    private String _codeName;
    private String version;
    
    public OSVersionInfoEx() {
        this._version = null;
        this._codeName = null;
        this.version = null;
        final Scanner scanner = new Scanner(new FileReader("/etc/os-release"));
        scanner.useDelimiter("\n");
        while (scanner.hasNext()) {
            final String[] split = scanner.next().split("=");
            if (split[0].equals("VERSION_ID")) {
                this.setVersion(split[1].replaceAll("^\"|\"$", ""));
            }
            if (split[0].equals("VERSION")) {
                split[1] = split[1].replaceAll("^\"|\"$", "");
                String[] array = split[1].split("[()]");
                if (array.length <= 1) {
                    array = split[1].split(", ");
                }
                if (array.length > 1) {
                    this.setCodeName(array[1]);
                }
                else {
                    this.setCodeName(split[1]);
                }
            }
        }
        scanner.close();
    }
    
    public String getCodeName() {
        return this._codeName;
    }
    
    public String getVersion() {
        return this._version;
    }
    
    public void setCodeName(final String codeName) {
        this._codeName = codeName;
    }
    
    public void setVersion(final String version) {
        this._version = version;
    }
    
    @Override
    public String toString() {
        if (this.version == null) {
            this.version = this.getVersion() + " (" + this.getCodeName() + ")";
        }
        return this.version;
    }
}
