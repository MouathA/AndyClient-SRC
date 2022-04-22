package DTool.modules.movement;

import DTool.modules.*;
import net.minecraft.client.*;
import DTool.events.*;
import DTool.events.listeners.*;

public class Step extends Module
{
    public Step() {
        super("Step", 0, Category.Movement);
    }
    
    @Override
    public void onDisable() {
        final Minecraft mc = Step.mc;
        Minecraft.thePlayer.capabilities.isFlying = false;
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            final Minecraft mc = Step.mc;
            Minecraft.thePlayer.stepHeight = 10.0;
        }
    }
}
