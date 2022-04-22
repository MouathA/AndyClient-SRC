package optifine;

import java.nio.*;
import java.awt.image.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.texture.*;
import shadersmod.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import java.io.*;
import java.awt.*;
import javax.imageio.*;
import java.util.*;

public class TextureUtils
{
    public static final String texGrassTop;
    public static final String texStone;
    public static final String texDirt;
    public static final String texCoarseDirt;
    public static final String texGrassSide;
    public static final String texStoneslabSide;
    public static final String texStoneslabTop;
    public static final String texBedrock;
    public static final String texSand;
    public static final String texGravel;
    public static final String texLogOak;
    public static final String texLogBigOak;
    public static final String texLogAcacia;
    public static final String texLogSpruce;
    public static final String texLogBirch;
    public static final String texLogJungle;
    public static final String texLogOakTop;
    public static final String texLogBigOakTop;
    public static final String texLogAcaciaTop;
    public static final String texLogSpruceTop;
    public static final String texLogBirchTop;
    public static final String texLogJungleTop;
    public static final String texLeavesOak;
    public static final String texLeavesBigOak;
    public static final String texLeavesAcacia;
    public static final String texLeavesBirch;
    public static final String texLeavesSpuce;
    public static final String texLeavesJungle;
    public static final String texGoldOre;
    public static final String texIronOre;
    public static final String texCoalOre;
    public static final String texObsidian;
    public static final String texGrassSideOverlay;
    public static final String texSnow;
    public static final String texGrassSideSnowed;
    public static final String texMyceliumSide;
    public static final String texMyceliumTop;
    public static final String texDiamondOre;
    public static final String texRedstoneOre;
    public static final String texLapisOre;
    public static final String texCactusSide;
    public static final String texClay;
    public static final String texFarmlandWet;
    public static final String texFarmlandDry;
    public static final String texNetherrack;
    public static final String texSoulSand;
    public static final String texGlowstone;
    public static final String texLeavesSpruce;
    public static final String texLeavesSpruceOpaque;
    public static final String texEndStone;
    public static final String texSandstoneTop;
    public static final String texSandstoneBottom;
    public static final String texRedstoneLampOff;
    public static final String texRedstoneLampOn;
    public static final String texWaterStill;
    public static final String texWaterFlow;
    public static final String texLavaStill;
    public static final String texLavaFlow;
    public static final String texFireLayer0;
    public static final String texFireLayer1;
    public static final String texPortal;
    public static final String texGlass;
    public static final String texGlassPaneTop;
    public static final String texCompass;
    public static final String texClock;
    public static TextureAtlasSprite iconGrassTop;
    public static TextureAtlasSprite iconGrassSide;
    public static TextureAtlasSprite iconGrassSideOverlay;
    public static TextureAtlasSprite iconSnow;
    public static TextureAtlasSprite iconGrassSideSnowed;
    public static TextureAtlasSprite iconMyceliumSide;
    public static TextureAtlasSprite iconMyceliumTop;
    public static TextureAtlasSprite iconWaterStill;
    public static TextureAtlasSprite iconWaterFlow;
    public static TextureAtlasSprite iconLavaStill;
    public static TextureAtlasSprite iconLavaFlow;
    public static TextureAtlasSprite iconPortal;
    public static TextureAtlasSprite iconFireLayer0;
    public static TextureAtlasSprite iconFireLayer1;
    public static TextureAtlasSprite iconGlass;
    public static TextureAtlasSprite iconGlassPaneTop;
    public static TextureAtlasSprite iconCompass;
    public static TextureAtlasSprite iconClock;
    public static final String SPRITE_PREFIX_BLOCKS;
    public static final String SPRITE_PREFIX_ITEMS;
    private static IntBuffer staticBuffer;
    
