package net.minecraft.tileentity;

import net.minecraft.server.gui.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class TileEntityBrewingStand extends TileEntityLockable implements IUpdatePlayerListBox, ISidedInventory
{
    private static final int[] inputSlots;
    private static final int[] outputSlots;
    private ItemStack[] brewingItemStacks;
    private int brewTime;
    private boolean[] filledSlots;
    private Item ingredientID;
    private String field_145942_n;
    private static final String __OBFID;
    private static final String[] lIlIllIllIllllIl;
    private static String[] lIlIllIlllIIIIlI;
    
    static {
        lllllllllIllIlll();
        lllllllllIllIllI();
        __OBFID = TileEntityBrewingStand.lIlIllIllIllllIl[0];
        inputSlots = new int[] { 3 };
        outputSlots = new int[] { 0, 1, 2 };
    }
    
    public TileEntityBrewingStand() {
        this.brewingItemStacks = new ItemStack[4];
    }
    
    @Override
    public String getName() {
        return this.hasCustomName() ? this.field_145942_n : TileEntityBrewingStand.lIlIllIllIllllIl[1];
    }
    
    @Override
    public boolean hasCustomName() {
        return this.field_145942_n != null && this.field_145942_n.length() > 0;
    }
    
    public void func_145937_a(final String field_145942_n) {
        this.field_145942_n = field_145942_n;
    }
    
    @Override
    public int getSizeInventory() {
        return this.brewingItemStacks.length;
    }
    
    @Override
    public void update() {
        if (this.brewTime > 0) {
            --this.brewTime;
            if (this.brewTime == 0) {
                this.brewPotions();
                this.markDirty();
            }
            else if (!this.canBrew()) {
                this.brewTime = 0;
                this.markDirty();
            }
            else if (this.ingredientID != this.brewingItemStacks[3].getItem()) {
                this.brewTime = 0;
                this.markDirty();
            }
        }
        else if (this.canBrew()) {
            this.brewTime = 400;
            this.ingredientID = this.brewingItemStacks[3].getItem();
        }
        if (!this.worldObj.isRemote) {
            final boolean[] func_174902_m = this.func_174902_m();
            if (!Arrays.equals(func_174902_m, this.filledSlots)) {
                this.filledSlots = func_174902_m;
                IBlockState blockState = this.worldObj.getBlockState(this.getPos());
                if (!(blockState.getBlock() instanceof BlockBrewingStand)) {
                    return;
                }
                for (int i = 0; i < BlockBrewingStand.BOTTLE_PROPS.length; ++i) {
                    blockState = blockState.withProperty(BlockBrewingStand.BOTTLE_PROPS[i], func_174902_m[i]);
                }
                this.worldObj.setBlockState(this.pos, blockState, 2);
            }
        }
    }
    
    private boolean canBrew() {
        if (this.brewingItemStacks[3] == null || this.brewingItemStacks[3].stackSize <= 0) {
            return false;
        }
        final ItemStack itemStack = this.brewingItemStacks[3];
        if (!itemStack.getItem().isPotionIngredient(itemStack)) {
            return false;
        }
        boolean b = false;
        for (int i = 0; i < 3; ++i) {
            if (this.brewingItemStacks[i] != null && this.brewingItemStacks[i].getItem() == Items.potionitem) {
                final int metadata = this.brewingItemStacks[i].getMetadata();
                final int func_145936_c = this.func_145936_c(metadata, itemStack);
                if (!ItemPotion.isSplash(metadata) && ItemPotion.isSplash(func_145936_c)) {
                    b = true;
                    break;
                }
                final List effects = Items.potionitem.getEffects(metadata);
                final List effects2 = Items.potionitem.getEffects(func_145936_c);
                if ((metadata <= 0 || effects != effects2) && (effects == null || (!effects.equals(effects2) && effects2 != null)) && metadata != func_145936_c) {
                    b = true;
                    break;
                }
            }
        }
        return b;
    }
    
    private void brewPotions() {
        if (this.canBrew()) {
            final ItemStack itemStack = this.brewingItemStacks[3];
            for (int i = 0; i < 3; ++i) {
                if (this.brewingItemStacks[i] != null && this.brewingItemStacks[i].getItem() == Items.potionitem) {
                    final int metadata = this.brewingItemStacks[i].getMetadata();
                    final int func_145936_c = this.func_145936_c(metadata, itemStack);
                    final List effects = Items.potionitem.getEffects(metadata);
                    final List effects2 = Items.potionitem.getEffects(func_145936_c);
                    if ((metadata <= 0 || effects != effects2) && (effects == null || (!effects.equals(effects2) && effects2 != null))) {
                        if (metadata != func_145936_c) {
                            this.brewingItemStacks[i].setItemDamage(func_145936_c);
                        }
                    }
                    else if (!ItemPotion.isSplash(metadata) && ItemPotion.isSplash(func_145936_c)) {
                        this.brewingItemStacks[i].setItemDamage(func_145936_c);
                    }
                }
            }
            if (itemStack.getItem().hasContainerItem()) {
                this.brewingItemStacks[3] = new ItemStack(itemStack.getItem().getContainerItem());
            }
            else {
                final ItemStack itemStack2 = this.brewingItemStacks[3];
                --itemStack2.stackSize;
                if (this.brewingItemStacks[3].stackSize <= 0) {
                    this.brewingItemStacks[3] = null;
                }
            }
        }
    }
    
    private int func_145936_c(final int n, final ItemStack itemStack) {
        return (itemStack == null) ? n : (itemStack.getItem().isPotionIngredient(itemStack) ? PotionHelper.applyIngredient(n, itemStack.getItem().getPotionEffect(itemStack)) : n);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        final NBTTagList tagList = nbtTagCompound.getTagList(TileEntityBrewingStand.lIlIllIllIllllIl[2], 10);
        this.brewingItemStacks = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); ++i) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(i);
            final byte byte1 = compoundTag.getByte(TileEntityBrewingStand.lIlIllIllIllllIl[3]);
            if (byte1 >= 0 && byte1 < this.brewingItemStacks.length) {
                this.brewingItemStacks[byte1] = ItemStack.loadItemStackFromNBT(compoundTag);
            }
        }
        this.brewTime = nbtTagCompound.getShort(TileEntityBrewingStand.lIlIllIllIllllIl[4]);
        if (nbtTagCompound.hasKey(TileEntityBrewingStand.lIlIllIllIllllIl[5], 8)) {
            this.field_145942_n = nbtTagCompound.getString(TileEntityBrewingStand.lIlIllIllIllllIl[6]);
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setShort(TileEntityBrewingStand.lIlIllIllIllllIl[7], (short)this.brewTime);
        final NBTTagList list = new NBTTagList();
        for (int i = 0; i < this.brewingItemStacks.length; ++i) {
            if (this.brewingItemStacks[i] != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte(TileEntityBrewingStand.lIlIllIllIllllIl[8], (byte)i);
                this.brewingItemStacks[i].writeToNBT(nbtTagCompound2);
                list.appendTag(nbtTagCompound2);
            }
        }
        nbtTagCompound.setTag(TileEntityBrewingStand.lIlIllIllIllllIl[9], list);
        if (this.hasCustomName()) {
            nbtTagCompound.setString(TileEntityBrewingStand.lIlIllIllIllllIl[10], this.field_145942_n);
        }
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return (n >= 0 && n < this.brewingItemStacks.length) ? this.brewingItemStacks[n] : null;
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (n >= 0 && n < this.brewingItemStacks.length) {
            final ItemStack itemStack = this.brewingItemStacks[n];
            this.brewingItemStacks[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int n) {
        if (n >= 0 && n < this.brewingItemStacks.length) {
            final ItemStack itemStack = this.brewingItemStacks[n];
            this.brewingItemStacks[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        if (n >= 0 && n < this.brewingItemStacks.length) {
            this.brewingItemStacks[n] = itemStack;
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
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return (n == 3) ? itemStack.getItem().isPotionIngredient(itemStack) : (itemStack.getItem() == Items.potionitem || itemStack.getItem() == Items.glass_bottle);
    }
    
    public boolean[] func_174902_m() {
        final boolean[] array = new boolean[3];
        for (int i = 0; i < 3; ++i) {
            if (this.brewingItemStacks[i] != null) {
                array[i] = true;
            }
        }
        return array;
    }
    
    @Override
    public int[] getSlotsForFace(final EnumFacing enumFacing) {
        return (enumFacing == EnumFacing.UP) ? TileEntityBrewingStand.inputSlots : TileEntityBrewingStand.outputSlots;
    }
    
    @Override
    public boolean canInsertItem(final int n, final ItemStack itemStack, final EnumFacing enumFacing) {
        return this.isItemValidForSlot(n, itemStack);
    }
    
    @Override
    public boolean canExtractItem(final int n, final ItemStack itemStack, final EnumFacing enumFacing) {
        return true;
    }
    
    @Override
    public String getGuiID() {
        return TileEntityBrewingStand.lIlIllIllIllllIl[11];
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerBrewingStand(inventoryPlayer, this);
    }
    
    @Override
    public int getField(final int n) {
        switch (n) {
            case 0: {
                return this.brewTime;
            }
            default: {
                return 0;
            }
        }
    }
    
    @Override
    public void setField(final int n, final int brewTime) {
        switch (n) {
            case 0: {
                this.brewTime = brewTime;
                break;
            }
        }
    }
    
    @Override
    public int getFieldCount() {
        return 1;
    }
    
    @Override
    public void clearInventory() {
        for (int i = 0; i < this.brewingItemStacks.length; ++i) {
            this.brewingItemStacks[i] = null;
        }
    }
    
    private static void lllllllllIllIllI() {
        (lIlIllIllIllllIl = new String[12])[0] = lllllllllIlIllIl(TileEntityBrewingStand.lIlIllIlllIIIIlI[0], TileEntityBrewingStand.lIlIllIlllIIIIlI[1]);
        TileEntityBrewingStand.lIlIllIllIllllIl[1] = lllllllllIlIlllI(TileEntityBrewingStand.lIlIllIlllIIIIlI[2], TileEntityBrewingStand.lIlIllIlllIIIIlI[3]);
        TileEntityBrewingStand.lIlIllIllIllllIl[2] = lllllllllIlIlllI(TileEntityBrewingStand.lIlIllIlllIIIIlI[4], TileEntityBrewingStand.lIlIllIlllIIIIlI[5]);
        TileEntityBrewingStand.lIlIllIllIllllIl[3] = lllllllllIlIllll(TileEntityBrewingStand.lIlIllIlllIIIIlI[6], TileEntityBrewingStand.lIlIllIlllIIIIlI[7]);
        TileEntityBrewingStand.lIlIllIllIllllIl[4] = lllllllllIlIllll(TileEntityBrewingStand.lIlIllIlllIIIIlI[8], TileEntityBrewingStand.lIlIllIlllIIIIlI[9]);
        TileEntityBrewingStand.lIlIllIllIllllIl[5] = lllllllllIllIIll(TileEntityBrewingStand.lIlIllIlllIIIIlI[10], TileEntityBrewingStand.lIlIllIlllIIIIlI[11]);
        TileEntityBrewingStand.lIlIllIllIllllIl[6] = lllllllllIlIlllI(TileEntityBrewingStand.lIlIllIlllIIIIlI[12], TileEntityBrewingStand.lIlIllIlllIIIIlI[13]);
        TileEntityBrewingStand.lIlIllIllIllllIl[7] = lllllllllIllIIll(TileEntityBrewingStand.lIlIllIlllIIIIlI[14], TileEntityBrewingStand.lIlIllIlllIIIIlI[15]);
        TileEntityBrewingStand.lIlIllIllIllllIl[8] = lllllllllIlIlllI(TileEntityBrewingStand.lIlIllIlllIIIIlI[16], TileEntityBrewingStand.lIlIllIlllIIIIlI[17]);
        TileEntityBrewingStand.lIlIllIllIllllIl[9] = lllllllllIlIllIl(TileEntityBrewingStand.lIlIllIlllIIIIlI[18], TileEntityBrewingStand.lIlIllIlllIIIIlI[19]);
        TileEntityBrewingStand.lIlIllIllIllllIl[10] = lllllllllIlIllIl(TileEntityBrewingStand.lIlIllIlllIIIIlI[20], TileEntityBrewingStand.lIlIllIlllIIIIlI[21]);
        TileEntityBrewingStand.lIlIllIllIllllIl[11] = lllllllllIlIllll(TileEntityBrewingStand.lIlIllIlllIIIIlI[22], TileEntityBrewingStand.lIlIllIlllIIIIlI[23]);
        TileEntityBrewingStand.lIlIllIlllIIIIlI = null;
    }
    
    private static void lllllllllIllIlll() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        TileEntityBrewingStand.lIlIllIlllIIIIlI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lllllllllIlIllll(final String s, final String s2) {
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
    
    private static String lllllllllIlIllIl(String s, final String s2) {
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
    
    private static String lllllllllIllIIll(final String s, final String s2) {
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
    
    private static String lllllllllIlIlllI(final String s, final String s2) {
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
}
