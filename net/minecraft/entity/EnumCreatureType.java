package net.minecraft.entity;

import net.minecraft.block.material.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;

public enum EnumCreatureType
{
    MONSTER("MONSTER", 0, "MONSTER", 0, (Class)IMob.class, 70, Material.air, false, false), 
    CREATURE("CREATURE", 1, "CREATURE", 1, (Class)EntityAnimal.class, 10, Material.air, true, true), 
    AMBIENT("AMBIENT", 2, "AMBIENT", 2, (Class)EntityAmbientCreature.class, 15, Material.air, true, false), 
    WATER_CREATURE("WATER_CREATURE", 3, "WATER_CREATURE", 3, (Class)EntityWaterMob.class, 5, Material.water, true, false);
    
    private final Class creatureClass;
    private final int maxNumberOfCreature;
    private final Material creatureMaterial;
    private final boolean isPeacefulCreature;
    private final boolean isAnimal;
    private static final EnumCreatureType[] $VALUES;
    private static final String __OBFID;
    private static final EnumCreatureType[] ENUM$VALUES;
    
    static {
        __OBFID = "CL_00001551";
        ENUM$VALUES = new EnumCreatureType[] { EnumCreatureType.MONSTER, EnumCreatureType.CREATURE, EnumCreatureType.AMBIENT, EnumCreatureType.WATER_CREATURE };
        $VALUES = new EnumCreatureType[] { EnumCreatureType.MONSTER, EnumCreatureType.CREATURE, EnumCreatureType.AMBIENT, EnumCreatureType.WATER_CREATURE };
    }
    
    private EnumCreatureType(final String s, final int n, final String s2, final int n2, final Class creatureClass, final int maxNumberOfCreature, final Material creatureMaterial, final boolean isPeacefulCreature, final boolean isAnimal) {
        this.creatureClass = creatureClass;
        this.maxNumberOfCreature = maxNumberOfCreature;
        this.creatureMaterial = creatureMaterial;
        this.isPeacefulCreature = isPeacefulCreature;
        this.isAnimal = isAnimal;
    }
    
    public Class getCreatureClass() {
        return this.creatureClass;
    }
    
    public int getMaxNumberOfCreature() {
        return this.maxNumberOfCreature;
    }
    
    public boolean getPeacefulCreature() {
        return this.isPeacefulCreature;
    }
    
    public boolean getAnimal() {
        return this.isAnimal;
    }
}
