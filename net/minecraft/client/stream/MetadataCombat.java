package net.minecraft.client.stream;

import net.minecraft.entity.*;

public class MetadataCombat extends Metadata
{
    private static final String __OBFID;
    
    public MetadataCombat(final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        super("player_combat");
        this.func_152808_a("player", entityLivingBase.getName());
        if (entityLivingBase2 != null) {
            this.func_152808_a("primary_opponent", entityLivingBase2.getName());
        }
        if (entityLivingBase2 != null) {
            this.func_152807_a("Combat between " + entityLivingBase.getName() + " and " + entityLivingBase2.getName());
        }
        else {
            this.func_152807_a("Combat between " + entityLivingBase.getName() + " and others");
        }
    }
    
    static {
        __OBFID = "CL_00002377";
    }
}
