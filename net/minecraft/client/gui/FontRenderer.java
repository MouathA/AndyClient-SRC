package net.minecraft.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.texture.*;
import java.awt.image.*;
import org.apache.commons.io.*;
import org.lwjgl.opengl.*;
import com.ibm.icu.text.*;
import optifine.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import java.io.*;

public class FontRenderer implements IResourceManagerReloadListener
{
    private static final ResourceLocation[] unicodePageLocations;
    private float[] charWidth;
    public int FONT_HEIGHT;
    public Random fontRandom;
    private byte[] glyphWidth;
    private int[] colorCode;
    private ResourceLocation locationFontTexture;
    private final TextureManager renderEngine;
    private float posX;
    private float posY;
    private boolean unicodeFlag;
    private boolean bidiFlag;
    private float red;
    private float blue;
    private float green;
    private float alpha;
    private int textColor;
    private boolean randomStyle;
    private boolean boldStyle;
    private boolean italicStyle;
    private boolean underlineStyle;
    private boolean strikethroughStyle;
    private static final String __OBFID;
    public GameSettings gameSettings;
    public ResourceLocation locationFontTextureBase;
    public boolean enabled;
    public float offsetBold;
    
    static {
        __OBFID = "CL_00000660";
        unicodePageLocations = new ResourceLocation[256];
    }
    
