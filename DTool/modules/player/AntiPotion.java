package DTool.modules.player;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;
import DTool.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class AntiPotion extends Module
{
    public AntiPotion() {
        super("AntiPotion", 0, Category.Player);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            final Minecraft mc = AntiPotion.mc;
            if (Minecraft.thePlayer.capabilities.isCreativeMode) {
                return;
            }
            final Minecraft mc2 = AntiPotion.mc;
            if (!Minecraft.thePlayer.onGround) {
                return;
            }
            if (!Utils.hasBadEffect()) {
                return;
            }
            while (0 < 1000) {
                final Minecraft mc3 = AntiPotion.mc;
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
                int n = 0;
                ++n;
            }
        }
    }
}
