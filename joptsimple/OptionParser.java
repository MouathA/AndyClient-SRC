package joptsimple;

import joptsimple.internal.*;
import java.io.*;
import joptsimple.util.*;
import java.util.*;

public class OptionParser implements OptionDeclarer
{
    private final AbbreviationMap recognizedOptions;
    private final Map requiredIf;
    private final Map requiredUnless;
    private OptionParserState state;
    private boolean posixlyCorrect;
    private boolean allowsUnrecognizedOptions;
    private HelpFormatter helpFormatter;
    
    public OptionParser() {
        this.helpFormatter = new BuiltinHelpFormatter();
        this.recognizedOptions = new AbbreviationMap();
        this.requiredIf = new HashMap();
        this.requiredUnless = new HashMap();
        this.state = OptionParserState.moreOptions(false);
        this.recognize(new NonOptionArgumentSpec());
    }
    
    public OptionParser(final String s) {
        this();
        new OptionSpecTokenizer(s).configure(this);
    }
    
    public OptionSpecBuilder accepts(final String s) {
        return this.acceptsAll(Collections.singletonList(s));
    }
    
    public OptionSpecBuilder accepts(final String s, final String s2) {
        return this.acceptsAll(Collections.singletonList(s), s2);
    }
    
    public OptionSpecBuilder acceptsAll(final Collection collection) {
        return this.acceptsAll(collection, "");
    }
    
