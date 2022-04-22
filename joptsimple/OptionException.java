package joptsimple;

import java.util.*;

public abstract class OptionException extends RuntimeException
{
    private static final long serialVersionUID = -1L;
    private final List options;
    
    protected OptionException(final Collection collection) {
        (this.options = new ArrayList()).addAll(collection);
    }
    
    protected OptionException(final Collection collection, final Throwable t) {
        super(t);
        (this.options = new ArrayList()).addAll(collection);
    }
    
    public Collection options() {
        return Collections.unmodifiableCollection((Collection<?>)this.options);
    }
    
    protected final String singleOptionMessage() {
        return this.singleOptionMessage(this.options.get(0));
    }
    
    protected final String singleOptionMessage(final String s) {
        return "'" + s + "'";
    }
    
    protected final String multipleOptionMessage() {
        final StringBuilder sb = new StringBuilder("[");
        final Iterator<String> iterator = (Iterator<String>)this.options.iterator();
        while (iterator.hasNext()) {
            sb.append(this.singleOptionMessage(iterator.next()));
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }
    
    static OptionException unrecognizedOption(final String s) {
        return new UnrecognizedOptionException(s);
    }
}
