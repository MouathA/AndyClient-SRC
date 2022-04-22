package DTool.modules.movement;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;

public class LongJump extends Module
{
    public LongJump() {
        super("LongJump", 0, Category.Movement);
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
            if (LongJump.mc.gameSettings.keyBindForward.pressed || LongJump.mc.gameSettings.keyBindBack.pressed || LongJump.mc.gameSettings.keyBindLeft.pressed || (LongJump.mc.gameSettings.keyBindRight.pressed && LongJump.mc.gameSettings.keyBindJump.pressed)) {
                final Minecraft mc = LongJump.mc;
                if (Minecraft.thePlayer.isAirBorne) {
                    final Minecraft mc2 = LongJump.mc;
                    final EntityPlayerSP thePlayer = Minecraft.thePlayer;
                    thePlayer.motionX *= 1.11;
                    final Minecraft mc3 = LongJump.mc;
                    final EntityPlayerSP thePlayer2 = Minecraft.thePlayer;
                    thePlayer2.motionZ *= 1.11;
                }
            }
            else {
                final Minecraft mc4 = LongJump.mc;
                final EntityPlayerSP thePlayer3 = Minecraft.thePlayer;
                thePlayer3.motionX *= 1.0;
                final Minecraft mc5 = LongJump.mc;
                final EntityPlayerSP thePlayer4 = Minecraft.thePlayer;
                thePlayer4.motionZ *= 1.0;
            }
        }
    }
}
