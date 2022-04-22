package org.apache.http.message;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.protocol.*;
import org.apache.http.*;

@Immutable
public class BasicLineParser implements LineParser
{
    @Deprecated
    public static final BasicLineParser DEFAULT;
    public static final BasicLineParser INSTANCE;
    protected final ProtocolVersion protocol;
    
    public BasicLineParser(final ProtocolVersion protocolVersion) {
        this.protocol = ((protocolVersion != null) ? protocolVersion : HttpVersion.HTTP_1_1);
    }
    
    public BasicLineParser() {
        this(null);
    }
    
    public static ProtocolVersion parseProtocolVersion(final String s, final LineParser lineParser) throws ParseException {
        Args.notNull(s, "Value");
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(s.length());
        charArrayBuffer.append(s);
        return ((lineParser != null) ? lineParser : BasicLineParser.INSTANCE).parseProtocolVersion(charArrayBuffer, new ParserCursor(0, s.length()));
    }
    
    public ProtocolVersion parseProtocolVersion(final CharArrayBuffer charArrayBuffer, final ParserCursor parserCursor) throws ParseException {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        final String protocol = this.protocol.getProtocol();
        final int length = protocol.length();
        final int pos = parserCursor.getPos();
        final int upperBound = parserCursor.getUpperBound();
        this.skipWhitespace(charArrayBuffer, parserCursor);
        parserCursor.getPos();
        if (1 + length + 4 > upperBound) {
            throw new ParseException("Not a valid protocol version: " + charArrayBuffer.substring(pos, upperBound));
        }
        while (true && 0 < length) {
            final boolean b = charArrayBuffer.charAt(1) == protocol.charAt(0);
            int index = 0;
            ++index;
        }
        if (true) {
            final boolean b2 = charArrayBuffer.charAt(1 + length) == '/';
        }
        if (!true) {
            throw new ParseException("Not a valid protocol version: " + charArrayBuffer.substring(pos, upperBound));
        }
        int index = charArrayBuffer.indexOf(46, 1, upperBound);
        if (0 == -1) {
            throw new ParseException("Invalid protocol version number: " + charArrayBuffer.substring(pos, upperBound));
        }
        final int int1 = Integer.parseInt(charArrayBuffer.substringTrimmed(1, 0));
        int index2 = charArrayBuffer.indexOf(32, 1, upperBound);
        if (index2 == -1) {
            index2 = upperBound;
        }
        final int int2 = Integer.parseInt(charArrayBuffer.substringTrimmed(1, index2));
        parserCursor.updatePos(index2);
        return this.createProtocolVersion(int1, int2);
    }
    
    protected ProtocolVersion createProtocolVersion(final int n, final int n2) {
        return this.protocol.forVersion(n, n2);
    }
    
    public boolean hasProtocolVersion(final CharArrayBuffer charArrayBuffer, final ParserCursor parserCursor) {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        int pos = parserCursor.getPos();
        final String protocol = this.protocol.getProtocol();
        final int length = protocol.length();
        if (charArrayBuffer.length() < length + 4) {
            return false;
        }
        if (pos < 0) {
            pos = charArrayBuffer.length() - 4 - length;
        }
        else if (pos == 0) {
            while (pos < charArrayBuffer.length() && HTTP.isWhitespace(charArrayBuffer.charAt(pos))) {
                ++pos;
            }
        }
        if (pos + length + 4 > charArrayBuffer.length()) {
            return false;
        }
        while (true && 0 < length) {
            final boolean b = charArrayBuffer.charAt(pos + 0) == protocol.charAt(0);
            int n = 0;
            ++n;
        }
        if (true) {
            final boolean b2 = charArrayBuffer.charAt(pos + length) == '/';
        }
        return true;
    }
    
    public static RequestLine parseRequestLine(final String s, final LineParser lineParser) throws ParseException {
        Args.notNull(s, "Value");
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(s.length());
        charArrayBuffer.append(s);
        return ((lineParser != null) ? lineParser : BasicLineParser.INSTANCE).parseRequestLine(charArrayBuffer, new ParserCursor(0, s.length()));
    }
    
