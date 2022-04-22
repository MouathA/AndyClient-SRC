package wdl;

import org.apache.logging.log4j.*;
import net.minecraft.world.storage.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.*;
import wdl.chan.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.chunk.storage.*;
import net.minecraft.nbt.*;
import java.io.*;
import net.minecraft.block.*;
import wdl.api.*;

public class WDLChunkLoader extends AnvilChunkLoader
{
    private static Logger logger;
    private final File chunkSaveLocation;
    
    static {
        WDLChunkLoader.logger = LogManager.getLogger();
    }
    
    public static WDLChunkLoader create(final SaveHandler saveHandler, final WorldProvider worldProvider) {
        return new WDLChunkLoader(getWorldSaveFolder(saveHandler, worldProvider));
    }
    
    private static File getWorldSaveFolder(final SaveHandler saveHandler, final WorldProvider worldProvider) {
        final File worldDirectory = saveHandler.getWorldDirectory();
        final String s = (String)worldProvider.getClass().getMethod("getSaveFolder", (Class<?>[])new Class[0]).invoke(worldProvider, new Object[0]);
        if (s != null) {
            final File file = new File(worldDirectory, s);
            file.mkdirs();
            return file;
        }
        return worldDirectory;
    }
    
    public WDLChunkLoader(final File chunkSaveLocation) {
        super(chunkSaveLocation);
        this.chunkSaveLocation = chunkSaveLocation;
    }
    
    @Override
    public void saveChunk(final World world, final Chunk chunk) throws MinecraftException, IOException {
        world.checkSessionLock();
        final NBTTagCompound writeChunkToNBT = this.writeChunkToNBT(chunk, world);
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setTag("Level", writeChunkToNBT);
        this.addChunkToPending(chunk.getChunkCoordIntPair(), nbtTagCompound);
    }
    
    private NBTTagCompound writeChunkToNBT(final Chunk chunk, final World world) {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
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
        ExtendedBlockStorage[] array;
        while (0 < (array = blockStorageArray).length) {
            final ExtendedBlockStorage extendedBlockStorage = array[0];
            if (extendedBlockStorage != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte("Y", (byte)(extendedBlockStorage.getYLocation() >> 4 & 0xFF));
                final byte[] array2 = new byte[extendedBlockStorage.getData().length];
                final NibbleArray nibbleArray = new NibbleArray();
                NibbleArray nibbleArray2 = null;
                while (0 < extendedBlockStorage.getData().length) {
                    final char c = extendedBlockStorage.getData()[0];
                    if (c >> 12 != 0) {
                        if (nibbleArray2 == null) {
                            nibbleArray2 = new NibbleArray();
                        }
                        nibbleArray2.set(0, 0, 0, c >> 12);
                    }
                    array2[0] = (byte)(c >> 4 & 0xFF);
                    nibbleArray.set(0, 0, 0, c & '\u000f');
                    int n = 0;
                    ++n;
                }
                nbtTagCompound2.setByteArray("Blocks", array2);
                nbtTagCompound2.setByteArray("Data", nibbleArray.getData());
                if (nibbleArray2 != null) {
                    nbtTagCompound2.setByteArray("Add", nibbleArray2.getData());
                }
                nbtTagCompound2.setByteArray("BlockLight", extendedBlockStorage.getBlocklightArray().getData());
                if (b) {
                    nbtTagCompound2.setByteArray("SkyLight", extendedBlockStorage.getSkylightArray().getData());
                }
                else {
                    nbtTagCompound2.setByteArray("SkyLight", new byte[extendedBlockStorage.getBlocklightArray().getData().length]);
                }
                list.appendTag(nbtTagCompound2);
            }
            int n2 = 0;
            ++n2;
        }
        nbtTagCompound.setTag("Sections", list);
        nbtTagCompound.setByteArray("Biomes", chunk.getBiomeArray());
        chunk.setHasEntities(false);
        nbtTagCompound.setTag("Entities", this.getEntityList(chunk));
        nbtTagCompound.setTag("TileEntities", this.getTileEntityList(chunk));
        final List pendingBlockUpdates = world.getPendingBlockUpdates(chunk, false);
        if (pendingBlockUpdates != null) {
            final long totalWorldTime = world.getTotalWorldTime();
            final NBTTagList list2 = new NBTTagList();
            for (final NextTickListEntry nextTickListEntry : pendingBlockUpdates) {
                final NBTTagCompound nbtTagCompound3 = new NBTTagCompound();
                final ResourceLocation resourceLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(nextTickListEntry.func_151351_a());
                nbtTagCompound3.setString("i", (resourceLocation == null) ? "" : resourceLocation.toString());
                nbtTagCompound3.setInteger("x", nextTickListEntry.field_180282_a.getX());
                nbtTagCompound3.setInteger("y", nextTickListEntry.field_180282_a.getY());
                nbtTagCompound3.setInteger("z", nextTickListEntry.field_180282_a.getZ());
                nbtTagCompound3.setInteger("t", (int)(nextTickListEntry.scheduledTime - totalWorldTime));
                nbtTagCompound3.setInteger("p", nextTickListEntry.priority);
                list2.appendTag(nbtTagCompound3);
            }
            nbtTagCompound.setTag("TileTicks", list2);
        }
        return nbtTagCompound;
    }
    
