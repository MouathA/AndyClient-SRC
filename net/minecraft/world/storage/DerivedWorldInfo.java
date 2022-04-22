package net.minecraft.world.storage;

import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class DerivedWorldInfo extends WorldInfo
{
    private final WorldInfo theWorldInfo;
    private static final String __OBFID;
    
    public DerivedWorldInfo(final WorldInfo theWorldInfo) {
        this.theWorldInfo = theWorldInfo;
    }
    
    @Override
    public NBTTagCompound getNBTTagCompound() {
        return this.theWorldInfo.getNBTTagCompound();
    }
    
    @Override
    public NBTTagCompound cloneNBTCompound(final NBTTagCompound nbtTagCompound) {
        return this.theWorldInfo.cloneNBTCompound(nbtTagCompound);
    }
    
    @Override
    public long getSeed() {
        return this.theWorldInfo.getSeed();
    }
    
    @Override
    public int getSpawnX() {
        return this.theWorldInfo.getSpawnX();
    }
    
    @Override
    public int getSpawnY() {
        return this.theWorldInfo.getSpawnY();
    }
    
    @Override
    public int getSpawnZ() {
        return this.theWorldInfo.getSpawnZ();
    }
    
    @Override
    public long getWorldTotalTime() {
        return this.theWorldInfo.getWorldTotalTime();
    }
    
    @Override
    public long getWorldTime() {
        return this.theWorldInfo.getWorldTime();
    }
    
    @Override
    public long getSizeOnDisk() {
        return this.theWorldInfo.getSizeOnDisk();
    }
    
    @Override
    public NBTTagCompound getPlayerNBTTagCompound() {
        return this.theWorldInfo.getPlayerNBTTagCompound();
    }
    
    @Override
    public String getWorldName() {
        return this.theWorldInfo.getWorldName();
    }
    
    @Override
    public int getSaveVersion() {
        return this.theWorldInfo.getSaveVersion();
    }
    
    @Override
    public long getLastTimePlayed() {
        return this.theWorldInfo.getLastTimePlayed();
    }
    
    @Override
    public boolean isThundering() {
        return this.theWorldInfo.isThundering();
    }
    
    @Override
    public int getThunderTime() {
        return this.theWorldInfo.getThunderTime();
    }
    
    @Override
    public boolean isRaining() {
        return this.theWorldInfo.isRaining();
    }
    
    @Override
    public int getRainTime() {
        return this.theWorldInfo.getRainTime();
    }
    
    @Override
    public WorldSettings.GameType getGameType() {
        return this.theWorldInfo.getGameType();
    }
    
    @Override
    public void setSpawnX(final int n) {
    }
    
    @Override
    public void setSpawnY(final int n) {
    }
    
    @Override
    public void setSpawnZ(final int n) {
    }
    
    @Override
    public void incrementTotalWorldTime(final long n) {
    }
    
    @Override
    public void setWorldTime(final long n) {
    }
    
    @Override
    public void setSpawn(final BlockPos blockPos) {
    }
    
    @Override
    public void setWorldName(final String s) {
    }
    
    @Override
    public void setSaveVersion(final int n) {
    }
    
    @Override
    public void setThundering(final boolean b) {
    }
    
    @Override
    public void setThunderTime(final int n) {
    }
    
    @Override
    public void setRaining(final boolean b) {
    }
    
    @Override
    public void setRainTime(final int n) {
    }
    
    @Override
    public boolean isMapFeaturesEnabled() {
        return this.theWorldInfo.isMapFeaturesEnabled();
    }
    
    @Override
    public boolean isHardcoreModeEnabled() {
        return this.theWorldInfo.isHardcoreModeEnabled();
    }
    
    @Override
    public WorldType getTerrainType() {
        return this.theWorldInfo.getTerrainType();
    }
    
    @Override
    public void setTerrainType(final WorldType worldType) {
    }
    
    @Override
    public boolean areCommandsAllowed() {
        return this.theWorldInfo.areCommandsAllowed();
    }
    
    @Override
    public void setAllowCommands(final boolean b) {
    }
    
    @Override
    public boolean isInitialized() {
        return this.theWorldInfo.isInitialized();
    }
    
    @Override
    public void setServerInitialized(final boolean b) {
    }
    
    @Override
    public GameRules getGameRulesInstance() {
        return this.theWorldInfo.getGameRulesInstance();
    }
    
    @Override
    public EnumDifficulty getDifficulty() {
        return this.theWorldInfo.getDifficulty();
    }
    
    @Override
    public void setDifficulty(final EnumDifficulty enumDifficulty) {
    }
    
    @Override
    public boolean isDifficultyLocked() {
        return this.theWorldInfo.isDifficultyLocked();
    }
    
    @Override
    public void setDifficultyLocked(final boolean b) {
    }
    
    static {
        __OBFID = "CL_00000584";
    }
}
