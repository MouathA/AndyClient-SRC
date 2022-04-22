package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import Mood.*;

public class Day extends Command
{
    public Day() {
        super("Day", "Day", "Day", new String[] { "Day" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length <= 0) {
            final Minecraft mc = Day.mc;
            Minecraft.thePlayer.sendChatMessage("/time set day");
            Segito.msg("Mostant\u00f3l nappal van.");
        }
    }
}
