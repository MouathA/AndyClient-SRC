package DTool.modules.player;

import DTool.modules.*;
import Mood.Host.Helper.*;
import net.minecraft.util.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;

public class Speed extends Module
{
    TimeHelper time;
    
    public Speed() {
        super("BHSpeed", 0, Category.Player);
        this.time = new TimeHelper();
    }
    
    @Override
    public void onDisable() {
        final Timer timer = Speed.mc.timer;
        Timer.timerSpeed = 1.0f;
        this.time.reset();
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            final Minecraft mc = Speed.mc;
            if (Minecraft.thePlayer.onGround) {
                this.time.reset();
                final Timer timer = Speed.mc.timer;
                Timer.timerSpeed = 2.0f;
                if (Speed.mc.gameSettings.keyBindForward.isKeyDown() || Speed.mc.gameSettings.keyBindLeft.isKeyDown() || Speed.mc.gameSettings.keyBindRight.isKeyDown()) {
                    final Minecraft mc2 = Speed.mc;
                    Minecraft.thePlayer.jump();
                }
            }
            else if (this.time.hasReached(300L)) {
                this.time.reset();
                final Timer timer2 = Speed.mc.timer;
                Timer.timerSpeed = 2.0f;
            }
        }
    }
}
