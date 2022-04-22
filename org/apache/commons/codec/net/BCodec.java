package org.apache.commons.codec.net;

import java.nio.charset.*;
import org.apache.commons.codec.binary.*;
import org.apache.commons.codec.*;

public class BCodec extends RFC1522Codec implements StringEncoder, StringDecoder
{
    private final Charset charset;
    
    public BCodec() {
        this(Charsets.UTF_8);
    }
    
    public BCodec(final Charset charset) {
        this.charset = charset;
    }
    
    public BCodec(final String s) {
        this(Charset.forName(s));
    }
    
    @Override
    protected String getEncoding() {
        return "B";
    }
    
    @Override
    protected byte[] doEncoding(final byte[] array) {
        if (array == null) {
            return null;
        }
        return Base64.encodeBase64(array);
    }
    
    @Override
    protected byte[] doDecoding(final byte[] array) {
        if (array == null) {
            return null;
        }
        return Base64.decodeBase64(array);
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
        throw new EncoderException("Objects of type " + o.getClass().getName() + " cannot be encoded using BCodec");
    }
    
    @Override
    public Object decode(final Object o) throws DecoderException {
        if (o == null) {
            return null;
        }
        if (o instanceof String) {
            return this.decode((String)o);
        }
        throw new DecoderException("Objects of type " + o.getClass().getName() + " cannot be decoded using BCodec");
    }
    
    public Charset getCharset() {
        return this.charset;
    }
    
    public String getDefaultCharset() {
        return this.charset.name();
    }
}
