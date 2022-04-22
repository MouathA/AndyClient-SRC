package net.minecraft.client.renderer.texture;

import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import shadersmod.client.*;
import net.minecraft.client.*;
import java.awt.image.*;
import optifine.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.data.*;
import net.minecraft.client.resources.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import net.minecraft.client.renderer.*;

public class TextureMap extends AbstractTexture implements ITickableTextureObject
{
    private static final Logger logger;
    public static final ResourceLocation field_174945_f;
    public static final ResourceLocation locationBlocksTexture;
    private final List listAnimatedSprites;
    private final Map mapRegisteredSprites;
    private final Map mapUploadedSprites;
    private final String basePath;
    private final IIconCreator field_174946_m;
    private int mipmapLevels;
    private final TextureAtlasSprite missingImage;
    private static final String __OBFID;
    private TextureAtlasSprite[] iconGrid;
    private int iconGridSize;
    private int iconGridCountX;
    private int iconGridCountY;
    private double iconGridSizeU;
    private double iconGridSizeV;
    private static final boolean ENABLE_SKIP;
    private boolean skipFirst;
    public int atlasWidth;
    public int atlasHeight;
    
    static {
        __OBFID = "CL_00001058";
        logger = LogManager.getLogger();
        field_174945_f = new ResourceLocation("missingno");
        locationBlocksTexture = new ResourceLocation("textures/atlas/blocks.png");
        ENABLE_SKIP = Boolean.parseBoolean(System.getProperty("fml.skipFirstTextureLoad", "true"));
    }
    
    public TextureMap(final String s) {
        this(s, null);
    }
    
    public TextureMap(final String s, final boolean b) {
        this(s, null, b);
    }
    
    public TextureMap(final String s, final IIconCreator iconCreator) {
        this(s, iconCreator, false);
    }
    
    public TextureMap(final String basePath, final IIconCreator field_174946_m, final boolean b) {
        this.iconGrid = null;
        this.iconGridSize = -1;
        this.iconGridCountX = -1;
        this.iconGridCountY = -1;
        this.iconGridSizeU = -1.0;
        this.iconGridSizeV = -1.0;
        this.skipFirst = false;
        this.atlasWidth = 0;
        this.atlasHeight = 0;
        this.listAnimatedSprites = Lists.newArrayList();
        this.mapRegisteredSprites = Maps.newHashMap();
        this.mapUploadedSprites = Maps.newHashMap();
        this.missingImage = new TextureAtlasSprite("missingno");
        this.basePath = basePath;
        this.field_174946_m = field_174946_m;
        this.skipFirst = (b && TextureMap.ENABLE_SKIP);
    }
    
    private void initMissingImage() {
        final int minSpriteSize = this.getMinSpriteSize();
        final int[] missingImageData = this.getMissingImageData(minSpriteSize);
        this.missingImage.setIconWidth(minSpriteSize);
        this.missingImage.setIconHeight(minSpriteSize);
        final int[][] array = new int[this.mipmapLevels + 1][];
        array[0] = missingImageData;
        this.missingImage.setFramesTextureData(Lists.newArrayList((Object[])new int[][][] { array }));
        this.missingImage.setIndexInMap(0);
    }
    
    @Override
    public void loadTexture(final IResourceManager resManager) throws IOException {
        ShadersTex.resManager = resManager;
        if (this.field_174946_m != null) {
            this.func_174943_a(resManager, this.field_174946_m);
        }
    }
    
    public void func_174943_a(final IResourceManager resourceManager, final IIconCreator iconCreator) {
        this.mapRegisteredSprites.clear();
        iconCreator.func_177059_a(this);
        if (this.mipmapLevels >= 4) {
            this.mipmapLevels = this.detectMaxMipmapLevel(this.mapRegisteredSprites, resourceManager);
            Config.log("Mipmap levels: " + this.mipmapLevels);
        }
        this.initMissingImage();
        this.deleteGlTexture();
        this.loadTextureAtlas(resourceManager);
    }
    
