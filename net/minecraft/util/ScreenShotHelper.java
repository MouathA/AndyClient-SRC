package net.minecraft.util;

import java.nio.*;
import org.apache.logging.log4j.*;
import java.text.*;
import java.io.*;
import net.minecraft.client.shader.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import javax.imageio.*;
import java.awt.image.*;
import net.minecraft.event.*;
import java.util.*;

public class ScreenShotHelper
{
    private static final Logger logger;
    private static final DateFormat dateFormat;
    private static IntBuffer pixelBuffer;
    private static int[] pixelValues;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000656";
        logger = LogManager.getLogger();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    }
    
    public static IChatComponent saveScreenshot(final File file, final int n, final int n2, final Framebuffer framebuffer) {
        return saveScreenshot(file, null, n, n2, framebuffer);
    }
    
    public static IChatComponent saveScreenshot(final File file, final String s, int framebufferTextureWidth, int framebufferTextureHeight, final Framebuffer framebuffer) {
        final File file2 = new File(file, "screenshots");
        file2.mkdir();
        if (OpenGlHelper.isFramebufferEnabled()) {
            framebufferTextureWidth = framebuffer.framebufferTextureWidth;
            framebufferTextureHeight = framebuffer.framebufferTextureHeight;
        }
        final int n = framebufferTextureWidth * framebufferTextureHeight;
        if (ScreenShotHelper.pixelBuffer == null || ScreenShotHelper.pixelBuffer.capacity() < n) {
            ScreenShotHelper.pixelBuffer = BufferUtils.createIntBuffer(n);
            ScreenShotHelper.pixelValues = new int[n];
        }
        GL11.glPixelStorei(3333, 1);
        GL11.glPixelStorei(3317, 1);
        ScreenShotHelper.pixelBuffer.clear();
        if (OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.func_179144_i(framebuffer.framebufferTexture);
            GL11.glGetTexImage(3553, 0, 32993, 33639, ScreenShotHelper.pixelBuffer);
        }
        else {
            GL11.glReadPixels(0, 0, framebufferTextureWidth, framebufferTextureHeight, 32993, 33639, ScreenShotHelper.pixelBuffer);
        }
        ScreenShotHelper.pixelBuffer.get(ScreenShotHelper.pixelValues);
        TextureUtil.func_147953_a(ScreenShotHelper.pixelValues, framebufferTextureWidth, framebufferTextureHeight);
        BufferedImage bufferedImage;
        if (OpenGlHelper.isFramebufferEnabled()) {
            bufferedImage = new BufferedImage(framebuffer.framebufferWidth, framebuffer.framebufferHeight, 1);
            int i;
            for (int n2 = i = framebuffer.framebufferTextureHeight - framebuffer.framebufferHeight; i < framebuffer.framebufferTextureHeight; ++i) {
                while (0 < framebuffer.framebufferWidth) {
                    bufferedImage.setRGB(0, i - n2, ScreenShotHelper.pixelValues[i * framebuffer.framebufferTextureWidth + 0]);
                    int n3 = 0;
                    ++n3;
                }
            }
        }
        else {
            bufferedImage = new BufferedImage(framebufferTextureWidth, framebufferTextureHeight, 1);
            bufferedImage.setRGB(0, 0, framebufferTextureWidth, framebufferTextureHeight, ScreenShotHelper.pixelValues, 0, framebufferTextureWidth);
        }
        File timestampedPNGFileForDirectory;
        if (s == null) {
            timestampedPNGFileForDirectory = getTimestampedPNGFileForDirectory(file2);
        }
        else {
            timestampedPNGFileForDirectory = new File(file2, s);
        }
        ImageIO.write(bufferedImage, "png", timestampedPNGFileForDirectory);
        final ChatComponentText chatComponentText = new ChatComponentText(timestampedPNGFileForDirectory.getName());
        chatComponentText.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, timestampedPNGFileForDirectory.getAbsolutePath()));
        chatComponentText.getChatStyle().setUnderlined(true);
        return new ChatComponentTranslation("screenshot.success", new Object[] { chatComponentText });
    }
    
    private static File getTimestampedPNGFileForDirectory(final File file) {
        final String string = ScreenShotHelper.dateFormat.format(new Date()).toString();
        File file2;
        while (true) {
            file2 = new File(file, String.valueOf(string) + ((true == true) ? "" : ("_" + 1)) + ".png");
            if (!file2.exists()) {
                break;
            }
            int n = 0;
            ++n;
        }
        return file2;
    }
}
