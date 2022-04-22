package joptsimple;

abstract class OptionParserState
{
    static OptionParserState noMoreOptions() {
        return new OptionParserState() {
            @Override
            protected void handleArgument(final OptionParser optionParser, final ArgumentList list, final OptionSet set) {
                optionParser.handleNonOptionArgument(list.next(), list, set);
            }
        };
    }
    
    static OptionParserState moreOptions(final boolean b) {
        return new OptionParserState(b) {
            final boolean val$posixlyCorrect;
            
            @Override
            protected void handleArgument(final OptionParser optionParser, final ArgumentList list, final OptionSet set) {
                final String next = list.next();
                if (ParserRules.isOptionTerminator(next)) {
                    optionParser.noMoreOptions();
                    return;
                }
                if (ParserRules.isLongOptionToken(next)) {
                    optionParser.handleLongOptionToken(next, list, set);
                    return;
                }
                if (ParserRules.isShortOptionToken(next)) {
                    optionParser.handleShortOptionToken(next, list, set);
                    return;
                }
                if (this.val$posixlyCorrect) {
                    optionParser.noMoreOptions();
                }
                optionParser.handleNonOptionArgument(next, list, set);
            }
        };
    }
    
    protected abstract void handleArgument(final OptionParser p0, final ArgumentList p1, final OptionSet p2);
}
