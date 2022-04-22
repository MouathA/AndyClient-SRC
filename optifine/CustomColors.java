package optifine;

import net.minecraft.init.*;
import net.minecraft.world.biome.*;
import org.apache.commons.lang3.tuple.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.block.state.*;
import net.minecraft.client.particle.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class CustomColors
{
    private static CustomColormap waterColors;
    private static CustomColormap foliagePineColors;
    private static CustomColormap foliageBirchColors;
    private static CustomColormap swampFoliageColors;
    private static CustomColormap swampGrassColors;
    private static CustomColormap[] colorsBlockColormaps;
    private static CustomColormap[][] blockColormaps;
    private static CustomColormap skyColors;
    private static CustomColorFader skyColorFader;
    private static CustomColormap fogColors;
    private static CustomColorFader fogColorFader;
    private static CustomColormap underwaterColors;
    private static CustomColorFader underwaterColorFader;
    private static CustomColormap[] lightMapsColorsRgb;
    private static int lightmapMinDimensionId;
    private static float[][] sunRgbs;
    private static float[][] torchRgbs;
    private static CustomColormap redstoneColors;
    private static CustomColormap xpOrbColors;
    private static CustomColormap stemColors;
    private static CustomColormap stemMelonColors;
    private static CustomColormap stemPumpkinColors;
    private static CustomColormap myceliumParticleColors;
    private static boolean useDefaultGrassFoliageColors;
    private static int particleWaterColor;
    private static int particlePortalColor;
    private static int lilyPadColor;
    private static int expBarTextColor;
    private static int bossTextColor;
    private static int signTextColor;
    private static Vec3 fogColorNether;
    private static Vec3 fogColorEnd;
    private static Vec3 skyColorEnd;
    private static int[] spawnEggPrimaryColors;
    private static int[] spawnEggSecondaryColors;
    private static float[][] wolfCollarColors;
    private static float[][] sheepColors;
    private static int[] textColors;
    private static int[] mapColorsOriginal;
    private static int[] potionColors;
    private static final IBlockState BLOCK_STATE_DIRT;
    private static final IBlockState BLOCK_STATE_WATER;
    public static Random random;
    private static final IColorizer COLORIZER_GRASS;
    private static final IColorizer COLORIZER_FOLIAGE;
    private static final IColorizer COLORIZER_FOLIAGE_PINE;
    private static final IColorizer COLORIZER_FOLIAGE_BIRCH;
    private static final IColorizer COLORIZER_WATER;
    private static final String[] lIlIIlIIllIIIllI;
    private static String[] lIlIIlIIllIllIIl;
    
    static {
        llllIIlIllIlllII();
        llllIIlIllIllIll();
        CustomColors.waterColors = null;
        CustomColors.foliagePineColors = null;
        CustomColors.foliageBirchColors = null;
        CustomColors.swampFoliageColors = null;
        CustomColors.swampGrassColors = null;
        CustomColors.colorsBlockColormaps = null;
        CustomColors.blockColormaps = null;
        CustomColors.skyColors = null;
        CustomColors.skyColorFader = new CustomColorFader();
        CustomColors.fogColors = null;
        CustomColors.fogColorFader = new CustomColorFader();
        CustomColors.underwaterColors = null;
        CustomColors.underwaterColorFader = new CustomColorFader();
        CustomColors.lightMapsColorsRgb = null;
        CustomColors.lightmapMinDimensionId = 0;
        CustomColors.sunRgbs = new float[16][3];
        CustomColors.torchRgbs = new float[16][3];
        CustomColors.redstoneColors = null;
        CustomColors.xpOrbColors = null;
        CustomColors.stemColors = null;
        CustomColors.stemMelonColors = null;
        CustomColors.stemPumpkinColors = null;
        CustomColors.myceliumParticleColors = null;
        CustomColors.useDefaultGrassFoliageColors = true;
        CustomColors.particleWaterColor = -1;
        CustomColors.particlePortalColor = -1;
        CustomColors.lilyPadColor = -1;
        CustomColors.expBarTextColor = -1;
        CustomColors.bossTextColor = -1;
        CustomColors.signTextColor = -1;
        CustomColors.fogColorNether = null;
        CustomColors.fogColorEnd = null;
        CustomColors.skyColorEnd = null;
        CustomColors.spawnEggPrimaryColors = null;
        CustomColors.spawnEggSecondaryColors = null;
        CustomColors.wolfCollarColors = null;
        CustomColors.sheepColors = null;
        CustomColors.textColors = null;
        CustomColors.mapColorsOriginal = null;
        CustomColors.potionColors = null;
        BLOCK_STATE_DIRT = Blocks.dirt.getDefaultState();
        BLOCK_STATE_WATER = Blocks.water.getDefaultState();
        CustomColors.random = new Random();
        COLORIZER_GRASS = new IColorizer() {
            @Override
            public int getColor(final IBlockAccess blockAccess, final BlockPos blockPos) {
                final BiomeGenBase colorBiome = CustomColors.getColorBiome(blockAccess, blockPos);
                return (CustomColors.access$0() != null && colorBiome == BiomeGenBase.swampland) ? CustomColors.access$0().getColor(colorBiome, blockPos) : colorBiome.func_180627_b(blockPos);
            }
            
            @Override
            public boolean isColorConstant() {
                return false;
            }
        };
        COLORIZER_FOLIAGE = new IColorizer() {
            @Override
            public int getColor(final IBlockAccess blockAccess, final BlockPos blockPos) {
                final BiomeGenBase colorBiome = CustomColors.getColorBiome(blockAccess, blockPos);
                return (CustomColors.access$1() != null && colorBiome == BiomeGenBase.swampland) ? CustomColors.access$1().getColor(colorBiome, blockPos) : colorBiome.func_180625_c(blockPos);
            }
            
            @Override
            public boolean isColorConstant() {
                return false;
            }
        };
        COLORIZER_FOLIAGE_PINE = new IColorizer() {
            @Override
            public int getColor(final IBlockAccess blockAccess, final BlockPos blockPos) {
                return (CustomColors.access$2() != null) ? CustomColors.access$2().getColor(blockAccess, blockPos) : 6396257;
            }
            
            @Override
            public boolean isColorConstant() {
                return CustomColors.access$2() == null;
            }
        };
        COLORIZER_FOLIAGE_BIRCH = new IColorizer() {
            @Override
            public int getColor(final IBlockAccess blockAccess, final BlockPos blockPos) {
                return (CustomColors.access$3() != null) ? CustomColors.access$3().getColor(blockAccess, blockPos) : 8431445;
            }
            
            @Override
            public boolean isColorConstant() {
                return CustomColors.access$3() == null;
            }
        };
        COLORIZER_WATER = new IColorizer() {
            @Override
            public int getColor(final IBlockAccess blockAccess, final BlockPos blockPos) {
                final BiomeGenBase colorBiome = CustomColors.getColorBiome(blockAccess, blockPos);
                return (CustomColors.access$4() != null) ? CustomColors.access$4().getColor(colorBiome, blockPos) : (Reflector.ForgeBiomeGenBase_getWaterColorMultiplier.exists() ? Reflector.callInt(colorBiome, Reflector.ForgeBiomeGenBase_getWaterColorMultiplier, new Object[0]) : colorBiome.waterColorMultiplier);
            }
            
            @Override
            public boolean isColorConstant() {
                return false;
            }
        };
    }
    
    public static void update() {
        CustomColors.waterColors = null;
        CustomColors.foliageBirchColors = null;
        CustomColors.foliagePineColors = null;
        CustomColors.swampGrassColors = null;
        CustomColors.swampFoliageColors = null;
        CustomColors.skyColors = null;
        CustomColors.fogColors = null;
        CustomColors.underwaterColors = null;
        CustomColors.redstoneColors = null;
        CustomColors.xpOrbColors = null;
        CustomColors.stemColors = null;
        CustomColors.myceliumParticleColors = null;
        CustomColors.lightMapsColorsRgb = null;
        CustomColors.particleWaterColor = -1;
        CustomColors.particlePortalColor = -1;
        CustomColors.lilyPadColor = -1;
        CustomColors.expBarTextColor = -1;
        CustomColors.bossTextColor = -1;
        CustomColors.signTextColor = -1;
        CustomColors.fogColorNether = null;
        CustomColors.fogColorEnd = null;
        CustomColors.skyColorEnd = null;
        CustomColors.colorsBlockColormaps = null;
        CustomColors.blockColormaps = null;
        CustomColors.useDefaultGrassFoliageColors = true;
        CustomColors.spawnEggPrimaryColors = null;
        CustomColors.spawnEggSecondaryColors = null;
        CustomColors.wolfCollarColors = null;
        CustomColors.sheepColors = null;
        CustomColors.textColors = null;
        setMapColors(CustomColors.mapColorsOriginal);
        CustomColors.potionColors = null;
        PotionHelper.clearPotionColorCache();
        final String s = CustomColors.lIlIIlIIllIIIllI[0];
        CustomColors.waterColors = getCustomColors(s, new String[] { CustomColors.lIlIIlIIllIIIllI[1], CustomColors.lIlIIlIIllIIIllI[2] }, 256, 256);
        updateUseDefaultGrassFoliageColors();
        if (Config.isCustomColors()) {
            CustomColors.foliagePineColors = getCustomColors(s, new String[] { CustomColors.lIlIIlIIllIIIllI[3], CustomColors.lIlIIlIIllIIIllI[4] }, 256, 256);
            CustomColors.foliageBirchColors = getCustomColors(s, new String[] { CustomColors.lIlIIlIIllIIIllI[5], CustomColors.lIlIIlIIllIIIllI[6] }, 256, 256);
            CustomColors.swampGrassColors = getCustomColors(s, new String[] { CustomColors.lIlIIlIIllIIIllI[7], CustomColors.lIlIIlIIllIIIllI[8] }, 256, 256);
            CustomColors.swampFoliageColors = getCustomColors(s, new String[] { CustomColors.lIlIIlIIllIIIllI[9], CustomColors.lIlIIlIIllIIIllI[10] }, 256, 256);
            CustomColors.skyColors = getCustomColors(s, new String[] { CustomColors.lIlIIlIIllIIIllI[11], CustomColors.lIlIIlIIllIIIllI[12] }, 256, 256);
            CustomColors.fogColors = getCustomColors(s, new String[] { CustomColors.lIlIIlIIllIIIllI[13], CustomColors.lIlIIlIIllIIIllI[14] }, 256, 256);
            CustomColors.underwaterColors = getCustomColors(s, new String[] { CustomColors.lIlIIlIIllIIIllI[15], CustomColors.lIlIIlIIllIIIllI[16] }, 256, 256);
            CustomColors.redstoneColors = getCustomColors(s, new String[] { CustomColors.lIlIIlIIllIIIllI[17], CustomColors.lIlIIlIIllIIIllI[18] }, 16, 1);
            CustomColors.xpOrbColors = getCustomColors(String.valueOf(s) + CustomColors.lIlIIlIIllIIIllI[19], -1, -1);
            CustomColors.stemColors = getCustomColors(s, new String[] { CustomColors.lIlIIlIIllIIIllI[20], CustomColors.lIlIIlIIllIIIllI[21] }, 8, 1);
            CustomColors.stemPumpkinColors = getCustomColors(String.valueOf(s) + CustomColors.lIlIIlIIllIIIllI[22], 8, 1);
            CustomColors.stemMelonColors = getCustomColors(String.valueOf(s) + CustomColors.lIlIIlIIllIIIllI[23], 8, 1);
            CustomColors.myceliumParticleColors = getCustomColors(s, new String[] { CustomColors.lIlIIlIIllIIIllI[24], CustomColors.lIlIIlIIllIIIllI[25] }, -1, -1);
            final Pair lightmapsRgb = parseLightmapsRgb();
            CustomColors.lightMapsColorsRgb = (CustomColormap[])lightmapsRgb.getLeft();
            CustomColors.lightmapMinDimensionId = (int)lightmapsRgb.getRight();
            readColorProperties(CustomColors.lIlIIlIIllIIIllI[26]);
            CustomColors.blockColormaps = readBlockColormaps(new String[] { String.valueOf(s) + CustomColors.lIlIIlIIllIIIllI[27], String.valueOf(s) + CustomColors.lIlIIlIIllIIIllI[28] }, CustomColors.colorsBlockColormaps, 256, 256);
            updateUseDefaultGrassFoliageColors();
        }
    }
    
    private static Pair parseLightmapsRgb() {
        final String s = CustomColors.lIlIIlIIllIIIllI[29];
        final String s2 = CustomColors.lIlIIlIIllIIIllI[30];
        final String[] collectFiles = ResUtils.collectFiles(s, s2);
        final HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
        for (int i = 0; i < collectFiles.length; ++i) {
            final String s3 = collectFiles[i];
            final String removePrefixSuffix = StrUtils.removePrefixSuffix(s3, s, s2);
            final int int1 = Config.parseInt(removePrefixSuffix, Integer.MIN_VALUE);
            if (int1 == Integer.MIN_VALUE) {
                warn(CustomColors.lIlIIlIIllIIIllI[31] + removePrefixSuffix + CustomColors.lIlIIlIIllIIIllI[32] + s3);
            }
            else {
                hashMap.put(int1, s3);
            }
        }
        final Set<Integer> keySet = hashMap.keySet();
        final Integer[] array = keySet.toArray(new Integer[keySet.size()]);
        Arrays.sort(array);
        if (array.length <= 0) {
            return new ImmutablePair(null, 0);
        }
        final int intValue = array[0];
        final CustomColormap[] array2 = new CustomColormap[array[array.length - 1] - intValue + 1];
        for (int j = 0; j < array.length; ++j) {
            final Integer n = array[j];
            final String s4 = hashMap.get(n);
            final CustomColormap customColors = getCustomColors(s4, -1, -1);
            if (customColors != null) {
                if (customColors.getWidth() < 16) {
                    warn(CustomColors.lIlIIlIIllIIIllI[33] + customColors.getWidth() + CustomColors.lIlIIlIIllIIIllI[34] + s4);
                }
                else {
                    array2[n - intValue] = customColors;
                }
            }
        }
        return new ImmutablePair(array2, intValue);
    }
    
    private static int getTextureHeight(final String s, final int n) {
        try {
            final InputStream resourceStream = Config.getResourceStream(new ResourceLocation(s));
            if (resourceStream == null) {
                return n;
            }
            final BufferedImage read = ImageIO.read(resourceStream);
            resourceStream.close();
            return (read == null) ? n : read.getHeight();
        }
        catch (IOException ex) {
            return n;
        }
    }
    
    private static void readColorProperties(final String s) {
        try {
            final InputStream resourceStream = Config.getResourceStream(new ResourceLocation(s));
            if (resourceStream == null) {
                return;
            }
            dbg(CustomColors.lIlIIlIIllIIIllI[35] + s);
            final Properties properties = new Properties();
            properties.load(resourceStream);
            resourceStream.close();
            CustomColors.particleWaterColor = readColor(properties, new String[] { CustomColors.lIlIIlIIllIIIllI[36], CustomColors.lIlIIlIIllIIIllI[37] });
            CustomColors.particlePortalColor = readColor(properties, CustomColors.lIlIIlIIllIIIllI[38]);
            CustomColors.lilyPadColor = readColor(properties, CustomColors.lIlIIlIIllIIIllI[39]);
            CustomColors.expBarTextColor = readColor(properties, CustomColors.lIlIIlIIllIIIllI[40]);
            CustomColors.bossTextColor = readColor(properties, CustomColors.lIlIIlIIllIIIllI[41]);
            CustomColors.signTextColor = readColor(properties, CustomColors.lIlIIlIIllIIIllI[42]);
            CustomColors.fogColorNether = readColorVec3(properties, CustomColors.lIlIIlIIllIIIllI[43]);
            CustomColors.fogColorEnd = readColorVec3(properties, CustomColors.lIlIIlIIllIIIllI[44]);
            CustomColors.skyColorEnd = readColorVec3(properties, CustomColors.lIlIIlIIllIIIllI[45]);
            CustomColors.colorsBlockColormaps = readCustomColormaps(properties, s);
            CustomColors.spawnEggPrimaryColors = readSpawnEggColors(properties, s, CustomColors.lIlIIlIIllIIIllI[46], CustomColors.lIlIIlIIllIIIllI[47]);
            CustomColors.spawnEggSecondaryColors = readSpawnEggColors(properties, s, CustomColors.lIlIIlIIllIIIllI[48], CustomColors.lIlIIlIIllIIIllI[49]);
            CustomColors.wolfCollarColors = readDyeColors(properties, s, CustomColors.lIlIIlIIllIIIllI[50], CustomColors.lIlIIlIIllIIIllI[51]);
            CustomColors.sheepColors = readDyeColors(properties, s, CustomColors.lIlIIlIIllIIIllI[52], CustomColors.lIlIIlIIllIIIllI[53]);
            CustomColors.textColors = readTextColors(properties, s, CustomColors.lIlIIlIIllIIIllI[54], CustomColors.lIlIIlIIllIIIllI[55]);
            final int[] mapColors = readMapColors(properties, s, CustomColors.lIlIIlIIllIIIllI[56], CustomColors.lIlIIlIIllIIIllI[57]);
            if (mapColors != null) {
                if (CustomColors.mapColorsOriginal == null) {
                    CustomColors.mapColorsOriginal = getMapColors();
                }
                setMapColors(mapColors);
            }
            CustomColors.potionColors = readPotionColors(properties, s, CustomColors.lIlIIlIIllIIIllI[58], CustomColors.lIlIIlIIllIIIllI[59]);
        }
        catch (FileNotFoundException ex2) {}
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private static CustomColormap[] readCustomColormaps(final Properties properties, final String s) {
        final ArrayList<CustomColormap> list = new ArrayList<CustomColormap>();
        final String s2 = CustomColors.lIlIIlIIllIIIllI[60];
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        for (final String s3 : ((Hashtable<String, V>)properties).keySet()) {
            final String property = properties.getProperty(s3);
            if (s3.startsWith(s2)) {
                hashMap.put(s3, property);
            }
        }
        final String[] array = hashMap.keySet().toArray(new String[hashMap.size()]);
        for (int i = 0; i < array.length; ++i) {
            final String s4 = array[i];
            final String property2 = properties.getProperty(s4);
            dbg(CustomColors.lIlIIlIIllIIIllI[61] + s4 + CustomColors.lIlIIlIIllIIIllI[62] + property2);
            final String fixResourcePath = TextureUtils.fixResourcePath(s4.substring(s2.length()), TextureUtils.getBasePath(s));
            final CustomColormap customColors = getCustomColors(fixResourcePath, 256, 256);
            if (customColors == null) {
                warn(CustomColors.lIlIIlIIllIIIllI[63] + fixResourcePath);
            }
            else {
                final MatchBlock[] matchBlocks = new ConnectedParser(CustomColors.lIlIIlIIllIIIllI[64]).parseMatchBlocks(property2);
                if (matchBlocks != null && matchBlocks.length > 0) {
                    for (int j = 0; j < matchBlocks.length; ++j) {
                        customColors.addMatchBlock(matchBlocks[j]);
                    }
                    list.add(customColors);
                }
                else {
                    warn(CustomColors.lIlIIlIIllIIIllI[65] + property2);
                }
            }
        }
        if (list.size() <= 0) {
            return null;
        }
        return list.toArray(new CustomColormap[list.size()]);
    }
    
    private static CustomColormap[][] readBlockColormaps(final String[] array, final CustomColormap[] array2, final int n, final int n2) {
        final String[] collectFiles = ResUtils.collectFiles(array, new String[] { CustomColors.lIlIIlIIllIIIllI[66] });
        Arrays.sort(collectFiles);
        final ArrayList list = new ArrayList();
        for (int i = 0; i < collectFiles.length; ++i) {
            final String s = collectFiles[i];
            dbg(CustomColors.lIlIIlIIllIIIllI[67] + s);
            try {
                final InputStream resourceStream = Config.getResourceStream(new ResourceLocation(CustomColors.lIlIIlIIllIIIllI[68], s));
                if (resourceStream == null) {
                    warn(CustomColors.lIlIIlIIllIIIllI[69] + s);
                }
                else {
                    final Properties properties = new Properties();
                    properties.load(resourceStream);
                    final CustomColormap customColormap = new CustomColormap(properties, s, n, n2);
                    if (customColormap.isValid(s) && customColormap.isValidMatchBlocks(s)) {
                        addToBlockList(customColormap, list);
                    }
                }
            }
            catch (FileNotFoundException ex2) {
                warn(CustomColors.lIlIIlIIllIIIllI[70] + s);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (array2 != null) {
            for (int j = 0; j < array2.length; ++j) {
                addToBlockList(array2[j], list);
            }
        }
        if (list.size() <= 0) {
            return null;
        }
        return blockListToArray(list);
    }
    
    private static void addToBlockList(final CustomColormap customColormap, final List list) {
        final int[] matchBlockIds = customColormap.getMatchBlockIds();
        if (matchBlockIds != null && matchBlockIds.length > 0) {
            for (int i = 0; i < matchBlockIds.length; ++i) {
                final int n = matchBlockIds[i];
                if (n < 0) {
                    warn(CustomColors.lIlIIlIIllIIIllI[71] + n);
                }
                else {
                    addToList(customColormap, list, n);
                }
            }
        }
        else {
            warn(CustomColors.lIlIIlIIllIIIllI[72] + Config.arrayToString(matchBlockIds));
        }
    }
    
    private static void addToList(final CustomColormap customColormap, final List list, final int i) {
        while (i >= list.size()) {
            list.add(null);
        }
        List<CustomColormap> list2 = list.get(i);
        if (list2 == null) {
            list2 = new ArrayList<CustomColormap>();
            list.set(i, list2);
        }
        list2.add(customColormap);
    }
    
    private static CustomColormap[][] blockListToArray(final List list) {
        final CustomColormap[][] array = new CustomColormap[list.size()][];
        for (int i = 0; i < list.size(); ++i) {
            final List list2 = list.get(i);
            if (list2 != null) {
                array[i] = (CustomColormap[])list2.toArray(new CustomColormap[list2.size()]);
            }
        }
        return array;
    }
    
    private static int readColor(final Properties properties, final String[] array) {
        for (int i = 0; i < array.length; ++i) {
            final int color = readColor(properties, array[i]);
            if (color >= 0) {
                return color;
            }
        }
        return -1;
    }
    
    private static int readColor(final Properties properties, final String s) {
        final String property = properties.getProperty(s);
        if (property == null) {
            return -1;
        }
        final String trim = property.trim();
        final int color = parseColor(trim);
        if (color < 0) {
            warn(CustomColors.lIlIIlIIllIIIllI[73] + s + CustomColors.lIlIIlIIllIIIllI[74] + trim);
            return color;
        }
        dbg(String.valueOf(s) + CustomColors.lIlIIlIIllIIIllI[75] + trim);
        return color;
    }
    
    private static int parseColor(String trim) {
        if (trim == null) {
            return -1;
        }
        trim = trim.trim();
        try {
            return Integer.parseInt(trim, 16) & 0xFFFFFF;
        }
        catch (NumberFormatException ex) {
            return -1;
        }
    }
    
    private static Vec3 readColorVec3(final Properties properties, final String s) {
        final int color = readColor(properties, s);
        if (color < 0) {
            return null;
        }
        return new Vec3((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f);
    }
    
    private static CustomColormap getCustomColors(final String s, final String[] array, final int n, final int n2) {
        for (int i = 0; i < array.length; ++i) {
            final CustomColormap customColors = getCustomColors(String.valueOf(s) + array[i], n, n2);
            if (customColors != null) {
                return customColors;
            }
        }
        return null;
    }
    
    public static CustomColormap getCustomColors(final String s, final int n, final int n2) {
        try {
            if (!Config.hasResource(new ResourceLocation(s))) {
                return null;
            }
            dbg(CustomColors.lIlIIlIIllIIIllI[76] + s);
            final Properties properties = new Properties();
            String replaceSuffix = StrUtils.replaceSuffix(s, CustomColors.lIlIIlIIllIIIllI[77], CustomColors.lIlIIlIIllIIIllI[78]);
            final ResourceLocation resourceLocation = new ResourceLocation(replaceSuffix);
            if (Config.hasResource(resourceLocation)) {
                final InputStream resourceStream = Config.getResourceStream(resourceLocation);
                properties.load(resourceStream);
                resourceStream.close();
                dbg(CustomColors.lIlIIlIIllIIIllI[79] + replaceSuffix);
            }
            else {
                ((Hashtable<String, String>)properties).put(CustomColors.lIlIIlIIllIIIllI[80], CustomColors.lIlIIlIIllIIIllI[81]);
                ((Hashtable<String, String>)properties).put(CustomColors.lIlIIlIIllIIIllI[82], s);
                replaceSuffix = s;
            }
            final CustomColormap customColormap = new CustomColormap(properties, replaceSuffix, n, n2);
            return customColormap.isValid(replaceSuffix) ? customColormap : null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static void updateUseDefaultGrassFoliageColors() {
        CustomColors.useDefaultGrassFoliageColors = (CustomColors.foliageBirchColors == null && CustomColors.foliagePineColors == null && CustomColors.swampGrassColors == null && CustomColors.swampFoliageColors == null && Config.isSwampColors() && Config.isSmoothBiomes());
    }
    
    public static int getColorMultiplier(final BakedQuad bakedQuad, final Block block, final IBlockAccess blockAccess, BlockPos blockPos, final RenderEnv renderEnv) {
        if (CustomColors.blockColormaps != null) {
            IBlockState blockState = renderEnv.getBlockState();
            if (!bakedQuad.func_178212_b()) {
                if (block == Blocks.grass) {
                    blockState = CustomColors.BLOCK_STATE_DIRT;
                }
                if (block == Blocks.redstone_wire) {
                    return -1;
                }
            }
            if (block == Blocks.double_plant && renderEnv.getMetadata() >= 8) {
                blockPos = blockPos.offsetDown();
                blockState = blockAccess.getBlockState(blockPos);
            }
            final CustomColormap blockColormap = getBlockColormap(blockState);
            if (blockColormap != null) {
                if (Config.isSmoothBiomes() && !blockColormap.isColorConstant()) {
                    return getSmoothColorMultiplier(blockAccess, blockPos, blockColormap, renderEnv.getColorizerBlockPosM());
                }
                return blockColormap.getColor(blockAccess, blockPos);
            }
        }
        if (!bakedQuad.func_178212_b()) {
            return -1;
        }
        if (block == Blocks.waterlily) {
            return getLilypadColorMultiplier(blockAccess, blockPos);
        }
        if (block == Blocks.redstone_wire) {
            return getRedstoneColor(renderEnv.getBlockState());
        }
        if (block instanceof BlockStem) {
            return getStemColorMultiplier(block, blockAccess, blockPos, renderEnv);
        }
        if (CustomColors.useDefaultGrassFoliageColors) {
            return -1;
        }
        final int metadata = renderEnv.getMetadata();
        IColorizer colorizer;
        if (block != Blocks.grass && block != Blocks.tallgrass && block != Blocks.double_plant) {
            if (block == Blocks.double_plant) {
                colorizer = CustomColors.COLORIZER_GRASS;
                if (metadata >= 8) {
                    blockPos = blockPos.offsetDown();
                }
            }
            else if (block == Blocks.leaves) {
                switch (metadata & 0x3) {
                    case 0: {
                        colorizer = CustomColors.COLORIZER_FOLIAGE;
                        break;
                    }
                    case 1: {
                        colorizer = CustomColors.COLORIZER_FOLIAGE_PINE;
                        break;
                    }
                    case 2: {
                        colorizer = CustomColors.COLORIZER_FOLIAGE_BIRCH;
                        break;
                    }
                    default: {
                        colorizer = CustomColors.COLORIZER_FOLIAGE;
                        break;
                    }
                }
            }
            else if (block == Blocks.leaves2) {
                colorizer = CustomColors.COLORIZER_FOLIAGE;
            }
            else {
                if (block != Blocks.vine) {
                    return -1;
                }
                colorizer = CustomColors.COLORIZER_FOLIAGE;
            }
        }
        else {
            colorizer = CustomColors.COLORIZER_GRASS;
        }
        return (Config.isSmoothBiomes() && !colorizer.isColorConstant()) ? getSmoothColorMultiplier(blockAccess, blockPos, colorizer, renderEnv.getColorizerBlockPosM()) : colorizer.getColor(blockAccess, blockPos);
    }
    
    protected static BiomeGenBase getColorBiome(final IBlockAccess blockAccess, final BlockPos blockPos) {
        BiomeGenBase biomeGenBase = blockAccess.getBiomeGenForCoords(blockPos);
        if (biomeGenBase == BiomeGenBase.swampland && !Config.isSwampColors()) {
            biomeGenBase = BiomeGenBase.plains;
        }
        return biomeGenBase;
    }
    
    private static CustomColormap getBlockColormap(final IBlockState blockState) {
        if (CustomColors.blockColormaps == null) {
            return null;
        }
        if (!(blockState instanceof BlockStateBase)) {
            return null;
        }
        final BlockStateBase blockStateBase = (BlockStateBase)blockState;
        final int blockId = blockStateBase.getBlockId();
        if (blockId < 0 || blockId >= CustomColors.blockColormaps.length) {
            return null;
        }
        final CustomColormap[] array = CustomColors.blockColormaps[blockId];
        if (array == null) {
            return null;
        }
        for (int i = 0; i < array.length; ++i) {
            final CustomColormap customColormap = array[i];
            if (customColormap.matchesBlock(blockStateBase)) {
                return customColormap;
            }
        }
        return null;
    }
    
    private static int getSmoothColorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final IColorizer colorizer, final BlockPosM blockPosM) {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        for (int i = x - 1; i <= x + 1; ++i) {
            for (int j = z - 1; j <= z + 1; ++j) {
                blockPosM.setXyz(i, y, j);
                final int color = colorizer.getColor(blockAccess, blockPosM);
                n += (color >> 16 & 0xFF);
                n2 += (color >> 8 & 0xFF);
                n3 += (color & 0xFF);
            }
        }
        return n / 9 << 16 | n2 / 9 << 8 | n3 / 9;
    }
    
    public static int getFluidColor(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final RenderEnv renderEnv) {
        final Block block = blockState.getBlock();
        IColorizer colorizer = getBlockColormap(blockState);
        if (colorizer == null && block.getMaterial() == Material.water) {
            colorizer = CustomColors.COLORIZER_WATER;
        }
        return (colorizer == null) ? block.colorMultiplier(blockAccess, blockPos) : ((Config.isSmoothBiomes() && !((CustomColormap)colorizer).isColorConstant()) ? getSmoothColorMultiplier(blockAccess, blockPos, colorizer, renderEnv.getColorizerBlockPosM()) : ((CustomColormap)colorizer).getColor(blockAccess, blockPos));
    }
    
    public static void updatePortalFX(final EntityFX entityFX) {
        if (CustomColors.particlePortalColor >= 0) {
            final int particlePortalColor = CustomColors.particlePortalColor;
            entityFX.setRBGColorF((particlePortalColor >> 16 & 0xFF) / 255.0f, (particlePortalColor >> 8 & 0xFF) / 255.0f, (particlePortalColor & 0xFF) / 255.0f);
        }
    }
    
    public static void updateMyceliumFX(final EntityFX entityFX) {
        if (CustomColors.myceliumParticleColors != null) {
            final int colorRandom = CustomColors.myceliumParticleColors.getColorRandom();
            entityFX.setRBGColorF((colorRandom >> 16 & 0xFF) / 255.0f, (colorRandom >> 8 & 0xFF) / 255.0f, (colorRandom & 0xFF) / 255.0f);
        }
    }
    
    private static int getRedstoneColor(final IBlockState blockState) {
        if (CustomColors.redstoneColors == null) {
            return -1;
        }
        return CustomColors.redstoneColors.getColor(getRedstoneLevel(blockState, 15));
    }
    
    public static void updateReddustFX(final EntityFX entityFX, final IBlockAccess blockAccess, final double n, final double n2, final double n3) {
        if (CustomColors.redstoneColors != null) {
            final int color = CustomColors.redstoneColors.getColor(getRedstoneLevel(blockAccess.getBlockState(new BlockPos(n, n2, n3)), 15));
            entityFX.setRBGColorF((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f);
        }
    }
    
    private static int getRedstoneLevel(final IBlockState blockState, final int n) {
        if (!(blockState.getBlock() instanceof BlockRedstoneWire)) {
            return n;
        }
        final Comparable value = blockState.getValue(BlockRedstoneWire.POWER);
        if (!(value instanceof Integer)) {
            return n;
        }
        return (int)value;
    }
    
    public static int getXpOrbColor(final float n) {
        if (CustomColors.xpOrbColors == null) {
            return -1;
        }
        return CustomColors.xpOrbColors.getColor((int)((MathHelper.sin(n) + 1.0f) * (CustomColors.xpOrbColors.getLength() - 1) / 2.0));
    }
    
    public static void updateWaterFX(final EntityFX entityFX, final IBlockAccess blockAccess, final double n, final double n2, final double n3) {
        if (CustomColors.waterColors != null || CustomColors.blockColormaps != null) {
            final BlockPos blockPos = new BlockPos(n, n2, n3);
            final int fluidColor = getFluidColor(blockAccess, CustomColors.BLOCK_STATE_WATER, blockPos, RenderEnv.getInstance(blockAccess, CustomColors.BLOCK_STATE_WATER, blockPos));
            final int n4 = fluidColor >> 16 & 0xFF;
            final int n5 = fluidColor >> 8 & 0xFF;
            final int n6 = fluidColor & 0xFF;
            float n7 = n4 / 255.0f;
            float n8 = n5 / 255.0f;
            float n9 = n6 / 255.0f;
            if (CustomColors.particleWaterColor >= 0) {
                final int n10 = CustomColors.particleWaterColor >> 16 & 0xFF;
                final int n11 = CustomColors.particleWaterColor >> 8 & 0xFF;
                final int n12 = CustomColors.particleWaterColor & 0xFF;
                n7 *= n10 / 255.0f;
                n8 *= n11 / 255.0f;
                n9 *= n12 / 255.0f;
            }
            entityFX.setRBGColorF(n7, n8, n9);
        }
    }
    
    private static int getLilypadColorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return (CustomColors.lilyPadColor < 0) ? Blocks.waterlily.colorMultiplier(blockAccess, blockPos) : CustomColors.lilyPadColor;
    }
    
    private static Vec3 getFogColorNether(final Vec3 vec3) {
        return (CustomColors.fogColorNether == null) ? vec3 : CustomColors.fogColorNether;
    }
    
    private static Vec3 getFogColorEnd(final Vec3 vec3) {
        return (CustomColors.fogColorEnd == null) ? vec3 : CustomColors.fogColorEnd;
    }
    
    private static Vec3 getSkyColorEnd(final Vec3 vec3) {
        return (CustomColors.skyColorEnd == null) ? vec3 : CustomColors.skyColorEnd;
    }
    
    public static Vec3 getSkyColor(final Vec3 vec3, final IBlockAccess blockAccess, final double n, final double n2, final double n3) {
        if (CustomColors.skyColors == null) {
            return vec3;
        }
        final int colorSmooth = CustomColors.skyColors.getColorSmooth(blockAccess, n, n2, n3, 3);
        return CustomColors.skyColorFader.getColor((colorSmooth >> 16 & 0xFF) / 255.0f * ((float)vec3.xCoord / 0.5f), (colorSmooth >> 8 & 0xFF) / 255.0f * ((float)vec3.yCoord / 0.66275f), (colorSmooth & 0xFF) / 255.0f * (float)vec3.zCoord);
    }
    
    private static Vec3 getFogColor(final Vec3 vec3, final IBlockAccess blockAccess, final double n, final double n2, final double n3) {
        if (CustomColors.fogColors == null) {
            return vec3;
        }
        final int colorSmooth = CustomColors.fogColors.getColorSmooth(blockAccess, n, n2, n3, 3);
        return CustomColors.fogColorFader.getColor((colorSmooth >> 16 & 0xFF) / 255.0f * ((float)vec3.xCoord / 0.753f), (colorSmooth >> 8 & 0xFF) / 255.0f * ((float)vec3.yCoord / 0.8471f), (colorSmooth & 0xFF) / 255.0f * (float)vec3.zCoord);
    }
    
    public static Vec3 getUnderwaterColor(final IBlockAccess blockAccess, final double n, final double n2, final double n3) {
        if (CustomColors.underwaterColors == null) {
            return null;
        }
        final int colorSmooth = CustomColors.underwaterColors.getColorSmooth(blockAccess, n, n2, n3, 3);
        return CustomColors.underwaterColorFader.getColor((colorSmooth >> 16 & 0xFF) / 255.0f, (colorSmooth >> 8 & 0xFF) / 255.0f, (colorSmooth & 0xFF) / 255.0f);
    }
    
    private static int getStemColorMultiplier(final Block block, final IBlockAccess blockAccess, final BlockPos blockPos, final RenderEnv renderEnv) {
        CustomColormap customColormap = CustomColors.stemColors;
        if (block == Blocks.pumpkin_stem && CustomColors.stemPumpkinColors != null) {
            customColormap = CustomColors.stemPumpkinColors;
        }
        if (block == Blocks.melon_stem && CustomColors.stemMelonColors != null) {
            customColormap = CustomColors.stemMelonColors;
        }
        if (customColormap == null) {
            return -1;
        }
        return customColormap.getColor(renderEnv.getMetadata());
    }
    
    public static boolean updateLightmap(final World world, final float n, final int[] array, final boolean b) {
        if (world == null) {
            return false;
        }
        if (CustomColors.lightMapsColorsRgb == null) {
            return false;
        }
        final int dimensionId = world.provider.getDimensionId();
        final int n2 = dimensionId - CustomColors.lightmapMinDimensionId;
        if (n2 < 0 || n2 >= CustomColors.lightMapsColorsRgb.length) {
            return false;
        }
        final CustomColormap customColormap = CustomColors.lightMapsColorsRgb[n2];
        if (customColormap == null) {
            return false;
        }
        final int height = customColormap.getHeight();
        if (b && height < 64) {
            return false;
        }
        final int width = customColormap.getWidth();
        if (width < 16) {
            warn(CustomColors.lIlIIlIIllIIIllI[83] + width + CustomColors.lIlIIlIIllIIIllI[84] + dimensionId);
            CustomColors.lightMapsColorsRgb[n2] = null;
            return false;
        }
        int n3 = 0;
        if (b) {
            n3 = width * 16 * 2;
        }
        float n4 = 1.1666666f * (world.getSunBrightness(1.0f) - 0.2f);
        if (world.func_175658_ac() > 0) {
            n4 = 1.0f;
        }
        final float n5 = Config.limitTo1(n4) * (width - 1);
        final float n6 = Config.limitTo1(n + 0.5f) * (width - 1);
        final float limitTo1 = Config.limitTo1(Config.getGameSettings().gammaSetting);
        final boolean b2 = limitTo1 > 1.0E-4f;
        final float[][] colorsRgb = customColormap.getColorsRgb();
        getLightMapColumn(colorsRgb, n5, n3, width, CustomColors.sunRgbs);
        getLightMapColumn(colorsRgb, n6, n3 + 16 * width, width, CustomColors.torchRgbs);
        final float[] array2 = new float[3];
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                for (int k = 0; k < 3; ++k) {
                    float limitTo2 = Config.limitTo1(CustomColors.sunRgbs[i][k] + CustomColors.torchRgbs[j][k]);
                    if (b2) {
                        final float n7 = 1.0f - limitTo2;
                        limitTo2 = limitTo1 * (1.0f - n7 * n7 * n7 * n7) + (1.0f - limitTo1) * limitTo2;
                    }
                    array2[k] = limitTo2;
                }
                array[i * 16 + j] = (0xFF000000 | (int)(array2[0] * 255.0f) << 16 | (int)(array2[1] * 255.0f) << 8 | (int)(array2[2] * 255.0f));
            }
        }
        return true;
    }
    
    private static void getLightMapColumn(final float[][] array, final float n, final int n2, final int n3, final float[][] array2) {
        final int n4 = (int)Math.floor(n);
        final int n5 = (int)Math.ceil(n);
        if (n4 == n5) {
            for (int i = 0; i < 16; ++i) {
                final float[] array3 = array[n2 + i * n3 + n4];
                final float[] array4 = array2[i];
                for (int j = 0; j < 3; ++j) {
                    array4[j] = array3[j];
                }
            }
        }
        else {
            final float n6 = 1.0f - (n - n4);
            final float n7 = 1.0f - (n5 - n);
            for (int k = 0; k < 16; ++k) {
                final float[] array5 = array[n2 + k * n3 + n4];
                final float[] array6 = array[n2 + k * n3 + n5];
                final float[] array7 = array2[k];
                for (int l = 0; l < 3; ++l) {
                    array7[l] = array5[l] * n6 + array6[l] * n7;
                }
            }
        }
    }
    
    public static Vec3 getWorldFogColor(Vec3 vec3, final WorldClient worldClient, final Entity entity, final float n) {
        switch (worldClient.provider.getDimensionId()) {
            case -1: {
                vec3 = getFogColorNether(vec3);
                break;
            }
            case 0: {
                Minecraft.getMinecraft();
                vec3 = getFogColor(vec3, Minecraft.theWorld, entity.posX, entity.posY + 1.0, entity.posZ);
                break;
            }
            case 1: {
                vec3 = getFogColorEnd(vec3);
                break;
            }
        }
        return vec3;
    }
    
    public static Vec3 getWorldSkyColor(Vec3 vec3, final WorldClient worldClient, final Entity entity, final float n) {
        switch (worldClient.provider.getDimensionId()) {
            case 0: {
                Minecraft.getMinecraft();
                vec3 = getSkyColor(vec3, Minecraft.theWorld, entity.posX, entity.posY + 1.0, entity.posZ);
                break;
            }
            case 1: {
                vec3 = getSkyColorEnd(vec3);
                break;
            }
        }
        return vec3;
    }
    
    private static int[] readSpawnEggColors(final Properties properties, final String s, final String s2, final String s3) {
        final ArrayList<Integer> list = new ArrayList<Integer>();
        final Set<String> keySet = ((Hashtable<String, V>)properties).keySet();
        int n = 0;
        for (final String s4 : keySet) {
            final String property = properties.getProperty(s4);
            if (s4.startsWith(s2)) {
                final int entityId = getEntityId(StrUtils.removePrefix(s4, s2));
                final int color = parseColor(property);
                if (entityId >= 0 && color >= 0) {
                    while (list.size() <= entityId) {
                        list.add(-1);
                    }
                    list.set(entityId, color);
                    ++n;
                }
                else {
                    warn(CustomColors.lIlIIlIIllIIIllI[85] + s4 + CustomColors.lIlIIlIIllIIIllI[86] + property);
                }
            }
        }
        if (n <= 0) {
            return null;
        }
        dbg(String.valueOf(s3) + CustomColors.lIlIIlIIllIIIllI[87] + n);
        final int[] array = new int[list.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = list.get(i);
        }
        return array;
    }
    
    private static int getSpawnEggColor(final ItemMonsterPlacer itemMonsterPlacer, final ItemStack itemStack, final int n, final int n2) {
        final int metadata = itemStack.getMetadata();
        final int[] array = (n == 0) ? CustomColors.spawnEggPrimaryColors : CustomColors.spawnEggSecondaryColors;
        if (array == null) {
            return n2;
        }
        if (metadata >= 0 && metadata < array.length) {
            final int n3 = array[metadata];
            return (n3 < 0) ? n2 : n3;
        }
        return n2;
    }
    
    public static int getColorFromItemStack(final ItemStack itemStack, final int n, final int n2) {
        if (itemStack == null) {
            return n2;
        }
        final Item item = itemStack.getItem();
        return (item == null) ? n2 : ((item instanceof ItemMonsterPlacer) ? getSpawnEggColor((ItemMonsterPlacer)item, itemStack, n, n2) : n2);
    }
    
    private static float[][] readDyeColors(final Properties properties, final String s, final String s2, final String s3) {
        final EnumDyeColor[] values = EnumDyeColor.values();
        final HashMap<String, EnumDyeColor> hashMap = new HashMap<String, EnumDyeColor>();
        for (int i = 0; i < values.length; ++i) {
            final EnumDyeColor enumDyeColor = values[i];
            hashMap.put(enumDyeColor.getName(), enumDyeColor);
        }
        final float[][] array = new float[values.length][];
        int n = 0;
        for (final String s4 : ((Hashtable<String, V>)properties).keySet()) {
            final String property = properties.getProperty(s4);
            if (s4.startsWith(s2)) {
                String removePrefix = StrUtils.removePrefix(s4, s2);
                if (removePrefix.equals(CustomColors.lIlIIlIIllIIIllI[88])) {
                    removePrefix = CustomColors.lIlIIlIIllIIIllI[89];
                }
                final EnumDyeColor enumDyeColor2 = hashMap.get(removePrefix);
                final int color = parseColor(property);
                if (enumDyeColor2 != null && color >= 0) {
                    array[enumDyeColor2.ordinal()] = new float[] { (color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f };
                    ++n;
                }
                else {
                    warn(CustomColors.lIlIIlIIllIIIllI[90] + s4 + CustomColors.lIlIIlIIllIIIllI[91] + property);
                }
            }
        }
        if (n <= 0) {
            return null;
        }
        dbg(String.valueOf(s3) + CustomColors.lIlIIlIIllIIIllI[92] + n);
        return array;
    }
    
    private static float[] getDyeColors(final EnumDyeColor enumDyeColor, final float[][] array, final float[] array2) {
        if (array == null) {
            return array2;
        }
        if (enumDyeColor == null) {
            return array2;
        }
        final float[] array3 = array[enumDyeColor.ordinal()];
        return (array3 == null) ? array2 : array3;
    }
    
    public static float[] getWolfCollarColors(final EnumDyeColor enumDyeColor, final float[] array) {
        return getDyeColors(enumDyeColor, CustomColors.wolfCollarColors, array);
    }
    
    public static float[] getSheepColors(final EnumDyeColor enumDyeColor, final float[] array) {
        return getDyeColors(enumDyeColor, CustomColors.sheepColors, array);
    }
    
    private static int[] readTextColors(final Properties properties, final String s, final String s2, final String s3) {
        final int[] array = new int[32];
        Arrays.fill(array, -1);
        int n = 0;
        for (final String s4 : ((Hashtable<String, V>)properties).keySet()) {
            final String property = properties.getProperty(s4);
            if (s4.startsWith(s2)) {
                final int int1 = Config.parseInt(StrUtils.removePrefix(s4, s2), -1);
                final int color = parseColor(property);
                if (int1 >= 0 && int1 < array.length && color >= 0) {
                    array[int1] = color;
                    ++n;
                }
                else {
                    warn(CustomColors.lIlIIlIIllIIIllI[93] + s4 + CustomColors.lIlIIlIIllIIIllI[94] + property);
                }
            }
        }
        if (n <= 0) {
            return null;
        }
        dbg(String.valueOf(s3) + CustomColors.lIlIIlIIllIIIllI[95] + n);
        return array;
    }
    
    public static int getTextColor(final int n, final int n2) {
        if (CustomColors.textColors == null) {
            return n2;
        }
        if (n >= 0 && n < CustomColors.textColors.length) {
            final int n3 = CustomColors.textColors[n];
            return (n3 < 0) ? n2 : n3;
        }
        return n2;
    }
    
    private static int[] readMapColors(final Properties properties, final String s, final String s2, final String s3) {
        final int[] array = new int[MapColor.mapColorArray.length];
        Arrays.fill(array, -1);
        int n = 0;
        for (final String s4 : ((Hashtable<String, V>)properties).keySet()) {
            final String property = properties.getProperty(s4);
            if (s4.startsWith(s2)) {
                final int mapColorIndex = getMapColorIndex(StrUtils.removePrefix(s4, s2));
                final int color = parseColor(property);
                if (mapColorIndex >= 0 && mapColorIndex < array.length && color >= 0) {
                    array[mapColorIndex] = color;
                    ++n;
                }
                else {
                    warn(CustomColors.lIlIIlIIllIIIllI[96] + s4 + CustomColors.lIlIIlIIllIIIllI[97] + property);
                }
            }
        }
        if (n <= 0) {
            return null;
        }
        dbg(String.valueOf(s3) + CustomColors.lIlIIlIIllIIIllI[98] + n);
        return array;
    }
    
    private static int[] readPotionColors(final Properties properties, final String s, final String s2, final String s3) {
        final int[] array = new int[Potion.potionTypes.length];
        Arrays.fill(array, -1);
        int n = 0;
        for (final String s4 : ((Hashtable<String, V>)properties).keySet()) {
            final String property = properties.getProperty(s4);
            if (s4.startsWith(s2)) {
                final int potionId = getPotionId(s4);
                final int color = parseColor(property);
                if (potionId >= 0 && potionId < array.length && color >= 0) {
                    array[potionId] = color;
                    ++n;
                }
                else {
                    warn(CustomColors.lIlIIlIIllIIIllI[99] + s4 + CustomColors.lIlIIlIIllIIIllI[100] + property);
                }
            }
        }
        if (n <= 0) {
            return null;
        }
        dbg(String.valueOf(s3) + CustomColors.lIlIIlIIllIIIllI[101] + n);
        return array;
    }
    
    private static int getPotionId(final String s) {
        if (s.equals(CustomColors.lIlIIlIIllIIIllI[102])) {
            return 0;
        }
        final Potion[] potionTypes = Potion.potionTypes;
        for (int i = 0; i < potionTypes.length; ++i) {
            final Potion potion = potionTypes[i];
            if (potion != null && potion.getName().equals(s)) {
                return potion.getId();
            }
        }
        return -1;
    }
    
    public static int getPotionColor(final int n, final int n2) {
        if (CustomColors.potionColors == null) {
            return n2;
        }
        if (n >= 0 && n < CustomColors.potionColors.length) {
            final int n3 = CustomColors.potionColors[n];
            return (n3 < 0) ? n2 : n3;
        }
        return n2;
    }
    
    private static int getMapColorIndex(final String s) {
        return (s == null) ? -1 : (s.equals(CustomColors.lIlIIlIIllIIIllI[103]) ? MapColor.airColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[104]) ? MapColor.grassColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[105]) ? MapColor.sandColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[106]) ? MapColor.clothColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[107]) ? MapColor.tntColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[108]) ? MapColor.iceColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[109]) ? MapColor.ironColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[110]) ? MapColor.foliageColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[111]) ? MapColor.snowColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[112]) ? MapColor.clayColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[113]) ? MapColor.dirtColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[114]) ? MapColor.stoneColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[115]) ? MapColor.waterColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[116]) ? MapColor.woodColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[117]) ? MapColor.quartzColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[118]) ? MapColor.adobeColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[119]) ? MapColor.magentaColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[120]) ? MapColor.lightBlueColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[121]) ? MapColor.lightBlueColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[122]) ? MapColor.yellowColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[123]) ? MapColor.limeColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[124]) ? MapColor.pinkColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[125]) ? MapColor.grayColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[126]) ? MapColor.silverColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[127]) ? MapColor.cyanColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[128]) ? MapColor.purpleColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[129]) ? MapColor.blueColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[130]) ? MapColor.brownColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[131]) ? MapColor.greenColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[132]) ? MapColor.redColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[133]) ? MapColor.blackColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[134]) ? MapColor.goldColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[135]) ? MapColor.diamondColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[136]) ? MapColor.lapisColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[137]) ? MapColor.emeraldColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[138]) ? MapColor.obsidianColor.colorIndex : (s.equals(CustomColors.lIlIIlIIllIIIllI[139]) ? MapColor.netherrackColor.colorIndex : -1)))))))))))))))))))))))))))))))))))));
    }
    
    private static int[] getMapColors() {
        final MapColor[] mapColorArray = MapColor.mapColorArray;
        final int[] array = new int[mapColorArray.length];
        Arrays.fill(array, -1);
        for (int n = 0; n < mapColorArray.length && n < array.length; ++n) {
            final MapColor mapColor = mapColorArray[n];
            if (mapColor != null) {
                array[n] = mapColor.colorValue;
            }
        }
        return array;
    }
    
    private static void setMapColors(final int[] array) {
        if (array != null) {
            final MapColor[] mapColorArray = MapColor.mapColorArray;
            for (int n = 0; n < mapColorArray.length && n < array.length; ++n) {
                final MapColor mapColor = mapColorArray[n];
                if (mapColor != null) {
                    final int colorValue = array[n];
                    if (colorValue >= 0) {
                        mapColor.colorValue = colorValue;
                    }
                }
            }
        }
    }
    
    private static int getEntityId(final String s) {
        if (s == null) {
            return -1;
        }
        final int func_180122_a = EntityList.func_180122_a(s);
        if (func_180122_a < 0) {
            return -1;
        }
        return Config.equals(s, EntityList.getStringFromID(func_180122_a)) ? func_180122_a : -1;
    }
    
    private static void dbg(final String s) {
        Config.dbg(CustomColors.lIlIIlIIllIIIllI[140] + s);
    }
    
    private static void warn(final String s) {
        Config.warn(CustomColors.lIlIIlIIllIIIllI[141] + s);
    }
    
    public static int getExpBarTextColor(final int n) {
        return (CustomColors.expBarTextColor < 0) ? n : CustomColors.expBarTextColor;
    }
    
    public static int getBossTextColor(final int n) {
        return (CustomColors.bossTextColor < 0) ? n : CustomColors.bossTextColor;
    }
    
    public static int getSignTextColor(final int n) {
        return (CustomColors.signTextColor < 0) ? n : CustomColors.signTextColor;
    }
    
    static CustomColormap access$0() {
        return CustomColors.swampGrassColors;
    }
    
    static CustomColormap access$1() {
        return CustomColors.swampFoliageColors;
    }
    
    static CustomColormap access$2() {
        return CustomColors.foliagePineColors;
    }
    
    static CustomColormap access$3() {
        return CustomColors.foliageBirchColors;
    }
    
    static CustomColormap access$4() {
        return CustomColors.waterColors;
    }
    
    private static void llllIIlIllIllIll() {
        (lIlIIlIIllIIIllI = new String[142])[0] = llllIIlIlIlllIll(CustomColors.lIlIIlIIllIllIIl[0], CustomColors.lIlIIlIIllIllIIl[1]);
        CustomColors.lIlIIlIIllIIIllI[1] = llllIIlIlIllllII(CustomColors.lIlIIlIIllIllIIl[2], CustomColors.lIlIIlIIllIllIIl[3]);
        CustomColors.lIlIIlIIllIIIllI[2] = llllIIlIlIllllIl(CustomColors.lIlIIlIIllIllIIl[4], CustomColors.lIlIIlIIllIllIIl[5]);
        CustomColors.lIlIIlIIllIIIllI[3] = llllIIlIlIllllII(CustomColors.lIlIIlIIllIllIIl[6], CustomColors.lIlIIlIIllIllIIl[7]);
        CustomColors.lIlIIlIIllIIIllI[4] = llllIIlIlIllllII(CustomColors.lIlIIlIIllIllIIl[8], CustomColors.lIlIIlIIllIllIIl[9]);
        CustomColors.lIlIIlIIllIIIllI[5] = llllIIlIlIllllII(CustomColors.lIlIIlIIllIllIIl[10], CustomColors.lIlIIlIIllIllIIl[11]);
        CustomColors.lIlIIlIIllIIIllI[6] = llllIIlIlIlllllI(CustomColors.lIlIIlIIllIllIIl[12], CustomColors.lIlIIlIIllIllIIl[13]);
        CustomColors.lIlIIlIIllIIIllI[7] = llllIIlIlIlllllI(CustomColors.lIlIIlIIllIllIIl[14], CustomColors.lIlIIlIIllIllIIl[15]);
        CustomColors.lIlIIlIIllIIIllI[8] = llllIIlIlIllllII(CustomColors.lIlIIlIIllIllIIl[16], CustomColors.lIlIIlIIllIllIIl[17]);
        CustomColors.lIlIIlIIllIIIllI[9] = llllIIlIlIlllllI(CustomColors.lIlIIlIIllIllIIl[18], CustomColors.lIlIIlIIllIllIIl[19]);
        CustomColors.lIlIIlIIllIIIllI[10] = llllIIlIlIllllII(CustomColors.lIlIIlIIllIllIIl[20], CustomColors.lIlIIlIIllIllIIl[21]);
        CustomColors.lIlIIlIIllIIIllI[11] = llllIIlIlIllllII(CustomColors.lIlIIlIIllIllIIl[22], CustomColors.lIlIIlIIllIllIIl[23]);
        CustomColors.lIlIIlIIllIIIllI[12] = llllIIlIlIlllllI(CustomColors.lIlIIlIIllIllIIl[24], CustomColors.lIlIIlIIllIllIIl[25]);
        CustomColors.lIlIIlIIllIIIllI[13] = llllIIlIlIlllIll(CustomColors.lIlIIlIIllIllIIl[26], CustomColors.lIlIIlIIllIllIIl[27]);
        CustomColors.lIlIIlIIllIIIllI[14] = llllIIlIlIlllIll("X9oN7iX2VSEq0NthkeZYpg==", "Psrew");
        CustomColors.lIlIIlIIllIIIllI[15] = llllIIlIlIlllIll("AORX9bHzry9PEdk/0MnqSg==", "OLZxP");
        CustomColors.lIlIIlIIllIIIllI[16] = llllIIlIlIlllIll("aIk8xRwpwnwRya6tgUfChBLvpunWSqbt", "AgMvP");
        CustomColors.lIlIIlIIllIIIllI[17] = llllIIlIlIllllII("3IfAn7Jt5xMwrF6NJqftTw==", "FcUcu");
        CustomColors.lIlIIlIIllIIIllI[18] = llllIIlIlIlllIll("OW5BZP8uZO6eZ+wNwqe4SpcmMulDeB9x", "EtlRm");
        CustomColors.lIlIIlIIllIIIllI[19] = llllIIlIlIllllII("KvQcZypDIWSl7UQLE4ktkQ==", "xfUrJ");
        CustomColors.lIlIIlIIllIIIllI[20] = llllIIlIlIlllllI("WqOWxrcYMJ3mN8CEUB6B1w==", "bIEqR");
        CustomColors.lIlIIlIIllIIIllI[21] = llllIIlIlIllllII("py30ZZD7TuxL8kRyOAqJQA==", "cpGGW");
        CustomColors.lIlIIlIIllIIIllI[22] = llllIIlIlIlllllI("yZh/uNW3NK8997SIRC+olg==", "KTtXN");
        CustomColors.lIlIIlIIllIIIllI[23] = llllIIlIlIllllII("LdoCqMJZ36Jqw5RSM08RQQ==", "KUmJJ");
        CustomColors.lIlIIlIIllIIIllI[24] = llllIIlIlIlllllI("JNhPptnnrAMECvrPxg5NABYax82x5s28", "VODkY");
        CustomColors.lIlIIlIIllIIIllI[25] = llllIIlIlIllllIl("Ay4FDgoHIgsbBxwjDwgKCzQJBwkceRYFAQ==", "nWfkf");
        CustomColors.lIlIIlIIllIIIllI[26] = llllIIlIlIllllII("TlxZ83wbxsnruB8CYk0MS2XR2p+syt6j+7sCAHqlwh0=", "RRqpQ");
        CustomColors.lIlIIlIIllIIIllI[27] = llllIIlIlIllllIl("IDMJMBsuaQ==", "CFzDt");
        CustomColors.lIlIIlIIllIIIllI[28] = llllIIlIlIllllIl("KCUDMT85Zg==", "JIlRT");
        CustomColors.lIlIIlIIllIIIllI[29] = llllIIlIlIllllIl("FAQaADsaDw8TYBUODQk7FAYaTjgWFQYF", "ygjaO");
        CustomColors.lIlIIlIIllIIIllI[30] = llllIIlIlIlllllI("B3unJazhhXo=", "TVBLB");
        CustomColors.lIlIIlIIllIIIllI[31] = llllIIlIlIllllII("tvi2qm2KTpOmil6/jI/dDcWiyj+oQPcX4YQo4PPIvB4=", "CMlAR");
        CustomColors.lIlIIlIIllIIIllI[32] = llllIIlIlIlllllI("qI9OXJmzBHLIyR1IdvrMCg==", "safUY");
        CustomColors.lIlIIlIIllIIIllI[33] = llllIIlIlIllllIl("MzcTBBQTPUUJER0xEQgZCnkSDBwOMV9F", "zYeex");
        CustomColors.lIlIIlIIllIIIllI[34] = llllIIlIlIlllIll("z0qlbN5CaRlGps/iK7AA0Q==", "nvXwT");
        CustomColors.lIlIIlIIllIIIllI[35] = llllIIlIlIllllII("yGLTzlyfIHRofX+/7Kd40Q==", "czIua");
        CustomColors.lIlIIlIIllIIIllI[36] = llllIIlIlIlllllI("g0s++DHUXWaIEfVI5vr5Nw==", "paKvA");
        CustomColors.lIlIIlIIllIIIllI[37] = llllIIlIlIlllIll("bze6HyXf+4e2+29MCh5Jtg==", "DPIOg");
        CustomColors.lIlIIlIIllIIIllI[38] = llllIIlIlIlllIll("U6r9Kfn5aNvttTfdac/OlQ==", "MGJTy");
        CustomColors.lIlIIlIIllIIIllI[39] = llllIIlIlIllllIl("IQoNHxosBw==", "Mcafj");
        CustomColors.lIlIIlIIllIIIllI[40] = llllIIlIlIlllllI("W5mDTI4JY9Q3Tt/bldVwTA==", "ExQta");
        CustomColors.lIlIIlIIllIIIllI[41] = llllIIlIlIllllII("1Mcis/bDX5Vn//OU5jKUFw==", "SKFOh");
        CustomColors.lIlIIlIIllIIIllI[42] = llllIIlIlIlllllI("u3BNlIWDIPeqhwL3IhJlFg==", "vjUKd");
        CustomColors.lIlIIlIIllIIIllI[43] = llllIIlIlIlllllI("zRGYrEqxyL3DygpjVRBQPQ==", "kqnks");
        CustomColors.lIlIIlIIllIIIllI[44] = llllIIlIlIllllII("xZq3Z8or03w3R4ugj38o6A==", "lbuAm");
        CustomColors.lIlIIlIIllIIIllI[45] = llllIIlIlIlllIll("Tnfr6ZYBYUk=", "ppUUP");
        CustomColors.lIlIIlIIllIIIllI[46] = llllIIlIlIlllIll("Xa3/trx7b15SI6IL1JhLOw==", "opHqq");
        CustomColors.lIlIIlIIllIIIllI[47] = llllIIlIlIllllIl("Oxc1LghIAjM+RhsPMTUK", "hgTYf");
        CustomColors.lIlIIlIIllIIIllI[48] = llllIIlIlIlllllI("t1sKDprwEyjcYshxeICA3g==", "qkijU");
        CustomColors.lIlIIlIIllIIIllI[49] = llllIIlIlIllllIl("GSMoLwRqNi4/SjkjJiw=", "JSIXj");
        CustomColors.lIlIIlIIllIIIllI[50] = llllIIlIlIlllllI("4stOm6tKCaE=", "oEXrm");
        CustomColors.lIlIIlIIllIIIllI[51] = llllIIlIlIllllIl("BT8lN2YxPyU9JyA=", "RPIQF");
        CustomColors.lIlIIlIIllIIIllI[52] = llllIIlIlIllllIl("PS8rIDRg", "NGNED");
        CustomColors.lIlIIlIIllIIIllI[53] = llllIIlIlIllllII("eOQOgxO52aOaJlRRWB1Xuw==", "mxedH");
        CustomColors.lIlIIlIIllIIIllI[54] = llllIIlIlIllllIl("IyAUFmY0KggHZg==", "WElbH");
        CustomColors.lIlIIlIIllIIIllI[55] = llllIIlIlIlllllI("7qjV/BwlH6Y=", "dFEXO");
        CustomColors.lIlIIlIIllIIIllI[56] = llllIIlIlIlllllI("0wsCc1oB4WE=", "HLILW");
        CustomColors.lIlIIlIIllIIIllI[57] = llllIIlIlIllllII("Zc5lEKWOmFaBZqtj6rM25g==", "LcOBk");
        CustomColors.lIlIIlIIllIIIllI[58] = llllIIlIlIlllIll("nz+lSZtvpmU=", "vdNim");
        CustomColors.lIlIIlIIllIIIllI[59] = llllIIlIlIlllIll("rftCbelipyQ=", "RaRuG");
        CustomColors.lIlIIlIIllIIIllI[60] = llllIIlIlIlllllI("lJQFuHicZAOfKLSerosPhQ==", "mwkeh");
        CustomColors.lIlIIlIIllIIIllI[61] = llllIIlIlIlllIll("kKBdloEnTnjsEP8SX47iyw==", "hMvRH");
        CustomColors.lIlIIlIIllIIIllI[62] = llllIIlIlIlllllI("GSxwitFsDdM=", "YIBsK");
        CustomColors.lIlIIlIIllIIIllI[63] = llllIIlIlIlllllI("pYDKQQRENnIW6pttHZupmKx/wzaQOAnM", "AiUtN");
        CustomColors.lIlIIlIIllIIIllI[64] = llllIIlIlIllllII("OZNqMvWSuf7WGjHv8sknbw==", "nnaTB");
        CustomColors.lIlIIlIIllIIIllI[65] = llllIIlIlIllllII("+VnXOqMcnRO3Fvyam8lHyJICQzpluuNNG93cTCjCakY=", "PldVs");
        CustomColors.lIlIIlIIllIIIllI[66] = llllIIlIlIllllIl("QSIjJDMKICUiJhw=", "oRQKC");
        CustomColors.lIlIIlIIllIIIllI[67] = llllIIlIlIlllIll("/+mahoTE2tX+wPnkcHkKGlOl6HSc7cR8", "dJPal");
        CustomColors.lIlIIlIIllIIIllI[68] = llllIIlIlIllllII("FlNHjuAa902hnZJ4LrFC+A==", "zsuTw");
        CustomColors.lIlIIlIIllIIIllI[69] = llllIIlIlIllllII("q6ykqybGsl5KR0WHrSVVe+Vu/AE7Ys+pjQuVnNbXf8g=", "xxrsS");
        CustomColors.lIlIIlIIllIIIllI[70] = llllIIlIlIlllllI("JRMMTtybwBQfj5Kcj6rqNkdf4R5/vV0q", "aKBAY");
        CustomColors.lIlIIlIIllIIIllI[71] = llllIIlIlIllllIl("KB41FSsIFGMWKw4TKFQOJUpj", "apCtG");
        CustomColors.lIlIIlIIllIIIllI[72] = llllIIlIlIllllIl("BjpuHxs8NiZSGCQ6LRkJcnU=", "HUNrz");
        CustomColors.lIlIIlIIllIIIllI[73] = llllIIlIlIlllIll("UvBsnzqRd4H7rUo3uAcZXw==", "OVypq");
        CustomColors.lIlIIlIIllIIIllI[74] = llllIIlIlIllllII("jesXo+Ws+jUsR2iNYwdwjA==", "QMNzr");
        CustomColors.lIlIIlIIllIIIllI[75] = llllIIlIlIlllIll("knCGIFtlYB8=", "WkQVr");
        CustomColors.lIlIIlIIllIIIllI[76] = llllIIlIlIllllII("lKEfwjlB9VW4Rwqs+3eTzA==", "uLaVY");
        CustomColors.lIlIIlIIllIIIllI[77] = llllIIlIlIlllIll("U5R/rCxw+Yk=", "RCWjS");
        CustomColors.lIlIIlIIllIIIllI[78] = llllIIlIlIlllIll("GQQt/0bVcwDOHQXQKfGGFg==", "Eshtg");
        CustomColors.lIlIIlIIllIIIllI[79] = llllIIlIlIlllIll("PCgxEil4lM8w4K1hu5eCyS18yTJEbGdR", "lYRKh");
        CustomColors.lIlIIlIIllIIIllI[80] = llllIIlIlIlllIll("6xjo9321A9Q=", "KHIcA");
        CustomColors.lIlIIlIIllIIIllI[81] = llllIIlIlIlllllI("RyChqRrn98I=", "MPpsd");
        CustomColors.lIlIIlIIllIIIllI[82] = llllIIlIlIllllIl("IAYzGAU2", "SiFjf");
        CustomColors.lIlIIlIIllIIIllI[83] = llllIIlIlIlllIll("oJqpJSa4bHcVw7LSEkSnQI2+3UsrD26+EOlvIZ1FhRg=", "TcVuh");
        CustomColors.lIlIIlIIllIIIllI[84] = llllIIlIlIllllIl("eBQWF288GxQAISsbFgt1eA==", "XryeO");
        CustomColors.lIlIIlIIllIIIllI[85] = llllIIlIlIllllIl("IQMbDgMBCU0cHwkaA08KDwpNDAAEAh9VTw==", "hmmoo");
        CustomColors.lIlIIlIIllIIIllI[86] = llllIIlIlIllllII("D/e9v1G/8SXXLOYFZoJOzA==", "kBSWq");
        CustomColors.lIlIIlIIllIIIllI[87] = llllIIlIlIlllllI("7xxdOVMAdeyJnQNO8uojQA==", "AIGKg");
        CustomColors.lIlIIlIIllIIIllI[88] = llllIIlIlIllllII("O5/NSc0vP+DvgQ11zx9hQw==", "RABqi");
        CustomColors.lIlIIlIIllIIIllI[89] = llllIIlIlIllllIl("NDETOw0HOhgmHA==", "XXtSy");
        CustomColors.lIlIIlIIllIIIllI[90] = llllIIlIlIlllllI("TAQ6v5jGFUWjOYxnyKAV5A==", "BzZBH");
        CustomColors.lIlIIlIIllIIIllI[91] = llllIIlIlIlllIll("BNP52t6eigo=", "yPlVA");
        CustomColors.lIlIIlIIllIIIllI[92] = llllIIlIlIlllllI("GcAiv3UW2DcQLVDV76q4Vw==", "JfZNr");
        CustomColors.lIlIIlIIllIIIllI[93] = llllIIlIlIllllIl("PTs4FTUdMW4XNhg6PE55", "tUNtY");
        CustomColors.lIlIIlIIllIIIllI[94] = llllIIlIlIllllII("dzI/PCC9c4lHJhOVTeF3Og==", "DvMqs");
        CustomColors.lIlIIlIIllIIIllI[95] = llllIIlIlIlllIll("Bu7B5dYfY9jp1ikHRck25A==", "kLAmR");
        CustomColors.lIlIIlIIllIIIllI[96] = llllIIlIlIlllllI("wxo3FHHIt+SDADNRttzu9w==", "LAFzp");
        CustomColors.lIlIIlIIllIIIllI[97] = llllIIlIlIllllII("QhMyw6ucfiDbi75pgpICog==", "MUJsP");
        CustomColors.lIlIIlIIllIIIllI[98] = llllIIlIlIlllIll("eCRrGlBA8zWJmlp7OnblYA==", "aZcNL");
        CustomColors.lIlIIlIIllIIIllI[99] = llllIIlIlIlllIll("hI2lxbIJrnF+6+fwIRlMLg==", "VlHUG");
        CustomColors.lIlIIlIIllIIIllI[100] = llllIIlIlIlllllI("7KRBQWkArw0=", "oxRqw");
        CustomColors.lIlIIlIIllIIIllI[101] = llllIIlIlIllllIl("Qy4KGAgRPl9U", "cMetg");
        CustomColors.lIlIIlIIllIIIllI[102] = llllIIlIlIllllIl("NTcgOiMrdiMyOCAq", "EXTSL");
        CustomColors.lIlIIlIIllIIIllI[103] = llllIIlIlIlllIll("wJXMWM4hN28=", "tpIcv");
        CustomColors.lIlIIlIIllIIIllI[104] = llllIIlIlIllllII("9a9t4zjn7usq5+RCkaIDOg==", "HOJjB");
        CustomColors.lIlIIlIIllIIIllI[105] = llllIIlIlIlllIll("gDU7/pLxqjs=", "HxsWq");
        CustomColors.lIlIIlIIllIIIllI[106] = llllIIlIlIlllIll("qht+xfx0v+4=", "fCFKb");
        CustomColors.lIlIIlIIllIIIllI[107] = llllIIlIlIllllIl("DiQX", "zJcFT");
        CustomColors.lIlIIlIIllIIIllI[108] = llllIIlIlIllllIl("OjEh", "SRDDp");
        CustomColors.lIlIIlIIllIIIllI[109] = llllIIlIlIlllllI("QK5P/ChDFak=", "xTRMR");
        CustomColors.lIlIIlIIllIIIllI[110] = llllIIlIlIlllllI("IW01qNcMGRk=", "ULspR");
        CustomColors.lIlIIlIIllIIIllI[111] = llllIIlIlIlllllI("mad23iv4Tjo=", "pIvnI");
        CustomColors.lIlIIlIIllIIIllI[112] = llllIIlIlIlllIll("8+18l8SO1xc=", "EZghW");
        CustomColors.lIlIIlIIllIIIllI[113] = llllIIlIlIlllllI("ZFY0oJ/ThGI=", "DMhKf");
        CustomColors.lIlIIlIIllIIIllI[114] = llllIIlIlIllllIl("MTMDNws=", "BGlYn");
        CustomColors.lIlIIlIIllIIIllI[115] = llllIIlIlIllllIl("ICocARA=", "WKhdb");
        CustomColors.lIlIIlIIllIIIllI[116] = llllIIlIlIllllII("bMYW3MJBCI54woonB3Fmdw==", "BTCBo");
        CustomColors.lIlIIlIIllIIIllI[117] = llllIIlIlIllllII("4+wV9WuvO/u3xo16tT5OJg==", "hYNWj");
        CustomColors.lIlIIlIIllIIIllI[118] = llllIIlIlIllllIl("Mg4CLxE=", "SjmMt");
        CustomColors.lIlIIlIIllIIIllI[119] = llllIIlIlIlllIll("SXl3y+rf4LI=", "OpiJK");
        CustomColors.lIlIIlIIllIIIllI[120] = llllIIlIlIlllllI("XEOcXO1NRVT+iLsLgyE5Kw==", "ZcgRf");
        CustomColors.lIlIIlIIllIIIllI[121] = llllIIlIlIlllIll("LfIp0ljPvYDdP2a1+2lPNQ==", "mfRKM");
        CustomColors.lIlIIlIIllIIIllI[122] = llllIIlIlIllllII("NnQnKfpWsUF+hfX1+iJQUw==", "INXeF");
        CustomColors.lIlIIlIIllIIIllI[123] = llllIIlIlIllllIl("OCo3Nw==", "TCZRo");
        CustomColors.lIlIIlIIllIIIllI[124] = llllIIlIlIlllIll("YjQeucX5qUg=", "gdkRr");
        CustomColors.lIlIIlIIllIIIllI[125] = llllIIlIlIlllIll("Orj9gPh27OE=", "FYtFy");
        CustomColors.lIlIIlIIllIIIllI[126] = llllIIlIlIlllllI("TVSd65PCMqk=", "WqJVo");
        CustomColors.lIlIIlIIllIIIllI[127] = llllIIlIlIllllII("fpa4xRxIwoBGVMfen/Kxiw==", "gSCwV");
        CustomColors.lIlIIlIIllIIIllI[128] = llllIIlIlIllllIl("HQQaGzoI", "mqhkV");
        CustomColors.lIlIIlIIllIIIllI[129] = llllIIlIlIllllII("cW0nYqL9CnTDGcKgwk7z1A==", "pZFot");
        CustomColors.lIlIIlIIllIIIllI[130] = llllIIlIlIllllII("f7nJXu7DIElNDOcoZ98X2A==", "YxMLo");
        CustomColors.lIlIIlIIllIIIllI[131] = llllIIlIlIlllIll("kBNSuUeUuiA=", "jjbDu");
        CustomColors.lIlIIlIIllIIIllI[132] = llllIIlIlIlllIll("p6U4f8kVBqA=", "tbBDY");
        CustomColors.lIlIIlIIllIIIllI[133] = llllIIlIlIlllllI("3aqzoM4+pgI=", "ZuLYT");
        CustomColors.lIlIIlIIllIIIllI[134] = llllIIlIlIllllIl("IgEgDw==", "EnLkm");
        CustomColors.lIlIIlIIllIIIllI[135] = llllIIlIlIllllIl("DxkZKRsFFA==", "kpxDt");
        CustomColors.lIlIIlIIllIIIllI[136] = llllIIlIlIlllIll("6ankNHcp/aY=", "mySMx");
        CustomColors.lIlIIlIIllIIIllI[137] = llllIIlIlIlllllI("bnhIcrsqO5A=", "iDpJP");
        CustomColors.lIlIIlIIllIIIllI[138] = llllIIlIlIlllllI("5QttrHzCf5j0g9IW+3FNfA==", "WqGTH");
        CustomColors.lIlIIlIIllIIIllI[139] = llllIIlIlIlllllI("71Q3cX3ttwgHb7e1DeUsHQ==", "wXFir");
        CustomColors.lIlIIlIIllIIIllI[140] = llllIIlIlIlllIll("EDvd5jeksH0FpaxNCU6knw==", "sXVVM");
        CustomColors.lIlIIlIIllIIIllI[141] = llllIIlIlIllllII("vad1wDz17J0LhHQCvGwVjw==", "vxVUK");
        CustomColors.lIlIIlIIllIllIIl = null;
    }
    
    private static void llllIIlIllIlllII() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        CustomColors.lIlIIlIIllIllIIl = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String llllIIlIlIllllII(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
            final Cipher instance = Cipher.getInstance("AES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String llllIIlIlIlllllI(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
            final Cipher instance = Cipher.getInstance("DES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String llllIIlIlIlllIll(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher instance = Cipher.getInstance("Blowfish");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String llllIIlIlIllllIl(String s, final String s2) {
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
    
    public interface IColorizer
    {
        int getColor(final IBlockAccess p0, final BlockPos p1);
        
        boolean isColorConstant();
    }
}
