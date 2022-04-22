package net.minecraft.client.renderer.texture;

import java.nio.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import optifine.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import javax.imageio.*;
import org.apache.commons.io.*;
import java.io.*;
import org.lwjgl.*;
import java.awt.image.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class TextureUtil
{
    private static final Logger logger;
    private static final IntBuffer dataBuffer;
    public static final DynamicTexture missingTexture;
    public static final int[] missingTextureData;
    private static final int[] field_147957_g;
    private static final String __OBFID;
    private static final String[] llIllllIIIllllI;
    private static String[] llIllllIIIlllll;
    
    static {
        lIlIIIllllIllIll();
        lIlIIIllllIllIlI();
        __OBFID = TextureUtil.llIllllIIIllllI[0];
        logger = LogManager.getLogger();
        dataBuffer = GLAllocation.createDirectIntBuffer(4194304);
        missingTexture = new DynamicTexture(16, 16);
        missingTextureData = TextureUtil.missingTexture.getTextureData();
        final int[] array = { -524040, -524040, -524040, -524040, -524040, -524040, -524040, -524040 };
        final int[] array2 = { -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216 };
        final int length = array.length;
        for (int i = 0; i < 16; ++i) {
            System.arraycopy((i < length) ? array : array2, 0, TextureUtil.missingTextureData, 16 * i, length);
            System.arraycopy((i < length) ? array2 : array, 0, TextureUtil.missingTextureData, 16 * i + length, length);
        }
        TextureUtil.missingTexture.updateDynamicTexture();
        field_147957_g = new int[4];
    }
    
    public static int glGenTextures() {
        return GlStateManager.func_179146_y();
    }
    
    public static void deleteTexture(final int n) {
        GlStateManager.func_179150_h(n);
    }
    
    public static int uploadTextureImage(final int n, final BufferedImage bufferedImage) {
        return uploadTextureImageAllocate(n, bufferedImage, false, false);
    }
    
    public static void uploadTexture(final int n, final int[] array, final int n2, final int n3) {
        bindTexture(n);
        uploadTextureSub(0, array, n2, n3, 0, 0, false, false, false);
    }
    
    public static int[][] generateMipmapData(final int n, final int n2, final int[][] array) {
        final int[][] array2 = new int[n + 1][];
        array2[0] = array[0];
        if (n > 0) {
            boolean b = false;
            for (int i = 0; i < array.length; ++i) {
                if (array[0][i] >> 24 == 0) {
                    b = true;
                    break;
                }
            }
            for (int j = 1; j <= n; ++j) {
                if (array[j] != null) {
                    array2[j] = array[j];
                }
                else {
                    final int[] array3 = array2[j - 1];
                    final int[] array4 = new int[array3.length >> 2];
                    final int n3 = n2 >> j;
                    final int n4 = array4.length / n3;
                    final int n5 = n3 << 1;
                    for (int k = 0; k < n3; ++k) {
                        for (int l = 0; l < n4; ++l) {
                            final int n6 = 2 * (k + l * n5);
                            array4[k + l * n3] = func_147943_a(array3[n6 + 0], array3[n6 + 1], array3[n6 + 0 + n5], array3[n6 + 1 + n5], b);
                        }
                    }
                    array2[j] = array4;
                }
            }
        }
        return array2;
    }
    
    private static int func_147943_a(final int n, final int n2, final int n3, final int n4, final boolean b) {
        return Mipmaps.alphaBlend(n, n2, n3, n4);
    }
    
    private static int func_147944_a(final int n, final int n2, final int n3, final int n4, final int n5) {
        return (int)((float)Math.pow(((float)Math.pow((n >> n5 & 0xFF) / 255.0f, 2.2) + (float)Math.pow((n2 >> n5 & 0xFF) / 255.0f, 2.2) + (float)Math.pow((n3 >> n5 & 0xFF) / 255.0f, 2.2) + (float)Math.pow((n4 >> n5 & 0xFF) / 255.0f, 2.2)) * 0.25, 0.45454545454545453) * 255.0);
    }
    
    public static void uploadTextureMipmap(final int[][] array, final int n, final int n2, final int n3, final int n4, final boolean b, final boolean b2) {
        for (int i = 0; i < array.length; ++i) {
            uploadTextureSub(i, array[i], n >> i, n2 >> i, n3 >> i, n4 >> i, b, b2, array.length > 1);
        }
    }
    
    private static void uploadTextureSub(final int n, final int[] array, final int n2, final int n3, final int n4, final int n5, final boolean b, final boolean textureClamped, final boolean b2) {
        final int n6 = 4194304 / n2;
        func_147954_b(b, b2);
        setTextureClamped(textureClamped);
        int min;
        for (int i = 0; i < n2 * n3; i += n2 * min) {
            final int n7 = i / n2;
            min = Math.min(n6, n3 - n7);
            copyToBufferPos(array, i, n2 * min);
            GL11.glTexSubImage2D(3553, n, n4, n5 + n7, n2, min, 32993, 33639, TextureUtil.dataBuffer);
        }
    }
    
    public static int uploadTextureImageAllocate(final int n, final BufferedImage bufferedImage, final boolean b, final boolean b2) {
        allocateTexture(n, bufferedImage.getWidth(), bufferedImage.getHeight());
        return uploadTextureImageSub(n, bufferedImage, 0, 0, b, b2);
    }
    
    public static void allocateTexture(final int n, final int n2, final int n3) {
        func_180600_a(n, 0, n2, n3);
    }
    
    public static void func_180600_a(final int n, final int n2, final int n3, final int n4) {
        Class<TextureUtil> targetClass = TextureUtil.class;
        if (Reflector.SplashScreen.exists()) {
            targetClass = (Class<TextureUtil>)Reflector.SplashScreen.getTargetClass();
        }
        synchronized (targetClass) {
            deleteTexture(n);
            bindTexture(n);
        }
        if (n2 >= 0) {
            GL11.glTexParameteri(3553, 33085, n2);
            GL11.glTexParameterf(3553, 33082, 0.0f);
            GL11.glTexParameterf(3553, 33083, (float)n2);
            GL11.glTexParameterf(3553, 34049, 0.0f);
        }
        for (int i = 0; i <= n2; ++i) {
            GL11.glTexImage2D(3553, i, 6408, n3 >> i, n4 >> i, 0, 32993, 33639, (IntBuffer)null);
        }
    }
    
    public static int uploadTextureImageSub(final int n, final BufferedImage bufferedImage, final int n2, final int n3, final boolean b, final boolean b2) {
        bindTexture(n);
        uploadTextureImageSubImpl(bufferedImage, n2, n3, b, b2);
        return n;
    }
    
    private static void uploadTextureImageSubImpl(final BufferedImage bufferedImage, final int n, final int n2, final boolean textureBlurred, final boolean textureClamped) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int n3 = 4194304 / width;
        final int[] array = new int[n3 * width];
        setTextureBlurred(textureBlurred);
        setTextureClamped(textureClamped);
        for (int i = 0; i < width * height; i += width * n3) {
            final int n4 = i / width;
            final int min = Math.min(n3, height - n4);
            final int n5 = width * min;
            bufferedImage.getRGB(0, n4, width, min, array, 0, width);
            copyToBuffer(array, n5);
            GL11.glTexSubImage2D(3553, 0, n, n2 + n4, width, min, 32993, 33639, TextureUtil.dataBuffer);
        }
    }
    
    public static void setTextureClamped(final boolean b) {
        if (b) {
            GL11.glTexParameteri(3553, 10242, 33071);
            GL11.glTexParameteri(3553, 10243, 33071);
        }
        else {
            GL11.glTexParameteri(3553, 10242, 10497);
            GL11.glTexParameteri(3553, 10243, 10497);
        }
    }
    
    private static void setTextureBlurred(final boolean b) {
        func_147954_b(b, false);
    }
    
    public static void func_147954_b(final boolean b, final boolean b2) {
        if (b) {
            GL11.glTexParameteri(3553, 10241, b2 ? 9987 : 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
        }
        else {
            final int mipmapType = Config.getMipmapType();
            GL11.glTexParameteri(3553, 10241, b2 ? mipmapType : 9728);
            GL11.glTexParameteri(3553, 10240, 9728);
        }
    }
    
    private static void copyToBuffer(final int[] array, final int n) {
        copyToBufferPos(array, 0, n);
    }
    
    private static void copyToBufferPos(final int[] array, final int n, final int n2) {
        int[] updateAnaglyph = array;
        if (Minecraft.getMinecraft().gameSettings.anaglyph) {
            updateAnaglyph = updateAnaglyph(array);
        }
        TextureUtil.dataBuffer.clear();
        TextureUtil.dataBuffer.put(updateAnaglyph, n, n2);
        TextureUtil.dataBuffer.position(0).limit(n2);
    }
    
    static void bindTexture(final int n) {
        GlStateManager.func_179144_i(n);
    }
    
    public static int[] readImageData(final IResourceManager resourceManager, final ResourceLocation resourceLocation) throws IOException {
        final BufferedImage func_177053_a = func_177053_a(resourceManager.getResource(resourceLocation).getInputStream());
        if (func_177053_a == null) {
            return null;
        }
        final int width = func_177053_a.getWidth();
        final int height = func_177053_a.getHeight();
        final int[] array = new int[width * height];
        func_177053_a.getRGB(0, 0, width, height, array, 0, width);
        return array;
    }
    
    public static BufferedImage func_177053_a(final InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        BufferedImage read;
        try {
            read = ImageIO.read(inputStream);
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
        IOUtils.closeQuietly(inputStream);
        return read;
    }
    
    public static int[] updateAnaglyph(final int[] array) {
        final int[] array2 = new int[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = func_177054_c(array[i]);
        }
        return array2;
    }
    
    public static int func_177054_c(final int n) {
        final int n2 = n >> 24 & 0xFF;
        final int n3 = n >> 16 & 0xFF;
        final int n4 = n >> 8 & 0xFF;
        final int n5 = n & 0xFF;
        return n2 << 24 | (n3 * 30 + n4 * 59 + n5 * 11) / 100 << 16 | (n3 * 30 + n4 * 70) / 100 << 8 | (n3 * 30 + n5 * 70) / 100;
    }
    
    public static void func_177055_a(final String s, final int n, final int n2, final int n3, final int n4) {
        bindTexture(n);
        GL11.glPixelStorei(3333, 1);
        GL11.glPixelStorei(3317, 1);
        for (int i = 0; i <= n2; ++i) {
            final File file = new File(String.valueOf(s) + TextureUtil.llIllllIIIllllI[1] + i + TextureUtil.llIllllIIIllllI[2]);
            final int n5 = n3 >> i;
            final int n6 = n4 >> i;
            final int n7 = n5 * n6;
            final IntBuffer intBuffer = BufferUtils.createIntBuffer(n7);
            final int[] array = new int[n7];
            GL11.glGetTexImage(3553, i, 32993, 33639, intBuffer);
            intBuffer.get(array);
            final BufferedImage bufferedImage = new BufferedImage(n5, n6, 2);
            bufferedImage.setRGB(0, 0, n5, n6, array, 0, n5);
            try {
                ImageIO.write(bufferedImage, TextureUtil.llIllllIIIllllI[3], file);
                TextureUtil.logger.debug(TextureUtil.llIllllIIIllllI[4], file.getAbsolutePath());
            }
            catch (Exception ex) {
                TextureUtil.logger.debug(TextureUtil.llIllllIIIllllI[5], ex);
            }
        }
    }
    
    public static void func_147953_a(final int[] array, final int n, final int n2) {
        final int[] array2 = new int[n];
        for (int n3 = n2 / 2, i = 0; i < n3; ++i) {
            System.arraycopy(array, i * n, array2, 0, n);
            System.arraycopy(array, (n2 - 1 - i) * n, array, i * n, n);
            System.arraycopy(array2, 0, array, (n2 - 1 - i) * n, n);
        }
    }
    
    public static BufferedImage readBufferedImage(final InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        BufferedImage read;
        try {
            read = ImageIO.read(inputStream);
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
        IOUtils.closeQuietly(inputStream);
        return read;
    }
    
    private static void lIlIIIllllIllIlI() {
        (llIllllIIIllllI = new String[6])[0] = lIlIIIllllIlIllI(TextureUtil.llIllllIIIlllll[0], TextureUtil.llIllllIIIlllll[1]);
        TextureUtil.llIllllIIIllllI[1] = lIlIIIllllIlIlll(TextureUtil.llIllllIIIlllll[2], TextureUtil.llIllllIIIlllll[3]);
        TextureUtil.llIllllIIIllllI[2] = lIlIIIllllIlIllI(TextureUtil.llIllllIIIlllll[4], TextureUtil.llIllllIIIlllll[5]);
        TextureUtil.llIllllIIIllllI[3] = lIlIIIllllIllIII(TextureUtil.llIllllIIIlllll[6], TextureUtil.llIllllIIIlllll[7]);
        TextureUtil.llIllllIIIllllI[4] = lIlIIIllllIlIlll(TextureUtil.llIllllIIIlllll[8], TextureUtil.llIllllIIIlllll[9]);
        TextureUtil.llIllllIIIllllI[5] = lIlIIIllllIllIIl(TextureUtil.llIllllIIIlllll[10], TextureUtil.llIllllIIIlllll[11]);
        TextureUtil.llIllllIIIlllll = null;
    }
    
    private static void lIlIIIllllIllIll() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        TextureUtil.llIllllIIIlllll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lIlIIIllllIllIII(String s, final String s2) {
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
    
    private static String lIlIIIllllIlIlll(final String s, final String s2) {
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
    
    private static String lIlIIIllllIlIllI(final String s, final String s2) {
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
    
    private static String lIlIIIllllIllIIl(final String s, final String s2) {
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
}
