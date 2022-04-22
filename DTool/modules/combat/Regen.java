package DTool.modules.combat;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;

public class Regen extends Module
{
    public Regen() {
        super("Regen", 0, Category.Combat);
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
            final Minecraft mc = Regen.mc;
            if (Minecraft.thePlayer.getHealth() <= 17.0f) {
                final Minecraft mc2 = Regen.mc;
                if (Minecraft.thePlayer.isCollidedVertically) {}
            }
        }
    }
}
