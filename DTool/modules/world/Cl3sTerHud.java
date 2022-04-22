package DTool.modules.world;

import DTool.modules.*;
import DTool.util.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.potion.*;

public class Cl3sTerHud extends Module
{
    public Cl3sTerHud() {
        super("HUD", 0, Category.World);
    }
    
    @Override
    public void onDisable() {
        Wrapper.getPlayer().removePotionEffect(Potion.nightVision.id);
        Wrapper.getPlayer().removePotionEffect(Potion.waterBreathing.id);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            Wrapper.getPlayer().addPotionEffect(new PotionEffect(Potion.nightVision.id, Integer.MAX_VALUE, 0));
            Wrapper.getPlayer().addPotionEffect(new PotionEffect(Potion.waterBreathing.id, Integer.MAX_VALUE, 0));
        }
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
}
