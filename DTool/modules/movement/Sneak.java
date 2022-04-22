package DTool.modules.movement;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;

public class Sneak extends Module
{
    public Sneak() {
        super("Sneak", 0, Category.Movement);
    }
    
    @Override
    public void onDisable() {
        Sneak.mc.gameSettings.keyBindSneak.pressed = false;
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            Sneak.mc.gameSettings.keyBindSneak.pressed = true;
        }
    }
}
