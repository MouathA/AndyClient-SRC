package org.apache.http.impl;

import org.apache.http.config.*;
import java.nio.charset.*;

public final class ConnSupport
{
    public static CharsetDecoder createDecoder(final ConnectionConfig connectionConfig) {
        if (connectionConfig == null) {
            return null;
        }
        final Charset charset = connectionConfig.getCharset();
        final CodingErrorAction malformedInputAction = connectionConfig.getMalformedInputAction();
        final CodingErrorAction unmappableInputAction = connectionConfig.getUnmappableInputAction();
        if (charset != null) {
            return charset.newDecoder().onMalformedInput((malformedInputAction != null) ? malformedInputAction : CodingErrorAction.REPORT).onUnmappableCharacter((unmappableInputAction != null) ? unmappableInputAction : CodingErrorAction.REPORT);
        }
        return null;
    }
    
    public static CharsetEncoder createEncoder(final ConnectionConfig connectionConfig) {
        if (connectionConfig == null) {
            return null;
        }
        final Charset charset = connectionConfig.getCharset();
        if (charset != null) {
            final CodingErrorAction malformedInputAction = connectionConfig.getMalformedInputAction();
            final CodingErrorAction unmappableInputAction = connectionConfig.getUnmappableInputAction();
            return charset.newEncoder().onMalformedInput((malformedInputAction != null) ? malformedInputAction : CodingErrorAction.REPORT).onUnmappableCharacter((unmappableInputAction != null) ? unmappableInputAction : CodingErrorAction.REPORT);
        }
        return null;
    }
}
