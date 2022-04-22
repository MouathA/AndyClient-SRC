package Mood.Gui.clickgui.comp;

import Mood.Gui.clickgui.*;
import DTool.modules.*;
import Mood.Gui.clickgui.setting.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;

public class CheckBox extends Comp
{
    public CheckBox(final double x, final double y, final Clickgui parent, final Module module, final Setting setting) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.module = module;
        this.setting = setting;
    }
    
    @Override
    public void drawScreen(final int n, final int n2) {
        super.drawScreen(n, n2);
        Gui.drawRect(this.parent.posX + this.x - 70.0, this.parent.posY + this.y, this.parent.posX + this.x + 10.0 - 70.0, this.parent.posY + this.y + 10.0, this.setting.getValBoolean() ? new Color(230, 10, 230).getRGB() : new Color(30, 30, 30).getRGB());
        Minecraft.getMinecraft();
        Minecraft.fontRendererObj.drawString(this.setting.getName(), (int)(this.parent.posX + this.x - 55.0), (int)(this.parent.posY + this.y + 1.0), new Color(200, 200, 200).getRGB());
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        super.mouseClicked(n, n2, n3);
        if (this.isInside(n, n2, this.parent.posX + this.x - 70.0, this.parent.posY + this.y, this.parent.posX + this.x + 10.0 - 70.0, this.parent.posY + this.y + 10.0) && n3 == 0) {
            this.setting.setValBoolean(!this.setting.getValBoolean());
        }
    }
}
