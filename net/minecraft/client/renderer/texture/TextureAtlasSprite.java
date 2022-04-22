package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.data.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import shadersmod.client.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import net.minecraft.client.resources.*;
import optifine.*;

public class TextureAtlasSprite
{
    private final String iconName;
    protected List framesTextureData;
    protected int[][] field_176605_b;
    private AnimationMetadataSection animationMetadata;
    protected boolean rotated;
    protected int originX;
    protected int originY;
    protected int width;
    protected int height;
    private float minU;
    private float maxU;
    private float minV;
    private float maxV;
    protected int frameCounter;
    protected int tickCounter;
    private static String field_176607_p;
    private static String field_176606_q;
    private static final String __OBFID;
    private int indexInMap;
    public float baseU;
    public float baseV;
    public int sheetWidth;
    public int sheetHeight;
    public int glSpriteTextureId;
    public TextureAtlasSprite spriteSingle;
    public boolean isSpriteSingle;
    public int mipmapLevels;
    
    static {
        __OBFID = "CL_00001062";
        TextureAtlasSprite.field_176607_p = "builtin/clock";
        TextureAtlasSprite.field_176606_q = "builtin/compass";
    }
    
    private TextureAtlasSprite(final TextureAtlasSprite textureAtlasSprite) {
        this.framesTextureData = Lists.newArrayList();
        this.indexInMap = -1;
        this.glSpriteTextureId = -1;
        this.spriteSingle = null;
        this.isSpriteSingle = false;
        this.mipmapLevels = 0;
        this.iconName = textureAtlasSprite.iconName;
        this.isSpriteSingle = true;
    }
    
    protected TextureAtlasSprite(final String iconName) {
        this.framesTextureData = Lists.newArrayList();
        this.indexInMap = -1;
        this.glSpriteTextureId = -1;
        this.spriteSingle = null;
        this.isSpriteSingle = false;
        this.mipmapLevels = 0;
        this.iconName = iconName;
        if (Config.isMultiTexture()) {
            this.spriteSingle = new TextureAtlasSprite(this);
        }
    }
    
    protected static TextureAtlasSprite func_176604_a(final ResourceLocation resourceLocation) {
        final String string = resourceLocation.toString();
        return TextureAtlasSprite.field_176607_p.equals(string) ? new TextureClock(string) : (TextureAtlasSprite.field_176606_q.equals(string) ? new TextureCompass(string) : new TextureAtlasSprite(string));
    }
    
    public static void func_176602_a(final String field_176607_p) {
        TextureAtlasSprite.field_176607_p = field_176607_p;
    }
    
    public static void func_176603_b(final String field_176606_q) {
        TextureAtlasSprite.field_176606_q = field_176606_q;
    }
    
    public void initSprite(final int n, final int n2, final int originX, final int originY, final boolean rotated) {
        this.originX = originX;
        this.originY = originY;
        this.rotated = rotated;
        final float n3 = (float)(0.009999999776482582 / n);
        final float n4 = (float)(0.009999999776482582 / n2);
        this.minU = originX / (float)n + n3;
        this.maxU = (originX + this.width) / (float)n - n3;
        this.minV = originY / (float)n2 + n4;
        this.maxV = (originY + this.height) / (float)n2 - n4;
        this.baseU = Math.min(this.minU, this.maxU);
        this.baseV = Math.min(this.minV, this.maxV);
        if (this.spriteSingle != null) {
            this.spriteSingle.initSprite(this.width, this.height, 0, 0, false);
        }
    }
    
    public void copyFrom(final TextureAtlasSprite textureAtlasSprite) {
        this.originX = textureAtlasSprite.originX;
        this.originY = textureAtlasSprite.originY;
        this.width = textureAtlasSprite.width;
        this.height = textureAtlasSprite.height;
        this.rotated = textureAtlasSprite.rotated;
        this.minU = textureAtlasSprite.minU;
        this.maxU = textureAtlasSprite.maxU;
        this.minV = textureAtlasSprite.minV;
        this.maxV = textureAtlasSprite.maxV;
        if (this.spriteSingle != null) {
            this.spriteSingle.initSprite(this.width, this.height, 0, 0, false);
        }
    }
    
