package joptsimple;

import java.util.*;

class UnrecognizedOptionException extends OptionException
{
    private static final long serialVersionUID = -1L;
    
    UnrecognizedOptionException(final String s) {
        super(Collections.singletonList(s));
    }
    
    @Override
    public String getMessage() {
        return this.singleOptionMessage() + " is not a recognized option";
    }
}
