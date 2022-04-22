package net.minecraft.world.biome;

import net.minecraft.init.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.gen.feature.*;

public class BiomeGenSnow extends BiomeGenBase
{
    private boolean field_150615_aC;
    private WorldGenIceSpike field_150616_aD;
    private WorldGenIcePath field_150617_aE;
    private static final String __OBFID;
    
    public BiomeGenSnow(final int n, final boolean field_150615_aC) {
        super(n);
        this.field_150616_aD = new WorldGenIceSpike();
        this.field_150617_aE = new WorldGenIcePath(4);
        this.field_150615_aC = field_150615_aC;
        if (field_150615_aC) {
            this.topBlock = Blocks.snow.getDefaultState();
        }
        this.spawnableCreatureList.clear();
    }
    
    @Override
    public void func_180624_a(final World world, final Random random, final BlockPos blockPos) {
        if (this.field_150615_aC) {
            int n = 0;
            while (0 < 3) {
                this.field_150616_aD.generate(world, random, world.getHorizon(blockPos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8)));
                ++n;
            }
            while (0 < 2) {
                this.field_150617_aE.generate(world, random, world.getHorizon(blockPos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8)));
                ++n;
            }
        }
        super.func_180624_a(world, random, blockPos);
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return (WorldGenAbstractTree)new WorldGenTaiga2(false);
    }
    
    @Override
    protected BiomeGenBase createMutatedBiome(final int n) {
        final BiomeGenBase setHeight = new BiomeGenSnow(n, true).func_150557_a(13828095, true).setBiomeName(String.valueOf(this.biomeName) + " Spikes").setEnableSnow().setTemperatureRainfall(0.0f, 0.5f).setHeight(new Height(this.minHeight + 0.1f, this.maxHeight + 0.1f));
        setHeight.minHeight = this.minHeight + 0.3f;
        setHeight.maxHeight = this.maxHeight + 0.4f;
        return setHeight;
    }
    
    static {
        __OBFID = "CL_00000174";
    }
}