    public NBTTagList getEntityList(final Chunk chunk) {
        final NBTTagList list = new NBTTagList();
        if (!WDLPluginChannels.canSaveEntities(chunk)) {
            return list;
        }
        final ArrayList<Entity> list2 = new ArrayList<Entity>();
        ClassInheratanceMultiMap[] entityLists;
        while (0 < (entityLists = chunk.getEntityLists()).length) {
            list2.addAll((Collection<?>)entityLists[0]);
            int n = 0;
            ++n;
        }
        for (final Entity entity : WDL.newEntities.get(chunk.getChunkCoordIntPair())) {
            entity.isDead = false;
            list2.add(entity);
        }
        for (final Entity entity2 : list2) {
            if (entity2 == null) {
                WDLChunkLoader.logger.warn("[WDL] Null entity in chunk at " + chunk.getChunkCoordIntPair());
            }
            else {
                if (!shouldSaveEntity(entity2)) {
                    continue;
                }
                for (final WDLApi.ModInfo modInfo : WDLApi.getImplementingExtensions(IEntityEditor.class)) {
                    if (((IEntityEditor)modInfo.mod).shouldEdit(entity2)) {
                        ((IEntityEditor)modInfo.mod).editEntity(entity2);
                    }
                }
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                if (!entity2.writeToNBTOptional(nbtTagCompound)) {
                    continue;
                }
                chunk.setHasEntities(true);
                list.appendTag(nbtTagCompound);
            }
        }
        return list;
    }
    
    public static boolean shouldSaveEntity(final Entity entity) {
        if (entity instanceof EntityPlayer) {
            return false;
        }
        if (!EntityUtils.isEntityEnabled(entity)) {
            WDLMessages.chatMessageTranslated(WDLMessageTypes.REMOVE_ENTITY, "wdl.messages.removeEntity.notSavingUserPreference", entity);
            return false;
        }
        return true;
    }
    
