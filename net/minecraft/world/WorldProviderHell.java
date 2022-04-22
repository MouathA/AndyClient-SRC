package net.minecraft.world;

import net.minecraft.world.biome.*;
import net.minecraft.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.border.*;

public class WorldProviderHell extends WorldProvider
{
    private static final String __OBFID;
    
    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.hell, 0.0f);
        this.isHellWorld = true;
        this.hasNoSky = true;
        this.dimensionId = -1;
    }
    
    @Override
    public Vec3 getFogColor(final float n, final float n2) {
        return new Vec3(0.20000000298023224, 0.029999999329447746, 0.029999999329447746);
    }
    
    @Override
    protected void generateLightBrightnessTable() {
        final float n = 0.1f;
        while (0 <= 15) {
            final float n2 = 1.0f - 0 / 15.0f;
            this.lightBrightnessTable[0] = (1.0f - n2) / (n2 * 3.0f + 1.0f) * (1.0f - n) + n;
            int n3 = 0;
            ++n3;
        }
    }
    
    @Override
    public IChunkProvider createChunkGenerator() {
        return (IChunkProvider)new ChunkProviderHell(this.worldObj, this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.worldObj.getSeed());
    }
    
    @Override
    public boolean isSurfaceWorld() {
        return false;
    }
    
    @Override
    public boolean canCoordinateBeSpawn(final int n, final int n2) {
        return false;
    }
    
    @Override
    public float calculateCelestialAngle(final long n, final float n2) {
        return 0.5f;
    }
    
    @Override
    public boolean canRespawnHere() {
        return false;
    }
    
    @Override
    public boolean doesXZShowFog(final int n, final int n2) {
        return true;
    }
    
    @Override
    public String getDimensionName() {
        return "Nether";
    }
    
    @Override
    public String getInternalNameSuffix() {
        return "_nether";
    }
    
    @Override
    public WorldBorder getWorldBorder() {
        return new WorldBorder() {
            private static final String __OBFID;
            final WorldProviderHell this$0;
            
            @Override
            public double getCenterX() {
                return super.getCenterX() / 8.0;
            }
            
            @Override
            public double getCenterZ() {
                return super.getCenterZ() / 8.0;
            }
            
            static {
                __OBFID = "CL_00002008";
            }
        };
    }
    
    static {
        __OBFID = "CL_00000387";
    }
}
