package DTool.modules.player;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;

public class PerfectHorseJump extends Module
{
    public PerfectHorseJump() {
        super("PerfectHorseJump", 0, Category.Player);
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return super.onEnable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate) {
            event.isPre();
        }
    }
}
