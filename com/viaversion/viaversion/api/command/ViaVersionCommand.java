package com.viaversion.viaversion.api.command;

import java.util.*;

public interface ViaVersionCommand
{
    void registerSubCommand(final ViaSubCommand p0) throws Exception;
    
    boolean hasSubCommand(final String p0);
    
    ViaSubCommand getSubCommand(final String p0);
    
    boolean onCommand(final ViaCommandSender p0, final String[] p1);
    
    List onTabComplete(final ViaCommandSender p0, final String[] p1);
    
    void showHelp(final ViaCommandSender p0);
}
