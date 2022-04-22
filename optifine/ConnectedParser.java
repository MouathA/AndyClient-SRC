package optifine;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.world.biome.*;
import net.minecraft.util.*;

public class ConnectedParser
{
    private String context;
    private static final MatchBlock[] NO_MATCH_BLOCKS;
    
    static {
        NO_MATCH_BLOCKS = new MatchBlock[0];
    }
    
    public ConnectedParser(final String context) {
        this.context = null;
        this.context = context;
    }
    
    public String parseName(final String s) {
        String s2 = s;
        final int lastIndex = s.lastIndexOf(47);
        if (lastIndex >= 0) {
            s2 = s.substring(lastIndex + 1);
        }
        final int lastIndex2 = s2.lastIndexOf(46);
        if (lastIndex2 >= 0) {
            s2 = s2.substring(0, lastIndex2);
        }
        return s2;
    }
    
    public String parseBasePath(final String s) {
        final int lastIndex = s.lastIndexOf(47);
        return (lastIndex < 0) ? "" : s.substring(0, lastIndex);
    }
    
    public MatchBlock[] parseMatchBlocks(final String s) {
        if (s == null) {
            return null;
        }
        final ArrayList list = new ArrayList();
        final String[] tokenize = Config.tokenize(s, " ");
        while (0 < tokenize.length) {
            final MatchBlock[] matchBlock = this.parseMatchBlock(tokenize[0]);
            if (matchBlock == null) {
                return ConnectedParser.NO_MATCH_BLOCKS;
            }
            list.addAll(Arrays.asList(matchBlock));
            int n = 0;
            ++n;
        }
        return list.toArray(new MatchBlock[list.size()]);
    }
    
