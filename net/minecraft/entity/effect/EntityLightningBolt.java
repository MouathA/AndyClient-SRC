package net.minecraft.entity.effect;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;

public class EntityLightningBolt extends EntityWeatherEffect
{
    private int lightningState;
    public long boltVertex;
    private int boltLivingTime;
    private static final String __OBFID;
    
    public EntityLightningBolt(final World world, final double n, final double n2, final double n3) {
        super(world);
        this.setLocationAndAngles(n, n2, n3, 0.0f, 0.0f);
        this.lightningState = 2;
        this.boltVertex = this.rand.nextLong();
        this.boltLivingTime = this.rand.nextInt(3) + 1;
        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doFireTick") && (world.getDifficulty() == EnumDifficulty.NORMAL || world.getDifficulty() == EnumDifficulty.HARD) && world.isAreaLoaded(new BlockPos(this), 10)) {
            final BlockPos blockPos = new BlockPos(this);
            if (world.getBlockState(blockPos).getBlock().getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(world, blockPos)) {
                world.setBlockState(blockPos, Blocks.fire.getDefaultState());
            }
            while (0 < 4) {
                final BlockPos add = blockPos.add(this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1);
                if (world.getBlockState(add).getBlock().getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(world, add)) {
                    world.setBlockState(add, Blocks.fire.getDefaultState());
                }
                int n4 = 0;
                ++n4;
            }
        }
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.lightningState == 2) {
            this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0f, 0.8f + this.rand.nextFloat() * 0.2f);
            this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 2.0f, 0.5f + this.rand.nextFloat() * 0.2f);
        }
        --this.lightningState;
        if (this.lightningState < 0) {
            if (this.boltLivingTime == 0) {
                this.setDead();
            }
            else if (this.lightningState < -this.rand.nextInt(10)) {
                --this.boltLivingTime;
                this.lightningState = 1;
                this.boltVertex = this.rand.nextLong();
                final BlockPos blockPos = new BlockPos(this);
                if (!this.worldObj.isRemote && this.worldObj.getGameRules().getGameRuleBooleanValue("doFireTick") && this.worldObj.isAreaLoaded(blockPos, 10) && this.worldObj.getBlockState(blockPos).getBlock().getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(this.worldObj, blockPos)) {
                    this.worldObj.setBlockState(blockPos, Blocks.fire.getDefaultState());
                }
            }
        }
        if (this.lightningState >= 0) {
            if (this.worldObj.isRemote) {
                this.worldObj.setLastLightningBolt(2);
            }
            else {
                final double n = 3.0;
                final List entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(this.posX - n, this.posY - n, this.posZ - n, this.posX + n, this.posY + 6.0 + n, this.posZ + n));
                while (0 < entitiesWithinAABBExcludingEntity.size()) {
                    entitiesWithinAABBExcludingEntity.get(0).onStruckByLightning(this);
                    int n2 = 0;
                    ++n2;
                }
            }
        }
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    static {
        __OBFID = "CL_00001666";
    }
}
