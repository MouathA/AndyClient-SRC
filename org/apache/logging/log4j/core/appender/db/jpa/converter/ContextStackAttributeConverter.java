package org.apache.logging.log4j.core.appender.db.jpa.converter;

import javax.persistence.*;
import org.apache.logging.log4j.*;
import java.util.*;

@Converter(autoApply = false)
public class ContextStackAttributeConverter implements AttributeConverter
{
    public String convertToDatabaseColumn(final ThreadContext.ContextStack contextStack) {
        if (contextStack == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        for (final String s : contextStack.asList()) {
            if (sb.length() > 0) {
                sb.append('\n');
            }
            sb.append(s);
        }
        return sb.toString();
    }
    
    public ThreadContext.ContextStack convertToEntityAttribute(final String s) {
        throw new UnsupportedOperationException("Log events can only be persisted, not extracted.");
    }
    
    public Object convertToEntityAttribute(final Object o) {
        return this.convertToEntityAttribute((String)o);
    }
    
    public Object convertToDatabaseColumn(final Object o) {
        return this.convertToDatabaseColumn((ThreadContext.ContextStack)o);
    }
}
