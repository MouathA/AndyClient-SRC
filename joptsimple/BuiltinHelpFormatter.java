package joptsimple;

import joptsimple.internal.*;
import java.util.*;

public class BuiltinHelpFormatter implements HelpFormatter
{
    private final Rows nonOptionRows;
    private final Rows optionRows;
    
    BuiltinHelpFormatter() {
        this(80, 2);
    }
    
    public BuiltinHelpFormatter(final int n, final int n2) {
        this.nonOptionRows = new Rows(n * 2, 0);
        this.optionRows = new Rows(n, n2);
    }
    
    public String format(final Map map) {
        final TreeSet set = new TreeSet(new Comparator() {
            final BuiltinHelpFormatter this$0;
            
            public int compare(final OptionDescriptor optionDescriptor, final OptionDescriptor optionDescriptor2) {
                return optionDescriptor.options().iterator().next().compareTo((String)optionDescriptor2.options().iterator().next());
            }
            
            public int compare(final Object o, final Object o2) {
                return this.compare((OptionDescriptor)o, (OptionDescriptor)o2);
            }
        });
        set.addAll(map.values());
        this.addRows(set);
        return this.formattedHelpOutput();
    }
    
    private String formattedHelpOutput() {
        final StringBuilder sb = new StringBuilder();
        final String render = this.nonOptionRows.render();
        if (!Strings.isNullOrEmpty(render)) {
            sb.append(render).append(Strings.LINE_SEPARATOR);
        }
        sb.append(this.optionRows.render());
        return sb.toString();
    }
    
    private void addRows(final Collection collection) {
        this.addNonOptionsDescription(collection);
        if (collection.isEmpty()) {
            this.optionRows.add("No options specified", "");
        }
        else {
            this.addHeaders(collection);
            this.addOptions(collection);
        }
        this.fitRowsToWidth();
    }
    
    private void addNonOptionsDescription(final Collection collection) {
        final OptionDescriptor andRemoveNonOptionsSpec = this.findAndRemoveNonOptionsSpec(collection);
        if (this.shouldShowNonOptionArgumentDisplay(andRemoveNonOptionsSpec)) {
            this.nonOptionRows.add("Non-option arguments:", "");
            this.nonOptionRows.add(this.createNonOptionArgumentsDisplay(andRemoveNonOptionsSpec), "");
        }
    }
    
    private boolean shouldShowNonOptionArgumentDisplay(final OptionDescriptor optionDescriptor) {
        return !Strings.isNullOrEmpty(optionDescriptor.description()) || !Strings.isNullOrEmpty(optionDescriptor.argumentTypeIndicator()) || !Strings.isNullOrEmpty(optionDescriptor.argumentDescription());
    }
    
    private String createNonOptionArgumentsDisplay(final OptionDescriptor optionDescriptor) {
        final StringBuilder sb = new StringBuilder();
        this.maybeAppendOptionInfo(sb, optionDescriptor);
        this.maybeAppendNonOptionsDescription(sb, optionDescriptor);
        return sb.toString();
    }
    
    private void maybeAppendNonOptionsDescription(final StringBuilder sb, final OptionDescriptor optionDescriptor) {
        sb.append((sb.length() > 0 && !Strings.isNullOrEmpty(optionDescriptor.description())) ? " -- " : "").append(optionDescriptor.description());
    }
    
    private OptionDescriptor findAndRemoveNonOptionsSpec(final Collection collection) {
        final Iterator<OptionDescriptor> iterator = collection.iterator();
        while (iterator.hasNext()) {
            final OptionDescriptor optionDescriptor = iterator.next();
            if (optionDescriptor.representsNonOptions()) {
                iterator.remove();
                return optionDescriptor;
            }
        }
        throw new AssertionError((Object)"no non-options argument spec");
    }
    
    private void addHeaders(final Collection collection) {
        if (this.hasRequiredOption(collection)) {
            this.optionRows.add("Option (* = required)", "Description");
            this.optionRows.add("---------------------", "-----------");
        }
        else {
            this.optionRows.add("Option", "Description");
            this.optionRows.add("------", "-----------");
        }
    }
    
    private boolean hasRequiredOption(final Collection collection) {
        final Iterator<OptionDescriptor> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isRequired()) {
                return true;
            }
        }
        return false;
    }
    
    private void addOptions(final Collection collection) {
        for (final OptionDescriptor optionDescriptor : collection) {
            if (!optionDescriptor.representsNonOptions()) {
                this.optionRows.add(this.createOptionDisplay(optionDescriptor), this.createDescriptionDisplay(optionDescriptor));
            }
        }
    }
    
    private String createOptionDisplay(final OptionDescriptor optionDescriptor) {
        final StringBuilder sb = new StringBuilder(optionDescriptor.isRequired() ? "* " : "");
        final Iterator<String> iterator = (Iterator<String>)optionDescriptor.options().iterator();
        while (iterator.hasNext()) {
            final String s = iterator.next();
            sb.append((s.length() > 1) ? "--" : ParserRules.HYPHEN);
            sb.append(s);
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        this.maybeAppendOptionInfo(sb, optionDescriptor);
        return sb.toString();
    }
    
    private void maybeAppendOptionInfo(final StringBuilder sb, final OptionDescriptor optionDescriptor) {
        final String typeIndicator = this.extractTypeIndicator(optionDescriptor);
        final String argumentDescription = optionDescriptor.argumentDescription();
        if (typeIndicator != null || !Strings.isNullOrEmpty(argumentDescription)) {
            this.appendOptionHelp(sb, typeIndicator, argumentDescription, optionDescriptor.requiresArgument());
        }
    }
    
    private String extractTypeIndicator(final OptionDescriptor optionDescriptor) {
        final String argumentTypeIndicator = optionDescriptor.argumentTypeIndicator();
        if (!Strings.isNullOrEmpty(argumentTypeIndicator) && !String.class.getName().equals(argumentTypeIndicator)) {
            return Classes.shortNameOf(argumentTypeIndicator);
        }
        return null;
    }
    
    private void appendOptionHelp(final StringBuilder sb, final String s, final String s2, final boolean b) {
        if (b) {
            this.appendTypeIndicator(sb, s, s2, '<', '>');
        }
        else {
            this.appendTypeIndicator(sb, s, s2, '[', ']');
        }
    }
    
    private void appendTypeIndicator(final StringBuilder sb, final String s, final String s2, final char c, final char c2) {
        sb.append(' ').append(c);
        if (s != null) {
            sb.append(s);
        }
        if (!Strings.isNullOrEmpty(s2)) {
            if (s != null) {
                sb.append(": ");
            }
            sb.append(s2);
        }
        sb.append(c2);
    }
    
    private String createDescriptionDisplay(final OptionDescriptor optionDescriptor) {
        final List defaultValues = optionDescriptor.defaultValues();
        if (defaultValues.isEmpty()) {
            return optionDescriptor.description();
        }
        return (optionDescriptor.description() + ' ' + Strings.surround("default: " + this.createDefaultValuesDisplay(defaultValues), '(', ')')).trim();
    }
    
    private String createDefaultValuesDisplay(final List list) {
        return (list.size() == 1) ? list.get(0).toString() : list.toString();
    }
    
    private void fitRowsToWidth() {
        this.nonOptionRows.fitToWidth();
        this.optionRows.fitToWidth();
    }
}
