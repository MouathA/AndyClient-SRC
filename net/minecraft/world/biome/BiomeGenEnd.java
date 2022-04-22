package net.minecraft.world.biome;

import net.minecraft.entity.monster.*;
import net.minecraft.init.*;

public class BiomeGenEnd extends BiomeGenBase
{
    private static final String __OBFID;
    
    public BiomeGenEnd(final int n) {
        super(n);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 10, 4, 4));
        this.topBlock = Blocks.dirt.getDefaultState();
        this.fillerBlock = Blocks.dirt.getDefaultState();
        this.theBiomeDecorator = new BiomeEndDecorator();
    }
    
    @Override
    public int getSkyColorByTemp(final float n) {
        return 0;
    }
    
    static {
        __OBFID = "CL_00000187";
    }
}
