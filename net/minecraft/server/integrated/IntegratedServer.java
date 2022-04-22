package net.minecraft.server.integrated;

import net.minecraft.server.*;
import net.minecraft.client.multiplayer.*;
import org.apache.logging.log4j.*;
import net.minecraft.server.management.*;
import net.minecraft.world.demo.*;
import net.minecraft.command.*;
import optifine.*;
import net.minecraft.world.storage.*;
import java.io.*;
import net.minecraft.client.settings.*;
import net.minecraft.world.*;
import net.minecraft.crash.*;
import net.minecraft.client.*;
import net.minecraft.profiler.*;
import net.minecraft.util.*;
import java.net.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import java.util.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.*;

public class IntegratedServer extends MinecraftServer
{
    private static final Logger logger;
    private final Minecraft mc;
    private final WorldSettings theWorldSettings;
    private boolean isGamePaused;
    private boolean isPublic;
    private ThreadLanServerPing lanServerPing;
    
    static {
        logger = LogManager.getLogger();
    }
    
    public IntegratedServer(final Minecraft mc) {
        super(mc.getProxy(), new File(Minecraft.mcDataDir, IntegratedServer.USER_CACHE_FILE.getName()));
        this.mc = mc;
        this.theWorldSettings = null;
    }
    
    public IntegratedServer(final Minecraft mc, final String folderName, final String worldName, final WorldSettings worldSettings) {
        super(new File(Minecraft.mcDataDir, "saves"), mc.getProxy(), new File(Minecraft.mcDataDir, IntegratedServer.USER_CACHE_FILE.getName()));
        this.setServerOwner(Minecraft.getSession().getUsername());
        this.setFolderName(folderName);
        this.setWorldName(worldName);
        this.setDemo(mc.isDemo());
        this.canCreateBonusChest(worldSettings.isBonusChestEnabled());
        this.setBuildLimit(256);
        this.setConfigManager(new IntegratedPlayerList(this));
        this.mc = mc;
        this.theWorldSettings = (this.isDemo() ? DemoWorldServer.demoWorldSettings : worldSettings);
    }
    
    @Override
    protected ServerCommandManager createNewCommandManager() {
        return new IntegratedServerCommandManager();
    }
    
