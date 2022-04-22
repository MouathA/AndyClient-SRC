package oshi.software.os.mac.local;

import oshi.hardware.*;
import oshi.util.*;

public class CentralProcessor implements Processor
{
    private String _vendor;
    private String _name;
    private String _identifier;
    private String _stepping;
    private String _model;
    private String _family;
    private Boolean _cpu64;
    
    public CentralProcessor() {
        this._identifier = null;
    }
    
    public String getVendor() {
        if (this._vendor == null) {
            this._vendor = ExecutingCommand.getFirstAnswer("sysctl -n machdep.cpu.vendor");
        }
        return this._vendor;
    }
    
    public void setVendor(final String vendor) {
        this._vendor = vendor;
    }
    
    public String getName() {
        if (this._name == null) {
            this._name = ExecutingCommand.getFirstAnswer("sysctl -n machdep.cpu.brand_string");
        }
        return this._name;
    }
    
    public void setName(final String name) {
        this._name = name;
    }
    
    public String getIdentifier() {
        if (this._identifier == null) {
            final StringBuilder sb = new StringBuilder();
            if (this.getVendor().contentEquals("GenuineIntel")) {
                sb.append((this == null) ? "Intel64" : "x86");
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
    
    public void setCpu64(final boolean b) {
        this._cpu64 = b;
    }
    
    public String getStepping() {
        if (this._stepping == null) {
            this._stepping = ExecutingCommand.getFirstAnswer("sysctl -n machdep.cpu.stepping");
        }
        return this._stepping;
    }
    
    public void setStepping(final String stepping) {
        this._stepping = stepping;
    }
    
    public String getModel() {
        if (this._model == null) {
            this._model = ExecutingCommand.getFirstAnswer("sysctl -n machdep.cpu.model");
        }
        return this._model;
    }
    
    public void setModel(final String model) {
        this._model = model;
    }
    
    public String getFamily() {
        if (this._family == null) {
            this._family = ExecutingCommand.getFirstAnswer("sysctl -n machdep.cpu.family");
        }
        return this._family;
    }
    
    public void setFamily(final String family) {
        this._family = family;
    }
    
    public float getLoad() {
        return 100.0f - Float.valueOf(ExecutingCommand.runNative("top -l 1 -R -F -n1").get(3).split(" ")[6].replace("%", ""));
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
}