    public int getOriginX() {
        return this.originX;
    }
    
    public int getOriginY() {
        return this.originY;
    }
    
    public int getIconWidth() {
        return this.width;
    }
    
    public int getIconHeight() {
        return this.height;
    }
    
    public float getMinU() {
        return this.minU;
    }
    
    public float getMaxU() {
        return this.maxU;
    }
    
    public float getInterpolatedU(final double n) {
        return this.minU + (this.maxU - this.minU) * (float)n / 16.0f;
    }
    
    public float getMinV() {
        return this.minV;
    }
    
    public float getMaxV() {
        return this.maxV;
    }
    
    public float getInterpolatedV(final double n) {
        return this.minV + (this.maxV - this.minV) * ((float)n / 16.0f);
    }
    
    public String getIconName() {
        return this.iconName;
    }
    
    public void updateAnimation() {
        ++this.tickCounter;
        if (this.tickCounter >= this.animationMetadata.getFrameTimeSingle(this.frameCounter)) {
            final int frameIndex = this.animationMetadata.getFrameIndex(this.frameCounter);
            this.frameCounter = (this.frameCounter + 1) % ((this.animationMetadata.getFrameCount() == 0) ? this.framesTextureData.size() : this.animationMetadata.getFrameCount());
            this.tickCounter = 0;
            final int frameIndex2 = this.animationMetadata.getFrameIndex(this.frameCounter);
            final boolean isSpriteSingle = this.isSpriteSingle;
            if (frameIndex != frameIndex2 && frameIndex2 >= 0 && frameIndex2 < this.framesTextureData.size()) {
                if (Config.isShaders()) {
                    ShadersTex.uploadTexSub(this.framesTextureData.get(frameIndex2), this.width, this.height, this.originX, this.originY, false, isSpriteSingle);
                }
                else {
                    TextureUtil.uploadTextureMipmap(this.framesTextureData.get(frameIndex2), this.width, this.height, this.originX, this.originY, false, isSpriteSingle);
                }
            }
        }
        else if (this.animationMetadata.func_177219_e()) {
            this.func_180599_n();
        }
    }
    
    private void func_180599_n() {
        final double n = 1.0 - this.tickCounter / (double)this.animationMetadata.getFrameTimeSingle(this.frameCounter);
        final int frameIndex = this.animationMetadata.getFrameIndex(this.frameCounter);
        final int frameIndex2 = this.animationMetadata.getFrameIndex((this.frameCounter + 1) % ((this.animationMetadata.getFrameCount() == 0) ? this.framesTextureData.size() : this.animationMetadata.getFrameCount()));
        if (frameIndex != frameIndex2 && frameIndex2 >= 0 && frameIndex2 < this.framesTextureData.size()) {
            final int[][] array = this.framesTextureData.get(frameIndex);
            final int[][] array2 = this.framesTextureData.get(frameIndex2);
            if (this.field_176605_b == null || this.field_176605_b.length != array.length) {
                this.field_176605_b = new int[array.length][];
            }
            while (0 < array.length) {
                if (this.field_176605_b[0] == null) {
                    this.field_176605_b[0] = new int[array[0].length];
                }
                if (0 < array2.length && array2[0].length == array[0].length) {
                    while (0 < array[0].length) {
                        final int n2 = array[0][0];
                        final int n3 = array2[0][0];
                        this.field_176605_b[0][0] = ((n2 & 0xFF000000) | (int)(((n2 & 0xFF0000) >> 16) * n + ((n3 & 0xFF0000) >> 16) * (1.0 - n)) << 16 | (int)(((n2 & 0xFF00) >> 8) * n + ((n3 & 0xFF00) >> 8) * (1.0 - n)) << 8 | (int)((n2 & 0xFF) * n + (n3 & 0xFF) * (1.0 - n)));
                        int n4 = 0;
                        ++n4;
                    }
                }
                int n5 = 0;
                ++n5;
            }
            TextureUtil.uploadTextureMipmap(this.field_176605_b, this.width, this.height, this.originX, this.originY, false, false);
        }
    }
    
