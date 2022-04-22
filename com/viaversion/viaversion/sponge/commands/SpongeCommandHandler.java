package com.viaversion.viaversion.sponge.commands;

import com.viaversion.viaversion.commands.*;
import org.spongepowered.api.command.parameter.*;
import com.viaversion.viaversion.api.command.*;
import org.spongepowered.api.command.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.*;
import net.kyori.adventure.text.*;
import org.jetbrains.annotations.*;

public class SpongeCommandHandler extends ViaCommandHandler implements Command.Raw
{
    public CommandResult process(final CommandCause commandCause, final ArgumentReader.Mutable mutable) {
        this.onCommand(new SpongeCommandSender(commandCause), (mutable.input().length() > 0) ? mutable.input().split(" ") : new String[0]);
        return CommandResult.success();
    }
    
    public List complete(final CommandCause commandCause, final ArgumentReader.Mutable mutable) {
        return (List)this.onTabComplete(new SpongeCommandSender(commandCause), mutable.input().split(" ", -1)).stream().map(CommandCompletion::of).collect(Collectors.toList());
    }
    
    public boolean canExecute(final CommandCause commandCause) {
        return commandCause.hasPermission("viaversion.admin");
    }
    
    public Optional shortDescription(final CommandCause commandCause) {
        return Optional.of(Component.text("Shows ViaVersion Version and more."));
    }
    
    public Optional extendedDescription(final CommandCause commandCause) {
        return this.shortDescription(commandCause);
    }
    
    public Optional help(@NotNull final CommandCause commandCause) {
        return Optional.empty();
    }
    
    public Component usage(final CommandCause commandCause) {
        return (Component)Component.text("Usage /viaversion");
    }
}
