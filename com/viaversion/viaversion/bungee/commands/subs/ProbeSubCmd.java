package com.viaversion.viaversion.bungee.commands.subs;

import com.viaversion.viaversion.bungee.platform.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.bungee.service.*;

public class ProbeSubCmd extends ViaSubCommand
{
    @Override
    public String name() {
        return "probe";
    }
    
    @Override
    public String description() {
        return "Forces ViaVersion to scan server protocol versions " + ((((BungeeViaConfig)Via.getConfig()).getBungeePingInterval() == -1) ? "" : "(Also happens at an interval)");
    }
    
    @Override
    public boolean execute(final ViaCommandSender viaCommandSender, final String[] array) {
        ProtocolDetectorService.getInstance().run();
        ViaSubCommand.sendMessage(viaCommandSender, "&6Started searching for protocol versions", new Object[0]);
        return true;
    }
}