    public RequestLine parseRequestLine(final CharArrayBuffer charArrayBuffer, final ParserCursor parserCursor) throws ParseException {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        final int pos = parserCursor.getPos();
        final int upperBound = parserCursor.getUpperBound();
        this.skipWhitespace(charArrayBuffer, parserCursor);
        final int pos2 = parserCursor.getPos();
        final int index = charArrayBuffer.indexOf(32, pos2, upperBound);
        if (index < 0) {
            throw new ParseException("Invalid request line: " + charArrayBuffer.substring(pos, upperBound));
        }
        final String substringTrimmed = charArrayBuffer.substringTrimmed(pos2, index);
        parserCursor.updatePos(index);
        this.skipWhitespace(charArrayBuffer, parserCursor);
        final int pos3 = parserCursor.getPos();
        final int index2 = charArrayBuffer.indexOf(32, pos3, upperBound);
        if (index2 < 0) {
            throw new ParseException("Invalid request line: " + charArrayBuffer.substring(pos, upperBound));
        }
        final String substringTrimmed2 = charArrayBuffer.substringTrimmed(pos3, index2);
        parserCursor.updatePos(index2);
        final ProtocolVersion protocolVersion = this.parseProtocolVersion(charArrayBuffer, parserCursor);
        this.skipWhitespace(charArrayBuffer, parserCursor);
        if (!parserCursor.atEnd()) {
            throw new ParseException("Invalid request line: " + charArrayBuffer.substring(pos, upperBound));
        }
        return this.createRequestLine(substringTrimmed, substringTrimmed2, protocolVersion);
    }
    
    protected RequestLine createRequestLine(final String s, final String s2, final ProtocolVersion protocolVersion) {
        return new BasicRequestLine(s, s2, protocolVersion);
    }
    
    public static StatusLine parseStatusLine(final String s, final LineParser lineParser) throws ParseException {
        Args.notNull(s, "Value");
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(s.length());
        charArrayBuffer.append(s);
        return ((lineParser != null) ? lineParser : BasicLineParser.INSTANCE).parseStatusLine(charArrayBuffer, new ParserCursor(0, s.length()));
    }
    
    public StatusLine parseStatusLine(final CharArrayBuffer charArrayBuffer, final ParserCursor parserCursor) throws ParseException {
        Args.notNull(charArrayBuffer, "Char array buffer");
        Args.notNull(parserCursor, "Parser cursor");
        final int pos = parserCursor.getPos();
        final int upperBound = parserCursor.getUpperBound();
        final ProtocolVersion protocolVersion = this.parseProtocolVersion(charArrayBuffer, parserCursor);
        this.skipWhitespace(charArrayBuffer, parserCursor);
        final int pos2 = parserCursor.getPos();
        int index = charArrayBuffer.indexOf(32, pos2, upperBound);
        if (index < 0) {
            index = upperBound;
        }
        final String substringTrimmed = charArrayBuffer.substringTrimmed(pos2, index);
        while (0 < substringTrimmed.length()) {
            if (!Character.isDigit(substringTrimmed.charAt(0))) {
                throw new ParseException("Status line contains invalid status code: " + charArrayBuffer.substring(pos, upperBound));
            }
            int n = 0;
            ++n;
        }
        final int int1 = Integer.parseInt(substringTrimmed);
        final int n2 = index;
        String substringTrimmed2;
        if (n2 < upperBound) {
            substringTrimmed2 = charArrayBuffer.substringTrimmed(n2, upperBound);
        }
        else {
            substringTrimmed2 = "";
        }
        return this.createStatusLine(protocolVersion, int1, substringTrimmed2);
    }
    
    protected StatusLine createStatusLine(final ProtocolVersion protocolVersion, final int n, final String s) {
        return new BasicStatusLine(protocolVersion, n, s);
    }
    
    public static Header parseHeader(final String s, final LineParser lineParser) throws ParseException {
        Args.notNull(s, "Value");
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(s.length());
        charArrayBuffer.append(s);
        return ((lineParser != null) ? lineParser : BasicLineParser.INSTANCE).parseHeader(charArrayBuffer);
    }
    
    public Header parseHeader(final CharArrayBuffer charArrayBuffer) throws ParseException {
        return new BufferedHeader(charArrayBuffer);
    }
    
    protected void skipWhitespace(final CharArrayBuffer charArrayBuffer, final ParserCursor parserCursor) {
        int pos;
        for (pos = parserCursor.getPos(); pos < parserCursor.getUpperBound() && HTTP.isWhitespace(charArrayBuffer.charAt(pos)); ++pos) {}
        parserCursor.updatePos(pos);
    }
    
    static {
        DEFAULT = new BasicLineParser();
        INSTANCE = new BasicLineParser();
    }
}
