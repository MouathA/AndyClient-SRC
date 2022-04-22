package net.minecraft.world;

import net.minecraft.world.storage.*;
import net.minecraft.village.*;
import net.minecraft.profiler.*;
import net.minecraft.scoreboard.*;
import net.minecraft.world.border.*;
import com.google.common.collect.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.structure.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.server.gui.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.command.*;
import com.google.common.base.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.crash.*;
import java.util.concurrent.*;
import net.minecraft.server.*;
import net.minecraft.nbt.*;

public abstract class World implements IBlockAccess
{
    protected boolean scheduledUpdatesAreImmediate;
    public final List loadedEntityList;
    protected final List unloadedEntityList;
    public final List loadedTileEntityList;
    public final List tickableTileEntities;
    private final List addedTileEntityList;
    private final List tileEntitiesToBeRemoved;
    public final List playerEntities;
    public final List weatherEffects;
    protected final IntHashMap entitiesById;
    private long cloudColour;
    private int skylightSubtracted;
    protected int updateLCG;
    protected final int DIST_HASH_MAGIC = 1013904223;
    protected float prevRainingStrength;
    protected float rainingStrength;
    protected float prevThunderingStrength;
    protected float thunderingStrength;
    private int lastLightningBolt;
    public final Random rand;
    public final WorldProvider provider;
    protected List worldAccesses;
    protected IChunkProvider chunkProvider;
    protected final ISaveHandler saveHandler;
    protected WorldInfo worldInfo;
    protected boolean findingSpawnPoint;
    protected MapStorage mapStorage;
    protected VillageCollection villageCollectionObj;
    public final Profiler theProfiler;
    private final Calendar theCalendar;
    protected Scoreboard worldScoreboard;
    public final boolean isRemote;
    protected Set activeChunkSet;
    private int ambientTickCountdown;
    protected boolean spawnHostileMobs;
    protected boolean spawnPeacefulMobs;
    private boolean processingLoadedTiles;
    private final WorldBorder worldBorder;
    int[] lightUpdateBlockList;
    private static final String __OBFID;
    
    protected World(final ISaveHandler saveHandler, final WorldInfo worldInfo, final WorldProvider provider, final Profiler theProfiler, final boolean isRemote) {
        this.loadedEntityList = Lists.newArrayList();
        this.unloadedEntityList = Lists.newArrayList();
        this.loadedTileEntityList = Lists.newArrayList();
        this.tickableTileEntities = Lists.newArrayList();
        this.addedTileEntityList = Lists.newArrayList();
        this.tileEntitiesToBeRemoved = Lists.newArrayList();
        this.playerEntities = Lists.newArrayList();
        this.weatherEffects = Lists.newArrayList();
        this.entitiesById = new IntHashMap();
        this.cloudColour = 16777215L;
        this.updateLCG = new Random().nextInt();
        this.rand = new Random();
        this.worldAccesses = Lists.newArrayList();
        this.theCalendar = Calendar.getInstance();
        this.worldScoreboard = new Scoreboard();
        this.activeChunkSet = Sets.newHashSet();
        this.ambientTickCountdown = this.rand.nextInt(12000);
        this.spawnHostileMobs = true;
        this.spawnPeacefulMobs = true;
        this.lightUpdateBlockList = new int[32768];
        this.saveHandler = saveHandler;
        this.theProfiler = theProfiler;
        this.worldInfo = worldInfo;
        this.provider = provider;
        this.isRemote = isRemote;
        this.worldBorder = provider.getWorldBorder();
    }
    
    public World init() {
        return this;
    }
    
    @Override
    public BiomeGenBase getBiomeGenForCoords(final BlockPos blockPos) {
        if (this.isBlockLoaded(blockPos)) {
            return this.getChunkFromBlockCoords(blockPos).getBiome(blockPos, this.provider.getWorldChunkManager());
        }
        return this.provider.getWorldChunkManager().func_180300_a(blockPos, BiomeGenBase.plains);
    }
    
    public WorldChunkManager getWorldChunkManager() {
        return this.provider.getWorldChunkManager();
    }
    
    protected abstract IChunkProvider createChunkProvider();
    
    public void initialize(final WorldSettings worldSettings) {
        this.worldInfo.setServerInitialized(true);
    }
    
    public void setInitialSpawnLocation() {
        this.setSpawnLocation(new BlockPos(8, 64, 8));
    }
    
    public Block getGroundAboveSeaLevel(final BlockPos blockPos) {
        BlockPos offsetUp;
        for (offsetUp = new BlockPos(blockPos.getX(), 63, blockPos.getZ()); this != offsetUp.offsetUp(); offsetUp = offsetUp.offsetUp()) {}
        return this.getBlockState(offsetUp).getBlock();
    }
    
    public boolean isBlockLoaded(final BlockPos blockPos) {
        return this.isBlockLoaded(blockPos, true);
    }
    
    public boolean isBlockLoaded(final BlockPos blockPos, final boolean b) {
        return this < blockPos && this.isChunkLoaded(blockPos.getX() >> 4, blockPos.getZ() >> 4, b);
    }
    
    public boolean isAreaLoaded(final BlockPos blockPos, final int n) {
        return this.isAreaLoaded(blockPos, n, true);
    }
    
    public boolean isAreaLoaded(final BlockPos blockPos, final int n, final boolean b) {
        return this.isAreaLoaded(blockPos.getX() - n, blockPos.getY() - n, blockPos.getZ() - n, blockPos.getX() + n, blockPos.getY() + n, blockPos.getZ() + n, b);
    }
    
    public boolean isAreaLoaded(final BlockPos blockPos, final BlockPos blockPos2) {
        return this.isAreaLoaded(blockPos, blockPos2, true);
    }
    
    public boolean isAreaLoaded(final BlockPos blockPos, final BlockPos blockPos2, final boolean b) {
        return this.isAreaLoaded(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos2.getX(), blockPos2.getY(), blockPos2.getZ(), b);
    }
    
    public boolean isAreaLoaded(final StructureBoundingBox structureBoundingBox) {
        return this.isAreaLoaded(structureBoundingBox, true);
    }
    
    public boolean isAreaLoaded(final StructureBoundingBox structureBoundingBox, final boolean b) {
        return this.isAreaLoaded(structureBoundingBox.minX, structureBoundingBox.minY, structureBoundingBox.minZ, structureBoundingBox.maxX, structureBoundingBox.maxY, structureBoundingBox.maxZ, b);
    }
    
