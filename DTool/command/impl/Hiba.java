package DTool.command.impl;

import DTool.command.*;
import Mood.*;

public class Hiba extends Command
{
    public Hiba() {
        super("-", "-", "-", new String[] { "-" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        Segito.msg("Ilyen parancs nem l\u00e9tezik!");
    }
}