    @Override
    protected void loadAllWorlds(final String s, final String worldName, final long n, final WorldType worldType, final String s2) {
        this.convertMapIfNeeded(s);
        final ISaveHandler saveLoader = this.getActiveAnvilConverter().getSaveLoader(s, true);
        this.setResourcePackFromWorld(this.getFolderName(), saveLoader);
        WorldInfo loadWorldInfo = saveLoader.loadWorldInfo();
        if (Reflector.DimensionManager.exists()) {
            final WorldServer worldServer = (WorldServer)(this.isDemo() ? new DemoWorldServer(this, saveLoader, loadWorldInfo, 0, this.theProfiler).init() : ((WorldServer)new WorldServerOF(this, saveLoader, loadWorldInfo, 0, this.theProfiler).init()));
            worldServer.initialize(this.theWorldSettings);
            Integer[] array;
            while (0 < (array = (Integer[])Reflector.call(Reflector.DimensionManager_getStaticDimensionIDs, new Object[0])).length) {
                final int intValue = array[0];
                final WorldServer worldServer2 = (WorldServer)((intValue == 0) ? worldServer : new WorldServerMulti(this, saveLoader, intValue, worldServer, this.theProfiler).init());
                worldServer2.addWorldAccess(new WorldManager(this, worldServer2));
                if (!this.isSinglePlayer()) {
                    worldServer2.getWorldInfo().setGameType(this.getGameType());
                }
                if (Reflector.EventBus.exists()) {
                    Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, worldServer2);
                }
                int n2 = 0;
                ++n2;
            }
            this.getConfigurationManager().setPlayerManager(new WorldServer[] { worldServer });
            if (worldServer.getWorldInfo().getDifficulty() == null) {
                this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
            }
        }
        else {
            this.worldServers = new WorldServer[3];
            this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
            this.setResourcePackFromWorld(this.getFolderName(), saveLoader);
            if (loadWorldInfo == null) {
                loadWorldInfo = new WorldInfo(this.theWorldSettings, worldName);
            }
            else {
                loadWorldInfo.setWorldName(worldName);
            }
            while (0 < this.worldServers.length) {
                if (this.isDemo()) {
                    this.worldServers[0] = (WorldServer)new DemoWorldServer(this, saveLoader, loadWorldInfo, 1, this.theProfiler).init();
                }
                else {
                    this.worldServers[0] = (WorldServer)new WorldServerOF(this, saveLoader, loadWorldInfo, 1, this.theProfiler).init();
                }
                this.worldServers[0].initialize(this.theWorldSettings);
                this.worldServers[0].addWorldAccess(new WorldManager(this, this.worldServers[0]));
                int n3 = 0;
                ++n3;
            }
            this.getConfigurationManager().setPlayerManager(this.worldServers);
            if (this.worldServers[0].getWorldInfo().getDifficulty() == null) {
                this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
            }
        }
        this.initialWorldChunkLoad();
    }
    
    @Override
    protected boolean startServer() throws IOException {
        IntegratedServer.logger.info("Starting integrated minecraft server version 1.8");
        this.setOnlineMode(true);
        this.setCanSpawnAnimals(true);
        this.setCanSpawnNPCs(true);
        this.setAllowPvp(true);
        this.setAllowFlight(true);
        IntegratedServer.logger.info("Generating keypair");
        this.setKeyPair(CryptManager.generateKeyPair());
        if (Reflector.FMLCommonHandler_handleServerAboutToStart.exists() && !Reflector.callBoolean(Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]), Reflector.FMLCommonHandler_handleServerAboutToStart, this)) {
            return false;
        }
        this.loadAllWorlds(this.getFolderName(), this.getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.getWorldName());
        this.setMOTD(String.valueOf(this.getServerOwner()) + " - " + this.worldServers[0].getWorldInfo().getWorldName());
        if (Reflector.FMLCommonHandler_handleServerStarting.exists()) {
            final Object call = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
            if (Reflector.FMLCommonHandler_handleServerStarting.getReturnType() == Boolean.TYPE) {
                return Reflector.callBoolean(call, Reflector.FMLCommonHandler_handleServerStarting, this);
            }
            Reflector.callVoid(call, Reflector.FMLCommonHandler_handleServerStarting, this);
        }
        return true;
    }
    
    @Override
    public void tick() {
        final boolean isGamePaused = this.isGamePaused;
        this.isGamePaused = (Minecraft.getMinecraft().getNetHandler() != null && Minecraft.getMinecraft().isGamePaused());
        if (!isGamePaused && this.isGamePaused) {
            IntegratedServer.logger.info("Saving and pausing game...");
            this.getConfigurationManager().saveAllPlayerData();
            this.saveAllWorlds(false);
        }
        if (this.isGamePaused) {
            final Queue futureTaskQueue = this.futureTaskQueue;
            final Queue futureTaskQueue2 = this.futureTaskQueue;
            // monitorenter(futureTaskQueue3 = this.futureTaskQueue)
            while (!this.futureTaskQueue.isEmpty()) {
                if (Reflector.FMLCommonHandler_callFuture.exists()) {
                    Reflector.callVoid(Reflector.FMLCommonHandler_callFuture, this.futureTaskQueue.poll());
                }
                else {
                    this.futureTaskQueue.poll().run();
                }
            }
        }
        // monitorexit(futureTaskQueue3)
        else {
            super.tick();
            final GameSettings gameSettings = this.mc.gameSettings;
            if (GameSettings.renderDistanceChunks != this.getConfigurationManager().getViewDistance()) {
                final Logger logger = IntegratedServer.logger;
                final String s = "Changing view distance to {}, from {}";
                final Object[] array = new Object[2];
                final int n = 0;
                final GameSettings gameSettings2 = this.mc.gameSettings;
                array[n] = GameSettings.renderDistanceChunks;
                array[1] = this.getConfigurationManager().getViewDistance();
                logger.info(s, array);
                final ServerConfigurationManager configurationManager = this.getConfigurationManager();
                final GameSettings gameSettings3 = this.mc.gameSettings;
                configurationManager.setViewDistance(GameSettings.renderDistanceChunks);
            }
            if (Minecraft.theWorld != null) {
                final WorldInfo worldInfo = this.worldServers[0].getWorldInfo();
                final WorldInfo worldInfo2 = Minecraft.theWorld.getWorldInfo();
                if (!worldInfo.isDifficultyLocked() && worldInfo2.getDifficulty() != worldInfo.getDifficulty()) {
                    IntegratedServer.logger.info("Changing difficulty to {}, from {}", worldInfo2.getDifficulty(), worldInfo.getDifficulty());
                    this.setDifficultyForAllWorlds(worldInfo2.getDifficulty());
                }
                else if (worldInfo2.isDifficultyLocked() && !worldInfo.isDifficultyLocked()) {
                    IntegratedServer.logger.info("Locking difficulty to {}", worldInfo2.getDifficulty());
                    final WorldServer[] worldServers = this.worldServers;
                    while (0 < worldServers.length) {
                        final WorldServer worldServer = worldServers[0];
                        if (worldServer != null) {
                            worldServer.getWorldInfo().setDifficultyLocked(true);
                        }
                        int n2 = 0;
                        ++n2;
                    }
                }
            }
        }
    }
    
    @Override
    public boolean canStructuresSpawn() {
        return false;
    }
    
    @Override
    public WorldSettings.GameType getGameType() {
        return this.theWorldSettings.getGameType();
    }
    
    @Override
    public EnumDifficulty getDifficulty() {
        return (Minecraft.theWorld == null) ? this.mc.gameSettings.difficulty : Minecraft.theWorld.getWorldInfo().getDifficulty();
    }
    
    @Override
    public boolean isHardcore() {
        return this.theWorldSettings.getHardcoreEnabled();
    }
    
    @Override
    public File getDataDirectory() {
        return Minecraft.mcDataDir;
    }
    
    @Override
    public boolean isDedicatedServer() {
        return false;
    }
    
    @Override
    protected void finalTick(final CrashReport crashReport) {
        this.mc.crashed(crashReport);
    }
    
    @Override
    public CrashReport addServerInfoToCrashReport(CrashReport addServerInfoToCrashReport) {
        addServerInfoToCrashReport = super.addServerInfoToCrashReport(addServerInfoToCrashReport);
        addServerInfoToCrashReport.getCategory().addCrashSectionCallable("Type", new Callable() {
            final IntegratedServer this$0;
            
            @Override
            public String call() {
                return "Integrated Server (map_client.txt)";
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        addServerInfoToCrashReport.getCategory().addCrashSectionCallable("Is Modded", new Callable() {
            final IntegratedServer this$0;
            
            @Override
            public String call() {
                final String clientModName = ClientBrandRetriever.getClientModName();
                if (!clientModName.equals("vanilla")) {
                    return "Definitely; Client brand changed to '" + clientModName + "'";
                }
                final String serverModName = this.this$0.getServerModName();
                return serverModName.equals("vanilla") ? ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.") : ("Definitely; Server brand changed to '" + serverModName + "'");
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        return addServerInfoToCrashReport;
    }
    
    @Override
    public void setDifficultyForAllWorlds(final EnumDifficulty enumDifficulty) {
        super.setDifficultyForAllWorlds(enumDifficulty);
        if (Minecraft.theWorld != null) {
            Minecraft.theWorld.getWorldInfo().setDifficulty(enumDifficulty);
        }
    }
    
    @Override
    public void addServerStatsToSnooper(final PlayerUsageSnooper playerUsageSnooper) {
        super.addServerStatsToSnooper(playerUsageSnooper);
        playerUsageSnooper.addClientStat("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
    }
    
    @Override
    public boolean isSnooperEnabled() {
        return Minecraft.getMinecraft().isSnooperEnabled();
    }
    
    @Override
    public String shareToLAN(final WorldSettings.GameType gameType, final boolean commandsAllowedForAll) {
        HttpUtil.getSuitableLanPort();
        this.getNetworkSystem().addLanEndpoint(null, 25564);
        IntegratedServer.logger.info("Started on " + 25564);
        this.isPublic = true;
        (this.lanServerPing = new ThreadLanServerPing(this.getMOTD(), new StringBuilder(String.valueOf(25564)).toString())).start();
        this.getConfigurationManager().func_152604_a(gameType);
        this.getConfigurationManager().setCommandsAllowedForAll(commandsAllowedForAll);
        return new StringBuilder(String.valueOf(25564)).toString();
    }
    
    @Override
    public void stopServer() {
        super.stopServer();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }
    
    @Override
    public void initiateShutdown() {
        Futures.getUnchecked(this.addScheduledTask(new Runnable() {
            final IntegratedServer this$0;
            
            @Override
            public void run() {
                final Iterator<EntityPlayerMP> iterator = Lists.newArrayList(this.this$0.getConfigurationManager().playerEntityList).iterator();
                while (iterator.hasNext()) {
                    this.this$0.getConfigurationManager().playerLoggedOut(iterator.next());
                }
            }
        }));
        super.initiateShutdown();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }
    
    public void func_175592_a() {
        this.func_175585_v();
    }
    
    public boolean getPublic() {
        return this.isPublic;
    }
    
    @Override
    public void setGameType(final WorldSettings.GameType gameType) {
        this.getConfigurationManager().func_152604_a(gameType);
    }
    
    @Override
    public boolean isCommandBlockEnabled() {
        return true;
    }
    
    @Override
    public int getOpPermissionLevel() {
        return 4;
    }
}
