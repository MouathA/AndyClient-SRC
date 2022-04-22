package org.apache.logging.log4j.core.appender.db.jpa.converter;

import javax.persistence.*;
import java.util.*;

@Converter(autoApply = false)
public class ContextMapAttributeConverter implements AttributeConverter
{
    public String convertToDatabaseColumn(final Map map) {
        if (map == null) {
            return null;
        }
        return map.toString();
    }
    
    public Map convertToEntityAttribute(final String s) {
        throw new UnsupportedOperationException("Log events can only be persisted, not extracted.");
    }
    
    public Object convertToEntityAttribute(final Object o) {
        return this.convertToEntityAttribute((String)o);
    }
    
    public Object convertToDatabaseColumn(final Object o) {
        return this.convertToDatabaseColumn((Map)o);
    }
}
