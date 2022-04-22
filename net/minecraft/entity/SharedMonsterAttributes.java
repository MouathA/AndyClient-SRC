package net.minecraft.entity;

import org.apache.logging.log4j.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.ai.attributes.*;
import java.util.*;

public class SharedMonsterAttributes
{
    private static final Logger logger;
    public static final IAttribute maxHealth;
    public static final IAttribute followRange;
    public static final IAttribute knockbackResistance;
    public static final IAttribute movementSpeed;
    public static final IAttribute attackDamage;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001695";
        logger = LogManager.getLogger();
        maxHealth = new RangedAttribute(null, "generic.maxHealth", 20.0, 0.0, Double.MAX_VALUE).setDescription("Max Health").setShouldWatch(true);
        followRange = new RangedAttribute(null, "generic.followRange", 32.0, 0.0, 2048.0).setDescription("Follow Range");
        knockbackResistance = new RangedAttribute(null, "generic.knockbackResistance", 0.0, 0.0, 1.0).setDescription("Knockback Resistance");
        movementSpeed = new RangedAttribute(null, "generic.movementSpeed", 0.699999988079071, 0.0, Double.MAX_VALUE).setDescription("Movement Speed").setShouldWatch(true);
        attackDamage = new RangedAttribute(null, "generic.attackDamage", 2.0, 0.0, Double.MAX_VALUE);
    }
    
    public static NBTTagList writeBaseAttributeMapToNBT(final BaseAttributeMap baseAttributeMap) {
        final NBTTagList list = new NBTTagList();
        final Iterator<IAttributeInstance> iterator = baseAttributeMap.getAllAttributes().iterator();
        while (iterator.hasNext()) {
            list.appendTag(writeAttributeInstanceToNBT(iterator.next()));
        }
        return list;
    }
    
    private static NBTTagCompound writeAttributeInstanceToNBT(final IAttributeInstance attributeInstance) {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setString("Name", attributeInstance.getAttribute().getAttributeUnlocalizedName());
        nbtTagCompound.setDouble("Base", attributeInstance.getBaseValue());
        final Collection func_111122_c = attributeInstance.func_111122_c();
        if (func_111122_c != null && !func_111122_c.isEmpty()) {
            final NBTTagList list = new NBTTagList();
            for (final AttributeModifier attributeModifier : func_111122_c) {
                if (attributeModifier.isSaved()) {
                    list.appendTag(writeAttributeModifierToNBT(attributeModifier));
                }
            }
            nbtTagCompound.setTag("Modifiers", list);
        }
        return nbtTagCompound;
    }
    
    private static NBTTagCompound writeAttributeModifierToNBT(final AttributeModifier attributeModifier) {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setString("Name", attributeModifier.getName());
        nbtTagCompound.setDouble("Amount", attributeModifier.getAmount());
        nbtTagCompound.setInteger("Operation", attributeModifier.getOperation());
        nbtTagCompound.setLong("UUIDMost", attributeModifier.getID().getMostSignificantBits());
        nbtTagCompound.setLong("UUIDLeast", attributeModifier.getID().getLeastSignificantBits());
        return nbtTagCompound;
    }
    
    public static void func_151475_a(final BaseAttributeMap baseAttributeMap, final NBTTagList list) {
        while (0 < list.tagCount()) {
            final NBTTagCompound compoundTag = list.getCompoundTagAt(0);
            final IAttributeInstance attributeInstanceByName = baseAttributeMap.getAttributeInstanceByName(compoundTag.getString("Name"));
            if (attributeInstanceByName != null) {
                applyModifiersToAttributeInstance(attributeInstanceByName, compoundTag);
            }
            else {
                SharedMonsterAttributes.logger.warn("Ignoring unknown attribute '" + compoundTag.getString("Name") + "'");
            }
            int n = 0;
            ++n;
        }
    }
    
    private static void applyModifiersToAttributeInstance(final IAttributeInstance attributeInstance, final NBTTagCompound nbtTagCompound) {
        attributeInstance.setBaseValue(nbtTagCompound.getDouble("Base"));
        if (nbtTagCompound.hasKey("Modifiers", 9)) {
            final NBTTagList tagList = nbtTagCompound.getTagList("Modifiers", 10);
            while (0 < tagList.tagCount()) {
                final AttributeModifier attributeModifierFromNBT = readAttributeModifierFromNBT(tagList.getCompoundTagAt(0));
                if (attributeModifierFromNBT != null) {
                    final AttributeModifier modifier = attributeInstance.getModifier(attributeModifierFromNBT.getID());
                    if (modifier != null) {
                        attributeInstance.removeModifier(modifier);
                    }
                    attributeInstance.applyModifier(attributeModifierFromNBT);
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    public static AttributeModifier readAttributeModifierFromNBT(final NBTTagCompound nbtTagCompound) {
        return new AttributeModifier(new UUID(nbtTagCompound.getLong("UUIDMost"), nbtTagCompound.getLong("UUIDLeast")), nbtTagCompound.getString("Name"), nbtTagCompound.getDouble("Amount"), nbtTagCompound.getInteger("Operation"));
    }
}
