package net.minecraft.realms;

import net.minecraft.world.storage.*;

public class RealmsLevelSummary implements Comparable
{
    private SaveFormatComparator levelSummary;
    private static final String __OBFID;
    
    public RealmsLevelSummary(final SaveFormatComparator levelSummary) {
        this.levelSummary = levelSummary;
    }
    
    public int getGameMode() {
        return this.levelSummary.getEnumGameType().getID();
    }
    
    public String getLevelId() {
        return this.levelSummary.getFileName();
    }
    
    public boolean hasCheats() {
        return this.levelSummary.getCheatsEnabled();
    }
    
    public boolean isHardcore() {
        return this.levelSummary.isHardcoreModeEnabled();
    }
    
    public boolean isRequiresConversion() {
        return this.levelSummary.requiresConversion();
    }
    
    public String getLevelName() {
        return this.levelSummary.getDisplayName();
    }
    
    public long getLastPlayed() {
        return this.levelSummary.getLastTimePlayed();
    }
    
    public int compareTo(final SaveFormatComparator saveFormatComparator) {
        return this.levelSummary.compareTo(saveFormatComparator);
    }
    
    public long getSizeOnDisk() {
        return this.levelSummary.func_154336_c();
    }
    
    public int compareTo(final RealmsLevelSummary realmsLevelSummary) {
        return (this.levelSummary.getLastTimePlayed() < realmsLevelSummary.getLastPlayed()) ? 1 : ((this.levelSummary.getLastTimePlayed() > realmsLevelSummary.getLastPlayed()) ? -1 : this.levelSummary.getFileName().compareTo(realmsLevelSummary.getLevelId()));
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((RealmsLevelSummary)o);
    }
    
    static {
        __OBFID = "CL_00001857";
    }
}