    static {
        texLogBirchTop = "log_birch_top";
        texLeavesJungle = "leaves_jungle";
        texCoalOre = "coal_ore";
        texRedstoneLampOn = "redstone_lamp_on";
        texRedstoneLampOff = "redstone_lamp_off";
        texClock = "clock";
        texEndStone = "end_stone";
        texLogJungle = "log_jungle";
        texLeavesBigOak = "leaves_big_oak";
        texGlowstone = "glowstone";
        texLogOakTop = "log_oak_top";
        texBedrock = "bedrock";
        texIronOre = "iron_ore";
        texSoulSand = "soul_sand";
        texGrassSideOverlay = "grass_side_overlay";
        texDirt = "dirt";
        texLogOak = "log_oak";
        texRedstoneOre = "redstone_ore";
        texLogBigOakTop = "log_big_oak_top";
        texGravel = "gravel";
        texLogBirch = "log_birch";
        texLogSpruce = "log_spruce";
        texFireLayer0 = "fire_layer_0";
        texSandstoneTop = "sandstone_top";
        texWaterFlow = "water_flow";
        texLogAcaciaTop = "log_acacia_top";
        texMyceliumSide = "mycelium_side";
        texSnow = "snow";
        texLogBigOak = "log_big_oak";
        texGrassSideSnowed = "grass_side_snowed";
        texGrassSide = "grass_side";
        texCompass = "compass";
        texClay = "clay";
        texLavaStill = "lava_still";
        texLavaFlow = "lava_flow";
        texGrassTop = "grass_top";
        texFireLayer1 = "fire_layer_1";
        texDiamondOre = "diamond_ore";
        texLapisOre = "lapis_ore";
        texNetherrack = "netherrack";
        texSandstoneBottom = "sandstone_bottom";
        texStoneslabSide = "stone_slab_side";
        texSand = "sand";
        texFarmlandWet = "farmland_wet";
        texStone = "stone";
        texLogSpruceTop = "log_spruce_top";
        texLeavesSpuce = "leaves_spruce";
        texGlass = "glass";
        texWaterStill = "water_still";
        texLeavesOak = "leaves_oak";
        texLeavesSpruce = "leaves_spruce";
        texLeavesBirch = "leaves_birch";
        texMyceliumTop = "mycelium_top";
        texGlassPaneTop = "glass_pane_top";
        texFarmlandDry = "farmland_dry";
        texObsidian = "obsidian";
        SPRITE_PREFIX_ITEMS = "minecraft:items/";
        texStoneslabTop = "stone_slab_top";
        texPortal = "portal";
        texLogJungleTop = "log_jungle_top";
        texCactusSide = "cactus_side";
        texCoarseDirt = "coarse_dirt";
        texLogAcacia = "log_acacia";
        texLeavesSpruceOpaque = "leaves_spruce_opaque";
        SPRITE_PREFIX_BLOCKS = "minecraft:blocks/";
        texGoldOre = "gold_ore";
        texLeavesAcacia = "leaves_acacia";
        TextureUtils.staticBuffer = GLAllocation.createDirectIntBuffer(256);
    }
    
