package DTool.modules.player;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;

public class FastPlace extends Module
{
    public FastPlace() {
        super("FastPlace", 0, Category.Player);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            final Minecraft mc = FastPlace.mc;
            Minecraft.rightClickDelayTimer = 0;
        }
    }
}
