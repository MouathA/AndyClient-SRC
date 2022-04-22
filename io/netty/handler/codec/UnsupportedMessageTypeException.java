package io.netty.handler.codec;

public class UnsupportedMessageTypeException extends CodecException
{
    private static final long serialVersionUID = 2799598826487038726L;
    
    public UnsupportedMessageTypeException(final Object o, final Class... array) {
        super(message((o == null) ? "null" : o.getClass().getName(), array));
    }
    
    public UnsupportedMessageTypeException() {
    }
    
    public UnsupportedMessageTypeException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public UnsupportedMessageTypeException(final String s) {
        super(s);
    }
    
    public UnsupportedMessageTypeException(final Throwable t) {
        super(t);
    }
    
    private static String message(final String s, final Class... array) {
        final StringBuilder sb = new StringBuilder(s);
        if (array != null && array.length > 0) {
            sb.append(" (expected: ").append(array[0].getName());
            while (1 < array.length) {
                final Class clazz = array[1];
                if (clazz == null) {
                    break;
                }
                sb.append(", ").append(clazz.getName());
                int n = 0;
                ++n;
            }
            sb.append(')');
        }
        return sb.toString();
    }
}
