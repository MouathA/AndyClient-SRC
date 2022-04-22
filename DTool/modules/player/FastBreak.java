package DTool.modules.player;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.multiplayer.*;

public class FastBreak extends Module
{
    public FastBreak() {
        super("FastBreak", 0, Category.Player);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            PlayerControllerMP.blockHitDelay = 0;
        }
    }
}
