package net.minecraft.client.multiplayer;

import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.network.*;
import net.minecraft.client.audio.*;
import net.minecraft.client.entity.*;
import net.minecraft.item.*;
import net.minecraft.stats.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.passive.*;

public class PlayerControllerMP
{
    private final Minecraft mc;
    private final NetHandlerPlayClient netClientHandler;
    private BlockPos field_178895_c;
    private ItemStack currentItemHittingBlock;
    private float curBlockDamageMP;
    private float stepSoundTickCounter;
    public static int blockHitDelay;
    private boolean isHittingBlock;
    private WorldSettings.GameType currentGameType;
    public static int currentPlayerItem;
    private static final String __OBFID;
    
    public PlayerControllerMP(final Minecraft mc, final NetHandlerPlayClient netClientHandler) {
        this.field_178895_c = new BlockPos(-1, -1, -1);
        this.currentGameType = WorldSettings.GameType.SURVIVAL;
        this.mc = mc;
        this.netClientHandler = netClientHandler;
    }
    
    public static void func_178891_a(final Minecraft minecraft, final PlayerControllerMP playerControllerMP, final BlockPos blockPos, final EnumFacing enumFacing) {
        if (!Minecraft.theWorld.func_175719_a(Minecraft.thePlayer, blockPos, enumFacing)) {
            playerControllerMP.func_178888_a(blockPos, enumFacing);
        }
    }
    
    public void setPlayerCapabilities(final EntityPlayer entityPlayer) {
        this.currentGameType.configurePlayerCapabilities(entityPlayer.capabilities);
    }
    
    public boolean enableEverythingIsScrewedUpMode() {
        return this.currentGameType == WorldSettings.GameType.SPECTATOR;
    }
    
    public void setGameType(final WorldSettings.GameType currentGameType) {
        (this.currentGameType = currentGameType).configurePlayerCapabilities(Minecraft.thePlayer.capabilities);
    }
    
    public void flipPlayer(final EntityPlayer entityPlayer) {
        entityPlayer.rotationYaw = -180.0f;
    }
    
    public boolean shouldDrawHUD() {
        return this.currentGameType.isSurvivalOrAdventure();
    }
    
