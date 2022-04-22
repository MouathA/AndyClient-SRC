package DTool.modules.player;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class NoFall extends Module
{
    public NoFall() {
        super("NoFall", 50, Category.Player);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            final Minecraft mc = NoFall.mc;
            if (Minecraft.thePlayer.fallDistance > 1.0f) {
                final Minecraft mc2 = NoFall.mc;
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
            }
        }
    }
}
