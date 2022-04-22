package wdl;

import net.minecraft.network.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import net.minecraft.world.chunk.storage.*;
import com.google.common.collect.*;
import org.apache.logging.log4j.*;
import wdl.chan.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import wdl.gui.*;
import wdl.api.*;
import net.minecraft.world.chunk.*;
import java.io.*;
import net.minecraft.nbt.*;
import net.minecraft.world.storage.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;
import java.util.*;

public class WDL
{
    public static final String VERSION;
    public static final String EXPECTED_MINECRAFT_VERSION;
    public static final String GITHUB_REPO;
    public static Minecraft minecraft;
    public static WorldClient worldClient;
    public static NetworkManager networkManager;
    public static EntityPlayerSP thePlayer;
    public static Container windowContainer;
    public static BlockPos lastClickedBlock;
    public static Entity lastEntity;
    public static SaveHandler saveHandler;
    public static IChunkLoader chunkLoader;
    public static HashMap newTileEntities;
    public static HashMultimap newEntities;
    public static HashMap newMapDatas;
    public static boolean downloading;
    public static boolean isMultiworld;
    public static boolean propsFound;
    public static boolean startOnChange;
    public static boolean overrideLastModifiedCheck;
    public static boolean saving;
    public static boolean worldLoadingDeferred;
    public static String worldName;
    public static String baseFolderName;
    public static Properties baseProps;
    public static Properties worldProps;
    public static final Properties globalProps;
    public static final Properties defaultProps;
    private static Logger logger;
    
    static {
        VERSION = "1.8.9a-beta2";
        EXPECTED_MINECRAFT_VERSION = "1.8.9";
        GITHUB_REPO = "Pokechu22/WorldDownloader";
        WDL.networkManager = null;
        WDL.newTileEntities = new HashMap();
        WDL.newEntities = HashMultimap.create();
        WDL.newMapDatas = new HashMap();
        WDL.downloading = false;
        WDL.isMultiworld = false;
        WDL.propsFound = false;
        WDL.startOnChange = false;
        WDL.overrideLastModifiedCheck = false;
        WDL.saving = false;
        WDL.worldLoadingDeferred = false;
        WDL.worldName = "WorldDownloaderERROR";
        WDL.baseFolderName = "WorldDownloaderERROR";
        WDL.logger = LogManager.getLogger();
        WDL.minecraft = Minecraft.getInstance();
        (defaultProps = new Properties()).setProperty("ServerName", "");
        WDL.defaultProps.setProperty("WorldName", "");
        WDL.defaultProps.setProperty("LinkedWorlds", "");
        WDL.defaultProps.setProperty("Backup", "ZIP");
        WDL.defaultProps.setProperty("AllowCheats", "true");
        WDL.defaultProps.setProperty("GameType", "keep");
        WDL.defaultProps.setProperty("Time", "keep");
        WDL.defaultProps.setProperty("Weather", "keep");
        WDL.defaultProps.setProperty("MapFeatures", "false");
        WDL.defaultProps.setProperty("RandomSeed", "");
        WDL.defaultProps.setProperty("MapGenerator", "void");
        WDL.defaultProps.setProperty("GeneratorName", "flat");
        WDL.defaultProps.setProperty("GeneratorVersion", "0");
        WDL.defaultProps.setProperty("GeneratorOptions", ";0");
        WDL.defaultProps.setProperty("Spawn", "player");
        WDL.defaultProps.setProperty("SpawnX", "8");
        WDL.defaultProps.setProperty("SpawnY", "127");
        WDL.defaultProps.setProperty("SpawnZ", "8");
        WDL.defaultProps.setProperty("PlayerPos", "keep");
        WDL.defaultProps.setProperty("PlayerX", "8");
        WDL.defaultProps.setProperty("PlayerY", "127");
        WDL.defaultProps.setProperty("PlayerZ", "8");
        WDL.defaultProps.setProperty("PlayerHealth", "20");
        WDL.defaultProps.setProperty("PlayerFood", "20");
        WDL.defaultProps.setProperty("Messages.enableAll", "true");
        WDL.defaultProps.setProperty("Entity.TrackDistanceMode", "server");
        WDL.defaultProps.setProperty("Entity.FireworksRocketEntity.Enabled", "false");
        WDL.defaultProps.setProperty("Entity.EnderDragon.Enabled", "false");
        WDL.defaultProps.setProperty("Entity.WitherBoss.Enabled", "false");
        WDL.defaultProps.setProperty("Entity.PrimedTnt.Enabled", "false");
        WDL.defaultProps.setProperty("Entity.null.Enabled", "false");
        WDL.defaultProps.setProperty("EntityGroup.Other.Enabled", "true");
        WDL.defaultProps.setProperty("EntityGroup.Hostile.Enabled", "true");
        WDL.defaultProps.setProperty("EntityGroup.Passive.Enabled", "true");
        WDL.defaultProps.setProperty("LastSaved", "-1");
        WDL.defaultProps.setProperty("TutorialShown", "false");
        WDL.defaultProps.setProperty("UpdateMinecraftVersion", "client");
        WDL.defaultProps.setProperty("UpdateAllowBetas", "true");
        globalProps = new Properties(WDL.defaultProps);
        final FileReader fileReader = new FileReader(new File(Minecraft.mcDataDir, "WorldDownloader.txt"));
        WDL.globalProps.load(fileReader);
        if (fileReader != null) {
            fileReader.close();
        }
        WDL.baseProps = new Properties(WDL.globalProps);
        WDL.worldProps = new Properties(WDL.baseProps);
    }
    
