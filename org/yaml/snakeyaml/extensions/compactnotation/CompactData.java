package org.yaml.snakeyaml.extensions.compactnotation;

import java.util.*;

public class CompactData
{
    private String prefix;
    private List arguments;
    private Map properties;
    
    public CompactData(final String prefix) {
        this.arguments = new ArrayList();
        this.properties = new HashMap();
        this.prefix = prefix;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public Map getProperties() {
        return this.properties;
    }
    
    public List getArguments() {
        return this.arguments;
    }
    
    @Override
    public String toString() {
        return "CompactData: " + this.prefix + " " + this.properties;
    }
}
