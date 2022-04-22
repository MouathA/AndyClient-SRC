package com.viaversion.viaversion.bungee.commands;

import com.viaversion.viaversion.commands.*;
import com.viaversion.viaversion.bungee.commands.subs.*;
import com.viaversion.viaversion.api.command.*;

public class BungeeCommandHandler extends ViaCommandHandler
{
    public BungeeCommandHandler() {
        this.registerSubCommand(new ProbeSubCmd());
    }
}
