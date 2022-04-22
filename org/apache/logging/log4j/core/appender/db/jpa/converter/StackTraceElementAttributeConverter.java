package org.apache.logging.log4j.core.appender.db.jpa.converter;

import javax.persistence.*;
import org.apache.logging.log4j.core.helpers.*;

@Converter(autoApply = false)
public class StackTraceElementAttributeConverter implements AttributeConverter
{
    private static final int UNKNOWN_SOURCE = -1;
    private static final int NATIVE_METHOD = -2;
    
    public String convertToDatabaseColumn(final StackTraceElement stackTraceElement) {
        if (stackTraceElement == null) {
            return null;
        }
        return stackTraceElement.toString();
    }
    
    public StackTraceElement convertToEntityAttribute(final String s) {
        if (Strings.isEmpty(s)) {
            return null;
        }
        return convertString(s);
    }
    
    static StackTraceElement convertString(final String s) {
        final int index = s.indexOf("(");
        final String substring = s.substring(0, index);
        final String substring2 = substring.substring(0, substring.lastIndexOf("."));
        final String substring3 = substring.substring(substring.lastIndexOf(".") + 1);
        final String substring4 = s.substring(index + 1, s.indexOf(")"));
        String s2 = null;
        if (!"Native Method".equals(substring4)) {
            if (!"Unknown Source".equals(substring4)) {
                final int index2 = substring4.indexOf(":");
                if (index2 > -1) {
                    s2 = substring4.substring(0, index2);
                    Integer.parseInt(substring4.substring(index2 + 1));
                }
                else {
                    s2 = substring4.substring(0);
                }
            }
        }
        return new StackTraceElement(substring2, substring3, s2, -2);
    }
    
    public Object convertToEntityAttribute(final Object o) {
        return this.convertToEntityAttribute((String)o);
    }
    
    public Object convertToDatabaseColumn(final Object o) {
        return this.convertToDatabaseColumn((StackTraceElement)o);
    }
}
