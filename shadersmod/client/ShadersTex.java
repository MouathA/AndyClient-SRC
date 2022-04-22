package shadersmod.client;

import java.nio.*;
import net.minecraft.util.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import shadersmod.common.*;
import java.awt.image.*;
import javax.imageio.*;
import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class ShadersTex
{
    public static final int initialBufferSize;
    public static ByteBuffer byteBuffer;
    public static IntBuffer intBuffer;
    public static int[] intArray;
    public static final int defBaseTexColor;
    public static final int defNormTexColor;
    public static final int defSpecTexColor;
    public static Map multiTexMap;
    public static TextureMap updatingTextureMap;
    public static TextureAtlasSprite updatingSprite;
    public static MultiTexID updatingTex;
    public static MultiTexID boundTex;
    public static int updatingPage;
    public static String iconName;
    public static IResourceManager resManager;
    static ResourceLocation resLocation;
    static int imageSize;
    private static final String[] llIlIIIllIIlIII;
    private static String[] llIlIIIllIIllII;
    
    static {
        lIIllIlIIlIIIllI();
        lIIllIlIIlIIIlIl();
        initialBufferSize = 1048576;
        defSpecTexColor = 0;
        defBaseTexColor = 0;
        defNormTexColor = -8421377;
        ShadersTex.byteBuffer = BufferUtils.createByteBuffer(4194304);
        ShadersTex.intBuffer = ShadersTex.byteBuffer.asIntBuffer();
        ShadersTex.intArray = new int[1048576];
        ShadersTex.multiTexMap = new HashMap();
        ShadersTex.updatingTextureMap = null;
        ShadersTex.updatingSprite = null;
        ShadersTex.updatingTex = null;
        ShadersTex.boundTex = null;
        ShadersTex.updatingPage = 0;
        ShadersTex.iconName = null;
        ShadersTex.resManager = null;
        ShadersTex.resLocation = null;
        ShadersTex.imageSize = 0;
    }
    
    public static IntBuffer getIntBuffer(final int n) {
        if (ShadersTex.intBuffer.capacity() < n) {
            ShadersTex.byteBuffer = BufferUtils.createByteBuffer(roundUpPOT(n) * 4);
            ShadersTex.intBuffer = ShadersTex.byteBuffer.asIntBuffer();
        }
        return ShadersTex.intBuffer;
    }
    
    public static int[] getIntArray(final int n) {
        if (ShadersTex.intArray == null) {
            ShadersTex.intArray = new int[1048576];
        }
        if (ShadersTex.intArray.length < n) {
            ShadersTex.intArray = new int[roundUpPOT(n)];
        }
        return ShadersTex.intArray;
    }
    
    public static int roundUpPOT(final int n) {
        final int n2 = n - 1;
        final int n3 = n2 | n2 >> 1;
        final int n4 = n3 | n3 >> 2;
        final int n5 = n4 | n4 >> 4;
        final int n6 = n5 | n5 >> 8;
        return (n6 | n6 >> 16) + 1;
    }
    
    public static int log2(int n) {
        int n2 = 0;
        if ((n & 0xFFFF0000) != 0x0) {
            n2 += 16;
            n >>= 16;
        }
        if ((n & 0xFF00) != 0x0) {
            n2 += 8;
            n >>= 8;
        }
        if ((n & 0xF0) != 0x0) {
            n2 += 4;
            n >>= 4;
        }
        if ((n & 0x6) != 0x0) {
            n2 += 2;
            n >>= 2;
        }
        if ((n & 0x2) != 0x0) {
            ++n2;
        }
        return n2;
    }
    
    public static IntBuffer fillIntBuffer(final int n, final int n2) {
        getIntArray(n);
        getIntBuffer(n);
        Arrays.fill(ShadersTex.intArray, 0, n, n2);
        ShadersTex.intBuffer.put(ShadersTex.intArray, 0, n);
        return ShadersTex.intBuffer;
    }
    
    public static int[] createAIntImage(final int n) {
        final int[] array = new int[n * 3];
        Arrays.fill(array, 0, n, 0);
        Arrays.fill(array, n, n * 2, -8421377);
        Arrays.fill(array, n * 2, n * 3, 0);
        return array;
    }
    
    public static int[] createAIntImage(final int n, final int n2) {
        final int[] array = new int[n * 3];
        Arrays.fill(array, 0, n, n2);
        Arrays.fill(array, n, n * 2, -8421377);
        Arrays.fill(array, n * 2, n * 3, 0);
        return array;
    }
    
    public static MultiTexID getMultiTexID(final AbstractTexture abstractTexture) {
        MultiTexID multiTex = abstractTexture.multiTex;
        if (multiTex == null) {
            final int glTextureId = abstractTexture.getGlTextureId();
            multiTex = (MultiTexID)ShadersTex.multiTexMap.get(glTextureId);
            if (multiTex == null) {
                multiTex = new MultiTexID(glTextureId, GL11.glGenTextures(), GL11.glGenTextures());
                ShadersTex.multiTexMap.put(glTextureId, multiTex);
            }
            abstractTexture.multiTex = multiTex;
        }
        return multiTex;
    }
    
    public static void deleteTextures(final AbstractTexture abstractTexture, final int n) {
        final MultiTexID multiTex = abstractTexture.multiTex;
        if (multiTex != null) {
            abstractTexture.multiTex = null;
            ShadersTex.multiTexMap.remove(multiTex.base);
            GlStateManager.func_179150_h(multiTex.norm);
            GlStateManager.func_179150_h(multiTex.spec);
            if (multiTex.base != n) {
                SMCLog.warning(ShadersTex.llIlIIIllIIlIII[0] + multiTex.base + ShadersTex.llIlIIIllIIlIII[1] + n);
                GlStateManager.func_179150_h(multiTex.base);
            }
        }
    }
    
    public static void bindNSTextures(final int n, final int n2) {
        if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
            GlStateManager.setActiveTexture(33986);
            GlStateManager.func_179144_i(n);
            GlStateManager.setActiveTexture(33987);
            GlStateManager.func_179144_i(n2);
            GlStateManager.setActiveTexture(33984);
        }
    }
    
    public static void bindNSTextures(final MultiTexID multiTexID) {
        bindNSTextures(multiTexID.norm, multiTexID.spec);
    }
    
    public static void bindTextures(final int n, final int n2, final int n3) {
        if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
            GlStateManager.setActiveTexture(33986);
            GlStateManager.func_179144_i(n2);
            GlStateManager.setActiveTexture(33987);
            GlStateManager.func_179144_i(n3);
            GlStateManager.setActiveTexture(33984);
        }
        GlStateManager.func_179144_i(n);
    }
    
    public static void bindTextures(final MultiTexID boundTex) {
        ShadersTex.boundTex = boundTex;
        if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
            if (Shaders.configNormalMap) {
                GlStateManager.setActiveTexture(33986);
                GlStateManager.func_179144_i(boundTex.norm);
            }
            if (Shaders.configSpecularMap) {
                GlStateManager.setActiveTexture(33987);
                GlStateManager.func_179144_i(boundTex.spec);
            }
            GlStateManager.setActiveTexture(33984);
        }
        GlStateManager.func_179144_i(boundTex.base);
    }
    
    public static void bindTexture(final ITextureObject textureObject) {
        textureObject.getGlTextureId();
        if (textureObject instanceof TextureMap) {
            Shaders.atlasSizeX = ((TextureMap)textureObject).atlasWidth;
            Shaders.atlasSizeY = ((TextureMap)textureObject).atlasHeight;
            bindTextures(textureObject.getMultiTexID());
        }
        else {
            Shaders.atlasSizeX = 0;
            Shaders.atlasSizeY = 0;
            bindTextures(textureObject.getMultiTexID());
        }
    }
    
    public static void bindTextureMapForUpdateAndRender(final TextureManager textureManager, final ResourceLocation resourceLocation) {
        final TextureMap textureMap = (TextureMap)textureManager.getTexture(resourceLocation);
        Shaders.atlasSizeX = textureMap.atlasWidth;
        Shaders.atlasSizeY = textureMap.atlasHeight;
        bindTextures(ShadersTex.updatingTex = textureMap.getMultiTexID());
    }
    
    public static void bindTextures(final int n) {
        bindTextures(ShadersTex.multiTexMap.get(n));
    }
    
    public static void initDynamicTexture(final int n, final int n2, final int n3, final DynamicTexture dynamicTexture) {
        final MultiTexID multiTexID = dynamicTexture.getMultiTexID();
        final int[] textureData = dynamicTexture.getTextureData();
        final int n4 = n2 * n3;
        Arrays.fill(textureData, n4, n4 * 2, -8421377);
        Arrays.fill(textureData, n4 * 2, n4 * 3, 0);
        TextureUtil.allocateTexture(multiTexID.base, n2, n3);
        TextureUtil.func_147954_b(false, false);
        TextureUtil.setTextureClamped(false);
        TextureUtil.allocateTexture(multiTexID.norm, n2, n3);
        TextureUtil.func_147954_b(false, false);
        TextureUtil.setTextureClamped(false);
        TextureUtil.allocateTexture(multiTexID.spec, n2, n3);
        TextureUtil.func_147954_b(false, false);
        TextureUtil.setTextureClamped(false);
        GlStateManager.func_179144_i(multiTexID.base);
    }
    
    public static void updateDynamicTexture(final int n, final int[] array, final int n2, final int n3, final DynamicTexture dynamicTexture) {
        final MultiTexID multiTexID = dynamicTexture.getMultiTexID();
        GlStateManager.func_179144_i(multiTexID.base);
        updateDynTexSubImage1(array, n2, n3, 0, 0, 0);
        GlStateManager.func_179144_i(multiTexID.norm);
        updateDynTexSubImage1(array, n2, n3, 0, 0, 1);
        GlStateManager.func_179144_i(multiTexID.spec);
        updateDynTexSubImage1(array, n2, n3, 0, 0, 2);
        GlStateManager.func_179144_i(multiTexID.base);
    }
    
    public static void updateDynTexSubImage1(final int[] array, final int n, final int n2, final int n3, final int n4, final int n5) {
        final int n6 = n * n2;
        final IntBuffer intBuffer = getIntBuffer(n6);
        intBuffer.clear();
        final int n7 = n5 * n6;
        if (array.length >= n7 + n6) {
            intBuffer.put(array, n7, n6).position(0).limit(n6);
            GL11.glTexSubImage2D(3553, 0, n3, n4, n, n2, 32993, 33639, intBuffer);
            intBuffer.clear();
        }
    }
    
    public static ITextureObject createDefaultTexture() {
        final DynamicTexture dynamicTexture = new DynamicTexture(1, 1);
        dynamicTexture.getTextureData()[0] = -1;
        dynamicTexture.updateDynamicTexture();
        return dynamicTexture;
    }
    
    public static void allocateTextureMap(final int n, final int n2, final int atlasWidth, final int atlasHeight, final Stitcher stitcher, final TextureMap updatingTextureMap) {
        SMCLog.info(ShadersTex.llIlIIIllIIlIII[2] + n2 + ShadersTex.llIlIIIllIIlIII[3] + atlasWidth + ShadersTex.llIlIIIllIIlIII[4] + atlasHeight + ShadersTex.llIlIIIllIIlIII[5]);
        ShadersTex.updatingTextureMap = updatingTextureMap;
        updatingTextureMap.atlasWidth = atlasWidth;
        updatingTextureMap.atlasHeight = atlasHeight;
        final MultiTexID multiTexID = ShadersTex.updatingTex = getMultiTexID(updatingTextureMap);
        TextureUtil.func_180600_a(multiTexID.base, n2, atlasWidth, atlasHeight);
        if (Shaders.configNormalMap) {
            TextureUtil.func_180600_a(multiTexID.norm, n2, atlasWidth, atlasHeight);
        }
        if (Shaders.configSpecularMap) {
            TextureUtil.func_180600_a(multiTexID.spec, n2, atlasWidth, atlasHeight);
        }
        GlStateManager.func_179144_i(n);
    }
    
    public static TextureAtlasSprite setSprite(final TextureAtlasSprite updatingSprite) {
        return ShadersTex.updatingSprite = updatingSprite;
    }
    
    public static String setIconName(final String iconName) {
        return ShadersTex.iconName = iconName;
    }
    
    public static void uploadTexSubForLoadAtlas(final int[][] array, final int n, final int n2, final int n3, final int n4, final boolean b, final boolean b2) {
        TextureUtil.uploadTextureMipmap(array, n, n2, n3, n4, b, b2);
        final boolean b3 = false;
        if (Shaders.configNormalMap) {
            final int[][] imageAndMipmaps = readImageAndMipmaps(String.valueOf(ShadersTex.iconName) + ShadersTex.llIlIIIllIIlIII[6], n, n2, array.length, b3, -8421377);
            GlStateManager.func_179144_i(ShadersTex.updatingTex.norm);
            TextureUtil.uploadTextureMipmap(imageAndMipmaps, n, n2, n3, n4, b, b2);
        }
        if (Shaders.configSpecularMap) {
            final int[][] imageAndMipmaps2 = readImageAndMipmaps(String.valueOf(ShadersTex.iconName) + ShadersTex.llIlIIIllIIlIII[7], n, n2, array.length, b3, 0);
            GlStateManager.func_179144_i(ShadersTex.updatingTex.spec);
            TextureUtil.uploadTextureMipmap(imageAndMipmaps2, n, n2, n3, n4, b, b2);
        }
        GlStateManager.func_179144_i(ShadersTex.updatingTex.base);
    }
    
    public static int[][] readImageAndMipmaps(final String s, final int n, final int n2, final int n3, final boolean b, final int n4) {
        final int[][] array = new int[n3][];
        final int[] array2 = array[0] = new int[n * n2];
        boolean b2 = false;
        final BufferedImage image = readImage(ShadersTex.updatingTextureMap.completeResourceLocation(new ResourceLocation(s), 0));
        if (image != null) {
            final int width = image.getWidth();
            image.getHeight();
            if (width + (b ? 16 : 0) == n) {
                b2 = true;
                image.getRGB(0, 0, width, width, array2, 0, width);
            }
        }
        if (!b2) {
            Arrays.fill(array2, n4);
        }
        GlStateManager.func_179144_i(ShadersTex.updatingTex.spec);
        return genMipmapsSimple(array.length - 1, n, array);
    }
    
    public static BufferedImage readImage(final ResourceLocation resourceLocation) {
        try {
            final IResource resource = ShadersTex.resManager.getResource(resourceLocation);
            if (resource == null) {
                return null;
            }
            final InputStream inputStream = resource.getInputStream();
            if (inputStream == null) {
                return null;
            }
            final BufferedImage read = ImageIO.read(inputStream);
            inputStream.close();
            return read;
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    public static int[][] genMipmapsSimple(final int n, final int n2, final int[][] array) {
        for (int i = 1; i <= n; ++i) {
            if (array[i] == null) {
                final int n3 = n2 >> i;
                final int n4 = n3 * 2;
                final int[] array2 = array[i - 1];
                final int n5 = i;
                final int[] array3 = new int[n3 * n3];
                array[n5] = array3;
                final int[] array4 = array3;
                for (int j = 0; j < n3; ++j) {
                    for (int k = 0; k < n3; ++k) {
                        final int n6 = j * 2 * n4 + k * 2;
                        array4[j * n3 + k] = blend4Simple(array2[n6], array2[n6 + 1], array2[n6 + n4], array2[n6 + n4 + 1]);
                    }
                }
            }
        }
        return array;
    }
    
    public static void uploadTexSub(final int[][] array, final int n, final int n2, final int n3, final int n4, final boolean b, final boolean b2) {
        TextureUtil.uploadTextureMipmap(array, n, n2, n3, n4, b, b2);
        if (Shaders.configNormalMap || Shaders.configSpecularMap) {
            if (Shaders.configNormalMap) {
                GlStateManager.func_179144_i(ShadersTex.updatingTex.norm);
                uploadTexSub1(array, n, n2, n3, n4, 1);
            }
            if (Shaders.configSpecularMap) {
                GlStateManager.func_179144_i(ShadersTex.updatingTex.spec);
                uploadTexSub1(array, n, n2, n3, n4, 2);
            }
            GlStateManager.func_179144_i(ShadersTex.updatingTex.base);
        }
    }
    
    public static void uploadTexSub1(final int[][] array, final int n, final int n2, final int n3, final int n4, final int n5) {
        final IntBuffer intBuffer = getIntBuffer(n * n2);
        for (int length = array.length, n6 = 0, n7 = n, n8 = n2, n9 = n3, n10 = n4; n7 > 0 && n8 > 0 && n6 < length; n7 >>= 1, n8 >>= 1, n9 >>= 1, n10 >>= 1, ++n6) {
            final int n11 = n7 * n8;
            final int[] array2 = array[n6];
            intBuffer.clear();
            if (array2.length >= n11 * (n5 + 1)) {
                intBuffer.put(array2, n11 * n5, n11).position(0).limit(n11);
                GL11.glTexSubImage2D(3553, n6, n9, n10, n7, n8, 32993, 33639, intBuffer);
            }
        }
        intBuffer.clear();
    }
    
    public static int blend4Alpha(final int n, final int n2, final int n3, final int n4) {
        int n5 = n >>> 24 & 0xFF;
        int n6 = n2 >>> 24 & 0xFF;
        int n7 = n3 >>> 24 & 0xFF;
        int n8 = n4 >>> 24 & 0xFF;
        final int n9 = n5 + n6 + n7 + n8;
        final int n10 = (n9 + 2) / 4;
        int n11;
        if (n9 != 0) {
            n11 = n9;
        }
        else {
            n11 = 4;
            n5 = 1;
            n6 = 1;
            n7 = 1;
            n8 = 1;
        }
        final int n12 = (n11 + 1) / 2;
        return n10 << 24 | ((n >>> 16 & 0xFF) * n5 + (n2 >>> 16 & 0xFF) * n6 + (n3 >>> 16 & 0xFF) * n7 + (n4 >>> 16 & 0xFF) * n8 + n12) / n11 << 16 | ((n >>> 8 & 0xFF) * n5 + (n2 >>> 8 & 0xFF) * n6 + (n3 >>> 8 & 0xFF) * n7 + (n4 >>> 8 & 0xFF) * n8 + n12) / n11 << 8 | ((n >>> 0 & 0xFF) * n5 + (n2 >>> 0 & 0xFF) * n6 + (n3 >>> 0 & 0xFF) * n7 + (n4 >>> 0 & 0xFF) * n8 + n12) / n11 << 0;
    }
    
    public static int blend4Simple(final int n, final int n2, final int n3, final int n4) {
        return ((n >>> 24 & 0xFF) + (n2 >>> 24 & 0xFF) + (n3 >>> 24 & 0xFF) + (n4 >>> 24 & 0xFF) + 2) / 4 << 24 | ((n >>> 16 & 0xFF) + (n2 >>> 16 & 0xFF) + (n3 >>> 16 & 0xFF) + (n4 >>> 16 & 0xFF) + 2) / 4 << 16 | ((n >>> 8 & 0xFF) + (n2 >>> 8 & 0xFF) + (n3 >>> 8 & 0xFF) + (n4 >>> 8 & 0xFF) + 2) / 4 << 8 | ((n >>> 0 & 0xFF) + (n2 >>> 0 & 0xFF) + (n3 >>> 0 & 0xFF) + (n4 >>> 0 & 0xFF) + 2) / 4 << 0;
    }
    
    public static void genMipmapAlpha(final int[] array, final int n, final int n2, final int n3) {
        Math.min(n2, n3);
        int n4 = n;
        int i = n2;
        int n5 = n3;
        int n6 = 0;
        int n7 = 0;
        int j = 0;
        while (i > 1) {
            if (n5 <= 1) {
                break;
            }
            n6 = n4 + i * n5;
            n7 = i / 2;
            final int n8 = n5 / 2;
            for (int k = 0; k < n8; ++k) {
                final int n9 = n6 + k * n7;
                final int n10 = n4 + k * 2 * i;
                for (int l = 0; l < n7; ++l) {
                    array[n9 + l] = blend4Alpha(array[n10 + l * 2], array[n10 + l * 2 + 1], array[n10 + i + l * 2], array[n10 + i + l * 2 + 1]);
                }
            }
            ++j;
            i = n7;
            n5 = n8;
            n4 = n6;
        }
        while (j > 0) {
            --j;
            final int n11 = n2 >> j;
            final int n12 = n3 >> j;
            int n14;
            final int n13 = n14 = n6 - n11 * n12;
            for (int n15 = 0; n15 < n12; ++n15) {
                for (int n16 = 0; n16 < n11; ++n16) {
                    if (array[n14] == 0) {
                        array[n14] = (array[n6 + n15 / 2 * n7 + n16 / 2] & 0xFFFFFF);
                    }
                    ++n14;
                }
            }
            n6 = n13;
            n7 = n11;
        }
    }
    
    public static void genMipmapSimple(final int[] array, final int n, final int n2, final int n3) {
        Math.min(n2, n3);
        int n4 = n;
        int i = n2;
        int n5 = n3;
        int n6 = 0;
        int n7 = 0;
        int j = 0;
        while (i > 1) {
            if (n5 <= 1) {
                break;
            }
            n6 = n4 + i * n5;
            n7 = i / 2;
            final int n8 = n5 / 2;
            for (int k = 0; k < n8; ++k) {
                final int n9 = n6 + k * n7;
                final int n10 = n4 + k * 2 * i;
                for (int l = 0; l < n7; ++l) {
                    array[n9 + l] = blend4Simple(array[n10 + l * 2], array[n10 + l * 2 + 1], array[n10 + i + l * 2], array[n10 + i + l * 2 + 1]);
                }
            }
            ++j;
            i = n7;
            n5 = n8;
            n4 = n6;
        }
        while (j > 0) {
            --j;
            final int n11 = n2 >> j;
            final int n12 = n3 >> j;
            int n14;
            final int n13 = n14 = n6 - n11 * n12;
            for (int n15 = 0; n15 < n12; ++n15) {
                for (int n16 = 0; n16 < n11; ++n16) {
                    if (array[n14] == 0) {
                        array[n14] = (array[n6 + n15 / 2 * n7 + n16 / 2] & 0xFFFFFF);
                    }
                    ++n14;
                }
            }
            n6 = n13;
            n7 = n11;
        }
    }
    
    public static boolean isSemiTransparent(final int[] array, final int n, final int n2) {
        final int n3 = n * n2;
        if (array[0] >>> 24 == 255 && array[n3 - 1] == 0) {
            return true;
        }
        for (int i = 0; i < n3; ++i) {
            final int n4 = array[i] >>> 24;
            if (n4 != 0 && n4 != 255) {
                return true;
            }
        }
        return false;
    }
    
    public static void updateSubTex1(final int[] array, final int n, final int n2, final int n3, final int n4) {
        int n5 = 0;
        for (int n6 = n, n7 = n2, n8 = n3, n9 = n4; n6 > 0 && n7 > 0; n6 /= 2, n7 /= 2, n8 /= 2, n9 /= 2) {
            GL11.glCopyTexSubImage2D(3553, n5, n8, n9, 0, 0, n6, n7);
            ++n5;
        }
    }
    
    public static void setupTexture(final MultiTexID multiTexID, final int[] array, final int n, final int n2, final boolean b, final boolean b2) {
        final int n3 = b ? 9729 : 9728;
        final int n4 = b2 ? 10496 : 10497;
        final int n5 = n * n2;
        final IntBuffer intBuffer = getIntBuffer(n5);
        intBuffer.clear();
        intBuffer.put(array, 0, n5).position(0).limit(n5);
        GlStateManager.func_179144_i(multiTexID.base);
        GL11.glTexImage2D(3553, 0, 6408, n, n2, 0, 32993, 33639, intBuffer);
        GL11.glTexParameteri(3553, 10241, n3);
        GL11.glTexParameteri(3553, 10240, n3);
        GL11.glTexParameteri(3553, 10242, n4);
        GL11.glTexParameteri(3553, 10243, n4);
        intBuffer.put(array, n5, n5).position(0).limit(n5);
        GlStateManager.func_179144_i(multiTexID.norm);
        GL11.glTexImage2D(3553, 0, 6408, n, n2, 0, 32993, 33639, intBuffer);
        GL11.glTexParameteri(3553, 10241, n3);
        GL11.glTexParameteri(3553, 10240, n3);
        GL11.glTexParameteri(3553, 10242, n4);
        GL11.glTexParameteri(3553, 10243, n4);
        intBuffer.put(array, n5 * 2, n5).position(0).limit(n5);
        GlStateManager.func_179144_i(multiTexID.spec);
        GL11.glTexImage2D(3553, 0, 6408, n, n2, 0, 32993, 33639, intBuffer);
        GL11.glTexParameteri(3553, 10241, n3);
        GL11.glTexParameteri(3553, 10240, n3);
        GL11.glTexParameteri(3553, 10242, n4);
        GL11.glTexParameteri(3553, 10243, n4);
        GlStateManager.func_179144_i(multiTexID.base);
    }
    
    public static void updateSubImage(final MultiTexID multiTexID, final int[] array, final int n, final int n2, final int n3, final int n4, final boolean b, final boolean b2) {
        final int n5 = n * n2;
        final IntBuffer intBuffer = getIntBuffer(n5);
        intBuffer.clear();
        intBuffer.put(array, 0, n5);
        intBuffer.position(0).limit(n5);
        GlStateManager.func_179144_i(multiTexID.base);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GL11.glTexSubImage2D(3553, 0, n3, n4, n, n2, 32993, 33639, intBuffer);
        if (array.length == n5 * 3) {
            intBuffer.clear();
            intBuffer.put(array, n5, n5).position(0);
            intBuffer.position(0).limit(n5);
        }
        GlStateManager.func_179144_i(multiTexID.norm);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GL11.glTexSubImage2D(3553, 0, n3, n4, n, n2, 32993, 33639, intBuffer);
        if (array.length == n5 * 3) {
            intBuffer.clear();
            intBuffer.put(array, n5 * 2, n5);
            intBuffer.position(0).limit(n5);
        }
        GlStateManager.func_179144_i(multiTexID.spec);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GL11.glTexSubImage2D(3553, 0, n3, n4, n, n2, 32993, 33639, intBuffer);
        GlStateManager.setActiveTexture(33984);
    }
    
    public static ResourceLocation getNSMapLocation(final ResourceLocation resourceLocation, final String s) {
        return new ResourceLocation(resourceLocation.getResourceDomain(), String.valueOf(resourceLocation.getResourcePath().split(ShadersTex.llIlIIIllIIlIII[8])[0]) + ShadersTex.llIlIIIllIIlIII[9] + s + ShadersTex.llIlIIIllIIlIII[10]);
    }
    
    public static void loadNSMap(final IResourceManager resourceManager, final ResourceLocation resourceLocation, final int n, final int n2, final int[] array) {
        if (Shaders.configNormalMap) {
            loadNSMap1(resourceManager, getNSMapLocation(resourceLocation, ShadersTex.llIlIIIllIIlIII[11]), n, n2, array, n * n2, -8421377);
        }
        if (Shaders.configSpecularMap) {
            loadNSMap1(resourceManager, getNSMapLocation(resourceLocation, ShadersTex.llIlIIIllIIlIII[12]), n, n2, array, n * n2 * 2, 0);
        }
    }
    
    public static void loadNSMap1(final IResourceManager resourceManager, final ResourceLocation resourceLocation, final int n, final int n2, final int[] array, final int n3, final int n4) {
        boolean b = false;
        try {
            final BufferedImage read = ImageIO.read(resourceManager.getResource(resourceLocation).getInputStream());
            if (read != null && read.getWidth() == n && read.getHeight() == n2) {
                read.getRGB(0, 0, n, n2, array, n3, n);
                b = true;
            }
        }
        catch (IOException ex) {}
        if (!b) {
            Arrays.fill(array, n3, n3 + n * n2, n4);
        }
    }
    
    public static int loadSimpleTexture(final int n, final BufferedImage bufferedImage, final boolean b, final boolean b2, final IResourceManager resourceManager, final ResourceLocation resourceLocation, final MultiTexID multiTexID) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int[] intArray = getIntArray(width * height * 3);
        bufferedImage.getRGB(0, 0, width, height, intArray, 0, width);
        loadNSMap(resourceManager, resourceLocation, width, height, intArray);
        setupTexture(multiTexID, intArray, width, height, b, b2);
        return n;
    }
    
    public static void mergeImage(final int[] array, final int n, final int n2, final int n3) {
    }
    
    public static int blendColor(final int n, final int n2, final int n3) {
        final int n4 = 255 - n3;
        return ((n >>> 24 & 0xFF) * n3 + (n2 >>> 24 & 0xFF) * n4) / 255 << 24 | ((n >>> 16 & 0xFF) * n3 + (n2 >>> 16 & 0xFF) * n4) / 255 << 16 | ((n >>> 8 & 0xFF) * n3 + (n2 >>> 8 & 0xFF) * n4) / 255 << 8 | ((n >>> 0 & 0xFF) * n3 + (n2 >>> 0 & 0xFF) * n4) / 255 << 0;
    }
    
    public static void loadLayeredTexture(final LayeredTexture layeredTexture, final IResourceManager resourceManager, final List list) {
        int width = 0;
        int height = 0;
        int n = 0;
        int[] aIntImage = null;
        for (final String s : list) {
            if (s != null) {
                try {
                    final ResourceLocation resourceLocation = new ResourceLocation(s);
                    final BufferedImage read = ImageIO.read(resourceManager.getResource(resourceLocation).getInputStream());
                    if (n == 0) {
                        width = read.getWidth();
                        height = read.getHeight();
                        n = width * height;
                        aIntImage = createAIntImage(n, 0);
                    }
                    final int[] intArray = getIntArray(n * 3);
                    read.getRGB(0, 0, width, height, intArray, 0, width);
                    loadNSMap(resourceManager, resourceLocation, width, height, intArray);
                    for (int i = 0; i < n; ++i) {
                        final int n2 = intArray[i] >>> 24 & 0xFF;
                        aIntImage[n * 0 + i] = blendColor(intArray[n * 0 + i], aIntImage[n * 0 + i], n2);
                        aIntImage[n * 1 + i] = blendColor(intArray[n * 1 + i], aIntImage[n * 1 + i], n2);
                        aIntImage[n * 2 + i] = blendColor(intArray[n * 2 + i], aIntImage[n * 2 + i], n2);
                    }
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        setupTexture(layeredTexture.getMultiTexID(), aIntImage, width, height, false, false);
    }
    
    static void updateTextureMinMagFilter() {
        final ITextureObject texture = Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture);
        if (texture != null) {
            final MultiTexID multiTexID = texture.getMultiTexID();
            GlStateManager.func_179144_i(multiTexID.base);
            GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilB]);
            GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilB]);
            GlStateManager.func_179144_i(multiTexID.norm);
            GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilN]);
            GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilN]);
            GlStateManager.func_179144_i(multiTexID.spec);
            GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilS]);
            GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilS]);
            GlStateManager.func_179144_i(0);
        }
    }
    
    public static IResource loadResource(final IResourceManager resManager, final ResourceLocation resLocation) throws IOException {
        ShadersTex.resManager = resManager;
        ShadersTex.resLocation = resLocation;
        return resManager.getResource(resLocation);
    }
    
    public static int[] loadAtlasSprite(final BufferedImage bufferedImage, final int n, final int n2, final int n3, final int n4, final int[] array, final int n5, final int n6) {
        ShadersTex.imageSize = n3 * n4;
        bufferedImage.getRGB(n, n2, n3, n4, array, n5, n6);
        loadNSMap(ShadersTex.resManager, ShadersTex.resLocation, n3, n4, array);
        return array;
    }
    
    public static int[][] getFrameTexData(final int[][] array, final int n, final int n2, final int n3) {
        final int length = array.length;
        final int[][] array2 = new int[length][];
        for (int i = 0; i < length; ++i) {
            final int[] array3 = array[i];
            if (array3 != null) {
                final int n4 = (n >> i) * (n2 >> i);
                final int[] array4 = new int[n4 * 3];
                array2[i] = array4;
                final int n5 = array3.length / 3;
                final int n6 = n4 * n3;
                final int n7 = 0;
                System.arraycopy(array3, n6, array4, n7, n4);
                final int n8 = n6 + n5;
                final int n9 = n7 + n4;
                System.arraycopy(array3, n8, array4, n9, n4);
                System.arraycopy(array3, n8 + n5, array4, n9 + n4, n4);
            }
        }
        return array2;
    }
    
    public static int[][] prepareAF(final TextureAtlasSprite textureAtlasSprite, final int[][] array, final int n, final int n2) {
        return array;
    }
    
    public static void fixTransparentColor(final TextureAtlasSprite textureAtlasSprite, final int[] array) {
    }
    
    private static void lIIllIlIIlIIIlIl() {
        (llIlIIIllIIlIII = new String[13])[0] = lIIllIlIIIllllIl(ShadersTex.llIlIIIllIIllII[0], ShadersTex.llIlIIIllIIllII[1]);
        ShadersTex.llIlIIIllIIlIII[1] = lIIllIlIIIlllllI(ShadersTex.llIlIIIllIIllII[2], ShadersTex.llIlIIIllIIllII[3]);
        ShadersTex.llIlIIIllIIlIII[2] = lIIllIlIIIllllll(ShadersTex.llIlIIIllIIllII[4], ShadersTex.llIlIIIllIIllII[5]);
        ShadersTex.llIlIIIllIIlIII[3] = lIIllIlIIlIIIIII(ShadersTex.llIlIIIllIIllII[6], ShadersTex.llIlIIIllIIllII[7]);
        ShadersTex.llIlIIIllIIlIII[4] = lIIllIlIIIllllll(ShadersTex.llIlIIIllIIllII[8], ShadersTex.llIlIIIllIIllII[9]);
        ShadersTex.llIlIIIllIIlIII[5] = lIIllIlIIIlllllI(ShadersTex.llIlIIIllIIllII[10], ShadersTex.llIlIIIllIIllII[11]);
        ShadersTex.llIlIIIllIIlIII[6] = lIIllIlIIlIIIIII(ShadersTex.llIlIIIllIIllII[12], ShadersTex.llIlIIIllIIllII[13]);
        ShadersTex.llIlIIIllIIlIII[7] = lIIllIlIIIllllIl(ShadersTex.llIlIIIllIIllII[14], ShadersTex.llIlIIIllIIllII[15]);
        ShadersTex.llIlIIIllIIlIII[8] = lIIllIlIIIllllll(ShadersTex.llIlIIIllIIllII[16], ShadersTex.llIlIIIllIIllII[17]);
        ShadersTex.llIlIIIllIIlIII[9] = lIIllIlIIlIIIIII(ShadersTex.llIlIIIllIIllII[18], ShadersTex.llIlIIIllIIllII[19]);
        ShadersTex.llIlIIIllIIlIII[10] = lIIllIlIIIllllIl(ShadersTex.llIlIIIllIIllII[20], ShadersTex.llIlIIIllIIllII[21]);
        ShadersTex.llIlIIIllIIlIII[11] = lIIllIlIIIllllIl(ShadersTex.llIlIIIllIIllII[22], ShadersTex.llIlIIIllIIllII[23]);
        ShadersTex.llIlIIIllIIlIII[12] = lIIllIlIIlIIIIII(ShadersTex.llIlIIIllIIllII[24], ShadersTex.llIlIIIllIIllII[25]);
        ShadersTex.llIlIIIllIIllII = null;
    }
    
    private static void lIIllIlIIlIIIllI() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        ShadersTex.llIlIIIllIIllII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lIIllIlIIIlllllI(final String s, final String s2) {
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
    
    private static String lIIllIlIIIllllll(String s, final String s2) {
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
    
    private static String lIIllIlIIlIIIIII(final String s, final String s2) {
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
    
    private static String lIIllIlIIIllllIl(final String s, final String s2) {
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
}
