package optifine;

import net.minecraft.client.entity.*;
import org.apache.commons.io.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import java.io.*;
import net.minecraft.client.renderer.texture.*;
import java.awt.image.*;
import java.awt.*;

public class CapeUtils
{
    public static void downloadCape(final AbstractClientPlayer abstractClientPlayer) {
        final String nameClear = abstractClientPlayer.getNameClear();
        if (nameClear != null && !nameClear.isEmpty()) {
            final String string = "http://s.optifine.net/capes/" + nameClear + ".png";
            final ResourceLocation locationOfCape = new ResourceLocation("capeof/" + FilenameUtils.getBaseName(string));
            final TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
            final ITextureObject texture = textureManager.getTexture(locationOfCape);
            if (texture != null && texture instanceof ThreadDownloadImageData) {
                final ThreadDownloadImageData threadDownloadImageData = (ThreadDownloadImageData)texture;
                if (threadDownloadImageData.imageFound != null) {
                    if (threadDownloadImageData.imageFound) {
                        abstractClientPlayer.setLocationOfCape(locationOfCape);
                    }
                    return;
                }
            }
            final ThreadDownloadImageData threadDownloadImageData2 = new ThreadDownloadImageData(null, string, null, new IImageBuffer(locationOfCape) {
                ImageBufferDownload ibd = new ImageBufferDownload();
                private final AbstractClientPlayer val$player;
                private final ResourceLocation val$rl;
                
                @Override
                public BufferedImage parseUserSkin(final BufferedImage bufferedImage) {
                    return CapeUtils.parseCape(bufferedImage);
                }
                
                @Override
                public void func_152634_a() {
                    this.val$player.setLocationOfCape(this.val$rl);
                }
            });
            threadDownloadImageData2.pipeline = true;
            textureManager.loadTexture(locationOfCape, threadDownloadImageData2);
        }
    }
    
    public static BufferedImage parseCape(final BufferedImage bufferedImage) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        while (64 < width || 32 < height) {}
        final BufferedImage bufferedImage2 = new BufferedImage(64, 32, 2);
        final Graphics graphics = bufferedImage2.getGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.dispose();
        return bufferedImage2;
    }
}
