package net.minecraft.entity.item;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public class EntityMinecartTNT extends EntityMinecart
{
    private int minecartTNTFuse;
    private static final String __OBFID;
    
    public EntityMinecartTNT(final World world) {
        super(world);
        this.minecartTNTFuse = -1;
    }
    
    public EntityMinecartTNT(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
        this.minecartTNTFuse = -1;
    }
    
    @Override
    public EnumMinecartType func_180456_s() {
        return EnumMinecartType.TNT;
    }
    
    @Override
    public IBlockState func_180457_u() {
        return Blocks.tnt.getDefaultState();
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.minecartTNTFuse > 0) {
            --this.minecartTNTFuse;
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0, new int[0]);
        }
        else if (this.minecartTNTFuse == 0) {
            this.explodeCart(this.motionX * this.motionX + this.motionZ * this.motionZ);
        }
        if (this.isCollidedHorizontally) {
            final double n = this.motionX * this.motionX + this.motionZ * this.motionZ;
            if (n >= 0.009999999776482582) {
                this.explodeCart(n);
            }
        }
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        final Entity sourceOfDamage = damageSource.getSourceOfDamage();
        if (sourceOfDamage instanceof EntityArrow) {
            final EntityArrow entityArrow = (EntityArrow)sourceOfDamage;
            if (entityArrow.isBurning()) {
                this.explodeCart(entityArrow.motionX * entityArrow.motionX + entityArrow.motionY * entityArrow.motionY + entityArrow.motionZ * entityArrow.motionZ);
            }
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    public void killMinecart(final DamageSource damageSource) {
        super.killMinecart(damageSource);
        final double n = this.motionX * this.motionX + this.motionZ * this.motionZ;
        if (!damageSource.isExplosion()) {
            this.entityDropItem(new ItemStack(Blocks.tnt, 1), 0.0f);
        }
        if (damageSource.isFireDamage() || damageSource.isExplosion() || n >= 0.009999999776482582) {
            this.explodeCart(n);
        }
    }
    
    protected void explodeCart(final double n) {
        if (!this.worldObj.isRemote) {
            double sqrt = Math.sqrt(n);
            if (sqrt > 5.0) {
                sqrt = 5.0;
            }
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)(4.0 + this.rand.nextDouble() * 1.5 * sqrt), true);
            this.setDead();
        }
    }
    
    @Override
    public void fall(final float n, final float n2) {
        if (n >= 3.0f) {
            final float n3 = n / 10.0f;
            this.explodeCart(n3 * n3);
        }
        super.fall(n, n2);
    }
    
    @Override
    public void onActivatorRailPass(final int n, final int n2, final int n3, final boolean b) {
        if (b && this.minecartTNTFuse < 0) {
            this.ignite();
        }
    }
    
    @Override
    public void handleHealthUpdate(final byte b) {
        if (b == 10) {
            this.ignite();
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    public void ignite() {
        this.minecartTNTFuse = 80;
        if (!this.worldObj.isRemote) {
            this.worldObj.setEntityState(this, (byte)10);
            if (!this.isSlient()) {
                this.worldObj.playSoundAtEntity(this, "game.tnt.primed", 1.0f, 1.0f);
            }
        }
    }
    
    public int func_94104_d() {
        return this.minecartTNTFuse;
    }
    
    @Override
    public float getExplosionResistance(final Explosion p0, final World p1, final BlockPos p2, final IBlockState p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmple       27
        //     4: aload           4
        //     6: invokestatic    net/minecraft/block/BlockRailBase.func_176563_d:(Lnet/minecraft/block/state/IBlockState;)Z
        //     9: ifne            23
        //    12: aload_2        
        //    13: aload_3        
        //    14: invokevirtual   net/minecraft/util/BlockPos.offsetUp:()Lnet/minecraft/util/BlockPos;
        //    17: invokestatic    net/minecraft/block/BlockRailBase.func_176562_d:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Z
        //    20: ifeq            27
        //    23: fconst_0       
        //    24: goto            36
        //    27: aload_0        
        //    28: aload_1        
        //    29: aload_2        
        //    30: aload_3        
        //    31: aload           4
        //    33: invokespecial   net/minecraft/entity/item/EntityMinecart.getExplosionResistance:(Lnet/minecraft/world/Explosion;Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)F
        //    36: freturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public boolean func_174816_a(final Explosion p0, final World p1, final BlockPos p2, final IBlockState p3, final float p4) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmple       27
        //     4: aload           4
        //     6: invokestatic    net/minecraft/block/BlockRailBase.func_176563_d:(Lnet/minecraft/block/state/IBlockState;)Z
        //     9: ifne            23
        //    12: aload_2        
        //    13: aload_3        
        //    14: invokevirtual   net/minecraft/util/BlockPos.offsetUp:()Lnet/minecraft/util/BlockPos;
        //    17: invokestatic    net/minecraft/block/BlockRailBase.func_176562_d:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Z
        //    20: ifeq            27
        //    23: iconst_0       
        //    24: goto            38
        //    27: aload_0        
        //    28: aload_1        
        //    29: aload_2        
        //    30: aload_3        
        //    31: aload           4
        //    33: fload           5
        //    35: invokespecial   net/minecraft/entity/item/EntityMinecart.func_174816_a:(Lnet/minecraft/world/Explosion;Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;F)Z
        //    38: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey("TNTFuse", 99)) {
            this.minecartTNTFuse = nbtTagCompound.getInteger("TNTFuse");
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("TNTFuse", this.minecartTNTFuse);
    }
    
    static {
        __OBFID = "CL_00001680";
    }
}
