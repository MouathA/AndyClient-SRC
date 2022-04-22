package joptsimple;

import java.util.*;

class NoArgumentOptionSpec extends AbstractOptionSpec
{
    NoArgumentOptionSpec(final String s) {
        this(Collections.singletonList(s), "");
    }
    
    NoArgumentOptionSpec(final Collection collection, final String s) {
        super(collection, s);
    }
    
    @Override
    void handleOption(final OptionParser optionParser, final ArgumentList list, final OptionSet set, final String s) {
        set.add(this);
    }
    
    public boolean acceptsArguments() {
        return false;
    }
    
    public boolean requiresArgument() {
        return false;
    }
    
    public boolean isRequired() {
        return false;
    }
    
    public String argumentDescription() {
        return "";
    }
    
    public String argumentTypeIndicator() {
        return "";
    }
    
    @Override
    protected Void convert(final String s) {
        return null;
    }
    
    public List defaultValues() {
        return Collections.emptyList();
    }
    
    @Override
    protected Object convert(final String s) {
        return this.convert(s);
    }
}
