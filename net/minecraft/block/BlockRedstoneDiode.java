package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public abstract class BlockRedstoneDiode extends BlockDirectional
{
    protected final boolean isRepeaterPowered;
    private static final String __OBFID;
    
    protected BlockRedstoneDiode(final boolean isRepeaterPowered) {
        super(Material.circuits);
        this.isRepeaterPowered = isRepeaterPowered;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown()) && super.canPlaceBlockAt(world, blockPos);
    }
    
    public boolean func_176409_d(final World world, final BlockPos blockPos) {
        return World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown());
    }
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!this.func_176405_b(world, blockPos, blockState)) {
            final boolean func_176404_e = this.func_176404_e(world, blockPos, blockState);
            if (this.isRepeaterPowered && !func_176404_e) {
                world.setBlockState(blockPos, this.func_180675_k(blockState), 2);
            }
            else if (!this.isRepeaterPowered) {
                world.setBlockState(blockPos, this.func_180674_e(blockState), 2);
                if (!func_176404_e) {
                    world.func_175654_a(blockPos, this.func_180674_e(blockState).getBlock(), this.func_176399_m(blockState), -1);
                }
            }
        }
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return enumFacing.getAxis() != EnumFacing.Axis.Y;
    }
    
    protected boolean func_176406_l(final IBlockState blockState) {
        return this.isRepeaterPowered;
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return this.isProvidingWeakPower(blockAccess, blockPos, blockState, enumFacing);
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return this.func_176406_l(blockState) ? ((blockState.getValue(BlockRedstoneDiode.AGE) == enumFacing) ? this.func_176408_a(blockAccess, blockPos, blockState) : 0) : 0;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (this.func_176409_d(world, blockToAir)) {
            this.func_176398_g(world, blockToAir, blockState);
        }
        else {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                world.notifyNeighborsOfStateChange(blockToAir.offset(values[0]), this);
                int n = 0;
                ++n;
            }
        }
    }
    
    protected void func_176398_g(final World p0, final BlockPos p1, final IBlockState p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: aload_2        
        //     3: aload_3        
        //     4: invokevirtual   net/minecraft/block/BlockRedstoneDiode.func_176405_b:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z
        //     7: ifne            82
        //    10: aload_0        
        //    11: aload_1        
        //    12: aload_2        
        //    13: aload_3        
        //    14: invokevirtual   net/minecraft/block/BlockRedstoneDiode.func_176404_e:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z
        //    17: istore          4
        //    19: aload_0        
        //    20: getfield        net/minecraft/block/BlockRedstoneDiode.isRepeaterPowered:Z
        //    23: ifeq            31
        //    26: iload           4
        //    28: ifeq            43
        //    31: aload_0        
        //    32: getfield        net/minecraft/block/BlockRedstoneDiode.isRepeaterPowered:Z
        //    35: ifne            82
        //    38: iload           4
        //    40: ifeq            82
        //    43: aload_1        
        //    44: aload_2        
        //    45: aload_0        
        //    46: invokevirtual   net/minecraft/world/World.isBlockTickPending:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/Block;)Z
        //    49: ifne            82
        //    52: aload_0        
        //    53: aload_1        
        //    54: aload_2        
        //    55: aload_3        
        //    56: ifeq            62
        //    59: goto            69
        //    62: aload_0        
        //    63: getfield        net/minecraft/block/BlockRedstoneDiode.isRepeaterPowered:Z
        //    66: ifeq            69
        //    69: aload_1        
        //    70: aload_2        
        //    71: aload_0        
        //    72: aload_0        
        //    73: aload_3        
        //    74: invokevirtual   net/minecraft/block/BlockRedstoneDiode.func_176403_d:(Lnet/minecraft/block/state/IBlockState;)I
        //    77: bipush          -2
        //    79: invokevirtual   net/minecraft/world/World.func_175654_a:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/Block;II)V
        //    82: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0082 (coming from #0079).
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
    
    public boolean func_176405_b(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState) {
        return false;
    }
    
    protected int func_176397_f(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockRedstoneDiode.AGE);
        final BlockPos offset = blockPos.offset(enumFacing);
        final int redstonePower = world.getRedstonePower(offset, enumFacing);
        if (redstonePower >= 15) {
            return redstonePower;
        }
        final IBlockState blockState2 = world.getBlockState(offset);
        return Math.max(redstonePower, (blockState2.getBlock() == Blocks.redstone_wire) ? ((int)blockState2.getValue(BlockRedstoneWire.POWER)) : 0);
    }
    
    protected int func_176407_c(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState) {
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockRedstoneDiode.AGE);
        final EnumFacing rotateY = enumFacing.rotateY();
        final EnumFacing rotateYCCW = enumFacing.rotateYCCW();
        return Math.max(this.func_176401_c(blockAccess, blockPos.offset(rotateY), rotateY), this.func_176401_c(blockAccess, blockPos.offset(rotateYCCW), rotateYCCW));
    }
    
    protected int func_176401_c(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        return (int)(this.func_149908_a(block) ? ((block == Blocks.redstone_wire) ? blockState.getValue(BlockRedstoneWire.POWER) : blockAccess.getStrongPower(blockPos, enumFacing)) : 0);
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockRedstoneDiode.AGE, entityLivingBase.func_174811_aO().getOpposite());
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        if (blockState > 0) {
            world.scheduleUpdate(blockPos, this, 1);
        }
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.func_176400_h(world, blockPos, blockState);
    }
    
    protected void func_176400_h(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockRedstoneDiode.AGE);
        final BlockPos offset = blockPos.offset(enumFacing.getOpposite());
        world.notifyBlockOfStateChange(offset, this);
        world.notifyNeighborsOfStateExcept(offset, this, enumFacing);
    }
    
    @Override
    public void onBlockDestroyedByPlayer(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (this.isRepeaterPowered) {
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                world.notifyNeighborsOfStateChange(blockPos.offset(values[0]), this);
                int n = 0;
                ++n;
            }
        }
        super.onBlockDestroyedByPlayer(world, blockPos, blockState);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    protected boolean func_149908_a(final Block block) {
        return block.canProvidePower();
    }
    
    protected int func_176408_a(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState) {
        return 15;
    }
    
    public boolean func_149907_e(final Block block) {
        return block == this.func_180674_e(this.getDefaultState()).getBlock() || block == this.func_180675_k(this.getDefaultState()).getBlock();
    }
    
    protected int func_176399_m(final IBlockState blockState) {
        return this.func_176403_d(blockState);
    }
    
    protected abstract int func_176403_d(final IBlockState p0);
    
    protected abstract IBlockState func_180674_e(final IBlockState p0);
    
    protected abstract IBlockState func_180675_k(final IBlockState p0);
    
    @Override
    public boolean isAssociatedBlock(final Block block) {
        return this.func_149907_e(block);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    static {
        __OBFID = "CL_00000226";
    }
}