    public static void update() {
        final TextureMap textureMapBlocks = getTextureMapBlocks();
        if (textureMapBlocks != null) {
            final String s = "minecraft:blocks/";
            TextureUtils.iconGrassTop = textureMapBlocks.getSpriteSafe(String.valueOf(s) + "grass_top");
            TextureUtils.iconGrassSide = textureMapBlocks.getSpriteSafe(String.valueOf(s) + "grass_side");
            TextureUtils.iconGrassSideOverlay = textureMapBlocks.getSpriteSafe(String.valueOf(s) + "grass_side_overlay");
            TextureUtils.iconSnow = textureMapBlocks.getSpriteSafe(String.valueOf(s) + "snow");
            TextureUtils.iconGrassSideSnowed = textureMapBlocks.getSpriteSafe(String.valueOf(s) + "grass_side_snowed");
            TextureUtils.iconMyceliumSide = textureMapBlocks.getSpriteSafe(String.valueOf(s) + "mycelium_side");
            TextureUtils.iconMyceliumTop = textureMapBlocks.getSpriteSafe(String.valueOf(s) + "mycelium_top");
            TextureUtils.iconWaterStill = textureMapBlocks.getSpriteSafe(String.valueOf(s) + "water_still");
            TextureUtils.iconWaterFlow = textureMapBlocks.getSpriteSafe(String.valueOf(s) + "water_flow");
            TextureUtils.iconLavaStill = textureMapBlocks.getSpriteSafe(String.valueOf(s) + "lava_still");
            TextureUtils.iconLavaFlow = textureMapBlocks.getSpriteSafe(String.valueOf(s) + "lava_flow");
            TextureUtils.iconFireLayer0 = textureMapBlocks.getSpriteSafe(String.valueOf(s) + "fire_layer_0");
            TextureUtils.iconFireLayer1 = textureMapBlocks.getSpriteSafe(String.valueOf(s) + "fire_layer_1");
            TextureUtils.iconPortal = textureMapBlocks.getSpriteSafe(String.valueOf(s) + "portal");
            TextureUtils.iconGlass = textureMapBlocks.getSpriteSafe(String.valueOf(s) + "glass");
            TextureUtils.iconGlassPaneTop = textureMapBlocks.getSpriteSafe(String.valueOf(s) + "glass_pane_top");
            final String s2 = "minecraft:items/";
            TextureUtils.iconCompass = textureMapBlocks.getSpriteSafe(String.valueOf(s2) + "compass");
            TextureUtils.iconClock = textureMapBlocks.getSpriteSafe(String.valueOf(s2) + "clock");
        }
    }
    
    public static BufferedImage fixTextureDimensions(final String s, final BufferedImage bufferedImage) {
        if (s.startsWith("/mob/zombie") || s.startsWith("/mob/pigzombie")) {
            final int width = bufferedImage.getWidth();
            final int height = bufferedImage.getHeight();
            if (width == height * 2) {
                final BufferedImage bufferedImage2 = new BufferedImage(width, height * 2, 2);
                final Graphics2D graphics = bufferedImage2.createGraphics();
                graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                graphics.drawImage(bufferedImage, 0, 0, width, height, null);
                return bufferedImage2;
            }
        }
        return bufferedImage;
    }
    
    public static int ceilPowerOfTwo(final int n) {
        while (1 < n) {}
        return 1;
    }
    
