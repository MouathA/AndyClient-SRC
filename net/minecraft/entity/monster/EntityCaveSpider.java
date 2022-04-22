package net.minecraft.entity.monster;

import net.minecraft.potion.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public class EntityCaveSpider extends EntitySpider
{
    private static final String __OBFID;
    
    public EntityCaveSpider(final World world) {
        super(world);
        this.setSize(0.7f, 0.5f);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0);
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            if (entity instanceof EntityLivingBase) {
                if (this.worldObj.getDifficulty() != EnumDifficulty.NORMAL) {
                    if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {}
                }
                if (15 > 0) {
                    ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.poison.id, 300, 0));
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public IEntityLivingData func_180482_a(final DifficultyInstance difficultyInstance, final IEntityLivingData entityLivingData) {
        return entityLivingData;
    }
    
    @Override
    public float getEyeHeight() {
        return 0.45f;
    }
    
    static {
        __OBFID = "CL_00001683";
    }
}
