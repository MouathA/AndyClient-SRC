package net.minecraft.command;

public class WrongUsageException extends SyntaxErrorException
{
    private static final String __OBFID;
    
    public WrongUsageException(final String s, final Object... array) {
        super(s, array);
    }
    
    static {
        __OBFID = "CL_00001192";
    }
}
