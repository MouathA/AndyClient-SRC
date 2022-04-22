package optifine;

import net.minecraft.world.biome.*;
import net.minecraft.block.*;
import java.util.regex.*;
import net.minecraft.client.renderer.texture.*;
import java.io.*;
import java.awt.image.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;

public class CustomColormap implements CustomColors.IColorizer
{
    public String name;
    public String basePath;
    private int format;
    private MatchBlock[] matchBlocks;
    private String source;
    private int color;
    private int yVariance;
    private int yOffset;
    private int width;
    private int height;
    private int[] colors;
    private float[][] colorsRgb;
    public static final String KEY_FORMAT;
    public static final String KEY_BLOCKS;
    public static final String KEY_SOURCE;
    public static final String KEY_COLOR;
    public static final String KEY_Y_VARIANCE;
    public static final String KEY_Y_OFFSET;
    
    public CustomColormap(final Properties properties, final String s, final int width, final int height) {
        this.name = null;
        this.basePath = null;
        this.format = -1;
        this.matchBlocks = null;
        this.source = null;
        this.color = -1;
        this.yVariance = 0;
        this.yOffset = 0;
        this.width = 0;
        this.height = 0;
        this.colors = null;
        this.colorsRgb = null;
        final ConnectedParser connectedParser = new ConnectedParser("Colormap");
        this.name = connectedParser.parseName(s);
        this.basePath = connectedParser.parseBasePath(s);
        this.format = this.parseFormat(properties.getProperty("format"));
        this.matchBlocks = connectedParser.parseMatchBlocks(properties.getProperty("blocks"));
        this.source = parseTexture(properties.getProperty("source"), s, this.basePath);
        this.color = ConnectedParser.parseColor(properties.getProperty("color"), -1);
        this.yVariance = connectedParser.parseInt(properties.getProperty("yVariance"), 0);
        this.yOffset = connectedParser.parseInt(properties.getProperty("yOffset"), 0);
        this.width = width;
        this.height = height;
    }
    
    private int parseFormat(final String s) {
        if (s == null) {
            return 0;
        }
        if (s.equals("vanilla")) {
            return 0;
        }
        if (s.equals("grid")) {
            return 1;
        }
        if (s.equals("fixed")) {
            return 2;
        }
        warn("Unknown format: " + s);
        return -1;
    }
    
    public boolean isValid(final String s) {
        if (this.format != 0 && this.format != 1) {
            if (this.format != 2) {
                return false;
            }
            if (this.color < 0) {
                this.color = 16777215;
            }
        }
        else {
            if (this.source == null) {
                warn("Source not defined: " + s);
                return false;
            }
            this.readColors();
            if (this.colors == null) {
                return false;
            }
            if (this.color < 0) {
                if (this.format == 0) {
                    this.color = this.getColor(127, 127);
                }
                if (this.format == 1) {
                    this.color = this.getColorGrid(BiomeGenBase.plains, new BlockPos(0, 64, 0));
                }
            }
        }
        return true;
    }
    
    public boolean isValidMatchBlocks(final String s) {
        if (this.matchBlocks == null) {
            this.matchBlocks = this.detectMatchBlocks();
            if (this.matchBlocks == null) {
                warn("Match blocks not defined: " + s);
                return false;
            }
        }
        return true;
    }
    
    private MatchBlock[] detectMatchBlocks() {
        final Block blockFromName = Block.getBlockFromName(this.name);
        if (blockFromName != null) {
            return new MatchBlock[] { new MatchBlock(Block.getIdFromBlock(blockFromName)) };
        }
        final Matcher matcher = Pattern.compile("^block([0-9]+).*$").matcher(this.name);
        if (matcher.matches()) {
            final int int1 = Config.parseInt(matcher.group(1), -1);
            if (int1 >= 0) {
                return new MatchBlock[] { new MatchBlock(int1) };
            }
        }
        final MatchBlock[] matchBlock = new ConnectedParser("Colormap").parseMatchBlock(this.name);
        return (MatchBlock[])((matchBlock != null) ? matchBlock : null);
    }
    
