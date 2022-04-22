package org.apache.logging.log4j.message;

public class FormattedMessageFactory extends AbstractMessageFactory
{
    @Override
    public Message newMessage(final String s, final Object... array) {
        return new FormattedMessage(s, array);
    }
}
