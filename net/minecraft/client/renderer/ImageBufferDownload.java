package net.minecraft.client.renderer;

import java.awt.image.*;
import java.awt.*;

public class ImageBufferDownload implements IImageBuffer
{
    private int[] imageData;
    private int imageWidth;
    private int imageHeight;
    
    @Override
    public BufferedImage parseUserSkin(final BufferedImage bufferedImage) {
        if (bufferedImage == null) {
            return null;
        }
        this.imageWidth = 64;
        this.imageHeight = 64;
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        while (this.imageWidth < width || this.imageHeight < height) {
            this.imageWidth *= 2;
            this.imageHeight *= 2;
        }
        final BufferedImage bufferedImage2 = new BufferedImage(this.imageWidth, this.imageHeight, 2);
        final Graphics graphics = bufferedImage2.getGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        if (bufferedImage.getHeight() == 32) {
            graphics.drawImage(bufferedImage2, 24, 48, 20, 52, 4, 16, 8, 20, null);
            graphics.drawImage(bufferedImage2, 28, 48, 24, 52, 8, 16, 12, 20, null);
            graphics.drawImage(bufferedImage2, 20, 52, 16, 64, 8, 20, 12, 32, null);
            graphics.drawImage(bufferedImage2, 24, 52, 20, 64, 4, 20, 8, 32, null);
            graphics.drawImage(bufferedImage2, 28, 52, 24, 64, 0, 20, 4, 32, null);
            graphics.drawImage(bufferedImage2, 32, 52, 28, 64, 12, 20, 16, 32, null);
            graphics.drawImage(bufferedImage2, 40, 48, 36, 52, 44, 16, 48, 20, null);
            graphics.drawImage(bufferedImage2, 44, 48, 40, 52, 48, 16, 52, 20, null);
            graphics.drawImage(bufferedImage2, 36, 52, 32, 64, 48, 20, 52, 32, null);
            graphics.drawImage(bufferedImage2, 40, 52, 36, 64, 44, 20, 48, 32, null);
            graphics.drawImage(bufferedImage2, 44, 52, 40, 64, 40, 20, 44, 32, null);
            graphics.drawImage(bufferedImage2, 48, 52, 44, 64, 52, 20, 56, 32, null);
        }
        graphics.dispose();
        this.imageData = ((DataBufferInt)bufferedImage2.getRaster().getDataBuffer()).getData();
        this.setAreaOpaque(0, 0, 32, 16);
        this.setAreaTransparent(32, 0, 64, 32);
        this.setAreaOpaque(0, 16, 64, 32);
        this.setAreaTransparent(0, 32, 16, 48);
        this.setAreaTransparent(16, 32, 40, 48);
        this.setAreaTransparent(40, 32, 56, 48);
        this.setAreaTransparent(0, 48, 16, 64);
        this.setAreaOpaque(16, 48, 48, 64);
        this.setAreaTransparent(48, 48, 64, 64);
        return bufferedImage2;
    }
    
    @Override
    public void func_152634_a() {
    }
    
    private void setAreaTransparent(final int n, final int n2, final int n3, final int n4) {
        if (!this.hasTransparency(n, n2, n3, n4)) {
            for (int i = n; i < n3; ++i) {
                for (int j = n2; j < n4; ++j) {
                    final int[] imageData = this.imageData;
                    final int n5 = i + j * this.imageWidth;
                    imageData[n5] &= 0xFFFFFF;
                }
            }
        }
    }
    
    private void setAreaOpaque(final int n, final int n2, final int n3, final int n4) {
        for (int i = n; i < n3; ++i) {
            for (int j = n2; j < n4; ++j) {
                final int[] imageData = this.imageData;
                final int n5 = i + j * this.imageWidth;
                imageData[n5] |= 0xFF000000;
            }
        }
    }
    
    private boolean hasTransparency(final int n, final int n2, final int n3, final int n4) {
        for (int i = n; i < n3; ++i) {
            for (int j = n2; j < n4; ++j) {
                if ((this.imageData[i + j * this.imageWidth] >> 24 & 0xFF) < 128) {
                    return true;
                }
            }
        }
        return false;
    }
}
