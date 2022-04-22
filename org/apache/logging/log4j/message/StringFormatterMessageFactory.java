package org.apache.logging.log4j.message;

public final class StringFormatterMessageFactory extends AbstractMessageFactory
{
    public static final StringFormatterMessageFactory INSTANCE;
    
    @Override
    public Message newMessage(final String s, final Object... array) {
        return new StringFormattedMessage(s, array);
    }
    
    static {
        INSTANCE = new StringFormatterMessageFactory();
    }
}
