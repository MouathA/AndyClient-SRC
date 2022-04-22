package net.minecraft.world.biome;

import net.minecraft.init.*;

public class BiomeGenBeach extends BiomeGenBase
{
    private static final String __OBFID;
    
    public BiomeGenBeach(final int n) {
        super(n);
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.sand.getDefaultState();
        this.fillerBlock = Blocks.sand.getDefaultState();
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 0;
        this.theBiomeDecorator.reedsPerChunk = 0;
        this.theBiomeDecorator.cactiPerChunk = 0;
    }
    
    static {
        __OBFID = "CL_00000157";
    }
}
