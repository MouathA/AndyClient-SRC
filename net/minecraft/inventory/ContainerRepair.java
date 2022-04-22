package net.minecraft.inventory;

import net.minecraft.world.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import org.apache.commons.lang3.*;

public class ContainerRepair extends Container
{
    private static final Logger logger;
    private IInventory outputSlot;
    private IInventory inputSlots;
    private World theWorld;
    private BlockPos field_178156_j;
    public int maximumCost;
    private int materialCost;
    private String repairedItemName;
    private final EntityPlayer thePlayer;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001732";
        logger = LogManager.getLogger();
    }
    
    public ContainerRepair(final InventoryPlayer inventoryPlayer, final World world, final EntityPlayer entityPlayer) {
        this(inventoryPlayer, world, BlockPos.ORIGIN, entityPlayer);
    }
    
    public ContainerRepair(final InventoryPlayer inventoryPlayer, final World theWorld, final BlockPos field_178156_j, final EntityPlayer thePlayer) {
        this.outputSlot = new InventoryCraftResult();
        this.inputSlots = new InventoryBasic("Repair", true, 2) {
            private static final String __OBFID;
            final ContainerRepair this$0;
            
            @Override
            public void markDirty() {
                super.markDirty();
                this.this$0.onCraftMatrixChanged(this);
            }
            
            static {
                __OBFID = "CL_00001733";
            }
        };
        this.field_178156_j = field_178156_j;
        this.theWorld = theWorld;
        this.thePlayer = thePlayer;
        this.addSlotToContainer(new Slot(this.inputSlots, 0, 27, 47));
        this.addSlotToContainer(new Slot(this.inputSlots, 1, 76, 47));
        this.addSlotToContainer(new Slot(this.outputSlot, 2, 134, 47, theWorld, field_178156_j) {
            private static final String __OBFID;
            final ContainerRepair this$0;
            private final World val$worldIn;
            private final BlockPos val$p_i45807_3_;
            
            @Override
            public boolean isItemValid(final ItemStack itemStack) {
                return false;
            }
            
            @Override
            public boolean canTakeStack(final EntityPlayer entityPlayer) {
                return (entityPlayer.capabilities.isCreativeMode || entityPlayer.experienceLevel >= this.this$0.maximumCost) && this.this$0.maximumCost > 0 && this.getHasStack();
            }
            
            @Override
            public void onPickupFromSlot(final EntityPlayer entityPlayer, final ItemStack itemStack) {
                if (!entityPlayer.capabilities.isCreativeMode) {
                    entityPlayer.addExperienceLevel(-this.this$0.maximumCost);
                }
                ContainerRepair.access$0(this.this$0).setInventorySlotContents(0, null);
                if (ContainerRepair.access$1(this.this$0) > 0) {
                    final ItemStack stackInSlot = ContainerRepair.access$0(this.this$0).getStackInSlot(1);
                    if (stackInSlot != null && stackInSlot.stackSize > ContainerRepair.access$1(this.this$0)) {
                        final ItemStack itemStack2 = stackInSlot;
                        itemStack2.stackSize -= ContainerRepair.access$1(this.this$0);
                        ContainerRepair.access$0(this.this$0).setInventorySlotContents(1, stackInSlot);
                    }
                    else {
                        ContainerRepair.access$0(this.this$0).setInventorySlotContents(1, null);
                    }
                }
                else {
                    ContainerRepair.access$0(this.this$0).setInventorySlotContents(1, null);
                }
                this.this$0.maximumCost = 0;
                final IBlockState blockState = this.val$worldIn.getBlockState(this.val$p_i45807_3_);
                if (!entityPlayer.capabilities.isCreativeMode && !this.val$worldIn.isRemote && blockState.getBlock() == Blocks.anvil && entityPlayer.getRNG().nextFloat() < 0.12f) {
                    int intValue = (int)blockState.getValue(BlockAnvil.DAMAGE);
                    if (++intValue > 2) {
                        this.val$worldIn.setBlockToAir(this.val$p_i45807_3_);
                        this.val$worldIn.playAuxSFX(1020, this.val$p_i45807_3_, 0);
                    }
                    else {
                        this.val$worldIn.setBlockState(this.val$p_i45807_3_, blockState.withProperty(BlockAnvil.DAMAGE, intValue), 2);
                        this.val$worldIn.playAuxSFX(1021, this.val$p_i45807_3_, 0);
                    }
                }
                else if (!this.val$worldIn.isRemote) {
                    this.val$worldIn.playAuxSFX(1021, this.val$p_i45807_3_, 0);
                }
            }
            
            static {
                __OBFID = "CL_00001734";
            }
        });
        int n2 = 0;
        while (0 < 3) {
            while (0 < 9) {
                this.addSlotToContainer(new Slot(inventoryPlayer, 9, 8, 84));
                int n = 0;
                ++n;
            }
            ++n2;
        }
        while (0 < 9) {
            this.addSlotToContainer(new Slot(inventoryPlayer, 0, 8, 142));
            ++n2;
        }
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory inventory) {
        super.onCraftMatrixChanged(inventory);
        if (inventory == this.inputSlots) {
            this.updateRepairOutput();
        }
    }
    
    public void updateRepairOutput() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/inventory/ContainerRepair.inputSlots:Lnet/minecraft/inventory/IInventory;
        //     4: iconst_0       
        //     5: invokeinterface net/minecraft/inventory/IInventory.getStackInSlot:(I)Lnet/minecraft/item/ItemStack;
        //    10: astore          8
        //    12: aload_0        
        //    13: iconst_1       
        //    14: putfield        net/minecraft/inventory/ContainerRepair.maximumCost:I
        //    17: aload           8
        //    19: ifnonnull       41
        //    22: aload_0        
        //    23: getfield        net/minecraft/inventory/ContainerRepair.outputSlot:Lnet/minecraft/inventory/IInventory;
        //    26: iconst_0       
        //    27: aconst_null    
        //    28: invokeinterface net/minecraft/inventory/IInventory.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //    33: aload_0        
        //    34: iconst_0       
        //    35: putfield        net/minecraft/inventory/ContainerRepair.maximumCost:I
        //    38: goto            953
        //    41: aload           8
        //    43: invokevirtual   net/minecraft/item/ItemStack.copy:()Lnet/minecraft/item/ItemStack;
        //    46: astore          12
        //    48: aload_0        
        //    49: getfield        net/minecraft/inventory/ContainerRepair.inputSlots:Lnet/minecraft/inventory/IInventory;
        //    52: iconst_1       
        //    53: invokeinterface net/minecraft/inventory/IInventory.getStackInSlot:(I)Lnet/minecraft/item/ItemStack;
        //    58: astore          13
        //    60: aload           12
        //    62: invokestatic    net/minecraft/enchantment/EnchantmentHelper.getEnchantments:(Lnet/minecraft/item/ItemStack;)Ljava/util/Map;
        //    65: astore          14
        //    67: iconst_0       
        //    68: aload           8
        //    70: invokevirtual   net/minecraft/item/ItemStack.getRepairCost:()I
        //    73: iadd           
        //    74: aload           13
        //    76: ifnonnull       83
        //    79: iconst_0       
        //    80: goto            88
        //    83: aload           13
        //    85: invokevirtual   net/minecraft/item/ItemStack.getRepairCost:()I
        //    88: iadd           
        //    89: istore          16
        //    91: aload_0        
        //    92: iconst_0       
        //    93: putfield        net/minecraft/inventory/ContainerRepair.materialCost:I
        //    96: aload           13
        //    98: ifnull          766
        //   101: aload           13
        //   103: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   106: getstatic       net/minecraft/init/Items.enchanted_book:Lnet/minecraft/item/ItemEnchantedBook;
        //   109: if_acmpne       130
        //   112: getstatic       net/minecraft/init/Items.enchanted_book:Lnet/minecraft/item/ItemEnchantedBook;
        //   115: aload           13
        //   117: invokevirtual   net/minecraft/item/ItemEnchantedBook.func_92110_g:(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/nbt/NBTTagList;
        //   120: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //   123: ifle            130
        //   126: iconst_1       
        //   127: goto            131
        //   130: iconst_0       
        //   131: istore          15
        //   133: aload           12
        //   135: invokevirtual   net/minecraft/item/ItemStack.isItemStackDamageable:()Z
        //   138: ifeq            260
        //   141: aload           12
        //   143: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   146: aload           8
        //   148: aload           13
        //   150: invokevirtual   net/minecraft/item/Item.getIsRepairable:(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z
        //   153: ifeq            260
        //   156: aload           12
        //   158: invokevirtual   net/minecraft/item/ItemStack.getItemDamage:()I
        //   161: aload           12
        //   163: invokevirtual   net/minecraft/item/ItemStack.getMaxDamage:()I
        //   166: iconst_4       
        //   167: idiv           
        //   168: invokestatic    java/lang/Math.min:(II)I
        //   171: istore          17
        //   173: iload           17
        //   175: ifgt            195
        //   178: aload_0        
        //   179: getfield        net/minecraft/inventory/ContainerRepair.outputSlot:Lnet/minecraft/inventory/IInventory;
        //   182: iconst_0       
        //   183: aconst_null    
        //   184: invokeinterface net/minecraft/inventory/IInventory.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   189: aload_0        
        //   190: iconst_0       
        //   191: putfield        net/minecraft/inventory/ContainerRepair.maximumCost:I
        //   194: return         
        //   195: goto            238
        //   198: aload           12
        //   200: invokevirtual   net/minecraft/item/ItemStack.getItemDamage:()I
        //   203: iload           17
        //   205: isub           
        //   206: istore          19
        //   208: aload           12
        //   210: iload           19
        //   212: invokevirtual   net/minecraft/item/ItemStack.setItemDamage:(I)V
        //   215: iinc            9, 1
        //   218: aload           12
        //   220: invokevirtual   net/minecraft/item/ItemStack.getItemDamage:()I
        //   223: aload           12
        //   225: invokevirtual   net/minecraft/item/ItemStack.getMaxDamage:()I
        //   228: iconst_4       
        //   229: idiv           
        //   230: invokestatic    java/lang/Math.min:(II)I
        //   233: istore          17
        //   235: iinc            18, 1
        //   238: iload           17
        //   240: ifle            252
        //   243: iconst_0       
        //   244: aload           13
        //   246: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   249: if_icmplt       198
        //   252: aload_0        
        //   253: iconst_0       
        //   254: putfield        net/minecraft/inventory/ContainerRepair.materialCost:I
        //   257: goto            766
        //   260: iconst_0       
        //   261: ifne            302
        //   264: aload           12
        //   266: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   269: aload           13
        //   271: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   274: if_acmpne       285
        //   277: aload           12
        //   279: invokevirtual   net/minecraft/item/ItemStack.isItemStackDamageable:()Z
        //   282: ifne            302
        //   285: aload_0        
        //   286: getfield        net/minecraft/inventory/ContainerRepair.outputSlot:Lnet/minecraft/inventory/IInventory;
        //   289: iconst_0       
        //   290: aconst_null    
        //   291: invokeinterface net/minecraft/inventory/IInventory.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   296: aload_0        
        //   297: iconst_0       
        //   298: putfield        net/minecraft/inventory/ContainerRepair.maximumCost:I
        //   301: return         
        //   302: aload           12
        //   304: invokevirtual   net/minecraft/item/ItemStack.isItemStackDamageable:()Z
        //   307: ifeq            394
        //   310: iconst_0       
        //   311: ifne            394
        //   314: aload           8
        //   316: invokevirtual   net/minecraft/item/ItemStack.getMaxDamage:()I
        //   319: aload           8
        //   321: invokevirtual   net/minecraft/item/ItemStack.getItemDamage:()I
        //   324: isub           
        //   325: istore          17
        //   327: aload           13
        //   329: invokevirtual   net/minecraft/item/ItemStack.getMaxDamage:()I
        //   332: aload           13
        //   334: invokevirtual   net/minecraft/item/ItemStack.getItemDamage:()I
        //   337: isub           
        //   338: istore          18
        //   340: iconst_0       
        //   341: aload           12
        //   343: invokevirtual   net/minecraft/item/ItemStack.getMaxDamage:()I
        //   346: bipush          12
        //   348: imul           
        //   349: bipush          100
        //   351: idiv           
        //   352: iadd           
        //   353: istore          19
        //   355: iload           17
        //   357: iload           19
        //   359: iadd           
        //   360: istore          21
        //   362: aload           12
        //   364: invokevirtual   net/minecraft/item/ItemStack.getMaxDamage:()I
        //   367: iload           21
        //   369: isub           
        //   370: istore          20
        //   372: iconst_0       
        //   373: ifge            376
        //   376: iconst_0       
        //   377: aload           12
        //   379: invokevirtual   net/minecraft/item/ItemStack.getMetadata:()I
        //   382: if_icmpge       394
        //   385: aload           12
        //   387: iconst_0       
        //   388: invokevirtual   net/minecraft/item/ItemStack.setItemDamage:(I)V
        //   391: iinc            9, 2
        //   394: aload           13
        //   396: invokestatic    net/minecraft/enchantment/EnchantmentHelper.getEnchantments:(Lnet/minecraft/item/ItemStack;)Ljava/util/Map;
        //   399: astore          21
        //   401: aload           21
        //   403: invokeinterface java/util/Map.keySet:()Ljava/util/Set;
        //   408: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //   413: astore          22
        //   415: goto            756
        //   418: aload           22
        //   420: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   425: checkcast       Ljava/lang/Integer;
        //   428: invokevirtual   java/lang/Integer.intValue:()I
        //   431: istore          19
        //   433: iload           19
        //   435: invokestatic    net/minecraft/enchantment/Enchantment.func_180306_c:(I)Lnet/minecraft/enchantment/Enchantment;
        //   438: astore          23
        //   440: aload           23
        //   442: ifnull          756
        //   445: aload           14
        //   447: iload           19
        //   449: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   452: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   457: ifeq            481
        //   460: aload           14
        //   462: iload           19
        //   464: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   467: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   472: checkcast       Ljava/lang/Integer;
        //   475: invokevirtual   java/lang/Integer.intValue:()I
        //   478: goto            482
        //   481: iconst_0       
        //   482: istore          20
        //   484: aload           21
        //   486: iload           19
        //   488: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   491: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   496: checkcast       Ljava/lang/Integer;
        //   499: invokevirtual   java/lang/Integer.intValue:()I
        //   502: istore          24
        //   504: iconst_0       
        //   505: iload           24
        //   507: if_icmpne       520
        //   510: iinc            24, 1
        //   513: iload           24
        //   515: istore          25
        //   517: goto            528
        //   520: iload           24
        //   522: iconst_0       
        //   523: invokestatic    java/lang/Math.max:(II)I
        //   526: istore          25
        //   528: iload           25
        //   530: istore          24
        //   532: aload           23
        //   534: aload           8
        //   536: invokevirtual   net/minecraft/enchantment/Enchantment.canApply:(Lnet/minecraft/item/ItemStack;)Z
        //   539: istore          26
        //   541: aload_0        
        //   542: getfield        net/minecraft/inventory/ContainerRepair.thePlayer:Lnet/minecraft/entity/player/EntityPlayer;
        //   545: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   548: getfield        net/minecraft/entity/player/PlayerCapabilities.isCreativeMode:Z
        //   551: ifne            565
        //   554: aload           8
        //   556: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   559: getstatic       net/minecraft/init/Items.enchanted_book:Lnet/minecraft/item/ItemEnchantedBook;
        //   562: if_acmpne       565
        //   565: aload           14
        //   567: invokeinterface java/util/Map.keySet:()Ljava/util/Set;
        //   572: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //   577: astore          27
        //   579: goto            618
        //   582: aload           27
        //   584: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   589: checkcast       Ljava/lang/Integer;
        //   592: invokevirtual   java/lang/Integer.intValue:()I
        //   595: istore          28
        //   597: iconst_1       
        //   598: iload           19
        //   600: if_icmpeq       618
        //   603: aload           23
        //   605: iconst_1       
        //   606: invokestatic    net/minecraft/enchantment/Enchantment.func_180306_c:(I)Lnet/minecraft/enchantment/Enchantment;
        //   609: invokevirtual   net/minecraft/enchantment/Enchantment.canApplyTogether:(Lnet/minecraft/enchantment/Enchantment;)Z
        //   612: ifne            618
        //   615: iinc            9, 1
        //   618: aload           27
        //   620: invokeinterface java/util/Iterator.hasNext:()Z
        //   625: ifne            582
        //   628: iconst_0       
        //   629: ifeq            756
        //   632: iload           24
        //   634: aload           23
        //   636: invokevirtual   net/minecraft/enchantment/Enchantment.getMaxLevel:()I
        //   639: if_icmple       649
        //   642: aload           23
        //   644: invokevirtual   net/minecraft/enchantment/Enchantment.getMaxLevel:()I
        //   647: istore          24
        //   649: aload           14
        //   651: iload           19
        //   653: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   656: iload           24
        //   658: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   661: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   666: pop            
        //   667: aload           23
        //   669: invokevirtual   net/minecraft/enchantment/Enchantment.getWeight:()I
        //   672: tableswitch {
        //                2: 728
        //                3: 731
        //                4: 731
        //                5: 731
        //                6: 734
        //                7: 731
        //                8: 731
        //                9: 731
        //               10: 731
        //               11: 737
        //          default: 731
        //        }
        //   728: goto            737
        //   731: goto            737
        //   734: goto            737
        //   737: iconst_0       
        //   738: ifeq            748
        //   741: iconst_1       
        //   742: iconst_0       
        //   743: invokestatic    java/lang/Math.max:(II)I
        //   746: istore          28
        //   748: iconst_0       
        //   749: iconst_1       
        //   750: iload           24
        //   752: imul           
        //   753: iadd           
        //   754: istore          9
        //   756: aload           22
        //   758: invokeinterface java/util/Iterator.hasNext:()Z
        //   763: ifne            418
        //   766: aload_0        
        //   767: getfield        net/minecraft/inventory/ContainerRepair.repairedItemName:Ljava/lang/String;
        //   770: invokestatic    org/apache/commons/lang3/StringUtils.isBlank:(Ljava/lang/CharSequence;)Z
        //   773: ifeq            792
        //   776: aload           8
        //   778: invokevirtual   net/minecraft/item/ItemStack.hasDisplayName:()Z
        //   781: ifeq            817
        //   784: aload           12
        //   786: invokevirtual   net/minecraft/item/ItemStack.clearCustomName:()V
        //   789: goto            817
        //   792: aload_0        
        //   793: getfield        net/minecraft/inventory/ContainerRepair.repairedItemName:Ljava/lang/String;
        //   796: aload           8
        //   798: invokevirtual   net/minecraft/item/ItemStack.getDisplayName:()Ljava/lang/String;
        //   801: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   804: ifne            817
        //   807: aload           12
        //   809: aload_0        
        //   810: getfield        net/minecraft/inventory/ContainerRepair.repairedItemName:Ljava/lang/String;
        //   813: invokevirtual   net/minecraft/item/ItemStack.setStackDisplayName:(Ljava/lang/String;)Lnet/minecraft/item/ItemStack;
        //   816: pop            
        //   817: aload_0        
        //   818: iload           16
        //   820: iconst_0       
        //   821: iadd           
        //   822: putfield        net/minecraft/inventory/ContainerRepair.maximumCost:I
        //   825: iconst_0       
        //   826: ifgt            832
        //   829: aconst_null    
        //   830: astore          12
        //   832: iconst_1       
        //   833: iconst_0       
        //   834: if_icmpne       856
        //   837: iconst_1       
        //   838: ifle            856
        //   841: aload_0        
        //   842: getfield        net/minecraft/inventory/ContainerRepair.maximumCost:I
        //   845: bipush          40
        //   847: if_icmplt       856
        //   850: aload_0        
        //   851: bipush          39
        //   853: putfield        net/minecraft/inventory/ContainerRepair.maximumCost:I
        //   856: aload_0        
        //   857: getfield        net/minecraft/inventory/ContainerRepair.maximumCost:I
        //   860: bipush          40
        //   862: if_icmplt       881
        //   865: aload_0        
        //   866: getfield        net/minecraft/inventory/ContainerRepair.thePlayer:Lnet/minecraft/entity/player/EntityPlayer;
        //   869: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   872: getfield        net/minecraft/entity/player/PlayerCapabilities.isCreativeMode:Z
        //   875: ifne            881
        //   878: aconst_null    
        //   879: astore          12
        //   881: aload           12
        //   883: ifnull          937
        //   886: aload           12
        //   888: invokevirtual   net/minecraft/item/ItemStack.getRepairCost:()I
        //   891: istore          17
        //   893: aload           13
        //   895: ifnull          915
        //   898: iload           17
        //   900: aload           13
        //   902: invokevirtual   net/minecraft/item/ItemStack.getRepairCost:()I
        //   905: if_icmpge       915
        //   908: aload           13
        //   910: invokevirtual   net/minecraft/item/ItemStack.getRepairCost:()I
        //   913: istore          17
        //   915: iload           17
        //   917: iconst_2       
        //   918: imul           
        //   919: iconst_1       
        //   920: iadd           
        //   921: istore          17
        //   923: aload           12
        //   925: iload           17
        //   927: invokevirtual   net/minecraft/item/ItemStack.setRepairCost:(I)V
        //   930: aload           14
        //   932: aload           12
        //   934: invokestatic    net/minecraft/enchantment/EnchantmentHelper.setEnchantments:(Ljava/util/Map;Lnet/minecraft/item/ItemStack;)V
        //   937: aload_0        
        //   938: getfield        net/minecraft/inventory/ContainerRepair.outputSlot:Lnet/minecraft/inventory/IInventory;
        //   941: iconst_0       
        //   942: aload           12
        //   944: invokeinterface net/minecraft/inventory/IInventory.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   949: aload_0        
        //   950: invokevirtual   net/minecraft/inventory/ContainerRepair.detectAndSendChanges:()V
        //   953: return         
        // 
        // The error that occurred was:
        // 
        // java.util.ConcurrentModificationException
        //     at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
        //     at java.util.ArrayList$Itr.next(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2863)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
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
    
    @Override
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        crafting.sendProgressBarUpdate(this, 0, this.maximumCost);
    }
    
    @Override
    public void updateProgressBar(final int n, final int maximumCost) {
        if (n == 0) {
            this.maximumCost = maximumCost;
        }
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        if (!this.theWorld.isRemote) {
            while (0 < this.inputSlots.getSizeInventory()) {
                final ItemStack stackInSlotOnClosing = this.inputSlots.getStackInSlotOnClosing(0);
                if (stackInSlotOnClosing != null) {
                    entityPlayer.dropPlayerItemWithRandomChoice(stackInSlotOnClosing, false);
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.theWorld.getBlockState(this.field_178156_j).getBlock() == Blocks.anvil && entityPlayer.getDistanceSq(this.field_178156_j.getX() + 0.5, this.field_178156_j.getY() + 0.5, this.field_178156_j.getZ() + 0.5) <= 64.0;
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n == 2) {
                if (!this.mergeItemStack(stack, 3, 39, true)) {
                    return null;
                }
                slot.onSlotChange(stack, copy);
            }
            else if (n != 0 && n != 1) {
                if (n >= 3 && n < 39 && !this.mergeItemStack(stack, 0, 2, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, 3, 39, false)) {
                return null;
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }
            if (stack.stackSize == copy.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(entityPlayer, stack);
        }
        return copy;
    }
    
    public void updateItemName(final String repairedItemName) {
        this.repairedItemName = repairedItemName;
        if (this.getSlot(2).getHasStack()) {
            final ItemStack stack = this.getSlot(2).getStack();
            if (StringUtils.isBlank(repairedItemName)) {
                stack.clearCustomName();
            }
            else {
                stack.setStackDisplayName(this.repairedItemName);
            }
        }
        this.updateRepairOutput();
    }
    
    static IInventory access$0(final ContainerRepair containerRepair) {
        return containerRepair.inputSlots;
    }
    
    static int access$1(final ContainerRepair containerRepair) {
        return containerRepair.materialCost;
    }
}
