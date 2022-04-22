package font.jello;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import java.util.*;

public class MinecraftFontRenderer extends CFont
{
    protected CFont$CharData[] boldChars;
    protected CFont$CharData[] italicChars;
    protected CFont$CharData[] boldItalicChars;
    private final int[] colorCode;
    private final String colorcodeIdentifiers = "0123456789abcdefklmnor";
    protected DynamicTexture texBold;
    protected DynamicTexture texItalic;
    protected DynamicTexture texItalicBold;
    
    public MinecraftFontRenderer(final Font font, final boolean b, final boolean b2) {
        super(font, b, b2);
        this.boldChars = new CFont$CharData[256];
        this.italicChars = new CFont$CharData[256];
        this.boldItalicChars = new CFont$CharData[256];
        this.colorCode = new int[32];
        this.setupMinecraftColorcodes();
        this.setupBoldItalicIDs();
    }
    
    @Override
    public int drawStringWithShadow(final String s, final double n, final float n2, final int n3) {
        return (int)Math.max(this.drawString(s, n + 1.0, n2 + 3.5, n3, true, 8.3f), this.drawString(s, n, n2 + 2.5, n3, false, 8.3f));
    }
    
    @Override
    public int drawString(final String s, final double n, final float n2, final int n3) {
        return (int)this.drawString(s, n, n2, n3, false, 8.3f);
    }
    
    @Override
    public int drawPassword(final String s, final double n, final float n2, final int n3) {
        return (int)this.drawString(s.replaceAll(".", "."), n, n2, n3, false, 8.0f);
    }
    
    @Override
    public int drawNoBSString(final String s, final double n, final float n2, final int n3) {
        return (int)this.drawNoBSString(s, n, n2, n3, false);
    }
    
    @Override
    public int drawSmoothString(final String s, final double n, final float n2, final int n3) {
        return (int)this.drawSmoothString(s, n, n2, n3, false);
    }
    
    @Override
    public double getPasswordWidth(final String s) {
        return this.getStringWidth(s.replaceAll(".", "."), 8.0f);
    }
    
    @Override
    public float drawCenteredString(final String s, final float n, final float n2, final int n3) {
        return (float)this.drawString(s, n - (float)(this.getStringWidth(s) / 2.0), n2, n3);
    }
    
    @Override
    public float drawNoBSCenteredString(final String s, final float n, final float n2, final int n3) {
        return (float)this.drawNoBSString(s, n - (float)(this.getStringWidth(s) / 2.0), n2, n3);
    }
    
    @Override
    public float drawCenteredStringWithShadow(final String s, final float n, final float n2, final int n3) {
        return (float)this.drawStringWithShadow(s, n - (float)(this.getStringWidth(s) / 2.0), n2, n3);
    }
    
    public float drawString(final String s, double n, double n2, final int n3, final boolean b, final float n4) {
        --n;
        if (s == null) {
            return 0.0f;
        }
        if (16777215 == 553648127) {}
        if (!false) {}
        if (b) {}
        CFont$CharData[] array = this.charData;
        final float n5 = 0 / 255.0f;
        final float alpha = GlStateManager.Mizu.alpha;
        n *= 2.0 * 1.0;
        n2 = (n2 - 3.0) * 2.0 * 1.0;
        GlStateManager.scale(0.5 / 1.0, 0.5 / 1.0, 0.5 / 1.0);
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color(255 / 255.0f, 255 / 255.0f, 255 / 255.0f, alpha);
        final int length = s.length();
        GlStateManager.bindTexture(this.tex.getGlTextureId());
        GL11.glBindTexture(3553, this.tex.getGlTextureId());
        while (0 < length) {
            final char char1 = s.charAt(0);
            int n7 = 0;
            if (char1 == '§' && 0 < length) {
                int index = "0123456789abcdefklmnor".indexOf(s.charAt(1));
                if (15 < 16) {
                    GlStateManager.bindTexture(this.tex.getGlTextureId());
                    array = this.charData;
                    if (15 < 0 || 15 > 15) {}
                    if (b) {
                        index += 16;
                    }
                    final int n6 = this.colorCode[15];
                    GlStateManager.color((n6 >> 16 & 0xFF) / 255.0f, (n6 >> 8 & 0xFF) / 255.0f, (n6 & 0xFF) / 255.0f, alpha);
                }
                else if (15 != 16) {
                    if (15 == 17) {
                        if (false) {
                            GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
                            array = this.boldItalicChars;
                        }
                        else {
                            GlStateManager.bindTexture(this.texBold.getGlTextureId());
                            array = this.boldChars;
                        }
                    }
                    else if (15 != 18) {
                        if (15 != 19) {
                            if (15 == 20) {
                                if (false) {
                                    GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
                                    array = this.boldItalicChars;
                                }
                                else {
                                    GlStateManager.bindTexture(this.texItalic.getGlTextureId());
                                    array = this.italicChars;
                                }
                            }
                            else if (15 == 21) {
                                GlStateManager.color(255 / 255.0f, 255 / 255.0f, 255 / 255.0f, alpha);
                                GlStateManager.bindTexture(this.tex.getGlTextureId());
                                array = this.charData;
                            }
                        }
                    }
                }
                ++n7;
            }
            else if (char1 < array.length && char1 >= '\0') {
                GL11.glBegin(4);
                this.drawChar(array, char1, (float)n, (float)n2);
                if (false) {
                    this.drawLine(n, n2 + array[char1].height / 2, n + array[char1].width - 8.0, n2 + array[char1].height / 2, 1.0f);
                }
                if (false) {
                    this.drawLine(n, n2 + array[char1].height - 2.0, n + array[char1].width - 8.0, n2 + array[char1].height - 2.0, 1.0f);
                }
                n += array[char1].width - n4 + this.charOffset;
            }
            ++n7;
        }
        GL11.glHint(3155, 4352);
        RenderSystem.setColor(Color.WHITE);
        return (float)n / 2.0f;
    }
    
