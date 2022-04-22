package com.viaversion.viaversion.bungee.commands;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.*;
import com.viaversion.viaversion.api.command.*;

public class BungeeCommand extends Command implements TabExecutor
{
    private final BungeeCommandHandler handler;
    
    public BungeeCommand(final BungeeCommandHandler handler) {
        super("viaversion", "", new String[] { "viaver", "vvbungee" });
        this.handler = handler;
    }
    
    public void execute(final CommandSender commandSender, final String[] array) {
        this.handler.onCommand(new BungeeCommandSender(commandSender), array);
    }
    
    public Iterable onTabComplete(final CommandSender commandSender, final String[] array) {
        return this.handler.onTabComplete(new BungeeCommandSender(commandSender), array);
    }
}