    private void readColors() {
        this.colors = null;
        if (this.source == null) {
            return;
        }
        final String string = String.valueOf(this.source) + ".png";
        final InputStream resourceStream = Config.getResourceStream(new ResourceLocation(string));
        if (resourceStream == null) {
            return;
        }
        final BufferedImage func_177053_a = TextureUtil.func_177053_a(resourceStream);
        if (func_177053_a == null) {
            return;
        }
        final int width = func_177053_a.getWidth();
        final int height = func_177053_a.getHeight();
        final boolean b = this.width < 0 || this.width == width;
        final boolean b2 = this.height < 0 || this.height == height;
        if (!b || !b2) {
            dbg("Non-standard palette size: " + width + "x" + height + ", should be: " + this.width + "x" + this.height + ", path: " + string);
        }
        this.width = width;
        this.height = height;
        if (this.width <= 0 || this.height <= 0) {
            warn("Invalid palette size: " + width + "x" + height + ", path: " + string);
            return;
        }
        func_177053_a.getRGB(0, 0, width, height, this.colors = new int[width * height], 0, width);
    }
    
    private static void dbg(final String s) {
        Config.dbg("CustomColors: " + s);
    }
    
    private static void warn(final String s) {
        Config.warn("CustomColors: " + s);
    }
    
    private static String parseTexture(String s, final String s2, final String s3) {
        if (s != null) {
            final String s4 = ".png";
            if (s.endsWith(s4)) {
                s = s.substring(0, s.length() - s4.length());
            }
            s = fixTextureName(s, s3);
            return s;
        }
        String s5 = s2;
        final int lastIndex = s2.lastIndexOf(47);
        if (lastIndex >= 0) {
            s5 = s2.substring(lastIndex + 1);
        }
        final int lastIndex2 = s5.lastIndexOf(46);
        if (lastIndex2 >= 0) {
            s5 = s5.substring(0, lastIndex2);
        }
        return fixTextureName(s5, s3);
    }
    
    private static String fixTextureName(String s, final String s2) {
        s = TextureUtils.fixResourcePath(s, s2);
        if (!s.startsWith(s2) && !s.startsWith("textures/") && !s.startsWith("mcpatcher/")) {
            s = String.valueOf(s2) + "/" + s;
        }
        if (s.endsWith(".png")) {
            s = s.substring(0, s.length() - 4);
        }
        final String s3 = "textures/blocks/";
        if (s.startsWith(s3)) {
            s = s.substring(s3.length());
        }
        if (s.startsWith("/")) {
            s = s.substring(1);
        }
        return s;
    }
    
    public boolean matchesBlock(final BlockStateBase blockStateBase) {
        return Matches.block(blockStateBase, this.matchBlocks);
    }
    
    public int getColorRandom() {
        if (this.format == 2) {
            return this.color;
        }
        return this.colors[CustomColors.random.nextInt(this.colors.length)];
    }
    
    public int getColor(int limit) {
        limit = Config.limit(limit, 0, this.colors.length);
        return this.colors[limit] & 0xFFFFFF;
    }
    
    public int getColor(int limit, int limit2) {
        limit = Config.limit(limit, 0, this.width - 1);
        limit2 = Config.limit(limit2, 0, this.height - 1);
        return this.colors[limit2 * this.width + limit] & 0xFFFFFF;
    }
    
    public float[][] getColorsRgb() {
        if (this.colorsRgb == null) {
            this.colorsRgb = toRgb(this.colors);
        }
        return this.colorsRgb;
    }
    
    @Override
    public int getColor(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return this.getColor(CustomColors.getColorBiome(blockAccess, blockPos), blockPos);
    }
    
    @Override
    public boolean isColorConstant() {
        return this.format == 2;
    }
    
    public int getColor(final BiomeGenBase biomeGenBase, final BlockPos blockPos) {
        return (this.format == 0) ? this.getColorVanilla(biomeGenBase, blockPos) : ((this.format == 1) ? this.getColorGrid(biomeGenBase, blockPos) : this.color);
    }
    
