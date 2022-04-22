package net.minecraft.command;

public class EntityNotFoundException extends CommandException
{
    private static final String __OBFID;
    
    public EntityNotFoundException() {
        this("commands.generic.entity.notFound", new Object[0]);
    }
    
    public EntityNotFoundException(final String s, final Object... array) {
        super(s, array);
    }
    
    static {
        __OBFID = "CL_00002335";
    }
}
