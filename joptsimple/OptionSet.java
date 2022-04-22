package joptsimple;

import joptsimple.internal.*;
import java.util.*;

public class OptionSet
{
    private final List detectedSpecs;
    private final Map detectedOptions;
    private final Map optionsToArguments;
    private final Map recognizedSpecs;
    private final Map defaultValues;
    
    OptionSet(final Map recognizedSpecs) {
        this.detectedSpecs = new ArrayList();
        this.detectedOptions = new HashMap();
        this.optionsToArguments = new IdentityHashMap();
        this.defaultValues = defaultValues(recognizedSpecs);
        this.recognizedSpecs = recognizedSpecs;
    }
    
    public boolean hasOptions() {
        return !this.detectedOptions.isEmpty();
    }
    
    public boolean has(final String s) {
        return this.detectedOptions.containsKey(s);
    }
    
    public boolean has(final OptionSpec optionSpec) {
        return this.optionsToArguments.containsKey(optionSpec);
    }
    
    public boolean hasArgument(final String s) {
        final AbstractOptionSpec abstractOptionSpec = this.detectedOptions.get(s);
        return abstractOptionSpec != null && this.hasArgument(abstractOptionSpec);
    }
    
    public boolean hasArgument(final OptionSpec optionSpec) {
        Objects.ensureNotNull(optionSpec);
        final List list = this.optionsToArguments.get(optionSpec);
        return list != null && !list.isEmpty();
    }
    
    public Object valueOf(final String s) {
        Objects.ensureNotNull(s);
        final AbstractOptionSpec abstractOptionSpec = this.detectedOptions.get(s);
        if (abstractOptionSpec == null) {
            final List defaultValues = this.defaultValuesFor(s);
            return defaultValues.isEmpty() ? null : defaultValues.get(0);
        }
        return this.valueOf(abstractOptionSpec);
    }
    
    public Object valueOf(final OptionSpec optionSpec) {
        Objects.ensureNotNull(optionSpec);
        final List values = this.valuesOf(optionSpec);
        switch (values.size()) {
            case 0: {
                return null;
            }
            case 1: {
                return values.get(0);
            }
            default: {
                throw new MultipleArgumentsForOptionException(optionSpec.options());
            }
        }
    }
    
    public List valuesOf(final String s) {
        Objects.ensureNotNull(s);
        final AbstractOptionSpec abstractOptionSpec = this.detectedOptions.get(s);
        return (abstractOptionSpec == null) ? this.defaultValuesFor(s) : this.valuesOf(abstractOptionSpec);
    }
    
    public List valuesOf(final OptionSpec optionSpec) {
        Objects.ensureNotNull(optionSpec);
        final List<String> list = this.optionsToArguments.get(optionSpec);
        if (list == null || list.isEmpty()) {
            return this.defaultValueFor(optionSpec);
        }
        final AbstractOptionSpec abstractOptionSpec = (AbstractOptionSpec)optionSpec;
        final ArrayList<Object> list2 = new ArrayList<Object>();
        final Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            list2.add(abstractOptionSpec.convert(iterator.next()));
        }
        return Collections.unmodifiableList((List<?>)list2);
    }
    
    public List specs() {
        final List detectedSpecs = this.detectedSpecs;
        detectedSpecs.remove(this.detectedOptions.get("[arguments]"));
        return Collections.unmodifiableList((List<?>)detectedSpecs);
    }
    
    public Map asMap() {
        final HashMap<AbstractOptionSpec, List> hashMap = new HashMap<AbstractOptionSpec, List>();
        for (final AbstractOptionSpec abstractOptionSpec : this.recognizedSpecs.values()) {
            if (!abstractOptionSpec.representsNonOptions()) {
                hashMap.put(abstractOptionSpec, this.valuesOf(abstractOptionSpec));
            }
        }
        return Collections.unmodifiableMap((Map<?, ?>)hashMap);
    }
    
    public List nonOptionArguments() {
        return Collections.unmodifiableList((List<?>)this.valuesOf(this.detectedOptions.get("[arguments]")));
    }
    
    void add(final AbstractOptionSpec abstractOptionSpec) {
        this.addWithArgument(abstractOptionSpec, null);
    }
    
    void addWithArgument(final AbstractOptionSpec abstractOptionSpec, final String s) {
        this.detectedSpecs.add(abstractOptionSpec);
        final Iterator<String> iterator = abstractOptionSpec.options().iterator();
        while (iterator.hasNext()) {
            this.detectedOptions.put(iterator.next(), abstractOptionSpec);
        }
        List<?> list = this.optionsToArguments.get(abstractOptionSpec);
        if (list == null) {
            list = new ArrayList<Object>();
            this.optionsToArguments.put(abstractOptionSpec, list);
        }
        if (s != null) {
            list.add(s);
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }
        final OptionSet set = (OptionSet)o;
        final HashMap hashMap = new HashMap(this.optionsToArguments);
        final HashMap hashMap2 = new HashMap(set.optionsToArguments);
        return this.detectedOptions.equals(set.detectedOptions) && hashMap.equals(hashMap2);
    }
    
    @Override
    public int hashCode() {
        return this.detectedOptions.hashCode() ^ new HashMap(this.optionsToArguments).hashCode();
    }
    
    private List defaultValuesFor(final String s) {
        if (this.defaultValues.containsKey(s)) {
            return this.defaultValues.get(s);
        }
        return Collections.emptyList();
    }
    
    private List defaultValueFor(final OptionSpec optionSpec) {
        return this.defaultValuesFor(optionSpec.options().iterator().next());
    }
    
    private static Map defaultValues(final Map map) {
        final HashMap<Object, List> hashMap = new HashMap<Object, List>();
        for (final Map.Entry<Object, V> entry : map.entrySet()) {
            hashMap.put(entry.getKey(), ((AbstractOptionSpec)entry.getValue()).defaultValues());
        }
        return hashMap;
    }
}
