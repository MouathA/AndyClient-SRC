package com.viaversion.viaversion.bungee.commands;

import com.viaversion.viaversion.api.command.*;
import net.md_5.bungee.api.*;
import java.util.*;
import net.md_5.bungee.api.connection.*;

public class BungeeCommandSender implements ViaCommandSender
{
    private final CommandSender sender;
    
    public BungeeCommandSender(final CommandSender sender) {
        this.sender = sender;
    }
    
    @Override
    public boolean hasPermission(final String s) {
        return this.sender.hasPermission(s);
    }
    
    @Override
    public void sendMessage(final String s) {
        this.sender.sendMessage(s);
    }
    
    @Override
    public UUID getUUID() {
        if (this.sender instanceof ProxiedPlayer) {
            return ((ProxiedPlayer)this.sender).getUniqueId();
        }
        return UUID.fromString(this.getName());
    }
    
    @Override
    public String getName() {
        return this.sender.getName();
    }
}
