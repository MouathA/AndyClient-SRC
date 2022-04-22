package org.apache.logging.log4j.message;

public final class ParameterizedMessageFactory extends AbstractMessageFactory
{
    public static final ParameterizedMessageFactory INSTANCE;
    
    @Override
    public Message newMessage(final String s, final Object... array) {
        return new ParameterizedMessage(s, array);
    }
    
    static {
        INSTANCE = new ParameterizedMessageFactory();
    }
}
