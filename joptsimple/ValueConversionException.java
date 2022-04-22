package joptsimple;

public class ValueConversionException extends RuntimeException
{
    private static final long serialVersionUID = -1L;
    
    public ValueConversionException(final String s) {
        this(s, null);
    }
    
    public ValueConversionException(final String s, final Throwable t) {
        super(s, t);
    }
}
