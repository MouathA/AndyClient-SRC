package org.apache.commons.codec.net;

import java.nio.charset.*;
import org.apache.commons.codec.binary.*;
import java.io.*;
import org.apache.commons.codec.*;

abstract class RFC1522Codec
{
    protected static final char SEP = '?';
    protected static final String POSTFIX = "?=";
    protected static final String PREFIX = "=?";
    
    protected String encodeText(final String s, final Charset charset) throws EncoderException {
        if (s == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("=?");
        sb.append(charset);
        sb.append('?');
        sb.append(this.getEncoding());
        sb.append('?');
        sb.append(StringUtils.newStringUsAscii(this.doEncoding(s.getBytes(charset))));
        sb.append("?=");
        return sb.toString();
    }
    
    protected String encodeText(final String s, final String s2) throws EncoderException, UnsupportedEncodingException {
        if (s == null) {
            return null;
        }
        return this.encodeText(s, Charset.forName(s2));
    }
    
    protected String decodeText(final String s) throws DecoderException, UnsupportedEncodingException {
        if (s == null) {
            return null;
        }
        if (!s.startsWith("=?") || !s.endsWith("?=")) {
            throw new DecoderException("RFC 1522 violation: malformed encoded content");
        }
        final int n = s.length() - 2;
        final int index = s.indexOf(63, 2);
        if (index == n) {
            throw new DecoderException("RFC 1522 violation: charset token not found");
        }
        final String substring = s.substring(2, index);
        if (substring.equals("")) {
            throw new DecoderException("RFC 1522 violation: charset not specified");
        }
        final int index2 = s.indexOf(63, 2);
        if (index2 == n) {
            throw new DecoderException("RFC 1522 violation: encoding token not found");
        }
        final String substring2 = s.substring(2, index2);
        if (!this.getEncoding().equalsIgnoreCase(substring2)) {
            throw new DecoderException("This codec cannot decode " + substring2 + " encoded content");
        }
        return new String(this.doDecoding(StringUtils.getBytesUsAscii(s.substring(2, s.indexOf(63, 2)))), substring);
    }
    
    protected abstract String getEncoding();
    
    protected abstract byte[] doEncoding(final byte[] p0) throws EncoderException;
    
    protected abstract byte[] doDecoding(final byte[] p0) throws DecoderException;
}
