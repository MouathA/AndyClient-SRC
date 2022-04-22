package org.apache.logging.log4j.message;

public abstract class AbstractMessageFactory implements MessageFactory
{
    @Override
    public Message newMessage(final Object o) {
        return new ObjectMessage(o);
    }
    
    @Override
    public Message newMessage(final String s) {
        return new SimpleMessage(s);
    }
    
    @Override
    public abstract Message newMessage(final String p0, final Object... p1);
}
