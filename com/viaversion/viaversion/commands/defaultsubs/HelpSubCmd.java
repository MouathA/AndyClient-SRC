package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.api.*;

public class HelpSubCmd extends ViaSubCommand
{
    @Override
    public String name() {
        return "help";
    }
    
    @Override
    public String description() {
        return "You are looking at it right now!";
    }
    
    @Override
    public boolean execute(final ViaCommandSender viaCommandSender, final String[] array) {
        Via.getManager().getCommandHandler().showHelp(viaCommandSender);
        return true;
    }
}
