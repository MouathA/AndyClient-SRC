package DTool.modules.visuals;

import DTool.modules.*;
import DTool.events.*;
import net.minecraft.client.*;
import java.awt.*;
import Mood.*;
import DTool.events.listeners.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class TabGUI extends Module
{
    public int currentTab;
    public boolean expanded;
    
    public TabGUI() {
        super("TabGUI", 0, Category.Visuals);
        this.toggled = true;
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventRenderGUI) {
            final Minecraft mc = TabGUI.mc;
            final FontRenderer fontRendererObj = Minecraft.fontRendererObj;
            Gui.drawRect(1.0, 30.5, 70.0, 30 + Category.values().length * 16 + 1.5, Color.BLACK.getRGB());
            Gui.drawRect(1.0, 33 + this.currentTab * 16, 70.0, 33 + this.currentTab * 16 + 12, Color.ORANGE.getRGB());
            Category[] values;
            int n = 0;
            while (0 < (values = Category.values()).length) {
                fontRendererObj.drawStringWithShadow(values[0].name, 11.0, 35, -1);
                ++n;
                final byte b = 1;
            }
            if (this.expanded) {
                final Category category = Category.values()[this.currentTab];
                final List moduleByCategory = Client.getModuleByCategory(category);
                if (moduleByCategory.size() == 0) {
                    return;
                }
                Gui.drawRect(71.0, 30.5, 165.0, 30 + moduleByCategory.size() * 16 + 1.5, Color.BLACK.getRGB());
                Gui.drawRect(71.0, 33 + category.moduleIndex * 16, 165.0, 33 + category.moduleIndex * 16 + 12, Color.ORANGE.getRGB());
                final Iterator<Module> iterator = moduleByCategory.iterator();
                while (iterator.hasNext()) {
                    fontRendererObj.drawStringWithShadow(iterator.next().name, 73.0, 35, -1);
                    ++n;
                }
            }
        }
        if (event instanceof EventKey) {
            final int code = ((EventKey)event).code;
            final Category category2 = Category.values()[this.currentTab];
            final List moduleByCategory2 = Client.getModuleByCategory(category2);
            if (code == 200) {
                if (this.expanded) {
                    if (category2.moduleIndex <= 0) {
                        category2.moduleIndex = Category.values().length - 1;
                    }
                    else {
                        final Category category3 = category2;
                        --category3.moduleIndex;
                    }
                }
                else if (this.currentTab <= 0) {
                    this.currentTab = Category.values().length - 1;
                }
                else {
                    --this.currentTab;
                }
            }
            if (code == 208) {
                if (this.expanded) {
                    if (category2.moduleIndex >= moduleByCategory2.size() - 1) {
                        category2.moduleIndex = 0;
                    }
                    else {
                        final Category category4 = category2;
                        ++category4.moduleIndex;
                    }
                }
                else if (this.currentTab >= Category.values().length - 1) {
                    this.currentTab = 0;
                }
                else {
                    ++this.currentTab;
                }
            }
            if (code == 205) {
                if (this.expanded && moduleByCategory2.size() != 0) {
                    final Module module = moduleByCategory2.get(category2.moduleIndex);
                    if (!module.name.equals("TabGUI")) {
                        module.toggle();
                    }
                }
                else {
                    this.expanded = true;
                }
            }
            if (code == 203) {
                this.expanded = false;
            }
        }
    }
}