    public NBTTagList getTileEntityList(final Chunk chunk) {
        final NBTTagList list = new NBTTagList();
        if (!WDLPluginChannels.canSaveTileEntities(chunk)) {
            return list;
        }
        final Map tileEntityMap = chunk.getTileEntityMap();
        final Map oldTileEntities = this.getOldTileEntities(chunk);
        Map<?, TileEntity> map = WDL.newTileEntities.get(chunk.getChunkCoordIntPair());
        if (map == null) {
            map = new HashMap<Object, TileEntity>();
        }
        final HashSet<BlockPos> set = new HashSet<BlockPos>();
        set.addAll((Collection<?>)tileEntityMap.keySet());
        set.addAll((Collection<?>)oldTileEntities.keySet());
        set.addAll((Collection<?>)map.keySet());
        for (final BlockPos blockPos : set) {
            if (map.containsKey(blockPos)) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                final TileEntity tileEntity = map.get(blockPos);
                tileEntity.writeToNBT(nbtTagCompound);
                WDLMessages.chatMessageTranslated(WDLMessageTypes.LOAD_TILE_ENTITY, "wdl.messages.tileEntity.usingNew", String.valueOf(nbtTagCompound.getString("id")) + " (" + tileEntity.getClass().getCanonicalName() + ")", blockPos);
                editTileEntity(blockPos, nbtTagCompound, ITileEntityEditor.TileEntityCreationMode.NEW);
                list.appendTag(nbtTagCompound);
            }
            else if (oldTileEntities.containsKey(blockPos)) {
                final NBTTagCompound nbtTagCompound2 = oldTileEntities.get(blockPos);
                WDLMessages.chatMessageTranslated(WDLMessageTypes.LOAD_TILE_ENTITY, "wdl.messages.tileEntity.usingOld", nbtTagCompound2.getString("id"), blockPos);
                editTileEntity(blockPos, nbtTagCompound2, ITileEntityEditor.TileEntityCreationMode.IMPORTED);
                list.appendTag(nbtTagCompound2);
            }
            else {
                if (!tileEntityMap.containsKey(blockPos)) {
                    continue;
                }
                final TileEntity tileEntity2 = tileEntityMap.get(blockPos);
                final NBTTagCompound nbtTagCompound3 = new NBTTagCompound();
                tileEntity2.writeToNBT(nbtTagCompound3);
                editTileEntity(blockPos, nbtTagCompound3, ITileEntityEditor.TileEntityCreationMode.EXISTING);
                list.appendTag(nbtTagCompound3);
            }
        }
        return list;
    }
    
    public Map getOldTileEntities(final Chunk chunk) {
        final HashMap<BlockPos, NBTTagCompound> hashMap = new HashMap<BlockPos, NBTTagCompound>();
        final DataInputStream chunkInputStream = RegionFileCache.getChunkInputStream(this.chunkSaveLocation, chunk.xPosition, chunk.zPosition);
        if (chunkInputStream == null) {
            final HashMap<BlockPos, NBTTagCompound> hashMap2 = hashMap;
            if (chunkInputStream != null) {
                chunkInputStream.close();
            }
            return hashMap2;
        }
        final NBTTagList tagList = CompressedStreamTools.read(chunkInputStream).getCompoundTag("Level").getTagList("TileEntities", 10);
        if (tagList != null) {
            while (0 < tagList.tagCount()) {
                final NBTTagCompound compoundTag = tagList.getCompoundTagAt(0);
                final String string = compoundTag.getString("id");
                final BlockPos blockPos = new BlockPos(compoundTag.getInteger("x"), compoundTag.getInteger("y"), compoundTag.getInteger("z"));
                if (this.shouldImportTileEntity(string, blockPos, chunk.getBlock(blockPos), compoundTag, chunk)) {
                    hashMap.put(blockPos, compoundTag);
                }
                else {
                    WDLMessages.chatMessageTranslated(WDLMessageTypes.LOAD_TILE_ENTITY, "wdl.messages.tileEntity.notImporting", string, blockPos);
                }
                int n = 0;
                ++n;
            }
        }
        if (chunkInputStream != null) {
            chunkInputStream.close();
        }
        return hashMap;
    }
    
    public boolean shouldImportTileEntity(final String s, final BlockPos blockPos, final Block block, final NBTTagCompound nbtTagCompound, final Chunk chunk) {
        if (block instanceof BlockChest && s.equals("Chest")) {
            return true;
        }
        if (block instanceof BlockDispenser && s.equals("Trap")) {
            return true;
        }
        if (block instanceof BlockDropper && s.equals("Dropper")) {
            return true;
        }
        if (block instanceof BlockFurnace && s.equals("Furnace")) {
            return true;
        }
        if (block instanceof BlockNote && s.equals("Music")) {
            return true;
        }
        if (block instanceof BlockBrewingStand && s.equals("Cauldron")) {
            return true;
        }
        if (block instanceof BlockHopper && s.equals("Hopper")) {
            return true;
        }
        if (block instanceof BlockBeacon && s.equals("Beacon")) {
            return true;
        }
        final Iterator<WDLApi.ModInfo> iterator = WDLApi.getImplementingExtensions(ITileEntityImportationIdentifier.class).iterator();
        while (iterator.hasNext()) {
            if (((ITileEntityImportationIdentifier)iterator.next().mod).shouldImportTileEntity(s, blockPos, block, nbtTagCompound, chunk)) {
                return true;
            }
        }
        return false;
    }
    
    public static void editTileEntity(final BlockPos blockPos, final NBTTagCompound nbtTagCompound, final ITileEntityEditor.TileEntityCreationMode tileEntityCreationMode) {
        for (final WDLApi.ModInfo modInfo : WDLApi.getImplementingExtensions(ITileEntityEditor.class)) {
            if (((ITileEntityEditor)modInfo.mod).shouldEdit(blockPos, nbtTagCompound, tileEntityCreationMode)) {
                ((ITileEntityEditor)modInfo.mod).editTileEntity(blockPos, nbtTagCompound, tileEntityCreationMode);
                WDLMessages.chatMessageTranslated(WDLMessageTypes.LOAD_TILE_ENTITY, "wdl.messages.tileEntity.edited", blockPos, modInfo.getDisplayName());
            }
        }
    }
}
