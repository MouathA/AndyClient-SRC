package optifine;

import net.minecraft.world.biome.*;
import net.minecraft.block.properties.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import java.util.*;
import net.minecraft.util.*;

public class ConnectedProperties
{
    public String name;
    public String basePath;
    public MatchBlock[] matchBlocks;
    public int[] metadatas;
    public String[] matchTiles;
    public int method;
    public String[] tiles;
    public int connect;
    public int faces;
    public BiomeGenBase[] biomes;
    public int minHeight;
    public int maxHeight;
    public int renderPass;
    public boolean innerSeams;
    public int width;
    public int height;
    public int[] weights;
    public int symmetry;
    public int[] sumWeights;
    public int sumAllWeights;
    public TextureAtlasSprite[] matchTileIcons;
    public TextureAtlasSprite[] tileIcons;
    
    public ConnectedProperties(final Properties properties, final String s) {
        this.name = null;
        this.basePath = null;
        this.matchBlocks = null;
        this.metadatas = null;
        this.matchTiles = null;
        this.method = 0;
        this.tiles = null;
        this.connect = 0;
        this.faces = 63;
        this.biomes = null;
        this.minHeight = 0;
        this.maxHeight = 1024;
        this.renderPass = 0;
        this.innerSeams = false;
        this.width = 0;
        this.height = 0;
        this.weights = null;
        this.symmetry = 1;
        this.sumWeights = null;
        this.sumAllWeights = 1;
        this.matchTileIcons = null;
        this.tileIcons = null;
        final ConnectedParser connectedParser = new ConnectedParser("ConnectedTextures");
        this.name = connectedParser.parseName(s);
        this.basePath = connectedParser.parseBasePath(s);
        this.matchBlocks = connectedParser.parseMatchBlocks(properties.getProperty("matchBlocks"));
        this.metadatas = connectedParser.parseIntList(properties.getProperty("metadata"));
        this.matchTiles = this.parseMatchTiles(properties.getProperty("matchTiles"));
        this.method = parseMethod(properties.getProperty("method"));
        this.tiles = this.parseTileNames(properties.getProperty("tiles"));
        this.connect = parseConnect(properties.getProperty("connect"));
        this.faces = parseFaces(properties.getProperty("faces"));
        this.biomes = connectedParser.parseBiomes(properties.getProperty("biomes"));
        this.minHeight = connectedParser.parseInt(properties.getProperty("minHeight"), -1);
        this.maxHeight = connectedParser.parseInt(properties.getProperty("maxHeight"), 1024);
        this.renderPass = connectedParser.parseInt(properties.getProperty("renderPass"));
        this.innerSeams = ConnectedParser.parseBoolean(properties.getProperty("innerSeams"));
        this.width = connectedParser.parseInt(properties.getProperty("width"));
        this.height = connectedParser.parseInt(properties.getProperty("height"));
        this.weights = connectedParser.parseIntList(properties.getProperty("weights"));
        this.symmetry = parseSymmetry(properties.getProperty("symmetry"));
    }
    
    private String[] parseMatchTiles(final String s) {
        if (s == null) {
            return null;
        }
        final String[] tokenize = Config.tokenize(s, " ");
        while (0 < tokenize.length) {
            String substring = tokenize[0];
            if (substring.endsWith(".png")) {
                substring = substring.substring(0, substring.length() - 4);
            }
            tokenize[0] = TextureUtils.fixResourcePath(substring, this.basePath);
            int n = 0;
            ++n;
        }
        return tokenize;
    }
    
