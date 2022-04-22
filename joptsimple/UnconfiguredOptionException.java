package joptsimple;

import java.util.*;

class UnconfiguredOptionException extends OptionException
{
    private static final long serialVersionUID = -1L;
    
    UnconfiguredOptionException(final String s) {
        this(Collections.singletonList(s));
    }
    
    UnconfiguredOptionException(final Collection collection) {
        super(collection);
    }
    
    @Override
    public String getMessage() {
        return "Option " + this.multipleOptionMessage() + " has not been configured on this parser";
    }
}
