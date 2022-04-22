package joptsimple;

import java.util.*;

class IllegalOptionSpecificationException extends OptionException
{
    private static final long serialVersionUID = -1L;
    
    IllegalOptionSpecificationException(final String s) {
        super(Collections.singletonList(s));
    }
    
    @Override
    public String getMessage() {
        return this.singleOptionMessage() + " is not a legal option character";
    }
}
