package DTool.modules.combat;

import DTool.modules.*;
import DTool.util.*;
import java.util.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.client.multiplayer.*;

public class Triggerbot extends Module
{
    Timer delay;
    Random r;
    
    public Triggerbot() {
        super("Triggerbot", 0, Category.Combat);
        this.delay = new Timer();
        this.r = new Random();
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            final Minecraft mc = Triggerbot.mc;
            final boolean sprinting = Minecraft.thePlayer.isSprinting();
            if (Triggerbot.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && this.delay.isDelayComplete(70 + this.r.nextInt(66))) {
                this.delay.setLastMS();
                final Entity entityHit = Triggerbot.mc.objectMouseOver.entityHit;
                final Minecraft mc2 = Triggerbot.mc;
                Minecraft.thePlayer.setSprinting(false);
                if (entityHit instanceof EntityPlayer && entityHit.isEntityAlive()) {
                    final Minecraft mc3 = Triggerbot.mc;
                    if (!Minecraft.thePlayer.isUsingItem()) {
                        final Minecraft mc4 = Triggerbot.mc;
                        if (!Minecraft.thePlayer.isDead) {
                            final Minecraft mc5 = Triggerbot.mc;
                            Minecraft.thePlayer.swingItem();
                            final Minecraft mc6 = Triggerbot.mc;
                            final PlayerControllerMP playerController = Minecraft.playerController;
                            final Minecraft mc7 = Triggerbot.mc;
                            playerController.attackEntity(Minecraft.thePlayer, entityHit);
                            final Minecraft mc8 = Triggerbot.mc;
                            Minecraft.thePlayer.setSprinting(sprinting);
                        }
                    }
                }
            }
        }
    }
}
