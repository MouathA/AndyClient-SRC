package org.apache.logging.log4j.core.appender.db.jpa.converter;

import javax.persistence.*;
import org.apache.logging.log4j.status.*;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.core.helpers.*;

@Converter(autoApply = false)
public class MessageAttributeConverter implements AttributeConverter
{
    private static final StatusLogger LOGGER;
    
    public String convertToDatabaseColumn(final Message message) {
        if (message == null) {
            return null;
        }
        return message.getFormattedMessage();
    }
    
    public Message convertToEntityAttribute(final String s) {
        if (Strings.isEmpty(s)) {
            return null;
        }
        return MessageAttributeConverter.LOGGER.getMessageFactory().newMessage(s);
    }
    
    public Object convertToEntityAttribute(final Object o) {
        return this.convertToEntityAttribute((String)o);
    }
    
    public Object convertToDatabaseColumn(final Object o) {
        return this.convertToDatabaseColumn((Message)o);
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
