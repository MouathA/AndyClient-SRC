package org.apache.logging.log4j.core.appender.db.jpa.converter;

import javax.persistence.*;
import com.fasterxml.jackson.databind.*;
import java.util.*;
import org.apache.logging.log4j.core.helpers.*;
import com.fasterxml.jackson.core.type.*;

@Converter(autoApply = false)
public class ContextMapJsonAttributeConverter implements AttributeConverter
{
    static final ObjectMapper OBJECT_MAPPER;
    
    public String convertToDatabaseColumn(final Map map) {
        if (map == null) {
            return null;
        }
        return ContextMapJsonAttributeConverter.OBJECT_MAPPER.writeValueAsString((Object)map);
    }
    
    public Map convertToEntityAttribute(final String s) {
        if (Strings.isEmpty(s)) {
            return null;
        }
        return (Map)ContextMapJsonAttributeConverter.OBJECT_MAPPER.readValue(s, (TypeReference)new TypeReference() {
            final ContextMapJsonAttributeConverter this$0;
        });
    }
    
    public Object convertToEntityAttribute(final Object o) {
        return this.convertToEntityAttribute((String)o);
    }
    
    public Object convertToDatabaseColumn(final Object o) {
        return this.convertToDatabaseColumn((Map)o);
    }
    
    static {
        OBJECT_MAPPER = new ObjectMapper();
    }
}
