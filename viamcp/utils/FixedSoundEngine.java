package viamcp.utils;

import net.minecraft.client.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import viamcp.*;

public class FixedSoundEngine
{
    private static final Minecraft mc;
    
    static {
        mc = Minecraft.getMinecraft();
    }
    
    public static boolean destroyBlock(final World world, final BlockPos blockPos, final boolean b) {
        final IBlockState blockState = world.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        world.playAuxSFX(2001, blockPos, Block.getStateId(blockState));
        if (block.getMaterial() == Material.air) {
            return false;
        }
        if (b) {
            block.dropBlockAsItem(world, blockPos, blockState, 0);
        }
        return world.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
    }
    
    public static boolean onItemUse(final ItemBlock itemBlock, final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!world.getBlockState(offset).getBlock().isReplaceable(world, offset)) {
            offset = offset.offset(enumFacing);
        }
        if (itemStack.stackSize == 0) {
            return false;
        }
        if (!entityPlayer.canPlayerEdit(offset, enumFacing, itemStack)) {
            return false;
        }
        if (world.canBlockBePlaced(itemBlock.getBlock(), offset, false, enumFacing, null, itemStack)) {
            if (world.setBlockState(offset, itemBlock.getBlock().onBlockPlaced(world, offset, enumFacing, n, n2, n3, itemBlock.getMetadata(itemStack.getMetadata()), entityPlayer), 3)) {
                final IBlockState blockState = world.getBlockState(offset);
                if (blockState.getBlock() == itemBlock.getBlock()) {
                    ItemBlock.setTileEntityNBT(world, entityPlayer, offset, itemStack);
                    itemBlock.getBlock().onBlockPlacedBy(world, offset, blockState, entityPlayer, itemStack);
                }
                if (ViaMCP.getInstance().getVersion() != 47) {
                    Minecraft.theWorld.playSoundAtPos(offset.add(0.5, 0.5, 0.5), itemBlock.getBlock().stepSound.getPlaceSound(), (itemBlock.getBlock().stepSound.getVolume() + 1.0f) / 2.0f, itemBlock.getBlock().stepSound.getFrequency() * 0.8f, false);
                }
                else {
                    world.playSoundEffect(offset.getX() + 0.5f, offset.getY() + 0.5f, offset.getZ() + 0.5f, itemBlock.getBlock().stepSound.getPlaceSound(), (itemBlock.getBlock().stepSound.getVolume() + 1.0f) / 2.0f, itemBlock.getBlock().stepSound.getFrequency() * 0.8f);
                }
                --itemStack.stackSize;
            }
            return true;
        }
        return false;
    }
}
