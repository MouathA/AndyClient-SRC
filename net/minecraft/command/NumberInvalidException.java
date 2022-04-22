package net.minecraft.command;

public class NumberInvalidException extends CommandException
{
    private static final String __OBFID;
    
    public NumberInvalidException() {
        this("commands.generic.num.invalid", new Object[0]);
    }
    
    public NumberInvalidException(final String s, final Object... array) {
        super(s, array);
    }
    
    static {
        __OBFID = "CL_00001188";
    }
}
