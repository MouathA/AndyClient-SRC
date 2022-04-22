package net.minecraft.entity.ai.attributes;

import com.google.common.collect.*;
import net.minecraft.server.management.*;
import java.util.*;

public class ServersideAttributeMap extends BaseAttributeMap
{
    private final Set attributeInstanceSet;
    protected final Map descriptionToAttributeInstanceMap;
    private static final String __OBFID;
    
    public ServersideAttributeMap() {
        this.attributeInstanceSet = Sets.newHashSet();
        this.descriptionToAttributeInstanceMap = new LowerStringMap();
    }
    
    public ModifiableAttributeInstance func_180795_e(final IAttribute attribute) {
        return (ModifiableAttributeInstance)super.getAttributeInstance(attribute);
    }
    
    public ModifiableAttributeInstance func_180796_b(final String s) {
        IAttributeInstance attributeInstanceByName = super.getAttributeInstanceByName(s);
        if (attributeInstanceByName == null) {
            attributeInstanceByName = this.descriptionToAttributeInstanceMap.get(s);
        }
        return (ModifiableAttributeInstance)attributeInstanceByName;
    }
    
    @Override
    public IAttributeInstance registerAttribute(final IAttribute attribute) {
        final IAttributeInstance registerAttribute = super.registerAttribute(attribute);
        if (attribute instanceof RangedAttribute && ((RangedAttribute)attribute).getDescription() != null) {
            this.descriptionToAttributeInstanceMap.put(((RangedAttribute)attribute).getDescription(), registerAttribute);
        }
        return registerAttribute;
    }
    
    @Override
    protected IAttributeInstance func_180376_c(final IAttribute attribute) {
        return new ModifiableAttributeInstance(this, attribute);
    }
    
    @Override
    public void func_180794_a(final IAttributeInstance attributeInstance) {
        if (attributeInstance.getAttribute().getShouldWatch()) {
            this.attributeInstanceSet.add(attributeInstance);
        }
        final Iterator<IAttribute> iterator = this.field_180377_c.get(attributeInstance.getAttribute()).iterator();
        while (iterator.hasNext()) {
            final ModifiableAttributeInstance func_180795_e = this.func_180795_e(iterator.next());
            if (func_180795_e != null) {
                func_180795_e.flagForUpdate();
            }
        }
    }
    
    public Set getAttributeInstanceSet() {
        return this.attributeInstanceSet;
    }
    
    public Collection getWatchedAttributes() {
        final HashSet hashSet = Sets.newHashSet();
        for (final IAttributeInstance attributeInstance : this.getAllAttributes()) {
            if (attributeInstance.getAttribute().getShouldWatch()) {
                hashSet.add(attributeInstance);
            }
        }
        return hashSet;
    }
    
    @Override
    public IAttributeInstance getAttributeInstanceByName(final String s) {
        return this.func_180796_b(s);
    }
    
    @Override
    public IAttributeInstance getAttributeInstance(final IAttribute attribute) {
        return this.func_180795_e(attribute);
    }
    
    static {
        __OBFID = "CL_00001569";
    }
}
