package DTool.modules.movement;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;

public class WaterSpeed extends Module
{
    public WaterSpeed() {
        super("WaterSpeed", 0, Category.Movement);
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
            final Minecraft mc = WaterSpeed.mc;
            final EntityPlayerSP thePlayer = Minecraft.thePlayer;
            if (!thePlayer.isInWater() || thePlayer.isSneaking()) {
                return;
            }
            final EntityPlayerSP entityPlayerSP = thePlayer;
            entityPlayerSP.motionY += 0.15;
        }
    }
}
