package joptsimple;

import java.util.*;

class RequiredArgumentOptionSpec extends ArgumentAcceptingOptionSpec
{
    RequiredArgumentOptionSpec(final String s) {
        super(s, true);
    }
    
    RequiredArgumentOptionSpec(final Collection collection, final String s) {
        super(collection, true, s);
    }
    
    @Override
    protected void detectOptionArgument(final OptionParser optionParser, final ArgumentList list, final OptionSet set) {
        if (!list.hasMore()) {
            throw new OptionMissingRequiredArgumentException(this.options());
        }
        this.addArguments(set, list.next());
    }
}
