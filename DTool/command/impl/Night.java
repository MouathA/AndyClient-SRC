package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import Mood.*;

public class Night extends Command
{
    public Night() {
        super("Night", "Night", "Night", new String[] { "Night" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length <= 0) {
            final Minecraft mc = Night.mc;
            Minecraft.thePlayer.sendChatMessage("/time set 16000");
            Segito.msg("Mostant\u00f3l \u00e9jszaka van.");
        }
    }
}
