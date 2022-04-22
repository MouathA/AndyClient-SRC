package net.minecraft.block.properties;

import com.google.common.collect.*;
import java.util.*;

public class PropertyInteger extends PropertyHelper
{
    private final ImmutableSet allowedValues;
    private static final String __OBFID;
    
    protected PropertyInteger(final String s, final int n, final int n2) {
        super(s, Integer.class);
        if (n < 0) {
            throw new IllegalArgumentException("Min value of " + s + " must be 0 or greater");
        }
        if (n2 <= n) {
            throw new IllegalArgumentException("Max value of " + s + " must be greater than min (" + n + ")");
        }
        final HashSet hashSet = Sets.newHashSet();
        for (int i = n; i <= n2; ++i) {
            hashSet.add(i);
        }
        this.allowedValues = ImmutableSet.copyOf(hashSet);
    }
    
    @Override
    public Collection getAllowedValues() {
        return this.allowedValues;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && super.equals(o) && this.allowedValues.equals(((PropertyInteger)o).allowedValues));
    }
    
    @Override
    public int hashCode() {
        return 31 * super.hashCode() + this.allowedValues.hashCode();
    }
    
    public static PropertyInteger create(final String s, final int n, final int n2) {
        return new PropertyInteger(s, n, n2);
    }
    
    public String getName0(final Integer n) {
        return n.toString();
    }
    
    @Override
    public String getName(final Comparable comparable) {
        return this.getName0((Integer)comparable);
    }
    
    static {
        __OBFID = "CL_00002014";
    }
}