    private static String parseName(final String s) {
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
    
    private static String parseBasePath(final String s) {
        final int lastIndex = s.lastIndexOf(47);
        return (lastIndex < 0) ? "" : s.substring(0, lastIndex);
    }
    
    private String[] parseTileNames(final String p0) {
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
        //    15: ldc             " ,"
        //    17: invokestatic    optifine/Config.tokenize:(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        //    20: astore_3       
        //    21: goto            164
        //    24: aload_3        
        //    25: iconst_0       
        //    26: aaload         
        //    27: astore          5
        //    29: aload           5
        //    31: ldc             "-"
        //    33: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    36: ifeq            154
        //    39: aload           5
        //    41: ldc             "-"
        //    43: invokestatic    optifine/Config.tokenize:(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        //    46: astore          6
        //    48: aload           6
        //    50: arraylength    
        //    51: iconst_2       
        //    52: if_icmpne       154
        //    55: aload           6
        //    57: iconst_0       
        //    58: aaload         
        //    59: iconst_m1      
        //    60: invokestatic    optifine/Config.parseInt:(Ljava/lang/String;I)I
        //    63: istore          7
        //    65: aload           6
        //    67: iconst_1       
        //    68: aaload         
        //    69: iconst_m1      
        //    70: invokestatic    optifine/Config.parseInt:(Ljava/lang/String;I)I
        //    73: istore          8
        //    75: iload           7
        //    77: iflt            154
        //    80: iload           8
        //    82: iflt            154
        //    85: iload           7
        //    87: iload           8
        //    89: if_icmpgt       122
        //    92: iload           7
        //    94: istore          9
        //    96: iload           9
        //    98: iload           8
        //   100: if_icmple       106
        //   103: goto            161
        //   106: aload_2        
        //   107: iload           9
        //   109: invokestatic    java/lang/String.valueOf:(I)Ljava/lang/String;
        //   112: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   115: pop            
        //   116: iinc            9, 1
        //   119: goto            96
        //   122: new             Ljava/lang/StringBuilder;
        //   125: dup            
        //   126: ldc             "Invalid interval: "
        //   128: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   131: aload           5
        //   133: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   136: ldc             ", when parsing: "
        //   138: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   141: aload_1        
        //   142: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   145: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   148: invokestatic    optifine/Config.warn:(Ljava/lang/String;)V
        //   151: goto            161
        //   154: aload_2        
        //   155: aload           5
        //   157: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   160: pop            
        //   161: iinc            4, 1
        //   164: iconst_0       
        //   165: aload_3        
        //   166: arraylength    
        //   167: if_icmplt       24
        //   170: aload_2        
        //   171: aload_2        
        //   172: invokevirtual   java/util/ArrayList.size:()I
        //   175: anewarray       Ljava/lang/String;
        //   178: invokevirtual   java/util/ArrayList.toArray:([Ljava/lang/Object;)[Ljava/lang/Object;
        //   181: checkcast       [Ljava/lang/String;
        //   184: astore          4
        //   186: goto            348
        //   189: aload           4
        //   191: iconst_0       
        //   192: aaload         
        //   193: astore          6
        //   195: aload           6
        //   197: aload_0        
        //   198: getfield        optifine/ConnectedProperties.basePath:Ljava/lang/String;
        //   201: invokestatic    optifine/TextureUtils.fixResourcePath:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   204: astore          6
        //   206: aload           6
        //   208: aload_0        
        //   209: getfield        optifine/ConnectedProperties.basePath:Ljava/lang/String;
        //   212: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   215: ifne            268
        //   218: aload           6
        //   220: ldc             "textures/"
        //   222: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   225: ifne            268
        //   228: aload           6
        //   230: ldc             "mcpatcher/"
        //   232: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   235: ifne            268
        //   238: new             Ljava/lang/StringBuilder;
        //   241: dup            
        //   242: aload_0        
        //   243: getfield        optifine/ConnectedProperties.basePath:Ljava/lang/String;
        //   246: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //   249: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   252: ldc_w           "/"
        //   255: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   258: aload           6
        //   260: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   263: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   266: astore          6
        //   268: aload           6
        //   270: ldc             ".png"
        //   272: invokevirtual   java/lang/String.endsWith:(Ljava/lang/String;)Z
        //   275: ifeq            293
        //   278: aload           6
        //   280: iconst_0       
        //   281: aload           6
        //   283: invokevirtual   java/lang/String.length:()I
        //   286: iconst_4       
        //   287: isub           
        //   288: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   291: astore          6
        //   293: ldc_w           "textures/blocks/"
        //   296: astore          7
        //   298: aload           6
        //   300: aload           7
        //   302: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   305: ifeq            320
        //   308: aload           6
        //   310: aload           7
        //   312: invokevirtual   java/lang/String.length:()I
        //   315: invokevirtual   java/lang/String.substring:(I)Ljava/lang/String;
        //   318: astore          6
        //   320: aload           6
        //   322: ldc_w           "/"
        //   325: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   328: ifeq            339
        //   331: aload           6
        //   333: iconst_1       
        //   334: invokevirtual   java/lang/String.substring:(I)Ljava/lang/String;
        //   337: astore          6
        //   339: aload           4
        //   341: iconst_0       
        //   342: aload           6
        //   344: aastore        
        //   345: iinc            5, 1
        //   348: iconst_0       
        //   349: aload           4
        //   351: arraylength    
        //   352: if_icmplt       189
        //   355: aload           4
        //   357: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static int parseSymmetry(final String s) {
        if (s == null) {
            return 1;
        }
        if (s.equals("opposite")) {
            return 2;
        }
        if (s.equals("all")) {
            return 6;
        }
        Config.warn("Unknown symmetry: " + s);
        return 1;
    }
    
    private static int parseFaces(final String s) {
        if (s == null) {
            return 63;
        }
        final String[] tokenize = Config.tokenize(s, " ,");
        while (0 < tokenize.length) {
            final int n = 0x0 | parseFace(tokenize[0]);
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    private static int parseFace(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        if (lowerCase.equals("bottom") || lowerCase.equals("down")) {
            return 1;
        }
        if (lowerCase.equals("top") || lowerCase.equals("up")) {
            return 2;
        }
        if (lowerCase.equals("north")) {
            return 4;
        }
        if (lowerCase.equals("south")) {
            return 8;
        }
        if (lowerCase.equals("east")) {
            return 32;
        }
        if (lowerCase.equals("west")) {
            return 16;
        }
        if (lowerCase.equals("sides")) {
            return 60;
        }
        if (lowerCase.equals("all")) {
            return 63;
        }
        Config.warn("Unknown face: " + lowerCase);
        return 128;
    }
    
    private static int parseConnect(final String s) {
        if (s == null) {
            return 0;
        }
        if (s.equals("block")) {
            return 1;
        }
        if (s.equals("tile")) {
            return 2;
        }
        if (s.equals("material")) {
            return 3;
        }
        Config.warn("Unknown connect: " + s);
        return 128;
    }
    
    public static IProperty getProperty(final String s, final Collection collection) {
        for (final IProperty property : collection) {
            if (s.equals(property.getName())) {
                return property;
            }
        }
        return null;
    }
    
    private static int parseMethod(final String s) {
        if (s == null) {
            return 1;
        }
        if (s.equals("ctm") || s.equals("glass")) {
            return 1;
        }
        if (s.equals("horizontal") || s.equals("bookshelf")) {
            return 2;
        }
        if (s.equals("vertical")) {
            return 6;
        }
        if (s.equals("top")) {
            return 3;
        }
        if (s.equals("random")) {
            return 4;
        }
        if (s.equals("repeat")) {
            return 5;
        }
        if (s.equals("fixed")) {
            return 7;
        }
        if (s.equals("horizontal+vertical") || s.equals("h+v")) {
            return 8;
        }
        if (!s.equals("vertical+horizontal") && !s.equals("v+h")) {
            Config.warn("Unknown method: " + s);
            return 0;
        }
        return 9;
    }
    
    public boolean isValid(final String s) {
        if (this.name == null || this.name.length() <= 0) {
            Config.warn("No name found: " + s);
            return false;
        }
        if (this.basePath == null) {
            Config.warn("No base path found: " + s);
            return false;
        }
        if (this.matchBlocks == null) {
            this.matchBlocks = this.detectMatchBlocks();
        }
        if (this.matchTiles == null && this.matchBlocks == null) {
            this.matchTiles = this.detectMatchTiles();
        }
        if (this.matchBlocks == null && this.matchTiles == null) {
            Config.warn("No matchBlocks or matchTiles specified: " + s);
            return false;
        }
        if (this.method == 0) {
            Config.warn("No method: " + s);
            return false;
        }
        if (this.tiles == null || this.tiles.length <= 0) {
            Config.warn("No tiles specified: " + s);
            return false;
        }
        if (this.connect == 0) {
            this.connect = this.detectConnect();
        }
        if (this.connect == 128) {
            Config.warn("Invalid connect in: " + s);
            return false;
        }
        if (this.renderPass > 0) {
            Config.warn("Render pass not supported: " + this.renderPass);
            return false;
        }
        if ((this.faces & 0x80) != 0x0) {
            Config.warn("Invalid faces in: " + s);
            return false;
        }
        if ((this.symmetry & 0x80) != 0x0) {
            Config.warn("Invalid symmetry in: " + s);
            return false;
        }
        switch (this.method) {
            case 1: {
                return this.isValidCtm(s);
            }
            case 2: {
                return this.isValidHorizontal(s);
            }
            case 3: {
                return this.isValidTop(s);
            }
            case 4: {
                return this.isValidRandom(s);
            }
            case 5: {
                return this.isValidRepeat(s);
            }
            case 6: {
                return this.isValidVertical(s);
            }
            case 7: {
                return this.isValidFixed(s);
            }
            case 8: {
                return this.isValidHorizontalVertical(s);
            }
            case 9: {
                return this.isValidVerticalHorizontal(s);
            }
            default: {
                Config.warn("Unknown method: " + s);
                return false;
            }
        }
    }
    
    private int detectConnect() {
        return (this.matchBlocks != null) ? 1 : ((this.matchTiles != null) ? 2 : 128);
    }
    
    private MatchBlock[] detectMatchBlocks() {
        final int[] detectMatchBlockIds = this.detectMatchBlockIds();
        if (detectMatchBlockIds == null) {
            return null;
        }
        final MatchBlock[] array = new MatchBlock[detectMatchBlockIds.length];
        while (0 < array.length) {
            array[0] = new MatchBlock(detectMatchBlockIds[0]);
            int n = 0;
            ++n;
        }
        return array;
    }
    
    private int[] detectMatchBlockIds() {
        if (!this.name.startsWith("block")) {
            return null;
        }
        int i;
        int n;
        for (n = (i = 5); i < this.name.length(); ++i) {
            final char char1 = this.name.charAt(i);
            if (char1 < '0') {
                break;
            }
            if (char1 > '9') {
                break;
            }
        }
        if (i == n) {
            return null;
        }
        final int int1 = Config.parseInt(this.name.substring(n, i), -1);
        return (int[])((int1 < 0) ? null : new int[] { int1 });
    }
    
    private String[] detectMatchTiles() {
        return (String[])((getIcon(this.name) == null) ? null : new String[] { this.name });
    }
    
    private static TextureAtlasSprite getIcon(final String s) {
        final TextureMap textureMapBlocks = Minecraft.getMinecraft().getTextureMapBlocks();
        final TextureAtlasSprite spriteSafe = textureMapBlocks.getSpriteSafe(s);
        if (spriteSafe != null) {
            return spriteSafe;
        }
        return textureMapBlocks.getSpriteSafe("blocks/" + s);
    }
    
    private boolean isValidCtm(final String s) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("0-11 16-27 32-43 48-58");
        }
        if (this.tiles.length < 47) {
            Config.warn("Invalid tiles, must be at least 47: " + s);
            return false;
        }
        return true;
    }
    
    private boolean isValidHorizontal(final String s) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("12-15");
        }
        if (this.tiles.length != 4) {
            Config.warn("Invalid tiles, must be exactly 4: " + s);
            return false;
        }
        return true;
    }
    
    private boolean isValidVertical(final String s) {
        if (this.tiles == null) {
            Config.warn("No tiles defined for vertical: " + s);
            return false;
        }
        if (this.tiles.length != 4) {
            Config.warn("Invalid tiles, must be exactly 4: " + s);
            return false;
        }
        return true;
    }
    
    private boolean isValidHorizontalVertical(final String s) {
        if (this.tiles == null) {
            Config.warn("No tiles defined for horizontal+vertical: " + s);
            return false;
        }
        if (this.tiles.length != 7) {
            Config.warn("Invalid tiles, must be exactly 7: " + s);
            return false;
        }
        return true;
    }
    
    private boolean isValidVerticalHorizontal(final String s) {
        if (this.tiles == null) {
            Config.warn("No tiles defined for vertical+horizontal: " + s);
            return false;
        }
        if (this.tiles.length != 7) {
            Config.warn("Invalid tiles, must be exactly 7: " + s);
            return false;
        }
        return true;
    }
    
    private boolean isValidRandom(final String s) {
        if (this.tiles != null && this.tiles.length > 0) {
            if (this.weights != null) {
                if (this.weights.length > this.tiles.length) {
                    Config.warn("More weights defined than tiles, trimming weights: " + s);
                    final int[] weights = new int[this.tiles.length];
                    System.arraycopy(this.weights, 0, weights, 0, weights.length);
                    this.weights = weights;
                }
                int average = 0;
                if (this.weights.length < this.tiles.length) {
                    Config.warn("Less weights defined than tiles, expanding weights: " + s);
                    final int[] weights2 = new int[this.tiles.length];
                    System.arraycopy(this.weights, 0, weights2, 0, this.weights.length);
                    average = MathUtils.getAverage(this.weights);
                    int length = this.weights.length;
                    while (0 < weights2.length) {
                        weights2[0] = 0;
                        ++length;
                    }
                    this.weights = weights2;
                }
                this.sumWeights = new int[this.weights.length];
                while (0 < this.weights.length) {
                    final int n = 0 + this.weights[0];
                    this.sumWeights[0] = 0;
                    ++average;
                }
                this.sumAllWeights = 0;
                if (this.sumAllWeights <= 0) {
                    Config.warn("Invalid sum of all weights: " + 0);
                    this.sumAllWeights = 1;
                }
            }
            return true;
        }
        Config.warn("Tiles not defined: " + s);
        return false;
    }
    
    private boolean isValidRepeat(final String s) {
        if (this.tiles == null) {
            Config.warn("Tiles not defined: " + s);
            return false;
        }
        if (this.width <= 0 || this.width > 16) {
            Config.warn("Invalid width: " + s);
            return false;
        }
        if (this.height <= 0 || this.height > 16) {
            Config.warn("Invalid height: " + s);
            return false;
        }
        if (this.tiles.length != this.width * this.height) {
            Config.warn("Number of tiles does not equal width x height: " + s);
            return false;
        }
        return true;
    }
    
    private boolean isValidFixed(final String s) {
        if (this.tiles == null) {
            Config.warn("Tiles not defined: " + s);
            return false;
        }
        if (this.tiles.length != 1) {
            Config.warn("Number of tiles should be 1 for method: fixed.");
            return false;
        }
        return true;
    }
    
    private boolean isValidTop(final String s) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("66");
        }
        if (this.tiles.length != 1) {
            Config.warn("Invalid tiles, must be exactly 1: " + s);
            return false;
        }
        return true;
    }
    
    public void updateIcons(final TextureMap textureMap) {
        if (this.matchTiles != null) {
            this.matchTileIcons = registerIcons(this.matchTiles, textureMap);
        }
        if (this.tiles != null) {
            this.tileIcons = registerIcons(this.tiles, textureMap);
        }
    }
    
    private static TextureAtlasSprite[] registerIcons(final String[] array, final TextureMap textureMap) {
        if (array == null) {
            return null;
        }
        final ArrayList<TextureAtlasSprite> list = new ArrayList<TextureAtlasSprite>();
        while (0 < array.length) {
            final ResourceLocation resourceLocation = new ResourceLocation(array[0]);
            final String resourceDomain = resourceLocation.getResourceDomain();
            String s = resourceLocation.getResourcePath();
            if (!s.contains("/")) {
                s = "textures/blocks/" + s;
            }
            final String string = String.valueOf(s) + ".png";
            if (!Config.hasResource(new ResourceLocation(resourceDomain, string))) {
                Config.warn("File not found: " + string);
            }
            final String s2 = "textures/";
            String substring = s;
            if (s.startsWith(s2)) {
                substring = s.substring(s2.length());
            }
            list.add(textureMap.func_174942_a(new ResourceLocation(resourceDomain, substring)));
            int n = 0;
            ++n;
        }
        return list.toArray(new TextureAtlasSprite[list.size()]);
    }
    
    public boolean matchesBlockId(final int n) {
        return Matches.blockId(n, this.matchBlocks);
    }
    
    public boolean matchesBlock(final int n, final int n2) {
        return Matches.block(n, n2, this.matchBlocks) && Matches.metadata(n2, this.metadatas);
    }
    
    public boolean matchesIcon(final TextureAtlasSprite textureAtlasSprite) {
        return Matches.sprite(textureAtlasSprite, this.matchTileIcons);
    }
    
    @Override
    public String toString() {
        return "CTM name: " + this.name + ", basePath: " + this.basePath + ", matchBlocks: " + Config.arrayToString(this.matchBlocks) + ", matchTiles: " + Config.arrayToString(this.matchTiles);
    }
    
    public boolean matchesBiome(final BiomeGenBase biomeGenBase) {
        return Matches.biome(biomeGenBase, this.biomes);
    }
    
    public int getMetadataMax() {
        int n = this.getMax(this.metadatas, -1);
        if (this.matchBlocks != null) {
            while (0 < this.matchBlocks.length) {
                n = this.getMax(this.matchBlocks[0].getMetadatas(), n);
                int n2 = 0;
                ++n2;
            }
        }
        return n;
    }
    
    private int getMax(final int[] array, int n) {
        if (array == null) {
            return n;
        }
        while (0 < array.length) {
            final int n2 = array[0];
            if (n2 > n) {
                n = n2;
            }
            int n3 = 0;
            ++n3;
        }
        return n;
    }
}
