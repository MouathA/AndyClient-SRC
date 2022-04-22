package net.minecraft.world.storage;

import net.minecraft.world.*;
import net.minecraft.server.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;
import java.util.concurrent.*;

public class WorldInfo
{
    public static final EnumDifficulty DEFAULT_DIFFICULTY;
    private long randomSeed;
    private WorldType terrainType;
    private String generatorOptions;
    private int spawnX;
    private int spawnY;
    private int spawnZ;
    private long totalTime;
    private long worldTime;
    private long lastTimePlayed;
    private long sizeOnDisk;
    private NBTTagCompound playerTag;
    private int dimension;
    private String levelName;
    private int saveVersion;
    private int cleanWeatherTime;
    private boolean raining;
    private int rainTime;
    private boolean thundering;
    private int thunderTime;
    private WorldSettings.GameType theGameType;
    private boolean mapFeaturesEnabled;
    private boolean hardcore;
    private boolean allowCommands;
    private boolean initialized;
    private EnumDifficulty difficulty;
    private boolean difficultyLocked;
    private double borderCenterX;
    private double borderCenterZ;
    private double borderSize;
    private long borderSizeLerpTime;
    private double borderSizeLerpTarget;
    private double borderSafeZone;
    private double borderDamagePerBlock;
    private int borderWarningDistance;
    private int borderWarningTime;
    private GameRules theGameRules;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000587";
        DEFAULT_DIFFICULTY = EnumDifficulty.NORMAL;
    }
    
    protected WorldInfo() {
        this.terrainType = WorldType.DEFAULT;
        this.generatorOptions = "";
        this.borderCenterX = 0.0;
        this.borderCenterZ = 0.0;
        this.borderSize = 6.0E7;
        this.borderSizeLerpTime = 0L;
        this.borderSizeLerpTarget = 0.0;
        this.borderSafeZone = 5.0;
        this.borderDamagePerBlock = 0.2;
        this.borderWarningDistance = 5;
        this.borderWarningTime = 15;
        this.theGameRules = new GameRules();
    }
    
    public WorldInfo(final NBTTagCompound nbtTagCompound) {
        this.terrainType = WorldType.DEFAULT;
        this.generatorOptions = "";
        this.borderCenterX = 0.0;
        this.borderCenterZ = 0.0;
        this.borderSize = 6.0E7;
        this.borderSizeLerpTime = 0L;
        this.borderSizeLerpTarget = 0.0;
        this.borderSafeZone = 5.0;
        this.borderDamagePerBlock = 0.2;
        this.borderWarningDistance = 5;
        this.borderWarningTime = 15;
        this.theGameRules = new GameRules();
        this.randomSeed = nbtTagCompound.getLong("RandomSeed");
        if (nbtTagCompound.hasKey("generatorName", 8)) {
            this.terrainType = WorldType.parseWorldType(nbtTagCompound.getString("generatorName"));
            if (this.terrainType == null) {
                this.terrainType = WorldType.DEFAULT;
            }
            else if (this.terrainType.isVersioned()) {
                if (nbtTagCompound.hasKey("generatorVersion", 99)) {
                    nbtTagCompound.getInteger("generatorVersion");
                }
                this.terrainType = this.terrainType.getWorldTypeForGeneratorVersion(0);
            }
            if (nbtTagCompound.hasKey("generatorOptions", 8)) {
                this.generatorOptions = nbtTagCompound.getString("generatorOptions");
            }
        }
        this.theGameType = WorldSettings.GameType.getByID(nbtTagCompound.getInteger("GameType"));
        if (nbtTagCompound.hasKey("MapFeatures", 99)) {
            this.mapFeaturesEnabled = nbtTagCompound.getBoolean("MapFeatures");
        }
        else {
            this.mapFeaturesEnabled = true;
        }
        this.spawnX = nbtTagCompound.getInteger("SpawnX");
        this.spawnY = nbtTagCompound.getInteger("SpawnY");
        this.spawnZ = nbtTagCompound.getInteger("SpawnZ");
        this.totalTime = nbtTagCompound.getLong("Time");
        if (nbtTagCompound.hasKey("DayTime", 99)) {
            this.worldTime = nbtTagCompound.getLong("DayTime");
        }
        else {
            this.worldTime = this.totalTime;
        }
        this.lastTimePlayed = nbtTagCompound.getLong("LastPlayed");
        this.sizeOnDisk = nbtTagCompound.getLong("SizeOnDisk");
        this.levelName = nbtTagCompound.getString("LevelName");
        this.saveVersion = nbtTagCompound.getInteger("version");
        this.cleanWeatherTime = nbtTagCompound.getInteger("clearWeatherTime");
        this.rainTime = nbtTagCompound.getInteger("rainTime");
        this.raining = nbtTagCompound.getBoolean("raining");
        this.thunderTime = nbtTagCompound.getInteger("thunderTime");
        this.thundering = nbtTagCompound.getBoolean("thundering");
        this.hardcore = nbtTagCompound.getBoolean("hardcore");
        if (nbtTagCompound.hasKey("initialized", 99)) {
            this.initialized = nbtTagCompound.getBoolean("initialized");
        }
        else {
            this.initialized = true;
        }
        if (nbtTagCompound.hasKey("allowCommands", 99)) {
            this.allowCommands = nbtTagCompound.getBoolean("allowCommands");
        }
        else {
            this.allowCommands = (this.theGameType == WorldSettings.GameType.CREATIVE);
        }
        if (nbtTagCompound.hasKey("Player", 10)) {
            this.playerTag = nbtTagCompound.getCompoundTag("Player");
            this.dimension = this.playerTag.getInteger("Dimension");
        }
        if (nbtTagCompound.hasKey("GameRules", 10)) {
            this.theGameRules.readGameRulesFromNBT(nbtTagCompound.getCompoundTag("GameRules"));
        }
        if (nbtTagCompound.hasKey("Difficulty", 99)) {
            this.difficulty = EnumDifficulty.getDifficultyEnum(nbtTagCompound.getByte("Difficulty"));
        }
        if (nbtTagCompound.hasKey("DifficultyLocked", 1)) {
            this.difficultyLocked = nbtTagCompound.getBoolean("DifficultyLocked");
        }
        if (nbtTagCompound.hasKey("BorderCenterX", 99)) {
            this.borderCenterX = nbtTagCompound.getDouble("BorderCenterX");
        }
        if (nbtTagCompound.hasKey("BorderCenterZ", 99)) {
            this.borderCenterZ = nbtTagCompound.getDouble("BorderCenterZ");
        }
        if (nbtTagCompound.hasKey("BorderSize", 99)) {
            this.borderSize = nbtTagCompound.getDouble("BorderSize");
        }
        if (nbtTagCompound.hasKey("BorderSizeLerpTime", 99)) {
            this.borderSizeLerpTime = nbtTagCompound.getLong("BorderSizeLerpTime");
        }
        if (nbtTagCompound.hasKey("BorderSizeLerpTarget", 99)) {
            this.borderSizeLerpTarget = nbtTagCompound.getDouble("BorderSizeLerpTarget");
        }
        if (nbtTagCompound.hasKey("BorderSafeZone", 99)) {
            this.borderSafeZone = nbtTagCompound.getDouble("BorderSafeZone");
        }
        if (nbtTagCompound.hasKey("BorderDamagePerBlock", 99)) {
            this.borderDamagePerBlock = nbtTagCompound.getDouble("BorderDamagePerBlock");
        }
        if (nbtTagCompound.hasKey("BorderWarningBlocks", 99)) {
            this.borderWarningDistance = nbtTagCompound.getInteger("BorderWarningBlocks");
        }
        if (nbtTagCompound.hasKey("BorderWarningTime", 99)) {
            this.borderWarningTime = nbtTagCompound.getInteger("BorderWarningTime");
        }
    }
    
    public WorldInfo(final WorldSettings worldSettings, final String levelName) {
        this.terrainType = WorldType.DEFAULT;
        this.generatorOptions = "";
        this.borderCenterX = 0.0;
        this.borderCenterZ = 0.0;
        this.borderSize = 6.0E7;
        this.borderSizeLerpTime = 0L;
        this.borderSizeLerpTarget = 0.0;
        this.borderSafeZone = 5.0;
        this.borderDamagePerBlock = 0.2;
        this.borderWarningDistance = 5;
        this.borderWarningTime = 15;
        this.theGameRules = new GameRules();
        this.populateFromWorldSettings(worldSettings);
        this.levelName = levelName;
        this.difficulty = WorldInfo.DEFAULT_DIFFICULTY;
        this.initialized = false;
    }
    
    public void populateFromWorldSettings(final WorldSettings worldSettings) {
        this.randomSeed = worldSettings.getSeed();
        this.theGameType = worldSettings.getGameType();
        this.mapFeaturesEnabled = worldSettings.isMapFeaturesEnabled();
        this.hardcore = worldSettings.getHardcoreEnabled();
        this.terrainType = worldSettings.getTerrainType();
        this.generatorOptions = worldSettings.getWorldName();
        this.allowCommands = worldSettings.areCommandsAllowed();
    }
    
    public WorldInfo(final WorldInfo worldInfo) {
        this.terrainType = WorldType.DEFAULT;
        this.generatorOptions = "";
        this.borderCenterX = 0.0;
        this.borderCenterZ = 0.0;
        this.borderSize = 6.0E7;
        this.borderSizeLerpTime = 0L;
        this.borderSizeLerpTarget = 0.0;
        this.borderSafeZone = 5.0;
        this.borderDamagePerBlock = 0.2;
        this.borderWarningDistance = 5;
        this.borderWarningTime = 15;
        this.theGameRules = new GameRules();
        this.randomSeed = worldInfo.randomSeed;
        this.terrainType = worldInfo.terrainType;
        this.generatorOptions = worldInfo.generatorOptions;
        this.theGameType = worldInfo.theGameType;
        this.mapFeaturesEnabled = worldInfo.mapFeaturesEnabled;
        this.spawnX = worldInfo.spawnX;
        this.spawnY = worldInfo.spawnY;
        this.spawnZ = worldInfo.spawnZ;
        this.totalTime = worldInfo.totalTime;
        this.worldTime = worldInfo.worldTime;
        this.lastTimePlayed = worldInfo.lastTimePlayed;
        this.sizeOnDisk = worldInfo.sizeOnDisk;
        this.playerTag = worldInfo.playerTag;
        this.dimension = worldInfo.dimension;
        this.levelName = worldInfo.levelName;
        this.saveVersion = worldInfo.saveVersion;
        this.rainTime = worldInfo.rainTime;
        this.raining = worldInfo.raining;
        this.thunderTime = worldInfo.thunderTime;
        this.thundering = worldInfo.thundering;
        this.hardcore = worldInfo.hardcore;
        this.allowCommands = worldInfo.allowCommands;
        this.initialized = worldInfo.initialized;
        this.theGameRules = worldInfo.theGameRules;
        this.difficulty = worldInfo.difficulty;
        this.difficultyLocked = worldInfo.difficultyLocked;
        this.borderCenterX = worldInfo.borderCenterX;
        this.borderCenterZ = worldInfo.borderCenterZ;
        this.borderSize = worldInfo.borderSize;
        this.borderSizeLerpTime = worldInfo.borderSizeLerpTime;
        this.borderSizeLerpTarget = worldInfo.borderSizeLerpTarget;
        this.borderSafeZone = worldInfo.borderSafeZone;
        this.borderDamagePerBlock = worldInfo.borderDamagePerBlock;
        this.borderWarningTime = worldInfo.borderWarningTime;
        this.borderWarningDistance = worldInfo.borderWarningDistance;
    }
    
    public NBTTagCompound getNBTTagCompound() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.updateTagCompound(nbtTagCompound, this.playerTag);
        return nbtTagCompound;
    }
    
    public NBTTagCompound cloneNBTCompound(final NBTTagCompound nbtTagCompound) {
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        this.updateTagCompound(nbtTagCompound2, nbtTagCompound);
        return nbtTagCompound2;
    }
    
    private void updateTagCompound(final NBTTagCompound nbtTagCompound, final NBTTagCompound nbtTagCompound2) {
        nbtTagCompound.setLong("RandomSeed", this.randomSeed);
        nbtTagCompound.setString("generatorName", this.terrainType.getWorldTypeName());
        nbtTagCompound.setInteger("generatorVersion", this.terrainType.getGeneratorVersion());
        nbtTagCompound.setString("generatorOptions", this.generatorOptions);
        nbtTagCompound.setInteger("GameType", this.theGameType.getID());
        nbtTagCompound.setBoolean("MapFeatures", this.mapFeaturesEnabled);
        nbtTagCompound.setInteger("SpawnX", this.spawnX);
        nbtTagCompound.setInteger("SpawnY", this.spawnY);
        nbtTagCompound.setInteger("SpawnZ", this.spawnZ);
        nbtTagCompound.setLong("Time", this.totalTime);
        nbtTagCompound.setLong("DayTime", this.worldTime);
        nbtTagCompound.setLong("SizeOnDisk", this.sizeOnDisk);
        nbtTagCompound.setLong("LastPlayed", MinecraftServer.getCurrentTimeMillis());
        nbtTagCompound.setString("LevelName", this.levelName);
        nbtTagCompound.setInteger("version", this.saveVersion);
        nbtTagCompound.setInteger("clearWeatherTime", this.cleanWeatherTime);
        nbtTagCompound.setInteger("rainTime", this.rainTime);
        nbtTagCompound.setBoolean("raining", this.raining);
        nbtTagCompound.setInteger("thunderTime", this.thunderTime);
        nbtTagCompound.setBoolean("thundering", this.thundering);
        nbtTagCompound.setBoolean("hardcore", this.hardcore);
        nbtTagCompound.setBoolean("allowCommands", this.allowCommands);
        nbtTagCompound.setBoolean("initialized", this.initialized);
        nbtTagCompound.setDouble("BorderCenterX", this.borderCenterX);
        nbtTagCompound.setDouble("BorderCenterZ", this.borderCenterZ);
        nbtTagCompound.setDouble("BorderSize", this.borderSize);
        nbtTagCompound.setLong("BorderSizeLerpTime", this.borderSizeLerpTime);
        nbtTagCompound.setDouble("BorderSafeZone", this.borderSafeZone);
        nbtTagCompound.setDouble("BorderDamagePerBlock", this.borderDamagePerBlock);
        nbtTagCompound.setDouble("BorderSizeLerpTarget", this.borderSizeLerpTarget);
        nbtTagCompound.setDouble("BorderWarningBlocks", this.borderWarningDistance);
        nbtTagCompound.setDouble("BorderWarningTime", this.borderWarningTime);
        if (this.difficulty != null) {
            nbtTagCompound.setByte("Difficulty", (byte)this.difficulty.getDifficultyId());
        }
        nbtTagCompound.setBoolean("DifficultyLocked", this.difficultyLocked);
        nbtTagCompound.setTag("GameRules", this.theGameRules.writeGameRulesToNBT());
        if (nbtTagCompound2 != null) {
            nbtTagCompound.setTag("Player", nbtTagCompound2);
        }
    }
    
    public long getSeed() {
        return this.randomSeed;
    }
    
    public int getSpawnX() {
        return this.spawnX;
    }
    
    public int getSpawnY() {
        return this.spawnY;
    }
    
    public int getSpawnZ() {
        return this.spawnZ;
    }
    
    public long getWorldTotalTime() {
        return this.totalTime;
    }
    
    public long getWorldTime() {
        return this.worldTime;
    }
    
    public long getSizeOnDisk() {
        return this.sizeOnDisk;
    }
    
    public NBTTagCompound getPlayerNBTTagCompound() {
        return this.playerTag;
    }
    
    public void setSpawnX(final int spawnX) {
        this.spawnX = spawnX;
    }
    
    public void setSpawnY(final int spawnY) {
        this.spawnY = spawnY;
    }
    
    public void setSpawnZ(final int spawnZ) {
        this.spawnZ = spawnZ;
    }
    
    public void incrementTotalWorldTime(final long totalTime) {
        this.totalTime = totalTime;
    }
    
    public void setWorldTime(final long worldTime) {
        this.worldTime = worldTime;
    }
    
    public void setSpawn(final BlockPos blockPos) {
        this.spawnX = blockPos.getX();
        this.spawnY = blockPos.getY();
        this.spawnZ = blockPos.getZ();
    }
    
    public String getWorldName() {
        return this.levelName;
    }
    
    public void setWorldName(final String levelName) {
        this.levelName = levelName;
    }
    
    public int getSaveVersion() {
        return this.saveVersion;
    }
    
    public void setSaveVersion(final int saveVersion) {
        this.saveVersion = saveVersion;
    }
    
    public long getLastTimePlayed() {
        return this.lastTimePlayed;
    }
    
    public int func_176133_A() {
        return this.cleanWeatherTime;
    }
    
    public void func_176142_i(final int cleanWeatherTime) {
        this.cleanWeatherTime = cleanWeatherTime;
    }
    
    public boolean isThundering() {
        return this.thundering;
    }
    
    public void setThundering(final boolean thundering) {
        this.thundering = thundering;
    }
    
    public int getThunderTime() {
        return this.thunderTime;
    }
    
    public void setThunderTime(final int thunderTime) {
        this.thunderTime = thunderTime;
    }
    
    public boolean isRaining() {
        return this.raining;
    }
    
    public void setRaining(final boolean raining) {
        this.raining = raining;
    }
    
    public int getRainTime() {
        return this.rainTime;
    }
    
    public void setRainTime(final int rainTime) {
        this.rainTime = rainTime;
    }
    
    public WorldSettings.GameType getGameType() {
        return this.theGameType;
    }
    
    public boolean isMapFeaturesEnabled() {
        return this.mapFeaturesEnabled;
    }
    
    public void setMapFeaturesEnabled(final boolean mapFeaturesEnabled) {
        this.mapFeaturesEnabled = mapFeaturesEnabled;
    }
    
    public void setGameType(final WorldSettings.GameType theGameType) {
        this.theGameType = theGameType;
    }
    
    public boolean isHardcoreModeEnabled() {
        return this.hardcore;
    }
    
    public void setHardcore(final boolean hardcore) {
        this.hardcore = hardcore;
    }
    
    public WorldType getTerrainType() {
        return this.terrainType;
    }
    
    public void setTerrainType(final WorldType terrainType) {
        this.terrainType = terrainType;
    }
    
    public String getGeneratorOptions() {
        return this.generatorOptions;
    }
    
    public boolean areCommandsAllowed() {
        return this.allowCommands;
    }
    
    public void setAllowCommands(final boolean allowCommands) {
        this.allowCommands = allowCommands;
    }
    
    public boolean isInitialized() {
        return this.initialized;
    }
    
    public void setServerInitialized(final boolean initialized) {
        this.initialized = initialized;
    }
    
    public GameRules getGameRulesInstance() {
        return this.theGameRules;
    }
    
    public double func_176120_C() {
        return this.borderCenterX;
    }
    
    public double func_176126_D() {
        return this.borderCenterZ;
    }
    
    public double func_176137_E() {
        return this.borderSize;
    }
    
    public void func_176145_a(final double borderSize) {
        this.borderSize = borderSize;
    }
    
    public long func_176134_F() {
        return this.borderSizeLerpTime;
    }
    
    public void func_176135_e(final long borderSizeLerpTime) {
        this.borderSizeLerpTime = borderSizeLerpTime;
    }
    
    public double func_176132_G() {
        return this.borderSizeLerpTarget;
    }
    
    public void func_176118_b(final double borderSizeLerpTarget) {
        this.borderSizeLerpTarget = borderSizeLerpTarget;
    }
    
    public void func_176141_c(final double borderCenterZ) {
        this.borderCenterZ = borderCenterZ;
    }
    
    public void func_176124_d(final double borderCenterX) {
        this.borderCenterX = borderCenterX;
    }
    
    public double func_176138_H() {
        return this.borderSafeZone;
    }
    
    public void func_176129_e(final double borderSafeZone) {
        this.borderSafeZone = borderSafeZone;
    }
    
    public double func_176140_I() {
        return this.borderDamagePerBlock;
    }
    
    public void func_176125_f(final double borderDamagePerBlock) {
        this.borderDamagePerBlock = borderDamagePerBlock;
    }
    
    public int func_176131_J() {
        return this.borderWarningDistance;
    }
    
    public int func_176139_K() {
        return this.borderWarningTime;
    }
    
    public void func_176122_j(final int borderWarningDistance) {
        this.borderWarningDistance = borderWarningDistance;
    }
    
    public void func_176136_k(final int borderWarningTime) {
        this.borderWarningTime = borderWarningTime;
    }
    
    public EnumDifficulty getDifficulty() {
        return this.difficulty;
    }
    
    public void setDifficulty(final EnumDifficulty difficulty) {
        this.difficulty = difficulty;
    }
    
    public boolean isDifficultyLocked() {
        return this.difficultyLocked;
    }
    
    public void setDifficultyLocked(final boolean difficultyLocked) {
        this.difficultyLocked = difficultyLocked;
    }
    
    public void addToCrashReport(final CrashReportCategory crashReportCategory) {
        crashReportCategory.addCrashSectionCallable("Level seed", new Callable() {
            private static final String __OBFID;
            final WorldInfo this$0;
            
            @Override
            public String call() {
                return String.valueOf(this.this$0.getSeed());
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000588";
            }
        });
        crashReportCategory.addCrashSectionCallable("Level generator", new Callable() {
            private static final String __OBFID;
            final WorldInfo this$0;
            
            @Override
            public String call() {
                return String.format("ID %02d - %s, ver %d. Features enabled: %b", WorldInfo.access$0(this.this$0).getWorldTypeID(), WorldInfo.access$0(this.this$0).getWorldTypeName(), WorldInfo.access$0(this.this$0).getGeneratorVersion(), WorldInfo.access$1(this.this$0));
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000589";
            }
        });
        crashReportCategory.addCrashSectionCallable("Level generator options", new Callable() {
            private static final String __OBFID;
            final WorldInfo this$0;
            
            @Override
            public String call() {
                return WorldInfo.access$2(this.this$0);
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000590";
            }
        });
        crashReportCategory.addCrashSectionCallable("Level spawn location", new Callable() {
            private static final String __OBFID;
            final WorldInfo this$0;
            
            @Override
            public String call() {
                return CrashReportCategory.getCoordinateInfo(WorldInfo.access$3(this.this$0), WorldInfo.access$4(this.this$0), WorldInfo.access$5(this.this$0));
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000591";
            }
        });
        crashReportCategory.addCrashSectionCallable("Level time", new Callable() {
            private static final String __OBFID;
            final WorldInfo this$0;
            
            @Override
            public String call() {
                return String.format("%d game time, %d day time", WorldInfo.access$6(this.this$0), WorldInfo.access$7(this.this$0));
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000592";
            }
        });
        crashReportCategory.addCrashSectionCallable("Level dimension", new Callable() {
            private static final String __OBFID;
            final WorldInfo this$0;
            
            @Override
            public String call() {
                return String.valueOf(WorldInfo.access$8(this.this$0));
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000593";
            }
        });
        crashReportCategory.addCrashSectionCallable("Level storage version", new Callable() {
            private static final String __OBFID;
            final WorldInfo this$0;
            
            @Override
            public String call() {
                String s = "Unknown?";
                switch (WorldInfo.access$9(this.this$0)) {
                    case 19132: {
                        s = "McRegion";
                        break;
                    }
                    case 19133: {
                        s = "Anvil";
                        break;
                    }
                }
                return String.format("0x%05X - %s", WorldInfo.access$9(this.this$0), s);
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000594";
            }
        });
        crashReportCategory.addCrashSectionCallable("Level weather", new Callable() {
            private static final String __OBFID;
            final WorldInfo this$0;
            
            @Override
            public String call() {
                return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", WorldInfo.access$10(this.this$0), WorldInfo.access$11(this.this$0), WorldInfo.access$12(this.this$0), WorldInfo.access$13(this.this$0));
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000595";
            }
        });
        crashReportCategory.addCrashSectionCallable("Level game mode", new Callable() {
            private static final String __OBFID;
            final WorldInfo this$0;
            
            @Override
            public String call() {
                return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", WorldInfo.access$14(this.this$0).getName(), WorldInfo.access$14(this.this$0).getID(), WorldInfo.access$15(this.this$0), WorldInfo.access$16(this.this$0));
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                __OBFID = "CL_00000597";
            }
        });
    }
    
    static WorldType access$0(final WorldInfo worldInfo) {
        return worldInfo.terrainType;
    }
    
    static boolean access$1(final WorldInfo worldInfo) {
        return worldInfo.mapFeaturesEnabled;
    }
    
    static String access$2(final WorldInfo worldInfo) {
        return worldInfo.generatorOptions;
    }
    
    static int access$3(final WorldInfo worldInfo) {
        return worldInfo.spawnX;
    }
    
    static int access$4(final WorldInfo worldInfo) {
        return worldInfo.spawnY;
    }
    
    static int access$5(final WorldInfo worldInfo) {
        return worldInfo.spawnZ;
    }
    
    static long access$6(final WorldInfo worldInfo) {
        return worldInfo.totalTime;
    }
    
    static long access$7(final WorldInfo worldInfo) {
        return worldInfo.worldTime;
    }
    
    static int access$8(final WorldInfo worldInfo) {
        return worldInfo.dimension;
    }
    
    static int access$9(final WorldInfo worldInfo) {
        return worldInfo.saveVersion;
    }
    
    static int access$10(final WorldInfo worldInfo) {
        return worldInfo.rainTime;
    }
    
    static boolean access$11(final WorldInfo worldInfo) {
        return worldInfo.raining;
    }
    
    static int access$12(final WorldInfo worldInfo) {
        return worldInfo.thunderTime;
    }
    
    static boolean access$13(final WorldInfo worldInfo) {
        return worldInfo.thundering;
    }
    
    static WorldSettings.GameType access$14(final WorldInfo worldInfo) {
        return worldInfo.theGameType;
    }
    
    static boolean access$15(final WorldInfo worldInfo) {
        return worldInfo.hardcore;
    }
    
    static boolean access$16(final WorldInfo worldInfo) {
        return worldInfo.allowCommands;
    }
}
