package net.minecraft.world.biome;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;

public class BiomeDecorator
{
    protected World currentWorld;
    protected Random randomGenerator;
    protected BlockPos field_180294_c;
    protected ChunkProviderSettings field_180293_d;
    protected WorldGenerator clayGen;
    protected WorldGenerator sandGen;
    protected WorldGenerator gravelAsSandGen;
    protected WorldGenerator dirtGen;
    protected WorldGenerator gravelGen;
    protected WorldGenerator field_180296_j;
    protected WorldGenerator field_180297_k;
    protected WorldGenerator field_180295_l;
    protected WorldGenerator coalGen;
    protected WorldGenerator ironGen;
    protected WorldGenerator goldGen;
    protected WorldGenerator field_180299_p;
    protected WorldGenerator field_180298_q;
    protected WorldGenerator lapisGen;
    protected WorldGenFlowers yellowFlowerGen;
    protected WorldGenerator mushroomBrownGen;
    protected WorldGenerator mushroomRedGen;
    protected WorldGenerator bigMushroomGen;
    protected WorldGenerator reedGen;
    protected WorldGenerator cactusGen;
    protected WorldGenerator waterlilyGen;
    protected int waterlilyPerChunk;
    protected int treesPerChunk;
    protected int flowersPerChunk;
    protected int grassPerChunk;
    protected int deadBushPerChunk;
    protected int mushroomsPerChunk;
    protected int reedsPerChunk;
    protected int cactiPerChunk;
    protected int sandPerChunk;
    protected int sandPerChunk2;
    protected int clayPerChunk;
    protected int bigMushroomsPerChunk;
    public boolean generateLakes;
    private static final String __OBFID;
    
    public BiomeDecorator() {
        this.clayGen = (WorldGenerator)new WorldGenClay(4);
        this.sandGen = (WorldGenerator)new WorldGenSand((Block)Blocks.sand, 7);
        this.gravelAsSandGen = (WorldGenerator)new WorldGenSand(Blocks.gravel, 6);
        this.yellowFlowerGen = new WorldGenFlowers(Blocks.yellow_flower, BlockFlower.EnumFlowerType.DANDELION);
        this.mushroomBrownGen = (WorldGenerator)new GeneratorBushFeature(Blocks.brown_mushroom);
        this.mushroomRedGen = (WorldGenerator)new GeneratorBushFeature(Blocks.red_mushroom);
        this.bigMushroomGen = (WorldGenerator)new WorldGenBigMushroom();
        this.reedGen = (WorldGenerator)new WorldGenReed();
        this.cactusGen = (WorldGenerator)new WorldGenCactus();
        this.waterlilyGen = (WorldGenerator)new WorldGenWaterlily();
        this.flowersPerChunk = 2;
        this.grassPerChunk = 1;
        this.sandPerChunk = 1;
        this.sandPerChunk2 = 3;
        this.clayPerChunk = 1;
        this.generateLakes = true;
    }
    