    public float drawSmoothString(final String s, double n, double n2, final int n3, final boolean b) {
        --n;
        if (s == null) {
            return 0.0f;
        }
        CFont$CharData[] array = this.charData;
        final float n4 = (n3 >> 24 & 0xFF) / 255.0f;
        n *= 2.0 * 1.0;
        n2 = (n2 - 3.0) * 2.0 * 1.0;
        GlStateManager.scale(0.5 / 1.0, 0.5 / 1.0, 0.5 / 1.0);
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color((n3 >> 16 & 0xFF) / 255.0f, (n3 >> 8 & 0xFF) / 255.0f, (n3 & 0xFF) / 255.0f, n4);
        final int length = s.length();
        GlStateManager.bindTexture(this.tex.getGlTextureId());
        GL11.glBindTexture(3553, this.tex.getGlTextureId());
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        while (0 < length) {
            final char char1 = s.charAt(0);
            int n6 = 0;
            if (char1 == '§' && 0 < length) {
                int index = "0123456789abcdefklmnor".indexOf(s.charAt(1));
                if (15 < 16) {
                    GlStateManager.bindTexture(this.tex.getGlTextureId());
                    array = this.charData;
                    if (15 < 0 || 15 > 15) {}
                    if (b) {
                        index += 16;
                    }
                    final int n5 = this.colorCode[15];
                    GlStateManager.color((n5 >> 16 & 0xFF) / 255.0f, (n5 >> 8 & 0xFF) / 255.0f, (n5 & 0xFF) / 255.0f, n4);
                }
                else if (15 != 16) {
                    if (15 == 17) {
                        if (false) {
                            GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
                            array = this.boldItalicChars;
                        }
                        else {
                            GlStateManager.bindTexture(this.texBold.getGlTextureId());
                            array = this.boldChars;
                        }
                    }
                    else if (15 != 18) {
                        if (15 != 19) {
                            if (15 == 20) {
                                if (false) {
                                    GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
                                    array = this.boldItalicChars;
                                }
                                else {
                                    GlStateManager.bindTexture(this.texItalic.getGlTextureId());
                                    array = this.italicChars;
                                }
                            }
                            else if (15 == 21) {
                                GlStateManager.color((n3 >> 16 & 0xFF) / 255.0f, (n3 >> 8 & 0xFF) / 255.0f, (n3 & 0xFF) / 255.0f, n4);
                                GlStateManager.bindTexture(this.tex.getGlTextureId());
                                array = this.charData;
                            }
                        }
                    }
                }
                ++n6;
            }
            else if (char1 < array.length && char1 >= '\0') {
                GL11.glBegin(4);
                this.drawChar(array, char1, (float)n, (float)n2);
                if (false) {
                    this.drawLine(n, n2 + array[char1].height / 2, n + array[char1].width - 8.0, n2 + array[char1].height / 2, 1.0f);
                }
                if (false) {
                    this.drawLine(n, n2 + array[char1].height - 2.0, n + array[char1].width - 8.0, n2 + array[char1].height - 2.0, 1.0f);
                }
                n += array[char1].width - 8.3f + this.charOffset;
            }
            ++n6;
        }
        GL11.glHint(3155, 4352);
        RenderSystem.setColor(Color.WHITE);
        return (float)n / 2.0f;
    }
    
