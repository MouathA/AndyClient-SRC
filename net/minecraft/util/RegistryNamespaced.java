package net.minecraft.util;

import com.google.common.collect.*;
import java.util.*;

public class RegistryNamespaced extends RegistrySimple implements IObjectIntIterable
{
    protected final ObjectIntIdentityMap underlyingIntegerMap;
    protected final Map field_148758_b;
    private static final String __OBFID;
    
    public RegistryNamespaced() {
        this.underlyingIntegerMap = new ObjectIntIdentityMap();
        this.field_148758_b = ((BiMap)this.registryObjects).inverse();
    }
    
    public void register(final int n, final Object o, final Object o2) {
        this.underlyingIntegerMap.put(o2, n);
        this.putObject(o, o2);
    }
    
    @Override
    protected Map createUnderlyingMap() {
        return HashBiMap.create();
    }
    
    @Override
    public Object getObject(final Object o) {
        return super.getObject(o);
    }
    
    public Object getNameForObject(final Object o) {
        return this.field_148758_b.get(o);
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return super.containsKey(o);
    }
    
    public int getIDForObject(final Object o) {
        return this.underlyingIntegerMap.get(o);
    }
    
    public Object getObjectById(final int n) {
        return this.underlyingIntegerMap.getByValue(n);
    }
    
    @Override
    public Iterator iterator() {
        return this.underlyingIntegerMap.iterator();
    }
    
    static {
        __OBFID = "CL_00001206";
    }
}