    public OptionSpecBuilder acceptsAll(final Collection collection, final String s) {
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("need at least one option");
        }
        ParserRules.ensureLegalOptions(collection);
        return new OptionSpecBuilder(this, collection, s);
    }
    
    public NonOptionArgumentSpec nonOptions() {
        final NonOptionArgumentSpec nonOptionArgumentSpec = new NonOptionArgumentSpec();
        this.recognize(nonOptionArgumentSpec);
        return nonOptionArgumentSpec;
    }
    
    public NonOptionArgumentSpec nonOptions(final String s) {
        final NonOptionArgumentSpec nonOptionArgumentSpec = new NonOptionArgumentSpec(s);
        this.recognize(nonOptionArgumentSpec);
        return nonOptionArgumentSpec;
    }
    
    public void posixlyCorrect(final boolean posixlyCorrect) {
        this.posixlyCorrect = posixlyCorrect;
        this.state = OptionParserState.moreOptions(posixlyCorrect);
    }
    
    boolean posixlyCorrect() {
        return this.posixlyCorrect;
    }
    
    public void allowsUnrecognizedOptions() {
        this.allowsUnrecognizedOptions = true;
    }
    
    boolean doesAllowsUnrecognizedOptions() {
        return this.allowsUnrecognizedOptions;
    }
    
    public void recognizeAlternativeLongOptions(final boolean b) {
        if (b) {
            this.recognize(new AlternativeLongOptionSpec());
        }
        else {
            this.recognizedOptions.remove(String.valueOf("W"));
        }
    }
    
    void recognize(final AbstractOptionSpec abstractOptionSpec) {
        this.recognizedOptions.putAll(abstractOptionSpec.options(), abstractOptionSpec);
    }
    
    public void printHelpOn(final OutputStream outputStream) throws IOException {
        this.printHelpOn(new OutputStreamWriter(outputStream));
    }
    
    public void printHelpOn(final Writer writer) throws IOException {
        writer.write(this.helpFormatter.format(this.recognizedOptions.toJavaUtilMap()));
        writer.flush();
    }
    
    public void formatHelpWith(final HelpFormatter helpFormatter) {
        if (helpFormatter == null) {
            throw new NullPointerException();
        }
        this.helpFormatter = helpFormatter;
    }
    
    public Map recognizedOptions() {
        return new HashMap(this.recognizedOptions.toJavaUtilMap());
    }
    
    public OptionSet parse(final String... array) {
        final ArgumentList list = new ArgumentList(array);
        final OptionSet set = new OptionSet(this.recognizedOptions.toJavaUtilMap());
        set.add((AbstractOptionSpec)this.recognizedOptions.get("[arguments]"));
        while (list.hasMore()) {
            this.state.handleArgument(this, list, set);
        }
        this.reset();
        this.ensureRequiredOptions(set);
        return set;
    }
    
    private void ensureRequiredOptions(final OptionSet set) {
        final Collection missingRequiredOptions = this.missingRequiredOptions(set);
        final boolean helpOptionPresent = this.isHelpOptionPresent(set);
        if (!missingRequiredOptions.isEmpty() && !helpOptionPresent) {
            throw new MissingRequiredOptionException(missingRequiredOptions);
        }
    }
    
    private Collection missingRequiredOptions(final OptionSet set) {
        final HashSet set2 = new HashSet();
        for (final AbstractOptionSpec abstractOptionSpec : this.recognizedOptions.toJavaUtilMap().values()) {
            if (abstractOptionSpec.isRequired() && !set.has(abstractOptionSpec)) {
                set2.addAll(abstractOptionSpec.options());
            }
        }
        for (final Map.Entry<Collection, V> entry : this.requiredIf.entrySet()) {
            final AbstractOptionSpec spec = this.specFor(entry.getKey().iterator().next());
            if (this.optionsHasAnyOf(set, (Collection)entry.getValue()) && !set.has(spec)) {
                set2.addAll(spec.options());
            }
        }
        for (final Map.Entry<Collection, V> entry2 : this.requiredUnless.entrySet()) {
            final AbstractOptionSpec spec2 = this.specFor(entry2.getKey().iterator().next());
            if (!this.optionsHasAnyOf(set, (Collection)entry2.getValue()) && !set.has(spec2)) {
                set2.addAll(spec2.options());
            }
        }
        return set2;
    }
    
    private boolean optionsHasAnyOf(final OptionSet set, final Collection collection) {
        final Iterator<OptionSpec> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (set.has(iterator.next())) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isHelpOptionPresent(final OptionSet set) {
        for (final AbstractOptionSpec abstractOptionSpec : this.recognizedOptions.toJavaUtilMap().values()) {
            if (abstractOptionSpec.isForHelp() && set.has(abstractOptionSpec)) {
                break;
            }
        }
        return true;
    }
    
    void handleLongOptionToken(final String s, final ArgumentList list, final OptionSet set) {
        final KeyValuePair longOptionWithArgument = parseLongOptionWithArgument(s);
        if (!this.isRecognized(longOptionWithArgument.key)) {
            throw OptionException.unrecognizedOption(longOptionWithArgument.key);
        }
        this.specFor(longOptionWithArgument.key).handleOption(this, list, set, longOptionWithArgument.value);
    }
    
    void handleShortOptionToken(final String s, final ArgumentList list, final OptionSet set) {
        final KeyValuePair shortOptionWithArgument = parseShortOptionWithArgument(s);
        if (this.isRecognized(shortOptionWithArgument.key)) {
            this.specFor(shortOptionWithArgument.key).handleOption(this, list, set, shortOptionWithArgument.value);
        }
        else {
            this.handleShortOptionCluster(s, list, set);
        }
    }
    
    private void handleShortOptionCluster(final String s, final ArgumentList list, final OptionSet set) {
        final char[] shortOptions = extractShortOptionsFrom(s);
        this.validateOptionCharacters(shortOptions);
        while (0 < shortOptions.length) {
            final AbstractOptionSpec spec = this.specFor(shortOptions[0]);
            if (spec.acceptsArguments() && shortOptions.length > 1) {
                spec.handleOption(this, list, set, String.valueOf(shortOptions, 1, shortOptions.length - 1 - 0));
                break;
            }
            spec.handleOption(this, list, set, null);
            int n = 0;
            ++n;
        }
    }
    
    void handleNonOptionArgument(final String s, final ArgumentList list, final OptionSet set) {
        this.specFor("[arguments]").handleOption(this, list, set, s);
    }
    
    void noMoreOptions() {
        this.state = OptionParserState.noMoreOptions();
    }
    
    boolean looksLikeAnOption(final String s) {
        return ParserRules.isShortOptionToken(s) || ParserRules.isLongOptionToken(s);
    }
    
    boolean isRecognized(final String s) {
        return this.recognizedOptions.contains(s);
    }
    
    void requiredIf(final Collection collection, final String s) {
        this.requiredIf(collection, this.specFor(s));
    }
    
    void requiredIf(final Collection collection, final OptionSpec optionSpec) {
        this.putRequiredOption(collection, optionSpec, this.requiredIf);
    }
    
    void requiredUnless(final Collection collection, final String s) {
        this.requiredUnless(collection, this.specFor(s));
    }
    
    void requiredUnless(final Collection collection, final OptionSpec optionSpec) {
        this.putRequiredOption(collection, optionSpec, this.requiredUnless);
    }
    
    private void putRequiredOption(final Collection collection, final OptionSpec optionSpec, final Map map) {
        final Iterator<String> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (this.specFor(iterator.next()) == null) {
                throw new UnconfiguredOptionException(collection);
            }
        }
        Set<?> set = map.get(collection);
        if (set == null) {
            set = new HashSet<Object>();
            map.put(collection, set);
        }
        set.add(optionSpec);
    }
    
    private AbstractOptionSpec specFor(final char c) {
        return this.specFor(String.valueOf(c));
    }
    
    private AbstractOptionSpec specFor(final String s) {
        return (AbstractOptionSpec)this.recognizedOptions.get(s);
    }
    
    private void reset() {
        this.state = OptionParserState.moreOptions(this.posixlyCorrect);
    }
    
    private static char[] extractShortOptionsFrom(final String s) {
        final char[] array = new char[s.length() - 1];
        s.getChars(1, s.length(), array, 0);
        return array;
    }
    
    private void validateOptionCharacters(final char[] array) {
        while (0 < array.length) {
            final String value = String.valueOf(array[0]);
            if (!this.isRecognized(value)) {
                throw OptionException.unrecognizedOption(value);
            }
            if (this.specFor(value).acceptsArguments()) {
                return;
            }
            int n = 0;
            ++n;
        }
    }
    
    private static KeyValuePair parseLongOptionWithArgument(final String s) {
        return KeyValuePair.valueOf(s.substring(2));
    }
    
    private static KeyValuePair parseShortOptionWithArgument(final String s) {
        return KeyValuePair.valueOf(s.substring(1));
    }
}
