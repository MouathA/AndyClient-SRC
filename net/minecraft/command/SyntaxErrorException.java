package net.minecraft.command;

public class SyntaxErrorException extends CommandException
{
    private static final String __OBFID;
    
    public SyntaxErrorException() {
        this("commands.generic.snytax", new Object[0]);
    }
    
    public SyntaxErrorException(final String s, final Object... array) {
        super(s, array);
    }
    
    static {
        __OBFID = "CL_00001189";
    }
}
