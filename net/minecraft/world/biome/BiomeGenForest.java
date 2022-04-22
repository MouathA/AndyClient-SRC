package net.minecraft.world.biome;

import net.minecraft.entity.passive.*;
import java.util.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class BiomeGenForest extends BiomeGenBase
{
    private int field_150632_aF;
    protected static final WorldGenForest field_150629_aC;
    protected static final WorldGenForest field_150630_aD;
    protected static final WorldGenCanopyTree field_150631_aE;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000170";
        field_150629_aC = new WorldGenForest(false, true);
        field_150630_aD = new WorldGenForest(false, false);
        field_150631_aE = new WorldGenCanopyTree(false);
    }
    
    public BiomeGenForest(final int n, final int field_150632_aF) {
        super(n);
        this.field_150632_aF = field_150632_aF;
        this.theBiomeDecorator.treesPerChunk = 10;
        this.theBiomeDecorator.grassPerChunk = 2;
        if (this.field_150632_aF == 1) {
            this.theBiomeDecorator.treesPerChunk = 6;
            this.theBiomeDecorator.flowersPerChunk = 100;
            this.theBiomeDecorator.grassPerChunk = 1;
        }
        this.setFillerBlockMetadata(5159473);
        this.setTemperatureRainfall(0.7f, 0.8f);
        if (this.field_150632_aF == 2) {
            this.field_150609_ah = 353825;
            this.color = 3175492;
            this.setTemperatureRainfall(0.6f, 0.6f);
        }
        if (this.field_150632_aF == 0) {
            this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 4, 4));
        }
        if (this.field_150632_aF == 3) {
            this.theBiomeDecorator.treesPerChunk = -999;
        }
    }
    
    @Override
    protected BiomeGenBase func_150557_a(final int color, final boolean b) {
        if (this.field_150632_aF == 2) {
            this.field_150609_ah = 353825;
            this.color = color;
            if (b) {
                this.field_150609_ah = (this.field_150609_ah & 0xFEFEFE) >> 1;
            }
            return this;
        }
        return super.func_150557_a(color, b);
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return (WorldGenAbstractTree)((this.field_150632_aF == 3 && random.nextInt(3) > 0) ? BiomeGenForest.field_150631_aE : ((this.field_150632_aF != 2 && random.nextInt(5) != 0) ? this.worldGeneratorTrees : BiomeGenForest.field_150630_aD));
    }
    
    @Override
    public BlockFlower.EnumFlowerType pickRandomFlower(final Random random, final BlockPos blockPos) {
        if (this.field_150632_aF == 1) {
            final BlockFlower.EnumFlowerType enumFlowerType = BlockFlower.EnumFlowerType.values()[(int)(MathHelper.clamp_double((1.0 + BiomeGenForest.field_180281_af.func_151601_a(blockPos.getX() / 48.0, blockPos.getZ() / 48.0)) / 2.0, 0.0, 0.9999) * BlockFlower.EnumFlowerType.values().length)];
            return (enumFlowerType == BlockFlower.EnumFlowerType.BLUE_ORCHID) ? BlockFlower.EnumFlowerType.POPPY : enumFlowerType;
        }
        return super.pickRandomFlower(random, blockPos);
    }
    
    @Override
    public void func_180624_a(final World p0, final Random p1, final BlockPos p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/world/biome/BiomeGenForest.field_150632_aF:I
        //     4: iconst_3       
        //     5: if_icmpne       128
        //     8: goto            123
        //    11: goto            115
        //    14: bipush          9
        //    16: aload_2        
        //    17: iconst_3       
        //    18: invokevirtual   java/util/Random.nextInt:(I)I
        //    21: iadd           
        //    22: istore          6
        //    24: bipush          9
        //    26: aload_2        
        //    27: iconst_3       
        //    28: invokevirtual   java/util/Random.nextInt:(I)I
        //    31: iadd           
        //    32: istore          7
        //    34: aload_1        
        //    35: aload_3        
        //    36: iload           6
        //    38: iconst_0       
        //    39: iconst_0       
        //    40: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    43: invokevirtual   net/minecraft/world/World.getHorizon:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //    46: astore          8
        //    48: aload_2        
        //    49: bipush          20
        //    51: invokevirtual   java/util/Random.nextInt:(I)I
        //    54: ifne            79
        //    57: new             Lnet/minecraft/world/gen/feature/WorldGenBigMushroom;
        //    60: dup            
        //    61: invokespecial   net/minecraft/world/gen/feature/WorldGenBigMushroom.<init>:()V
        //    64: astore          9
        //    66: aload           9
        //    68: aload_1        
        //    69: aload_2        
        //    70: aload           8
        //    72: invokevirtual   net/minecraft/world/gen/feature/WorldGenBigMushroom.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //    75: pop            
        //    76: goto            112
        //    79: aload_0        
        //    80: aload_2        
        //    81: invokevirtual   net/minecraft/world/biome/BiomeGenForest.genBigTreeChance:(Ljava/util/Random;)Lnet/minecraft/world/gen/feature/WorldGenAbstractTree;
        //    84: astore          9
        //    86: aload           9
        //    88: invokevirtual   net/minecraft/world/gen/feature/WorldGenAbstractTree.func_175904_e:()V
        //    91: aload           9
        //    93: aload_1        
        //    94: aload_2        
        //    95: aload           8
        //    97: invokevirtual   net/minecraft/world/gen/feature/WorldGenAbstractTree.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //   100: ifeq            112
        //   103: aload           9
        //   105: aload_1        
        //   106: aload_2        
        //   107: aload           8
        //   109: invokevirtual   net/minecraft/world/gen/feature/WorldGenAbstractTree.func_180711_a:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)V
        //   112: iinc            5, 1
        //   115: iconst_0       
        //   116: iconst_4       
        //   117: if_icmplt       14
        //   120: iinc            4, 1
        //   123: iconst_0       
        //   124: iconst_4       
        //   125: if_icmplt       11
        //   128: aload_2        
        //   129: iconst_5       
        //   130: invokevirtual   java/util/Random.nextInt:(I)I
        //   133: iconst_3       
        //   134: isub           
        //   135: istore          4
        //   137: aload_0        
        //   138: getfield        net/minecraft/world/biome/BiomeGenForest.field_150632_aF:I
        //   141: iconst_1       
        //   142: if_icmpne       148
        //   145: iinc            4, 2
        //   148: goto            303
        //   151: aload_2        
        //   152: iconst_3       
        //   153: invokevirtual   java/util/Random.nextInt:(I)I
        //   156: istore          6
        //   158: iload           6
        //   160: ifne            175
        //   163: getstatic       net/minecraft/world/biome/BiomeGenForest.field_180280_ag:Lnet/minecraft/world/gen/feature/WorldGenDoublePlant;
        //   166: getstatic       net/minecraft/block/BlockDoublePlant$EnumPlantType.SYRINGA:Lnet/minecraft/block/BlockDoublePlant$EnumPlantType;
        //   169: invokevirtual   net/minecraft/world/gen/feature/WorldGenDoublePlant.func_180710_a:(Lnet/minecraft/block/BlockDoublePlant$EnumPlantType;)V
        //   172: goto            208
        //   175: iload           6
        //   177: iconst_1       
        //   178: if_icmpne       193
        //   181: getstatic       net/minecraft/world/biome/BiomeGenForest.field_180280_ag:Lnet/minecraft/world/gen/feature/WorldGenDoublePlant;
        //   184: getstatic       net/minecraft/block/BlockDoublePlant$EnumPlantType.ROSE:Lnet/minecraft/block/BlockDoublePlant$EnumPlantType;
        //   187: invokevirtual   net/minecraft/world/gen/feature/WorldGenDoublePlant.func_180710_a:(Lnet/minecraft/block/BlockDoublePlant$EnumPlantType;)V
        //   190: goto            208
        //   193: iload           6
        //   195: iconst_2       
        //   196: if_icmpne       208
        //   199: getstatic       net/minecraft/world/biome/BiomeGenForest.field_180280_ag:Lnet/minecraft/world/gen/feature/WorldGenDoublePlant;
        //   202: getstatic       net/minecraft/block/BlockDoublePlant$EnumPlantType.PAEONIA:Lnet/minecraft/block/BlockDoublePlant$EnumPlantType;
        //   205: invokevirtual   net/minecraft/world/gen/feature/WorldGenDoublePlant.func_180710_a:(Lnet/minecraft/block/BlockDoublePlant$EnumPlantType;)V
        //   208: iconst_0       
        //   209: iconst_5       
        //   210: if_icmpge       300
        //   213: aload_2        
        //   214: bipush          16
        //   216: invokevirtual   java/util/Random.nextInt:(I)I
        //   219: bipush          8
        //   221: iadd           
        //   222: istore          8
        //   224: aload_2        
        //   225: bipush          16
        //   227: invokevirtual   java/util/Random.nextInt:(I)I
        //   230: bipush          8
        //   232: iadd           
        //   233: istore          9
        //   235: aload_2        
        //   236: aload_1        
        //   237: aload_3        
        //   238: iload           8
        //   240: iconst_0       
        //   241: iload           9
        //   243: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //   246: invokevirtual   net/minecraft/world/World.getHorizon:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //   249: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   252: bipush          32
        //   254: iadd           
        //   255: invokevirtual   java/util/Random.nextInt:(I)I
        //   258: istore          10
        //   260: getstatic       net/minecraft/world/biome/BiomeGenForest.field_180280_ag:Lnet/minecraft/world/gen/feature/WorldGenDoublePlant;
        //   263: aload_1        
        //   264: aload_2        
        //   265: new             Lnet/minecraft/util/BlockPos;
        //   268: dup            
        //   269: aload_3        
        //   270: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   273: iload           8
        //   275: iadd           
        //   276: iload           10
        //   278: aload_3        
        //   279: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   282: iload           9
        //   284: iadd           
        //   285: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   288: invokevirtual   net/minecraft/world/gen/feature/WorldGenDoublePlant.generate:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)Z
        //   291: ifne            300
        //   294: iinc            7, 1
        //   297: goto            208
        //   300: iinc            5, 1
        //   303: iconst_0       
        //   304: iconst_0       
        //   305: if_icmplt       151
        //   308: aload_0        
        //   309: aload_1        
        //   310: aload_2        
        //   311: aload_3        
        //   312: invokespecial   net/minecraft/world/biome/BiomeGenBase.func_180624_a:(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)V
        //   315: return         
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
    
    @Override
    public int func_180627_b(final BlockPos blockPos) {
        final int func_180627_b = super.func_180627_b(blockPos);
        return (this.field_150632_aF == 3) ? ((func_180627_b & 0xFEFEFE) + 2634762 >> 1) : func_180627_b;
    }
    
    @Override
    protected BiomeGenBase createMutatedBiome(final int n) {
        if (this.biomeID == BiomeGenBase.forest.biomeID) {
            final BiomeGenForest biomeGenForest = new BiomeGenForest(n, 1);
            biomeGenForest.setHeight(new Height(this.minHeight, this.maxHeight + 0.2f));
            biomeGenForest.setBiomeName("Flower Forest");
            biomeGenForest.func_150557_a(6976549, true);
            biomeGenForest.setFillerBlockMetadata(8233509);
            return biomeGenForest;
        }
        return (this.biomeID != BiomeGenBase.birchForest.biomeID && this.biomeID != BiomeGenBase.birchForestHills.biomeID) ? new BiomeGenMutated(n, (BiomeGenBase)this) {
            private static final String __OBFID;
            final BiomeGenForest this$0;
            
            @Override
            public void func_180624_a(final World world, final Random random, final BlockPos blockPos) {
                this.baseBiome.func_180624_a(world, random, blockPos);
            }
            
            static {
                __OBFID = "CL_00000172";
            }
        } : new BiomeGenMutated(n, (BiomeGenBase)this) {
            private static final String __OBFID;
            final BiomeGenForest this$0;
            
            @Override
            public WorldGenAbstractTree genBigTreeChance(final Random random) {
                return (WorldGenAbstractTree)(random.nextBoolean() ? BiomeGenForest.field_150629_aC : BiomeGenForest.field_150630_aD);
            }
            
            static {
                __OBFID = "CL_00001861";
            }
        };
    }
}
