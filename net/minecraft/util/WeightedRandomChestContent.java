package net.minecraft.util;

import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import com.google.common.collect.*;
import java.util.*;

public class WeightedRandomChestContent extends WeightedRandom.Item
{
    private ItemStack theItemId;
    private int theMinimumChanceToGenerateItem;
    private int theMaximumChanceToGenerateItem;
    private static final String __OBFID;
    
    public WeightedRandomChestContent(final net.minecraft.item.Item item, final int n, final int theMinimumChanceToGenerateItem, final int theMaximumChanceToGenerateItem, final int n2) {
        super(n2);
        this.theItemId = new ItemStack(item, 1, n);
        this.theMinimumChanceToGenerateItem = theMinimumChanceToGenerateItem;
        this.theMaximumChanceToGenerateItem = theMaximumChanceToGenerateItem;
    }
    
    public WeightedRandomChestContent(final ItemStack theItemId, final int theMinimumChanceToGenerateItem, final int theMaximumChanceToGenerateItem, final int n) {
        super(n);
        this.theItemId = theItemId;
        this.theMinimumChanceToGenerateItem = theMinimumChanceToGenerateItem;
        this.theMaximumChanceToGenerateItem = theMaximumChanceToGenerateItem;
    }
    
    public static void generateChestContents(final Random p0, final List p1, final IInventory p2, final int p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: aload_0        
        //     4: aload_1        
        //     5: invokestatic    net/minecraft/util/WeightedRandom.getRandomItem:(Ljava/util/Random;Ljava/util/Collection;)Lnet/minecraft/util/WeightedRandom$Item;
        //     8: checkcast       Lnet/minecraft/util/WeightedRandomChestContent;
        //    11: astore          5
        //    13: aload           5
        //    15: getfield        net/minecraft/util/WeightedRandomChestContent.theMinimumChanceToGenerateItem:I
        //    18: aload_0        
        //    19: aload           5
        //    21: getfield        net/minecraft/util/WeightedRandomChestContent.theMaximumChanceToGenerateItem:I
        //    24: aload           5
        //    26: getfield        net/minecraft/util/WeightedRandomChestContent.theMinimumChanceToGenerateItem:I
        //    29: isub           
        //    30: iconst_1       
        //    31: iadd           
        //    32: invokevirtual   java/util/Random.nextInt:(I)I
        //    35: iadd           
        //    36: istore          6
        //    38: aload           5
        //    40: getfield        net/minecraft/util/WeightedRandomChestContent.theItemId:Lnet/minecraft/item/ItemStack;
        //    43: invokevirtual   net/minecraft/item/ItemStack.getMaxStackSize:()I
        //    46: iload           6
        //    48: if_icmplt       89
        //    51: aload           5
        //    53: getfield        net/minecraft/util/WeightedRandomChestContent.theItemId:Lnet/minecraft/item/ItemStack;
        //    56: invokevirtual   net/minecraft/item/ItemStack.copy:()Lnet/minecraft/item/ItemStack;
        //    59: astore          7
        //    61: aload           7
        //    63: iload           6
        //    65: putfield        net/minecraft/item/ItemStack.stackSize:I
        //    68: aload_2        
        //    69: aload_0        
        //    70: aload_2        
        //    71: invokeinterface net/minecraft/inventory/IInventory.getSizeInventory:()I
        //    76: invokevirtual   java/util/Random.nextInt:(I)I
        //    79: aload           7
        //    81: invokeinterface net/minecraft/inventory/IInventory.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //    86: goto            135
        //    89: goto            129
        //    92: aload           5
        //    94: getfield        net/minecraft/util/WeightedRandomChestContent.theItemId:Lnet/minecraft/item/ItemStack;
        //    97: invokevirtual   net/minecraft/item/ItemStack.copy:()Lnet/minecraft/item/ItemStack;
        //   100: astore          8
        //   102: aload           8
        //   104: iconst_1       
        //   105: putfield        net/minecraft/item/ItemStack.stackSize:I
        //   108: aload_2        
        //   109: aload_0        
        //   110: aload_2        
        //   111: invokeinterface net/minecraft/inventory/IInventory.getSizeInventory:()I
        //   116: invokevirtual   java/util/Random.nextInt:(I)I
        //   119: aload           8
        //   121: invokeinterface net/minecraft/inventory/IInventory.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   126: iinc            7, 1
        //   129: iconst_0       
        //   130: iload           6
        //   132: if_icmplt       92
        //   135: iinc            4, 1
        //   138: iconst_0       
        //   139: iload_3        
        //   140: if_icmplt       3
        //   143: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static void func_177631_a(final Random p0, final List p1, final TileEntityDispenser p2, final int p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: aload_0        
        //     4: aload_1        
        //     5: invokestatic    net/minecraft/util/WeightedRandom.getRandomItem:(Ljava/util/Random;Ljava/util/Collection;)Lnet/minecraft/util/WeightedRandom$Item;
        //     8: checkcast       Lnet/minecraft/util/WeightedRandomChestContent;
        //    11: astore          5
        //    13: aload           5
        //    15: getfield        net/minecraft/util/WeightedRandomChestContent.theMinimumChanceToGenerateItem:I
        //    18: aload_0        
        //    19: aload           5
        //    21: getfield        net/minecraft/util/WeightedRandomChestContent.theMaximumChanceToGenerateItem:I
        //    24: aload           5
        //    26: getfield        net/minecraft/util/WeightedRandomChestContent.theMinimumChanceToGenerateItem:I
        //    29: isub           
        //    30: iconst_1       
        //    31: iadd           
        //    32: invokevirtual   java/util/Random.nextInt:(I)I
        //    35: iadd           
        //    36: istore          6
        //    38: aload           5
        //    40: getfield        net/minecraft/util/WeightedRandomChestContent.theItemId:Lnet/minecraft/item/ItemStack;
        //    43: invokevirtual   net/minecraft/item/ItemStack.getMaxStackSize:()I
        //    46: iload           6
        //    48: if_icmplt       85
        //    51: aload           5
        //    53: getfield        net/minecraft/util/WeightedRandomChestContent.theItemId:Lnet/minecraft/item/ItemStack;
        //    56: invokevirtual   net/minecraft/item/ItemStack.copy:()Lnet/minecraft/item/ItemStack;
        //    59: astore          7
        //    61: aload           7
        //    63: iload           6
        //    65: putfield        net/minecraft/item/ItemStack.stackSize:I
        //    68: aload_2        
        //    69: aload_0        
        //    70: aload_2        
        //    71: invokevirtual   net/minecraft/tileentity/TileEntityDispenser.getSizeInventory:()I
        //    74: invokevirtual   java/util/Random.nextInt:(I)I
        //    77: aload           7
        //    79: invokevirtual   net/minecraft/tileentity/TileEntityDispenser.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //    82: goto            127
        //    85: goto            121
        //    88: aload           5
        //    90: getfield        net/minecraft/util/WeightedRandomChestContent.theItemId:Lnet/minecraft/item/ItemStack;
        //    93: invokevirtual   net/minecraft/item/ItemStack.copy:()Lnet/minecraft/item/ItemStack;
        //    96: astore          8
        //    98: aload           8
        //   100: iconst_1       
        //   101: putfield        net/minecraft/item/ItemStack.stackSize:I
        //   104: aload_2        
        //   105: aload_0        
        //   106: aload_2        
        //   107: invokevirtual   net/minecraft/tileentity/TileEntityDispenser.getSizeInventory:()I
        //   110: invokevirtual   java/util/Random.nextInt:(I)I
        //   113: aload           8
        //   115: invokevirtual   net/minecraft/tileentity/TileEntityDispenser.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   118: iinc            7, 1
        //   121: iconst_0       
        //   122: iload           6
        //   124: if_icmplt       88
        //   127: iinc            4, 1
        //   130: iconst_0       
        //   131: iload_3        
        //   132: if_icmplt       3
        //   135: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static List func_177629_a(final List list, final WeightedRandomChestContent... array) {
        final ArrayList arrayList = Lists.newArrayList(list);
        Collections.addAll(arrayList, array);
        return arrayList;
    }
    
    static {
        __OBFID = "CL_00001505";
    }
}
