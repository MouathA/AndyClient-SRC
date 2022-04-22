package net.minecraft.entity.item;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;

public class EntityMinecartFurnace extends EntityMinecart
{
    private int fuel;
    public double pushX;
    public double pushZ;
    private static final String __OBFID;
    
    public EntityMinecartFurnace(final World world) {
        super(world);
    }
    
    public EntityMinecartFurnace(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
    }
    
    @Override
    public EnumMinecartType func_180456_s() {
        return EnumMinecartType.FURNACE;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.fuel > 0) {
            --this.fuel;
        }
        if (this.fuel <= 0) {
            final double n = 0.0;
            this.pushZ = n;
            this.pushX = n;
        }
        this.setMinecartPowered(this.fuel > 0);
        if (this != 0 && this.rand.nextInt(4) == 0) {
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 0.8, this.posZ, 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    @Override
    protected double func_174898_m() {
        return 0.2;
    }
    
    @Override
    public void killMinecart(final DamageSource damageSource) {
        super.killMinecart(damageSource);
        if (!damageSource.isExplosion()) {
            this.entityDropItem(new ItemStack(Blocks.furnace, 1), 0.0f);
        }
    }
    
    @Override
    protected void func_180460_a(final BlockPos blockPos, final IBlockState blockState) {
        super.func_180460_a(blockPos, blockState);
        final double n = this.pushX * this.pushX + this.pushZ * this.pushZ;
        if (n > 1.0E-4 && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001) {
            final double n2 = MathHelper.sqrt_double(n);
            this.pushX /= n2;
            this.pushZ /= n2;
            if (this.pushX * this.motionX + this.pushZ * this.motionZ < 0.0) {
                this.pushX = 0.0;
                this.pushZ = 0.0;
            }
            else {
                final double n3 = n2 / this.func_174898_m();
                this.pushX *= n3;
                this.pushZ *= n3;
            }
        }
    }
    
    @Override
    protected void applyDrag() {
        final double n = this.pushX * this.pushX + this.pushZ * this.pushZ;
        if (n > 1.0E-4) {
            final double n2 = MathHelper.sqrt_double(n);
            this.pushX /= n2;
            this.pushZ /= n2;
            final double n3 = 1.0;
            this.motionX *= 0.800000011920929;
            this.motionY *= 0.0;
            this.motionZ *= 0.800000011920929;
            this.motionX += this.pushX * n3;
            this.motionZ += this.pushZ * n3;
        }
        else {
            this.motionX *= 0.9800000190734863;
            this.motionY *= 0.0;
            this.motionZ *= 0.9800000190734863;
        }
        super.applyDrag();
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == Items.coal) {
            if (!entityPlayer.capabilities.isCreativeMode) {
                final ItemStack itemStack = currentItem;
                if (--itemStack.stackSize == 0) {
                    entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
                }
            }
            this.fuel += 3600;
        }
        this.pushX = this.posX - entityPlayer.posX;
        this.pushZ = this.posZ - entityPlayer.posZ;
        return true;
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setDouble("PushX", this.pushX);
        nbtTagCompound.setDouble("PushZ", this.pushZ);
        nbtTagCompound.setShort("Fuel", (short)this.fuel);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.pushX = nbtTagCompound.getDouble("PushX");
        this.pushZ = nbtTagCompound.getDouble("PushZ");
        this.fuel = nbtTagCompound.getShort("Fuel");
    }
    
    protected void setMinecartPowered(final boolean b) {
        if (b) {
            this.dataWatcher.updateObject(16, (byte)(this.dataWatcher.getWatchableObjectByte(16) | 0x1));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(this.dataWatcher.getWatchableObjectByte(16) & 0xFFFFFFFE));
        }
    }
    
    @Override
    public IBlockState func_180457_u() {
        return ((this != 0) ? Blocks.lit_furnace : Blocks.furnace).getDefaultState().withProperty(BlockFurnace.FACING, EnumFacing.NORTH);
    }
    
    static {
        __OBFID = "CL_00001675";
    }
}
