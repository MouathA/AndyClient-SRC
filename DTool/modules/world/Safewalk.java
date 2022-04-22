package DTool.modules.world;

import DTool.modules.*;
import DTool.events.*;
import net.minecraft.entity.player.*;

public class Safewalk extends Module
{
    public Safewalk() {
        super("Safewalk", 0, Category.World);
    }
    
    @Override
    public boolean onEnable() {
        return super.onEnable();
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventSneaking && event.isPre()) {
            final EventSneaking eventSneaking = (EventSneaking)event;
            if (eventSneaking.entity.onGround && eventSneaking.entity instanceof EntityPlayer) {
                eventSneaking.sneaking = true;
            }
            else {
                eventSneaking.sneaking = false;
            }
            eventSneaking.offset = -1.0;
            eventSneaking.revertFlagAfter = true;
        }
    }
}
