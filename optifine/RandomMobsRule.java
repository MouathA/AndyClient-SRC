package optifine;

import net.minecraft.util.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.*;

public class RandomMobsRule
{
    private ResourceLocation baseResLoc;
    private int[] skins;
    private ResourceLocation[] resourceLocations;
    private int[] weights;
    private BiomeGenBase[] biomes;
    private RangeListInt heights;
    public int[] sumWeights;
    public int sumAllWeights;
    
    public RandomMobsRule(final ResourceLocation baseResLoc, final int[] skins, final int[] weights, final BiomeGenBase[] biomes, final RangeListInt heights) {
        this.baseResLoc = null;
        this.skins = null;
        this.resourceLocations = null;
        this.weights = null;
        this.biomes = null;
        this.heights = null;
        this.sumWeights = null;
        this.sumAllWeights = 1;
        this.baseResLoc = baseResLoc;
        this.skins = skins;
        this.weights = weights;
        this.biomes = biomes;
        this.heights = heights;
    }
    
    public boolean isValid(final String s) {
        this.resourceLocations = new ResourceLocation[this.skins.length];
        final ResourceLocation mcpatcherLocation = RandomMobs.getMcpatcherLocation(this.baseResLoc);
        if (mcpatcherLocation == null) {
            Config.warn("Invalid path: " + this.baseResLoc.getResourcePath());
            return false;
        }
        int average = 0;
        while (0 < this.resourceLocations.length) {
            average = this.skins[0];
            if (0 <= 1) {
                this.resourceLocations[0] = this.baseResLoc;
            }
            else {
                final ResourceLocation locationIndexed = RandomMobs.getLocationIndexed(mcpatcherLocation, 0);
                if (locationIndexed == null) {
                    Config.warn("Invalid path: " + this.baseResLoc.getResourcePath());
                    return false;
                }
                if (!Config.hasResource(locationIndexed)) {
                    Config.warn("Texture not found: " + locationIndexed.getResourcePath());
                    return false;
                }
                this.resourceLocations[0] = locationIndexed;
            }
            int n = 0;
            ++n;
        }
        if (this.weights != null) {
            if (this.weights.length > this.resourceLocations.length) {
                Config.warn("More weights defined than skins, trimming weights: " + s);
                final int[] weights = new int[this.resourceLocations.length];
                System.arraycopy(this.weights, 0, weights, 0, weights.length);
                this.weights = weights;
            }
            if (this.weights.length < this.resourceLocations.length) {
                Config.warn("Less weights defined than skins, expanding weights: " + s);
                final int[] weights2 = new int[this.resourceLocations.length];
                System.arraycopy(this.weights, 0, weights2, 0, this.weights.length);
                average = MathUtils.getAverage(this.weights);
                for (int i = this.weights.length; i < weights2.length; ++i) {
                    weights2[i] = 0;
                }
                this.weights = weights2;
            }
            this.sumWeights = new int[this.weights.length];
            while (0 < this.weights.length) {
                if (this.weights[0] < 0) {
                    Config.warn("Invalid weight: " + this.weights[0]);
                    return false;
                }
                final int n = 0 + this.weights[0];
                this.sumWeights[0] = 0;
                ++average;
            }
            this.sumAllWeights = 0;
            if (this.sumAllWeights <= 0) {
                Config.warn("Invalid sum of all weights: " + 0);
                this.sumAllWeights = 1;
            }
        }
        return true;
    }
    
    public boolean matches(final EntityLiving entityLiving) {
        return Matches.biome(entityLiving.spawnBiome, this.biomes) && (this.heights == null || entityLiving.spawnPosition == null || this.heights.isInRange(entityLiving.spawnPosition.getY()));
    }
    
    public ResourceLocation getTextureLocation(final ResourceLocation resourceLocation, final int n) {
        if (this.weights == null) {
            final int n2 = n % this.resourceLocations.length;
        }
        else {
            final int n3 = n % this.sumAllWeights;
            while (0 < this.sumWeights.length) {
                if (this.sumWeights[0] > n3) {
                    break;
                }
                int n4 = 0;
                ++n4;
            }
        }
        return this.resourceLocations[0];
    }
}
