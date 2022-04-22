package Mood.Matrix.DefaultParticles;

import net.minecraft.client.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import org.lwjgl.*;
import java.nio.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import java.awt.*;

public final class GlUtils
{
    private static final Random random;
    public static List vbos;
    public static List textures;
    
    static {
        random = new Random();
        GlUtils.vbos = new ArrayList();
        GlUtils.textures = new ArrayList();
    }
    
    public static void glScissor(final int[] array) {
        glScissor((float)array[0], (float)array[1], (float)(array[0] + array[2]), (float)(array[1] + array[3]));
    }
    
    public static void glScissor(final float n, final float n2, final float n3, final float n4) {
        final int scaleFactor = getScaleFactor();
        GL11.glScissor((int)(n * scaleFactor), (int)(Minecraft.getMinecraft().displayHeight - n4 * scaleFactor), (int)((n3 - n) * scaleFactor), (int)((n4 - n2) * scaleFactor));
    }
    
    public static int getScaleFactor() {
        final boolean unicode = Minecraft.getMinecraft().isUnicode();
        final int guiScale = Minecraft.getMinecraft().gameSettings.guiScale;
        if (1000 == 0) {}
        int n = 0;
        while (1 < 1000 && Minecraft.getMinecraft().displayWidth / 2 >= 320 && Minecraft.getMinecraft().displayHeight / 2 >= 240) {
            ++n;
        }
        if (unicode && true && true != true) {
            --n;
        }
        return 1;
    }
    
    public static int getMouseX() {
        return Mouse.getX() * getScreenWidth() / Minecraft.getMinecraft().displayWidth;
    }
    
    public static int getMouseY() {
        return getScreenHeight() - Mouse.getY() * getScreenHeight() / Minecraft.getMinecraft().displayWidth - 1;
    }
    
    public static int getScreenWidth() {
        return Minecraft.getMinecraft().displayWidth / getScaleFactor();
    }
    
    public static int getScreenHeight() {
        return Minecraft.getMinecraft().displayHeight / getScaleFactor();
    }
    
    public static boolean isHovered(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return n5 >= n && n5 <= n + n3 && n6 >= n2 && n6 < n2 + n4;
    }
    
    public static int genVBO() {
        final int glGenBuffers = GL15.glGenBuffers();
        GlUtils.vbos.add(glGenBuffers);
        GL15.glBindBuffer(34962, glGenBuffers);
        return glGenBuffers;
    }
    
    public static int getTexture() {
        final int glGenTextures = GL11.glGenTextures();
        GlUtils.textures.add(glGenTextures);
        return glGenTextures;
    }
    
    public static int applyTexture(final int n, final File file, final int n2, final int n3) throws IOException {
        applyTexture(n, ImageIO.read(file), n2, n3);
        return n;
    }
    
    public static int applyTexture(final int n, final BufferedImage bufferedImage, final int n2, final int n3) {
        final int[] array = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
        bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), array, 0, bufferedImage.getWidth());
        final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(bufferedImage.getWidth() * bufferedImage.getHeight() * 4);
        while (0 < bufferedImage.getHeight()) {
            while (0 < bufferedImage.getWidth()) {
                final int n4 = array[0 * bufferedImage.getWidth() + 0];
                byteBuffer.put((byte)(n4 >> 16 & 0xFF));
                byteBuffer.put((byte)(n4 >> 8 & 0xFF));
                byteBuffer.put((byte)(n4 & 0xFF));
                byteBuffer.put((byte)(n4 >> 24 & 0xFF));
                int n5 = 0;
                ++n5;
            }
            int n6 = 0;
            ++n6;
        }
        byteBuffer.flip();
        applyTexture(n, bufferedImage.getWidth(), bufferedImage.getHeight(), byteBuffer, n2, n3);
        return n;
    }
    
    public static int applyTexture(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final int n4, final int n5) {
        GL11.glBindTexture(3553, n);
        GL11.glTexParameteri(3553, 10241, n4);
        GL11.glTexParameteri(3553, 10240, n4);
        GL11.glTexParameteri(3553, 10242, n5);
        GL11.glTexParameteri(3553, 10243, n5);
        GL11.glPixelStorei(3317, 1);
        GL11.glTexImage2D(3553, 0, 32856, n2, n3, 0, 6408, 5121, byteBuffer);
        GL11.glBindTexture(3553, 0);
        return n;
    }
    
    public static void cleanup() {
        GL15.glBindBuffer(34962, 0);
        GL11.glBindTexture(3553, 0);
        final Iterator<Integer> iterator = GlUtils.vbos.iterator();
        while (iterator.hasNext()) {
            GL15.glDeleteBuffers(iterator.next());
        }
        final Iterator<Integer> iterator2 = GlUtils.textures.iterator();
        while (iterator2.hasNext()) {
            GL11.glDeleteTextures(iterator2.next());
        }
    }
    
    public static void glColor(final float n, final float n2, final float n3, final float n4) {
        GlStateManager.color(n, n2, n3, n4);
    }
    
    public static void glColor(final Color color) {
        GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    public static void glColor(final int n) {
        GlStateManager.color((n >> 16 & 0xFF) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n & 0xFF) / 255.0f, (n >> 24 & 0xFF) / 255.0f);
    }
    
    public static Color getHSBColor(final float n, final float n2, final float n3) {
        return Color.getHSBColor(n, n2, n3);
    }
    
    public static Color getRandomColor(final int n, final float n2) {
        return getHSBColor(GlUtils.random.nextFloat(), (float)((GlUtils.random.nextInt(n) + n) / n + n), n2);
    }
    
    public static Color getRandomColor() {
        return getRandomColor(1000, 0.6f);
    }
}
