package Mood.Gui.clickgui.comp;

import Mood.Gui.clickgui.*;
import DTool.modules.*;
import Mood.Gui.clickgui.setting.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import java.math.*;

public class Slider extends Comp
{
    private boolean dragging;
    private double renderWidth;
    private double renderWidth2;
    
    public Slider(final double x, final double y, final Clickgui parent, final Module module, final Setting setting) {
        this.dragging = false;
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.module = module;
        this.setting = setting;
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        super.mouseClicked(n, n2, n3);
        if (this.isInside(n, n2, this.parent.posX + this.x - 70.0, this.parent.posY + this.y + 10.0, this.parent.posX + this.x - 70.0 + this.renderWidth2, this.parent.posY + this.y + 20.0) && n3 == 0) {
            this.dragging = true;
        }
    }
    
    @Override
    public void mouseReleased(final int n, final int n2, final int n3) {
        super.mouseReleased(n, n2, n3);
        this.dragging = false;
    }
    
    @Override
    public void drawScreen(final int n, final int n2) {
        super.drawScreen(n, n2);
        final double min = this.setting.getMin();
        final double max = this.setting.getMax();
        final double n3 = 90.0;
        this.renderWidth = n3 * (this.setting.getValDouble() - min) / (max - min);
        this.renderWidth2 = n3 * (this.setting.getMax() - min) / (max - min);
        final double min2 = Math.min(n3, Math.max(0.0, n - (this.parent.posX + this.x - 70.0)));
        if (this.dragging) {
            if (min2 == 0.0) {
                this.setting.setValDouble(this.setting.getMin());
            }
            else {
                this.setting.setValDouble(this.roundToPlace(min2 / n3 * (max - min) + min, 1));
            }
        }
        Gui.drawRect(this.parent.posX + this.x - 70.0, this.parent.posY + this.y + 10.0, this.parent.posX + this.x - 70.0 + this.renderWidth2, this.parent.posY + this.y + 20.0, new Color(230, 10, 230).darker().getRGB());
        Gui.drawRect(this.parent.posX + this.x - 70.0, this.parent.posY + this.y + 10.0, this.parent.posX + this.x - 70.0 + this.renderWidth, this.parent.posY + this.y + 20.0, new Color(230, 10, 230).getRGB());
        Minecraft.getMinecraft();
        Minecraft.fontRendererObj.drawString(String.valueOf(this.setting.getName()) + ": " + this.setting.getValDouble(), (int)(this.parent.posX + this.x - 70.0), (int)(this.parent.posY + this.y), -1);
    }
    
    private double roundToPlace(final double n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(n).setScale(n2, RoundingMode.HALF_UP).doubleValue();
    }
}
