package wdl;

import net.minecraft.profiler.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import wdl.api.*;
import java.util.*;
import net.minecraft.world.chunk.*;
import wdl.chan.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.passive.*;
import net.minecraft.village.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.storage.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import wdl.gui.*;

public class WDLEvents
{
    private static final Profiler profiler;
    
    static {
        profiler = Minecraft.getMinecraft().mcProfiler;
    }
    
    public static void onWorldLoad(final WorldClient worldClient) {
        WDLEvents.profiler.startSection("Core");
        if (WDL.minecraft.isIntegratedServerRunning()) {
            return;
        }
        if (WDL.downloading) {
            if (!WDL.saving) {
                WDLMessages.chatMessageTranslated(WDLMessageTypes.INFO, "wdl.messages.generalInfo.worldChanged", new Object[0]);
                WDL.worldLoadingDeferred = true;
            }
            WDLEvents.profiler.endSection();
            return;
        }
        final boolean loadWorld = WDL.loadWorld();
        WDLEvents.profiler.endSection();
        for (final WDLApi.ModInfo modInfo : WDLApi.getImplementingExtensions(IWorldLoadListener.class)) {
            WDLEvents.profiler.startSection(modInfo.id);
            ((IWorldLoadListener)modInfo.mod).onWorldLoad(worldClient, loadWorld);
            WDLEvents.profiler.endSection();
        }
    }
    