    public int[][] getFrameTextureData(final int n) {
        return this.framesTextureData.get(n);
    }
    
    public int getFrameCount() {
        return this.framesTextureData.size();
    }
    
    public void setIconWidth(final int width) {
        this.width = width;
        if (this.spriteSingle != null) {
            this.spriteSingle.setIconWidth(this.width);
        }
    }
    
    public void setIconHeight(final int height) {
        this.height = height;
        if (this.spriteSingle != null) {
            this.spriteSingle.setIconHeight(this.height);
        }
    }
    
    public void func_180598_a(final BufferedImage[] p0, final AnimationMetadataSection p1) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   net/minecraft/client/renderer/texture/TextureAtlasSprite.resetSprite:()V
        //     4: aload_1        
        //     5: iconst_0       
        //     6: aaload         
        //     7: invokevirtual   java/awt/image/BufferedImage.getWidth:()I
        //    10: istore_3       
        //    11: aload_1        
        //    12: iconst_0       
        //    13: aaload         
        //    14: invokevirtual   java/awt/image/BufferedImage.getHeight:()I
        //    17: istore          4
        //    19: aload_0        
        //    20: iload_3        
        //    21: putfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.width:I
        //    24: aload_0        
        //    25: iload           4
        //    27: putfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.height:I
        //    30: aload_1        
        //    31: arraylength    
        //    32: anewarray       [I
        //    35: astore          5
        //    37: goto            189
        //    40: aload_1        
        //    41: iconst_0       
        //    42: aaload         
        //    43: astore          7
        //    45: aload           7
        //    47: ifnull          186
        //    50: goto            141
        //    53: aload           7
        //    55: invokevirtual   java/awt/image/BufferedImage.getWidth:()I
        //    58: iload_3        
        //    59: iconst_0       
        //    60: ishr           
        //    61: if_icmpne       76
        //    64: aload           7
        //    66: invokevirtual   java/awt/image/BufferedImage.getHeight:()I
        //    69: iload           4
        //    71: iconst_0       
        //    72: ishr           
        //    73: if_icmpeq       141
        //    76: new             Ljava/lang/RuntimeException;
        //    79: dup            
        //    80: ldc             "Unable to load miplevel: %d, image is size: %dx%d, expected %dx%d"
        //    82: iconst_5       
        //    83: anewarray       Ljava/lang/Object;
        //    86: dup            
        //    87: iconst_0       
        //    88: iconst_0       
        //    89: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    92: aastore        
        //    93: dup            
        //    94: iconst_1       
        //    95: aload           7
        //    97: invokevirtual   java/awt/image/BufferedImage.getWidth:()I
        //   100: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   103: aastore        
        //   104: dup            
        //   105: iconst_2       
        //   106: aload           7
        //   108: invokevirtual   java/awt/image/BufferedImage.getHeight:()I
        //   111: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   114: aastore        
        //   115: dup            
        //   116: iconst_3       
        //   117: iload_3        
        //   118: iconst_0       
        //   119: ishr           
        //   120: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   123: aastore        
        //   124: dup            
        //   125: iconst_4       
        //   126: iload           4
        //   128: iconst_0       
        //   129: ishr           
        //   130: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   133: aastore        
        //   134: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   137: invokespecial   java/lang/RuntimeException.<init>:(Ljava/lang/String;)V
        //   140: athrow         
        //   141: aload           5
        //   143: iconst_0       
        //   144: aload           7
        //   146: invokevirtual   java/awt/image/BufferedImage.getWidth:()I
        //   149: aload           7
        //   151: invokevirtual   java/awt/image/BufferedImage.getHeight:()I
        //   154: imul           
        //   155: newarray        I
        //   157: aastore        
        //   158: aload           7
        //   160: iconst_0       
        //   161: iconst_0       
        //   162: aload           7
        //   164: invokevirtual   java/awt/image/BufferedImage.getWidth:()I
        //   167: aload           7
        //   169: invokevirtual   java/awt/image/BufferedImage.getHeight:()I
        //   172: aload           5
        //   174: iconst_0       
        //   175: aaload         
        //   176: iconst_0       
        //   177: aload           7
        //   179: invokevirtual   java/awt/image/BufferedImage.getWidth:()I
        //   182: invokevirtual   java/awt/image/BufferedImage.getRGB:(IIII[III)[I
        //   185: pop            
        //   186: iinc            6, 1
        //   189: iconst_0       
        //   190: aload_1        
        //   191: arraylength    
        //   192: if_icmplt       40
        //   195: aload_2        
        //   196: ifnonnull       231
        //   199: iload           4
        //   201: iload_3        
        //   202: if_icmpeq       216
        //   205: new             Ljava/lang/RuntimeException;
        //   208: dup            
        //   209: ldc_w           "broken aspect ratio and not an animation"
        //   212: invokespecial   java/lang/RuntimeException.<init>:(Ljava/lang/String;)V
        //   215: athrow         
        //   216: aload_0        
        //   217: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.framesTextureData:Ljava/util/List;
        //   220: aload           5
        //   222: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   227: pop            
        //   228: goto            429
        //   231: iload           4
        //   233: iload_3        
        //   234: idiv           
        //   235: istore          6
        //   237: iload_3        
        //   238: istore          8
        //   240: iload_3        
        //   241: istore          9
        //   243: aload_0        
        //   244: aload_0        
        //   245: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.width:I
        //   248: putfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.height:I
        //   251: aload_2        
        //   252: invokevirtual   net/minecraft/client/resources/data/AnimationMetadataSection.getFrameCount:()I
        //   255: ifle            355
        //   258: aload_2        
        //   259: invokevirtual   net/minecraft/client/resources/data/AnimationMetadataSection.getFrameIndexSet:()Ljava/util/Set;
        //   262: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //   267: astore          10
        //   269: goto            337
        //   272: aload           10
        //   274: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   279: checkcast       Ljava/lang/Integer;
        //   282: invokevirtual   java/lang/Integer.intValue:()I
        //   285: istore          7
        //   287: new             Ljava/lang/RuntimeException;
        //   290: dup            
        //   291: new             Ljava/lang/StringBuilder;
        //   294: dup            
        //   295: ldc_w           "invalid frameindex "
        //   298: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   301: iconst_0       
        //   302: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   305: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   308: invokespecial   java/lang/RuntimeException.<init>:(Ljava/lang/String;)V
        //   311: athrow         
        //   312: aload_0        
        //   313: iconst_0       
        //   314: invokespecial   net/minecraft/client/renderer/texture/TextureAtlasSprite.allocateFrameTextureData:(I)V
        //   317: aload_0        
        //   318: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.framesTextureData:Ljava/util/List;
        //   321: iconst_0       
        //   322: aload           5
        //   324: iconst_0       
        //   325: iload           9
        //   327: iconst_0       
        //   328: invokestatic    net/minecraft/client/renderer/texture/TextureAtlasSprite.getFrameTextureData:([[IIII)[[I
        //   331: invokeinterface java/util/List.set:(ILjava/lang/Object;)Ljava/lang/Object;
        //   336: pop            
        //   337: aload           10
        //   339: invokeinterface java/util/Iterator.hasNext:()Z
        //   344: ifne            272
        //   347: aload_0        
        //   348: aload_2        
        //   349: putfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.animationMetadata:Lnet/minecraft/client/resources/data/AnimationMetadataSection;
        //   352: goto            429
        //   355: invokestatic    com/google/common/collect/Lists.newArrayList:()Ljava/util/ArrayList;
        //   358: astore          10
        //   360: goto            400
        //   363: aload_0        
        //   364: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.framesTextureData:Ljava/util/List;
        //   367: aload           5
        //   369: iconst_0       
        //   370: iload           9
        //   372: iconst_0       
        //   373: invokestatic    net/minecraft/client/renderer/texture/TextureAtlasSprite.getFrameTextureData:([[IIII)[[I
        //   376: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   381: pop            
        //   382: aload           10
        //   384: new             Lnet/minecraft/client/resources/data/AnimationFrame;
        //   387: dup            
        //   388: iconst_0       
        //   389: iconst_m1      
        //   390: invokespecial   net/minecraft/client/resources/data/AnimationFrame.<init>:(II)V
        //   393: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   396: pop            
        //   397: iinc            7, 1
        //   400: aload_0        
        //   401: new             Lnet/minecraft/client/resources/data/AnimationMetadataSection;
        //   404: dup            
        //   405: aload           10
        //   407: aload_0        
        //   408: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.width:I
        //   411: aload_0        
        //   412: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.height:I
        //   415: aload_2        
        //   416: invokevirtual   net/minecraft/client/resources/data/AnimationMetadataSection.getFrameTime:()I
        //   419: aload_2        
        //   420: invokevirtual   net/minecraft/client/resources/data/AnimationMetadataSection.func_177219_e:()Z
        //   423: invokespecial   net/minecraft/client/resources/data/AnimationMetadataSection.<init>:(Ljava/util/List;IIIZ)V
        //   426: putfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.animationMetadata:Lnet/minecraft/client/resources/data/AnimationMetadataSection;
        //   429: goto            493
        //   432: aload_0        
        //   433: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.framesTextureData:Ljava/util/List;
        //   436: iconst_0       
        //   437: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   442: checkcast       [[I
        //   445: astore          9
        //   447: aload           9
        //   449: ifnull          490
        //   452: aload_0        
        //   453: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.iconName:Ljava/lang/String;
        //   456: ldc_w           "minecraft:blocks/leaves_"
        //   459: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   462: ifne            490
        //   465: goto            483
        //   468: aload           9
        //   470: iconst_0       
        //   471: aaload         
        //   472: astore          10
        //   474: aload_0        
        //   475: aload           10
        //   477: invokespecial   net/minecraft/client/renderer/texture/TextureAtlasSprite.fixTransparentColor:([I)V
        //   480: iinc            7, 1
        //   483: iconst_0       
        //   484: aload           9
        //   486: arraylength    
        //   487: if_icmplt       468
        //   490: iinc            8, 1
        //   493: iconst_0       
        //   494: aload_0        
        //   495: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.framesTextureData:Ljava/util/List;
        //   498: invokeinterface java/util/List.size:()I
        //   503: if_icmplt       432
        //   506: aload_0        
        //   507: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.spriteSingle:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //   510: ifnull          522
        //   513: aload_0        
        //   514: getfield        net/minecraft/client/renderer/texture/TextureAtlasSprite.spriteSingle:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;
        //   517: aload_1        
        //   518: aload_2        
        //   519: invokevirtual   net/minecraft/client/renderer/texture/TextureAtlasSprite.func_180598_a:([Ljava/awt/image/BufferedImage;Lnet/minecraft/client/resources/data/AnimationMetadataSection;)V
        //   522: return         
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void generateMipmaps(final int n) {
        final ArrayList arrayList = Lists.newArrayList();
        while (0 < this.framesTextureData.size()) {
            final int[][] array = this.framesTextureData.get(0);
            if (array != null) {
                arrayList.add(TextureUtil.generateMipmapData(n, this.width, array));
            }
            int n2 = 0;
            ++n2;
        }
        this.setFramesTextureData(arrayList);
        if (this.spriteSingle != null) {
            this.spriteSingle.generateMipmaps(n);
        }
    }
    