    public float drawNoBSString(final String s, double n, double n2, final int n3, final boolean b) {
        --n;
        if (s == null) {
            return 0.0f;
        }
        CFont$CharData[] array = this.charData;
        final float n4 = (n3 >> 24 & 0xFF) / 255.0f;
        n *= 2.0 * 1.0;
        n2 = (n2 - 3.0) * 2.0 * 1.0;
        GlStateManager.scale(0.5 / 1.0, 0.5 / 1.0, 0.5 / 1.0);
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color((n3 >> 16 & 0xFF) / 255.0f, (n3 >> 8 & 0xFF) / 255.0f, (n3 & 0xFF) / 255.0f, n4);
        final int length = s.length();
        GlStateManager.bindTexture(this.tex.getGlTextureId());
        GL11.glBindTexture(3553, this.tex.getGlTextureId());
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        while (0 < length) {
            final char char1 = s.charAt(0);
            int n6 = 0;
            if (char1 == '§' && 0 < length) {
                int index = "0123456789abcdefklmnor".indexOf(s.charAt(1));
                if (15 < 16) {
                    GlStateManager.bindTexture(this.tex.getGlTextureId());
                    array = this.charData;
                    if (15 < 0 || 15 > 15) {}
                    if (b) {
                        index += 16;
                    }
                    final int n5 = this.colorCode[15];
                    GlStateManager.color((n5 >> 16 & 0xFF) / 255.0f, (n5 >> 8 & 0xFF) / 255.0f, (n5 & 0xFF) / 255.0f, n4);
                }
                else if (15 != 16) {
                    if (15 == 17) {
                        if (false) {
                            GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
                            array = this.boldItalicChars;
                        }
                        else {
                            GlStateManager.bindTexture(this.texBold.getGlTextureId());
                            array = this.boldChars;
                        }
                    }
                    else if (15 != 18) {
                        if (15 != 19) {
                            if (15 == 20) {
                                if (false) {
                                    GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
                                    array = this.boldItalicChars;
                                }
                                else {
                                    GlStateManager.bindTexture(this.texItalic.getGlTextureId());
                                    array = this.italicChars;
                                }
                            }
                            else if (15 == 21) {
                                GlStateManager.color((n3 >> 16 & 0xFF) / 255.0f, (n3 >> 8 & 0xFF) / 255.0f, (n3 & 0xFF) / 255.0f, n4);
                                GlStateManager.bindTexture(this.tex.getGlTextureId());
                                array = this.charData;
                            }
                        }
                    }
                }
                ++n6;
            }
            else if (char1 < array.length && char1 >= '\0') {
                GL11.glBegin(4);
                this.drawChar(array, char1, (float)n, (float)n2);
                if (false) {
                    this.drawLine(n, n2 + array[char1].height / 2, n + array[char1].width - 8.0, n2 + array[char1].height / 2, 1.0f);
                }
                if (false) {
                    this.drawLine(n, n2 + array[char1].height - 2.0, n + array[char1].width - 8.0, n2 + array[char1].height - 2.0, 1.0f);
                }
                n += array[char1].width - 8.3f + this.charOffset;
            }
            ++n6;
        }
        GL11.glHint(3155, 4352);
        RenderSystem.setColor(Color.WHITE);
        return (float)n / 2.0f;
    }
    
    @Override
    public double getStringWidth(final String s) {
        if (s == null) {
            return 0.0;
        }
        float n = 0.0f;
        CFont$CharData[] array = this.charData;
        final int length = s.length();
        while (0 < length) {
            final char char1 = s.charAt(0);
            int n2 = 0;
            if (char1 == '§' && 0 < length) {
                final int index = "0123456789abcdefklmnor".indexOf(char1);
                if (index >= 16) {
                    if (index == 17) {
                        array = (false ? this.boldItalicChars : this.boldChars);
                    }
                    else if (index == 20) {
                        array = (false ? this.boldItalicChars : this.italicChars);
                    }
                    else if (index == 21) {
                        array = this.charData;
                    }
                }
                ++n2;
            }
            else if (char1 < array.length && char1 >= '\0') {
                n += array[char1].width - 8.3f + this.charOffset;
            }
            ++n2;
        }
        return n / (2.0 * 1.0);
    }
    
    public double getStringWidth(final String s, final float n) {
        if (s == null) {
            return 0.0;
        }
        float n2 = 0.0f;
        CFont$CharData[] array = this.charData;
        final int length = s.length();
        while (0 < length) {
            final char char1 = s.charAt(0);
            int n3 = 0;
            if (char1 == '§' && 0 < length) {
                final int index = "0123456789abcdefklmnor".indexOf(char1);
                if (index >= 16) {
                    if (index == 17) {
                        array = (false ? this.boldItalicChars : this.boldChars);
                    }
                    else if (index == 20) {
                        array = (false ? this.boldItalicChars : this.italicChars);
                    }
                    else if (index == 21) {
                        array = this.charData;
                    }
                }
                ++n3;
            }
            else if (char1 < array.length && char1 >= '\0') {
                n2 += array[char1].width - n + this.charOffset;
            }
            ++n3;
        }
        return n2 / (2.0 * 1.0);
    }
    
