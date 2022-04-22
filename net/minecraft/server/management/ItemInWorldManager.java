package net.minecraft.server.management;

import net.minecraft.network.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.network.play.server.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;

public class ItemInWorldManager
{
    public World theWorld;
    public EntityPlayerMP thisPlayerMP;
    private WorldSettings.GameType gameType;
    private boolean isDestroyingBlock;
    private int initialDamage;
    private BlockPos field_180240_f;
    private int curblockDamage;
    private boolean receivedFinishDiggingPacket;
    private BlockPos field_180241_i;
    private int initialBlockDamage;
    private int durabilityRemainingOnBlock;
    private static final String __OBFID;
    
    public ItemInWorldManager(final World theWorld) {
        this.gameType = WorldSettings.GameType.NOT_SET;
        this.field_180240_f = BlockPos.ORIGIN;
        this.field_180241_i = BlockPos.ORIGIN;
        this.durabilityRemainingOnBlock = -1;
        this.theWorld = theWorld;
    }
    
    public void setGameType(final WorldSettings.GameType gameType) {
        (this.gameType = gameType).configurePlayerCapabilities(this.thisPlayerMP.capabilities);
        this.thisPlayerMP.sendPlayerAbilities();
        this.thisPlayerMP.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.UPDATE_GAME_MODE, new EntityPlayerMP[] { this.thisPlayerMP }));
    }
    
    public WorldSettings.GameType getGameType() {
        return this.gameType;
    }
    
    public boolean func_180239_c() {
        return this.gameType.isSurvivalOrAdventure();
    }
    
    public boolean isCreative() {
        return this.gameType.isCreative();
    }
    
    public void initializeGameType(final WorldSettings.GameType gameType) {
        if (this.gameType == WorldSettings.GameType.NOT_SET) {
            this.gameType = gameType;
        }
        this.setGameType(this.gameType);
    }
    
    public void updateBlockRemoving() {
        ++this.curblockDamage;
        if (this.receivedFinishDiggingPacket) {
            final int n = this.curblockDamage - this.initialBlockDamage;
            final Block block = this.theWorld.getBlockState(this.field_180241_i).getBlock();
            if (block.getMaterial() == Material.air) {
                this.receivedFinishDiggingPacket = false;
            }
            else {
                final float n2 = block.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, this.field_180241_i) * (n + 1);
                final int durabilityRemainingOnBlock = (int)(n2 * 10.0f);
                if (durabilityRemainingOnBlock != this.durabilityRemainingOnBlock) {
                    this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180241_i, durabilityRemainingOnBlock);
                    this.durabilityRemainingOnBlock = durabilityRemainingOnBlock;
                }
                if (n2 >= 1.0f) {
                    this.receivedFinishDiggingPacket = false;
                    this.func_180237_b(this.field_180241_i);
                }
            }
        }
        else if (this.isDestroyingBlock) {
            final Block block2 = this.theWorld.getBlockState(this.field_180240_f).getBlock();
            if (block2.getMaterial() == Material.air) {
                this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, -1);
                this.durabilityRemainingOnBlock = -1;
                this.isDestroyingBlock = false;
            }
            else {
                final int durabilityRemainingOnBlock2 = (int)(block2.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, this.field_180241_i) * (this.curblockDamage - this.initialDamage + 1) * 10.0f);
                if (durabilityRemainingOnBlock2 != this.durabilityRemainingOnBlock) {
                    this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, durabilityRemainingOnBlock2);
                    this.durabilityRemainingOnBlock = durabilityRemainingOnBlock2;
                }
            }
        }
    }
    
    public void func_180784_a(final BlockPos field_180240_f, final EnumFacing enumFacing) {
        if (this.isCreative()) {
            if (!this.theWorld.func_175719_a(null, field_180240_f, enumFacing)) {
                this.func_180237_b(field_180240_f);
            }
        }
        else {
            final Block block = this.theWorld.getBlockState(field_180240_f).getBlock();
            if (this.gameType.isAdventure()) {
                if (this.gameType == WorldSettings.GameType.SPECTATOR) {
                    return;
                }
                if (!this.thisPlayerMP.func_175142_cm()) {
                    final ItemStack currentEquippedItem = this.thisPlayerMP.getCurrentEquippedItem();
                    if (currentEquippedItem == null) {
                        return;
                    }
                    if (!currentEquippedItem.canDestroy(block)) {
                        return;
                    }
                }
            }
            this.theWorld.func_175719_a(null, field_180240_f, enumFacing);
            this.initialDamage = this.curblockDamage;
            float playerRelativeBlockHardness = 1.0f;
            if (block.getMaterial() != Material.air) {
                block.onBlockClicked(this.theWorld, field_180240_f, this.thisPlayerMP);
                playerRelativeBlockHardness = block.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, field_180240_f);
            }
            if (block.getMaterial() != Material.air && playerRelativeBlockHardness >= 1.0f) {
                this.func_180237_b(field_180240_f);
            }
            else {
                this.isDestroyingBlock = true;
                this.field_180240_f = field_180240_f;
                final int durabilityRemainingOnBlock = (int)(playerRelativeBlockHardness * 10.0f);
                this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), field_180240_f, durabilityRemainingOnBlock);
                this.durabilityRemainingOnBlock = durabilityRemainingOnBlock;
            }
        }
    }
    
    public void func_180785_a(final BlockPos field_180241_i) {
        if (field_180241_i.equals(this.field_180240_f)) {
            final int n = this.curblockDamage - this.initialDamage;
            final Block block = this.theWorld.getBlockState(field_180241_i).getBlock();
            if (block.getMaterial() != Material.air) {
                if (block.getPlayerRelativeBlockHardness(this.thisPlayerMP, this.thisPlayerMP.worldObj, field_180241_i) * (n + 1) >= 0.7f) {
                    this.isDestroyingBlock = false;
                    this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), field_180241_i, -1);
                    this.func_180237_b(field_180241_i);
                }
                else if (!this.receivedFinishDiggingPacket) {
                    this.isDestroyingBlock = false;
                    this.receivedFinishDiggingPacket = true;
                    this.field_180241_i = field_180241_i;
                    this.initialBlockDamage = this.initialDamage;
                }
            }
        }
    }
    
    public void func_180238_e() {
        this.isDestroyingBlock = false;
        this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, -1);
    }
    
    private boolean func_180235_c(final BlockPos blockToAir) {
        final IBlockState blockState = this.theWorld.getBlockState(blockToAir);
        blockState.getBlock().onBlockHarvested(this.theWorld, blockToAir, blockState, this.thisPlayerMP);
        final boolean setBlockToAir = this.theWorld.setBlockToAir(blockToAir);
        if (setBlockToAir) {
            blockState.getBlock().onBlockDestroyedByPlayer(this.theWorld, blockToAir, blockState);
        }
        return setBlockToAir;
    }
    
    public boolean func_180237_b(final BlockPos blockPos) {
        if (this.gameType.isCreative() && this.thisPlayerMP.getHeldItem() != null && this.thisPlayerMP.getHeldItem().getItem() instanceof ItemSword) {
            return false;
        }
        final IBlockState blockState = this.theWorld.getBlockState(blockPos);
        final TileEntity tileEntity = this.theWorld.getTileEntity(blockPos);
        if (this.gameType.isAdventure()) {
            if (this.gameType == WorldSettings.GameType.SPECTATOR) {
                return false;
            }
            if (!this.thisPlayerMP.func_175142_cm()) {
                final ItemStack currentEquippedItem = this.thisPlayerMP.getCurrentEquippedItem();
                if (currentEquippedItem == null) {
                    return false;
                }
                if (!currentEquippedItem.canDestroy(blockState.getBlock())) {
                    return false;
                }
            }
        }
        this.theWorld.playAuxSFXAtEntity(this.thisPlayerMP, 2001, blockPos, Block.getStateId(blockState));
        final boolean func_180235_c = this.func_180235_c(blockPos);
        if (this.isCreative()) {
            this.thisPlayerMP.playerNetServerHandler.sendPacket(new S23PacketBlockChange(this.theWorld, blockPos));
        }
        else {
            final ItemStack currentEquippedItem2 = this.thisPlayerMP.getCurrentEquippedItem();
            final boolean canHarvestBlock = this.thisPlayerMP.canHarvestBlock(blockState.getBlock());
            if (currentEquippedItem2 != null) {
                currentEquippedItem2.onBlockDestroyed(this.theWorld, blockState.getBlock(), blockPos, this.thisPlayerMP);
                if (currentEquippedItem2.stackSize == 0) {
                    this.thisPlayerMP.destroyCurrentEquippedItem();
                }
            }
            if (func_180235_c && canHarvestBlock) {
                blockState.getBlock().harvestBlock(this.theWorld, this.thisPlayerMP, blockPos, blockState, tileEntity);
            }
        }
        return func_180235_c;
    }
    
    public boolean tryUseItem(final EntityPlayer entityPlayer, final World world, final ItemStack itemStack) {
        if (this.gameType == WorldSettings.GameType.SPECTATOR) {
            return false;
        }
        final int stackSize = itemStack.stackSize;
        final int metadata = itemStack.getMetadata();
        final ItemStack useItemRightClick = itemStack.useItemRightClick(world, entityPlayer);
        if (useItemRightClick == itemStack && (useItemRightClick == null || (useItemRightClick.stackSize == stackSize && useItemRightClick.getMaxItemUseDuration() <= 0 && useItemRightClick.getMetadata() == metadata))) {
            return false;
        }
        entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = useItemRightClick;
        if (this.isCreative()) {
            useItemRightClick.stackSize = stackSize;
            if (useItemRightClick.isItemStackDamageable()) {
                useItemRightClick.setItemDamage(metadata);
            }
        }
        if (useItemRightClick.stackSize == 0) {
            entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = null;
        }
        if (!entityPlayer.isUsingItem()) {
            ((EntityPlayerMP)entityPlayer).sendContainerToPlayer(entityPlayer.inventoryContainer);
        }
        return true;
    }
    
    public boolean func_180236_a(final EntityPlayer entityPlayer, final World world, final ItemStack itemStack, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (this.gameType == WorldSettings.GameType.SPECTATOR) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof ILockableContainer) {
                final Block block = world.getBlockState(blockPos).getBlock();
                ILockableContainer lockableContainer = (ILockableContainer)tileEntity;
                if (lockableContainer instanceof TileEntityChest && block instanceof BlockChest) {
                    lockableContainer = ((BlockChest)block).getLockableContainer(world, blockPos);
                }
                if (lockableContainer != null) {
                    entityPlayer.displayGUIChest(lockableContainer);
                    return true;
                }
            }
            else if (tileEntity instanceof IInventory) {
                entityPlayer.displayGUIChest((IInventory)tileEntity);
                return true;
            }
            return false;
        }
        if (!entityPlayer.isSneaking() || entityPlayer.getHeldItem() == null) {
            final IBlockState blockState = world.getBlockState(blockPos);
            if (blockState.getBlock().onBlockActivated(world, blockPos, blockState, entityPlayer, enumFacing, n, n2, n3)) {
                return true;
            }
        }
        if (itemStack == null) {
            return false;
        }
        if (this.isCreative()) {
            final int metadata = itemStack.getMetadata();
            final int stackSize = itemStack.stackSize;
            final boolean onItemUse = itemStack.onItemUse(entityPlayer, world, blockPos, enumFacing, n, n2, n3);
            itemStack.setItemDamage(metadata);
            itemStack.stackSize = stackSize;
            return onItemUse;
        }
        return itemStack.onItemUse(entityPlayer, world, blockPos, enumFacing, n, n2, n3);
    }
    
    public void setWorld(final WorldServer theWorld) {
        this.theWorld = theWorld;
    }
    
    static {
        __OBFID = "CL_00001442";
    }
}
