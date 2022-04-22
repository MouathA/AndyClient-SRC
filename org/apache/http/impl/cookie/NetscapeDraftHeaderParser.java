package org.apache.http.impl.cookie;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.util.*;
import org.apache.http.*;
import org.apache.http.message.*;
import org.apache.http.protocol.*;

@Immutable
public class NetscapeDraftHeaderParser
{
    public static final NetscapeDraftHeaderParser DEFAULT;
    
    public HeaderElement parseHeader(final CharArrayBuffer charArrayBuffer, final ParserCursor parserCursor) throws ParseException {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        final NameValuePair nameValuePair = this.parseNameValuePair(charArrayBuffer, parserCursor);
        final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
        while (!parserCursor.atEnd()) {
            list.add(this.parseNameValuePair(charArrayBuffer, parserCursor));
        }
        return new BasicHeaderElement(nameValuePair.getName(), nameValuePair.getValue(), list.toArray(new NameValuePair[list.size()]));
    }
    
    private NameValuePair parseNameValuePair(final CharArrayBuffer charArrayBuffer, final ParserCursor parserCursor) {
        int i = parserCursor.getPos();
        final int pos = parserCursor.getPos();
        int upperBound;
        for (upperBound = parserCursor.getUpperBound(); i < upperBound; ++i) {
            final char char1 = charArrayBuffer.charAt(i);
            if (char1 == '=') {
                break;
            }
            if (char1 == ';') {
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
            return new BasicNameValuePair(s, null);
        }
        int n = i;
        while (i < upperBound && charArrayBuffer.charAt(i) != ';') {
            ++i;
        }
        int n2;
        for (n2 = i; n < n2 && HTTP.isWhitespace(charArrayBuffer.charAt(n)); ++n) {}
        while (n2 > n && HTTP.isWhitespace(charArrayBuffer.charAt(n2 - 1))) {
            --n2;
        }
        final String substring = charArrayBuffer.substring(n, n2);
        if (true) {
            ++i;
        }
        parserCursor.updatePos(i);
        return new BasicNameValuePair(s, substring);
    }
    
    static {
        DEFAULT = new NetscapeDraftHeaderParser();
    }
}
