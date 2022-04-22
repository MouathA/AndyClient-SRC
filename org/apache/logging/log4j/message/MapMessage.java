package org.apache.logging.log4j.message;

import org.apache.logging.log4j.util.*;
import java.util.*;

public class MapMessage implements MultiformatMessage
{
    private static final long serialVersionUID = -5031471831131487120L;
    private final SortedMap data;
    
    public MapMessage() {
        this.data = new TreeMap();
    }
    
    public MapMessage(final Map map) {
        this.data = ((map instanceof SortedMap) ? ((SortedMap)map) : new TreeMap(map));
    }
    
    @Override
    public String[] getFormats() {
        final String[] array = new String[MapFormat.values().length];
        final MapFormat[] values = MapFormat.values();
        while (0 < values.length) {
            final MapFormat mapFormat = values[0];
            final String[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = mapFormat.name();
            int n3 = 0;
            ++n3;
        }
        return array;
    }
    
    @Override
    public Object[] getParameters() {
        return this.data.values().toArray();
    }
    
    @Override
    public String getFormat() {
        return "";
    }
    
    public Map getData() {
        return Collections.unmodifiableMap((Map<?, ?>)this.data);
    }
    
    public void clear() {
        this.data.clear();
    }
    
    public void put(final String s, final String s2) {
        if (s2 == null) {
            throw new IllegalArgumentException("No value provided for key " + s);
        }
        this.validate(s, s2);
        this.data.put(s, s2);
    }
    
    protected void validate(final String s, final String s2) {
    }
    
    public void putAll(final Map map) {
        this.data.putAll(map);
    }
    
    public String get(final String s) {
        return (String)this.data.get(s);
    }
    
    public String remove(final String s) {
        return (String)this.data.remove(s);
    }
    
    public String asString() {
        return this.asString((MapFormat)null);
    }
    
    public String asString(final String s) {
        return this.asString((MapFormat)EnglishEnums.valueOf(MapFormat.class, s));
    }
    
    private String asString(final MapFormat mapFormat) {
        final StringBuilder sb = new StringBuilder();
        if (mapFormat == null) {
            this.appendMap(sb);
        }
        else {
            switch (mapFormat) {
                case XML: {
                    this.asXML(sb);
                    break;
                }
                case JSON: {
                    this.asJSON(sb);
                    break;
                }
                case JAVA: {
                    this.asJava(sb);
                    break;
                }
                default: {
                    this.appendMap(sb);
                    break;
                }
            }
        }
        return sb.toString();
    }
    
    public void asXML(final StringBuilder sb) {
        sb.append("<Map>\n");
        for (final Map.Entry<String, V> entry : this.data.entrySet()) {
            sb.append("  <Entry key=\"").append(entry.getKey()).append("\">").append((String)entry.getValue()).append("</Entry>\n");
        }
        sb.append("</Map>");
    }
    
    @Override
    public String getFormattedMessage() {
        return this.asString();
    }
    
    @Override
    public String getFormattedMessage(final String[] array) {
        if (array == null || array.length == 0) {
            return this.asString();
        }
        while (0 < array.length) {
            final String s = array[0];
            final MapFormat[] values = MapFormat.values();
            while (0 < values.length) {
                final MapFormat mapFormat = values[0];
                if (mapFormat.name().equalsIgnoreCase(s)) {
                    return this.asString(mapFormat);
                }
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return this.asString();
    }
    
    protected void appendMap(final StringBuilder sb) {
        for (final Map.Entry<String, V> entry : this.data.entrySet()) {
            if (!false) {
                sb.append(" ");
            }
            sb.append(entry.getKey()).append("=\"").append((String)entry.getValue()).append("\"");
        }
    }
    
    protected void asJSON(final StringBuilder sb) {
        sb.append("{");
        for (final Map.Entry<String, V> entry : this.data.entrySet()) {
            if (!false) {
                sb.append(", ");
            }
            sb.append("\"").append(entry.getKey()).append("\":");
            sb.append("\"").append((String)entry.getValue()).append("\"");
        }
        sb.append("}");
    }
    
    protected void asJava(final StringBuilder sb) {
        sb.append("{");
        for (final Map.Entry<String, V> entry : this.data.entrySet()) {
            if (!false) {
                sb.append(", ");
            }
            sb.append(entry.getKey()).append("=\"").append((String)entry.getValue()).append("\"");
        }
        sb.append("}");
    }
    
    public MapMessage newInstance(final Map map) {
        return new MapMessage(map);
    }
    
    @Override
    public String toString() {
        return this.asString();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && this.data.equals(((MapMessage)o).data));
    }
    
    @Override
    public int hashCode() {
        return this.data.hashCode();
    }
    
    @Override
    public Throwable getThrowable() {
        return null;
    }
    
    public enum MapFormat
    {
        XML("XML", 0), 
        JSON("JSON", 1), 
        JAVA("JAVA", 2);
        
        private static final MapFormat[] $VALUES;
        
        private MapFormat(final String s, final int n) {
        }
        
        static {
            $VALUES = new MapFormat[] { MapFormat.XML, MapFormat.JSON, MapFormat.JAVA };
        }
    }
}
