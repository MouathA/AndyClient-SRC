package org.apache.commons.compress.archivers.zip;

import java.nio.*;
import java.nio.charset.*;
import java.io.*;

class NioZipEncoding implements ZipEncoding
{
    private final Charset charset;
    
    public NioZipEncoding(final Charset charset) {
        this.charset = charset;
    }
    
    public boolean canEncode(final String s) {
        final CharsetEncoder encoder = this.charset.newEncoder();
        encoder.onMalformedInput(CodingErrorAction.REPORT);
        encoder.onUnmappableCharacter(CodingErrorAction.REPORT);
        return encoder.canEncode(s);
    }
    
    public ByteBuffer encode(final String s) {
        final CharsetEncoder encoder = this.charset.newEncoder();
        encoder.onMalformedInput(CodingErrorAction.REPORT);
        encoder.onUnmappableCharacter(CodingErrorAction.REPORT);
        final CharBuffer wrap = CharBuffer.wrap(s);
        ByteBuffer byteBuffer = ByteBuffer.allocate(s.length() + (s.length() + 1) / 2);
        while (wrap.remaining() > 0) {
            final CoderResult encode = encoder.encode(wrap, byteBuffer, true);
            if (encode.isUnmappable() || encode.isMalformed()) {
                if (encode.length() * 6 > byteBuffer.remaining()) {
                    byteBuffer = ZipEncodingHelper.growBuffer(byteBuffer, byteBuffer.position() + encode.length() * 6);
                }
                while (0 < encode.length()) {
                    ZipEncodingHelper.appendSurrogate(byteBuffer, wrap.get());
                    int n = 0;
                    ++n;
                }
            }
            else if (encode.isOverflow()) {
                byteBuffer = ZipEncodingHelper.growBuffer(byteBuffer, 0);
            }
            else {
                if (encode.isUnderflow()) {
                    encoder.flush(byteBuffer);
                    break;
                }
                continue;
            }
        }
        byteBuffer.limit(byteBuffer.position());
        byteBuffer.rewind();
        return byteBuffer;
    }
    
    public String decode(final byte[] array) throws IOException {
        return this.charset.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(array)).toString();
    }
}
