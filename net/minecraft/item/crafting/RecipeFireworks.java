package net.minecraft.item.crafting;

import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;

public class RecipeFireworks implements IRecipe
{
    private ItemStack field_92102_a;
    private static final String __OBFID;
    
    @Override
    public boolean matches(final InventoryCrafting p0, final World p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aconst_null    
        //     2: putfield        net/minecraft/item/crafting/RecipeFireworks.field_92102_a:Lnet/minecraft/item/ItemStack;
        //     5: goto            192
        //     8: aload_1        
        //     9: iconst_0       
        //    10: invokevirtual   net/minecraft/inventory/InventoryCrafting.getStackInSlot:(I)Lnet/minecraft/item/ItemStack;
        //    13: astore          10
        //    15: aload           10
        //    17: ifnull          189
        //    20: aload           10
        //    22: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    25: getstatic       net/minecraft/init/Items.gunpowder:Lnet/minecraft/item/Item;
        //    28: if_acmpne       37
        //    31: iinc            4, 1
        //    34: goto            189
        //    37: aload           10
        //    39: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    42: getstatic       net/minecraft/init/Items.firework_charge:Lnet/minecraft/item/Item;
        //    45: if_acmpne       54
        //    48: iinc            6, 1
        //    51: goto            189
        //    54: aload           10
        //    56: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    59: getstatic       net/minecraft/init/Items.dye:Lnet/minecraft/item/Item;
        //    62: if_acmpne       71
        //    65: iinc            5, 1
        //    68: goto            189
        //    71: aload           10
        //    73: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    76: getstatic       net/minecraft/init/Items.paper:Lnet/minecraft/item/Item;
        //    79: if_acmpne       88
        //    82: iinc            3, 1
        //    85: goto            189
        //    88: aload           10
        //    90: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    93: getstatic       net/minecraft/init/Items.glowstone_dust:Lnet/minecraft/item/Item;
        //    96: if_acmpne       105
        //    99: iinc            7, 1
        //   102: goto            189
        //   105: aload           10
        //   107: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   110: getstatic       net/minecraft/init/Items.diamond:Lnet/minecraft/item/Item;
        //   113: if_acmpne       122
        //   116: iinc            7, 1
        //   119: goto            189
        //   122: aload           10
        //   124: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   127: getstatic       net/minecraft/init/Items.fire_charge:Lnet/minecraft/item/Item;
        //   130: if_acmpne       139
        //   133: iinc            8, 1
        //   136: goto            189
        //   139: aload           10
        //   141: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   144: getstatic       net/minecraft/init/Items.feather:Lnet/minecraft/item/Item;
        //   147: if_acmpne       156
        //   150: iinc            8, 1
        //   153: goto            189
        //   156: aload           10
        //   158: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   161: getstatic       net/minecraft/init/Items.gold_nugget:Lnet/minecraft/item/Item;
        //   164: if_acmpne       173
        //   167: iinc            8, 1
        //   170: goto            189
        //   173: aload           10
        //   175: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   178: getstatic       net/minecraft/init/Items.skull:Lnet/minecraft/item/Item;
        //   181: if_acmpeq       186
        //   184: iconst_0       
        //   185: ireturn        
        //   186: iinc            8, 1
        //   189: iinc            9, 1
        //   192: iconst_0       
        //   193: aload_1        
        //   194: invokevirtual   net/minecraft/inventory/InventoryCrafting.getSizeInventory:()I
        //   197: if_icmplt       8
        //   200: iconst_0       
        //   201: iconst_3       
        //   202: if_icmpgt       881
        //   205: iconst_0       
        //   206: iconst_1       
        //   207: if_icmpgt       881
        //   210: iconst_0       
        //   211: iconst_1       
        //   212: if_icmplt       382
        //   215: iconst_0       
        //   216: iconst_1       
        //   217: if_icmpne       382
        //   220: iconst_0       
        //   221: ifne            382
        //   224: aload_0        
        //   225: new             Lnet/minecraft/item/ItemStack;
        //   228: dup            
        //   229: getstatic       net/minecraft/init/Items.fireworks:Lnet/minecraft/item/Item;
        //   232: invokespecial   net/minecraft/item/ItemStack.<init>:(Lnet/minecraft/item/Item;)V
        //   235: putfield        net/minecraft/item/crafting/RecipeFireworks.field_92102_a:Lnet/minecraft/item/ItemStack;
        //   238: iconst_0       
        //   239: ifle            380
        //   242: new             Lnet/minecraft/nbt/NBTTagCompound;
        //   245: dup            
        //   246: invokespecial   net/minecraft/nbt/NBTTagCompound.<init>:()V
        //   249: astore          9
        //   251: new             Lnet/minecraft/nbt/NBTTagCompound;
        //   254: dup            
        //   255: invokespecial   net/minecraft/nbt/NBTTagCompound.<init>:()V
        //   258: astore          10
        //   260: new             Lnet/minecraft/nbt/NBTTagList;
        //   263: dup            
        //   264: invokespecial   net/minecraft/nbt/NBTTagList.<init>:()V
        //   267: astore          11
        //   269: goto            336
        //   272: aload_1        
        //   273: iconst_0       
        //   274: invokevirtual   net/minecraft/inventory/InventoryCrafting.getStackInSlot:(I)Lnet/minecraft/item/ItemStack;
        //   277: astore          13
        //   279: aload           13
        //   281: ifnull          333
        //   284: aload           13
        //   286: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   289: getstatic       net/minecraft/init/Items.firework_charge:Lnet/minecraft/item/Item;
        //   292: if_acmpne       333
        //   295: aload           13
        //   297: invokevirtual   net/minecraft/item/ItemStack.hasTagCompound:()Z
        //   300: ifeq            333
        //   303: aload           13
        //   305: invokevirtual   net/minecraft/item/ItemStack.getTagCompound:()Lnet/minecraft/nbt/NBTTagCompound;
        //   308: ldc             "Explosion"
        //   310: bipush          10
        //   312: invokevirtual   net/minecraft/nbt/NBTTagCompound.hasKey:(Ljava/lang/String;I)Z
        //   315: ifeq            333
        //   318: aload           11
        //   320: aload           13
        //   322: invokevirtual   net/minecraft/item/ItemStack.getTagCompound:()Lnet/minecraft/nbt/NBTTagCompound;
        //   325: ldc             "Explosion"
        //   327: invokevirtual   net/minecraft/nbt/NBTTagCompound.getCompoundTag:(Ljava/lang/String;)Lnet/minecraft/nbt/NBTTagCompound;
        //   330: invokevirtual   net/minecraft/nbt/NBTTagList.appendTag:(Lnet/minecraft/nbt/NBTBase;)V
        //   333: iinc            12, 1
        //   336: iconst_0       
        //   337: aload_1        
        //   338: invokevirtual   net/minecraft/inventory/InventoryCrafting.getSizeInventory:()I
        //   341: if_icmplt       272
        //   344: aload           10
        //   346: ldc             "Explosions"
        //   348: aload           11
        //   350: invokevirtual   net/minecraft/nbt/NBTTagCompound.setTag:(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V
        //   353: aload           10
        //   355: ldc             "Flight"
        //   357: iconst_0       
        //   358: i2b            
        //   359: invokevirtual   net/minecraft/nbt/NBTTagCompound.setByte:(Ljava/lang/String;B)V
        //   362: aload           9
        //   364: ldc             "Fireworks"
        //   366: aload           10
        //   368: invokevirtual   net/minecraft/nbt/NBTTagCompound.setTag:(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V
        //   371: aload_0        
        //   372: getfield        net/minecraft/item/crafting/RecipeFireworks.field_92102_a:Lnet/minecraft/item/ItemStack;
        //   375: aload           9
        //   377: invokevirtual   net/minecraft/item/ItemStack.setTagCompound:(Lnet/minecraft/nbt/NBTTagCompound;)V
        //   380: iconst_1       
        //   381: ireturn        
        //   382: iconst_0       
        //   383: iconst_1       
        //   384: if_icmpne       674
        //   387: iconst_0       
        //   388: ifne            674
        //   391: iconst_0       
        //   392: ifne            674
        //   395: iconst_0       
        //   396: ifle            674
        //   399: iconst_0       
        //   400: iconst_1       
        //   401: if_icmpgt       674
        //   404: aload_0        
        //   405: new             Lnet/minecraft/item/ItemStack;
        //   408: dup            
        //   409: getstatic       net/minecraft/init/Items.firework_charge:Lnet/minecraft/item/Item;
        //   412: invokespecial   net/minecraft/item/ItemStack.<init>:(Lnet/minecraft/item/Item;)V
        //   415: putfield        net/minecraft/item/crafting/RecipeFireworks.field_92102_a:Lnet/minecraft/item/ItemStack;
        //   418: new             Lnet/minecraft/nbt/NBTTagCompound;
        //   421: dup            
        //   422: invokespecial   net/minecraft/nbt/NBTTagCompound.<init>:()V
        //   425: astore          9
        //   427: new             Lnet/minecraft/nbt/NBTTagCompound;
        //   430: dup            
        //   431: invokespecial   net/minecraft/nbt/NBTTagCompound.<init>:()V
        //   434: astore          10
        //   436: invokestatic    com/google/common/collect/Lists.newArrayList:()Ljava/util/ArrayList;
        //   439: astore          12
        //   441: goto            591
        //   444: aload_1        
        //   445: iconst_0       
        //   446: invokevirtual   net/minecraft/inventory/InventoryCrafting.getStackInSlot:(I)Lnet/minecraft/item/ItemStack;
        //   449: astore          14
        //   451: aload           14
        //   453: ifnull          588
        //   456: aload           14
        //   458: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   461: getstatic       net/minecraft/init/Items.dye:Lnet/minecraft/item/Item;
        //   464: if_acmpne       491
        //   467: aload           12
        //   469: getstatic       net/minecraft/item/ItemDye.dyeColors:[I
        //   472: aload           14
        //   474: invokevirtual   net/minecraft/item/ItemStack.getMetadata:()I
        //   477: bipush          15
        //   479: iand           
        //   480: iaload         
        //   481: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   484: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   487: pop            
        //   488: goto            588
        //   491: aload           14
        //   493: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   496: getstatic       net/minecraft/init/Items.glowstone_dust:Lnet/minecraft/item/Item;
        //   499: if_acmpne       513
        //   502: aload           10
        //   504: ldc             "Flicker"
        //   506: iconst_1       
        //   507: invokevirtual   net/minecraft/nbt/NBTTagCompound.setBoolean:(Ljava/lang/String;Z)V
        //   510: goto            588
        //   513: aload           14
        //   515: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   518: getstatic       net/minecraft/init/Items.diamond:Lnet/minecraft/item/Item;
        //   521: if_acmpne       535
        //   524: aload           10
        //   526: ldc             "Trail"
        //   528: iconst_1       
        //   529: invokevirtual   net/minecraft/nbt/NBTTagCompound.setBoolean:(Ljava/lang/String;Z)V
        //   532: goto            588
        //   535: aload           14
        //   537: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   540: getstatic       net/minecraft/init/Items.fire_charge:Lnet/minecraft/item/Item;
        //   543: if_acmpne       549
        //   546: goto            588
        //   549: aload           14
        //   551: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   554: getstatic       net/minecraft/init/Items.feather:Lnet/minecraft/item/Item;
        //   557: if_acmpne       563
        //   560: goto            588
        //   563: aload           14
        //   565: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   568: getstatic       net/minecraft/init/Items.gold_nugget:Lnet/minecraft/item/Item;
        //   571: if_acmpne       577
        //   574: goto            588
        //   577: aload           14
        //   579: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   582: getstatic       net/minecraft/init/Items.skull:Lnet/minecraft/item/Item;
        //   585: if_acmpne       588
        //   588: iinc            13, 1
        //   591: iconst_0       
        //   592: aload_1        
        //   593: invokevirtual   net/minecraft/inventory/InventoryCrafting.getSizeInventory:()I
        //   596: if_icmplt       444
        //   599: aload           12
        //   601: invokevirtual   java/util/ArrayList.size:()I
        //   604: newarray        I
        //   606: astore          13
        //   608: goto            630
        //   611: aload           13
        //   613: iconst_0       
        //   614: aload           12
        //   616: iconst_0       
        //   617: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //   620: checkcast       Ljava/lang/Integer;
        //   623: invokevirtual   java/lang/Integer.intValue:()I
        //   626: iastore        
        //   627: iinc            14, 1
        //   630: iconst_0       
        //   631: aload           13
        //   633: arraylength    
        //   634: if_icmplt       611
        //   637: aload           10
        //   639: ldc             "Colors"
        //   641: aload           13
        //   643: invokevirtual   net/minecraft/nbt/NBTTagCompound.setIntArray:(Ljava/lang/String;[I)V
        //   646: aload           10
        //   648: ldc             "Type"
        //   650: iconst_3       
        //   651: invokevirtual   net/minecraft/nbt/NBTTagCompound.setByte:(Ljava/lang/String;B)V
        //   654: aload           9
        //   656: ldc             "Explosion"
        //   658: aload           10
        //   660: invokevirtual   net/minecraft/nbt/NBTTagCompound.setTag:(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V
        //   663: aload_0        
        //   664: getfield        net/minecraft/item/crafting/RecipeFireworks.field_92102_a:Lnet/minecraft/item/ItemStack;
        //   667: aload           9
        //   669: invokevirtual   net/minecraft/item/ItemStack.setTagCompound:(Lnet/minecraft/nbt/NBTTagCompound;)V
        //   672: iconst_1       
        //   673: ireturn        
        //   674: iconst_0       
        //   675: ifne            879
        //   678: iconst_0       
        //   679: ifne            879
        //   682: iconst_0       
        //   683: iconst_1       
        //   684: if_icmpne       879
        //   687: iconst_0       
        //   688: ifle            879
        //   691: iconst_0       
        //   692: iconst_0       
        //   693: if_icmpne       879
        //   696: invokestatic    com/google/common/collect/Lists.newArrayList:()Ljava/util/ArrayList;
        //   699: astore          11
        //   701: goto            782
        //   704: aload_1        
        //   705: iconst_0       
        //   706: invokevirtual   net/minecraft/inventory/InventoryCrafting.getStackInSlot:(I)Lnet/minecraft/item/ItemStack;
        //   709: astore          13
        //   711: aload           13
        //   713: ifnull          779
        //   716: aload           13
        //   718: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   721: getstatic       net/minecraft/init/Items.dye:Lnet/minecraft/item/Item;
        //   724: if_acmpne       751
        //   727: aload           11
        //   729: getstatic       net/minecraft/item/ItemDye.dyeColors:[I
        //   732: aload           13
        //   734: invokevirtual   net/minecraft/item/ItemStack.getMetadata:()I
        //   737: bipush          15
        //   739: iand           
        //   740: iaload         
        //   741: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   744: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   747: pop            
        //   748: goto            779
        //   751: aload           13
        //   753: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   756: getstatic       net/minecraft/init/Items.firework_charge:Lnet/minecraft/item/Item;
        //   759: if_acmpne       779
        //   762: aload_0        
        //   763: aload           13
        //   765: invokevirtual   net/minecraft/item/ItemStack.copy:()Lnet/minecraft/item/ItemStack;
        //   768: putfield        net/minecraft/item/crafting/RecipeFireworks.field_92102_a:Lnet/minecraft/item/ItemStack;
        //   771: aload_0        
        //   772: getfield        net/minecraft/item/crafting/RecipeFireworks.field_92102_a:Lnet/minecraft/item/ItemStack;
        //   775: iconst_1       
        //   776: putfield        net/minecraft/item/ItemStack.stackSize:I
        //   779: iinc            12, 1
        //   782: iconst_0       
        //   783: aload_1        
        //   784: invokevirtual   net/minecraft/inventory/InventoryCrafting.getSizeInventory:()I
        //   787: if_icmplt       704
        //   790: aload           11
        //   792: invokevirtual   java/util/ArrayList.size:()I
        //   795: newarray        I
        //   797: astore          12
        //   799: goto            821
        //   802: aload           12
        //   804: iconst_0       
        //   805: aload           11
        //   807: iconst_0       
        //   808: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //   811: checkcast       Ljava/lang/Integer;
        //   814: invokevirtual   java/lang/Integer.intValue:()I
        //   817: iastore        
        //   818: iinc            13, 1
        //   821: iconst_0       
        //   822: aload           12
        //   824: arraylength    
        //   825: if_icmplt       802
        //   828: aload_0        
        //   829: getfield        net/minecraft/item/crafting/RecipeFireworks.field_92102_a:Lnet/minecraft/item/ItemStack;
        //   832: ifnull          877
        //   835: aload_0        
        //   836: getfield        net/minecraft/item/crafting/RecipeFireworks.field_92102_a:Lnet/minecraft/item/ItemStack;
        //   839: invokevirtual   net/minecraft/item/ItemStack.hasTagCompound:()Z
        //   842: ifeq            877
        //   845: aload_0        
        //   846: getfield        net/minecraft/item/crafting/RecipeFireworks.field_92102_a:Lnet/minecraft/item/ItemStack;
        //   849: invokevirtual   net/minecraft/item/ItemStack.getTagCompound:()Lnet/minecraft/nbt/NBTTagCompound;
        //   852: ldc             "Explosion"
        //   854: invokevirtual   net/minecraft/nbt/NBTTagCompound.getCompoundTag:(Ljava/lang/String;)Lnet/minecraft/nbt/NBTTagCompound;
        //   857: astore          13
        //   859: aload           13
        //   861: ifnonnull       866
        //   864: iconst_0       
        //   865: ireturn        
        //   866: aload           13
        //   868: ldc             "FadeColors"
        //   870: aload           12
        //   872: invokevirtual   net/minecraft/nbt/NBTTagCompound.setIntArray:(Ljava/lang/String;[I)V
        //   875: iconst_1       
        //   876: ireturn        
        //   877: iconst_0       
        //   878: ireturn        
        //   879: iconst_0       
        //   880: ireturn        
        //   881: iconst_0       
        //   882: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
        return this.field_92102_a.copy();
    }
    
    @Override
    public int getRecipeSize() {
        return 10;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return this.field_92102_a;
    }
    
    @Override
    public ItemStack[] func_179532_b(final InventoryCrafting inventoryCrafting) {
        final ItemStack[] array = new ItemStack[inventoryCrafting.getSizeInventory()];
        while (0 < array.length) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(0);
            if (stackInSlot != null && stackInSlot.getItem().hasContainerItem()) {
                array[0] = new ItemStack(stackInSlot.getItem().getContainerItem());
            }
            int n = 0;
            ++n;
        }
        return array;
    }
    
    static {
        __OBFID = "CL_00000083";
    }
}
