package net.minecraft.world.biome;

import net.minecraft.world.gen.*;
import net.minecraft.block.state.*;
import org.apache.logging.log4j.*;
import net.minecraft.init.*;
import com.google.common.collect.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.block.*;
import java.awt.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.*;
import java.util.*;
import java.nio.charset.*;

public abstract class BiomeGenBase
{
    private static final Logger logger;
    protected static final Height height_Default;
    protected static final Height height_ShallowWaters;
    protected static final Height height_Oceans;
    protected static final Height height_DeepOceans;
    protected static final Height height_LowPlains;
    protected static final Height height_MidPlains;
    protected static final Height height_LowHills;
    protected static final Height height_HighPlateaus;
    protected static final Height height_MidHills;
    protected static final Height height_Shores;
    protected static final Height height_RockyWaters;
    protected static final Height height_LowIslands;
    protected static final Height height_PartiallySubmerged;
    private static final BiomeGenBase[] biomeList;
    public static final Set explorationBiomesList;
    public static final Map field_180278_o;
    public static final BiomeGenBase ocean;
    public static final BiomeGenBase plains;
    public static final BiomeGenBase desert;
    public static final BiomeGenBase extremeHills;
    public static final BiomeGenBase forest;
    public static final BiomeGenBase taiga;
    public static final BiomeGenBase swampland;
    public static final BiomeGenBase river;
    public static final BiomeGenBase hell;
    public static final BiomeGenBase sky;
    public static final BiomeGenBase frozenOcean;
    public static final BiomeGenBase frozenRiver;
    public static final BiomeGenBase icePlains;
    public static final BiomeGenBase iceMountains;
    public static final BiomeGenBase mushroomIsland;
    public static final BiomeGenBase mushroomIslandShore;
    public static final BiomeGenBase beach;
    public static final BiomeGenBase desertHills;
    public static final BiomeGenBase forestHills;
    public static final BiomeGenBase taigaHills;
    public static final BiomeGenBase extremeHillsEdge;
    public static final BiomeGenBase jungle;
    public static final BiomeGenBase jungleHills;
    public static final BiomeGenBase jungleEdge;
    public static final BiomeGenBase deepOcean;
    public static final BiomeGenBase stoneBeach;
    public static final BiomeGenBase coldBeach;
    public static final BiomeGenBase birchForest;
    public static final BiomeGenBase birchForestHills;
    public static final BiomeGenBase roofedForest;
    public static final BiomeGenBase coldTaiga;
    public static final BiomeGenBase coldTaigaHills;
    public static final BiomeGenBase megaTaiga;
    public static final BiomeGenBase megaTaigaHills;
    public static final BiomeGenBase extremeHillsPlus;
    public static final BiomeGenBase savanna;
    public static final BiomeGenBase savannaPlateau;
    public static final BiomeGenBase mesa;
    public static final BiomeGenBase mesaPlateau_F;
    public static final BiomeGenBase mesaPlateau;
    public static final BiomeGenBase field_180279_ad;
    protected static final NoiseGeneratorPerlin temperatureNoise;
    protected static final NoiseGeneratorPerlin field_180281_af;
    protected static final WorldGenDoublePlant field_180280_ag;
    public String biomeName;
    public int color;
    public int field_150609_ah;
    public IBlockState topBlock;
    public IBlockState fillerBlock;
    public int fillerBlockMetadata;
    public float minHeight;
    public float maxHeight;
    public float temperature;
    public float rainfall;
    public int waterColorMultiplier;
    public BiomeDecorator theBiomeDecorator;
    protected List spawnableMonsterList;
    protected List spawnableCreatureList;
    protected List spawnableWaterCreatureList;
    protected List spawnableCaveCreatureList;
    protected boolean enableSnow;
    protected boolean enableRain;
    public final int biomeID;
    protected WorldGenTrees worldGeneratorTrees;
    protected WorldGenBigTree worldGeneratorBigTree;
    protected WorldGenSwamp worldGeneratorSwamp;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000158";
        logger = LogManager.getLogger();
        height_Default = new Height(0.1f, 0.2f);
        height_ShallowWaters = new Height(-0.5f, 0.0f);
        height_Oceans = new Height(-1.0f, 0.1f);
        height_DeepOceans = new Height(-1.8f, 0.1f);
        height_LowPlains = new Height(0.125f, 0.05f);
        height_MidPlains = new Height(0.2f, 0.2f);
        height_LowHills = new Height(0.45f, 0.3f);
        height_HighPlateaus = new Height(1.5f, 0.025f);
        height_MidHills = new Height(1.0f, 0.5f);
        height_Shores = new Height(0.0f, 0.025f);
        height_RockyWaters = new Height(0.1f, 0.8f);
        height_LowIslands = new Height(0.2f, 0.3f);
        height_PartiallySubmerged = new Height(-0.2f, 0.1f);
        biomeList = new BiomeGenBase[256];
        explorationBiomesList = Sets.newHashSet();
        field_180278_o = Maps.newHashMap();
        ocean = new BiomeGenOcean(0).setColor(112).setBiomeName("Ocean").setHeight(BiomeGenBase.height_Oceans);
        plains = new BiomeGenPlains(1).setColor(9286496).setBiomeName("Plains");
        desert = new BiomeGenDesert(2).setColor(16421912).setBiomeName("Desert").setDisableRain().setTemperatureRainfall(2.0f, 0.0f).setHeight(BiomeGenBase.height_LowPlains);
        extremeHills = new BiomeGenHills(3, false).setColor(6316128).setBiomeName("Extreme Hills").setHeight(BiomeGenBase.height_MidHills).setTemperatureRainfall(0.2f, 0.3f);
        forest = new BiomeGenForest(4, 0).setColor(353825).setBiomeName("Forest");
        taiga = new BiomeGenTaiga(5, 0).setColor(747097).setBiomeName("Taiga").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.25f, 0.8f).setHeight(BiomeGenBase.height_MidPlains);
        swampland = new BiomeGenSwamp(6).setColor(522674).setBiomeName("Swampland").setFillerBlockMetadata(9154376).setHeight(BiomeGenBase.height_PartiallySubmerged).setTemperatureRainfall(0.8f, 0.9f);
        river = new BiomeGenRiver(7).setColor(255).setBiomeName("River").setHeight(BiomeGenBase.height_ShallowWaters);
        hell = new BiomeGenHell(8).setColor(16711680).setBiomeName("Hell").setDisableRain().setTemperatureRainfall(2.0f, 0.0f);
        sky = new BiomeGenEnd(9).setColor(8421631).setBiomeName("The End").setDisableRain();
        frozenOcean = new BiomeGenOcean(10).setColor(9474208).setBiomeName("FrozenOcean").setEnableSnow().setHeight(BiomeGenBase.height_Oceans).setTemperatureRainfall(0.0f, 0.5f);
        frozenRiver = new BiomeGenRiver(11).setColor(10526975).setBiomeName("FrozenRiver").setEnableSnow().setHeight(BiomeGenBase.height_ShallowWaters).setTemperatureRainfall(0.0f, 0.5f);
        icePlains = new BiomeGenSnow(12, false).setColor(16777215).setBiomeName("Ice Plains").setEnableSnow().setTemperatureRainfall(0.0f, 0.5f).setHeight(BiomeGenBase.height_LowPlains);
        iceMountains = new BiomeGenSnow(13, false).setColor(10526880).setBiomeName("Ice Mountains").setEnableSnow().setHeight(BiomeGenBase.height_LowHills).setTemperatureRainfall(0.0f, 0.5f);
        mushroomIsland = new BiomeGenMushroomIsland(14).setColor(16711935).setBiomeName("MushroomIsland").setTemperatureRainfall(0.9f, 1.0f).setHeight(BiomeGenBase.height_LowIslands);
        mushroomIslandShore = new BiomeGenMushroomIsland(15).setColor(10486015).setBiomeName("MushroomIslandShore").setTemperatureRainfall(0.9f, 1.0f).setHeight(BiomeGenBase.height_Shores);
        beach = new BiomeGenBeach(16).setColor(16440917).setBiomeName("Beach").setTemperatureRainfall(0.8f, 0.4f).setHeight(BiomeGenBase.height_Shores);
        desertHills = new BiomeGenDesert(17).setColor(13786898).setBiomeName("DesertHills").setDisableRain().setTemperatureRainfall(2.0f, 0.0f).setHeight(BiomeGenBase.height_LowHills);
        forestHills = new BiomeGenForest(18, 0).setColor(2250012).setBiomeName("ForestHills").setHeight(BiomeGenBase.height_LowHills);
        taigaHills = new BiomeGenTaiga(19, 0).setColor(1456435).setBiomeName("TaigaHills").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.25f, 0.8f).setHeight(BiomeGenBase.height_LowHills);
        extremeHillsEdge = new BiomeGenHills(20, true).setColor(7501978).setBiomeName("Extreme Hills Edge").setHeight(BiomeGenBase.height_MidHills.attenuate()).setTemperatureRainfall(0.2f, 0.3f);
        jungle = new BiomeGenJungle(21, false).setColor(5470985).setBiomeName("Jungle").setFillerBlockMetadata(5470985).setTemperatureRainfall(0.95f, 0.9f);
        jungleHills = new BiomeGenJungle(22, false).setColor(2900485).setBiomeName("JungleHills").setFillerBlockMetadata(5470985).setTemperatureRainfall(0.95f, 0.9f).setHeight(BiomeGenBase.height_LowHills);
        jungleEdge = new BiomeGenJungle(23, true).setColor(6458135).setBiomeName("JungleEdge").setFillerBlockMetadata(5470985).setTemperatureRainfall(0.95f, 0.8f);
        deepOcean = new BiomeGenOcean(24).setColor(48).setBiomeName("Deep Ocean").setHeight(BiomeGenBase.height_DeepOceans);
        stoneBeach = new BiomeGenStoneBeach(25).setColor(10658436).setBiomeName("Stone Beach").setTemperatureRainfall(0.2f, 0.3f).setHeight(BiomeGenBase.height_RockyWaters);
        coldBeach = new BiomeGenBeach(26).setColor(16445632).setBiomeName("Cold Beach").setTemperatureRainfall(0.05f, 0.3f).setHeight(BiomeGenBase.height_Shores).setEnableSnow();
        birchForest = new BiomeGenForest(27, 2).setBiomeName("Birch Forest").setColor(3175492);
        birchForestHills = new BiomeGenForest(28, 2).setBiomeName("Birch Forest Hills").setColor(2055986).setHeight(BiomeGenBase.height_LowHills);
        roofedForest = new BiomeGenForest(29, 3).setColor(4215066).setBiomeName("Roofed Forest");
        coldTaiga = new BiomeGenTaiga(30, 0).setColor(3233098).setBiomeName("Cold Taiga").setFillerBlockMetadata(5159473).setEnableSnow().setTemperatureRainfall(-0.5f, 0.4f).setHeight(BiomeGenBase.height_MidPlains).func_150563_c(16777215);
        coldTaigaHills = new BiomeGenTaiga(31, 0).setColor(2375478).setBiomeName("Cold Taiga Hills").setFillerBlockMetadata(5159473).setEnableSnow().setTemperatureRainfall(-0.5f, 0.4f).setHeight(BiomeGenBase.height_LowHills).func_150563_c(16777215);
        megaTaiga = new BiomeGenTaiga(32, 1).setColor(5858897).setBiomeName("Mega Taiga").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.3f, 0.8f).setHeight(BiomeGenBase.height_MidPlains);
        megaTaigaHills = new BiomeGenTaiga(33, 1).setColor(4542270).setBiomeName("Mega Taiga Hills").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.3f, 0.8f).setHeight(BiomeGenBase.height_LowHills);
        extremeHillsPlus = new BiomeGenHills(34, true).setColor(5271632).setBiomeName("Extreme Hills+").setHeight(BiomeGenBase.height_MidHills).setTemperatureRainfall(0.2f, 0.3f);
        savanna = new BiomeGenSavanna(35).setColor(12431967).setBiomeName("Savanna").setTemperatureRainfall(1.2f, 0.0f).setDisableRain().setHeight(BiomeGenBase.height_LowPlains);
        savannaPlateau = new BiomeGenSavanna(36).setColor(10984804).setBiomeName("Savanna Plateau").setTemperatureRainfall(1.0f, 0.0f).setDisableRain().setHeight(BiomeGenBase.height_HighPlateaus);
        mesa = new BiomeGenMesa(37, false, false).setColor(14238997).setBiomeName("Mesa");
        mesaPlateau_F = new BiomeGenMesa(38, false, true).setColor(11573093).setBiomeName("Mesa Plateau F").setHeight(BiomeGenBase.height_HighPlateaus);
        mesaPlateau = new BiomeGenMesa(39, false, false).setColor(13274213).setBiomeName("Mesa Plateau").setHeight(BiomeGenBase.height_HighPlateaus);
        field_180279_ad = BiomeGenBase.ocean;
        BiomeGenBase.plains.createMutation();
        BiomeGenBase.desert.createMutation();
        BiomeGenBase.forest.createMutation();
        BiomeGenBase.taiga.createMutation();
        BiomeGenBase.swampland.createMutation();
        BiomeGenBase.icePlains.createMutation();
        BiomeGenBase.jungle.createMutation();
        BiomeGenBase.jungleEdge.createMutation();
        BiomeGenBase.coldTaiga.createMutation();
        BiomeGenBase.savanna.createMutation();
        BiomeGenBase.savannaPlateau.createMutation();
        BiomeGenBase.mesa.createMutation();
        BiomeGenBase.mesaPlateau_F.createMutation();
        BiomeGenBase.mesaPlateau.createMutation();
        BiomeGenBase.birchForest.createMutation();
        BiomeGenBase.birchForestHills.createMutation();
        BiomeGenBase.roofedForest.createMutation();
        BiomeGenBase.megaTaiga.createMutation();
        BiomeGenBase.extremeHills.createMutation();
        BiomeGenBase.extremeHillsPlus.createMutation();
        BiomeGenBase.megaTaiga.createMutatedBiome(BiomeGenBase.megaTaigaHills.biomeID + 128).setBiomeName("Redwood Taiga Hills M");
        final BiomeGenBase[] biomeList2 = BiomeGenBase.biomeList;
        while (0 < biomeList2.length) {
            final BiomeGenBase biomeGenBase = biomeList2[0];
            if (biomeGenBase != null) {
                if (BiomeGenBase.field_180278_o.containsKey(biomeGenBase.biomeName)) {
                    throw new Error("Biome \"" + biomeGenBase.biomeName + "\" is defined as both ID " + ((BiomeGenBase)BiomeGenBase.field_180278_o.get(biomeGenBase.biomeName)).biomeID + " and " + biomeGenBase.biomeID);
                }
                BiomeGenBase.field_180278_o.put(biomeGenBase.biomeName, biomeGenBase);
                if (biomeGenBase.biomeID < 128) {
                    BiomeGenBase.explorationBiomesList.add(biomeGenBase);
                }
            }
            int n = 0;
            ++n;
        }
        BiomeGenBase.explorationBiomesList.remove(BiomeGenBase.hell);
        BiomeGenBase.explorationBiomesList.remove(BiomeGenBase.sky);
        BiomeGenBase.explorationBiomesList.remove(BiomeGenBase.frozenOcean);
        BiomeGenBase.explorationBiomesList.remove(BiomeGenBase.extremeHillsEdge);
        temperatureNoise = new NoiseGeneratorPerlin(new Random(1234L), 1);
        field_180281_af = new NoiseGeneratorPerlin(new Random(2345L), 1);
        field_180280_ag = new WorldGenDoublePlant();
    }
    
    protected BiomeGenBase(final int biomeID) {
        this.topBlock = Blocks.grass.getDefaultState();
        this.fillerBlock = Blocks.dirt.getDefaultState();
        this.fillerBlockMetadata = 5169201;
        this.minHeight = BiomeGenBase.height_Default.rootHeight;
        this.maxHeight = BiomeGenBase.height_Default.variation;
        this.temperature = 0.5f;
        this.rainfall = 0.5f;
        this.waterColorMultiplier = 16777215;
        this.spawnableMonsterList = Lists.newArrayList();
        this.spawnableCreatureList = Lists.newArrayList();
        this.spawnableWaterCreatureList = Lists.newArrayList();
        this.spawnableCaveCreatureList = Lists.newArrayList();
        this.enableRain = true;
        this.worldGeneratorTrees = new WorldGenTrees(false);
        this.worldGeneratorBigTree = new WorldGenBigTree(false);
        this.worldGeneratorSwamp = new WorldGenSwamp();
        this.biomeID = biomeID;
        BiomeGenBase.biomeList[biomeID] = this;
        this.theBiomeDecorator = this.createBiomeDecorator();
        this.spawnableCreatureList.add(new SpawnListEntry(EntitySheep.class, 12, 4, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityRabbit.class, 10, 3, 3));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityPig.class, 10, 4, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10, 4, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 8, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeper.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 10, 1, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityWitch.class, 5, 1, 1));
        this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquid.class, 10, 4, 4));
        this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityBat.class, 10, 8, 8));
    }
    
    protected BiomeDecorator createBiomeDecorator() {
        return new BiomeDecorator();
    }
    
    protected BiomeGenBase setTemperatureRainfall(final float temperature, final float rainfall) {
        if (temperature > 0.1f && temperature < 0.2f) {
            throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
        }
        this.temperature = temperature;
        this.rainfall = rainfall;
        return this;
    }
    
    protected final BiomeGenBase setHeight(final Height height) {
        this.minHeight = height.rootHeight;
        this.maxHeight = height.variation;
        return this;
    }
    
    protected BiomeGenBase setDisableRain() {
        this.enableRain = false;
        return this;
    }
    
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return (WorldGenAbstractTree)((random.nextInt(10) == 0) ? this.worldGeneratorBigTree : this.worldGeneratorTrees);
    }
    
    public WorldGenerator getRandomWorldGenForGrass(final Random random) {
        return (WorldGenerator)new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
    }
    
    public BlockFlower.EnumFlowerType pickRandomFlower(final Random random, final BlockPos blockPos) {
        return (random.nextInt(3) > 0) ? BlockFlower.EnumFlowerType.DANDELION : BlockFlower.EnumFlowerType.POPPY;
    }
    
    protected BiomeGenBase setEnableSnow() {
        this.enableSnow = true;
        return this;
    }
    
    protected BiomeGenBase setBiomeName(final String biomeName) {
        this.biomeName = biomeName;
        return this;
    }
    
    protected BiomeGenBase setFillerBlockMetadata(final int fillerBlockMetadata) {
        this.fillerBlockMetadata = fillerBlockMetadata;
        return this;
    }
    
    protected BiomeGenBase setColor(final int n) {
        this.func_150557_a(n, false);
        return this;
    }
    
    protected BiomeGenBase func_150563_c(final int field_150609_ah) {
        this.field_150609_ah = field_150609_ah;
        return this;
    }
    
    protected BiomeGenBase func_150557_a(final int n, final boolean b) {
        this.color = n;
        if (b) {
            this.field_150609_ah = (n & 0xFEFEFE) >> 1;
        }
        else {
            this.field_150609_ah = n;
        }
        return this;
    }
    
    public int getSkyColorByTemp(float clamp_float) {
        clamp_float /= 3.0f;
        clamp_float = MathHelper.clamp_float(clamp_float, -1.0f, 1.0f);
        return Color.getHSBColor(0.62222224f - clamp_float * 0.05f, 0.5f + clamp_float * 0.1f, 1.0f).getRGB();
    }
    
    public List getSpawnableList(final EnumCreatureType enumCreatureType) {
        switch (SwitchEnumCreatureType.field_180275_a[enumCreatureType.ordinal()]) {
            case 1: {
                return this.spawnableMonsterList;
            }
            case 2: {
                return this.spawnableCreatureList;
            }
            case 3: {
                return this.spawnableWaterCreatureList;
            }
            case 4: {
                return this.spawnableCaveCreatureList;
            }
            default: {
                return Collections.emptyList();
            }
        }
    }
    
    public boolean getEnableSnow() {
        return this.isSnowyBiome();
    }
    
    public boolean canSpawnLightningBolt() {
        return !this.isSnowyBiome() && this.enableRain;
    }
    
    public boolean isHighHumidity() {
        return this.rainfall > 0.85f;
    }
    
    public float getSpawningChance() {
        return 0.1f;
    }
    
    public final int getIntRainfall() {
        return (int)(this.rainfall * 65536.0f);
    }
    
    public final float getFloatRainfall() {
        return this.rainfall;
    }
    
    public final float func_180626_a(final BlockPos blockPos) {
        if (blockPos.getY() > 64) {
            return this.temperature - ((float)(BiomeGenBase.temperatureNoise.func_151601_a(blockPos.getX() * 1.0 / 8.0, blockPos.getZ() * 1.0 / 8.0) * 4.0) + blockPos.getY() - 64.0f) * 0.05f / 30.0f;
        }
        return this.temperature;
    }
    
    public void func_180624_a(final World world, final Random random, final BlockPos blockPos) {
        this.theBiomeDecorator.func_180292_a(world, random, this, blockPos);
    }
    
    public int func_180627_b(final BlockPos blockPos) {
        return ColorizerGrass.getGrassColor(MathHelper.clamp_float(this.func_180626_a(blockPos), 0.0f, 1.0f), MathHelper.clamp_float(this.getFloatRainfall(), 0.0f, 1.0f));
    }
    
    public int func_180625_c(final BlockPos blockPos) {
        return ColorizerFoliage.getFoliageColor(MathHelper.clamp_float(this.func_180626_a(blockPos), 0.0f, 1.0f), MathHelper.clamp_float(this.getFloatRainfall(), 0.0f, 1.0f));
    }
    
    public boolean isSnowyBiome() {
        return this.enableSnow;
    }
    
    public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        this.func_180628_b(world, random, chunkPrimer, n, n2, n3);
    }
    
    public final void func_180628_b(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        final IBlockState topBlock = this.topBlock;
        final IBlockState fillerBlock = this.fillerBlock;
        final int n4 = (int)(n3 / 3.0 + 3.0 + random.nextDouble() * 0.25);
    }
    
    protected BiomeGenBase createMutation() {
        return this.createMutatedBiome(this.biomeID + 128);
    }
    
    protected BiomeGenBase createMutatedBiome(final int n) {
        return new BiomeGenMutated(n, this);
    }
    
    public Class getBiomeClass() {
        return this.getClass();
    }
    
    public boolean isEqualTo(final BiomeGenBase biomeGenBase) {
        return biomeGenBase == this || (biomeGenBase != null && this.getBiomeClass() == biomeGenBase.getBiomeClass());
    }
    
    public TempCategory getTempCategory() {
        return (this.temperature < 0.2) ? TempCategory.COLD : ((this.temperature < 1.0) ? TempCategory.MEDIUM : TempCategory.WARM);
    }
    
    public static BiomeGenBase[] getBiomeGenArray() {
        return BiomeGenBase.biomeList;
    }
    
    public static BiomeGenBase getBiome(final int n) {
        return getBiomeFromBiomeList(n, null);
    }
    
    public static BiomeGenBase getBiomeFromBiomeList(final int n, final BiomeGenBase biomeGenBase) {
        if (n >= 0 && n <= BiomeGenBase.biomeList.length) {
            final BiomeGenBase biomeGenBase2 = BiomeGenBase.biomeList[n];
            return (biomeGenBase2 == null) ? biomeGenBase : biomeGenBase2;
        }
        BiomeGenBase.logger.warn("Biome ID is out of bounds: " + n + ", defaulting to 0 (Ocean)");
        return BiomeGenBase.ocean;
    }
    
    public static class Height
    {
        public float rootHeight;
        public float variation;
        private static final String __OBFID;
        
        public Height(final float rootHeight, final float variation) {
            this.rootHeight = rootHeight;
            this.variation = variation;
        }
        
        public Height attenuate() {
            return new Height(this.rootHeight * 0.8f, this.variation * 0.6f);
        }
        
        static {
            __OBFID = "CL_00000159";
        }
    }
    
    public static class SpawnListEntry extends WeightedRandom.Item
    {
        public Class entityClass;
        public int minGroupCount;
        public int maxGroupCount;
        private static final String __OBFID;
        
        public SpawnListEntry(final Class entityClass, final int n, final int minGroupCount, final int maxGroupCount) {
            super(n);
            this.entityClass = entityClass;
            this.minGroupCount = minGroupCount;
            this.maxGroupCount = maxGroupCount;
        }
        
        @Override
        public String toString() {
            return String.valueOf(this.entityClass.getSimpleName()) + "*(" + this.minGroupCount + "-" + this.maxGroupCount + "):" + this.itemWeight;
        }
        
        static {
            __OBFID = "CL_00000161";
        }
    }
    
    static final class SwitchEnumCreatureType
    {
        static final int[] field_180275_a;
        private static final String __OBFID;
        private static final String[] llIlIIIIIlllIIl;
        private static String[] llIlIIIIIlllIlI;
        
        static {
            lIIllIIllIIIIlII();
            lIIllIIllIIIIIll();
            __OBFID = SwitchEnumCreatureType.llIlIIIIIlllIIl[0];
            field_180275_a = new int[EnumCreatureType.values().length];
            try {
                SwitchEnumCreatureType.field_180275_a[EnumCreatureType.MONSTER.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumCreatureType.field_180275_a[EnumCreatureType.CREATURE.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumCreatureType.field_180275_a[EnumCreatureType.WATER_CREATURE.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumCreatureType.field_180275_a[EnumCreatureType.AMBIENT.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
        
        private static void lIIllIIllIIIIIll() {
            (llIlIIIIIlllIIl = new String[1])[0] = lIIllIIllIIIIIlI(SwitchEnumCreatureType.llIlIIIIIlllIlI[0], SwitchEnumCreatureType.llIlIIIIIlllIlI[1]);
            SwitchEnumCreatureType.llIlIIIIIlllIlI = null;
        }
        
        private static void lIIllIIllIIIIlII() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumCreatureType.llIlIIIIIlllIlI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIIllIIllIIIIIlI(String s, final String s2) {
            s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int n = 0;
            final char[] charArray2 = s.toCharArray();
            for (int length = charArray2.length, i = 0; i < length; ++i) {
                sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
                ++n;
            }
            return sb.toString();
        }
    }
    
    public enum TempCategory
    {
        OCEAN("OCEAN", 0, "OCEAN", 0), 
        COLD("COLD", 1, "COLD", 1), 
        MEDIUM("MEDIUM", 2, "MEDIUM", 2), 
        WARM("WARM", 3, "WARM", 3);
        
        private static final TempCategory[] $VALUES;
        private static final String __OBFID;
        private static final TempCategory[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00000160";
            ENUM$VALUES = new TempCategory[] { TempCategory.OCEAN, TempCategory.COLD, TempCategory.MEDIUM, TempCategory.WARM };
            $VALUES = new TempCategory[] { TempCategory.OCEAN, TempCategory.COLD, TempCategory.MEDIUM, TempCategory.WARM };
        }
        
        private TempCategory(final String s, final int n, final String s2, final int n2) {
        }
    }
}
