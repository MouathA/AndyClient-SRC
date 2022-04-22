package com.mojang.realmsclient.util;

import net.minecraft.realms.*;
import org.apache.commons.codec.binary.*;
import javax.imageio.*;
import java.io.*;
import org.apache.commons.io.*;
import java.awt.image.*;
import java.nio.*;
import org.lwjgl.opengl.*;
import java.util.*;

public class RealmsTextureManager
{
    private static Map textures;
    private static Boolean useMultitextureArb;
    
    public static void bindWorldTemplate(final String s, final String s2) {
        if (s2 == null) {
            RealmsScreen.bind("textures/gui/presets/isles.png");
            return;
        }
        GL11.glBindTexture(3553, getTextureId(s, s2));
    }
    
    public static int getTextureId(final String s, final String s2) {
        int n;
        if (RealmsTextureManager.textures.containsKey(s)) {
            final RealmsTexture realmsTexture = RealmsTextureManager.textures.get(s);
            if (realmsTexture.image.equals(s2)) {
                return realmsTexture.textureId;
            }
            GL11.glDeleteTextures(realmsTexture.textureId);
            n = realmsTexture.textureId;
        }
        else {
            n = GL11.glGenTextures();
        }
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new Base64().decode(s2));
        final BufferedImage read = ImageIO.read(byteArrayInputStream);
        IOUtils.closeQuietly(byteArrayInputStream);
        read.getWidth();
        read.getHeight();
        final int[] array = new int[0];
        read.getRGB(0, 0, 0, 0, array, 0, 0);
        final IntBuffer intBuffer = ByteBuffer.allocateDirect(0).order(ByteOrder.nativeOrder()).asIntBuffer();
        intBuffer.put(array);
        intBuffer.flip();
        if (-1 == -1) {
            if (getUseMultiTextureArb()) {
                RealmsTextureManager.GL_TEXTURE0 = 33984;
            }
            else {
                RealmsTextureManager.GL_TEXTURE0 = 33984;
            }
        }
        glActiveTexture(-1);
        GL11.glBindTexture(3553, n);
        GL11.glTexImage2D(3553, 0, 6408, 0, 0, 0, 32993, 33639, intBuffer);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexParameteri(3553, 10241, 9729);
        RealmsTextureManager.textures.put(s, new RealmsTexture(s2, n));
        return n;
    }
    
    public static void glActiveTexture(final int n) {
        if (getUseMultiTextureArb()) {
            ARBMultitexture.glActiveTextureARB(n);
        }
        else {
            GL13.glActiveTexture(n);
        }
    }
    
    public static boolean getUseMultiTextureArb() {
        if (RealmsTextureManager.useMultitextureArb == null) {
            final ContextCapabilities capabilities = GLContext.getCapabilities();
            RealmsTextureManager.useMultitextureArb = (capabilities.GL_ARB_multitexture && !capabilities.OpenGL13);
        }
        return RealmsTextureManager.useMultitextureArb;
    }
    
    static {
        RealmsTextureManager.textures = new HashMap();
    }
    
    public static class RealmsTexture
    {
        String image;
        int textureId;
        
        public RealmsTexture(final String image, final int textureId) {
            this.image = image;
            this.textureId = textureId;
        }
    }
}
