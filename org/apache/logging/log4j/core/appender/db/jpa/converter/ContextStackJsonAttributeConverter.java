package org.apache.logging.log4j.core.appender.db.jpa.converter;

import javax.persistence.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.helpers.*;
import com.fasterxml.jackson.core.type.*;
import org.apache.logging.log4j.spi.*;
import java.util.*;

@Converter(autoApply = false)
public class ContextStackJsonAttributeConverter implements AttributeConverter
{
    public String convertToDatabaseColumn(final ThreadContext.ContextStack contextStack) {
        if (contextStack == null) {
            return null;
        }
        return ContextMapJsonAttributeConverter.OBJECT_MAPPER.writeValueAsString((Object)contextStack.asList());
    }
    
    public ThreadContext.ContextStack convertToEntityAttribute(final String s) {
        if (Strings.isEmpty(s)) {
            return null;
        }
        final List list = (List)ContextMapJsonAttributeConverter.OBJECT_MAPPER.readValue(s, (TypeReference)new TypeReference() {
            final ContextStackJsonAttributeConverter this$0;
        });
        final DefaultThreadContextStack defaultThreadContextStack = new DefaultThreadContextStack(true);
        defaultThreadContextStack.addAll(list);
        return defaultThreadContextStack;
    }
    
    public Object convertToEntityAttribute(final Object o) {
        return this.convertToEntityAttribute((String)o);
    }
    
    public Object convertToDatabaseColumn(final Object o) {
        return this.convertToDatabaseColumn((ThreadContext.ContextStack)o);
    }
}
