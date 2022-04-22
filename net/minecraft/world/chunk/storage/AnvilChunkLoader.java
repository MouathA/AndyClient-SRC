package net.minecraft.world.chunk.storage;

import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.storage.*;
import java.io.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;

public class AnvilChunkLoader implements IChunkLoader, IThreadedFileIO
{
    private static final Logger logger;
    private List chunksToRemove;
    private Set pendingAnvilChunksCoordinates;
    private Object syncLockObject;
    private final File chunkSaveLocation;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000384";
        logger = LogManager.getLogger();
    }
    
    public AnvilChunkLoader(final File chunkSaveLocation) {
        this.chunksToRemove = Lists.newArrayList();
        this.pendingAnvilChunksCoordinates = Sets.newHashSet();
        this.syncLockObject = new Object();
        this.chunkSaveLocation = chunkSaveLocation;
    }
    
    @Override
    public Chunk loadChunk(final World world, final int n, final int n2) throws IOException {
        NBTTagCompound nbtTagCompound = null;
        final ChunkCoordIntPair chunkCoordIntPair = new ChunkCoordIntPair(n, n2);
        final Object syncLockObject = this.syncLockObject;
        // monitorenter(syncLockObject2 = this.syncLockObject)
        if (this.pendingAnvilChunksCoordinates.contains(chunkCoordIntPair)) {
            while (0 < this.chunksToRemove.size()) {
                if (this.chunksToRemove.get(0).chunkCoordinate.equals(chunkCoordIntPair)) {
                    nbtTagCompound = this.chunksToRemove.get(0).nbtTags;
                    break;
                }
                int n3 = 0;
                ++n3;
            }
        }
        // monitorexit(syncLockObject2)
        if (nbtTagCompound == null) {
            final DataInputStream chunkInputStream = RegionFileCache.getChunkInputStream(this.chunkSaveLocation, n, n2);
            if (chunkInputStream == null) {
                return null;
            }
            nbtTagCompound = CompressedStreamTools.read(chunkInputStream);
        }
        return this.checkedReadChunkFromNBT(world, n, n2, nbtTagCompound);
    }
    
    protected Chunk checkedReadChunkFromNBT(final World world, final int n, final int n2, final NBTTagCompound nbtTagCompound) {
        if (!nbtTagCompound.hasKey("Level", 10)) {
            AnvilChunkLoader.logger.error("Chunk file at " + n + "," + n2 + " is missing level data, skipping");
            return null;
        }
        if (!nbtTagCompound.getCompoundTag("Level").hasKey("Sections", 9)) {
            AnvilChunkLoader.logger.error("Chunk file at " + n + "," + n2 + " is missing block data, skipping");
            return null;
        }
        Chunk chunk = this.readChunkFromNBT(world, nbtTagCompound.getCompoundTag("Level"));
        if (!chunk.isAtLocation(n, n2)) {
            AnvilChunkLoader.logger.error("Chunk file at " + n + "," + n2 + " is in the wrong location; relocating. (Expected " + n + ", " + n2 + ", got " + chunk.xPosition + ", " + chunk.zPosition + ")");
            nbtTagCompound.setInteger("xPos", n);
            nbtTagCompound.setInteger("zPos", n2);
            chunk = this.readChunkFromNBT(world, nbtTagCompound.getCompoundTag("Level"));
        }
        return chunk;
    }
    
    @Override
    public void saveChunk(final World world, final Chunk chunk) throws MinecraftException, IOException {
        world.checkSessionLock();
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        nbtTagCompound.setTag("Level", nbtTagCompound2);
        this.writeChunkToNBT(chunk, world, nbtTagCompound2);
        this.addChunkToPending(chunk.getChunkCoordIntPair(), nbtTagCompound);
    }
    
    protected void addChunkToPending(final ChunkCoordIntPair chunkCoordIntPair, final NBTTagCompound nbtTagCompound) {
        final Object syncLockObject = this.syncLockObject;
        // monitorenter(syncLockObject2 = this.syncLockObject)
        if (this.pendingAnvilChunksCoordinates.contains(chunkCoordIntPair)) {
            while (0 < this.chunksToRemove.size()) {
                if (this.chunksToRemove.get(0).chunkCoordinate.equals(chunkCoordIntPair)) {
                    this.chunksToRemove.set(0, new PendingChunk(chunkCoordIntPair, nbtTagCompound));
                    // monitorexit(syncLockObject2)
                    return;
                }
                int n = 0;
                ++n;
            }
        }
        this.chunksToRemove.add(new PendingChunk(chunkCoordIntPair, nbtTagCompound));
        this.pendingAnvilChunksCoordinates.add(chunkCoordIntPair);
        ThreadedFileIOBase.func_178779_a().queueIO(this);
    }
    // monitorexit(syncLockObject2)
    
    private void writeChunkNBTTags(final PendingChunk pendingChunk) throws IOException {
        final DataOutputStream chunkOutputStream = RegionFileCache.getChunkOutputStream(this.chunkSaveLocation, pendingChunk.chunkCoordinate.chunkXPos, pendingChunk.chunkCoordinate.chunkZPos);
        CompressedStreamTools.write(pendingChunk.nbtTags, chunkOutputStream);
        chunkOutputStream.close();
    }
    
    @Override
    public void saveExtraChunkData(final World world, final Chunk chunk) {
    }
    
    @Override
    public void chunkTick() {
    }
    
    @Override
    public void saveExtraData() {
        while (this == 0) {}
    }
    
    private void writeChunkToNBT(final Chunk chunk, final World world, final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte("V", (byte)1);
        nbtTagCompound.setInteger("xPos", chunk.xPosition);
        nbtTagCompound.setInteger("zPos", chunk.zPosition);
        nbtTagCompound.setLong("LastUpdate", world.getTotalWorldTime());
        nbtTagCompound.setIntArray("HeightMap", chunk.getHeightMap());
        nbtTagCompound.setBoolean("TerrainPopulated", chunk.isTerrainPopulated());
        nbtTagCompound.setBoolean("LightPopulated", chunk.isLightPopulated());
        nbtTagCompound.setLong("InhabitedTime", chunk.getInhabitedTime());
        final ExtendedBlockStorage[] blockStorageArray = chunk.getBlockStorageArray();
        final NBTTagList list = new NBTTagList();
        final boolean b = !world.provider.getHasNoSky();
        int length = blockStorageArray.length;
        nbtTagCompound.setTag("Sections", list);
        nbtTagCompound.setByteArray("Biomes", chunk.getBiomeArray());
        chunk.setHasEntities(false);
        final NBTTagList list2 = new NBTTagList();
        while (0 < chunk.getEntityLists().length) {
            for (final Entity entity : chunk.getEntityLists()[0]) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                if (entity.writeToNBTOptional(nbtTagCompound2)) {
                    chunk.setHasEntities(true);
                    list2.appendTag(nbtTagCompound2);
                }
            }
            ++length;
        }
        nbtTagCompound.setTag("Entities", list2);
        final NBTTagList list3 = new NBTTagList();
        for (final TileEntity tileEntity : chunk.getTileEntityMap().values()) {
            final NBTTagCompound nbtTagCompound3 = new NBTTagCompound();
            tileEntity.writeToNBT(nbtTagCompound3);
            list3.appendTag(nbtTagCompound3);
        }
        nbtTagCompound.setTag("TileEntities", list3);
        final List pendingBlockUpdates = world.getPendingBlockUpdates(chunk, false);
        if (pendingBlockUpdates != null) {
            final long totalWorldTime = world.getTotalWorldTime();
            final NBTTagList list4 = new NBTTagList();
            for (final NextTickListEntry nextTickListEntry : pendingBlockUpdates) {
                final NBTTagCompound nbtTagCompound4 = new NBTTagCompound();
                final ResourceLocation resourceLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(nextTickListEntry.func_151351_a());
                nbtTagCompound4.setString("i", (resourceLocation == null) ? "" : resourceLocation.toString());
                nbtTagCompound4.setInteger("x", nextTickListEntry.field_180282_a.getX());
                nbtTagCompound4.setInteger("y", nextTickListEntry.field_180282_a.getY());
                nbtTagCompound4.setInteger("z", nextTickListEntry.field_180282_a.getZ());
                nbtTagCompound4.setInteger("t", (int)(nextTickListEntry.scheduledTime - totalWorldTime));
                nbtTagCompound4.setInteger("p", nextTickListEntry.priority);
                list4.appendTag(nbtTagCompound4);
            }
            nbtTagCompound.setTag("TileTicks", list4);
        }
    }
    
    private Chunk readChunkFromNBT(final World p0, final NBTTagCompound p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "xPos"
        //     3: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //     6: istore_3       
        //     7: aload_2        
        //     8: ldc             "zPos"
        //    10: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    13: istore          4
        //    15: new             Lnet/minecraft/world/chunk/Chunk;
        //    18: dup            
        //    19: aload_1        
        //    20: iload_3        
        //    21: iload           4
        //    23: invokespecial   net/minecraft/world/chunk/Chunk.<init>:(Lnet/minecraft/world/World;II)V
        //    26: astore          5
        //    28: aload           5
        //    30: aload_2        
        //    31: ldc_w           "HeightMap"
        //    34: invokevirtual   net/minecraft/nbt/NBTTagCompound.getIntArray:(Ljava/lang/String;)[I
        //    37: invokevirtual   net/minecraft/world/chunk/Chunk.setHeightMap:([I)V
        //    40: aload           5
        //    42: aload_2        
        //    43: ldc_w           "TerrainPopulated"
        //    46: invokevirtual   net/minecraft/nbt/NBTTagCompound.getBoolean:(Ljava/lang/String;)Z
        //    49: invokevirtual   net/minecraft/world/chunk/Chunk.setTerrainPopulated:(Z)V
        //    52: aload           5
        //    54: aload_2        
        //    55: ldc_w           "LightPopulated"
        //    58: invokevirtual   net/minecraft/nbt/NBTTagCompound.getBoolean:(Ljava/lang/String;)Z
        //    61: invokevirtual   net/minecraft/world/chunk/Chunk.setLightPopulated:(Z)V
        //    64: aload           5
        //    66: aload_2        
        //    67: ldc_w           "InhabitedTime"
        //    70: invokevirtual   net/minecraft/nbt/NBTTagCompound.getLong:(Ljava/lang/String;)J
        //    73: invokevirtual   net/minecraft/world/chunk/Chunk.setInhabitedTime:(J)V
        //    76: aload_2        
        //    77: ldc             "Sections"
        //    79: bipush          10
        //    81: invokevirtual   net/minecraft/nbt/NBTTagCompound.getTagList:(Ljava/lang/String;I)Lnet/minecraft/nbt/NBTTagList;
        //    84: astore          6
        //    86: bipush          16
        //    88: anewarray       Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //    91: astore          8
        //    93: aload_1        
        //    94: getfield        net/minecraft/world/World.provider:Lnet/minecraft/world/WorldProvider;
        //    97: invokevirtual   net/minecraft/world/WorldProvider.getHasNoSky:()Z
        //   100: ifeq            107
        //   103: iconst_0       
        //   104: goto            108
        //   107: iconst_1       
        //   108: istore          9
        //   110: goto            339
        //   113: aload           6
        //   115: iconst_0       
        //   116: invokevirtual   net/minecraft/nbt/NBTTagList.getCompoundTagAt:(I)Lnet/minecraft/nbt/NBTTagCompound;
        //   119: astore          11
        //   121: aload           11
        //   123: ldc_w           "Y"
        //   126: invokevirtual   net/minecraft/nbt/NBTTagCompound.getByte:(Ljava/lang/String;)B
        //   129: istore          12
        //   131: new             Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //   134: dup            
        //   135: iconst_0       
        //   136: iload           9
        //   138: invokespecial   net/minecraft/world/chunk/storage/ExtendedBlockStorage.<init>:(IZ)V
        //   141: astore          13
        //   143: aload           11
        //   145: ldc_w           "Blocks"
        //   148: invokevirtual   net/minecraft/nbt/NBTTagCompound.getByteArray:(Ljava/lang/String;)[B
        //   151: astore          14
        //   153: new             Lnet/minecraft/world/chunk/NibbleArray;
        //   156: dup            
        //   157: aload           11
        //   159: ldc_w           "Data"
        //   162: invokevirtual   net/minecraft/nbt/NBTTagCompound.getByteArray:(Ljava/lang/String;)[B
        //   165: invokespecial   net/minecraft/world/chunk/NibbleArray.<init>:([B)V
        //   168: astore          15
        //   170: aload           11
        //   172: ldc_w           "Add"
        //   175: bipush          7
        //   177: invokevirtual   net/minecraft/nbt/NBTTagCompound.hasKey:(Ljava/lang/String;I)Z
        //   180: ifeq            201
        //   183: new             Lnet/minecraft/world/chunk/NibbleArray;
        //   186: dup            
        //   187: aload           11
        //   189: ldc_w           "Add"
        //   192: invokevirtual   net/minecraft/nbt/NBTTagCompound.getByteArray:(Ljava/lang/String;)[B
        //   195: invokespecial   net/minecraft/world/chunk/NibbleArray.<init>:([B)V
        //   198: goto            202
        //   201: aconst_null    
        //   202: astore          16
        //   204: aload           14
        //   206: arraylength    
        //   207: newarray        C
        //   209: astore          17
        //   211: goto            266
        //   214: aload           16
        //   216: ifnull          230
        //   219: aload           16
        //   221: iconst_0       
        //   222: iconst_0       
        //   223: iconst_0       
        //   224: invokevirtual   net/minecraft/world/chunk/NibbleArray.get:(III)I
        //   227: goto            231
        //   230: iconst_0       
        //   231: istore          22
        //   233: aload           17
        //   235: iconst_0       
        //   236: iload           22
        //   238: bipush          12
        //   240: ishl           
        //   241: aload           14
        //   243: iconst_0       
        //   244: baload         
        //   245: sipush          255
        //   248: iand           
        //   249: iconst_4       
        //   250: ishl           
        //   251: ior            
        //   252: aload           15
        //   254: iconst_0       
        //   255: iconst_0       
        //   256: iconst_0       
        //   257: invokevirtual   net/minecraft/world/chunk/NibbleArray.get:(III)I
        //   260: ior            
        //   261: i2c            
        //   262: castore        
        //   263: iinc            18, 1
        //   266: iconst_0       
        //   267: aload           17
        //   269: arraylength    
        //   270: if_icmplt       214
        //   273: aload           13
        //   275: aload           17
        //   277: invokevirtual   net/minecraft/world/chunk/storage/ExtendedBlockStorage.setData:([C)V
        //   280: aload           13
        //   282: new             Lnet/minecraft/world/chunk/NibbleArray;
        //   285: dup            
        //   286: aload           11
        //   288: ldc_w           "BlockLight"
        //   291: invokevirtual   net/minecraft/nbt/NBTTagCompound.getByteArray:(Ljava/lang/String;)[B
        //   294: invokespecial   net/minecraft/world/chunk/NibbleArray.<init>:([B)V
        //   297: invokevirtual   net/minecraft/world/chunk/storage/ExtendedBlockStorage.setBlocklightArray:(Lnet/minecraft/world/chunk/NibbleArray;)V
        //   300: iload           9
        //   302: ifeq            325
        //   305: aload           13
        //   307: new             Lnet/minecraft/world/chunk/NibbleArray;
        //   310: dup            
        //   311: aload           11
        //   313: ldc_w           "SkyLight"
        //   316: invokevirtual   net/minecraft/nbt/NBTTagCompound.getByteArray:(Ljava/lang/String;)[B
        //   319: invokespecial   net/minecraft/world/chunk/NibbleArray.<init>:([B)V
        //   322: invokevirtual   net/minecraft/world/chunk/storage/ExtendedBlockStorage.setSkylightArray:(Lnet/minecraft/world/chunk/NibbleArray;)V
        //   325: aload           13
        //   327: invokevirtual   net/minecraft/world/chunk/storage/ExtendedBlockStorage.removeInvalidBlocks:()V
        //   330: aload           8
        //   332: iconst_0       
        //   333: aload           13
        //   335: aastore        
        //   336: iinc            10, 1
        //   339: iconst_0       
        //   340: aload           6
        //   342: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //   345: if_icmplt       113
        //   348: aload           5
        //   350: aload           8
        //   352: invokevirtual   net/minecraft/world/chunk/Chunk.setStorageArrays:([Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;)V
        //   355: aload_2        
        //   356: ldc_w           "Biomes"
        //   359: bipush          7
        //   361: invokevirtual   net/minecraft/nbt/NBTTagCompound.hasKey:(Ljava/lang/String;I)Z
        //   364: ifeq            379
        //   367: aload           5
        //   369: aload_2        
        //   370: ldc_w           "Biomes"
        //   373: invokevirtual   net/minecraft/nbt/NBTTagCompound.getByteArray:(Ljava/lang/String;)[B
        //   376: invokevirtual   net/minecraft/world/chunk/Chunk.setBiomeArray:([B)V
        //   379: aload_2        
        //   380: ldc_w           "Entities"
        //   383: bipush          10
        //   385: invokevirtual   net/minecraft/nbt/NBTTagCompound.getTagList:(Ljava/lang/String;I)Lnet/minecraft/nbt/NBTTagList;
        //   388: astore          10
        //   390: aload           10
        //   392: ifnull          515
        //   395: goto            506
        //   398: aload           10
        //   400: iconst_0       
        //   401: invokevirtual   net/minecraft/nbt/NBTTagList.getCompoundTagAt:(I)Lnet/minecraft/nbt/NBTTagCompound;
        //   404: astore          12
        //   406: aload           12
        //   408: aload_1        
        //   409: invokestatic    net/minecraft/entity/EntityList.createEntityFromNBT:(Lnet/minecraft/nbt/NBTTagCompound;Lnet/minecraft/world/World;)Lnet/minecraft/entity/Entity;
        //   412: astore          13
        //   414: aload           5
        //   416: iconst_1       
        //   417: invokevirtual   net/minecraft/world/chunk/Chunk.setHasEntities:(Z)V
        //   420: aload           13
        //   422: ifnull          503
        //   425: aload           5
        //   427: aload           13
        //   429: invokevirtual   net/minecraft/world/chunk/Chunk.addEntity:(Lnet/minecraft/entity/Entity;)V
        //   432: aload           13
        //   434: astore          14
        //   436: aload           12
        //   438: astore          15
        //   440: goto            490
        //   443: aload           15
        //   445: ldc_w           "Riding"
        //   448: invokevirtual   net/minecraft/nbt/NBTTagCompound.getCompoundTag:(Ljava/lang/String;)Lnet/minecraft/nbt/NBTTagCompound;
        //   451: aload_1        
        //   452: invokestatic    net/minecraft/entity/EntityList.createEntityFromNBT:(Lnet/minecraft/nbt/NBTTagCompound;Lnet/minecraft/world/World;)Lnet/minecraft/entity/Entity;
        //   455: astore          16
        //   457: aload           16
        //   459: ifnull          476
        //   462: aload           5
        //   464: aload           16
        //   466: invokevirtual   net/minecraft/world/chunk/Chunk.addEntity:(Lnet/minecraft/entity/Entity;)V
        //   469: aload           14
        //   471: aload           16
        //   473: invokevirtual   net/minecraft/entity/Entity.mountEntity:(Lnet/minecraft/entity/Entity;)V
        //   476: aload           16
        //   478: astore          14
        //   480: aload           15
        //   482: ldc_w           "Riding"
        //   485: invokevirtual   net/minecraft/nbt/NBTTagCompound.getCompoundTag:(Ljava/lang/String;)Lnet/minecraft/nbt/NBTTagCompound;
        //   488: astore          15
        //   490: aload           15
        //   492: ldc_w           "Riding"
        //   495: bipush          10
        //   497: invokevirtual   net/minecraft/nbt/NBTTagCompound.hasKey:(Ljava/lang/String;I)Z
        //   500: ifne            443
        //   503: iinc            11, 1
        //   506: iconst_0       
        //   507: aload           10
        //   509: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //   512: if_icmplt       398
        //   515: aload_2        
        //   516: ldc_w           "TileEntities"
        //   519: bipush          10
        //   521: invokevirtual   net/minecraft/nbt/NBTTagCompound.getTagList:(Ljava/lang/String;I)Lnet/minecraft/nbt/NBTTagList;
        //   524: astore          11
        //   526: aload           11
        //   528: ifnull          573
        //   531: goto            564
        //   534: aload           11
        //   536: iconst_0       
        //   537: invokevirtual   net/minecraft/nbt/NBTTagList.getCompoundTagAt:(I)Lnet/minecraft/nbt/NBTTagCompound;
        //   540: astore          13
        //   542: aload           13
        //   544: invokestatic    net/minecraft/tileentity/TileEntity.createAndLoadEntity:(Lnet/minecraft/nbt/NBTTagCompound;)Lnet/minecraft/tileentity/TileEntity;
        //   547: astore          14
        //   549: aload           14
        //   551: ifnull          561
        //   554: aload           5
        //   556: aload           14
        //   558: invokevirtual   net/minecraft/world/chunk/Chunk.addTileEntity:(Lnet/minecraft/tileentity/TileEntity;)V
        //   561: iinc            12, 1
        //   564: iconst_0       
        //   565: aload           11
        //   567: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //   570: if_icmplt       534
        //   573: aload_2        
        //   574: ldc_w           "TileTicks"
        //   577: bipush          9
        //   579: invokevirtual   net/minecraft/nbt/NBTTagCompound.hasKey:(Ljava/lang/String;I)Z
        //   582: ifeq            719
        //   585: aload_2        
        //   586: ldc_w           "TileTicks"
        //   589: bipush          10
        //   591: invokevirtual   net/minecraft/nbt/NBTTagCompound.getTagList:(Ljava/lang/String;I)Lnet/minecraft/nbt/NBTTagList;
        //   594: astore          12
        //   596: aload           12
        //   598: ifnull          719
        //   601: goto            710
        //   604: aload           12
        //   606: iconst_0       
        //   607: invokevirtual   net/minecraft/nbt/NBTTagList.getCompoundTagAt:(I)Lnet/minecraft/nbt/NBTTagCompound;
        //   610: astore          14
        //   612: aload           14
        //   614: ldc_w           "i"
        //   617: bipush          8
        //   619: invokevirtual   net/minecraft/nbt/NBTTagCompound.hasKey:(Ljava/lang/String;I)Z
        //   622: ifeq            641
        //   625: aload           14
        //   627: ldc_w           "i"
        //   630: invokevirtual   net/minecraft/nbt/NBTTagCompound.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   633: invokestatic    net/minecraft/block/Block.getBlockFromName:(Ljava/lang/String;)Lnet/minecraft/block/Block;
        //   636: astore          15
        //   638: goto            654
        //   641: aload           14
        //   643: ldc_w           "i"
        //   646: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   649: invokestatic    net/minecraft/block/Block.getBlockById:(I)Lnet/minecraft/block/Block;
        //   652: astore          15
        //   654: aload_1        
        //   655: new             Lnet/minecraft/util/BlockPos;
        //   658: dup            
        //   659: aload           14
        //   661: ldc_w           "x"
        //   664: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   667: aload           14
        //   669: ldc_w           "y"
        //   672: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   675: aload           14
        //   677: ldc_w           "z"
        //   680: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   683: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   686: aload           15
        //   688: aload           14
        //   690: ldc_w           "t"
        //   693: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   696: aload           14
        //   698: ldc_w           "p"
        //   701: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   704: invokevirtual   net/minecraft/world/World.func_180497_b:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/Block;II)V
        //   707: iinc            13, 1
        //   710: iconst_0       
        //   711: aload           12
        //   713: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //   716: if_icmplt       604
        //   719: aload           5
        //   721: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static class PendingChunk
    {
        public final ChunkCoordIntPair chunkCoordinate;
        public final NBTTagCompound nbtTags;
        private static final String __OBFID;
        
        public PendingChunk(final ChunkCoordIntPair chunkCoordinate, final NBTTagCompound nbtTags) {
            this.chunkCoordinate = chunkCoordinate;
            this.nbtTags = nbtTags;
        }
        
        static {
            __OBFID = "CL_00000385";
        }
    }
}
