package joptsimple;

import java.util.*;

class OptionArgumentConversionException extends OptionException
{
    private static final long serialVersionUID = -1L;
    private final String argument;
    
    OptionArgumentConversionException(final Collection collection, final String argument, final Throwable t) {
        super(collection, t);
        this.argument = argument;
    }
    
    @Override
    public String getMessage() {
        return "Cannot parse argument '" + this.argument + "' of option " + this.multipleOptionMessage();
    }
}