    private void allocateFrameTextureData(final int n) {
        if (this.framesTextureData.size() <= n) {
            for (int i = this.framesTextureData.size(); i <= n; ++i) {
                this.framesTextureData.add(null);
            }
        }
        if (this.spriteSingle != null) {
            this.spriteSingle.allocateFrameTextureData(n);
        }
    }
    
    private static int[][] getFrameTextureData(final int[][] array, final int n, final int n2, final int n3) {
        final int[][] array2 = new int[array.length][];
        while (0 < array.length) {
            final int[] array3 = array[0];
            if (array3 != null) {
                array2[0] = new int[(n >> 0) * (n2 >> 0)];
                System.arraycopy(array3, n3 * array2[0].length, array2[0], 0, array2[0].length);
            }
            int n4 = 0;
            ++n4;
        }
        return array2;
    }
    
    public void clearFramesTextureData() {
        this.framesTextureData.clear();
        if (this.spriteSingle != null) {
            this.spriteSingle.clearFramesTextureData();
        }
    }
    
    public boolean hasAnimationMetadata() {
        return this.animationMetadata != null;
    }
    
    public void setFramesTextureData(final List list) {
        this.framesTextureData = list;
        if (this.spriteSingle != null) {
            this.spriteSingle.setFramesTextureData(list);
        }
    }
    
