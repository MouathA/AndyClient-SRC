package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.stats.*;

public class ItemEnderEye extends Item
{
    private static final String __OBFID;
    
    public ItemEnderEye() {
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public boolean onItemUse(final ItemStack p0, final EntityPlayer p1, final World p2, final BlockPos p3, final EnumFacing p4, final float p5, final float p6, final float p7) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload           4
        //     3: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //     6: astore          9
        //     8: aload_2        
        //     9: aload           4
        //    11: aload           5
        //    13: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //    16: aload           5
        //    18: aload_1        
        //    19: invokevirtual   net/minecraft/entity/player/EntityPlayer.func_175151_a:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/item/ItemStack;)Z
        //    22: ifeq            534
        //    25: aload           9
        //    27: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    32: getstatic       net/minecraft/init/Blocks.end_portal_frame:Lnet/minecraft/block/Block;
        //    35: if_acmpne       534
        //    38: aload           9
        //    40: getstatic       net/minecraft/block/BlockEndPortalFrame.field_176507_b:Lnet/minecraft/block/properties/PropertyBool;
        //    43: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //    48: checkcast       Ljava/lang/Boolean;
        //    51: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //    54: ifne            534
        //    57: aload_3        
        //    58: getfield        net/minecraft/world/World.isRemote:Z
        //    61: ifeq            66
        //    64: iconst_1       
        //    65: ireturn        
        //    66: aload_3        
        //    67: aload           4
        //    69: aload           9
        //    71: getstatic       net/minecraft/block/BlockEndPortalFrame.field_176507_b:Lnet/minecraft/block/properties/PropertyBool;
        //    74: iconst_1       
        //    75: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    78: invokeinterface net/minecraft/block/state/IBlockState.withProperty:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;
        //    83: iconst_2       
        //    84: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //    87: pop            
        //    88: aload_3        
        //    89: aload           4
        //    91: getstatic       net/minecraft/init/Blocks.end_portal_frame:Lnet/minecraft/block/Block;
        //    94: invokevirtual   net/minecraft/world/World.updateComparatorOutputLevel:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/Block;)V
        //    97: aload_1        
        //    98: dup            
        //    99: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   102: iconst_1       
        //   103: isub           
        //   104: putfield        net/minecraft/item/ItemStack.stackSize:I
        //   107: goto            206
        //   110: aload           4
        //   112: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   115: i2f            
        //   116: ldc             5.0
        //   118: getstatic       net/minecraft/item/ItemEnderEye.itemRand:Ljava/util/Random;
        //   121: invokevirtual   java/util/Random.nextFloat:()F
        //   124: ldc             6.0
        //   126: fmul           
        //   127: fadd           
        //   128: ldc             16.0
        //   130: fdiv           
        //   131: fadd           
        //   132: f2d            
        //   133: dstore          11
        //   135: aload           4
        //   137: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   140: i2f            
        //   141: ldc             0.8125
        //   143: fadd           
        //   144: f2d            
        //   145: dstore          13
        //   147: aload           4
        //   149: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   152: i2f            
        //   153: ldc             5.0
        //   155: getstatic       net/minecraft/item/ItemEnderEye.itemRand:Ljava/util/Random;
        //   158: invokevirtual   java/util/Random.nextFloat:()F
        //   161: ldc             6.0
        //   163: fmul           
        //   164: fadd           
        //   165: ldc             16.0
        //   167: fdiv           
        //   168: fadd           
        //   169: f2d            
        //   170: dstore          15
        //   172: dconst_0       
        //   173: dstore          17
        //   175: dconst_0       
        //   176: dstore          19
        //   178: dconst_0       
        //   179: dstore          21
        //   181: aload_3        
        //   182: getstatic       net/minecraft/util/EnumParticleTypes.SMOKE_NORMAL:Lnet/minecraft/util/EnumParticleTypes;
        //   185: dload           11
        //   187: dload           13
        //   189: dload           15
        //   191: dload           17
        //   193: dload           19
        //   195: dload           21
        //   197: iconst_0       
        //   198: newarray        I
        //   200: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   203: iinc            10, 1
        //   206: iconst_0       
        //   207: bipush          16
        //   209: if_icmplt       110
        //   212: aload           9
        //   214: getstatic       net/minecraft/block/BlockEndPortalFrame.field_176508_a:Lnet/minecraft/block/properties/PropertyDirection;
        //   217: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   222: checkcast       Lnet/minecraft/util/EnumFacing;
        //   225: astore          10
        //   227: aload           10
        //   229: invokevirtual   net/minecraft/util/EnumFacing.rotateY:()Lnet/minecraft/util/EnumFacing;
        //   232: astore          15
        //   234: goto            298
        //   237: aload           4
        //   239: aload           15
        //   241: bipush          -2
        //   243: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/BlockPos;
        //   246: astore          17
        //   248: aload_3        
        //   249: aload           17
        //   251: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   254: astore          18
        //   256: aload           18
        //   258: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   263: getstatic       net/minecraft/init/Blocks.end_portal_frame:Lnet/minecraft/block/Block;
        //   266: if_acmpne       295
        //   269: aload           18
        //   271: getstatic       net/minecraft/block/BlockEndPortalFrame.field_176507_b:Lnet/minecraft/block/properties/PropertyBool;
        //   274: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   279: checkcast       Ljava/lang/Boolean;
        //   282: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   285: ifne            291
        //   288: goto            304
        //   291: iconst_1       
        //   292: ifne            295
        //   295: iinc            16, 1
        //   298: bipush          -2
        //   300: iconst_2       
        //   301: if_icmple       237
        //   304: iconst_0       
        //   305: ifeq            532
        //   308: iconst_0       
        //   309: iconst_2       
        //   310: if_icmpne       532
        //   313: aload           4
        //   315: aload           10
        //   317: iconst_4       
        //   318: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/BlockPos;
        //   321: astore          16
        //   323: goto            382
        //   326: aload           16
        //   328: aload           15
        //   330: iconst_0       
        //   331: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/BlockPos;
        //   334: astore          18
        //   336: aload_3        
        //   337: aload           18
        //   339: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   342: astore          19
        //   344: aload           19
        //   346: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   351: getstatic       net/minecraft/init/Blocks.end_portal_frame:Lnet/minecraft/block/Block;
        //   354: if_acmpne       376
        //   357: aload           19
        //   359: getstatic       net/minecraft/block/BlockEndPortalFrame.field_176507_b:Lnet/minecraft/block/properties/PropertyBool;
        //   362: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   367: checkcast       Ljava/lang/Boolean;
        //   370: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   373: ifne            379
        //   376: goto            387
        //   379: iinc            17, 1
        //   382: iconst_0       
        //   383: iconst_0       
        //   384: if_icmple       326
        //   387: goto            467
        //   390: aload           4
        //   392: aload           15
        //   394: iconst_0       
        //   395: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/BlockPos;
        //   398: astore          16
        //   400: goto            459
        //   403: aload           16
        //   405: aload           10
        //   407: iconst_1       
        //   408: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/BlockPos;
        //   411: astore          19
        //   413: aload_3        
        //   414: aload           19
        //   416: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   419: astore          20
        //   421: aload           20
        //   423: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   428: getstatic       net/minecraft/init/Blocks.end_portal_frame:Lnet/minecraft/block/Block;
        //   431: if_acmpne       453
        //   434: aload           20
        //   436: getstatic       net/minecraft/block/BlockEndPortalFrame.field_176507_b:Lnet/minecraft/block/properties/PropertyBool;
        //   439: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //   444: checkcast       Ljava/lang/Boolean;
        //   447: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   450: ifne            456
        //   453: goto            464
        //   456: iinc            18, 1
        //   459: iconst_1       
        //   460: iconst_3       
        //   461: if_icmple       403
        //   464: iinc            17, 4
        //   467: iconst_0       
        //   468: iconst_1       
        //   469: if_icmple       390
        //   472: iconst_0       
        //   473: ifeq            532
        //   476: goto            527
        //   479: aload           4
        //   481: aload           15
        //   483: iconst_0       
        //   484: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/BlockPos;
        //   487: astore          16
        //   489: goto            519
        //   492: aload           16
        //   494: aload           10
        //   496: iconst_1       
        //   497: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/BlockPos;
        //   500: astore          19
        //   502: aload_3        
        //   503: aload           19
        //   505: getstatic       net/minecraft/init/Blocks.end_portal:Lnet/minecraft/block/Block;
        //   508: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   511: iconst_2       
        //   512: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z
        //   515: pop            
        //   516: iinc            18, 1
        //   519: iconst_1       
        //   520: iconst_3       
        //   521: if_icmple       492
        //   524: iinc            17, 1
        //   527: iconst_0       
        //   528: iconst_0       
        //   529: if_icmple       479
        //   532: iconst_1       
        //   533: ireturn        
        //   534: iconst_0       
        //   535: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        final MovingObjectPosition movingObjectPositionFromPlayer = this.getMovingObjectPositionFromPlayer(world, entityPlayer, false);
        if (movingObjectPositionFromPlayer != null && movingObjectPositionFromPlayer.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && world.getBlockState(movingObjectPositionFromPlayer.func_178782_a()).getBlock() == Blocks.end_portal_frame) {
            return itemStack;
        }
        if (!world.isRemote) {
            final BlockPos func_180499_a = world.func_180499_a("Stronghold", new BlockPos(entityPlayer));
            if (func_180499_a != null) {
                final EntityEnderEye entityEnderEye = new EntityEnderEye(world, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);
                entityEnderEye.func_180465_a(func_180499_a);
                world.spawnEntityInWorld(entityEnderEye);
                world.playSoundAtEntity(entityPlayer, "random.bow", 0.5f, 0.4f / (ItemEnderEye.itemRand.nextFloat() * 0.4f + 0.8f));
                world.playAuxSFXAtEntity(null, 1002, new BlockPos(entityPlayer), 0);
                if (!entityPlayer.capabilities.isCreativeMode) {
                    --itemStack.stackSize;
                }
                entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            }
        }
        return itemStack;
    }
    
    static {
        __OBFID = "CL_00000026";
    }
}
