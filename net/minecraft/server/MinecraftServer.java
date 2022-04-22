package net.minecraft.server;

import net.minecraft.profiler.*;
import net.minecraft.network.*;
import java.net.*;
import java.security.*;
import com.mojang.authlib.yggdrasil.*;
import com.mojang.authlib.minecraft.*;
import net.minecraft.server.management.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.world.chunk.storage.*;
import net.minecraft.world.demo.*;
import net.minecraft.world.storage.*;
import net.minecraft.crash.*;
import javax.imageio.*;
import org.apache.commons.lang3.*;
import java.io.*;
import io.netty.handler.codec.base64.*;
import com.google.common.base.*;
import io.netty.buffer.*;
import java.awt.image.*;
import com.mojang.authlib.*;
import java.util.*;
import java.awt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.command.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.*;

public abstract class MinecraftServer implements ICommandSender, Runnable, IThreadListener, IPlayerUsage
{
    private static final Logger logger;
    public static final File USER_CACHE_FILE;
    private static MinecraftServer mcServer;
    private final ISaveFormat anvilConverterForAnvilFile;
    private final PlayerUsageSnooper usageSnooper;
    private final File anvilFile;
    private final List playersOnline;
    private final ICommandManager commandManager;
    public final Profiler theProfiler;
    private final NetworkSystem networkSystem;
    private final ServerStatusResponse statusResponse;
    private final Random random;
    private int serverPort;
    public WorldServer[] worldServers;
    private ServerConfigurationManager serverConfigManager;
    private boolean serverRunning;
    private boolean serverStopped;
    private int tickCounter;
    protected final Proxy serverProxy;
    public String currentTask;
    public int percentDone;
    private boolean onlineMode;
    private boolean canSpawnAnimals;
    private boolean canSpawnNPCs;
    private boolean pvpEnabled;
    private boolean allowFlight;
    private String motd;
    private int buildLimit;
    private int maxPlayerIdleMinutes;
    public final long[] tickTimeArray;
    public long[][] timeOfLastDimensionTick;
    private KeyPair serverKeyPair;
    private String serverOwner;
    private String folderName;
    private String worldName;
    private boolean isDemo;
    private boolean enableBonusChest;
    private boolean worldIsBeingDeleted;
    private String resourcePackUrl;
    private String resourcePackHash;
    private boolean serverIsRunning;
    private long timeOfLastWarning;
    private String userMessage;
    private boolean startProfiling;
    private boolean isGamemodeForced;
    private final YggdrasilAuthenticationService authService;
    private final MinecraftSessionService sessionService;
    private long nanoTimeSinceStatusRefresh;
    private final GameProfileRepository profileRepo;
    private final PlayerProfileCache profileCache;
    protected final Queue futureTaskQueue;
    private Thread serverThread;
    private long currentTime;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001462";
        logger = LogManager.getLogger();
        USER_CACHE_FILE = new File("usercache.json");
    }
    
    public MinecraftServer(final Proxy serverProxy, final File file) {
        this.usageSnooper = new PlayerUsageSnooper("server", this, getCurrentTimeMillis());
        this.playersOnline = Lists.newArrayList();
        this.theProfiler = new Profiler();
        this.statusResponse = new ServerStatusResponse();
        this.random = new Random();
        this.serverPort = -1;
        this.serverRunning = true;
        this.maxPlayerIdleMinutes = 0;
        this.tickTimeArray = new long[100];
        this.resourcePackUrl = "";
        this.resourcePackHash = "";
        this.nanoTimeSinceStatusRefresh = 0L;
        this.futureTaskQueue = Queues.newArrayDeque();
        this.currentTime = getCurrentTimeMillis();
        this.serverProxy = serverProxy;
        MinecraftServer.mcServer = this;
        this.anvilFile = null;
        this.networkSystem = null;
        this.profileCache = new PlayerProfileCache(this, file);
        this.commandManager = null;
        this.anvilConverterForAnvilFile = null;
        this.authService = new YggdrasilAuthenticationService(serverProxy, UUID.randomUUID().toString());
        this.sessionService = this.authService.createMinecraftSessionService();
        this.profileRepo = this.authService.createProfileRepository();
    }
    
    public MinecraftServer(final File anvilFile, final Proxy serverProxy, final File file) {
        this.usageSnooper = new PlayerUsageSnooper("server", this, getCurrentTimeMillis());
        this.playersOnline = Lists.newArrayList();
        this.theProfiler = new Profiler();
        this.statusResponse = new ServerStatusResponse();
        this.random = new Random();
        this.serverPort = -1;
        this.serverRunning = true;
        this.maxPlayerIdleMinutes = 0;
        this.tickTimeArray = new long[100];
        this.resourcePackUrl = "";
        this.resourcePackHash = "";
        this.nanoTimeSinceStatusRefresh = 0L;
        this.futureTaskQueue = Queues.newArrayDeque();
        this.currentTime = getCurrentTimeMillis();
        this.serverProxy = serverProxy;
        MinecraftServer.mcServer = this;
        this.anvilFile = anvilFile;
        this.networkSystem = new NetworkSystem(this);
        this.profileCache = new PlayerProfileCache(this, file);
        this.commandManager = this.createNewCommandManager();
        this.anvilConverterForAnvilFile = new AnvilSaveConverter(anvilFile);
        this.authService = new YggdrasilAuthenticationService(serverProxy, UUID.randomUUID().toString());
        this.sessionService = this.authService.createMinecraftSessionService();
        this.profileRepo = this.authService.createProfileRepository();
    }
    
    protected ServerCommandManager createNewCommandManager() {
        return new ServerCommandManager();
    }
    
    protected abstract boolean startServer() throws IOException;
    
    protected void convertMapIfNeeded(final String s) {
        if (this.getActiveAnvilConverter().isOldMapFormat(s)) {
            MinecraftServer.logger.info("Converting map!");
            this.setUserMessage("menu.convertingLevel");
            this.getActiveAnvilConverter().convertMapFormat(s, new IProgressUpdate() {
                private long startTime = System.currentTimeMillis();
                private static final String __OBFID;
                final MinecraftServer this$0;
                
                @Override
                public void displaySavingString(final String s) {
                }
                
                @Override
                public void resetProgressAndMessage(final String s) {
                }
                
                @Override
                public void setLoadingProgress(final int n) {
                    if (System.currentTimeMillis() - this.startTime >= 1000L) {
                        this.startTime = System.currentTimeMillis();
                        MinecraftServer.access$0().info("Converting... " + n + "%");
                    }
                }
                
                @Override
                public void setDoneWorking() {
                }
                
                @Override
                public void displayLoadingString(final String s) {
                }
                
                static {
                    __OBFID = "CL_00001417";
                }
            });
        }
    }
    
    protected synchronized void setUserMessage(final String userMessage) {
        this.userMessage = userMessage;
    }
    
    public synchronized String getUserMessage() {
        return this.userMessage;
    }
    
    protected void loadAllWorlds(final String s, final String worldName, final long n, final WorldType worldType, final String worldName2) {
        this.convertMapIfNeeded(s);
        this.setUserMessage("menu.loadingLevel");
        this.worldServers = new WorldServer[3];
        this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
        final ISaveHandler saveLoader = this.anvilConverterForAnvilFile.getSaveLoader(s, true);
        this.setResourcePackFromWorld(this.getFolderName(), saveLoader);
        WorldInfo loadWorldInfo = saveLoader.loadWorldInfo();
        WorldSettings demoWorldSettings;
        if (loadWorldInfo == null) {
            if (this.isDemo()) {
                demoWorldSettings = DemoWorldServer.demoWorldSettings;
            }
            else {
                demoWorldSettings = new WorldSettings(n, this.getGameType(), this.canStructuresSpawn(), this.isHardcore(), worldType);
                demoWorldSettings.setWorldName(worldName2);
                if (this.enableBonusChest) {
                    demoWorldSettings.enableBonusChest();
                }
            }
            loadWorldInfo = new WorldInfo(demoWorldSettings, worldName);
        }
        else {
            loadWorldInfo.setWorldName(worldName);
            demoWorldSettings = new WorldSettings(loadWorldInfo);
        }
        while (0 < this.worldServers.length) {
            if (false == true) {}
            if (0 == 2) {}
            if (!false) {
                if (this.isDemo()) {
                    this.worldServers[0] = (WorldServer)new DemoWorldServer(this, saveLoader, loadWorldInfo, 1, this.theProfiler).init();
                }
                else {
                    this.worldServers[0] = (WorldServer)new WorldServer(this, saveLoader, loadWorldInfo, 1, this.theProfiler).init();
                }
                this.worldServers[0].initialize(demoWorldSettings);
            }
            else {
                this.worldServers[0] = (WorldServer)new WorldServerMulti(this, saveLoader, 1, this.worldServers[0], this.theProfiler).init();
            }
            this.worldServers[0].addWorldAccess(new WorldManager(this, this.worldServers[0]));
            if (!this.isSinglePlayer()) {
                this.worldServers[0].getWorldInfo().setGameType(this.getGameType());
            }
            int n2 = 0;
            ++n2;
        }
        this.serverConfigManager.setPlayerManager(this.worldServers);
        this.setDifficultyForAllWorlds(this.getDifficulty());
        this.initialWorldChunkLoad();
    }
    
    protected void initialWorldChunkLoad() {
        this.setUserMessage("menu.generatingTerrain");
        MinecraftServer.logger.info("Preparing start region for level " + 0);
        final WorldServer worldServer = this.worldServers[0];
        final BlockPos spawnPoint = worldServer.getSpawnPoint();
        long currentTimeMillis = getCurrentTimeMillis();
        while (-192 <= 192 && this.isServerRunning()) {
            while (-192 <= 192 && this.isServerRunning()) {
                final long currentTimeMillis2 = getCurrentTimeMillis();
                if (currentTimeMillis2 - currentTimeMillis > 1000L) {
                    this.outputPercentRemaining("Preparing spawn area", 0);
                    currentTimeMillis = currentTimeMillis2;
                }
                int n = 0;
                ++n;
                worldServer.theChunkProviderServer.loadChunk(spawnPoint.getX() - 192 >> 4, spawnPoint.getZ() - 192 >> 4);
                final int n2;
                n2 += 16;
            }
            final int n3;
            n3 += 16;
        }
        this.clearCurrentTask();
    }
    
    protected void setResourcePackFromWorld(final String s, final ISaveHandler saveHandler) {
        final File file = new File(saveHandler.getWorldDirectory(), "resources.zip");
        if (file.isFile()) {
            this.setResourcePack("level://" + s + "/" + file.getName(), "");
        }
    }
    
    public abstract boolean canStructuresSpawn();
    
    public abstract WorldSettings.GameType getGameType();
    
    public abstract EnumDifficulty getDifficulty();
    
    public abstract boolean isHardcore();
    
    public abstract int getOpPermissionLevel();
    
    protected void outputPercentRemaining(final String currentTask, final int percentDone) {
        this.currentTask = currentTask;
        this.percentDone = percentDone;
        MinecraftServer.logger.info(String.valueOf(currentTask) + ": " + percentDone + "%");
    }
    
    protected void clearCurrentTask() {
        this.currentTask = null;
        this.percentDone = 0;
    }
    
    protected void saveAllWorlds(final boolean b) {
        if (!this.worldIsBeingDeleted) {
            final WorldServer[] worldServers = this.worldServers;
            while (0 < worldServers.length) {
                final WorldServer worldServer = worldServers[0];
                if (worldServer != null) {
                    if (!b) {
                        MinecraftServer.logger.info("Saving chunks for level '" + worldServer.getWorldInfo().getWorldName() + "'/" + worldServer.provider.getDimensionName());
                    }
                    worldServer.saveAllChunks(true, null);
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    public void stopServer() {
        if (!this.worldIsBeingDeleted) {
            MinecraftServer.logger.info("Stopping server");
            if (this.getNetworkSystem() != null) {
                this.getNetworkSystem().terminateEndpoints();
            }
            if (this.serverConfigManager != null) {
                MinecraftServer.logger.info("Saving players");
                this.serverConfigManager.saveAllPlayerData();
                this.serverConfigManager.removeAllPlayers();
            }
            if (this.worldServers != null) {
                MinecraftServer.logger.info("Saving worlds");
                this.saveAllWorlds(false);
                while (0 < this.worldServers.length) {
                    this.worldServers[0].flush();
                    int n = 0;
                    ++n;
                }
            }
            if (this.usageSnooper.isSnooperRunning()) {
                this.usageSnooper.stopSnooper();
            }
        }
    }
    
    public boolean isServerRunning() {
        return this.serverRunning;
    }
    
    public void initiateShutdown() {
        this.serverRunning = false;
    }
    
    protected void func_175585_v() {
        MinecraftServer.mcServer = this;
    }
    
    @Override
    public void run() {
        if (this.startServer()) {
            this.currentTime = getCurrentTimeMillis();
            long n = 0L;
            this.statusResponse.setServerDescription(new ChatComponentText(this.motd));
            this.statusResponse.setProtocolVersionInfo(new ServerStatusResponse.MinecraftProtocolVersionIdentifier("1.8", 47));
            this.addFaviconToStatusResponse(this.statusResponse);
            while (this.serverRunning) {
                final long currentTimeMillis = getCurrentTimeMillis();
                long n2 = currentTimeMillis - this.currentTime;
                if (n2 > 2000L && this.currentTime - this.timeOfLastWarning >= 15000L) {
                    MinecraftServer.logger.warn("Can't keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", n2, n2 / 50L);
                    n2 = 2000L;
                    this.timeOfLastWarning = this.currentTime;
                }
                if (n2 < 0L) {
                    MinecraftServer.logger.warn("Time ran backwards! Did the system time change?");
                    n2 = 0L;
                }
                n += n2;
                this.currentTime = currentTimeMillis;
                if (this.worldServers[0].areAllPlayersAsleep()) {
                    this.tick();
                    n = 0L;
                }
                else {
                    while (n > 50L) {
                        n -= 50L;
                        this.tick();
                    }
                }
                Thread.sleep(Math.max(1L, 50L - n));
                this.serverIsRunning = true;
            }
        }
        else {
            this.finalTick(null);
        }
        this.stopServer();
        this.serverStopped = true;
        this.systemExitNow();
    }
    
    private void addFaviconToStatusResponse(final ServerStatusResponse serverStatusResponse) {
        final File file = this.getFile("server-icon.png");
        if (file.isFile()) {
            final ByteBuf buffer = Unpooled.buffer();
            final BufferedImage read = ImageIO.read(file);
            Validate.validState(read.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
            Validate.validState(read.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
            ImageIO.write(read, "PNG", new ByteBufOutputStream(buffer));
            serverStatusResponse.setFavicon("data:image/png;base64," + Base64.encode(buffer).toString(Charsets.UTF_8));
            buffer.release();
        }
    }
    
    public File getDataDirectory() {
        return new File(".");
    }
    
    protected void finalTick(final CrashReport crashReport) {
    }
    
    protected void systemExitNow() {
    }
    
    public void tick() {
        final long nanoTime = System.nanoTime();
        ++this.tickCounter;
        if (this.startProfiling) {
            this.startProfiling = false;
            this.theProfiler.profilingEnabled = true;
            this.theProfiler.clearProfiling();
        }
        this.theProfiler.startSection("root");
        this.updateTimeLightAndEntities();
        if (nanoTime - this.nanoTimeSinceStatusRefresh >= 5000000000L) {
            this.nanoTimeSinceStatusRefresh = nanoTime;
            this.statusResponse.setPlayerCountData(new ServerStatusResponse.PlayerCountData(this.getMaxPlayers(), this.getCurrentPlayerCount()));
            final GameProfile[] players = new GameProfile[Math.min(this.getCurrentPlayerCount(), 12)];
            final int randomIntegerInRange = MathHelper.getRandomIntegerInRange(this.random, 0, this.getCurrentPlayerCount() - players.length);
            while (0 < players.length) {
                players[0] = ((EntityPlayerMP)this.serverConfigManager.playerEntityList.get(randomIntegerInRange + 0)).getGameProfile();
                int n = 0;
                ++n;
            }
            Collections.shuffle(Arrays.asList(players));
            this.statusResponse.getPlayerCountData().setPlayers(players);
        }
        if (this.tickCounter % 900 == 0) {
            this.theProfiler.startSection("save");
            this.serverConfigManager.saveAllPlayerData();
            this.saveAllWorlds(true);
            this.theProfiler.endSection();
        }
        this.theProfiler.startSection("tallying");
        this.tickTimeArray[this.tickCounter % 100] = System.nanoTime() - nanoTime;
        this.theProfiler.endSection();
        this.theProfiler.startSection("snooper");
        if (!this.usageSnooper.isSnooperRunning() && this.tickCounter > 100) {
            this.usageSnooper.startSnooper();
        }
        if (this.tickCounter % 6000 == 0) {
            this.usageSnooper.addMemoryStatsToSnooper();
        }
        this.theProfiler.endSection();
        this.theProfiler.endSection();
    }
    
    public void updateTimeLightAndEntities() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //     4: ldc_w           "jobs"
        //     7: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //    10: aload_0        
        //    11: getfield        net/minecraft/server/MinecraftServer.futureTaskQueue:Ljava/util/Queue;
        //    14: astore_1       
        //    15: aload_0        
        //    16: getfield        net/minecraft/server/MinecraftServer.futureTaskQueue:Ljava/util/Queue;
        //    19: dup            
        //    20: astore_2       
        //    21: monitorenter   
        //    22: goto            53
        //    25: aload_0        
        //    26: getfield        net/minecraft/server/MinecraftServer.futureTaskQueue:Ljava/util/Queue;
        //    29: invokeinterface java/util/Queue.poll:()Ljava/lang/Object;
        //    34: checkcast       Ljava/util/concurrent/FutureTask;
        //    37: invokevirtual   java/util/concurrent/FutureTask.run:()V
        //    40: goto            53
        //    43: astore_3       
        //    44: getstatic       net/minecraft/server/MinecraftServer.logger:Lorg/apache/logging/log4j/Logger;
        //    47: aload_3        
        //    48: invokeinterface org/apache/logging/log4j/Logger.fatal:(Ljava/lang/Object;)V
        //    53: aload_0        
        //    54: getfield        net/minecraft/server/MinecraftServer.futureTaskQueue:Ljava/util/Queue;
        //    57: invokeinterface java/util/Queue.isEmpty:()Z
        //    62: ifeq            25
        //    65: aload_2        
        //    66: monitorexit    
        //    67: goto            73
        //    70: aload_2        
        //    71: monitorexit    
        //    72: athrow         
        //    73: aload_0        
        //    74: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //    77: ldc_w           "levels"
        //    80: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //    83: goto            341
        //    86: invokestatic    java/lang/System.nanoTime:()J
        //    89: lstore_3       
        //    90: iconst_0       
        //    91: ifeq            101
        //    94: aload_0        
        //    95: invokevirtual   net/minecraft/server/MinecraftServer.getAllowNether:()Z
        //    98: ifeq            319
        //   101: aload_0        
        //   102: getfield        net/minecraft/server/MinecraftServer.worldServers:[Lnet/minecraft/world/WorldServer;
        //   105: iconst_0       
        //   106: aaload         
        //   107: astore          5
        //   109: aload_0        
        //   110: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   113: aload           5
        //   115: invokevirtual   net/minecraft/world/WorldServer.getWorldInfo:()Lnet/minecraft/world/storage/WorldInfo;
        //   118: invokevirtual   net/minecraft/world/storage/WorldInfo.getWorldName:()Ljava/lang/String;
        //   121: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   124: aload_0        
        //   125: getfield        net/minecraft/server/MinecraftServer.tickCounter:I
        //   128: bipush          20
        //   130: irem           
        //   131: ifne            194
        //   134: aload_0        
        //   135: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   138: ldc_w           "timeSync"
        //   141: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   144: aload_0        
        //   145: getfield        net/minecraft/server/MinecraftServer.serverConfigManager:Lnet/minecraft/server/management/ServerConfigurationManager;
        //   148: new             Lnet/minecraft/network/play/server/S03PacketTimeUpdate;
        //   151: dup            
        //   152: aload           5
        //   154: invokevirtual   net/minecraft/world/WorldServer.getTotalWorldTime:()J
        //   157: aload           5
        //   159: invokevirtual   net/minecraft/world/WorldServer.getWorldTime:()J
        //   162: aload           5
        //   164: invokevirtual   net/minecraft/world/WorldServer.getGameRules:()Lnet/minecraft/world/GameRules;
        //   167: ldc_w           "doDaylightCycle"
        //   170: invokevirtual   net/minecraft/world/GameRules.getGameRuleBooleanValue:(Ljava/lang/String;)Z
        //   173: invokespecial   net/minecraft/network/play/server/S03PacketTimeUpdate.<init>:(JJZ)V
        //   176: aload           5
        //   178: getfield        net/minecraft/world/WorldServer.provider:Lnet/minecraft/world/WorldProvider;
        //   181: invokevirtual   net/minecraft/world/WorldProvider.getDimensionId:()I
        //   184: invokevirtual   net/minecraft/server/management/ServerConfigurationManager.sendPacketToAllPlayersInDimension:(Lnet/minecraft/network/Packet;I)V
        //   187: aload_0        
        //   188: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   191: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   194: aload_0        
        //   195: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   198: ldc_w           "tick"
        //   201: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   204: aload           5
        //   206: invokevirtual   net/minecraft/world/WorldServer.tick:()V
        //   209: goto            242
        //   212: astore          7
        //   214: aload           7
        //   216: ldc_w           "Exception ticking world"
        //   219: invokestatic    net/minecraft/crash/CrashReport.makeCrashReport:(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/crash/CrashReport;
        //   222: astore          6
        //   224: aload           5
        //   226: aload           6
        //   228: invokevirtual   net/minecraft/world/WorldServer.addWorldInfoToCrashReport:(Lnet/minecraft/crash/CrashReport;)Lnet/minecraft/crash/CrashReportCategory;
        //   231: pop            
        //   232: new             Lnet/minecraft/util/ReportedException;
        //   235: dup            
        //   236: aload           6
        //   238: invokespecial   net/minecraft/util/ReportedException.<init>:(Lnet/minecraft/crash/CrashReport;)V
        //   241: athrow         
        //   242: aload           5
        //   244: invokevirtual   net/minecraft/world/WorldServer.updateEntities:()V
        //   247: goto            280
        //   250: astore          7
        //   252: aload           7
        //   254: ldc_w           "Exception ticking world entities"
        //   257: invokestatic    net/minecraft/crash/CrashReport.makeCrashReport:(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/crash/CrashReport;
        //   260: astore          6
        //   262: aload           5
        //   264: aload           6
        //   266: invokevirtual   net/minecraft/world/WorldServer.addWorldInfoToCrashReport:(Lnet/minecraft/crash/CrashReport;)Lnet/minecraft/crash/CrashReportCategory;
        //   269: pop            
        //   270: new             Lnet/minecraft/util/ReportedException;
        //   273: dup            
        //   274: aload           6
        //   276: invokespecial   net/minecraft/util/ReportedException.<init>:(Lnet/minecraft/crash/CrashReport;)V
        //   279: athrow         
        //   280: aload_0        
        //   281: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   284: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   287: aload_0        
        //   288: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   291: ldc_w           "tracker"
        //   294: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   297: aload           5
        //   299: invokevirtual   net/minecraft/world/WorldServer.getEntityTracker:()Lnet/minecraft/entity/EntityTracker;
        //   302: invokevirtual   net/minecraft/entity/EntityTracker.updateTrackedEntities:()V
        //   305: aload_0        
        //   306: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   309: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   312: aload_0        
        //   313: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   316: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   319: aload_0        
        //   320: getfield        net/minecraft/server/MinecraftServer.timeOfLastDimensionTick:[[J
        //   323: iconst_0       
        //   324: aaload         
        //   325: aload_0        
        //   326: getfield        net/minecraft/server/MinecraftServer.tickCounter:I
        //   329: bipush          100
        //   331: irem           
        //   332: invokestatic    java/lang/System.nanoTime:()J
        //   335: lload_3        
        //   336: lsub           
        //   337: lastore        
        //   338: iinc            2, 1
        //   341: iconst_0       
        //   342: aload_0        
        //   343: getfield        net/minecraft/server/MinecraftServer.worldServers:[Lnet/minecraft/world/WorldServer;
        //   346: arraylength    
        //   347: if_icmplt       86
        //   350: aload_0        
        //   351: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   354: ldc_w           "connection"
        //   357: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   360: aload_0        
        //   361: invokevirtual   net/minecraft/server/MinecraftServer.getNetworkSystem:()Lnet/minecraft/network/NetworkSystem;
        //   364: invokevirtual   net/minecraft/network/NetworkSystem.networkTick:()V
        //   367: aload_0        
        //   368: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   371: ldc_w           "players"
        //   374: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   377: aload_0        
        //   378: getfield        net/minecraft/server/MinecraftServer.serverConfigManager:Lnet/minecraft/server/management/ServerConfigurationManager;
        //   381: invokevirtual   net/minecraft/server/management/ServerConfigurationManager.onTick:()V
        //   384: aload_0        
        //   385: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   388: ldc_w           "tickables"
        //   391: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   394: goto            418
        //   397: aload_0        
        //   398: getfield        net/minecraft/server/MinecraftServer.playersOnline:Ljava/util/List;
        //   401: iconst_0       
        //   402: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   407: checkcast       Lnet/minecraft/server/gui/IUpdatePlayerListBox;
        //   410: invokeinterface net/minecraft/server/gui/IUpdatePlayerListBox.update:()V
        //   415: iinc            2, 1
        //   418: iconst_0       
        //   419: aload_0        
        //   420: getfield        net/minecraft/server/MinecraftServer.playersOnline:Ljava/util/List;
        //   423: invokeinterface java/util/List.size:()I
        //   428: if_icmplt       397
        //   431: aload_0        
        //   432: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   435: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   438: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public boolean getAllowNether() {
        return true;
    }
    
    public void startServerThread() {
        (this.serverThread = new Thread(this, "Server thread")).start();
    }
    
    public File getFile(final String s) {
        return new File(this.getDataDirectory(), s);
    }
    
    public void logWarning(final String s) {
        MinecraftServer.logger.warn(s);
    }
    
    public WorldServer worldServerForDimension(final int n) {
        return (n == -1) ? this.worldServers[1] : ((n == 1) ? this.worldServers[2] : this.worldServers[0]);
    }
    
    public String getMinecraftVersion() {
        return "1.8";
    }
    
    public int getCurrentPlayerCount() {
        return this.serverConfigManager.getCurrentPlayerCount();
    }
    
    public int getMaxPlayers() {
        return this.serverConfigManager.getMaxPlayers();
    }
    
    public String[] getAllUsernames() {
        return this.serverConfigManager.getAllUsernames();
    }
    
    public GameProfile[] getGameProfiles() {
        return this.serverConfigManager.getAllProfiles();
    }
    
    public String getServerModName() {
        return "vanilla";
    }
    
    public CrashReport addServerInfoToCrashReport(final CrashReport crashReport) {
        crashReport.getCategory().addCrashSectionCallable("Profiler Position", new Callable() {
            private static final String __OBFID;
            final MinecraftServer this$0;
            
            public String func_179879_a() {
                return this.this$0.theProfiler.profilingEnabled ? this.this$0.theProfiler.getNameOfLastSection() : "N/A (disabled)";
            }
            
            @Override
            public Object call() {
                return this.func_179879_a();
            }
            
            static {
                __OBFID = "CL_00001418";
            }
        });
        if (this.serverConfigManager != null) {
            crashReport.getCategory().addCrashSectionCallable("Player Count", new Callable() {
                private static final String __OBFID;
                final MinecraftServer this$0;
                
                @Override
                public String call() {
                    return String.valueOf(MinecraftServer.access$1(this.this$0).getCurrentPlayerCount()) + " / " + MinecraftServer.access$1(this.this$0).getMaxPlayers() + "; " + MinecraftServer.access$1(this.this$0).playerEntityList;
                }
                
                @Override
                public Object call() throws Exception {
                    return this.call();
                }
                
                static {
                    __OBFID = "CL_00001419";
                }
            });
        }
        return crashReport;
    }
    
    public List func_180506_a(final ICommandSender commandSender, String substring, final BlockPos blockPos) {
        final ArrayList arrayList = Lists.newArrayList();
        if (substring.startsWith("/")) {
            substring = substring.substring(1);
            final boolean b = !substring.contains(" ");
            final List tabCompletionOptions = this.commandManager.getTabCompletionOptions(commandSender, substring, blockPos);
            if (tabCompletionOptions != null) {
                for (final String s : tabCompletionOptions) {
                    if (b) {
                        arrayList.add("/" + s);
                    }
                    else {
                        arrayList.add(s);
                    }
                }
            }
            return arrayList;
        }
        final String[] split = substring.split(" ", -1);
        final String s2 = split[split.length - 1];
        final String[] allUsernames = this.serverConfigManager.getAllUsernames();
        while (0 < allUsernames.length) {
            final String s3 = allUsernames[0];
            if (CommandBase.doesStringStartWith(s2, s3)) {
                arrayList.add(s3);
            }
            int n = 0;
            ++n;
        }
        return arrayList;
    }
    
    public static MinecraftServer getServer() {
        return MinecraftServer.mcServer;
    }
    
    public boolean func_175578_N() {
        return this.anvilFile != null;
    }
    
    @Override
    public String getName() {
        return "Server";
    }
    
    @Override
    public void addChatMessage(final IChatComponent chatComponent) {
        MinecraftServer.logger.info(chatComponent.getUnformattedText());
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int n, final String s) {
        return true;
    }
    
    public ICommandManager getCommandManager() {
        return this.commandManager;
    }
    
    public KeyPair getKeyPair() {
        return this.serverKeyPair;
    }
    
    public String getServerOwner() {
        return this.serverOwner;
    }
    
    public void setServerOwner(final String serverOwner) {
        this.serverOwner = serverOwner;
    }
    
    public boolean isSinglePlayer() {
        return this.serverOwner != null;
    }
    
    public String getFolderName() {
        return this.folderName;
    }
    
    public void setFolderName(final String folderName) {
        this.folderName = folderName;
    }
    
    public void setWorldName(final String worldName) {
        this.worldName = worldName;
    }
    
    public String getWorldName() {
        return this.worldName;
    }
    
    public void setKeyPair(final KeyPair serverKeyPair) {
        this.serverKeyPair = serverKeyPair;
    }
    
    public void setDifficultyForAllWorlds(final EnumDifficulty enumDifficulty) {
        while (0 < this.worldServers.length) {
            final WorldServer worldServer = this.worldServers[0];
            if (worldServer != null) {
                if (worldServer.getWorldInfo().isHardcoreModeEnabled()) {
                    worldServer.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
                    worldServer.setAllowedSpawnTypes(true, true);
                }
                else if (this.isSinglePlayer()) {
                    worldServer.getWorldInfo().setDifficulty(enumDifficulty);
                    worldServer.setAllowedSpawnTypes(worldServer.getDifficulty() != EnumDifficulty.PEACEFUL, true);
                }
                else {
                    worldServer.getWorldInfo().setDifficulty(enumDifficulty);
                    worldServer.setAllowedSpawnTypes(this.allowSpawnMonsters(), this.canSpawnAnimals);
                }
            }
            int n = 0;
            ++n;
        }
    }
    
    protected boolean allowSpawnMonsters() {
        return true;
    }
    
    public boolean isDemo() {
        return this.isDemo;
    }
    
    public void setDemo(final boolean isDemo) {
        this.isDemo = isDemo;
    }
    
    public void canCreateBonusChest(final boolean enableBonusChest) {
        this.enableBonusChest = enableBonusChest;
    }
    
    public ISaveFormat getActiveAnvilConverter() {
        return this.anvilConverterForAnvilFile;
    }
    
    public void deleteWorldAndStopServer() {
        this.worldIsBeingDeleted = true;
        this.getActiveAnvilConverter().flushCache();
        while (0 < this.worldServers.length) {
            final WorldServer worldServer = this.worldServers[0];
            if (worldServer != null) {
                worldServer.flush();
            }
            int n = 0;
            ++n;
        }
        this.getActiveAnvilConverter().deleteWorldDirectory(this.worldServers[0].getSaveHandler().getWorldDirectoryName());
        this.initiateShutdown();
    }
    
    public String getResourcePackUrl() {
        return this.resourcePackUrl;
    }
    
    public String getResourcePackHash() {
        return this.resourcePackHash;
    }
    
    public void setResourcePack(final String resourcePackUrl, final String resourcePackHash) {
        this.resourcePackUrl = resourcePackUrl;
        this.resourcePackHash = resourcePackHash;
    }
    
    @Override
    public void addServerStatsToSnooper(final PlayerUsageSnooper playerUsageSnooper) {
        playerUsageSnooper.addClientStat("whitelist_enabled", false);
        playerUsageSnooper.addClientStat("whitelist_count", 0);
        if (this.serverConfigManager != null) {
            playerUsageSnooper.addClientStat("players_current", this.getCurrentPlayerCount());
            playerUsageSnooper.addClientStat("players_max", this.getMaxPlayers());
            playerUsageSnooper.addClientStat("players_seen", this.serverConfigManager.getAvailablePlayerDat().length);
        }
        playerUsageSnooper.addClientStat("uses_auth", this.onlineMode);
        playerUsageSnooper.addClientStat("gui_state", this.getGuiEnabled() ? "enabled" : "disabled");
        playerUsageSnooper.addClientStat("run_time", (getCurrentTimeMillis() - playerUsageSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L);
        playerUsageSnooper.addClientStat("avg_tick_ms", (int)(MathHelper.average(this.tickTimeArray) * 1.0E-6));
        if (this.worldServers != null) {
            while (0 < this.worldServers.length) {
                if (this.worldServers[0] != null) {
                    final WorldServer worldServer = this.worldServers[0];
                    final WorldInfo worldInfo = worldServer.getWorldInfo();
                    playerUsageSnooper.addClientStat("world[" + 0 + "][dimension]", worldServer.provider.getDimensionId());
                    playerUsageSnooper.addClientStat("world[" + 0 + "][mode]", worldInfo.getGameType());
                    playerUsageSnooper.addClientStat("world[" + 0 + "][difficulty]", worldServer.getDifficulty());
                    playerUsageSnooper.addClientStat("world[" + 0 + "][hardcore]", worldInfo.isHardcoreModeEnabled());
                    playerUsageSnooper.addClientStat("world[" + 0 + "][generator_name]", worldInfo.getTerrainType().getWorldTypeName());
                    playerUsageSnooper.addClientStat("world[" + 0 + "][generator_version]", worldInfo.getTerrainType().getGeneratorVersion());
                    playerUsageSnooper.addClientStat("world[" + 0 + "][height]", this.buildLimit);
                    playerUsageSnooper.addClientStat("world[" + 0 + "][chunks_loaded]", worldServer.getChunkProvider().getLoadedChunkCount());
                    int n = 0;
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
        }
        playerUsageSnooper.addClientStat("worlds", 0);
    }
    
    @Override
    public void addServerTypeToSnooper(final PlayerUsageSnooper playerUsageSnooper) {
        playerUsageSnooper.addStatToSnooper("singleplayer", this.isSinglePlayer());
        playerUsageSnooper.addStatToSnooper("server_brand", this.getServerModName());
        playerUsageSnooper.addStatToSnooper("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
        playerUsageSnooper.addStatToSnooper("dedicated", this.isDedicatedServer());
    }
    
    @Override
    public boolean isSnooperEnabled() {
        return true;
    }
    
    public abstract boolean isDedicatedServer();
    
    public boolean isServerInOnlineMode() {
        return this.onlineMode;
    }
    
    public void setOnlineMode(final boolean onlineMode) {
        this.onlineMode = onlineMode;
    }
    
    public boolean getCanSpawnAnimals() {
        return this.canSpawnAnimals;
    }
    
    public void setCanSpawnAnimals(final boolean canSpawnAnimals) {
        this.canSpawnAnimals = canSpawnAnimals;
    }
    
    public boolean getCanSpawnNPCs() {
        return this.canSpawnNPCs;
    }
    
    public void setCanSpawnNPCs(final boolean canSpawnNPCs) {
        this.canSpawnNPCs = canSpawnNPCs;
    }
    
    public boolean isPVPEnabled() {
        return this.pvpEnabled;
    }
    
    public void setAllowPvp(final boolean pvpEnabled) {
        this.pvpEnabled = pvpEnabled;
    }
    
    public boolean isFlightAllowed() {
        return this.allowFlight;
    }
    
    public void setAllowFlight(final boolean allowFlight) {
        this.allowFlight = allowFlight;
    }
    
    public abstract boolean isCommandBlockEnabled();
    
    public String getMOTD() {
        return this.motd;
    }
    
    public void setMOTD(final String motd) {
        this.motd = motd;
    }
    
    public int getBuildLimit() {
        return this.buildLimit;
    }
    
    public void setBuildLimit(final int buildLimit) {
        this.buildLimit = buildLimit;
    }
    
    public ServerConfigurationManager getConfigurationManager() {
        return this.serverConfigManager;
    }
    
    public void setConfigManager(final ServerConfigurationManager serverConfigManager) {
        this.serverConfigManager = serverConfigManager;
    }
    
    public void setGameType(final WorldSettings.GameType gameType) {
        while (0 < this.worldServers.length) {
            getServer().worldServers[0].getWorldInfo().setGameType(gameType);
            int n = 0;
            ++n;
        }
    }
    
    public NetworkSystem getNetworkSystem() {
        return this.networkSystem;
    }
    
    public boolean serverIsInRunLoop() {
        return this.serverIsRunning;
    }
    
    public boolean getGuiEnabled() {
        return false;
    }
    
    public abstract String shareToLAN(final WorldSettings.GameType p0, final boolean p1);
    
    public int getTickCounter() {
        return this.tickCounter;
    }
    
    public void enableProfiling() {
        this.startProfiling = true;
    }
    
    public PlayerUsageSnooper getPlayerUsageSnooper() {
        return this.usageSnooper;
    }
    
    @Override
    public BlockPos getPosition() {
        return BlockPos.ORIGIN;
    }
    
    @Override
    public Vec3 getPositionVector() {
        return new Vec3(0.0, 0.0, 0.0);
    }
    
    @Override
    public World getEntityWorld() {
        return this.worldServers[0];
    }
    
    @Override
    public Entity getCommandSenderEntity() {
        return null;
    }
    
    public int getSpawnProtectionSize() {
        return 16;
    }
    
    public boolean isBlockProtected(final World world, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        return false;
    }
    
    public boolean getForceGamemode() {
        return this.isGamemodeForced;
    }
    
    public Proxy getServerProxy() {
        return this.serverProxy;
    }
    
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
    
    public int getMaxPlayerIdleMinutes() {
        return this.maxPlayerIdleMinutes;
    }
    
    public void setPlayerIdleTimeout(final int maxPlayerIdleMinutes) {
        this.maxPlayerIdleMinutes = maxPlayerIdleMinutes;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(this.getName());
    }
    
    public boolean isAnnouncingPlayerAchievements() {
        return true;
    }
    
    public MinecraftSessionService getMinecraftSessionService() {
        return this.sessionService;
    }
    
    public GameProfileRepository getGameProfileRepository() {
        return this.profileRepo;
    }
    
    public PlayerProfileCache getPlayerProfileCache() {
        return this.profileCache;
    }
    
    public ServerStatusResponse getServerStatusResponse() {
        return this.statusResponse;
    }
    
    public void refreshStatusNextTick() {
        this.nanoTimeSinceStatusRefresh = 0L;
    }
    
    public Entity getEntityFromUuid(final UUID uuid) {
        final WorldServer[] worldServers = this.worldServers;
        while (0 < worldServers.length) {
            final WorldServer worldServer = worldServers[0];
            if (worldServer != null) {
                final Entity entityFromUuid = worldServer.getEntityFromUuid(uuid);
                if (entityFromUuid != null) {
                    return entityFromUuid;
                }
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    @Override
    public boolean sendCommandFeedback() {
        return getServer().worldServers[0].getGameRules().getGameRuleBooleanValue("sendCommandFeedback");
    }
    
    @Override
    public void func_174794_a(final CommandResultStats.Type type, final int n) {
    }
    
    public int getMaxWorldSize() {
        return 29999984;
    }
    
    public ListenableFuture callFromMainThread(final Callable callable) {
        Validate.notNull(callable);
        if (!this.isCallingFromMinecraftThread()) {
            final ListenableFutureTask create = ListenableFutureTask.create(callable);
            final Queue futureTaskQueue = this.futureTaskQueue;
            // monitorenter(futureTaskQueue2 = this.futureTaskQueue)
            this.futureTaskQueue.add(create);
            // monitorexit(futureTaskQueue2)
            return create;
        }
        return Futures.immediateFuture(callable.call());
    }
    
    @Override
    public ListenableFuture addScheduledTask(final Runnable runnable) {
        Validate.notNull(runnable);
        return this.callFromMainThread(Executors.callable(runnable));
    }
    
    @Override
    public boolean isCallingFromMinecraftThread() {
        return Thread.currentThread() == this.serverThread;
    }
    
    public int getNetworkCompressionTreshold() {
        return 256;
    }
    
    static Logger access$0() {
        return MinecraftServer.logger;
    }
    
    static ServerConfigurationManager access$1(final MinecraftServer minecraftServer) {
        return minecraftServer.serverConfigManager;
    }
}
