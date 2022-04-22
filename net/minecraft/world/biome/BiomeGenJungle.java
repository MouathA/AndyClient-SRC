package net.minecraft.world.biome;

import net.minecraft.entity.passive.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.world.gen.feature.*;

public class BiomeGenJungle extends BiomeGenBase
{
    private boolean field_150614_aC;
    private static final String __OBFID;
    
    public BiomeGenJungle(final int n, final boolean field_150614_aC) {
        super(n);
        this.field_150614_aC = field_150614_aC;
        if (field_150614_aC) {
            this.theBiomeDecorator.treesPerChunk = 2;
        }
        else {
            this.theBiomeDecorator.treesPerChunk = 50;
        }
        this.theBiomeDecorator.grassPerChunk = 25;
        this.theBiomeDecorator.flowersPerChunk = 4;
        if (!field_150614_aC) {
            this.spawnableMonsterList.add(new SpawnListEntry(EntityOcelot.class, 2, 1, 1));
        }
        this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10, 4, 4));
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return (WorldGenAbstractTree)((random.nextInt(10) == 0) ? this.worldGeneratorBigTree : ((random.nextInt(2) == 0) ? new WorldGenShrub(BlockPlanks.EnumType.JUNGLE.func_176839_a(), BlockPlanks.EnumType.OAK.func_176839_a()) : ((!this.field_150614_aC && random.nextInt(3) == 0) ? new WorldGenMegaJungle(false, 10, 20, BlockPlanks.EnumType.JUNGLE.func_176839_a(), BlockPlanks.EnumType.JUNGLE.func_176839_a()) : new WorldGenTrees(false, 4 + random.nextInt(7), BlockPlanks.EnumType.JUNGLE.func_176839_a(), BlockPlanks.EnumType.JUNGLE.func_176839_a(), true))));
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForGrass(final Random random) {
        return (WorldGenerator)((random.nextInt(4) == 0) ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS));
    }
    
    @Override
    public void func_180624_a(final World world, final Random random, final BlockPos blockPos) {
        super.func_180624_a(world, random, blockPos);
        final int n = random.nextInt(16) + 8;
        int n2 = random.nextInt(16) + 8;
        new WorldGenMelon().generate(world, random, blockPos.add(n, random.nextInt(world.getHorizon(blockPos.add(n, 0, 0)).getY() * 2), 0));
        final WorldGenVines worldGenVines = new WorldGenVines();
        while (0 < 50) {
            worldGenVines.generate(world, random, blockPos.add(random.nextInt(16) + 8, 128, random.nextInt(16) + 8));
            ++n2;
        }
    }
    
    static {
        __OBFID = "CL_00000175";
    }
}
