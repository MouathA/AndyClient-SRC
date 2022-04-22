package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import Mood.*;

public class Weather extends Command
{
    public Weather() {
        super("Weather", "Weather", "Weather", new String[] { "Weather" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length <= 0) {
            final Minecraft mc = Weather.mc;
            Minecraft.thePlayer.sendChatMessage("/toggledownfall");
            Segito.msg("Mostant\u00f3l csapad\u00e9kos id\u0151 van.");
        }
        if (array[0].equalsIgnoreCase("off")) {
            final Minecraft mc2 = Weather.mc;
            Minecraft.thePlayer.sendChatMessage("/weather clear");
            Segito.msg("Csapad\u00e9k el\u00e1ll\u00edtva.");
        }
    }
}