    public FontRenderer(final GameSettings gameSettings, final ResourceLocation resourceLocation, final TextureManager renderEngine, final boolean unicodeFlag) {
        this.charWidth = new float[256];
        this.FONT_HEIGHT = 9;
        this.fontRandom = new Random();
        this.glyphWidth = new byte[65536];
        this.colorCode = new int[32];
        this.enabled = true;
        this.offsetBold = 1.0f;
        this.gameSettings = gameSettings;
        this.locationFontTextureBase = resourceLocation;
        this.locationFontTexture = resourceLocation;
        this.renderEngine = renderEngine;
        this.unicodeFlag = unicodeFlag;
        this.bindTexture(this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase));
        while (0 < 32) {
            if (0 == 6) {
                final int n;
                n += 85;
            }
            if (gameSettings.anaglyph) {
                final int n = 0;
            }
            if (0 >= 16) {}
            this.colorCode[0] = 0;
            int n2 = 0;
            ++n2;
        }
        this.readGlyphSizes();
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
        while (0 < FontRenderer.unicodePageLocations.length) {
            FontRenderer.unicodePageLocations[0] = null;
            int n = 0;
            ++n;
        }
        this.readFontTexture();
        this.readGlyphSizes();
    }
    
    private void readFontTexture() {
        final BufferedImage func_177053_a = TextureUtil.func_177053_a(this.getResourceInputStream(this.locationFontTexture));
        final Properties fontProperties = FontUtils.readFontProperties(this.locationFontTexture);
        final int width = func_177053_a.getWidth();
        final int height = func_177053_a.getHeight();
        final int n = width / 16;
        final int n2 = height / 16;
        final float n3 = width / 128.0f;
        this.offsetBold = 1.0f / Config.limit(n3, 1.0f, 2.0f);
        final float float1 = FontUtils.readFloat(fontProperties, "offsetBold", -1.0f);
        if (float1 >= 0.0f) {
            this.offsetBold = float1;
        }
        final int[] array = new int[width * height];
        func_177053_a.getRGB(0, 0, width, height, array, 0, width);
        while (0 < 256) {
            int i;
            for (i = n - 1; i >= 0; --i) {
                final int n4 = 0 * n + i;
                while (0 < n2 && false) {
                    if ((array[n4 + (0 * n2 + 0) * width] >> 24 & 0xFF) > 16) {}
                    int n5 = 0;
                    ++n5;
                }
                if (!false) {
                    break;
                }
            }
            if (0 == 65) {}
            if (0 == 32) {
                if (n <= 8) {
                    i = (int)(2.0f * n3);
                }
                else {
                    i = (int)(1.5f * n3);
                }
            }
            this.charWidth[0] = (i + 1) / n3 + 1.0f;
            int n6 = 0;
            ++n6;
        }
        FontUtils.readCustomCharWidths(fontProperties, this.charWidth);
    }
    
    private void readGlyphSizes() {
        final InputStream resourceInputStream = this.getResourceInputStream(new ResourceLocation("font/glyph_sizes.bin"));
        resourceInputStream.read(this.glyphWidth);
        IOUtils.closeQuietly(resourceInputStream);
    }
    
    private float renderCharAtPos(final int n, final char c, final boolean b) {
        return (c == ' ') ? (this.unicodeFlag ? 4.0f : this.charWidth[c]) : ((c == ' ') ? 4.0f : (("\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8£\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1ªº¿®¬½¼¡«»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261±\u2265\u2264\u2320\u2321\u00f7\u2248°\u2219·\u221a\u207f²\u25a0\u0000".indexOf(c) != -1 && !this.unicodeFlag) ? this.renderDefaultChar(n, b) : this.renderUnicodeChar(c, b)));
    }
    
    private float renderDefaultChar(final int n, final boolean b) {
        final float n2 = (float)(n % 16 * 8);
        final float n3 = (float)(n / 16 * 8);
        final float n4 = b ? 1.0f : 0.0f;
        this.bindTexture(this.locationFontTexture);
        final float n5 = 7.99f;
        GL11.glBegin(5);
        GL11.glTexCoord2f(n2 / 128.0f, n3 / 128.0f);
        GL11.glVertex3f(this.posX + n4, this.posY, 0.0f);
        GL11.glTexCoord2f(n2 / 128.0f, (n3 + 7.99f) / 128.0f);
        GL11.glVertex3f(this.posX - n4, this.posY + 7.99f, 0.0f);
        GL11.glTexCoord2f((n2 + n5 - 1.0f) / 128.0f, n3 / 128.0f);
        GL11.glVertex3f(this.posX + n5 - 1.0f + n4, this.posY, 0.0f);
        GL11.glTexCoord2f((n2 + n5 - 1.0f) / 128.0f, (n3 + 7.99f) / 128.0f);
        GL11.glVertex3f(this.posX + n5 - 1.0f - n4, this.posY + 7.99f, 0.0f);
        return this.charWidth[n];
    }
    
    private ResourceLocation getUnicodePageLocation(final int n) {
        if (FontRenderer.unicodePageLocations[n] == null) {
            FontRenderer.unicodePageLocations[n] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", n));
            FontRenderer.unicodePageLocations[n] = FontUtils.getHdFontLocation(FontRenderer.unicodePageLocations[n]);
        }
        return FontRenderer.unicodePageLocations[n];
    }
    
    private void loadGlyphTexture(final int n) {
        this.bindTexture(this.getUnicodePageLocation(n));
    }
    
    private float renderUnicodeChar(final char c, final boolean b) {
        if (this.glyphWidth[c] == 0) {
            return 0.0f;
        }
        this.loadGlyphTexture(c / '\u0100');
        final int n = this.glyphWidth[c] >>> 4;
        final int n2 = this.glyphWidth[c] & 0xF;
        final float n3 = (float)(n & 0xF);
        final float n4 = (float)(n2 + 1);
        final float n5 = c % '\u0010' * 16 + n3;
        final float n6 = (float)((c & '\u00ff') / 16 * 16);
        final float n7 = n4 - n3 - 0.02f;
        final float n8 = b ? 1.0f : 0.0f;
        GL11.glBegin(5);
        GL11.glTexCoord2f(n5 / 256.0f, n6 / 256.0f);
        GL11.glVertex3f(this.posX + n8, this.posY, 0.0f);
        GL11.glTexCoord2f(n5 / 256.0f, (n6 + 15.98f) / 256.0f);
        GL11.glVertex3f(this.posX - n8, this.posY + 7.99f, 0.0f);
        GL11.glTexCoord2f((n5 + n7) / 256.0f, n6 / 256.0f);
        GL11.glVertex3f(this.posX + n7 / 2.0f + n8, this.posY, 0.0f);
        GL11.glTexCoord2f((n5 + n7) / 256.0f, (n6 + 15.98f) / 256.0f);
        GL11.glVertex3f(this.posX + n7 / 2.0f - n8, this.posY + 7.99f, 0.0f);
        return (n4 - n3) / 2.0f + 1.0f;
    }
    
    public int func_175063_a(final String s, final float n, final float n2, final int n3) {
        return this.func_175065_a(s, n, n2, n3, true);
    }
    
    public int drawString(final String s, final int n, final int n2, final int n3) {
        return this.enabled ? this.func_175065_a(s, (float)n, (float)n2, n3, false) : 0;
    }
    
    public int func_175065_a(final String s, final float n, final float n2, final int n3, final boolean b) {
        this.enableAlpha();
        this.resetStyles();
        int n4;
        if (b) {
            n4 = Math.max(this.func_180455_b(s, n + 1.0f, n2 + 1.0f, n3, true), this.func_180455_b(s, n, n2, n3, false));
        }
        else {
            n4 = this.func_180455_b(s, n, n2, n3, false);
        }
        return n4;
    }
    
    private String bidiReorder(final String s) {
        final Bidi bidi = new Bidi(new ArabicShaping(8).shape(s), 127);
        bidi.setReorderingMode(0);
        return bidi.writeReordered(2);
    }
    
    private void resetStyles() {
        this.randomStyle = false;
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
    }
    
    private void renderStringAtPos(final String s, final boolean b) {
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            int n = 0;
            if (char1 == '§' && 1 < s.length()) {
                int index = "0123456789abcdefklmnor".indexOf(s.toLowerCase().charAt(1));
                if (15 < 16) {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    if (15 < 0 || 15 > 15) {}
                    if (b) {
                        index += 16;
                    }
                    int textColor = this.colorCode[15];
                    if (Config.isCustomColors()) {
                        textColor = CustomColors.getTextColor(15, textColor);
                    }
                    this.textColor = textColor;
                    this.setColor((textColor >> 16) / 255.0f, (textColor >> 8 & 0xFF) / 255.0f, (textColor & 0xFF) / 255.0f, this.alpha);
                }
                else if (15 == 16) {
                    this.randomStyle = true;
                }
                else if (15 == 17) {
                    this.boldStyle = true;
                }
                else if (15 == 18) {
                    this.strikethroughStyle = true;
                }
                else if (15 == 19) {
                    this.underlineStyle = true;
                }
                else if (15 == 20) {
                    this.italicStyle = true;
                }
                else if (15 == 21) {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    this.setColor(this.red, this.blue, this.green, this.alpha);
                }
                ++n;
            }
            else {
                "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8£\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1ªº¿®¬½¼¡«»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261±\u2265\u2264\u2320\u2321\u00f7\u2248°\u2219·\u221a\u207f²\u25a0\u0000".indexOf(char1);
                if (this.randomStyle && 15 != -1) {
                    while ((int)this.charWidth[15] != (int)this.charWidth[this.fontRandom.nextInt(this.charWidth.length)]) {}
                }
                final float n2 = (15 != -1 && !this.unicodeFlag) ? this.offsetBold : 0.5f;
                final boolean b2 = (char1 == '\0' || 15 == -1 || this.unicodeFlag) && b;
                if (b2) {
                    this.posX -= n2;
                    this.posY -= n2;
                }
                float renderCharAtPos = this.renderCharAtPos(15, char1, this.italicStyle);
                if (b2) {
                    this.posX += n2;
                    this.posY += n2;
                }
                if (this.boldStyle) {
                    this.posX += n2;
                    if (b2) {
                        this.posX -= n2;
                        this.posY -= n2;
                    }
                    this.renderCharAtPos(15, char1, this.italicStyle);
                    this.posX -= n2;
                    if (b2) {
                        this.posX += n2;
                        this.posY += n2;
                    }
                    renderCharAtPos += n2;
                }
                if (this.strikethroughStyle) {
                    final Tessellator instance = Tessellator.getInstance();
                    final WorldRenderer worldRenderer = instance.getWorldRenderer();
                    worldRenderer.startDrawingQuads();
                    worldRenderer.addVertex(this.posX, this.posY + this.FONT_HEIGHT / 2, 0.0);
                    worldRenderer.addVertex(this.posX + renderCharAtPos, this.posY + this.FONT_HEIGHT / 2, 0.0);
                    worldRenderer.addVertex(this.posX + renderCharAtPos, this.posY + this.FONT_HEIGHT / 2 - 1.0f, 0.0);
                    worldRenderer.addVertex(this.posX, this.posY + this.FONT_HEIGHT / 2 - 1.0f, 0.0);
                    instance.draw();
                }
                if (this.underlineStyle) {
                    final Tessellator instance2 = Tessellator.getInstance();
                    final WorldRenderer worldRenderer2 = instance2.getWorldRenderer();
                    worldRenderer2.startDrawingQuads();
                    final int n3 = this.underlineStyle ? -1 : 0;
                    worldRenderer2.addVertex(this.posX + n3, this.posY + this.FONT_HEIGHT, 0.0);
                    worldRenderer2.addVertex(this.posX + renderCharAtPos, this.posY + this.FONT_HEIGHT, 0.0);
                    worldRenderer2.addVertex(this.posX + renderCharAtPos, this.posY + this.FONT_HEIGHT - 1.0f, 0.0);
                    worldRenderer2.addVertex(this.posX + n3, this.posY + this.FONT_HEIGHT - 1.0f, 0.0);
                    instance2.draw();
                }
                this.posX += renderCharAtPos;
            }
            ++n;
        }
    }
    
    public int exploitfixer(final String s) {
        if (s == null) {
            return 0;
        }
        float n = 0.0f;
        while (0 < s.length()) {
            float charWidthFloat = this.getCharWidthFloat(s.charAt(0));
            int n2 = 0;
            if (charWidthFloat < 0.0f && 0 < s.length() - 1) {
                ++n2;
                final char char1 = s.charAt(0);
                if (char1 != 'l' && char1 != 'L' && (char1 == 'r' || char1 == 'R')) {}
                charWidthFloat = 0.0f;
            }
            n += charWidthFloat;
            if (true && charWidthFloat > 0.0f) {
                ++n;
            }
            ++n2;
        }
        return (int)n;
    }
    
    private int renderStringAligned(final String s, int n, final int n2, final int n3, final int n4, final boolean b) {
        if (this.bidiFlag) {
            n = n + n3 - this.getStringWidth(this.bidiReorder(s));
        }
        return this.func_180455_b(s, (float)n, (float)n2, n4, b);
    }
    
    private int func_180455_b(String bidiReorder, final float posX, final float posY, int n, final boolean b) {
        if (bidiReorder == null) {
            return 0;
        }
        if (this.bidiFlag) {
            bidiReorder = this.bidiReorder(bidiReorder);
        }
        if ((n & 0xFC000000) == 0x0) {
            n |= 0xFF000000;
        }
        if (b) {
            n = ((n & 0xFCFCFC) >> 2 | (n & 0xFF000000));
        }
        this.red = (n >> 16 & 0xFF) / 255.0f;
        this.blue = (n >> 8 & 0xFF) / 255.0f;
        this.green = (n & 0xFF) / 255.0f;
        this.alpha = (n >> 24 & 0xFF) / 255.0f;
        this.setColor(this.red, this.blue, this.green, this.alpha);
        this.posX = posX;
        this.posY = posY;
        this.renderStringAtPos(bidiReorder, b);
        return (int)this.posX;
    }
    
    public int getStringWidth(final String s) {
        if (s == null) {
            return 0;
        }
        float n = 0.0f;
        while (0 < s.length()) {
            float charWidthFloat = this.getCharWidthFloat(s.charAt(0));
            int n2 = 0;
            if (charWidthFloat < 0.0f && 0 < s.length() - 1) {
                ++n2;
                final char char1 = s.charAt(0);
                if (char1 != 'l' && char1 != 'L' && (char1 == 'r' || char1 == 'R')) {}
                charWidthFloat = 0.0f;
            }
            n += charWidthFloat;
            if (true && charWidthFloat > 0.0f) {
                n += (this.unicodeFlag ? 1.0f : this.offsetBold);
            }
            ++n2;
        }
        return (int)n;
    }
    
    public int getCharWidth(final char c) {
        return Math.round(this.getCharWidthFloat(c));
    }
    
    private float getCharWidthFloat(final char c) {
        if (c == '§') {
            return -1.0f;
        }
        if (c == ' ') {
            return this.charWidth[32];
        }
        final int index = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8£\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1ªº¿®¬½¼¡«»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261±\u2265\u2264\u2320\u2321\u00f7\u2248°\u2219·\u221a\u207f²\u25a0\u0000".indexOf(c);
        if (c > '\0' && index != -1 && !this.unicodeFlag) {
            return this.charWidth[index];
        }
        if (this.glyphWidth[c] != 0) {
            final int n = this.glyphWidth[c] >>> 4;
            int n2 = this.glyphWidth[c] & 0xF;
            return (float)((++n2 - (n & 0xF)) / 2 + 1);
        }
        return 0.0f;
    }
    
    public String trimStringToWidth(final String s, final int n) {
        return this.trimStringToWidth(s, n, false);
    }
    
    public String trimStringToWidth(final String s, final int n, final boolean b) {
        final StringBuilder sb = new StringBuilder();
        float n2 = 0.0f;
        final int n3 = b ? (s.length() - 1) : 0;
        for (int n4 = b ? -1 : 1, n5 = n3; n5 >= 0 && n5 < s.length() && n2 < n; n5 += n4) {
            final char char1 = s.charAt(n5);
            final float charWidthFloat = this.getCharWidthFloat(char1);
            if (true) {
                if (char1 != 'l' && char1 != 'L') {
                    if (char1 == 'r' || char1 == 'R') {}
                }
            }
            else if (charWidthFloat >= 0.0f) {
                n2 += charWidthFloat;
                if (true) {
                    ++n2;
                }
            }
            if (n2 > n) {
                break;
            }
            if (b) {
                sb.insert(0, char1);
            }
            else {
                sb.append(char1);
            }
        }
        return sb.toString();
    }
    
    private String trimStringNewline(String substring) {
        while (substring != null && substring.endsWith("\n")) {
            substring = substring.substring(0, substring.length() - 1);
        }
        return substring;
    }
    
    public void drawSplitString(String trimStringNewline, final int n, final int n2, final int n3, final int textColor) {
        this.resetStyles();
        this.textColor = textColor;
        trimStringNewline = this.trimStringNewline(trimStringNewline);
        this.renderSplitString(trimStringNewline, n, n2, n3, false);
    }
    
    private void renderSplitString(final String s, final int n, int n2, final int n3, final boolean b) {
        final Iterator<String> iterator = this.listFormattedStringToWidth(s, n3).iterator();
        while (iterator.hasNext()) {
            this.renderStringAligned(iterator.next(), n, n2, n3, this.textColor, b);
            n2 += this.FONT_HEIGHT;
        }
    }
    
    public int splitStringWidth(final String s, final int n) {
        return this.FONT_HEIGHT * this.listFormattedStringToWidth(s, n).size();
    }
    
    public void setUnicodeFlag(final boolean unicodeFlag) {
        this.unicodeFlag = unicodeFlag;
    }
    
    public boolean getUnicodeFlag() {
        return this.unicodeFlag;
    }
    
    public void setBidiFlag(final boolean bidiFlag) {
        this.bidiFlag = bidiFlag;
    }
    
    public List listFormattedStringToWidth(final String s, final int n) {
        return Arrays.asList(this.wrapFormattedStringToWidth(s, n).split("\n"));
    }
    
    String wrapFormattedStringToWidth(final String s, final int n) {
        final int sizeStringToWidth = this.sizeStringToWidth(s, n);
        if (s.length() <= sizeStringToWidth) {
            return s;
        }
        final String substring = s.substring(0, sizeStringToWidth);
        final char char1 = s.charAt(sizeStringToWidth);
        return String.valueOf(substring) + "\n" + this.wrapFormattedStringToWidth(String.valueOf(getFormatFromString(substring)) + s.substring(sizeStringToWidth + ((char1 == ' ' || char1 == '\n') ? 1 : 0)), n);
    }
    
    private int sizeStringToWidth(final String s, final int n) {
        final int length = s.length();
        float n2 = 0.0f;
        while (0 < length) {
            final char char1 = s.charAt(0);
            int n3 = 0;
            switch (char1) {
                case 10: {
                    --n3;
                    break;
                }
                case 167: {
                    if (0 >= length - 1) {
                        break;
                    }
                    ++n3;
                    final char char2 = s.charAt(0);
                    if (char2 == 'l' || char2 == 'L') {
                        break;
                    }
                    if (char2 == 'r' || char2 == 'R' || isFormatColor(char2)) {
                        break;
                    }
                    break;
                }
                default: {
                    n2 += this.getCharWidthFloat(char1);
                    if (true) {
                        ++n2;
                        break;
                    }
                    break;
                }
            }
            if (char1 == '\n') {
                ++n3;
                break;
            }
            if (n2 > n) {
                break;
            }
            ++n3;
        }
        return (length && -1 != -1 && -1 < 0) ? -1 : 0;
    }
    
    private static boolean isFormatColor(final char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }
    
    private static boolean isFormatSpecial(final char c) {
        return (c >= 'k' && c <= 'o') || (c >= 'K' && c <= 'O') || c == 'r' || c == 'R';
    }
    
    public static String getFormatFromString(final String s) {
        String s2 = "";
        final int length = s.length();
        while (s.indexOf(167, 0) != -1) {
            if (-1 < length - 1) {
                final char char1 = s.charAt(0);
                if (isFormatColor(char1)) {
                    s2 = "§" + char1;
                }
                else {
                    if (!isFormatSpecial(char1)) {
                        continue;
                    }
                    s2 = String.valueOf(s2) + "§" + char1;
                }
            }
        }
        return s2;
    }
    
    public boolean getBidiFlag() {
        return this.bidiFlag;
    }
    
    public int func_175064_b(final char c) {
        final int index = "0123456789abcdef".indexOf(c);
        if (index >= 0 && index < this.colorCode.length) {
            int textColor = this.colorCode[index];
            if (Config.isCustomColors()) {
                textColor = CustomColors.getTextColor(index, textColor);
            }
            return textColor;
        }
        return 16777215;
    }
    
    protected void setColor(final float n, final float n2, final float n3, final float n4) {
        GlStateManager.color(n, n2, n3, n4);
    }
    
    protected void enableAlpha() {
    }
    
    protected void bindTexture(final ResourceLocation resourceLocation) {
        this.renderEngine.bindTexture(resourceLocation);
    }
    
    protected InputStream getResourceInputStream(final ResourceLocation resourceLocation) throws IOException {
        return Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation).getInputStream();
    }
    
    public int drawStringWithShadow(final String s, final double n, final double n2, final int n3) {
        return this.func_175065_a(s, (float)n, (float)n2, n3, true);
    }
    
    private int renderString(String bidiReorder, final float posX, final float posY, int n, final boolean b) {
        if (bidiReorder == null) {
            return 0;
        }
        if (this.bidiFlag) {
            bidiReorder = this.bidiReorder(bidiReorder);
        }
        if ((n & 0xFC000000) == 0x0) {
            n |= 0xFF000000;
        }
        if (b) {
            n = ((n & 0xFCFCFC) >> 2 | (n & 0xFF000000));
        }
        this.red = (n >> 16 & 0xFF) / 255.0f;
        this.blue = (n >> 8 & 0xFF) / 255.0f;
        this.green = (n & 0xFF) / 255.0f;
        this.alpha = (n >> 24 & 0xFF) / 255.0f;
        this.setColor(this.red, this.blue, this.green, this.alpha);
        this.posX = posX;
        this.posY = posY;
        this.renderStringAtPos(bidiReorder, b);
        return (int)this.posX;
    }
    
    public int drawString(final String s, final float n, final float n2, final int n3, final boolean b) {
        this.enableAlpha();
        this.resetStyles();
        int n4;
        if (b) {
            n4 = Math.max(this.renderString(s, n + 1.0f, n2 + 1.0f, n3, true), this.renderString(s, n, n2, n3, false));
        }
        else {
            n4 = this.renderString(s, n, n2, n3, false);
        }
        return n4;
    }
    
    public int drawString(final String s, final double n, final double n2, final int n3) {
        return this.enabled ? this.drawString(s, (float)n, (float)n2, n3, false) : 0;
    }
}
