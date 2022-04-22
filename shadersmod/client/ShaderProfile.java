package shadersmod.client;

import java.util.*;

public class ShaderProfile
{
    private String name;
    private Map mapOptionValues;
    private Set disabledPrograms;
    
    public ShaderProfile(final String name) {
        this.name = null;
        this.mapOptionValues = new HashMap();
        this.disabledPrograms = new HashSet();
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void addOptionValue(final String s, final String s2) {
        this.mapOptionValues.put(s, s2);
    }
    
    public void addOptionValues(final ShaderProfile shaderProfile) {
        if (shaderProfile != null) {
            this.mapOptionValues.putAll(shaderProfile.mapOptionValues);
        }
    }
    
    public void applyOptionValues(final ShaderOption[] array) {
        while (0 < array.length) {
            final ShaderOption shaderOption = array[0];
            final String value = this.mapOptionValues.get(shaderOption.getName());
            if (value != null) {
                shaderOption.setValue(value);
            }
            int n = 0;
            ++n;
        }
    }
    
    public String[] getOptions() {
        final Set keySet = this.mapOptionValues.keySet();
        return (String[])keySet.toArray(new String[keySet.size()]);
    }
    
    public String getValue(final String s) {
        return this.mapOptionValues.get(s);
    }
    
    public void addDisabledProgram(final String s) {
        this.disabledPrograms.add(s);
    }
    
    public Collection getDisabledPrograms() {
        return new HashSet(this.disabledPrograms);
    }
    
    public void addDisabledPrograms(final Collection collection) {
        this.disabledPrograms.addAll(collection);
    }
    
    public boolean isProgramDisabled(final String s) {
        return this.disabledPrograms.contains(s);
    }
}
