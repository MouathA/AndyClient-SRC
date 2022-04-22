package font.jello.Utils;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import font.jello.*;
import net.minecraft.util.*;
import java.awt.*;

public class FontUtil
{
    public static Minecraft mc;
    public static FontRenderer fr;
    public static JelloFontRenderer jelloFont;
    public static JelloFontRenderer jelloFontScale;
    public static JelloFontRenderer jelloFontAddAlt;
    public static JelloFontRenderer jelloFontGui;
    public static JelloFontRenderer jelloFontDuration;
    public static JelloFontRenderer jelloFontMusic;
    public static JelloFontRenderer jelloFontAddAlt2;
    public static JelloFontRenderer jelloFontAddAlt3;
    public static JelloFontRenderer jelloFontRegular;
    public static JelloFontRenderer jelloFontRegularBig;
    public static JelloFontRenderer jelloFontBoldSmall;
    public static JelloFontRenderer jelloFontMarker;
    public static JelloFontRenderer jelloFontSmall;
    public static JelloFontRenderer jelloFontSmallPassword;
    public static JelloFontRenderer jelloFontBig;
    public static JelloFontRenderer jelloFontMedium;
    public static JelloFontRenderer font;
    public static JelloFontRenderer fontBig;
    public static JelloFontRenderer fontSmall;
    public static JelloFontRenderer superherofx1;
    public static JelloFontRenderer superherofx2;
    public static JelloFontRenderer spicyClickGuiFont;
    
    static {
        FontUtil.mc = Minecraft.getMinecraft();
        FontUtil.fr = Minecraft.fontRendererObj;
        FontUtil.jelloFont = JelloFontRenderer.createFontRenderer(getJelloFont(20.0f, false));
        FontUtil.jelloFontScale = JelloFontRenderer.createFontRenderer(getJelloFont(24.0f, false));
        FontUtil.jelloFontAddAlt = JelloFontRenderer.createFontRenderer(getJelloFont(24.0f, false));
        FontUtil.jelloFontGui = JelloFontRenderer.createFontRenderer(getJelloFont(25.0f, false));
        FontUtil.jelloFontDuration = JelloFontRenderer.createFontRenderer(getJelloFont(13.0f, false));
        FontUtil.jelloFontMusic = JelloFontRenderer.createFontRenderer(getJelloFont(12.0f, false));
        FontUtil.jelloFontAddAlt2 = JelloFontRenderer.createFontRenderer(getJelloFont(35.0f, false));
        FontUtil.jelloFontAddAlt3 = JelloFontRenderer.createFontRenderer(getJelloFont(19.0f, false));
        FontUtil.jelloFontRegular = JelloFontRenderer.createFontRenderer(getJelloFontRegular(20));
        FontUtil.jelloFontRegularBig = JelloFontRenderer.createFontRenderer(getJelloFontRegular(24));
        FontUtil.jelloFontBoldSmall = JelloFontRenderer.createFontRenderer(getJelloFont(19.0f, true));
        FontUtil.jelloFontMarker = JelloFontRenderer.createFontRenderer(getJelloFont(19.0f, false));
        FontUtil.jelloFontSmall = JelloFontRenderer.createFontRenderer(getJelloFont(14.0f, false));
        FontUtil.jelloFontSmallPassword = JelloFontRenderer.createFontRenderer(getJelloFont(16.0f, false));
        FontUtil.jelloFontBig = JelloFontRenderer.createFontRenderer(getJelloFont(41.0f, true));
        FontUtil.jelloFontMedium = JelloFontRenderer.createFontRenderer(getJelloFont(25.0f, false));
        FontUtil.font = JelloFontRenderer.createFontRenderer(getJelloFontRegular(18));
        FontUtil.fontBig = JelloFontRenderer.createFontRenderer(getJelloFontRegular(33));
        FontUtil.fontSmall = JelloFontRenderer.createFontRenderer(getJelloFontRegular(14));
        FontUtil.superherofx1 = JelloFontRenderer.createFontRenderer(getFont(14.0f, false, new ResourceLocation("MooDTool/fonts/arial.ttf")));
        FontUtil.superherofx2 = JelloFontRenderer.createFontRenderer(getFont(14.0f, false, new ResourceLocation("MooDTool/fonts/arial.ttf")));
        FontUtil.spicyClickGuiFont = JelloFontRenderer.createFontRenderer(getJelloFont(23.0f, false));
    }
    
    private static Font getFont(final float n, final boolean b, final ResourceLocation resourceLocation) {
        return Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation).getInputStream()).deriveFont(0, n);
    }
    
    private static Font getJelloFont(final float n, final boolean b) {
        return Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(b ? "Jello/jellomedium.ttf" : "Jello/jellolight.ttf")).getInputStream()).deriveFont(0, n);
    }
    
    private static Font getJelloFontRegular(final int n) {
        return Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("Jello/jelloregular.ttf")).getInputStream()).deriveFont(0, (float)n);
    }
    
    private static Font getJelloFontUltralight(final int n) {
        return Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("Jello/jelloultralight.ttf")).getInputStream()).deriveFont(0, (float)n);
    }
}
