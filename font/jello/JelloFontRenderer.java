package font.jello;

import java.awt.*;

public class JelloFontRenderer
{
    public int FONT_HEIGHT;
    
    public JelloFontRenderer() {
        this.FONT_HEIGHT = 9;
    }
    
    public static JelloFontRenderer createFontRenderer(final Font font) {
        return new MinecraftFontRenderer(font, true, true);
    }
    
    public int drawString(String replaceAll, final double n, final float n2, final int n3) {
        replaceAll = replaceAll.replaceAll("\u00c3\u201a", "");
        return 0;
    }
    
    public int drawPassword(String replaceAll, final double n, final float n2, final int n3) {
        replaceAll = replaceAll.replaceAll("\u00c3\u201a", "");
        return 0;
    }
    
    public int drawNoBSString(String replaceAll, final double n, final float n2, final int n3) {
        replaceAll = replaceAll.replaceAll("\u00c3\u201a", "");
        return 0;
    }
    
    public int drawSmoothString(String replaceAll, final double n, final float n2, final int n3) {
        replaceAll = replaceAll.replaceAll("\u00c3\u201a", "");
        return 0;
    }
    
    public int drawStringWithShadow(String replaceAll, final double n, final float n2, final int n3) {
        replaceAll = replaceAll.replaceAll("\u00c3\u201a", "");
        return 0;
    }
    
    public float drawNoBSCenteredString(String replaceAll, final float n, final float n2, final int n3) {
        replaceAll = replaceAll.replaceAll("\u00c3\u201a", "");
        return 0.0f;
    }
    
    public float drawCenteredString(String replaceAll, final float n, final float n2, final int n3) {
        replaceAll = replaceAll.replaceAll("\u00c3\u201a", "");
        return 0.0f;
    }
    
    public float drawCenteredStringWithShadow(String replaceAll, final float n, final float n2, final int n3) {
        replaceAll = replaceAll.replaceAll("\u00c3\u201a", "");
        return 0.0f;
    }
    
    public double getStringWidth(String replaceAll) {
        replaceAll = replaceAll.replaceAll("\u00c3\u201a", "");
        return 0.0;
    }
    
    public double getPasswordWidth(String replaceAll) {
        replaceAll = replaceAll.replaceAll("\u00c3\u201a", "");
        return 0.0;
    }
    
    public int getHeight() {
        return 0;
    }
    
    public static Font createFontFromFile(final String s, final int n) {
        return Font.createFont(0, new Object().getClass().getResourceAsStream("/" + s + ".ttf")).deriveFont(0, (float)n);
    }
    
    public String trimStringToWidth(String replaceAll, final int n, final boolean b) {
        replaceAll = replaceAll.replaceAll("\u00c3\u201a", "");
        return "";
    }
    
    public String trimStringToWidth(String replaceAll, final int n) {
        replaceAll = replaceAll.replaceAll("\u00c3\u201a", "");
        return "";
    }
    
    public String trimStringToWidthPassword(String replaceAll, final int n, final boolean b) {
        replaceAll = replaceAll.replaceAll("\u00c3\u201a", "");
        return "";
    }
}
