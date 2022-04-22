package DTool.modules.world;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;

public class Tower extends Module
{
    public Tower() {
        super("Tower", 0, Category.World);
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate) {
            final Minecraft mc = Tower.mc;
            if (Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
                final Minecraft mc2 = Tower.mc;
                final EntityPlayerSP thePlayer = Minecraft.thePlayer;
                final Minecraft mc3 = Tower.mc;
                thePlayer.setRotation(Minecraft.thePlayer.cameraYaw, 90.0f);
                final Minecraft mc4 = Tower.mc;
                Minecraft.rightClickDelayTimer = 0;
                Timer.timerSpeed = 1.6f;
                Tower.mc.gameSettings.keyBindUseItem.pressed = true;
                final Minecraft mc5 = Tower.mc;
                Minecraft.thePlayer.jump();
            }
        }
    }
    
    @Override
    public void onDisable() {
        Tower.mc.gameSettings.keyBindUseItem.pressed = false;
        final Minecraft mc = Tower.mc;
        Minecraft.rightClickDelayTimer = 4;
        Timer.timerSpeed = 1.0f;
        super.onDisable();
    }
}
