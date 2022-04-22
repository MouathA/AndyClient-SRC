package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class ByteTagConverter implements TagConverter
{
    public Byte convert(final ByteTag byteTag) {
        return byteTag.getValue();
    }
    
    public ByteTag convert(final Byte b) {
        return new ByteTag(b);
    }
    
    @Override
    public Tag convert(final Object o) {
        return this.convert((Byte)o);
    }
    
    @Override
    public Object convert(final Tag tag) {
        return this.convert((ByteTag)tag);
    }
}
