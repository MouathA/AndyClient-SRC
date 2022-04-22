package oshi.software.os.linux.proc;

import oshi.hardware.*;
import java.io.*;
import java.util.*;
import oshi.util.*;

public class CentralProcessor implements Processor
{
    private String _vendor;
    private String _name;
    private String _identifier;
    private String _stepping;
    private String _model;
    private String _family;
    private boolean _cpu64;
    
    public CentralProcessor() {
        this._identifier = null;
    }
    
    public String getVendor() {
        return this._vendor;
    }
    
    public void setVendor(final String vendor) {
        this._vendor = vendor;
    }
    
    public String getName() {
        return this._name;
    }
    
    public void setName(final String name) {
        this._name = name;
    }
    
    public String getIdentifier() {
        if (this._identifier == null) {
            final StringBuilder sb = new StringBuilder();
            if (this.getVendor().contentEquals("GenuineIntel")) {
                sb.append(this.isCpu64bit() ? "Intel64" : "x86");
            }
            else {
                sb.append(this.getVendor());
            }
            sb.append(" Family ");
            sb.append(this.getFamily());
            sb.append(" Model ");
            sb.append(this.getModel());
            sb.append(" Stepping ");
            sb.append(this.getStepping());
            this._identifier = sb.toString();
        }
        return this._identifier;
    }
    
    public void setIdentifier(final String identifier) {
        this._identifier = identifier;
    }
    
    public boolean isCpu64bit() {
        return this._cpu64;
    }
    
    public void setCpu64(final boolean cpu64) {
        this._cpu64 = cpu64;
    }
    
    public String getStepping() {
        return this._stepping;
    }
    
    public void setStepping(final String stepping) {
        this._stepping = stepping;
    }
    
    public String getModel() {
        return this._model;
    }
    
    public void setModel(final String model) {
        this._model = model;
    }
    
    public String getFamily() {
        return this._family;
    }
    
    public void setFamily(final String family) {
        this._family = family;
    }
    
    public float getLoad() {
        final Scanner scanner = new Scanner(new FileReader("/proc/stat"));
        scanner.useDelimiter("\n");
        final String[] split = scanner.next().split(" ");
        final ArrayList<Float> list = new ArrayList<Float>();
        final String[] array = split;
        while (0 < array.length) {
            final String s = array[0];
            if (s.matches("-?\\d+(\\.\\d+)?")) {
                list.add(Float.valueOf(s));
            }
            int n = 0;
            ++n;
        }
        return FormatUtil.round((list.get(0) + list.get(2)) * 100.0f / (list.get(0) + list.get(2) + list.get(3)), 2);
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
}
