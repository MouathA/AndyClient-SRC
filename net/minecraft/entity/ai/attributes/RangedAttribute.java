package net.minecraft.entity.ai.attributes;

import net.minecraft.util.*;

public class RangedAttribute extends BaseAttribute
{
    private final double minimumValue;
    private final double maximumValue;
    private String description;
    private static final String __OBFID;
    
    public RangedAttribute(final IAttribute attribute, final String s, final double n, final double minimumValue, final double maximumValue) {
        super(attribute, s, n);
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        if (minimumValue > maximumValue) {
            throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
        }
        if (n < minimumValue) {
            throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
        }
        if (n > maximumValue) {
            throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
        }
    }
    
    public RangedAttribute setDescription(final String description) {
        this.description = description;
        return this;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    @Override
    public double clampValue(double clamp_double) {
        clamp_double = MathHelper.clamp_double(clamp_double, this.minimumValue, this.maximumValue);
        return clamp_double;
    }
    
    static {
        __OBFID = "CL_00001568";
    }
}
