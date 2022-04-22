package com.viaversion.viaversion.bukkit.commands;

import com.viaversion.viaversion.api.command.*;
import org.bukkit.command.*;
import java.util.*;
import org.bukkit.entity.*;

public class BukkitCommandSender implements ViaCommandSender
{
    private final CommandSender sender;
    
    public BukkitCommandSender(final CommandSender sender) {
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
        if (this.sender instanceof Player) {
            return ((Player)this.sender).getUniqueId();
        }
        return UUID.fromString(this.getName());
    }
    
    @Override
    public String getName() {
        return this.sender.getName();
    }
}
