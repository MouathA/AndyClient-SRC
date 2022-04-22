package net.minecraft.tileentity;

import net.minecraft.server.gui.*;
import net.minecraft.nbt.*;
import net.minecraft.item.crafting.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class TileEntityFurnace extends TileEntityLockable implements IUpdatePlayerListBox, ISidedInventory
{
    private static final int[] slotsTop;
    private static final int[] slotsBottom;
    private static final int[] slotsSides;
    private ItemStack[] furnaceItemStacks;
    private int furnaceBurnTime;
    private int currentItemBurnTime;
    private int field_174906_k;
    private int field_174905_l;
    private String furnaceCustomName;
    private static final String __OBFID;
    private static final String[] lIIIlIlIIlllIIII;
    private static String[] lIIIlIlIIlllIllI;
    
    static {
        llIIllIIIlIlIIIl();
        llIIllIIIlIlIIII();
        __OBFID = TileEntityFurnace.lIIIlIlIIlllIIII[0];
        slotsTop = new int[1];
        slotsBottom = new int[] { 2, 1 };
        slotsSides = new int[] { 1 };
    }
    
    public TileEntityFurnace() {
        this.furnaceItemStacks = new ItemStack[3];
    }
    
    @Override
    public int getSizeInventory() {
        return this.furnaceItemStacks.length;
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return this.furnaceItemStacks[n];
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.furnaceItemStacks[n] == null) {
            return null;
        }
        if (this.furnaceItemStacks[n].stackSize <= n2) {
            final ItemStack itemStack = this.furnaceItemStacks[n];
            this.furnaceItemStacks[n] = null;
            return itemStack;
        }
        final ItemStack splitStack = this.furnaceItemStacks[n].splitStack(n2);
        if (this.furnaceItemStacks[n].stackSize == 0) {
            this.furnaceItemStacks[n] = null;
        }
        return splitStack;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int n) {
        if (this.furnaceItemStacks[n] != null) {
            final ItemStack itemStack = this.furnaceItemStacks[n];
            this.furnaceItemStacks[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        final boolean b = itemStack != null && itemStack.isItemEqual(this.furnaceItemStacks[n]) && ItemStack.areItemStackTagsEqual(itemStack, this.furnaceItemStacks[n]);
        this.furnaceItemStacks[n] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        if (n == 0 && !b) {
            this.field_174905_l = this.func_174904_a(itemStack);
            this.field_174906_k = 0;
            this.markDirty();
        }
    }
    
    @Override
    public String getName() {
        return this.hasCustomName() ? this.furnaceCustomName : TileEntityFurnace.lIIIlIlIIlllIIII[1];
    }
    
    @Override
    public boolean hasCustomName() {
        return this.furnaceCustomName != null && this.furnaceCustomName.length() > 0;
    }
    
    public void setCustomInventoryName(final String furnaceCustomName) {
        this.furnaceCustomName = furnaceCustomName;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        final NBTTagList tagList = nbtTagCompound.getTagList(TileEntityFurnace.lIIIlIlIIlllIIII[2], 10);
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); ++i) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(i);
            final byte byte1 = compoundTag.getByte(TileEntityFurnace.lIIIlIlIIlllIIII[3]);
            if (byte1 >= 0 && byte1 < this.furnaceItemStacks.length) {
                this.furnaceItemStacks[byte1] = ItemStack.loadItemStackFromNBT(compoundTag);
            }
        }
        this.furnaceBurnTime = nbtTagCompound.getShort(TileEntityFurnace.lIIIlIlIIlllIIII[4]);
        this.field_174906_k = nbtTagCompound.getShort(TileEntityFurnace.lIIIlIlIIlllIIII[5]);
        this.field_174905_l = nbtTagCompound.getShort(TileEntityFurnace.lIIIlIlIIlllIIII[6]);
        this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
        if (nbtTagCompound.hasKey(TileEntityFurnace.lIIIlIlIIlllIIII[7], 8)) {
            this.furnaceCustomName = nbtTagCompound.getString(TileEntityFurnace.lIIIlIlIIlllIIII[8]);
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setShort(TileEntityFurnace.lIIIlIlIIlllIIII[9], (short)this.furnaceBurnTime);
        nbtTagCompound.setShort(TileEntityFurnace.lIIIlIlIIlllIIII[10], (short)this.field_174906_k);
        nbtTagCompound.setShort(TileEntityFurnace.lIIIlIlIIlllIIII[11], (short)this.field_174905_l);
        final NBTTagList list = new NBTTagList();
        for (int i = 0; i < this.furnaceItemStacks.length; ++i) {
            if (this.furnaceItemStacks[i] != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte(TileEntityFurnace.lIIIlIlIIlllIIII[12], (byte)i);
                this.furnaceItemStacks[i].writeToNBT(nbtTagCompound2);
                list.appendTag(nbtTagCompound2);
            }
        }
        nbtTagCompound.setTag(TileEntityFurnace.lIIIlIlIIlllIIII[13], list);
        if (this.hasCustomName()) {
            nbtTagCompound.setString(TileEntityFurnace.lIIIlIlIIlllIIII[14], this.furnaceCustomName);
        }
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }
    
    public static boolean func_174903_a(final IInventory inventory) {
        return inventory.getField(0) > 0;
    }
    
    @Override
    public void update() {
        final boolean burning = this.isBurning();
        boolean b = false;
        if (this.isBurning()) {
            --this.furnaceBurnTime;
        }
        if (!this.worldObj.isRemote) {
            if (!this.isBurning() && (this.furnaceItemStacks[1] == null || this.furnaceItemStacks[0] == null)) {
                if (!this.isBurning() && this.field_174906_k > 0) {
                    this.field_174906_k = MathHelper.clamp_int(this.field_174906_k - 2, 0, this.field_174905_l);
                }
            }
            else {
                if (!this.isBurning() && this.canSmelt()) {
                    final int itemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
                    this.furnaceBurnTime = itemBurnTime;
                    this.currentItemBurnTime = itemBurnTime;
                    if (this.isBurning()) {
                        b = true;
                        if (this.furnaceItemStacks[1] != null) {
                            final ItemStack itemStack = this.furnaceItemStacks[1];
                            --itemStack.stackSize;
                            if (this.furnaceItemStacks[1].stackSize == 0) {
                                final Item containerItem = this.furnaceItemStacks[1].getItem().getContainerItem();
                                this.furnaceItemStacks[1] = ((containerItem != null) ? new ItemStack(containerItem) : null);
                            }
                        }
                    }
                }
                if (this.isBurning() && this.canSmelt()) {
                    ++this.field_174906_k;
                    if (this.field_174906_k == this.field_174905_l) {
                        this.field_174906_k = 0;
                        this.field_174905_l = this.func_174904_a(this.furnaceItemStacks[0]);
                        this.smeltItem();
                        b = true;
                    }
                }
                else {
                    this.field_174906_k = 0;
                }
            }
            if (burning != this.isBurning()) {
                b = true;
                BlockFurnace.func_176446_a(this.isBurning(), this.worldObj, this.pos);
            }
        }
        if (b) {
            this.markDirty();
        }
    }
    
    public int func_174904_a(final ItemStack itemStack) {
        return 200;
    }
    
    private boolean canSmelt() {
        if (this.furnaceItemStacks[0] == null) {
            return false;
        }
        final ItemStack smeltingResult = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
        return smeltingResult != null && (this.furnaceItemStacks[2] == null || (this.furnaceItemStacks[2].isItemEqual(smeltingResult) && ((this.furnaceItemStacks[2].stackSize < this.getInventoryStackLimit() && this.furnaceItemStacks[2].stackSize < this.furnaceItemStacks[2].getMaxStackSize()) || this.furnaceItemStacks[2].stackSize < smeltingResult.getMaxStackSize())));
    }
    
    public void smeltItem() {
        if (this.canSmelt()) {
            final ItemStack smeltingResult = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
            if (this.furnaceItemStacks[2] == null) {
                this.furnaceItemStacks[2] = smeltingResult.copy();
            }
            else if (this.furnaceItemStacks[2].getItem() == smeltingResult.getItem()) {
                final ItemStack itemStack = this.furnaceItemStacks[2];
                ++itemStack.stackSize;
            }
            if (this.furnaceItemStacks[0].getItem() == Item.getItemFromBlock(Blocks.sponge) && this.furnaceItemStacks[0].getMetadata() == 1 && this.furnaceItemStacks[1] != null && this.furnaceItemStacks[1].getItem() == Items.bucket) {
                this.furnaceItemStacks[1] = new ItemStack(Items.water_bucket);
            }
            final ItemStack itemStack2 = this.furnaceItemStacks[0];
            --itemStack2.stackSize;
            if (this.furnaceItemStacks[0].stackSize <= 0) {
                this.furnaceItemStacks[0] = null;
            }
        }
    }
    
    public static int getItemBurnTime(final ItemStack itemStack) {
        if (itemStack == null) {
            return 0;
        }
        final Item item = itemStack.getItem();
        if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air) {
            final Block blockFromItem = Block.getBlockFromItem(item);
            if (blockFromItem == Blocks.wooden_slab) {
                return 150;
            }
            if (blockFromItem.getMaterial() == Material.wood) {
                return 300;
            }
            if (blockFromItem == Blocks.coal_block) {
                return 16000;
            }
        }
        return (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals(TileEntityFurnace.lIIIlIlIIlllIIII[15])) ? 200 : ((item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals(TileEntityFurnace.lIIIlIlIIlllIIII[16])) ? 200 : ((item instanceof ItemHoe && ((ItemHoe)item).getMaterialName().equals(TileEntityFurnace.lIIIlIlIIlllIIII[17])) ? 200 : ((item == Items.stick) ? 100 : ((item == Items.coal) ? 1600 : ((item == Items.lava_bucket) ? 20000 : ((item == Item.getItemFromBlock(Blocks.sapling)) ? 100 : ((item == Items.blaze_rod) ? 2400 : 0)))))));
    }
    
    public static boolean isItemFuel(final ItemStack itemStack) {
        return getItemBurnTime(itemStack) > 0;
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
        return n != 2 && (n != 1 || isItemFuel(itemStack) || SlotFurnaceFuel.func_178173_c_(itemStack));
    }
    
    @Override
    public int[] getSlotsForFace(final EnumFacing enumFacing) {
        return (enumFacing == EnumFacing.DOWN) ? TileEntityFurnace.slotsBottom : ((enumFacing == EnumFacing.UP) ? TileEntityFurnace.slotsTop : TileEntityFurnace.slotsSides);
    }
    
    @Override
    public boolean canInsertItem(final int n, final ItemStack itemStack, final EnumFacing enumFacing) {
        return this.isItemValidForSlot(n, itemStack);
    }
    
    @Override
    public boolean canExtractItem(final int n, final ItemStack itemStack, final EnumFacing enumFacing) {
        if (enumFacing == EnumFacing.DOWN && n == 1) {
            final Item item = itemStack.getItem();
            if (item != Items.water_bucket && item != Items.bucket) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String getGuiID() {
        return TileEntityFurnace.lIIIlIlIIlllIIII[18];
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerFurnace(inventoryPlayer, this);
    }
    
    @Override
    public int getField(final int n) {
        switch (n) {
            case 0: {
                return this.furnaceBurnTime;
            }
            case 1: {
                return this.currentItemBurnTime;
            }
            case 2: {
                return this.field_174906_k;
            }
            case 3: {
                return this.field_174905_l;
            }
            default: {
                return 0;
            }
        }
    }
    
    @Override
    public void setField(final int n, final int n2) {
        switch (n) {
            case 0: {
                this.furnaceBurnTime = n2;
                break;
            }
            case 1: {
                this.currentItemBurnTime = n2;
                break;
            }
            case 2: {
                this.field_174906_k = n2;
                break;
            }
            case 3: {
                this.field_174905_l = n2;
                break;
            }
        }
    }
    
    @Override
    public int getFieldCount() {
        return 4;
    }
    
    @Override
    public void clearInventory() {
        for (int i = 0; i < this.furnaceItemStacks.length; ++i) {
            this.furnaceItemStacks[i] = null;
        }
    }
    
    private static void llIIllIIIlIlIIII() {
        (lIIIlIlIIlllIIII = new String[19])[0] = llIIllIIIlIIIIlI(TileEntityFurnace.lIIIlIlIIlllIllI[0], TileEntityFurnace.lIIIlIlIIlllIllI[1]);
        TileEntityFurnace.lIIIlIlIIlllIIII[1] = llIIllIIIlIIIIll(TileEntityFurnace.lIIIlIlIIlllIllI[2], TileEntityFurnace.lIIIlIlIIlllIllI[3]);
        TileEntityFurnace.lIIIlIlIIlllIIII[2] = llIIllIIIlIIIIll(TileEntityFurnace.lIIIlIlIIlllIllI[4], TileEntityFurnace.lIIIlIlIIlllIllI[5]);
        TileEntityFurnace.lIIIlIlIIlllIIII[3] = llIIllIIIlIIIIll(TileEntityFurnace.lIIIlIlIIlllIllI[6], TileEntityFurnace.lIIIlIlIIlllIllI[7]);
        TileEntityFurnace.lIIIlIlIIlllIIII[4] = llIIllIIIlIIIIll(TileEntityFurnace.lIIIlIlIIlllIllI[8], TileEntityFurnace.lIIIlIlIIlllIllI[9]);
        TileEntityFurnace.lIIIlIlIIlllIIII[5] = llIIllIIIlIIIIll(TileEntityFurnace.lIIIlIlIIlllIllI[10], TileEntityFurnace.lIIIlIlIIlllIllI[11]);
        TileEntityFurnace.lIIIlIlIIlllIIII[6] = llIIllIIIlIIIIll(TileEntityFurnace.lIIIlIlIIlllIllI[12], TileEntityFurnace.lIIIlIlIIlllIllI[13]);
        TileEntityFurnace.lIIIlIlIIlllIIII[7] = llIIllIIIlIIIlIl(TileEntityFurnace.lIIIlIlIIlllIllI[14], TileEntityFurnace.lIIIlIlIIlllIllI[15]);
        TileEntityFurnace.lIIIlIlIIlllIIII[8] = llIIllIIIlIIIlIl(TileEntityFurnace.lIIIlIlIIlllIllI[16], TileEntityFurnace.lIIIlIlIIlllIllI[17]);
        TileEntityFurnace.lIIIlIlIIlllIIII[9] = llIIllIIIlIIIlIl(TileEntityFurnace.lIIIlIlIIlllIllI[18], TileEntityFurnace.lIIIlIlIIlllIllI[19]);
        TileEntityFurnace.lIIIlIlIIlllIIII[10] = llIIllIIIlIIIlIl(TileEntityFurnace.lIIIlIlIIlllIllI[20], TileEntityFurnace.lIIIlIlIIlllIllI[21]);
        TileEntityFurnace.lIIIlIlIIlllIIII[11] = llIIllIIIlIIlIlI(TileEntityFurnace.lIIIlIlIIlllIllI[22], TileEntityFurnace.lIIIlIlIIlllIllI[23]);
        TileEntityFurnace.lIIIlIlIIlllIIII[12] = llIIllIIIlIIIIll(TileEntityFurnace.lIIIlIlIIlllIllI[24], TileEntityFurnace.lIIIlIlIIlllIllI[25]);
        TileEntityFurnace.lIIIlIlIIlllIIII[13] = llIIllIIIlIIIlIl(TileEntityFurnace.lIIIlIlIIlllIllI[26], TileEntityFurnace.lIIIlIlIIlllIllI[27]);
        TileEntityFurnace.lIIIlIlIIlllIIII[14] = llIIllIIIlIIIIll(TileEntityFurnace.lIIIlIlIIlllIllI[28], TileEntityFurnace.lIIIlIlIIlllIllI[29]);
        TileEntityFurnace.lIIIlIlIIlllIIII[15] = llIIllIIIlIIIIlI(TileEntityFurnace.lIIIlIlIIlllIllI[30], TileEntityFurnace.lIIIlIlIIlllIllI[31]);
        TileEntityFurnace.lIIIlIlIIlllIIII[16] = llIIllIIIlIIlIlI(TileEntityFurnace.lIIIlIlIIlllIllI[32], TileEntityFurnace.lIIIlIlIIlllIllI[33]);
        TileEntityFurnace.lIIIlIlIIlllIIII[17] = llIIllIIIlIIIIll(TileEntityFurnace.lIIIlIlIIlllIllI[34], TileEntityFurnace.lIIIlIlIIlllIllI[35]);
        TileEntityFurnace.lIIIlIlIIlllIIII[18] = llIIllIIIlIIIlIl(TileEntityFurnace.lIIIlIlIIlllIllI[36], TileEntityFurnace.lIIIlIlIIlllIllI[37]);
        TileEntityFurnace.lIIIlIlIIlllIllI = null;
    }
    
    private static void llIIllIIIlIlIIIl() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        TileEntityFurnace.lIIIlIlIIlllIllI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String llIIllIIIlIIIlIl(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher instance = Cipher.getInstance("Blowfish");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String llIIllIIIlIIlIlI(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
            final Cipher instance = Cipher.getInstance("DES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String llIIllIIIlIIIIll(String s, final String s2) {
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
    
    private static String llIIllIIIlIIIIlI(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
            final Cipher instance = Cipher.getInstance("AES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
