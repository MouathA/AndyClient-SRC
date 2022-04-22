package DTool.modules.world;

import DTool.modules.*;
import Mood.*;
import DTool.events.*;
import DTool.events.listeners.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.gui.*;

public class Shop extends Module
{
    public GuiScreen guiScreen;
    
    public Shop() {
        super("Shop", 0, Category.World);
    }
    
    @Override
    public void onDisable() {
        Shop.mc.setIngameFocus();
        Shop.mc.displayGuiScreen(this.guiScreen);
    }
    
    @Override
    public boolean onEnable() {
        this.guiScreen = Shop.mc.currentScreen;
        Segito.msg("ein GUI (z.B. Bedwars Villager)");
        Segito.msg("Dr[R-SHIFT] um das GUI zu saven. ");
        Segito.msg("Wenn du sp[P] drsiehst du das GUI wieder.");
        return super.onEnable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre() && Shop.mc.currentScreen != null && !(Shop.mc.currentScreen instanceof GuiChat)) {
            if (Keyboard.isKeyDown(54)) {
                this.guiScreen = Shop.mc.currentScreen;
                Shop.mc.setIngameFocus();
                Segito.msg("Gui wurde erfolgreich gespeichert");
            }
            if (!(this.guiScreen instanceof GuiEditSign)) {
                final boolean b = this.guiScreen instanceof GuiScreenBook;
            }
        }
    }
}
