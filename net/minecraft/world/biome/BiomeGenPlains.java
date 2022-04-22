package net.minecraft.world.biome;

import net.minecraft.entity.passive.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.*;

public class BiomeGenPlains extends BiomeGenBase
{
    protected boolean field_150628_aC;
    private static final String __OBFID;
    
    protected BiomeGenPlains(final int n) {
        super(n);
        this.setTemperatureRainfall(0.8f, 0.4f);
        this.setHeight(BiomeGenPlains.height_LowPlains);
        this.spawnableCreatureList.add(new SpawnListEntry(EntityHorse.class, 5, 2, 6));
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.flowersPerChunk = 4;
        this.theBiomeDecorator.grassPerChunk = 10;
    }
    
    @Override
    public BlockFlower.EnumFlowerType pickRandomFlower(final Random random, final BlockPos blockPos) {
        if (BiomeGenPlains.field_180281_af.func_151601_a(blockPos.getX() / 200.0, blockPos.getZ() / 200.0) < -0.8) {
            switch (random.nextInt(4)) {
                case 0: {
                    return BlockFlower.EnumFlowerType.ORANGE_TULIP;
                }
                case 1: {
                    return BlockFlower.EnumFlowerType.RED_TULIP;
                }
                case 2: {
                    return BlockFlower.EnumFlowerType.PINK_TULIP;
                }
                default: {
                    return BlockFlower.EnumFlowerType.WHITE_TULIP;
                }
            }
        }
        else {
            if (random.nextInt(3) > 0) {
                final int nextInt = random.nextInt(3);
                return (nextInt == 0) ? BlockFlower.EnumFlowerType.POPPY : ((nextInt == 1) ? BlockFlower.EnumFlowerType.HOUSTONIA : BlockFlower.EnumFlowerType.OXEYE_DAISY);
            }
            return BlockFlower.EnumFlowerType.DANDELION;
        }
    }
    
    @Override
    public void func_180624_a(final World world, final Random random, final BlockPos blockPos) {
        int n3 = 0;
        if (BiomeGenPlains.field_180281_af.func_151601_a((blockPos.getX() + 8) / 200.0, (blockPos.getZ() + 8) / 200.0) < -0.8) {
            this.theBiomeDecorator.flowersPerChunk = 15;
            this.theBiomeDecorator.grassPerChunk = 5;
        }
        else {
            this.theBiomeDecorator.flowersPerChunk = 4;
            this.theBiomeDecorator.grassPerChunk = 10;
            BiomeGenPlains.field_180280_ag.func_180710_a(BlockDoublePlant.EnumPlantType.GRASS);
            while (0 < 7) {
                final int n = random.nextInt(16) + 8;
                final int n2 = random.nextInt(16) + 8;
                BiomeGenPlains.field_180280_ag.generate(world, random, blockPos.add(n, random.nextInt(world.getHorizon(blockPos.add(n, 0, n2)).getY() + 32), n2));
                ++n3;
            }
        }
        if (this.field_150628_aC) {
            BiomeGenPlains.field_180280_ag.func_180710_a(BlockDoublePlant.EnumPlantType.SUNFLOWER);
            while (0 < 10) {
                final int n4 = random.nextInt(16) + 8;
                final int n5 = random.nextInt(16) + 8;
                BiomeGenPlains.field_180280_ag.generate(world, random, blockPos.add(n4, random.nextInt(world.getHorizon(blockPos.add(n4, 0, n5)).getY() + 32), n5));
                ++n3;
            }
        }
        super.func_180624_a(world, random, blockPos);
    }
    
    @Override
    protected BiomeGenBase createMutatedBiome(final int n) {
        final BiomeGenPlains biomeGenPlains = new BiomeGenPlains(n);
        biomeGenPlains.setBiomeName("Sunflower Plains");
        biomeGenPlains.field_150628_aC = true;
        biomeGenPlains.setColor(9286496);
        biomeGenPlains.field_150609_ah = 14273354;
        return biomeGenPlains;
    }
    
    static {
        __OBFID = "CL_00000180";
    }
}
