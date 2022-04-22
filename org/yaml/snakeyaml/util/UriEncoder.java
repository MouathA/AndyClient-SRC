package org.yaml.snakeyaml.util;

import java.nio.*;
import java.net.*;
import java.nio.charset.*;
import org.yaml.snakeyaml.external.com.google.gdata.util.common.base.*;

public abstract class UriEncoder
{
    private static final CharsetDecoder UTF8Decoder;
    private static final String SAFE_CHARS = "-_.!~*'()@:$&,;=[]/";
    private static final Escaper escaper;
    
    public static String encode(final String s) {
        return UriEncoder.escaper.escape(s);
    }
    
    public static String decode(final ByteBuffer byteBuffer) throws CharacterCodingException {
        return UriEncoder.UTF8Decoder.decode(byteBuffer).toString();
    }
    
    public static String decode(final String s) {
        return URLDecoder.decode(s, "UTF-8");
    }
    
    static {
        UTF8Decoder = Charset.forName("UTF-8").newDecoder().onMalformedInput(CodingErrorAction.REPORT);
        escaper = new PercentEscaper("-_.!~*'()@:$&,;=[]/", false);
    }
}
