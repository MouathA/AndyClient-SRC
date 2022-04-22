package DTool.modules.movement;

import DTool.modules.*;
import net.minecraft.client.*;
import DTool.events.*;
import DTool.events.listeners.*;

public class Sprint extends Module
{
    public Sprint() {
        super("Sprint", 49, Category.Movement);
    }
    
    @Override
    public void onDisable() {
        final Minecraft mc = Sprint.mc;
        Minecraft.thePlayer.setSprinting(Sprint.mc.gameSettings.keyBindSprint.getIsKeyPressed());
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            final Minecraft mc = Sprint.mc;
            if (Minecraft.thePlayer.moveForward > 0.0f) {
                final Minecraft mc2 = Sprint.mc;
                if (!Minecraft.thePlayer.isUsingItem()) {
                    final Minecraft mc3 = Sprint.mc;
                    if (!Minecraft.thePlayer.isSneaking()) {
                        final Minecraft mc4 = Sprint.mc;
                        if (!Minecraft.thePlayer.isCollidedHorizontally) {
                            final Minecraft mc5 = Sprint.mc;
                            Minecraft.thePlayer.setSprinting(true);
                        }
                    }
                }
            }
        }
    }
}
