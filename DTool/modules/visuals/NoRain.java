package DTool.modules.visuals;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;

public class NoRain extends Module
{
    boolean isRain;
    
    public NoRain() {
        super("NoRain", 0, Category.Visuals);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            final Minecraft mc = NoRain.mc;
            if (Minecraft.theWorld.isRaining()) {
                if (NoRain.mc.isSingleplayer()) {
                    final Minecraft mc2 = NoRain.mc;
                    Minecraft.theWorld.setRainStrength(0.0f);
                }
                else {
                    final Minecraft mc3 = NoRain.mc;
                    Minecraft.theWorld.setRainStrength(0.0f);
                    final Minecraft mc4 = NoRain.mc;
                    Minecraft.theWorld.setRainStrength(0.0f);
                }
            }
        }
    }
    
    @Override
    public boolean onEnable() {
        super.onEnable();
        final Minecraft mc = NoRain.mc;
        return this.isRain = Minecraft.theWorld.isRaining();
    }
    
    @Override
    public void onDisable() {
        if (this.isRain) {
            final Minecraft mc = NoRain.mc;
            Minecraft.theWorld.setRainStrength(1.0f);
        }
        super.onDisable();
    }
}
