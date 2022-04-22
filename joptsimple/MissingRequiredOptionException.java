package joptsimple;

import java.util.*;

class MissingRequiredOptionException extends OptionException
{
    private static final long serialVersionUID = -1L;
    
    protected MissingRequiredOptionException(final Collection collection) {
        super(collection);
    }
    
    @Override
    public String getMessage() {
        return "Missing required option(s) " + this.multipleOptionMessage();
    }
}
