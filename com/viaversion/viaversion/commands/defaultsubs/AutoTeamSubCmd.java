package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.configuration.*;

public class AutoTeamSubCmd extends ViaSubCommand
{
    @Override
    public String name() {
        return "autoteam";
    }
    
    @Override
    public String description() {
        return "Toggle automatically teaming to prevent colliding.";
    }
    
    @Override
    public boolean execute(final ViaCommandSender viaCommandSender, final String[] array) {
        final ConfigurationProvider configurationProvider = Via.getPlatform().getConfigurationProvider();
        final boolean b = !Via.getConfig().isAutoTeam();
        configurationProvider.set("auto-team", b);
        configurationProvider.saveConfig();
        ViaSubCommand.sendMessage(viaCommandSender, "&6We will %s", b ? "&aautomatically team players" : "&cno longer auto team players");
        ViaSubCommand.sendMessage(viaCommandSender, "&6All players will need to re-login for the change to take place.", new Object[0]);
        return true;
    }
}
