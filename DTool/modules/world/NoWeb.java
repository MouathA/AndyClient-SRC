package DTool.modules.world;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;

public class NoWeb extends Module
{
    public NoWeb() {
        super("NoWeb", 0, Category.World);
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
        if (event instanceof EventUpdate) {
            final Minecraft mc = NoWeb.mc;
            Minecraft.thePlayer.isInWeb = false;
        }
    }
}
