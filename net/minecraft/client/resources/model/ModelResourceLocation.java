package net.minecraft.client.resources.model;

import net.minecraft.util.*;
import org.apache.commons.lang3.*;

public class ModelResourceLocation extends ResourceLocation
{
    private final String field_177519_c;
    private static final String __OBFID;
    
    protected ModelResourceLocation(final int n, final String... array) {
        super(0, new String[] { array[0], array[1] });
        this.field_177519_c = (StringUtils.isEmpty(array[2]) ? "normal" : array[2].toLowerCase());
    }
    
    public ModelResourceLocation(final String s) {
        this(0, func_177517_b(s));
    }
    
    public ModelResourceLocation(final ResourceLocation resourceLocation, final String s) {
        this(resourceLocation.toString(), s);
    }
    
    public ModelResourceLocation(final String s, final String s2) {
        this(0, func_177517_b(String.valueOf(s) + '#' + ((s2 == null) ? "normal" : s2)));
    }
    
    protected static String[] func_177517_b(final String s) {
        final String[] array = { null, s, null };
        final int index = s.indexOf(35);
        String substring = s;
        if (index >= 0) {
            array[2] = s.substring(index + 1, s.length());
            if (index > 1) {
                substring = s.substring(0, index);
            }
        }
        System.arraycopy(ResourceLocation.func_177516_a(substring), 0, array, 0, 2);
        return array;
    }
    
    public String func_177518_c() {
        return this.field_177519_c;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof ModelResourceLocation && super.equals(o) && this.field_177519_c.equals(((ModelResourceLocation)o).field_177519_c));
    }
    
    @Override
    public int hashCode() {
        return 31 * super.hashCode() + this.field_177519_c.hashCode();
    }
    
    @Override
    public String toString() {
        return String.valueOf(super.toString()) + '#' + this.field_177519_c;
    }
    
    static {
        __OBFID = "CL_00002387";
    }
}
