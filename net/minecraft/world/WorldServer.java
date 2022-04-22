package net.minecraft.world;

import net.minecraft.server.*;
import net.minecraft.server.management.*;
import net.minecraft.world.gen.*;
import org.apache.logging.log4j.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.profiler.*;
import com.google.common.collect.*;
import net.minecraft.world.storage.*;
import net.minecraft.village.*;
import net.minecraft.scoreboard.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.effect.*;
import net.minecraft.world.chunk.storage.*;
import net.minecraft.block.state.*;
import com.google.common.base.*;
import net.minecraft.world.gen.structure.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.world.chunk.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.network.play.server.*;
import java.util.*;
import com.google.common.util.concurrent.*;

public class WorldServer extends World implements IThreadListener
{
    private static final Logger logger;
    private final MinecraftServer mcServer;
    private final EntityTracker theEntityTracker;
    private final PlayerManager thePlayerManager;
    private final Set pendingTickListEntriesHashSet;
    private final TreeSet pendingTickListEntriesTreeSet;
    private final Map entitiesByUuid;
    public ChunkProviderServer theChunkProviderServer;
    public boolean disableLevelSaving;
    private boolean allPlayersSleeping;
    private int updateEntityTick;
    private final Teleporter worldTeleporter;
    private final SpawnerAnimals field_175742_R;
    protected final VillageSiege villageSiege;
    private ServerBlockEventList[] field_147490_S;
    private int blockEventCacheIndex;
    private static final List bonusChestContent;
    private List pendingTickListEntriesThisTick;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001437";
        logger = LogManager.getLogger();
        bonusChestContent = Lists.newArrayList(new WeightedRandomChestContent(Items.stick, 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.planks), 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log), 0, 1, 3, 10), new WeightedRandomChestContent(Items.stone_axe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_axe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.stone_pickaxe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.apple, 0, 2, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 2, 3, 3), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log2), 0, 1, 3, 10));
    }
    
    public WorldServer(final MinecraftServer mcServer, final ISaveHandler saveHandler, final WorldInfo worldInfo, final int n, final Profiler profiler) {
        super(saveHandler, worldInfo, WorldProvider.getProviderForDimension(n), profiler, false);
        this.pendingTickListEntriesHashSet = Sets.newHashSet();
        this.pendingTickListEntriesTreeSet = new TreeSet();
        this.entitiesByUuid = Maps.newHashMap();
        this.field_175742_R = new SpawnerAnimals();
        this.villageSiege = new VillageSiege(this);
        this.field_147490_S = new ServerBlockEventList[] { new ServerBlockEventList((Object)null), new ServerBlockEventList((Object)null) };
        this.pendingTickListEntriesThisTick = Lists.newArrayList();
        this.mcServer = mcServer;
        this.theEntityTracker = new EntityTracker(this);
        this.thePlayerManager = new PlayerManager(this);
        this.provider.registerWorld(this);
        this.chunkProvider = this.createChunkProvider();
        this.worldTeleporter = new Teleporter(this);
        this.calculateInitialSkylight();
        this.calculateInitialWeather();
        this.getWorldBorder().setSize(mcServer.getMaxWorldSize());
    }
    
    @Override
    public World init() {
        this.mapStorage = new MapStorage(this.saveHandler);
        final String func_176062_a = VillageCollection.func_176062_a(this.provider);
        final VillageCollection villageCollectionObj = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, func_176062_a);
        if (villageCollectionObj == null) {
            this.villageCollectionObj = new VillageCollection(this);
            this.mapStorage.setData(func_176062_a, this.villageCollectionObj);
        }
        else {
            (this.villageCollectionObj = villageCollectionObj).func_82566_a(this);
        }
        this.worldScoreboard = new ServerScoreboard(this.mcServer);
        ScoreboardSaveData scoreboardSaveData = (ScoreboardSaveData)this.mapStorage.loadData(ScoreboardSaveData.class, "scoreboard");
        if (scoreboardSaveData == null) {
            scoreboardSaveData = new ScoreboardSaveData();
            this.mapStorage.setData("scoreboard", scoreboardSaveData);
        }
        scoreboardSaveData.func_96499_a(this.worldScoreboard);
        ((ServerScoreboard)this.worldScoreboard).func_96547_a(scoreboardSaveData);
        this.getWorldBorder().setCenter(this.worldInfo.func_176120_C(), this.worldInfo.func_176126_D());
        this.getWorldBorder().func_177744_c(this.worldInfo.func_176140_I());
        this.getWorldBorder().setDamageBuffer(this.worldInfo.func_176138_H());
        this.getWorldBorder().setWarningDistance(this.worldInfo.func_176131_J());
        this.getWorldBorder().setWarningTime(this.worldInfo.func_176139_K());
        if (this.worldInfo.func_176134_F() > 0L) {
            this.getWorldBorder().setTransition(this.worldInfo.func_176137_E(), this.worldInfo.func_176132_G(), this.worldInfo.func_176134_F());
        }
        else {
            this.getWorldBorder().setTransition(this.worldInfo.func_176137_E());
        }
        return this;
    }
    
    @Override
    public void tick() {
        super.tick();
        if (this.getWorldInfo().isHardcoreModeEnabled() && this.getDifficulty() != EnumDifficulty.HARD) {
            this.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
        }
        this.provider.getWorldChunkManager().cleanupCache();
        if (this.areAllPlayersAsleep()) {
            if (this.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
                final long n = this.worldInfo.getWorldTime() + 24000L;
                this.worldInfo.setWorldTime(n - n % 24000L);
            }
            this.wakeAllPlayers();
        }
        this.theProfiler.startSection("mobSpawner");
        if (this.getGameRules().getGameRuleBooleanValue("doMobSpawning") && this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD) {
            this.field_175742_R.findChunksForSpawning(this, this.spawnHostileMobs, this.spawnPeacefulMobs, this.worldInfo.getWorldTotalTime() % 400L == 0L);
        }
        this.theProfiler.endStartSection("chunkSource");
        this.chunkProvider.unloadQueuedChunks();
        final int calculateSkylightSubtracted = this.calculateSkylightSubtracted(1.0f);
        if (calculateSkylightSubtracted != this.getSkylightSubtracted()) {
            this.setSkylightSubtracted(calculateSkylightSubtracted);
        }
        this.worldInfo.incrementTotalWorldTime(this.worldInfo.getWorldTotalTime() + 1L);
        if (this.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
            this.worldInfo.setWorldTime(this.worldInfo.getWorldTime() + 1L);
        }
        this.theProfiler.endStartSection("tickPending");
        this.tickUpdates(false);
        this.theProfiler.endStartSection("tickBlocks");
        this.func_147456_g();
        this.theProfiler.endStartSection("chunkMap");
        this.thePlayerManager.updatePlayerInstances();
        this.theProfiler.endStartSection("village");
        this.villageCollectionObj.tick();
        this.villageSiege.tick();
        this.theProfiler.endStartSection("portalForcer");
        this.worldTeleporter.removeStalePortalLocations(this.getTotalWorldTime());
        this.theProfiler.endSection();
        this.func_147488_Z();
    }
    
    public BiomeGenBase.SpawnListEntry func_175734_a(final EnumCreatureType enumCreatureType, final BlockPos blockPos) {
        final List func_177458_a = this.getChunkProvider().func_177458_a(enumCreatureType, blockPos);
        return (func_177458_a != null && !func_177458_a.isEmpty()) ? ((BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(this.rand, func_177458_a)) : null;
    }
    
    public boolean func_175732_a(final EnumCreatureType enumCreatureType, final BiomeGenBase.SpawnListEntry spawnListEntry, final BlockPos blockPos) {
        final List func_177458_a = this.getChunkProvider().func_177458_a(enumCreatureType, blockPos);
        return func_177458_a != null && !func_177458_a.isEmpty() && func_177458_a.contains(spawnListEntry);
    }
    
    @Override
    public void updateAllPlayersSleepingFlag() {
        this.allPlayersSleeping = false;
        if (!this.playerEntities.isEmpty()) {
            for (final EntityPlayer entityPlayer : this.playerEntities) {
                if (entityPlayer.func_175149_v()) {
                    int n = 0;
                    ++n;
                }
                else {
                    if (!entityPlayer.isPlayerSleeping()) {
                        continue;
                    }
                    int n2 = 0;
                    ++n2;
                }
            }
            this.allPlayersSleeping = (0 > 0 && 0 >= this.playerEntities.size() - 0);
        }
    }
    
    protected void wakeAllPlayers() {
        this.allPlayersSleeping = false;
        for (final EntityPlayer entityPlayer : this.playerEntities) {
            if (entityPlayer.isPlayerSleeping()) {
                entityPlayer.wakeUpPlayer(false, false, true);
            }
        }
        this.resetRainAndThunder();
    }
    
    private void resetRainAndThunder() {
        this.worldInfo.setRainTime(0);
        this.worldInfo.setRaining(false);
        this.worldInfo.setThunderTime(0);
        this.worldInfo.setThundering(false);
    }
    
    public boolean areAllPlayersAsleep() {
        if (this.allPlayersSleeping && !this.isRemote) {
            for (final EntityPlayer entityPlayer : this.playerEntities) {
                if (entityPlayer.func_175149_v() || !entityPlayer.isPlayerFullyAsleep()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void setInitialSpawnLocation() {
        if (this.worldInfo.getSpawnY() <= 0) {
            this.worldInfo.setSpawnY(64);
        }
        int spawnX = this.worldInfo.getSpawnX();
        int spawnZ = this.worldInfo.getSpawnZ();
        while (this.getGroundAboveSeaLevel(new BlockPos(spawnX, 0, spawnZ)).getMaterial() == Material.air) {
            spawnX += this.rand.nextInt(8) - this.rand.nextInt(8);
            spawnZ += this.rand.nextInt(8) - this.rand.nextInt(8);
            int n = 0;
            ++n;
            if (0 == 10000) {
                break;
            }
        }
        this.worldInfo.setSpawnX(spawnX);
        this.worldInfo.setSpawnZ(spawnZ);
    }
    
    @Override
    protected void func_147456_g() {
        super.func_147456_g();
        if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
            for (final ChunkCoordIntPair chunkCoordIntPair : this.activeChunkSet) {
                this.getChunkFromChunkCoords(chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos).func_150804_b(false);
            }
        }
        else {
            for (final ChunkCoordIntPair chunkCoordIntPair2 : this.activeChunkSet) {
                final int n = chunkCoordIntPair2.chunkXPos * 16;
                final int n2 = chunkCoordIntPair2.chunkZPos * 16;
                this.theProfiler.startSection("getChunk");
                final Chunk chunkFromChunkCoords = this.getChunkFromChunkCoords(chunkCoordIntPair2.chunkXPos, chunkCoordIntPair2.chunkZPos);
                this.func_147467_a(n, n2, chunkFromChunkCoords);
                this.theProfiler.endStartSection("tickChunk");
                chunkFromChunkCoords.func_150804_b(false);
                this.theProfiler.endStartSection("thunder");
                if (this.rand.nextInt(100000) == 0 && this.isRaining() && this.isThundering()) {
                    this.updateLCG = this.updateLCG * 3 + 1013904223;
                    final int n3 = this.updateLCG >> 2;
                    final BlockPos func_175736_a = this.func_175736_a(new BlockPos(n + (n3 & 0xF), 0, n2 + (n3 >> 8 & 0xF)));
                    if (this.func_175727_C(func_175736_a)) {
                        this.addWeatherEffect(new EntityLightningBolt(this, func_175736_a.getX(), func_175736_a.getY(), func_175736_a.getZ()));
                    }
                }
                this.theProfiler.endStartSection("iceandsnow");
                if (this.rand.nextInt(16) == 0) {
                    this.updateLCG = this.updateLCG * 3 + 1013904223;
                    final int n4 = this.updateLCG >> 2;
                    final BlockPos func_175725_q = this.func_175725_q(new BlockPos(n + (n4 & 0xF), 0, n2 + (n4 >> 8 & 0xF)));
                    final BlockPos offsetDown = func_175725_q.offsetDown();
                    if (this.func_175662_w(offsetDown)) {
                        this.setBlockState(offsetDown, Blocks.ice.getDefaultState());
                    }
                    if (this.isRaining() && this.func_175708_f(func_175725_q, true)) {
                        this.setBlockState(func_175725_q, Blocks.snow_layer.getDefaultState());
                    }
                    if (this.isRaining() && this.getBiomeGenForCoords(offsetDown).canSpawnLightningBolt()) {
                        this.getBlockState(offsetDown).getBlock().fillWithRain(this, offsetDown);
                    }
                }
                this.theProfiler.endStartSection("tickBlocks");
                final int int1 = this.getGameRules().getInt("randomTickSpeed");
                if (int1 > 0) {
                    final ExtendedBlockStorage[] blockStorageArray = chunkFromChunkCoords.getBlockStorageArray();
                    while (0 < blockStorageArray.length) {
                        final ExtendedBlockStorage extendedBlockStorage = blockStorageArray[0];
                        if (extendedBlockStorage != null && extendedBlockStorage.getNeedsRandomTick()) {
                            while (0 < int1) {
                                this.updateLCG = this.updateLCG * 3 + 1013904223;
                                final int n5 = this.updateLCG >> 2;
                                final int n6 = n5 & 0xF;
                                final int n7 = n5 >> 8 & 0xF;
                                final int n8 = n5 >> 16 & 0xF;
                                int n9 = 0;
                                ++n9;
                                final BlockPos blockPos = new BlockPos(n6 + n, n8 + extendedBlockStorage.getYLocation(), n7 + n2);
                                final IBlockState value = extendedBlockStorage.get(n6, n8, n7);
                                final Block block = value.getBlock();
                                if (block.getTickRandomly()) {
                                    int n10 = 0;
                                    ++n10;
                                    block.randomTick(this, blockPos, value, this.rand);
                                }
                                int n11 = 0;
                                ++n11;
                            }
                        }
                        int n12 = 0;
                        ++n12;
                    }
                }
                this.theProfiler.endSection();
            }
        }
    }
    
    protected BlockPos func_175736_a(final BlockPos blockPos) {
        final BlockPos func_175725_q = this.func_175725_q(blockPos);
        final List func_175647_a = this.func_175647_a(EntityLivingBase.class, new AxisAlignedBB(func_175725_q, new BlockPos(func_175725_q.getX(), this.getHeight(), func_175725_q.getZ())).expand(3.0, 3.0, 3.0), new Predicate() {
            private static final String __OBFID;
            final WorldServer this$0;
            
            public boolean func_180242_a(final EntityLivingBase entityLivingBase) {
                return entityLivingBase != null && entityLivingBase.isEntityAlive() && this.this$0.isAgainstSky(entityLivingBase.getPosition());
            }
            
            @Override
            public boolean apply(final Object o) {
                return this.func_180242_a((EntityLivingBase)o);
            }
            
            static {
                __OBFID = "CL_00001889";
            }
        });
        return func_175647_a.isEmpty() ? func_175725_q : func_175647_a.get(this.rand.nextInt(func_175647_a.size())).getPosition();
    }
    
    @Override
    public boolean isBlockTickPending(final BlockPos blockPos, final Block block) {
        return this.pendingTickListEntriesThisTick.contains(new NextTickListEntry(blockPos, block));
    }
    
    @Override
    public void scheduleUpdate(final BlockPos blockPos, final Block block, final int n) {
        this.func_175654_a(blockPos, block, n, 0);
    }
    
    @Override
    public void func_175654_a(final BlockPos blockPos, final Block block, final int n, final int priority) {
        final NextTickListEntry nextTickListEntry = new NextTickListEntry(blockPos, block);
        if (this.scheduledUpdatesAreImmediate && block.getMaterial() != Material.air && block.requiresUpdates()) {
            if (this.isAreaLoaded(nextTickListEntry.field_180282_a.add(-8, -8, -8), nextTickListEntry.field_180282_a.add(8, 8, 8))) {
                final IBlockState blockState = this.getBlockState(nextTickListEntry.field_180282_a);
                if (blockState.getBlock().getMaterial() != Material.air && blockState.getBlock() == nextTickListEntry.func_151351_a()) {
                    blockState.getBlock().updateTick(this, nextTickListEntry.field_180282_a, blockState, this.rand);
                }
            }
            return;
        }
        if (this.isAreaLoaded(blockPos.add(-8, -8, -8), blockPos.add(8, 8, 8))) {
            if (block.getMaterial() != Material.air) {
                nextTickListEntry.setScheduledTime(1 + this.worldInfo.getWorldTotalTime());
                nextTickListEntry.setPriority(priority);
            }
            if (!this.pendingTickListEntriesHashSet.contains(nextTickListEntry)) {
                this.pendingTickListEntriesHashSet.add(nextTickListEntry);
                this.pendingTickListEntriesTreeSet.add(nextTickListEntry);
            }
        }
    }
    
    @Override
    public void func_180497_b(final BlockPos blockPos, final Block block, final int n, final int priority) {
        final NextTickListEntry nextTickListEntry = new NextTickListEntry(blockPos, block);
        nextTickListEntry.setPriority(priority);
        if (block.getMaterial() != Material.air) {
            nextTickListEntry.setScheduledTime(n + this.worldInfo.getWorldTotalTime());
        }
        if (!this.pendingTickListEntriesHashSet.contains(nextTickListEntry)) {
            this.pendingTickListEntriesHashSet.add(nextTickListEntry);
            this.pendingTickListEntriesTreeSet.add(nextTickListEntry);
        }
    }
    
    @Override
    public void updateEntities() {
        if (this.playerEntities.isEmpty()) {
            if (this.updateEntityTick++ >= 1200) {
                return;
            }
        }
        else {
            this.resetUpdateEntityTick();
        }
        super.updateEntities();
    }
    
    public void resetUpdateEntityTick() {
        this.updateEntityTick = 0;
    }
    
    @Override
    public boolean tickUpdates(final boolean b) {
        if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
            return false;
        }
        this.pendingTickListEntriesTreeSet.size();
        if (1000 != this.pendingTickListEntriesHashSet.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
        }
        if (1000 > 1000) {}
        this.theProfiler.startSection("cleaning");
        while (0 < 1000) {
            final NextTickListEntry nextTickListEntry = this.pendingTickListEntriesTreeSet.first();
            if (!b && nextTickListEntry.scheduledTime > this.worldInfo.getWorldTotalTime()) {
                break;
            }
            this.pendingTickListEntriesTreeSet.remove(nextTickListEntry);
            this.pendingTickListEntriesHashSet.remove(nextTickListEntry);
            this.pendingTickListEntriesThisTick.add(nextTickListEntry);
            int n = 0;
            ++n;
        }
        this.theProfiler.endSection();
        this.theProfiler.startSection("ticking");
        final Iterator<NextTickListEntry> iterator = (Iterator<NextTickListEntry>)this.pendingTickListEntriesThisTick.iterator();
        while (iterator.hasNext()) {
            final NextTickListEntry nextTickListEntry2 = iterator.next();
            iterator.remove();
            if (this.isAreaLoaded(nextTickListEntry2.field_180282_a.add(0, 0, 0), nextTickListEntry2.field_180282_a.add(0, 0, 0))) {
                final IBlockState blockState = this.getBlockState(nextTickListEntry2.field_180282_a);
                if (blockState.getBlock().getMaterial() == Material.air || !Block.isEqualTo(blockState.getBlock(), nextTickListEntry2.func_151351_a())) {
                    continue;
                }
                blockState.getBlock().updateTick(this, nextTickListEntry2.field_180282_a, blockState, this.rand);
            }
            else {
                this.scheduleUpdate(nextTickListEntry2.field_180282_a, nextTickListEntry2.func_151351_a(), 0);
            }
        }
        this.theProfiler.endSection();
        this.pendingTickListEntriesThisTick.clear();
        return !this.pendingTickListEntriesTreeSet.isEmpty();
    }
    
    @Override
    public List getPendingBlockUpdates(final Chunk chunk, final boolean b) {
        final ChunkCoordIntPair chunkCoordIntPair = chunk.getChunkCoordIntPair();
        final int n = (chunkCoordIntPair.chunkXPos << 4) - 2;
        final int n2 = n + 16 + 2;
        final int n3 = (chunkCoordIntPair.chunkZPos << 4) - 2;
        return this.func_175712_a(new StructureBoundingBox(n, 0, n3, n2, 256, n3 + 16 + 2), b);
    }
    
    @Override
    public List func_175712_a(final StructureBoundingBox structureBoundingBox, final boolean b) {
        ArrayList<NextTickListEntry> arrayList = null;
        while (0 < 2) {
            Iterator<NextTickListEntry> iterator;
            if (!false) {
                iterator = (Iterator<NextTickListEntry>)this.pendingTickListEntriesTreeSet.iterator();
            }
            else {
                iterator = (Iterator<NextTickListEntry>)this.pendingTickListEntriesThisTick.iterator();
                if (!this.pendingTickListEntriesThisTick.isEmpty()) {
                    WorldServer.logger.debug("toBeTicked = " + this.pendingTickListEntriesThisTick.size());
                }
            }
            while (iterator.hasNext()) {
                final NextTickListEntry nextTickListEntry = iterator.next();
                final BlockPos field_180282_a = nextTickListEntry.field_180282_a;
                if (field_180282_a.getX() >= structureBoundingBox.minX && field_180282_a.getX() < structureBoundingBox.maxX && field_180282_a.getZ() >= structureBoundingBox.minZ && field_180282_a.getZ() < structureBoundingBox.maxZ) {
                    if (b) {
                        this.pendingTickListEntriesHashSet.remove(nextTickListEntry);
                        iterator.remove();
                    }
                    if (arrayList == null) {
                        arrayList = (ArrayList<NextTickListEntry>)Lists.newArrayList();
                    }
                    arrayList.add(nextTickListEntry);
                }
            }
            int n = 0;
            ++n;
        }
        return arrayList;
    }
    
    @Override
    public void updateEntityWithOptionalForce(final Entity entity, final boolean b) {
        if (!this.func_175735_ai() && (entity instanceof EntityAnimal || entity instanceof EntityWaterMob)) {
            entity.setDead();
        }
        if (!this.func_175738_ah() && entity instanceof INpc) {
            entity.setDead();
        }
        super.updateEntityWithOptionalForce(entity, b);
    }
    
    private boolean func_175738_ah() {
        return this.mcServer.getCanSpawnNPCs();
    }
    
    private boolean func_175735_ai() {
        return this.mcServer.getCanSpawnAnimals();
    }
    
    @Override
    protected IChunkProvider createChunkProvider() {
        return (IChunkProvider)(this.theChunkProviderServer = new ChunkProviderServer(this, this.saveHandler.getChunkLoader(this.provider), this.provider.createChunkGenerator()));
    }
    
    public List func_147486_a(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final ArrayList arrayList = Lists.newArrayList();
        while (0 < this.loadedTileEntityList.size()) {
            final TileEntity tileEntity = this.loadedTileEntityList.get(0);
            final BlockPos pos = tileEntity.getPos();
            if (pos.getX() >= n && pos.getY() >= n2 && pos.getZ() >= n3 && pos.getX() < n4 && pos.getY() < n5 && pos.getZ() < n6) {
                arrayList.add(tileEntity);
            }
            int n7 = 0;
            ++n7;
        }
        return arrayList;
    }
    
    @Override
    public boolean isBlockModifiable(final EntityPlayer entityPlayer, final BlockPos blockPos) {
        return !this.mcServer.isBlockProtected(this, blockPos, entityPlayer) && this.getWorldBorder().contains(blockPos);
    }
    
    @Override
    public void initialize(final WorldSettings worldSettings) {
        if (!this.worldInfo.isInitialized()) {
            this.createSpawnPosition(worldSettings);
            if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
                this.setDebugWorldSettings();
            }
            super.initialize(worldSettings);
            this.worldInfo.setServerInitialized(true);
        }
    }
    
    private void setDebugWorldSettings() {
        this.worldInfo.setMapFeaturesEnabled(false);
        this.worldInfo.setAllowCommands(true);
        this.worldInfo.setRaining(false);
        this.worldInfo.setThundering(false);
        this.worldInfo.func_176142_i(1000000000);
        this.worldInfo.setWorldTime(6000L);
        this.worldInfo.setGameType(WorldSettings.GameType.SPECTATOR);
        this.worldInfo.setHardcore(false);
        this.worldInfo.setDifficulty(EnumDifficulty.PEACEFUL);
        this.worldInfo.setDifficultyLocked(true);
        this.getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
    }
    
    private void createSpawnPosition(final WorldSettings worldSettings) {
        if (!this.provider.canRespawnHere()) {
            this.worldInfo.setSpawn(BlockPos.ORIGIN.offsetUp(this.provider.getAverageGroundLevel()));
        }
        else if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
            this.worldInfo.setSpawn(BlockPos.ORIGIN.offsetUp());
        }
        else {
            this.findingSpawnPoint = true;
            final WorldChunkManager worldChunkManager = this.provider.getWorldChunkManager();
            final List biomesToSpawnIn = worldChunkManager.getBiomesToSpawnIn();
            final Random random = new Random(this.getSeed());
            final BlockPos biomePosition = worldChunkManager.findBiomePosition(0, 0, 256, biomesToSpawnIn, random);
            final int averageGroundLevel = this.provider.getAverageGroundLevel();
            if (biomePosition != null) {
                biomePosition.getX();
                biomePosition.getZ();
            }
            else {
                WorldServer.logger.warn("Unable to find spawn biome");
            }
            while (!this.provider.canCoordinateBeSpawn(0, 0)) {
                final int n = 0 + (random.nextInt(64) - random.nextInt(64));
                final int n2 = 0 + (random.nextInt(64) - random.nextInt(64));
                int n3 = 0;
                ++n3;
                if (0 == 1000) {
                    break;
                }
            }
            this.worldInfo.setSpawn(new BlockPos(0, averageGroundLevel, 0));
            this.findingSpawnPoint = false;
            if (worldSettings.isBonusChestEnabled()) {
                this.createBonusChest();
            }
        }
    }
    
    protected void createBonusChest() {
        final WorldGeneratorBonusChest worldGeneratorBonusChest = new WorldGeneratorBonusChest(WorldServer.bonusChestContent, 10);
        while (0 < 10 && !worldGeneratorBonusChest.generate((World)this, this.rand, this.func_175672_r(new BlockPos(this.worldInfo.getSpawnX() + this.rand.nextInt(6) - this.rand.nextInt(6), 0, this.worldInfo.getSpawnZ() + this.rand.nextInt(6) - this.rand.nextInt(6))).offsetUp())) {
            int n = 0;
            ++n;
        }
    }
    
    public BlockPos func_180504_m() {
        return this.provider.func_177496_h();
    }
    
    public void saveAllChunks(final boolean b, final IProgressUpdate progressUpdate) throws MinecraftException {
        if (this.chunkProvider.canSave()) {
            if (progressUpdate != null) {
                progressUpdate.displaySavingString("Saving level");
            }
            this.saveLevel();
            if (progressUpdate != null) {
                progressUpdate.displayLoadingString("Saving chunks");
            }
            this.chunkProvider.saveChunks(b, progressUpdate);
            for (final Chunk chunk : this.theChunkProviderServer.func_152380_a()) {
                if (!this.thePlayerManager.func_152621_a(chunk.xPosition, chunk.zPosition)) {
                    this.theChunkProviderServer.dropChunk(chunk.xPosition, chunk.zPosition);
                }
            }
        }
    }
    
    public void saveChunkData() {
        if (this.chunkProvider.canSave()) {
            this.chunkProvider.saveExtraData();
        }
    }
    
    protected void saveLevel() throws MinecraftException {
        this.checkSessionLock();
        this.worldInfo.func_176145_a(this.getWorldBorder().getDiameter());
        this.worldInfo.func_176124_d(this.getWorldBorder().getCenterX());
        this.worldInfo.func_176141_c(this.getWorldBorder().getCenterZ());
        this.worldInfo.func_176129_e(this.getWorldBorder().getDamageBuffer());
        this.worldInfo.func_176125_f(this.getWorldBorder().func_177727_n());
        this.worldInfo.func_176122_j(this.getWorldBorder().getWarningDistance());
        this.worldInfo.func_176136_k(this.getWorldBorder().getWarningTime());
        this.worldInfo.func_176118_b(this.getWorldBorder().getTargetSize());
        this.worldInfo.func_176135_e(this.getWorldBorder().getTimeUntilTarget());
        this.saveHandler.saveWorldInfoWithPlayer(this.worldInfo, this.mcServer.getConfigurationManager().getHostPlayerData());
        this.mapStorage.saveAllData();
    }
    
    @Override
    protected void onEntityAdded(final Entity entity) {
        super.onEntityAdded(entity);
        this.entitiesById.addKey(entity.getEntityId(), entity);
        this.entitiesByUuid.put(entity.getUniqueID(), entity);
        final Entity[] parts = entity.getParts();
        if (parts != null) {
            while (0 < parts.length) {
                this.entitiesById.addKey(parts[0].getEntityId(), parts[0]);
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    protected void onEntityRemoved(final Entity entity) {
        super.onEntityRemoved(entity);
        this.entitiesById.removeObject(entity.getEntityId());
        this.entitiesByUuid.remove(entity.getUniqueID());
        final Entity[] parts = entity.getParts();
        if (parts != null) {
            while (0 < parts.length) {
                this.entitiesById.removeObject(parts[0].getEntityId());
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public boolean addWeatherEffect(final Entity entity) {
        if (super.addWeatherEffect(entity)) {
            this.mcServer.getConfigurationManager().sendToAllNear(entity.posX, entity.posY, entity.posZ, 512.0, this.provider.getDimensionId(), new S2CPacketSpawnGlobalEntity(entity));
            return true;
        }
        return false;
    }
    
    @Override
    public void setEntityState(final Entity entity, final byte b) {
        this.getEntityTracker().func_151248_b(entity, new S19PacketEntityStatus(entity, b));
    }
    
    @Override
    public Explosion newExplosion(final Entity entity, final double n, final double n2, final double n3, final float n4, final boolean b, final boolean b2) {
        final Explosion explosion = new Explosion(this, entity, n, n2, n3, n4, b, b2);
        explosion.doExplosionA();
        explosion.doExplosionB(false);
        if (!b2) {
            explosion.func_180342_d();
        }
        for (final EntityPlayer entityPlayer : this.playerEntities) {
            if (entityPlayer.getDistanceSq(n, n2, n3) < 4096.0) {
                ((EntityPlayerMP)entityPlayer).playerNetServerHandler.sendPacket(new S27PacketExplosion(n, n2, n3, n4, explosion.func_180343_e(), (Vec3)explosion.func_77277_b().get(entityPlayer)));
            }
        }
        return explosion;
    }
    
    @Override
    public void addBlockEvent(final BlockPos blockPos, final Block block, final int n, final int n2) {
        final BlockEventData blockEventData = new BlockEventData(blockPos, block, n, n2);
        final Iterator<BlockEventData> iterator = this.field_147490_S[this.blockEventCacheIndex].iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(blockEventData)) {
                return;
            }
        }
        this.field_147490_S[this.blockEventCacheIndex].add(blockEventData);
    }
    
    private void func_147488_Z() {
        while (!this.field_147490_S[this.blockEventCacheIndex].isEmpty()) {
            final int blockEventCacheIndex = this.blockEventCacheIndex;
            this.blockEventCacheIndex ^= 0x1;
            for (final BlockEventData blockEventData : this.field_147490_S[blockEventCacheIndex]) {
                if (this.func_147485_a(blockEventData)) {
                    this.mcServer.getConfigurationManager().sendToAllNear(blockEventData.func_180328_a().getX(), blockEventData.func_180328_a().getY(), blockEventData.func_180328_a().getZ(), 64.0, this.provider.getDimensionId(), new S24PacketBlockAction(blockEventData.func_180328_a(), blockEventData.getBlock(), blockEventData.getEventID(), blockEventData.getEventParameter()));
                }
            }
            this.field_147490_S[blockEventCacheIndex].clear();
        }
    }
    
    private boolean func_147485_a(final BlockEventData blockEventData) {
        final IBlockState blockState = this.getBlockState(blockEventData.func_180328_a());
        return blockState.getBlock() == blockEventData.getBlock() && blockState.getBlock().onBlockEventReceived(this, blockEventData.func_180328_a(), blockState, blockEventData.getEventID(), blockEventData.getEventParameter());
    }
    
    public void flush() {
        this.saveHandler.flush();
    }
    
    @Override
    protected void updateWeather() {
        final boolean raining = this.isRaining();
        super.updateWeather();
        if (this.prevRainingStrength != this.rainingStrength) {
            this.mcServer.getConfigurationManager().sendPacketToAllPlayersInDimension(new S2BPacketChangeGameState(7, this.rainingStrength), this.provider.getDimensionId());
        }
        if (this.prevThunderingStrength != this.thunderingStrength) {
            this.mcServer.getConfigurationManager().sendPacketToAllPlayersInDimension(new S2BPacketChangeGameState(8, this.thunderingStrength), this.provider.getDimensionId());
        }
        if (raining != this.isRaining()) {
            if (raining) {
                this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(2, 0.0f));
            }
            else {
                this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(1, 0.0f));
            }
            this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(7, this.rainingStrength));
            this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(8, this.thunderingStrength));
        }
    }
    
    @Override
    protected int getRenderDistanceChunks() {
        return this.mcServer.getConfigurationManager().getViewDistance();
    }
    
    public MinecraftServer func_73046_m() {
        return this.mcServer;
    }
    
    public EntityTracker getEntityTracker() {
        return this.theEntityTracker;
    }
    
    public PlayerManager getPlayerManager() {
        return this.thePlayerManager;
    }
    
    public Teleporter getDefaultTeleporter() {
        return this.worldTeleporter;
    }
    
    public void func_175739_a(final EnumParticleTypes enumParticleTypes, final double n, final double n2, final double n3, final int n4, final double n5, final double n6, final double n7, final double n8, final int... array) {
        this.func_180505_a(enumParticleTypes, false, n, n2, n3, n4, n5, n6, n7, n8, array);
    }
    
    public void func_180505_a(final EnumParticleTypes enumParticleTypes, final boolean b, final double n, final double n2, final double n3, final int n4, final double n5, final double n6, final double n7, final double n8, final int... array) {
        final S2APacketParticles s2APacketParticles = new S2APacketParticles(enumParticleTypes, b, (float)n, (float)n2, (float)n3, (float)n5, (float)n6, (float)n7, (float)n8, n4, array);
        while (0 < this.playerEntities.size()) {
            final EntityPlayerMP entityPlayerMP = this.playerEntities.get(0);
            final double distanceSq = entityPlayerMP.getPosition().distanceSq(n, n2, n3);
            if (distanceSq <= 256.0 || (b && distanceSq <= 65536.0)) {
                entityPlayerMP.playerNetServerHandler.sendPacket(s2APacketParticles);
            }
            int n9 = 0;
            ++n9;
        }
    }
    
    public Entity getEntityFromUuid(final UUID uuid) {
        return this.entitiesByUuid.get(uuid);
    }
    
    @Override
    public ListenableFuture addScheduledTask(final Runnable runnable) {
        return this.mcServer.addScheduledTask(runnable);
    }
    
    @Override
    public boolean isCallingFromMinecraftThread() {
        return this.mcServer.isCallingFromMinecraftThread();
    }
    
    static class ServerBlockEventList extends ArrayList
    {
        private static final String __OBFID;
        
        private ServerBlockEventList() {
        }
        
        ServerBlockEventList(final Object o) {
            this();
        }
        
        static {
            __OBFID = "CL_00001439";
        }
    }
}
