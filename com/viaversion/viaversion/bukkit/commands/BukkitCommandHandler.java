package com.viaversion.viaversion.bukkit.commands;

import com.viaversion.viaversion.commands.*;
import org.bukkit.command.*;
import com.viaversion.viaversion.api.command.*;
import java.util.*;

public class BukkitCommandHandler extends ViaCommandHandler implements CommandExecutor, TabExecutor
{
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] array) {
        return this.onCommand(new BukkitCommandSender(commandSender), array);
    }
    
    public List onTabComplete(final CommandSender commandSender, final Command command, final String s, final String[] array) {
        return this.onTabComplete(new BukkitCommandSender(commandSender), array);
    }
}