    public int getColorSmooth(final IBlockAccess blockAccess, final double n, final double n2, final double n3, final int n4) {
        if (this.format == 2) {
            return this.color;
        }
        final int floor_double = MathHelper.floor_double(n);
        final int floor_double2 = MathHelper.floor_double(n2);
        final int floor_double3 = MathHelper.floor_double(n3);
        final BlockPosM blockPosM = new BlockPosM(0, 0, 0);
        int n5 = floor_double - n4;
        while (0 <= floor_double + n4) {
            int n6 = floor_double3 - n4;
            while (0 <= floor_double3 + n4) {
                blockPosM.setXyz(0, floor_double2, 0);
                this.getColor(blockAccess, blockPosM);
                int n7 = 0;
                ++n7;
                ++n6;
            }
            ++n5;
        }
        return 0;
    }
    
    private int getColorVanilla(final BiomeGenBase biomeGenBase, final BlockPos blockPos) {
        final double n = MathHelper.clamp_float(biomeGenBase.func_180626_a(blockPos), 0.0f, 1.0f);
        return this.getColor((int)((1.0 - n) * (this.width - 1)), (int)((1.0 - MathHelper.clamp_float(biomeGenBase.getFloatRainfall(), 0.0f, 1.0f) * n) * (this.height - 1)));
    }
    
    private int getColorGrid(final BiomeGenBase biomeGenBase, final BlockPos blockPos) {
        final int biomeID = biomeGenBase.biomeID;
        int n = blockPos.getY() - this.yOffset;
        if (this.yVariance > 0) {
            n += (Config.intHash(blockPos.getX() << 16 + blockPos.getZ()) & 0xFF) % (this.yVariance * 2 + 1) - this.yVariance;
        }
        return this.getColor(biomeID, n);
    }
    
    public int getLength() {
        return (this.format == 2) ? 1 : this.colors.length;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    private static float[][] toRgb(final int[] array) {
        final float[][] array2 = new float[array.length][3];
        while (0 < array.length) {
            final int n = array[0];
            final float n2 = (n >> 16 & 0xFF) / 255.0f;
            final float n3 = (n >> 8 & 0xFF) / 255.0f;
            final float n4 = (n & 0xFF) / 255.0f;
            final float[] array3 = array2[0];
            array3[0] = n2;
            array3[1] = n3;
            array3[2] = n4;
            int n5 = 0;
            ++n5;
        }
        return array2;
    }
    
    public void addMatchBlock(final MatchBlock matchBlock) {
        if (this.matchBlocks == null) {
            this.matchBlocks = new MatchBlock[0];
        }
        this.matchBlocks = (MatchBlock[])Config.addObjectToArray(this.matchBlocks, matchBlock);
    }
    
    public void addMatchBlock(final int n, final int n2) {
        final MatchBlock matchBlock = this.getMatchBlock(n);
        if (matchBlock != null) {
            if (n2 >= 0) {
                matchBlock.addMetadata(n2);
            }
        }
        else {
            this.addMatchBlock(new MatchBlock(n, n2));
        }
    }
    
    private MatchBlock getMatchBlock(final int n) {
        if (this.matchBlocks == null) {
            return null;
        }
        while (0 < this.matchBlocks.length) {
            final MatchBlock matchBlock = this.matchBlocks[0];
            if (matchBlock.getBlockId() == n) {
                return matchBlock;
            }
            int n2 = 0;
            ++n2;
        }
        return null;
    }
    
    public int[] getMatchBlockIds() {
        if (this.matchBlocks == null) {
            return null;
        }
        final HashSet<Integer> set = new HashSet<Integer>();
        while (0 < this.matchBlocks.length) {
            final MatchBlock matchBlock = this.matchBlocks[0];
            if (matchBlock.getBlockId() >= 0) {
                set.add(matchBlock.getBlockId());
            }
            int n = 0;
            ++n;
        }
        final Integer[] array = set.toArray(new Integer[set.size()]);
        final int[] array2 = new int[array.length];
        while (0 < array.length) {
            array2[0] = array[0];
            int n2 = 0;
            ++n2;
        }
        return array2;
    }
    
    @Override
    public String toString() {
        return this.basePath + "/" + this.name + ", blocks: " + Config.arrayToString(this.matchBlocks) + ", source: " + this.source;
    }
    
    static {
        KEY_COLOR = "color";
        KEY_Y_OFFSET = "yOffset";
        KEY_FORMAT = "format";
        KEY_Y_VARIANCE = "yVariance";
        KEY_BLOCKS = "blocks";
        KEY_SOURCE = "source";
    }
}
