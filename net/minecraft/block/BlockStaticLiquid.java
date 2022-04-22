package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import java.util.*;

public class BlockStaticLiquid extends BlockLiquid
{
    private static final String __OBFID;
    
    protected BlockStaticLiquid(final Material material) {
        super(material);
        this.setTickRandomly(false);
        if (material == Material.lava) {
            this.setTickRandomly(true);
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!this.func_176365_e(world, blockPos, blockState)) {
            this.updateLiquid(world, blockPos, blockState);
        }
    }
    
    private void updateLiquid(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final BlockDynamicLiquid dynamicLiquidForMaterial = BlockLiquid.getDynamicLiquidForMaterial(this.blockMaterial);
        world.setBlockState(blockPos, dynamicLiquidForMaterial.getDefaultState().withProperty(BlockStaticLiquid.LEVEL, blockState.getValue(BlockStaticLiquid.LEVEL)), 2);
        world.scheduleUpdate(blockPos, dynamicLiquidForMaterial, this.tickRate(world));
    }
    
    @Override
    public void updateTick(final World p0, final BlockPos p1, final IBlockState p2, final Random p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/block/BlockStaticLiquid.blockMaterial:Lnet/minecraft/block/material/Material;
        //     4: getstatic       net/minecraft/block/material/Material.lava:Lnet/minecraft/block/material/Material;
        //     7: if_acmpne       201
        //    10: aload_1        
        //    11: invokevirtual   net/minecraft/world/World.getGameRules:()Lnet/minecraft/world/GameRules;
        //    14: ldc             "doFireTick"
        //    16: invokevirtual   net/minecraft/world/GameRules.getGameRuleBooleanValue:(Ljava/lang/String;)Z
        //    19: ifeq            201
        //    22: aload           4
        //    24: iconst_3       
        //    25: invokevirtual   java/util/Random.nextInt:(I)I
        //    28: istore          5
        //    30: iload           5
        //    32: ifle            134
        //    35: aload_2        
        //    36: astore          6
        //    38: goto            125
        //    41: aload           6
        //    43: aload           4
        //    45: iconst_3       
        //    46: invokevirtual   java/util/Random.nextInt:(I)I
        //    49: iconst_1       
        //    50: isub           
        //    51: iconst_1       
        //    52: aload           4
        //    54: iconst_3       
        //    55: invokevirtual   java/util/Random.nextInt:(I)I
        //    58: iconst_1       
        //    59: isub           
        //    60: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    63: astore          6
        //    65: aload_1        
        //    66: aload           6
        //    68: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    71: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    76: astore          8
        //    78: aload           8
        //    80: getfield        net/minecraft/block/Block.blockMaterial:Lnet/minecraft/block/material/Material;
        //    83: getstatic       net/minecraft/block/material/Material.air:Lnet/minecraft/block/material/Material;
        //    86: if_acmpne       110
        //    89: aload_0        
        //    90: aload_1        
        //    91: aload           6
        //    93: ifeq            122
        //    96: aload_1        
        //    97: aload           6
        //    99: getstatic       net/minecraft/init/Blocks.fire:Lnet/minecraft/block/BlockFire;
        //   102: invokevirtual   net/minecraft/block/BlockFire.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   105: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z
        //   108: pop            
        //   109: return         
        //   110: aload           8
        //   112: getfield        net/minecraft/block/Block.blockMaterial:Lnet/minecraft/block/material/Material;
        //   115: invokevirtual   net/minecraft/block/material/Material.blocksMovement:()Z
        //   118: ifeq            122
        //   121: return         
        //   122: iinc            7, 1
        //   125: iconst_0       
        //   126: iload           5
        //   128: if_icmplt       41
        //   131: goto            201
        //   134: goto            201
        //   137: aload_2        
        //   138: aload           4
        //   140: iconst_3       
        //   141: invokevirtual   java/util/Random.nextInt:(I)I
        //   144: iconst_1       
        //   145: isub           
        //   146: iconst_0       
        //   147: aload           4
        //   149: iconst_3       
        //   150: invokevirtual   java/util/Random.nextInt:(I)I
        //   153: iconst_1       
        //   154: isub           
        //   155: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   158: astore          7
        //   160: aload_1        
        //   161: aload           7
        //   163: invokevirtual   net/minecraft/util/BlockPos.offsetUp:()Lnet/minecraft/util/BlockPos;
        //   166: invokevirtual   net/minecraft/world/World.isAirBlock:(Lnet/minecraft/util/BlockPos;)Z
        //   169: ifeq            198
        //   172: aload_0        
        //   173: aload_1        
        //   174: aload           7
        //   176: invokespecial   net/minecraft/block/BlockStaticLiquid.getCanBlockBurn:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Z
        //   179: ifeq            198
        //   182: aload_1        
        //   183: aload           7
        //   185: invokevirtual   net/minecraft/util/BlockPos.offsetUp:()Lnet/minecraft/util/BlockPos;
        //   188: getstatic       net/minecraft/init/Blocks.fire:Lnet/minecraft/block/BlockFire;
        //   191: invokevirtual   net/minecraft/block/BlockFire.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   194: invokevirtual   net/minecraft/world/World.setBlockState:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z
        //   197: pop            
        //   198: iinc            6, 1
        //   201: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0122 (coming from #0093).
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
    
    private boolean getCanBlockBurn(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos).getBlock().getMaterial().getCanBurn();
    }
    
    static {
        __OBFID = "CL_00000315";
    }
}
