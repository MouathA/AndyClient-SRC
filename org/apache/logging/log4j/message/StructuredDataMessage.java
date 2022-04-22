package org.apache.logging.log4j.message;

import java.util.*;
import org.apache.logging.log4j.util.*;

public class StructuredDataMessage extends MapMessage
{
    private static final long serialVersionUID = 1703221292892071920L;
    private static final int MAX_LENGTH = 32;
    private static final int HASHVAL = 31;
    private StructuredDataId id;
    private String message;
    private String type;
    
    public StructuredDataMessage(final String s, final String message, final String type) {
        this.id = new StructuredDataId(s, null, null);
        this.message = message;
        this.type = type;
    }
    
    public StructuredDataMessage(final String s, final String message, final String type, final Map map) {
        super(map);
        this.id = new StructuredDataId(s, null, null);
        this.message = message;
        this.type = type;
    }
    
    public StructuredDataMessage(final StructuredDataId id, final String message, final String type) {
        this.id = id;
        this.message = message;
        this.type = type;
    }
    
    public StructuredDataMessage(final StructuredDataId id, final String message, final String type, final Map map) {
        super(map);
        this.id = id;
        this.message = message;
        this.type = type;
    }
    
    private StructuredDataMessage(final StructuredDataMessage structuredDataMessage, final Map map) {
        super(map);
        this.id = structuredDataMessage.id;
        this.message = structuredDataMessage.message;
        this.type = structuredDataMessage.type;
    }
    
    protected StructuredDataMessage() {
    }
    
    @Override
    public String[] getFormats() {
        final String[] array = new String[Format.values().length];
        final Format[] values = Format.values();
        while (0 < values.length) {
            final Format format = values[0];
            final String[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = format.name();
            int n3 = 0;
            ++n3;
        }
        return array;
    }
    
    public StructuredDataId getId() {
        return this.id;
    }
    
    protected void setId(final String s) {
        this.id = new StructuredDataId(s, null, null);
    }
    
    protected void setId(final StructuredDataId id) {
        this.id = id;
    }
    
    public String getType() {
        return this.type;
    }
    
    protected void setType(final String type) {
        if (type.length() > 32) {
            throw new IllegalArgumentException("structured data type exceeds maximum length of 32 characters: " + type);
        }
        this.type = type;
    }
    
    @Override
    public String getFormat() {
        return this.message;
    }
    
    protected void setMessageFormat(final String message) {
        this.message = message;
    }
    
    @Override
    protected void validate(final String s, final String s2) {
        this.validateKey(s);
    }
    
    private void validateKey(final String s) {
        if (s.length() > 32) {
            throw new IllegalArgumentException("Structured data keys are limited to 32 characters. key: " + s);
        }
        final char[] charArray = s.toCharArray();
        while (0 < charArray.length) {
            final char c = charArray[0];
            if (c < '!' || c > '~' || c == '=' || c == ']' || c == '\"') {
                throw new IllegalArgumentException("Structured data keys must contain printable US ASCII charactersand may not contain a space, =, ], or \"");
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public String asString() {
        return this.asString(Format.FULL, null);
    }
    
    @Override
    public String asString(final String s) {
        return this.asString((Format)EnglishEnums.valueOf(Format.class, s), null);
    }
    
    public final String asString(final Format format, final StructuredDataId structuredDataId) {
        final StringBuilder sb = new StringBuilder();
        final boolean equals = Format.FULL.equals(format);
        if (equals) {
            if (this.getType() == null) {
                return sb.toString();
            }
            sb.append(this.getType()).append(" ");
        }
        final StructuredDataId id = this.getId();
        StructuredDataId id2;
        if (id != null) {
            id2 = id.makeId(structuredDataId);
        }
        else {
            id2 = structuredDataId;
        }
        if (id2 == null || id2.getName() == null) {
            return sb.toString();
        }
        sb.append("[");
        sb.append(id2);
        sb.append(" ");
        this.appendMap(sb);
        sb.append("]");
        if (equals) {
            final String format2 = this.getFormat();
            if (format2 != null) {
                sb.append(" ").append(format2);
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getFormattedMessage() {
        return this.asString(Format.FULL, null);
    }
    
    @Override
    public String getFormattedMessage(final String[] array) {
        if (array != null && array.length > 0) {
            while (0 < array.length) {
                final String s = array[0];
                if (Format.XML.name().equalsIgnoreCase(s)) {
                    return this.asXML();
                }
                if (Format.FULL.name().equalsIgnoreCase(s)) {
                    return this.asString(Format.FULL, null);
                }
                int n = 0;
                ++n;
            }
            return this.asString(null, null);
        }
        return this.asString(Format.FULL, null);
    }
    
    private String asXML() {
        final StringBuilder sb = new StringBuilder();
        final StructuredDataId id = this.getId();
        if (id == null || id.getName() == null || this.type == null) {
            return sb.toString();
        }
        sb.append("<StructuredData>\n");
        sb.append("<type>").append(this.type).append("</type>\n");
        sb.append("<id>").append(id).append("</id>\n");
        super.asXML(sb);
        sb.append("</StructuredData>\n");
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return this.asString(null, null);
    }
    
    @Override
    public MapMessage newInstance(final Map map) {
        return new StructuredDataMessage(this, map);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final StructuredDataMessage structuredDataMessage = (StructuredDataMessage)o;
        if (!super.equals(o)) {
            return false;
        }
        Label_0072: {
            if (this.type != null) {
                if (this.type.equals(structuredDataMessage.type)) {
                    break Label_0072;
                }
            }
            else if (structuredDataMessage.type == null) {
                break Label_0072;
            }
            return false;
        }
        Label_0105: {
            if (this.id != null) {
                if (this.id.equals(structuredDataMessage.id)) {
                    break Label_0105;
                }
            }
            else if (structuredDataMessage.id == null) {
                break Label_0105;
            }
            return false;
        }
        if (this.message != null) {
            if (this.message.equals(structuredDataMessage.message)) {
                return true;
            }
        }
        else if (structuredDataMessage.message == null) {
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * super.hashCode() + ((this.type != null) ? this.type.hashCode() : 0)) + ((this.id != null) ? this.id.hashCode() : 0)) + ((this.message != null) ? this.message.hashCode() : 0);
    }
    
    public enum Format
    {
        XML("XML", 0), 
        FULL("FULL", 1);
        
        private static final Format[] $VALUES;
        
        private Format(final String s, final int n) {
        }
        
        static {
            $VALUES = new Format[] { Format.XML, Format.FULL };
        }
    }
}
