package joptsimple;

import joptsimple.internal.*;
import java.util.*;

abstract class AbstractOptionSpec implements OptionSpec, OptionDescriptor
{
    private final List options;
    private final String description;
    private boolean forHelp;
    
    protected AbstractOptionSpec(final String s) {
        this(Collections.singletonList(s), "");
    }
    
    protected AbstractOptionSpec(final Collection collection, final String description) {
        this.options = new ArrayList();
        this.arrangeOptions(collection);
        this.description = description;
    }
    
    public final Collection options() {
        return Collections.unmodifiableList((List<?>)this.options);
    }
    
    public final List values(final OptionSet set) {
        return set.valuesOf(this);
    }
    
    public final Object value(final OptionSet set) {
        return set.valueOf(this);
    }
    
    public String description() {
        return this.description;
    }
    
    public final AbstractOptionSpec forHelp() {
        this.forHelp = true;
        return this;
    }
    
    public final boolean isForHelp() {
        return this.forHelp;
    }
    
    public boolean representsNonOptions() {
        return false;
    }
    
    protected abstract Object convert(final String p0);
    
    protected Object convertWith(final ValueConverter valueConverter, final String s) {
        return Reflection.convertWith(valueConverter, s);
    }
    
    protected String argumentTypeIndicatorFrom(final ValueConverter valueConverter) {
        if (valueConverter == null) {
            return null;
        }
        final String valuePattern = valueConverter.valuePattern();
        return (valuePattern == null) ? valueConverter.valueType().getName() : valuePattern;
    }
    
    abstract void handleOption(final OptionParser p0, final ArgumentList p1, final OptionSet p2, final String p3);
    
    private void arrangeOptions(final Collection collection) {
        if (collection.size() == 1) {
            this.options.addAll(collection);
            return;
        }
        final ArrayList<String> list = (ArrayList<String>)new ArrayList<Comparable>();
        final ArrayList<String> list2 = (ArrayList<String>)new ArrayList<Comparable>();
        for (final String s : collection) {
            if (s.length() == 1) {
                list.add(s);
            }
            else {
                list2.add(s);
            }
        }
        Collections.sort((List<Comparable>)list);
        Collections.sort((List<Comparable>)list2);
        this.options.addAll(list);
        this.options.addAll(list2);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof AbstractOptionSpec && this.options.equals(((AbstractOptionSpec)o).options);
    }
    
    @Override
    public int hashCode() {
        return this.options.hashCode();
    }
    
    @Override
    public String toString() {
        return this.options.toString();
    }
}
