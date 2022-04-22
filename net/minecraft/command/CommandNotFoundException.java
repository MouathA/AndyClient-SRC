package net.minecraft.command;

public class CommandNotFoundException extends CommandException
{
    private static final String __OBFID;
    
    public CommandNotFoundException() {
        this("commands.generic.notFound", new Object[0]);
    }
    
    public CommandNotFoundException(final String s, final Object... array) {
        super(s, array);
    }
    
    static {
        __OBFID = "CL_00001191";
    }
}
