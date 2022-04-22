package net.minecraft.tileentity;

import net.minecraft.server.gui.*;
import com.google.common.collect.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.stats.*;
import net.minecraft.block.state.*;
import net.minecraft.network.*;
import net.minecraft.nbt.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class TileEntityBeacon extends TileEntityLockable implements IUpdatePlayerListBox, IInventory
{
    public static final Potion[][] effectsList;
    private final List field_174909_f;
    private long field_146016_i;
    private float field_146014_j;
    private boolean isComplete;
    private int levels;
    private int primaryEffect;
    private int secondaryEffect;
    private ItemStack payment;
    private String field_146008_p;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000339";
        effectsList = new Potion[][] { { Potion.moveSpeed, Potion.digSpeed }, { Potion.resistance, Potion.jump }, { Potion.damageBoost }, { Potion.regeneration } };
    }
    
    public TileEntityBeacon() {
        this.field_174909_f = Lists.newArrayList();
        this.levels = -1;
    }
    
    @Override
    public void update() {
        if (this.worldObj.getTotalWorldTime() % 80L == 0L) {
            this.func_174908_m();
        }
    }
    
    public void func_174908_m() {
        this.func_146003_y();
        this.func_146000_x();
    }
    
    private void func_146000_x() {
        if (this.isComplete && this.levels > 0 && !this.worldObj.isRemote && this.primaryEffect > 0) {
            final double n = this.levels * 10 + 10;
            if (this.levels < 4 || this.primaryEffect == this.secondaryEffect) {}
            final int x = this.pos.getX();
            final int y = this.pos.getY();
            final int z = this.pos.getZ();
            final List entitiesWithinAABB = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1).expand(n, n, n).addCoord(0.0, this.worldObj.getHeight(), 0.0));
            final Iterator<EntityPlayer> iterator = entitiesWithinAABB.iterator();
            while (iterator.hasNext()) {
                iterator.next().addPotionEffect(new PotionEffect(this.primaryEffect, 180, 1, true, true));
            }
            if (this.levels >= 4 && this.primaryEffect != this.secondaryEffect && this.secondaryEffect > 0) {
                final Iterator<EntityPlayer> iterator2 = entitiesWithinAABB.iterator();
                while (iterator2.hasNext()) {
                    iterator2.next().addPotionEffect(new PotionEffect(this.secondaryEffect, 180, 0, true, true));
                }
            }
        }
    }
    
    private void func_146003_y() {
        final int levels = this.levels;
        final int x = this.pos.getX();
        final int y = this.pos.getY();
        final int z = this.pos.getZ();
        this.levels = 0;
        this.field_174909_f.clear();
        this.isComplete = true;
        BeamSegment beamSegment = new BeamSegment(EntitySheep.func_175513_a(EnumDyeColor.WHITE));
        this.field_174909_f.add(beamSegment);
        int n = y + 1;
        while (1 < this.worldObj.getActualHeight()) {
            final IBlockState blockState = this.worldObj.getBlockState(new BlockPos(x, 1, z));
            Label_0313: {
                float[] array;
                if (blockState.getBlock() == Blocks.stained_glass) {
                    array = EntitySheep.func_175513_a((EnumDyeColor)blockState.getValue(BlockStainedGlass.field_176547_a));
                }
                else if (blockState.getBlock() != Blocks.stained_glass_pane) {
                    if (blockState.getBlock().getLightOpacity() >= 15) {
                        this.isComplete = false;
                        this.field_174909_f.clear();
                        break;
                    }
                    beamSegment.func_177262_a();
                    break Label_0313;
                }
                else {
                    array = EntitySheep.func_175513_a((EnumDyeColor)blockState.getValue(BlockStainedGlassPane.field_176245_a));
                }
                final float[] array2 = { (beamSegment.func_177263_b()[0] + array[0]) / 2.0f, (beamSegment.func_177263_b()[1] + array[1]) / 2.0f, (beamSegment.func_177263_b()[2] + array[2]) / 2.0f };
                if (Arrays.equals(array2, beamSegment.func_177263_b())) {
                    beamSegment.func_177262_a();
                }
                else {
                    beamSegment = new BeamSegment(array2);
                    this.field_174909_f.add(beamSegment);
                }
            }
            ++n;
        }
        if (this.isComplete && this.levels == 0) {
            this.isComplete = false;
        }
        if (!this.worldObj.isRemote && this.levels == 4 && levels < this.levels) {
            final Iterator iterator = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(x, y, z, x, y - 4, z).expand(10.0, 5.0, 10.0)).iterator();
            while (iterator.hasNext()) {
                iterator.next().triggerAchievement(AchievementList.fullBeacon);
            }
        }
    }
    
    public List func_174907_n() {
        return this.field_174909_f;
    }
    
    public float shouldBeamRender() {
        if (!this.isComplete) {
            return 0.0f;
        }
        final int n = (int)(this.worldObj.getTotalWorldTime() - this.field_146016_i);
        this.field_146016_i = this.worldObj.getTotalWorldTime();
        if (n > 1) {
            this.field_146014_j -= n / 40.0f;
            if (this.field_146014_j < 0.0f) {
                this.field_146014_j = 0.0f;
            }
        }
        this.field_146014_j += 0.025f;
        if (this.field_146014_j > 1.0f) {
            this.field_146014_j = 1.0f;
        }
        return this.field_146014_j;
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.pos, 3, nbtTagCompound);
    }
    
    @Override
    public double getMaxRenderDistanceSquared() {
        return 65536.0;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.primaryEffect = nbtTagCompound.getInteger("Primary");
        this.secondaryEffect = nbtTagCompound.getInteger("Secondary");
        this.levels = nbtTagCompound.getInteger("Levels");
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("Primary", this.primaryEffect);
        nbtTagCompound.setInteger("Secondary", this.secondaryEffect);
        nbtTagCompound.setInteger("Levels", this.levels);
    }
    
    @Override
    public int getSizeInventory() {
        return 1;
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return (n == 0) ? this.payment : null;
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (n != 0 || this.payment == null) {
            return null;
        }
        if (n2 >= this.payment.stackSize) {
            final ItemStack payment = this.payment;
            this.payment = null;
            return payment;
        }
        final ItemStack payment2 = this.payment;
        payment2.stackSize -= n2;
        return new ItemStack(this.payment.getItem(), n2, this.payment.getMetadata());
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int n) {
        if (n == 0 && this.payment != null) {
            final ItemStack payment = this.payment;
            this.payment = null;
            return payment;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack payment) {
        if (n == 0) {
            this.payment = payment;
        }
    }
    
    @Override
    public String getName() {
        return (this != null) ? this.field_146008_p : "container.beacon";
    }
    
    public void func_145999_a(final String field_146008_p) {
        this.field_146008_p = field_146008_p;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return this.worldObj.getTileEntity(this.pos) == this && entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0;
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return itemStack.getItem() == Items.emerald || itemStack.getItem() == Items.diamond || itemStack.getItem() == Items.gold_ingot || itemStack.getItem() == Items.iron_ingot;
    }
    
    @Override
    public String getGuiID() {
        return "minecraft:beacon";
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerBeacon(inventoryPlayer, this);
    }
    
    @Override
    public int getField(final int n) {
        switch (n) {
            case 0: {
                return this.levels;
            }
            case 1: {
                return this.primaryEffect;
            }
            case 2: {
                return this.secondaryEffect;
            }
            default: {
                return 0;
            }
        }
    }
    
    @Override
    public void setField(final int n, final int secondaryEffect) {
        switch (n) {
            case 0: {
                this.levels = secondaryEffect;
                break;
            }
            case 1: {
                this.primaryEffect = secondaryEffect;
                break;
            }
            case 2: {
                this.secondaryEffect = secondaryEffect;
                break;
            }
        }
    }
    
    @Override
    public int getFieldCount() {
        return 3;
    }
    
    @Override
    public void clearInventory() {
        this.payment = null;
    }
    
    @Override
    public boolean receiveClientEvent(final int n, final int n2) {
        if (n == 1) {
            this.func_174908_m();
            return true;
        }
        return super.receiveClientEvent(n, n2);
    }
    
    public static class BeamSegment
    {
        private final float[] field_177266_a;
        private int field_177265_b;
        private static final String __OBFID;
        
        public BeamSegment(final float[] field_177266_a) {
            this.field_177266_a = field_177266_a;
            this.field_177265_b = 1;
        }
        
        protected void func_177262_a() {
            ++this.field_177265_b;
        }
        
        public float[] func_177263_b() {
            return this.field_177266_a;
        }
        
        public int func_177264_c() {
            return this.field_177265_b;
        }
        
        static {
            __OBFID = "CL_00002042";
        }
    }
}
