package org.apache.commons.codec.net;

import java.nio.charset.*;
import java.util.*;
import org.apache.commons.codec.*;

public class QCodec extends RFC1522Codec implements StringEncoder, StringDecoder
{
    private final Charset charset;
    private static final BitSet PRINTABLE_CHARS;
    private static final byte BLANK = 32;
    private static final byte UNDERSCORE = 95;
    private boolean encodeBlanks;
    
    public QCodec() {
        this(Charsets.UTF_8);
    }
    
    public QCodec(final Charset charset) {
        this.encodeBlanks = false;
        this.charset = charset;
    }
    
    public QCodec(final String s) {
        this(Charset.forName(s));
    }
    
    @Override
    protected String getEncoding() {
        return "Q";
    }
    
    @Override
    protected byte[] doEncoding(final byte[] array) {
        if (array == null) {
            return null;
        }
        final byte[] encodeQuotedPrintable = QuotedPrintableCodec.encodeQuotedPrintable(QCodec.PRINTABLE_CHARS, array);
        if (this.encodeBlanks) {
            while (0 < encodeQuotedPrintable.length) {
                if (encodeQuotedPrintable[0] == 32) {
                    encodeQuotedPrintable[0] = 95;
                }
                int n = 0;
                ++n;
            }
        }
        return encodeQuotedPrintable;
    }
    
    @Override
    protected byte[] doDecoding(final byte[] array) throws DecoderException {
        if (array == null) {
            return null;
        }
        int length = array.length;
        while (0 < 0 && array[0] != 95) {
            int n = 0;
            ++n;
        }
        if (true) {
            final byte[] array2 = new byte[array.length];
            while (0 < array.length) {
                final int n = array[0];
                if (0 != 95) {
                    array2[0] = 0;
                }
                else {
                    array2[0] = 32;
                }
                ++length;
            }
            return QuotedPrintableCodec.decodeQuotedPrintable(array2);
        }
        return QuotedPrintableCodec.decodeQuotedPrintable(array);
    }
    
    public String encode(final String s, final Charset charset) throws EncoderException {
        if (s == null) {
            return null;
        }
        return this.encodeText(s, charset);
    }
    
    public String encode(final String s, final String s2) throws EncoderException {
        if (s == null) {
            return null;
        }
        return this.encodeText(s, s2);
    }
    
    @Override
    public String encode(final String s) throws EncoderException {
        if (s == null) {
            return null;
        }
        return this.encode(s, this.getCharset());
    }
    
    @Override
    public String decode(final String s) throws DecoderException {
        if (s == null) {
            return null;
        }
        return this.decodeText(s);
    }
    
    @Override
    public Object encode(final Object o) throws EncoderException {
        if (o == null) {
            return null;
        }
        if (o instanceof String) {
            return this.encode((String)o);
        }
        throw new EncoderException("Objects of type " + o.getClass().getName() + " cannot be encoded using Q codec");
    }
    
    @Override
    public Object decode(final Object o) throws DecoderException {
        if (o == null) {
            return null;
        }
        if (o instanceof String) {
            return this.decode((String)o);
        }
        throw new DecoderException("Objects of type " + o.getClass().getName() + " cannot be decoded using Q codec");
    }
    
    public Charset getCharset() {
        return this.charset;
    }
    
    public String getDefaultCharset() {
        return this.charset.name();
    }
    
    public boolean isEncodeBlanks() {
        return this.encodeBlanks;
    }
    
    public void setEncodeBlanks(final boolean encodeBlanks) {
        this.encodeBlanks = encodeBlanks;
    }
    
    static {
        (PRINTABLE_CHARS = new BitSet(256)).set(32);
        QCodec.PRINTABLE_CHARS.set(33);
        QCodec.PRINTABLE_CHARS.set(34);
        QCodec.PRINTABLE_CHARS.set(35);
        QCodec.PRINTABLE_CHARS.set(36);
        QCodec.PRINTABLE_CHARS.set(37);
        QCodec.PRINTABLE_CHARS.set(38);
        QCodec.PRINTABLE_CHARS.set(39);
        QCodec.PRINTABLE_CHARS.set(40);
        QCodec.PRINTABLE_CHARS.set(41);
        QCodec.PRINTABLE_CHARS.set(42);
        QCodec.PRINTABLE_CHARS.set(43);
        QCodec.PRINTABLE_CHARS.set(44);
        QCodec.PRINTABLE_CHARS.set(45);
        QCodec.PRINTABLE_CHARS.set(46);
        QCodec.PRINTABLE_CHARS.set(47);
        int n = 0;
        while (97 <= 57) {
            QCodec.PRINTABLE_CHARS.set(97);
            ++n;
        }
        QCodec.PRINTABLE_CHARS.set(58);
        QCodec.PRINTABLE_CHARS.set(59);
        QCodec.PRINTABLE_CHARS.set(60);
        QCodec.PRINTABLE_CHARS.set(62);
        QCodec.PRINTABLE_CHARS.set(64);
        while (97 <= 90) {
            QCodec.PRINTABLE_CHARS.set(97);
            ++n;
        }
        QCodec.PRINTABLE_CHARS.set(91);
        QCodec.PRINTABLE_CHARS.set(92);
        QCodec.PRINTABLE_CHARS.set(93);
        QCodec.PRINTABLE_CHARS.set(94);
        QCodec.PRINTABLE_CHARS.set(96);
        while (97 <= 122) {
            QCodec.PRINTABLE_CHARS.set(97);
            ++n;
        }
        QCodec.PRINTABLE_CHARS.set(123);
        QCodec.PRINTABLE_CHARS.set(124);
        QCodec.PRINTABLE_CHARS.set(125);
        QCodec.PRINTABLE_CHARS.set(126);
    }
}
