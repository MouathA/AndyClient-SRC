package net.minecraft.block.properties;

import com.google.common.collect.*;
import java.util.*;

public class PropertyBool extends PropertyHelper
{
    private final ImmutableSet allowedValues;
    private static final String __OBFID;
    
    protected PropertyBool(final String s) {
        super(s, Boolean.class);
        this.allowedValues = ImmutableSet.of(true, false);
    }
    
    @Override
    public Collection getAllowedValues() {
        return this.allowedValues;
    }
    
    public static PropertyBool create(final String s) {
        return new PropertyBool(s);
    }
    
    public String getName0(final Boolean b) {
        return b.toString();
    }
    
    @Override
    public String getName(final Comparable comparable) {
        return this.getName0((Boolean)comparable);
    }
    
    static {
        __OBFID = "CL_00002017";
    }
}
