package DTool.modules.movement;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;

public class Spider extends Module
{
    public Spider() {
        super("Spider", 0, Category.Movement);
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
        if (event instanceof EventUpdate && event.isPre()) {
            final Minecraft mc = Spider.mc;
            if (Minecraft.thePlayer.isCollidedHorizontally) {
                final Minecraft mc2 = Spider.mc;
                Minecraft.thePlayer.motionY = 0.2;
            }
        }
    }
}
