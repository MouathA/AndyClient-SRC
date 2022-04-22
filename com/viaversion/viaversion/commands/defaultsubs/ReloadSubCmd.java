package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.api.*;

public class ReloadSubCmd extends ViaSubCommand
{
    @Override
    public String name() {
        return "reload";
    }
    
    @Override
    public String description() {
        return "Reload the config from the disk";
    }
    
    @Override
    public boolean execute(final ViaCommandSender viaCommandSender, final String[] array) {
        Via.getPlatform().getConfigurationProvider().reloadConfig();
        ViaSubCommand.sendMessage(viaCommandSender, "&6Configuration successfully reloaded! Some features may need a restart.", new Object[0]);
        return true;
    }
}
