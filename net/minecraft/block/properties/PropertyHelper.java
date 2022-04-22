package net.minecraft.block.properties;

import com.google.common.base.*;

public abstract class PropertyHelper implements IProperty
{
    private final Class valueClass;
    private final String name;
    private static final String __OBFID;
    
    protected PropertyHelper(final String name, final Class valueClass) {
        this.valueClass = valueClass;
        this.name = name;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public Class getValueClass() {
        return this.valueClass;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("name", this.name).add("clazz", this.valueClass).add("values", this.getAllowedValues()).toString();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && this.getClass() == o.getClass()) {
            final PropertyHelper propertyHelper = (PropertyHelper)o;
            return this.valueClass.equals(propertyHelper.valueClass) && this.name.equals(propertyHelper.name);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return 31 * this.valueClass.hashCode() + this.name.hashCode();
    }
    
    static {
        __OBFID = "CL_00002018";
    }
}
