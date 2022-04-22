package net.minecraft.entity.ai.attributes;

import com.google.common.collect.*;
import java.util.*;

public class ModifiableAttributeInstance implements IAttributeInstance
{
    private final BaseAttributeMap attributeMap;
    private final IAttribute genericAttribute;
    private final Map mapByOperation;
    private final Map mapByName;
    private final Map mapByUUID;
    private double baseValue;
    private boolean needsUpdate;
    private double cachedValue;
    private static final String __OBFID;
    
    public ModifiableAttributeInstance(final BaseAttributeMap attributeMap, final IAttribute genericAttribute) {
        this.mapByOperation = Maps.newHashMap();
        this.mapByName = Maps.newHashMap();
        this.mapByUUID = Maps.newHashMap();
        this.needsUpdate = true;
        this.attributeMap = attributeMap;
        this.genericAttribute = genericAttribute;
        this.baseValue = genericAttribute.getDefaultValue();
        while (0 < 3) {
            this.mapByOperation.put(0, Sets.newHashSet());
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public IAttribute getAttribute() {
        return this.genericAttribute;
    }
    
    @Override
    public double getBaseValue() {
        return this.baseValue;
    }
    
    @Override
    public void setBaseValue(final double baseValue) {
        if (baseValue != this.getBaseValue()) {
            this.baseValue = baseValue;
            this.flagForUpdate();
        }
    }
    
    @Override
    public Collection getModifiersByOperation(final int n) {
        return this.mapByOperation.get(n);
    }
    
    @Override
    public Collection func_111122_c() {
        final HashSet hashSet = Sets.newHashSet();
        while (0 < 3) {
            hashSet.addAll(this.getModifiersByOperation(0));
            int n = 0;
            ++n;
        }
        return hashSet;
    }
    
    @Override
    public AttributeModifier getModifier(final UUID uuid) {
        return this.mapByUUID.get(uuid);
    }
    
    @Override
    public boolean func_180374_a(final AttributeModifier attributeModifier) {
        return this.mapByUUID.get(attributeModifier.getID()) != null;
    }
    
    @Override
    public void applyModifier(final AttributeModifier attributeModifier) {
        if (this.getModifier(attributeModifier.getID()) != null) {
            throw new IllegalArgumentException("Modifier is already applied on this attribute!");
        }
        Set<AttributeModifier> hashSet = this.mapByName.get(attributeModifier.getName());
        if (hashSet == null) {
            hashSet = (Set<AttributeModifier>)Sets.newHashSet();
            this.mapByName.put(attributeModifier.getName(), hashSet);
        }
        this.mapByOperation.get(attributeModifier.getOperation()).add(attributeModifier);
        hashSet.add(attributeModifier);
        this.mapByUUID.put(attributeModifier.getID(), attributeModifier);
        this.flagForUpdate();
    }
    
    protected void flagForUpdate() {
        this.needsUpdate = true;
        this.attributeMap.func_180794_a(this);
    }
    
    @Override
    public void removeModifier(final AttributeModifier attributeModifier) {
        while (0 < 3) {
            this.mapByOperation.get(0).remove(attributeModifier);
            int n = 0;
            ++n;
        }
        final Set set = this.mapByName.get(attributeModifier.getName());
        if (set != null) {
            set.remove(attributeModifier);
            if (set.isEmpty()) {
                this.mapByName.remove(attributeModifier.getName());
            }
        }
        this.mapByUUID.remove(attributeModifier.getID());
        this.flagForUpdate();
    }
    
    @Override
    public void removeAllModifiers() {
        final Collection func_111122_c = this.func_111122_c();
        if (func_111122_c != null) {
            final Iterator iterator = Lists.newArrayList(func_111122_c).iterator();
            while (iterator.hasNext()) {
                this.removeModifier(iterator.next());
            }
        }
    }
    
    @Override
    public double getAttributeValue() {
        if (this.needsUpdate) {
            this.cachedValue = this.computeValue();
            this.needsUpdate = false;
        }
        return this.cachedValue;
    }
    
    private double computeValue() {
        double baseValue = this.getBaseValue();
        final Iterator<AttributeModifier> iterator = this.func_180375_b(0).iterator();
        while (iterator.hasNext()) {
            baseValue += iterator.next().getAmount();
        }
        double n = baseValue;
        final Iterator<AttributeModifier> iterator2 = this.func_180375_b(1).iterator();
        while (iterator2.hasNext()) {
            n += baseValue * iterator2.next().getAmount();
        }
        final Iterator<AttributeModifier> iterator3 = this.func_180375_b(2).iterator();
        while (iterator3.hasNext()) {
            n *= 1.0 + iterator3.next().getAmount();
        }
        return this.genericAttribute.clampValue(n);
    }
    
    private Collection func_180375_b(final int n) {
        final HashSet hashSet = Sets.newHashSet(this.getModifiersByOperation(n));
        for (IAttribute attribute = this.genericAttribute.func_180372_d(); attribute != null; attribute = attribute.func_180372_d()) {
            final IAttributeInstance attributeInstance = this.attributeMap.getAttributeInstance(attribute);
            if (attributeInstance != null) {
                hashSet.addAll(attributeInstance.getModifiersByOperation(n));
            }
        }
        return hashSet;
    }
    
    static {
        __OBFID = "CL_00001567";
    }
}
