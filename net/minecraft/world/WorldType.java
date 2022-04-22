package net.minecraft.world;

public class WorldType
{
    public static final WorldType[] worldTypes;
    public static final WorldType DEFAULT;
    public static final WorldType FLAT;
    public static final WorldType LARGE_BIOMES;
    public static final WorldType AMPLIFIED;
    public static final WorldType CUSTOMIZED;
    public static final WorldType DEBUG_WORLD;
    public static final WorldType DEFAULT_1_1;
    private final int worldTypeId;
    private final String worldType;
    private final int generatorVersion;
    private boolean canBeCreated;
    private boolean isWorldTypeVersioned;
    private boolean hasNotificationData;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000150";
        worldTypes = new WorldType[16];
        DEFAULT = new WorldType(0, "default", 1).setVersioned();
        FLAT = new WorldType(1, "flat");
        LARGE_BIOMES = new WorldType(2, "largeBiomes");
        AMPLIFIED = new WorldType(3, "amplified").setNotificationData();
        CUSTOMIZED = new WorldType(4, "customized");
        DEBUG_WORLD = new WorldType(5, "debug_all_block_states");
        DEFAULT_1_1 = new WorldType(8, "default_1_1", 0).setCanBeCreated(false);
    }
    
    private WorldType(final int n, final String s) {
        this(n, s, 0);
    }
    
    private WorldType(final int worldTypeId, final String worldType, final int generatorVersion) {
        this.worldType = worldType;
        this.generatorVersion = generatorVersion;
        this.canBeCreated = true;
        this.worldTypeId = worldTypeId;
        WorldType.worldTypes[worldTypeId] = this;
    }
    
    public String getWorldTypeName() {
        return this.worldType;
    }
    
    public String getTranslateName() {
        return "generator." + this.worldType;
    }
    
    public String func_151359_c() {
        return String.valueOf(this.getTranslateName()) + ".info";
    }
    
    public int getGeneratorVersion() {
        return this.generatorVersion;
    }
    
    public WorldType getWorldTypeForGeneratorVersion(final int n) {
        return (this == WorldType.DEFAULT && n == 0) ? WorldType.DEFAULT_1_1 : this;
    }
    
    private WorldType setCanBeCreated(final boolean canBeCreated) {
        this.canBeCreated = canBeCreated;
        return this;
    }
    
    public boolean getCanBeCreated() {
        return this.canBeCreated;
    }
    
    private WorldType setVersioned() {
        this.isWorldTypeVersioned = true;
        return this;
    }
    
    public boolean isVersioned() {
        return this.isWorldTypeVersioned;
    }
    
    public static WorldType parseWorldType(final String s) {
        while (0 < WorldType.worldTypes.length) {
            if (WorldType.worldTypes[0] != null && WorldType.worldTypes[0].worldType.equalsIgnoreCase(s)) {
                return WorldType.worldTypes[0];
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public int getWorldTypeID() {
        return this.worldTypeId;
    }
    
    public boolean showWorldInfoNotice() {
        return this.hasNotificationData;
    }
    
    private WorldType setNotificationData() {
        this.hasNotificationData = true;
        return this;
    }
}
