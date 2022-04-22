package joptsimple;

import java.util.*;

class OptionMissingRequiredArgumentException extends OptionException
{
    private static final long serialVersionUID = -1L;
    
    OptionMissingRequiredArgumentException(final Collection collection) {
        super(collection);
    }
    
    @Override
    public String getMessage() {
        return "Option " + this.multipleOptionMessage() + " requires an argument";
    }
}
