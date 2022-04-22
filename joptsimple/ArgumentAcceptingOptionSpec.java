package joptsimple;

import joptsimple.internal.*;
import java.util.*;

public abstract class ArgumentAcceptingOptionSpec extends AbstractOptionSpec
{
    private static final char NIL_VALUE_SEPARATOR = '\0';
    private boolean optionRequired;
    private final boolean argumentRequired;
    private ValueConverter converter;
    private String argumentDescription;
    private String valueSeparator;
    private final List defaultValues;
    
    ArgumentAcceptingOptionSpec(final String s, final boolean argumentRequired) {
        super(s);
        this.argumentDescription = "";
        this.valueSeparator = String.valueOf('\0');
        this.defaultValues = new ArrayList();
        this.argumentRequired = argumentRequired;
    }
    
    ArgumentAcceptingOptionSpec(final Collection collection, final boolean argumentRequired, final String s) {
        super(collection, s);
        this.argumentDescription = "";
        this.valueSeparator = String.valueOf('\0');
        this.defaultValues = new ArrayList();
        this.argumentRequired = argumentRequired;
    }
    
    public final ArgumentAcceptingOptionSpec ofType(final Class clazz) {
        return this.withValuesConvertedBy(Reflection.findConverter(clazz));
    }
    
    public final ArgumentAcceptingOptionSpec withValuesConvertedBy(final ValueConverter converter) {
        if (converter == null) {
            throw new NullPointerException("illegal null converter");
        }
        this.converter = converter;
        return this;
    }
    
    public final ArgumentAcceptingOptionSpec describedAs(final String argumentDescription) {
        this.argumentDescription = argumentDescription;
        return this;
    }
    
    public final ArgumentAcceptingOptionSpec withValuesSeparatedBy(final char c) {
        if (c == '\0') {
            throw new IllegalArgumentException("cannot use U+0000 as separator");
        }
        this.valueSeparator = String.valueOf(c);
        return this;
    }
    
    public final ArgumentAcceptingOptionSpec withValuesSeparatedBy(final String valueSeparator) {
        if (valueSeparator.indexOf(0) != -1) {
            throw new IllegalArgumentException("cannot use U+0000 in separator");
        }
        this.valueSeparator = valueSeparator;
        return this;
    }
    
    public ArgumentAcceptingOptionSpec defaultsTo(final Object o, final Object... array) {
        this.addDefaultValue(o);
        this.defaultsTo(array);
        return this;
    }
    
    public ArgumentAcceptingOptionSpec defaultsTo(final Object[] array) {
        while (0 < array.length) {
            this.addDefaultValue(array[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public ArgumentAcceptingOptionSpec required() {
        this.optionRequired = true;
        return this;
    }
    
    public boolean isRequired() {
        return this.optionRequired;
    }
    
    private void addDefaultValue(final Object o) {
        Objects.ensureNotNull(o);
        this.defaultValues.add(o);
    }
    
    @Override
    final void handleOption(final OptionParser optionParser, final ArgumentList list, final OptionSet set, final String s) {
        if (Strings.isNullOrEmpty(s)) {
            this.detectOptionArgument(optionParser, list, set);
        }
        else {
            this.addArguments(set, s);
        }
    }
    
    protected void addArguments(final OptionSet set, final String s) {
        final StringTokenizer stringTokenizer = new StringTokenizer(s, this.valueSeparator);
        if (!stringTokenizer.hasMoreTokens()) {
            set.addWithArgument(this, s);
        }
        else {
            while (stringTokenizer.hasMoreTokens()) {
                set.addWithArgument(this, stringTokenizer.nextToken());
            }
        }
    }
    
    protected abstract void detectOptionArgument(final OptionParser p0, final ArgumentList p1, final OptionSet p2);
    
    @Override
    protected final Object convert(final String s) {
        return this.convertWith(this.converter, s);
    }
    
    protected boolean canConvertArgument(final String s) {
        final StringTokenizer stringTokenizer = new StringTokenizer(s, this.valueSeparator);
        while (stringTokenizer.hasMoreTokens()) {
            this.convert(stringTokenizer.nextToken());
        }
        return true;
    }
    
    protected boolean isArgumentOfNumberType() {
        return this.converter != null && Number.class.isAssignableFrom(this.converter.valueType());
    }
    
    public boolean acceptsArguments() {
        return true;
    }
    
    public boolean requiresArgument() {
        return this.argumentRequired;
    }
    
    public String argumentDescription() {
        return this.argumentDescription;
    }
    
    public String argumentTypeIndicator() {
        return this.argumentTypeIndicatorFrom(this.converter);
    }
    
    public List defaultValues() {
        return Collections.unmodifiableList((List<?>)this.defaultValues);
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && this.requiresArgument() == ((ArgumentAcceptingOptionSpec)o).requiresArgument();
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ (this.argumentRequired ? 0 : 1);
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    @Override
    public boolean representsNonOptions() {
        return super.representsNonOptions();
    }
    
    @Override
    public String description() {
        return super.description();
    }
}