    public static void startDownload() {
        WDL.worldClient = Minecraft.theWorld;
        if (!WDLPluginChannels.canDownloadAtAll()) {
            return;
        }
        if (WDL.isMultiworld && WDL.worldName.isEmpty()) {
            WDL.minecraft.displayGuiScreen(new GuiWDLMultiworldSelect(I18n.format("wdl.gui.multiworldSelect.title.startDownload", new Object[0]), new GuiWDLMultiworldSelect.WorldSelectionCallback() {
                @Override
                public void onWorldSelected(final String worldName) {
                    WDL.worldName = worldName;
                    WDL.isMultiworld = true;
                    WDL.propsFound = true;
                    WDL.minecraft.displayGuiScreen(null);
                }
                
                @Override
                public void onCancel() {
                    WDL.minecraft.displayGuiScreen(null);
                }
            }));
            return;
        }
        if (!WDL.propsFound) {
            WDL.minecraft.displayGuiScreen(new GuiWDLMultiworld(new GuiWDLMultiworld.MultiworldCallback() {
                @Override
                public void onSelect(final boolean isMultiworld) {
                    WDL.isMultiworld = isMultiworld;
                    if (WDL.isMultiworld) {
                        WDL.minecraft.displayGuiScreen(new GuiWDLMultiworldSelect(I18n.format("wdl.gui.multiworldSelect.title.startDownload", new Object[0]), new GuiWDLMultiworldSelect.WorldSelectionCallback() {
                            final WDL$2 this$1;
                            
                            @Override
                            public void onWorldSelected(final String worldName) {
                                WDL.worldName = worldName;
                                WDL.isMultiworld = true;
                                WDL.propsFound = true;
                                WDL.minecraft.displayGuiScreen(null);
                            }
                            
                            @Override
                            public void onCancel() {
                                WDL.minecraft.displayGuiScreen(null);
                            }
                        }));
                    }
                    else {
                        WDL.baseProps.setProperty("LinkedWorlds", "");
                        WDL.propsFound = true;
                        WDL.minecraft.displayGuiScreen(null);
                    }
                }
                
                @Override
                public void onCancel() {
                    WDL.minecraft.displayGuiScreen(null);
                }
            }));
            return;
        }
        WDL.worldProps = loadWorldProps(WDL.worldName);
        WDL.saveHandler = (SaveHandler)WDL.minecraft.getSaveLoader().getSaveLoader(getWorldFolderName(WDL.worldName), true);
        final long long1 = Long.parseLong(WDL.worldProps.getProperty("LastSaved", "-1"));
        final FileInputStream fileInputStream = new FileInputStream(new File(WDL.saveHandler.getWorldDirectory(), "level.dat"));
        final long long2 = CompressedStreamTools.readCompressed(fileInputStream).getCompoundTag("Data").getLong("LastPlayed");
        if (!WDL.overrideLastModifiedCheck && long2 > long1) {
            WDL.minecraft.displayGuiScreen(new GuiWDLOverwriteChanges(long1, long2));
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            return;
        }
        if (fileInputStream != null) {
            fileInputStream.close();
        }
        WDL.minecraft.displayGuiScreen(null);
        WDL.minecraft.setIngameFocus();
        WDL.chunkLoader = WDLChunkLoader.create(WDL.saveHandler, WDL.worldClient.provider);
        WDL.newTileEntities = new HashMap();
        WDL.newEntities = HashMultimap.create();
        WDL.newMapDatas = new HashMap();
        if (WDL.baseProps.getProperty("ServerName").isEmpty()) {
            WDL.baseProps.setProperty("ServerName", getServerName());
        }
        WDL.startOnChange = true;
        WDL.downloading = true;
        WDLMessages.chatMessageTranslated(WDLMessageTypes.INFO, "wdl.messages.generalInfo.downloadStarted", new Object[0]);
    }
    
    public static void stopDownload() {
        if (WDL.downloading) {
            WDL.downloading = false;
            WDL.startOnChange = false;
            WDLMessages.chatMessageTranslated(WDLMessageTypes.INFO, "wdl.messages.generalInfo.downloadStopped", new Object[0]);
        }
    }
    
    public static void cancelDownload() {
        if (WDL.downloading) {
            WDL.minecraft.getSaveLoader().flushCache();
            WDL.saveHandler.flush();
            WDL.startOnChange = false;
            WDL.saving = false;
            WDL.downloading = false;
            WDL.worldLoadingDeferred = false;
            WDLMessages.chatMessageTranslated(WDLMessageTypes.INFO, "wdl.messages.generalInfo.downloadCanceled", new Object[0]);
        }
    }
    
