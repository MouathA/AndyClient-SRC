package org.apache.http.message;

import java.io.*;
import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;

@NotThreadSafe
public class BufferedHeader implements FormattedHeader, Cloneable, Serializable
{
    private static final long serialVersionUID = -2768352615787625448L;
    private final String name;
    private final CharArrayBuffer buffer;
    private final int valuePos;
    
    public BufferedHeader(final CharArrayBuffer buffer) throws ParseException {
        Args.notNull(buffer, "Char array buffer");
        final int index = buffer.indexOf(58);
        if (index == -1) {
            throw new ParseException("Invalid header: " + buffer.toString());
        }
        final String substringTrimmed = buffer.substringTrimmed(0, index);
        if (substringTrimmed.length() == 0) {
            throw new ParseException("Invalid header: " + buffer.toString());
        }
        this.buffer = buffer;
        this.name = substringTrimmed;
        this.valuePos = index + 1;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getValue() {
        return this.buffer.substringTrimmed(this.valuePos, this.buffer.length());
    }
    
    public HeaderElement[] getElements() throws ParseException {
        final ParserCursor parserCursor = new ParserCursor(0, this.buffer.length());
        parserCursor.updatePos(this.valuePos);
        return BasicHeaderValueParser.INSTANCE.parseElements(this.buffer, parserCursor);
    }
    
    public int getValuePos() {
        return this.valuePos;
    }
    
    public CharArrayBuffer getBuffer() {
        return this.buffer;
    }
    
    @Override
    public String toString() {
        return this.buffer.toString();
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