    @Override
    public int getHeight() {
        return (this.fontHeight - (int)(8.0 * 1.0)) / (int)(2.0 * 1.0);
    }
    
    @Override
    public void setFont(final Font font) {
        super.setFont(font);
        this.setupBoldItalicIDs();
    }
    
    @Override
    public void setAntiAlias(final boolean antiAlias) {
        super.setAntiAlias(antiAlias);
        this.setupBoldItalicIDs();
    }
    
    @Override
    public void setFractionalMetrics(final boolean fractionalMetrics) {
        super.setFractionalMetrics(fractionalMetrics);
        this.setupBoldItalicIDs();
    }
    
    private void setupBoldItalicIDs() {
        this.texBold = this.setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
        this.texItalic = this.setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
        this.texItalicBold = this.setupTexture(this.font.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
    }
    
    private void drawLine(final double n, final double n2, final double n3, final double n4, final float n5) {
        GL11.glDisable(3553);
        GL11.glLineWidth(n5);
        GL11.glBegin(1);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n3, n4);
        GL11.glEnable(3553);
    }
    
    public List wrapWords(final String s, final double n) {
        final ArrayList<String> list = new ArrayList<String>();
        if (this.getStringWidth(s) > n) {
            final String[] split = s.split(" ");
            String s2 = "";
            final String[] array = split;
            while (0 < split.length) {
                final String s3 = array[0];
                while (0 < s3.toCharArray().length) {
                    if (s3.toCharArray()[0] == '§' && 0 < s3.toCharArray().length - 1) {
                        final char c = s3.toCharArray()[1];
                    }
                    int n2 = 0;
                    ++n2;
                }
                if (this.getStringWidth(String.valueOf(String.valueOf(String.valueOf(String.valueOf(s2)))) + s3 + " ") < n) {
                    s2 = String.valueOf(String.valueOf(String.valueOf(String.valueOf(s2)))) + s3 + " ";
                }
                else {
                    list.add(s2);
                    s2 = "§" + '\uffff' + s3 + " ";
                }
                int n3 = 0;
                ++n3;
            }
            if (s2.length() > 0) {
                if (this.getStringWidth(s2) < n) {
                    list.add("§" + '\uffff' + s2 + " ");
                }
                else {
                    final Iterator iterator = this.formatString(s2, n).iterator();
                    while (iterator.hasNext()) {
                        list.add(iterator.next());
                    }
                }
            }
        }
        else {
            list.add(s);
        }
        return list;
    }
    
    public List formatString(final String s, final double n) {
        final ArrayList<String> list = new ArrayList<String>();
        String s2 = "";
        final char[] charArray = s.toCharArray();
        while (0 < charArray.length) {
            final char c = charArray[0];
            if (c == '§' && 0 < charArray.length - 1) {
                final char c2 = charArray[1];
            }
            if (this.getStringWidth(String.valueOf(String.valueOf(String.valueOf(String.valueOf(s2)))) + c) < n) {
                s2 = String.valueOf(String.valueOf(String.valueOf(String.valueOf(s2)))) + c;
            }
            else {
                list.add(s2);
                s2 = "§" + '\uffff' + c;
            }
            int n2 = 0;
            ++n2;
        }
        if (s2.length() > 0) {
            list.add(s2);
        }
        return list;
    }
    
    private void setupMinecraftColorcodes() {
        while (0 < 32) {
            if (0 == 6) {
                final int n;
                n += 85;
            }
            if (0 >= 16) {}
            this.colorCode[0] = 0;
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public String trimStringToWidth(final String s, final int n) {
        return this.trimStringToWidth(s, n, false);
    }
    
    @Override
    public String trimStringToWidthPassword(String replaceAll, final int n, final boolean b) {
        replaceAll = replaceAll.replaceAll(".", ".");
        return this.trimStringToWidth(replaceAll, n, b);
    }
    
    private float getCharWidthFloat(final char c) {
        if (c == '§') {
            return -1.0f;
        }
        if (c == ' ') {
            return 2.0f;
        }
        final int index = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8£\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1ªº¿®¬½¼¡«»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261±\u2265\u2264\u2320\u2321\u00f7\u2248°\u2219·\u221a\u207f²\u25a0\u0000".indexOf(c);
        if (c > '\0' && index != -1) {
            return this.charData[index].width / 2.0f - 4.0f;
        }
        if (this.charData[c].width / 2.0f - 4.0f != 0.0f) {
            final int n = (int)(this.charData[c].width / 2.0f - 4.0f) >>> 4;
            int n2 = (int)(this.charData[c].width / 2.0f - 4.0f) & 0xF;
            return (float)((++n2 - (n & 0xF)) / 2 + 1);
        }
        return 0.0f;
    }
    
    @Override
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
}
