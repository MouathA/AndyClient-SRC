package net.minecraft.command;

public class CommandException extends Exception
{
    private final Object[] errorObjects;
    private static final String __OBFID;
    
    public CommandException(final String s, final Object... errorObjects) {
        super(s);
        this.errorObjects = errorObjects;
    }
    
    public Object[] getErrorOjbects() {
        return this.errorObjects;
    }
    
    static {
        __OBFID = "CL_00001187";
    }
}