    public void func_180292_a(final World currentWorld, final Random randomGenerator, final BiomeGenBase biomeGenBase, final BlockPos field_180294_c) {
        if (this.currentWorld != null) {
            throw new RuntimeException("Already decorating");
        }
        this.currentWorld = currentWorld;
        final String generatorOptions = currentWorld.getWorldInfo().getGeneratorOptions();
        if (generatorOptions != null) {
            this.field_180293_d = ChunkProviderSettings.Factory.func_177865_a(generatorOptions).func_177864_b();
        }
        else {
            this.field_180293_d = ChunkProviderSettings.Factory.func_177865_a("").func_177864_b();
        }
        this.randomGenerator = randomGenerator;
        this.field_180294_c = field_180294_c;
        this.dirtGen = (WorldGenerator)new WorldGenMinable(Blocks.dirt.getDefaultState(), this.field_180293_d.field_177789_I);
        this.gravelGen = (WorldGenerator)new WorldGenMinable(Blocks.gravel.getDefaultState(), this.field_180293_d.field_177785_M);
        this.field_180296_j = (WorldGenerator)new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.GRANITE), this.field_180293_d.field_177796_Q);
        this.field_180297_k = (WorldGenerator)new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.DIORITE), this.field_180293_d.field_177792_U);
        this.field_180295_l = (WorldGenerator)new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.ANDESITE), this.field_180293_d.field_177800_Y);
        this.coalGen = (WorldGenerator)new WorldGenMinable(Blocks.coal_ore.getDefaultState(), this.field_180293_d.field_177844_ac);
        this.ironGen = (WorldGenerator)new WorldGenMinable(Blocks.iron_ore.getDefaultState(), this.field_180293_d.field_177848_ag);
        this.goldGen = (WorldGenerator)new WorldGenMinable(Blocks.gold_ore.getDefaultState(), this.field_180293_d.field_177828_ak);
        this.field_180299_p = (WorldGenerator)new WorldGenMinable(Blocks.redstone_ore.getDefaultState(), this.field_180293_d.field_177836_ao);
        this.field_180298_q = (WorldGenerator)new WorldGenMinable(Blocks.diamond_ore.getDefaultState(), this.field_180293_d.field_177814_as);
        this.lapisGen = (WorldGenerator)new WorldGenMinable(Blocks.lapis_ore.getDefaultState(), this.field_180293_d.field_177822_aw);
        this.genDecorations(biomeGenBase);
        this.currentWorld = null;
        this.randomGenerator = null;
    }
    
    protected void genDecorations(final BiomeGenBase p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/world/biome/BiomeDecorator.generateOres:()V
        //     4: goto            71
        //     7: aload_0        
        //     8: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //    11: bipush          16
        //    13: invokevirtual   java/util/Random.nextInt:(I)I
        //    16: bipush          8
        //    18: iadd           
        //    19: istore_3       
        //    20: aload_0        
        //    21: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //    24: bipush          16
        //    26: invokevirtual   java/util/Random.nextInt:(I)I
        //    29: bipush          8
        //    31: iadd           
        //    32: istore          4
        //    34: aload_0        
        //    35: getfield        net/minecraft/world/biome/BiomeDecorator.sandGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //    38: aload_0        
        //    39: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //    42: aload_0        
        //    43: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //    46: aload_0        
        //    47: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //    50: aload_0        
        //    51: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //    54: iconst_0       
        //    55: iconst_0       
        //    56: iload           4
        //    58: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    61: invokevirtual   net/minecraft/world/World.func_175672_r:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //    64: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //    67: pop            
        //    68: iinc            2, 1
        //    71: iconst_0       
        //    72: aload_0        
        //    73: getfield        net/minecraft/world/biome/BiomeDecorator.sandPerChunk2:I
        //    76: if_icmplt       7
        //    79: goto            146
        //    82: aload_0        
        //    83: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //    86: bipush          16
        //    88: invokevirtual   java/util/Random.nextInt:(I)I
        //    91: bipush          8
        //    93: iadd           
        //    94: istore_3       
        //    95: aload_0        
        //    96: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //    99: bipush          16
        //   101: invokevirtual   java/util/Random.nextInt:(I)I
        //   104: bipush          8
        //   106: iadd           
        //   107: istore          4
        //   109: aload_0        
        //   110: getfield        net/minecraft/world/biome/BiomeDecorator.clayGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //   113: aload_0        
        //   114: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   117: aload_0        
        //   118: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   121: aload_0        
        //   122: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   125: aload_0        
        //   126: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   129: iconst_0       
        //   130: iconst_0       
        //   131: iload           4
        //   133: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   136: invokevirtual   net/minecraft/world/World.func_175672_r:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //   139: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //   142: pop            
        //   143: iinc            2, 1
        //   146: iconst_0       
        //   147: aload_0        
        //   148: getfield        net/minecraft/world/biome/BiomeDecorator.clayPerChunk:I
        //   151: if_icmplt       82
        //   154: goto            221
        //   157: aload_0        
        //   158: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   161: bipush          16
        //   163: invokevirtual   java/util/Random.nextInt:(I)I
        //   166: bipush          8
        //   168: iadd           
        //   169: istore_3       
        //   170: aload_0        
        //   171: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   174: bipush          16
        //   176: invokevirtual   java/util/Random.nextInt:(I)I
        //   179: bipush          8
        //   181: iadd           
        //   182: istore          4
        //   184: aload_0        
        //   185: getfield        net/minecraft/world/biome/BiomeDecorator.gravelAsSandGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //   188: aload_0        
        //   189: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   192: aload_0        
        //   193: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   196: aload_0        
        //   197: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   200: aload_0        
        //   201: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   204: iconst_0       
        //   205: iconst_0       
        //   206: iload           4
        //   208: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   211: invokevirtual   net/minecraft/world/World.func_175672_r:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //   214: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //   217: pop            
        //   218: iinc            2, 1
        //   221: iconst_0       
        //   222: aload_0        
        //   223: getfield        net/minecraft/world/biome/BiomeDecorator.sandPerChunk:I
        //   226: if_icmplt       157
        //   229: aload_0        
        //   230: getfield        net/minecraft/world/biome/BiomeDecorator.treesPerChunk:I
        //   233: istore_2       
        //   234: aload_0        
        //   235: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   238: bipush          10
        //   240: invokevirtual   java/util/Random.nextInt:(I)I
        //   243: ifne            249
        //   246: iinc            2, 1
        //   249: goto            421
        //   252: aload_0        
        //   253: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   256: bipush          16
        //   258: invokevirtual   java/util/Random.nextInt:(I)I
        //   261: bipush          8
        //   263: iadd           
        //   264: istore          4
        //   266: aload_0        
        //   267: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   270: bipush          16
        //   272: invokevirtual   java/util/Random.nextInt:(I)I
        //   275: bipush          8
        //   277: iadd           
        //   278: istore          5
        //   280: aload_1        
        //   281: aload_0        
        //   282: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   285: invokevirtual   net/minecraft/world/biome/BiomeGenBase.genBigTreeChance:(Ljava/util/Random;)Lnet/minecraft/world/gen/feature/WorldGenAbstractTree;
        //   288: astore          7
        //   290: aload           7
        //   292: invokevirtual   net/minecraft/world/gen/feature/WorldGenAbstractTree.func_175904_e:()V
        //   295: aload_0        
        //   296: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   299: aload_0        
        //   300: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   303: iload           4
        //   305: iconst_0       
        //   306: iload           5
        //   308: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   311: invokevirtual   net/minecraft/world/World.getHorizon:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //   314: astore          6
        //   316: aload           7
        //   318: aload_0        
        //   319: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   322: aload_0        
        //   323: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   326: aload           6
        //   328: invokevirtual   net/minecraft/world/gen/feature/WorldGenAbstractTree.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //   331: ifeq            349
        //   334: aload           7
        //   336: aload_0        
        //   337: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   340: aload_0        
        //   341: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   344: aload           6
        //   346: invokevirtual   net/minecraft/world/gen/feature/WorldGenAbstractTree.func_180711_a:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)V
        //   349: iinc            3, 1
        //   352: goto            421
        //   355: aload_0        
        //   356: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   359: bipush          16
        //   361: invokevirtual   java/util/Random.nextInt:(I)I
        //   364: bipush          8
        //   366: iadd           
        //   367: istore          4
        //   369: aload_0        
        //   370: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   373: bipush          16
        //   375: invokevirtual   java/util/Random.nextInt:(I)I
        //   378: bipush          8
        //   380: iadd           
        //   381: istore          5
        //   383: aload_0        
        //   384: getfield        net/minecraft/world/biome/BiomeDecorator.bigMushroomGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //   387: aload_0        
        //   388: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   391: aload_0        
        //   392: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   395: aload_0        
        //   396: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   399: aload_0        
        //   400: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   403: iload           4
        //   405: iconst_0       
        //   406: iload           5
        //   408: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   411: invokevirtual   net/minecraft/world/World.getHorizon:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //   414: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //   417: pop            
        //   418: iinc            3, 1
        //   421: iconst_0       
        //   422: aload_0        
        //   423: getfield        net/minecraft/world/biome/BiomeDecorator.bigMushroomsPerChunk:I
        //   426: if_icmplt       355
        //   429: goto            574
        //   432: aload_0        
        //   433: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   436: bipush          16
        //   438: invokevirtual   java/util/Random.nextInt:(I)I
        //   441: bipush          8
        //   443: iadd           
        //   444: istore          4
        //   446: aload_0        
        //   447: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   450: bipush          16
        //   452: invokevirtual   java/util/Random.nextInt:(I)I
        //   455: bipush          8
        //   457: iadd           
        //   458: istore          5
        //   460: aload_0        
        //   461: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   464: aload_0        
        //   465: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   468: aload_0        
        //   469: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   472: iload           4
        //   474: iconst_0       
        //   475: iload           5
        //   477: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   480: invokevirtual   net/minecraft/world/World.getHorizon:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //   483: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   486: bipush          32
        //   488: iadd           
        //   489: invokevirtual   java/util/Random.nextInt:(I)I
        //   492: istore          7
        //   494: aload_0        
        //   495: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   498: iload           4
        //   500: iload           7
        //   502: iload           5
        //   504: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   507: astore          6
        //   509: aload_1        
        //   510: aload_0        
        //   511: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   514: aload           6
        //   516: invokevirtual   net/minecraft/world/biome/BiomeGenBase.pickRandomFlower:(Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/BlockFlower$EnumFlowerType;
        //   519: astore          8
        //   521: aload           8
        //   523: invokevirtual   net/minecraft/block/BlockFlower$EnumFlowerType.func_176964_a:()Lnet/minecraft/block/BlockFlower$EnumFlowerColor;
        //   526: invokevirtual   net/minecraft/block/BlockFlower$EnumFlowerColor.func_180346_a:()Lnet/minecraft/block/BlockFlower;
        //   529: astore          9
        //   531: aload           9
        //   533: invokevirtual   net/minecraft/block/BlockFlower.getMaterial:()Lnet/minecraft/block/material/Material;
        //   536: getstatic       net/minecraft/block/material/Material.air:Lnet/minecraft/block/material/Material;
        //   539: if_acmpeq       571
        //   542: aload_0        
        //   543: getfield        net/minecraft/world/biome/BiomeDecorator.yellowFlowerGen:Lnet/minecraft/world/gen/feature/WorldGenFlowers;
        //   546: aload           9
        //   548: aload           8
        //   550: invokevirtual   net/minecraft/world/gen/feature/WorldGenFlowers.setGeneratedBlock:(Lnet/minecraft/block/BlockFlower;Lnet/minecraft/block/BlockFlower$EnumFlowerType;)V
        //   553: aload_0        
        //   554: getfield        net/minecraft/world/biome/BiomeDecorator.yellowFlowerGen:Lnet/minecraft/world/gen/feature/WorldGenFlowers;
        //   557: aload_0        
        //   558: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   561: aload_0        
        //   562: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   565: aload           6
        //   567: invokevirtual   net/minecraft/world/gen/feature/WorldGenFlowers.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //   570: pop            
        //   571: iinc            3, 1
        //   574: iconst_0       
        //   575: aload_0        
        //   576: getfield        net/minecraft/world/biome/BiomeDecorator.flowersPerChunk:I
        //   579: if_icmplt       432
        //   582: goto            682
        //   585: aload_0        
        //   586: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   589: bipush          16
        //   591: invokevirtual   java/util/Random.nextInt:(I)I
        //   594: bipush          8
        //   596: iadd           
        //   597: istore          4
        //   599: aload_0        
        //   600: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   603: bipush          16
        //   605: invokevirtual   java/util/Random.nextInt:(I)I
        //   608: bipush          8
        //   610: iadd           
        //   611: istore          5
        //   613: aload_0        
        //   614: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   617: aload_0        
        //   618: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   621: aload_0        
        //   622: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   625: iload           4
        //   627: iconst_0       
        //   628: iload           5
        //   630: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   633: invokevirtual   net/minecraft/world/World.getHorizon:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //   636: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   639: iconst_2       
        //   640: imul           
        //   641: invokevirtual   java/util/Random.nextInt:(I)I
        //   644: istore          7
        //   646: aload_1        
        //   647: aload_0        
        //   648: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   651: invokevirtual   net/minecraft/world/biome/BiomeGenBase.getRandomWorldGenForGrass:(Ljava/util/Random;)Lnet/minecraft/world/gen/feature/WorldGenerator;
        //   654: aload_0        
        //   655: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   658: aload_0        
        //   659: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   662: aload_0        
        //   663: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   666: iload           4
        //   668: iload           7
        //   670: iload           5
        //   672: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   675: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //   678: pop            
        //   679: iinc            3, 1
        //   682: iconst_0       
        //   683: aload_0        
        //   684: getfield        net/minecraft/world/biome/BiomeDecorator.grassPerChunk:I
        //   687: if_icmplt       585
        //   690: goto            789
        //   693: aload_0        
        //   694: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   697: bipush          16
        //   699: invokevirtual   java/util/Random.nextInt:(I)I
        //   702: bipush          8
        //   704: iadd           
        //   705: istore          4
        //   707: aload_0        
        //   708: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   711: bipush          16
        //   713: invokevirtual   java/util/Random.nextInt:(I)I
        //   716: bipush          8
        //   718: iadd           
        //   719: istore          5
        //   721: aload_0        
        //   722: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   725: aload_0        
        //   726: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   729: aload_0        
        //   730: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   733: iload           4
        //   735: iconst_0       
        //   736: iload           5
        //   738: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   741: invokevirtual   net/minecraft/world/World.getHorizon:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //   744: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   747: iconst_2       
        //   748: imul           
        //   749: invokevirtual   java/util/Random.nextInt:(I)I
        //   752: istore          7
        //   754: new             Lnet/minecraft/world/gen/feature/WorldGenDeadBush;
        //   757: dup            
        //   758: invokespecial   net/minecraft/world/gen/feature/WorldGenDeadBush.<init>:()V
        //   761: aload_0        
        //   762: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   765: aload_0        
        //   766: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   769: aload_0        
        //   770: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   773: iload           4
        //   775: iload           7
        //   777: iload           5
        //   779: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   782: invokevirtual   net/minecraft/world/gen/feature/WorldGenDeadBush.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //   785: pop            
        //   786: iinc            3, 1
        //   789: iconst_0       
        //   790: aload_0        
        //   791: getfield        net/minecraft/world/biome/BiomeDecorator.deadBushPerChunk:I
        //   794: if_icmplt       693
        //   797: goto            931
        //   800: aload_0        
        //   801: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   804: bipush          16
        //   806: invokevirtual   java/util/Random.nextInt:(I)I
        //   809: bipush          8
        //   811: iadd           
        //   812: istore          4
        //   814: aload_0        
        //   815: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   818: bipush          16
        //   820: invokevirtual   java/util/Random.nextInt:(I)I
        //   823: bipush          8
        //   825: iadd           
        //   826: istore          5
        //   828: aload_0        
        //   829: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   832: aload_0        
        //   833: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   836: aload_0        
        //   837: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   840: iload           4
        //   842: iconst_0       
        //   843: iload           5
        //   845: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   848: invokevirtual   net/minecraft/world/World.getHorizon:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //   851: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   854: iconst_2       
        //   855: imul           
        //   856: invokevirtual   java/util/Random.nextInt:(I)I
        //   859: istore          7
        //   861: aload_0        
        //   862: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   865: iload           4
        //   867: iload           7
        //   869: iload           5
        //   871: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   874: astore          6
        //   876: aload           6
        //   878: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   881: ifle            910
        //   884: aload           6
        //   886: invokevirtual   net/minecraft/util/BlockPos.offsetDown:()Lnet/minecraft/util/BlockPos;
        //   889: astore          8
        //   891: aload_0        
        //   892: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   895: aload           8
        //   897: invokevirtual   net/minecraft/world/World.isAirBlock:(Lnet/minecraft/util/BlockPos;)Z
        //   900: ifeq            910
        //   903: aload           8
        //   905: astore          6
        //   907: goto            876
        //   910: aload_0        
        //   911: getfield        net/minecraft/world/biome/BiomeDecorator.waterlilyGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //   914: aload_0        
        //   915: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   918: aload_0        
        //   919: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   922: aload           6
        //   924: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //   927: pop            
        //   928: iinc            3, 1
        //   931: iconst_0       
        //   932: aload_0        
        //   933: getfield        net/minecraft/world/biome/BiomeDecorator.waterlilyPerChunk:I
        //   936: if_icmplt       800
        //   939: goto            1129
        //   942: aload_0        
        //   943: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   946: iconst_4       
        //   947: invokevirtual   java/util/Random.nextInt:(I)I
        //   950: ifne            1020
        //   953: aload_0        
        //   954: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   957: bipush          16
        //   959: invokevirtual   java/util/Random.nextInt:(I)I
        //   962: bipush          8
        //   964: iadd           
        //   965: istore          4
        //   967: aload_0        
        //   968: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //   971: bipush          16
        //   973: invokevirtual   java/util/Random.nextInt:(I)I
        //   976: bipush          8
        //   978: iadd           
        //   979: istore          5
        //   981: aload_0        
        //   982: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //   985: aload_0        
        //   986: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //   989: iload           4
        //   991: iconst_0       
        //   992: iload           5
        //   994: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   997: invokevirtual   net/minecraft/world/World.getHorizon:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //  1000: astore          8
        //  1002: aload_0        
        //  1003: getfield        net/minecraft/world/biome/BiomeDecorator.mushroomBrownGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //  1006: aload_0        
        //  1007: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1010: aload_0        
        //  1011: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1014: aload           8
        //  1016: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  1019: pop            
        //  1020: aload_0        
        //  1021: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1024: bipush          8
        //  1026: invokevirtual   java/util/Random.nextInt:(I)I
        //  1029: ifne            1126
        //  1032: aload_0        
        //  1033: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1036: bipush          16
        //  1038: invokevirtual   java/util/Random.nextInt:(I)I
        //  1041: bipush          8
        //  1043: iadd           
        //  1044: istore          4
        //  1046: aload_0        
        //  1047: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1050: bipush          16
        //  1052: invokevirtual   java/util/Random.nextInt:(I)I
        //  1055: bipush          8
        //  1057: iadd           
        //  1058: istore          5
        //  1060: aload_0        
        //  1061: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1064: aload_0        
        //  1065: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1068: aload_0        
        //  1069: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1072: iload           4
        //  1074: iconst_0       
        //  1075: iload           5
        //  1077: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1080: invokevirtual   net/minecraft/world/World.getHorizon:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //  1083: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //  1086: iconst_2       
        //  1087: imul           
        //  1088: invokevirtual   java/util/Random.nextInt:(I)I
        //  1091: istore          7
        //  1093: aload_0        
        //  1094: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1097: iload           4
        //  1099: iload           7
        //  1101: iload           5
        //  1103: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1106: astore          6
        //  1108: aload_0        
        //  1109: getfield        net/minecraft/world/biome/BiomeDecorator.mushroomRedGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //  1112: aload_0        
        //  1113: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1116: aload_0        
        //  1117: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1120: aload           6
        //  1122: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  1125: pop            
        //  1126: iinc            3, 1
        //  1129: iconst_0       
        //  1130: aload_0        
        //  1131: getfield        net/minecraft/world/biome/BiomeDecorator.mushroomsPerChunk:I
        //  1134: if_icmplt       942
        //  1137: aload_0        
        //  1138: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1141: iconst_4       
        //  1142: invokevirtual   java/util/Random.nextInt:(I)I
        //  1145: ifne            1235
        //  1148: aload_0        
        //  1149: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1152: bipush          16
        //  1154: invokevirtual   java/util/Random.nextInt:(I)I
        //  1157: bipush          8
        //  1159: iadd           
        //  1160: istore_3       
        //  1161: aload_0        
        //  1162: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1165: bipush          16
        //  1167: invokevirtual   java/util/Random.nextInt:(I)I
        //  1170: bipush          8
        //  1172: iadd           
        //  1173: istore          4
        //  1175: aload_0        
        //  1176: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1179: aload_0        
        //  1180: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1183: aload_0        
        //  1184: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1187: iconst_0       
        //  1188: iconst_0       
        //  1189: iload           4
        //  1191: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1194: invokevirtual   net/minecraft/world/World.getHorizon:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //  1197: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //  1200: iconst_2       
        //  1201: imul           
        //  1202: invokevirtual   java/util/Random.nextInt:(I)I
        //  1205: istore          5
        //  1207: aload_0        
        //  1208: getfield        net/minecraft/world/biome/BiomeDecorator.mushroomBrownGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //  1211: aload_0        
        //  1212: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1215: aload_0        
        //  1216: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1219: aload_0        
        //  1220: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1223: iconst_0       
        //  1224: iload           5
        //  1226: iload           4
        //  1228: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1231: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  1234: pop            
        //  1235: aload_0        
        //  1236: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1239: bipush          8
        //  1241: invokevirtual   java/util/Random.nextInt:(I)I
        //  1244: ifne            1334
        //  1247: aload_0        
        //  1248: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1251: bipush          16
        //  1253: invokevirtual   java/util/Random.nextInt:(I)I
        //  1256: bipush          8
        //  1258: iadd           
        //  1259: istore_3       
        //  1260: aload_0        
        //  1261: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1264: bipush          16
        //  1266: invokevirtual   java/util/Random.nextInt:(I)I
        //  1269: bipush          8
        //  1271: iadd           
        //  1272: istore          4
        //  1274: aload_0        
        //  1275: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1278: aload_0        
        //  1279: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1282: aload_0        
        //  1283: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1286: iconst_0       
        //  1287: iconst_0       
        //  1288: iload           4
        //  1290: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1293: invokevirtual   net/minecraft/world/World.getHorizon:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //  1296: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //  1299: iconst_2       
        //  1300: imul           
        //  1301: invokevirtual   java/util/Random.nextInt:(I)I
        //  1304: istore          5
        //  1306: aload_0        
        //  1307: getfield        net/minecraft/world/biome/BiomeDecorator.mushroomRedGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //  1310: aload_0        
        //  1311: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1314: aload_0        
        //  1315: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1318: aload_0        
        //  1319: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1322: iconst_0       
        //  1323: iload           5
        //  1325: iload           4
        //  1327: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1330: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  1333: pop            
        //  1334: goto            1430
        //  1337: aload_0        
        //  1338: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1341: bipush          16
        //  1343: invokevirtual   java/util/Random.nextInt:(I)I
        //  1346: bipush          8
        //  1348: iadd           
        //  1349: istore          4
        //  1351: aload_0        
        //  1352: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1355: bipush          16
        //  1357: invokevirtual   java/util/Random.nextInt:(I)I
        //  1360: bipush          8
        //  1362: iadd           
        //  1363: istore          5
        //  1365: aload_0        
        //  1366: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1369: aload_0        
        //  1370: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1373: aload_0        
        //  1374: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1377: iload           4
        //  1379: iconst_0       
        //  1380: iload           5
        //  1382: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1385: invokevirtual   net/minecraft/world/World.getHorizon:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //  1388: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //  1391: iconst_2       
        //  1392: imul           
        //  1393: invokevirtual   java/util/Random.nextInt:(I)I
        //  1396: istore          7
        //  1398: aload_0        
        //  1399: getfield        net/minecraft/world/biome/BiomeDecorator.reedGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //  1402: aload_0        
        //  1403: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1406: aload_0        
        //  1407: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1410: aload_0        
        //  1411: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1414: iload           4
        //  1416: iload           7
        //  1418: iload           5
        //  1420: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1423: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  1426: pop            
        //  1427: iinc            3, 1
        //  1430: iconst_0       
        //  1431: aload_0        
        //  1432: getfield        net/minecraft/world/biome/BiomeDecorator.reedsPerChunk:I
        //  1435: if_icmplt       1337
        //  1438: goto            1534
        //  1441: aload_0        
        //  1442: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1445: bipush          16
        //  1447: invokevirtual   java/util/Random.nextInt:(I)I
        //  1450: bipush          8
        //  1452: iadd           
        //  1453: istore          4
        //  1455: aload_0        
        //  1456: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1459: bipush          16
        //  1461: invokevirtual   java/util/Random.nextInt:(I)I
        //  1464: bipush          8
        //  1466: iadd           
        //  1467: istore          5
        //  1469: aload_0        
        //  1470: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1473: aload_0        
        //  1474: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1477: aload_0        
        //  1478: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1481: iload           4
        //  1483: iconst_0       
        //  1484: iload           5
        //  1486: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1489: invokevirtual   net/minecraft/world/World.getHorizon:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //  1492: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //  1495: iconst_2       
        //  1496: imul           
        //  1497: invokevirtual   java/util/Random.nextInt:(I)I
        //  1500: istore          7
        //  1502: aload_0        
        //  1503: getfield        net/minecraft/world/biome/BiomeDecorator.reedGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //  1506: aload_0        
        //  1507: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1510: aload_0        
        //  1511: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1514: aload_0        
        //  1515: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1518: iload           4
        //  1520: iload           7
        //  1522: iload           5
        //  1524: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1527: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  1530: pop            
        //  1531: iinc            3, 1
        //  1534: aload_0        
        //  1535: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1538: bipush          32
        //  1540: invokevirtual   java/util/Random.nextInt:(I)I
        //  1543: ifne            1636
        //  1546: aload_0        
        //  1547: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1550: bipush          16
        //  1552: invokevirtual   java/util/Random.nextInt:(I)I
        //  1555: bipush          8
        //  1557: iadd           
        //  1558: istore_3       
        //  1559: aload_0        
        //  1560: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1563: bipush          16
        //  1565: invokevirtual   java/util/Random.nextInt:(I)I
        //  1568: bipush          8
        //  1570: iadd           
        //  1571: istore          4
        //  1573: aload_0        
        //  1574: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1577: aload_0        
        //  1578: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1581: aload_0        
        //  1582: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1585: iconst_0       
        //  1586: iconst_0       
        //  1587: iload           4
        //  1589: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1592: invokevirtual   net/minecraft/world/World.getHorizon:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //  1595: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //  1598: iconst_2       
        //  1599: imul           
        //  1600: invokevirtual   java/util/Random.nextInt:(I)I
        //  1603: istore          5
        //  1605: new             Lnet/minecraft/world/gen/feature/WorldGenPumpkin;
        //  1608: dup            
        //  1609: invokespecial   net/minecraft/world/gen/feature/WorldGenPumpkin.<init>:()V
        //  1612: aload_0        
        //  1613: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1616: aload_0        
        //  1617: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1620: aload_0        
        //  1621: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1624: iconst_0       
        //  1625: iload           5
        //  1627: iload           4
        //  1629: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1632: invokevirtual   net/minecraft/world/gen/feature/WorldGenPumpkin.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  1635: pop            
        //  1636: goto            1732
        //  1639: aload_0        
        //  1640: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1643: bipush          16
        //  1645: invokevirtual   java/util/Random.nextInt:(I)I
        //  1648: bipush          8
        //  1650: iadd           
        //  1651: istore          4
        //  1653: aload_0        
        //  1654: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1657: bipush          16
        //  1659: invokevirtual   java/util/Random.nextInt:(I)I
        //  1662: bipush          8
        //  1664: iadd           
        //  1665: istore          5
        //  1667: aload_0        
        //  1668: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1671: aload_0        
        //  1672: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1675: aload_0        
        //  1676: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1679: iload           4
        //  1681: iconst_0       
        //  1682: iload           5
        //  1684: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1687: invokevirtual   net/minecraft/world/World.getHorizon:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //  1690: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //  1693: iconst_2       
        //  1694: imul           
        //  1695: invokevirtual   java/util/Random.nextInt:(I)I
        //  1698: istore          7
        //  1700: aload_0        
        //  1701: getfield        net/minecraft/world/biome/BiomeDecorator.cactusGen:Lnet/minecraft/world/gen/feature/WorldGenerator;
        //  1704: aload_0        
        //  1705: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1708: aload_0        
        //  1709: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1712: aload_0        
        //  1713: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1716: iload           4
        //  1718: iload           7
        //  1720: iload           5
        //  1722: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1725: invokevirtual   net/minecraft/world/gen/feature/WorldGenerator.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  1728: pop            
        //  1729: iinc            3, 1
        //  1732: iconst_0       
        //  1733: aload_0        
        //  1734: getfield        net/minecraft/world/biome/BiomeDecorator.cactiPerChunk:I
        //  1737: if_icmplt       1639
        //  1740: aload_0        
        //  1741: getfield        net/minecraft/world/biome/BiomeDecorator.generateLakes:Z
        //  1744: ifeq            1923
        //  1747: goto            1923
        //  1750: aload_0        
        //  1751: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1754: aload_0        
        //  1755: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1758: bipush          16
        //  1760: invokevirtual   java/util/Random.nextInt:(I)I
        //  1763: bipush          8
        //  1765: iadd           
        //  1766: aload_0        
        //  1767: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1770: aload_0        
        //  1771: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1774: sipush          248
        //  1777: invokevirtual   java/util/Random.nextInt:(I)I
        //  1780: bipush          8
        //  1782: iadd           
        //  1783: invokevirtual   java/util/Random.nextInt:(I)I
        //  1786: aload_0        
        //  1787: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1790: bipush          16
        //  1792: invokevirtual   java/util/Random.nextInt:(I)I
        //  1795: bipush          8
        //  1797: iadd           
        //  1798: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1801: astore          8
        //  1803: new             Lnet/minecraft/world/gen/feature/WorldGenLiquids;
        //  1806: dup            
        //  1807: getstatic       net/minecraft/init/Blocks.flowing_water:Lnet/minecraft/block/BlockDynamicLiquid;
        //  1810: invokespecial   net/minecraft/world/gen/feature/WorldGenLiquids.<init>:(Lnet/minecraft/block/Block;)V
        //  1813: aload_0        
        //  1814: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1817: aload_0        
        //  1818: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1821: aload           8
        //  1823: invokevirtual   net/minecraft/world/gen/feature/WorldGenLiquids.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  1826: pop            
        //  1827: iinc            3, 1
        //  1830: goto            1923
        //  1833: aload_0        
        //  1834: getfield        net/minecraft/world/biome/BiomeDecorator.field_180294_c:Lnet/minecraft/util/BlockPos;
        //  1837: aload_0        
        //  1838: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1841: bipush          16
        //  1843: invokevirtual   java/util/Random.nextInt:(I)I
        //  1846: bipush          8
        //  1848: iadd           
        //  1849: aload_0        
        //  1850: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1853: aload_0        
        //  1854: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1857: aload_0        
        //  1858: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1861: sipush          240
        //  1864: invokevirtual   java/util/Random.nextInt:(I)I
        //  1867: bipush          8
        //  1869: iadd           
        //  1870: invokevirtual   java/util/Random.nextInt:(I)I
        //  1873: bipush          8
        //  1875: iadd           
        //  1876: invokevirtual   java/util/Random.nextInt:(I)I
        //  1879: aload_0        
        //  1880: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1883: bipush          16
        //  1885: invokevirtual   java/util/Random.nextInt:(I)I
        //  1888: bipush          8
        //  1890: iadd           
        //  1891: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //  1894: astore          8
        //  1896: new             Lnet/minecraft/world/gen/feature/WorldGenLiquids;
        //  1899: dup            
        //  1900: getstatic       net/minecraft/init/Blocks.flowing_lava:Lnet/minecraft/block/BlockDynamicLiquid;
        //  1903: invokespecial   net/minecraft/world/gen/feature/WorldGenLiquids.<init>:(Lnet/minecraft/block/Block;)V
        //  1906: aload_0        
        //  1907: getfield        net/minecraft/world/biome/BiomeDecorator.currentWorld:Lnet/minecraft/world/World;
        //  1910: aload_0        
        //  1911: getfield        net/minecraft/world/biome/BiomeDecorator.randomGenerator:Ljava/util/Random;
        //  1914: aload           8
        //  1916: invokevirtual   net/minecraft/world/gen/feature/WorldGenLiquids.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //  1919: pop            
        //  1920: iinc            3, 1
        //  1923: return         
        // 
        // The error that occurred was:
        // 
        // java.util.ConcurrentModificationException
        //     at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
        //     at java.util.ArrayList$Itr.next(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2863)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected void genStandardOre1(final int n, final WorldGenerator worldGenerator, final int n2, int n3) {
        ++n3;
        while (0 < n) {
            worldGenerator.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(this.randomGenerator.nextInt(16), this.randomGenerator.nextInt(0) + 0, this.randomGenerator.nextInt(16)));
            int n4 = 0;
            ++n4;
        }
    }
    
    protected void genStandardOre2(final int n, final WorldGenerator worldGenerator, final int n2, final int n3) {
        while (0 < n) {
            worldGenerator.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(this.randomGenerator.nextInt(16), this.randomGenerator.nextInt(n3) + this.randomGenerator.nextInt(n3) + n2 - n3, this.randomGenerator.nextInt(16)));
            int n4 = 0;
            ++n4;
        }
    }
    
    protected void generateOres() {
        this.genStandardOre1(this.field_180293_d.field_177790_J, this.dirtGen, this.field_180293_d.field_177791_K, this.field_180293_d.field_177784_L);
        this.genStandardOre1(this.field_180293_d.field_177786_N, this.gravelGen, this.field_180293_d.field_177787_O, this.field_180293_d.field_177797_P);
        this.genStandardOre1(this.field_180293_d.field_177795_V, this.field_180297_k, this.field_180293_d.field_177794_W, this.field_180293_d.field_177801_X);
        this.genStandardOre1(this.field_180293_d.field_177799_R, this.field_180296_j, this.field_180293_d.field_177798_S, this.field_180293_d.field_177793_T);
        this.genStandardOre1(this.field_180293_d.field_177802_Z, this.field_180295_l, this.field_180293_d.field_177846_aa, this.field_180293_d.field_177847_ab);
        this.genStandardOre1(this.field_180293_d.field_177845_ad, this.coalGen, this.field_180293_d.field_177851_ae, this.field_180293_d.field_177853_af);
        this.genStandardOre1(this.field_180293_d.field_177849_ah, this.ironGen, this.field_180293_d.field_177832_ai, this.field_180293_d.field_177834_aj);
        this.genStandardOre1(this.field_180293_d.field_177830_al, this.goldGen, this.field_180293_d.field_177840_am, this.field_180293_d.field_177842_an);
        this.genStandardOre1(this.field_180293_d.field_177838_ap, this.field_180299_p, this.field_180293_d.field_177818_aq, this.field_180293_d.field_177816_ar);
        this.genStandardOre1(this.field_180293_d.field_177812_at, this.field_180298_q, this.field_180293_d.field_177826_au, this.field_180293_d.field_177824_av);
        this.genStandardOre2(this.field_180293_d.field_177820_ax, this.lapisGen, this.field_180293_d.field_177807_ay, this.field_180293_d.field_177805_az);
    }
    
    static {
        __OBFID = "CL_00000164";
    }
}
