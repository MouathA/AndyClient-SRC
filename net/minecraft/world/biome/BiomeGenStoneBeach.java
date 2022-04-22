package net.minecraft.world.biome;

import net.minecraft.init.*;

public class BiomeGenStoneBeach extends BiomeGenBase
{
    private static final String __OBFID;
    
    public BiomeGenStoneBeach(final int n) {
        super(n);
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.stone.getDefaultState();
        this.fillerBlock = Blocks.stone.getDefaultState();
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 0;
        this.theBiomeDecorator.reedsPerChunk = 0;
        this.theBiomeDecorator.cactiPerChunk = 0;
    }
    
    static {
        __OBFID = "CL_00000184";
    }
}
