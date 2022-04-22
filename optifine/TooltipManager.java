package optifine;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.settings.*;
import java.util.*;

public class TooltipManager
{
    private GuiScreen guiScreen;
    private int lastMouseX;
    private int lastMouseY;
    private long mouseStillTime;
    
    public TooltipManager(final GuiScreen guiScreen) {
        this.guiScreen = null;
        this.lastMouseX = 0;
        this.lastMouseY = 0;
        this.mouseStillTime = 0L;
        this.guiScreen = guiScreen;
    }
    
    public void drawTooltips(final int lastMouseX, final int lastMouseY, final List list) {
        if (Math.abs(lastMouseX - this.lastMouseX) <= 5 && Math.abs(lastMouseY - this.lastMouseY) <= 5) {
            if (System.currentTimeMillis() >= this.mouseStillTime + 700) {
                final int n = GuiScreen.width / 2 - 150;
                int n2 = GuiScreen.height / 6 - 7;
                if (lastMouseY <= n2 + 98) {
                    n2 += 105;
                }
                final int n3 = n + 150 + 150;
                final int n4 = n2 + 84 + 10;
                final GuiButton selectedButton = this.getSelectedButton(lastMouseX, lastMouseY, list);
                if (selectedButton instanceof IOptionControl) {
                    final String[] tooltipLines = getTooltipLines(((IOptionControl)selectedButton).getOption());
                    if (tooltipLines == null) {
                        return;
                    }
                    GuiVideoSettings.drawGradientRect(this.guiScreen, n, n2, n3, n4, -536870912, -536870912);
                    while (0 < tooltipLines.length) {
                        final String s = tooltipLines[0];
                        if (s.endsWith("!")) {}
                        Minecraft.getMinecraft();
                        Minecraft.fontRendererObj.func_175063_a(s, (float)(n + 5), (float)(n2 + 5 + 0), 16719904);
                        int n5 = 0;
                        ++n5;
                    }
                }
            }
        }
        else {
            this.lastMouseX = lastMouseX;
            this.lastMouseY = lastMouseY;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }
    
    private GuiButton getSelectedButton(final int n, final int n2, final List list) {
        while (0 < list.size()) {
            final GuiButton guiButton = list.get(0);
            final int buttonWidth = GuiVideoSettings.getButtonWidth(guiButton);
            final int buttonHeight = GuiVideoSettings.getButtonHeight(guiButton);
            if (n >= guiButton.xPosition && n2 >= guiButton.yPosition && n < guiButton.xPosition + buttonWidth && n2 < guiButton.yPosition + buttonHeight) {
                return guiButton;
            }
            int n3 = 0;
            ++n3;
        }
        return null;
    }
    
    private static String[] getTooltipLines(final GameSettings.Options options) {
        return getTooltipLines(options.getEnumString());
    }
    
    private static String[] getTooltipLines(final String s) {
        final ArrayList list = new ArrayList();
        if (list.size() <= 0) {
            return null;
        }
        return list.toArray(new String[list.size()]);
    }
}
