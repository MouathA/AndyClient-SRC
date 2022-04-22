package DTool.modules.movement;

import DTool.modules.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import DTool.events.*;
import DTool.events.listeners.*;

public class Flight extends Module
{
    public Flight() {
        super("Flight", 7, Category.Movement);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        final Minecraft mc = Flight.mc;
        if (!Minecraft.thePlayer.capabilities.isCreativeMode) {
            final Minecraft mc2 = Flight.mc;
            if (!Minecraft.playerController.getCurrentGameType().equals(WorldSettings.GameType.SPECTATOR)) {
                final Minecraft mc3 = Flight.mc;
                Minecraft.thePlayer.capabilities.allowFlying = false;
            }
        }
        final Minecraft mc4 = Flight.mc;
        Minecraft.thePlayer.capabilities.setFlySpeed(0.05f);
        final Minecraft mc5 = Flight.mc;
        Minecraft.thePlayer.capabilities.isFlying = false;
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            final Minecraft mc = Flight.mc;
            Minecraft.thePlayer.capabilities.allowFlying = true;
            final Minecraft mc2 = Flight.mc;
            Minecraft.thePlayer.capabilities.isFlying = true;
            final Minecraft mc3 = Flight.mc;
            Minecraft.thePlayer.capabilities.setFlySpeed(0.0f);
        }
    }
}
