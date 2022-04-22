package DTool.modules.player;

import DTool.modules.*;
import net.minecraft.client.entity.*;
import DTool.events.*;
import DTool.events.listeners.*;

public class InstantRespawn extends Module
{
    EntityPlayerSP player;
    
    public InstantRespawn() {
        super("InstantRespawn", 0, Category.Player);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre() && this.player != null && this.player.getHealth() <= 0.0f) {
            this.player.respawnPlayer();
        }
    }
}
