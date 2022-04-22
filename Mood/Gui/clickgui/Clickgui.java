package Mood.Gui.clickgui;

import DTool.modules.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import Mood.*;
import DTool.util.*;
import java.util.*;
import java.io.*;
import Mood.Gui.clickgui.setting.*;
import Mood.Gui.clickgui.comp.*;
import net.minecraft.client.*;

public class Clickgui extends GuiScreen
{
    public double posX;
    public double posY;
    public double width;
    public double height;
    public double dragX;
    public double dragY;
    public boolean dragging;
    public Module.Category selectedCategory;
    private Module selectedModule;
    public int modeIndex;
    public ArrayList comps;
    
    public Clickgui() {
        this.comps = new ArrayList();
        this.dragging = false;
        this.getScaledRes();
        this.posX = ScaledResolution.getScaledWidth() / 2 - 150;
        this.getScaledRes();
        this.posY = ScaledResolution.getScaledHeight() / 2 - 100;
        this.width = this.posX + 300.0;
        this.height += 200.0;
        this.selectedCategory = Module.Category.Combat;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        if (this.dragging) {
            this.posX = n - this.dragX;
            this.posY = n2 - this.dragY;
        }
        this.width = this.posX + 300.0;
        this.height = this.posY + 200.0;
        Gui.drawRect(this.posX, this.posY - 10.0, this.width, this.posY, Color.DARK_GRAY.getRGB());
        Gui.drawRect(this.posX, this.posY, this.width, this.height, new Color(45, 45, 45).getRGB());
        Module.Category[] values;
        int n4 = 0;
        while (0 < (values = Module.Category.values()).length) {
            final Module.Category category = values[0];
            Gui.drawRect(this.posX, this.posY + 1.0 + 0, this.posX + 60.0, this.posY + 15.0 + 0, category.equals(this.selectedCategory) ? Color.BLACK.getRGB() : Color.BLACK.getRGB());
            this.fontRendererObj.drawString(category.name(), (int)this.posX + 2, (int)(this.posY + 5.0) + 0, Color.WHITE.getRGB());
            n4 += 15;
            int n5 = 0;
            ++n5;
        }
        final Client instance = Client.INSTANCE;
        for (final Module module : Client.getModuleByCategory(this.selectedCategory)) {
            Gui.drawRect(this.posX + 62.0, this.posY + 1.0 + 0, this.posX + 158.0, this.posY + 15.0 + 0, module.onEnable() ? Color.BLACK.getRGB() : new Color(35, 35, 35).getRGB());
            this.fontRendererObj.drawString(module.getName(), (int)this.posX + 67, (int)(this.posY + 5.0) + 0, ColorUtils.faszarainbow());
            n4 += 15;
        }
        final Iterator<Comp> iterator2 = this.comps.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().drawScreen(n, n2);
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
        final Iterator<Comp> iterator = this.comps.iterator();
        while (iterator.hasNext()) {
            iterator.next().keyTyped(c, n);
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        if (this.isInside(n, n2, this.posX, this.posY - 10.0, this.width, this.posY) && n3 == 0) {
            this.dragging = true;
            this.dragX = n - this.posX;
            this.dragY = n2 - this.posY;
        }
        final Module.Category[] values;
        int length = (values = Module.Category.values()).length;
        int n4 = 0;
        while (0 < 3) {
            final Module.Category selectedCategory = values[0];
            if (this.isInside(n, n2, this.posX, this.posY + 1.0 + 0, this.posX + 60.0, this.posY + 15.0 + 0) && n3 == 0) {
                this.selectedCategory = selectedCategory;
            }
            n4 += 15;
            int n5 = 0;
            ++n5;
        }
        final Client instance = Client.INSTANCE;
        for (final Module selectedModule : Client.getModuleByCategory(this.selectedCategory)) {
            if (this.isInside(n, n2, this.posX + 65.0, this.posY + 1.0 + 0, this.posX + 125.0, this.posY + 15.0 + 0)) {
                if (n3 == 0) {
                    selectedModule.toggle();
                }
                if (n3 == 1) {
                    this.comps.clear();
                    if (Client.INSTANCE.getSettingsManager().getSettingsByMod(selectedModule) != null) {
                        for (final Setting setting : Client.INSTANCE.getSettingsManager().getSettingsByMod(selectedModule)) {
                            this.selectedModule = selectedModule;
                            if (setting.isCombo()) {
                                this.comps.add(new Combo(275.0, 3, this, this.selectedModule, setting));
                                length += 15;
                            }
                            if (setting.isCheck()) {
                                this.comps.add(new CheckBox(275.0, 3, this, this.selectedModule, setting));
                                length += 15;
                            }
                            if (setting.isSlider()) {
                                this.comps.add(new Slider(275.0, 3, this, this.selectedModule, setting));
                                length += 25;
                            }
                        }
                    }
                }
            }
            n4 += 15;
        }
        final Iterator<Comp> iterator3 = (Iterator<Comp>)this.comps.iterator();
        while (iterator3.hasNext()) {
            iterator3.next().mouseClicked(n, n2, n3);
        }
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        super.mouseReleased(n, n2, n3);
        this.dragging = false;
        final Iterator<Comp> iterator = this.comps.iterator();
        while (iterator.hasNext()) {
            iterator.next().mouseReleased(n, n2, n3);
        }
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.dragging = false;
    }
    
    public boolean isInside(final int n, final int n2, final double n3, final double n4, final double n5, final double n6) {
        return n > n3 && n < n5 && n2 > n4 && n2 < n6;
    }
    
    public ScaledResolution getScaledRes() {
        return new ScaledResolution(Minecraft.getMinecraft());
    }
}
