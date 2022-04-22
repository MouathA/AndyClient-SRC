package DTool.modules.player;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.settings.*;

public class SkinBlinker extends Module
{
    int current;
    
    public SkinBlinker() {
        super("SkinBlinker", 0, Category.Player);
        this.current = 0;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public boolean onEnable() {
        return super.onEnable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            ++this.current;
            if (this.current >= 2) {
                this.current = 0;
            }
            switch (this.current) {
                case 1: {
                    GameSettings.func_178877_a(EnumPlayerModelParts.JACKET);
                    GameSettings.func_178877_a(EnumPlayerModelParts.LEFT_SLEEVE);
                    GameSettings.func_178877_a(EnumPlayerModelParts.RIGHT_SLEEVE);
                    GameSettings.func_178877_a(EnumPlayerModelParts.RIGHT_PANTS_LEG);
                    GameSettings.func_178877_a(EnumPlayerModelParts.LEFT_PANTS_LEG);
                    GameSettings.func_178877_a(EnumPlayerModelParts.HAT);
                    break;
                }
            }
        }
    }
}
