package net.minecraft.world.chunk;

import net.minecraft.world.chunk.storage.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.world.gen.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;

public class Chunk
{
    private static final Logger logger;
    private final ExtendedBlockStorage[] storageArrays;
    private final byte[] blockBiomeArray;
    private final int[] precipitationHeightMap;
    private final boolean[] updateSkylightColumns;
    private boolean isChunkLoaded;
    private final World worldObj;
    private final int[] heightMap;
    public final int xPosition;
    public final int zPosition;
    private boolean isGapLightingUpdated;
    private final Map chunkTileEntityMap;
    private final ClassInheratanceMultiMap[] entityLists;
    private boolean isTerrainPopulated;
    private boolean isLightPopulated;
    private boolean field_150815_m;
    private boolean isModified;
    private boolean hasEntities;
    private long lastSaveTime;
    private int heightMapMinimum;
    private long inhabitedTime;
    private int queuedLightChecks;
    private ConcurrentLinkedQueue field_177447_w;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000373";
        logger = LogManager.getLogger();
    }
    
    public Chunk(final World worldObj, final int xPosition, final int zPosition) {
        this.storageArrays = new ExtendedBlockStorage[16];
        this.blockBiomeArray = new byte[256];
        this.precipitationHeightMap = new int[256];
        this.updateSkylightColumns = new boolean[256];
        this.chunkTileEntityMap = Maps.newHashMap();
        this.queuedLightChecks = 4096;
        this.field_177447_w = Queues.newConcurrentLinkedQueue();
        this.entityLists = new ClassInheratanceMultiMap[16];
        this.worldObj = worldObj;
        this.xPosition = xPosition;
        this.zPosition = zPosition;
        this.heightMap = new int[256];
        while (0 < this.entityLists.length) {
            this.entityLists[0] = new ClassInheratanceMultiMap(Entity.class);
            int n = 0;
            ++n;
        }
        Arrays.fill(this.precipitationHeightMap, -999);
        Arrays.fill(this.blockBiomeArray, (byte)(-1));
    }
    
    public Chunk(final World world, final ChunkPrimer chunkPrimer, final int n, final int n2) {
        this(world, n, n2);
        final boolean b = !world.provider.getHasNoSky();
        while (0 < 16) {
            while (0 < 16) {
                while (0 < 256) {
                    final IBlockState blockState = chunkPrimer.getBlockState(0);
                    if (blockState.getBlock().getMaterial() != Material.air) {
                        if (this.storageArrays[0] == null) {
                            this.storageArrays[0] = new ExtendedBlockStorage(0, b);
                        }
                        this.storageArrays[0].set(0, 0, 0, blockState);
                    }
                    int n3 = 0;
                    ++n3;
                }
                int n4 = 0;
                ++n4;
            }
            int n5 = 0;
            ++n5;
        }
    }
    
    public boolean isAtLocation(final int n, final int n2) {
        return n == this.xPosition && n2 == this.zPosition;
    }
    
    public int getHeight(final BlockPos blockPos) {
        return this.getHeight(blockPos.getX() & 0xF, blockPos.getZ() & 0xF);
    }
    
    public int getHeight(final int n, final int n2) {
        return this.heightMap[n2 << 4 | n];
    }
    
    public int getTopFilledSegment() {
        for (int i = this.storageArrays.length - 1; i >= 0; --i) {
            if (this.storageArrays[i] != null) {
                return this.storageArrays[i].getYLocation();
            }
        }
        return 0;
    }
    
    public ExtendedBlockStorage[] getBlockStorageArray() {
        return this.storageArrays;
    }
    
    protected void generateHeightMap() {
        final int topFilledSegment = this.getTopFilledSegment();
        this.heightMapMinimum = Integer.MAX_VALUE;
        while (0 < 16) {
            while (0 < 16) {
                this.precipitationHeightMap[0] = -999;
                int i = topFilledSegment + 16;
                while (i > 0) {
                    if (this.getBlock0(0, i - 1, 0).getLightOpacity() == 0) {
                        --i;
                    }
                    else {
                        if ((this.heightMap[0] = i) < this.heightMapMinimum) {
                            this.heightMapMinimum = i;
                            break;
                        }
                        break;
                    }
                }
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        this.isModified = true;
    }
    
    public void generateSkylightMap() {
        final int topFilledSegment = this.getTopFilledSegment();
        this.heightMapMinimum = Integer.MAX_VALUE;
        while (0 < 16) {
            while (0 < 16) {
                this.precipitationHeightMap[0] = -999;
                int n = topFilledSegment + 16;
                while (15 > 0) {
                    if (this.getBlockLightOpacity(0, 14, 0) == 0) {
                        --n;
                    }
                    else {
                        this.heightMap[0] = 15;
                        if (15 < this.heightMapMinimum) {
                            this.heightMapMinimum = 15;
                            break;
                        }
                        break;
                    }
                }
                if (!this.worldObj.provider.getHasNoSky()) {
                    int n2 = topFilledSegment + 16 - 1;
                    do {
                        this.getBlockLightOpacity(0, n2, 0);
                        if (true || 15 != 15) {}
                        if (15 > 0) {
                            final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[n2 >> 4];
                            if (extendedBlockStorage == null) {
                                continue;
                            }
                            extendedBlockStorage.setExtSkylightValue(0, n2 & 0xF, 0, 15);
                            this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + 0, n2, (this.zPosition << 4) + 0));
                        }
                    } while (--n2 > 0 && 15 > 0);
                }
                int n3 = 0;
                ++n3;
            }
            int n4 = 0;
            ++n4;
        }
        this.isModified = true;
    }
    
    private void propagateSkylightOcclusion(final int n, final int n2) {
        this.updateSkylightColumns[n + n2 * 16] = true;
        this.isGapLightingUpdated = true;
    }
    
    private void recheckGaps(final boolean b) {
        this.worldObj.theProfiler.startSection("recheckGaps");
        if (this.worldObj.isAreaLoaded(new BlockPos(this.xPosition * 16 + 8, 0, this.zPosition * 16 + 8), 16)) {
            while (0 < 16) {
                while (0 < 16) {
                    if (this.updateSkylightColumns[0]) {
                        this.updateSkylightColumns[0] = false;
                        final int height = this.getHeight(0, 0);
                        final int n = this.xPosition * 16 + 0;
                        final int n2 = this.zPosition * 16 + 0;
                        for (final EnumFacing enumFacing : EnumFacing.Plane.HORIZONTAL) {
                            Math.min(Integer.MAX_VALUE, this.worldObj.getChunksLowestHorizon(n + enumFacing.getFrontOffsetX(), n2 + enumFacing.getFrontOffsetZ()));
                        }
                        this.checkSkylightNeighborHeight(n, n2, Integer.MAX_VALUE);
                        for (final EnumFacing enumFacing2 : EnumFacing.Plane.HORIZONTAL) {
                            this.checkSkylightNeighborHeight(n + enumFacing2.getFrontOffsetX(), n2 + enumFacing2.getFrontOffsetZ(), height);
                        }
                        if (b) {
                            this.worldObj.theProfiler.endSection();
                            return;
                        }
                    }
                    int n3 = 0;
                    ++n3;
                }
                int n4 = 0;
                ++n4;
            }
            this.isGapLightingUpdated = false;
        }
        this.worldObj.theProfiler.endSection();
    }
    
    private void checkSkylightNeighborHeight(final int n, final int n2, final int n3) {
        final int y = this.worldObj.getHorizon(new BlockPos(n, 0, n2)).getY();
        if (y > n3) {
            this.updateSkylightNeighborHeight(n, n2, n3, y + 1);
        }
        else if (y < n3) {
            this.updateSkylightNeighborHeight(n, n2, y, n3 + 1);
        }
    }
    
    private void updateSkylightNeighborHeight(final int n, final int n2, final int n3, final int n4) {
        if (n4 > n3 && this.worldObj.isAreaLoaded(new BlockPos(n, 0, n2), 16)) {
            for (int i = n3; i < n4; ++i) {
                this.worldObj.checkLightFor(EnumSkyBlock.SKY, new BlockPos(n, i, n2));
            }
            this.isModified = true;
        }
    }
    
    private void relightBlock(final int n, final int n2, final int n3) {
        int n5;
        final int n4 = n5 = (this.heightMap[n3 << 4 | n] & 0xFF);
        if (n2 > n4) {
            n5 = n2;
        }
        while (n5 > 0 && this.getBlockLightOpacity(n, n5 - 1, n3) == 0) {
            --n5;
        }
        if (n5 != n4) {
            this.worldObj.markBlocksDirtyVertical(n + this.xPosition * 16, n3 + this.zPosition * 16, n5, n4);
            this.heightMap[n3 << 4 | n] = n5;
            final int n6 = this.xPosition * 16 + n;
            final int n7 = this.zPosition * 16 + n3;
            if (!this.worldObj.provider.getHasNoSky()) {
                if (n5 < n4) {
                    int n8 = n5;
                    while (0 < n4) {
                        final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[0];
                        if (extendedBlockStorage != null) {
                            extendedBlockStorage.setExtSkylightValue(n, 0, n3, 15);
                            this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + n, 0, (this.zPosition << 4) + n3));
                        }
                        ++n8;
                    }
                }
                else {
                    int n9 = n4;
                    while (0 < n5) {
                        final ExtendedBlockStorage extendedBlockStorage2 = this.storageArrays[0];
                        if (extendedBlockStorage2 != null) {
                            extendedBlockStorage2.setExtSkylightValue(n, 0, n3, 0);
                            this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + n, 0, (this.zPosition << 4) + n3));
                        }
                        ++n9;
                    }
                }
                while (n5 > 0 && 0 > 0) {
                    --n5;
                    this.getBlockLightOpacity(n, n5, n3);
                    if (!true) {}
                    if (0 < 0) {}
                    final ExtendedBlockStorage extendedBlockStorage3 = this.storageArrays[n5 >> 4];
                    if (extendedBlockStorage3 != null) {
                        extendedBlockStorage3.setExtSkylightValue(n, n5 & 0xF, n3, 0);
                    }
                }
            }
            final int n10 = this.heightMap[n3 << 4 | n];
            final int n11;
            if (0 < (n11 = n4)) {}
            if (0 < this.heightMapMinimum) {
                this.heightMapMinimum = 0;
            }
            if (!this.worldObj.provider.getHasNoSky()) {
                for (final EnumFacing enumFacing : EnumFacing.Plane.HORIZONTAL) {
                    this.updateSkylightNeighborHeight(n6 + enumFacing.getFrontOffsetX(), n7 + enumFacing.getFrontOffsetZ(), 1, 0);
                }
                this.updateSkylightNeighborHeight(n6, n7, 1, 0);
            }
            this.isModified = true;
        }
    }
    
    public int getBlockLightOpacity(final BlockPos blockPos) {
        return this.getBlock(blockPos).getLightOpacity();
    }
    
    private int getBlockLightOpacity(final int n, final int n2, final int n3) {
        return this.getBlock0(n, n2, n3).getLightOpacity();
    }
    
    private Block getBlock0(final int n, final int n2, final int n3) {
        Block block = Blocks.air;
        if (n2 >= 0 && n2 >> 4 < this.storageArrays.length) {
            final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[n2 >> 4];
            if (extendedBlockStorage != null) {
                block = extendedBlockStorage.getBlockByExtId(n, n2 & 0xF, n3);
            }
        }
        return block;
    }
    
    public Block getBlock(final int n, final int n2, final int n3) {
        return this.getBlock0(n & 0xF, n2, n3 & 0xF);
    }
    
    public Block getBlock(final BlockPos blockPos) {
        return this.getBlock0(blockPos.getX() & 0xF, blockPos.getY(), blockPos.getZ() & 0xF);
    }
    
    public IBlockState getBlockState(final BlockPos blockPos) {
        if (this.worldObj.getWorldType() == WorldType.DEBUG_WORLD) {
            IBlockState blockState = null;
            if (blockPos.getY() == 60) {
                blockState = Blocks.barrier.getDefaultState();
            }
            if (blockPos.getY() == 70) {
                blockState = ChunkProviderDebug.func_177461_b(blockPos.getX(), blockPos.getZ());
            }
            return (blockState == null) ? Blocks.air.getDefaultState() : blockState;
        }
        if (blockPos.getY() >= 0 && blockPos.getY() >> 4 < this.storageArrays.length) {
            final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[blockPos.getY() >> 4];
            if (extendedBlockStorage != null) {
                return extendedBlockStorage.get(blockPos.getX() & 0xF, blockPos.getY() & 0xF, blockPos.getZ() & 0xF);
            }
        }
        return Blocks.air.getDefaultState();
    }
    
    private int getBlockMetadata(final int n, final int n2, final int n3) {
        if (n2 >> 4 >= this.storageArrays.length) {
            return 0;
        }
        final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[n2 >> 4];
        return (extendedBlockStorage != null) ? extendedBlockStorage.getExtBlockMetadata(n, n2 & 0xF, n3) : 0;
    }
    
    public int getBlockMetadata(final BlockPos blockPos) {
        return this.getBlockMetadata(blockPos.getX() & 0xF, blockPos.getY(), blockPos.getZ() & 0xF);
    }
    
    public IBlockState setBlockState(final BlockPos blockPos, final IBlockState blockState) {
        final int n = blockPos.getX() & 0xF;
        final int y = blockPos.getY();
        final int n2 = blockPos.getZ() & 0xF;
        final int n3 = n2 << 4 | n;
        if (y >= this.precipitationHeightMap[n3] - 1) {
            this.precipitationHeightMap[n3] = -999;
        }
        final int n4 = this.heightMap[n3];
        final IBlockState blockState2 = this.getBlockState(blockPos);
        if (blockState2 == blockState) {
            return null;
        }
        final Block block = blockState.getBlock();
        final Block block2 = blockState2.getBlock();
        ExtendedBlockStorage extendedBlockStorage = this.storageArrays[y >> 4];
        if (extendedBlockStorage == null) {
            if (block == Blocks.air) {
                return null;
            }
            final ExtendedBlockStorage[] storageArrays = this.storageArrays;
            final int n5 = y >> 4;
            final ExtendedBlockStorage extendedBlockStorage2 = new ExtendedBlockStorage(y >> 4 << 4, !this.worldObj.provider.getHasNoSky());
            storageArrays[n5] = extendedBlockStorage2;
            extendedBlockStorage = extendedBlockStorage2;
            final boolean b = y >= n4;
        }
        extendedBlockStorage.set(n, y & 0xF, n2, blockState);
        if (block2 != block) {
            if (!this.worldObj.isRemote) {
                block2.breakBlock(this.worldObj, blockPos, blockState2);
            }
            else if (block2 instanceof ITileEntityProvider) {
                this.worldObj.removeTileEntity(blockPos);
            }
        }
        if (extendedBlockStorage.getBlockByExtId(n, y & 0xF, n2) != block) {
            return null;
        }
        if (false) {
            this.generateSkylightMap();
        }
        else {
            final int lightOpacity = block.getLightOpacity();
            final int lightOpacity2 = block2.getLightOpacity();
            if (lightOpacity > 0) {
                if (y >= n4) {
                    this.relightBlock(n, y + 1, n2);
                }
            }
            else if (y == n4 - 1) {
                this.relightBlock(n, y, n2);
            }
            if (lightOpacity != lightOpacity2 && (lightOpacity < lightOpacity2 || this.getLightFor(EnumSkyBlock.SKY, blockPos) > 0 || this.getLightFor(EnumSkyBlock.BLOCK, blockPos) > 0)) {
                this.propagateSkylightOcclusion(n, n2);
            }
        }
        if (block2 instanceof ITileEntityProvider) {
            final TileEntity func_177424_a = this.func_177424_a(blockPos, EnumCreateEntityType.CHECK);
            if (func_177424_a != null) {
                func_177424_a.updateContainingBlockInfo();
            }
        }
        if (!this.worldObj.isRemote && block2 != block) {
            block.onBlockAdded(this.worldObj, blockPos, blockState);
        }
        if (block instanceof ITileEntityProvider) {
            TileEntity tileEntity = this.func_177424_a(blockPos, EnumCreateEntityType.CHECK);
            if (tileEntity == null) {
                tileEntity = ((ITileEntityProvider)block).createNewTileEntity(this.worldObj, block.getMetaFromState(blockState));
                this.worldObj.setTileEntity(blockPos, tileEntity);
            }
            if (tileEntity != null) {
                tileEntity.updateContainingBlockInfo();
            }
        }
        this.isModified = true;
        return blockState2;
    }
    
    public int getLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos) {
        final int n = blockPos.getX() & 0xF;
        final int y = blockPos.getY();
        final int n2 = blockPos.getZ() & 0xF;
        final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[y >> 4];
        return (extendedBlockStorage == null) ? (this.canSeeSky(blockPos) ? enumSkyBlock.defaultLightValue : 0) : ((enumSkyBlock == EnumSkyBlock.SKY) ? (this.worldObj.provider.getHasNoSky() ? 0 : extendedBlockStorage.getExtSkylightValue(n, y & 0xF, n2)) : ((enumSkyBlock == EnumSkyBlock.BLOCK) ? extendedBlockStorage.getExtBlocklightValue(n, y & 0xF, n2) : enumSkyBlock.defaultLightValue));
    }
    
    public void setLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos, final int n) {
        final int n2 = blockPos.getX() & 0xF;
        final int y = blockPos.getY();
        final int n3 = blockPos.getZ() & 0xF;
        ExtendedBlockStorage extendedBlockStorage = this.storageArrays[y >> 4];
        if (extendedBlockStorage == null) {
            final ExtendedBlockStorage[] storageArrays = this.storageArrays;
            final int n4 = y >> 4;
            final ExtendedBlockStorage extendedBlockStorage2 = new ExtendedBlockStorage(y >> 4 << 4, !this.worldObj.provider.getHasNoSky());
            storageArrays[n4] = extendedBlockStorage2;
            extendedBlockStorage = extendedBlockStorage2;
            this.generateSkylightMap();
        }
        this.isModified = true;
        if (enumSkyBlock == EnumSkyBlock.SKY) {
            if (!this.worldObj.provider.getHasNoSky()) {
                extendedBlockStorage.setExtSkylightValue(n2, y & 0xF, n3, n);
            }
        }
        else if (enumSkyBlock == EnumSkyBlock.BLOCK) {
            extendedBlockStorage.setExtBlocklightValue(n2, y & 0xF, n3, n);
        }
    }
    
    public int setLight(final BlockPos blockPos, final int n) {
        final int n2 = blockPos.getX() & 0xF;
        final int y = blockPos.getY();
        final int n3 = blockPos.getZ() & 0xF;
        final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[y >> 4];
        if (extendedBlockStorage == null) {
            return (!this.worldObj.provider.getHasNoSky() && n < EnumSkyBlock.SKY.defaultLightValue) ? (EnumSkyBlock.SKY.defaultLightValue - n) : 0;
        }
        int n4 = (this.worldObj.provider.getHasNoSky() ? 0 : extendedBlockStorage.getExtSkylightValue(n2, y & 0xF, n3)) - n;
        final int extBlocklightValue = extendedBlockStorage.getExtBlocklightValue(n2, y & 0xF, n3);
        if (extBlocklightValue > n4) {
            n4 = extBlocklightValue;
        }
        return n4;
    }
    
    public void addEntity(final Entity entity) {
        this.hasEntities = true;
        final int floor_double = MathHelper.floor_double(entity.posX / 16.0);
        final int floor_double2 = MathHelper.floor_double(entity.posZ / 16.0);
        if (floor_double != this.xPosition || floor_double2 != this.zPosition) {
            Chunk.logger.warn("Wrong location! (" + floor_double + ", " + floor_double2 + ") should be (" + this.xPosition + ", " + this.zPosition + "), " + entity, entity);
            entity.setDead();
        }
        MathHelper.floor_double(entity.posY / 16.0);
        if (0 < 0) {}
        if (0 >= this.entityLists.length) {
            final int n = this.entityLists.length - 1;
        }
        entity.addedToChunk = true;
        entity.chunkCoordX = this.xPosition;
        entity.chunkCoordY = 0;
        entity.chunkCoordZ = this.zPosition;
        this.entityLists[0].add(entity);
    }
    
    public void removeEntity(final Entity entity) {
        this.removeEntityAtIndex(entity, entity.chunkCoordY);
    }
    
    public void removeEntityAtIndex(final Entity entity, int n) {
        if (0 < 0) {}
        if (0 >= this.entityLists.length) {
            n = this.entityLists.length - 1;
        }
        this.entityLists[0].remove(entity);
    }
    
    public boolean canSeeSky(final BlockPos blockPos) {
        return blockPos.getY() >= this.heightMap[(blockPos.getZ() & 0xF) << 4 | (blockPos.getX() & 0xF)];
    }
    
    private TileEntity createNewTileEntity(final BlockPos blockPos) {
        final Block block = this.getBlock(blockPos);
        return block.hasTileEntity() ? ((ITileEntityProvider)block).createNewTileEntity(this.worldObj, this.getBlockMetadata(blockPos)) : null;
    }
    
    public TileEntity func_177424_a(final BlockPos blockPos, final EnumCreateEntityType enumCreateEntityType) {
        TileEntity newTileEntity = this.chunkTileEntityMap.get(blockPos);
        if (newTileEntity == null) {
            if (enumCreateEntityType == EnumCreateEntityType.IMMEDIATE) {
                newTileEntity = this.createNewTileEntity(blockPos);
                this.worldObj.setTileEntity(blockPos, newTileEntity);
            }
            else if (enumCreateEntityType == EnumCreateEntityType.QUEUED) {
                this.field_177447_w.add(blockPos);
            }
        }
        else if (newTileEntity.isInvalid()) {
            this.chunkTileEntityMap.remove(blockPos);
            return null;
        }
        return newTileEntity;
    }
    
    public void addTileEntity(final TileEntity tileEntity) {
        this.addTileEntity(tileEntity.getPos(), tileEntity);
        if (this.isChunkLoaded) {
            this.worldObj.addTileEntity(tileEntity);
        }
    }
    
    public void addTileEntity(final BlockPos pos, final TileEntity tileEntity) {
        tileEntity.setWorldObj(this.worldObj);
        tileEntity.setPos(pos);
        if (this.getBlock(pos) instanceof ITileEntityProvider) {
            if (this.chunkTileEntityMap.containsKey(pos)) {
                this.chunkTileEntityMap.get(pos).invalidate();
            }
            tileEntity.validate();
            this.chunkTileEntityMap.put(pos, tileEntity);
        }
    }
    
    public void removeTileEntity(final BlockPos blockPos) {
        if (this.isChunkLoaded) {
            final TileEntity tileEntity = this.chunkTileEntityMap.remove(blockPos);
            if (tileEntity != null) {
                tileEntity.invalidate();
            }
        }
    }
    
    public void onChunkLoad() {
        this.isChunkLoaded = true;
        this.worldObj.addTileEntities(this.chunkTileEntityMap.values());
        while (0 < this.entityLists.length) {
            final Iterator iterator = this.entityLists[0].iterator();
            while (iterator.hasNext()) {
                iterator.next().onChunkLoad();
            }
            this.worldObj.loadEntities(this.entityLists[0]);
            int n = 0;
            ++n;
        }
    }
    
    public void onChunkUnload() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iconst_0       
        //     2: putfield        net/minecraft/world/chunk/Chunk.isChunkLoaded:Z
        //     5: aload_0        
        //     6: getfield        net/minecraft/world/chunk/Chunk.chunkTileEntityMap:Ljava/util/Map;
        //     9: invokeinterface java/util/Map.values:()Ljava/util/Collection;
        //    14: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //    19: astore_1       
        //    20: goto            41
        //    23: aload_1        
        //    24: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    29: checkcast       Lnet/minecraft/tileentity/TileEntity;
        //    32: astore_2       
        //    33: aload_0        
        //    34: getfield        net/minecraft/world/chunk/Chunk.worldObj:Lnet/minecraft/world/World;
        //    37: aload_2        
        //    38: invokevirtual   net/minecraft/world/World.markTileEntityForRemoval:(Lnet/minecraft/tileentity/TileEntity;)V
        //    41: aload_1        
        //    42: invokeinterface java/util/Iterator.hasNext:()Z
        //    47: ifne            23
        //    50: goto            69
        //    53: aload_0        
        //    54: getfield        net/minecraft/world/chunk/Chunk.worldObj:Lnet/minecraft/world/World;
        //    57: aload_0        
        //    58: getfield        net/minecraft/world/chunk/Chunk.entityLists:[Lnet/minecraft/util/ClassInheratanceMultiMap;
        //    61: iconst_0       
        //    62: aaload         
        //    63: invokevirtual   net/minecraft/world/World.unloadEntities:(Ljava/util/Collection;)V
        //    66: iinc            2, 1
        //    69: iconst_0       
        //    70: aload_0        
        //    71: getfield        net/minecraft/world/chunk/Chunk.entityLists:[Lnet/minecraft/util/ClassInheratanceMultiMap;
        //    74: arraylength    
        //    75: if_icmplt       53
        //    78: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void setChunkModified() {
        this.isModified = true;
    }
    
    public void func_177414_a(final Entity entity, final AxisAlignedBB axisAlignedBB, final List list, final Predicate predicate) {
        final int floor_double = MathHelper.floor_double((axisAlignedBB.minY - 2.0) / 16.0);
        final int floor_double2 = MathHelper.floor_double((axisAlignedBB.maxY + 2.0) / 16.0);
        final int clamp_int = MathHelper.clamp_int(floor_double, 0, this.entityLists.length - 1);
        for (int clamp_int2 = MathHelper.clamp_int(floor_double2, 0, this.entityLists.length - 1), i = clamp_int; i <= clamp_int2; ++i) {
            for (final Entity entity2 : this.entityLists[i]) {
                if (entity2 != entity && entity2.getEntityBoundingBox().intersectsWith(axisAlignedBB) && (predicate == null || predicate.apply(entity2))) {
                    list.add(entity2);
                    final Entity[] parts = entity2.getParts();
                    if (parts == null) {
                        continue;
                    }
                    while (0 < parts.length) {
                        final Entity entity3 = parts[0];
                        if (entity3 != entity && entity3.getEntityBoundingBox().intersectsWith(axisAlignedBB) && (predicate == null || predicate.apply(entity3))) {
                            list.add(entity3);
                        }
                        int n = 0;
                        ++n;
                    }
                }
            }
        }
    }
    
    public void func_177430_a(final Class clazz, final AxisAlignedBB axisAlignedBB, final List list, final Predicate predicate) {
        final int floor_double = MathHelper.floor_double((axisAlignedBB.minY - 2.0) / 16.0);
        final int floor_double2 = MathHelper.floor_double((axisAlignedBB.maxY + 2.0) / 16.0);
        final int clamp_int = MathHelper.clamp_int(floor_double, 0, this.entityLists.length - 1);
        for (int clamp_int2 = MathHelper.clamp_int(floor_double2, 0, this.entityLists.length - 1), i = clamp_int; i <= clamp_int2; ++i) {
            for (final Entity entity : this.entityLists[i].func_180215_b(clazz)) {
                if (entity.getEntityBoundingBox().intersectsWith(axisAlignedBB) && (predicate == null || predicate.apply(entity))) {
                    list.add(entity);
                }
            }
        }
    }
    
    public boolean needsSaving(final boolean b) {
        if (b) {
            if ((this.hasEntities && this.worldObj.getTotalWorldTime() != this.lastSaveTime) || this.isModified) {
                return true;
            }
        }
        else if (this.hasEntities && this.worldObj.getTotalWorldTime() >= this.lastSaveTime + 600L) {
            return true;
        }
        return this.isModified;
    }
    
    public Random getRandomWithSeed(final long n) {
        return new Random(this.worldObj.getSeed() + this.xPosition * this.xPosition * 4987142 + this.xPosition * 5947611 + this.zPosition * this.zPosition * 4392871L + this.zPosition * 389711 ^ n);
    }
    
    public boolean isEmpty() {
        return false;
    }
    
    public void populateChunk(final IChunkProvider chunkProvider, final IChunkProvider chunkProvider2, final int n, final int n2) {
        final boolean chunkExists = chunkProvider.chunkExists(n, n2 - 1);
        final boolean chunkExists2 = chunkProvider.chunkExists(n + 1, n2);
        final boolean chunkExists3 = chunkProvider.chunkExists(n, n2 + 1);
        final boolean chunkExists4 = chunkProvider.chunkExists(n - 1, n2);
        final boolean chunkExists5 = chunkProvider.chunkExists(n - 1, n2 - 1);
        final boolean chunkExists6 = chunkProvider.chunkExists(n + 1, n2 + 1);
        final boolean chunkExists7 = chunkProvider.chunkExists(n - 1, n2 + 1);
        final boolean chunkExists8 = chunkProvider.chunkExists(n + 1, n2 - 1);
        if (chunkExists2 && chunkExists3 && chunkExists6) {
            if (!this.isTerrainPopulated) {
                chunkProvider.populate(chunkProvider2, n, n2);
            }
            else {
                chunkProvider.func_177460_a(chunkProvider2, this, n, n2);
            }
        }
        if (chunkExists4 && chunkExists3 && chunkExists7) {
            final Chunk provideChunk = chunkProvider.provideChunk(n - 1, n2);
            if (!provideChunk.isTerrainPopulated) {
                chunkProvider.populate(chunkProvider2, n - 1, n2);
            }
            else {
                chunkProvider.func_177460_a(chunkProvider2, provideChunk, n - 1, n2);
            }
        }
        if (chunkExists && chunkExists2 && chunkExists8) {
            final Chunk provideChunk2 = chunkProvider.provideChunk(n, n2 - 1);
            if (!provideChunk2.isTerrainPopulated) {
                chunkProvider.populate(chunkProvider2, n, n2 - 1);
            }
            else {
                chunkProvider.func_177460_a(chunkProvider2, provideChunk2, n, n2 - 1);
            }
        }
        if (chunkExists5 && chunkExists && chunkExists4) {
            final Chunk provideChunk3 = chunkProvider.provideChunk(n - 1, n2 - 1);
            if (!provideChunk3.isTerrainPopulated) {
                chunkProvider.populate(chunkProvider2, n - 1, n2 - 1);
            }
            else {
                chunkProvider.func_177460_a(chunkProvider2, provideChunk3, n - 1, n2 - 1);
            }
        }
    }
    
    public BlockPos func_177440_h(final BlockPos blockPos) {
        final int n = (blockPos.getX() & 0xF) | (blockPos.getZ() & 0xF) << 4;
        if (new BlockPos(blockPos.getX(), this.precipitationHeightMap[n], blockPos.getZ()).getY() == -999) {
            BlockPos offsetDown = new BlockPos(blockPos.getX(), this.getTopFilledSegment() + 15, blockPos.getZ());
            while (offsetDown.getY() > 0 && -1 == -1) {
                final Material material = this.getBlock(offsetDown).getMaterial();
                if (!material.blocksMovement() && !material.isLiquid()) {
                    offsetDown = offsetDown.offsetDown();
                }
                else {
                    final int n2 = offsetDown.getY() + 1;
                }
            }
            this.precipitationHeightMap[n] = -1;
        }
        return new BlockPos(blockPos.getX(), this.precipitationHeightMap[n], blockPos.getZ());
    }
    
    public void func_150804_b(final boolean b) {
        if (this.isGapLightingUpdated && !this.worldObj.provider.getHasNoSky() && !b) {
            this.recheckGaps(this.worldObj.isRemote);
        }
        this.field_150815_m = true;
        if (!this.isLightPopulated && this.isTerrainPopulated) {
            this.func_150809_p();
        }
        while (!this.field_177447_w.isEmpty()) {
            final BlockPos blockPos = this.field_177447_w.poll();
            if (this.func_177424_a(blockPos, EnumCreateEntityType.CHECK) == null && this.getBlock(blockPos).hasTileEntity()) {
                this.worldObj.setTileEntity(blockPos, this.createNewTileEntity(blockPos));
                this.worldObj.markBlockRangeForRenderUpdate(blockPos, blockPos);
            }
        }
    }
    
    public boolean isPopulated() {
        return this.field_150815_m && this.isTerrainPopulated && this.isLightPopulated;
    }
    
    public ChunkCoordIntPair getChunkCoordIntPair() {
        return new ChunkCoordIntPair(this.xPosition, this.zPosition);
    }
    
    public boolean getAreLevelsEmpty(final int n, final int n2) {
        if (0 < 0) {}
        if (255 >= 256) {}
        while (0 <= 255) {
            final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[0];
            if (extendedBlockStorage != null && !extendedBlockStorage.isEmpty()) {
                return false;
            }
            final int n3;
            n3 += 16;
        }
        return true;
    }
    
    public void setStorageArrays(final ExtendedBlockStorage[] array) {
        if (this.storageArrays.length != array.length) {
            Chunk.logger.warn("Could not set level chunk sections, array length is " + array.length + " instead of " + this.storageArrays.length);
        }
        else {
            while (0 < this.storageArrays.length) {
                this.storageArrays[0] = array[0];
                int n = 0;
                ++n;
            }
        }
    }
    
    public void func_177439_a(final byte[] array, final int n, final boolean b) {
        final boolean b2 = !this.worldObj.provider.getHasNoSky();
        int n4 = 0;
        while (0 < this.storageArrays.length) {
            if ((n & 0x1) != 0x0) {
                if (this.storageArrays[0] == null) {
                    this.storageArrays[0] = new ExtendedBlockStorage(0, b2);
                }
                final char[] data = this.storageArrays[0].getData();
                while (0 < data.length) {
                    data[0] = (char)((array[1] & 0xFF) << 8 | (array[0] & 0xFF));
                    final int n2;
                    n2 += 2;
                    int n3 = 0;
                    ++n3;
                }
            }
            else if (b && this.storageArrays[0] != null) {
                this.storageArrays[0] = null;
            }
            ++n4;
        }
        while (0 < this.storageArrays.length) {
            if ((n & 0x1) != 0x0 && this.storageArrays[0] != null) {
                final NibbleArray blocklightArray = this.storageArrays[0].getBlocklightArray();
                System.arraycopy(array, 0, blocklightArray.getData(), 0, blocklightArray.getData().length);
                final int n2 = 0 + blocklightArray.getData().length;
            }
            ++n4;
        }
        if (b2) {
            while (0 < this.storageArrays.length) {
                if ((n & 0x1) != 0x0 && this.storageArrays[0] != null) {
                    final NibbleArray skylightArray = this.storageArrays[0].getSkylightArray();
                    System.arraycopy(array, 0, skylightArray.getData(), 0, skylightArray.getData().length);
                    final int n2 = 0 + skylightArray.getData().length;
                }
                ++n4;
            }
        }
        if (b) {
            System.arraycopy(array, 0, this.blockBiomeArray, 0, this.blockBiomeArray.length);
            final int n3 = 0 + this.blockBiomeArray.length;
        }
        while (0 < this.storageArrays.length) {
            if (this.storageArrays[0] != null && (n & 0x1) != 0x0) {
                this.storageArrays[0].removeInvalidBlocks();
            }
            ++n4;
        }
        this.isLightPopulated = true;
        this.isTerrainPopulated = true;
        this.generateHeightMap();
        final Iterator<TileEntity> iterator = this.chunkTileEntityMap.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().updateContainingBlockInfo();
        }
    }
    
    public BiomeGenBase getBiome(final BlockPos blockPos, final WorldChunkManager worldChunkManager) {
        final int n = blockPos.getX() & 0xF;
        final int n2 = blockPos.getZ() & 0xF;
        int biomeID = this.blockBiomeArray[n2 << 4 | n] & 0xFF;
        if (biomeID == 255) {
            biomeID = worldChunkManager.func_180300_a(blockPos, BiomeGenBase.plains).biomeID;
            this.blockBiomeArray[n2 << 4 | n] = (byte)(biomeID & 0xFF);
        }
        final BiomeGenBase biome = BiomeGenBase.getBiome(biomeID);
        return (biome == null) ? BiomeGenBase.plains : biome;
    }
    
    public byte[] getBiomeArray() {
        return this.blockBiomeArray;
    }
    
    public void setBiomeArray(final byte[] array) {
        if (this.blockBiomeArray.length != array.length) {
            Chunk.logger.warn("Could not set level chunk biomes, array length is " + array.length + " instead of " + this.blockBiomeArray.length);
        }
        else {
            while (0 < this.blockBiomeArray.length) {
                this.blockBiomeArray[0] = array[0];
                int n = 0;
                ++n;
            }
        }
    }
    
    public void resetRelightChecks() {
        this.queuedLightChecks = 0;
    }
    
    public void enqueueRelightChecks() {
        final BlockPos blockPos = new BlockPos(this.xPosition << 4, 0, this.zPosition << 4);
        while (0 < 8) {
            if (this.queuedLightChecks >= 4096) {
                return;
            }
            final int n = this.queuedLightChecks % 16;
            final int n2 = this.queuedLightChecks / 16 % 16;
            final int n3 = this.queuedLightChecks / 256;
            ++this.queuedLightChecks;
            while (0 < 16) {
                final BlockPos add = blockPos.add(n2, (n << 4) + 0, n3);
                final boolean b = !false || 0 == 15 || n2 == 0 || n2 == 15 || n3 == 0 || n3 == 15;
                if ((this.storageArrays[n] == null && b) || (this.storageArrays[n] != null && this.storageArrays[n].getBlockByExtId(n2, 0, n3).getMaterial() == Material.air)) {
                    final EnumFacing[] values = EnumFacing.values();
                    while (0 < values.length) {
                        final BlockPos offset = add.offset(values[0]);
                        if (this.worldObj.getBlockState(offset).getBlock().getLightValue() > 0) {
                            this.worldObj.checkLight(offset);
                        }
                        int n4 = 0;
                        ++n4;
                    }
                    this.worldObj.checkLight(add);
                }
                int n5 = 0;
                ++n5;
            }
            int n6 = 0;
            ++n6;
        }
    }
    
    public void func_150809_p() {
        this.isTerrainPopulated = true;
        this.isLightPopulated = true;
        final BlockPos blockPos = new BlockPos(this.xPosition << 4, 0, this.zPosition << 4);
        if (!this.worldObj.provider.getHasNoSky()) {
            if (this.worldObj.isAreaLoaded(blockPos.add(-1, 0, -1), blockPos.add(16, 63, 16))) {
            Label_0112:
                while (0 < 16) {
                    while (0 < 16) {
                        if (!this.func_150811_f(0, 0)) {
                            this.isLightPopulated = false;
                            break Label_0112;
                        }
                        int n = 0;
                        ++n;
                    }
                    int n2 = 0;
                    ++n2;
                }
                if (this.isLightPopulated) {
                    for (final EnumFacing enumFacing : EnumFacing.Plane.HORIZONTAL) {
                        this.worldObj.getChunkFromBlockCoords(blockPos.offset(enumFacing, (enumFacing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE) ? 16 : 1)).func_180700_a(enumFacing.getOpposite());
                    }
                    this.func_177441_y();
                }
            }
            else {
                this.isLightPopulated = false;
            }
        }
    }
    
    private void func_177441_y() {
        while (0 < this.updateSkylightColumns.length) {
            this.updateSkylightColumns[0] = true;
            int n = 0;
            ++n;
        }
        this.recheckGaps(false);
    }
    
    private void func_180700_a(final EnumFacing enumFacing) {
        if (this.isTerrainPopulated) {
            if (enumFacing == EnumFacing.EAST) {
                while (0 < 16) {
                    this.func_150811_f(15, 0);
                    int n = 0;
                    ++n;
                }
            }
            else if (enumFacing == EnumFacing.WEST) {
                while (0 < 16) {
                    this.func_150811_f(0, 0);
                    int n = 0;
                    ++n;
                }
            }
            else if (enumFacing == EnumFacing.SOUTH) {
                while (0 < 16) {
                    this.func_150811_f(0, 15);
                    int n = 0;
                    ++n;
                }
            }
            else if (enumFacing == EnumFacing.NORTH) {
                while (0 < 16) {
                    this.func_150811_f(0, 0);
                    int n = 0;
                    ++n;
                }
            }
        }
    }
    
    private boolean func_150811_f(final int n, final int n2) {
        final BlockPos blockPos = new BlockPos(this.xPosition << 4, 0, this.zPosition << 4);
        int i = this.getTopFilledSegment() + 16 - 1;
        while (true) {
            if (i <= 63) {
                if (i <= 0) {
                    break;
                }
                if (true) {
                    break;
                }
            }
            final BlockPos add = blockPos.add(n, i, n2);
            final int blockLightOpacity = this.getBlockLightOpacity(add);
            if (blockLightOpacity != 255 || i < 63) {}
            if (true || blockLightOpacity <= 0) {
                if (true && blockLightOpacity == 0 && !this.worldObj.checkLight(add)) {
                    return false;
                }
            }
            --i;
        }
        while (i > 0) {
            final BlockPos add2 = blockPos.add(n, i, n2);
            if (this.getBlock(add2).getLightValue() > 0) {
                this.worldObj.checkLight(add2);
            }
            --i;
        }
        return true;
    }
    
    public boolean isLoaded() {
        return this.isChunkLoaded;
    }
    
    public void func_177417_c(final boolean isChunkLoaded) {
        this.isChunkLoaded = isChunkLoaded;
    }
    
    public World getWorld() {
        return this.worldObj;
    }
    
    public int[] getHeightMap() {
        return this.heightMap;
    }
    
    public void setHeightMap(final int[] array) {
        if (this.heightMap.length != array.length) {
            Chunk.logger.warn("Could not set level chunk heightmap, array length is " + array.length + " instead of " + this.heightMap.length);
        }
        else {
            while (0 < this.heightMap.length) {
                this.heightMap[0] = array[0];
                int n = 0;
                ++n;
            }
        }
    }
    
    public Map getTileEntityMap() {
        return this.chunkTileEntityMap;
    }
    
    public ClassInheratanceMultiMap[] getEntityLists() {
        return this.entityLists;
    }
    
    public boolean isTerrainPopulated() {
        return this.isTerrainPopulated;
    }
    
    public void setTerrainPopulated(final boolean isTerrainPopulated) {
        this.isTerrainPopulated = isTerrainPopulated;
    }
    
    public boolean isLightPopulated() {
        return this.isLightPopulated;
    }
    
    public void setLightPopulated(final boolean isLightPopulated) {
        this.isLightPopulated = isLightPopulated;
    }
    
    public void setModified(final boolean isModified) {
        this.isModified = isModified;
    }
    
    public void setHasEntities(final boolean hasEntities) {
        this.hasEntities = hasEntities;
    }
    
    public void setLastSaveTime(final long lastSaveTime) {
        this.lastSaveTime = lastSaveTime;
    }
    
    public int getLowestHeight() {
        return this.heightMapMinimum;
    }
    
    public long getInhabitedTime() {
        return this.inhabitedTime;
    }
    
    public void setInhabitedTime(final long inhabitedTime) {
        this.inhabitedTime = inhabitedTime;
    }
    
    public enum EnumCreateEntityType
    {
        IMMEDIATE("IMMEDIATE", 0, "IMMEDIATE", 0), 
        QUEUED("QUEUED", 1, "QUEUED", 1), 
        CHECK("CHECK", 2, "CHECK", 2);
        
        private static final EnumCreateEntityType[] $VALUES;
        private static final String __OBFID;
        private static final EnumCreateEntityType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002009";
            ENUM$VALUES = new EnumCreateEntityType[] { EnumCreateEntityType.IMMEDIATE, EnumCreateEntityType.QUEUED, EnumCreateEntityType.CHECK };
            $VALUES = new EnumCreateEntityType[] { EnumCreateEntityType.IMMEDIATE, EnumCreateEntityType.QUEUED, EnumCreateEntityType.CHECK };
        }
        
        private EnumCreateEntityType(final String s, final int n, final String s2, final int n2) {
        }
    }
}
