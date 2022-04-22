package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.message.*;

@Plugin(name = "MessagePatternConverter", category = "Converter")
@ConverterKeys({ "m", "msg", "message" })
public final class MessagePatternConverter extends LogEventPatternConverter
{
    private final String[] formats;
    private final Configuration config;
    
    private MessagePatternConverter(final Configuration config, final String[] formats) {
        super("Message", "message");
        this.formats = formats;
        this.config = config;
    }
    
    public static MessagePatternConverter newInstance(final Configuration configuration, final String[] array) {
        return new MessagePatternConverter(configuration, array);
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        final Message message = logEvent.getMessage();
        if (message != null) {
            String s;
            if (message instanceof MultiformatMessage) {
                s = ((MultiformatMessage)message).getFormattedMessage(this.formats);
            }
            else {
                s = message.getFormattedMessage();
            }
            if (s != null) {
                sb.append((this.config != null && s.contains("${")) ? this.config.getStrSubstitutor().replace(logEvent, s) : s);
            }
            else {
                sb.append("null");
            }
        }
    }
}
