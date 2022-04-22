package net.minecraft.tileentity;

import net.minecraft.server.gui.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import java.util.*;
import java.nio.charset.*;

public class TileEntityChest extends TileEntityLockable implements IUpdatePlayerListBox, IInventory
{
    private ItemStack[] chestContents;
    public boolean adjacentChestChecked;
    public TileEntityChest adjacentChestZNeg;
    public TileEntityChest adjacentChestXPos;
    public TileEntityChest adjacentChestXNeg;
    public TileEntityChest adjacentChestZPos;
    public float lidAngle;
    public float prevLidAngle;
    public int numPlayersUsing;
    private int ticksSinceSync;
    private int cachedChestType;
    private String customName;
    private static final String __OBFID;
    
    public TileEntityChest() {
        this.chestContents = new ItemStack[27];
        this.cachedChestType = -1;
    }
    
    public TileEntityChest(final int cachedChestType) {
        this.chestContents = new ItemStack[27];
        this.cachedChestType = cachedChestType;
    }
    
    @Override
    public int getSizeInventory() {
        return 27;
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return this.chestContents[n];
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.chestContents[n] == null) {
            return null;
        }
        if (this.chestContents[n].stackSize <= n2) {
            final ItemStack itemStack = this.chestContents[n];
            this.chestContents[n] = null;
            this.markDirty();
            return itemStack;
        }
        final ItemStack splitStack = this.chestContents[n].splitStack(n2);
        if (this.chestContents[n].stackSize == 0) {
            this.chestContents[n] = null;
        }
        this.markDirty();
        return splitStack;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int n) {
        if (this.chestContents[n] != null) {
            final ItemStack itemStack = this.chestContents[n];
            this.chestContents[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.chestContents[n] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        this.markDirty();
    }
    
    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "container.chest";
    }
    
    @Override
    public boolean hasCustomName() {
        return this.customName != null && this.customName.length() > 0;
    }
    
    public void setCustomName(final String customName) {
        this.customName = customName;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        final NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
        this.chestContents = new ItemStack[this.getSizeInventory()];
        if (nbtTagCompound.hasKey("CustomName", 8)) {
            this.customName = nbtTagCompound.getString("CustomName");
        }
        while (0 < tagList.tagCount()) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(0);
            final int n = compoundTag.getByte("Slot") & 0xFF;
            if (n >= 0 && n < this.chestContents.length) {
                this.chestContents[n] = ItemStack.loadItemStackFromNBT(compoundTag);
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        final NBTTagList list = new NBTTagList();
        while (0 < this.chestContents.length) {
            if (this.chestContents[0] != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte("Slot", (byte)0);
                this.chestContents[0].writeToNBT(nbtTagCompound2);
                list.appendTag(nbtTagCompound2);
            }
            int n = 0;
            ++n;
        }
        nbtTagCompound.setTag("Items", list);
        if (this.hasCustomName()) {
            nbtTagCompound.setString("CustomName", this.customName);
        }
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return this.worldObj.getTileEntity(this.pos) == this && entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0;
    }
    
    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        this.adjacentChestChecked = false;
    }
    
    private void func_174910_a(final TileEntityChest tileEntityChest, final EnumFacing enumFacing) {
        if (tileEntityChest.isInvalid()) {
            this.adjacentChestChecked = false;
        }
        else if (this.adjacentChestChecked) {
            switch (SwitchEnumFacing.field_177366_a[enumFacing.ordinal()]) {
                case 1: {
                    if (this.adjacentChestZNeg != tileEntityChest) {
                        this.adjacentChestChecked = false;
                        break;
                    }
                    break;
                }
                case 2: {
                    if (this.adjacentChestZPos != tileEntityChest) {
                        this.adjacentChestChecked = false;
                        break;
                    }
                    break;
                }
                case 3: {
                    if (this.adjacentChestXPos != tileEntityChest) {
                        this.adjacentChestChecked = false;
                        break;
                    }
                    break;
                }
                case 4: {
                    if (this.adjacentChestXNeg != tileEntityChest) {
                        this.adjacentChestChecked = false;
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public void checkForAdjacentChests() {
        if (!this.adjacentChestChecked) {
            this.adjacentChestChecked = true;
            this.adjacentChestXNeg = this.func_174911_a(EnumFacing.WEST);
            this.adjacentChestXPos = this.func_174911_a(EnumFacing.EAST);
            this.adjacentChestZNeg = this.func_174911_a(EnumFacing.NORTH);
            this.adjacentChestZPos = this.func_174911_a(EnumFacing.SOUTH);
        }
    }
    
    protected TileEntityChest func_174911_a(final EnumFacing enumFacing) {
        final BlockPos offset = this.pos.offset(enumFacing);
        if (this.func_174912_b(offset)) {
            final TileEntity tileEntity = this.worldObj.getTileEntity(offset);
            if (tileEntity instanceof TileEntityChest) {
                final TileEntityChest tileEntityChest = (TileEntityChest)tileEntity;
                tileEntityChest.func_174910_a(this, enumFacing.getOpposite());
                return tileEntityChest;
            }
        }
        return null;
    }
    
    private boolean func_174912_b(final BlockPos blockPos) {
        if (this.worldObj == null) {
            return false;
        }
        final Block block = this.worldObj.getBlockState(blockPos).getBlock();
        return block instanceof BlockChest && ((BlockChest)block).chestType == this.getChestType();
    }
    
    @Override
    public void update() {
        this.checkForAdjacentChests();
        final int x = this.pos.getX();
        final int y = this.pos.getY();
        final int z = this.pos.getZ();
        ++this.ticksSinceSync;
        if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + x + y + z) % 200 == 0) {
            this.numPlayersUsing = 0;
            final float n = 5.0f;
            for (final EntityPlayer entityPlayer : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(x - n, y - n, z - n, x + 1 + n, y + 1 + n, z + 1 + n))) {
                if (entityPlayer.openContainer instanceof ContainerChest) {
                    final IInventory lowerChestInventory = ((ContainerChest)entityPlayer.openContainer).getLowerChestInventory();
                    if (lowerChestInventory != this && (!(lowerChestInventory instanceof InventoryLargeChest) || !((InventoryLargeChest)lowerChestInventory).isPartOfLargeChest(this))) {
                        continue;
                    }
                    ++this.numPlayersUsing;
                }
            }
        }
        this.prevLidAngle = this.lidAngle;
        final float n2 = 0.1f;
        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0f && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
            double n3 = x + 0.5;
            double n4 = z + 0.5;
            if (this.adjacentChestZPos != null) {
                n4 += 0.5;
            }
            if (this.adjacentChestXPos != null) {
                n3 += 0.5;
            }
            this.worldObj.playSoundEffect(n3, y + 0.5, n4, "random.chestopen", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
        }
        if ((this.numPlayersUsing == 0 && this.lidAngle > 0.0f) || (this.numPlayersUsing > 0 && this.lidAngle < 1.0f)) {
            final float lidAngle = this.lidAngle;
            if (this.numPlayersUsing > 0) {
                this.lidAngle += n2;
            }
            else {
                this.lidAngle -= n2;
            }
            if (this.lidAngle > 1.0f) {
                this.lidAngle = 1.0f;
            }
            final float n5 = 0.5f;
            if (this.lidAngle < n5 && lidAngle >= n5 && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
                double n6 = x + 0.5;
                double n7 = z + 0.5;
                if (this.adjacentChestZPos != null) {
                    n7 += 0.5;
                }
                if (this.adjacentChestXPos != null) {
                    n6 += 0.5;
                }
                this.worldObj.playSoundEffect(n6, y + 0.5, n7, "random.chestclosed", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
            }
            if (this.lidAngle < 0.0f) {
                this.lidAngle = 0.0f;
            }
        }
    }
    
    @Override
    public boolean receiveClientEvent(final int n, final int numPlayersUsing) {
        if (n == 1) {
            this.numPlayersUsing = numPlayersUsing;
            return true;
        }
        return super.receiveClientEvent(n, numPlayersUsing);
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
        if (!entityPlayer.func_175149_v()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }
            ++this.numPlayersUsing;
            this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
            this.worldObj.notifyNeighborsOfStateChange(this.pos, this.getBlockType());
            this.worldObj.notifyNeighborsOfStateChange(this.pos.offsetDown(), this.getBlockType());
        }
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
        if (!entityPlayer.func_175149_v() && this.getBlockType() instanceof BlockChest) {
            --this.numPlayersUsing;
            this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
            this.worldObj.notifyNeighborsOfStateChange(this.pos, this.getBlockType());
            this.worldObj.notifyNeighborsOfStateChange(this.pos.offsetDown(), this.getBlockType());
        }
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return true;
    }
    
    @Override
    public void invalidate() {
        super.invalidate();
        this.updateContainingBlockInfo();
        this.checkForAdjacentChests();
    }
    
    public int getChestType() {
        if (this.cachedChestType == -1) {
            if (this.worldObj == null || !(this.getBlockType() instanceof BlockChest)) {
                return 0;
            }
            this.cachedChestType = ((BlockChest)this.getBlockType()).chestType;
        }
        return this.cachedChestType;
    }
    
    @Override
    public String getGuiID() {
        return "minecraft:chest";
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerChest(inventoryPlayer, this, entityPlayer);
    }
    
    @Override
    public int getField(final int n) {
        return 0;
    }
    
    @Override
    public void setField(final int n, final int n2) {
    }
    
    @Override
    public int getFieldCount() {
        return 0;
    }
    
    @Override
    public void clearInventory() {
        while (0 < this.chestContents.length) {
            this.chestContents[0] = null;
            int n = 0;
            ++n;
        }
    }
    
    static {
        __OBFID = "CL_00000346";
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_177366_a;
        private static final String __OBFID;
        private static final String[] lllllllllllIIlI;
        private static String[] lllllllllllIIll;
        
        static {
            lIllllIIllIlllll();
            lIllllIIllIllllI();
            __OBFID = SwitchEnumFacing.lllllllllllIIlI[0];
            field_177366_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_177366_a[EnumFacing.NORTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_177366_a[EnumFacing.SOUTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_177366_a[EnumFacing.EAST.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.field_177366_a[EnumFacing.WEST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
        
        private static void lIllllIIllIllllI() {
            (lllllllllllIIlI = new String[1])[0] = lIllllIIllIlllIl(SwitchEnumFacing.lllllllllllIIll[0], SwitchEnumFacing.lllllllllllIIll[1]);
            SwitchEnumFacing.lllllllllllIIll = null;
        }
        
        private static void lIllllIIllIlllll() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.lllllllllllIIll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIllllIIllIlllIl(String s, final String s2) {
            s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int n = 0;
            final char[] charArray2 = s.toCharArray();
            for (int length = charArray2.length, i = 0; i < length; ++i) {
                sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
                ++n;
            }
            return sb.toString();
        }
    }
}