    public Chunk getChunkFromBlockCoords(final BlockPos blockPos) {
        return this.getChunkFromChunkCoords(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }
    
    public Chunk getChunkFromChunkCoords(final int n, final int n2) {
        return this.chunkProvider.provideChunk(n, n2);
    }
    
    public boolean setBlockState(final BlockPos blockPos, final IBlockState blockState, final int n) {
        if (this >= blockPos) {
            return false;
        }
        if (!this.isRemote && this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
            return false;
        }
        final Chunk chunkFromBlockCoords = this.getChunkFromBlockCoords(blockPos);
        final Block block = blockState.getBlock();
        final IBlockState setBlockState = chunkFromBlockCoords.setBlockState(blockPos, blockState);
        if (setBlockState == null) {
            return false;
        }
        final Block block2 = setBlockState.getBlock();
        if (block.getLightOpacity() != block2.getLightOpacity() || block.getLightValue() != block2.getLightValue()) {
            this.theProfiler.startSection("checkLight");
            this.checkLight(blockPos);
            this.theProfiler.endSection();
        }
        if ((n & 0x2) != 0x0 && (!this.isRemote || (n & 0x4) == 0x0) && chunkFromBlockCoords.isPopulated()) {
            this.markBlockForUpdate(blockPos);
        }
        if (!this.isRemote && (n & 0x1) != 0x0) {
            this.func_175722_b(blockPos, setBlockState.getBlock());
            if (block.hasComparatorInputOverride()) {
                this.updateComparatorOutputLevel(blockPos, block);
            }
        }
        return true;
    }
    
    public boolean setBlockToAir(final BlockPos blockPos) {
        return this.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
    }
    
    public boolean destroyBlock(final BlockPos blockPos, final boolean b) {
        final IBlockState blockState = this.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if (block.getMaterial() == Material.air) {
            return false;
        }
        this.playAuxSFX(2001, blockPos, Block.getStateId(blockState));
        if (b) {
            block.dropBlockAsItem(this, blockPos, blockState, 0);
        }
        return this.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
    }
    
    public boolean setBlockState(final BlockPos blockPos, final IBlockState blockState) {
        return this.setBlockState(blockPos, blockState, 3);
    }
    
    public void markBlockForUpdate(final BlockPos blockPos) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).markBlockForUpdate(blockPos);
            int n = 0;
            ++n;
        }
    }
    
    public void func_175722_b(final BlockPos blockPos, final Block block) {
        if (this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD) {
            this.notifyNeighborsOfStateChange(blockPos, block);
        }
    }
    
    public void markBlocksDirtyVertical(final int n, final int n2, int n3, int n4) {
        if (n3 > n4) {
            final int n5 = n4;
            n4 = n3;
            n3 = n5;
        }
        if (!this.provider.getHasNoSky()) {
            for (int i = n3; i <= n4; ++i) {
                this.checkLightFor(EnumSkyBlock.SKY, new BlockPos(n, i, n2));
            }
        }
        this.markBlockRangeForRenderUpdate(n, n3, n2, n, n4, n2);
    }
    
    public void markBlockRangeForRenderUpdate(final BlockPos blockPos, final BlockPos blockPos2) {
        this.markBlockRangeForRenderUpdate(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
    }
    
    public void markBlockRangeForRenderUpdate(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).markBlockRangeForRenderUpdate(n, n2, n3, n4, n5, n6);
            int n7 = 0;
            ++n7;
        }
    }
    
    public void notifyNeighborsOfStateChange(final BlockPos blockPos, final Block block) {
        this.notifyBlockOfStateChange(blockPos.offsetWest(), block);
        this.notifyBlockOfStateChange(blockPos.offsetEast(), block);
        this.notifyBlockOfStateChange(blockPos.offsetDown(), block);
        this.notifyBlockOfStateChange(blockPos.offsetUp(), block);
        this.notifyBlockOfStateChange(blockPos.offsetNorth(), block);
        this.notifyBlockOfStateChange(blockPos.offsetSouth(), block);
    }
    
    public void notifyNeighborsOfStateExcept(final BlockPos blockPos, final Block block, final EnumFacing enumFacing) {
        if (enumFacing != EnumFacing.WEST) {
            this.notifyBlockOfStateChange(blockPos.offsetWest(), block);
        }
        if (enumFacing != EnumFacing.EAST) {
            this.notifyBlockOfStateChange(blockPos.offsetEast(), block);
        }
        if (enumFacing != EnumFacing.DOWN) {
            this.notifyBlockOfStateChange(blockPos.offsetDown(), block);
        }
        if (enumFacing != EnumFacing.UP) {
            this.notifyBlockOfStateChange(blockPos.offsetUp(), block);
        }
        if (enumFacing != EnumFacing.NORTH) {
            this.notifyBlockOfStateChange(blockPos.offsetNorth(), block);
        }
        if (enumFacing != EnumFacing.SOUTH) {
            this.notifyBlockOfStateChange(blockPos.offsetSouth(), block);
        }
    }
    
    public void notifyBlockOfStateChange(final BlockPos blockPos, final Block block) {
        if (!this.isRemote) {
            final IBlockState blockState = this.getBlockState(blockPos);
            blockState.getBlock().onNeighborBlockChange(this, blockPos, blockState, block);
        }
    }
    
    public boolean isBlockTickPending(final BlockPos blockPos, final Block block) {
        return false;
    }
    
    public boolean isAgainstSky(final BlockPos blockPos) {
        return this.getChunkFromBlockCoords(blockPos).canSeeSky(blockPos);
    }
    
    public boolean canBlockSeeSky(final BlockPos blockPos) {
        if (blockPos.getY() >= 63) {
            return this.isAgainstSky(blockPos);
        }
        final BlockPos blockPos2 = new BlockPos(blockPos.getX(), 63, blockPos.getZ());
        if (!this.isAgainstSky(blockPos2)) {
            return false;
        }
        for (BlockPos blockPos3 = blockPos2.offsetDown(); blockPos3.getY() > blockPos.getY(); blockPos3 = blockPos3.offsetDown()) {
            final Block block = this.getBlockState(blockPos3).getBlock();
            if (block.getLightOpacity() > 0 && !block.getMaterial().isLiquid()) {
                return false;
            }
        }
        return true;
    }
    
    public int getLight(BlockPos blockPos) {
        if (blockPos.getY() < 0) {
            return 0;
        }
        if (blockPos.getY() >= 256) {
            blockPos = new BlockPos(blockPos.getX(), 255, blockPos.getZ());
        }
        return this.getChunkFromBlockCoords(blockPos).setLight(blockPos, 0);
    }
    
    public int getLightFromNeighbors(final BlockPos blockPos) {
        return this.getLight(blockPos, true);
    }
    
    public int getLight(BlockPos blockPos, final boolean b) {
        if (blockPos.getX() < -30000000 || blockPos.getZ() < -30000000 || blockPos.getX() >= 30000000 || blockPos.getZ() >= 30000000) {
            return 15;
        }
        if (b && this.getBlockState(blockPos).getBlock().getUseNeighborBrightness()) {
            int light = this.getLight(blockPos.offsetUp(), false);
            final int light2 = this.getLight(blockPos.offsetEast(), false);
            final int light3 = this.getLight(blockPos.offsetWest(), false);
            final int light4 = this.getLight(blockPos.offsetSouth(), false);
            final int light5 = this.getLight(blockPos.offsetNorth(), false);
            if (light2 > light) {
                light = light2;
            }
            if (light3 > light) {
                light = light3;
            }
            if (light4 > light) {
                light = light4;
            }
            if (light5 > light) {
                light = light5;
            }
            return light;
        }
        if (blockPos.getY() < 0) {
            return 0;
        }
        if (blockPos.getY() >= 256) {
            blockPos = new BlockPos(blockPos.getX(), 255, blockPos.getZ());
        }
        return this.getChunkFromBlockCoords(blockPos).setLight(blockPos, this.skylightSubtracted);
    }
    
    public BlockPos getHorizon(final BlockPos p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //     4: ldc_w           -30000000
        //     7: if_icmplt       93
        //    10: aload_1        
        //    11: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //    14: ldc_w           -30000000
        //    17: if_icmplt       93
        //    20: aload_1        
        //    21: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //    24: ldc_w           30000000
        //    27: if_icmpge       93
        //    30: aload_1        
        //    31: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //    34: ldc_w           30000000
        //    37: if_icmpge       93
        //    40: aload_0        
        //    41: aload_1        
        //    42: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //    45: iconst_4       
        //    46: ishr           
        //    47: aload_1        
        //    48: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //    51: iconst_4       
        //    52: ishr           
        //    53: aload_0        
        //    54: aload_1        
        //    55: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //    58: iconst_4       
        //    59: ishr           
        //    60: aload_1        
        //    61: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //    64: iconst_4       
        //    65: ishr           
        //    66: invokevirtual   net/minecraft/world/World.getChunkFromChunkCoords:(II)Lnet/minecraft/world/chunk/Chunk;
        //    69: aload_1        
        //    70: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //    73: bipush          15
        //    75: iand           
        //    76: aload_1        
        //    77: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //    80: bipush          15
        //    82: iand           
        //    83: invokevirtual   net/minecraft/world/chunk/Chunk.getHeight:(II)I
        //    86: istore_2       
        //    87: goto            93
        //    90: goto            93
        //    93: new             Lnet/minecraft/util/BlockPos;
        //    96: dup            
        //    97: aload_1        
        //    98: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   101: bipush          64
        //   103: aload_1        
        //   104: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   107: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   110: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0093 (coming from #0087).
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
    
    public int getChunksLowestHorizon(final int n, final int n2) {
        if (n >= -30000000 && n2 >= -30000000 && n < 30000000 && n2 < 30000000) {
            n >> 4;
            n2 >> 4;
            return 0;
        }
        return 64;
    }
    
    public int getLightFromNeighborsFor(final EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
        if (this.provider.getHasNoSky() && enumSkyBlock == EnumSkyBlock.SKY) {
            return 0;
        }
        if (blockPos.getY() < 0) {
            blockPos = new BlockPos(blockPos.getX(), 0, blockPos.getZ());
        }
        if (this >= blockPos) {
            return enumSkyBlock.defaultLightValue;
        }
        if (!this.isBlockLoaded(blockPos)) {
            return enumSkyBlock.defaultLightValue;
        }
        if (this.getBlockState(blockPos).getBlock().getUseNeighborBrightness()) {
            int light = this.getLightFor(enumSkyBlock, blockPos.offsetUp());
            final int light2 = this.getLightFor(enumSkyBlock, blockPos.offsetEast());
            final int light3 = this.getLightFor(enumSkyBlock, blockPos.offsetWest());
            final int light4 = this.getLightFor(enumSkyBlock, blockPos.offsetSouth());
            final int light5 = this.getLightFor(enumSkyBlock, blockPos.offsetNorth());
            if (light2 > light) {
                light = light2;
            }
            if (light3 > light) {
                light = light3;
            }
            if (light4 > light) {
                light = light4;
            }
            if (light5 > light) {
                light = light5;
            }
            return light;
        }
        return this.getChunkFromBlockCoords(blockPos).getLightFor(enumSkyBlock, blockPos);
    }
    
    public int getLightFor(final EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
        if (blockPos.getY() < 0) {
            blockPos = new BlockPos(blockPos.getX(), 0, blockPos.getZ());
        }
        if (this >= blockPos) {
            return enumSkyBlock.defaultLightValue;
        }
        if (!this.isBlockLoaded(blockPos)) {
            return enumSkyBlock.defaultLightValue;
        }
        return this.getChunkFromBlockCoords(blockPos).getLightFor(enumSkyBlock, blockPos);
    }
    
    public void setLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos, final int n) {
        if (this >= blockPos && this.isBlockLoaded(blockPos)) {
            this.getChunkFromBlockCoords(blockPos).setLightFor(enumSkyBlock, blockPos, n);
            this.notifyLightSet(blockPos);
        }
    }
    
    public void notifyLightSet(final BlockPos blockPos) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).notifyLightSet(blockPos);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public int getCombinedLight(final BlockPos blockPos, final int n) {
        final int lightFromNeighbors = this.getLightFromNeighborsFor(EnumSkyBlock.SKY, blockPos);
        int lightFromNeighbors2 = this.getLightFromNeighborsFor(EnumSkyBlock.BLOCK, blockPos);
        if (lightFromNeighbors2 < n) {
            lightFromNeighbors2 = n;
        }
        return lightFromNeighbors << 20 | lightFromNeighbors2 << 4;
    }
    
    public float getLightBrightness(final BlockPos blockPos) {
        return this.provider.getLightBrightnessTable()[this.getLightFromNeighbors(blockPos)];
    }
    
    @Override
    public IBlockState getBlockState(final BlockPos blockPos) {
        if (this >= blockPos) {
            return Blocks.air.getDefaultState();
        }
        return this.getChunkFromBlockCoords(blockPos).getBlockState(blockPos);
    }
    
    public boolean isDaytime() {
        return this.skylightSubtracted < 4;
    }
    
    public MovingObjectPosition rayTraceBlocks(final Vec3 vec3, final Vec3 vec4) {
        return this.rayTraceBlocks(vec3, vec4, false, false, false);
    }
    
    public MovingObjectPosition rayTraceBlocks(final Vec3 vec3, final Vec3 vec4, final boolean b) {
        return this.rayTraceBlocks(vec3, vec4, b, false, false);
    }
    
    public MovingObjectPosition rayTraceBlocks(final Vec3 p0, final Vec3 p1, final boolean p2, final boolean p3, final boolean p4) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/util/Vec3.xCoord:D
        //     4: invokestatic    java/lang/Double.isNaN:(D)Z
        //     7: ifne            888
        //    10: aload_1        
        //    11: getfield        net/minecraft/util/Vec3.yCoord:D
        //    14: invokestatic    java/lang/Double.isNaN:(D)Z
        //    17: ifne            888
        //    20: aload_1        
        //    21: getfield        net/minecraft/util/Vec3.zCoord:D
        //    24: invokestatic    java/lang/Double.isNaN:(D)Z
        //    27: ifne            888
        //    30: aload_2        
        //    31: getfield        net/minecraft/util/Vec3.xCoord:D
        //    34: invokestatic    java/lang/Double.isNaN:(D)Z
        //    37: ifne            886
        //    40: aload_2        
        //    41: getfield        net/minecraft/util/Vec3.yCoord:D
        //    44: invokestatic    java/lang/Double.isNaN:(D)Z
        //    47: ifne            886
        //    50: aload_2        
        //    51: getfield        net/minecraft/util/Vec3.zCoord:D
        //    54: invokestatic    java/lang/Double.isNaN:(D)Z
        //    57: ifne            886
        //    60: aload_2        
        //    61: getfield        net/minecraft/util/Vec3.xCoord:D
        //    64: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    67: istore          6
        //    69: aload_2        
        //    70: getfield        net/minecraft/util/Vec3.yCoord:D
        //    73: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    76: istore          7
        //    78: aload_2        
        //    79: getfield        net/minecraft/util/Vec3.zCoord:D
        //    82: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    85: istore          8
        //    87: aload_1        
        //    88: getfield        net/minecraft/util/Vec3.xCoord:D
        //    91: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    94: istore          9
        //    96: aload_1        
        //    97: getfield        net/minecraft/util/Vec3.yCoord:D
        //   100: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   103: istore          10
        //   105: aload_1        
        //   106: getfield        net/minecraft/util/Vec3.zCoord:D
        //   109: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   112: istore          11
        //   114: new             Lnet/minecraft/util/BlockPos;
        //   117: dup            
        //   118: iload           9
        //   120: iload           10
        //   122: iload           11
        //   124: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   127: astore          12
        //   129: new             Lnet/minecraft/util/BlockPos;
        //   132: iload           6
        //   134: iload           7
        //   136: iload           8
        //   138: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   141: aload_0        
        //   142: aload           12
        //   144: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   147: astore          13
        //   149: aload           13
        //   151: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   156: astore          14
        //   158: iload           4
        //   160: ifeq            176
        //   163: aload           14
        //   165: aload_0        
        //   166: aload           12
        //   168: aload           13
        //   170: invokevirtual   net/minecraft/block/Block.getCollisionBoundingBox:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/util/AxisAlignedBB;
        //   173: ifnull          207
        //   176: aload           14
        //   178: aload           13
        //   180: iload_3        
        //   181: invokevirtual   net/minecraft/block/Block.canCollideCheck:(Lnet/minecraft/block/state/IBlockState;Z)Z
        //   184: ifeq            207
        //   187: aload           14
        //   189: aload_0        
        //   190: aload           12
        //   192: aload_1        
        //   193: aload_2        
        //   194: invokevirtual   net/minecraft/block/Block.collisionRayTrace:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/Vec3;Lnet/minecraft/util/Vec3;)Lnet/minecraft/util/MovingObjectPosition;
        //   197: astore          15
        //   199: aload           15
        //   201: ifnull          207
        //   204: aload           15
        //   206: areturn        
        //   207: aconst_null    
        //   208: astore          15
        //   210: goto            865
        //   213: aload_1        
        //   214: getfield        net/minecraft/util/Vec3.xCoord:D
        //   217: invokestatic    java/lang/Double.isNaN:(D)Z
        //   220: ifne            243
        //   223: aload_1        
        //   224: getfield        net/minecraft/util/Vec3.yCoord:D
        //   227: invokestatic    java/lang/Double.isNaN:(D)Z
        //   230: ifne            243
        //   233: aload_1        
        //   234: getfield        net/minecraft/util/Vec3.zCoord:D
        //   237: invokestatic    java/lang/Double.isNaN:(D)Z
        //   240: ifeq            245
        //   243: aconst_null    
        //   244: areturn        
        //   245: iload           9
        //   247: iload           6
        //   249: if_icmpne       278
        //   252: iload           10
        //   254: iload           7
        //   256: if_icmpne       278
        //   259: iload           11
        //   261: iload           8
        //   263: if_icmpne       278
        //   266: iload           5
        //   268: ifeq            276
        //   271: aload           15
        //   273: goto            277
        //   276: aconst_null    
        //   277: areturn        
        //   278: ldc2_w          999.0
        //   281: dstore          20
        //   283: ldc2_w          999.0
        //   286: dstore          22
        //   288: ldc2_w          999.0
        //   291: dstore          24
        //   293: iload           6
        //   295: iload           9
        //   297: if_icmple       310
        //   300: iload           9
        //   302: i2d            
        //   303: dconst_1       
        //   304: dadd           
        //   305: dstore          20
        //   307: goto            327
        //   310: iload           6
        //   312: iload           9
        //   314: if_icmpge       327
        //   317: iload           9
        //   319: i2d            
        //   320: dconst_0       
        //   321: dadd           
        //   322: dstore          20
        //   324: goto            327
        //   327: iload           7
        //   329: iload           10
        //   331: if_icmple       344
        //   334: iload           10
        //   336: i2d            
        //   337: dconst_1       
        //   338: dadd           
        //   339: dstore          22
        //   341: goto            361
        //   344: iload           7
        //   346: iload           10
        //   348: if_icmpge       361
        //   351: iload           10
        //   353: i2d            
        //   354: dconst_0       
        //   355: dadd           
        //   356: dstore          22
        //   358: goto            361
        //   361: iload           8
        //   363: iload           11
        //   365: if_icmple       378
        //   368: iload           11
        //   370: i2d            
        //   371: dconst_1       
        //   372: dadd           
        //   373: dstore          24
        //   375: goto            395
        //   378: iload           8
        //   380: iload           11
        //   382: if_icmpge       395
        //   385: iload           11
        //   387: i2d            
        //   388: dconst_0       
        //   389: dadd           
        //   390: dstore          24
        //   392: goto            395
        //   395: ldc2_w          999.0
        //   398: dstore          26
        //   400: ldc2_w          999.0
        //   403: dstore          28
        //   405: ldc2_w          999.0
        //   408: dstore          30
        //   410: aload_2        
        //   411: getfield        net/minecraft/util/Vec3.xCoord:D
        //   414: aload_1        
        //   415: getfield        net/minecraft/util/Vec3.xCoord:D
        //   418: dsub           
        //   419: dstore          32
        //   421: aload_2        
        //   422: getfield        net/minecraft/util/Vec3.yCoord:D
        //   425: aload_1        
        //   426: getfield        net/minecraft/util/Vec3.yCoord:D
        //   429: dsub           
        //   430: dstore          34
        //   432: aload_2        
        //   433: getfield        net/minecraft/util/Vec3.zCoord:D
        //   436: aload_1        
        //   437: getfield        net/minecraft/util/Vec3.zCoord:D
        //   440: dsub           
        //   441: dstore          36
        //   443: goto            473
        //   446: dload           20
        //   448: aload_1        
        //   449: getfield        net/minecraft/util/Vec3.xCoord:D
        //   452: dsub           
        //   453: dload           32
        //   455: ddiv           
        //   456: dstore          26
        //   458: goto            488
        //   461: dload           22
        //   463: aload_1        
        //   464: getfield        net/minecraft/util/Vec3.yCoord:D
        //   467: dsub           
        //   468: dload           34
        //   470: ddiv           
        //   471: dstore          28
        //   473: goto            488
        //   476: dload           24
        //   478: aload_1        
        //   479: getfield        net/minecraft/util/Vec3.zCoord:D
        //   482: dsub           
        //   483: dload           36
        //   485: ddiv           
        //   486: dstore          30
        //   488: dload           26
        //   490: ldc2_w          -0.0
        //   493: dcmpl          
        //   494: ifne            502
        //   497: ldc2_w          -1.0E-4
        //   500: dstore          26
        //   502: dload           28
        //   504: ldc2_w          -0.0
        //   507: dcmpl          
        //   508: ifne            516
        //   511: ldc2_w          -1.0E-4
        //   514: dstore          28
        //   516: dload           30
        //   518: ldc2_w          -0.0
        //   521: dcmpl          
        //   522: ifne            530
        //   525: ldc2_w          -1.0E-4
        //   528: dstore          30
        //   530: dload           26
        //   532: dload           28
        //   534: dcmpg          
        //   535: ifge            597
        //   538: dload           26
        //   540: dload           30
        //   542: dcmpg          
        //   543: ifge            597
        //   546: iload           6
        //   548: iload           9
        //   550: if_icmple       559
        //   553: getstatic       net/minecraft/util/EnumFacing.WEST:Lnet/minecraft/util/EnumFacing;
        //   556: goto            562
        //   559: getstatic       net/minecraft/util/EnumFacing.EAST:Lnet/minecraft/util/EnumFacing;
        //   562: astore          38
        //   564: new             Lnet/minecraft/util/Vec3;
        //   567: dup            
        //   568: dload           20
        //   570: aload_1        
        //   571: getfield        net/minecraft/util/Vec3.yCoord:D
        //   574: dload           34
        //   576: dload           26
        //   578: dmul           
        //   579: dadd           
        //   580: aload_1        
        //   581: getfield        net/minecraft/util/Vec3.zCoord:D
        //   584: dload           36
        //   586: dload           26
        //   588: dmul           
        //   589: dadd           
        //   590: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   593: astore_1       
        //   594: goto            704
        //   597: dload           28
        //   599: dload           30
        //   601: dcmpg          
        //   602: ifge            656
        //   605: iload           7
        //   607: iload           10
        //   609: if_icmple       618
        //   612: getstatic       net/minecraft/util/EnumFacing.DOWN:Lnet/minecraft/util/EnumFacing;
        //   615: goto            621
        //   618: getstatic       net/minecraft/util/EnumFacing.UP:Lnet/minecraft/util/EnumFacing;
        //   621: astore          38
        //   623: new             Lnet/minecraft/util/Vec3;
        //   626: dup            
        //   627: aload_1        
        //   628: getfield        net/minecraft/util/Vec3.xCoord:D
        //   631: dload           32
        //   633: dload           28
        //   635: dmul           
        //   636: dadd           
        //   637: dload           22
        //   639: aload_1        
        //   640: getfield        net/minecraft/util/Vec3.zCoord:D
        //   643: dload           36
        //   645: dload           28
        //   647: dmul           
        //   648: dadd           
        //   649: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   652: astore_1       
        //   653: goto            704
        //   656: iload           8
        //   658: iload           11
        //   660: if_icmple       669
        //   663: getstatic       net/minecraft/util/EnumFacing.NORTH:Lnet/minecraft/util/EnumFacing;
        //   666: goto            672
        //   669: getstatic       net/minecraft/util/EnumFacing.SOUTH:Lnet/minecraft/util/EnumFacing;
        //   672: astore          38
        //   674: new             Lnet/minecraft/util/Vec3;
        //   677: dup            
        //   678: aload_1        
        //   679: getfield        net/minecraft/util/Vec3.xCoord:D
        //   682: dload           32
        //   684: dload           30
        //   686: dmul           
        //   687: dadd           
        //   688: aload_1        
        //   689: getfield        net/minecraft/util/Vec3.yCoord:D
        //   692: dload           34
        //   694: dload           30
        //   696: dmul           
        //   697: dadd           
        //   698: dload           24
        //   700: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   703: astore_1       
        //   704: aload_1        
        //   705: getfield        net/minecraft/util/Vec3.xCoord:D
        //   708: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   711: aload           38
        //   713: getstatic       net/minecraft/util/EnumFacing.EAST:Lnet/minecraft/util/EnumFacing;
        //   716: if_acmpne       723
        //   719: iconst_1       
        //   720: goto            719
        //   723: istore          9
        //   725: aload_1        
        //   726: getfield        net/minecraft/util/Vec3.yCoord:D
        //   729: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   732: aload           38
        //   734: getstatic       net/minecraft/util/EnumFacing.UP:Lnet/minecraft/util/EnumFacing;
        //   737: if_acmpne       744
        //   740: iconst_1       
        //   741: goto            740
        //   744: istore          10
        //   746: aload_1        
        //   747: getfield        net/minecraft/util/Vec3.zCoord:D
        //   750: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   753: aload           38
        //   755: getstatic       net/minecraft/util/EnumFacing.SOUTH:Lnet/minecraft/util/EnumFacing;
        //   758: if_acmpne       765
        //   761: iconst_1       
        //   762: goto            761
        //   765: istore          11
        //   767: new             Lnet/minecraft/util/BlockPos;
        //   770: dup            
        //   771: iload           9
        //   773: iload           10
        //   775: iload           11
        //   777: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   780: astore          12
        //   782: aload_0        
        //   783: aload           12
        //   785: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   788: astore          39
        //   790: aload           39
        //   792: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   797: astore          40
        //   799: iload           4
        //   801: ifeq            817
        //   804: aload           40
        //   806: aload_0        
        //   807: aload           12
        //   809: aload           39
        //   811: invokevirtual   net/minecraft/block/Block.getCollisionBoundingBox:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/util/AxisAlignedBB;
        //   814: ifnull          865
        //   817: aload           40
        //   819: aload           39
        //   821: iload_3        
        //   822: invokevirtual   net/minecraft/block/Block.canCollideCheck:(Lnet/minecraft/block/state/IBlockState;Z)Z
        //   825: ifeq            848
        //   828: aload           40
        //   830: aload_0        
        //   831: aload           12
        //   833: aload_1        
        //   834: aload_2        
        //   835: invokevirtual   net/minecraft/block/Block.collisionRayTrace:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/Vec3;Lnet/minecraft/util/Vec3;)Lnet/minecraft/util/MovingObjectPosition;
        //   838: astore          41
        //   840: aload           41
        //   842: ifnull          865
        //   845: aload           41
        //   847: areturn        
        //   848: new             Lnet/minecraft/util/MovingObjectPosition;
        //   851: dup            
        //   852: getstatic       net/minecraft/util/MovingObjectPosition$MovingObjectType.MISS:Lnet/minecraft/util/MovingObjectPosition$MovingObjectType;
        //   855: aload_1        
        //   856: aload           38
        //   858: aload           12
        //   860: invokespecial   net/minecraft/util/MovingObjectPosition.<init>:(Lnet/minecraft/util/MovingObjectPosition$MovingObjectType;Lnet/minecraft/util/Vec3;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/BlockPos;)V
        //   863: astore          15
        //   865: sipush          200
        //   868: iinc            16, -1
        //   871: ifge            213
        //   874: iload           5
        //   876: ifeq            884
        //   879: aload           15
        //   881: goto            885
        //   884: aconst_null    
        //   885: areturn        
        //   886: aconst_null    
        //   887: areturn        
        //   888: aconst_null    
        //   889: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0761 (coming from #0762).
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
    
    public void playSoundAtEntity(final Entity entity, final String s, final float n, final float n2) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).playSound(s, entity.posX, entity.posY, entity.posZ, n, n2);
            int n3 = 0;
            ++n3;
        }
    }
    
    public void playSoundToNearExcept(final EntityPlayer entityPlayer, final String s, final float n, final float n2) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).playSoundToNearExcept(entityPlayer, s, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, n, n2);
            int n3 = 0;
            ++n3;
        }
    }
    
    public void playSoundEffect(final double n, final double n2, final double n3, final String s, final float n4, final float n5) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).playSound(s, n, n2, n3, n4, n5);
            int n6 = 0;
            ++n6;
        }
    }
    
    public void playSound(final double n, final double n2, final double n3, final String s, final float n4, final float n5, final boolean b) {
    }
    
    public void func_175717_a(final BlockPos blockPos, final String s) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).func_174961_a(s, blockPos);
            int n = 0;
            ++n;
        }
    }
    
    public void spawnParticle(final EnumParticleTypes enumParticleTypes, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final int... array) {
        this.spawnParticle(enumParticleTypes.func_179348_c(), enumParticleTypes.func_179344_e(), n, n2, n3, n4, n5, n6, array);
    }
    
    public void spawnParticle(final EnumParticleTypes enumParticleTypes, final boolean b, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final int... array) {
        this.spawnParticle(enumParticleTypes.func_179348_c(), enumParticleTypes.func_179344_e() | b, n, n2, n3, n4, n5, n6, array);
    }
    
    private void spawnParticle(final int n, final boolean b, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).func_180442_a(n, b, n2, n3, n4, n5, n6, n7, array);
            int n8 = 0;
            ++n8;
        }
    }
    
    public boolean addWeatherEffect(final Entity entity) {
        this.weatherEffects.add(entity);
        return true;
    }
    
    public boolean spawnEntityInWorld(final Entity entity) {
        final int floor_double = MathHelper.floor_double(entity.posX / 16.0);
        final int floor_double2 = MathHelper.floor_double(entity.posZ / 16.0);
        final boolean forceSpawn = entity.forceSpawn;
        if (entity instanceof EntityPlayer) {}
        if (entity instanceof EntityPlayer) {
            this.playerEntities.add(entity);
            this.updateAllPlayersSleepingFlag();
        }
        this.getChunkFromChunkCoords(floor_double, floor_double2).addEntity(entity);
        this.loadedEntityList.add(entity);
        this.onEntityAdded(entity);
        return true;
    }
    
    protected void onEntityAdded(final Entity entity) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).onEntityAdded(entity);
            int n = 0;
            ++n;
        }
    }
    
    protected void onEntityRemoved(final Entity entity) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).onEntityRemoved(entity);
            int n = 0;
            ++n;
        }
    }
    
    public void removeEntity(final Entity entity) {
        if (entity.riddenByEntity != null) {
            entity.riddenByEntity.mountEntity(null);
        }
        if (entity.ridingEntity != null) {
            entity.mountEntity(null);
        }
        entity.setDead();
        if (entity instanceof EntityPlayer) {
            this.playerEntities.remove(entity);
            this.updateAllPlayersSleepingFlag();
            this.onEntityRemoved(entity);
        }
    }
    
    public void removePlayerEntityDangerously(final Entity p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/entity/Entity.setDead:()V
        //     4: aload_1        
        //     5: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //     8: ifeq            26
        //    11: aload_0        
        //    12: getfield        net/minecraft/world/World.playerEntities:Ljava/util/List;
        //    15: aload_1        
        //    16: invokeinterface java/util/List.remove:(Ljava/lang/Object;)Z
        //    21: pop            
        //    22: aload_0        
        //    23: invokevirtual   net/minecraft/world/World.updateAllPlayersSleepingFlag:()V
        //    26: aload_1        
        //    27: getfield        net/minecraft/entity/Entity.chunkCoordX:I
        //    30: istore_2       
        //    31: aload_1        
        //    32: getfield        net/minecraft/entity/Entity.chunkCoordZ:I
        //    35: istore_3       
        //    36: aload_1        
        //    37: getfield        net/minecraft/entity/Entity.addedToChunk:Z
        //    40: ifeq            56
        //    43: aload_0        
        //    44: iload_2        
        //    45: iload_3        
        //    46: aload_0        
        //    47: iload_2        
        //    48: iload_3        
        //    49: invokevirtual   net/minecraft/world/World.getChunkFromChunkCoords:(II)Lnet/minecraft/world/chunk/Chunk;
        //    52: aload_1        
        //    53: invokevirtual   net/minecraft/world/chunk/Chunk.removeEntity:(Lnet/minecraft/entity/Entity;)V
        //    56: aload_0        
        //    57: getfield        net/minecraft/world/World.loadedEntityList:Ljava/util/List;
        //    60: aload_1        
        //    61: invokeinterface java/util/List.remove:(Ljava/lang/Object;)Z
        //    66: pop            
        //    67: aload_0        
        //    68: aload_1        
        //    69: invokevirtual   net/minecraft/world/World.onEntityRemoved:(Lnet/minecraft/entity/Entity;)V
        //    72: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0056 (coming from #0053).
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
    
    public void addWorldAccess(final IWorldAccess worldAccess) {
        this.worldAccesses.add(worldAccess);
    }
    
    public void removeWorldAccess(final IWorldAccess worldAccess) {
        this.worldAccesses.remove(worldAccess);
    }
    
    public List getCollidingBoundingBoxes(final Entity p0, final AxisAlignedBB p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: astore_3       
        //     4: aload_2        
        //     5: getfield        net/minecraft/util/AxisAlignedBB.minX:D
        //     8: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    11: istore          4
        //    13: aload_2        
        //    14: getfield        net/minecraft/util/AxisAlignedBB.maxX:D
        //    17: dconst_1       
        //    18: dadd           
        //    19: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    22: istore          5
        //    24: aload_2        
        //    25: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //    28: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    31: istore          6
        //    33: aload_2        
        //    34: getfield        net/minecraft/util/AxisAlignedBB.maxY:D
        //    37: dconst_1       
        //    38: dadd           
        //    39: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    42: istore          7
        //    44: aload_2        
        //    45: getfield        net/minecraft/util/AxisAlignedBB.minZ:D
        //    48: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    51: istore          8
        //    53: aload_2        
        //    54: getfield        net/minecraft/util/AxisAlignedBB.maxZ:D
        //    57: dconst_1       
        //    58: dadd           
        //    59: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    62: istore          9
        //    64: iload           4
        //    66: istore          10
        //    68: goto            249
        //    71: iload           8
        //    73: istore          11
        //    75: goto            239
        //    78: aload_0        
        //    79: new             Lnet/minecraft/util/BlockPos;
        //    82: dup            
        //    83: iload           10
        //    85: bipush          64
        //    87: iload           11
        //    89: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //    92: invokevirtual   net/minecraft/world/World.isBlockLoaded:(Lnet/minecraft/util/BlockPos;)Z
        //    95: ifeq            236
        //    98: iload           6
        //   100: iconst_1       
        //   101: isub           
        //   102: istore          12
        //   104: goto            229
        //   107: new             Lnet/minecraft/util/BlockPos;
        //   110: dup            
        //   111: iload           10
        //   113: iload           12
        //   115: iload           11
        //   117: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   120: astore          13
        //   122: aload_1        
        //   123: invokevirtual   net/minecraft/entity/Entity.isOutsideBorder:()Z
        //   126: istore          14
        //   128: aload_0        
        //   129: aload_0        
        //   130: invokevirtual   net/minecraft/world/World.getWorldBorder:()Lnet/minecraft/world/border/WorldBorder;
        //   133: aload_1        
        //   134: invokevirtual   net/minecraft/world/World.isInsideBorder:(Lnet/minecraft/world/border/WorldBorder;Lnet/minecraft/entity/Entity;)Z
        //   137: istore          15
        //   139: iload           14
        //   141: ifeq            157
        //   144: iload           15
        //   146: ifeq            157
        //   149: aload_1        
        //   150: iconst_0       
        //   151: invokevirtual   net/minecraft/entity/Entity.setOutsideBorder:(Z)V
        //   154: goto            172
        //   157: iload           14
        //   159: ifne            172
        //   162: iload           15
        //   164: ifne            172
        //   167: aload_1        
        //   168: iconst_1       
        //   169: invokevirtual   net/minecraft/entity/Entity.setOutsideBorder:(Z)V
        //   172: aload_0        
        //   173: invokevirtual   net/minecraft/world/World.getWorldBorder:()Lnet/minecraft/world/border/WorldBorder;
        //   176: aload           13
        //   178: invokevirtual   net/minecraft/world/border/WorldBorder.contains:(Lnet/minecraft/util/BlockPos;)Z
        //   181: ifne            200
        //   184: iload           15
        //   186: ifeq            200
        //   189: getstatic       net/minecraft/init/Blocks.stone:Lnet/minecraft/block/Block;
        //   192: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //   195: astore          16
        //   197: goto            208
        //   200: aload_0        
        //   201: aload           13
        //   203: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   206: astore          16
        //   208: aload           16
        //   210: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   215: aload_0        
        //   216: aload           13
        //   218: aload           16
        //   220: aload_2        
        //   221: aload_3        
        //   222: aload_1        
        //   223: invokevirtual   net/minecraft/block/Block.addCollisionBoxesToList:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;)V
        //   226: iinc            12, 1
        //   229: iload           12
        //   231: iload           7
        //   233: if_icmplt       107
        //   236: iinc            11, 1
        //   239: iload           11
        //   241: iload           9
        //   243: if_icmplt       78
        //   246: iinc            10, 1
        //   249: iload           10
        //   251: iload           5
        //   253: if_icmplt       71
        //   256: ldc2_w          0.25
        //   259: dstore          10
        //   261: aload_0        
        //   262: aload_1        
        //   263: aload_2        
        //   264: dload           10
        //   266: dload           10
        //   268: dload           10
        //   270: invokevirtual   net/minecraft/util/AxisAlignedBB.expand:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //   273: invokevirtual   net/minecraft/world/World.getEntitiesWithinAABBExcludingEntity:(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;)Ljava/util/List;
        //   276: astore          12
        //   278: goto            377
        //   281: aload_1        
        //   282: getfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   285: aload           12
        //   287: if_acmpeq       374
        //   290: aload_1        
        //   291: getfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   294: aload           12
        //   296: if_acmpeq       374
        //   299: aload           12
        //   301: iconst_0       
        //   302: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   307: checkcast       Lnet/minecraft/entity/Entity;
        //   310: invokevirtual   net/minecraft/entity/Entity.getBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   313: astore          14
        //   315: aload           14
        //   317: ifnull          336
        //   320: aload           14
        //   322: aload_2        
        //   323: invokevirtual   net/minecraft/util/AxisAlignedBB.intersectsWith:(Lnet/minecraft/util/AxisAlignedBB;)Z
        //   326: ifeq            336
        //   329: aload_3        
        //   330: aload           14
        //   332: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   335: pop            
        //   336: aload_1        
        //   337: aload           12
        //   339: iconst_0       
        //   340: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   345: checkcast       Lnet/minecraft/entity/Entity;
        //   348: invokevirtual   net/minecraft/entity/Entity.getCollisionBox:(Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/AxisAlignedBB;
        //   351: astore          14
        //   353: aload           14
        //   355: ifnull          374
        //   358: aload           14
        //   360: aload_2        
        //   361: invokevirtual   net/minecraft/util/AxisAlignedBB.intersectsWith:(Lnet/minecraft/util/AxisAlignedBB;)Z
        //   364: ifeq            374
        //   367: aload_3        
        //   368: aload           14
        //   370: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   373: pop            
        //   374: iinc            13, 1
        //   377: iconst_0       
        //   378: aload           12
        //   380: invokeinterface java/util/List.size:()I
        //   385: if_icmplt       281
        //   388: aload_3        
        //   389: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public boolean isInsideBorder(final WorldBorder worldBorder, final Entity entity) {
        final double minX = worldBorder.minX();
        final double minZ = worldBorder.minZ();
        final double maxX = worldBorder.maxX();
        final double maxZ = worldBorder.maxZ();
        double n;
        double n2;
        double n3;
        double n4;
        if (entity.isOutsideBorder()) {
            n = minX + 1.0;
            n2 = minZ + 1.0;
            n3 = maxX - 1.0;
            n4 = maxZ - 1.0;
        }
        else {
            n = minX - 1.0;
            n2 = minZ - 1.0;
            n3 = maxX + 1.0;
            n4 = maxZ + 1.0;
        }
        return entity.posX > n && entity.posX < n3 && entity.posZ > n2 && entity.posZ < n4;
    }
    
    public boolean isFlammableWithin(final AxisAlignedBB axisAlignedBB) {
        final ArrayList arrayList = Lists.newArrayList();
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
        for (int i = floor_double; i < floor_double2; ++i) {
            for (int j = floor_double5; j < floor_double6; ++j) {
                if (this.isBlockLoaded(new BlockPos(i, 64, j))) {
                    for (int k = floor_double3 - 1; k < floor_double4; ++k) {
                        final BlockPos blockPos = new BlockPos(i, k, j);
                        IBlockState blockState;
                        if (i >= -30000000 && i < 30000000 && j >= -30000000 && j < 30000000) {
                            blockState = this.getBlockState(blockPos);
                        }
                        else {
                            blockState = Blocks.bedrock.getDefaultState();
                        }
                        blockState.getBlock().addCollisionBoxesToList(this, blockPos, blockState, axisAlignedBB, arrayList, null);
                    }
                }
            }
        }
        return false;
    }
    
    public List func_147461_a(final AxisAlignedBB axisAlignedBB) {
        final ArrayList arrayList = Lists.newArrayList();
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
        for (int i = floor_double; i < floor_double2; ++i) {
            for (int j = floor_double5; j < floor_double6; ++j) {
                if (this.isBlockLoaded(new BlockPos(i, 64, j))) {
                    for (int k = floor_double3 - 1; k < floor_double4; ++k) {
                        final BlockPos blockPos = new BlockPos(i, k, j);
                        IBlockState blockState;
                        if (i >= -30000000 && i < 30000000 && j >= -30000000 && j < 30000000) {
                            blockState = this.getBlockState(blockPos);
                        }
                        else {
                            blockState = Blocks.bedrock.getDefaultState();
                        }
                        blockState.getBlock().addCollisionBoxesToList(this, blockPos, blockState, axisAlignedBB, arrayList, null);
                    }
                }
            }
        }
        return arrayList;
    }
    
    public int calculateSkylightSubtracted(final float n) {
        return (int)((1.0f - (float)((float)((1.0f - MathHelper.clamp_float(1.0f - (MathHelper.cos(this.getCelestialAngle(n) * 3.1415927f * 2.0f) * 2.0f + 0.5f), 0.0f, 1.0f)) * (1.0 - this.getRainStrength(n) * 5.0f / 16.0)) * (1.0 - this.getWeightedThunderStrength(n) * 5.0f / 16.0))) * 11.0f);
    }
    
    public float getSunBrightness(final float n) {
        return (float)((float)((1.0f - MathHelper.clamp_float(1.0f - (MathHelper.cos(this.getCelestialAngle(n) * 3.1415927f * 2.0f) * 2.0f + 0.2f), 0.0f, 1.0f)) * (1.0 - this.getRainStrength(n) * 5.0f / 16.0)) * (1.0 - this.getWeightedThunderStrength(n) * 5.0f / 16.0)) * 0.8f + 0.2f;
    }
    
    public Vec3 getSkyColor(final Entity entity, final float n) {
        final float clamp_float = MathHelper.clamp_float(MathHelper.cos(this.getCelestialAngle(n) * 3.1415927f * 2.0f) * 2.0f + 0.5f, 0.0f, 1.0f);
        final BlockPos blockPos = new BlockPos(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ));
        final BiomeGenBase biomeGenForCoords = this.getBiomeGenForCoords(blockPos);
        final int skyColorByTemp = biomeGenForCoords.getSkyColorByTemp(biomeGenForCoords.func_180626_a(blockPos));
        final float n2 = (skyColorByTemp >> 16 & 0xFF) / 255.0f;
        final float n3 = (skyColorByTemp >> 8 & 0xFF) / 255.0f;
        final float n4 = (skyColorByTemp & 0xFF) / 255.0f;
        float n5 = n2 * clamp_float;
        float n6 = n3 * clamp_float;
        float n7 = n4 * clamp_float;
        final float rainStrength = this.getRainStrength(n);
        if (rainStrength > 0.0f) {
            final float n8 = (n5 * 0.3f + n6 * 0.59f + n7 * 0.11f) * 0.6f;
            final float n9 = 1.0f - rainStrength * 0.75f;
            n5 = n5 * n9 + n8 * (1.0f - n9);
            n6 = n6 * n9 + n8 * (1.0f - n9);
            n7 = n7 * n9 + n8 * (1.0f - n9);
        }
        final float weightedThunderStrength = this.getWeightedThunderStrength(n);
        if (weightedThunderStrength > 0.0f) {
            final float n10 = (n5 * 0.3f + n6 * 0.59f + n7 * 0.11f) * 0.2f;
            final float n11 = 1.0f - weightedThunderStrength * 0.75f;
            n5 = n5 * n11 + n10 * (1.0f - n11);
            n6 = n6 * n11 + n10 * (1.0f - n11);
            n7 = n7 * n11 + n10 * (1.0f - n11);
        }
        if (this.lastLightningBolt > 0) {
            float n12 = this.lastLightningBolt - n;
            if (n12 > 1.0f) {
                n12 = 1.0f;
            }
            final float n13 = n12 * 0.45f;
            n5 = n5 * (1.0f - n13) + 0.8f * n13;
            n6 = n6 * (1.0f - n13) + 0.8f * n13;
            n7 = n7 * (1.0f - n13) + 1.0f * n13;
        }
        return new Vec3(n5, n6, n7);
    }
    
    public float getCelestialAngle(final float n) {
        return this.provider.calculateCelestialAngle(this.worldInfo.getWorldTime(), n);
    }
    
    public int getMoonPhase() {
        return this.provider.getMoonPhase(this.worldInfo.getWorldTime());
    }
    
    public float getCurrentMoonPhaseFactor() {
        return WorldProvider.moonPhaseFactors[this.provider.getMoonPhase(this.worldInfo.getWorldTime())];
    }
    
    public float getCelestialAngleRadians(final float n) {
        return this.getCelestialAngle(n) * 3.1415927f * 2.0f;
    }
    
    public Vec3 getCloudColour(final float n) {
        final float clamp_float = MathHelper.clamp_float(MathHelper.cos(this.getCelestialAngle(n) * 3.1415927f * 2.0f) * 2.0f + 0.5f, 0.0f, 1.0f);
        float n2 = (this.cloudColour >> 16 & 0xFFL) / 255.0f;
        float n3 = (this.cloudColour >> 8 & 0xFFL) / 255.0f;
        float n4 = (this.cloudColour & 0xFFL) / 255.0f;
        final float rainStrength = this.getRainStrength(n);
        if (rainStrength > 0.0f) {
            final float n5 = (n2 * 0.3f + n3 * 0.59f + n4 * 0.11f) * 0.6f;
            final float n6 = 1.0f - rainStrength * 0.95f;
            n2 = n2 * n6 + n5 * (1.0f - n6);
            n3 = n3 * n6 + n5 * (1.0f - n6);
            n4 = n4 * n6 + n5 * (1.0f - n6);
        }
        float n7 = n2 * (clamp_float * 0.9f + 0.1f);
        float n8 = n3 * (clamp_float * 0.9f + 0.1f);
        float n9 = n4 * (clamp_float * 0.85f + 0.15f);
        final float weightedThunderStrength = this.getWeightedThunderStrength(n);
        if (weightedThunderStrength > 0.0f) {
            final float n10 = (n7 * 0.3f + n8 * 0.59f + n9 * 0.11f) * 0.2f;
            final float n11 = 1.0f - weightedThunderStrength * 0.95f;
            n7 = n7 * n11 + n10 * (1.0f - n11);
            n8 = n8 * n11 + n10 * (1.0f - n11);
            n9 = n9 * n11 + n10 * (1.0f - n11);
        }
        return new Vec3(n7, n8, n9);
    }
    
    public Vec3 getFogColor(final float n) {
        return this.provider.getFogColor(this.getCelestialAngle(n), n);
    }
    
    public BlockPos func_175725_q(final BlockPos blockPos) {
        return this.getChunkFromBlockCoords(blockPos).func_177440_h(blockPos);
    }
    
    public BlockPos func_175672_r(final BlockPos blockPos) {
        final Chunk chunkFromBlockCoords = this.getChunkFromBlockCoords(blockPos);
        BlockPos blockPos2;
        BlockPos offsetDown;
        for (blockPos2 = new BlockPos(blockPos.getX(), chunkFromBlockCoords.getTopFilledSegment() + 16, blockPos.getZ()); blockPos2.getY() >= 0; blockPos2 = offsetDown) {
            offsetDown = blockPos2.offsetDown();
            final Material material = chunkFromBlockCoords.getBlock(offsetDown).getMaterial();
            if (material.blocksMovement() && material != Material.leaves) {
                break;
            }
        }
        return blockPos2;
    }
    
    public float getStarBrightness(final float n) {
        final float clamp_float = MathHelper.clamp_float(1.0f - (MathHelper.cos(this.getCelestialAngle(n) * 3.1415927f * 2.0f) * 2.0f + 0.25f), 0.0f, 1.0f);
        return clamp_float * clamp_float * 0.5f;
    }
    
    public void scheduleUpdate(final BlockPos blockPos, final Block block, final int n) {
    }
    
    public void func_175654_a(final BlockPos blockPos, final Block block, final int n, final int n2) {
    }
    
    public void func_180497_b(final BlockPos blockPos, final Block block, final int n, final int n2) {
    }
    
    public void updateEntities() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //     4: ldc_w           "entities"
        //     7: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //    10: aload_0        
        //    11: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //    14: ldc_w           "global"
        //    17: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //    20: goto            131
        //    23: aload_0        
        //    24: getfield        net/minecraft/world/World.weatherEffects:Ljava/util/List;
        //    27: iconst_0       
        //    28: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //    33: checkcast       Lnet/minecraft/entity/Entity;
        //    36: astore_2       
        //    37: aload_2        
        //    38: dup            
        //    39: getfield        net/minecraft/entity/Entity.ticksExisted:I
        //    42: iconst_1       
        //    43: iadd           
        //    44: putfield        net/minecraft/entity/Entity.ticksExisted:I
        //    47: aload_2        
        //    48: invokevirtual   net/minecraft/entity/Entity.onUpdate:()V
        //    51: goto            107
        //    54: astore          5
        //    56: aload           5
        //    58: ldc_w           "Ticking entity"
        //    61: invokestatic    net/minecraft/crash/CrashReport.makeCrashReport:(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/crash/CrashReport;
        //    64: astore_3       
        //    65: aload_3        
        //    66: ldc_w           "Entity being ticked"
        //    69: invokevirtual   net/minecraft/crash/CrashReport.makeCategory:(Ljava/lang/String;)Lnet/minecraft/crash/CrashReportCategory;
        //    72: astore          4
        //    74: aload_2        
        //    75: ifnonnull       92
        //    78: aload           4
        //    80: ldc_w           "Entity"
        //    83: ldc_w           "~~NULL~~"
        //    86: invokevirtual   net/minecraft/crash/CrashReportCategory.addCrashSection:(Ljava/lang/String;Ljava/lang/Object;)V
        //    89: goto            98
        //    92: aload_2        
        //    93: aload           4
        //    95: invokevirtual   net/minecraft/entity/Entity.addEntityCrashInfo:(Lnet/minecraft/crash/CrashReportCategory;)V
        //    98: new             Lnet/minecraft/util/ReportedException;
        //   101: dup            
        //   102: aload_3        
        //   103: invokespecial   net/minecraft/util/ReportedException.<init>:(Lnet/minecraft/crash/CrashReport;)V
        //   106: athrow         
        //   107: aload_2        
        //   108: getfield        net/minecraft/entity/Entity.isDead:Z
        //   111: ifeq            128
        //   114: aload_0        
        //   115: getfield        net/minecraft/world/World.weatherEffects:Ljava/util/List;
        //   118: iconst_0       
        //   119: iinc            1, -1
        //   122: invokeinterface java/util/List.remove:(I)Ljava/lang/Object;
        //   127: pop            
        //   128: iinc            1, 1
        //   131: iconst_0       
        //   132: aload_0        
        //   133: getfield        net/minecraft/world/World.weatherEffects:Ljava/util/List;
        //   136: invokeinterface java/util/List.size:()I
        //   141: if_icmplt       23
        //   144: aload_0        
        //   145: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   148: ldc_w           "remove"
        //   151: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   154: aload_0        
        //   155: getfield        net/minecraft/world/World.loadedEntityList:Ljava/util/List;
        //   158: aload_0        
        //   159: getfield        net/minecraft/world/World.unloadedEntityList:Ljava/util/List;
        //   162: invokeinterface java/util/List.removeAll:(Ljava/util/Collection;)Z
        //   167: pop            
        //   168: goto            224
        //   171: aload_0        
        //   172: getfield        net/minecraft/world/World.unloadedEntityList:Ljava/util/List;
        //   175: iconst_0       
        //   176: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   181: checkcast       Lnet/minecraft/entity/Entity;
        //   184: astore_2       
        //   185: aload_2        
        //   186: getfield        net/minecraft/entity/Entity.chunkCoordX:I
        //   189: istore          5
        //   191: aload_2        
        //   192: getfield        net/minecraft/entity/Entity.chunkCoordZ:I
        //   195: istore          6
        //   197: aload_2        
        //   198: getfield        net/minecraft/entity/Entity.addedToChunk:Z
        //   201: ifeq            221
        //   204: aload_0        
        //   205: iload           5
        //   207: iload           6
        //   209: aload_0        
        //   210: iload           5
        //   212: iload           6
        //   214: invokevirtual   net/minecraft/world/World.getChunkFromChunkCoords:(II)Lnet/minecraft/world/chunk/Chunk;
        //   217: aload_2        
        //   218: invokevirtual   net/minecraft/world/chunk/Chunk.removeEntity:(Lnet/minecraft/entity/Entity;)V
        //   221: iinc            1, 1
        //   224: iconst_0       
        //   225: aload_0        
        //   226: getfield        net/minecraft/world/World.unloadedEntityList:Ljava/util/List;
        //   229: invokeinterface java/util/List.size:()I
        //   234: if_icmplt       171
        //   237: goto            260
        //   240: aload_0        
        //   241: aload_0        
        //   242: getfield        net/minecraft/world/World.unloadedEntityList:Ljava/util/List;
        //   245: iconst_0       
        //   246: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   251: checkcast       Lnet/minecraft/entity/Entity;
        //   254: invokevirtual   net/minecraft/world/World.onEntityRemoved:(Lnet/minecraft/entity/Entity;)V
        //   257: iinc            1, 1
        //   260: iconst_0       
        //   261: aload_0        
        //   262: getfield        net/minecraft/world/World.unloadedEntityList:Ljava/util/List;
        //   265: invokeinterface java/util/List.size:()I
        //   270: if_icmplt       240
        //   273: aload_0        
        //   274: getfield        net/minecraft/world/World.unloadedEntityList:Ljava/util/List;
        //   277: invokeinterface java/util/List.clear:()V
        //   282: aload_0        
        //   283: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   286: ldc_w           "regular"
        //   289: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   292: goto            502
        //   295: aload_0        
        //   296: getfield        net/minecraft/world/World.loadedEntityList:Ljava/util/List;
        //   299: iconst_0       
        //   300: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   305: checkcast       Lnet/minecraft/entity/Entity;
        //   308: astore_2       
        //   309: aload_2        
        //   310: getfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   313: ifnull          353
        //   316: aload_2        
        //   317: getfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   320: getfield        net/minecraft/entity/Entity.isDead:Z
        //   323: ifne            340
        //   326: aload_2        
        //   327: getfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   330: getfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   333: aload_2        
        //   334: if_acmpne       340
        //   337: goto            499
        //   340: aload_2        
        //   341: getfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   344: aconst_null    
        //   345: putfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   348: aload_2        
        //   349: aconst_null    
        //   350: putfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   353: aload_0        
        //   354: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   357: ldc_w           "tick"
        //   360: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   363: aload_2        
        //   364: getfield        net/minecraft/entity/Entity.isDead:Z
        //   367: ifne            413
        //   370: aload_0        
        //   371: aload_2        
        //   372: invokevirtual   net/minecraft/world/World.updateEntity:(Lnet/minecraft/entity/Entity;)V
        //   375: goto            413
        //   378: astore          7
        //   380: aload           7
        //   382: ldc_w           "Ticking entity"
        //   385: invokestatic    net/minecraft/crash/CrashReport.makeCrashReport:(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/crash/CrashReport;
        //   388: astore_3       
        //   389: aload_3        
        //   390: ldc_w           "Entity being ticked"
        //   393: invokevirtual   net/minecraft/crash/CrashReport.makeCategory:(Ljava/lang/String;)Lnet/minecraft/crash/CrashReportCategory;
        //   396: astore          4
        //   398: aload_2        
        //   399: aload           4
        //   401: invokevirtual   net/minecraft/entity/Entity.addEntityCrashInfo:(Lnet/minecraft/crash/CrashReportCategory;)V
        //   404: new             Lnet/minecraft/util/ReportedException;
        //   407: dup            
        //   408: aload_3        
        //   409: invokespecial   net/minecraft/util/ReportedException.<init>:(Lnet/minecraft/crash/CrashReport;)V
        //   412: athrow         
        //   413: aload_0        
        //   414: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   417: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   420: aload_0        
        //   421: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   424: ldc_w           "remove"
        //   427: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   430: aload_2        
        //   431: getfield        net/minecraft/entity/Entity.isDead:Z
        //   434: ifeq            492
        //   437: aload_2        
        //   438: getfield        net/minecraft/entity/Entity.chunkCoordX:I
        //   441: istore          5
        //   443: aload_2        
        //   444: getfield        net/minecraft/entity/Entity.chunkCoordZ:I
        //   447: istore          6
        //   449: aload_2        
        //   450: getfield        net/minecraft/entity/Entity.addedToChunk:Z
        //   453: ifeq            473
        //   456: aload_0        
        //   457: iload           5
        //   459: iload           6
        //   461: aload_0        
        //   462: iload           5
        //   464: iload           6
        //   466: invokevirtual   net/minecraft/world/World.getChunkFromChunkCoords:(II)Lnet/minecraft/world/chunk/Chunk;
        //   469: aload_2        
        //   470: invokevirtual   net/minecraft/world/chunk/Chunk.removeEntity:(Lnet/minecraft/entity/Entity;)V
        //   473: aload_0        
        //   474: getfield        net/minecraft/world/World.loadedEntityList:Ljava/util/List;
        //   477: iconst_0       
        //   478: iinc            1, -1
        //   481: invokeinterface java/util/List.remove:(I)Ljava/lang/Object;
        //   486: pop            
        //   487: aload_0        
        //   488: aload_2        
        //   489: invokevirtual   net/minecraft/world/World.onEntityRemoved:(Lnet/minecraft/entity/Entity;)V
        //   492: aload_0        
        //   493: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   496: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   499: iinc            1, 1
        //   502: iconst_0       
        //   503: aload_0        
        //   504: getfield        net/minecraft/world/World.loadedEntityList:Ljava/util/List;
        //   507: invokeinterface java/util/List.size:()I
        //   512: if_icmplt       295
        //   515: aload_0        
        //   516: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   519: ldc_w           "blockEntities"
        //   522: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   525: aload_0        
        //   526: iconst_1       
        //   527: putfield        net/minecraft/world/World.processingLoadedTiles:Z
        //   530: aload_0        
        //   531: getfield        net/minecraft/world/World.tickableTileEntities:Ljava/util/List;
        //   534: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   539: astore          7
        //   541: goto            708
        //   544: aload           7
        //   546: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   551: checkcast       Lnet/minecraft/tileentity/TileEntity;
        //   554: astore          8
        //   556: aload           8
        //   558: invokevirtual   net/minecraft/tileentity/TileEntity.isInvalid:()Z
        //   561: ifne            652
        //   564: aload           8
        //   566: invokevirtual   net/minecraft/tileentity/TileEntity.hasWorldObj:()Z
        //   569: ifeq            652
        //   572: aload           8
        //   574: invokevirtual   net/minecraft/tileentity/TileEntity.getPos:()Lnet/minecraft/util/BlockPos;
        //   577: astore          9
        //   579: aload_0        
        //   580: aload           9
        //   582: invokevirtual   net/minecraft/world/World.isBlockLoaded:(Lnet/minecraft/util/BlockPos;)Z
        //   585: ifeq            652
        //   588: aload_0        
        //   589: getfield        net/minecraft/world/World.worldBorder:Lnet/minecraft/world/border/WorldBorder;
        //   592: aload           9
        //   594: invokevirtual   net/minecraft/world/border/WorldBorder.contains:(Lnet/minecraft/util/BlockPos;)Z
        //   597: ifeq            652
        //   600: aload           8
        //   602: checkcast       Lnet/minecraft/server/gui/IUpdatePlayerListBox;
        //   605: invokeinterface net/minecraft/server/gui/IUpdatePlayerListBox.update:()V
        //   610: goto            652
        //   613: astore          10
        //   615: aload           10
        //   617: ldc_w           "Ticking block entity"
        //   620: invokestatic    net/minecraft/crash/CrashReport.makeCrashReport:(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/crash/CrashReport;
        //   623: astore          11
        //   625: aload           11
        //   627: ldc_w           "Block entity being ticked"
        //   630: invokevirtual   net/minecraft/crash/CrashReport.makeCategory:(Ljava/lang/String;)Lnet/minecraft/crash/CrashReportCategory;
        //   633: astore          12
        //   635: aload           8
        //   637: aload           12
        //   639: invokevirtual   net/minecraft/tileentity/TileEntity.addInfoToCrashReport:(Lnet/minecraft/crash/CrashReportCategory;)V
        //   642: new             Lnet/minecraft/util/ReportedException;
        //   645: dup            
        //   646: aload           11
        //   648: invokespecial   net/minecraft/util/ReportedException.<init>:(Lnet/minecraft/crash/CrashReport;)V
        //   651: athrow         
        //   652: aload           8
        //   654: invokevirtual   net/minecraft/tileentity/TileEntity.isInvalid:()Z
        //   657: ifeq            708
        //   660: aload           7
        //   662: invokeinterface java/util/Iterator.remove:()V
        //   667: aload_0        
        //   668: getfield        net/minecraft/world/World.loadedTileEntityList:Ljava/util/List;
        //   671: aload           8
        //   673: invokeinterface java/util/List.remove:(Ljava/lang/Object;)Z
        //   678: pop            
        //   679: aload_0        
        //   680: aload           8
        //   682: invokevirtual   net/minecraft/tileentity/TileEntity.getPos:()Lnet/minecraft/util/BlockPos;
        //   685: invokevirtual   net/minecraft/world/World.isBlockLoaded:(Lnet/minecraft/util/BlockPos;)Z
        //   688: ifeq            708
        //   691: aload_0        
        //   692: aload           8
        //   694: invokevirtual   net/minecraft/tileentity/TileEntity.getPos:()Lnet/minecraft/util/BlockPos;
        //   697: invokevirtual   net/minecraft/world/World.getChunkFromBlockCoords:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/world/chunk/Chunk;
        //   700: aload           8
        //   702: invokevirtual   net/minecraft/tileentity/TileEntity.getPos:()Lnet/minecraft/util/BlockPos;
        //   705: invokevirtual   net/minecraft/world/chunk/Chunk.removeTileEntity:(Lnet/minecraft/util/BlockPos;)V
        //   708: aload           7
        //   710: invokeinterface java/util/Iterator.hasNext:()Z
        //   715: ifne            544
        //   718: aload_0        
        //   719: iconst_0       
        //   720: putfield        net/minecraft/world/World.processingLoadedTiles:Z
        //   723: aload_0        
        //   724: getfield        net/minecraft/world/World.tileEntitiesToBeRemoved:Ljava/util/List;
        //   727: invokeinterface java/util/List.isEmpty:()Z
        //   732: ifne            772
        //   735: aload_0        
        //   736: getfield        net/minecraft/world/World.tickableTileEntities:Ljava/util/List;
        //   739: aload_0        
        //   740: getfield        net/minecraft/world/World.tileEntitiesToBeRemoved:Ljava/util/List;
        //   743: invokeinterface java/util/List.removeAll:(Ljava/util/Collection;)Z
        //   748: pop            
        //   749: aload_0        
        //   750: getfield        net/minecraft/world/World.loadedTileEntityList:Ljava/util/List;
        //   753: aload_0        
        //   754: getfield        net/minecraft/world/World.tileEntitiesToBeRemoved:Ljava/util/List;
        //   757: invokeinterface java/util/List.removeAll:(Ljava/util/Collection;)Z
        //   762: pop            
        //   763: aload_0        
        //   764: getfield        net/minecraft/world/World.tileEntitiesToBeRemoved:Ljava/util/List;
        //   767: invokeinterface java/util/List.clear:()V
        //   772: aload_0        
        //   773: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   776: ldc_w           "pendingBlockEntities"
        //   779: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   782: aload_0        
        //   783: getfield        net/minecraft/world/World.addedTileEntityList:Ljava/util/List;
        //   786: invokeinterface java/util/List.isEmpty:()Z
        //   791: ifne            906
        //   794: goto            884
        //   797: aload_0        
        //   798: getfield        net/minecraft/world/World.addedTileEntityList:Ljava/util/List;
        //   801: iconst_0       
        //   802: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   807: checkcast       Lnet/minecraft/tileentity/TileEntity;
        //   810: astore          9
        //   812: aload           9
        //   814: invokevirtual   net/minecraft/tileentity/TileEntity.isInvalid:()Z
        //   817: ifne            881
        //   820: aload_0        
        //   821: getfield        net/minecraft/world/World.loadedTileEntityList:Ljava/util/List;
        //   824: aload           9
        //   826: invokeinterface java/util/List.contains:(Ljava/lang/Object;)Z
        //   831: ifne            841
        //   834: aload_0        
        //   835: aload           9
        //   837: invokevirtual   net/minecraft/world/World.addTileEntity:(Lnet/minecraft/tileentity/TileEntity;)Z
        //   840: pop            
        //   841: aload_0        
        //   842: aload           9
        //   844: invokevirtual   net/minecraft/tileentity/TileEntity.getPos:()Lnet/minecraft/util/BlockPos;
        //   847: invokevirtual   net/minecraft/world/World.isBlockLoaded:(Lnet/minecraft/util/BlockPos;)Z
        //   850: ifeq            872
        //   853: aload_0        
        //   854: aload           9
        //   856: invokevirtual   net/minecraft/tileentity/TileEntity.getPos:()Lnet/minecraft/util/BlockPos;
        //   859: invokevirtual   net/minecraft/world/World.getChunkFromBlockCoords:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/world/chunk/Chunk;
        //   862: aload           9
        //   864: invokevirtual   net/minecraft/tileentity/TileEntity.getPos:()Lnet/minecraft/util/BlockPos;
        //   867: aload           9
        //   869: invokevirtual   net/minecraft/world/chunk/Chunk.addTileEntity:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/tileentity/TileEntity;)V
        //   872: aload_0        
        //   873: aload           9
        //   875: invokevirtual   net/minecraft/tileentity/TileEntity.getPos:()Lnet/minecraft/util/BlockPos;
        //   878: invokevirtual   net/minecraft/world/World.markBlockForUpdate:(Lnet/minecraft/util/BlockPos;)V
        //   881: iinc            8, 1
        //   884: iconst_0       
        //   885: aload_0        
        //   886: getfield        net/minecraft/world/World.addedTileEntityList:Ljava/util/List;
        //   889: invokeinterface java/util/List.size:()I
        //   894: if_icmplt       797
        //   897: aload_0        
        //   898: getfield        net/minecraft/world/World.addedTileEntityList:Ljava/util/List;
        //   901: invokeinterface java/util/List.clear:()V
        //   906: aload_0        
        //   907: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   910: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   913: aload_0        
        //   914: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   917: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   920: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0221 (coming from #0218).
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
    
    public boolean addTileEntity(final TileEntity tileEntity) {
        final boolean add = this.loadedTileEntityList.add(tileEntity);
        if (add && tileEntity instanceof IUpdatePlayerListBox) {
            this.tickableTileEntities.add(tileEntity);
        }
        return add;
    }
    
    public void addTileEntities(final Collection collection) {
        if (this.processingLoadedTiles) {
            this.addedTileEntityList.addAll(collection);
        }
        else {
            for (final TileEntity tileEntity : collection) {
                this.loadedTileEntityList.add(tileEntity);
                if (tileEntity instanceof IUpdatePlayerListBox) {
                    this.tickableTileEntities.add(tileEntity);
                }
            }
        }
    }
    
    public void updateEntity(final Entity entity) {
        this.updateEntityWithOptionalForce(entity, true);
    }
    
    public void updateEntityWithOptionalForce(final Entity p0, final boolean p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/Entity.posX:D
        //     4: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //     7: istore_3       
        //     8: aload_1        
        //     9: getfield        net/minecraft/entity/Entity.posZ:D
        //    12: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    15: istore          4
        //    17: iload_2        
        //    18: ifeq            42
        //    21: aload_0        
        //    22: iload_3        
        //    23: bipush          32
        //    25: isub           
        //    26: iconst_0       
        //    27: iload           4
        //    29: bipush          32
        //    31: isub           
        //    32: iload_3        
        //    33: bipush          32
        //    35: iadd           
        //    36: iconst_0       
        //    37: iload           4
        //    39: bipush          32
        //    41: iadd           
        //    42: aload_1        
        //    43: aload_1        
        //    44: getfield        net/minecraft/entity/Entity.posX:D
        //    47: putfield        net/minecraft/entity/Entity.lastTickPosX:D
        //    50: aload_1        
        //    51: aload_1        
        //    52: getfield        net/minecraft/entity/Entity.posY:D
        //    55: putfield        net/minecraft/entity/Entity.lastTickPosY:D
        //    58: aload_1        
        //    59: aload_1        
        //    60: getfield        net/minecraft/entity/Entity.posZ:D
        //    63: putfield        net/minecraft/entity/Entity.lastTickPosZ:D
        //    66: aload_1        
        //    67: aload_1        
        //    68: getfield        net/minecraft/entity/Entity.rotationYaw:F
        //    71: putfield        net/minecraft/entity/Entity.prevRotationYaw:F
        //    74: aload_1        
        //    75: aload_1        
        //    76: getfield        net/minecraft/entity/Entity.rotationPitch:F
        //    79: putfield        net/minecraft/entity/Entity.prevRotationPitch:F
        //    82: iload_2        
        //    83: ifeq            121
        //    86: aload_1        
        //    87: getfield        net/minecraft/entity/Entity.addedToChunk:Z
        //    90: ifeq            121
        //    93: aload_1        
        //    94: dup            
        //    95: getfield        net/minecraft/entity/Entity.ticksExisted:I
        //    98: iconst_1       
        //    99: iadd           
        //   100: putfield        net/minecraft/entity/Entity.ticksExisted:I
        //   103: aload_1        
        //   104: getfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   107: ifnull          117
        //   110: aload_1        
        //   111: invokevirtual   net/minecraft/entity/Entity.updateRidden:()V
        //   114: goto            121
        //   117: aload_1        
        //   118: invokevirtual   net/minecraft/entity/Entity.onUpdate:()V
        //   121: aload_0        
        //   122: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   125: ldc_w           "chunkCheck"
        //   128: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   131: aload_1        
        //   132: getfield        net/minecraft/entity/Entity.posX:D
        //   135: invokestatic    java/lang/Double.isNaN:(D)Z
        //   138: ifne            151
        //   141: aload_1        
        //   142: getfield        net/minecraft/entity/Entity.posX:D
        //   145: invokestatic    java/lang/Double.isInfinite:(D)Z
        //   148: ifeq            159
        //   151: aload_1        
        //   152: aload_1        
        //   153: getfield        net/minecraft/entity/Entity.lastTickPosX:D
        //   156: putfield        net/minecraft/entity/Entity.posX:D
        //   159: aload_1        
        //   160: getfield        net/minecraft/entity/Entity.posY:D
        //   163: invokestatic    java/lang/Double.isNaN:(D)Z
        //   166: ifne            179
        //   169: aload_1        
        //   170: getfield        net/minecraft/entity/Entity.posY:D
        //   173: invokestatic    java/lang/Double.isInfinite:(D)Z
        //   176: ifeq            187
        //   179: aload_1        
        //   180: aload_1        
        //   181: getfield        net/minecraft/entity/Entity.lastTickPosY:D
        //   184: putfield        net/minecraft/entity/Entity.posY:D
        //   187: aload_1        
        //   188: getfield        net/minecraft/entity/Entity.posZ:D
        //   191: invokestatic    java/lang/Double.isNaN:(D)Z
        //   194: ifne            207
        //   197: aload_1        
        //   198: getfield        net/minecraft/entity/Entity.posZ:D
        //   201: invokestatic    java/lang/Double.isInfinite:(D)Z
        //   204: ifeq            215
        //   207: aload_1        
        //   208: aload_1        
        //   209: getfield        net/minecraft/entity/Entity.lastTickPosZ:D
        //   212: putfield        net/minecraft/entity/Entity.posZ:D
        //   215: aload_1        
        //   216: getfield        net/minecraft/entity/Entity.rotationPitch:F
        //   219: f2d            
        //   220: invokestatic    java/lang/Double.isNaN:(D)Z
        //   223: ifne            237
        //   226: aload_1        
        //   227: getfield        net/minecraft/entity/Entity.rotationPitch:F
        //   230: f2d            
        //   231: invokestatic    java/lang/Double.isInfinite:(D)Z
        //   234: ifeq            245
        //   237: aload_1        
        //   238: aload_1        
        //   239: getfield        net/minecraft/entity/Entity.prevRotationPitch:F
        //   242: putfield        net/minecraft/entity/Entity.rotationPitch:F
        //   245: aload_1        
        //   246: getfield        net/minecraft/entity/Entity.rotationYaw:F
        //   249: f2d            
        //   250: invokestatic    java/lang/Double.isNaN:(D)Z
        //   253: ifne            267
        //   256: aload_1        
        //   257: getfield        net/minecraft/entity/Entity.rotationYaw:F
        //   260: f2d            
        //   261: invokestatic    java/lang/Double.isInfinite:(D)Z
        //   264: ifeq            275
        //   267: aload_1        
        //   268: aload_1        
        //   269: getfield        net/minecraft/entity/Entity.prevRotationYaw:F
        //   272: putfield        net/minecraft/entity/Entity.rotationYaw:F
        //   275: aload_1        
        //   276: getfield        net/minecraft/entity/Entity.posX:D
        //   279: ldc2_w          16.0
        //   282: ddiv           
        //   283: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   286: istore          6
        //   288: aload_1        
        //   289: getfield        net/minecraft/entity/Entity.posY:D
        //   292: ldc2_w          16.0
        //   295: ddiv           
        //   296: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   299: istore          7
        //   301: aload_1        
        //   302: getfield        net/minecraft/entity/Entity.posZ:D
        //   305: ldc2_w          16.0
        //   308: ddiv           
        //   309: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   312: istore          8
        //   314: aload_1        
        //   315: getfield        net/minecraft/entity/Entity.addedToChunk:Z
        //   318: ifeq            348
        //   321: aload_1        
        //   322: getfield        net/minecraft/entity/Entity.chunkCoordX:I
        //   325: iload           6
        //   327: if_icmpne       348
        //   330: aload_1        
        //   331: getfield        net/minecraft/entity/Entity.chunkCoordY:I
        //   334: iload           7
        //   336: if_icmpne       348
        //   339: aload_1        
        //   340: getfield        net/minecraft/entity/Entity.chunkCoordZ:I
        //   343: iload           8
        //   345: if_icmpeq       414
        //   348: aload_1        
        //   349: getfield        net/minecraft/entity/Entity.addedToChunk:Z
        //   352: ifeq            384
        //   355: aload_0        
        //   356: aload_1        
        //   357: getfield        net/minecraft/entity/Entity.chunkCoordX:I
        //   360: aload_1        
        //   361: getfield        net/minecraft/entity/Entity.chunkCoordZ:I
        //   364: aload_0        
        //   365: aload_1        
        //   366: getfield        net/minecraft/entity/Entity.chunkCoordX:I
        //   369: aload_1        
        //   370: getfield        net/minecraft/entity/Entity.chunkCoordZ:I
        //   373: invokevirtual   net/minecraft/world/World.getChunkFromChunkCoords:(II)Lnet/minecraft/world/chunk/Chunk;
        //   376: aload_1        
        //   377: aload_1        
        //   378: getfield        net/minecraft/entity/Entity.chunkCoordY:I
        //   381: invokevirtual   net/minecraft/world/chunk/Chunk.removeEntityAtIndex:(Lnet/minecraft/entity/Entity;I)V
        //   384: aload_0        
        //   385: iload           6
        //   387: iload           8
        //   389: aload_1        
        //   390: iconst_1       
        //   391: putfield        net/minecraft/entity/Entity.addedToChunk:Z
        //   394: aload_0        
        //   395: iload           6
        //   397: iload           8
        //   399: invokevirtual   net/minecraft/world/World.getChunkFromChunkCoords:(II)Lnet/minecraft/world/chunk/Chunk;
        //   402: aload_1        
        //   403: invokevirtual   net/minecraft/world/chunk/Chunk.addEntity:(Lnet/minecraft/entity/Entity;)V
        //   406: goto            414
        //   409: aload_1        
        //   410: iconst_0       
        //   411: putfield        net/minecraft/entity/Entity.addedToChunk:Z
        //   414: aload_0        
        //   415: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   418: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   421: iload_2        
        //   422: ifeq            484
        //   425: aload_1        
        //   426: getfield        net/minecraft/entity/Entity.addedToChunk:Z
        //   429: ifeq            484
        //   432: aload_1        
        //   433: getfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   436: ifnull          484
        //   439: aload_1        
        //   440: getfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   443: getfield        net/minecraft/entity/Entity.isDead:Z
        //   446: ifne            471
        //   449: aload_1        
        //   450: getfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   453: getfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   456: aload_1        
        //   457: if_acmpne       471
        //   460: aload_0        
        //   461: aload_1        
        //   462: getfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   465: invokevirtual   net/minecraft/world/World.updateEntity:(Lnet/minecraft/entity/Entity;)V
        //   468: goto            484
        //   471: aload_1        
        //   472: getfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   475: aconst_null    
        //   476: putfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   479: aload_1        
        //   480: aconst_null    
        //   481: putfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   484: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0384 (coming from #0381).
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
    
    public boolean checkNoEntityCollision(final AxisAlignedBB axisAlignedBB) {
        return this.checkNoEntityCollision(axisAlignedBB, (Entity)null);
    }
    
    public boolean checkBlockCollision(final AxisAlignedBB axisAlignedBB) {
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ);
        for (int i = floor_double; i <= floor_double2; ++i) {
            for (int j = floor_double3; j <= floor_double4; ++j) {
                for (int k = floor_double5; k <= floor_double6; ++k) {
                    if (this.getBlockState(new BlockPos(i, j, k)).getBlock().getMaterial() != Material.air) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean isAnyLiquid(final AxisAlignedBB axisAlignedBB) {
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ);
        for (int i = floor_double; i <= floor_double2; ++i) {
            for (int j = floor_double3; j <= floor_double4; ++j) {
                for (int k = floor_double5; k <= floor_double6; ++k) {
                    if (this.getBlockState(new BlockPos(i, j, k)).getBlock().getMaterial().isLiquid()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean func_147470_e(final AxisAlignedBB axisAlignedBB) {
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
        for (int i = floor_double; i < floor_double2; ++i) {
            for (int j = floor_double3; j < floor_double4; ++j) {
                for (int k = floor_double5; k < floor_double6; ++k) {
                    final Block block = this.getBlockState(new BlockPos(i, j, k)).getBlock();
                    if (block == Blocks.fire || block == Blocks.flowing_lava || block == Blocks.lava) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean handleMaterialAcceleration(final AxisAlignedBB axisAlignedBB, final Material material, final Entity entity) {
        MathHelper.floor_double(axisAlignedBB.minX);
        MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
        MathHelper.floor_double(axisAlignedBB.minY);
        MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
        MathHelper.floor_double(axisAlignedBB.minZ);
        MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
        return false;
    }
    
    public boolean isMaterialInBB(final AxisAlignedBB axisAlignedBB, final Material material) {
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
        for (int i = floor_double; i < floor_double2; ++i) {
            for (int j = floor_double3; j < floor_double4; ++j) {
                for (int k = floor_double5; k < floor_double6; ++k) {
                    if (this.getBlockState(new BlockPos(i, j, k)).getBlock().getMaterial() == material) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean isAABBInMaterial(final AxisAlignedBB axisAlignedBB, final Material material) {
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
        for (int i = floor_double; i < floor_double2; ++i) {
            for (int j = floor_double3; j < floor_double4; ++j) {
                for (int k = floor_double5; k < floor_double6; ++k) {
                    final IBlockState blockState = this.getBlockState(new BlockPos(i, j, k));
                    if (blockState.getBlock().getMaterial() == material) {
                        final int intValue = (int)blockState.getValue(BlockLiquid.LEVEL);
                        double n = j + 1;
                        if (intValue < 8) {
                            n = j + 1 - intValue / 8.0;
                        }
                        if (n >= axisAlignedBB.minY) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public Explosion createExplosion(final Entity entity, final double n, final double n2, final double n3, final float n4, final boolean b) {
        return this.newExplosion(entity, n, n2, n3, n4, false, b);
    }
    
    public Explosion newExplosion(final Entity entity, final double n, final double n2, final double n3, final float n4, final boolean b, final boolean b2) {
        final Explosion explosion = new Explosion(this, entity, n, n2, n3, n4, b, b2);
        explosion.doExplosionA();
        explosion.doExplosionB(true);
        return explosion;
    }
    
    public float getBlockDensity(final Vec3 vec3, final AxisAlignedBB axisAlignedBB) {
        final double n = 1.0 / ((axisAlignedBB.maxX - axisAlignedBB.minX) * 2.0 + 1.0);
        final double n2 = 1.0 / ((axisAlignedBB.maxY - axisAlignedBB.minY) * 2.0 + 1.0);
        final double n3 = 1.0 / ((axisAlignedBB.maxZ - axisAlignedBB.minZ) * 2.0 + 1.0);
        if (n >= 0.0 && n2 >= 0.0 && n3 >= 0.0) {
            for (float n4 = 0.0f; n4 <= 1.0f; n4 += (float)n) {
                for (float n5 = 0.0f; n5 <= 1.0f; n5 += (float)n2) {
                    for (float n6 = 0.0f; n6 <= 1.0f; n6 += (float)n3) {
                        if (this.rayTraceBlocks(new Vec3(axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * n4, axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * n5, axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * n6), vec3) == null) {
                            int n7 = 0;
                            ++n7;
                        }
                        int n8 = 0;
                        ++n8;
                    }
                }
            }
            return 0 / (float)0;
        }
        return 0.0f;
    }
    
    public boolean func_175719_a(final EntityPlayer entityPlayer, BlockPos offset, final EnumFacing enumFacing) {
        offset = offset.offset(enumFacing);
        if (this.getBlockState(offset).getBlock() == Blocks.fire) {
            this.playAuxSFXAtEntity(entityPlayer, 1004, offset, 0);
            this.setBlockToAir(offset);
            return true;
        }
        return false;
    }
    
    public String getDebugLoadedEntities() {
        return "All: " + this.loadedEntityList.size();
    }
    
    public String getProviderName() {
        return this.chunkProvider.makeString();
    }
    
    @Override
    public TileEntity getTileEntity(final BlockPos blockPos) {
        if (this >= blockPos) {
            return null;
        }
        TileEntity func_177424_a = null;
        int n = 0;
        if (this.processingLoadedTiles) {
            while (0 < this.addedTileEntityList.size()) {
                final TileEntity tileEntity = this.addedTileEntityList.get(0);
                if (!tileEntity.isInvalid() && tileEntity.getPos().equals(blockPos)) {
                    func_177424_a = tileEntity;
                    break;
                }
                ++n;
            }
        }
        if (func_177424_a == null) {
            func_177424_a = this.getChunkFromBlockCoords(blockPos).func_177424_a(blockPos, Chunk.EnumCreateEntityType.IMMEDIATE);
        }
        if (func_177424_a == null) {
            while (0 < this.addedTileEntityList.size()) {
                final TileEntity tileEntity2 = this.addedTileEntityList.get(0);
                if (!tileEntity2.isInvalid() && tileEntity2.getPos().equals(blockPos)) {
                    func_177424_a = tileEntity2;
                    break;
                }
                ++n;
            }
        }
        return func_177424_a;
    }
    
    public void setTileEntity(final BlockPos pos, final TileEntity tileEntity) {
        if (tileEntity != null && !tileEntity.isInvalid()) {
            if (this.processingLoadedTiles) {
                tileEntity.setPos(pos);
                final Iterator<TileEntity> iterator = (Iterator<TileEntity>)this.addedTileEntityList.iterator();
                while (iterator.hasNext()) {
                    final TileEntity tileEntity2 = iterator.next();
                    if (tileEntity2.getPos().equals(pos)) {
                        tileEntity2.invalidate();
                        iterator.remove();
                    }
                }
                this.addedTileEntityList.add(tileEntity);
            }
            else {
                this.addTileEntity(tileEntity);
                this.getChunkFromBlockCoords(pos).addTileEntity(pos, tileEntity);
            }
        }
    }
    
    public void removeTileEntity(final BlockPos blockPos) {
        final TileEntity tileEntity = this.getTileEntity(blockPos);
        if (tileEntity != null && this.processingLoadedTiles) {
            tileEntity.invalidate();
            this.addedTileEntityList.remove(tileEntity);
        }
        else {
            if (tileEntity != null) {
                this.addedTileEntityList.remove(tileEntity);
                this.loadedTileEntityList.remove(tileEntity);
                this.tickableTileEntities.remove(tileEntity);
            }
            this.getChunkFromBlockCoords(blockPos).removeTileEntity(blockPos);
        }
    }
    
    public void markTileEntityForRemoval(final TileEntity tileEntity) {
        this.tileEntitiesToBeRemoved.add(tileEntity);
    }
    
    public boolean func_175665_u(final BlockPos blockPos) {
        final IBlockState blockState = this.getBlockState(blockPos);
        final AxisAlignedBB collisionBoundingBox = blockState.getBlock().getCollisionBoundingBox(this, blockPos, blockState);
        return collisionBoundingBox != null && collisionBoundingBox.getAverageEdgeLength() >= 1.0;
    }
    
    public static boolean doesBlockHaveSolidTopSurface(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        return (block.getMaterial().isOpaque() && block.isFullCube()) || ((block instanceof BlockStairs) ? (blockState.getValue(BlockStairs.HALF) == BlockStairs.EnumHalf.TOP) : ((block instanceof BlockSlab) ? (blockState.getValue(BlockSlab.HALF_PROP) == BlockSlab.EnumBlockHalf.TOP) : (block instanceof BlockHopper || (block instanceof BlockSnow && (int)blockState.getValue(BlockSnow.LAYERS_PROP) == 7))));
    }
    
    public boolean func_175677_d(final BlockPos blockPos, final boolean b) {
        if (this >= blockPos) {
            return b;
        }
        if (this.chunkProvider.func_177459_a(blockPos).isEmpty()) {
            return b;
        }
        final Block block = this.getBlockState(blockPos).getBlock();
        return block.getMaterial().isOpaque() && block.isFullCube();
    }
    
    public void calculateInitialSkylight() {
        final int calculateSkylightSubtracted = this.calculateSkylightSubtracted(1.0f);
        if (calculateSkylightSubtracted != this.skylightSubtracted) {
            this.skylightSubtracted = calculateSkylightSubtracted;
        }
    }
    
    public void setAllowedSpawnTypes(final boolean spawnHostileMobs, final boolean spawnPeacefulMobs) {
        this.spawnHostileMobs = spawnHostileMobs;
        this.spawnPeacefulMobs = spawnPeacefulMobs;
    }
    
    public void tick() {
        this.updateWeather();
    }
    
    protected void calculateInitialWeather() {
        if (this.worldInfo.isRaining()) {
            this.rainingStrength = 1.0f;
            if (this.worldInfo.isThundering()) {
                this.thunderingStrength = 1.0f;
            }
        }
    }
    
    protected void updateWeather() {
        if (!this.provider.getHasNoSky() && !this.isRemote) {
            int func_176133_A = this.worldInfo.func_176133_A();
            if (func_176133_A > 0) {
                --func_176133_A;
                this.worldInfo.func_176142_i(func_176133_A);
                this.worldInfo.setThunderTime(this.worldInfo.isThundering() ? 1 : 2);
                this.worldInfo.setRainTime(this.worldInfo.isRaining() ? 1 : 2);
            }
            int thunderTime = this.worldInfo.getThunderTime();
            if (thunderTime <= 0) {
                if (this.worldInfo.isThundering()) {
                    this.worldInfo.setThunderTime(this.rand.nextInt(12000) + 3600);
                }
                else {
                    this.worldInfo.setThunderTime(this.rand.nextInt(168000) + 12000);
                }
            }
            else {
                --thunderTime;
                this.worldInfo.setThunderTime(thunderTime);
                if (thunderTime <= 0) {
                    this.worldInfo.setThundering(!this.worldInfo.isThundering());
                }
            }
            this.prevThunderingStrength = this.thunderingStrength;
            if (this.worldInfo.isThundering()) {
                this.thunderingStrength += (float)0.01;
            }
            else {
                this.thunderingStrength -= (float)0.01;
            }
            this.thunderingStrength = MathHelper.clamp_float(this.thunderingStrength, 0.0f, 1.0f);
            int rainTime = this.worldInfo.getRainTime();
            if (rainTime <= 0) {
                if (this.worldInfo.isRaining()) {
                    this.worldInfo.setRainTime(this.rand.nextInt(12000) + 12000);
                }
                else {
                    this.worldInfo.setRainTime(this.rand.nextInt(168000) + 12000);
                }
            }
            else {
                --rainTime;
                this.worldInfo.setRainTime(rainTime);
                if (rainTime <= 0) {
                    this.worldInfo.setRaining(!this.worldInfo.isRaining());
                }
            }
            this.prevRainingStrength = this.rainingStrength;
            if (this.worldInfo.isRaining()) {
                this.rainingStrength += (float)0.01;
            }
            else {
                this.rainingStrength -= (float)0.01;
            }
            this.rainingStrength = MathHelper.clamp_float(this.rainingStrength, 0.0f, 1.0f);
        }
    }
    
    protected void setActivePlayerChunksAndCheckLight() {
        this.activeChunkSet.clear();
        this.theProfiler.startSection("buildList");
        while (0 < this.playerEntities.size()) {
            final EntityPlayer entityPlayer = this.playerEntities.get(0);
            final int floor_double = MathHelper.floor_double(entityPlayer.posX / 16.0);
            final int floor_double2 = MathHelper.floor_double(entityPlayer.posZ / 16.0);
            for (int renderDistanceChunks = this.getRenderDistanceChunks(), i = -renderDistanceChunks; i <= renderDistanceChunks; ++i) {
                for (int j = -renderDistanceChunks; j <= renderDistanceChunks; ++j) {
                    this.activeChunkSet.add(new ChunkCoordIntPair(i + floor_double, j + floor_double2));
                }
            }
            int nextInt = 0;
            ++nextInt;
        }
        this.theProfiler.endSection();
        if (this.ambientTickCountdown > 0) {
            --this.ambientTickCountdown;
        }
        this.theProfiler.startSection("playerCheckLight");
        if (!this.playerEntities.isEmpty()) {
            final int nextInt = this.rand.nextInt(this.playerEntities.size());
            final EntityPlayer entityPlayer2 = this.playerEntities.get(0);
            this.checkLight(new BlockPos(MathHelper.floor_double(entityPlayer2.posX) + this.rand.nextInt(11) - 5, MathHelper.floor_double(entityPlayer2.posY) + this.rand.nextInt(11) - 5, MathHelper.floor_double(entityPlayer2.posZ) + this.rand.nextInt(11) - 5));
        }
        this.theProfiler.endSection();
    }
    
    protected abstract int getRenderDistanceChunks();
    
    protected void func_147467_a(final int n, final int n2, final Chunk chunk) {
        this.theProfiler.endStartSection("moodSound");
        if (this.ambientTickCountdown == 0 && !this.isRemote) {
            this.updateLCG = this.updateLCG * 3 + 1013904223;
            final int n3 = this.updateLCG >> 2;
            final int n4 = n3 & 0xF;
            final int n5 = n3 >> 8 & 0xF;
            final int n6 = n3 >> 16 & 0xFF;
            final BlockPos blockPos = new BlockPos(n4, n6, n5);
            final Block block = chunk.getBlock(blockPos);
            final int n7 = n4 + n;
            final int n8 = n5 + n2;
            if (block.getMaterial() == Material.air && this.getLight(blockPos) <= this.rand.nextInt(8) && this.getLightFor(EnumSkyBlock.SKY, blockPos) <= 0) {
                final EntityPlayer closestPlayer = this.getClosestPlayer(n7 + 0.5, n6 + 0.5, n8 + 0.5, 8.0);
                if (closestPlayer != null && closestPlayer.getDistanceSq(n7 + 0.5, n6 + 0.5, n8 + 0.5) > 4.0) {
                    this.playSoundEffect(n7 + 0.5, n6 + 0.5, n8 + 0.5, "ambient.cave.cave", 0.7f, 0.8f + this.rand.nextFloat() * 0.2f);
                    this.ambientTickCountdown = this.rand.nextInt(12000) + 6000;
                }
            }
        }
        this.theProfiler.endStartSection("checkLight");
        chunk.enqueueRelightChecks();
    }
    
    protected void func_147456_g() {
        this.setActivePlayerChunksAndCheckLight();
    }
    
    public void func_175637_a(final Block block, final BlockPos blockPos, final Random random) {
        this.scheduledUpdatesAreImmediate = true;
        block.updateTick(this, blockPos, this.getBlockState(blockPos), random);
        this.scheduledUpdatesAreImmediate = false;
    }
    
    public boolean func_175675_v(final BlockPos blockPos) {
        return this.func_175670_e(blockPos, false);
    }
    
    public boolean func_175662_w(final BlockPos blockPos) {
        return this.func_175670_e(blockPos, true);
    }
    
    public boolean func_175670_e(final BlockPos blockPos, final boolean b) {
        if (this.getBiomeGenForCoords(blockPos).func_180626_a(blockPos) > 0.15f) {
            return false;
        }
        if (blockPos.getY() >= 0 && blockPos.getY() < 256 && this.getLightFor(EnumSkyBlock.BLOCK, blockPos) < 10) {
            final IBlockState blockState = this.getBlockState(blockPos);
            final Block block = blockState.getBlock();
            if ((block == Blocks.water || block == Blocks.flowing_water) && (int)blockState.getValue(BlockLiquid.LEVEL) == 0) {
                if (!b) {
                    return true;
                }
                if (this != blockPos.offsetWest() || this != blockPos.offsetEast() || this != blockPos.offsetNorth() || this != blockPos.offsetSouth()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean checkLight(final BlockPos blockPos) {
        if (!this.provider.getHasNoSky()) {
            final boolean b = false | this.checkLightFor(EnumSkyBlock.SKY, blockPos);
        }
        final boolean b2 = false | this.checkLightFor(EnumSkyBlock.BLOCK, blockPos);
        return false;
    }
    
    private int func_175638_a(final BlockPos blockPos, final EnumSkyBlock enumSkyBlock) {
        if (enumSkyBlock == EnumSkyBlock.SKY && this.isAgainstSky(blockPos)) {
            return 15;
        }
        final Block block = this.getBlockState(blockPos).getBlock();
        int n = (enumSkyBlock == EnumSkyBlock.SKY) ? 0 : block.getLightValue();
        block.getLightOpacity();
        if (n >= 14) {
            return n;
        }
        final EnumFacing[] values = EnumFacing.values();
        while (0 < values.length) {
            final int n2 = this.getLightFor(enumSkyBlock, blockPos.offset(values[0])) - 1;
            if (n2 > n) {
                n = n2;
            }
            if (n >= 14) {
                return n;
            }
            int n3 = 0;
            ++n3;
        }
        return n;
    }
    
    public boolean checkLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos) {
        if (!this.isAreaLoaded(blockPos, 17, false)) {
            return false;
        }
        this.theProfiler.startSection("getBrightness");
        final int light = this.getLightFor(enumSkyBlock, blockPos);
        final int func_175638_a = this.func_175638_a(blockPos, enumSkyBlock);
        blockPos.getX();
        blockPos.getY();
        blockPos.getZ();
        if (func_175638_a > light) {
            final int[] lightUpdateBlockList = this.lightUpdateBlockList;
            final int n = 0;
            int n2 = 0;
            ++n2;
            lightUpdateBlockList[n] = 133152;
        }
        else if (func_175638_a < light) {
            final int[] lightUpdateBlockList2 = this.lightUpdateBlockList;
            final int n3 = 0;
            int n2 = 0;
            ++n2;
            lightUpdateBlockList2[n3] = (0x20820 | light << 18);
        }
        this.theProfiler.endSection();
        this.theProfiler.startSection("checkedPosition < toCheckCount");
        this.theProfiler.endSection();
        return true;
    }
    
    public boolean tickUpdates(final boolean b) {
        return false;
    }
    
    public List getPendingBlockUpdates(final Chunk chunk, final boolean b) {
        return null;
    }
    
    public List func_175712_a(final StructureBoundingBox structureBoundingBox, final boolean b) {
        return null;
    }
    
    public List getEntitiesWithinAABBExcludingEntity(final Entity entity, final AxisAlignedBB axisAlignedBB) {
        return this.func_175674_a(entity, axisAlignedBB, IEntitySelector.field_180132_d);
    }
    
    public List func_175674_a(final Entity p0, final AxisAlignedBB p1, final Predicate p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: astore          4
        //     5: aload_2        
        //     6: getfield        net/minecraft/util/AxisAlignedBB.minX:D
        //     9: ldc2_w          2.0
        //    12: dsub           
        //    13: ldc2_w          16.0
        //    16: ddiv           
        //    17: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    20: istore          5
        //    22: aload_2        
        //    23: getfield        net/minecraft/util/AxisAlignedBB.maxX:D
        //    26: ldc2_w          2.0
        //    29: dadd           
        //    30: ldc2_w          16.0
        //    33: ddiv           
        //    34: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    37: istore          6
        //    39: aload_2        
        //    40: getfield        net/minecraft/util/AxisAlignedBB.minZ:D
        //    43: ldc2_w          2.0
        //    46: dsub           
        //    47: ldc2_w          16.0
        //    50: ddiv           
        //    51: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    54: istore          7
        //    56: aload_2        
        //    57: getfield        net/minecraft/util/AxisAlignedBB.maxZ:D
        //    60: ldc2_w          2.0
        //    63: dadd           
        //    64: ldc2_w          16.0
        //    67: ddiv           
        //    68: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    71: istore          8
        //    73: iload           5
        //    75: istore          9
        //    77: goto            121
        //    80: iload           7
        //    82: istore          10
        //    84: goto            111
        //    87: aload_0        
        //    88: iload           9
        //    90: iload           10
        //    92: aload_0        
        //    93: iload           9
        //    95: iload           10
        //    97: invokevirtual   net/minecraft/world/World.getChunkFromChunkCoords:(II)Lnet/minecraft/world/chunk/Chunk;
        //   100: aload_1        
        //   101: aload_2        
        //   102: aload           4
        //   104: aload_3        
        //   105: invokevirtual   net/minecraft/world/chunk/Chunk.func_177414_a:(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;Ljava/util/List;Lcom/google/common/base/Predicate;)V
        //   108: iinc            10, 1
        //   111: iload           10
        //   113: iload           8
        //   115: if_icmple       87
        //   118: iinc            9, 1
        //   121: iload           9
        //   123: iload           6
        //   125: if_icmple       80
        //   128: aload           4
        //   130: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0111 (coming from #0108).
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
    
    public List func_175644_a(final Class clazz, final Predicate predicate) {
        final ArrayList arrayList = Lists.newArrayList();
        for (final Entity entity : this.loadedEntityList) {
            if (clazz.isAssignableFrom(entity.getClass()) && predicate.apply(entity)) {
                arrayList.add(entity);
            }
        }
        return arrayList;
    }
    
    public List func_175661_b(final Class clazz, final Predicate predicate) {
        final ArrayList arrayList = Lists.newArrayList();
        for (final Entity entity : this.playerEntities) {
            if (clazz.isAssignableFrom(entity.getClass()) && predicate.apply(entity)) {
                arrayList.add(entity);
            }
        }
        return arrayList;
    }
    
    public List getEntitiesWithinAABB(final Class clazz, final AxisAlignedBB axisAlignedBB) {
        return this.func_175647_a(clazz, axisAlignedBB, IEntitySelector.field_180132_d);
    }
    
    public List func_175647_a(final Class p0, final AxisAlignedBB p1, final Predicate p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/util/AxisAlignedBB.minX:D
        //     4: ldc2_w          2.0
        //     7: dsub           
        //     8: ldc2_w          16.0
        //    11: ddiv           
        //    12: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    15: istore          4
        //    17: aload_2        
        //    18: getfield        net/minecraft/util/AxisAlignedBB.maxX:D
        //    21: ldc2_w          2.0
        //    24: dadd           
        //    25: ldc2_w          16.0
        //    28: ddiv           
        //    29: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    32: istore          5
        //    34: aload_2        
        //    35: getfield        net/minecraft/util/AxisAlignedBB.minZ:D
        //    38: ldc2_w          2.0
        //    41: dsub           
        //    42: ldc2_w          16.0
        //    45: ddiv           
        //    46: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    49: istore          6
        //    51: aload_2        
        //    52: getfield        net/minecraft/util/AxisAlignedBB.maxZ:D
        //    55: ldc2_w          2.0
        //    58: dadd           
        //    59: ldc2_w          16.0
        //    62: ddiv           
        //    63: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    66: istore          7
        //    68: invokestatic    com/google/common/collect/Lists.newArrayList:()Ljava/util/ArrayList;
        //    71: astore          8
        //    73: iload           4
        //    75: istore          9
        //    77: goto            121
        //    80: iload           6
        //    82: istore          10
        //    84: goto            111
        //    87: aload_0        
        //    88: iload           9
        //    90: iload           10
        //    92: aload_0        
        //    93: iload           9
        //    95: iload           10
        //    97: invokevirtual   net/minecraft/world/World.getChunkFromChunkCoords:(II)Lnet/minecraft/world/chunk/Chunk;
        //   100: aload_1        
        //   101: aload_2        
        //   102: aload           8
        //   104: aload_3        
        //   105: invokevirtual   net/minecraft/world/chunk/Chunk.func_177430_a:(Ljava/lang/Class;Lnet/minecraft/util/AxisAlignedBB;Ljava/util/List;Lcom/google/common/base/Predicate;)V
        //   108: iinc            10, 1
        //   111: iload           10
        //   113: iload           7
        //   115: if_icmple       87
        //   118: iinc            9, 1
        //   121: iload           9
        //   123: iload           5
        //   125: if_icmple       80
        //   128: aload           8
        //   130: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0111 (coming from #0108).
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
    
    public Entity findNearestEntityWithinAABB(final Class clazz, final AxisAlignedBB axisAlignedBB, final Entity entity) {
        final List entitiesWithinAABB = this.getEntitiesWithinAABB(clazz, axisAlignedBB);
        Entity entity2 = null;
        double n = Double.MAX_VALUE;
        while (0 < entitiesWithinAABB.size()) {
            final Entity entity3 = entitiesWithinAABB.get(0);
            if (entity3 != entity && IEntitySelector.field_180132_d.apply(entity3)) {
                final double distanceSqToEntity = entity.getDistanceSqToEntity(entity3);
                if (distanceSqToEntity <= n) {
                    entity2 = entity3;
                    n = distanceSqToEntity;
                }
            }
            int n2 = 0;
            ++n2;
        }
        return entity2;
    }
    
    public Entity getEntityByID(final int n) {
        return (Entity)this.entitiesById.lookup(n);
    }
    
    public List getLoadedEntityList() {
        return this.loadedEntityList;
    }
    
    public void func_175646_b(final BlockPos blockPos, final TileEntity tileEntity) {
        if (this.isBlockLoaded(blockPos)) {
            this.getChunkFromBlockCoords(blockPos).setChunkModified();
        }
    }
    
    public int countEntities(final Class clazz) {
        for (final Entity entity : this.loadedEntityList) {
            if ((!(entity instanceof EntityLiving) || !((EntityLiving)entity).isNoDespawnRequired()) && clazz.isAssignableFrom(((EntityLiving)entity).getClass())) {
                int n = 0;
                ++n;
            }
        }
        return 0;
    }
    
    public void loadEntities(final Collection collection) {
        this.loadedEntityList.addAll(collection);
        final Iterator<Entity> iterator = collection.iterator();
        while (iterator.hasNext()) {
            this.onEntityAdded(iterator.next());
        }
    }
    
    public void unloadEntities(final Collection collection) {
        this.unloadedEntityList.addAll(collection);
    }
    
    public boolean canBlockBePlaced(final Block p0, final BlockPos p1, final boolean p2, final EnumFacing p3, final Entity p4, final ItemStack p5) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_2        
        //     2: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //     5: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    10: astore          7
        //    12: iload_3        
        //    13: ifeq            20
        //    16: aconst_null    
        //    17: goto            30
        //    20: aload_1        
        //    21: aload_0        
        //    22: aload_2        
        //    23: aload_1        
        //    24: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //    27: invokevirtual   net/minecraft/block/Block.getCollisionBoundingBox:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/util/AxisAlignedBB;
        //    30: astore          8
        //    32: aload           8
        //    34: ifnull          49
        //    37: aload_0        
        //    38: aload           8
        //    40: aload           5
        //    42: ifne            49
        //    45: iconst_0       
        //    46: goto            100
        //    49: aload           7
        //    51: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //    54: getstatic       net/minecraft/block/material/Material.circuits:Lnet/minecraft/block/material/Material;
        //    57: if_acmpne       71
        //    60: aload_1        
        //    61: getstatic       net/minecraft/init/Blocks.anvil:Lnet/minecraft/block/Block;
        //    64: if_acmpne       71
        //    67: iconst_1       
        //    68: goto            100
        //    71: aload           7
        //    73: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //    76: invokevirtual   net/minecraft/block/material/Material.isReplaceable:()Z
        //    79: ifeq            99
        //    82: aload_1        
        //    83: aload_0        
        //    84: aload_2        
        //    85: aload           4
        //    87: aload           6
        //    89: invokevirtual   net/minecraft/block/Block.canReplace:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/item/ItemStack;)Z
        //    92: ifeq            99
        //    95: iconst_1       
        //    96: goto            100
        //    99: iconst_0       
        //   100: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0049 (coming from #0042).
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
    public int getStrongPower(final BlockPos blockPos, final EnumFacing enumFacing) {
        final IBlockState blockState = this.getBlockState(blockPos);
        return blockState.getBlock().isProvidingStrongPower(this, blockPos, blockState, enumFacing);
    }
    
    @Override
    public WorldType getWorldType() {
        return this.worldInfo.getTerrainType();
    }
    
    public int getStrongPower(final BlockPos blockPos) {
        final int max = Math.max(0, this.getStrongPower(blockPos.offsetDown(), EnumFacing.DOWN));
        if (max >= 15) {
            return max;
        }
        final int max2 = Math.max(max, this.getStrongPower(blockPos.offsetUp(), EnumFacing.UP));
        if (max2 >= 15) {
            return max2;
        }
        final int max3 = Math.max(max2, this.getStrongPower(blockPos.offsetNorth(), EnumFacing.NORTH));
        if (max3 >= 15) {
            return max3;
        }
        final int max4 = Math.max(max3, this.getStrongPower(blockPos.offsetSouth(), EnumFacing.SOUTH));
        if (max4 >= 15) {
            return max4;
        }
        final int max5 = Math.max(max4, this.getStrongPower(blockPos.offsetWest(), EnumFacing.WEST));
        if (max5 >= 15) {
            return max5;
        }
        final int max6 = Math.max(max5, this.getStrongPower(blockPos.offsetEast(), EnumFacing.EAST));
        return (max6 >= 15) ? max6 : max6;
    }
    
    public boolean func_175709_b(final BlockPos blockPos, final EnumFacing enumFacing) {
        return this.getRedstonePower(blockPos, enumFacing) > 0;
    }
    
    public int getRedstonePower(final BlockPos blockPos, final EnumFacing enumFacing) {
        final IBlockState blockState = this.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        return block.isNormalCube() ? this.getStrongPower(blockPos) : block.isProvidingWeakPower(this, blockPos, blockState, enumFacing);
    }
    
    public boolean isBlockPowered(final BlockPos blockPos) {
        return this.getRedstonePower(blockPos.offsetDown(), EnumFacing.DOWN) > 0 || this.getRedstonePower(blockPos.offsetUp(), EnumFacing.UP) > 0 || this.getRedstonePower(blockPos.offsetNorth(), EnumFacing.NORTH) > 0 || this.getRedstonePower(blockPos.offsetSouth(), EnumFacing.SOUTH) > 0 || this.getRedstonePower(blockPos.offsetWest(), EnumFacing.WEST) > 0 || this.getRedstonePower(blockPos.offsetEast(), EnumFacing.EAST) > 0;
    }
    
    public int func_175687_A(final BlockPos blockPos) {
        final EnumFacing[] values = EnumFacing.values();
        while (0 < values.length) {
            final EnumFacing enumFacing = values[0];
            final int redstonePower = this.getRedstonePower(blockPos.offset(enumFacing), enumFacing);
            if (redstonePower >= 15) {
                return 15;
            }
            if (redstonePower > 0) {}
            int n = 0;
            ++n;
        }
        return 0;
    }
    
    public EntityPlayer getClosestPlayerToEntity(final Entity entity, final double n) {
        return this.getClosestPlayer(entity.posX, entity.posY, entity.posZ, n);
    }
    
    public EntityPlayer getClosestPlayer(final double n, final double n2, final double n3, final double n4) {
        double n5 = -1.0;
        EntityPlayer entityPlayer = null;
        while (0 < this.playerEntities.size()) {
            final EntityPlayer entityPlayer2 = this.playerEntities.get(0);
            if (IEntitySelector.field_180132_d.apply(entityPlayer2)) {
                final double distanceSq = entityPlayer2.getDistanceSq(n, n2, n3);
                if ((n4 < 0.0 || distanceSq < n4 * n4) && (n5 == -1.0 || distanceSq < n5)) {
                    n5 = distanceSq;
                    entityPlayer = entityPlayer2;
                }
            }
            int n6 = 0;
            ++n6;
        }
        return entityPlayer;
    }
    
    public boolean func_175636_b(final double n, final double n2, final double n3, final double n4) {
        while (0 < this.playerEntities.size()) {
            final EntityPlayer entityPlayer = this.playerEntities.get(0);
            if (IEntitySelector.field_180132_d.apply(entityPlayer)) {
                final double distanceSq = entityPlayer.getDistanceSq(n, n2, n3);
                if (n4 < 0.0 || distanceSq < n4 * n4) {
                    return true;
                }
            }
            int n5 = 0;
            ++n5;
        }
        return false;
    }
    
    public EntityPlayer getPlayerEntityByName(final String s) {
        while (0 < this.playerEntities.size()) {
            final EntityPlayer entityPlayer = this.playerEntities.get(0);
            if (s.equals(entityPlayer.getName())) {
                return entityPlayer;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public EntityPlayer getPlayerEntityByUUID(final UUID uuid) {
        while (0 < this.playerEntities.size()) {
            final EntityPlayer entityPlayer = this.playerEntities.get(0);
            if (uuid.equals(entityPlayer.getUniqueID())) {
                return entityPlayer;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public void sendQuittingDisconnectingPacket() {
    }
    
    public void checkSessionLock() throws MinecraftException {
        this.saveHandler.checkSessionLock();
    }
    
    public void func_82738_a(final long n) {
        this.worldInfo.incrementTotalWorldTime(n);
    }
    
    public long getSeed() {
        return this.worldInfo.getSeed();
    }
    
    public long getTotalWorldTime() {
        return this.worldInfo.getWorldTotalTime();
    }
    
    public long getWorldTime() {
        return this.worldInfo.getWorldTime();
    }
    
    public void setWorldTime(final long worldTime) {
        this.worldInfo.setWorldTime(worldTime);
    }
    
    public BlockPos getSpawnPoint() {
        BlockPos horizon = new BlockPos(this.worldInfo.getSpawnX(), this.worldInfo.getSpawnY(), this.worldInfo.getSpawnZ());
        if (!this.getWorldBorder().contains(horizon)) {
            horizon = this.getHorizon(new BlockPos(this.getWorldBorder().getCenterX(), 0.0, this.getWorldBorder().getCenterZ()));
        }
        return horizon;
    }
    
    public void setSpawnLocation(final BlockPos spawn) {
        this.worldInfo.setSpawn(spawn);
    }
    
    public void joinEntityInSurroundings(final Entity entity) {
        final int floor_double = MathHelper.floor_double(entity.posX / 16.0);
        final int floor_double2 = MathHelper.floor_double(entity.posZ / 16.0);
        for (int i = floor_double - 2; i <= floor_double + 2; ++i) {
            for (int j = floor_double2 - 2; j <= floor_double2 + 2; ++j) {
                this.getChunkFromChunkCoords(i, j);
            }
        }
        if (!this.loadedEntityList.contains(entity)) {
            this.loadedEntityList.add(entity);
        }
    }
    
    public boolean isBlockModifiable(final EntityPlayer entityPlayer, final BlockPos blockPos) {
        return true;
    }
    
    public void setEntityState(final Entity entity, final byte b) {
    }
    
    public IChunkProvider getChunkProvider() {
        return this.chunkProvider;
    }
    
    public void addBlockEvent(final BlockPos blockPos, final Block block, final int n, final int n2) {
        block.onBlockEventReceived(this, blockPos, this.getBlockState(blockPos), n, n2);
    }
    
    public ISaveHandler getSaveHandler() {
        return this.saveHandler;
    }
    
    public WorldInfo getWorldInfo() {
        return this.worldInfo;
    }
    
    public GameRules getGameRules() {
        return this.worldInfo.getGameRulesInstance();
    }
    
    public void updateAllPlayersSleepingFlag() {
    }
    
    public float getWeightedThunderStrength(final float n) {
        return (this.prevThunderingStrength + (this.thunderingStrength - this.prevThunderingStrength) * n) * this.getRainStrength(n);
    }
    
    public void setThunderStrength(final float n) {
        this.prevThunderingStrength = n;
        this.thunderingStrength = n;
    }
    
    public float getRainStrength(final float n) {
        return this.prevRainingStrength + (this.rainingStrength - this.prevRainingStrength) * n;
    }
    
    public void setRainStrength(final float n) {
        this.prevRainingStrength = n;
        this.rainingStrength = n;
    }
    
    public boolean isThundering() {
        return this.getWeightedThunderStrength(1.0f) > 0.9;
    }
    
    public boolean func_175727_C(final BlockPos p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifle            6
        //     4: iconst_0       
        //     5: ireturn        
        //     6: aload_0        
        //     7: aload_1        
        //     8: invokevirtual   net/minecraft/world/World.isAgainstSky:(Lnet/minecraft/util/BlockPos;)Z
        //    11: ifne            16
        //    14: iconst_0       
        //    15: ireturn        
        //    16: aload_0        
        //    17: aload_1        
        //    18: invokevirtual   net/minecraft/world/World.func_175725_q:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //    21: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //    24: aload_1        
        //    25: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //    28: if_icmple       33
        //    31: iconst_0       
        //    32: ireturn        
        //    33: aload_0        
        //    34: aload_1        
        //    35: invokevirtual   net/minecraft/world/World.getBiomeGenForCoords:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/world/biome/BiomeGenBase;
        //    38: astore_2       
        //    39: aload_2        
        //    40: invokevirtual   net/minecraft/world/biome/BiomeGenBase.getEnableSnow:()Z
        //    43: ifeq            50
        //    46: iconst_0       
        //    47: goto            63
        //    50: aload_0        
        //    51: aload_1        
        //    52: goto            59
        //    55: iconst_0       
        //    56: goto            63
        //    59: aload_2        
        //    60: invokevirtual   net/minecraft/world/biome/BiomeGenBase.canSpawnLightningBolt:()Z
        //    63: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0063 (coming from #0047).
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
    
    public boolean func_180502_D(final BlockPos blockPos) {
        return this.getBiomeGenForCoords(blockPos).isHighHumidity();
    }
    
    public MapStorage func_175693_T() {
        return this.mapStorage;
    }
    
    public void setItemData(final String s, final WorldSavedData worldSavedData) {
        this.mapStorage.setData(s, worldSavedData);
    }
    
    public WorldSavedData loadItemData(final Class clazz, final String s) {
        return this.mapStorage.loadData(clazz, s);
    }
    
    public int getUniqueDataId(final String s) {
        return this.mapStorage.getUniqueDataId(s);
    }
    
    public void func_175669_a(final int n, final BlockPos blockPos, final int n2) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).func_180440_a(n, blockPos, n2);
            int n3 = 0;
            ++n3;
        }
    }
    
    public void playAuxSFX(final int n, final BlockPos blockPos, final int n2) {
        this.playAuxSFXAtEntity(null, n, blockPos, n2);
    }
    
    public void playAuxSFXAtEntity(final EntityPlayer entityPlayer, final int n, final BlockPos blockPos, final int n2) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).func_180439_a(entityPlayer, n, blockPos, n2);
            int n3 = 0;
            ++n3;
        }
    }
    
    public int getHeight() {
        return 256;
    }
    
    public int getActualHeight() {
        return this.provider.getHasNoSky() ? 128 : 256;
    }
    
    public Random setRandomSeed(final int n, final int n2, final int n3) {
        this.rand.setSeed(n * 341873128712L + n2 * 132897987541L + this.getWorldInfo().getSeed() + n3);
        return this.rand;
    }
    
    public BlockPos func_180499_a(final String s, final BlockPos blockPos) {
        return this.getChunkProvider().func_180513_a(this, s, blockPos);
    }
    
    @Override
    public boolean extendedLevelsInChunkCache() {
        return false;
    }
    
    public double getHorizon() {
        return (this.worldInfo.getTerrainType() == WorldType.FLAT) ? 0.0 : 63.0;
    }
    
    public CrashReportCategory addWorldInfoToCrashReport(final CrashReport crashReport) {
        final CrashReportCategory categoryDepth = crashReport.makeCategoryDepth("Affected level", 1);
        categoryDepth.addCrashSection("Level name", (this.worldInfo == null) ? "????" : this.worldInfo.getWorldName());
        categoryDepth.addCrashSectionCallable("All players", new Callable() {
            private static final String __OBFID;
            final World this$0;
            
            @Override
            public String call() {
                return String.valueOf(this.this$0.playerEntities.size()) + " total; " + this.this$0.playerEntities.toString();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000143";
            }
        });
        categoryDepth.addCrashSectionCallable("Chunk stats", new Callable() {
            private static final String __OBFID;
            final World this$0;
            
            @Override
            public String call() {
                return this.this$0.chunkProvider.makeString();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000144";
            }
        });
        this.worldInfo.addToCrashReport(categoryDepth);
        return categoryDepth;
    }
    
    public void sendBlockBreakProgress(final int n, final BlockPos blockPos, final int n2) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).sendBlockBreakProgress(n, blockPos, n2);
            int n3 = 0;
            ++n3;
        }
    }
    
    public Calendar getCurrentDate() {
        if (this.getTotalWorldTime() % 600L == 0L) {
            this.theCalendar.setTimeInMillis(MinecraftServer.getCurrentTimeMillis());
        }
        return this.theCalendar;
    }
    
    public void makeFireworks(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final NBTTagCompound nbtTagCompound) {
    }
    
    public Scoreboard getScoreboard() {
        return this.worldScoreboard;
    }
    
    public void updateComparatorOutputLevel(final BlockPos blockPos, final Block block) {
        for (final EnumFacing enumFacing : EnumFacing.Plane.HORIZONTAL) {
            final BlockPos offset = blockPos.offset(enumFacing);
            if (this.isBlockLoaded(offset)) {
                final IBlockState blockState = this.getBlockState(offset);
                if (Blocks.unpowered_comparator.func_149907_e(blockState.getBlock())) {
                    blockState.getBlock().onNeighborBlockChange(this, offset, blockState, block);
                }
                else {
                    if (!blockState.getBlock().isNormalCube()) {
                        continue;
                    }
                    final BlockPos offset2 = offset.offset(enumFacing);
                    final IBlockState blockState2 = this.getBlockState(offset2);
                    if (!Blocks.unpowered_comparator.func_149907_e(blockState2.getBlock())) {
                        continue;
                    }
                    blockState2.getBlock().onNeighborBlockChange(this, offset2, blockState2, block);
                }
            }
        }
    }
    
    public DifficultyInstance getDifficultyForLocation(final BlockPos blockPos) {
        long inhabitedTime = 0L;
        float currentMoonPhaseFactor = 0.0f;
        if (this.isBlockLoaded(blockPos)) {
            currentMoonPhaseFactor = this.getCurrentMoonPhaseFactor();
            inhabitedTime = this.getChunkFromBlockCoords(blockPos).getInhabitedTime();
        }
        return new DifficultyInstance(this.getDifficulty(), this.getWorldTime(), inhabitedTime, currentMoonPhaseFactor);
    }
    
    public EnumDifficulty getDifficulty() {
        return this.getWorldInfo().getDifficulty();
    }
    
    public int getSkylightSubtracted() {
        return this.skylightSubtracted;
    }
    
    public void setSkylightSubtracted(final int skylightSubtracted) {
        this.skylightSubtracted = skylightSubtracted;
    }
    
    public int func_175658_ac() {
        return this.lastLightningBolt;
    }
    
    public void setLastLightningBolt(final int lastLightningBolt) {
        this.lastLightningBolt = lastLightningBolt;
    }
    
    public boolean isFindingSpawnPoint() {
        return this.findingSpawnPoint;
    }
    
    public VillageCollection getVillageCollection() {
        return this.villageCollectionObj;
    }
    
    public WorldBorder getWorldBorder() {
        return this.worldBorder;
    }
    
    public boolean chunkExists(final int n, final int n2) {
        final BlockPos spawnPoint = this.getSpawnPoint();
        final int n3 = n * 16 + 8 - spawnPoint.getX();
        final int n4 = n2 * 16 + 8 - spawnPoint.getZ();
        return n3 >= -128 && n3 <= 128 && n4 >= -128 && n4 <= 128;
    }
    
    static {
        __OBFID = "CL_00000140";
    }
}
