package net.minecraft.block.properties;

import java.util.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;

public class PropertyDirection extends PropertyEnum
{
    private static final String __OBFID;
    
    protected PropertyDirection(final String s, final Collection collection) {
        super(s, EnumFacing.class, collection);
    }
    
    public static PropertyDirection create(final String s) {
        return create(s, Predicates.alwaysTrue());
    }
    
    public static PropertyDirection create(final String s, final Predicate predicate) {
        return create(s, Collections2.filter(Lists.newArrayList((Object[])EnumFacing.values()), predicate));
    }
    
    public static PropertyDirection create(final String s, final Collection collection) {
        return new PropertyDirection(s, collection);
    }
    
    static {
        __OBFID = "CL_00002016";
    }
}
