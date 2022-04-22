package net.minecraft.world;

import net.minecraft.world.storage.*;
import net.minecraft.entity.player.*;

public final class WorldSettings
{
    private final long seed;
    private final GameType theGameType;
    private final boolean mapFeaturesEnabled;
    private final boolean hardcoreEnabled;
    private final WorldType terrainType;
    private boolean commandsAllowed;
    private boolean bonusChestEnabled;
    private String worldName;
    private static final String __OBFID;
    
    public WorldSettings(final long seed, final GameType theGameType, final boolean mapFeaturesEnabled, final boolean hardcoreEnabled, final WorldType terrainType) {
        this.worldName = "";
        this.seed = seed;
        this.theGameType = theGameType;
        this.mapFeaturesEnabled = mapFeaturesEnabled;
        this.hardcoreEnabled = hardcoreEnabled;
        this.terrainType = terrainType;
    }
    
    public WorldSettings(final WorldInfo worldInfo) {
        this(worldInfo.getSeed(), worldInfo.getGameType(), worldInfo.isMapFeaturesEnabled(), worldInfo.isHardcoreModeEnabled(), worldInfo.getTerrainType());
    }
    
    public WorldSettings enableBonusChest() {
        this.bonusChestEnabled = true;
        return this;
    }
    
    public WorldSettings enableCommands() {
        this.commandsAllowed = true;
        return this;
    }
    
    public WorldSettings setWorldName(final String worldName) {
        this.worldName = worldName;
        return this;
    }
    
    public boolean isBonusChestEnabled() {
        return this.bonusChestEnabled;
    }
    
    public long getSeed() {
        return this.seed;
    }
    
    public GameType getGameType() {
        return this.theGameType;
    }
    
    public boolean getHardcoreEnabled() {
        return this.hardcoreEnabled;
    }
    
    public boolean isMapFeaturesEnabled() {
        return this.mapFeaturesEnabled;
    }
    
    public WorldType getTerrainType() {
        return this.terrainType;
    }
    
    public boolean areCommandsAllowed() {
        return this.commandsAllowed;
    }
    
    public static GameType getGameTypeById(final int n) {
        return GameType.getByID(n);
    }
    
    public String getWorldName() {
        return this.worldName;
    }
    
    static {
        __OBFID = "CL_00000147";
    }
    
    public enum GameType
    {
        NOT_SET("NOT_SET", 0, "NOT_SET", 0, -1, ""), 
        SURVIVAL("SURVIVAL", 1, "SURVIVAL", 1, 0, "survival"), 
        CREATIVE("CREATIVE", 2, "CREATIVE", 2, 1, "creative"), 
        ADVENTURE("ADVENTURE", 3, "ADVENTURE", 3, 2, "adventure"), 
        SPECTATOR("SPECTATOR", 4, "SPECTATOR", 4, 3, "spectator");
        
        int id;
        String name;
        private static final GameType[] $VALUES;
        private static final String __OBFID;
        private static final GameType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00000148";
            ENUM$VALUES = new GameType[] { GameType.NOT_SET, GameType.SURVIVAL, GameType.CREATIVE, GameType.ADVENTURE, GameType.SPECTATOR };
            $VALUES = new GameType[] { GameType.NOT_SET, GameType.SURVIVAL, GameType.CREATIVE, GameType.ADVENTURE, GameType.SPECTATOR };
        }
        
        private GameType(final String s, final int n, final String s2, final int n2, final int id, final String name) {
            this.id = id;
            this.name = name;
        }
        
        public int getID() {
            return this.id;
        }
        
        public String getName() {
            return this.name;
        }
        
        public void configurePlayerCapabilities(final PlayerCapabilities playerCapabilities) {
            if (this == GameType.CREATIVE) {
                playerCapabilities.allowFlying = true;
                playerCapabilities.isCreativeMode = true;
                playerCapabilities.disableDamage = true;
            }
            else if (this == GameType.SPECTATOR) {
                playerCapabilities.allowFlying = true;
                playerCapabilities.isCreativeMode = false;
                playerCapabilities.disableDamage = true;
                playerCapabilities.isFlying = true;
            }
            else {
                playerCapabilities.allowFlying = false;
                playerCapabilities.isCreativeMode = false;
                playerCapabilities.disableDamage = false;
                playerCapabilities.isFlying = false;
            }
            playerCapabilities.allowEdit = !this.isAdventure();
        }
        
        public boolean isAdventure() {
            return this == GameType.ADVENTURE || this == GameType.SPECTATOR;
        }
        
        public boolean isCreative() {
            return this == GameType.CREATIVE;
        }
        
        public boolean isSurvivalOrAdventure() {
            return this == GameType.SURVIVAL || this == GameType.ADVENTURE;
        }
        
        public static GameType getByID(final int n) {
            final GameType[] values = values();
            while (0 < values.length) {
                final GameType gameType = values[0];
                if (gameType.id == n) {
                    return gameType;
                }
                int n2 = 0;
                ++n2;
            }
            return GameType.SURVIVAL;
        }
        
        public static GameType getByName(final String s) {
            final GameType[] values = values();
            while (0 < values.length) {
                final GameType gameType = values[0];
                if (gameType.name.equals(s)) {
                    return gameType;
                }
                int n = 0;
                ++n;
            }
            return GameType.SURVIVAL;
        }
    }
}