    static void startSaveThread() {
        WDLMessages.chatMessageTranslated(WDLMessageTypes.INFO, "wdl.messages.generalInfo.saveStarted", new Object[0]);
        WDL.saving = true;
        new Thread() {
            @Override
            public void run() {
                WDL.saving = false;
            }
        }.start();
    }
    
    public static boolean loadWorld() {
        WDL.worldName = "";
        WDL.worldClient = Minecraft.theWorld;
        WDL.thePlayer = Minecraft.thePlayer;
        WDL.windowContainer = WDL.thePlayer.openContainer;
        WDL.overrideLastModifiedCheck = false;
        final NetworkManager networkManager = WDL.thePlayer.sendQueue.getNetworkManager();
        WDL.networkManager;
        final NetworkManager networkManager2 = networkManager;
        if (WDL.networkManager != networkManager) {
            WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_WORLD_LOAD, "wdl.messages.onWorldLoad.differentServer", new Object[0]);
            WDL.networkManager = networkManager;
            if (networkManager2 != null) {
                WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_WORLD_LOAD, "wdl.messages.onWorldLoad.spigot", WDL.thePlayer.getClientBrand());
            }
            else {
                WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_WORLD_LOAD, "wdl.messages.onWorldLoad.vanilla", WDL.thePlayer.getClientBrand());
            }
            WDL.startOnChange = false;
            return true;
        }
        WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_WORLD_LOAD, "wdl.messages.onWorldLoad.sameServer", new Object[0]);
        if (networkManager2 != null) {
            WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_WORLD_LOAD, "wdl.messages.onWorldLoad.spigot", WDL.thePlayer.getClientBrand());
        }
        else {
            WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_WORLD_LOAD, "wdl.messages.onWorldLoad.vanilla", WDL.thePlayer.getClientBrand());
        }
        WDL.startOnChange;
        return false;
    }
    
    public static void onSaveComplete() {
        WDL.minecraft.getSaveLoader().flushCache();
        WDL.saveHandler.flush();
        WDL.worldClient = null;
        WDL.worldLoadingDeferred = false;
        if (WDL.downloading) {
            WDLMessages.chatMessageTranslated(WDLMessageTypes.INFO, "wdl.messages.generalInfo.saveComplete.startingAgain", new Object[0]);
            loadWorld();
            return;
        }
        WDLMessages.chatMessageTranslated(WDLMessageTypes.INFO, "wdl.messages.generalInfo.saveComplete.done", new Object[0]);
    }
    
    public static void saveEverything() throws Exception {
        if (!WDLPluginChannels.canDownloadAtAll()) {
            WDLMessages.chatMessageTranslated(WDLMessageTypes.ERROR, "wdl.messages.generalError.forbidden", new Object[0]);
            return;
        }
        final WorldBackup.WorldBackupType match = WorldBackup.WorldBackupType.match(WDL.baseProps.getProperty("Backup", "ZIP"));
        final GuiWDLSaveProgress guiWDLSaveProgress = new GuiWDLSaveProgress(I18n.format("wdl.saveProgress.title", new Object[0]), ((match != WorldBackup.WorldBackupType.NONE) ? 6 : 5) + WDLApi.getImplementingExtensions(ISaveListener.class).size());
        WDL.minecraft.addScheduledTask(new Runnable() {
            private final GuiWDLSaveProgress val$progressScreen;
            
            @Override
            public void run() {
                WDL.minecraft.displayGuiScreen(this.val$progressScreen);
            }
        });
        WDL.saveHandler.checkSessionLock();
        saveWorldInfo(guiWDLSaveProgress, savePlayer(guiWDLSaveProgress));
        saveMapData(guiWDLSaveProgress);
        saveChunks(guiWDLSaveProgress);
        for (final WDLApi.ModInfo modInfo : WDLApi.getImplementingExtensions(ISaveListener.class)) {
            guiWDLSaveProgress.startMajorTask(I18n.format("wdl.saveProgress.extension.title", modInfo.getDisplayName()), 1);
            ((ISaveListener)modInfo.mod).afterChunksSaved(WDL.saveHandler.getWorldDirectory());
        }
        WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, "wdl.messages.saving.flushingIO", new Object[0]);
        guiWDLSaveProgress.startMajorTask(I18n.format("wdl.saveProgress.flushingIO.title", new Object[0]), 1);
        guiWDLSaveProgress.setMinorTaskProgress(I18n.format("wdl.saveProgress.flushingIO.subtitle", new Object[0]), 1);
        ThreadedFileIOBase.getThreadedIOInstance().waitForFinish();
        if (match != WorldBackup.WorldBackupType.NONE) {
            WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, "wdl.messages.saving.backingUp", new Object[0]);
            guiWDLSaveProgress.startMajorTask(match.getTitle(), 1);
            guiWDLSaveProgress.setMinorTaskProgress(I18n.format("wdl.saveProgress.backingUp.preparing", new Object[0]), 1);
            WorldBackup.backupWorld(WDL.saveHandler.getWorldDirectory(), getWorldFolderName(WDL.worldName), match, guiWDLSaveProgress);
        }
        guiWDLSaveProgress.setDoneWorking();
    }
    
    public static NBTTagCompound savePlayer(final GuiWDLSaveProgress guiWDLSaveProgress) {
        if (!WDLPluginChannels.canDownloadAtAll()) {
            return new NBTTagCompound();
        }
        guiWDLSaveProgress.startMajorTask(I18n.format("wdl.saveProgress.playerData.title", new Object[0]), 3 + WDLApi.getImplementingExtensions(IPlayerInfoEditor.class).size());
        WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, "wdl.messages.saving.savingPlayer", new Object[0]);
        guiWDLSaveProgress.setMinorTaskProgress(I18n.format("wdl.saveProgress.playerData.creatingNBT", new Object[0]), 1);
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        WDL.thePlayer.writeToNBT(nbtTagCompound);
        guiWDLSaveProgress.setMinorTaskProgress(I18n.format("wdl.saveProgress.playerData.editingNBT", new Object[0]), 2);
        applyOverridesToPlayer(nbtTagCompound);
        for (final WDLApi.ModInfo modInfo : WDLApi.getImplementingExtensions(IPlayerInfoEditor.class)) {
            guiWDLSaveProgress.setMinorTaskProgress(I18n.format("wdl.saveProgress.playerData.extension", modInfo.getDisplayName()), 3);
            ((IPlayerInfoEditor)modInfo.mod).editPlayerInfo(WDL.thePlayer, WDL.saveHandler, nbtTagCompound);
            int n = 0;
            ++n;
        }
        guiWDLSaveProgress.setMinorTaskProgress(I18n.format("wdl.saveProgress.playerData.writingNBT", new Object[0]), 3);
        final File file = new File(WDL.saveHandler.getWorldDirectory(), "playerdata");
        final File file2 = new File(file, String.valueOf(WDL.thePlayer.getUniqueID().toString()) + ".dat.tmp");
        final File file3 = new File(file, String.valueOf(WDL.thePlayer.getUniqueID().toString()) + ".dat");
        final FileOutputStream fileOutputStream = new FileOutputStream(file2);
        CompressedStreamTools.writeCompressed(nbtTagCompound, fileOutputStream);
        if (file3.exists()) {
            file3.delete();
        }
        file2.renameTo(file3);
        if (fileOutputStream != null) {
            fileOutputStream.close();
        }
        WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, "wdl.messages.saving.playerSaved", new Object[0]);
        return nbtTagCompound;
    }
    
    public static void saveWorldInfo(final GuiWDLSaveProgress guiWDLSaveProgress, final NBTTagCompound nbtTagCompound) {
        if (!WDLPluginChannels.canDownloadAtAll()) {
            return;
        }
        guiWDLSaveProgress.startMajorTask(I18n.format("wdl.saveProgress.worldMetadata.title", new Object[0]), 3 + WDLApi.getImplementingExtensions(IWorldInfoEditor.class).size());
        WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, "wdl.messages.saving.savingWorld", new Object[0]);
        guiWDLSaveProgress.setMinorTaskProgress(I18n.format("wdl.saveProgress.worldMetadata.creatingNBT", new Object[0]), 1);
        WDL.worldClient.getWorldInfo().setSaveVersion(19133);
        final NBTTagCompound cloneNBTCompound = WDL.worldClient.getWorldInfo().cloneNBTCompound(nbtTagCompound);
        guiWDLSaveProgress.setMinorTaskProgress(I18n.format("wdl.saveProgress.worldMetadata.editingNBT", new Object[0]), 2);
        applyOverridesToWorldInfo(cloneNBTCompound);
        for (final WDLApi.ModInfo modInfo : WDLApi.getImplementingExtensions(IWorldInfoEditor.class)) {
            guiWDLSaveProgress.setMinorTaskProgress(I18n.format("wdl.saveProgress.worldMetadata.extension", modInfo.getDisplayName()), 3);
            ((IWorldInfoEditor)modInfo.mod).editWorldInfo(WDL.worldClient, WDL.worldClient.getWorldInfo(), WDL.saveHandler, cloneNBTCompound);
            int n = 0;
            ++n;
        }
        guiWDLSaveProgress.setMinorTaskProgress(I18n.format("wdl.saveProgress.worldMetadata.writingNBT", new Object[0]), 3);
        final File worldDirectory = WDL.saveHandler.getWorldDirectory();
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        nbtTagCompound2.setTag("Data", cloneNBTCompound);
        WDL.worldProps.setProperty("LastSaved", Long.toString(cloneNBTCompound.getLong("LastPlayed")));
        final File file = new File(worldDirectory, "level.dat_new");
        final File file2 = new File(worldDirectory, "level.dat_old");
        final File file3 = new File(worldDirectory, "level.dat");
        final FileOutputStream fileOutputStream = new FileOutputStream(file);
        CompressedStreamTools.writeCompressed(nbtTagCompound2, fileOutputStream);
        if (file2.exists()) {
            file2.delete();
        }
        file3.renameTo(file2);
        if (file3.exists()) {
            file3.delete();
        }
        file.renameTo(file3);
        if (file.exists()) {
            file.delete();
        }
        if (fileOutputStream != null) {
            fileOutputStream.close();
        }
        WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, "wdl.messages.saving.worldSaved", new Object[0]);
    }
    
    public static void saveChunks(final GuiWDLSaveProgress guiWDLSaveProgress) throws IllegalArgumentException, IllegalAccessException {
        if (!WDLPluginChannels.canDownloadAtAll()) {
            return;
        }
        WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, "wdl.messages.saving.savingChunks", new Object[0]);
        final List list = (List)ReflectionUtils.stealAndGetField(WDL.worldClient.getChunkProvider(), List.class);
        guiWDLSaveProgress.startMajorTask(I18n.format("wdl.saveProgress.chunk.title", new Object[0]), list.size());
        while (0 < list.size()) {
            final Chunk chunk = list.get(0);
            if (chunk != null && WDLPluginChannels.canSaveChunk(chunk)) {
                guiWDLSaveProgress.setMinorTaskProgress(I18n.format("wdl.saveProgress.chunk.saving", chunk.xPosition, chunk.zPosition), 0);
                saveChunk(chunk);
            }
            int n = 0;
            ++n;
        }
        WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, "wdl.messages.saving.chunksSaved", new Object[0]);
    }
    
    public static void saveChunk(final Chunk chunk) {
        if (!WDLPluginChannels.canDownloadAtAll()) {
            return;
        }
        if (!WDLPluginChannels.canSaveChunk(chunk)) {
            return;
        }
        chunk.setTerrainPopulated(true);
        WDL.chunkLoader.saveChunk(WDL.worldClient, chunk);
    }
    
    public static void loadBaseProps() {
        WDL.baseFolderName = getBaseFolderName();
        WDL.baseProps = new Properties(WDL.globalProps);
        final FileReader fileReader = new FileReader(new File(new File(new File(Minecraft.mcDataDir, "saves"), WDL.baseFolderName), "WorldDownloader.txt"));
        WDL.baseProps.load(fileReader);
        WDL.propsFound = true;
        if (fileReader != null) {
            fileReader.close();
        }
        if (WDL.baseProps.getProperty("LinkedWorlds").isEmpty()) {
            WDL.isMultiworld = false;
            WDL.worldProps = new Properties(WDL.baseProps);
        }
        else {
            WDL.isMultiworld = true;
        }
    }
    
    public static Properties loadWorldProps(final String s) {
        final Properties properties = new Properties(WDL.baseProps);
        if (s.isEmpty()) {
            return properties;
        }
        final File file = new File(new File(Minecraft.mcDataDir, "saves"), getWorldFolderName(s));
        final InputStreamReader inputStreamReader = null;
        properties.load(new FileReader(new File(file, "WorldDownloader.txt")));
        final Properties properties2 = properties;
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        return properties2;
    }
    
    public static void saveProps() {
        saveProps(WDL.worldName, WDL.worldProps);
    }
    
    public static void saveProps(final String s, final Properties properties) {
        final File file = new File(Minecraft.mcDataDir, "saves");
        if (s.length() > 0) {
            final File file2 = new File(file, getWorldFolderName(s));
            file2.mkdirs();
            properties.store(new FileWriter(new File(file2, "WorldDownloader.txt")), I18n.format("wdl.props.world.title", new Object[0]));
        }
        else if (!WDL.isMultiworld) {
            WDL.baseProps.putAll(properties);
        }
        final File file3 = new File(file, WDL.baseFolderName);
        file3.mkdirs();
        WDL.baseProps.store(new FileWriter(new File(file3, "WorldDownloader.txt")), I18n.format("wdl.props.base.title", new Object[0]));
    }
    
    public static void saveGlobalProps() {
        WDL.globalProps.store(new FileWriter(new File(Minecraft.mcDataDir, "WorldDownloader.txt")), I18n.format("wdl.props.global.title", new Object[0]));
    }
    
    public static void applyOverridesToPlayer(final NBTTagCompound nbtTagCompound) {
        final String property = WDL.worldProps.getProperty("PlayerHealth");
        if (!property.equals("keep")) {
            nbtTagCompound.setShort("Health", Short.parseShort(property));
        }
        final String property2 = WDL.worldProps.getProperty("PlayerFood");
        if (!property2.equals("keep")) {
            final int int1 = Integer.parseInt(property2);
            nbtTagCompound.setInteger("foodLevel", int1);
            nbtTagCompound.setInteger("foodTickTimer", 0);
            if (int1 == 20) {
                nbtTagCompound.setFloat("foodSaturationLevel", 5.0f);
            }
            else {
                nbtTagCompound.setFloat("foodSaturationLevel", 0.0f);
            }
            nbtTagCompound.setFloat("foodExhaustionLevel", 0.0f);
        }
        if (WDL.worldProps.getProperty("PlayerPos").equals("xyz")) {
            final int int2 = Integer.parseInt(WDL.worldProps.getProperty("PlayerX"));
            final int int3 = Integer.parseInt(WDL.worldProps.getProperty("PlayerY"));
            final int int4 = Integer.parseInt(WDL.worldProps.getProperty("PlayerZ"));
            final NBTTagList list = new NBTTagList();
            list.appendTag(new NBTTagDouble(int2 + 0.5));
            list.appendTag(new NBTTagDouble(int3 + 0.621));
            list.appendTag(new NBTTagDouble(int4 + 0.5));
            nbtTagCompound.setTag("Pos", list);
            final NBTTagList list2 = new NBTTagList();
            list2.appendTag(new NBTTagDouble(0.0));
            list2.appendTag(new NBTTagDouble(-1.0E-4));
            list2.appendTag(new NBTTagDouble(0.0));
            nbtTagCompound.setTag("Motion", list2);
            final NBTTagList list3 = new NBTTagList();
            list3.appendTag(new NBTTagFloat(0.0f));
            list3.appendTag(new NBTTagFloat(0.0f));
            nbtTagCompound.setTag("Rotation", list3);
        }
        if (WDL.thePlayer.capabilities.allowFlying) {
            nbtTagCompound.getCompoundTag("abilities").setBoolean("flying", true);
        }
    }
    
    public static void applyOverridesToWorldInfo(final NBTTagCompound nbtTagCompound) {
        final String property = WDL.baseProps.getProperty("ServerName");
        final String property2 = WDL.worldProps.getProperty("WorldName");
        if (property2.isEmpty()) {
            nbtTagCompound.setString("LevelName", property);
        }
        else {
            nbtTagCompound.setString("LevelName", String.valueOf(property) + " - " + property2);
        }
        if (WDL.worldProps.getProperty("AllowCheats").equals("true")) {
            nbtTagCompound.setBoolean("allowCommands", true);
        }
        else {
            nbtTagCompound.setBoolean("allowCommands", false);
        }
        final String property3 = WDL.worldProps.getProperty("GameType");
        if (property3.equals("keep")) {
            if (WDL.thePlayer.capabilities.isCreativeMode) {
                nbtTagCompound.setInteger("GameType", 1);
            }
            else {
                nbtTagCompound.setInteger("GameType", 0);
            }
        }
        else if (property3.equals("survival")) {
            nbtTagCompound.setInteger("GameType", 0);
        }
        else if (property3.equals("creative")) {
            nbtTagCompound.setInteger("GameType", 1);
        }
        else if (property3.equals("hardcore")) {
            nbtTagCompound.setInteger("GameType", 0);
            nbtTagCompound.setBoolean("hardcore", true);
        }
        final String property4 = WDL.worldProps.getProperty("Time");
        if (!property4.equals("keep")) {
            nbtTagCompound.setLong("Time", Integer.parseInt(property4));
        }
        final String property5 = WDL.worldProps.getProperty("RandomSeed");
        long long1 = 0L;
        if (!property5.isEmpty()) {
            long1 = Long.parseLong(property5);
        }
        nbtTagCompound.setLong("RandomSeed", long1);
        nbtTagCompound.setBoolean("MapFeatures", Boolean.parseBoolean(WDL.worldProps.getProperty("MapFeatures")));
        nbtTagCompound.setString("generatorName", WDL.worldProps.getProperty("GeneratorName"));
        nbtTagCompound.setString("generatorOptions", WDL.worldProps.getProperty("GeneratorOptions"));
        nbtTagCompound.setInteger("generatorVersion", Integer.parseInt(WDL.worldProps.getProperty("GeneratorVersion")));
        final String property6 = WDL.worldProps.getProperty("Weather");
        if (property6.equals("sunny")) {
            nbtTagCompound.setBoolean("raining", false);
            nbtTagCompound.setInteger("rainTime", 0);
            nbtTagCompound.setBoolean("thundering", false);
            nbtTagCompound.setInteger("thunderTime", 0);
        }
        else if (property6.equals("rain")) {
            nbtTagCompound.setBoolean("raining", true);
            nbtTagCompound.setInteger("rainTime", 24000);
            nbtTagCompound.setBoolean("thundering", false);
            nbtTagCompound.setInteger("thunderTime", 0);
        }
        else if (property6.equals("thunderstorm")) {
            nbtTagCompound.setBoolean("raining", true);
            nbtTagCompound.setInteger("rainTime", 24000);
            nbtTagCompound.setBoolean("thundering", true);
            nbtTagCompound.setInteger("thunderTime", 24000);
        }
        final String property7 = WDL.worldProps.getProperty("Spawn");
        if (property7.equals("player")) {
            final int floor_double = MathHelper.floor_double(WDL.thePlayer.posX);
            final int floor_double2 = MathHelper.floor_double(WDL.thePlayer.posY);
            final int floor_double3 = MathHelper.floor_double(WDL.thePlayer.posZ);
            nbtTagCompound.setInteger("SpawnX", floor_double);
            nbtTagCompound.setInteger("SpawnY", floor_double2);
            nbtTagCompound.setInteger("SpawnZ", floor_double3);
            nbtTagCompound.setBoolean("initialized", true);
        }
        else if (property7.equals("xyz")) {
            final int int1 = Integer.parseInt(WDL.worldProps.getProperty("SpawnX"));
            final int int2 = Integer.parseInt(WDL.worldProps.getProperty("SpawnY"));
            final int int3 = Integer.parseInt(WDL.worldProps.getProperty("SpawnZ"));
            nbtTagCompound.setInteger("SpawnX", int1);
            nbtTagCompound.setInteger("SpawnY", int2);
            nbtTagCompound.setInteger("SpawnZ", int3);
            nbtTagCompound.setBoolean("initialized", true);
        }
    }
    
    public static void saveMapData(final GuiWDLSaveProgress guiWDLSaveProgress) {
        if (!WDLPluginChannels.canSaveMaps()) {
            return;
        }
        final File file = new File(WDL.saveHandler.getWorldDirectory(), "data");
        file.mkdirs();
        guiWDLSaveProgress.startMajorTask(I18n.format("wdl.saveProgress.map.title", new Object[0]), WDL.newMapDatas.size());
        WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, "wdl.messages.saving.savingMapItemData", new Object[0]);
        for (final Map.Entry<Object, V> entry : WDL.newMapDatas.entrySet()) {
            int n = 0;
            ++n;
            guiWDLSaveProgress.setMinorTaskProgress(I18n.format("wdl.saveProgress.map.saving", entry.getKey()), 0);
            final File file2 = new File(file, "map_" + entry.getKey() + ".dat");
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            ((MapData)entry.getValue()).writeToNBT(nbtTagCompound2);
            nbtTagCompound.setTag("data", nbtTagCompound2);
            CompressedStreamTools.writeCompressed(nbtTagCompound, new FileOutputStream(file2));
        }
        WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, "wdl.messages.saving.mapItemDataSaved", new Object[0]);
    }
    
    public static String getServerName() {
        if (WDL.minecraft.getCurrentServerData() != null) {
            String s = WDL.minecraft.getCurrentServerData().serverName;
            if (s.equals(I18n.format("selectServer.defaultName", new Object[0]))) {
                s = WDL.minecraft.getCurrentServerData().serverIP;
            }
            return s;
        }
        return "Unidentified Server";
    }
    
    public static String getBaseFolderName() {
        return getServerName().replaceAll("\\W+", "_");
    }
    
    public static String getWorldFolderName(final String s) {
        if (s.isEmpty()) {
            return WDL.baseFolderName;
        }
        return String.valueOf(WDL.baseFolderName) + " - " + s;
    }
    
    public static void saveContainerItems(final Container container, final IInventory inventory, final int n) {
        for (int size = container.inventorySlots.size(), sizeInventory = inventory.getSizeInventory(), n2 = n; n2 < size && 0 < sizeInventory; ++n2) {
            inventory.setInventorySlotContents(0, container.getSlot(n2).getStack());
            int n3 = 0;
            ++n3;
        }
    }
    
    public static void saveInventoryFields(final IInventory inventory, final IInventory inventory2) {
        while (0 < inventory.getFieldCount()) {
            inventory2.setField(0, inventory.getField(0));
            int n = 0;
            ++n;
        }
    }
    
    public static void saveTileEntity(final BlockPos blockPos, final TileEntity tileEntity) {
        final ChunkCoordIntPair chunkCoordIntPair = new ChunkCoordIntPair(blockPos.getX() / 16, blockPos.getZ() / 16);
        if (!WDL.newTileEntities.containsKey(chunkCoordIntPair)) {
            WDL.newTileEntities.put(chunkCoordIntPair, new HashMap<ChunkCoordIntPair, HashMap<ChunkCoordIntPair, HashMap<ChunkCoordIntPair, HashMap>>>());
        }
        ((Map<BlockPos, TileEntity>)WDL.newTileEntities.get(chunkCoordIntPair)).put(blockPos, tileEntity);
    }
    
    public static String getDebugInfo() {
        final StringBuilder sb = new StringBuilder();
        sb.append("### CORE INFO\n\n");
        sb.append("WDL version: ").append("1.8.9a-beta2").append('\n');
        sb.append("Launched version: ").append(Minecraft.getInstance().getVersion()).append('\n');
        sb.append("Client brand: ").append(ClientBrandRetriever.getClientModName()).append('\n');
        sb.append("File location: ");
        sb.append(new File(WDL.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath().replace("\u2f60\u2f66\u2f70\u2f67\u2f3b\u2f7b\u2f74\u2f78\u2f70", "<USERNAME>"));
        sb.append("\n\n### EXTENSIONS\n\n");
        final Map wdlMods = WDLApi.getWDLMods();
        sb.append(wdlMods.size()).append(" loaded\n");
        for (final Map.Entry<String, V> entry : wdlMods.entrySet()) {
            sb.append("\n#### ").append(entry.getKey()).append("\n\n");
            sb.append(((WDLApi.ModInfo)entry.getValue()).getInfo());
        }
        sb.append("\n### STATE\n\n");
        sb.append("minecraft: ").append(WDL.minecraft).append('\n');
        sb.append("worldClient: ").append(WDL.worldClient).append('\n');
        sb.append("networkManager: ").append(WDL.networkManager).append('\n');
        sb.append("thePlayer: ").append(WDL.thePlayer).append('\n');
        sb.append("windowContainer: ").append(WDL.windowContainer).append('\n');
        sb.append("lastClickedBlock: ").append(WDL.lastClickedBlock).append('\n');
        sb.append("lastEntity: ").append(WDL.lastEntity).append('\n');
        sb.append("saveHandler: ").append(WDL.saveHandler).append('\n');
        sb.append("chunkLoader: ").append(WDL.chunkLoader).append('\n');
        sb.append("newTileEntities: ").append(WDL.newTileEntities).append('\n');
        sb.append("newEntities: ").append(WDL.newEntities).append('\n');
        sb.append("newMapDatas: ").append(WDL.newMapDatas).append('\n');
        sb.append("downloading: ").append(WDL.downloading).append('\n');
        sb.append("isMultiworld: ").append(WDL.isMultiworld).append('\n');
        sb.append("propsFound: ").append(WDL.propsFound).append('\n');
        sb.append("startOnChange: ").append(WDL.startOnChange).append('\n');
        sb.append("overrideLastModifiedCheck: ").append(WDL.overrideLastModifiedCheck).append('\n');
        sb.append("saving: ").append(WDL.saving).append('\n');
        sb.append("worldLoadingDeferred: ").append(WDL.worldLoadingDeferred).append('\n');
        sb.append("worldName: ").append(WDL.worldName).append('\n');
        sb.append("baseFolderName: ").append(WDL.baseFolderName).append('\n');
        sb.append("### CONNECTED SERVER\n\n");
        final ServerData currentServerData = Minecraft.getInstance().getCurrentServerData();
        if (currentServerData == null) {
            sb.append("No data\n");
        }
        else {
            sb.append("Name: ").append(currentServerData.serverName).append('\n');
            sb.append("IP: ").append(currentServerData.serverIP).append('\n');
        }
        sb.append("\n### PROPERTIES\n\n");
        sb.append("\n#### BASE\n\n");
        if (WDL.baseProps != null) {
            if (!WDL.baseProps.isEmpty()) {
                for (final Map.Entry<Object, Object> entry2 : WDL.baseProps.entrySet()) {
                    sb.append(entry2.getKey()).append(": ").append(entry2.getValue());
                    sb.append('\n');
                }
            }
            else {
                sb.append("empty\n");
            }
        }
        else {
            sb.append("null\n");
        }
        sb.append("\n#### WORLD\n\n");
        if (WDL.worldProps != null) {
            if (!WDL.worldProps.isEmpty()) {
                for (final Map.Entry<Object, Object> entry3 : WDL.worldProps.entrySet()) {
                    sb.append(entry3.getKey()).append(": ").append(entry3.getValue());
                    sb.append('\n');
                }
            }
            else {
                sb.append("empty\n");
            }
        }
        else {
            sb.append("null\n");
        }
        sb.append("\n#### DEFAULT\n\n");
        if (WDL.globalProps != null) {
            if (!WDL.globalProps.isEmpty()) {
                for (final Map.Entry<Object, Object> entry4 : WDL.globalProps.entrySet()) {
                    sb.append(entry4.getKey()).append(": ").append(entry4.getValue());
                    sb.append('\n');
                }
            }
            else {
                sb.append("empty\n");
            }
        }
        else {
            sb.append("null\n");
        }
        return sb.toString();
    }
    
    public static void crashed(final Throwable t, final String s) {
        CrashReport crashReport2;
        if (t instanceof ReportedException) {
            final CrashReport crashReport = ((ReportedException)t).getCrashReport();
            crashReport2 = CrashReport.makeCrashReport(crashReport.getCrashCause(), String.valueOf(s) + " (" + crashReport.getCauseStackTraceOrString() + ")");
            ((List)ReflectionUtils.stealAndGetField(crashReport2, List.class)).addAll((Collection)ReflectionUtils.stealAndGetField(crashReport, List.class));
        }
        else {
            crashReport2 = CrashReport.makeCrashReport(t, s);
        }
        WDL.minecraft.crashed(crashReport2);
    }
    
    public static String getMinecraftVersion() {
        final Map func_175596_ai = Minecraft.func_175596_ai();
        if (func_175596_ai.containsKey("X-Minecraft-Version")) {
            return func_175596_ai.get("X-Minecraft-Version");
        }
        return "1.8.9";
    }
    
    public static String getMinecraftVersionInfo() {
        return String.format("Minecraft %s (%s/%s)", getMinecraftVersion(), Minecraft.getInstance().getVersion(), ClientBrandRetriever.getClientModName());
    }
}
