package joptsimple;

import java.util.*;

class OptionalArgumentOptionSpec extends ArgumentAcceptingOptionSpec
{
    OptionalArgumentOptionSpec(final String s) {
        super(s, false);
    }
    
    OptionalArgumentOptionSpec(final Collection collection, final String s) {
        super(collection, false, s);
    }
    
    @Override
    protected void detectOptionArgument(final OptionParser optionParser, final ArgumentList list, final OptionSet set) {
        if (list.hasMore()) {
            final String peek = list.peek();
            if (!optionParser.looksLikeAnOption(peek)) {
                this.handleOptionArgument(optionParser, set, list);
            }
            else if (this.isArgumentOfNumberType() && this.canConvertArgument(peek)) {
                this.addArguments(set, list.next());
            }
            else {
                set.add(this);
            }
        }
        else {
            set.add(this);
        }
    }
    
    private void handleOptionArgument(final OptionParser optionParser, final OptionSet set, final ArgumentList list) {
        if (optionParser.posixlyCorrect()) {
            set.add(this);
            optionParser.noMoreOptions();
        }
        else {
            this.addArguments(set, list.next());
        }
    }
}