    public void loadTextureAtlas(final IResourceManager resManager) {
        ShadersTex.resManager = resManager;
        Config.dbg("Multitexture: " + Config.isMultiTexture());
        if (Config.isMultiTexture()) {
            final Iterator<TextureAtlasSprite> iterator = this.mapUploadedSprites.values().iterator();
            while (iterator.hasNext()) {
                iterator.next().deleteSpriteTexture();
            }
        }
        ConnectedTextures.updateIcons(this);
        CustomItems.updateIcons(this);
        final int glMaximumTextureSize = Minecraft.getGLMaximumTextureSize();
        final Stitcher stitcher = new Stitcher(glMaximumTextureSize, glMaximumTextureSize, true, 0, this.mipmapLevels);
        this.mapUploadedSprites.clear();
        this.listAnimatedSprites.clear();
        Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPre, this);
        final int minSpriteSize = this.getMinSpriteSize();
        this.iconGridSize = minSpriteSize;
        int n = 1 << this.mipmapLevels;
        final Iterator<Map.Entry<K, TextureAtlasSprite>> iterator2 = this.mapRegisteredSprites.entrySet().iterator();
        while (iterator2.hasNext() && !this.skipFirst) {
            final TextureAtlasSprite textureAtlasSprite = iterator2.next().getValue();
            final ResourceLocation resourceLocation = new ResourceLocation(textureAtlasSprite.getIconName());
            final ResourceLocation completeResourceLocation = this.completeResourceLocation(resourceLocation, 0);
            if (textureAtlasSprite.hasCustomLoader(resManager, resourceLocation)) {
                if (!textureAtlasSprite.load(resManager, resourceLocation)) {
                    Math.min(Integer.MAX_VALUE, Math.min(textureAtlasSprite.getIconWidth(), textureAtlasSprite.getIconHeight()));
                    stitcher.addSprite(textureAtlasSprite);
                }
                Config.dbg("Custom loader: " + textureAtlasSprite);
            }
            else {
                final IResource loadResource = ShadersTex.loadResource(resManager, completeResourceLocation);
                final BufferedImage[] array = new BufferedImage[1 + this.mipmapLevels];
                array[0] = TextureUtil.func_177053_a(loadResource.getInputStream());
                if (array != null) {
                    final int width = array[0].getWidth();
                    if (width < minSpriteSize || this.mipmapLevels > 0) {
                        array[0] = ((this.mipmapLevels > 0) ? TextureUtils.scaleToPowerOfTwo(array[0], minSpriteSize) : TextureUtils.scaleMinTo(array[0], minSpriteSize));
                        final int width2 = array[0].getWidth();
                        if (width2 != width) {
                            if (!TextureUtils.isPowerOfTwo(width)) {
                                Config.log("Scaled non power of 2: " + textureAtlasSprite.getIconName() + ", " + width + " -> " + width2);
                            }
                            else {
                                Config.log("Scaled too small texture: " + textureAtlasSprite.getIconName() + ", " + width + " -> " + width2);
                            }
                        }
                    }
                }
                final TextureMetadataSection textureMetadataSection = (TextureMetadataSection)loadResource.getMetadata("texture");
                if (textureMetadataSection != null) {
                    final List listMipmaps = textureMetadataSection.getListMipmaps();
                    if (!listMipmaps.isEmpty()) {
                        final int width3 = array[0].getWidth();
                        final int height = array[0].getHeight();
                        if (MathHelper.roundUpToPowerOfTwo(width3) != width3 || MathHelper.roundUpToPowerOfTwo(height) != height) {
                            throw new RuntimeException("Unable to load extra miplevels, source-texture is not power of two");
                        }
                    }
                    for (final int intValue : listMipmaps) {
                        if (intValue > 0 && intValue < array.length - 1 && array[intValue] == null) {
                            array[intValue] = TextureUtil.func_177053_a(ShadersTex.loadResource(resManager, this.completeResourceLocation(resourceLocation, intValue)).getInputStream());
                        }
                    }
                }
                textureAtlasSprite.func_180598_a(array, (AnimationMetadataSection)loadResource.getMetadata("animation"));
                Math.min(Integer.MAX_VALUE, Math.min(textureAtlasSprite.getIconWidth(), textureAtlasSprite.getIconHeight()));
                final int min = Math.min(Integer.lowestOneBit(textureAtlasSprite.getIconWidth()), Integer.lowestOneBit(textureAtlasSprite.getIconHeight()));
                if (min < n) {
                    TextureMap.logger.warn("Texture {} with size {}x{} limits mip level from {} to {}", completeResourceLocation, textureAtlasSprite.getIconWidth(), textureAtlasSprite.getIconHeight(), MathHelper.calculateLogBaseTwo(n), MathHelper.calculateLogBaseTwo(min));
                    n = min;
                }
                stitcher.addSprite(textureAtlasSprite);
            }
        }
        final int min2 = Math.min(Integer.MAX_VALUE, n);
        MathHelper.calculateLogBaseTwo(min2);
        if (0 < 0) {}
        if (0 < this.mipmapLevels) {
            TextureMap.logger.info("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", this.basePath, this.mipmapLevels, 0, min2);
            this.mipmapLevels = 0;
        }
        final Iterator<TextureAtlasSprite> iterator4 = this.mapRegisteredSprites.values().iterator();
        while (iterator4.hasNext() && !this.skipFirst) {
            iterator4.next().generateMipmaps(this.mipmapLevels);
        }
        this.missingImage.generateMipmaps(this.mipmapLevels);
        stitcher.addSprite(this.missingImage);
        this.skipFirst = false;
        stitcher.doStitch();
        TextureMap.logger.info("Created: {}x{} {}-atlas", stitcher.getCurrentWidth(), stitcher.getCurrentHeight(), this.basePath);
        if (Config.isShaders()) {
            ShadersTex.allocateTextureMap(this.getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight(), stitcher, this);
        }
        else {
            TextureUtil.func_180600_a(this.getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
        }
        final HashMap hashMap = Maps.newHashMap(this.mapRegisteredSprites);
        for (final TextureAtlasSprite sprite : stitcher.getStichSlots()) {
            if (Config.isShaders()) {
                ShadersTex.setIconName(ShadersTex.setSprite(sprite).getIconName());
            }
            final String iconName = sprite.getIconName();
            hashMap.remove(iconName);
            this.mapUploadedSprites.put(iconName, sprite);
            if (Config.isShaders()) {
                ShadersTex.uploadTexSubForLoadAtlas(sprite.getFrameTextureData(0), sprite.getIconWidth(), sprite.getIconHeight(), sprite.getOriginX(), sprite.getOriginY(), false, false);
            }
            else {
                TextureUtil.uploadTextureMipmap(sprite.getFrameTextureData(0), sprite.getIconWidth(), sprite.getIconHeight(), sprite.getOriginX(), sprite.getOriginY(), false, false);
            }
            if (sprite.hasAnimationMetadata()) {
                this.listAnimatedSprites.add(sprite);
            }
        }
        final Iterator<TextureAtlasSprite> iterator6 = hashMap.values().iterator();
        while (iterator6.hasNext()) {
            iterator6.next().copyFrom(this.missingImage);
        }
        if (Config.isMultiTexture()) {
            final int currentWidth = stitcher.getCurrentWidth();
            final int currentHeight = stitcher.getCurrentHeight();
            for (final TextureAtlasSprite textureAtlasSprite2 : stitcher.getStichSlots()) {
                textureAtlasSprite2.sheetWidth = currentWidth;
                textureAtlasSprite2.sheetHeight = currentHeight;
                textureAtlasSprite2.mipmapLevels = this.mipmapLevels;
                final TextureAtlasSprite spriteSingle = textureAtlasSprite2.spriteSingle;
                if (spriteSingle != null) {
                    spriteSingle.sheetWidth = currentWidth;
                    spriteSingle.sheetHeight = currentHeight;
                    spriteSingle.mipmapLevels = this.mipmapLevels;
                    textureAtlasSprite2.bindSpriteTexture();
                    TextureUtil.uploadTextureMipmap(spriteSingle.getFrameTextureData(0), spriteSingle.getIconWidth(), spriteSingle.getIconHeight(), spriteSingle.getOriginX(), spriteSingle.getOriginY(), false, true);
                }
            }
            Config.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        }
        Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPost, this);
        this.updateIconGrid(stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
        if (Config.equals(System.getProperty("saveTextureMap"), "true")) {
            Config.dbg("Exporting texture map to: " + this.basePath + "_x.png");
            TextureUtil.func_177055_a(this.basePath.replaceAll("/", "_"), this.getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
        }
    }
    
    public ResourceLocation completeResourceLocation(final ResourceLocation resourceLocation, final int n) {
        return this.isAbsoluteLocation(resourceLocation) ? ((n == 0) ? new ResourceLocation(resourceLocation.getResourceDomain(), String.valueOf(resourceLocation.getResourcePath()) + ".png") : new ResourceLocation(resourceLocation.getResourceDomain(), String.valueOf(resourceLocation.getResourcePath()) + "mipmap" + n + ".png")) : ((n == 0) ? new ResourceLocation(resourceLocation.getResourceDomain(), String.format("%s/%s%s", this.basePath, resourceLocation.getResourcePath(), ".png")) : new ResourceLocation(resourceLocation.getResourceDomain(), String.format("%s/mipmaps/%s.%d%s", this.basePath, resourceLocation.getResourcePath(), n, ".png")));
    }
    
    public TextureAtlasSprite getAtlasSprite(final String s) {
        TextureAtlasSprite missingImage = this.mapUploadedSprites.get(s);
        if (missingImage == null) {
            missingImage = this.missingImage;
        }
        return missingImage;
    }
    
    public void updateAnimations() {
        if (Config.isShaders()) {
            ShadersTex.updatingTex = this.getMultiTexID();
        }
        TextureUtil.bindTexture(this.getGlTextureId());
        for (final TextureAtlasSprite textureAtlasSprite : this.listAnimatedSprites) {
            if (this.isTerrainAnimationActive(textureAtlasSprite)) {
                textureAtlasSprite.updateAnimation();
            }
        }
        if (Config.isMultiTexture()) {
            for (final TextureAtlasSprite textureAtlasSprite2 : this.listAnimatedSprites) {
                if (this.isTerrainAnimationActive(textureAtlasSprite2)) {
                    final TextureAtlasSprite spriteSingle = textureAtlasSprite2.spriteSingle;
                    if (spriteSingle == null) {
                        continue;
                    }
                    if (textureAtlasSprite2 == TextureUtils.iconClock || textureAtlasSprite2 == TextureUtils.iconCompass) {
                        spriteSingle.frameCounter = textureAtlasSprite2.frameCounter;
                    }
                    textureAtlasSprite2.bindSpriteTexture();
                    spriteSingle.updateAnimation();
                }
            }
            TextureUtil.bindTexture(this.getGlTextureId());
        }
        if (Config.isShaders()) {
            ShadersTex.updatingTex = null;
        }
    }
    
    public TextureAtlasSprite func_174942_a(final ResourceLocation resourceLocation) {
        if (resourceLocation == null) {
            throw new IllegalArgumentException("Location cannot be null!");
        }
        TextureAtlasSprite func_176604_a = this.mapRegisteredSprites.get(resourceLocation.toString());
        if (func_176604_a == null) {
            func_176604_a = TextureAtlasSprite.func_176604_a(resourceLocation);
            this.mapRegisteredSprites.put(resourceLocation.toString(), func_176604_a);
            if (func_176604_a instanceof TextureAtlasSprite && func_176604_a.getIndexInMap() < 0) {
                func_176604_a.setIndexInMap(this.mapRegisteredSprites.size());
            }
        }
        return func_176604_a;
    }
    
    @Override
    public void tick() {
        this.updateAnimations();
    }
    
    public void setMipmapLevels(final int mipmapLevels) {
        this.mipmapLevels = mipmapLevels;
    }
    
    public TextureAtlasSprite func_174944_f() {
        return this.missingImage;
    }
    
    public TextureAtlasSprite getTextureExtry(final String s) {
        return this.mapRegisteredSprites.get(new ResourceLocation(s).toString());
    }
    
    public boolean setTextureEntry(final String s, final TextureAtlasSprite textureAtlasSprite) {
        if (!this.mapRegisteredSprites.containsKey(s)) {
            this.mapRegisteredSprites.put(s, textureAtlasSprite);
            if (textureAtlasSprite.getIndexInMap() < 0) {
                textureAtlasSprite.setIndexInMap(this.mapRegisteredSprites.size());
            }
            return true;
        }
        return false;
    }
    
    private boolean isAbsoluteLocation(final ResourceLocation resourceLocation) {
        return this.isAbsoluteLocationPath(resourceLocation.getResourcePath());
    }
    
    private boolean isAbsoluteLocationPath(final String s) {
        final String lowerCase = s.toLowerCase();
        return lowerCase.startsWith("mcpatcher/") || lowerCase.startsWith("optifine/");
    }
    
    public TextureAtlasSprite getSpriteSafe(final String s) {
        return this.mapRegisteredSprites.get(new ResourceLocation(s).toString());
    }
    
    private boolean isTerrainAnimationActive(final TextureAtlasSprite textureAtlasSprite) {
        return (textureAtlasSprite != TextureUtils.iconWaterStill && textureAtlasSprite != TextureUtils.iconWaterFlow) ? ((textureAtlasSprite != TextureUtils.iconLavaStill && textureAtlasSprite != TextureUtils.iconLavaFlow) ? ((textureAtlasSprite != TextureUtils.iconFireLayer0 && textureAtlasSprite != TextureUtils.iconFireLayer1) ? ((textureAtlasSprite == TextureUtils.iconPortal) ? Config.isAnimatedPortal() : (textureAtlasSprite == TextureUtils.iconClock || textureAtlasSprite == TextureUtils.iconCompass || Config.isAnimatedTerrain())) : Config.isAnimatedFire()) : Config.isAnimatedLava()) : Config.isAnimatedWater();
    }
    
    public int getCountRegisteredSprites() {
        return this.mapRegisteredSprites.size();
    }
    
    private int detectMaxMipmapLevel(final Map map, final IResourceManager resourceManager) {
        this.detectMinimumSpriteSize(map, resourceManager, 20);
        if (16 < 16) {}
        MathHelper.roundUpToPowerOfTwo(16);
        if (16 > 16) {
            Config.log("Sprite size: " + 16);
        }
        MathHelper.calculateLogBaseTwo(16);
        if (4 < 4) {}
        return 4;
    }
    
    private int detectMinimumSpriteSize(final Map map, final IResourceManager resourceManager, final int n) {
        final HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        final Iterator<Map.Entry<K, TextureAtlasSprite>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            final TextureAtlasSprite textureAtlasSprite = iterator.next().getValue();
            final ResourceLocation resourceLocation = new ResourceLocation(textureAtlasSprite.getIconName());
            final ResourceLocation completeResourceLocation = this.completeResourceLocation(resourceLocation, 0);
            if (!textureAtlasSprite.hasCustomLoader(resourceManager, resourceLocation)) {
                final IResource resource = resourceManager.getResource(completeResourceLocation);
                if (resource == null) {
                    continue;
                }
                final InputStream inputStream = resource.getInputStream();
                if (inputStream == null) {
                    continue;
                }
                final Dimension imageSize = TextureUtils.getImageSize(inputStream, "png");
                if (imageSize == null) {
                    continue;
                }
                final int roundUpToPowerOfTwo = MathHelper.roundUpToPowerOfTwo(imageSize.width);
                if (!hashMap.containsKey(roundUpToPowerOfTwo)) {
                    hashMap.put(roundUpToPowerOfTwo, 1);
                }
                else {
                    hashMap.put(roundUpToPowerOfTwo, hashMap.get(roundUpToPowerOfTwo) + 1);
                }
            }
        }
        final TreeSet set = new TreeSet<Integer>(hashMap.keySet());
        final Iterator<Integer> iterator2 = set.iterator();
        while (iterator2.hasNext()) {
            iterator2.next();
            final int n2 = 0 + hashMap.get(0);
        }
        final int n3 = 0 * n / 100;
        for (final int intValue : set) {
            final int n4 = 0 + hashMap.get(intValue);
            if (intValue > 16) {}
            if (0 > n3) {
                return 16;
            }
        }
        return 16;
    }
    
