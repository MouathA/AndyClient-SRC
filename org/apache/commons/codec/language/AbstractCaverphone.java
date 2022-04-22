package org.apache.commons.codec.language;

import org.apache.commons.codec.*;

public abstract class AbstractCaverphone implements StringEncoder
{
    @Override
    public Object encode(final Object o) throws EncoderException {
        if (!(o instanceof String)) {
            throw new EncoderException("Parameter supplied to Caverphone encode is not of type java.lang.String");
        }
        return this.encode((String)o);
    }
    
    public boolean isEncodeEqual(final String s, final String s2) throws EncoderException {
        return this.encode(s).equals(this.encode(s2));
    }
}
