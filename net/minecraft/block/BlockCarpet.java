package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.block.state.*;

public class BlockCarpet extends Block
{
    public static final PropertyEnum field_176330_a;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000338";
        field_176330_a = PropertyEnum.create("color", EnumDyeColor.class);
    }
    
    protected BlockCarpet() {
        super(Material.carpet);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockCarpet.field_176330_a, EnumDyeColor.WHITE));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBoundsFromMeta(0);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBoundsFromMeta(0);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBlockBoundsFromMeta(0);
    }
    
    protected void setBlockBoundsFromMeta(final int n) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1 / 16.0f, 1.0f);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p0, final BlockPos p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: aload_2        
        //     3: invokespecial   net/minecraft/block/Block.canPlaceBlockAt:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Z
        //     6: ifeq            17
        //     9: aload_0        
        //    10: aload_1        
        //    11: aload_2        
        //    12: ifeq            17
        //    15: iconst_1       
        //    16: ireturn        
        //    17: iconst_0       
        //    18: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0017 (coming from #0012).
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
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.checkAndDropBlock(world, blockPos, blockState);
    }
    
    private boolean checkAndDropBlock(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        if (blockToAir != 0) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
            return false;
        }
        return true;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return enumFacing == EnumFacing.UP || super.shouldSideBeRendered(blockAccess, blockPos, enumFacing);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumDyeColor)blockState.getValue(BlockCarpet.field_176330_a)).func_176765_a();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockCarpet.field_176330_a, EnumDyeColor.func_176764_b(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumDyeColor)blockState.getValue(BlockCarpet.field_176330_a)).func_176765_a();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockCarpet.field_176330_a });
    }
}
