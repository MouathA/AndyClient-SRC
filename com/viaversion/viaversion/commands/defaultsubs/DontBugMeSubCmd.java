package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.configuration.*;

public class DontBugMeSubCmd extends ViaSubCommand
{
    @Override
    public String name() {
        return "dontbugme";
    }
    
    @Override
    public String description() {
        return "Toggle checking for updates";
    }
    
    @Override
    public boolean execute(final ViaCommandSender viaCommandSender, final String[] array) {
        final ConfigurationProvider configurationProvider = Via.getPlatform().getConfigurationProvider();
        final boolean checkForUpdates = !Via.getConfig().isCheckForUpdates();
        Via.getConfig().setCheckForUpdates(checkForUpdates);
        configurationProvider.saveConfig();
        ViaSubCommand.sendMessage(viaCommandSender, "&6We will %snotify you about updates.", checkForUpdates ? "&a" : "&cnot ");
        return true;
    }
}
