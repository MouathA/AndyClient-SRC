package DTool.modules.movement;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;

public class JetPack extends Module
{
    public JetPack() {
        super("JetPack", 0, Category.Movement);
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
        if (event instanceof EventUpdate && event.isPre() && JetPack.mc.gameSettings.keyBindJump.isKeyDown()) {
            final Minecraft mc = JetPack.mc;
            final EntityPlayerSP thePlayer = Minecraft.thePlayer;
            thePlayer.motionY += 0.07;
        }
    }
}
