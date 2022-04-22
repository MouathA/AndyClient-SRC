package net.minecraft.tileentity;

import net.minecraft.server.gui.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.command.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class TileEntityHopper extends TileEntityLockable implements IHopper, IUpdatePlayerListBox
{
    private ItemStack[] inventory;
    private String customName;
    private int transferCooldown;
    private static final String __OBFID;
    
    public TileEntityHopper() {
        this.inventory = new ItemStack[5];
        this.transferCooldown = -1;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        final NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];
        if (nbtTagCompound.hasKey("CustomName", 8)) {
            this.customName = nbtTagCompound.getString("CustomName");
        }
        this.transferCooldown = nbtTagCompound.getInteger("TransferCooldown");
        while (0 < tagList.tagCount()) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(0);
            final byte byte1 = compoundTag.getByte("Slot");
            if (byte1 >= 0 && byte1 < this.inventory.length) {
                this.inventory[byte1] = ItemStack.loadItemStackFromNBT(compoundTag);
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        final NBTTagList list = new NBTTagList();
        while (0 < this.inventory.length) {
            if (this.inventory[0] != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte("Slot", (byte)0);
                this.inventory[0].writeToNBT(nbtTagCompound2);
                list.appendTag(nbtTagCompound2);
            }
            int n = 0;
            ++n;
        }
        nbtTagCompound.setTag("Items", list);
        nbtTagCompound.setInteger("TransferCooldown", this.transferCooldown);
        if (this != null) {
            nbtTagCompound.setString("CustomName", this.customName);
        }
    }
    
    @Override
    public void markDirty() {
        super.markDirty();
    }
    
    @Override
    public int getSizeInventory() {
        return this.inventory.length;
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return this.inventory[n];
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.inventory[n] == null) {
            return null;
        }
        if (this.inventory[n].stackSize <= n2) {
            final ItemStack itemStack = this.inventory[n];
            this.inventory[n] = null;
            return itemStack;
        }
        final ItemStack splitStack = this.inventory[n].splitStack(n2);
        if (this.inventory[n].stackSize == 0) {
            this.inventory[n] = null;
        }
        return splitStack;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int n) {
        if (this.inventory[n] != null) {
            final ItemStack itemStack = this.inventory[n];
            this.inventory[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.inventory[n] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
    }
    
    @Override
    public String getName() {
        return (this != null) ? this.customName : "container.hopper";
    }
    
    public void setCustomName(final String customName) {
        this.customName = customName;
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
        return true;
    }
    
    @Override
    public void update() {
        if (this.worldObj != null && !this.worldObj.isRemote) {
            --this.transferCooldown;
            if (this > 0) {
                this.setTransferCooldown(0);
                this.func_145887_i();
            }
        }
    }
    
    public boolean func_145887_i() {
        if (this.worldObj != null && !this.worldObj.isRemote) {
            if (this > 0 && BlockHopper.getActiveStateFromMetadata(this.getBlockMetadata())) {
                if (this != null) {
                    this.func_145883_k();
                }
                if (this != null) {
                    final boolean b = this == null;
                }
            }
            return false;
        }
        return false;
    }
    
    private boolean func_145883_k() {
        final IInventory func_145895_l = this.func_145895_l();
        if (func_145895_l == null) {
            return false;
        }
        final EnumFacing opposite = BlockHopper.func_176428_b(this.getBlockMetadata()).getOpposite();
        if (opposite != 0) {
            return false;
        }
        while (0 < this.getSizeInventory()) {
            if (this.getStackInSlot(0) != null) {
                final ItemStack copy = this.getStackInSlot(0).copy();
                final ItemStack func_174918_a = func_174918_a(func_145895_l, this.decrStackSize(0, 1), opposite);
                if (func_174918_a == null || func_174918_a.stackSize == 0) {
                    func_145895_l.markDirty();
                    return true;
                }
                this.setInventorySlotContents(0, copy);
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static boolean func_145898_a(final IInventory inventory, final EntityItem entityItem) {
        if (entityItem == null) {
            return false;
        }
        final ItemStack func_174918_a = func_174918_a(inventory, entityItem.getEntityItem().copy(), null);
        if (func_174918_a != null && func_174918_a.stackSize != 0) {
            entityItem.setEntityItemStack(func_174918_a);
        }
        else {
            entityItem.setDead();
        }
        return true;
    }
    
    public static ItemStack func_174918_a(final IInventory inventory, ItemStack itemStack, final EnumFacing enumFacing) {
        if (inventory instanceof ISidedInventory && enumFacing != null) {
            int n = 0;
            for (int[] slotsForFace = ((ISidedInventory)inventory).getSlotsForFace(enumFacing); 0 < slotsForFace.length && itemStack != null; itemStack = func_174916_c(inventory, itemStack, slotsForFace[0], enumFacing), ++n) {
                if (itemStack.stackSize <= 0) {
                    break;
                }
            }
        }
        else {
            while (0 < inventory.getSizeInventory() && itemStack != null && itemStack.stackSize > 0) {
                itemStack = func_174916_c(inventory, itemStack, 0, enumFacing);
                int n2 = 0;
                ++n2;
            }
        }
        if (itemStack != null && itemStack.stackSize == 0) {
            itemStack = null;
        }
        return itemStack;
    }
    
    private static ItemStack func_174916_c(final IInventory p0, final ItemStack p1, final int p2, final EnumFacing p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iload_2        
        //     2: invokeinterface net/minecraft/inventory/IInventory.getStackInSlot:(I)Lnet/minecraft/item/ItemStack;
        //     7: astore          4
        //     9: aload_0        
        //    10: aload_1        
        //    11: iload_2        
        //    12: aload_3        
        //    13: ifne            135
        //    16: aload           4
        //    18: ifnonnull       34
        //    21: aload_0        
        //    22: iload_2        
        //    23: aload_1        
        //    24: invokeinterface net/minecraft/inventory/IInventory.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //    29: aconst_null    
        //    30: astore_1       
        //    31: goto            98
        //    34: aload           4
        //    36: aload_1        
        //    37: if_acmpeq       98
        //    40: aload_1        
        //    41: invokevirtual   net/minecraft/item/ItemStack.getMaxStackSize:()I
        //    44: aload           4
        //    46: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    49: isub           
        //    50: istore          6
        //    52: aload_1        
        //    53: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    56: iload           6
        //    58: invokestatic    java/lang/Math.min:(II)I
        //    61: istore          7
        //    63: aload_1        
        //    64: dup            
        //    65: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    68: iload           7
        //    70: isub           
        //    71: putfield        net/minecraft/item/ItemStack.stackSize:I
        //    74: aload           4
        //    76: dup            
        //    77: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    80: iload           7
        //    82: iadd           
        //    83: putfield        net/minecraft/item/ItemStack.stackSize:I
        //    86: iload           7
        //    88: ifle            95
        //    91: iconst_1       
        //    92: goto            96
        //    95: iconst_0       
        //    96: istore          5
        //    98: aload_0        
        //    99: instanceof      Lnet/minecraft/tileentity/TileEntityHopper;
        //   102: ifeq            129
        //   105: aload_0        
        //   106: checkcast       Lnet/minecraft/tileentity/TileEntityHopper;
        //   109: astore          6
        //   111: aload           6
        //   113: if_icmpgt       123
        //   116: aload           6
        //   118: bipush          8
        //   120: invokevirtual   net/minecraft/tileentity/TileEntityHopper.setTransferCooldown:(I)V
        //   123: aload_0        
        //   124: invokeinterface net/minecraft/inventory/IInventory.markDirty:()V
        //   129: aload_0        
        //   130: invokeinterface net/minecraft/inventory/IInventory.markDirty:()V
        //   135: aload_1        
        //   136: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0129 (coming from #0124).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private IInventory func_145895_l() {
        final EnumFacing func_176428_b = BlockHopper.func_176428_b(this.getBlockMetadata());
        return func_145893_b(this.getWorld(), this.pos.getX() + func_176428_b.getFrontOffsetX(), this.pos.getY() + func_176428_b.getFrontOffsetY(), this.pos.getZ() + func_176428_b.getFrontOffsetZ());
    }
    
    public static IInventory func_145884_b(final IHopper hopper) {
        return func_145893_b(hopper.getWorld(), hopper.getXPos(), hopper.getYPos() + 1.0, hopper.getZPos());
    }
    
    public static EntityItem func_145897_a(final World world, final double n, final double n2, final double n3) {
        final List func_175647_a = world.func_175647_a(EntityItem.class, new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 1.0), IEntitySelector.selectAnything);
        return (func_175647_a.size() > 0) ? func_175647_a.get(0) : null;
    }
    
    public static IInventory func_145893_b(final World world, final double n, final double n2, final double n3) {
        IInventory lockableContainer = null;
        final int floor_double = MathHelper.floor_double(n);
        final int floor_double2 = MathHelper.floor_double(n2);
        final int floor_double3 = MathHelper.floor_double(n3);
        final BlockPos blockPos = new BlockPos(floor_double, floor_double2, floor_double3);
        final TileEntity tileEntity = world.getTileEntity(new BlockPos(floor_double, floor_double2, floor_double3));
        if (tileEntity instanceof IInventory) {
            lockableContainer = (IInventory)tileEntity;
            if (lockableContainer instanceof TileEntityChest) {
                final Block block = world.getBlockState(new BlockPos(floor_double, floor_double2, floor_double3)).getBlock();
                if (block instanceof BlockChest) {
                    lockableContainer = ((BlockChest)block).getLockableContainer(world, blockPos);
                }
            }
        }
        if (lockableContainer == null) {
            final List func_175674_a = world.func_175674_a(null, new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 1.0), IEntitySelector.selectInventories);
            if (func_175674_a.size() > 0) {
                lockableContainer = func_175674_a.get(world.rand.nextInt(func_175674_a.size()));
            }
        }
        return lockableContainer;
    }
    
    @Override
    public double getXPos() {
        return this.pos.getX();
    }
    
    @Override
    public double getYPos() {
        return this.pos.getY();
    }
    
    @Override
    public double getZPos() {
        return this.pos.getZ();
    }
    
    public void setTransferCooldown(final int transferCooldown) {
        this.transferCooldown = transferCooldown;
    }
    
    @Override
    public String getGuiID() {
        return "minecraft:hopper";
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerHopper(inventoryPlayer, this, entityPlayer);
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
        while (0 < this.inventory.length) {
            this.inventory[0] = null;
            int n = 0;
            ++n;
        }
    }
    
    static {
        __OBFID = "CL_00000359";
    }
}
