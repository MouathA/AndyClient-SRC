package org.apache.commons.codec.net;

import org.apache.commons.codec.*;

class Utils
{
    static int digit16(final byte b) throws DecoderException {
        final int digit = Character.digit((char)b, 16);
        if (digit == -1) {
            throw new DecoderException("Invalid URL encoding: not a valid digit (radix 16): " + b);
        }
        return digit;
    }
}
