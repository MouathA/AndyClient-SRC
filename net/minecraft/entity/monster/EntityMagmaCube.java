package net.minecraft.entity.monster;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class EntityMagmaCube extends EntitySlime
{
    private static final String __OBFID;
    
    public EntityMagmaCube(final World world) {
        super(world);
        this.isImmuneToFire = true;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224);
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;
    }
    
    @Override
    public boolean handleLavaMovement() {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), (Entity)this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox());
    }
    
    @Override
    public int getTotalArmorValue() {
        return this.getSlimeSize() * 3;
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return 15728880;
    }
    
    @Override
    public float getBrightness(final float n) {
        return 1.0f;
    }
    
    @Override
    protected EnumParticleTypes func_180487_n() {
        return EnumParticleTypes.FLAME;
    }
    
    @Override
    protected EntitySlime createInstance() {
        return new EntityMagmaCube(this.worldObj);
    }
    
    @Override
    protected Item getDropItem() {
        return Items.magma_cream;
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final Item dropItem = this.getDropItem();
        if (dropItem != null && this.getSlimeSize() > 1) {
            int n2 = this.rand.nextInt(4) - 2;
            if (n > 0) {
                n2 += this.rand.nextInt(n + 1);
            }
            while (0 < n2) {
                this.dropItem(dropItem, 1);
                int n3 = 0;
                ++n3;
            }
        }
    }
    
    @Override
    public boolean isBurning() {
        return false;
    }
    
    @Override
    protected int getJumpDelay() {
        return super.getJumpDelay() * 4;
    }
    
    @Override
    protected void alterSquishAmount() {
        this.squishAmount *= 0.9f;
    }
    
    @Override
    protected void jump() {
        this.motionY = 0.42f + this.getSlimeSize() * 0.1f;
        this.isAirBorne = true;
    }
    
    @Override
    protected void func_180466_bG() {
        this.motionY = 0.22f + this.getSlimeSize() * 0.05f;
        this.isAirBorne = true;
    }
    
    @Override
    public void fall(final float n, final float n2) {
    }
    
    protected boolean canDamagePlayer() {
        return true;
    }
    
    @Override
    protected int getAttackStrength() {
        return super.getAttackStrength() + 2;
    }
    
    @Override
    protected String getJumpSound() {
        return (this.getSlimeSize() > 1) ? "mob.magmacube.big" : "mob.magmacube.small";
    }
    
    protected boolean makesSoundOnLand() {
        return true;
    }
    
    static {
        __OBFID = "CL_00001691";
    }
}
