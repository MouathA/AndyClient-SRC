package net.minecraft.world.biome;

import net.minecraft.init.*;
import net.minecraft.entity.passive.*;

public class BiomeGenMushroomIsland extends BiomeGenBase
{
    private static final String __OBFID;
    
    public BiomeGenMushroomIsland(final int n) {
        super(n);
        this.theBiomeDecorator.treesPerChunk = -100;
        this.theBiomeDecorator.flowersPerChunk = -100;
        this.theBiomeDecorator.grassPerChunk = -100;
        this.theBiomeDecorator.mushroomsPerChunk = 1;
        this.theBiomeDecorator.bigMushroomsPerChunk = 1;
        this.topBlock = Blocks.mycelium.getDefaultState();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCreatureList.add(new SpawnListEntry(EntityMooshroom.class, 8, 4, 8));
    }
    
    static {
        __OBFID = "CL_00000177";
    }
}
