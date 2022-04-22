package org.apache.http.message;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.util.*;
import org.apache.http.*;
import org.apache.http.protocol.*;

@Immutable
public class BasicHeaderValueParser implements HeaderValueParser
{
    @Deprecated
    public static final BasicHeaderValueParser DEFAULT;
    public static final BasicHeaderValueParser INSTANCE;
    private static final char PARAM_DELIMITER = ';';
    private static final char ELEM_DELIMITER = ',';
    private static final char[] ALL_DELIMITERS;
    
    public static HeaderElement[] parseElements(final String s, final HeaderValueParser headerValueParser) throws ParseException {
        Args.notNull(s, "Value");
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(s.length());
        charArrayBuffer.append(s);
        return ((headerValueParser != null) ? headerValueParser : BasicHeaderValueParser.INSTANCE).parseElements(charArrayBuffer, new ParserCursor(0, s.length()));
    }
    
    public HeaderElement[] parseElements(final CharArrayBuffer charArrayBuffer, final ParserCursor parserCursor) {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        final ArrayList<HeaderElement> list = new ArrayList<HeaderElement>();
        while (!parserCursor.atEnd()) {
            final HeaderElement headerElement = this.parseHeaderElement(charArrayBuffer, parserCursor);
            if (headerElement.getName().length() != 0 || headerElement.getValue() != null) {
                list.add(headerElement);
            }
        }
        return list.toArray(new HeaderElement[list.size()]);
    }
    
    public static HeaderElement parseHeaderElement(final String s, final HeaderValueParser headerValueParser) throws ParseException {
        Args.notNull(s, "Value");
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(s.length());
        charArrayBuffer.append(s);
        return ((headerValueParser != null) ? headerValueParser : BasicHeaderValueParser.INSTANCE).parseHeaderElement(charArrayBuffer, new ParserCursor(0, s.length()));
    }
    
    public HeaderElement parseHeaderElement(final CharArrayBuffer charArrayBuffer, final ParserCursor parserCursor) {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        final NameValuePair nameValuePair = this.parseNameValuePair(charArrayBuffer, parserCursor);
        NameValuePair[] parameters = null;
        if (!parserCursor.atEnd() && charArrayBuffer.charAt(parserCursor.getPos() - 1) != ',') {
            parameters = this.parseParameters(charArrayBuffer, parserCursor);
        }
        return this.createHeaderElement(nameValuePair.getName(), nameValuePair.getValue(), parameters);
    }
    
    protected HeaderElement createHeaderElement(final String s, final String s2, final NameValuePair[] array) {
        return new BasicHeaderElement(s, s2, array);
    }
    
    public static NameValuePair[] parseParameters(final String s, final HeaderValueParser headerValueParser) throws ParseException {
        Args.notNull(s, "Value");
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(s.length());
        charArrayBuffer.append(s);
        return ((headerValueParser != null) ? headerValueParser : BasicHeaderValueParser.INSTANCE).parseParameters(charArrayBuffer, new ParserCursor(0, s.length()));
    }
    
    public NameValuePair[] parseParameters(final CharArrayBuffer charArrayBuffer, final ParserCursor parserCursor) {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        int pos;
        for (pos = parserCursor.getPos(); pos < parserCursor.getUpperBound() && HTTP.isWhitespace(charArrayBuffer.charAt(pos)); ++pos) {}
        parserCursor.updatePos(pos);
        if (parserCursor.atEnd()) {
            return new NameValuePair[0];
        }
        final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
        while (!parserCursor.atEnd()) {
            list.add(this.parseNameValuePair(charArrayBuffer, parserCursor));
            if (charArrayBuffer.charAt(parserCursor.getPos() - 1) == ',') {
                break;
            }
        }
        return list.toArray(new NameValuePair[list.size()]);
    }
    
    public static NameValuePair parseNameValuePair(final String s, final HeaderValueParser headerValueParser) throws ParseException {
        Args.notNull(s, "Value");
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(s.length());
        charArrayBuffer.append(s);
        return ((headerValueParser != null) ? headerValueParser : BasicHeaderValueParser.INSTANCE).parseNameValuePair(charArrayBuffer, new ParserCursor(0, s.length()));
    }
    
    public NameValuePair parseNameValuePair(final CharArrayBuffer charArrayBuffer, final ParserCursor parserCursor) {
        return this.parseNameValuePair(charArrayBuffer, parserCursor, BasicHeaderValueParser.ALL_DELIMITERS);
    }
    
    private static boolean isOneOf(final char c, final char[] array) {
        if (array != null) {
            while (0 < array.length) {
                if (c == array[0]) {
                    return true;
                }
                int n = 0;
                ++n;
            }
        }
        return false;
    }
    
    public NameValuePair parseNameValuePair(final CharArrayBuffer charArrayBuffer, final ParserCursor parserCursor, final char[] array) {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        int i = parserCursor.getPos();
        final int pos = parserCursor.getPos();
        int upperBound;
        for (upperBound = parserCursor.getUpperBound(); i < upperBound; ++i) {
            final char char1 = charArrayBuffer.charAt(i);
            if (char1 == '=') {
                break;
            }
            if (isOneOf(char1, array)) {
                break;
            }
        }
        String s;
        if (i == upperBound) {
            s = charArrayBuffer.substringTrimmed(pos, upperBound);
        }
        else {
            s = charArrayBuffer.substringTrimmed(pos, i);
            ++i;
        }
        if (true) {
            parserCursor.updatePos(i);
            return this.createNameValuePair(s, null);
        }
        int n = i;
        while (i < upperBound) {
            final char char2 = charArrayBuffer.charAt(i);
            if (char2 == '\"' && !false) {
                final boolean b = !false;
            }
            if (!false && !false && isOneOf(char2, array)) {
                break;
            }
            if (!false) {
                final boolean b2 = false && char2 == '\\';
            }
            ++i;
        }
        int n2;
        for (n2 = i; n < n2 && HTTP.isWhitespace(charArrayBuffer.charAt(n)); ++n) {}
        while (n2 > n && HTTP.isWhitespace(charArrayBuffer.charAt(n2 - 1))) {
            --n2;
        }
        if (n2 - n >= 2 && charArrayBuffer.charAt(n) == '\"' && charArrayBuffer.charAt(n2 - 1) == '\"') {
            ++n;
            --n2;
        }
        final String substring = charArrayBuffer.substring(n, n2);
        if (true) {
            ++i;
        }
        parserCursor.updatePos(i);
        return this.createNameValuePair(s, substring);
    }
    
    protected NameValuePair createNameValuePair(final String s, final String s2) {
        return new BasicNameValuePair(s, s2);
    }
    
    static {
        DEFAULT = new BasicHeaderValueParser();
        INSTANCE = new BasicHeaderValueParser();
        ALL_DELIMITERS = new char[] { ';', ',' };
    }
}
