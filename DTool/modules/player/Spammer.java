package DTool.modules.player;

import DTool.modules.*;
import DTool.events.*;
import DTool.events.listeners.*;
import net.minecraft.client.*;
import java.util.*;

public class Spammer extends Module
{
    int current;
    public static String text;
    
    static {
        Spammer.text = "Andy Mod Client By: iTzMatthew1337!";
    }
    
    public Spammer() {
        super("Spammer", 0, Category.Player);
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
            if (this.current >= 0.0f * 20.0f) {
                this.current = 0;
                final Minecraft mc = Spammer.mc;
                Minecraft.thePlayer.sendChatMessage(Spammer.text.replace("%random%", new StringBuilder(String.valueOf(new Random().nextInt(9))).toString()));
            }
            else {
                ++this.current;
            }
        }
    }
}
