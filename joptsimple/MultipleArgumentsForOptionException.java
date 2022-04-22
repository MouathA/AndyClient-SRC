package joptsimple;

import java.util.*;

class MultipleArgumentsForOptionException extends OptionException
{
    private static final long serialVersionUID = -1L;
    
    MultipleArgumentsForOptionException(final Collection collection) {
        super(collection);
    }
    
    @Override
    public String getMessage() {
        return "Found multiple arguments for option " + this.multipleOptionMessage() + ", but you asked for only one";
    }
}
