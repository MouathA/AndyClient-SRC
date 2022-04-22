package joptsimple;

import joptsimple.internal.*;
import java.util.*;

public class NonOptionArgumentSpec extends AbstractOptionSpec
{
    static final String NAME = "[arguments]";
    private ValueConverter converter;
    private String argumentDescription;
    
    NonOptionArgumentSpec() {
        this("");
    }
    
    NonOptionArgumentSpec(final String s) {
        super(Arrays.asList("[arguments]"), s);
        this.argumentDescription = "";
    }
    
    public NonOptionArgumentSpec ofType(final Class clazz) {
        this.converter = Reflection.findConverter(clazz);
        return this;
    }
    
    public final NonOptionArgumentSpec withValuesConvertedBy(final ValueConverter converter) {
        if (converter == null) {
            throw new NullPointerException("illegal null converter");
        }
        this.converter = converter;
        return this;
    }
    
    public NonOptionArgumentSpec describedAs(final String argumentDescription) {
        this.argumentDescription = argumentDescription;
        return this;
    }
    
    @Override
    protected final Object convert(final String s) {
        return this.convertWith(this.converter, s);
    }
    
    @Override
    void handleOption(final OptionParser optionParser, final ArgumentList list, final OptionSet set, final String s) {
        set.addWithArgument(this, s);
    }
    
    public List defaultValues() {
        return Collections.emptyList();
    }
    
    public boolean isRequired() {
        return false;
    }
    
    public boolean acceptsArguments() {
        return false;
    }
    
    public boolean requiresArgument() {
        return false;
    }
    
    public String argumentDescription() {
        return this.argumentDescription;
    }
    
    public String argumentTypeIndicator() {
        return this.argumentTypeIndicatorFrom(this.converter);
    }
    
    @Override
    public boolean representsNonOptions() {
        return true;
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
    public String description() {
        return super.description();
    }
}
