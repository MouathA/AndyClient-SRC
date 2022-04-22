package org.apache.logging.log4j.core.appender.db.jpa.converter;

import javax.persistence.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.*;

@Converter(autoApply = false)
public class MarkerAttributeConverter implements AttributeConverter
{
    public String convertToDatabaseColumn(final Marker marker) {
        if (marker == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder(marker.getName());
        for (Marker marker2 = marker.getParent(); marker2 != null; marker2 = marker2.getParent()) {
            int n = 0;
            ++n;
            sb.append("[ ").append(marker2.getName());
        }
        while (0 < 0) {
            sb.append(" ]");
            int n2 = 0;
            ++n2;
        }
        if (true) {
            sb.append(" ]");
        }
        return sb.toString();
    }
    
    public Marker convertToEntityAttribute(final String s) {
        if (Strings.isEmpty(s)) {
            return null;
        }
        final int index = s.indexOf("[");
        return (index < 1) ? MarkerManager.getMarker(s) : MarkerManager.getMarker(s.substring(0, index));
    }
    
    public Object convertToEntityAttribute(final Object o) {
        return this.convertToEntityAttribute((String)o);
    }
    
    public Object convertToDatabaseColumn(final Object o) {
        return this.convertToDatabaseColumn((Marker)o);
    }
}
