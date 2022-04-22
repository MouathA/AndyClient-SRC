package joptsimple;

import java.util.*;

class AlternativeLongOptionSpec extends ArgumentAcceptingOptionSpec
{
    AlternativeLongOptionSpec() {
        super(Collections.singletonList("W"), true, "Alternative form of long options");
        this.describedAs("opt=value");
    }
    
    @Override
    protected void detectOptionArgument(final OptionParser optionParser, final ArgumentList list, final OptionSet set) {
        if (!list.hasMore()) {
            throw new OptionMissingRequiredArgumentException(this.options());
        }
        list.treatNextAsLongOption();
    }
}
