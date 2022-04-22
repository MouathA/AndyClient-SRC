package com.viaversion.viaversion.velocity.command;

import com.viaversion.viaversion.commands.*;
import com.viaversion.viaversion.velocity.command.subs.*;
import com.viaversion.viaversion.api.command.*;
import java.util.*;
import com.velocitypowered.api.command.*;

public class VelocityCommandHandler extends ViaCommandHandler implements SimpleCommand
{
    public VelocityCommandHandler() {
        this.registerSubCommand(new ProbeSubCmd());
    }
    
    public void execute(final SimpleCommand.Invocation invocation) {
        this.onCommand(new VelocityCommandSender(invocation.source()), (String[])invocation.arguments());
    }
    
    public List suggest(final SimpleCommand.Invocation invocation) {
        return this.onTabComplete(new VelocityCommandSender(invocation.source()), (String[])invocation.arguments());
    }
    
    public List suggest(final CommandInvocation commandInvocation) {
        return this.suggest((SimpleCommand.Invocation)commandInvocation);
    }
    
    public void execute(final CommandInvocation commandInvocation) {
        this.execute((SimpleCommand.Invocation)commandInvocation);
    }
}