    public MatchBlock[] parseMatchBlock(String trim) {
        if (trim == null) {
            return null;
        }
        trim = trim.trim();
        if (trim.length() <= 0) {
            return null;
        }
        final String[] tokenize = Config.tokenize(trim, ":");
        String s;
        if (tokenize.length > 1 && this.isFullBlockName(tokenize)) {
            s = tokenize[0];
        }
        else {
            s = "minecraft";
        }
        final String s2 = tokenize[0];
        final String[] array = Arrays.copyOfRange(tokenize, 1, tokenize.length);
        final Block[] blockPart = this.parseBlockPart(s, s2);
        if (blockPart == null) {
            return null;
        }
        final MatchBlock[] array2 = new MatchBlock[blockPart.length];
        while (0 < blockPart.length) {
            final Block block = blockPart[0];
            final int idFromBlock = Block.getIdFromBlock(block);
            int[] blockMetadatas = null;
            if (array.length > 0) {
                blockMetadatas = this.parseBlockMetadatas(block, array);
                if (blockMetadatas == null) {
                    return null;
                }
            }
            array2[0] = new MatchBlock(idFromBlock, blockMetadatas);
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    public boolean isFullBlockName(final String[] array) {
        if (array.length < 2) {
            return false;
        }
        final String s = array[1];
        return s.length() >= 1 && !this.startsWithDigit(s) && !s.contains("=");
    }
    
    public boolean startsWithDigit(final String s) {
        return s != null && s.length() >= 1 && Character.isDigit(s.charAt(0));
    }
    
    public Block[] parseBlockPart(final String s, final String s2) {
        if (this.startsWithDigit(s2)) {
            final int[] intList = this.parseIntList(s2);
            if (intList == null) {
                return null;
            }
            final Block[] array = new Block[intList.length];
            while (0 < intList.length) {
                final int n = intList[0];
                final Block blockById = Block.getBlockById(n);
                if (blockById == null) {
                    this.warn("Block not found for id: " + n);
                    return null;
                }
                array[0] = blockById;
                int n2 = 0;
                ++n2;
            }
            return array;
        }
        else {
            final String string = String.valueOf(s) + ":" + s2;
            final Block blockFromName = Block.getBlockFromName(string);
            if (blockFromName == null) {
                this.warn("Block not found for name: " + string);
                return null;
            }
            return new Block[] { blockFromName };
        }
    }
    
    public int[] parseBlockMetadatas(final Block p0, final String[] p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: arraylength    
        //     2: ifgt            7
        //     5: aconst_null    
        //     6: areturn        
        //     7: aload_2        
        //     8: iconst_0       
        //     9: aaload         
        //    10: astore_3       
        //    11: aload_0        
        //    12: aload_3        
        //    13: invokevirtual   optifine/ConnectedParser.startsWithDigit:(Ljava/lang/String;)Z
        //    16: ifeq            29
        //    19: aload_0        
        //    20: aload_3        
        //    21: invokevirtual   optifine/ConnectedParser.parseIntList:(Ljava/lang/String;)[I
        //    24: astore          4
        //    26: aload           4
        //    28: areturn        
        //    29: aload_1        
        //    30: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //    33: astore          4
        //    35: aload           4
        //    37: invokeinterface net/minecraft/block/state/IBlockState.getPropertyNames:()Ljava/util/Collection;
        //    42: astore          5
        //    44: new             Ljava/util/HashMap;
        //    47: dup            
        //    48: invokespecial   java/util/HashMap.<init>:()V
        //    51: astore          6
        //    53: goto            302
        //    56: aload_2        
        //    57: iconst_0       
        //    58: aaload         
        //    59: astore          8
        //    61: aload           8
        //    63: invokevirtual   java/lang/String.length:()I
        //    66: ifle            299
        //    69: aload           8
        //    71: ldc             "="
        //    73: invokestatic    optifine/Config.tokenize:(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        //    76: astore          9
        //    78: aload           9
        //    80: arraylength    
        //    81: iconst_2       
        //    82: if_icmpeq       108
        //    85: aload_0        
        //    86: new             Ljava/lang/StringBuilder;
        //    89: dup            
        //    90: ldc             "Invalid block property: "
        //    92: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    95: aload           8
        //    97: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   100: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   103: invokevirtual   optifine/ConnectedParser.warn:(Ljava/lang/String;)V
        //   106: aconst_null    
        //   107: areturn        
        //   108: aload           9
        //   110: iconst_0       
        //   111: aaload         
        //   112: astore          10
        //   114: aload           9
        //   116: iconst_1       
        //   117: aaload         
        //   118: astore          11
        //   120: aload           10
        //   122: aload           5
        //   124: invokestatic    optifine/ConnectedProperties.getProperty:(Ljava/lang/String;Ljava/util/Collection;)Lnet/minecraft/block/properties/IProperty;
        //   127: astore          12
        //   129: aload           12
        //   131: ifnonnull       166
        //   134: aload_0        
        //   135: new             Ljava/lang/StringBuilder;
        //   138: dup            
        //   139: ldc             "Property not found: "
        //   141: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   144: aload           10
        //   146: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   149: ldc             ", block: "
        //   151: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   154: aload_1        
        //   155: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   158: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   161: invokevirtual   optifine/ConnectedParser.warn:(Ljava/lang/String;)V
        //   164: aconst_null    
        //   165: areturn        
        //   166: aload           6
        //   168: aload           10
        //   170: invokevirtual   java/util/HashMap.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   173: checkcast       Ljava/util/List;
        //   176: astore          13
        //   178: aload           13
        //   180: ifnonnull       202
        //   183: new             Ljava/util/ArrayList;
        //   186: dup            
        //   187: invokespecial   java/util/ArrayList.<init>:()V
        //   190: astore          13
        //   192: aload           6
        //   194: aload           12
        //   196: aload           13
        //   198: invokevirtual   java/util/HashMap.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   201: pop            
        //   202: aload           11
        //   204: ldc             ","
        //   206: invokestatic    optifine/Config.tokenize:(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        //   209: astore          14
        //   211: goto            292
        //   214: aload           14
        //   216: iconst_0       
        //   217: aaload         
        //   218: astore          16
        //   220: aload           12
        //   222: aload           16
        //   224: invokestatic    optifine/ConnectedParser.parsePropertyValue:(Lnet/minecraft/block/properties/IProperty;Ljava/lang/String;)Ljava/lang/Comparable;
        //   227: astore          17
        //   229: aload           17
        //   231: ifnonnull       276
        //   234: aload_0        
        //   235: new             Ljava/lang/StringBuilder;
        //   238: dup            
        //   239: ldc             "Property value not found: "
        //   241: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   244: aload           16
        //   246: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   249: ldc             ", property: "
        //   251: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   254: aload           10
        //   256: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   259: ldc             ", block: "
        //   261: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   264: aload_1        
        //   265: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   268: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   271: invokevirtual   optifine/ConnectedParser.warn:(Ljava/lang/String;)V
        //   274: aconst_null    
        //   275: areturn        
        //   276: aload           13
        //   278: checkcast       Ljava/util/List;
        //   281: aload           17
        //   283: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   288: pop            
        //   289: iinc            15, 1
        //   292: iconst_0       
        //   293: aload           14
        //   295: arraylength    
        //   296: if_icmplt       214
        //   299: iinc            7, 1
        //   302: iconst_0       
        //   303: aload_2        
        //   304: arraylength    
        //   305: if_icmplt       56
        //   308: aload           6
        //   310: invokevirtual   java/util/HashMap.isEmpty:()Z
        //   313: ifeq            318
        //   316: aconst_null    
        //   317: areturn        
        //   318: new             Ljava/util/ArrayList;
        //   321: dup            
        //   322: invokespecial   java/util/ArrayList.<init>:()V
        //   325: astore          7
        //   327: goto            367
        //   330: aload_0        
        //   331: aload_1        
        //   332: iconst_0       
        //   333: invokespecial   optifine/ConnectedParser.getStateFromMeta:(Lnet/minecraft/block/Block;I)Lnet/minecraft/block/state/IBlockState;
        //   336: astore          10
        //   338: aload_0        
        //   339: aload           10
        //   341: aload           6
        //   343: invokevirtual   optifine/ConnectedParser.matchState:(Lnet/minecraft/block/state/IBlockState;Ljava/util/Map;)Z
        //   346: ifeq            364
        //   349: aload           7
        //   351: iconst_0       
        //   352: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   355: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   358: pop            
        //   359: goto            364
        //   362: astore          10
        //   364: iinc            9, 1
        //   367: iconst_0       
        //   368: bipush          16
        //   370: if_icmplt       330
        //   373: aload           7
        //   375: invokevirtual   java/util/ArrayList.size:()I
        //   378: bipush          16
        //   380: if_icmpne       385
        //   383: aconst_null    
        //   384: areturn        
        //   385: aload           7
        //   387: invokevirtual   java/util/ArrayList.size:()I
        //   390: newarray        I
        //   392: astore          9
        //   394: goto            416
        //   397: aload           9
        //   399: iconst_0       
        //   400: aload           7
        //   402: iconst_0       
        //   403: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //   406: checkcast       Ljava/lang/Integer;
        //   409: invokevirtual   java/lang/Integer.intValue:()I
        //   412: iastore        
        //   413: iinc            8, 1
        //   416: iconst_0       
        //   417: aload           9
        //   419: arraylength    
        //   420: if_icmplt       397
        //   423: aload           9
        //   425: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private IBlockState getStateFromMeta(final Block block, final int n) {
        IBlockState blockState = block.getStateFromMeta(n);
        if (block == Blocks.double_plant && n > 7) {
            blockState = blockState.withProperty(BlockDoublePlant.VARIANT_PROP, block.getStateFromMeta(n & 0x7).getValue(BlockDoublePlant.VARIANT_PROP));
        }
        return blockState;
    }
    
    public static Comparable parsePropertyValue(final IProperty property, final String s) {
        Comparable comparable = parseValue(s, property.getValueClass());
        if (comparable == null) {
            comparable = getPropertyValue(s, property.getAllowedValues());
        }
        return comparable;
    }
    
    public static Comparable getPropertyValue(final String s, final Collection collection) {
        for (final Comparable comparable : collection) {
            if (String.valueOf(comparable).equals(s)) {
                return comparable;
            }
        }
        return null;
    }
    
    public static Comparable parseValue(final String s, final Class clazz) {
        return (Comparable)((clazz == String.class) ? s : ((clazz == Boolean.class) ? Boolean.valueOf(s) : ((Double)((clazz == Float.class) ? Float.valueOf(s) : ((clazz == Double.class) ? Double.valueOf(s) : ((double)((clazz == Integer.class) ? Integer.valueOf(s) : ((long)((clazz == Long.class) ? Long.valueOf(s) : null)))))))));
    }
    
    public boolean matchState(final IBlockState blockState, final Map map) {
        for (final IProperty property : map.keySet()) {
            final List list = (List)map.get(property);
            final Comparable value = blockState.getValue(property);
            if (value == null) {
                return false;
            }
            if (!list.contains(value)) {
                return false;
            }
        }
        return true;
    }
    
    public BiomeGenBase[] parseBiomes(final String s) {
        if (s == null) {
            return null;
        }
        final String[] tokenize = Config.tokenize(s, " ");
        final ArrayList<BiomeGenBase> list = new ArrayList<BiomeGenBase>();
        while (0 < tokenize.length) {
            final String s2 = tokenize[0];
            final BiomeGenBase biome = this.findBiome(s2);
            if (biome == null) {
                this.warn("Biome not found: " + s2);
            }
            else {
                list.add(biome);
            }
            int n = 0;
            ++n;
        }
        return list.toArray(new BiomeGenBase[list.size()]);
    }
    
    public BiomeGenBase findBiome(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        if (lowerCase.equals("nether")) {
            return BiomeGenBase.hell;
        }
        final BiomeGenBase[] biomeGenArray = BiomeGenBase.getBiomeGenArray();
        while (0 < biomeGenArray.length) {
            final BiomeGenBase biomeGenBase = biomeGenArray[0];
            if (biomeGenBase != null && biomeGenBase.biomeName.replace(" ", "").toLowerCase().equals(lowerCase)) {
                return biomeGenBase;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public int parseInt(final String s) {
        if (s == null) {
            return -1;
        }
        final int int1 = Config.parseInt(s, -1);
        if (int1 < 0) {
            this.warn("Invalid number: " + s);
        }
        return int1;
    }
    
    public int parseInt(final String s, final int n) {
        if (s == null) {
            return n;
        }
        final int int1 = Config.parseInt(s, -1);
        if (int1 < 0) {
            this.warn("Invalid number: " + s);
            return n;
        }
        return int1;
    }
    
    public int[] parseIntList(final String p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: aconst_null    
        //     5: areturn        
        //     6: new             Ljava/util/ArrayList;
        //     9: dup            
        //    10: invokespecial   java/util/ArrayList.<init>:()V
        //    13: astore_2       
        //    14: aload_1        
        //    15: ldc_w           " ,"
        //    18: invokestatic    optifine/Config.tokenize:(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        //    21: astore_3       
        //    22: goto            256
        //    25: aload_3        
        //    26: iconst_0       
        //    27: aaload         
        //    28: astore          5
        //    30: aload           5
        //    32: ldc_w           "-"
        //    35: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    38: ifeq            195
        //    41: aload           5
        //    43: ldc_w           "-"
        //    46: invokestatic    optifine/Config.tokenize:(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        //    49: astore          6
        //    51: aload           6
        //    53: arraylength    
        //    54: iconst_2       
        //    55: if_icmpeq       93
        //    58: aload_0        
        //    59: new             Ljava/lang/StringBuilder;
        //    62: dup            
        //    63: ldc_w           "Invalid interval: "
        //    66: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    69: aload           5
        //    71: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    74: ldc_w           ", when parsing: "
        //    77: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    80: aload_1        
        //    81: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    84: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    87: invokevirtual   optifine/ConnectedParser.warn:(Ljava/lang/String;)V
        //    90: goto            253
        //    93: aload           6
        //    95: iconst_0       
        //    96: aaload         
        //    97: iconst_m1      
        //    98: invokestatic    optifine/Config.parseInt:(Ljava/lang/String;I)I
        //   101: istore          7
        //   103: aload           6
        //   105: iconst_1       
        //   106: aaload         
        //   107: iconst_m1      
        //   108: invokestatic    optifine/Config.parseInt:(Ljava/lang/String;I)I
        //   111: istore          8
        //   113: iload           7
        //   115: iflt            160
        //   118: iload           8
        //   120: iflt            160
        //   123: iload           7
        //   125: iload           8
        //   127: if_icmpgt       160
        //   130: iload           7
        //   132: istore          9
        //   134: goto            150
        //   137: aload_2        
        //   138: iload           9
        //   140: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   143: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   146: pop            
        //   147: iinc            9, 1
        //   150: iload           9
        //   152: iload           8
        //   154: if_icmple       137
        //   157: goto            253
        //   160: aload_0        
        //   161: new             Ljava/lang/StringBuilder;
        //   164: dup            
        //   165: ldc_w           "Invalid interval: "
        //   168: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   171: aload           5
        //   173: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   176: ldc_w           ", when parsing: "
        //   179: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   182: aload_1        
        //   183: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   186: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   189: invokevirtual   optifine/ConnectedParser.warn:(Ljava/lang/String;)V
        //   192: goto            253
        //   195: aload           5
        //   197: iconst_m1      
        //   198: invokestatic    optifine/Config.parseInt:(Ljava/lang/String;I)I
        //   201: istore          6
        //   203: iload           6
        //   205: ifge            243
        //   208: aload_0        
        //   209: new             Ljava/lang/StringBuilder;
        //   212: dup            
        //   213: ldc_w           "Invalid number: "
        //   216: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   219: aload           5
        //   221: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   224: ldc_w           ", when parsing: "
        //   227: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   230: aload_1        
        //   231: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   234: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   237: invokevirtual   optifine/ConnectedParser.warn:(Ljava/lang/String;)V
        //   240: goto            253
        //   243: aload_2        
        //   244: iload           6
        //   246: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   249: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   252: pop            
        //   253: iinc            4, 1
        //   256: iconst_0       
        //   257: aload_3        
        //   258: arraylength    
        //   259: if_icmplt       25
        //   262: aload_2        
        //   263: invokevirtual   java/util/ArrayList.size:()I
        //   266: newarray        I
        //   268: astore          4
        //   270: goto            291
        //   273: aload           4
        //   275: iconst_0       
        //   276: aload_2        
        //   277: iconst_0       
        //   278: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //   281: checkcast       Ljava/lang/Integer;
        //   284: invokevirtual   java/lang/Integer.intValue:()I
        //   287: iastore        
        //   288: iinc            5, 1
        //   291: iconst_0       
        //   292: aload           4
        //   294: arraylength    
        //   295: if_icmplt       273
        //   298: aload           4
        //   300: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public boolean[] parseFaces(final String p0, final boolean[] p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: aload_2        
        //     5: areturn        
        //     6: ldc_w           Lnet/minecraft/util/EnumFacing;.class
        //     9: invokestatic    java/util/EnumSet.allOf:(Ljava/lang/Class;)Ljava/util/EnumSet;
        //    12: astore_3       
        //    13: aload_1        
        //    14: ldc_w           " ,"
        //    17: invokestatic    optifine/Config.tokenize:(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        //    20: astore          4
        //    22: goto            125
        //    25: aload           4
        //    27: iconst_0       
        //    28: aaload         
        //    29: astore          6
        //    31: aload           6
        //    33: ldc_w           "sides"
        //    36: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    39: ifeq            77
        //    42: aload_3        
        //    43: getstatic       net/minecraft/util/EnumFacing.NORTH:Lnet/minecraft/util/EnumFacing;
        //    46: invokevirtual   java/util/EnumSet.add:(Ljava/lang/Object;)Z
        //    49: pop            
        //    50: aload_3        
        //    51: getstatic       net/minecraft/util/EnumFacing.SOUTH:Lnet/minecraft/util/EnumFacing;
        //    54: invokevirtual   java/util/EnumSet.add:(Ljava/lang/Object;)Z
        //    57: pop            
        //    58: aload_3        
        //    59: getstatic       net/minecraft/util/EnumFacing.WEST:Lnet/minecraft/util/EnumFacing;
        //    62: invokevirtual   java/util/EnumSet.add:(Ljava/lang/Object;)Z
        //    65: pop            
        //    66: aload_3        
        //    67: getstatic       net/minecraft/util/EnumFacing.EAST:Lnet/minecraft/util/EnumFacing;
        //    70: invokevirtual   java/util/EnumSet.add:(Ljava/lang/Object;)Z
        //    73: pop            
        //    74: goto            122
        //    77: aload           6
        //    79: ldc_w           "all"
        //    82: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    85: ifeq            102
        //    88: aload_3        
        //    89: getstatic       net/minecraft/util/EnumFacing.VALUES:[Lnet/minecraft/util/EnumFacing;
        //    92: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //    95: invokevirtual   java/util/EnumSet.addAll:(Ljava/util/Collection;)Z
        //    98: pop            
        //    99: goto            122
        //   102: aload_0        
        //   103: aload           6
        //   105: invokevirtual   optifine/ConnectedParser.parseFace:(Ljava/lang/String;)Lnet/minecraft/util/EnumFacing;
        //   108: astore          7
        //   110: aload           7
        //   112: ifnull          122
        //   115: aload_3        
        //   116: aload           7
        //   118: invokevirtual   java/util/EnumSet.add:(Ljava/lang/Object;)Z
        //   121: pop            
        //   122: iinc            5, 1
        //   125: iconst_0       
        //   126: aload           4
        //   128: arraylength    
        //   129: if_icmplt       25
        //   132: getstatic       net/minecraft/util/EnumFacing.VALUES:[Lnet/minecraft/util/EnumFacing;
        //   135: arraylength    
        //   136: newarray        Z
        //   138: astore          5
        //   140: goto            159
        //   143: aload           5
        //   145: iconst_0       
        //   146: aload_3        
        //   147: getstatic       net/minecraft/util/EnumFacing.VALUES:[Lnet/minecraft/util/EnumFacing;
        //   150: iconst_0       
        //   151: aaload         
        //   152: invokevirtual   java/util/EnumSet.contains:(Ljava/lang/Object;)Z
        //   155: bastore        
        //   156: iinc            6, 1
        //   159: iconst_0       
        //   160: aload           5
        //   162: arraylength    
        //   163: if_icmplt       143
        //   166: aload           5
        //   168: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public EnumFacing parseFace(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        if (lowerCase.equals("bottom") || lowerCase.equals("down")) {
            return EnumFacing.DOWN;
        }
        if (lowerCase.equals("top") || lowerCase.equals("up")) {
            return EnumFacing.UP;
        }
        if (lowerCase.equals("north")) {
            return EnumFacing.NORTH;
        }
        if (lowerCase.equals("south")) {
            return EnumFacing.SOUTH;
        }
        if (lowerCase.equals("east")) {
            return EnumFacing.EAST;
        }
        if (lowerCase.equals("west")) {
            return EnumFacing.WEST;
        }
        Config.warn("Unknown face: " + lowerCase);
        return null;
    }
    
    public void dbg(final String s) {
        Config.dbg(this.context + ": " + s);
    }
    
    public void warn(final String s) {
        Config.warn(this.context + ": " + s);
    }
    
    public RangeListInt parseRangeListInt(final String s) {
        if (s == null) {
            return null;
        }
        final RangeListInt rangeListInt = new RangeListInt();
        final String[] tokenize = Config.tokenize(s, " ,");
        while (0 < tokenize.length) {
            final RangeInt rangeInt = this.parseRangeInt(tokenize[0]);
            if (rangeInt == null) {
                return null;
            }
            rangeListInt.addRange(rangeInt);
            int n = 0;
            ++n;
        }
        return rangeListInt;
    }
    
    private RangeInt parseRangeInt(final String s) {
        if (s == null) {
            return null;
        }
        if (s.indexOf(45) >= 0) {
            final String[] tokenize = Config.tokenize(s, "-");
            if (tokenize.length != 2) {
                this.warn("Invalid range: " + s);
                return null;
            }
            final int int1 = Config.parseInt(tokenize[0], -1);
            final int int2 = Config.parseInt(tokenize[1], -1);
            if (int1 >= 0 && int2 >= 0) {
                return new RangeInt(int1, int2);
            }
            this.warn("Invalid range: " + s);
            return null;
        }
        else {
            final int int3 = Config.parseInt(s, -1);
            if (int3 < 0) {
                this.warn("Invalid integer: " + s);
                return null;
            }
            return new RangeInt(int3, int3);
        }
    }
    
    public static boolean parseBoolean(final String s) {
        return s != null && s.toLowerCase().equals("true");
    }
    
    public static int parseColor(String trim, final int n) {
        if (trim == null) {
            return n;
        }
        trim = trim.trim();
        return Integer.parseInt(trim, 16) & 0xFFFFFF;
    }
}
