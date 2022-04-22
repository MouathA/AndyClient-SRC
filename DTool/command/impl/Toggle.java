package DTool.command.impl;

import DTool.command.*;
import DTool.modules.*;
import Mood.*;
import java.util.*;

public class Toggle extends Command
{
    public Toggle() {
        super("t", "Toggles a module by name", "toggle <name>", new String[] { "" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length > 0) {
            final String s2 = array[0];
            for (final Module module : Client.modules) {
                if (module.name.equalsIgnoreCase(s2)) {
                    module.toggle();
                    Segito.msg(module.isEnable() ? (String.valueOf(module.name) + " >§a Bekapcsolva") : (module.name + " >§c Kikapcsolva"));
                    break;
                }
            }
            if (!true) {
                Segito.msg("-t <Module neve>");
            }
        }
    }
}