    private void resetSprite() {
        this.animationMetadata = null;
        this.setFramesTextureData(Lists.newArrayList());
        this.frameCounter = 0;
        this.tickCounter = 0;
        if (this.spriteSingle != null) {
            this.spriteSingle.resetSprite();
        }
    }
    
    @Override
    public String toString() {
        return "TextureAtlasSprite{name='" + this.iconName + '\'' + ", frameCount=" + this.framesTextureData.size() + ", rotated=" + this.rotated + ", x=" + this.originX + ", y=" + this.originY + ", height=" + this.height + ", width=" + this.width + ", u0=" + this.minU + ", u1=" + this.maxU + ", v0=" + this.minV + ", v1=" + this.maxV + '}';
    }
    
    public boolean hasCustomLoader(final IResourceManager resourceManager, final ResourceLocation resourceLocation) {
        return false;
    }
    
    public boolean load(final IResourceManager resourceManager, final ResourceLocation resourceLocation) {
        return true;
    }
    
    public int getIndexInMap() {
        return this.indexInMap;
    }
    
    public void setIndexInMap(final int indexInMap) {
        this.indexInMap = indexInMap;
    }
    
    private void fixTransparentColor(final int[] array) {
        if (array != null) {
            long n = 0L;
            long n2 = 0L;
            long n3 = 0L;
            long n4 = 0L;
            int n7 = 0;
            while (0 < array.length) {
                final int n5 = array[0];
                if ((n5 >> 24 & 0xFF) >= 16) {
                    final int n6 = n5 >> 16 & 0xFF;
                    n7 = (n5 >> 8 & 0xFF);
                    final int n8 = n5 & 0xFF;
                    n += n6;
                    n2 += 0;
                    n3 += n8;
                    ++n4;
                }
                int n9 = 0;
                ++n9;
            }
            if (n4 > 0L) {
                final int n9 = (int)(n / n4);
                final int n10 = 0x0 | (int)(n2 / n4) << 8 | (int)(n3 / n4);
                while (0 < array.length) {
                    if ((array[0] >> 24 & 0xFF) <= 16) {
                        array[0] = n10;
                    }
                    ++n7;
                }
            }
        }
    }
    
    public double getSpriteU16(final float n) {
        return (n - this.minU) / (this.maxU - this.minU) * 16.0f;
    }
    
    public double getSpriteV16(final float n) {
        return (n - this.minV) / (this.maxV - this.minV) * 16.0f;
    }
    
    public void bindSpriteTexture() {
        if (this.glSpriteTextureId < 0) {
            TextureUtil.func_180600_a(this.glSpriteTextureId = TextureUtil.glGenTextures(), this.mipmapLevels, this.width, this.height);
        }
        TextureUtils.bindTexture(this.glSpriteTextureId);
    }
    
    public void deleteSpriteTexture() {
        if (this.glSpriteTextureId >= 0) {
            TextureUtil.deleteTexture(this.glSpriteTextureId);
            this.glSpriteTextureId = -1;
        }
    }
    
    public float toSingleU(float n) {
        n -= this.baseU;
        n *= this.sheetWidth / (float)this.width;
        return n;
    }
    
    public float toSingleV(float n) {
        n -= this.baseV;
        n *= this.sheetHeight / (float)this.height;
        return n;
    }
}