    private int getMinSpriteSize() {
        final int n = 1 << this.mipmapLevels;
        if (8 < 8) {}
        return 8;
    }
    
    private int[] getMissingImageData(final int n) {
        final BufferedImage bufferedImage = new BufferedImage(16, 16, 2);
        bufferedImage.setRGB(0, 0, 16, 16, TextureUtil.missingTextureData, 0, 16);
        final BufferedImage scaleToPowerOfTwo = TextureUtils.scaleToPowerOfTwo(bufferedImage, n);
        final int[] array = new int[n * n];
        scaleToPowerOfTwo.getRGB(0, 0, n, n, array, 0, n);
        return array;
    }
    
    public boolean isTextureBound() {
        return GlStateManager.getBoundTexture() == this.getGlTextureId();
    }
    
    private void updateIconGrid(final int n, final int n2) {
        this.iconGridCountX = -1;
        this.iconGridCountY = -1;
        this.iconGrid = null;
        if (this.iconGridSize > 0) {
            this.iconGridCountX = n / this.iconGridSize;
            this.iconGridCountY = n2 / this.iconGridSize;
            this.iconGrid = new TextureAtlasSprite[this.iconGridCountX * this.iconGridCountY];
            this.iconGridSizeU = 1.0 / this.iconGridCountX;
            this.iconGridSizeV = 1.0 / this.iconGridCountY;
            for (final TextureAtlasSprite textureAtlasSprite : this.mapUploadedSprites.values()) {
                final double n3 = 0.5 / n;
                final double n4 = 0.5 / n2;
                final double n5 = Math.min(textureAtlasSprite.getMinU(), textureAtlasSprite.getMaxU()) + n3;
                final double n6 = Math.min(textureAtlasSprite.getMinV(), textureAtlasSprite.getMaxV()) + n4;
                final double n7 = Math.max(textureAtlasSprite.getMinU(), textureAtlasSprite.getMaxU()) - n3;
                final double n8 = Math.max(textureAtlasSprite.getMinV(), textureAtlasSprite.getMaxV()) - n4;
                final int n9 = (int)(n5 / this.iconGridSizeU);
                final int n10 = (int)(n6 / this.iconGridSizeV);
                final int n11 = (int)(n7 / this.iconGridSizeU);
                final int n12 = (int)(n8 / this.iconGridSizeV);
                for (int i = n9; i <= n11; ++i) {
                    if (i >= 0 && i < this.iconGridCountX) {
                        for (int j = n10; j <= n12; ++j) {
                            if (j >= 0 && j < this.iconGridCountX) {
                                this.iconGrid[j * this.iconGridCountX + i] = textureAtlasSprite;
                            }
                            else {
                                Config.warn("Invalid grid V: " + j + ", icon: " + textureAtlasSprite.getIconName());
                            }
                        }
                    }
                    else {
                        Config.warn("Invalid grid U: " + i + ", icon: " + textureAtlasSprite.getIconName());
                    }
                }
            }
        }
    }
    
    public TextureAtlasSprite getIconByUV(final double n, final double n2) {
        if (this.iconGrid == null) {
            return null;
        }
        final int n3 = (int)(n2 / this.iconGridSizeV) * this.iconGridCountX + (int)(n / this.iconGridSizeU);
        return (n3 >= 0 && n3 <= this.iconGrid.length) ? this.iconGrid[n3] : null;
    }
}
