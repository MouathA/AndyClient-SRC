package font;

import net.minecraft.client.renderer.texture.*;
import java.awt.image.*;
import java.awt.*;
import java.awt.geom.*;
import org.lwjgl.opengl.*;

public class CFont
{
    float imgSize;
    CharData[] charData;
    Font font;
    boolean antiAlias;
    boolean fractionalMetrics;
    int fontHeight;
    int charOffset;
    DynamicTexture tex;
    
    public CFont(final Font font, final boolean antiAlias, final boolean fractionalMetrics) {
        this.imgSize = 512.0f;
        this.charData = new CharData[256];
        this.fontHeight = -1;
        this.charOffset = 0;
        this.font = font;
        this.antiAlias = antiAlias;
        this.fractionalMetrics = fractionalMetrics;
        this.tex = this.setupTexture(font, antiAlias, fractionalMetrics, this.charData);
    }
    
    protected DynamicTexture setupTexture(final Font font, final boolean b, final boolean b2, final CharData[] array) {
        return new DynamicTexture(this.generateFontImage(font, b, b2, array));
    }
    
    protected BufferedImage generateFontImage(final Font font, final boolean b, final boolean b2, final CharData[] array) {
        final int n = (int)this.imgSize;
        final BufferedImage bufferedImage = new BufferedImage(n, n, 2);
        final Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
        graphics2D.setFont(font);
        graphics2D.setColor(new Color(255, 255, 255, 0));
        graphics2D.fillRect(0, 0, n, n);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, b2 ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, b ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, b ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        final FontMetrics fontMetrics = graphics2D.getFontMetrics();
        while (0 < array.length) {
            final char c = 0;
            final CharData charData = new CharData();
            final Rectangle2D stringBounds = fontMetrics.getStringBounds(String.valueOf(c), graphics2D);
            charData.width = stringBounds.getBounds().width + 8;
            charData.height = stringBounds.getBounds().height;
            if (0 + charData.width >= n) {}
            if (charData.height > 0) {
                final int height = charData.height;
            }
            charData.storedX = 0;
            charData.storedY = 1;
            if (charData.height > this.fontHeight) {
                this.fontHeight = charData.height;
            }
            array[0] = charData;
            graphics2D.drawString(String.valueOf(c), 2, 1 + fontMetrics.getAscent());
            final int n2 = 0 + charData.width;
            int n3 = 0;
            ++n3;
        }
        return bufferedImage;
    }
    
    public void drawChar(final CharData[] array, final char c, final float n, final float n2) throws ArrayIndexOutOfBoundsException {
        this.drawQuad(n, n2, (float)array[c].width, (float)array[c].height, (float)array[c].storedX, (float)array[c].storedY, (float)array[c].width, (float)array[c].height);
    }
    
    protected void drawQuad(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        final float n9 = n5 / this.imgSize;
        final float n10 = n6 / this.imgSize;
        final float n11 = n7 / this.imgSize;
        final float n12 = n8 / this.imgSize;
        GL11.glTexCoord2f(n9 + n11, n10);
        GL11.glVertex2d(n + n3, n2);
        GL11.glTexCoord2f(n9, n10);
        GL11.glVertex2d(n, n2);
        GL11.glTexCoord2f(n9, n10 + n12);
        GL11.glVertex2d(n, n2 + n4);
        GL11.glTexCoord2f(n9, n10 + n12);
        GL11.glVertex2d(n, n2 + n4);
        GL11.glTexCoord2f(n9 + n11, n10 + n12);
        GL11.glVertex2d(n + n3, n2 + n4);
        GL11.glTexCoord2f(n9 + n11, n10);
        GL11.glVertex2d(n + n3, n2);
    }
    
    public void setAntiAlias(final boolean antiAlias) {
        if (this.antiAlias != antiAlias) {
            this.antiAlias = antiAlias;
            this.tex = this.setupTexture(this.font, antiAlias, this.fractionalMetrics, this.charData);
        }
    }
    
    public boolean isFractionalMetrics() {
        return this.fractionalMetrics;
    }
    
    public void setFractionalMetrics(final boolean fractionalMetrics) {
        if (this.fractionalMetrics != fractionalMetrics) {
            this.fractionalMetrics = fractionalMetrics;
            this.tex = this.setupTexture(this.font, this.antiAlias, fractionalMetrics, this.charData);
        }
    }
    
    public void setFont(final Font font) {
        this.font = font;
        this.tex = this.setupTexture(font, this.antiAlias, this.fractionalMetrics, this.charData);
    }
    
    protected static class CharData
    {
        public int width;
        public int height;
        public int storedX;
        public int storedY;
    }
}