    public boolean func_178888_a(final BlockPos blockToAir, final EnumFacing enumFacing) {
        if (this.currentGameType.isAdventure()) {
            if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
                return false;
            }
            if (!Minecraft.thePlayer.func_175142_cm()) {
                final Block block = Minecraft.theWorld.getBlockState(blockToAir).getBlock();
                final ItemStack currentEquippedItem = Minecraft.thePlayer.getCurrentEquippedItem();
                if (currentEquippedItem == null) {
                    return false;
                }
                if (!currentEquippedItem.canDestroy(block)) {
                    return false;
                }
            }
        }
        if (this.currentGameType.isCreative() && Minecraft.thePlayer.getHeldItem() != null && Minecraft.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
            return false;
        }
        final WorldClient theWorld = Minecraft.theWorld;
        final IBlockState blockState = theWorld.getBlockState(blockToAir);
        final Block block2 = blockState.getBlock();
        if (block2.getMaterial() == Material.air) {
            return false;
        }
        theWorld.playAuxSFX(2001, blockToAir, Block.getStateId(blockState));
        final boolean setBlockToAir = theWorld.setBlockToAir(blockToAir);
        if (setBlockToAir) {
            block2.onBlockDestroyedByPlayer(theWorld, blockToAir, blockState);
        }
        this.field_178895_c = new BlockPos(this.field_178895_c.getX(), -1, this.field_178895_c.getZ());
        if (!this.currentGameType.isCreative()) {
            final ItemStack currentEquippedItem2 = Minecraft.thePlayer.getCurrentEquippedItem();
            if (currentEquippedItem2 != null) {
                currentEquippedItem2.onBlockDestroyed(theWorld, block2, blockToAir, Minecraft.thePlayer);
                if (currentEquippedItem2.stackSize == 0) {
                    Minecraft.thePlayer.destroyCurrentEquippedItem();
                }
            }
        }
        return setBlockToAir;
    }
    
    public boolean func_180511_b(final BlockPos field_178895_c, final EnumFacing enumFacing) {
        if (this.currentGameType.isAdventure()) {
            if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
                return false;
            }
            if (!Minecraft.thePlayer.func_175142_cm()) {
                final Block block = Minecraft.theWorld.getBlockState(field_178895_c).getBlock();
                final ItemStack currentEquippedItem = Minecraft.thePlayer.getCurrentEquippedItem();
                if (currentEquippedItem == null) {
                    return false;
                }
                if (!currentEquippedItem.canDestroy(block)) {
                    return false;
                }
            }
        }
        if (!Minecraft.theWorld.getWorldBorder().contains(field_178895_c)) {
            return false;
        }
        if (this.currentGameType.isCreative()) {
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, field_178895_c, enumFacing));
            func_178891_a(this.mc, this, field_178895_c, enumFacing);
            PlayerControllerMP.blockHitDelay = 5;
        }
        else if (!this.isHittingBlock || !this.func_178893_a(field_178895_c)) {
            if (this.isHittingBlock) {
                this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.field_178895_c, enumFacing));
            }
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, field_178895_c, enumFacing));
            final Block block2 = Minecraft.theWorld.getBlockState(field_178895_c).getBlock();
            final boolean b = block2.getMaterial() != Material.air;
            if (b && this.curBlockDamageMP == 0.0f) {
                block2.onBlockClicked(Minecraft.theWorld, field_178895_c, Minecraft.thePlayer);
            }
            if (b && block2.getPlayerRelativeBlockHardness(Minecraft.thePlayer, Minecraft.thePlayer.worldObj, field_178895_c) >= 1.0f) {
                this.func_178888_a(field_178895_c, enumFacing);
            }
            else {
                this.isHittingBlock = true;
                this.field_178895_c = field_178895_c;
                this.currentItemHittingBlock = Minecraft.thePlayer.getHeldItem();
                this.curBlockDamageMP = 0.0f;
                this.stepSoundTickCounter = 0.0f;
                Minecraft.theWorld.sendBlockBreakProgress(Minecraft.thePlayer.getEntityId(), this.field_178895_c, (int)(this.curBlockDamageMP * 10.0f) - 1);
            }
        }
        return true;
    }
    
    public void resetBlockRemoving() {
        if (this.isHittingBlock) {
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.field_178895_c, EnumFacing.DOWN));
            this.isHittingBlock = false;
            this.curBlockDamageMP = 0.0f;
            Minecraft.theWorld.sendBlockBreakProgress(Minecraft.thePlayer.getEntityId(), this.field_178895_c, -1);
        }
    }
    
    public boolean func_180512_c(final BlockPos blockPos, final EnumFacing enumFacing) {
        this.syncCurrentPlayItem();
        if (PlayerControllerMP.blockHitDelay > 0) {
            --PlayerControllerMP.blockHitDelay;
            return true;
        }
        if (this.currentGameType.isCreative() && Minecraft.theWorld.getWorldBorder().contains(blockPos)) {
            PlayerControllerMP.blockHitDelay = 5;
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, enumFacing));
            func_178891_a(this.mc, this, blockPos, enumFacing);
            return true;
        }
        if (!this.func_178893_a(blockPos)) {
            return this.func_180511_b(blockPos, enumFacing);
        }
        final Block block = Minecraft.theWorld.getBlockState(blockPos).getBlock();
        if (block.getMaterial() == Material.air) {
            return this.isHittingBlock = false;
        }
        this.curBlockDamageMP += block.getPlayerRelativeBlockHardness(Minecraft.thePlayer, Minecraft.thePlayer.worldObj, blockPos);
        if (this.stepSoundTickCounter % 4.0f == 0.0f) {
            Minecraft.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(block.stepSound.getStepSound()), (block.stepSound.getVolume() + 1.0f) / 8.0f, block.stepSound.getFrequency() * 0.5f, blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f));
        }
        ++this.stepSoundTickCounter;
        if (this.curBlockDamageMP >= 1.0f) {
            this.isHittingBlock = false;
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, enumFacing));
            this.func_178888_a(blockPos, enumFacing);
            this.curBlockDamageMP = 0.0f;
            this.stepSoundTickCounter = 0.0f;
            PlayerControllerMP.blockHitDelay = 5;
        }
        Minecraft.theWorld.sendBlockBreakProgress(Minecraft.thePlayer.getEntityId(), this.field_178895_c, (int)(this.curBlockDamageMP * 10.0f) - 1);
        return true;
    }
    
    public float getBlockReachDistance() {
        return this.currentGameType.isCreative() ? 5.0f : 4.5f;
    }
    
    public void updateController() {
        this.syncCurrentPlayItem();
        if (this.netClientHandler.getNetworkManager().isChannelOpen()) {
            this.netClientHandler.getNetworkManager().processReceivedPackets();
        }
        else {
            this.netClientHandler.getNetworkManager().checkDisconnected();
        }
    }
    
    private boolean func_178893_a(final BlockPos blockPos) {
        final ItemStack heldItem = Minecraft.thePlayer.getHeldItem();
        boolean b = this.currentItemHittingBlock == null && heldItem == null;
        if (this.currentItemHittingBlock != null && heldItem != null) {
            b = (heldItem.getItem() == this.currentItemHittingBlock.getItem() && ItemStack.areItemStackTagsEqual(heldItem, this.currentItemHittingBlock) && (heldItem.isItemStackDamageable() || heldItem.getMetadata() == this.currentItemHittingBlock.getMetadata()));
        }
        return blockPos.equals(this.field_178895_c) && b;
    }
    
    private void syncCurrentPlayItem() {
        final int currentItem = Minecraft.thePlayer.inventory.currentItem;
        if (currentItem != PlayerControllerMP.currentPlayerItem) {
            PlayerControllerMP.currentPlayerItem = currentItem;
            this.netClientHandler.addToSendQueue(new C09PacketHeldItemChange(PlayerControllerMP.currentPlayerItem));
        }
    }
    
    public boolean func_178890_a(final EntityPlayerSP entityPlayerSP, final WorldClient worldClient, final ItemStack itemStack, final BlockPos blockPos, final EnumFacing enumFacing, final Vec3 vec3) {
        this.syncCurrentPlayItem();
        final float n = (float)(vec3.xCoord - blockPos.getX());
        final float n2 = (float)(vec3.yCoord - blockPos.getY());
        final float n3 = (float)(vec3.zCoord - blockPos.getZ());
        if (!Minecraft.theWorld.getWorldBorder().contains(blockPos)) {
            return false;
        }
        if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
            final IBlockState blockState = worldClient.getBlockState(blockPos);
            if ((entityPlayerSP.isSneaking() && entityPlayerSP.getHeldItem() != null) || blockState.getBlock().onBlockActivated(worldClient, blockPos, blockState, entityPlayerSP, enumFacing, n, n2, n3)) {}
            if (!true && itemStack != null && itemStack.getItem() instanceof ItemBlock && !((ItemBlock)itemStack.getItem()).canPlaceBlockOnSide(worldClient, blockPos, enumFacing, entityPlayerSP, itemStack)) {
                return false;
            }
        }
        this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(blockPos, enumFacing.getIndex(), entityPlayerSP.inventory.getCurrentItem(), n, n2, n3));
        if (true || this.currentGameType == WorldSettings.GameType.SPECTATOR) {
            return true;
        }
        if (itemStack == null) {
            return false;
        }
        if (this.currentGameType.isCreative()) {
            final int metadata = itemStack.getMetadata();
            final int stackSize = itemStack.stackSize;
            final boolean onItemUse = itemStack.onItemUse(entityPlayerSP, worldClient, blockPos, enumFacing, n, n2, n3);
            itemStack.setItemDamage(metadata);
            itemStack.stackSize = stackSize;
            return onItemUse;
        }
        return itemStack.onItemUse(entityPlayerSP, worldClient, blockPos, enumFacing, n, n2, n3);
    }
    
    public boolean sendUseItem(final EntityPlayer entityPlayer, final World world, final ItemStack itemStack) {
        if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
            return false;
        }
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(entityPlayer.inventory.getCurrentItem()));
        final int stackSize = itemStack.stackSize;
        final ItemStack useItemRightClick = itemStack.useItemRightClick(world, entityPlayer);
        if (useItemRightClick == itemStack && (useItemRightClick == null || useItemRightClick.stackSize == stackSize)) {
            return false;
        }
        entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = useItemRightClick;
        if (useItemRightClick.stackSize == 0) {
            entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = null;
        }
        return true;
    }
    
    public EntityPlayerSP func_178892_a(final World world, final StatFileWriter statFileWriter) {
        return new EntityPlayerSP(this.mc, world, this.netClientHandler, statFileWriter);
    }
    
    public void attackEntity(final EntityPlayer entityPlayer, final Entity entity) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
        if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
            entityPlayer.attackTargetEntityWithCurrentItem(entity);
        }
    }
    
    public boolean interactWithEntitySendPacket(final EntityPlayer entityPlayer, final Entity entity) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.INTERACT));
        return this.currentGameType != WorldSettings.GameType.SPECTATOR && entityPlayer.interactWith(entity);
    }
    
    public boolean func_178894_a(final EntityPlayer entityPlayer, final Entity entity, final MovingObjectPosition movingObjectPosition) {
        this.syncCurrentPlayItem();
        final Vec3 vec3 = new Vec3(movingObjectPosition.hitVec.xCoord - entity.posX, movingObjectPosition.hitVec.yCoord - entity.posY, movingObjectPosition.hitVec.zCoord - entity.posZ);
        this.netClientHandler.addToSendQueue(new C02PacketUseEntity(entity, vec3));
        return this.currentGameType != WorldSettings.GameType.SPECTATOR && entity.func_174825_a(entityPlayer, vec3);
    }
    
    public ItemStack windowClick(final int n, final int n2, final int n3, final int n4, final EntityPlayer entityPlayer) {
        final short nextTransactionID = entityPlayer.openContainer.getNextTransactionID(entityPlayer.inventory);
        final ItemStack slotClick = entityPlayer.openContainer.slotClick(n2, n3, n4, entityPlayer);
        this.netClientHandler.addToSendQueue(new C0EPacketClickWindow(n, n2, n3, n4, slotClick, nextTransactionID));
        return slotClick;
    }
    
    public void sendEnchantPacket(final int n, final int n2) {
        this.netClientHandler.addToSendQueue(new C11PacketEnchantItem(n, n2));
    }
    
    public void sendSlotPacket(final ItemStack itemStack, final int n) {
        if (this.currentGameType.isCreative()) {
            this.netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(n, itemStack));
        }
    }
    
    public void sendPacketDropItem(final ItemStack itemStack) {
        if (this.currentGameType.isCreative() && itemStack != null) {
            this.netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(-1, itemStack));
        }
    }
    
    public void onStoppedUsingItem(final EntityPlayer entityPlayer) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        entityPlayer.stopUsingItem();
    }
    
    public boolean gameIsSurvivalOrAdventure() {
        return this.currentGameType.isSurvivalOrAdventure();
    }
    
    public boolean isNotCreative() {
        return !this.currentGameType.isCreative();
    }
    
    public boolean isInCreativeMode() {
        return this.currentGameType.isCreative();
    }
    
    public boolean extendedReach() {
        return this.currentGameType.isCreative();
    }
    
    public boolean isRidingHorse() {
        return Minecraft.thePlayer.isRiding() && Minecraft.thePlayer.ridingEntity instanceof EntityHorse;
    }
    
    public boolean isSpectatorMode() {
        return this.currentGameType == WorldSettings.GameType.SPECTATOR;
    }
    
    public WorldSettings.GameType func_178889_l() {
        return this.currentGameType;
    }
    
    public boolean onPlayerRightClick(final EntityPlayer entityPlayer, final WorldClient worldClient, final ItemStack itemStack, final BlockPos blockPos, final EnumFacing enumFacing, final Vec3 vec3) {
        this.syncCurrentPlayItem();
        final float n = (float)(vec3.xCoord - blockPos.getX());
        final float n2 = (float)(vec3.yCoord - blockPos.getY());
        final float n3 = (float)(vec3.zCoord - blockPos.getZ());
        if (!Minecraft.theWorld.getWorldBorder().contains(blockPos)) {
            return false;
        }
        if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
            final IBlockState blockState = worldClient.getBlockState(blockPos);
            if ((entityPlayer.isSneaking() && entityPlayer.getHeldItem() != null) || blockState.getBlock().onBlockActivated(worldClient, blockPos, blockState, entityPlayer, enumFacing, n, n2, n3)) {}
            if (!true && itemStack != null && itemStack.getItem() instanceof ItemBlock && !((ItemBlock)itemStack.getItem()).canPlaceBlockOnSide(worldClient, blockPos, enumFacing, entityPlayer, itemStack)) {
                return false;
            }
        }
        this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(blockPos, enumFacing.getIndex(), entityPlayer.inventory.getCurrentItem(), n, n2, n3));
        if (true || this.currentGameType == WorldSettings.GameType.SPECTATOR) {
            return true;
        }
        if (itemStack == null) {
            return false;
        }
        if (this.currentGameType.isCreative()) {
            final int metadata = itemStack.getMetadata();
            final int stackSize = itemStack.stackSize;
            final boolean onItemUse = itemStack.onItemUse(entityPlayer, worldClient, blockPos, enumFacing, n, n2, n3);
            itemStack.setItemDamage(metadata);
            itemStack.stackSize = stackSize;
            return onItemUse;
        }
        return itemStack.onItemUse(entityPlayer, worldClient, blockPos, enumFacing, n, n2, n3);
    }
    
    public WorldSettings.GameType getCurrentGameType() {
        return this.currentGameType;
    }
    
    static {
        __OBFID = "CL_00000881";
    }
}
