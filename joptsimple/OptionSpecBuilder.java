package joptsimple;

import java.util.*;

public class OptionSpecBuilder extends NoArgumentOptionSpec
{
    private final OptionParser parser;
    
    OptionSpecBuilder(final OptionParser parser, final Collection collection, final String s) {
        super(collection, s);
        this.parser = parser;
        this.attachToParser();
    }
    
    private void attachToParser() {
        this.parser.recognize(this);
    }
    
    public ArgumentAcceptingOptionSpec withRequiredArg() {
        final RequiredArgumentOptionSpec requiredArgumentOptionSpec = new RequiredArgumentOptionSpec(this.options(), this.description());
        this.parser.recognize(requiredArgumentOptionSpec);
        return requiredArgumentOptionSpec;
    }
    
    public ArgumentAcceptingOptionSpec withOptionalArg() {
        final OptionalArgumentOptionSpec optionalArgumentOptionSpec = new OptionalArgumentOptionSpec(this.options(), this.description());
        this.parser.recognize(optionalArgumentOptionSpec);
        return optionalArgumentOptionSpec;
    }
    
    public OptionSpecBuilder requiredIf(final String s, final String... array) {
        final Iterator<String> iterator = this.validatedDependents(s, array).iterator();
        while (iterator.hasNext()) {
            this.parser.requiredIf(this.options(), iterator.next());
        }
        return this;
    }
    
    public OptionSpecBuilder requiredIf(final OptionSpec optionSpec, final OptionSpec... array) {
        this.parser.requiredIf(this.options(), optionSpec);
        while (0 < array.length) {
            this.parser.requiredIf(this.options(), array[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public OptionSpecBuilder requiredUnless(final String s, final String... array) {
        final Iterator<String> iterator = this.validatedDependents(s, array).iterator();
        while (iterator.hasNext()) {
            this.parser.requiredUnless(this.options(), iterator.next());
        }
        return this;
    }
    
    public OptionSpecBuilder requiredUnless(final OptionSpec optionSpec, final OptionSpec... array) {
        this.parser.requiredUnless(this.options(), optionSpec);
        while (0 < array.length) {
            this.parser.requiredUnless(this.options(), array[0]);
            int n = 0;
            ++n;
        }
        return this;
    }
    
    private List validatedDependents(final String s, final String... array) {
        final ArrayList<Object> list = new ArrayList<Object>();
        list.add(s);
        Collections.addAll(list, array);
        for (final String s2 : list) {
            if (!this.parser.isRecognized(s2)) {
                throw new UnconfiguredOptionException(s2);
            }
        }
        return list;
    }
    
    @Override
    public List defaultValues() {
        return super.defaultValues();
    }
    
    @Override
    public String argumentTypeIndicator() {
        return super.argumentTypeIndicator();
    }
    
    @Override
    public String argumentDescription() {
        return super.argumentDescription();
    }
    
    @Override
    public boolean isRequired() {
        return super.isRequired();
    }
    
    @Override
    public boolean requiresArgument() {
        return super.requiresArgument();
    }
    
    @Override
    public boolean acceptsArguments() {
        return super.acceptsArguments();
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
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