    public static void onChunkNoLongerNeeded(final Chunk chunk) {
        if (!WDL.downloading) {
            return;
        }
        if (chunk == null) {
            return;
        }
        if (WDLPluginChannels.canSaveChunk(chunk)) {
            WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_CHUNK_NO_LONGER_NEEDED, "wdl.messages.onChunkNoLongerNeeded.saved", chunk.xPosition, chunk.zPosition);
            WDL.saveChunk(chunk);
        }
        else {
            WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_CHUNK_NO_LONGER_NEEDED, "wdl.messages.onChunkNoLongerNeeded.didNotSave", chunk.xPosition, chunk.zPosition);
        }
    }
    
    public static void onItemGuiOpened() {
        if (!WDL.downloading) {
            return;
        }
        if (WDL.minecraft.objectMouseOver == null) {
            return;
        }
        if (WDL.minecraft.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            WDL.lastEntity = WDL.minecraft.objectMouseOver.entityHit;
        }
        else {
            WDL.lastEntity = null;
            WDL.lastClickedBlock = WDL.minecraft.objectMouseOver.func_178782_a();
        }
    }
    
    public static boolean onItemGuiClosed() {
        if (!WDL.downloading) {
            return true;
        }
        if (WDL.thePlayer.ridingEntity != null && WDL.thePlayer.ridingEntity instanceof EntityHorse && WDL.windowContainer instanceof ContainerHorseInventory) {
            final EntityHorse entityHorse = (EntityHorse)ReflectionUtils.stealAndGetField(WDL.windowContainer, EntityHorse.class);
            if (entityHorse == WDL.thePlayer.ridingEntity) {
                if (!WDLPluginChannels.canSaveEntities(entityHorse.chunkCoordX, entityHorse.chunkCoordZ)) {
                    WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_GUI_CLOSED_INFO, "wdl.messages.onGuiClosedInfo.cannotSaveEntities", new Object[0]);
                    return true;
                }
                final EntityHorse entityHorse2 = (EntityHorse)WDL.thePlayer.ridingEntity;
                final AnimalChest animalChest = new AnimalChest("HorseChest", (entityHorse2.isChested() && (entityHorse2.getHorseType() == 1 || entityHorse2.getHorseType() == 2)) ? 17 : 2);
                animalChest.func_110133_a(entityHorse2.getName());
                WDL.saveContainerItems(WDL.windowContainer, animalChest, 0);
                animalChest.func_110134_a(entityHorse2);
                ReflectionUtils.stealAndSetField(entityHorse2, AnimalChest.class, animalChest);
                WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_GUI_CLOSED_INFO, "wdl.messages.onGuiClosedInfo.savedRiddenHorse", new Object[0]);
                return true;
            }
        }
        if (WDL.lastEntity != null) {
            if (!WDLPluginChannels.canSaveEntities(WDL.lastEntity.chunkCoordX, WDL.lastEntity.chunkCoordZ)) {
                WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_GUI_CLOSED_INFO, "wdl.messages.onGuiClosedInfo.cannotSaveEntities", new Object[0]);
                return true;
            }
            String s;
            if (WDL.lastEntity instanceof EntityMinecartChest && WDL.windowContainer instanceof ContainerChest) {
                final EntityMinecartChest entityMinecartChest = (EntityMinecartChest)WDL.lastEntity;
                while (0 < entityMinecartChest.getSizeInventory()) {
                    entityMinecartChest.setInventorySlotContents(0, WDL.windowContainer.getSlot(0).getStack());
                    int n = 0;
                    ++n;
                }
                s = "storageMinecart";
            }
            else if (WDL.lastEntity instanceof EntityMinecartHopper && WDL.windowContainer instanceof ContainerHopper) {
                final EntityMinecartHopper entityMinecartHopper = (EntityMinecartHopper)WDL.lastEntity;
                while (0 < entityMinecartHopper.getSizeInventory()) {
                    entityMinecartHopper.setInventorySlotContents(0, WDL.windowContainer.getSlot(0).getStack());
                    int n = 0;
                    ++n;
                }
                s = "hopperMinecart";
            }
            else if (WDL.lastEntity instanceof EntityVillager && WDL.windowContainer instanceof ContainerMerchant) {
                ReflectionUtils.stealAndSetField(WDL.lastEntity, MerchantRecipeList.class, ((IMerchant)ReflectionUtils.stealAndGetField(WDL.windowContainer, IMerchant.class)).getRecipes(WDL.thePlayer));
                s = "villager";
            }
            else {
                if (!(WDL.lastEntity instanceof EntityHorse) || !(WDL.windowContainer instanceof ContainerHorseInventory)) {
                    return false;
                }
                final EntityHorse entityHorse3 = (EntityHorse)WDL.lastEntity;
                final AnimalChest animalChest2 = new AnimalChest("HorseChest", (entityHorse3.isChested() && (entityHorse3.getHorseType() == 1 || entityHorse3.getHorseType() == 2)) ? 17 : 2);
                animalChest2.func_110133_a(entityHorse3.getName());
                WDL.saveContainerItems(WDL.windowContainer, animalChest2, 0);
                animalChest2.func_110134_a(entityHorse3);
                ReflectionUtils.stealAndSetField(entityHorse3, AnimalChest.class, animalChest2);
                s = "horse";
            }
            WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_GUI_CLOSED_INFO, "wdl.messages.onGuiClosedInfo.savedEntity." + s, new Object[0]);
            return true;
        }
        else {
            final TileEntity tileEntity = WDL.worldClient.getTileEntity(WDL.lastClickedBlock);
            if (tileEntity == null) {
                WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_GUI_CLOSED_WARNING, "wdl.messages.onGuiClosedWarning.couldNotGetTE", WDL.lastClickedBlock);
                return true;
            }
            if (!WDLPluginChannels.canSaveContainers(tileEntity.getPos().getX() << 4, tileEntity.getPos().getZ() << 4)) {
                WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_GUI_CLOSED_INFO, "wdl.messages.onGuiClosedInfo.cannotSaveTileEntities", new Object[0]);
                return true;
            }
            String s2;
            if (WDL.windowContainer instanceof ContainerChest && tileEntity instanceof TileEntityChest) {
                if (WDL.windowContainer.inventorySlots.size() > 63) {
                    final BlockPos lastClickedBlock = WDL.lastClickedBlock;
                    final TileEntity tileEntity2 = tileEntity;
                    BlockPos blockPos = null;
                    BlockPos blockPos2 = null;
                    TileEntity tileEntity3 = null;
                    TileEntity tileEntity4 = null;
                    final BlockPos add = lastClickedBlock.add(0, 0, 1);
                    final TileEntity tileEntity5 = WDL.worldClient.getTileEntity(add);
                    if (tileEntity5 instanceof TileEntityChest && ((TileEntityChest)tileEntity5).getChestType() == ((TileEntityChest)tileEntity2).getChestType()) {
                        tileEntity3 = tileEntity2;
                        tileEntity4 = tileEntity5;
                        blockPos = lastClickedBlock;
                        blockPos2 = add;
                    }
                    final BlockPos add2 = lastClickedBlock.add(0, 0, -1);
                    final TileEntity tileEntity6 = WDL.worldClient.getTileEntity(add2);
                    if (tileEntity6 instanceof TileEntityChest && ((TileEntityChest)tileEntity6).getChestType() == ((TileEntityChest)tileEntity2).getChestType()) {
                        tileEntity3 = tileEntity6;
                        tileEntity4 = tileEntity2;
                        blockPos = add2;
                        blockPos2 = lastClickedBlock;
                    }
                    final BlockPos add3 = lastClickedBlock.add(1, 0, 0);
                    final TileEntity tileEntity7 = WDL.worldClient.getTileEntity(add3);
                    if (tileEntity7 instanceof TileEntityChest && ((TileEntityChest)tileEntity7).getChestType() == ((TileEntityChest)tileEntity2).getChestType()) {
                        tileEntity3 = tileEntity2;
                        tileEntity4 = tileEntity7;
                        blockPos = lastClickedBlock;
                        blockPos2 = add3;
                    }
                    final BlockPos add4 = lastClickedBlock.add(-1, 0, 0);
                    final TileEntity tileEntity8 = WDL.worldClient.getTileEntity(add4);
                    if (tileEntity8 instanceof TileEntityChest && ((TileEntityChest)tileEntity8).getChestType() == ((TileEntityChest)tileEntity2).getChestType()) {
                        tileEntity3 = tileEntity8;
                        tileEntity4 = tileEntity2;
                        blockPos = add4;
                        blockPos2 = lastClickedBlock;
                    }
                    if (tileEntity3 == null || tileEntity4 == null || blockPos == null || blockPos2 == null) {
                        WDLMessages.chatMessageTranslated(WDLMessageTypes.ERROR, "wdl.messages.onGuiClosedWarning.failedToFindDoubleChest", new Object[0]);
                        return true;
                    }
                    WDL.saveContainerItems(WDL.windowContainer, (IInventory)tileEntity3, 0);
                    WDL.saveContainerItems(WDL.windowContainer, (IInventory)tileEntity4, 27);
                    WDL.saveTileEntity(blockPos, tileEntity3);
                    WDL.saveTileEntity(blockPos2, tileEntity4);
                    s2 = "doubleChest";
                }
                else {
                    WDL.saveContainerItems(WDL.windowContainer, (IInventory)tileEntity, 0);
                    WDL.saveTileEntity(WDL.lastClickedBlock, tileEntity);
                    s2 = "singleChest";
                }
            }
            else if (WDL.windowContainer instanceof ContainerChest && tileEntity instanceof TileEntityEnderChest) {
                final InventoryEnderChest inventoryEnderChest = WDL.thePlayer.getInventoryEnderChest();
                final int sizeInventory = inventoryEnderChest.getSizeInventory();
                while (0 < WDL.windowContainer.inventorySlots.size() && 0 < sizeInventory) {
                    inventoryEnderChest.setInventorySlotContents(0, WDL.windowContainer.getSlot(0).getStack());
                    int n2 = 0;
                    ++n2;
                }
                s2 = "enderChest";
            }
            else if (WDL.windowContainer instanceof ContainerBrewingStand && tileEntity instanceof TileEntityBrewingStand) {
                final IInventory inventory = (IInventory)ReflectionUtils.stealAndGetField(WDL.windowContainer, IInventory.class);
                WDL.saveContainerItems(WDL.windowContainer, (IInventory)tileEntity, 0);
                WDL.saveInventoryFields(inventory, (IInventory)tileEntity);
                WDL.saveTileEntity(WDL.lastClickedBlock, tileEntity);
                s2 = "brewingStand";
            }
            else if (WDL.windowContainer instanceof ContainerDispenser && tileEntity instanceof TileEntityDispenser) {
                WDL.saveContainerItems(WDL.windowContainer, (IInventory)tileEntity, 0);
                WDL.saveTileEntity(WDL.lastClickedBlock, tileEntity);
                s2 = "dispenser";
            }
            else if (WDL.windowContainer instanceof ContainerFurnace && tileEntity instanceof TileEntityFurnace) {
                final IInventory inventory2 = (IInventory)ReflectionUtils.stealAndGetField(WDL.windowContainer, IInventory.class);
                WDL.saveContainerItems(WDL.windowContainer, (IInventory)tileEntity, 0);
                WDL.saveInventoryFields(inventory2, (IInventory)tileEntity);
                WDL.saveTileEntity(WDL.lastClickedBlock, tileEntity);
                s2 = "furnace";
            }
            else if (WDL.windowContainer instanceof ContainerHopper && tileEntity instanceof TileEntityHopper) {
                WDL.saveContainerItems(WDL.windowContainer, (IInventory)tileEntity, 0);
                WDL.saveTileEntity(WDL.lastClickedBlock, tileEntity);
                s2 = "hopper";
            }
            else {
                if (!(WDL.windowContainer instanceof ContainerBeacon) || !(tileEntity instanceof TileEntityBeacon)) {
                    return false;
                }
                final IInventory func_180611_e = ((ContainerBeacon)WDL.windowContainer).func_180611_e();
                final TileEntityBeacon tileEntityBeacon = (TileEntityBeacon)tileEntity;
                WDL.saveContainerItems(WDL.windowContainer, tileEntityBeacon, 0);
                WDL.saveInventoryFields(func_180611_e, tileEntityBeacon);
                WDL.saveTileEntity(WDL.lastClickedBlock, tileEntity);
                s2 = "beacon";
            }
            WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_GUI_CLOSED_INFO, "wdl.messages.onGuiClosedInfo.savedTileEntity." + s2, new Object[0]);
            return true;
        }
    }
    
    public static void onBlockEvent(final BlockPos blockPos, final Block block, final int n, final int n2) {
        if (!WDL.downloading) {
            return;
        }
        if (!WDLPluginChannels.canSaveTileEntities(blockPos.getX() << 4, blockPos.getZ() << 4)) {
            return;
        }
        if (block == Blocks.noteblock) {
            final TileEntityNote tileEntityNote = new TileEntityNote();
            tileEntityNote.note = (byte)(n2 % 25);
            WDL.worldClient.setTileEntity(blockPos, tileEntityNote);
            WDL.saveTileEntity(blockPos, tileEntityNote);
            WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_BLOCK_EVENT, "wdl.messages.onBlockEvent.noteblock", blockPos, n2, tileEntityNote);
        }
    }
    
    public static void onMapDataLoaded(final int n, final MapData mapData) {
        if (!WDL.downloading) {
            return;
        }
        if (!WDLPluginChannels.canSaveMaps()) {
            return;
        }
        final boolean containsKey = WDL.newMapDatas.containsKey(n);
        WDL.newMapDatas.put(n, mapData);
        if (!containsKey) {
            WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_MAP_SAVED, "wdl.messages.onMapSaved", n);
        }
    }
    
    public static void onPluginChannelPacket(final String s, final byte[] array) {
        WDLPluginChannels.onPluginChannelPacket(s, array);
    }
    
    public static void onRemoveEntityFromWorld(final Entity entity) {
        if (WDL.downloading && entity != null && WDLPluginChannels.canSaveEntities(entity.chunkCoordX, entity.chunkCoordZ)) {
            if (!EntityUtils.isEntityEnabled(entity)) {
                WDLMessages.chatMessageTranslated(WDLMessageTypes.REMOVE_ENTITY, "wdl.messages.removeEntity.allowingRemoveUserPref", entity);
                return;
            }
            final int entityTrackDistance = EntityUtils.getEntityTrackDistance(entity);
            if (entityTrackDistance < 0) {
                WDLMessages.chatMessageTranslated(WDLMessageTypes.REMOVE_ENTITY, "wdl.messages.removeEntity.allowingRemoveUnrecognizedDistance", entity);
                return;
            }
            final double distance = entity.getDistance(WDL.thePlayer.posX, entity.posY, WDL.thePlayer.posZ);
            if (distance > entityTrackDistance) {
                WDLMessages.chatMessageTranslated(WDLMessageTypes.REMOVE_ENTITY, "wdl.messages.removeEntity.savingDistance", entity, distance, entityTrackDistance);
                entity.chunkCoordX = MathHelper.floor_double(entity.posX / 16.0);
                entity.chunkCoordZ = MathHelper.floor_double(entity.posZ / 16.0);
                WDL.newEntities.put(new ChunkCoordIntPair(entity.chunkCoordX, entity.chunkCoordZ), entity);
                return;
            }
            WDLMessages.chatMessageTranslated(WDLMessageTypes.REMOVE_ENTITY, "wdl.messages.removeEntity.allowingRemoveDistance", entity, distance, entityTrackDistance);
        }
    }
    
    public static void onChatMessage(final String s) {
        final boolean b = WDL.minecraft.currentScreen instanceof GuiWDLGenerator;
        if ((b || WDL.downloading) && s.startsWith("Seed: ")) {
            final String substring = s.substring(6);
            WDL.worldProps.setProperty("RandomSeed", substring);
            if (WDL.worldProps.getProperty("MapGenerator", "void").equals("void")) {
                WDL.worldProps.setProperty("MapGenerator", "default");
                WDL.worldProps.setProperty("GeneratorName", "default");
                WDL.worldProps.setProperty("GeneratorVersion", "1");
                WDL.worldProps.setProperty("GeneratorOptions", "");
                WDLMessages.chatMessageTranslated(WDLMessageTypes.INFO, "wdl.messages.generalInfo.seedAndGenSet", substring);
            }
            else {
                WDLMessages.chatMessageTranslated(WDLMessageTypes.INFO, "wdl.messages.generalInfo.seedSet", substring);
            }
            if (b) {
                WDL.minecraft.currentScreen.initGui();
            }
        }
    }
}
