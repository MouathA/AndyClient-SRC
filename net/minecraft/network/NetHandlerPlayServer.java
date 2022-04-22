package net.minecraft.network;

import net.minecraft.network.play.*;
import net.minecraft.server.gui.*;
import net.minecraft.server.*;
import org.apache.logging.log4j.*;
import io.netty.util.concurrent.*;
import com.google.common.util.concurrent.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import org.apache.commons.lang3.*;
import net.minecraft.util.*;
import net.minecraft.command.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.server.management.*;
import net.minecraft.stats.*;
import com.google.common.collect.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;
import io.netty.buffer.*;
import java.io.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.inventory.*;
import net.minecraft.command.server.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class NetHandlerPlayServer implements INetHandlerPlayServer, IUpdatePlayerListBox
{
    private static final Logger logger;
    public final NetworkManager netManager;
    private final MinecraftServer serverController;
    public EntityPlayerMP playerEntity;
    private int networkTickCount;
    private int field_175090_f;
    private int floatingTickCount;
    private boolean field_147366_g;
    private int field_147378_h;
    private long lastPingTime;
    private long lastSentPingPacket;
    private int chatSpamThresholdCount;
    private int itemDropThreshold;
    private IntHashMap field_147372_n;
    private double lastPosX;
    private double lastPosY;
    private double lastPosZ;
    private boolean hasMoved;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001452";
        logger = LogManager.getLogger();
    }
    
    public NetHandlerPlayServer(final MinecraftServer serverController, final NetworkManager netManager, final EntityPlayerMP playerEntity) {
        this.field_147372_n = new IntHashMap();
        this.hasMoved = true;
        this.serverController = serverController;
        (this.netManager = netManager).setNetHandler(this);
        this.playerEntity = playerEntity;
        playerEntity.playerNetServerHandler = this;
    }
    
    @Override
    public void update() {
        this.field_147366_g = false;
        ++this.networkTickCount;
        this.serverController.theProfiler.startSection("keepAlive");
        if (this.networkTickCount - this.lastSentPingPacket > 40L) {
            this.lastSentPingPacket = this.networkTickCount;
            this.lastPingTime = this.currentTimeMillis();
            this.field_147378_h = (int)this.lastPingTime;
            this.sendPacket(new S00PacketKeepAlive(this.field_147378_h));
        }
        this.serverController.theProfiler.endSection();
        if (this.chatSpamThresholdCount > 0) {
            --this.chatSpamThresholdCount;
        }
        if (this.itemDropThreshold > 0) {
            --this.itemDropThreshold;
        }
        if (this.playerEntity.getLastActiveTime() > 0L && this.serverController.getMaxPlayerIdleMinutes() > 0 && MinecraftServer.getCurrentTimeMillis() - this.playerEntity.getLastActiveTime() > this.serverController.getMaxPlayerIdleMinutes() * 1000 * 60) {
            this.kickPlayerFromServer("You have been idle for too long!");
        }
    }
    
    public NetworkManager getNetworkManager() {
        return this.netManager;
    }
    
    public void kickPlayerFromServer(final String s) {
        final ChatComponentText chatComponentText = new ChatComponentText(s);
        this.netManager.sendPacket(new S40PacketDisconnect(chatComponentText), new GenericFutureListener(chatComponentText) {
            private static final String __OBFID;
            final NetHandlerPlayServer this$0;
            private final ChatComponentText val$var2;
            
            @Override
            public void operationComplete(final Future future) {
                this.this$0.netManager.closeChannel(this.val$var2);
            }
            
            static {
                __OBFID = "CL_00001453";
            }
        }, new GenericFutureListener[0]);
        this.netManager.disableAutoRead();
        Futures.getUnchecked(this.serverController.addScheduledTask(new Runnable() {
            private static final String __OBFID;
            final NetHandlerPlayServer this$0;
            
            @Override
            public void run() {
                this.this$0.netManager.checkDisconnected();
            }
            
            static {
                __OBFID = "CL_00001454";
            }
        }));
    }
    
    @Override
    public void processInput(final C0CPacketInput c0CPacketInput) {
        PacketThreadUtil.func_180031_a(c0CPacketInput, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.setEntityActionState(c0CPacketInput.getStrafeSpeed(), c0CPacketInput.getForwardSpeed(), c0CPacketInput.isJumping(), c0CPacketInput.isSneaking());
    }
    
    @Override
    public void processPlayer(final C03PacketPlayer c03PacketPlayer) {
        PacketThreadUtil.func_180031_a(c03PacketPlayer, this, this.playerEntity.getServerForPlayer());
        final WorldServer worldServerForDimension = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        this.field_147366_g = true;
        if (!this.playerEntity.playerConqueredTheEnd) {
            final double posX = this.playerEntity.posX;
            final double posY = this.playerEntity.posY;
            final double posZ = this.playerEntity.posZ;
            double n = 0.0;
            final double n2 = c03PacketPlayer.getPositionX() - this.lastPosX;
            final double n3 = c03PacketPlayer.getPositionY() - this.lastPosY;
            final double n4 = c03PacketPlayer.getPositionZ() - this.lastPosZ;
            if (c03PacketPlayer.func_149466_j()) {
                n = n2 * n2 + n3 * n3 + n4 * n4;
                if (!this.hasMoved && n < 0.25) {
                    this.hasMoved = true;
                }
            }
            if (this.hasMoved) {
                this.field_175090_f = this.networkTickCount;
                if (this.playerEntity.ridingEntity != null) {
                    float n5 = this.playerEntity.rotationYaw;
                    float n6 = this.playerEntity.rotationPitch;
                    this.playerEntity.ridingEntity.updateRiderPosition();
                    final double posX2 = this.playerEntity.posX;
                    final double posY2 = this.playerEntity.posY;
                    final double posZ2 = this.playerEntity.posZ;
                    if (c03PacketPlayer.getRotating()) {
                        n5 = c03PacketPlayer.getYaw();
                        n6 = c03PacketPlayer.getPitch();
                    }
                    this.playerEntity.onGround = c03PacketPlayer.func_149465_i();
                    this.playerEntity.onUpdateEntity();
                    this.playerEntity.setPositionAndRotation(posX2, posY2, posZ2, n5, n6);
                    if (this.playerEntity.ridingEntity != null) {
                        this.playerEntity.ridingEntity.updateRiderPosition();
                    }
                    this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
                    if (this.playerEntity.ridingEntity != null) {
                        if (n > 4.0) {
                            this.playerEntity.playerNetServerHandler.sendPacket(new S18PacketEntityTeleport(this.playerEntity.ridingEntity));
                            this.setPlayerLocation(this.playerEntity.posX, this.playerEntity.posY, this.playerEntity.posZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                        }
                        this.playerEntity.ridingEntity.isAirBorne = true;
                    }
                    if (this.hasMoved) {
                        this.lastPosX = this.playerEntity.posX;
                        this.lastPosY = this.playerEntity.posY;
                        this.lastPosZ = this.playerEntity.posZ;
                    }
                    worldServerForDimension.updateEntity(this.playerEntity);
                    return;
                }
                if (this.playerEntity.isPlayerSleeping()) {
                    this.playerEntity.onUpdateEntity();
                    this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                    worldServerForDimension.updateEntity(this.playerEntity);
                    return;
                }
                final double posY3 = this.playerEntity.posY;
                this.lastPosX = this.playerEntity.posX;
                this.lastPosY = this.playerEntity.posY;
                this.lastPosZ = this.playerEntity.posZ;
                double n7 = this.playerEntity.posX;
                double n8 = this.playerEntity.posY;
                double n9 = this.playerEntity.posZ;
                float n10 = this.playerEntity.rotationYaw;
                float n11 = this.playerEntity.rotationPitch;
                if (c03PacketPlayer.func_149466_j() && c03PacketPlayer.getPositionY() == -999.0) {
                    c03PacketPlayer.func_149469_a(false);
                }
                if (c03PacketPlayer.func_149466_j()) {
                    n7 = c03PacketPlayer.getPositionX();
                    n8 = c03PacketPlayer.getPositionY();
                    n9 = c03PacketPlayer.getPositionZ();
                    if (Math.abs(c03PacketPlayer.getPositionX()) > 3.0E7 || Math.abs(c03PacketPlayer.getPositionZ()) > 3.0E7) {
                        this.kickPlayerFromServer("Illegal position");
                        return;
                    }
                }
                if (c03PacketPlayer.getRotating()) {
                    n10 = c03PacketPlayer.getYaw();
                    n11 = c03PacketPlayer.getPitch();
                }
                this.playerEntity.onUpdateEntity();
                this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, n10, n11);
                if (!this.hasMoved) {
                    return;
                }
                final double n12 = n7 - this.playerEntity.posX;
                final double n13 = n8 - this.playerEntity.posY;
                final double n14 = n9 - this.playerEntity.posZ;
                final double min = Math.min(Math.abs(n12), Math.abs(this.playerEntity.motionX));
                final double min2 = Math.min(Math.abs(n13), Math.abs(this.playerEntity.motionY));
                final double min3 = Math.min(Math.abs(n14), Math.abs(this.playerEntity.motionZ));
                if (min * min + min2 * min2 + min3 * min3 > 100.0 && (!this.serverController.isSinglePlayer() || !this.serverController.getServerOwner().equals(this.playerEntity.getName()))) {
                    NetHandlerPlayServer.logger.warn(String.valueOf(this.playerEntity.getName()) + " moved too quickly! " + n12 + "," + n13 + "," + n14 + " (" + min + ", " + min2 + ", " + min3 + ")");
                    this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                    return;
                }
                final float n15 = 0.0625f;
                final boolean empty = worldServerForDimension.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(n15, n15, n15)).isEmpty();
                if (this.playerEntity.onGround && !c03PacketPlayer.func_149465_i() && n13 > 0.0) {
                    this.playerEntity.jump();
                }
                this.playerEntity.moveEntity(n12, n13, n14);
                this.playerEntity.onGround = c03PacketPlayer.func_149465_i();
                final double n16 = n13;
                final double n17 = n7 - this.playerEntity.posX;
                double n18 = n8 - this.playerEntity.posY;
                if (n18 > -0.5 || n18 < 0.5) {
                    n18 = 0.0;
                }
                final double n19 = n9 - this.playerEntity.posZ;
                if (n17 * n17 + n18 * n18 + n19 * n19 > 0.0625 && !this.playerEntity.isPlayerSleeping() && !this.playerEntity.theItemInWorldManager.isCreative()) {
                    NetHandlerPlayServer.logger.warn(String.valueOf(this.playerEntity.getName()) + " moved wrongly!");
                }
                this.playerEntity.setPositionAndRotation(n7, n8, n9, n10, n11);
                this.playerEntity.addMovementStat(this.playerEntity.posX - posX, this.playerEntity.posY - posY, this.playerEntity.posZ - posZ);
                if (!this.playerEntity.noClip) {
                    final boolean empty2 = worldServerForDimension.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(n15, n15, n15)).isEmpty();
                    if (empty && (true || !empty2) && !this.playerEntity.isPlayerSleeping()) {
                        this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, n10, n11);
                        return;
                    }
                }
                final AxisAlignedBB addCoord = this.playerEntity.getEntityBoundingBox().expand(n15, n15, n15).addCoord(0.0, -0.55, 0.0);
                if (!this.serverController.isFlightAllowed() && !this.playerEntity.capabilities.allowFlying && !worldServerForDimension.checkBlockCollision(addCoord)) {
                    if (n16 >= -0.03125) {
                        ++this.floatingTickCount;
                        if (this.floatingTickCount > 80) {
                            NetHandlerPlayServer.logger.warn(String.valueOf(this.playerEntity.getName()) + " was kicked for floating too long!");
                            this.kickPlayerFromServer("Flying is not enabled on this server");
                            return;
                        }
                    }
                }
                else {
                    this.floatingTickCount = 0;
                }
                this.playerEntity.onGround = c03PacketPlayer.func_149465_i();
                this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
                this.playerEntity.handleFalling(this.playerEntity.posY - posY3, c03PacketPlayer.func_149465_i());
            }
            else if (this.networkTickCount - this.field_175090_f > 20) {
                this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
            }
        }
    }
    
    public void setPlayerLocation(final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_175089_a(n, n2, n3, n4, n5, Collections.emptySet());
    }
    
    public void func_175089_a(final double lastPosX, final double lastPosY, final double lastPosZ, final float n, final float n2, final Set set) {
        this.hasMoved = false;
        this.lastPosX = lastPosX;
        this.lastPosY = lastPosY;
        this.lastPosZ = lastPosZ;
        if (set.contains(S08PacketPlayerPosLook.EnumFlags.X)) {
            this.lastPosX += this.playerEntity.posX;
        }
        if (set.contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
            this.lastPosY += this.playerEntity.posY;
        }
        if (set.contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
            this.lastPosZ += this.playerEntity.posZ;
        }
        float n3 = n;
        float n4 = n2;
        if (set.contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
            n3 = n + this.playerEntity.rotationYaw;
        }
        if (set.contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
            n4 = n2 + this.playerEntity.rotationPitch;
        }
        this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, n3, n4);
        this.playerEntity.playerNetServerHandler.sendPacket(new S08PacketPlayerPosLook(lastPosX, lastPosY, lastPosZ, n, n2, set));
    }
    
    @Override
    public void processPlayerDigging(final C07PacketPlayerDigging c07PacketPlayerDigging) {
        PacketThreadUtil.func_180031_a(c07PacketPlayerDigging, this, this.playerEntity.getServerForPlayer());
        final WorldServer worldServerForDimension = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        final BlockPos func_179715_a = c07PacketPlayerDigging.func_179715_a();
        this.playerEntity.markPlayerActive();
        switch (SwitchAction.field_180224_a[c07PacketPlayerDigging.func_180762_c().ordinal()]) {
            case 1: {
                if (!this.playerEntity.func_175149_v()) {
                    this.playerEntity.dropOneItem(false);
                }
            }
            case 2: {
                if (!this.playerEntity.func_175149_v()) {
                    this.playerEntity.dropOneItem(true);
                }
            }
            case 3: {
                this.playerEntity.stopUsingItem();
            }
            case 4:
            case 5:
            case 6: {
                final double n = this.playerEntity.posX - (func_179715_a.getX() + 0.5);
                final double n2 = this.playerEntity.posY - (func_179715_a.getY() + 0.5) + 1.5;
                final double n3 = this.playerEntity.posZ - (func_179715_a.getZ() + 0.5);
                if (n * n + n2 * n2 + n3 * n3 > 36.0) {
                    return;
                }
                if (func_179715_a.getY() >= this.serverController.getBuildLimit()) {
                    return;
                }
                if (c07PacketPlayerDigging.func_180762_c() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
                    if (!this.serverController.isBlockProtected(worldServerForDimension, func_179715_a, this.playerEntity) && worldServerForDimension.getWorldBorder().contains(func_179715_a)) {
                        this.playerEntity.theItemInWorldManager.func_180784_a(func_179715_a, c07PacketPlayerDigging.func_179714_b());
                    }
                    else {
                        this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(worldServerForDimension, func_179715_a));
                    }
                }
                else {
                    if (c07PacketPlayerDigging.func_180762_c() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                        this.playerEntity.theItemInWorldManager.func_180785_a(func_179715_a);
                    }
                    else if (c07PacketPlayerDigging.func_180762_c() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
                        this.playerEntity.theItemInWorldManager.func_180238_e();
                    }
                    if (worldServerForDimension.getBlockState(func_179715_a).getBlock().getMaterial() != Material.air) {
                        this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(worldServerForDimension, func_179715_a));
                    }
                }
            }
            default: {
                throw new IllegalArgumentException("Invalid player action");
            }
        }
    }
    
    @Override
    public void processPlayerBlockPlacement(final C08PacketPlayerBlockPlacement c08PacketPlayerBlockPlacement) {
        PacketThreadUtil.func_180031_a(c08PacketPlayerBlockPlacement, this, this.playerEntity.getServerForPlayer());
        final WorldServer worldServerForDimension = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        final ItemStack currentItem = this.playerEntity.inventory.getCurrentItem();
        final BlockPos func_179724_a = c08PacketPlayerBlockPlacement.func_179724_a();
        final EnumFacing front = EnumFacing.getFront(c08PacketPlayerBlockPlacement.getPlacedBlockDirection());
        this.playerEntity.markPlayerActive();
        if (c08PacketPlayerBlockPlacement.getPlacedBlockDirection() == 255) {
            if (currentItem == null) {
                return;
            }
            this.playerEntity.theItemInWorldManager.tryUseItem(this.playerEntity, worldServerForDimension, currentItem);
        }
        else if (func_179724_a.getY() >= this.serverController.getBuildLimit() - 1 && (front == EnumFacing.UP || func_179724_a.getY() >= this.serverController.getBuildLimit())) {
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("build.tooHigh", new Object[] { this.serverController.getBuildLimit() });
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
            this.playerEntity.playerNetServerHandler.sendPacket(new S02PacketChat(chatComponentTranslation));
        }
        else if (this.hasMoved && this.playerEntity.getDistanceSq(func_179724_a.getX() + 0.5, func_179724_a.getY() + 0.5, func_179724_a.getZ() + 0.5) < 64.0 && !this.serverController.isBlockProtected(worldServerForDimension, func_179724_a, this.playerEntity) && worldServerForDimension.getWorldBorder().contains(func_179724_a)) {
            this.playerEntity.theItemInWorldManager.func_180236_a(this.playerEntity, worldServerForDimension, currentItem, func_179724_a, front, c08PacketPlayerBlockPlacement.getPlacedBlockOffsetX(), c08PacketPlayerBlockPlacement.getPlacedBlockOffsetY(), c08PacketPlayerBlockPlacement.getPlacedBlockOffsetZ());
        }
        if (true) {
            this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(worldServerForDimension, func_179724_a));
            this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(worldServerForDimension, func_179724_a.offset(front)));
        }
        ItemStack currentItem2 = this.playerEntity.inventory.getCurrentItem();
        if (currentItem2 != null && currentItem2.stackSize == 0) {
            this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = null;
            currentItem2 = null;
        }
        if (currentItem2 == null || currentItem2.getMaxItemUseDuration() == 0) {
            this.playerEntity.isChangingQuantityOnly = true;
            this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = ItemStack.copyItemStack(this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem]);
            final Slot slotFromInventory = this.playerEntity.openContainer.getSlotFromInventory(this.playerEntity.inventory, this.playerEntity.inventory.currentItem);
            this.playerEntity.openContainer.detectAndSendChanges();
            this.playerEntity.isChangingQuantityOnly = false;
            if (!ItemStack.areItemStacksEqual(this.playerEntity.inventory.getCurrentItem(), c08PacketPlayerBlockPlacement.getStack())) {
                this.sendPacket(new S2FPacketSetSlot(this.playerEntity.openContainer.windowId, slotFromInventory.slotNumber, this.playerEntity.inventory.getCurrentItem()));
            }
        }
    }
    
    @Override
    public void func_175088_a(final C18PacketSpectate c18PacketSpectate) {
        PacketThreadUtil.func_180031_a(c18PacketSpectate, this, this.playerEntity.getServerForPlayer());
        if (this.playerEntity.func_175149_v()) {
            Entity func_179727_a = null;
            final WorldServer[] worldServers = this.serverController.worldServers;
            while (0 < worldServers.length) {
                final WorldServer worldServer = worldServers[0];
                if (worldServer != null) {
                    func_179727_a = c18PacketSpectate.func_179727_a(worldServer);
                    if (func_179727_a != null) {
                        break;
                    }
                }
                int n = 0;
                ++n;
            }
            if (func_179727_a != null) {
                this.playerEntity.func_175399_e(this.playerEntity);
                this.playerEntity.mountEntity(null);
                if (func_179727_a.worldObj != this.playerEntity.worldObj) {
                    final WorldServer serverForPlayer = this.playerEntity.getServerForPlayer();
                    final WorldServer worldServer2 = (WorldServer)func_179727_a.worldObj;
                    this.playerEntity.dimension = func_179727_a.dimension;
                    this.sendPacket(new S07PacketRespawn(this.playerEntity.dimension, serverForPlayer.getDifficulty(), serverForPlayer.getWorldInfo().getTerrainType(), this.playerEntity.theItemInWorldManager.getGameType()));
                    serverForPlayer.removePlayerEntityDangerously(this.playerEntity);
                    this.playerEntity.isDead = false;
                    this.playerEntity.setLocationAndAngles(func_179727_a.posX, func_179727_a.posY, func_179727_a.posZ, func_179727_a.rotationYaw, func_179727_a.rotationPitch);
                    if (this.playerEntity.isEntityAlive()) {
                        serverForPlayer.updateEntityWithOptionalForce(this.playerEntity, false);
                        worldServer2.spawnEntityInWorld(this.playerEntity);
                        worldServer2.updateEntityWithOptionalForce(this.playerEntity, false);
                    }
                    this.playerEntity.setWorld(worldServer2);
                    this.serverController.getConfigurationManager().func_72375_a(this.playerEntity, serverForPlayer);
                    this.playerEntity.setPositionAndUpdate(func_179727_a.posX, func_179727_a.posY, func_179727_a.posZ);
                    this.playerEntity.theItemInWorldManager.setWorld(worldServer2);
                    this.serverController.getConfigurationManager().updateTimeAndWeatherForPlayer(this.playerEntity, worldServer2);
                    this.serverController.getConfigurationManager().syncPlayerInventory(this.playerEntity);
                }
                else {
                    this.playerEntity.setPositionAndUpdate(func_179727_a.posX, func_179727_a.posY, func_179727_a.posZ);
                }
            }
        }
    }
    
    @Override
    public void func_175086_a(final C19PacketResourcePackStatus c19PacketResourcePackStatus) {
    }
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
        NetHandlerPlayServer.logger.info(String.valueOf(this.playerEntity.getName()) + " lost connection: " + chatComponent);
        this.serverController.refreshStatusNextTick();
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("multiplayer.player.left", new Object[] { this.playerEntity.getDisplayName() });
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        this.serverController.getConfigurationManager().sendChatMsg(chatComponentTranslation);
        this.playerEntity.mountEntityAndWakeUp();
        this.serverController.getConfigurationManager().playerLoggedOut(this.playerEntity);
        if (this.serverController.isSinglePlayer() && this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
            NetHandlerPlayServer.logger.info("Stopping singleplayer server as player logged out");
            this.serverController.initiateShutdown();
        }
    }
    
    public void sendPacket(final Packet packet) {
        if (packet instanceof S02PacketChat) {
            final S02PacketChat s02PacketChat = (S02PacketChat)packet;
            final EntityPlayer.EnumChatVisibility chatVisibility = this.playerEntity.getChatVisibility();
            if (chatVisibility == EntityPlayer.EnumChatVisibility.HIDDEN) {
                return;
            }
            if (chatVisibility == EntityPlayer.EnumChatVisibility.SYSTEM && !s02PacketChat.isChat()) {
                return;
            }
        }
        this.netManager.sendPacket(packet);
    }
    
    @Override
    public void processHeldItemChange(final C09PacketHeldItemChange c09PacketHeldItemChange) {
        PacketThreadUtil.func_180031_a(c09PacketHeldItemChange, this, this.playerEntity.getServerForPlayer());
        if (c09PacketHeldItemChange.getSlotId() >= 0 && c09PacketHeldItemChange.getSlotId() < 9) {
            this.playerEntity.inventory.currentItem = c09PacketHeldItemChange.getSlotId();
            this.playerEntity.markPlayerActive();
        }
        else {
            NetHandlerPlayServer.logger.warn(String.valueOf(this.playerEntity.getName()) + " tried to set an invalid carried item");
        }
    }
    
    @Override
    public void processChatMessage(final C01PacketChatMessage c01PacketChatMessage) {
        PacketThreadUtil.func_180031_a(c01PacketChatMessage, this, this.playerEntity.getServerForPlayer());
        if (this.playerEntity.getChatVisibility() == EntityPlayer.EnumChatVisibility.HIDDEN) {
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("chat.cannotSend", new Object[0]);
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
            this.sendPacket(new S02PacketChat(chatComponentTranslation));
        }
        else {
            this.playerEntity.markPlayerActive();
            final String normalizeSpace = StringUtils.normalizeSpace(c01PacketChatMessage.getMessage());
            while (0 < normalizeSpace.length()) {
                if (!ChatAllowedCharacters.isAllowedCharacter(normalizeSpace.charAt(0))) {
                    this.kickPlayerFromServer("Illegal characters in chat");
                    return;
                }
                int n = 0;
                ++n;
            }
            if (normalizeSpace.startsWith("/")) {
                this.handleSlashCommand(normalizeSpace);
            }
            else {
                this.serverController.getConfigurationManager().sendChatMsgImpl(new ChatComponentTranslation("chat.type.text", new Object[] { this.playerEntity.getDisplayName(), normalizeSpace }), false);
            }
            this.chatSpamThresholdCount += 20;
            if (this.chatSpamThresholdCount > 200 && !this.serverController.getConfigurationManager().canSendCommands(this.playerEntity.getGameProfile())) {
                this.kickPlayerFromServer("disconnect.spam");
            }
        }
    }
    
    private void handleSlashCommand(final String s) {
        this.serverController.getCommandManager().executeCommand(this.playerEntity, s);
    }
    
    @Override
    public void func_175087_a(final C0APacketAnimation c0APacketAnimation) {
        PacketThreadUtil.func_180031_a(c0APacketAnimation, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        this.playerEntity.swingItem();
    }
    
    @Override
    public void processEntityAction(final C0BPacketEntityAction c0BPacketEntityAction) {
        PacketThreadUtil.func_180031_a(c0BPacketEntityAction, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        switch (SwitchAction.field_180222_b[c0BPacketEntityAction.func_180764_b().ordinal()]) {
            case 1: {
                this.playerEntity.setSneaking(true);
                break;
            }
            case 2: {
                this.playerEntity.setSneaking(false);
                break;
            }
            case 3: {
                this.playerEntity.setSprinting(true);
                break;
            }
            case 4: {
                this.playerEntity.setSprinting(false);
                break;
            }
            case 5: {
                this.playerEntity.wakeUpPlayer(false, true, true);
                this.hasMoved = false;
                break;
            }
            case 6: {
                if (this.playerEntity.ridingEntity instanceof EntityHorse) {
                    ((EntityHorse)this.playerEntity.ridingEntity).setJumpPower(c0BPacketEntityAction.func_149512_e());
                    break;
                }
                break;
            }
            case 7: {
                if (this.playerEntity.ridingEntity instanceof EntityHorse) {
                    ((EntityHorse)this.playerEntity.ridingEntity).openGUI(this.playerEntity);
                    break;
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid client command!");
            }
        }
    }
    
    @Override
    public void processUseEntity(final C02PacketUseEntity c02PacketUseEntity) {
        PacketThreadUtil.func_180031_a(c02PacketUseEntity, this, this.playerEntity.getServerForPlayer());
        final Entity entityFromWorld = c02PacketUseEntity.getEntityFromWorld(this.serverController.worldServerForDimension(this.playerEntity.dimension));
        this.playerEntity.markPlayerActive();
        if (entityFromWorld != null) {
            final boolean canEntityBeSeen = this.playerEntity.canEntityBeSeen(entityFromWorld);
            double n = 36.0;
            if (!canEntityBeSeen) {
                n = 9.0;
            }
            if (this.playerEntity.getDistanceSqToEntity(entityFromWorld) < n) {
                if (c02PacketUseEntity.getAction() == C02PacketUseEntity.Action.INTERACT) {
                    this.playerEntity.interactWith(entityFromWorld);
                }
                else if (c02PacketUseEntity.getAction() == C02PacketUseEntity.Action.INTERACT_AT) {
                    entityFromWorld.func_174825_a(this.playerEntity, c02PacketUseEntity.func_179712_b());
                }
                else if (c02PacketUseEntity.getAction() == C02PacketUseEntity.Action.ATTACK) {
                    if (entityFromWorld instanceof EntityItem || entityFromWorld instanceof EntityXPOrb || entityFromWorld instanceof EntityArrow || entityFromWorld == this.playerEntity) {
                        this.kickPlayerFromServer("Attempting to attack an invalid entity");
                        this.serverController.logWarning("Player " + this.playerEntity.getName() + " tried to attack an invalid entity");
                        return;
                    }
                    this.playerEntity.attackTargetEntityWithCurrentItem(entityFromWorld);
                }
            }
        }
    }
    
    @Override
    public void processClientStatus(final C16PacketClientStatus c16PacketClientStatus) {
        PacketThreadUtil.func_180031_a(c16PacketClientStatus, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        switch (SwitchAction.field_180223_c[c16PacketClientStatus.getStatus().ordinal()]) {
            case 1: {
                if (this.playerEntity.playerConqueredTheEnd) {
                    this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, 0, true);
                    break;
                }
                if (this.playerEntity.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled()) {
                    if (this.serverController.isSinglePlayer() && this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
                        this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
                        this.serverController.deleteWorldAndStopServer();
                        break;
                    }
                    this.serverController.getConfigurationManager().getBannedPlayers().addEntry(new UserListBansEntry(this.playerEntity.getGameProfile(), null, "(You just lost the game)", null, "Death in Hardcore"));
                    this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
                    break;
                }
                else {
                    if (this.playerEntity.getHealth() > 0.0f) {
                        return;
                    }
                    this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, 0, false);
                    break;
                }
                break;
            }
            case 2: {
                this.playerEntity.getStatFile().func_150876_a(this.playerEntity);
                break;
            }
            case 3: {
                this.playerEntity.triggerAchievement(AchievementList.openInventory);
                break;
            }
        }
    }
    
    @Override
    public void processCloseWindow(final C0DPacketCloseWindow c0DPacketCloseWindow) {
        PacketThreadUtil.func_180031_a(c0DPacketCloseWindow, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.closeContainer();
    }
    
    @Override
    public void processClickWindow(final C0EPacketClickWindow c0EPacketClickWindow) {
        PacketThreadUtil.func_180031_a(c0EPacketClickWindow, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        if (this.playerEntity.openContainer.windowId == c0EPacketClickWindow.getWindowId() && this.playerEntity.openContainer.getCanCraft(this.playerEntity)) {
            if (this.playerEntity.func_175149_v()) {
                final ArrayList arrayList = Lists.newArrayList();
                while (0 < this.playerEntity.openContainer.inventorySlots.size()) {
                    arrayList.add(this.playerEntity.openContainer.inventorySlots.get(0).getStack());
                    int n = 0;
                    ++n;
                }
                this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, arrayList);
            }
            else if (ItemStack.areItemStacksEqual(c0EPacketClickWindow.getClickedItem(), this.playerEntity.openContainer.slotClick(c0EPacketClickWindow.getSlotId(), c0EPacketClickWindow.getUsedButton(), c0EPacketClickWindow.getMode(), this.playerEntity))) {
                this.playerEntity.playerNetServerHandler.sendPacket(new S32PacketConfirmTransaction(c0EPacketClickWindow.getWindowId(), c0EPacketClickWindow.getActionNumber(), true));
                this.playerEntity.isChangingQuantityOnly = true;
                this.playerEntity.openContainer.detectAndSendChanges();
                this.playerEntity.updateHeldItem();
                this.playerEntity.isChangingQuantityOnly = false;
            }
            else {
                this.field_147372_n.addKey(this.playerEntity.openContainer.windowId, c0EPacketClickWindow.getActionNumber());
                this.playerEntity.playerNetServerHandler.sendPacket(new S32PacketConfirmTransaction(c0EPacketClickWindow.getWindowId(), c0EPacketClickWindow.getActionNumber(), false));
                this.playerEntity.openContainer.setCanCraft(this.playerEntity, false);
                final ArrayList arrayList2 = Lists.newArrayList();
                while (0 < this.playerEntity.openContainer.inventorySlots.size()) {
                    arrayList2.add(this.playerEntity.openContainer.inventorySlots.get(0).getStack());
                    int n2 = 0;
                    ++n2;
                }
                this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, arrayList2);
            }
        }
    }
    
    @Override
    public void processEnchantItem(final C11PacketEnchantItem c11PacketEnchantItem) {
        PacketThreadUtil.func_180031_a(c11PacketEnchantItem, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        if (this.playerEntity.openContainer.windowId == c11PacketEnchantItem.getId() && this.playerEntity.openContainer.getCanCraft(this.playerEntity) && !this.playerEntity.func_175149_v()) {
            this.playerEntity.openContainer.enchantItem(this.playerEntity, c11PacketEnchantItem.getButton());
            this.playerEntity.openContainer.detectAndSendChanges();
        }
    }
    
    @Override
    public void processCreativeInventoryAction(final C10PacketCreativeInventoryAction c10PacketCreativeInventoryAction) {
        PacketThreadUtil.func_180031_a(c10PacketCreativeInventoryAction, this, this.playerEntity.getServerForPlayer());
        if (this.playerEntity.theItemInWorldManager.isCreative()) {
            final boolean b = c10PacketCreativeInventoryAction.getSlotId() < 0;
            final ItemStack stack = c10PacketCreativeInventoryAction.getStack();
            if (stack != null && stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag", 10)) {
                final NBTTagCompound compoundTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");
                if (compoundTag.hasKey("x") && compoundTag.hasKey("y") && compoundTag.hasKey("z")) {
                    final TileEntity tileEntity = this.playerEntity.worldObj.getTileEntity(new BlockPos(compoundTag.getInteger("x"), compoundTag.getInteger("y"), compoundTag.getInteger("z")));
                    if (tileEntity != null) {
                        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                        tileEntity.writeToNBT(nbtTagCompound);
                        nbtTagCompound.removeTag("x");
                        nbtTagCompound.removeTag("y");
                        nbtTagCompound.removeTag("z");
                        stack.setTagInfo("BlockEntityTag", nbtTagCompound);
                    }
                }
            }
            final boolean b2 = c10PacketCreativeInventoryAction.getSlotId() >= 1 && c10PacketCreativeInventoryAction.getSlotId() < 45;
            final boolean b3 = stack == null || stack.getItem() != null;
            final boolean b4 = stack == null || (stack.getMetadata() >= 0 && stack.stackSize <= 64 && stack.stackSize > 0);
            if (b2 && b3 && b4) {
                if (stack == null) {
                    this.playerEntity.inventoryContainer.putStackInSlot(c10PacketCreativeInventoryAction.getSlotId(), null);
                }
                else {
                    this.playerEntity.inventoryContainer.putStackInSlot(c10PacketCreativeInventoryAction.getSlotId(), stack);
                }
                this.playerEntity.inventoryContainer.setCanCraft(this.playerEntity, true);
            }
            else if (b && b3 && b4 && this.itemDropThreshold < 200) {
                this.itemDropThreshold += 20;
                final EntityItem dropPlayerItemWithRandomChoice = this.playerEntity.dropPlayerItemWithRandomChoice(stack, true);
                if (dropPlayerItemWithRandomChoice != null) {
                    dropPlayerItemWithRandomChoice.setAgeToCreativeDespawnTime();
                }
            }
        }
    }
    
    @Override
    public void processConfirmTransaction(final C0FPacketConfirmTransaction c0FPacketConfirmTransaction) {
        PacketThreadUtil.func_180031_a(c0FPacketConfirmTransaction, this, this.playerEntity.getServerForPlayer());
        final Short n = (Short)this.field_147372_n.lookup(this.playerEntity.openContainer.windowId);
        if (n != null && c0FPacketConfirmTransaction.getUid() == n && this.playerEntity.openContainer.windowId == c0FPacketConfirmTransaction.getId() && !this.playerEntity.openContainer.getCanCraft(this.playerEntity) && !this.playerEntity.func_175149_v()) {
            this.playerEntity.openContainer.setCanCraft(this.playerEntity, true);
        }
    }
    
    @Override
    public void processUpdateSign(final C12PacketUpdateSign c12PacketUpdateSign) {
        PacketThreadUtil.func_180031_a(c12PacketUpdateSign, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        final WorldServer worldServerForDimension = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        final BlockPos func_179722_a = c12PacketUpdateSign.func_179722_a();
        if (worldServerForDimension.isBlockLoaded(func_179722_a)) {
            final TileEntity tileEntity = worldServerForDimension.getTileEntity(func_179722_a);
            if (!(tileEntity instanceof TileEntitySign)) {
                return;
            }
            final TileEntitySign tileEntitySign = (TileEntitySign)tileEntity;
            if (!tileEntitySign.getIsEditable() || tileEntitySign.func_145911_b() != this.playerEntity) {
                this.serverController.logWarning("Player " + this.playerEntity.getName() + " just tried to change non-editable sign");
                return;
            }
            System.arraycopy(c12PacketUpdateSign.func_180768_b(), 0, tileEntitySign.signText, 0, 4);
            tileEntitySign.markDirty();
            worldServerForDimension.markBlockForUpdate(func_179722_a);
        }
    }
    
    @Override
    public void processKeepAlive(final C00PacketKeepAlive c00PacketKeepAlive) {
        if (c00PacketKeepAlive.getKey() == this.field_147378_h) {
            this.playerEntity.ping = (this.playerEntity.ping * 3 + (int)(this.currentTimeMillis() - this.lastPingTime)) / 4;
        }
    }
    
    private long currentTimeMillis() {
        return System.nanoTime() / 1000000L;
    }
    
    @Override
    public void processPlayerAbilities(final C13PacketPlayerAbilities c13PacketPlayerAbilities) {
        PacketThreadUtil.func_180031_a(c13PacketPlayerAbilities, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.capabilities.isFlying = (c13PacketPlayerAbilities.isFlying() && this.playerEntity.capabilities.allowFlying);
    }
    
    @Override
    public void processTabComplete(final C14PacketTabComplete c14PacketTabComplete) {
        PacketThreadUtil.func_180031_a(c14PacketTabComplete, this, this.playerEntity.getServerForPlayer());
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<String> iterator = this.serverController.func_180506_a(this.playerEntity, c14PacketTabComplete.getMessage(), c14PacketTabComplete.func_179709_b()).iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next());
        }
        this.playerEntity.playerNetServerHandler.sendPacket(new S3APacketTabComplete(arrayList.toArray(new String[arrayList.size()])));
    }
    
    @Override
    public void processClientSettings(final C15PacketClientSettings c15PacketClientSettings) {
        PacketThreadUtil.func_180031_a(c15PacketClientSettings, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.handleClientSettings(c15PacketClientSettings);
    }
    
    @Override
    public void processVanilla250Packet(final C17PacketCustomPayload c17PacketCustomPayload) {
        PacketThreadUtil.func_180031_a(c17PacketCustomPayload, this, this.playerEntity.getServerForPlayer());
        if ("MC|BEdit".equals(c17PacketCustomPayload.getChannelName())) {
            final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.wrappedBuffer(c17PacketCustomPayload.getBufferData()));
            final ItemStack itemStackFromBuffer = packetBuffer.readItemStackFromBuffer();
            if (itemStackFromBuffer == null) {
                packetBuffer.release();
                return;
            }
            if (!ItemWritableBook.validBookPageTagContents(itemStackFromBuffer.getTagCompound())) {
                throw new IOException("Invalid book tag!");
            }
            final ItemStack currentItem = this.playerEntity.inventory.getCurrentItem();
            if (currentItem != null) {
                if (itemStackFromBuffer.getItem() == Items.writable_book && itemStackFromBuffer.getItem() == currentItem.getItem()) {
                    currentItem.setTagInfo("pages", itemStackFromBuffer.getTagCompound().getTagList("pages", 8));
                }
                packetBuffer.release();
                return;
            }
            packetBuffer.release();
        }
        else {
            if (!"MC|BSign".equals(c17PacketCustomPayload.getChannelName())) {
                if ("MC|TrSel".equals(c17PacketCustomPayload.getChannelName())) {
                    final int int1 = c17PacketCustomPayload.getBufferData().readInt();
                    final Container openContainer = this.playerEntity.openContainer;
                    if (openContainer instanceof ContainerMerchant) {
                        ((ContainerMerchant)openContainer).setCurrentRecipeIndex(int1);
                    }
                }
                else if ("MC|AdvCdm".equals(c17PacketCustomPayload.getChannelName())) {
                    if (!this.serverController.isCommandBlockEnabled()) {
                        this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.notEnabled", new Object[0]));
                    }
                    else if (this.playerEntity.canCommandSenderUseCommand(2, "") && this.playerEntity.capabilities.isCreativeMode) {
                        final PacketBuffer bufferData = c17PacketCustomPayload.getBufferData();
                        final byte byte1 = bufferData.readByte();
                        CommandBlockLogic commandBlockLogic = null;
                        if (byte1 == 0) {
                            final TileEntity tileEntity = this.playerEntity.worldObj.getTileEntity(new BlockPos(bufferData.readInt(), bufferData.readInt(), bufferData.readInt()));
                            if (tileEntity instanceof TileEntityCommandBlock) {
                                commandBlockLogic = ((TileEntityCommandBlock)tileEntity).getCommandBlockLogic();
                            }
                        }
                        else if (byte1 == 1) {
                            final Entity entityByID = this.playerEntity.worldObj.getEntityByID(bufferData.readInt());
                            if (entityByID instanceof EntityMinecartCommandBlock) {
                                commandBlockLogic = ((EntityMinecartCommandBlock)entityByID).func_145822_e();
                            }
                        }
                        final String stringFromBuffer = bufferData.readStringFromBuffer(bufferData.readableBytes());
                        final boolean boolean1 = bufferData.readBoolean();
                        if (commandBlockLogic != null) {
                            commandBlockLogic.setCommand(stringFromBuffer);
                            commandBlockLogic.func_175573_a(boolean1);
                            if (!boolean1) {
                                commandBlockLogic.func_145750_b(null);
                            }
                            commandBlockLogic.func_145756_e();
                            this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.setCommand.success", new Object[] { stringFromBuffer }));
                        }
                        bufferData.release();
                    }
                    else {
                        this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.notAllowed", new Object[0]));
                    }
                }
                else if ("MC|Beacon".equals(c17PacketCustomPayload.getChannelName())) {
                    if (this.playerEntity.openContainer instanceof ContainerBeacon) {
                        final PacketBuffer bufferData2 = c17PacketCustomPayload.getBufferData();
                        final int int2 = bufferData2.readInt();
                        final int int3 = bufferData2.readInt();
                        final ContainerBeacon containerBeacon = (ContainerBeacon)this.playerEntity.openContainer;
                        final Slot slot = containerBeacon.getSlot(0);
                        if (slot.getHasStack()) {
                            slot.decrStackSize(1);
                            final IInventory func_180611_e = containerBeacon.func_180611_e();
                            func_180611_e.setField(1, int2);
                            func_180611_e.setField(2, int3);
                            func_180611_e.markDirty();
                        }
                    }
                }
                else if ("MC|ItemName".equals(c17PacketCustomPayload.getChannelName()) && this.playerEntity.openContainer instanceof ContainerRepair) {
                    final ContainerRepair containerRepair = (ContainerRepair)this.playerEntity.openContainer;
                    if (c17PacketCustomPayload.getBufferData() != null && c17PacketCustomPayload.getBufferData().readableBytes() >= 1) {
                        final String filterAllowedCharacters = ChatAllowedCharacters.filterAllowedCharacters(c17PacketCustomPayload.getBufferData().readStringFromBuffer(32767));
                        if (filterAllowedCharacters.length() <= 30) {
                            containerRepair.updateItemName(filterAllowedCharacters);
                        }
                    }
                    else {
                        containerRepair.updateItemName("");
                    }
                }
                return;
            }
            final PacketBuffer packetBuffer2 = new PacketBuffer(Unpooled.wrappedBuffer(c17PacketCustomPayload.getBufferData()));
            final ItemStack itemStackFromBuffer2 = packetBuffer2.readItemStackFromBuffer();
            if (itemStackFromBuffer2 == null) {
                packetBuffer2.release();
                return;
            }
            if (!ItemEditableBook.validBookTagContents(itemStackFromBuffer2.getTagCompound())) {
                throw new IOException("Invalid book tag!");
            }
            final ItemStack currentItem2 = this.playerEntity.inventory.getCurrentItem();
            if (currentItem2 == null) {
                packetBuffer2.release();
                return;
            }
            if (itemStackFromBuffer2.getItem() == Items.written_book && currentItem2.getItem() == Items.writable_book) {
                currentItem2.setTagInfo("author", new NBTTagString(this.playerEntity.getName()));
                currentItem2.setTagInfo("title", new NBTTagString(itemStackFromBuffer2.getTagCompound().getString("title")));
                currentItem2.setTagInfo("pages", itemStackFromBuffer2.getTagCompound().getTagList("pages", 8));
                currentItem2.setItem(Items.written_book);
            }
            packetBuffer2.release();
        }
    }
    
    static final class SwitchAction
    {
        static final int[] field_180224_a;
        static final int[] field_180222_b;
        static final int[] field_180223_c;
        private static final String __OBFID;
        private static final String[] lIIllIIIIlllIIlI;
        private static String[] lIIllIIIIlllIIll;
        
        static {
            lllIIIIIlIIIIIIl();
            lllIIIIIIllllllI();
            __OBFID = SwitchAction.lIIllIIIIlllIIlI[0];
            field_180223_c = new int[C16PacketClientStatus.EnumState.values().length];
            try {
                SwitchAction.field_180223_c[C16PacketClientStatus.EnumState.PERFORM_RESPAWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchAction.field_180223_c[C16PacketClientStatus.EnumState.REQUEST_STATS.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchAction.field_180223_c[C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            field_180222_b = new int[C0BPacketEntityAction.Action.values().length];
            try {
                SwitchAction.field_180222_b[C0BPacketEntityAction.Action.START_SNEAKING.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchAction.field_180222_b[C0BPacketEntityAction.Action.STOP_SNEAKING.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchAction.field_180222_b[C0BPacketEntityAction.Action.START_SPRINTING.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                SwitchAction.field_180222_b[C0BPacketEntityAction.Action.STOP_SPRINTING.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                SwitchAction.field_180222_b[C0BPacketEntityAction.Action.STOP_SLEEPING.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                SwitchAction.field_180222_b[C0BPacketEntityAction.Action.RIDING_JUMP.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                SwitchAction.field_180222_b[C0BPacketEntityAction.Action.OPEN_INVENTORY.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            field_180224_a = new int[C07PacketPlayerDigging.Action.values().length];
            try {
                SwitchAction.field_180224_a[C07PacketPlayerDigging.Action.DROP_ITEM.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
            try {
                SwitchAction.field_180224_a[C07PacketPlayerDigging.Action.DROP_ALL_ITEMS.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError12) {}
            try {
                SwitchAction.field_180224_a[C07PacketPlayerDigging.Action.RELEASE_USE_ITEM.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError13) {}
            try {
                SwitchAction.field_180224_a[C07PacketPlayerDigging.Action.START_DESTROY_BLOCK.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError14) {}
            try {
                SwitchAction.field_180224_a[C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError15) {}
            try {
                SwitchAction.field_180224_a[C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError16) {}
        }
        
        private static void lllIIIIIIllllllI() {
            (lIIllIIIIlllIIlI = new String[1])[0] = lllIIIIIIlllllII(SwitchAction.lIIllIIIIlllIIll[0], SwitchAction.lIIllIIIIlllIIll[1]);
            SwitchAction.lIIllIIIIlllIIll = null;
        }
        
        private static void lllIIIIIlIIIIIIl() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchAction.lIIllIIIIlllIIll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lllIIIIIIlllllII(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
                final Cipher instance = Cipher.getInstance("AES");
                instance.init(2, secretKeySpec);
                return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
}
