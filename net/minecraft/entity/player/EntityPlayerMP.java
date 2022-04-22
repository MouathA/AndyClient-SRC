package net.minecraft.entity.player;

import net.minecraft.server.*;
import org.apache.logging.log4j.*;
import com.mojang.authlib.*;
import net.minecraft.nbt.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.biome.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.scoreboard.*;
import net.minecraft.stats.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import net.minecraft.village.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.server.management.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;

public class EntityPlayerMP extends EntityPlayer implements ICrafting
{
    private static final Logger logger;
    private String translator;
    public NetHandlerPlayServer playerNetServerHandler;
    public final MinecraftServer mcServer;
    public final ItemInWorldManager theItemInWorldManager;
    public double managedPosX;
    public double managedPosZ;
    public final List loadedChunks;
    private final List destroyedItemsNetCache;
    private final StatisticsFile statsFile;
    private float field_130068_bO;
    private float lastHealth;
    private int lastFoodLevel;
    private boolean wasHungry;
    private int lastExperience;
    private int respawnInvulnerabilityTicks;
    private EnumChatVisibility chatVisibility;
    private boolean chatColours;
    private long playerLastActiveTime;
    private Entity field_175401_bS;
    private int currentWindowId;
    public boolean isChangingQuantityOnly;
    public int ping;
    public boolean playerConqueredTheEnd;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001440";
        logger = LogManager.getLogger();
    }
    
    public EntityPlayerMP(final MinecraftServer mcServer, final WorldServer worldServer, final GameProfile gameProfile, final ItemInWorldManager theItemInWorldManager) {
        super(worldServer, gameProfile);
        this.translator = "en_US";
        this.loadedChunks = Lists.newLinkedList();
        this.destroyedItemsNetCache = Lists.newLinkedList();
        this.field_130068_bO = Float.MIN_VALUE;
        this.lastHealth = -1.0E8f;
        this.lastFoodLevel = -99999999;
        this.wasHungry = true;
        this.lastExperience = -99999999;
        this.respawnInvulnerabilityTicks = 60;
        this.chatColours = true;
        this.playerLastActiveTime = System.currentTimeMillis();
        this.field_175401_bS = null;
        theItemInWorldManager.thisPlayerMP = this;
        this.theItemInWorldManager = theItemInWorldManager;
        BlockPos blockPos = worldServer.getSpawnPoint();
        if (!worldServer.provider.getHasNoSky() && worldServer.getWorldInfo().getGameType() != WorldSettings.GameType.ADVENTURE) {
            Math.max(5, mcServer.getSpawnProtectionSize() - 6);
            final int floor_double = MathHelper.floor_double(worldServer.getWorldBorder().getClosestDistance(blockPos.getX(), blockPos.getZ()));
            if (floor_double < 1) {}
            if (floor_double <= 1) {}
            blockPos = worldServer.func_175672_r(blockPos.add(this.rand.nextInt(2) - 1, 0, this.rand.nextInt(2) - 1));
        }
        this.mcServer = mcServer;
        this.statsFile = mcServer.getConfigurationManager().getPlayerStatsFile(this);
        this.func_174828_a(blockPos, this.stepHeight = 0.0f, 0.0f);
        while (!worldServer.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && this.posY < 255.0) {
            this.setPosition(this.posX, this.posY + 1.0, this.posZ);
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey("playerGameType", 99)) {
            if (MinecraftServer.getServer().getForceGamemode()) {
                this.theItemInWorldManager.setGameType(MinecraftServer.getServer().getGameType());
            }
            else {
                this.theItemInWorldManager.setGameType(WorldSettings.GameType.getByID(nbtTagCompound.getInteger("playerGameType")));
            }
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("playerGameType", this.theItemInWorldManager.getGameType().getID());
    }
    
    @Override
    public void addExperienceLevel(final int n) {
        super.addExperienceLevel(n);
        this.lastExperience = -1;
    }
    
    @Override
    public void func_71013_b(final int n) {
        super.func_71013_b(n);
        this.lastExperience = -1;
    }
    
    public void addSelfToInternalCraftingInventory() {
        this.openContainer.onCraftGuiOpened(this);
    }
    
    @Override
    public void func_152111_bt() {
        super.func_152111_bt();
        this.playerNetServerHandler.sendPacket(new S42PacketCombatEvent(this.getCombatTracker(), S42PacketCombatEvent.Event.ENTER_COMBAT));
    }
    
    @Override
    public void func_152112_bu() {
        super.func_152112_bu();
        this.playerNetServerHandler.sendPacket(new S42PacketCombatEvent(this.getCombatTracker(), S42PacketCombatEvent.Event.END_COMBAT));
    }
    
    @Override
    public void onUpdate() {
        this.theItemInWorldManager.updateBlockRemoving();
        --this.respawnInvulnerabilityTicks;
        if (this.hurtResistantTime > 0) {
            --this.hurtResistantTime;
        }
        this.openContainer.detectAndSendChanges();
        if (!this.worldObj.isRemote && !this.openContainer.canInteractWith(this)) {
            this.closeScreen();
            this.openContainer = this.inventoryContainer;
        }
        while (!this.destroyedItemsNetCache.isEmpty()) {
            final int min = Math.min(this.destroyedItemsNetCache.size(), Integer.MAX_VALUE);
            final int[] array = new int[min];
            final Iterator iterator = this.destroyedItemsNetCache.iterator();
            while (iterator.hasNext() && 0 < min) {
                final int[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = iterator.next();
                iterator.remove();
            }
            this.playerNetServerHandler.sendPacket(new S13PacketDestroyEntities(array));
        }
        if (!this.loadedChunks.isEmpty()) {
            final ArrayList arrayList = Lists.newArrayList();
            final Iterator iterator2 = this.loadedChunks.iterator();
            final ArrayList arrayList2 = Lists.newArrayList();
            while (iterator2.hasNext() && arrayList.size() < 10) {
                final ChunkCoordIntPair chunkCoordIntPair = iterator2.next();
                if (chunkCoordIntPair != null) {
                    if (!this.worldObj.isBlockLoaded(new BlockPos(chunkCoordIntPair.chunkXPos << 4, 0, chunkCoordIntPair.chunkZPos << 4))) {
                        continue;
                    }
                    final Chunk chunkFromChunkCoords = this.worldObj.getChunkFromChunkCoords(chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos);
                    if (!chunkFromChunkCoords.isPopulated()) {
                        continue;
                    }
                    arrayList.add(chunkFromChunkCoords);
                    arrayList2.addAll(((WorldServer)this.worldObj).func_147486_a(chunkCoordIntPair.chunkXPos * 16, 0, chunkCoordIntPair.chunkZPos * 16, chunkCoordIntPair.chunkXPos * 16 + 16, 256, chunkCoordIntPair.chunkZPos * 16 + 16));
                    iterator2.remove();
                }
                else {
                    iterator2.remove();
                }
            }
            if (!arrayList.isEmpty()) {
                if (arrayList.size() == 1) {
                    this.playerNetServerHandler.sendPacket(new S21PacketChunkData(arrayList.get(0), true, 65535));
                }
                else {
                    this.playerNetServerHandler.sendPacket(new S26PacketMapChunkBulk(arrayList));
                }
                final Iterator<TileEntity> iterator3 = arrayList2.iterator();
                while (iterator3.hasNext()) {
                    this.sendTileEntityUpdate(iterator3.next());
                }
                final Iterator<Chunk> iterator4 = arrayList.iterator();
                while (iterator4.hasNext()) {
                    this.getServerForPlayer().getEntityTracker().func_85172_a(this, iterator4.next());
                }
            }
        }
        final Entity func_175398_C = this.func_175398_C();
        if (func_175398_C != this) {
            if (!func_175398_C.isEntityAlive()) {
                this.func_175399_e(this);
            }
            else {
                this.setPositionAndRotation(func_175398_C.posX, func_175398_C.posY, func_175398_C.posZ, func_175398_C.rotationYaw, func_175398_C.rotationPitch);
                this.mcServer.getConfigurationManager().serverUpdateMountedMovingPlayer(this);
                if (this.isSneaking()) {
                    this.func_175399_e(this);
                }
            }
        }
    }
    
    public void onUpdateEntity() {
        super.onUpdate();
        while (0 < this.inventory.getSizeInventory()) {
            final ItemStack stackInSlot = this.inventory.getStackInSlot(0);
            if (stackInSlot != null && stackInSlot.getItem().isMap()) {
                final Packet mapDataPacket = ((ItemMapBase)stackInSlot.getItem()).createMapDataPacket(stackInSlot, this.worldObj, this);
                if (mapDataPacket != null) {
                    this.playerNetServerHandler.sendPacket(mapDataPacket);
                }
            }
            int n = 0;
            ++n;
        }
        if (this.getHealth() != this.lastHealth || this.lastFoodLevel != this.foodStats.getFoodLevel() || this.foodStats.getSaturationLevel() == 0.0f != this.wasHungry) {
            this.playerNetServerHandler.sendPacket(new S06PacketUpdateHealth(this.getHealth(), this.foodStats.getFoodLevel(), this.foodStats.getSaturationLevel()));
            this.lastHealth = this.getHealth();
            this.lastFoodLevel = this.foodStats.getFoodLevel();
            this.wasHungry = (this.foodStats.getSaturationLevel() == 0.0f);
        }
        if (this.getHealth() + this.getAbsorptionAmount() != this.field_130068_bO) {
            this.field_130068_bO = this.getHealth() + this.getAbsorptionAmount();
            final Iterator<ScoreObjective> iterator = this.getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.health).iterator();
            while (iterator.hasNext()) {
                this.getWorldScoreboard().getValueFromObjective(this.getName(), iterator.next()).func_96651_a(Arrays.asList(this));
            }
        }
        if (this.experienceTotal != this.lastExperience) {
            this.lastExperience = this.experienceTotal;
            this.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(this.experience, this.experienceTotal, this.experienceLevel));
        }
        if (this.ticksExisted % 20 * 5 == 0 && !this.getStatFile().hasAchievementUnlocked(AchievementList.exploreAllBiomes)) {
            this.func_147098_j();
        }
    }
    
    protected void func_147098_j() {
        final String biomeName = this.worldObj.getBiomeGenForCoords(new BlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ))).biomeName;
        JsonSerializableSet set = (JsonSerializableSet)this.getStatFile().func_150870_b(AchievementList.exploreAllBiomes);
        if (set == null) {
            set = (JsonSerializableSet)this.getStatFile().func_150872_a(AchievementList.exploreAllBiomes, new JsonSerializableSet());
        }
        set.add(biomeName);
        if (this.getStatFile().canUnlockAchievement(AchievementList.exploreAllBiomes) && set.size() >= BiomeGenBase.explorationBiomesList.size()) {
            final HashSet hashSet = Sets.newHashSet(BiomeGenBase.explorationBiomesList);
            for (final String s : set) {
                final Iterator<BiomeGenBase> iterator2 = hashSet.iterator();
                while (iterator2.hasNext()) {
                    if (iterator2.next().biomeName.equals(s)) {
                        iterator2.remove();
                    }
                }
                if (hashSet.isEmpty()) {
                    break;
                }
            }
            if (hashSet.isEmpty()) {
                this.triggerAchievement(AchievementList.exploreAllBiomes);
            }
        }
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        if (this.worldObj.getGameRules().getGameRuleBooleanValue("showDeathMessages")) {
            final Team team = this.getTeam();
            if (team != null && team.func_178771_j() != Team.EnumVisible.ALWAYS) {
                if (team.func_178771_j() == Team.EnumVisible.HIDE_FOR_OTHER_TEAMS) {
                    this.mcServer.getConfigurationManager().func_177453_a(this, this.getCombatTracker().func_151521_b());
                }
                else if (team.func_178771_j() == Team.EnumVisible.HIDE_FOR_OWN_TEAM) {
                    this.mcServer.getConfigurationManager().func_177452_b(this, this.getCombatTracker().func_151521_b());
                }
            }
            else {
                this.mcServer.getConfigurationManager().sendChatMsg(this.getCombatTracker().func_151521_b());
            }
        }
        if (!this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            this.inventory.dropAllItems();
        }
        final Iterator<ScoreObjective> iterator = this.worldObj.getScoreboard().func_96520_a(IScoreObjectiveCriteria.deathCount).iterator();
        while (iterator.hasNext()) {
            this.getWorldScoreboard().getValueFromObjective(this.getName(), iterator.next()).func_96648_a();
        }
        final EntityLivingBase func_94060_bK = this.func_94060_bK();
        if (func_94060_bK != null) {
            final EntityList.EntityEggInfo entityEggInfo = EntityList.entityEggs.get(EntityList.getEntityID(func_94060_bK));
            if (entityEggInfo != null) {
                this.triggerAchievement(entityEggInfo.field_151513_e);
            }
            func_94060_bK.addToPlayerScore(this, this.scoreValue);
        }
        this.triggerAchievement(StatList.deathsStat);
        this.func_175145_a(StatList.timeSinceDeathStat);
        this.getCombatTracker().func_94549_h();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        if ((!this.mcServer.isDedicatedServer() || !this.func_175400_cq() || !"fall".equals(damageSource.damageType)) && this.respawnInvulnerabilityTicks > 0 && damageSource != DamageSource.outOfWorld) {
            return false;
        }
        if (damageSource instanceof EntityDamageSource) {
            final Entity entity = damageSource.getEntity();
            if (entity instanceof EntityPlayer && !this.canAttackPlayer((EntityPlayer)entity)) {
                return false;
            }
            if (entity instanceof EntityArrow) {
                final EntityArrow entityArrow = (EntityArrow)entity;
                if (entityArrow.shootingEntity instanceof EntityPlayer && !this.canAttackPlayer((EntityPlayer)entityArrow.shootingEntity)) {
                    return false;
                }
            }
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    public boolean canAttackPlayer(final EntityPlayer entityPlayer) {
        return this.func_175400_cq() && super.canAttackPlayer(entityPlayer);
    }
    
    private boolean func_175400_cq() {
        return this.mcServer.isPVPEnabled();
    }
    
    @Override
    public void travelToDimension(final int n) {
        if (this.dimension == 1 && true == true) {
            this.triggerAchievement(AchievementList.theEnd2);
            this.worldObj.removeEntity(this);
            this.playerConqueredTheEnd = true;
            this.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(4, 0.0f));
        }
        else {
            if (this.dimension == 0 && true == true) {
                this.triggerAchievement(AchievementList.theEnd);
                final BlockPos func_180504_m = this.mcServer.worldServerForDimension(1).func_180504_m();
                if (func_180504_m != null) {
                    this.playerNetServerHandler.setPlayerLocation(func_180504_m.getX(), func_180504_m.getY(), func_180504_m.getZ(), 0.0f, 0.0f);
                }
            }
            else {
                this.triggerAchievement(AchievementList.portal);
            }
            this.mcServer.getConfigurationManager().transferPlayerToDimension(this, 1);
            this.lastExperience = -1;
            this.lastHealth = -1.0f;
            this.lastFoodLevel = -1;
        }
    }
    
    @Override
    public boolean func_174827_a(final EntityPlayerMP entityPlayerMP) {
        return entityPlayerMP.func_175149_v() ? (this.func_175398_C() == this) : (!this.func_175149_v() && super.func_174827_a(entityPlayerMP));
    }
    
    private void sendTileEntityUpdate(final TileEntity tileEntity) {
        if (tileEntity != null) {
            final Packet descriptionPacket = tileEntity.getDescriptionPacket();
            if (descriptionPacket != null) {
                this.playerNetServerHandler.sendPacket(descriptionPacket);
            }
        }
    }
    
    @Override
    public void onItemPickup(final Entity entity, final int n) {
        super.onItemPickup(entity, n);
        this.openContainer.detectAndSendChanges();
    }
    
    @Override
    public EnumStatus func_180469_a(final BlockPos blockPos) {
        final EnumStatus func_180469_a = super.func_180469_a(blockPos);
        if (func_180469_a == EnumStatus.OK) {
            final S0APacketUseBed s0APacketUseBed = new S0APacketUseBed(this, blockPos);
            this.getServerForPlayer().getEntityTracker().sendToAllTrackingEntity(this, s0APacketUseBed);
            this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.playerNetServerHandler.sendPacket(s0APacketUseBed);
        }
        return func_180469_a;
    }
    
    @Override
    public void wakeUpPlayer(final boolean b, final boolean b2, final boolean b3) {
        if (this.isPlayerSleeping()) {
            this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, 2));
        }
        super.wakeUpPlayer(b, b2, b3);
        if (this.playerNetServerHandler != null) {
            this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        }
    }
    
    @Override
    public void mountEntity(final Entity entity) {
        final Entity ridingEntity = this.ridingEntity;
        super.mountEntity(entity);
        if (entity != ridingEntity) {
            this.playerNetServerHandler.sendPacket(new S1BPacketEntityAttach(0, this, this.ridingEntity));
            this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        }
    }
    
    @Override
    protected void func_180433_a(final double n, final boolean b, final Block block, final BlockPos blockPos) {
    }
    
    public void handleFalling(final double n, final boolean b) {
        BlockPos offsetDown = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023224), MathHelper.floor_double(this.posZ));
        Block block = this.worldObj.getBlockState(offsetDown).getBlock();
        if (block.getMaterial() == Material.air) {
            final Block block2 = this.worldObj.getBlockState(offsetDown.offsetDown()).getBlock();
            if (block2 instanceof BlockFence || block2 instanceof BlockWall || block2 instanceof BlockFenceGate) {
                offsetDown = offsetDown.offsetDown();
                block = this.worldObj.getBlockState(offsetDown).getBlock();
            }
        }
        super.func_180433_a(n, b, block, offsetDown);
    }
    
    @Override
    public void func_175141_a(final TileEntitySign tileEntitySign) {
        tileEntitySign.func_145912_a(this);
        this.playerNetServerHandler.sendPacket(new S36PacketSignEditorOpen(tileEntitySign.getPos()));
    }
    
    private void getNextWindowId() {
        this.currentWindowId = this.currentWindowId % 100 + 1;
    }
    
    @Override
    public void displayGui(final IInteractionObject interactionObject) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, interactionObject.getGuiID(), interactionObject.getDisplayName()));
        this.openContainer = interactionObject.createContainer(this.inventory, this);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.onCraftGuiOpened(this);
    }
    
    @Override
    public void displayGUIChest(final IInventory inventory) {
        if (this.openContainer != this.inventoryContainer) {
            this.closeScreen();
        }
        if (inventory instanceof ILockableContainer) {
            final ILockableContainer lockableContainer = (ILockableContainer)inventory;
            if (lockableContainer.isLocked() && !this.func_175146_a(lockableContainer.getLockCode()) && !this.func_175149_v()) {
                this.playerNetServerHandler.sendPacket(new S02PacketChat(new ChatComponentTranslation("container.isLocked", new Object[] { inventory.getDisplayName() }), (byte)2));
                this.playerNetServerHandler.sendPacket(new S29PacketSoundEffect("random.door_close", this.posX, this.posY, this.posZ, 1.0f, 1.0f));
                return;
            }
        }
        this.getNextWindowId();
        if (inventory instanceof IInteractionObject) {
            this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, ((IInteractionObject)inventory).getGuiID(), inventory.getDisplayName(), inventory.getSizeInventory()));
            this.openContainer = ((IInteractionObject)inventory).createContainer(this.inventory, this);
        }
        else {
            this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, "minecraft:container", inventory.getDisplayName(), inventory.getSizeInventory()));
            this.openContainer = new ContainerChest(this.inventory, inventory, this);
        }
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.onCraftGuiOpened(this);
    }
    
    @Override
    public void displayVillagerTradeGui(final IMerchant merchant) {
        this.getNextWindowId();
        this.openContainer = new ContainerMerchant(this.inventory, merchant, this.worldObj);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.onCraftGuiOpened(this);
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, "minecraft:villager", merchant.getDisplayName(), ((ContainerMerchant)this.openContainer).getMerchantInventory().getSizeInventory()));
        final MerchantRecipeList recipes = merchant.getRecipes(this);
        if (recipes != null) {
            final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
            packetBuffer.writeInt(this.currentWindowId);
            recipes.func_151391_a(packetBuffer);
            this.playerNetServerHandler.sendPacket(new S3FPacketCustomPayload("MC|TrList", packetBuffer));
        }
    }
    
    @Override
    public void displayGUIHorse(final EntityHorse entityHorse, final IInventory inventory) {
        if (this.openContainer != this.inventoryContainer) {
            this.closeScreen();
        }
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, "EntityHorse", inventory.getDisplayName(), inventory.getSizeInventory(), entityHorse.getEntityId()));
        this.openContainer = new ContainerHorseInventory(this.inventory, inventory, entityHorse, this);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.onCraftGuiOpened(this);
    }
    
    @Override
    public void displayGUIBook(final ItemStack itemStack) {
        if (itemStack.getItem() == Items.written_book) {
            this.playerNetServerHandler.sendPacket(new S3FPacketCustomPayload("MC|BOpen", new PacketBuffer(Unpooled.buffer())));
        }
    }
    
    @Override
    public void sendSlotContents(final Container container, final int n, final ItemStack itemStack) {
        if (!(container.getSlot(n) instanceof SlotCrafting) && !this.isChangingQuantityOnly) {
            this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(container.windowId, n, itemStack));
        }
    }
    
    public void sendContainerToPlayer(final Container container) {
        this.updateCraftingInventory(container, container.getInventory());
    }
    
    @Override
    public void updateCraftingInventory(final Container container, final List list) {
        this.playerNetServerHandler.sendPacket(new S30PacketWindowItems(container.windowId, list));
        this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
    }
    
    @Override
    public void sendProgressBarUpdate(final Container container, final int n, final int n2) {
        this.playerNetServerHandler.sendPacket(new S31PacketWindowProperty(container.windowId, n, n2));
    }
    
    @Override
    public void func_175173_a(final Container container, final IInventory inventory) {
        while (0 < inventory.getFieldCount()) {
            this.playerNetServerHandler.sendPacket(new S31PacketWindowProperty(container.windowId, 0, inventory.getField(0)));
            int n = 0;
            ++n;
        }
    }
    
    public void closeScreen() {
        this.playerNetServerHandler.sendPacket(new S2EPacketCloseWindow(this.openContainer.windowId));
        this.closeContainer();
    }
    
    public void updateHeldItem() {
        if (!this.isChangingQuantityOnly) {
            this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
        }
    }
    
    public void closeContainer() {
        this.openContainer.onContainerClosed(this);
        this.openContainer = this.inventoryContainer;
    }
    
    public void setEntityActionState(final float moveStrafing, final float moveForward, final boolean isJumping, final boolean sneaking) {
        if (this.ridingEntity != null) {
            if (moveStrafing >= -1.0f && moveStrafing <= 1.0f) {
                this.moveStrafing = moveStrafing;
            }
            if (moveForward >= -1.0f && moveForward <= 1.0f) {
                this.moveForward = moveForward;
            }
            this.isJumping = isJumping;
            this.setSneaking(sneaking);
        }
    }
    
    @Override
    public void addStat(final StatBase statBase, final int n) {
        if (statBase != null) {
            this.statsFile.func_150871_b(this, statBase, n);
            final Iterator<ScoreObjective> iterator = this.getWorldScoreboard().func_96520_a(statBase.func_150952_k()).iterator();
            while (iterator.hasNext()) {
                this.getWorldScoreboard().getValueFromObjective(this.getName(), iterator.next()).increseScore(n);
            }
            if (this.statsFile.func_150879_e()) {
                this.statsFile.func_150876_a(this);
            }
        }
    }
    
    @Override
    public void func_175145_a(final StatBase statBase) {
        if (statBase != null) {
            this.statsFile.func_150873_a(this, statBase, 0);
            final Iterator<ScoreObjective> iterator = this.getWorldScoreboard().func_96520_a(statBase.func_150952_k()).iterator();
            while (iterator.hasNext()) {
                this.getWorldScoreboard().getValueFromObjective(this.getName(), iterator.next()).setScorePoints(0);
            }
            if (this.statsFile.func_150879_e()) {
                this.statsFile.func_150876_a(this);
            }
        }
    }
    
    public void mountEntityAndWakeUp() {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.mountEntity(this);
        }
        if (this.sleeping) {
            this.wakeUpPlayer(true, false, false);
        }
    }
    
    public void setPlayerHealthUpdated() {
        this.lastHealth = -1.0E8f;
    }
    
    @Override
    public void addChatComponentMessage(final IChatComponent chatComponent) {
        this.playerNetServerHandler.sendPacket(new S02PacketChat(chatComponent));
    }
    
    @Override
    protected void onItemUseFinish() {
        this.playerNetServerHandler.sendPacket(new S19PacketEntityStatus(this, (byte)9));
        super.onItemUseFinish();
    }
    
    @Override
    public void setItemInUse(final ItemStack itemStack, final int n) {
        super.setItemInUse(itemStack, n);
        if (itemStack != null && itemStack.getItem() != null && itemStack.getItem().getItemUseAction(itemStack) == EnumAction.EAT) {
            this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, 3));
        }
    }
    
    @Override
    public void clonePlayer(final EntityPlayer entityPlayer, final boolean b) {
        super.clonePlayer(entityPlayer, b);
        this.lastExperience = -1;
        this.lastHealth = -1.0f;
        this.lastFoodLevel = -1;
        this.destroyedItemsNetCache.addAll(((EntityPlayerMP)entityPlayer).destroyedItemsNetCache);
    }
    
    @Override
    protected void onNewPotionEffect(final PotionEffect potionEffect) {
        super.onNewPotionEffect(potionEffect);
        this.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.getEntityId(), potionEffect));
    }
    
    @Override
    protected void onChangedPotionEffect(final PotionEffect potionEffect, final boolean b) {
        super.onChangedPotionEffect(potionEffect, b);
        this.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.getEntityId(), potionEffect));
    }
    
    @Override
    protected void onFinishedPotionEffect(final PotionEffect potionEffect) {
        super.onFinishedPotionEffect(potionEffect);
        this.playerNetServerHandler.sendPacket(new S1EPacketRemoveEntityEffect(this.getEntityId(), potionEffect));
    }
    
    @Override
    public void setPositionAndUpdate(final double n, final double n2, final double n3) {
        this.playerNetServerHandler.setPlayerLocation(n, n2, n3, this.rotationYaw, this.rotationPitch);
    }
    
    @Override
    public void onCriticalHit(final Entity entity) {
        this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(entity, 4));
    }
    
    @Override
    public void onEnchantmentCritical(final Entity entity) {
        this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(entity, 5));
    }
    
    @Override
    public void sendPlayerAbilities() {
        if (this.playerNetServerHandler != null) {
            this.playerNetServerHandler.sendPacket(new S39PacketPlayerAbilities(this.capabilities));
            this.func_175135_B();
        }
    }
    
    public WorldServer getServerForPlayer() {
        return (WorldServer)this.worldObj;
    }
    
    @Override
    public void setGameType(final WorldSettings.GameType gameType) {
        this.theItemInWorldManager.setGameType(gameType);
        this.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(3, (float)gameType.getID()));
        if (gameType == WorldSettings.GameType.SPECTATOR) {
            this.mountEntity(null);
        }
        else {
            this.func_175399_e(this);
        }
        this.sendPlayerAbilities();
        this.func_175136_bO();
    }
    
    @Override
    public boolean func_175149_v() {
        return this.theItemInWorldManager.getGameType() == WorldSettings.GameType.SPECTATOR;
    }
    
    @Override
    public void addChatMessage(final IChatComponent chatComponent) {
        this.playerNetServerHandler.sendPacket(new S02PacketChat(chatComponent));
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int n, final String s) {
        if ("seed".equals(s) && !this.mcServer.isDedicatedServer()) {
            return true;
        }
        if ("tell".equals(s) || "help".equals(s) || "me".equals(s) || "trigger".equals(s)) {
            return true;
        }
        if (this.mcServer.getConfigurationManager().canSendCommands(this.getGameProfile())) {
            final UserListOpsEntry userListOpsEntry = (UserListOpsEntry)this.mcServer.getConfigurationManager().getOppedPlayers().getEntry(this.getGameProfile());
            return (userListOpsEntry != null) ? (userListOpsEntry.func_152644_a() >= n) : (this.mcServer.getOpPermissionLevel() >= n);
        }
        return false;
    }
    
    public String getPlayerIP() {
        final String string = this.playerNetServerHandler.netManager.getRemoteAddress().toString();
        final String substring = string.substring(string.indexOf("/") + 1);
        return substring.substring(0, substring.indexOf(":"));
    }
    
    public void handleClientSettings(final C15PacketClientSettings c15PacketClientSettings) {
        this.translator = c15PacketClientSettings.getLang();
        this.chatVisibility = c15PacketClientSettings.getChatVisibility();
        this.chatColours = c15PacketClientSettings.isColorsEnabled();
        this.getDataWatcher().updateObject(10, (byte)c15PacketClientSettings.getView());
    }
    
    public EnumChatVisibility getChatVisibility() {
        return this.chatVisibility;
    }
    
    public void func_175397_a(final String s, final String s2) {
        this.playerNetServerHandler.sendPacket(new S48PacketResourcePackSend(s, s2));
    }
    
    @Override
    public BlockPos getPosition() {
        return new BlockPos(this.posX, this.posY + 0.5, this.posZ);
    }
    
    public void markPlayerActive() {
        this.playerLastActiveTime = MinecraftServer.getCurrentTimeMillis();
    }
    
    public StatisticsFile getStatFile() {
        return this.statsFile;
    }
    
    public void func_152339_d(final Entity entity) {
        if (entity instanceof EntityPlayer) {
            this.playerNetServerHandler.sendPacket(new S13PacketDestroyEntities(new int[] { entity.getEntityId() }));
        }
        else {
            this.destroyedItemsNetCache.add(entity.getEntityId());
        }
    }
    
    @Override
    protected void func_175135_B() {
        if (this.func_175149_v()) {
            this.func_175133_bi();
            this.setInvisible(true);
        }
        else {
            super.func_175135_B();
        }
        this.getServerForPlayer().getEntityTracker().func_180245_a(this);
    }
    
    public Entity func_175398_C() {
        return (this.field_175401_bS == null) ? this : this.field_175401_bS;
    }
    
    public void func_175399_e(final Entity entity) {
        final Entity func_175398_C = this.func_175398_C();
        this.field_175401_bS = ((entity == null) ? this : entity);
        if (func_175398_C != this.field_175401_bS) {
            this.playerNetServerHandler.sendPacket(new S43PacketCamera(this.field_175401_bS));
            this.setPositionAndUpdate(this.field_175401_bS.posX, this.field_175401_bS.posY, this.field_175401_bS.posZ);
        }
    }
    
    @Override
    public void attackTargetEntityWithCurrentItem(final Entity entity) {
        if (this.theItemInWorldManager.getGameType() == WorldSettings.GameType.SPECTATOR) {
            this.func_175399_e(entity);
        }
        else {
            super.attackTargetEntityWithCurrentItem(entity);
        }
    }
    
    public long getLastActiveTime() {
        return this.playerLastActiveTime;
    }
    
    public IChatComponent func_175396_E() {
        return null;
    }
}
