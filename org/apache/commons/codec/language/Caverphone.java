package org.apache.commons.codec.language;

import org.apache.commons.codec.*;

@Deprecated
public class Caverphone implements StringEncoder
{
    private final Caverphone2 encoder;
    
    public Caverphone() {
        this.encoder = new Caverphone2();
    }
    
    public String caverphone(final String s) {
        return this.encoder.encode(s);
    }
    
    @Override
    public Object encode(final Object o) throws EncoderException {
        if (!(o instanceof String)) {
            throw new EncoderException("Parameter supplied to Caverphone encode is not of type java.lang.String");
        }
        return this.caverphone((String)o);
    }
    
    @Override
    public String encode(final String s) {
        return this.caverphone(s);
    }
    
    public boolean isCaverphoneEqual(final String s, final String s2) {
        return this.caverphone(s).equals(this.caverphone(s2));
    }
}
