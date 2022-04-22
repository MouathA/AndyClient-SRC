package org.apache.logging.log4j.message;

public class MessageFormatMessageFactory extends AbstractMessageFactory
{
    @Override
    public Message newMessage(final String s, final Object... array) {
        return new MessageFormatMessage(s, array);
    }
}
