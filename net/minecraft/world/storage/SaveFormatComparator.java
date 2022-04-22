package net.minecraft.world.storage;

import net.minecraft.world.*;

public class SaveFormatComparator implements Comparable
{
    private final String fileName;
    private final String displayName;
    private final long lastTimePlayed;
    private final long sizeOnDisk;
    private final boolean requiresConversion;
    private final WorldSettings.GameType theEnumGameType;
    private final boolean hardcore;
    private final boolean cheatsEnabled;
    private static final String __OBFID;
    
    public SaveFormatComparator(final String fileName, final String displayName, final long lastTimePlayed, final long sizeOnDisk, final WorldSettings.GameType theEnumGameType, final boolean requiresConversion, final boolean hardcore, final boolean cheatsEnabled) {
        this.fileName = fileName;
        this.displayName = displayName;
        this.lastTimePlayed = lastTimePlayed;
        this.sizeOnDisk = sizeOnDisk;
        this.theEnumGameType = theEnumGameType;
        this.requiresConversion = requiresConversion;
        this.hardcore = hardcore;
        this.cheatsEnabled = cheatsEnabled;
    }
    
    public String getFileName() {
        return this.fileName;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public long func_154336_c() {
        return this.sizeOnDisk;
    }
    
    public boolean requiresConversion() {
        return this.requiresConversion;
    }
    
    public long getLastTimePlayed() {
        return this.lastTimePlayed;
    }
    
    public int compareTo(final SaveFormatComparator saveFormatComparator) {
        return (this.lastTimePlayed < saveFormatComparator.lastTimePlayed) ? 1 : ((this.lastTimePlayed > saveFormatComparator.lastTimePlayed) ? -1 : this.fileName.compareTo(saveFormatComparator.fileName));
    }
    
    public WorldSettings.GameType getEnumGameType() {
        return this.theEnumGameType;
    }
    
    public boolean isHardcoreModeEnabled() {
        return this.hardcore;
    }
    
    public boolean getCheatsEnabled() {
        return this.cheatsEnabled;
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((SaveFormatComparator)o);
    }
    
    static {
        __OBFID = "CL_00000601";
    }
}