    public static int getPowerOfTwo(final int n) {
        while (1 < n) {
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    public static int twoToPower(final int n) {
        while (0 < n) {
            int n2 = 0;
            ++n2;
        }
        return 1;
    }
    
    public static ITextureObject getTexture(final ResourceLocation resourceLocation) {
        final ITextureObject texture = Config.getTextureManager().getTexture(resourceLocation);
        if (texture != null) {
            return texture;
        }
        if (!Config.hasResource(resourceLocation)) {
            return null;
        }
        final SimpleTexture simpleTexture = new SimpleTexture(resourceLocation);
        Config.getTextureManager().loadTexture(resourceLocation, simpleTexture);
        return simpleTexture;
    }
    
    public static void resourcesReloaded(final IResourceManager resourceManager) {
        if (getTextureMapBlocks() != null) {
            Config.dbg("*** Reloading custom textures ***");
            Config.getTextureManager().tick();
        }
    }
    
    public static TextureMap getTextureMapBlocks() {
        return Minecraft.getMinecraft().getTextureMapBlocks();
    }
    
    public static void registerResourceListener() {
        final IResourceManager resourceManager = Config.getResourceManager();
        if (resourceManager instanceof IReloadableResourceManager) {
            ((IReloadableResourceManager)resourceManager).registerReloadListener(new IResourceManagerReloadListener() {
                @Override
                public void onResourceManagerReload(final IResourceManager resourceManager) {
                    TextureUtils.resourcesReloaded(resourceManager);
                }
            });
        }
        Config.getTextureManager().loadTickableTexture(new ResourceLocation("optifine/TickableTextures"), new ITickableTextureObject() {
            @Override
            public void tick() {
            }
            
            @Override
            public void loadTexture(final IResourceManager resourceManager) throws IOException {
            }
            
            @Override
            public int getGlTextureId() {
                return 0;
            }
            
            @Override
            public void func_174936_b(final boolean b, final boolean b2) {
            }
            
            @Override
            public void func_174935_a() {
            }
            
            @Override
            public MultiTexID getMultiTexID() {
                return null;
            }
        });
    }
    
    public static String fixResourcePath(String s, String string) {
        final String s2 = "assets/minecraft/";
        if (s.startsWith(s2)) {
            s = s.substring(s2.length());
            return s;
        }
        if (s.startsWith("./")) {
            s = s.substring(2);
            if (!string.endsWith("/")) {
                string = String.valueOf(string) + "/";
            }
            s = String.valueOf(string) + s;
            return s;
        }
        if (s.startsWith("/~")) {
            s = s.substring(1);
        }
        final String s3 = "mcpatcher/";
        if (s.startsWith("~/")) {
            s = s.substring(2);
            s = String.valueOf(s3) + s;
            return s;
        }
        if (s.startsWith("/")) {
            s = String.valueOf(s3) + s.substring(1);
            return s;
        }
        return s;
    }
    
    public static String getBasePath(final String s) {
        final int lastIndex = s.lastIndexOf(47);
        return (lastIndex < 0) ? "" : s.substring(0, lastIndex);
    }
    
    public static void applyAnisotropicLevel() {
        if (GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
            GL11.glTexParameterf(3553, 34046, Math.min((float)Config.getAnisotropicFilterLevel(), GL11.glGetFloat(34047)));
        }
    }
    
    public static void bindTexture(final int n) {
        GlStateManager.func_179144_i(n);
    }
    
    public static boolean isPowerOfTwo(final int n) {
        return MathHelper.roundUpToPowerOfTwo(n) == n;
    }
    
    public static BufferedImage scaleToPowerOfTwo(final BufferedImage bufferedImage, final int n) {
        if (bufferedImage == null) {
            return bufferedImage;
        }
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int roundUpToPowerOfTwo = MathHelper.roundUpToPowerOfTwo(Math.max(width, n));
        if (roundUpToPowerOfTwo == width) {
            return bufferedImage;
        }
        final int n2 = height * roundUpToPowerOfTwo / width;
        final BufferedImage bufferedImage2 = new BufferedImage(roundUpToPowerOfTwo, n2, 2);
        final Graphics2D graphics = bufferedImage2.createGraphics();
        Object o = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
        if (roundUpToPowerOfTwo % width != 0) {
            o = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
        }
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, o);
        graphics.drawImage(bufferedImage, 0, 0, roundUpToPowerOfTwo, n2, null);
        return bufferedImage2;
    }
    
    public static BufferedImage scaleMinTo(final BufferedImage bufferedImage, final int n) {
        if (bufferedImage == null) {
            return bufferedImage;
        }
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        if (width >= n) {
            return bufferedImage;
        }
        int i;
        for (i = width; i < n; i *= 2) {}
        final int n2 = height * i / width;
        final BufferedImage bufferedImage2 = new BufferedImage(i, n2, 2);
        final Graphics2D graphics = bufferedImage2.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        graphics.drawImage(bufferedImage, 0, 0, i, n2, null);
        return bufferedImage2;
    }
    
    public static Dimension getImageSize(final InputStream inputStream, final String s) {
        final Iterator<ImageReader> imageReadersBySuffix = ImageIO.getImageReadersBySuffix(s);
        if (imageReadersBySuffix.hasNext()) {
            final ImageReader imageReader = imageReadersBySuffix.next();
            imageReader.setInput(ImageIO.createImageInputStream(inputStream));
            final Dimension dimension = new Dimension(imageReader.getWidth(imageReader.getMinIndex()), imageReader.getHeight(imageReader.getMinIndex()));
            imageReader.dispose();
            return dimension;
        }
        return null;
    }
}
