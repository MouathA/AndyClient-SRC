package net.minecraft.command;

public class PlayerNotFoundException extends CommandException
{
    private static final String __OBFID;
    
    public PlayerNotFoundException() {
        this("commands.generic.player.notFound", new Object[0]);
    }
    
    public PlayerNotFoundException(final String s, final Object... array) {
        super(s, array);
    }
    
    static {
        __OBFID = "CL_00001190";
    }
}
